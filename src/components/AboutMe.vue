<template>
  <div class="sb-container">
    <v-card
      height="100%"
      dark
      flat
      tile
      class="server-info"
    >
      <v-card
        class="mx-auto"
        width="280px"
        tile
        dark
      >
        <v-navigation-drawer
          permanent
          width="100%"
          dark
        >
          <!-- <v-system-bar></v-system-bar> -->
          <v-list dark>
            <v-list-item>
              <v-list-item-icon style="margin: 0px auto 20px;">
                <v-img
                  style="border-radius: 50%; width: 150px;"
                  :src="photo"
                ></v-img>
              </v-list-item-icon>
            </v-list-item>
            <v-list-item>
              <v-btn
                dark
                width="50%"
                style="margin: auto;"
              >
                修改头像
              </v-btn>
            </v-list-item>

            <v-list-item>
              <v-list-item-content>
                <v-list-item-title v-text="name"></v-list-item-title>
                <v-list-item-subtitle v-text="email"></v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <v-divider></v-divider>
        </v-navigation-drawer>
        <v-list>
          <v-list-group
            prepend-icon="~"
            @click="initPassword"
          >
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title>修改密码</v-list-item-title>
              </v-list-item-content>
            </template>
            <v-list-item>
              <v-text-field
                label="旧密码"
                v-model="password"
              >
              </v-text-field>
            </v-list-item>
            <v-list-item>
              <v-text-field
                label="新密码"
                v-model="passwordN"
              >
              </v-text-field>
            </v-list-item>
            <v-list-item>
              <v-text-field
                label="验证码"
                v-model="checkCode"
              >
              </v-text-field>
            </v-list-item>
            <v-list-item>
              <div style="width: 100%;">
                <v-btn
                  class="mx-2"
                  color="blue"
                  width="40%"
                >
                  发送验证码
                </v-btn>
                <v-btn
                  class="mx-2"
                  color="blue"
                  width="40%"
                >
                  确定
                </v-btn>
              </div>
            </v-list-item>
          </v-list-group>
          <v-list-group prepend-icon="~">
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title>创建团队</v-list-item-title>
              </v-list-item-content>
            </template>
            <v-list-item>
              <v-row
                dense
                style="width: 100%; height: 64px; border: white 0px solid; margin: auto;"
              >
                <v-text-field
                  clearable
                  outlined
                  dense
                  dark
                  hide-details
                  label="团队名字"
                  v-model="textG"
                  class="input-search mt-3"
                  autocomplete="off"
                  style="width: 70%; margin: auto;"
                ></v-text-field>
                <v-btn
                  style="width: 15%; height: 64%; margin: 12px 0px auto;"
                  @click="newGroup"
                >
                  创建
                </v-btn>
              </v-row>
            </v-list-item>
          </v-list-group>
        </v-list>
      </v-card>
    </v-card>
  </div>
</template>
<script>
import { addGroup } from '../api/addresslist/index';
export default {
  data () {
    return {
      name: this.$store.getters.myNick,
      photo: this.$store.getters.myIcon,
      email: this.$store.getters.email,
      checkCode: "",
      password: "",
      passwordN: "",
      textG: "",
    };
  },

  mounted () {
  },

  methods: {
    method1 () {

    },

    initPassword () {
      this.checkCode = ""
      this.password = ""
      this.passwordN = ""
    },

    newGroup () {
      if (this.textG == "" || this.$store.getters.userId == -1) {
        return;
      }
      addGroup({
        "master_id": this.$store.getters.userId,
        "name": this.textG,
        "notice": "",
        "photo": "",
      }).then(res => {
        this.textG = ""
        console.log(res)
      })
      // getGroups({
      //   "user_id": this.$store.getters.userId,
      //   "ACCESS_TOKEN": null
      // }).then(res => {
      //   // this.$store.commit("channels", res)
      //   this.groups = res
      //   this.groups.forEach(function (item) {
      //     item["show"] = false;
      //     item["quit"] = false;
      //   });
      //   this.groups = JSON.parse(JSON.stringify(this.groups))
      //   this.allPageG = Math.ceil(this.groups.length / this.num);
      // });
    }
  }
}
</script>