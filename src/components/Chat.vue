<template>
  <v-list three-line dark>
    <template v-for="(message, i) in messages" class="chat-list">
      <v-list-item :key="i" class="chat-list-item">
        <v-list-item-avatar>
      <!--TODO-- 等待对接后改成图片>
      <v-image :src="message.avatar"></v-image>-->
          <v-icon v-text="message.msgFromAvatar"></v-icon>
        </v-list-item-avatar>
<!--        TODO 聊天样式调整 & 一左一右 & 不同特效 & 发送状态-->
        <v-list-item-content>
          <v-list-item-title>{{ message.msgFromName }}</v-list-item-title>
          <v-list-item-subtitle>{{ message.msg }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </template>
  </v-list>
</template>

<script>


export default {
  data() {
    return {

    };
  },

  computed:{
    messages() {
      if (this.$store.state.messageList.length === 0) {
        return []
      } else {
        return this.$store.state.messageList[this.$store.state.currentChannelIdx].data
      }
    },
  },
  mounted() {

  },
  updated() {
    console.log("----")
    console.log(this.messages)
  },
  methods: {

  }
};
</script>
