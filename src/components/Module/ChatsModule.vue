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
              <v-list-item-group
                color="primary"
                mandatory
              >
                <v-list-item
                  v-for="(item, i) in this.messages"
                  @click="selectChannel(item.id, i, item)"
                  :id="'message-'+item.id.toString()"
                  :key="i"
                >
                  <v-list-item-avatar v-if="item.isMeeting">
                    <v-badge
                      color="green"
                      content="开会中"
                    >
                      <v-img
                        v-if="item.type==='friend'"
                        max-height="70px"
                        max-width="50px"
                        :src="item.chatAvatar"
                      ></v-img>
                      <v-avatar v-else-if="item.type==='group'">
                        <span class="white--text text-h5">{{ item.chatName[0] }}</span>
                      </v-avatar>
                    </v-badge>
                  </v-list-item-avatar>
                  <v-list-item-avatar v-else>
                    <v-img
                      v-if="item.type==='friend'"
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
            v-if="tools[0].show"
            ref="timeShaft"
            :chatId="this.$store.state.currentChannelId"
            :type="this.$store.state.currentChatType"
          ></TimeShaft>
          <InfoPage
            v-else-if="tools[1].show"
            ref="infoPage"
            :id="this.$store.state.currentChannelId"
            :type="this.$store.state.currentChatType"
          ></InfoPage>
          <Search
            v-else-if="tools[2].show"
            ref="search"
            :chatId="this.$store.state.currentChannelId"
            :type="this.$store.state.currentChatType"
          ></Search>
        </div>
      </div>
    </div>
    <ChatTools
      :draw="toolsDrawer"
      :tools="tools"
      v-show="$store.state.currentChannelIdx !== -1"
      @callback="callback"
    ></ChatTools>
    <TimeTool
      ref="timeTool"
      :chatId="this.$store.state.currentChannelId"
      :type="this.$store.state.currentChatType"
      v-show="$store.state.currentChannelIdx !== -1"
    ></TimeTool>
  </div>
</template>

<script>
import ChatTools from "@/components/Module/ChatsModule/ChatTools";
import TimeTool from "@/components/Module/ChatsModule/TimeTool";
import ChatHeader from "@/components/Module/ChatsModule/ChatHeader";
import ChatMessages from "@/components/Module/ChatsModule/ChatMessages";
import TimeShaft from "@/components/Module/ChatsModule/ChatTools/TimeShaft";
import InfoPage from "@/components/Module/ChatsModule/ChatTools/InfoPage"
import Search from "@/components/Module/ChatsModule/ChatTools/Search"
import { getMessagesList, haveRead } from "@/api/message";
export default {
  name: "ChatsModule",
  components: { ChatMessages, ChatHeader, ChatTools, TimeShaft, InfoPage, Search, TimeTool },
  data () {
    return {
      toolsDrawer: false, // 用于控制工具栏打开与否
      tools: [{
        icon: 'mdi-timeline',
        text: '',
        show: false
      }, {
        icon: 'mdi-account-details',
        text: '',
        show: false
      }, {
        icon: 'mdi-cloud-search-outline',
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
      showEnd: false, //是否展示时间轴按钮
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
        if (this.$store.state.userId !== payload.userId && this.$store.state.currentChannelId !== payload.chatId) {
          this.messages[idx].number += 1
        }
        this.messages[idx].lastMessage = {
          msg: payload.msg,
          time: payload.time
        }
        if (this.$refs.chatMessage !== undefined && this.$store.state.currentChannelId === payload.chatId) {
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
      this.toolsDrawer = false
      console.log(id)
      console.log(item)
      if (idx !== this.$store.state.currentChannelIdx) {
        this.$store.commit("changeChannel", { id: id, idx: idx, type: item.type, time: item.lastMessage.time });
        // 等画面完全渲染
        setTimeout(() => {
          console.log(this.$refs)
          this.$refs.chatMessage.init()
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

        //切换会议状态
        if (this.messages[this.$store.state.currentChannelIdx].isMeeting == false
          || !this.messages[this.$store.state.currentChannelIdx].isMeeting) {
          this.messages[this.$store.state.currentChannelIdx].isMeeting = false
          console.log("会议状态：开始=>关闭")
          this.$refs.timeTool.endOk(false)
        }
        else {
          console.log("会议状态：关闭=>开始")
          this.$refs.timeTool.tryOk(false)
        }
      }
    },


    isShowEnd (state) {
      console.log(state)
      this.showEnd = state
    },
  },

  created () {
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