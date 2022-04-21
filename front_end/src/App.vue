<template>
  <v-app>
    <router-view />
    <WelcomeOverlay />
  </v-app>
</template>

<script>
// import WelcomeOverlay from "./components/WelcomeOverlay";

//console.say = console.log;
console.say = function(){}

export default {
  components: {
    // WelcomeOverlay,
  },
};
</script>

<style lang="scss">
@import "./main.scss";
@import "./style.scss";
</style>
