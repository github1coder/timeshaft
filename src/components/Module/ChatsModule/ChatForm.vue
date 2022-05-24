<template>
  <div class="chat-form">
    <div :class="draw ? 'chat-form-open' : 'chat-form-close'">
      <v-divider></v-divider>
      <div class="text-left">
          <v-btn
              icon
              left
              class="text-left mr-8"
          >
            <v-icon class="ml-12" color="white" left size="24px">
              mdi-home
            </v-icon>
          </v-btn>
        <v-btn
            icon
            left
            class="text-left mr-8"
        >
          <v-icon class="ml-12" color="white" left size="24px">
            mdi-email
          </v-icon>
        </v-btn>
        <v-btn
            icon
            left
            class="text-left mr-8"
            @click="() => {
              this.$emit('selectStatusChange')
            }"
        >
          <v-icon class="ml-12" color="white" left size="24px">
            mdi-calendar
          </v-icon>
        </v-btn>
        <v-btn
            icon
            left
            class="text-left mr-8"
        >
          <v-icon class="ml-12" color="white" left size="24px">
            mdi-delete
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
          @keyup.enter="sendChat(inputMsg)"
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
      chatUrlPrivate: "/app/personalMessage",
      chatUrlGroup: "/app/groupMessage",
      inputMsg: "",
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
    isSpace(message) {
      let flag = true
       for (let i in message) {
          if (!message.charAt(i).isSpace()) {
            flag = false
            break
          }
       }
       return flag
    },
    sendChat(message) {
      if (this.isSpace(message)) {
        alert("")
      } else {
        let name = this.$store.state.myNick
        let avatar = this.$store.state.myIcon
        const msgForm = {
          msgFromName: name,
          msgFromAvatar: avatar,
          msg: message,
          time: this.getDate(),
          userId: this.$store.state.userId,
          chatId: this.$store.state.currentChannelId,
        }
        this.clearMsg()
        const url = this.$store.state.currentChatType === 'friend' ? this.chatUrlPrivate : this.chatUrlGroup;
        this.$emit("send", {
          url: url,
          data: msgForm,
        })
        this.$store.state.currentChatHaveRead += 1
      }
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