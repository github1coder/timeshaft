<template>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="600px"
  >
    <template v-slot:activator="{ on, attrs }">
      <v-btn
        absolute
        bottom
        color="blue-grey darken-4"
        right
        fab
        v-bind="attrs"
        v-on="on"
        v-if="snackbar === false"
      >
        <v-icon>mdi-plus</v-icon>
      </v-btn>
    </template>

    <v-card class="overflow-y-auto">
      <v-card-title>
        <span class="text-h5">添加事件</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col
              cols="12"
              sm="6"
              md="4"
            >
              <v-text-field
                label="事件主题"
                v-model="title"
                required
              ></v-text-field>
            </v-col>
            <v-col
              cols="12"
              sm="6"
              md="4"
            >
              <v-text-field
                label="打上标签(5个字以内)"
                required
                v-model="lable"
              ></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field
                label="事件描述"
                required
                v-model="description"
              ></v-text-field>
            </v-col>

            <v-col>
              <h1
                class="h1"
                style="font-size: 20px;"
              >开始时间</h1>
              <v-time-picker
                v-model="start_time"
                format="24hr"
                header-color="#777777"
              ></v-time-picker>
            </v-col>
          </v-row>
        </v-container>
        <small>*滑动鼠标选择事件开始时间</small>
      </v-card-text>
      <v-card-actions>
        <v-btn
          color="gray darken-1"
          text
          style="
              font-size: 20px;
              margin-left: 10%;
"
          @click="dialog = false"
        >
          关闭
        </v-btn>
        <v-spacer></v-spacer>
        <v-btn
          color="gray darken-1"
          text
          style="
              margin-right: 10%;
              font-size: 20px;"
          @click="setTimeline();"
        >
          添加
        </v-btn>

      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { setTimeline } from '../../../../../api/timeShaft/index'
export default {
  name: "TimeShaft",
  data () {
    return {
      //显示时间
      snackbar: false,
      timeout: -1,
      dialog: null,
      time: '',
      start_time: null,
      title: '',
      lable: '',
      description: '',
      timecolor: [
        'deep-orange', 'deep-orange lighten-5', 'deep-orange lighten-4', 'deep-orange lighten-3',
        'deep-orange lighten-2', 'deep-orange lighten-1', 'deep-orange darken-1', 'deep-orange darken-2',
        'deep-orange darken-3', 'deep-orange darken-4'
      ],
      chatid: {
        'user1': -1,
        'user2': -1
      },
      timeshaft_id: -1,
      items: []
    }
  },
  mounted () {

  },
  created () {

  },
  beforeDestroy () {

  },
  computed: {

  },
  methods: {
    setTimeline () {
      if (this.title === '' || this.lable === '' || this.time === '') {
        alert("请先补充完信息哦~");
      }
      else {
        setTimeline({
          group_id: this.$store.state.currentChannelId,
          creator_id: this.$store.state.userId,
          title: this.title,
          tag: [this.lable],
          conclude: this.description,
          type: 'friend'
        }).then(res => {
          this.timeshaft_id = res.timeshaft_id
        })
        this.dialog = false
        this.snackbar = true
      }
    },
    dataDestroy () {
      if (this.timer) {
        clearInterval(this.timer); // 在Vue实例销毁前，清除我们的定时器
      }

    },
    dateShow () {
      this.timer = setInterval(() => {
        this.dateFormat()
      }, 1000)

    },
    dateFormat () {
      let date = new Date();
      let year = date.getFullYear();
      let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      this.time = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    },
  }
}
</script>
