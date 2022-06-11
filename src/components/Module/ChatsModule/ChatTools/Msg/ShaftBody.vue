<template>
  <v-card style="height: 100%; width: 100%">
    <TimeNode
      :id="timeNodeId"
      :isManager="isManager()"
      :allTags="allTags.slice(1)"
      v-if="this.detail"
      @closeT="closeT"
    ></TimeNode>
    <v-row
      dense
      style="width: 100%; height: 80px; margin: auto;"
    >
      <v-combobox
        v-model="tag"
        style="width: 70%; margin: auto;"
        requried
        :items="allTags"
      ></v-combobox>
      <v-btn
        style="width: 15%; height: 64%; margin: 12px 0px auto;"
        rounded
        color="brown lighten-5"
        @click="search"
      >
        <!-- <v-icon style="width: 100%; height: 100%;">
          mdi-magnify
        </v-icon> -->
        搜索
      </v-btn>
    </v-row>
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
      max-height="700px"
    >

      <v-timeline
        align-top
        dense
      >
        <v-timeline-item
          small
          v-for="(item, i) in items.slice().reverse()"
          :key="i"
          :color="timecolor[i % 10]"
        >
          <!-- <v-row class="pt-1"> -->
          <div style="font-size: 5px; text-align: left;">{{item.begin_date}} ~ {{item.end_date}}</div>
          <div style="a:hover{color: blue;}; text-align: left; width: 90%;">
            <div style="color: #78909C;">
              事件发起者：{{item.host}}
            </div>
            <a><strong @click="showDetail(item.id)">事件主题：{{item.title}}</strong></a>
            <div class="text-caption">
              事件摘要：{{ item.conclude }}
            </div>
          </div>
          <!-- </v-row> -->
        </v-timeline-item>

      </v-timeline>
    </v-card>
  </v-card>
</template>
<script>
import { beginTimeShaftSingle, getTimeshaft, getTimeTags, searchTimeByTag } from "../../../../../api/timeShaft";
import { getGroupMember } from "../../../../../api/addresslist";
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
      //用来到
      timeNodeId: -1,
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
      allTags: [],
      tag: "",
    }
  },
  mounted () {
    this.updateTags();
  },
  created () {
    this.getShaft()
  },
  computed: {
  },
  methods: {
    updateTags () {
      getTimeTags({
        chatId: this.chatId,
        type: this.type,
      }).then(res => {
        this.allTags = res
      })
    },

    search () {
      if (this.tag == "") {
        return
      }
      else if (this.tag == "所有时间轴") {
        this.getShaft()
      }
      else {
        searchTimeByTag({
          chatId: this.chatId,
          type: this.type,
          tag: this.tag
        }).then(res => {
          if (!res || (res && !res.error)) {
            //正常返回
            this.items = res.items
          }
          else {
            //错误信息展示
          }
        })
      }
    },

    getShaft () {
      let para = {
        group_id: this.chatId,
        type: this.type === "group" ? "group" : "friend",
      }
      getTimeshaft(para).then(res => {
        this.items = res.items
      })
    },

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
          tags: this.lable,
          conclude: this.description,
          type: this.type == "group" ? "group" : "friend",
        }).then(res => {
          that.timeshaft_id = res.timeshaft_id
          that.dialog = false
          that.$parent.isShowEnd(true)
          //设置聊天开启id
        })
      }

    },

    //展示详细信息
    showDetail (id) {
      console.log("时间轴结点id：" + id)
      this.timeNodeId = id
      this.detail = true
    },

    closeT (flag) {
      this.detail = flag
    },

    isManager () {
      const that = this
      if (this.$store.state.currentChatType == "group") {
        getGroupMember({
          "id": this.$store.state.currentChannelId,
        }).then(res => {
          if (!res || (res && !res.error)) {
            if (res.findIndex(mem => mem.id == that.$store.state.userId && mem.type != "normal") == -1) {
              return false
            }
            else {
              return true
            }
          }
        })
      }
      else {
        return true
      }
      return false
    },
  },

}


</script>

<style scoped>
</style>