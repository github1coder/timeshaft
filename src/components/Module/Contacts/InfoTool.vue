<template>
  <v-card style="height: 100%; width: 100%">
    <info-me
      dark
      style="height: 100%; width: 100%; border-radius:0px;"
      v-if="this.$store.getters.about == -1"
      ref="infoMe"
    ></info-me>
    <info-f
      v-if="this.$store.getters.about == 0"
      ref="infoF"
    ></info-f>
    <info-g
      v-else-if="this.$store.getters.about == 1"
      ref="infoG"
    ></info-g>
  </v-card>
</template>

<script>
import InfoF from "./MemInfo/InfoF.vue"
import InfoG from "./MemInfo/InfoG.vue"
import InfoMe from "./MemInfo/InfoMe.vue"

export default {
  components: {
    InfoF,
    InfoG,
    InfoMe
  },

  data () {
    return {
    }
  },
  methods: {
  }
}

</script>