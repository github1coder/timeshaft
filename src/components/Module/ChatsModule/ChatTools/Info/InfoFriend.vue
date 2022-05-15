<template>
  <div style="width: 100%; height: 100%">
    <v-card
      dark
      flat
      tile
      style="width: 100%; height: 100%;"
    >
      <v-card style="margin: auto; width: 100%; height: 100%; border: grey 5px dashed;">
        <div style="width: 100%; height: 100%;">
          <v-img
            style="margin: auto; border-radius: 50%; width: 40%"
            :src="res.photo"
          ></v-img>
          <v-divider>
          </v-divider>

          <v-list-item>
            <v-list-item-content style="font-size: 25px;">
              <v-list-item-title style="font-size: 20px; line-height: 40px;">{{res.name}}</v-list-item-title>
              <v-list-item-title
                style="font-size: 20px; line-height: 40px;"
                v-show="res.nick != res.name"
              >备注：{{res.nick}}</v-list-item-title>
              <v-list-item-title style="font-size: 20px; line-height: 40px;">邮箱：{{res.email}}</v-list-item-title>
              <!-- <v-list-item-title>邮箱：{{this.$store.getters.infoEmail}}</v-list-item-title> -->
            </v-list-item-content>
          </v-list-item>
          <v-divider>
          </v-divider>
          <!-- <v-btn
            style="margin-top: 50px"
            color="success"
            width="30%"
            @click="sendMessage"
          >发消息</v-btn> -->
        </div>

        <!-- <div style="width: 50%; height: 100%">
          </div> -->
      </v-card>
    </v-card>
  </div>
</template>
<script>

import { getInfoMsg } from "../../../../../api/addresslist/index"
export default {
  data () {
    return {
      res: [],
    };
  },

  mounted () {
    getInfoMsg({
      "info_id": this.$store.state.currentChannelId,
      "type": "friend"
    }).then(res => {
      console.log("获取好友信息成功:" + this.$store.state.currentChannelId)
      this.res = res
    })
  },

  methods: {
    method1 () {

    },

    // sendMessage () {
    //   console.log("对好友进行消息发送")
    //   //todo, friend_id 为 this.$store.getters.infoId
    //   // changeSider
    //   setTimeout(function () {
    //     console.log(document.getElementById('nav-'+0))
    //     document.getElementById('nav-'+0).click()
    //     console.log("切换到聊天栏")
    //   }, 500)
    //   // 模拟点击事件
    //   const id = this.$store.state.infoId.toString()
    //   setTimeout(function () {
    //     console.log("得到好友关系id: "+ id)
    //     console.log(document.getElementById('message-'+id))
    //     document.getElementById('message-'+id).click()
    //     console.log("切换到聊天框")
    //   }, 500)

    // },
  }
}
</script>