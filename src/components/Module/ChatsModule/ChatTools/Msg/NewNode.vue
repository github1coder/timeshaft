<template>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="800px"
  >
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
              <v-combobox
                label="添加至少一个标签"
                counter=5
                required
                v-model="lable[0]"
                :items="allTags"
              ></v-combobox>
            </v-col>
            <v-col
              cols="12"
              sm="6"
              md="2"
            >
              <v-combobox
                counter=5
                required
                v-model="lable[1]"
                :items="allTags"
              ></v-combobox>
            </v-col>
            <v-col
              cols="12"
              sm="6"
              md="2"
            >
              <v-combobox
                counter=5
                required
                v-model="lable[2]"
                :items="allTags"
              ></v-combobox>
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
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-btn
          color="gray darken-1"
          text
          style="
              font-size: 20px;
              margin-left: 10%;
"
          @click="close"
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
import { beginTimeShaftSingle, getTimeTags } from "../../../../../api/timeShaft";
// import { meetingChange } from "../../../../../api/timeShaft";

export default {

  props: ["chatId", "type"],

  data () {
    return {
      dialog: true,
      title: '',
      lable: ["", "", ""],
      description: '',
      timeshaft_id: "",
      allTags: "",
    }
  },
  mounted () {
    getTimeTags({
      chatId: this.chatId,
      type: this.type,
    }).then(res => {
      this.allTags = res
    })
  },
  methods: {
    setTimeline () {
      if (this.title === '' || this.lable === ["", "", ""] || this.time === '') {
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
          type: this.type === "group" ? "group" : "friend",
        }).then(res => {
          that.timeshaft_id = res.timeshaft_id
          that.dialog = false
          // this.$emit("tryOk", true)
          // meetingChange({
          //   type: this.type,
          //   chatId: this.chatId,
          //   isMeeting: true,
          // }).then(res => {
          //   console.log(res)
          // })
        })
      }

    },

    close () {
      this.dialog = false
      this.$emit("closeD")
    },
  }
}

</script>