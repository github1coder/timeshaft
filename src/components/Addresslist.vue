<template>
  <div class="sidebar">
    <group-and-friend v-if="show == 1"></group-and-friend>
    <add-member v-else-if="show == 2"></add-member>
    <group-and-friend v-else></group-and-friend>
    <div
      class="overflow-hidden"
      dark
    >
      <v-bottom-navigation
        v-model="activeBtn"
        :input-value="showNav"
        color="blue"
        dark
      >
        <v-btn @click="show1">
          <v-icon>mdi-account-group</v-icon>
        </v-btn>

        <v-btn @click="show2">
          <v-icon>mdi-account-multiple-plus</v-icon>
        </v-btn>

        <v-btn @click="show3">
          <v-icon>mdi-email-plus-outline</v-icon>
        </v-btn>
      </v-bottom-navigation>
    </div>
  </div>
</template>

<script>
import '../api/addresslist/index'
import GroupAndFriend from './GroupAndFriend.vue'
import AddMember from './Addmember.vue'
export default {
  components: {
    AddMember,
    GroupAndFriend,
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

  updated () {
    this.$store.commit("changeChannel", this.item);
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