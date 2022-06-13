<template>
  <div class="chat-content">
    <TimeNode
      :id="shaftId"
      :isManager="false"
      :allTags="[]"
      v-if="detail"
      @closeT="closeT"
    ></TimeNode>
    <v-dialog
      v-model="dialog"
      persistent
      max-width="800px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          absolute
          bottom
          color="blue-grey darken-4"
          right
          fab
          v-bind="attrs"
          v-on="on"
          v-if="snackbar === false"
        >
          <v-icon>mdi-plus</v-icon>
        </v-btn>
      </template>

      <v-card class="overflow-y-auto">
        <v-card-title>
          <span class="text-h5">添加事件</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-text-field
                  label="事件主题"
                  v-model="title"
                  required
                ></v-text-field>
              </v-col>
              <v-col
                cols="12"
                sm="6"
                md="2"
              >
                <v-text-field
                  label="添加至少一个标签"
                  counter=5
                  required
                  v-model="label[0]"
                ></v-text-field>
              </v-col>
              <v-col
                cols="12"
                sm="6"
                md="2"
              >
                <v-text-field
                  label="标签2"
                  counter=5
                  v-model="label[1]"
                ></v-text-field>
              </v-col>
              <v-col
                cols="12"
                sm="6"
                md="2"
              >
                <v-text-field
                  label="标签3"
                  counter=5
                  v-model="label[2]"
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-textarea
                  label="事件描述"
                  dense
                  auto-grow
                  required
                  v-model="description"
                ></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-btn
            color="gray darken-1"
            text
            style="
              font-size: 20px;
              margin-left: 10%;
"
            @click="dialog = false"
          >
            关闭
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn
            color="gray darken-1"
            text
            style="
              margin-right: 10%;
              font-size: 20px;"
            @click="commitTimeline"
          >
            添加
          </v-btn>

        </v-card-actions>
      </v-card>
    </v-dialog>
    <div
      class="messages"
      id="scroll-target"
    >
      <div :class="draw ? 'message-container-open' : 'message-container-close'">
        <v-list
          three-line
          v-scroll:#scroll-target="onScroll"
        >
          <span>{{selected}}</span>
          <template
            v-for="(message, i) in content"
            class="chat-list"
          >
            <v-list-item
              :key="i"
              class="chat-list-item"
            >
              <v-list-item-avatar
                v-if="selecting && messages.length > 0"
                class="mx-0"
              >
                <input type="checkbox" :id="'check-'+(messages.length-1-i)" @click="check(messages.length-1-i)" :value="message.msgId"/>
              </v-list-item-avatar>
              <v-list-item-avatar
                v-if="message.isMeeting"
                class="mx-3"
              >
                <v-btn
                  color="success"
                  fab
                  x-small
                >
                  <v-icon>mdi-domain</v-icon>
                </v-btn>
              </v-list-item-avatar>
              <v-list-item-avatar class="mx-3">
                <!--TODO-- 等待对接后改成图片>-->
                <v-img :src="message.msgFromAvatar"></v-img>
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title>{{ message.msgFromName }}</v-list-item-title>
                <v-list-item-subtitle>
                  {{message.time}}
                </v-list-item-subtitle>
                <v-list-item-content v-if="message.msgType === 'text'" >
                  <span style="word-wrap: break-word;max-width: 400px">{{message.msg}}</span>
                </v-list-item-content>
                <v-list-item-content
                  v-else-if="message.msgType === 'timeShaft'"
                  @click="queryTimeShaft(message.msg)"
                  style="color: #2196F3"
                >
                  <span style="word-wrap: break-word;max-width: 400px">{{message.msg}}</span>
                </v-list-item-content>
              </v-list-item-content>
            </v-list-item>
          </template>
        </v-list>
      </div>
    </div>
    <ChatForm
      :draw="draw"
      @selectStatusChange="selecting = !selecting"
      @send="socketSend"
    ></ChatForm>
  </div>
</template>

