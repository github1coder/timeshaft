<template>
  <div class="dc-container">
    <Navigations></Navigations>
    <div class="base">
        <ChatsModule v-if="$store.state.siderState === 0"></ChatsModule>
        <Addresslist v-else-if="$store.state.siderState === 1"></Addresslist>
        <About v-else-if="$store.state.siderState === 4"></About>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import Addresslist from "@/components/Addresslist/Addresslist";
import About from "@/components/About/About";
import Navigations from "@/components/Navigations";
import ChatsModule from "@/components/Module/ChatsModule";

export default {
  name: "Home",
  components: {
    ChatsModule,
    Navigations,
    Addresslist,
    About,
  },
};
</script>

<style lang="scss"></style>