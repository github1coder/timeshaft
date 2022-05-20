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
              {{data.title}}
            </v-card-title>
            <v-divider></v-divider>
            <small style="align: left; text-align: left; font-size: 10px">{{data.name}}创建于{{data.startTime}}~{{data.endTime}}</small>
            <v-divider></v-divider>
            <v-card-text style="text-align: left; font-size: 25px;">
              标签:
              <v-chip
                class="ma-2"
                color="pink"
                label
                text-color="white"
                v-if="data.tags[0]"
              >
                <v-icon left>mdi-label</v-icon>
                {{data.tags[0]}}
              </v-chip>
              <v-chip
                class="ma-2"
                color="pink"
                label
                text-color="white"
                v-if="data.tags[1]"
              >
                <v-icon left>mdi-label</v-icon>
                {{data.tags[1]}}
              </v-chip>
              <v-chip
                class="ma-2"
                color="pink"
                label
                text-color="white"
                v-if="data.tags[2]"
              >
                <v-icon left>mdi-label</v-icon>
                {{data.tags[2]}}
              </v-chip>
            </v-card-text>
            <v-divider></v-divider>
            <v-card-subtitle style="font-size: 25px; height 500px;">
              摘要
            </v-card-subtitle>
            <v-card-text style="text-indent:2em; text-align: left; font-size: 20px;">
              {{data.conclude}}
            </v-card-text>
          </div>
          <div style="height: 5%;">
            <v-card-actions>
              <v-btn
                color="gray darken-1"
                text
                style="font-size: 20px; margin-left: 10%; width: 35%; background-color: pink; margin-top: auto;"
                @click="copyClicked"
              >
                复制分享链接
              </v-btn>
              <v-btn
                color="gray darken-1"
                text
                style="font-size: 20px; margin-left: 10%; width: 35%; background-color: pink; margin-top: auto;"
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
            :messages="data.message"
            v-if="show"
          ></History>
        </div>
      </v-card>
      <small
        ref="share"
        v-show="false"
      >{{data.key}}</small>
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
      this.$refs.share.select()
      document.execCommand('copy')
    },
  }
}

</script>