<template>
  <v-card
    dark
    style="height: 100%; width: 100%"
  >
    <info-f></info-f>
    <!-- <info-g v-else-if="this.$store.getters.about == 1"></info-g> -->
  </v-card>
</template>

<script>
import InfoF from "./Info/InfoFriend.vue"
// import InfoG from "./Info/InfoGroup.vue"

export default {
  components: {
    InfoF,
    // InfoG
  },

  data () {
    return {
    }
  },
  methods: {
  }
}

</script>