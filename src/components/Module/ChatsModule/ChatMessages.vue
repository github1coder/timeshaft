<template>
  <div class="chat-content">
    <div class="messages" id="scroll-target" >
      <div :class="draw ? 'message-container-open' : 'message-container-close'">
        <v-list  three-line dark v-scroll:#scroll-target="onScroll">
          <template v-for="(message, i) in messages" class="chat-list">
            <v-list-item :key="i" class="chat-list-item">
              <v-list-item-avatar>
                <!--TODO-- 等待对接后改成图片>-->
                <v-img :src="message.msgFromAvatar"></v-img>
<!--                <v-icon v-text="message.msgFromAvatar"></v-icon>-->
              </v-list-item-avatar>
              <!--        TODO 聊天样式调整 & 一左一右 & 不同特效 & 发送状态-->
              <v-list-item-content>
                <v-list-item-title>{{ message.msgFromName }}</v-list-item-title>
                <v-list-item-subtitle>{{ message.msg }}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </template>
        </v-list>
      </div>
    </div>
    <ChatForm :draw="draw" @send="socketSend"></ChatForm>
  </div>
</template>

<script>
import {chatUrl, getHistoryMessage} from "@/api/message";
import ChatForm from "@/components/Module/ChatsModule/ChatForm";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

export default {
  name: "ChatMessages",
  components: {ChatForm},
  props: ['draw'],
  data() {
    return {
      refreshed: true,
      cache: 0,
      messages: [],
      contactClient: null,
      contactSocket: null,
    }
  },
  methods: {
    onScroll() {
      console.log("It's scrolling")
      if (!this.refreshed && document.documentElement.scrollTop || document.querySelector('.messages').scrollTop === 0) {
        this.refreshed = true
      } else if (document.documentElement.scrollTop || document.querySelector('.messages').scrollTop !== 0) {
        this.refreshed = false
      }
      console.log(this.refreshed)
    },

    scrollToBottom() {
      this.$nextTick(()=>{
        const list = this.$el.querySelector(".messages");
        console.log("list is:")
        console.log(list)
        list.scrollTop = list.scrollHeight
      })

    },
    socketSend(payload) {
      // TODO 改成传参
      console.log(this.$store.state.chatClient)
      console.log(payload)
      this.messages.push(payload.data)
      this.$store.state.chatClient.send(payload.url, {}, JSON.stringify(payload.data));
      console.log("send + " + JSON.stringify(payload.data) + " to " + payload.url)
    },
    socketInit() {
      console.log("初始化聊天列表socket")
      this.chatClient = this.$store.state.chatClient
      this.chatSocket = this.$store.state.chatSocket
      if (this.chatClient == null || !this.chatClient.connected) {
        this.socketUrl = this.$store.state.DEBUG ? 'http://localhost:8080/websocket' : 'http://182.92.163.68:8080/websocket'
        if (this.chatClient != null && this.chatSocket.readyState === SockJS.OPEN) {
          this.chatClient.disconnect(() => {
            this.socketConnect()
          })
        } else if (this.chatClient != null && this.chatSocket.readyState === SockJS.CONNECTING) {
          console.log("连接正在建立")
          return;
        } else {
          console.log("第一次建立")
          this.socketConnect()
        }
        if (!this.checkInterval) {
          this.checkInterval = setInterval(() => {
            console.log("检测连接：" + this.chatSocket.readyState)
            if (this.chatClient != null &&this.chatClient.connected) {
              clearInterval(this.checkInterval)
              this.checkInterval = null
              console.log('重连成功')
            } else if (this.chatClient != null && this.chatSocket.readyState !== SockJS.CONNECTING) {
              //经常会遇到websocket的状态为open 但是stompClient的状态却是未连接状态，故此处需要把连接断掉 然后重连
              this.chatClient.disconnect(() => {
                this.socketConnect()
              })
            }
          }, 2000)
        }
      } else {
        console.log("连接已建立成功，不再执行")
      }
      this.$store.state.chatSocket = this.chatSocket
      this.$store.state.chatClient = this.chatClient
    },

    socketConnect() {
      this.chatSocket = new SockJS(this.socketUrl)
      this.chatClient = Stomp.over(this.chatSocket);
      this.chatClient.debug = null //关闭控制台打印
      this.chatClient.heartbeat.outgoing = 20000;
      this.chatClient.heartbeat.incoming = 0; //客户端不从服务端接收心跳包
      // 向服务器发起websocket连接
      this.chatClient.connect({ name: this.$store.state.myNick }, //此处注意更换自己的用户名，最好以参数形式带入
          frame => { // eslint-disable-line no-unused-vars
            console.log('链接成功！')
            console.log(this.chatClient)
            chatUrl({
              userId: this.$store.state.userId
            }).then(res => {
              this.chatClient.subscribe(res.url, payload => {
                let json = JSON.parse(payload.body)
                console.log("收到的json:")
                console.log(json)
                if (res.data.type === 0) {
                  console.log("即时通讯服务收到消息")
                  //TODO 具体逻辑
                  this.$emit("receive")
                  this.messages.push(json)
                  console.log(json)
                } else {
                  console.log("不合法的消息类型:" + res.data.type.toString())
                }
              })
            })
          },
          err => { // eslint-disable-line no-unused-vars
            setTimeout(() => {
              console.log("reconnecting...")
              this.socketInit()
            }, 20000)
          }
      );
      this.$store.state.contactSocket = this.contactSocket
      this.$store.state.contactClient = this.contactClient
    },
    clearMessages() {
      this.messages = []
    },
    init() {
      this.clearMessages()
      this.socketInit()
      setTimeout(() => {
        getHistoryMessage({
          userId: this.$store.state.userId,
          type: this.$store.state.currentChatType,
          chatId: this.$store.state.currentChannelId,
          index: this.$store.state.currentChatIndex,
        }).then(res => {
          console.log("拉取 " + res.data.length + " 条历史消息")
          console.log(res)
          this.$store.state.currentChatMore = res.more
          if (this.$store.state.currentChannelIdx !== -1) {
            this.$store.state.currentChatIndex = res.index
            console.log(this.messages)
            for (let i = res.data.length-1; i >= 0; i--) {
              this.messages.unshift(res.data[i])
            }
            this.$store.state.currentChatHaveRead += res.data.length
            console.log(this.messages)
          }
        })
      }, 100)
    }
  },
  computed: {

  },
  watch: {
    refreshed(newVal, oldVal) {
      if (newVal && !oldVal) {
        //TODO 补全
        console.log("more: " + this.$store.state.currentChatMore)
        if (this.$store.state.currentChatMore) {
          getHistoryMessage({
            userId: this.$store.state.userId,
            type: this.$store.state.currentChatType,
            chatId: this.$store.state.currentChannelId,
            index: this.$store.state.currentChatIndex,
          }).then(res => {
            console.log("拉取 " + res.data.length + " 条历史消息")
            console.log(res)
            this.$store.state.currentChatMore = res.more
            if (this.$store.state.currentChannelIdx !== -1) {
              this.$store.state.currentChatIndex = res.index
              console.log(this.messages)
              for (let i = res.data.length-1; i >= 0; i--) {
                this.messages.unshift(res.data[i])
              }
              this.$store.state.currentChatHaveRead += res.data.length
              console.log(this.messages)
            }
          })
        }
      }
    },
    messages(newVal, oldVal) {
      newVal
      oldVal
      this.scrollToBottom()
    },

  },

  created() {
    this.init()
  }

}
</script>

<style scoped>

</style>