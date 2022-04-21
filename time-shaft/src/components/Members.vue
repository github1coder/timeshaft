<template>
  <v-card width="100%" dark flat tile class="mcard">
    <v-list rounded dense>
      <v-subheader>ONLINE ({{ $store.state.members.length }})</v-subheader>
      <v-list-item-group v-model="member" color="">
        <v-list-item v-for="(item, i) in $store.state.members" :key="i">
          <v-list-item-avatar>
            <img :src="item.img" alt="alt" />
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title
              class="channel-title"
              v-text="item.name"
            ></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>
</template>

<script>
import socket from "../socket";

export default {
  data() {
    return {
      member: 0,
    };
  },

  updated() {},

  mounted() {
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
  },
};
</script>
