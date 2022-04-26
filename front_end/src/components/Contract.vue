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
          <v-list-group
            v-for="item in items"
            :key="item.title"
            v-model="item.active"
            :prepend-icon="item.action"
          >
            <!-- no-action -->
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title v-text="item.title"></v-list-item-title>
              </v-list-item-content>
            </template>
            <v-list-item
              v-for="subItem in item.items"
              :key="subItem.title"
              @click="method1"
            >
              <v-list-item-avatar>
                <v-img :src="subItem.avatar"></v-img>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title v-text="subItem.title"></v-list-item-title>
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
                      v-for="(btnn, i) in btns"
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
        </v-list>
      </v-card>
    </div>
  </div>
</template>

<script>
import socket from "../socket";
export default {
  components: {},

  data () {
    return {
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
      items: [{
        action: '1',
        title: '我的团队',
        items: [{
          title: 'List Item',
          avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        },],
      }, {
        action: '2',
        title: '我的好友',
        active: true,
        items: [{
          title: 'Breakfast & brunch',
          avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        }, {
          title: 'New American',
          avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        }, {
          title: 'Su',
          avatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        },],
      },],
      member: 0,
      btns: [{
        title: 'Click Me'
      }, {
        title: 'Click Me'
      }, {
        title: 'Click Me'
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
    }
  },

  mounted () {
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