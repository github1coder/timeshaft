<template>
  <div class="dc-container">
    <Navigations></Navigations>
    <div class="base">
      <ChatsModule
        ref="chatModule"
        v-if="$store.state.siderState === 0"
      ></ChatsModule>
      <ContractsModule
        v-else-if="$store.state.siderState === 1"
        ref="contractsModule"
      ></ContractsModule>
      <StarModule v-else-if="$store.state.siderState === 2"></StarModule>
      <CalendarModule v-else-if="$store.state.siderState === 3"></CalendarModule>

      <!-- <About v-else-if="$store.state.siderState === 4"></About> -->
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import ContractsModule from "@/components/Module/ContactsModule";
// import About from "@/components/About/About";
import Navigations from "@/components/Navigations";
import ChatsModule from "@/components/Module/ChatsModule";
import CalendarModule from "@/components/Module/CalendarModule";
import StarModule from "@/components/Module/StarModule";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { getListenerList } from "@/api/message";


export default {
  name: "Home",
  components: {
    CalendarModule,
    ChatsModule,
    Navigations,
    ContractsModule,
    StarModule,
  },
  data () {
    return {
      number: 0,
    }
  },
  created () {

    window.onbeforeunload = () => {
      this.$store.state.serviceClient = null
      this.$store.state.serviceSocket = null
      this.$store.state.currentChatType = null
      this.$store.state.currentChatIndex = null
      this.$store.state.currentChatMore = null
      this.$store.state.currentChatName = null
      this.$store.state.currentChannelIdx = -1
      this.$store.state.currentChannelId = -1
      console.log(this.$store.state)
      sessionStorage.setItem("data", JSON.stringify(this.$store.state))
      console.log("save")
      console.log(sessionStorage.getItem("data"))
    }
    console.log("get")
    console.log(sessionStorage.getItem("data"))
    if (sessionStorage.getItem("data")) {
      console.log(JSON.parse(sessionStorage.getItem("data")))
      this.$store.replaceState(JSON.parse(sessionStorage.getItem("data")))
      console.log(JSON.parse(sessionStorage.getItem("data")))
      setTimeout(function () {
        sessionStorage.removeItem("data");
      }, 300)
    }
    this.socketInit()

  },
  computed: {

  },
  methods: {
    socketInit () {
      console.log("初始化socket")
      if (this.$store.state.serviceClient == null || !this.$store.state.serviceClient.connected) {
        this.socketUrl = this.$store.state.DEBUG ? 'http://localhost:8080/websocket' : process.env.VUE_APP_baseURL + '/websocket'
        if (this.$store.state.serviceClient != null && this.$store.state.serviceSocket.readyState === SockJS.OPEN) {
          this.$store.state.serviceClient.disconnect(() => {
            this.socketConnect()
          })
        } else if (this.$store.state.serviceClient != null && this.$store.state.serviceSocket.readyState === SockJS.CONNECTING) {
          console.log("连接正在建立")
          return;
        } else {
          console.log("第一次建立")
          this.socketConnect()
        }
        if (!this.checkInterval) {
          this.checkInterval = setInterval(() => {
            console.log("检测连接：" + this.$store.state.serviceSocket.readyState)
            if (this.$store.state.serviceClient != null && this.$store.state.serviceClient.connected) {
              clearInterval(this.checkInterval)
              this.checkInterval = null
              console.log('重连成功')
            } else if (this.$store.state.serviceClient != null && this.$store.state.serviceSocket.readyState !== SockJS.CONNECTING) {
              //经常会遇到websocket的状态为open 但是stompClient的状态却是未连接状态，故此处需要把连接断掉 然后重连
              this.$store.state.serviceClient.disconnect(() => {
                this.socketConnect()
              })
            }
          }, 2000)
        }
      } else {
        console.log("连接已建立成功，不再执行")
      }
    },

    socketConnect () {
      const that = this
      this.$store.state.serviceSocket = new SockJS(this.socketUrl)
      this.$store.state.serviceClient = Stomp.over(this.$store.state.serviceSocket);
      this.$store.state.serviceClient.debug = null //关闭控制台打印
      this.$store.state.serviceClient.heartbeat.outgoing = 20000;
      this.$store.state.serviceClient.heartbeat.incoming = 0; //客户端不从服务端接收心跳包
      // 向服务器发起websocket连接
      this.$store.state.serviceClient.connect({ name: this.$store.state.myNick }, //此处注意更换自己的用户名，最好以参数形式带入
        frame => { // eslint-disable-line no-unused-vars
          console.log('链接成功！')
          console.log(this.$store.state.serviceClient)
          // TODO url合并？
          getListenerList({
          }).then(res => {
            console.log('链接成功！')
            console.log(this.$store.state.serviceClient)
            console.log(res)
            for (let listener in res) {
              this.$store.state.serviceClient.subscribe(res[listener].url, payload => {
                let json = JSON.parse(payload.body)
                console.log("收到的json:")
                console.log(json)
                if (res[listener].type === 0) {
                  console.log("即时通信服务收到消息")
                  setTimeout(() => {
                    if (this.$store.state.currentChannelId !== json.chatId && this.$store.state.currentChatType === json.type || this.$store.state.siderState !== 0) {
                      this.$store.state.unreadNum += 1
                    }
                    if ('chatModule' in this.$refs && 'receiveMessage' in this.$refs.chatModule) {
                      this.$refs.chatModule.receiveMessage(json)
                    }
                  }, 1000)
                } else if (res[listener].type === 1) {
                  console.log("添加好友服务收到消息")
                  setTimeout(() => {
                    this.$refs.chatModule.messages.push(json)
                  }, 100)
                } else if (res[listener].type === 2) {
                  console.log("会议状态服务收到消息")
                  setTimeout(() => {
                    const idx = this.$refs.chatModule.messages.findIndex(message => {
                      return message.id === json.chatId && message.type === json.type
                    })
                    if (idx !== -1) {
                      this.$refs.chatModule.messages[idx].isMeeting = json.isMeeting
                      if (that.$store.state.currentChannelId === this.$refs.chatModule.messages[idx].id) {
                        if (json.isMeeting === false) {
                          console.log("会议状态：开始=>关闭")
                          this.$refs.chatModule.$refs.timeTool.endOk(true)
                          if (this.$refs.chatModule.$refs.timeShaft) {
                            this.$refs.chatModule.$refs.timeShaft.getShaft()
                          }
                        }
                        else {
                          console.log("会议状态：关闭=>开始")
                          this.$refs.chatModule.$refs.timeTool.tryOk(true)
                        }
                      }
                    } else {
                      console.log("未找到聊天id:" + json.chatId)
                    }
                  }, 100)
                } else {
                  console.log("未知url类型:" + res[listener].type.toString())
                }
              })
              console.log(res[listener].url)
            }
          })
        },
        err => { // eslint-disable-line no-unused-vars
          setTimeout(() => {
            console.log("reconnecting...")
            this.socketInit()
          }, 20000)
        }
      );
    },
  }

};
</script>

<style lang="scss"></style>