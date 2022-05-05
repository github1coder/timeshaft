<template>
  <div style="width: 100%; height: 100%">
    <v-card
      dark
      flat
      tile
      style="width: 100%; height: 100%; border: 1px green solid"
    >
      <v-card style="margin: 10% 30%; width: 40%; height: 60%; border: 1px green solid">
        <div style="width: 100%; height: 100%;">
          <v-img
            style="margin: auto; border-radius: 50%; width: 60%"
            :src="photo"
          ></v-img>
          <v-list-item>
          </v-list-item>

          <v-list-item>
            <v-list-item-content>
              <v-list-item-title v-text="name"></v-list-item-title>
              <v-list-item-subtitle v-text="email"></v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </div>
        <!-- <div style="width: 50%; height: 100%">
          </div> -->
      </v-card>
    </v-card>
  </div>
</template>
<script>
export default {
  data () {
    return {
      name: this.$store.getters.infoName,
      photo: this.$store.getters.infoPhoto,
      email: this.$store.getters.infoEmail,
    };
  },

  mounted () {
  },

  methods: {
    method1 () {

    },
  }
}
</script>