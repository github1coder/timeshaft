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
import socket from "../socket";
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
    this.$store.commit("updateChannels", this.itemss);
    if (this.$store.state.membersComponentMounted) return;
    this.$store.commit("membersMounted");
    console.say("members mount");
    socket.getMembers((data) => {
      console.say("getMem", data);
      this.$store.commit("pushMembers", data);
    });
    socket.addEvent("memberJoin", (data) => {
      console.say("memberJoin", data);
      if (data.id == socket.sid) {
        //isMe
        this.member = this.$store.state.members.length;
        this.$store.commit("updateAvatar", data.user.img || "guest.png");
        this.$store.commit("setNick", data.user.nick);
      }
      this.$store.commit("pushMembers", data);

      this.$store.commit("pushChat", {
        user: {
          nick: "SERVER",
          img: this.$store.state.ownerIcon,
        },
        msg: data.user.nick + " has joined the chat.",
      });
    });

    socket.addEvent("memberLeave", (sid) => {
      this.$store.commit("removeMember", sid);
    });
  }
};
</script>