<template>
  <div>
    <div>
      <v-list
        three-line
        dark
      >
        <template
          v-for="(message, i) in messages"
          class="chat-list"
        >
          <v-list-item
            :key="i"
            class="chat-list-item"
          >
            <v-list-item-avatar>
              <!--TODO-- 等待对接后改成图片>-->
              <v-img :src="message.msgFromAvatar"></v-img>
              <!--                <v-icon v-text="message.msgFromAvatar"></v-icon>-->
            </v-list-item-avatar>
            <!--        TODO 聊天样式调整 & 一左一右 & 不同特效 & 发送状态-->
            <v-list-item-content>
              <v-list-item-title>{{ message.msgFromName }}</v-list-item-title>
              <v-list-item-subtitle>{{ message.msg }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-list>
    </div>
  </div>
</template>
<script>

export default {
  props: ["messages"],

  data () {
    return {
      about: -1,
      id: -1,
    }
  },
  methods: {
  }
}

</script>