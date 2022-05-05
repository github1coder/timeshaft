<template>
  <v-card style="height: 100%; width: 100%">
    <v-card
      dark
      style="height: 100%; width: 100%"
      v-if="this.$store.getters.about == -1"
    ></v-card>
    <info-f v-if="this.$store.getters.about == 0"></info-f>
    <info-g v-else-if="this.$store.getters.about == 1"></info-g>
  </v-card>
</template>

<script>
import InfoF from "./MemInfo/InfoF.vue"
import InfoG from "./MemInfo/InfoG.vue"

export default {
  components: {
    InfoF,
    InfoG,
  },

  data () {
    return {
    }
  },
  methods: {
  }
}

</script>

<style scoped>
</style>