<template>
  <div class="chat-form">
    <div :class="draw ? 'chat-form-open' : 'chat-form-close'">
      <v-divider></v-divider>
      <div class="text-left">
        <v-menu max-width="480" :nudge-top="300" v-model="emojiModel" :close-on-content-click="false"  transition="scale-transition">
          <v-tabs v-model="tab">
            <v-tab v-for="(emojiGroup, category) in emoji" :key="category">
              {{category}}
            </v-tab>
          </v-tabs>
          <v-tabs-items v-model="tab">
            <v-tab-item
              v-for="(emojiGroup, category) in emoji"
              :key="category">
              <v-container>
                <v-row>
                  <v-col
                      class="pa-0"
                      v-for="(emoji, emojiName) in emojiGroup"
                      :key="emojiName"
                  >
                    <span @click="insert(emoji)" :title="emojiName">{{emoji}}</span>
                  </v-col>
                </v-row>

              </v-container>
            </v-tab-item>
          </v-tabs-items>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
                icon
                left
                class="mr-8 ml-12"
                v-bind="attrs"
                v-on="on"
            >
              <v-icon color="white" size="24px">
                mdi-emoticon
              </v-icon>
            </v-btn>
          </template>

        </v-menu>

        <v-btn
            icon
            left
            class="mr-8 ml-12"
        >
          <v-icon color="white" size="24px">
            mdi-at
          </v-icon>
        </v-btn>
        <v-btn
            icon
            left
            class="mr-8 ml-12"
            @click="() => {
              this.$emit('selectStatusChange')
            }"
        >
          <v-icon color="white" size="24px">
            {{ $parent.selecting ? "mdi-close-circle-outline" : "mdi-timer-outline" }}
          </v-icon>
        </v-btn>
        <v-btn
            icon
            left
            class="mr-8 ml-12"
        >
          <v-icon color="white" size="24px">
            mdi-dots-horizontal
          </v-icon>
        </v-btn>
      </div>
      <textarea
          class="tarea"
          ref="tarea"
          v-model="inputMsg"
          @blur="getBlur"
          placeholder="按 Enter 发送"
          @keyup.enter="sendChat(inputMsg)"
      >
      </textarea>
    </div>
  </div>
</template>

<script>
import emoji from "../../../../public/emoji";

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
      emojiModel: false,
      emoji: emoji,
      tab: null,
      cursorPos: 0,
    }
  },
  methods: {
    insert (emoji) { // 添加表情
      let textDom = this.$refs.tarea
      var startPos = textDom.selectionStart
      var endPos = textDom.selectionEnd
      var scrollTop = textDom.scrollTop
      this.inputMsg = this.inputMsg.substring(0, startPos) + emoji + this.inputMsg.substring(endPos, this.inputMsg.length)
      setTimeout(() => {
        textDom.focus()
        textDom.selectionStart = this.cursorPos + 2 // 光标起始选择区域
        textDom.selectionEnd = this.cursorPos + 2 // 光标结尾选择区域
        textDom.scrollTop = scrollTop // 光标行高度
      }, 50)
      this.emojiModel = false
    },
    getBlur () { // 文本框失焦
      var element = this.$refs.tarea
      let cursorPos = 0
      console.log("1 " + cursorPos)
      console.log(document.selection)
      console.log(element)
      console.log(element.selectionStart)
      if (document.selection) {
        var selectRange = document.selection.createRange()
        selectRange.moveStart('character', -element.value.length)
        cursorPos = selectRange.text.length
      } else if (element.selectionStart || element.selectionStart === '0') {
        cursorPos = element.selectionStart
      }
      console.log("2 " + cursorPos)
      this.cursorPos = cursorPos
    },
    isSpace(message) {
      let flag = true
       for (let i in message) {
          if ((message.charAt(i) !== " " && message.charAt(i) !== "" && message.charAt(i) !== "\n")) {
            flag = false
            break
          }
       }
       return flag
    },
    sendChat(message) {
      if (this.isSpace(message)) {
        this.clearMsg()
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
  },
  created() {
    console.log("this is chat")
  }
}
</script>

<style scoped>
.tarea::-webkit-scrollbar {
  display: none;
}
textarea {
  height: 100%;
  width: 100%;
  border: none;
  outline: none;
  resize: none;
  color: #E0E0E0;
}
</style>