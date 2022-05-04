<template>
  <div class="dc-container">
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

    <div class="base">
      <div class="base-content">
        <!-- here side -->
        <ChatSider v-if="$store.state.siderState === 0"></ChatSider>
        <Addresslist v-else-if="$store.state.siderState === 1"></Addresslist>
        <About v-else-if="$store.state.siderState === 4"></About>
        <div class="chat">
          <v-card
            class="chat-header"
            tile
          >
            <div
              class="ch"
              v-if="$store.state.listenerList.length > 0"
            >
              <div
                class="ch"
                v-if="$store.state.messageList.length > 0"
              >
                <v-icon
                  dark
                  left
                >{{
                  $store.state.messageList[getCh()].chatAvatar
                }}</v-icon>
                {{ $store.state.messageList[getCh()].chatName }}
              </div>
              <div class="head-tools">
                <div class="tool-icons-container">
                  <div class="hidden-sm-and-down">
                    <div class="tool-icon">
                      <v-btn icon>
                        <v-icon color="white">mdi-bell</v-icon>
                      </v-btn>
                    </div>
                    <div class="tool-icon">
                      <v-btn icon>
                        <v-icon color="white">mdi-cloud</v-icon>
                      </v-btn>
                    </div>
                    <div class="tool-icon">
                      <v-btn icon>
                        <v-icon color="white">mdi-account-supervisor-circle</v-icon>
                      </v-btn>
                    </div>
                  </div>
                  <div class="tool-icon">
                    <v-btn icon>
                      <v-icon color="white">mdi-cloud</v-icon>
                    </v-btn>
                  </div>
                  <div class="tool-icon">
                    <v-btn icon>
                      <v-icon color="white">mdi-account-supervisor-circle</v-icon>
                    </v-btn>
                  </div>
                </div>
                <div class="hidden-md-and-up">
                  <div class="tool-icon mr-n4">
                    <v-btn icon>
                      <v-icon
                        color="white"
                        @click="drawer = !drawer"
                      >mdi-account-group</v-icon>
                    </v-btn>
                  </div>
                </div>
              </div>

            </div>
          </v-card>

          <div class="chat-screen">
            <div class="chat-content">
              <div
                class="messages"
                v-scroll.self="onScroll"
              >
                <div :class="draw ? 'message-container-open' : 'message-container-close'">
                  <Chat></Chat>
                </div>
              </div>
              <div class="chat-form">
                <div :class="draw ? 'chat-form-open' : 'chat-form-close'">
                  <v-text-field
                    class="mx-5 my-3 chat-form-tf"
                    label="这边输入消息捏~"
                    solo
                    flat
                    clearable
                    v-model="msginform"
                    autocomplete="off"
                    @keyup.enter="sendChat(chatUrl, msginform)"
                    @keypress="setCanMessageSubmit"
                  >
                  </v-text-field>
                </div>
              </div>
              <div
                class="moveband"
                v-if="draw"
              >
                <TimeShaft v-if="tools[0].show"></TimeShaft>
              </div>
            </div>
            <div class="members hidden-sm-and-down">
              <v-card dark>
                <v-list
                  dark
                  v-for="(item, i) in tools"
                  :key="i"
                >
                  <v-list-item-icon>
                    <v-icon
                      v-text="item.icon"
                      style="margin-left: 30%;"
                      @click="changeBand(i)"
                    ></v-icon>
                  </v-list-item-icon>
                </v-list>
              </v-card>
              <!--                  <v-icon class="pr-16" style="float: left;flex-direction:column" v-text="'mdi-wifi'" @click="draw = !draw"></v-icon>-->
              <!--                  <v-icon style="float: left;flex-direction:column" v-text="'mdi-bluetooth'" @click="draw = !draw"></v-icon>-->

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import Chat from "@/components/Chat.vue";
import TimeShaft from "../components/shaft/TimeShaft.vue";
// import Stomp from "stompjs"
// import Sidebar from "@/components/Sidebar.vue";
// import Members from "@/components/Members.vue";
// import AvatarChanger from "@/components/AvatarChanger";
// import ChatTools from "@/components/ChatTools";
// import ListSider from "@/components/ListSider";
import ChatSider from "@/components/ChatSider";
import Addresslist from "@/components/Addresslist/Addresslist";
import About from "@/components/About/About";

import { logout } from '../api/user';
import { getHistoryMessage } from "@/api/message";

