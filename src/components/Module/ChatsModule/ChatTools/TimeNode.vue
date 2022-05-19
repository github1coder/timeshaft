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
  <v-dialog
    v-model="show"
    persistent
    max-width="600px"
  >
    <v-card class="overflow-y-auto">
      <div style="width: 50%; float: left">123</div>
      <div style="width: 50%; float: right">
        <History
          :message="message"
          v-if="show"
        ></History>
      </div>
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
        >
          添加
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import History from "./Msg/History.vue"
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
      message: [{
        "msg": "1",
        "msgFromName": "yy",
        "msgFromAvatar": "1",
        "time": "1",
      }],


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