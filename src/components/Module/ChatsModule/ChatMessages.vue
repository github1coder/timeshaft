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
    <ChatForm :draw="draw"></ChatForm>
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

    }
  },
  computed: {
    messages() {
      if (this.$store.state.messageList.length === 0 || this.$store.state.currentChannelIdx === -1) {
        return []
      } else {
        this.scrollToBottom()
        return this.$store.state.messageList[this.$store.state.currentChannelIdx].data
      }
    },
  },
  watch: {
    refreshed(newVal, oldVal) {
      if (newVal && !oldVal) {
        //TODO 补全
        console.log("more: " + this.$store.state.more)
        if (this.$store.state.more) {
          getHistoryMessage({
            userId: this.$store.state.userId,
            type: this.$store.state.messageList[this.$store.state.currentChannelIdx].type,
            chatId: this.$store.state.currentChannelId,
            index: this.$store.state.messageList[this.$store.state.currentChannelIdx].index,
          }).then(res => {
            console.log("拉取 " + res.data.length + " 条历史消息")
            console.log(res)
            this.$store.state.more = res.more
            if (this.$store.state.currentChannelIdx !== -1) {
              this.$store.state.messageList[this.$store.state.currentChannelIdx].index = res.index
              console.log(this.$store.state.messageList[this.$store.state.currentChannelIdx].data)
              for (let i = res.data.length-1; i >= 0; i--) {
                this.$store.state.messageList[this.$store.state.currentChannelIdx].data.unshift(res.data[i])
              }
              this.$store.state.messageList[this.$store.state.currentChannelIdx].haveRead += res.data.length
              console.log(this.$store.state.messageList[this.$store.state.currentChannelIdx].data)
            }
          })
        }
      }
    },
  },

}
</script>

<style scoped>

</style>