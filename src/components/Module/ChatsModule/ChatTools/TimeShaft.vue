<template>
  <v-card
    class="mx-auto"
    height="100%"
  >
    <TimeNode
      :id="90"
      v-if="this.detail"
      @closeT="closeT"
    ></TimeNode>
    <v-card
      dark
      flat
    >
      <!-- this.timeshaft_id -->

      <v-dialog
        v-model="dialog"
        persistent
        max-width="800px"
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
                  md="2"
                >
                  <v-text-field
                    label="添加至少一个标签"
                    counter=5
                    required
                    v-model="lable[0]"
                  ></v-text-field>
                </v-col>
                <v-col
                  cols="12"
                  sm="6"
                  md="2"
                >
                  <v-text-field
                    label="标签2"
                    counter=5
                    v-model="lable[1]"
                  ></v-text-field>
                </v-col>
                <v-col
                  cols="12"
                  sm="6"
                  md="2"
                >
                  <v-text-field
                    label="标签3"
                    counter=5
                    v-model="lable[2]"
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-textarea
                    label="事件描述"
                    dense
                    auto-grow
                    required
                    v-model="description"
                  ></v-textarea>
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
      <!-- <div>
        <v-snackbar
          v-model="snackbar"
          light
          :timeout="0"
          top
        >
          已开启事件: <label class="red--text">{{title}}</label>
          <v-btn
            color="blue"
            text
            @click="snackbar = false; overTimeShaft()"
          >
            结束
          </v-btn>
        </v-snackbar>
      </div> -->

      <v-img
        src="https://cdn.vuetifyjs.com/images/cards/forest.jpg"
        gradient="to top, rgba(0,0,0,.44), rgba(0,0,0,.44)"
        height="150px"
      >
        <v-container class="fill-height">
          <v-row align="center">
            <v-row justify="end">
              <h1 style="
                      color: white;
                      text-transform: uppercase;
                      position: absolute;
                      top: 65%;
                      left: 20%;
                      transform: translateY(-50%);
                      margin: 0;
                      padding: 0;
                      font-size: 30px;
                    ">
                事件·Time Shaft
              </h1>
              <div
                class="text-uppercase font-weight-light"
                style="font-size: 15px; margin-top: 0"
              >
                {{$data.time}}
              </div>
            </v-row>
          </v-row>
        </v-container>
      </v-img>
    </v-card>
    <h1
      style="
      color: #4d4d4d;
      text-transform: uppercase;
      position: absolute;
      top: 60%;
      left: 15%;
      transform: translateY(-50%);
      margin: 0;
      padding: 0;
      font-size: 20px;
      text-align: center;
      /*text-shadow: 0px 5px 20px rgba(0, 0, 0, 1);*/
      mix-blend-mode: overlay;
    "
      v-if="items.length === 0"
    >
      您还没有和该好友添加事件呢~
    </h1>
    <v-card
      class="overflow-x-hidden overflow-y-auto"
      height="77%"
      v-if="items.length !== 0"
    >

      <v-timeline
        align-top
        dense
      >
        <v-timeline-item
          small
          v-for="(item, i) in items"
          :key="i"
          :color="timecolor[i % 10]"
        >
          <v-row class="pt-1">
            <v-col
              cols="3"
              style="width: 40px"
            >
              <strong style="margin-left: 0; font-size: 5px; padding-left: 0">{{item.begin_date}} ~ {{item.end_date}}</strong>
            </v-col>
            <v-col style="a:hover{color: blue;}">
              <a><strong @click="showDetail(1)">事件主题：{{item.title}}</strong></a>
              <div class="text-caption">
                事件摘要：{{ item.conclude }}
              </div>
              <v-row>
                <v-avatar>
                  <v-img :src="item.img"></v-img>
                </v-avatar>
                <a style="color: #78909C; margin-top: 5%">事件发起者：{{item.host}}</a>
              </v-row>
            </v-col>
          </v-row>
        </v-timeline-item>

      </v-timeline>
    </v-card>
  </v-card>
</template>
<script>
// import { getTimeLine} from '../../../../api/timeShaft/index'
import { beginTimeShaftSingle, getTimeshaft } from "../../../../api/timeShaft";
import TimeNode from "./Msg/TimeNode"

export default {
  name: "TimeShaft",
  //聊天id 聊天类型
  props: ['chatId', 'type'],

  components: {
    TimeNode
  },

  data () {
    return {
      //好友或者群id
      id: -1,
      snackbar: false,
      timeout: -1,
      dialog: null,
      time: '',
      start_time: null,
      title: '',
      lable: ["", "", ""],
      description: '',
      timecolor: [
        'deep-orange', 'deep-orange lighten-5', 'deep-orange lighten-4', 'deep-orange lighten-3',
        'deep-orange lighten-2', 'deep-orange lighten-1', 'deep-orange darken-1', 'deep-orange darken-2',
        'deep-orange darken-3', 'deep-orange darken-4'
      ],
      timeshaft_id: -1,
      items: [],
      detail: false,
    }
  },
  mounted () {
    this.dateShow();

  },
  created () {
    this.dateFormat();
    this.getShaft()

  },
  beforeDestroy () {
    this.dataDestroy();
  },
  computed: {

  },
  methods: {
    getShaft () {
      let para = {
        group_id: this.chatId,
        type: this.type == "group" ? "group" : "friend",
      }
      getTimeshaft(para).then(res => {
        this.items = res.items
      })
    },

    // overTimeShaft () {
    //   endTimeShaft({
    //     group_id: this.chatId,
    //     type: this.type
    //   })
    //   // this.$router.go(0)
    // },

    setTimeline () {
      if (this.title === '' || this.lable === '' || this.time === '') {
        alert("请先补充完信息哦~");
      }
      else {
        const that = this
        beginTimeShaftSingle({
          group_id: this.chatId,
          creator_id: this.$store.state.userId,
          title: this.title,
          tags: [this.lable],
          conclude: this.description,
          type: this.type == "group" ? "group" : "friend",
        }).then(res => {
          that.timeshaft_id = res.timeshaft_id
          that.dialog = false
          that.$parent.isShowEnd(that.chatId, that.type, true)
          //设置聊天开启id
        })
      }

    },

    //展示详细信息
    showDetail (id) {
      console.log(id)
      this.detail = true
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

    closeT (flag) {
      this.detail = flag
    },
  },



}
</script>

<style scoped>
</style>