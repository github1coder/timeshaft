<template>
  <!-- <v-dialog
    persistent
    v-model="show"
    max-width="600px"
  >
    <div style="width: 50%; float: left">123</div>
    <div style="width: 50%; float: right">
      <History
        :message="message"
        v-if="show"
      ></History>
    </div>
  </v-dialog> -->
  <div>
    <v-dialog
      v-model="show"
      persistent
      fullscreen
    >
      <v-card style="width: 100%;height: 100%;">
        <div style="height: 100%; width: 50%; float: left; border-right: 1px skyblue solid;">
          <div style="width: 80%; height: 90%; margin: auto;">
            <v-card-title style="text-align: left; font-size: 40px">
              这是一个标题
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text style="text-align: left; font-size: 25px;">
              标签:
              <v-chip
                class="ma-2"
                color="pink"
                label
                text-color="white"
              >
                <v-icon left>mdi-label</v-icon>
                Tags
              </v-chip>
              <v-chip
                class="ma-2"
                color="pink"
                label
                text-color="white"
              >
                <v-icon left>mdi-label</v-icon>
                Tags
              </v-chip>
              <v-chip
                class="ma-2"
                color="pink"
                label
                text-color="white"
              >
                <v-icon left>mdi-label</v-icon>
                Tags
              </v-chip>
            </v-card-text>
            <v-divider></v-divider>
            <v-card-subtitle style="font-size: 25px; height 500px;">
              摘要
            </v-card-subtitle>
            <v-card-text style="text-indent:2em; text-align: left; font-size: 20px;">
              zhehsifiheofhqjoqej
            </v-card-text>
          </div>
          <div style="height: 5%;">
            <v-card-actions>
              <v-btn
                color="gray darken-1"
                text
                style="font-size: 20px; margin-left: 10%; width: 80%; background-color: pink; margin-top: auto;"
                @keyup.tab="close"
                @click="close"
              >
                关闭(tab)
              </v-btn>
              <v-spacer></v-spacer>
            </v-card-actions>
          </div>
        </div>
        <div
          class="msg-info"
          style="height: 100%; width: 50%; float: right"
        >
          <History
            :messages="messages"
            v-if="show"
          ></History>
        </div>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import History from "./History.vue"
// import { getSingleTimeshaft } from "@/api/timeShaft"
export default {
  components: { History },

  props: ["id"],

  created () {
    // getSingleTimeshaft({
    //   timeshaft_id: this.id
    // }).then(res => {
    //   console.log(res)
    //   this.data = res
    // })
  },

  data () {
    return {
      show: true,
      data: {},
      messages: [{
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      }, {
        "msg": "你好，我是yy",
        "msgFromName": "hoirqjhoq1",
        "time": "2022-05-11 11:44:34",
        "msgFromAvatar": "http://182.92.163.68:8080/photo/9.png"
      },],


    }
  },
  methods: {
    close () {
      this.show = false
      this.$emit("closeT", this.show)
    }
  }
}

</script>