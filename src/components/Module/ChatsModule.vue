<template>
  <div class="base-content">
    <div class="sidebar">
      <div class="sb-container">
        <v-card
            height="100%"
            dark
            flat
            tile
            class="server-info"
        >
          <v-card
              height="60"
              tile
              class="server-title"
          >
            <div v-on:keyup.enter="search">
              <v-menu offset-y>
                <template v-slot:activator="{ on }">
                  <v-text-field
                      clearable
                      outlined
                      dense
                      dark
                      hide-details
                      label="请输入关键词"
                      v-model="text"
                      class="input-search mt-3"
                      autocomplete="off"
                      v-on="on"
                      ref="search"
                  ></v-text-field>
                </template>
                <v-list
                    v-if="searchResult.length > 0"
                    class="border-list"
                    dense
                >
                  <v-list-item
                      v-for="(item, index) in searchResult"
                      :key="index"
                      @click="itemClick(item)"
                  >
                    <v-list-item-title>{{ item.name }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
          </v-card>
          <v-card
              tile
              class="channels"
          >
            <!-- TODO expansion needed! -->
            <v-list
                width="100%"
                rounded
                max-height="80px"
            >
              <v-list-item-group color="primary">
                <v-list-item
                    v-for="(item, i) in this.messages"
                    @click="selectChannel(item.id, i, item)"
                    :id="'message-'+item.id.toString()"
                    :key="i"
                >
                  <v-list-item-avatar>
                    <v-img
                        v-if="item.type==='private'"
                        max-height="70px"
                        max-width="50px"
                        :src="item.chatAvatar"
                    ></v-img>
                    <v-avatar
                        v-else-if="item.type==='group'"
                    >
                      <span class="white--text text-h5">{{ item.chatName[0] }}</span>
                    </v-avatar>
                  </v-list-item-avatar>
                  <v-list-item-content>
                    <v-list-item-title
                        class="channel-title"
                        v-text="item.chatName"
                    ></v-list-item-title>
                    <v-list-item-content
                        style="text-align: left; font-size: 5px"
                    > {{item.number > 0? ("[未读 " + (item.number).toString() +" 条]") : ""  }} {{item.lastMessage.msg}}</v-list-item-content>
                  </v-list-item-content>
                  <v-list-item-content
                      style="text-align: right; font-size: 1px"
                      v-if="item.lastMessage !== null > 0"
                  >
                    {{item.lastMessage.time}}
                  </v-list-item-content>

                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-card>
        </v-card>
      </div>
    </div>
    <div class="chat">
      <ChatHeader></ChatHeader>
      <div class="chat-screen" v-if="$store.state.currentChannelIdx !== -1">
        <ChatMessages ref="chatMessage" :draw="toolsDrawer"></ChatMessages>
        <div
          class="moveBand"
          v-if="toolsDrawer"
        >
          <TimeShaft v-if="tools[0].show"></TimeShaft>
          <info-tool v-if="tools[1].show"></info-tool>
        </div>
      </div>
    </div>
    <ChatTools
      :draw="toolsDrawer"
      :tools="tools"
      @callback="callback"
    ></ChatTools>
  </div>
</template>

<script>
import ChatTools from "@/components/Module/ChatsModule/ChatTools";
import ChatHeader from "@/components/Module/ChatsModule/ChatHeader";
import ChatMessages from "@/components/Module/ChatsModule/ChatMessages";
import TimeShaft from "@/components/Module/ChatsModule/ChatTools/TimeShaft";
import InfoTool from "@/components/Module/ChatsModule/ChatTools/InfoTool"
import {contactUrl, getMessagesList, haveRead} from "@/api/message";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
export default {
  name: "ChatsModule",
  components: { ChatMessages, ChatHeader, ChatTools, TimeShaft, InfoTool },
  data () {
    return {
      toolsDrawer: false, // 用于控制工具栏打开与否
      tools: [{
        icon: 'mdi-timeline',
        text: '',
        show: false
      }, {
        icon: 'mdi-cloud-search-outline',
        text: '',
        show: false
      }, {
        icon: 'mdi-cog-outline',
        text: '',
        show: false
      },
      ],
      text: '',
      showSelect: true,
      searchResult: [],
      messageList: [],
      contactClient: null,
      contactSocket: null,
      socketUrl: null,
      checkInterval: null,
    }
  },
  methods: {
    callback (flag) {
      this.toolsDrawer = flag
    },
    itemClick (item) {
      this.text = item.name
      this.$refs.search.blur()
      // this.$router.push()
    },
    search () {
      this.$refs.search.blur()
      console.log(this.text)
    },
    toggleAC () {
      this.$store.commit("toggleAC");
    },
    selectChannel (id, idx, item) {
      // 等画面完全渲染
      setTimeout(()=> {
        console.log(this.$refs)
        this.$refs.chatMessage.init()
      }, 100)
      //关闭工具栏
      this.$parent.toolsDrawer = false
      console.log(id)
      console.log(item)
      this.$store.commit("changeChannel", { id: id, idx: idx, type: item.type, index: item.index });
      if (item.number !== 0) {
        item.number = 0
        haveRead({
          type: item.type,
          chatId: item.id,
          userId: this.$store.state.userId,
          time: item.time,
        }).then(res => {
          res
          console.log("have read this msg")
        })
      }
    },

    socketInit() {
      console.log("初始化聊天列表socket")
      console.log(this.contactClient)
      console.log(this.$store.state.contactClient)
      this.chatClient = this.$store.state.contactClient
      this.chatSocket = this.$store.state.contactSocket
      if (this.contactClient == null || !this.contactClient.connected) {
        this.socketUrl = this.$store.state.DEBUG ? 'http://localhost:8080/websocket' : 'http://182.92.163.68:8080/websocket'
        if (this.contactClient != null && this.contactSocket.readyState === SockJS.OPEN) {
          this.contactClient.disconnect(() => {
            this.socketConnect()
          })
        } else if (this.contactClient != null && this.contactSocket.readyState === SockJS.CONNECTING) {
          console.log("连接正在建立")
          return;
        } else {
          console.log("第一次建立")
          this.socketConnect()
        }
        if (!this.checkInterval) {
          this.checkInterval = setInterval(() => {
            console.log("检测连接：" + this.contactSocket.readyState)
            if (this.contactClient != null &&this.contactClient.connected) {
              clearInterval(this.checkInterval)
              this.checkInterval = null
              console.log('重连成功')
            } else if (this.contactClient != null && this.contactSocket.readyState !== SockJS.CONNECTING) {
              //经常会遇到websocket的状态为open 但是stompClient的状态却是未连接状态，故此处需要把连接断掉 然后重连
              this.contactClient.disconnect(() => {
                this.socketConnect()
              })
            }
          }, 2000)
        }
      } else {
        console.log("连接已建立成功，不再执行")
      }
    },

    socketConnect() {
      this.contactSocket = new SockJS(this.socketUrl)
      this.contactClient = Stomp.over(this.contactSocket);
      this.contactClient.debug = null //关闭控制台打印
      this.contactClient.heartbeat.outgoing = 20000;
      this.contactClient.heartbeat.incoming = 0; //客户端不从服务端接收心跳包
      // 向服务器发起websocket连接
      this.contactClient.connect({ name: this.$store.state.myNick }, //此处注意更换自己的用户名，最好以参数形式带入
          frame => { // eslint-disable-line no-unused-vars
            console.log('链接成功！')
            console.log(this.contactClient)
            // TODO url合并？
            contactUrl({
              userId: this.$store.state.userId
            }).then(res => {
              this.contactClient.subscribe(res.url, payload => {
                let json = JSON.parse(payload.body)
                console.log("收到的json:")
                console.log(json)
                if (res.data.type === 1) {
                  console.log("添加好友服务收到消息")
                  //TODO 具体逻辑
                  this.messages.push(json)
                  console.log(json)
                } else if(res.data.type === 2) {
                  console.log("添加群聊服务收到消息")
                  //TODO 具体逻辑
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
    }
  },
  created() {
    this.socketInit()
    setTimeout(
        () => {
          getMessagesList({
            srcId: this.$store.state.userId,
          }).then(res => {
            console.log("收到联系人列表")
            console.log(this.messageList)
            for (let d in res) {
              this.messageList.push(res[d])
            }
            console.log(this.messageList)
          })
        }, 100
    )
  },
  computed: {
    messages () {
      return this.messageList
    },
  }
}
</script>

<style scoped>
</style>