<template>
  <div style="height: 100%; width: 100%">
    <TimeNode
      :id="timeNodeId"
      :isManager="isManager()"
      :allTags="allTags.slice(1)"
      :stared="false"
      :self="self"
      v-if="this.detail"
      @closeT="closeT"
    ></TimeNode>
    <v-row
      dense
      style="width: 80%; height: 80px; margin: auto;"
    >
      <v-combobox
        :id="type"
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
    <v-divider></v-divider>
    <h1
      style="font-size: 20px; margin-top: 50px;"
      v-if="items.length === 0"
    >
      还没有添加事件哦
    </h1>
    <v-card
      class="overflow-x-hidden overflow-y-auto"
      v-if="items.length !== 0"
      :max-height="maxHeight"
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
            <a><strong @click="showDetail(item.id)">事件主题：{{item.title}}</strong></a>
            <div style="color: #78909C;">
              事件发起者：{{item.host}}
            </div>
            <div class="text-caption">
              事件标签：
              <v-label v-if="item.tags[0] != ''">{{ item.tags[0] }}</v-label>
              <v-label v-if="item.tags[1] && item.tags[1] != ''">、{{ item.tags[1] }}</v-label>
              <v-label v-if="item.tags[2] && item.tags[2] != ''">、{{ item.tags[2] }}</v-label>
            </div>
          </div>
          <!-- </v-row> -->
        </v-timeline-item>

      </v-timeline>
    </v-card>
  </div>
</template>
<script>
import { beginTimeShaftSingle, getTimeshaft, getTimeTags, searchTimeByTag } from "../../../../../api/timeShaft";
import { getGroupMember } from "../../../../../api/addresslist";
import TimeNode from "./TimeNode"

export default {
  name: "TimeShaft",
  //聊天id 聊天类型
  props: ['chatId', 'type', 'maxHeight', 'self'],

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
      tag: "所有时间轴",
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
    //由starModule调用
    flashTags (chatId, type) {
      getTimeTags({
        chatId: chatId,
        type: type,
      }).then(res => {
        this.allTags = res
      })
    },

    updateTags () {
      getTimeTags({
        chatId: this.chatId,
        type: this.type,
      }).then(res => {
        this.allTags = res
      })
    },

    search () {
      //如果在收藏夹页面
      if (this.$parent.$refs.allBody) {
        this.$parent.flash().
          this.updateTags()
      }

      const input = document.getElementById(this.type).value

      if (input == "") {
        return
      }
      else if (input == "所有时间轴") {
        this.getShaft()
      }
      else {
        searchTimeByTag({
          chatId: this.chatId,
          type: this.type,
          tag: input
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
        type: this.type,
      }
      getTimeshaft(para).then(res => {
        this.items = res.items
      })
    },

    setTimeline () {
      if (this.title === '' || this.lable === '' || this.time === '') {
        alert("请先补充完信息哦~");
      }
      else if (this.lable[0].length == 0 || this.lable[0].length > 5
        || (this.lable[1] && this.lable[1].length > 5)
        || (this.lable[2] && this.lable[2].length > 5)) {
        return
      }
      else {
        const that = this
        beginTimeShaftSingle({
          group_id: this.chatId,
          creator_id: this.$store.state.userId,
          title: this.title,
          tags: this.lable,
          conclude: this.description,
          type: this.type,
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

    closeT (flag, flashT, starId) {
      this.detail = flag
      if (starId != -1) {
        const index = this.items.findIndex(item => {
          return item.id == starId
        })
        this.items[index].star = true
        if (this.$parent.$refs.selfBody) {
          this.$parent.$refs.selfBody.updateTags()
          this.$parent.$refs.selfBody.getShaft()
        }
      }
      if (flashT) {
        this.updateTags()
        this.getShaft()
      }
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