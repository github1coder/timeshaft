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
    sendChat(url, message) {
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
    getDate() {
      const date = new Date();//当前时间
      const year = date.getFullYear(); //返回指定日期的年份
      const month = this.repair(date.getMonth() + 1);//月
      const day = this.repair(date.getDate());//日
      const hour = this.repair(date.getHours());//时
      const minute = this.repair(date.getMinutes());//分
      const second = this.repair(date.getSeconds());//秒
      //当前时间
      return year + "-" + month + "-" + day
          + " " + hour + ":" + minute + ":" + second
    },
    repair(i) {
      if (i >= 0 && i <= 9) {
        return "0" + i;
      } else {
        return i;
      }
    },
    clearMsg () {
      this.inputMsg = ""
    },
  }
}
</script>

<style scoped>

</style>