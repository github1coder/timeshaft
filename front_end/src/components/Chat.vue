<template>
  <v-list three-line dark>
    <template v-for="item in $store.state.chats" class="chat-list">
      <v-list-item :key="item.title" class="chat-list-item">
        <v-list-item-avatar>
          <v-img :src="item.avatar"></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
          <v-list-item-subtitle>{{ item.subtitle }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </template>
  </v-list>
</template>

<script>
import socket from "../socket";

export default {
  data() {
    return {
      chats: []
    };
  },

  mounted() {
    console.say("Chat.vue mount");
    socket.getChat(data => {
      // all messages
      console.say("all msgs:", data);
      for (let i of data) {
        this.$store.commit("pushChat", i);
      }
      setTimeout(this.scrollList, 10);
    });

    socket.addEvent("chat", data => {
      console.say("got chat:", data);
      //this.pushChat(data);
      this.$store.commit("pushChat", data);
    });
  },

  methods: {
    pushChat(data) {
      this.chats.push({
        avatar: data.user.img || "guest.png",
        title: data.user.nick,
        subtitle: data.msg
      });
    },
  }
};
</script>
