<template>
  <v-overlay
    class="welcome-overlay"
    :value="overlay"
    opacity="0.2"
  >
    <v-card
      color="#78909C"
      class="welcome-card"
    >
      <div>
        <v-avatar
          width="100"
          height="0"
          class="mt-5"
        >
          <v-img :src="$store.state.ownerIcon"></v-img>
        </v-avatar>
      </div>
      <h2 class="mt-4 text-center">欢迎使用Time Shaft</h2>
      <v-card-text>请登录</v-card-text>
      <v-card-text class="mt-n4 mb-n6">
        <v-form
          ref="welcomeform"
          v-model="valid"
        >
          <v-text-field
            class="text-field"
            v-model="email"
            label="邮箱"
            required
            :counter="30"
            :rules="rules.email"
          ></v-text-field>
          <v-text-field
            class="text-field"
            v-model="password"
            label="密码"
            required
            :counter="16"
            :rules="rules.password"
            :type="type"
            @mouseover="changeShowText"
            @mouseleave="changeShowPassword"
          ></v-text-field>
        </v-form>
      </v-card-text>

      <v-card-text>
        <v-card-actions>
          <v-btn
            class="join-btn"
            large
            color="normal"
            @click="login"
            :loading="loading"
            :disabled="loading"
          >登录</v-btn>

          <v-btn
            class="join-btn"
            large
            color="normal"
            to="/register"
          >注册</v-btn>
        </v-card-actions>

      </v-card-text>
      <v-card-text
        style="margin: auto"
        v-show="show"
      >
        <span style="color: brown">
          {{error}}
        </span>
      </v-card-text>
    </v-card>

  </v-overlay>
</template>

<script>
import { login } from '../api/user/index'

export default {
  data () {
    return {
      overlay: true,
      valid: true,
      email: "",
      password: "",
      loading: false,
      show: false,
      error: "",
      type: "password",
      rules: {
        email: [
          (email) => !!email || "邮箱不能为空",
          (email) => (email && (/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/).test(email)) || "邮箱不合法",
        ],
        password: [
          (password) => !!password || "密码不能为空",
          (password) => (password && (/^[\w]{6,16}$/).test(password)) || "请输入6~16位密码,仅允许字母和数字",
        ],
      },

    }
  },
  mounted () {
    console.log("reset usr config")
    this.$store.commit("setMyIcon", "guest.png")
    this.$store.commit("setMyNick", "N")
    this.$store.commit("setEmail", null)
    this.$store.commit("setLogin", false)
    this.$store.commit("setInfoId", -1)
    this.$store.commit("setInfoName", null)
    this.$store.commit("setInfoPhoto", null)
    this.$store.commit("setInfoNick", null)
    this.$store.commit("setMaster", -1)
    this.$store.commit("changeSiderState", 0)
    this.$store.commit("setAbout", -1)
    this.$store.state.chatClient = null
    this.$store.state.contactClient = null
    this.$store.state.chatSocket = null
    this.$store.state.contactSocket = null
    this.$store.state.checkInterval = null
    this.$store.state.siderState = 0
    this.$store.state.currentChannelId = -1
    this.$store.state.currentChannelIdx = -1
    sessionStorage.clear()
  },

  methods: {
    changeShowText () {
      this.type = "text";
    },

    changeShowPassword () {
      this.type = "password";
    },

    login () {
      this.$refs.welcomeform.validate();
      if (this.valid) {
        this.loading = true;
        const param = {
          'email': this.email,
          'password': this.password
        }
        login(param).then(res => {
          if (res.id) {
            this.$store.commit("setUserId", res.id)
            console.log(res)
            this.$store.commit("setMyIcon", res.photo)
            this.$store.commit("setMyNick", res.username)
            this.$store.commit("setEmail", res.email)
            this.$store.commit("setLogin", true)
            this.$router.push({
              path: '/home',
            })
          }
          else {
            this.error = "密码错误或邮箱不正确"
            this.show = true
            this.loading = false
          }
        })

      }
    }
  },
};
</script>
