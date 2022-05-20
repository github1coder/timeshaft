<template>
  <v-card style="height: 100%; width: 100%">
    <v-text-field
      clearable
      outlined
      dense
      dark
      hide-details
      label="请输入关键词"
      v-model="text"
      class="input-search mt-3"
      autocomplete="off"
      v-on="on"
      ref="search"
    ></v-text-field>
    <div>
      <History>
      </History>
    </div>

  </v-card>
</template>

<script>
import History from './Msg/History.vue'

export default {
  // props: ["type", "id"],
  components: {
    History
  },

  data () {
    return {
    }
  },
  methods: {
  },
  created () {
    console.log(this.type)
    console.log(this.id)
  }
}

</script>