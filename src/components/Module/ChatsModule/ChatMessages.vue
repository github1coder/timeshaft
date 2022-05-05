<template>
  <div class="chat-content">
    <div class="messages"  v-scroll.self="onScroll" >
      <div :class="draw ? 'message-container-open' : 'message-container-close'">
        <v-list  three-line dark>
          <template v-for="(message, i) in messages" class="chat-list">
            <v-list-item :key="i" class="chat-list-item">
              <v-list-item-avatar>
                <!--TODO-- 等待对接后改成图片>
                <v-image :src="message.avatar"></v-image>-->
                <v-icon v-text="message.msgFromAvatar"></v-icon>
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
    <div class="chat-form">
      <div :class="draw ? 'chat-form-open' : 'chat-form-close'">
        <v-text-field
            class="mx-5 my-3 chat-form-tf"
            label="这边输入消息捏~"
            solo
            flat
            clearable
            v-model="inputMsg"
            autocomplete="off"
            @keyup.enter="sendChat(chatUrl, inputMsg)"
        >
        </v-text-field>
      </div>
    </div>
  </div>
</template>

<script>
import {getHistoryMessage} from "@/api/message";

export default {
  name: "ChatMessages",
  props: ['draw'],
  data() {
    return {
      refreshed: true,
      chatUrl: "/app/personalMessage",
      inputMsg: "",
      cache: 0,
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
        srcId: this.$store.state.userId,
        dstId: this.$store.state.messageList[this.$store.state.currentChannelIdx].id,
      }
      console.log(this.$store.state.messageList[this.$store.state.currentChannelIdx].data)
      this.$store.state.messageList[this.$store.state.currentChannelIdx].data.push(msgForm)
      console.log(this.$store.state.messageList[this.$store.state.currentChannelIdx].data)
      this.clearMsg()
      this.$store.commit("WEBSOCKET_SEND", {
        url: url,
        data: msgForm,
      })
    },
    onScroll() {
      if (!this.refreshed && document.documentElement.scrollTop || document.querySelector('.messages').scrollTop === 0) {
        this.refreshed = true
      } else if (document.documentElement.scrollTop || document.querySelector('.messages').scrollTop !== 0) {
        this.refreshed = false
      }
    },
    getDate () {
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
    repair (i) {
      if (i >= 0 && i <= 9) {
        return "0" + i;
      } else {
        return i;
      }
    },
    clearMsg () {
      this.inputMsg = ""
    },
  },
  computed: {
    messages() {
      if (this.$store.state.messageList.length === 0) {
        return []
      } else {
        return this.$store.state.messageList[this.$store.state.currentChannelIdx].data
      }
    },
  },
  watch: {
    refreshed(newVal, oldVal) {
      if (newVal && !oldVal) {
        //TODO 补全
        console.log(this.$store.state.more)
        if (this.$store.state.more) {
          getHistoryMessage({
            srcId: this.$store.state.userId,
            dstId: this.$store.state.currentChannelId,
            index: this.$store.state.messageList[this.$store.state.currentChannelIdx].index,
          }).then(res => {
            console.log("000000000000000000")
            console.log(res)
            this.$store.state.more = res.more
            if (this.$store.state.currentChannelIdx !== -1) {
              this.$store.state.messageList[this.$store.state.currentChannelIdx].index = res.index
              console.log(this.$store.state.messageList[this.$store.state.currentChannelIdx].data)
              for (let i = res.data.length-1; i >= 0; i--) {
                this.$store.state.messageList[this.$store.state.currentChannelIdx].data.unshift(res.data[i])
              }
              console.log(this.$store.state.messageList[this.$store.state.currentChannelIdx].data)
            }
          })
        }
      }
    }
  },

}
</script>

<style scoped>

</style>