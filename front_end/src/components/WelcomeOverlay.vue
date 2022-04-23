<template>
  <v-overlay class="welcome-overlay" :value="overlay" opacity="0.8">
    <v-card color="#474b63" class="welcome-card">
      <div>
        <v-avatar width="100" height="0" class="mt-5">
          <v-img
            :src="$store.state.ownerIcon"
          ></v-img>
        </v-avatar>
      </div>
      <h2 class="mt-4 text-center">欢迎使用Time Shaft</h2>
      <v-card-text>请登录</v-card-text>
      <v-card-text class="mt-n4 mb-n6">
        <v-form ref="welcomeform" v-model="valid">
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
            color="info"
            @click="login"
            :loading="loading"
            :disabled="loading"
            >登录</v-btn
          >
          <v-btn
            class="join-btn"
            large
            color="info"
            to="/register"
            >注册</v-btn
          >
        </v-card-actions>
      </v-card-text>
    </v-card>
  </v-overlay>
</template>

<script>
import { login } from '../api/user/index'

export default {
  data() {
    return {
      overlay: false,
      valid: true,
      email: "",
      password: "",
      loading: false,
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
    };
  },

  mounted() {
    console.say("app.vue mount");
    if (!this.$store.state.loggedIn) {
      const acc = window.localStorage.getItem("accToken");
      if (acc)
        socket.sendToken(acc, (bool) => {
          if (!bool) this.overlay = true;
        });
      else {
        this.overlay = true;
      }
      this.$store.commit("setLogin", true);
    }
  },

  methods: {
    changeShowText() {
      this.type = "text";
    },

    changeShowPassword() {
      this.type = "password";
    },

    login() {
      this.$refs.registerForm.validate();
      if (this.valid)
      {
        this.loading = true;
        const param = {
          'email': this.email,
          'password': this.password
        }
        login(param).then(res => {
            console(res)
            //执行登录
          })
      }
    }
  },
};
</script>
