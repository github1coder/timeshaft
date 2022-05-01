<template>
  <div class="sb-container">
    <v-card
      height="100%"
      dark
      flat
      tile
      class="server-info"
      v-show="this.$store.getters.groupId != -1"
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
                  style="border-radius: 50%; width: 150px;"
                  :src="this.$store.getters.groupPhoto"
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
              <v-list-item-content @dblclick="iShowC">
                <v-list-item-title>
                  {{this.$store.getters.groupName}}
                </v-list-item-title>
                <v-textarea
                  :disabled="iShow"
                  filled
                  auto-grow
                  rows="4"
                  black
                  v-model="introduction"
                  row-height="30"
                ></v-textarea>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-navigation-drawer>
        <v-list>
          <v-list-group prepend-icon="~">
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title>群成员</v-list-item-title>
              </v-list-item-content>
            </template>
            <v-list-item
              v-for="(subItem, j) in friends.slice(num * (pageF - 1), num * pageF)"
              :key="j + num * (pageF - 1)"
              @click="method1"
            >
              <v-list-item-avatar>
                <v-img :src="subItem.photo"></v-img>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title
                  v-show="!subItem.show && !subItem.quit"
                  v-text="subItem.name"
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
    </v-card>
  </div>
</template>

<script>
import { getGroupMember } from '../api/addresslist/index'
export default {
  data () {
    return {
      name: "",
      iShow: true,
      introduction: "这是我的软工团队",
      num: 10,
      pageF: 1,
      allPageF: 1,
      friendsIndex: null,
      friends: [],
      friendsBtns: [{
        title: '修改群昵称',
        method: 'showTextField'
      }, {
        title: '设置管理员',
        method: 'showQuitField'
      },],
    };
  },

  mounted () {
    if (this.$store.getters.groupId != -1) {
      getGroupMember({
        "id": this.$store.getters.groupId,
        "ACCESS_TOKEN": null
      }).then(res => {
        this.friends = res
        this.friends.forEach(function (item) {
          item["show"] = false;
          item["quit"] = false;
          if (item.nick && item.nick != "") {
            item.name = item.nick
          }
        });
        this.friends = JSON.parse(JSON.stringify(this.friends))
        this.allPageF = Math.ceil(this.friends.length / this.num);
        // console.log(this.friends)
        // this.friends = [{
        //   'id': 1,
        //   'name': "1",
        //   'photo': "",
        //   "quit": false,
        //   "show": false,
        // }]
        // console.log(this.friends)
      })
    }
  },

  methods: {
    method1 () {

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
    iShowC () {
      this.iShow = !this.iShow
    },
    showTextField (j) {
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
  }
}
</script>