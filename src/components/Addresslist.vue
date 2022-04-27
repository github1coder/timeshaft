<template>
  <div class="sidebar">
    <div class="sb-container">
      <v-card
        height="100%"
        dark
        flat
        tile
        class="server-info"
      >
        <v-card
          height="48"
          tile
          class="server-title"
        >
          <!--          <div style="line-height:48px">Discord Clone</div>-->
          <v-text-field
            dark
            flat
            solo-inverted
            hide-details
            dense
            prepend-inner-icon="mdi-magnify"
            label="Search"
            class="hidden-sm-and-down mx-2 head-search"
            autocomplete="off"
          ></v-text-field>
        </v-card>
        <v-list>
          <v-list-group :prepend-icon="channelLabels[0].action">
            <!-- no-action -->
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title v-text="channelLabels[0].title"></v-list-item-title>
              </v-list-item-content>
            </template>
            <v-list-item
              v-for="subItem in groups"
              :key="subItem.title"
              @click="method1"
            >
              <v-list-item-avatar>
                <v-img :src="subItem.avatar"></v-img>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title
                  v-text="subItem.title"
                  style="text-align: left"
                ></v-list-item-title>
                <v-text-field v-show="false"></v-text-field>
              </v-list-item-content>
              <!-- 后面的省略号 -->
              <v-list-item-action>
                <v-menu right>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      dark
                      icon
                      v-bind="attrs"
                      v-on="on"
                    >
                      <v-icon>mdi-dots-vertical</v-icon>
                    </v-btn>
                  </template>
                  <v-list
                    hover
                    dark
                  >
                    <v-list-item
                      v-for="(btnn, i) in groupsBtns"
                      :key="i"
                      @click="method1"
                    >
                      <v-list-item-title>{{ btnn.title }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                </v-menu>
              </v-list-item-action>
            </v-list-item>
          </v-list-group>
          <v-list-group :prepend-icon="channelLabels[1].action">
            <!-- no-action -->
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title v-text="channelLabels[1].title">
                </v-list-item-title>
              </v-list-item-content>
            </template>
            <v-list-item
              v-for="(subItem, j) in friends"
              :key="subItem.title"
              @click="method1"
            >
              <v-list-item-avatar>
                <v-img :src="subItem.avatar"></v-img>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title
                  v-show="!subItem.show"
                  v-text="subItem.title"
                  style="text-align: left"
                ></v-list-item-title>
                <v-text-field
                  v-show="subItem.show"
                  v-model="name"
                  @keydown.esc="showTextField(j)"
                  @keydown.enter="changeFriendName(j)"
                  clearable
                >
                </v-text-field>
              </v-list-item-content>
              <!-- 后面的省略号 -->
              <v-list-item-action>
                <v-menu right>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      dark
                      icon
                      v-bind="attrs"
                      v-on="on"
                    >
                      <v-icon>mdi-dots-vertical</v-icon>
                    </v-btn>
                  </template>
                  <v-list
                    hover
                    dark
                  >
                    <v-list-item
                      v-for="(btnn, i) in friendsBtns"
                      :key="i"
                      @click="getMethod(btnn.method, j)"
                    >
                      <v-list-item-title>{{ btnn.title }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                </v-menu>
              </v-list-item-action>
            </v-list-item>
          </v-list-group>
        </v-list>
      </v-card>
    </div>
    <div class="text-center">
      <v-btn
        class="mx-2"
        fab
        dark
        large
        color="cyan"
      >
        <v-icon dark>mdi-pencil</v-icon>
      </v-btn>
      <v-btn
        class="mx-2"
        fab
        dark
        large
        color="purple"
      >
        <v-icon dark>mdi-android</v-icon>
      </v-btn>
      <v-btn
        class="mx-2"
        fab
        dark
        large
        color="indigo"
      >
        <v-icon dark>mdi-plus</v-icon>
      </v-btn>
    </div>
  </div>
</template>

<script>
import socket from "../socket";
import '../api/addresslist/index'
import { getGroups, changeNickname } from '../api/addresslist/index';
export default {
  components: {},

  data () {
    return {
      name: "",
      friendsIndex: null,
      itemss: [{
        text: "Announcements",
        icon: "mdi-bell-alert"
      }, {
        text: "Rules",
        icon: "mdi-file-check"
      }, {
        text: "Welcome",
        icon: "mdi-emoticon"
      }],
      channelLabels: [
        {
          action: '~',
          title: '我的团队',
        },
        {
          action: '~',
          title: '我的好友',
        },
      ],
      groups: [{
        title: 'List Item',
        avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
      },],
      friends: [{
        title: 'Breakfast & brunch',
        avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
      }, {
        title: 'New American',
        avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
      }, {
        title: 'Su',
        avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
      },],
      member: 0,
      //备注、删除好友、解散群聊，绑定方法
      //问题，如何添加管理员
      friendsBtns: [{
        title: '备注',
        method: 'showTextField'
      }, {
        title: '删除好友',
        method: 'showTextField'
      }, {
        title: 'Click Me'
      }, {
        title: 'Click Me 2'
      },],
      groupsBtns: [{
        title: '备注'
      }, {
        title: '退出群聊'
      }, {
        title: '解散群聊'
      }, {
        title: 'Click Me 2'
      },],
    };
  },

  updated () {
    this.$store.commit("changeChannel", this.item);
  },

  methods: {
    toggleAC () {
      this.$store.commit("toggleAC");
    },

    method1 () {
      console.assert(1)
    },

    getMethod: function (methodName, index) {
      this[methodName](index);
    },

    showTextField (j) {
      if (this.friendsIndex != null) {
        if (this.friendsIndex != j) {
          this.friends[this.friendsIndex].show = false;
        }
        this.friends[j].show = !this.friends[j].show;
      }
      else {
        this.friends[j].show = !this.friends[j].show;
      }
      this.friendsIndex = j;
      this.name = "";
    },

    changeFriendName (j) {
      if (this.name != "") {
        this.friends[j].title = this.name;
        changeNickname();
      }
      this.friendsIndex = null;
      this.friends[j].show = false;
    }
  },

  mounted () {
    getGroups({
      ACCESS_TOKEN: this.$store.accessToken
    }).then(res => {
      this.$store.commit("channels", res.data.groupsList)
      this.items.items = res.data.groupsList
    });


    this.$store.commit("updateChannels", this.itemss);
    if (this.$store.state.membersComponentMounted) return;
    this.$store.commit("membersMounted");
    console.say("members mount");
    socket.getMembers((data) => {
      console.say("getMem", data);
      this.$store.commit("pushMembers", data);
    });
    socket.addEvent("memberJoin", (data) => {
      console.say("memberJoin", data);
      if (data.id == socket.sid) {
        //isMe
        this.member = this.$store.state.members.length;
        this.$store.commit("updateAvatar", data.user.img || "guest.png");
        this.$store.commit("setNick", data.user.nick);
      }
      this.$store.commit("pushMembers", data);

      this.$store.commit("pushChat", {
        user: {
          nick: "SERVER",
          img: this.$store.state.ownerIcon,
        },
        msg: data.user.nick + " has joined the chat.",
      });
    });

    socket.addEvent("memberLeave", (sid) => {
      this.$store.commit("removeMember", sid);
    });
  }
};
</script>