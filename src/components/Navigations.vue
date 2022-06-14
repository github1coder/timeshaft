<template>
  <nav class="guilds">
    <v-card
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
                    编辑信息
                  </v-btn>
                  <v-divider class="my-3"></v-divider>
                  <v-btn
                    depressed
                    rounded
                    text
                    @click="disconnect"
                  >
                    退出登录
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
            @click="$store.commit('changeSiderState', i);$store.commit('setAbout', -1)"
            :ref="`sider` + i"
            :id="'nav-'+i"
          >
            <v-list-item-icon>
              <v-badge
                color="red"
                :value="($store.state.unreadNum !== 0 && i === 0) || ($store.state.applynum !== 0 && i === 1)"
                :content="i === 0 ? $store.state.unreadNum : $store.state.applynum"
              >
                <v-icon v-text="item.icon"></v-icon>
                <v-list-item-title
                  v-text="item.text"
                  style="font-size: 5px"
                ></v-list-item-title>
              </v-badge>
            </v-list-item-icon>

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
        text: '消息栏',
      }, {
        icon: 'mdi-account-box-multiple-outline',
        text: '通讯录',
      }, {
        icon: 'mdi-file-document-multiple-outline',
        text: '收藏夹',
      }, {
        icon: 'mdi-calendar-check',
        text: '日历表',
      },],
    }
  },
  methods: {
    disconnect () {
      logout({
      }).then(res => {
        console.log(res)
        this.$store.commit("setMyIcon", "guest.png")
        this.$store.commit("setMyNick", "N")
        this.$store.commit("setEmail", null)
        this.$store.commit("setLogin", false)
        this.$store.commit("setInfoId", -1)
        this.$store.commit("setInfoName", null)
        this.$store.commit("setInfoNick", null)
        this.$store.commit("setInfoPhoto", null)
        this.$store.commit("setMaster", -1)
        this.$store.commit("changeSiderState", 0)
        this.$store.commit("setAbout", -1)
        sessionStorage.setItem("login", "no")
        this.$router.push({
          path: '/',
        })
        this.$store.state.stompClient = null
        this.$store.state.websocket = null
        this.$store.state.checkInterval = null
        this.$store.state.siderState = 0
        this.$store.state.currentChannelId = -1
        this.$store.state.currentChannelIdx = -1
      })
    },
    editAccount () {
      if (this.$parent.$refs.contractsModule) {
        this.$parent.$refs.contractsModule.show = 0
        if (this.$parent.$refs.contractsModule.$refs.infoTool) {
          this.$parent.$refs.contractsModule.$refs.infoTool.about = -1
        }
      }

      this.$store.commit("changeSiderState", 1)
    },
  }
}

</script>

<style scoped>
</style>