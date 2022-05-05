<template>
  <div class="sidebar">
    <group-and-friend v-if="show == 0"></group-and-friend>
    <add-member v-else-if="show == 1"></add-member>
    <apply-list v-else></apply-list>
    <div
      class="overflow-hidden"
      dark
    >
      <v-bottom-navigation
        color="blue"
        dark
        mandatory
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
import '../../api/addresslist/index'
import AddMember from './Contracts/FriendTool/Addmember.vue'
import ApplyList from './Contracts/FriendTool/ApplyList.vue'
import GroupAndFriend from './Contracts/GroupAndFriend.vue'
export default {
  components: {
    AddMember,
    GroupAndFriend,
    ApplyList,
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
      show: 0,
      values: [true, false, false]
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
      this.show = 0;
    },
    show2 () {
      this.show = 1;
    },
    show3 () {
      this.show = 2;
    },
  },

  mounted () {
  },
};
</script>