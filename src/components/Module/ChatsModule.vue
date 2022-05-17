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
              <v-list-item-group color="primary" mandatory>
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
                    <v-avatar v-else-if="item.type==='group'">
                      <span class="white--text text-h5">{{ item.chatName[0] }}</span>
                    </v-avatar>
                  </v-list-item-avatar>
                  <v-list-item-content>
                    <v-list-item-title
                      class="channel-title"
                      v-text="item.chatName"
                    ></v-list-item-title>
                    <v-list-item-content style="text-align: left; font-size: 5px"> {{item.number > 0? ("[未读 " + (item.number).toString() +" 条]") : ""  }} {{item.lastMessage.msg}}</v-list-item-content>
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
      <div
        class="chat-screen"
        v-show="$store.state.currentChannelIdx !== -1"
      >
        <ChatMessages
          @send="sendMessage"
          @receive="receiveMessage"
          ref="chatMessage"
          :draw="toolsDrawer"
        ></ChatMessages>
        <div
          class="moveBand"
          v-show="toolsDrawer"
        >
          <TimeShaft
            v-show="tools[0].show"
            ref="timeShaft"
          ></TimeShaft>
          <InfoPage
            v-show="tools[1].show"
            ref="infoPage"
          ></InfoPage>
        </div>
      </div>
    </div>
    <ChatTools
      :draw="toolsDrawer"
      :tools="tools"
      v-show="$store.state.currentChannelIdx !== -1"
      @callback="callback"
    ></ChatTools>
  </div>
</template>

