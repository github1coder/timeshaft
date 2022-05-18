<template>
  <div class="chat-content">
    <div class="messages" id="scroll-target">
      <div :class="draw ? 'message-container-open' : 'message-container-close'">
        <v-list three-line dark v-scroll:#scroll-target="onScroll">
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
                <v-list-item-subtitle
                >{{ message.msg }}
                </v-list-item-subtitle>
              </v-list-item-content>
<!--              <v-list-item-content-->
<!--                  v-else-if="message.userId !== $store.state.userId" class="touser"-->
<!--              >-->
<!--                <v-list-item-title>{{ message.msgFromName }}</v-list-item-title>-->
<!--                <v-list-item-content-->
<!--                    class="tobubble"-->
<!--                >{{ message.msg }}-->
<!--                </v-list-item-content>-->
<!--              </v-list-item-content>-->
            </v-list-item>
          </template>
        </v-list>
      </div>
    </div>
    <ChatForm :draw="draw" @send="socketSend"></ChatForm>
  </div>
</template>

<script>
import {getHistoryMessage} from "@/api/message";
import ChatForm from "@/components/Module/ChatsModule/ChatForm";

export default {
  name: "ChatMessages",
  components: {ChatForm},
  props: ['draw'],
  data() {
    return {
      refreshed: true,
      cache: 0,
      messages: []
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
      this.$nextTick(() => {
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
      this.$emit("send", payload.data)
    },

    clearMessages() {
      this.messages = []
    },
    init() {
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
              this.messages.unshift(res.data[i])
            }
            this.$store.state.currentChatHaveRead += res.data.length
            console.log(this.messages)
          }
        })
      }, 100)
    }
  },
  computed: {},
  watch: {
    refreshed(newVal, oldVal) {
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
                this.messages.unshift(res.data[i])
              }
              this.$store.state.currentChatHaveRead += res.data.length
              console.log(this.messages)
            }
          })
        }
      }
    },
  },

  created() {

  }
}
</script>

<style lang="scss" scoped>

</style>