<template>
  <div>
    <v-btn
      style="position: fixed; top: 2rem;"
      v-show="showBack"
      @click="backChat"
    >返回时间轴</v-btn>
    <!-- <v-btn
      style="position: fixed; top: 2rem; right:4rem"
      v-show="showEnd"
      @click="endTime"
    >结束时间轴</v-btn> -->
  </div>
</template>

<script>

export default {
  data () {
    return {
      showBack: true,
      showEnd: true,
    }
  },

  methods: {
    backChat () {
      //todo:返回群聊
    },

    // endTime () {

    // },
  },
}
</script>

<style lang="scss"></style>