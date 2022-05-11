<template>
  <v-card style="height: 100%; width: 100%">
    <info-me
      dark
      style="height: 100%; width: 100%; border-radius:0px;"
      v-if="this.$store.getters.about == -1"
    ></info-me>
    <info-f v-if="this.$store.getters.about == 0"></info-f>
    <info-g v-else-if="this.$store.getters.about == 1"></info-g>
  </v-card>
</template>

<script>
import InfoF from "./Info/InfoFriend.vue"
import InfoG from "./Info/InfoGroup.vue"

export default {
  components: {
    InfoF,
    InfoG
  },

  data () {
    return {
    }
  },
  methods: {
  }
}

</script>