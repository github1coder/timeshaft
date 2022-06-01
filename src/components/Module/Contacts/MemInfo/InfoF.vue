<template>
  <div style="width: 100%; height: 100%">
    <v-card
      flat
      tile
      style="width: 100%; height: 100%;"
    >
      <v-card style="margin: auto; width: 60%; height: 100%; border: grey 5px dashed;">
        <div style="width: 100%; height: 100%;">
          <v-img
            style="margin: auto; border-radius: 50%; width: 40%"
            :src="photo"
          ></v-img>
          <v-divider>
          </v-divider>

          <v-list-item>
            <v-list-item-content style="font-size: 25px;">
              <v-list-item-title style="font-size: 20px; line-height: 40px;">{{name}}</v-list-item-title>
              <v-list-item-title
                style="font-size: 20px; line-height: 40px;"
                v-show="nick != ''"
              >备注：{{nick}}</v-list-item-title>
              <v-list-item-title style="font-size: 20px; line-height: 40px;">邮箱：{{email}}</v-list-item-title>
              <!-- <v-list-item-title>邮箱：{{this.$store.getters.infoEmail}}</v-list-item-title> -->
            </v-list-item-content>
          </v-list-item>
          <v-divider>
          </v-divider>
          <v-btn
            style="margin-top: 50px"
            color="success"
            width="30%"
            @click="sendMessage"
          >发消息</v-btn>
        </div>

        <!-- <div style="width: 50%; height: 100%">
          </div> -->
      </v-card>
    </v-card>
  </div>
</template>
<script>
export default {
  data () {
    return {
      photo: "",
      name: "",
      nick: "",
      email: "",
    };
  },

  mounted () {
  },

  methods: {
    method1 () {

    },

    init (photo, name, nick, email) {
      this.photo = photo
      this.name = name
      this.nick = nick
      this.email = email
    },

    sendMessage () {
      console.log("对好友进行消息发送")
      setTimeout(function () {
        console.log(document.getElementById('nav-' + 0))
        document.getElementById('nav-' + 0).click()
        console.log("切换到聊天栏")
        // 模拟点击事件
        //todo 尚未模拟聊天频道点击或聊天频道点击失效
        const id = this.$parent.$parent.id.toString()
        setTimeout(function () {
          console.log("得到好友关系id: " + id)
          console.log(document.getElementById('message-' + id))
          document.getElementById('message-' + id).click()
          console.log("切换到聊天框")
        }, 1000)
      }, 1000)



    },
  }
}
</script>