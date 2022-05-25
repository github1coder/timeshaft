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
            <v-card-title style=" font-size: 40px; font-weight: bold; ">
              主题：{{data.title}}
            </v-card-title>
            <v-divider></v-divider>
            <small style="align: left; text-align: left; font-size: 10px">{{data.name}}创建于{{data.startTime}}~{{data.endTime}}</small>
            <v-divider></v-divider>
            <small style="text-align: left;">时间轴链接：{{data.key}}</small>
            <v-divider></v-divider>
            <v-card-text style="text-align: left; font-size: 20px; font-weight: bold">
              标签:
              <v-chip
                class="ma-2"
                color="pink"
                label
                text-color="white"
                v-if="data.tags && data.tags[0]"
              >
                <v-icon left>mdi-label</v-icon>
                {{data.tags[0]}}
              </v-chip>
              <v-chip
                class="ma-2"
                color="pink"
                label
                text-color="white"
                v-if="data.tags && data.tags[1]"
              >
                <v-icon left>mdi-label</v-icon>
                {{data.tags[1]}}
              </v-chip>
              <v-chip
                class="ma-2"
                color="pink"
                label
                text-color="white"
                v-if="data.tags && data.tags[2]"
              >
                <v-icon left>mdi-label</v-icon>
                {{data.tags[2]}}
              </v-chip>
            </v-card-text>
            <v-divider></v-divider>
            <v-card-subtitle style="font-size: 25px;">
              摘要
            </v-card-subtitle>
            <v-card-text style="text-indent:2em; text-align: left; font-size: 20px;">
              {{data.conclude}}
            </v-card-text>
          </div>
          <div style="height: 5%;">
            <v-btn
              color="gray darken-1"
              text
              style="font-size: 20px; margin-left: 10%; width: 80%; background-color: pink; margin-top: auto; font-weight: bold"
              @keyup.tab="close"
              @click="close"
            >
              关闭(tab)
            </v-btn>
            <!-- <v-btn
              color="gray darken-1"
              text
              style="font-size: 20px; margin-left: 10%; width: 35%; background-color: pink; margin-top: auto;"
              @click="copyClicked"
            >
              复制分享链接
            </v-btn> -->
            <v-spacer></v-spacer>
          </div>
        </div>
        <div
          class="msg-info"
          style="height: 100%; width: 50%; float: right"
        >
          <History
            :messages="data.message"
            v-if="show"
          ></History>
        </div>
      </v-card>

    </v-dialog>
  </div>
</template>

<script>
import History from "./History.vue"
import { getSingleTimeshaft } from "@/api/timeShaft"

export default {
  components: { History },

  props: ["id"],

  mounted () {
    getSingleTimeshaft({
      timeshaft_id: this.id
    }).then(res => {
      console.log(res)
      this.data = res
    })
  },

  data () {
    return {
      show: true,
      data: {},
      messages: [],
    }
  },
  methods: {
    close () {
      this.show = false
      this.$emit("closeT", this.show)
    },

    copyClicked () {
      var input = document.createElement("input");   // js创建一个input输入框
      console.log(input)
      input.value = this.data.key;  // 将需要复制的文本赋值到创建的input输入框中
      document.body.appendChild(input);    // 将输入框暂时创建到实例里面
      input.select();   // 选中输入框中的内容
      console.log(input.value)
      document.execCommand("Copy") // 执行复制操作
      document.body.removeChild(input); // 最后删除实例中临时创建的input输入框，完成复制操作
    },
  }
}

</script>