<template>
  <div class="sidebar">
    <about-group v-if="this.$store.getters.about == 2"></about-group>
    <about-me v-else-if="this.$store.getters.about == 1"></about-me>

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
      this.$store.commit("setAbout", 1)
    },
    show2 () {
      this.$store.commit("setAbout", 2)
    },
  },

  mounted () {
  },
};
</script>