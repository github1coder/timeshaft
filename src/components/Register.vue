<template>
  <v-overlay
    class="welcome-overlay"
    :value="overlay"
    opacity="0.8"
  >
    <v-card
      color="#474b63"
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
      <v-card-text>首先，通过邮箱注册您的账户</v-card-text>
      <v-card-text class="mt-n4 mb-n6">
        <v-form
          ref="registerForm"
          v-model="valid"
        >
          <v-text-field
            class="text-field"
            v-model="username"
            label="昵称"
            required
            :counter="30"
            :rules="rules.username"
          ></v-text-field>
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
          <v-text-field
            class="text-field"
            v-model="rePassword"
            label="重复密码"
            required
            :counter="16"
            :rules="rules.rePassword"
            :type="type"
            @mouseover="changeShowText"
            @mouseleave="changeShowPassword"
          ></v-text-field>
          <v-text-field
            class="text-field"
            v-model="inputCheckCode"
            label="验证码"
            required
            :counter="6"
            :rules="rules.inputCheckCode"
          ></v-text-field>
        </v-form>
      </v-card-text>
      <v-card-text>
        <v-card-actions>
          <v-btn
            class="join-btn"
            large
            color="info"
            @click="sendCheckCode"
            :disabled="loadingCheckCode"
          >{{hint}}</v-btn>
          <v-btn
            class="join-btn"
            large
            color="info"
            @click="register"
            :loading="loadingRegister"
            :disabled="loadingRegister"
          >注册</v-btn>
        </v-card-actions>
      </v-card-text>
      <router-link to="/login">
        <v-card-text to="/login">已有账号，返回登录</v-card-text>
      </router-link>
    </v-card>
  </v-overlay>
</template>


<script>
import { getCheckCode, register, login } from '../api/user/index'
export default {
  data () {
    return {
      overlay: true,
      valid: true,
      loadingCheckCode: false,
      loadingRegister: false,
      checkCode: "",
      username: "",
      email: "",
      inputCheckCode: "",
      password: "",
      rePassword: "",
      type: "password",
      hint: "发送验证码",
      rules: {
        username: [
          (username) => !!username || "昵称不能为空",
          (username) => (username && username.length <= 30) || "昵称长度不能超过30",
        ],
        email: [
          (email) => !!email || "邮箱不能为空",
          (email) => (email && (/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/).test(email)) || "邮箱不合法",
        ],
        password: [
          (password) => !!password || "密码不能为空",
          (password) => (password && (/^[\w]{6,16}$/).test(password)) || "请输入6~16位密码,仅允许字母和数字",
        ],
        rePassword: [
          (rePassword) => (rePassword && rePassword === this.password) || "两次密码不一致",
        ],
        inputCheckCode: [
          (inputCheckCode) => !!inputCheckCode || "验证码不能为空",
          (inputCheckCode) => (inputCheckCode && this.inputCheckCode === this.checkCode) || "验证码错误",
        ]
      }
    };
  },

  mounted () {
  },

  methods: {
    changeShowText () {
      this.type = "text";
    },

    changeShowPassword () {
      this.type = "password";
    },

    register () {
      this.$refs.registerForm.validate();
      const param = {
        'username': this.username,
        'password': this.password,
        'email': this.email
      }
      if (this.valid) {
        this.loading = true;
        register(param).then(response => {
          console.log(response)
          login(param).then(res => {
            this.$store.commit("setUserId", res.id)
            // console.log(this.$store.getters.userId)
            this.$store.commit("setMyIcon", res.photo)
            this.$store.commit("setMyNick", res.username)
            this.$store.commit("setEmail", res.email)
            this.$store.commit("setLogin", true)
            this.$router.push({
              path: '/home',
            })
          })
        })
      }
    },

    async sendCheckCode () {
      if (this.email === '') {
        this.$message.error('请先输入邮箱再点击获取验证码')
      } else {
        if (!(this.email && (/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/).test(this.email))) {
          this.$message({ showClose: true, message: '请输入格式正确有效的邮箱号!', type: 'error' })
        } else {
          const param = {
            'email': this.email
          }
          console.say(param)
          getCheckCode(param).then(res => {
            this.checkCode = res.checkCode
            console.log(this.checkCode)
          })
        }
        console.log(this.checkCode)
        // 验证码倒计时, 60s后重新发送，并且验证码为空
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
      }
    },
  },
};
</script>
