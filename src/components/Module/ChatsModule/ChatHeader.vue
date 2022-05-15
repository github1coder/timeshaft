<template>
  <v-card
      class="chat-header"
      tile
  >
    <div
        class="ch"
        v-if="$store.state.currentChannelId !== -1"
    >
      <div
          class="ch"
      >
        {{ $store.state.currentChatName }}
      </div>
      <div class="head-tools">
        <div class="tool-icons-container">
          <div class="hidden-sm-and-down">
            <div class="tool-icon">
              <v-btn icon>
                <v-icon color="white">mdi-bell</v-icon>
              </v-btn>
            </div>
            <div class="tool-icon">
              <v-btn icon>
                <v-icon color="white">mdi-cloud</v-icon>
              </v-btn>
            </div>
            <div class="tool-icon">
              <v-btn icon>
                <v-icon color="white">mdi-account-supervisor-circle</v-icon>
              </v-btn>
            </div>
          </div>
          <div class="tool-icon">
            <v-btn icon>
              <v-icon color="white">mdi-cloud</v-icon>
            </v-btn>
          </div>
          <div class="tool-icon">
            <v-btn icon>
              <v-icon color="white">mdi-account-supervisor-circle</v-icon>
            </v-btn>
          </div>
        </div>
        <div class="hidden-md-and-up">
          <div class="tool-icon mr-n4">
            <v-btn icon>
              <v-icon
                  color="white"
              >mdi-account-group</v-icon>
            </v-btn>
          </div>
        </div>
      </div>

    </div>
  </v-card>
</template>

<script>
export default {
  name: "ChatHeader",
  data() {
    return {

    }
  },
  methods: {
    getCh () {
      return (this.cache =
          this.$store.state.currentChannelIdx === undefined ?
              this.cache :
              this.$store.state.currentChannelIdx);
    },
  }
}
</script>

<style scoped>

</style>