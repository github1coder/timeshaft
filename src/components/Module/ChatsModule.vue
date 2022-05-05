<template>
  <div class="base-content">
    <MessageList></MessageList>
    <div class="chat">
      <ChatHeader></ChatHeader>
      <div class="chat-screen">
        <ChatMessages :draw="toolsDrawer"></ChatMessages>
      </div>
      <ChatTools :draw="toolsDrawer"></ChatTools>
    </div>
  </div>
</template>

<script>
import ChatTools from "@/components/Module/ChatsModule/ChatTools";
import ChatHeader from "@/components/Module/ChatsModule/ChatHeader";
import ChatMessages from "@/components/Module/ChatsModule/ChatMessages";
import MessageList from "@/components/Module/ChatsModule/MessageList";
export default {
  name: "ChatsModule",
  components: {ChatMessages, ChatHeader, ChatTools, MessageList},
  data() {
    return {
      toolsDrawer: false, // 用于控制工具栏打开与否
    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>