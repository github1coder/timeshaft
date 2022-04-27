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
        <v-text-field
          clearable
          outlined
          dense
          dark
          hide-details
          label="请输入关键词"
          v-model="text"
          class="input-search mt-3"
          autocomplete="off"
          v-on="on"
          ref="search"
        ></v-text-field>
        <v-list>
          <v-list-group :prepend-icon="channelLabels[0].action">
            <!-- no-action -->
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title v-text="channelLabels[0].title"></v-list-item-title>
              </v-list-item-content>
            </template>
            <v-list-item
              v-for="(subItem, j) in groups"
              :key="subItem.group_name"
              @click="method1"
            >
              <v-list-item-avatar>
                <v-img :src="subItem.group_photo"></v-img>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title
                  v-text="subItem.group_name"
                  v-show="!subItem.show && !subItem.quit"
                  style="text-align: left"
                ></v-list-item-title>
                <v-text-field
                  v-show="subItem.show"
                  v-model="name"
                  @keydown.esc="showGroupTextField(j)"
                  @keydown.enter="changeGroupName(j)"
                  clearable
                ></v-text-field>
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
                      @click="getMethod(btnn.method, j)"
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
              :key="subItem.friend_name"
              @click="method1"
            >
              <v-list-item-avatar>
                <v-img :src="subItem.friend_photo"></v-img>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title
                  v-show="!subItem.show && !subItem.quit"
                  v-text="subItem.friend_name"
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
                <v-card v-show="subItem.quit">
                  <v-btn
                    class="ma-2"
                    tile
                    outlined
                    color="success"
                  >
                    确定
                  </v-btn>
                  <v-btn
                    class="ma-2"
                    tile
                    outlined
                    color="success"
                  >
                    取消
                  </v-btn>
                </v-card>
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
import { getGroups, changeNickname, getFriends, changeGroupNickname } from '../api/addresslist/index';
export default {
  components: {},

  data () {
    return {
      name: "",
      friendsIndex: null,
      groupsIndex: null,
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
        group_id: 1,
        group_name: 'List Item',
        group_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
        quit: false,
      }, {
        group_id: 1,
        group_name: 'List Item',
        group_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
        quit: false,
      },],

      friends: [{
        firend_id: 1,
        friend_name: 'Breakfast & brunch',
        friend_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
        quit: false,
      }, {
        firend_id: 1,
        friend_name: 'Breakfast & brunch',
        friend_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
        quit: false,
      },],
      member: 0,
      //备注、删除好友、解散群聊，绑定方法
      //问题，如何添加管理员
      friendsBtns: [{
        title: '备注',
        method: 'showTextField'
      }, {
        title: '删除好友',
        method: 'showQuitField'
      }, {
        title: 'Click Me'
      }, {
        title: 'Click Me 2'
      },],
      groupsBtns: [{
        title: '备注',
        method: 'showGroupTextField'
      }, {
        title: '退出群聊',
        method: 'showGroupQuitField'
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

    showQuitField (j) {
      if (this.firendsIndex != null) {
        if (this.firendsIndex != j) {
          this.friends[this.groupsIndex].quit = false;
        }
        this.firends[j].quit = !this.firends[j].quit;
      }
      else {
        this.firends[j].quit = !this.firends[j].quit;
      }
      this.firendsIndex = j;
      this.name = "";
    },

    showGroupTextField (j) {
      if (this.groupsIndex != null) {
        if (this.groupsIndex != j) {
          this.groups[this.groupsIndex].show = false;
        }
        this.groups[j].show = !this.groups[j].show;
      }
      else {
        this.groups[j].show = !this.groups[j].show;
      }
      this.groupsIndex = j;
      this.name = "";
    },

    showGroupQuitField (j) {
      if (this.groupsIndex != null) {
        if (this.groupsIndex != j) {
          this.groups[this.groupsIndex].show = false;
        }
        this.groups[j].quit = !this.groups[j].quit;
      }
      else {
        this.groups[j].quit = !this.groups[j].quit;
      }
      this.groupsIndex = j;
      this.name = "";
    },

    changeFriendName (j) {
      if (this.name != "") {
        this.friends[j].friend_name = this.name;
        changeNickname({
          "ACCESS_TOKEN": this.$store.accessToken,
          "friend_id": this.friends[j].friend_id,
          "friend_nickname": this.name
        });
      }
      this.friendsIndex = null;
      this.friends[j].show = false;
    },

    changeGroupName (j) {
      if (this.name != "") {
        this.friends[j].friend_name = this.name;
        changeGroupNickname({
          "ACCESS_TOKEN": this.$store.accessToken,
          "group_id": this.friends[j].friend_id,
          "group_nickname": this.name
        });
      }
      this.friendsIndex = null;
      this.friends[j].show = false;
    }
  },

  mounted () {
    getGroups({
      "ACCESS_TOKEN": this.$store.accessToken
    }).then(res => {
      this.$store.commit("channels", res.groupsList)
      this.groups = res.groupsList.array.forEach(element => {
        element['show'] = false
        element['quit'] = false
      });
    });
    getFriends({
      "ACCESS_TOKEN": this.$store.accessToken
    }).then(res => {
      this.$store.commit("channels", res.friendsList)
      this.groups = res.friends.array.forEach(element => {
        element['show'] = false
        element['quit'] = false
      });
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