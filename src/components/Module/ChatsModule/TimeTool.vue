<template>
  <div>
    <v-btn
      style="position: fixed; top: 0.5rem; right:15rem; font-weight: bold"
      v-if="start"
      @click="tryBegin"
      rounded
      color="blue-grey lighten-2"
      :disabled="disabled"
    >{{hint}}
    </v-btn>
    <v-btn
      style="position: fixed; top: 0.5rem; right:15rem; font-weight: bold"
      v-if="!start"
      @click="endTime"
      rounded
      color="green lighten-3"
      :disabled="disabled"
    >{{hint}}
    </v-btn>
    <v-chip
      color="pink"
      roun
      label
      text-color="white"
      style="position: fixed; top: 0.6rem; right:25rem; font-weight: bold"
      v-if="meeting"
    >
      <v-icon left>mdi-label</v-icon>
      {{state}}
    </v-chip>
    <NewNode
      v-if="dialog"
      :chatId="this.chatId"
      :type="this.type"
      @closeD="closeD"
      @tryOk="tryOk"
    ></NewNode>
  </div>
</template>


<script>
import NewNode from "./ChatTools/Msg/NewNode"
import { endTimeShaft } from "../../../api/timeShaft/index"

export default {

  props: ["chatId", "type"],

  components: {
    NewNode
  },
  data () {
    return {
      start: true,
      dialog: false,
      meeting: false,
      disabled: false,
      hint: "开启事件",
      state: "事件中"
    }
  },

  methods: {
    tryBegin () {
      this.dialog = true
    },

    closeD () {
      this.dialog = false
    },

    tryOk (intervel) {
      if (!this.timer && intervel) {
        this.state = "正在加入事件"
        this.count = 1
        this.disabled = true
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= 1) {
            this.count--
            this.hint = this.count
          } else {
            this.disabled = false
            clearInterval(this.timer)
            this.timer = null
            this.hint = "结束事件"
            this.state = "事件中"
          }
        }, 1000)
      }
      else {
        this.hint = "结束事件"
        this.state = "事件中"
      }
      this.dialog = false
      this.start = false
      this.meeting = true
    },

    endOk (intervel) {
      if (!this.timer && intervel) {
        this.state = "正在退出事件"
        this.count = 1
        this.disabled = true
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= 1) {
            this.count--
            this.hint = this.count
          } else {
            this.disabled = false
            clearInterval(this.timer)
            this.timer = null
            this.hint = "开启事件"
            this.state = "事件中"
            this.meeting = false
          }
        }, 1000)
      }
      else {
        this.hint = "开启事件"
        this.state = "事件中"
        this.meeting = false
      }
      this.dialog = false
      this.start = true
    },

    //结束当前时间轴
    endTime () {
      //目前使用friend调试
      endTimeShaft({
        group_id: this.chatId,
        chatId: this.chatId,
        type: this.type === "group" ? "group" : "friend",
      }).then(res => {
        if (!res || (res && !res.error)) {
          //正常返回
          // this.endOk(true)
        }
        else {
          //错误信息展示
        }
      })

    },
  },


  created () {
  }
}

</script>