<script>
import { getHistoryMessage } from "@/api/message";
import ChatForm from "@/components/Module/ChatsModule/ChatForm";
import { genTimeShaftFromMessages, queryTimeShaftId } from "@/api/timeShaft";
import TimeNode from "@/components/Module/ChatsModule/ChatTools/Msg/TimeNode";
export default {
  name: "ChatMessages",
  components: { TimeNode, ChatForm },
  props: ['draw'],
  data () {
    return {
      refreshed: false,
      cache: 0,
      messages: [], //
      selected: [], // messageId that had been selected
      selecting: false,
      dialog: null,
      description: null,
      snackbar: null,
      title: null,
      label: [],
      detail: null,
      shaftId: null,
      success: 0,
    }
  },
  methods: {
    check(id) {
      var el = document.getElementById("check-"+id)
      console.log("得到check元素：")
      console.log(el)
      // console.log(el.checked)
      // if (el.checked === undefined) {
      //   el.checked = true
      // } else {
      //   el.checked = !el.checked
      // }
      console.log(el.checked)
      console.log("value:" + el.value)
      if (el.checked) {
        console.log("加入selected")
        this.selected.push(el.value)
      } else {
        console.log("从selected移除")
        var idx = this.selected.findIndex(value => value === el.value)
        if (idx > -1) {
          this.selected.splice(idx, 1)
        }
      }
    },
    closeT (flag) {
      this.detail = flag
    },
    queryTimeShaft (msg) {
      queryTimeShaftId({
        msg: msg
      }).then(res => {
        console.log(res)
        if (res.timeShaftId !== -1) {
          this.detail = true
          this.shaftId = res.timeShaftId
        } else {
          alert("非法的时间轴")
        }
      })
    },
    onScroll () {
      console.log("It's scrolling")
      if (!this.refreshed && document.documentElement.scrollTop || document.querySelector('.messages').scrollTop === 0) {
        this.refreshed = true
      } else if (document.documentElement.scrollTop || document.querySelector('.messages').scrollTop !== 0) {
        this.refreshed = false
      }
      console.log(this.refreshed)
    },

    scrollToBottom () {
      this.$nextTick(() => {
        const list = this.$el.querySelector(".messages");
        console.log("list is:")
        console.log(list)
        list.scrollTop = list.scrollHeight
      })

    },
    socketSend (payload) {
      // TODO 改成传参
      console.log(this.$store.state.serviceClient)
      console.log(payload)
      this.scrollToBottom()
      // this.messages.push(payload.data)
      this.$store.state.serviceClient.send(payload.url, {}, JSON.stringify(payload.data));
      console.log("send + " + JSON.stringify(payload.data) + " to " + payload.url)
      this.$emit("send", payload.data)
    },

    clearMessages () {
      this.messages = []
    },
    init () {
      this.clearMessages()
      setTimeout(() => {
        console.log(this.$store.state.currentChatTime)
        getHistoryMessage({
          lastTime: this.$store.state.currentChatTime,
          userId: this.$store.state.userId,
          type: this.$store.state.currentChatType,
          chatId: this.$store.state.currentChannelId,
          first: this.$store.state.currentChatFirst,
        }).then(res => {
          console.log("拉取 " + res.data.length + " 条历史消息")
          console.log(res)
          this.$store.state.currentChatFirst = 0
          this.$store.state.currentChatMore = res.more
          if (this.$store.state.currentChannelIdx !== -1) {
            this.$store.state.currentChatTime = res.lastTime
            console.log(this.messages)
            for (let i = res.data.length - 1; i >= 0; i--) {
              this.messages.push(res.data[i])
            }
            this.$store.state.currentChatHaveRead += res.data.length
            console.log(this.messages)
          }
          this.scrollToBottom()
        })

      }, 100)
    },
    commitTimeline () {
      genTimeShaftFromMessages({
        chatId: this.$store.state.currentChannelId,
        userId: this.$store.state.userId,
        title: this.title,
        tags: this.label,
        conclude: this.description,
        msgIds: this.selected,
        type: this.$store.state.currentChatType
      }).then(res => {
        res
      })
      this.dialog = false
    },
  },
  computed: {
    content() {
      var msg = this.messages
      return msg.reverse()
    }
  },
  watch: {
    refreshed (newVal, oldVal) {
      if (newVal && !oldVal) {
        //TODO 补全
        console.log("more: " + this.$store.state.currentChatMore)
        if (this.$store.state.currentChatMore) {
          getHistoryMessage({
            lastTime: this.$store.state.currentChatTime,
            userId: this.$store.state.userId,
            type: this.$store.state.currentChatType,
            chatId: this.$store.state.currentChannelId,
            first: this.$store.state.currentChatFirst,
          }).then(res => {
            console.log("拉取 " + res.data.length + " 条历史消息")
            console.log(res)
            this.$store.state.currentChatFirst = 0
            this.$store.state.currentChatMore = res.more
            if (this.$store.state.currentChannelIdx !== -1) {
              this.$store.state.currentChatTime = res.lastTime
              console.log(this.messages)
              for (let i = res.data.length - 1; i >= 0; i--) {
                this.messages.push(res.data[i])
              }
              this.$store.state.currentChatHaveRead += res.data.length
              console.log(this.messages)
            }
          })
        }
      }
    },
    selecting (newVal, oldVal) {
      if (!newVal && oldVal && this.selected.length !== 0) {
        console.log("填写timeShaft信息")
        this.dialog = true
        this.selected = []
      }

    },
    // messages (newVal, oldVal) {
    //   const len = oldVal.length - newVal.length
    //   if (len > 0) {
    //     for (let i in this.selected) {
    //       var idx = this.messages.findIndex(message => message.msgId === this.selected[i])
    //       var el = document.getElementById("check-" + (messages.length-1-idx))
    //
    //     }
    //   }
    //
    // }
  },

  created () {

  }
}
</script>

<style lang="scss" scoped>
</style>