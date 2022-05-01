<template>
  <div class="sb-container">
    <v-card
      height="100%"
      dark
      flat
      tile
      class="server-info"
    >
      <v-card
        class="mx-auto"
        width="280px"
        tile
        dark
      >
        <v-navigation-drawer
          permanent
          width="100%"
          dark
        >
          <!-- <v-system-bar></v-system-bar> -->
          <v-list dark>
            <v-list-item>
              <v-list-item-icon style="margin: 0px auto 20px;">
                <v-img
                  style="border-radius: 50%; width: 200px;"
                  src="https://cdn.vuetifyjs.com/images/john.png"
                ></v-img>
              </v-list-item-icon>
            </v-list-item>
            <v-list-item>
              <v-btn
                dark
                width="50%"
                style="margin: auto;"
              >
                修改头像
              </v-btn>
            </v-list-item>

            <v-list-item>
              <v-list-item-content>
                <v-list-item-title class="title">John Leider</v-list-item-title>
                <v-list-item-subtitle>john@vuetifyjs.com</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <v-divider></v-divider>
        </v-navigation-drawer>
        <v-list>
          <v-list-group>
            <v-list-item>
              <v-text-field>
              </v-text-field>
            </v-list-item>
            <v-list-item>
              <v-text-field>
              </v-text-field>
            </v-list-item>
            <v-list-item>
              <div style="margin: auto; border: 1px white solid">
                <v-btn
                  class="mx-2"
                  color="error"
                  large
                  @click="killFriend(j + num * (pageF - 1))"
                >
                  确定
                </v-btn>
                <v-btn
                  class="mx-2"
                  color="success"
                  large
                  @click="showQuitField(j + num * (pageF - 1))"
                >
                  取消
                </v-btn>
              </div>
            </v-list-item>
          </v-list-group>
        </v-list>
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
  }
}
</script>