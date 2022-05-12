<template>
  <div class="chat-form">
    <div :class="draw ? 'chat-form-open' : 'chat-form-close'">
      <v-divider></v-divider>
      <div class="text-left">
          <v-btn
              v-for="icon in icons"
              :key="icon"
              icon
              left
              class="text-left mr-8"
          >
            <v-icon class="ml-12" color="white" left size="24px">
              {{ icon }}
            </v-icon>
          </v-btn>
      </div>
      <v-textarea
          class="mx-5  chat-form-tf"
          label="这边输入消息捏~"
          flat
          clearable
          solo
          v-model="inputMsg"
          autocomplete="off"
          @keyup.enter="sendChat(chatUrl, inputMsg)"
      >
      </v-textarea>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChatForm",
  props: ['draw'],
  data() {
    return {
      chatUrl: "/app/personalMessage",
      inputMsg: "",
      icons: [
        'mdi-home',
        'mdi-email',
        'mdi-calendar',
        'mdi-delete',
      ],
      items: [
        'default',
        'absolute',
        'fixed',
      ],
      padless: false,
      variant: 'default',
    }
  },
  methods: {
    sendChat (url, message) {
      let name = this.$store.state.myNick
      let avatar = this.$store.state.myIcon
      const msgForm = {
        msgFromName: name,
        msgFromAvatar: avatar,
        msg: message,
        time: this.getDate(),
        userId: this.$store.state.userId,
        chatId: this.$store.state.messageList[this.$store.state.currentChannelIdx].id,
      }
      console.log(this.$store.state.messageList[this.$store.state.currentChannelIdx].data)
      this.$store.state.messageList[this.$store.state.currentChannelIdx].data.push(msgForm)
      console.log(this.$store.state.messageList[this.$store.state.currentChannelIdx].data)
      this.clearMsg()
      this.$store.commit("WEBSOCKET_SEND", {
        url: url,
        data: msgForm,
      })
      this.$store.state.messageList[this.$store.state.currentChannelIdx].haveRead += 1
    },
  },
  computed: {
  }
}
</script>

<style scoped>

</style>