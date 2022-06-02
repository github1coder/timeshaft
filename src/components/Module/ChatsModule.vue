<template>
  <div class="base-content">
    <div class="sidebar">
      <div class="sb-container">
        <v-card
          height="100%"
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
              max-height="100px"
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
                  <v-list-item-avatar>
                    <v-badge
                      avatar
                      dot
                      bottom
                      :value="item.isMeeting"
                      color="green"
                      overlap
                    >
                      <v-avatar
                        size="30"
                        color="blue"
                      >
                        <v-img
                          v-if="item.type==='friend'"
                          :src="item.chatAvatar"
                        ></v-img>
                        <span
                          v-else-if="item.type==='group'"
                          class="white--text text-h5"
                        >{{ item.chatName[0] }}</span>
                      </v-avatar>
                    </v-badge>
                  </v-list-item-avatar>
                  <v-list-item-content>
                    <v-list-item-title
                      class="channel-title"
                      v-text="item.chatName"
                    ></v-list-item-title>
                    <v-list-item-content style="text-align: left; font-size: 5px">
                      {{ item.number > 0 ? ("[未读 " + (item.number).toString() + " 条]") : "" }}
                      {{ item.lastMessage.msg }}
                    </v-list-item-content>
                  </v-list-item-content>
                  <v-list-item-content
                    style="text-align: right; font-size: 1px"
                    v-if="item.lastMessage !== null > 0"
                  >
                    {{ item.lastMessage.time }}
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
import { getGroupMember } from '@/api/addresslist/index'

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

        if (this.$store.state.currentChannelId !== payload.chatId && this.$store.state.currentChatType === payload.type) {
          this.messages[idx].number += 1
        }

        if (this.$store.state.currentChannelId === payload.chatId && this.$store.state.currentChatType === payload.type) {
          haveRead({
            time: payload.time,
            userId: payload.userId,
            chatId: payload.chatId,
            type: payload.type
          }).then(res => {
            res
            console.log("have read this msg")
          })
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
      this.tools[0].show = false
      this.tools[1].show = false
      this.tools[2].show = false
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
          haveRead({
            type: item.type,
            chatId: item.id,
            userId: this.$store.state.userId,
            time: item.time,
          }).then(res => {
            res
            console.log(this.$store.state.unreadNum)
            this.$store.state.unreadNum -= item.number
            console.log(this.$store.state.unreadNum)
            console.log("have read this msg")
            item.number = 0
          })

        }

        //切换会议状态
        const that = this
        if (this.$store.state.currentChatType == "group") {
          getGroupMember({
            "id": this.$store.state.currentChannelId,
          }).then(res => {
            if (!res || (res && !res.error)) {
              if (res.findIndex(mem => mem.id == that.$store.state.userId && mem.type != "normal") == -1) {
                that.$refs.timeTool.timetoolShow = false
              }
              else {
                that.$refs.timeTool.timetoolShow = true
              }
            }
          })
        }
        else {
          this.$refs.timeTool.timetoolShow = true
        }
        if (this.messages[this.$store.state.currentChannelIdx].isMeeting == false
          || !this.messages[this.$store.state.currentChannelIdx].isMeeting) {
          this.messages[this.$store.state.currentChannelIdx].isMeeting = false
          console.log("会议状态：开始=>关闭")
          this.$refs.timeTool.endOk(false)
          if (this.$refs.timeShaft) {
            this.$refs.timeShaft.getShaft()
          }
        } else {
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
          this.$store.state.unreadNum = 0
          for (let d in res) {
            this.messages.push(res[d])
            this.$store.state.unreadNum += res[d].number
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