export default {
  name: "Home",
  components: {
    Addresslist,
    About,
    Chat,
    ChatSider,
    TimeShaft,
    // Sidebar,
    // Members,
    // ChatTools,
    // AvatarChanger
  },
  data () {
    return {
      refreshed: true,
      cache: 0,
      mini: true,
      drawer: false,
      canMessageSubmit: false,
      msginform: "",
      draw: null,
      tools: [{
        icon: 'mdi-timeline',
        text: '',
        show: false
      }, {
        icon: 'mdi-cloud-search-outline',
        text: '',
        show: false
      }, {
        icon: 'mdi-cog-outline',
        text: '',
        show: false
      },
      ],
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
      }, {
        icon: 'mdi-account-cog',
        text: '',
      },],
      model: 0
    };
  },

  methods: {
    changeBand (index) {
      if (!this.draw) {
        this.draw = !this.draw
        this.tools[index].show = true
      }
      else {
        if (this.tools[index].show) {
          this.draw = !this.draw
          this.tools[index].show = false
        }
        else {
          for (let i = 0; i < this.tools.length; i++) {
            if (i === index) {
              this.tools[i].show = true
            }
            else {
              this.tools[i].show = false
            }
          }
        }
      }

    },
    editAccount () {
      this.$store.commit("setAbout", 1)
      this.$store.commit("changeSiderState", 4)
    },
    clickIcon () {
      console.log(this.item);
    },
    clearMsg () {
      this.msginform = ""
    }
    ,
    getCh () {
      return (this.cache =
        this.$store.state.currentChannelIdx === undefined ?
          this.cache :
          this.$store.state.currentChannelIdx);
    },
    setCanMessageSubmit () {
      this.canMessageSubmit = true;
    },
    sendChat (url, message) {
      let name = this.$store.state.myNick
      let avatar = this.$store.state.myIcon
      const msgForm = {
        msgFromName: name,
        msgFromAvatar: avatar,
        msg: message,
        time: this.getDate(),
        srcId: this.$store.state.userId,
        dstId: this.$store.state.messageList[this.$store.state.currentChannelIdx].id,
      }
      console.log(this.$store.state.messageList[this.$store.state.currentChannelIdx].data)
      this.$store.state.messageList[this.$store.state.currentChannelIdx].data.push(msgForm)
      console.log(this.$store.state.messageList[this.$store.state.currentChannelIdx].data)
      // this.$store.commit("updateMessageList", {id: this.$store.state.currentChannelId, data:msgForm})
      this.clearMsg()
      this.$store.commit("WEBSOCKET_SEND", {
        url: url,
        data: msgForm,
      })
    },

    getDate () {
      const date = new Date();//当前时间
      const year = date.getFullYear(); //返回指定日期的年份
      const month = this.repair(date.getMonth() + 1);//月
      const day = this.repair(date.getDate());//日
      const hour = this.repair(date.getHours());//时
      const minute = this.repair(date.getMinutes());//分
      const second = this.repair(date.getSeconds());//秒
      //当前时间
      return year + "-" + month + "-" + day
        + " " + hour + ":" + minute + ":" + second
    },
    repair (i) {
      if (i >= 0 && i <= 9) {
        return "0" + i;
      } else {
        return i;
      }
    },

    disconnect () {
      logout({
        "user_id": this.$store.state.userId,
      }).then(res => {
        console.log(res)
        this.$store.commit("setMyIcon", "guest.png")
        this.$store.commit("setMyNick", "N")
        this.$store.commit("setEmail", null)
        this.$store.commit("setLogin", false)
        this.$store.commit("setGroupId", -1)
        this.$store.commit("setGroupName", null)
        this.$store.commit("setGroupPhoto", null)
        this.$store.commit("setMaster", -1)
        this.$store.commit("changeSiderState", 0)
        this.$store.commit("setAbout", 1)
        this.$router.push({
          path: '/',
        })
      })

    },
    onScroll () {
      // console.log(document.documentElement.scrollTop || document.querySelector('.messages').scrollTop)
      if (!this.refreshed && document.documentElement.scrollTop || document.querySelector('.messages').scrollTop === 0) {
        this.refreshed = true
      } else if (document.documentElement.scrollTop || document.querySelector('.messages').scrollTop !== 0) {
        this.refreshed = false
      }
      // console.log(this.refreshed)
    }
  },

  watch: {
    refreshed (newVal, oldVal) {
      if (newVal && !oldVal) {
        //TODO 补全
        console.log(this.$store.state.more)
        if (this.$store.state.more) {
          getHistoryMessage({
            srcId: this.$store.state.userId,
            dstId: this.$store.state.currentChannelId,
            index: this.$store.state.messageList[this.$store.state.currentChannelIdx].index,
          }).then(res => {
            console.log("000000000000000000")
            console.log(res)
            this.$store.state.more = res.more
            if (this.$store.state.currentChannelIdx !== -1) {
              this.$store.state.messageList[this.$store.state.currentChannelIdx].index = res.index
              console.log(this.$store.state.messageList[this.$store.state.currentChannelIdx].data)
              for (let i = res.data.length - 1; i >= 0; i--) {
                this.$store.state.messageList[this.$store.state.currentChannelIdx].data.unshift(res.data[i])
              }
              console.log(this.$store.state.messageList[this.$store.state.currentChannelIdx].data)
            }
          })
        }
      }
    }
  },

  computed: {
    chatUrl () {
      return "/app/personalMessage"
    },
  },

  mounted () {
  },
};
</script>

<style lang="scss"></style>