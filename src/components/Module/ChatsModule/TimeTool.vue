<template>
  <div>
    <v-btn
      style="position: fixed; top: 1rem; right:15rem"
      v-if="start"
      @click="tryBegin"
      :disabled="disabled"
    >{{hint}}
    </v-btn>
    <v-btn
      style="position: fixed; top: 1rem; right:15rem"
      v-if="!start"
      @click="endTime"
      :disabled="disabled"
    >{{hint}}
    </v-btn>
    <v-chip
      color="pink"
      label
      text-color="white"
      style="position: fixed; top: 1rem; right:25rem"
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
      hint: "开启时间轴",
      state: "会议中"
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
        this.state = "正在开始会议"
        this.count = 2
        this.disabled = true
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= 2) {
            this.count--
            this.hint = this.count
          } else {
            this.disabled = false
            clearInterval(this.timer)
            this.timer = null
            this.hint = "结束时间轴"
            this.state = "会议中"
          }
        }, 1000)
      }
      this.dialog = false
      this.start = false
      this.meeting = true
    },

    endOk (intervel) {
      if (!this.timer && intervel) {
        this.state = "正在结束会议"
        this.count = 2
        this.disabled = true
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= 2) {
            this.count--
            this.hint = this.count
          } else {
            this.disabled = false
            clearInterval(this.timer)
            this.timer = null
            this.hint = "开始时间轴"
            this.state = "会议中"
            this.meeting = false
          }
        }, 1000)
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
          this.endOk(true)
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