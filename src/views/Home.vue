<template>
  <div class="dc-container">
    <Navigations></Navigations>
    <div class="base">
      <ChatsModule v-if="$store.state.siderState === 0"></ChatsModule>
      <ContractsModule
        v-else-if="$store.state.siderState === 1"
        ref="contractsModule"
      ></ContractsModule>
      <Empty v-else-if="$store.state.siderState === 2"></Empty>
      <CalendarModule v-else-if="$store.state.siderState === 3"></CalendarModule>

      <!-- <About v-else-if="$store.state.siderState === 4"></About> -->
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import ContractsModule from "@/components/Module/ContactsModule";
// import About from "@/components/About/About";
import Navigations from "@/components/Navigations";
import ChatsModule from "@/components/Module/ChatsModule";
import CalendarModule from "@/components/Module/CalendarModule";
import Empty from "@/components/Module/empty";


export default {
  name: "Home",
  components: {
    CalendarModule,
    ChatsModule,
    Navigations,
    ContractsModule,
    Empty,
  },
  created () {
    window.onbeforeunload = () => {
      this.$store.state.chatClient = null
      this.$store.state.contactClient = null
      this.$store.state.chatSocket = null
      this.$store.state.contactSocket = null
      this.$store.state.currentChatType =  null,
      this.$store.state.currentChatIndex= null,
      this.$store.state.currentChatMore= null,
      this.$store.state.currentChatName= null,
      this.$store.state.currentChannelIdx = -1,
      this.$store.state.currentChannelId= -1,
      console.log(this.$store.state)
      sessionStorage.setItem("data", JSON.stringify(this.$store.state))
      console.log("save")
      console.log(sessionStorage.getItem("data"))
    }
    console.log("get")
    console.log(sessionStorage.getItem("data"))
    if (sessionStorage.getItem("data")) {
      console.log(JSON.parse(sessionStorage.getItem("data")))
      this.$store.replaceState(JSON.parse(sessionStorage.getItem("data")))
      console.log(JSON.parse(sessionStorage.getItem("data")))
      setTimeout(function () {
        sessionStorage.removeItem("data");
      }, 300)
    }

  }


};
</script>

<style lang="scss"></style>