<template>
  <div
    class="sb-container"
    style="overflow: auto; overflow-x: hidden; height: 100%; width: 100%;"
  >
    <v-card
      height="100%"
      dark
      flat
      tile
      class="server-info"
    >
      <v-list>
        <v-list-group
          :prepend-icon="channelLabels[0].action"
          @click="initBtns"
          value=true
        >
          <!-- no-action -->
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title v-text="channelLabels[0].title"></v-list-item-title>
            </v-list-item-content>
          </template>
          <v-list-item
            v-for="(subItem, j) in groups.slice(num * (pageG - 1), num * pageG)"
            :key="j"
            @click="infoG(j + num * (pageG - 1))"
            @contextmenu.prevent="method1"
          >
            <v-list-item-avatar color="blue">
              <span class="white--text text-h5">{{ subItem.group_name[0] }}</span>
              <!-- <v-img :src="subItem.group_photo"></v-img> -->
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title
                v-show="!subItem.show && !subItem.quit"
                v-text="subItem.group_name"
                style="text-align: left"
              ></v-list-item-title>
              <v-text-field
                v-show="subItem.show"
                v-model="name"
                @keydown.esc="showGroupTextField(j + num * (pageG - 1))"
                @keydown.enter="changeGroupName(j + num * (pageG - 1))"
                clearable
              >

              </v-text-field>
              <div
                class="text-center"
                v-show="subItem.quit"
              >
                <v-btn
                  class="mx-2"
                  color="error"
                  fab
                  x-small
                  @click="killGroup(j + num * (pageG - 1))"
                >
                  确定
                </v-btn>
                <v-btn
                  class="mx-2"
                  color="success"
                  fab
                  x-small
                  @click="showGroupQuitField(j + num * (pageG - 1))"
                >
                  取消
                </v-btn>
              </div>
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
                  <!-- <v-list-item
                    v-for="(btnn, i) in groupsBtns"
                    :key="i"
                    @click="getMethod(btnn.method, j + num * (pageG - 1))"
                  >
                    <v-list-item-title>{{ btnn.title }}</v-list-item-title>
                  </v-list-item> -->
                  <!-- <v-list-item @click="getMethod(groupsBtns[0].method, j + num * (pageG - 1))">
                    <v-list-item-title>{{ groupsBtns[0].title }}</v-list-item-title>
                  </v-list-item> -->
                  <v-list-item
                    v-if="isMaster(j + num * (pageG - 1))"
                    @click="getMethod(groupsBtns[1].method, j + num * (pageG - 1))"
                  >
                    <v-list-item-title>{{ groupsBtns[1].title }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-list-item-action>
          </v-list-item>
          <v-card>
            <v-row>
              <v-btn
                width="33%"
                @click="downPageG"
              >
                <v-icon>mdi-chevron-left</v-icon>
              </v-btn>
              <v-btn
                width="33%"
                disabled
              >
                {{pageG}}/{{allPageG}}
              </v-btn>
              <v-btn
                width="33%"
                @click="upPageG"
              >
                <v-icon>mdi-chevron-right</v-icon>
              </v-btn>
            </v-row>
          </v-card>
        </v-list-group>
      </v-list>
      <v-list>
        <v-list-group
          :prepend-icon="channelLabels[1].action"
          @click="initBtns"
          value=true
        >
          <!-- no-action -->
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title v-text="channelLabels[1].title">
              </v-list-item-title>
            </v-list-item-content>
          </template>
          <v-list-item
            v-for="(subItem, j) in friends.slice(num * (pageF - 1), num * pageF)"
            :key="j + num * (pageF - 1)"
            @click="infoF(j + num * (pageF - 1))"
            @contextmenu.prevent="method1"
          >
            <v-list-item-avatar>
              <v-img :src="subItem.friend_photo"></v-img>
            </v-list-item-avatar>
            <v-list-item-content>
              <v-list-item-title
                v-show="!subItem.show && !subItem.quit"
                v-text="subItem.friend_nick"
                style="text-align: left"
              ></v-list-item-title>
              <v-text-field
                v-show="subItem.show"
                v-model="name"
                @keydown.esc="showTextField(j + num * (pageF - 1))"
                @keydown.enter="changeFriendName(j + num * (pageF - 1))"
                clearable
              >
                <!-- 确定删除好友 -->
              </v-text-field>
              <div
                class="text-center"
                v-show="subItem.quit"
              >
                <v-btn
                  class="mx-2"
                  color="error"
                  fab
                  x-small
                  @click="killFriend(j + num * (pageF - 1))"
                >
                  确定
                </v-btn>
                <v-btn
                  class="mx-2"
                  color="success"
                  fab
                  x-small
                  @click="showQuitField(j + num * (pageF - 1))"
                >
                  取消
                </v-btn>
              </div>
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
                    @click="getMethod(btnn.method, j + num * (pageF - 1))"
                  >
                    <v-list-item-title>{{ btnn.title }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-list-item-action>
          </v-list-item>
          <v-card>
            <v-row>
              <v-btn
                width="33%"
                @click="downPageF"
              >
                <v-icon>mdi-chevron-left</v-icon>
              </v-btn>
              <v-btn
                width="33%"
                disabled
              >
                {{pageF}}/{{allPageF}}
              </v-btn>
              <v-btn
                width="33%"
                @click="upPageF"
              >
                <v-icon>mdi-chevron-right</v-icon>
              </v-btn>
            </v-row>
          </v-card>
        </v-list-group>
      </v-list>
    </v-card>
  </div>
</template>

<script>
import '../../../api/addresslist/index'
import { getGroups, changeNickname, getFriends, changeGroupNickname, delFriend, quitGroup } from '../../../api/addresslist/index';
export default {
  components: {},

  data () {
    return {
      name: "",
      textG: "",
      groupsIndex: null,
      friendsIndex: null,
      groupUnfolder: true,
      friendUnfolder: true,
      num: 10,
      pageF: 1,
      allPageF: 1,
      pageG: 1,
      allPageG: 1,
      channelLabels: [
        {
          action: 'mdi-account-multiple',
          title: '我的团队',
        },
        {
          action: 'mdi-message',
          title: '我的好友',
        },
      ],
      groups: [],
      friends: [],
      member: 0,
      //备注、删除好友、解散群聊，绑定方法
      //问题，如何添加管理员
      friendsBtns: [{
        title: '备注',
        method: 'showTextField'
      }, {
        title: '删除好友',
        method: 'showQuitField'
      },],
      groupsBtns: [{
        title: '详情',
        method: 'aboutG'
      }, {
        title: '退出群聊',
        method: 'showGroupQuitField'
      },],
    };
  },

  updated () {
  },

  methods: {
    toggleAC () {
      this.$store.commit("toggleAC");
    },

    isMaster (index) {
      return this.$store.getters.userId != this.groups[index].master_id
    },

    method1 () {
      console.log(1)
    },

    aboutG (index) {
      this.$store.commit("setInfoId", this.groups[index].group_id)
      this.$store.commit("setInfoName", this.groups[index].group_name)
      this.$store.commit("setInfoPhoto", this.groups[index].group_photo)
      this.$store.commit("setMaster", this.groups[index].master_id)
      // this.$store.commit("changeSiderState", 4)
      this.$store.commit("setAbout", 1)
      // this.$parent.$parent.$refs.sider4[0].$el.click()
      // this.$router.push({
      //   path: "/home"
      // })
    },

    initBtns () {
      if (this.groupsIndex != null) {
        this.groups[this.groupsIndex].show = false;
        this.groups[this.groupsIndex].quit = false;
      }
      if (this.friendsIndex != null) {
        this.friends[this.friendsIndex].show = false;
        this.friends[this.friendsIndex].quit = false;
      }
    },

    getMethod: function (methodName, index) {
      this[methodName](index);
    },

    showTextField (j) {
      if (this.groupsIndex != null) {
        this.groups[this.groupsIndex].show = false;
        this.groups[this.groupsIndex].quit = false;
      }
      if (this.friendsIndex != null) {
        if (this.friendsIndex != j) {
          this.friends[this.friendsIndex].show = false;
          this.friends[this.friendsIndex].quit = false;
        }
        this.friends[j].show = !this.friends[j].show;
        this.friends[j].quit = false;
      }
      else {
        this.friends[j].show = !this.friends[j].show;
        this.friends[j].quit = false;
      }
      this.friendsIndex = j;
      this.name = "";
    },

    showQuitField (j) {
      if (this.groupsIndex != null) {
        this.groups[this.groupsIndex].show = false;
        this.groups[this.groupsIndex].quit = false;
      }
      if (this.friendsIndex != null) {
        if (this.friendsIndex != j) {
          this.friends[this.friendsIndex].quit = false;
          this.friends[this.friendsIndex].show = false;
        }
        this.friends[j].quit = !this.friends[j].quit;
        this.friends[j].show = false;
      }
      else {
        this.friends[j].quit = !this.friends[j].quit;
        this.friends[j].show = false;
      }
      this.friendsIndex = j;
      this.name = "";
    },

    showGroupTextField (j) {
      if (this.friendsIndex != null) {
        this.friends[this.friendsIndex].show = false;
        this.friends[this.friendsIndex].quit = false;
      }
      if (this.groupsIndex != null) {
        if (this.groupsIndex != j) {
          this.groups[this.groupsIndex].show = false;
          this.groups[this.groupsIndex].quit = false;
        }
        this.groups[j].show = !this.groups[j].show;
        this.groups[j].quit = false;
      }
      else {
        this.groups[j].show = !this.groups[j].show;
        this.groups[j].quit = false;
      }
      this.groupsIndex = j;
      this.name = "";
    },

    showGroupQuitField (j) {
      if (this.friendsIndex != null) {
        this.friends[this.friendsIndex].show = false;
        this.friends[this.friendsIndex].quit = false;
      }
      if (this.groupsIndex != null) {
        if (this.groupsIndex != j) {
          this.groups[this.groupsIndex].quit = false;
          this.groups[this.groupsIndex].show = false;
        }
        this.groups[j].quit = !this.groups[j].quit;
        this.groups[j].show = false;
      }
      else {
        this.groups[j].quit = !this.groups[j].quit;
        this.groups[j].show = false;
      }
      this.groupsIndex = j;
      this.name = "";
    },

    downPageF () {
      this.initBtns()
      if (this.pageF != 1) {
        this.pageF -= 1
      }
    },
    upPageF () {
      this.initBtns()
      if (this.pageF != this.allPageF) {
        this.pageF += 1
      }
    },
    downPageG () {
      this.initBtns()
      if (this.pageG != 1 && this.pageG != 0) {
        this.pageG -= 1
      }
    },
    upPageG () {
      this.initBtns()
      if (this.pageG != this.allPageG) {
        this.pageG += 1
      }
    },

    changeFriendName (j) {
      if (this.name && this.name != "") {
        this.friends[j].friend_nick = this.name;
        changeNickname({
          "user_id": this.$store.getters.userId,
          "ACCESS_TOKEN": null,
          "friend_id": parseInt(this.friends[j].friend_id),
          "friend_nickname": this.name
        });
      }
      this.friendsIndex = null;
      this.friends[j].show = false;
    },

    changeGroupName (j) {
      if (this.name && this.name != "") {
        this.groups[j].group_name = this.name;
        changeGroupNickname({
          "user_id": this.$store.getters.userId,
          "ACCESS_TOKEN": null,
          "group_id": parseInt(this.groups[j].group_id),
          "group_nickname": this.name
        });
      }
      this.groupsIndex = null;
      this.groups[j].show = false;
    },

    killFriend (j) {
      delFriend({
        "user_id": this.$store.getters.userId,
        "ACCESS_TOKEN": null,
        "friend_id": parseInt(this.friends[j].friend_id)
      });
      this.friends.splice(j, 1);
      this.friendsIndex = null;
    },

    killGroup (j) {
      quitGroup({
        "user_id": this.$store.getters.userId,
        "ACCESS_TOKEN": null,
        "group_id": parseInt(this.groups[j].group_id)
      });
      this.groups.splice(j, 1);
      this.groupsIndex = null;
    },

    infoF (index) {
      console.log("info:" + index.toString())
      this.$store.commit("setInfoNick", null)
      if (this.friends[index].friend_name !== this.friends[index].friend_nick) {
        this.$store.commit("setInfoNick", this.friends[index].friend_nick)
      }
      this.$store.commit("setInfoId", this.friends[index].chat_id)
      console.log("chat_id:" + this.$store.getters.infoId)
      this.$store.commit("setInfoName", this.friends[index].friend_name)
      this.$store.commit("setInfoPhoto", this.friends[index].friend_photo)
      this.$store.commit("setInfoEmail", this.friends[index].mail)
      this.$store.commit("setAbout", 0)
    },

    infoG (index) {
      this.$store.commit("setInfoId", this.groups[index].group_id)
      this.$store.commit("setInfoName", this.groups[index].group_name)
      this.$store.commit("setInfoPhoto", this.groups[index].group_photo)
      this.$store.commit("setMaster", this.groups[index].master_id)
      // this.$store.commit("changeSiderState", 4)
      this.$store.commit("setAbout", 1)
      this.$parent.$refs.infoTool.$refs.infoG.memberShow = false
      this.$parent.$refs.infoTool.$refs.infoG.iShowTrue()
      // this.$parent.$parent.$refs.sider4[0].$el.click()
      // this.$router.push({
      //   path: "/home"
      // })
    },


    //解散、创建群聊后调用
    getG () {
      getGroups({
        "user_id": this.$store.getters.userId,
        "ACCESS_TOKEN": null
      }).then(res => {
        // this.$store.commit("channels", res)
        this.groups = res
        this.groups.forEach(function (item) {
          item["show"] = false;
          item["quit"] = false;
        });
        this.groups = JSON.parse(JSON.stringify(this.groups))
        this.allPageG = Math.ceil(this.groups.length / this.num);
        if (this.allPageG == 0) {
          this.pageG = 0;
        }
        else {
          this.pageG = 1;
        }
      });
    },
  },

  mounted () {
    if (this.$store.getters.userId != -1) {
      console.log(this.$store.getters.userId)
      getGroups({
        "user_id": this.$store.getters.userId,
        "ACCESS_TOKEN": null
      }).then(res => {
        // this.$store.commit("channels", res)
        this.groups = res
        this.groups.forEach(function (item) {
          item["show"] = false;
          item["quit"] = false;
        });
        this.groups = JSON.parse(JSON.stringify(this.groups))
        this.allPageG = Math.ceil(this.groups.length / this.num);
        if (this.allPageG == 0) {
          this.pageG = 0;
        }
        // this.groups = [{
        //   group_id: 1,
        //   group_name: 'Breakfast & brunch',
        //   group_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        //   show: false,
        //   quit: false,
        // }, {
        //   group_id: 2,
        //   group_name: 'List Item',
        //   group_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        //   show: false,
        //   quit: false,
        // },]
      });

      getFriends({
        "user_id": this.$store.getters.userId,
        "ACCESS_TOKEN": null
      }).then(res => {
        // this.$store.commit("channels", res)
        this.friends = res
        this.friends.forEach(function (item) {
          item["show"] = false;
          item["quit"] = false;
          if (item.friend_nick == "") {
            item.friend_nick = item.friend_name
          }
        });
        this.friends = JSON.parse(JSON.stringify(this.friends))
        this.allPageF = Math.ceil(this.friends.length / this.num);
        if (this.allPageF == 0) {
          this.pageF = 0;
        }
        else {
          this.pageF = 1;
        }
        // console.log(this.friends)
        // this.friends = [{
        //   'friend_id': 1,
        //   'friend_name': "1",
        //   'friend_photo': "",
        //   "quit": false,
        //   "show": false,
        // }]
        // console.log(this.friends)
      });
    }
  }
};
</script>