<template>
  <div style="width: 100%; height: 100%">
    <v-card
      dark
      flat
      tile
      style="width: 100%; height: 100%;"
    >
      <v-card style="margin: auto; width: 60%; height: 100%; border: grey 5px dashed;">
        <div style="width: 100%; height: 100%;">
          <v-img
            style="margin: auto; border-radius: 50%; width: 40%"
            :src="this.$store.getters.infoPhoto"
          ></v-img>
          <v-divider>
          </v-divider>

          <v-list-item>
            <v-list-item-content style="font-size: 25px;">
              <v-list-item-title style="font-size: 20px; line-height: 40px;">{{this.$store.getters.infoName}}</v-list-item-title>
              <v-list-item-title
                style="font-size: 20px; line-height: 40px;"
                v-show="this.$store.getters.infoNick != null"
              >备注：{{this.$store.getters.infoNick}}</v-list-item-title>
              <v-list-item-title style="font-size: 20px; line-height: 40px;">邮箱：{{this.$store.getters.infoEmail}}</v-list-item-title>
              <!-- <v-list-item-title>邮箱：{{this.$store.getters.infoEmail}}</v-list-item-title> -->
            </v-list-item-content>
          </v-list-item>
          <v-divider>
          </v-divider>
          <v-btn
            style="margin-top: 50px"
            color="success"
            width="30%"
            @click="sendMessage"
          >发消息</v-btn>
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
    };
  },

  mounted () {
  },

  methods: {
    method1 () {

    },

    sendMessage () {
      //todo, friend_id 为 this.$store.getters.infoId
    },
  }
}
</script>