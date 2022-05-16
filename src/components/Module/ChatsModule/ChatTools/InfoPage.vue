<template>
  <v-card style="height: 100%; width: 100%">
    <info-f
      v-show="about == 0"
      ref="infoF"
    ></info-f>
    <info-g
      v-show="about == 1"
      ref="infoG"
    ></info-g>
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
      about: -1,
      id: -1,
    }
  },
  methods: {
  }
}

</script>