<template>
  <div style="width: 100%; height: 100%">

    <v-card
      flat
      tile
      style="width: 100%; height: 100%;"
    >
      <v-card style="margin: auto; width: 100%; height: 100%; border: grey 5px dashed;">
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
  props: ["id"],
  data () {
    return {
      photo: "",
      name: "",
      nick: "",
      email: "",
    };
  },

  mounted () {
    this.initInfo()
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

    initInfo () {
      const that = this
      getInfoMsg({
        "info_id": this.id,
        "type": "friend"
      }).then(res => {
        if (!res || (res && !res.error)) {
          that.init(
            res.photo,
            res.name,
            res.nick,
            res.email)
        }
        console.log("获取好友信息成功:" + this.$parent.$parent.id)
      })
    },

  }
}
</script>