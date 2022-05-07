<template>
  <v-card
    height="100%"
    dark
  >
    <v-card
      height="100%"
      dark
      flat
      tile
    >
      <v-navigation-drawer
        permanent
        style="width: 50%; height: 100%; float: left;"
        dark
      >
        <!-- <v-system-bar></v-system-bar> -->
        <v-list dark>
          <v-list-item>
            <v-list-item-icon style="margin: 0px auto 0px;">
              <v-img
                style="border-radius: 50%; width: 300px;"
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
      <v-list style="width: 50%; float: right">
        <v-list-group
          prepend-icon="mdi-account-cog"
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
        <v-list-group
          prepend-icon="mdi-account-cowboy-hat"
          @click="initText"
        >
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title>创建团队</v-list-item-title>
            </v-list-item-content>
          </template>
          <v-list-item>
            <v-row
              dense
              style="width: 100%; height: 64px; margin: auto;"
            >
              <v-text-field
                clearable
                outlined
                dense
                dark
                hide-details
                :label="labelG"
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
</template>
<script>
import { addGroup } from '../../../../api/addresslist/index';
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
      labelG: "团队名字",
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

    initText () {
      this.textG = ""
      this.labelG = "团队名字"
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
        this.labelG = "创建成功"
        console.log(res)
        this.$parent.$parent.$parent.$children[1].getG()
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