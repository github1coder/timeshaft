<template>
  <v-overlay class="welcome-overlay" :value="$store.state.showAC" opacity="0.8">
    <v-card color="#474b63" class="welcome-card">
      <v-btn icon @click="$store.state.showAC = false">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <div>
        <v-avatar width="100" height="100" class="mt-5">
          <v-img :src="$store.state.myIcon || 'guest.png'"></v-img>
        </v-avatar>
      </div>
      <h2 class="mt-4 text-center">Change Avatar</h2>
      <v-card-text>Please type Image URL</v-card-text>
      <v-card-text class="mt-n4 mb-n6">
        <v-form ref="welcomeform" v-model="valid">
          <v-text-field
            class="text-field"
            v-model="imageUrl"
            label="Image URL"
            required
            :rules="urlRules"
          ></v-text-field>
        </v-form>
      </v-card-text>
      <v-card-text>
        <v-card-actions>
          <v-btn
            class="join-btn"
            large
            color="info"
            @click="updateAvatar"
            :loading="loading"
            :disabled="loading"
            >Update</v-btn
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
      valid: true,
      urlRegex: new window.RegExp(
        "^(https?:\\/\\/)?" + // protocol
        "((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|" + // domain name
        "((\\d{1,3}\\.){3}\\d{1,3}))" + // OR ip (v4) address
        "(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*" + // port and path
        "(\\?[;&a-z\\d%_.~+=-]*)?" + // query string
          "(\\#[-a-z\\d_]*)?$",
        "i"
      ), // fragment locator
      urlRules: [
        v => !!v || "Can not be empty",
        v => (v && this.urlRegex.test(v)) || "Invalid URL"
      ],
      imageUrl: null,
      loading: false
    };
  },

  methods: {
    updateAvatar() {
      this.loading = true;
      if (this.valid)
        socket.updateAvatar(this.imageUrl, () => {
          console.say("updateAvatar Callback")
          this.$store.commit("updateAvatar", this.imageUrl);
          this.loading = false;
          this.$store.commit("toggleAC");
        });
      else this.loading = false;
    },

    toggleAC() {
      this.$store.commit("toggleAC");
    }
  }
};
</script>
