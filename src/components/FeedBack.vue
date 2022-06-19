<template>
  <v-card style=" position: fixed; right: 50rem; top: 25rem; background-color: skyblue;">
    <v-card-title v-text="msg"></v-card-title>
  </v-card>
</template>

<script>

export default {
  props: ["msg"],
  data () {
    return {
    }
  },

  methods: {


  },
}
</script>

<style lang="scss"></style>