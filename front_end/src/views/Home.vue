<template>
  <v-content>
    <div class="dc-container">
      <nav class="guilds">



        <v-card dark flat tile width="100%" height="100%">
          <v-avatar class="round-icon" @click="clickIcon">
            <v-icon size="30">mdi-emoticon-devil-outline</v-icon>
          </v-avatar>
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
              >
                <v-list-item-icon >
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
          <ListSider v-else-if="$store.state.siderState === 1"></ListSider>

          <div class="chat">
            <v-card class="chat-header" tile>
              <div class="hidden-md-and-up">
                <div class="tool-icon mr-n4">
                  <v-app-bar-nav-icon dark @click.stop="leftDrawer = !leftDrawer" />
                </div>
              </div>
              <div class="ch" v-if="$store.state.channels.length > 0">
                <v-icon dark left>{{
                  $store.state.channels[getCh()].icon
                }}</v-icon>
                {{ $store.state.channels[getCh()].text }}
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
                        <v-icon color="white"
                          >mdi-account-supervisor-circle</v-icon
                        >
                      </v-btn>
                    </div>
                  </div>
                  <div class="hidden-md-and-up">
                    <div class="tool-icon mr-n4">
                      <v-btn icon>
                        <v-icon color="white" @click="drawer = !drawer"
                          >mdi-account-group</v-icon
                        >
                      </v-btn>
                    </div>
                  </div>
                </div>


              </div>
            </v-card>

            <div class="chat-screen">
              <div class="chat-content">
                <div class="messages">
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
                        v-model="msginform"
                        autocomplete="off"
                        @keyup.enter="sendChat"
                        @keypress="setCanMessageSubmit"
                    >
                    </v-text-field>
                  </div>
                </div>

                <div class="chat-tool-open" v-if="draw">
                    <v-timeline>
                      <v-timeline-item>timeline item</v-timeline-item>
                      <v-timeline-item>
                        timeline item
                      </v-timeline-item>
                      <v-timeline-item>timeline item</v-timeline-item>
                    </v-timeline>
                </div>
              </div>
              <div  class="members hidden-sm-and-down">
                <v-card dark>
                  <v-list
                      dark
                      v-for="(item, i) in tools"
                      :key="i">
                      <v-list-item-icon>
                        <v-icon v-text="item.icon" @click="draw = !draw"></v-icon>
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
  </v-content>
</template>

<script>
// @ is an alias to /src
import Chat from "@/components/Chat.vue";
// import Sidebar from "@/components/Sidebar.vue";
// import Members from "@/components/Members.vue";
// import AvatarChanger from "@/components/AvatarChanger";
// import ChatTools from "@/components/ChatTools";

import socket from "../socket";
import ListSider from "@/components/ListSider";
import ChatSider from "@/components/ChatSider";

export default {
  name: "Home",
  components: {
    ListSider,
    Chat,
    ChatSider,
    // Sidebar,
    // Members,
    // ChatTools,
    // AvatarChanger
  },
  data() {
    return {
      cache: 0,
      mini: true,
      drawer: false,
      leftDrawer: false,
      canMessageSubmit: false,
      msginform: "",
      draw: null,
      tools: [
        {
          icon: 'mdi-timeline',
          text: '',
        },
        {
          icon: 'mdi-cloud-search-outline',
          text: '',
        },
        {
          icon: 'mdi-cog-outline',
          text: '',
        },
      ],
      navs: [
        {
          icon: 'mdi-message-text',
          text: '',
        },
        {
          icon: 'mdi-account-box-multiple-outline',
          text: '',
        },
        {
          icon: 'mdi-file-document-multiple-outline',
          text: '',
        },
        {
          icon: 'mdi-calendar-check',
          text: '',
        },
      ],
      model: 0
    };
  },

  methods: {
    clickIcon() {
      console.log(this.item);
    },

    getCh() {
      return (this.cache =
        this.$store.state.currentChannel === undefined
          ? this.cache
          : this.$store.state.currentChannel);
    },

    setCanMessageSubmit() {
      this.canMessageSubmit = true;
    },

    sendChat() {
      if (this.msginform == "") return;
      console.say("form-msg:", this.msginform);
      socket.sendChat(this.msginform);
      this.msginform = "";
    },

  },

  mounted() {
    console.say("home.vue mount");
  }
};
</script>

<style lang="scss"></style>
