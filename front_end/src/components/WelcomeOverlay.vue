<template>
  <v-overlay class="welcome-overlay" :value="overlay" opacity="0.8">
    <v-card color="#474b63" class="welcome-card">
      <div>
        <v-avatar width="100" height="100" class="mt-5">
          <v-img
            :src="$store.state.ownerIcon"
          ></v-img>
        </v-avatar>
      </div>
      <h2 class="mt-4 text-center">Welcome to Discord Clone!!</h2>
      <v-card-text>First, enter your nickname</v-card-text>
      <v-card-text class="mt-n4 mb-n6">
        <v-form ref="welcomeform" v-model="valid">
          <v-text-field
            class="text-field"
            v-model="nickname"
            label="Nick"
            required
            :counter="30"
            :rules="rules"
          ></v-text-field>
        </v-form>
      </v-card-text>
      <v-card-text>
        <v-card-actions>
          <v-btn
            class="join-btn"
            large
            color="info"
            @click="join"
            :loading="loading"
            :disabled="loading"
            >Join</v-btn
          >
        </v-card-actions>
      </v-card-text>
    </v-card>
  </v-overlay>
</template>

<script>
import socket from "../socket";

export default {
  data() {
    return {
      overlay: false,
      valid: true,
      nickname: "",
      loading: false,
      rules: [
        (v) => !!v || "Nick is required",
        (v) => (v && v.length <= 30) || "Nick must be less than 30 characters",
      ],
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
    join() {
      console.say("Nick:", this.nickname);
      this.loading = true;
      this.$refs.welcomeform.validate();
      if (this.valid)
        socket.join(this.nickname, (token) => {
          this.overlay = false;
          window.localStorage.setItem("accToken", token);
        });
      else {
        this.loading = false;
      }
    },
  },
};
</script>
