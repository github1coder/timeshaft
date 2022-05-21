<template>
  <v-card
    dark
    style="height: 100%; width: 100%"
  >
    <v-row
      dense
      dark
      style="width: 100%; height: 64px; margin: auto;"
    >
      <v-text-field
        clearable
        outlined
        dense
        dark
        hide-details
        v-model="text"
        class="input-search mt-3"
        autocomplete="off"
        style="width: 70%; margin: auto;"
      ></v-text-field>
      <v-btn
        style="width: 15%; height: 64%; margin: 12px 0px auto;"
        @click="searchHistory"
      >
        <!-- <v-icon style="width: 100%; height: 100%;">
          mdi-magnify
        </v-icon> -->
        搜索
      </v-btn>
    </v-row>
    <div>
      <History v-show="show">
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
      text: "",
      show: false
    }
  },

  methods: {
    searchHistory () {

    },
  },

  created () {
    console.log(this.type)
    console.log(this.id)
  }
}

</script>