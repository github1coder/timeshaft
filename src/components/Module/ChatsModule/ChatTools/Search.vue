<template>
  <v-card
    dark
    style="height: 100%; width: 100%"
  >
    <v-row
      dense
      dark
      style="width: 100%; height: 64px; margin: auto;"
    >
      <v-text-field
        clearable
        outlined
        dense
        dark
        hide-details
        v-model="text"
        class="input-search mt-3"
        autocomplete="off"
        style="width: 70%; margin: auto;"
      ></v-text-field>
      <v-btn
        style="width: 15%; height: 64%; margin: 12px 0px auto;"
        @click="search"
      >
        <!-- <v-icon style="width: 100%; height: 100%;">
          mdi-magnify
        </v-icon> -->
        搜索
      </v-btn>
    </v-row>
    <h1
      v-text="feedback"
      v-if="!show"
    ></h1>
    <div style="height: 100%; width: 100%;">
      <History
        v-if="this.show"
        :messages="this.messages"
      >
      </History>
    </div>

  </v-card>
</template>

<script>
import History from './Msg/History.vue'
import { searchHistory } from "../../../../api/message/index"

export default {
  props: ["type", "chatId"],
  components: {
    History
  },

  data () {
    return {
      text: "",
      show: false,
      messages: "",
      feedback: "",
    }
  },

  methods: {
    search () {
      if (this.text == "") {
        return
      }
      const that = this
      that.show = false
      searchHistory({
        "chatId": this.chatId,
        "type": this.type,
        "text": this.text,
      }).then(res => {
        console.log("获得查询结果")
        if (!res[0]) {
          that.feedback = "无相关记录"
          that.show = false
        }
        else {
          that.feedback = ""
          that.messages = res
          that.show = true
        }

      })
    },
  },

  created () {
    console.log(this.type)
    console.log(this.id)
  }
}

</script>