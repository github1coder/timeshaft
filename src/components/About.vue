<template>
  <div class="sidebar">
    <about-me v-if="show == 1"></about-me>
    <about-group v-else-if="show == 2"></about-group>
    <div
      class="overflow-hidden"
      dark
    >
      <v-bottom-navigation
        color="blue"
        dark
      >
        <v-btn @click="show1">
          <v-icon>mdi-account-group</v-icon>
        </v-btn>

        <v-btn @click="show2">
          <v-icon>mdi-account-multiple-plus</v-icon>
        </v-btn>
      </v-bottom-navigation>
    </div>
  </div>
</template>

<script>
import '../api/addresslist/index'
import AboutMe from './AboutMe'
import AboutGroup from './AboutGroup'
export default {
  components: {
    AboutMe,
    AboutGroup,
  },

  data () {
    return {
      itemss: [{
        text: "Announcements",
        icon: "mdi-bell-alert"
      }, {
        text: "Rules",
        icon: "mdi-file-check"
      }, {
        text: "Welcome",
        icon: "mdi-emoticon"
      }],
      member: 0,
      show: 1,
    };
  },

  methods: {
    toggleAC () {
      this.$store.commit("toggleAC");
    },

    method1 () {
      console.assert(1)
    },
    show1 () {
      this.show = 1;
    },
    show2 () {
      this.show = 2;
    },
    show3 () {
      this.show = 3;
    },
  },

  mounted () {
  },
};
</script>