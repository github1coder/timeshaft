<template>
  <nav class="guilds">
    <v-card
      dark
      flat
      tile
      width="100%"
      height="100%"
    >
      <v-container fluid>
        <v-row justify="center">
          <v-menu
            bottom
            min-width="200px"
            rounded
            offset-y
          >
            <template v-slot:activator="{ on }">
              <v-btn
                icon
                x-large
                v-on="on"
              >
                <v-avatar
                  color="brown"
                  size="48"
                >
                  <span class="white--text text-h5">{{ $store.state.myNick[0] }}</span>
                </v-avatar>
              </v-btn>
            </template>
            <v-card dark>
              <v-list-item-content class="justify-center">
                <div class="mx-auto text-center">
                  <v-avatar color="brown">
                    <span class="white--text text-h5">{{ $store.state.myNick[0] }}</span>
                  </v-avatar>
                  <h3>{{ $store.state.userId }}</h3>
                  <p class="text-caption mt-1">
                    {{ $store.state.email }}
                  </p>
                  <v-divider class="my-3"></v-divider>
                  <v-btn
                    depressed
                    rounded
                    text
                    @click="editAccount"
                  >
                    Edit Account
                  </v-btn>
                  <v-divider class="my-3"></v-divider>
                  <v-btn
                    depressed
                    rounded
                    text
                    @click="disconnect"
                  >
                    Disconnect
                  </v-btn>
                </div>
              </v-list-item-content>
            </v-card>
          </v-menu>
        </v-row>
      </v-container>
      <v-list>
        <v-list-item-group
          v-model="model"
          mandatory
          color="indigo"
        >
          <v-list-item
            v-for="(item, i) in navs"
            :key="i"
            @click="$store.commit('changeSiderState', i)"
            :ref="`sider` + i"
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
    </v-card>
  </nav>
</template>

<script>
import { logout } from "@/api/user";

export default {
  name: "Navigations",
  data () {
    return {
      model: 0,
      navs: [{
        icon: 'mdi-message-text',
        text: '',
      }, {
        icon: 'mdi-account-box-multiple-outline',
        text: '',
      }, {
        icon: 'mdi-file-document-multiple-outline',
        text: '',
      }, {
        icon: 'mdi-calendar-check',
        text: '',
      },],
    }
  },
  methods: {
    disconnect () {
      logout({
        "user_id": this.$store.state.userId,
      }).then(res => {
        console.log(res)
        this.$store.commit("setMyIcon", "guest.png")
        this.$store.commit("setMyNick", "N")
        this.$store.commit("setEmail", null)
        this.$store.commit("setLogin", false)
        this.$store.commit("setInfoId", -1)
        this.$store.commit("setInfoName", null)
        this.$store.commit("setInfoPhoto", null)
        this.$store.commit("setMaster", -1)
        this.$store.commit("changeSiderState", 0)
        this.$store.commit("setAbout", -1)
        this.$router.push({
          path: '/',
        })
        this.$store.state.messageList = []
        this.$store.state.listenerList = []
        this.$store.state.stompClient = null
        this.$store.state.websocket = null
        this.$store.state.checkInterval = null
        this.$store.state.siderState = 0
        this.$store.state.currentChannelId = -1
        this.$store.state.currentChannelIdx = -1
      })
    },
    editAccount () {
      this.$store.commit("setAbout", -1)
      this.$store.commit("changeSiderState", 1)
    },
  }
}

</script>

<style scoped>
</style>