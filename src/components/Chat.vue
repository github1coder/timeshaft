<template>
  <v-list three-line dark>
    <template v-for="message in messages" class="chat-list">
      <v-list-item :key="message.name" class="chat-list-item">
        <v-list-item-avatar>
      <!--TODO-- 等待对接后改成图片>
      <v-image :src="message.avatar"></v-image>-->
          <v-icon v-text="message.avatar"></v-icon>
        </v-list-item-avatar>
<!--        TODO 聊天样式调整 & 一左一右 & 不同特效 & 发送状态-->
        <v-list-item-content>
          <v-list-item-title>{{ message.name }}</v-list-item-title>
          <v-list-item-subtitle>{{ message.message }}</v-list-item-subtitle>
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
      console.log("?")
      for (let i = 0; i < this.$store.state.listenerList.length; i++) {
        console.log(this.$store.state.listenerList[i].id + " " + this.$store.state.currentChannelId)
        if (this.$store.state.listenerList[i].id === this.$store.state.currentChannelId) {
          console.log("id" + this.$store.state.currentChannelId + " found")
          console.log(this.$store.state.listenerList[i].data)
          return this.$store.state.listenerList[i].data
        }
      }
      console.log("no such id!!")
      return []
    },
  },
  mounted() {

  },
  updated() {


  },
  methods: {

  }
};
</script>