<script>
import ChatTools from "@/components/Module/ChatsModule/ChatTools";
import ChatHeader from "@/components/Module/ChatsModule/ChatHeader";
import ChatMessages from "@/components/Module/ChatsModule/ChatMessages";
import TimeShaft from "@/components/Module/ChatsModule/ChatTools/TimeShaft";
import InfoPage from "@/components/Module/ChatsModule/ChatTools/InfoPage"
import { chatUrl, contactUrl, getMessagesList, haveRead } from "@/api/message";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
export default {
  name: "ChatsModule",
  components: { ChatMessages, ChatHeader, ChatTools, TimeShaft, InfoPage },
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
      messagesList: [],
      socketUrl: null,
      checkInterval: null,
    }
  },
  methods: {
    sendMessage (payload) {
      payload.type = this.$store.state.currentChatType
      console.log(this.messages)
      console.log(payload)
      const idx = this.messages.findIndex(message => {
        return message.id === payload.chatId && message.type === payload.type
      })
      console.log("idx: " + idx)
      if (idx !== -1) {
        console.log("out" + payload.time)
        this.messages[idx].lastTime = payload.time
        this.messages[idx].lastMessage = {
          msg: payload.msg,
          time: payload.time
        }
      } else {
        console.log("没有对应的聊天框" + payload.id + " " + payload.type)
      }
    }
    ,
    receiveMessage (payload) {
      console.log(this.messages)
      const idx = this.messages.findIndex(message => {
        return message.id === payload.chatId && message.type === payload.type
      })
      console.log("idx: " + idx)
      if (idx !== -1) {
        console.log("in" + payload.time)
        this.messages[idx].lastTime = payload.time
        this.messages[idx].number += 1
        this.messages[idx].lastMessage = {
          msg: payload.msg,
          time: payload.time
        }
        if (this.$refs.chatMessage !== undefined) {
          console.log(this.$refs.chatMessage)
          this.$refs.chatMessage.messages.push(payload)
          this.$refs.chatMessage.scrollToBottom()
        }

      } else {
        console.log("没有对应的聊天框" + payload.id + " " + payload.type)
      }
    },
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
      //关闭工具栏
      this.$parent.toolsDrawer = false
      console.log(id)
      console.log(item)
      if (idx !== this.$store.state.currentChannelIdx) {
        this.$store.commit("changeChannel", { id: id, idx: idx, type: item.type, time: item.lastMessage.time });
        // 等画面完全渲染
        setTimeout(()=> {
          console.log(this.$refs)
          this.$refs.chatMessage.init()
          this.$refs.timeShaft.getShaft()
        }, 100)
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
      }

    },

    socketInit () {
      console.log("初始化聊天列表socket")
      if (this.$store.state.contactClient == null || !this.$store.state.contactClient.connected) {
        this.socketUrl = this.$store.state.DEBUG ? 'http://localhost:8080/websocket' : 'http://182.92.163.68:8080/websocket'
        if (this.$store.state.contactClient != null && this.$store.state.contactSocket.readyState === SockJS.OPEN) {
          this.$store.state.contactClient.disconnect(() => {
            this.socketConnect()
          })
        } else if (this.$store.state.contactClient != null && this.$store.state.contactSocket.readyState === SockJS.CONNECTING) {
          console.log("连接正在建立")
          return;
        } else {
          console.log("第一次建立")
          this.socketConnect()
        }
        if (!this.checkInterval) {
          this.checkInterval = setInterval(() => {
            console.log("检测连接：" + this.$store.state.contactSocket.readyState)
            if (this.$store.state.contactClient != null && this.$store.state.contactClient.connected) {
              clearInterval(this.checkInterval)
              this.checkInterval = null
              console.log('重连成功')
            } else if (this.$store.state.contactClient != null && this.$store.state.contactSocket.readyState !== SockJS.CONNECTING) {
              //经常会遇到websocket的状态为open 但是stompClient的状态却是未连接状态，故此处需要把连接断掉 然后重连
              this.$store.state.contactClient.disconnect(() => {
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
      this.$store.state.contactSocket = new SockJS(this.socketUrl)
      this.$store.state.contactClient = Stomp.over(this.$store.state.contactSocket);
      this.$store.state.contactClient.debug = null //关闭控制台打印
      this.$store.state.contactClient.heartbeat.outgoing = 20000;
      this.$store.state.contactClient.heartbeat.incoming = 0; //客户端不从服务端接收心跳包
      // 向服务器发起websocket连接
      this.$store.state.contactClient.connect({ name: this.$store.state.myNick }, //此处注意更换自己的用户名，最好以参数形式带入
        frame => { // eslint-disable-line no-unused-vars
          console.log('链接成功！')
          console.log(this.$store.state.contactClient)
          // TODO url合并？
          contactUrl({
            userId: this.$store.state.userId
          }).then(res => {
            this.$store.state.contactClient.subscribe(res.url, payload => {
              let json = JSON.parse(payload.body)
              console.log("收到的json:")
              console.log(json)
              console.log("通讯录服务收到消息")
              this.messages.push(json)
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
    },
    chatSocketInit () {
      console.log("初始化聊天列表socket")
      if (this.$store.state.chatClient == null || !this.$store.state.chatClient.connected) {
        this.socketUrl = this.$store.state.DEBUG ? 'http://localhost:8080/websocket' : 'http://182.92.163.68:8080/websocket'
        if (this.$store.state.chatClient != null && this.$store.state.chatSocket.readyState === SockJS.OPEN) {
          this.$store.state.chatClient.disconnect(() => {
            this.chatSocketConnect()
          })
        } else if (this.$store.state.chatClient != null && this.$store.state.chatSocket.readyState === SockJS.CONNECTING) {
          console.log("连接正在建立")
          return;
        } else {
          console.log("第一次建立")
          this.chatSocketConnect()
        }
        if (!this.checkInterval) {
          this.checkInterval = setInterval(() => {
            console.log("检测连接：" + this.$store.state.chatSocket.readyState)
            if (this.$store.state.chatClient != null && this.$store.state.chatClient.connected) {
              clearInterval(this.checkInterval)
              this.checkInterval = null
              console.log('重连成功')
            } else if (this.$store.state.chatClient != null && this.$store.state.chatSocket.readyState !== SockJS.CONNECTING) {
              //经常会遇到websocket的状态为open 但是stompClient的状态却是未连接状态，故此处需要把连接断掉 然后重连
              this.$store.state.chatClient.disconnect(() => {
                this.chatSocketConnect()
              })
            }
          }, 2000)
        }
      } else {
        console.log("连接已建立成功，不再执行")
      }
    },

    chatSocketConnect () {
      this.$store.state.chatSocket = new SockJS(this.socketUrl)
      this.$store.state.chatClient = Stomp.over(this.$store.state.chatSocket);
      this.$store.state.chatClient.debug = null //关闭控制台打印
      this.$store.state.chatClient.heartbeat.outgoing = 20000;
      this.$store.state.chatClient.heartbeat.incoming = 0; //客户端不从服务端接收心跳包
      // 向服务器发起websocket连接
      this.$store.state.chatClient.connect({ name: this.$store.state.myNick }, //此处注意更换自己的用户名，最好以参数形式带入
        frame => { // eslint-disable-line no-unused-vars
          console.log('链接成功！')
          console.log(this.$store.state.chatClient)
          chatUrl({
            userId: this.$store.state.userId
          }).then(res => {
            this.$store.state.chatClient.subscribe(res.url, payload => {
              let json = JSON.parse(payload.body)
              console.log("收到的json:")
              console.log(json)
              console.log("即时通讯服务收到消息")
              this.receiveMessage(json)
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
    },
  },

  created () {
    this.socketInit()
    this.chatSocketInit()
    this.$store.state.currentChannelIdx = -1
    setTimeout(
      () => {
        getMessagesList({
          srcId: this.$store.state.userId,
        }).then(res => {
          console.log("收到联系人列表")
          console.log(this.messages)
          for (let d in res) {
            this.messages.push(res[d])
          }
          console.log(this.messages)
        })
      }, 100
    )
  },
  computed: {
    messages () {
      return this.messagesList
    }
  }
}
</script>

<style scoped>
</style>