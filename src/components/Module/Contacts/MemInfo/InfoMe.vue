<template>
  <v-card height="100%">
    <v-card
      height="100%"
      flat
      tile
    >
      <v-navigation-drawer
        permanent
        style="width: 50%; height: 100%; float: left;"
      >
        <!-- <v-system-bar></v-system-bar> -->
        <v-list>
          <v-list-item>
            <v-list-item-icon style="margin: 0px auto 0px;">
              <v-img
                style="border-radius: 50%; width: 300px;"
                :src="photo"
              ></v-img>
            </v-list-item-icon>
          </v-list-item>
          <v-divider></v-divider>
          <v-list-item>
            <v-btn
              width="50%"
              color="blue-grey lighten-4"
              rounded
              style="margin: auto; font-size: 15px; font-weight: bold"
              @click="updateMyState"
              :loading="isState"
            >
              {{state}}
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
          <v-form
            ref="changeForm"
            v-model="valid"
          >
            <v-list-item>
              <v-text-field
                label="旧密码"
                v-model="password"
                :rules="rules.password"
                :type="type"
                @mouseover="changeShowText"
                @mouseleave="changeShowPassword"
              >
              </v-text-field>
            </v-list-item>
            <v-list-item>
              <v-text-field
                label="新密码"
                v-model="passwordN"
                :rules="rules.passwordN"
                :type="type"
                @mouseover="changeShowText"
                @mouseleave="changeShowPassword"
              >
              </v-text-field>
            </v-list-item>
            <v-list-item>
              <v-text-field
                label="验证码"
                v-model="inputCheckCode"
                :rules="rules.inputCheckCode"
              >
              </v-text-field>
            </v-list-item>
          </v-form>
          <v-list-item>
            <div style="width: 100%;">
              <v-btn
                class="mx-2 white--text"
                width="40%"
                color="blue-grey"
                @click="newCheckCode"
                style="[disabled]{
                  color: white !important;

                }"
                :disabled="loadingCheckCode"
              >
                {{hint}}
                <v-icon
                  right
                  dark
                  small
                >mdi-send</v-icon>
              </v-btn>
              <v-btn
                class="ma-2 white--text"
                color="blue-grey"
                width="40%"
                @click="changePassword"
              >
                确定
                <v-icon
                  right
                  dark
                  small
                >mdi-account-arrow-up-outline</v-icon>
              </v-btn>
            </div>
            <span>{{msg}}</span>
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
                hide-details
                :label="labelG"
                v-model="textG"
                class="input-search mt-3"
                autocomplete="off"
                style="width: 70%; margin: auto;"
              ></v-text-field>
              <v-btn
                class="ma-2 white--text"
                style="width: 15%; height: 64%; margin: 12px 0.25px auto;"
                color="blue-grey"
                @click="newGroup"
              >
                创建
                <v-icon
                  right
                  dark
                >mdi-account-group-outline</v-icon>
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
import { getCheckCode, changePwd, updateSelf } from '../../../../api/user/index';
export default {
  data () {
    return {
      name: this.$store.getters.myNick,
      photo: this.$store.getters.myIcon,
      email: this.$store.getters.email,
      checkCode: "",
      inputCheckCode: "",
      password: "",
      passwordN: "",
      textG: "",
      loadingCheckCode: false,
      labelG: "团队名字",
      hint: "发送验证码",
      valid: true,
      type: "password",
      msg: "",
      state: "可被其他用户发现",
      isState: false,
      rules: {
        password: [
          (password) => !!password || "密码不能为空",
          (password) => (password && (/^[\w]{6,16}$/).test(password)) || "请输入6~16位密码,仅允许字母和数字",
        ],
        passwordN: [
          (password) => !!password || "密码不能为空",
          (password) => (password && (/^[\w]{6,16}$/).test(password)) || "请输入6~16位密码,仅允许字母和数字",
        ],
        inputCheckCode: [
          (inputCheckCode) => !!inputCheckCode || "验证码不能为空",
          // (inputCheckCode) => (inputCheckCode && this.inputCheckCode === this.checkCode) || "验证码错误",
        ]
      }
    };
  },

  mounted () {
    this.state = this.$store.state.state ? "可被其他用户发现" : "不可被其他用户发现"
  },

  methods: {
    method1 () {

    },

    updateMyState () {
      this.$store.state.state = !this.$store.state.state
      updateSelf({
        "state": this.$store.state.state
      })
      if (!this.timer) {
        this.count = 1
        this.isState = true
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= 1) {
            this.count--
            this.state = this.count
          } else {
            this.isState = false
            clearInterval(this.timer)
            this.timer = null
            this.state = this.$store.state.state ? "可被其他用户发现" : "不可被其他用户发现"
          }
        }, 1000)
      }
    },

    changeShowText () {
      this.type = "text";
    },

    changeShowPassword () {
      this.type = "password";
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

    newCheckCode () {
      getCheckCode({
        'email': this.$store.getters.email,
        "type": "password"
      })
      // .then(res => {
      // this.checkCode = res.checkCode
      // console.log(this.checkCode)
      // })
      if (!this.timer) {
        this.count = 60
        this.loadingCheckCode = true
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= 60) {
            this.count--
            this.hint = this.count
          } else {
            this.loadingCheckCode = false
            clearInterval(this.timer)
            this.timer = null
            this.hint = "发送验证码"
            this.checkCode = ""
          }
        }, 1000)
      }
    },

    changePassword () {
      if (!this.valid) {
        return
      }
      changePwd({
        'oldPassword': this.password,
        'newPassword': this.passwordN,
        'checkCode': this.inputCheckCode,
      }).then(res => {
        this.password = ""
        this.passwordN = ""
        this.inputCheckCode = ""
        console(res)
        if (res && res.error) {
          this.msg = res.error
        }
      })
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
        this.$parent.$parent.$parent.$refs.memberList.getG()
      })
    }
  }
}
</script>