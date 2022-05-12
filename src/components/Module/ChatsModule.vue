<template>
  <div class="base-content">
    <MessageList></MessageList>
    <div class="chat">
      <ChatHeader></ChatHeader>
      <div class="chat-screen" v-if="$store.state.currentChannelIdx !== -1">
        <ChatMessages :draw="toolsDrawer"></ChatMessages>
        <div
          class="moveBand"
          v-if="toolsDrawer"
        >
          <TimeShaft v-if="tools[0].show"></TimeShaft>
          <info-tool v-if="tools[1].show"></info-tool>
        </div>
      </div>
    </div>
    <ChatTools
      :draw="toolsDrawer"
      :tools="tools"
      @callback="callback"
    ></ChatTools>
  </div>
</template>

<script>
import ChatTools from "@/components/Module/ChatsModule/ChatTools";
import ChatHeader from "@/components/Module/ChatsModule/ChatHeader";
import ChatMessages from "@/components/Module/ChatsModule/ChatMessages";
import MessageList from "@/components/Module/ChatsModule/MessageList";
import TimeShaft from "@/components/Module/ChatsModule/ChatTools/TimeShaft";
import InfoTool from "@/components/Module/ChatsModule/ChatTools/InfoTool"
export default {
  name: "ChatsModule",
  components: { ChatMessages, ChatHeader, ChatTools, MessageList, TimeShaft, InfoTool },
  data () {
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
    callback (flag) {
      this.toolsDrawer = flag
    }
  }
}
</script>

<style scoped>
</style>