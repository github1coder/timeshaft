<template>
  <div
    style="height: 100%; weight: 100%;"
    class="one"
  >
    <v-list three-line>
      <v-list-item
        :key="i"
        v-for="(message, i) in messages"
        class="msg-css"
      >
        <v-list-item-avatar>
          <!--TODO-- 等待对接后改成图片>-->
          <v-img :src="message.msgFromAvatar"></v-img>
          <!--                <v-icon v-text="message.msgFromAvatar"></v-icon>-->
        </v-list-item-avatar>
        <!--        TODO 聊天样式调整 & 一左一右 & 不同特效 & 发送状态-->
        <v-list-item-content style="text-align: left;">
          <v-list-item-title style=" font-size: 20px;">{{ message.msgFromName }}</v-list-item-title>
          <v-list-item-subtitle style=" font-size: 20px;">{{ message.msg }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </div>
</template>
<style scoped>
.msg-css {
  border-radius: 10px;
}
.msg-css:hover {
  background-color: pink;
}
</style>
<script>

export default {
  props: ["messages"],

  data () {
    return {
    }
  },
  methods: {
  }
}

</script>