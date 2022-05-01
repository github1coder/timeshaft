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
        >
          <!-- <v-system-bar></v-system-bar> -->
          <v-list>
            <v-list-item>
              <v-list-item-icon style="margin: 0px auto 20px;">
                <v-img
                  style="border-radius: 50%; width: 200px;"
                  src="https://cdn.vuetifyjs.com/images/john.png"
                ></v-img>
              </v-list-item-icon>
            </v-list-item>

            <v-list-item link>
              <v-list-item-content>
                <v-list-item-title class="title">John Leider</v-list-item-title>
                <v-list-item-subtitle>john@vuetifyjs.com</v-list-item-subtitle>
              </v-list-item-content>

              <v-list-item-action>
                <v-icon>mdi-menu-down</v-icon>
              </v-list-item-action>
            </v-list-item>
          </v-list>
          <v-divider></v-divider>
          <v-list
            nav
            dense
          >
            <v-list-item-group
              v-model="item"
              color="primary"
            >
              <v-list-item
                v-for="(item, i) in items"
                :key="i"
              >
                <v-list-item-icon>
                  <v-icon v-text="item.icon"></v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                  <v-list-item-title v-text="item.text"></v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-navigation-drawer>
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