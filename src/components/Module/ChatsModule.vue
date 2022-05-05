<template>
  <div class="base-content">
    <MessageList></MessageList>
    <div class="chat">
      <ChatHeader></ChatHeader>
      <div class="chat-screen">
        <ChatMessages :draw="toolsDrawer"></ChatMessages>
        <div
            class="moveBand"
            v-if="toolsDrawer"
        >
          <TimeShaft v-if="tools[0].show"></TimeShaft>
        </div>
      </div>
    </div>
    <ChatTools :draw="toolsDrawer" :tools="tools" @callback="callback"></ChatTools>
  </div>
</template>

<script>
import ChatTools from "@/components/Module/ChatsModule/ChatTools";
import ChatHeader from "@/components/Module/ChatsModule/ChatHeader";
import ChatMessages from "@/components/Module/ChatsModule/ChatMessages";
import MessageList from "@/components/Module/ChatsModule/MessageList";
import TimeShaft from "@/components/Module/ChatsModule/ChatTools/TimeShaft";
export default {
  name: "ChatsModule",
  components: {ChatMessages, ChatHeader, ChatTools, MessageList, TimeShaft},
  data() {
    return {
      toolsDrawer: false, // 用于控制工具栏打开与否
      tools: [{
        icon: 'mdi-timeline',
        text: '',
        show: false
      }, {
        icon: 'mdi-cloud-search-outline',
        text: '',
        show: false
      }, {
        icon: 'mdi-cog-outline',
        text: '',
        show: false
      },
      ],
    }
  },
  methods: {
    callback(flag) {
      this.toolsDrawer = flag
    }
  }
}
</script>

<style scoped>

</style>