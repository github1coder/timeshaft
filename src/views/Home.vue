<template>
  <div class="dc-container">
    <Navigations></Navigations>
    <div class="base">
      <ChatsModule v-if="$store.state.siderState === 0"></ChatsModule>
      <ContractsModule v-else-if="$store.state.siderState === 1"></ContractsModule>
      <!-- <About v-else-if="$store.state.siderState === 4"></About> -->
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import ContractsModule from "@/components/Module/ContractsModule";
// import About from "@/components/About/About";
import Navigations from "@/components/Navigations";
import ChatsModule from "@/components/Module/ChatsModule";

export default {
  name: "Home",
  components: {
    ChatsModule,
    Navigations,
    ContractsModule,
  },
  created() {
    sessionStorage.getItem("data") &&
    this.$store.replaceState(JSON.parse(sessionStorage.getItem("data")))
  }
};
</script>

<style lang="scss"></style>