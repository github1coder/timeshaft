<template>
  <div class="dc-container">
    <Navigations></Navigations>
    <div class="base">
      <ChatsModule v-if="$store.state.siderState === 0"></ChatsModule>
      <ContractsModule v-else-if="$store.state.siderState === 1"></ContractsModule>
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
  created() {
    window.onbeforeunload = () => {
      sessionStorage.setItem("data", JSON.stringify(this.$store.state))
      console.log("save")
      console.log(sessionStorage.getItem("data"))
    }
    console.log("get")
    console.log(sessionStorage.getItem("data"))
    if (sessionStorage.getItem("data")) {
      this.$store.replaceState(JSON.parse(sessionStorage.getItem("data")))
      setTimeout(function () {
        sessionStorage.removeItem("data");
      }, 300)
    }

  }


};
</script>

<style lang="scss"></style>