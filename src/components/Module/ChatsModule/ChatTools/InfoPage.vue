<template>
  <v-card style="height: 100%; width: 100%">
    <info-f
      v-if="type == 'friend'"
      ref="infoF"
      :id="id"
    ></info-f>
    <info-g
      v-if="type == 'group'"
      ref="infoG"
      :id="id"
    ></info-g>
  </v-card>
</template>

<script>
import InfoF from "./Info/InfoFriend.vue"
import InfoG from "./Info/InfoGroup.vue"

export default {
  props: ["type", "id"],
  components: {
    InfoF,
    InfoG
  },

  data () {
    return {
    }
  },
  methods: {
  },
  created () {
    // console.log(this.type)
    // console.log(this.id)
  }
}

</script>