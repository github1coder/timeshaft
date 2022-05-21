<template>
  <div>
    <v-btn
      style="position: fixed; top: 1rem; right:15rem"
      v-if="start"
      @click="tryBegin"
    >开始时间轴
    </v-btn>
    <v-btn
      style="position: fixed; top: 1rem; right:5rem"
      v-if="true"
      @click="endTime"
    >结束时间轴
    </v-btn>
    <v-chip
      color="pink"
      label
      text-color="white"
      style="position: fixed; top: 1rem; right:25rem"
      v-if="meeting"
    >
      <v-icon left>mdi-label</v-icon>
      会议中
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
    }
  },

  methods: {
    tryBegin () {
      this.dialog = true
    },

    closeD () {
      this.dialog = false
    },

    tryOk () {
      this.dialog = false
      this.start = false
      this.meeting = true
    },

    endOk () {
      this.start = true
      this.meeting = false
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
          this.endOk()
          //广播一条消息，告诉好友或者群成员会议结束了()
          //todo: 让他们接受到消息以后调用timetool中的endOk方法
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