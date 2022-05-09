<template>
  <div style="width: 100%; height: 100%">
    <v-card
      style="width: 100%; height: 100%;"
      dark
      flat
      tile
      v-show="this.$store.getters.infoId != -1"
    >
      <v-card
        tile
        dark
        style="width: 100%; height: 100%;overflow: auto; overflow-x: hidden"
      >
        <!-- <v-row style="width: 100%; height: 100%;"> -->
        <v-navigation-drawer
          permanent
          style="width: 50%; height:100%; float: left;"
          dark
        >
          <!-- <v-system-bar></v-system-bar> -->
          <!-- <v-list-item>
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
            </v-list-item> -->
          <v-card style="font-size: 50px;">
            {{this.$store.getters.infoName}}
          </v-card>
          <v-textarea
            :disabled="iShow"
            filled
            auto-grow
            rows="4"
            black
            v-model="introduction"
            row-height="40"
            style="margin: 20px 20px 0px 20px;"
          ></v-textarea>
          <!-- <v-btn
            color="blue"
            class="mx-2"
            v-show="!iShow"
            @click="iShowChange"
          >
            修改群公告
          </v-btn>
          <v-btn
            v-show="iShow"
            class="mx-2"
            color="success"
            width="40%"
            @click="method1"
          >
            确认
          </v-btn>
          <v-btn
            v-show="iShow"
            class="mx-2"
            color="error"
            width="40%"
            @click="method1"
          >
            取消
          </v-btn> -->
        </v-navigation-drawer>
        <v-col style="width: 50%; height:100%; float: right">
          <v-list
            style="width: 100%; height:90%;"
            value="false"
          >
            <v-list-group
              prepend-icon="mdi-account-supervisor-circle"
              @click="getMember"
            >
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
                    @keydown.enter="changeName(j + num * (pageF - 1))"
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
                      @click="addSubMaster(j + num * (pageF - 1))"
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
                <v-list-item-action v-show="isSelf(j + num * (pageF - 1)) || isMaster()">
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
                      v-for="(btnn, i) in friendsBtns"
                      :key="i"
                      @click="getMethod(btnn.method, j + num * (pageF - 1))"
                    >
                      <v-list-item-title>{{ btnn.title }}</v-list-item-title>
                    </v-list-item> -->
                      <v-list-item
                        v-show="isSelf(j + num * (pageF - 1))"
                        @click="getMethod(friendsBtns[0].method, j + num * (pageF - 1))"
                      >
                        <v-list-item-title>{{ friendsBtns[0].title }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item
                        v-show="!isSelf(j + num * (pageF - 1)) && isMaster()"
                        @click="getMethod(friendsBtns[1].method, j + num * (pageF - 1))"
                      >
                        <v-list-item-title>{{ friendsBtns[1].title }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item
                        v-show="!isSelf(j + num * (pageF - 1)) && isMaster()"
                        @click="getMethod(friendsBtns[2].method, j + num * (pageF - 1))"
                      >
                        <v-list-item-title>{{ friendsBtns[2].title }}</v-list-item-title>
                      </v-list-item>
                    </v-list>
                  </v-menu>
                </v-list-item-action>
              </v-list-item>
              <v-card style="width: 100%; height: 100%;">
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
              </v-card>
            </v-list-group>
          </v-list>
          <v-card style="width: 100%; height:10%;">
            <v-btn
              v-show="isMaster() && !this.kill"
              @click="showKill"
              color="error"
            >
              解散群聊
            </v-btn>
            <div
              style="width: 100%;"
              v-show="kill"
            >
              <v-btn
                class="mx-2"
                color="blue"
                width="40%"
                @click="subGroup"
              >
                确认解散
              </v-btn>
              <v-btn
                class="mx-2"
                color="blue"
                width="40%"
                @click="showKill"
              >
                取消
              </v-btn>
            </div>
          </v-card>
        </v-col>
        <!-- </v-row> -->
      </v-card>
    </v-card>
    <!-- <v-card
      dark
      style="margin-top: 200px"
      v-show="this.$store.getters.infoId == -1"
    >
      从通讯录“我的团队”点击“详情”可在此页面查看团队信息
    </v-card> -->
  </div>

</template>

<script>
import { getGroupMember, changeGroupNickname, addGroupManager, delGroupManager, delGroup } from '../../../../api/addresslist/index'
export default {
  data () {
    return {
      name: "",
      iShow: true,
      kill: false,
      introduction: "团队介绍",
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
      }, {
        title: '取消管理员',
        method: 'subSubMaster'
      },],
    };
  },

  mounted () {
    // if (this.$store.getters.infoId != -1) {
    //   getGroupMember({
    //     "id": this.$store.getters.infoId,
    //     "ACCESS_TOKEN": null
    //   }).then(res => {
    //     this.friends = res
    //     this.friends.forEach(function (item) {
    //       item["show"] = false;
    //       item["quit"] = false;
    //       if (item.nick && item.nick != "") {
    //         item.name = item.nick
    //       }
    //     });
    //     this.friends = JSON.parse(JSON.stringify(this.friends))
    //     this.allPageF = Math.ceil(this.friends.length / this.num);
    //     // console.log(this.friends)
    //     // this.friends = [{
    //     //   'id': 1,
    //     //   'name': "1",
    //     //   'photo': "",
    //     //   "quit": false,
    //     //   "show": false,
    //     // }]
    //     // console.log(this.friends)
    //   })
    // }
  },

  methods: {
    method1 () {

    },

    getMember () {
      if (this.$store.getters.infoId != -1) {
        getGroupMember({
          "id": this.$store.getters.infoId,
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

    getMethod: function (methodName, index) {
      this[methodName](index);
    },
    downPageF () {
      this.initBtns()
      if (this.pageF != 1 && this.pageF != 0) {
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

    isMaster () {
      return this.$store.getters.userId == this.$store.getters.master
    },

    isSelf (index) {
      return this.$store.getters.userId == this.friends[index].id
    },

    changeName (index) {
      changeGroupNickname({
        "nickname": this.name,
        "group_id": this.$store.getters.infoId,
        "user_id": this.friends[index].id,
        "ACCESS_TOKEN": null,
      }).then(res => {
        console.log(res)
        this.friends[index].name = this.name
        this.friends[this.friendsIndex].show = false;
      })
    },

    addSubMaster (index) {
      addGroupManager({
        "group_id": this.$store.getters.infoId,
        "user_id": this.friends[index].id,
        "ACCESS_TOKEN": null,
      }).then(res => {
        console.log(res)
        this.showQuitField(index)
      })
    },

    subSubMaster (index) {
      delGroupManager({
        "group_id": this.$store.getters.infoId,
        "user_id": this.friends[index].id,
        "ACCESS_TOKEN": null,
      }).then(res => {
        console.log(res)
      })
    },

    showKill () {
      this.kill = !this.kill
    },

    subGroup () {
      delGroup({
        "user_id": this.$store.getters.infoId,
        "ACCESS_TOKEN": null,
        "group_id": this.$store.getters.infoId
      }).then(res => {
        console.log(res)
        this.$store.commit("setInfoId", -1)
        this.$store.commit("setInfoName", "")
        this.$store.commit("setInfoPhoto", "")
        this.$store.commit("setMaster", "")
        this.$store.commit("changeSiderState", 1)
        this.$store.commit("setAbout", -1)
        this.$parent.$parent.$parent.$children[1].getG()
        // this.$parent.$parent.$refs.sider1[0].$el.click()
      })
    },

    iShowChange () {
      this.iShow = true
    }
  }
}
</script>