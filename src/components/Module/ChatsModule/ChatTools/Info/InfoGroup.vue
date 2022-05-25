<template>
  <div style="width: 100%; height: 100%">
    <v-card
      style="width: 100%; height: 100%;"
      flat
      tile
    >
      <v-card
        tile
        style="width: 100%; overflow: auto; overflow-x: hidden"
        class="mem-info"
      >
        <!-- <v-row style="width: 100%; height: 100%;"> -->
        <v-navigation-drawer
          permanent
          style="width: 100%;"
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
            {{this.nameG}}
          </v-card>
          <v-textarea
            :disabled="iShow"
            filled
            auto-grow
            rows="4"
            black
            v-model="notice"
            row-height="40"
            style="margin: 20px 20px 0px 20px;"
          ></v-textarea>
          <!-- <v-btn
            color="blue"
            class="mx-2"
            v-show="iShow && this.$store.getters.userId == this.master"
            @click="iShowFalse"
          >
            修改群公告
          </v-btn> -->
          <v-btn
            v-show="!iShow"
            class="mx-2"
            rounded
            color="green lighten-3"
            width="40%"
            @click="changeNotice"
          >
            确认
          </v-btn>
          <v-btn
            v-show="!iShow"
            class="mx-2"
            rounded
            color="red lighten-3"
            width="40%"
            @click="iShowTrue"
          >
            取消
          </v-btn>
        </v-navigation-drawer>
        <v-col style="width: 100%;">
          <v-list style="width: 100%;">
            <v-list-group
              prepend-icon="mdi-account-supervisor-circle"
              @click="getMember"
              :value="memberShow"
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
                        v-show="!isSelf(j + num * (pageF - 1)) && isMaster() && !isManager(j + num * (pageF - 1))"
                        @click="getMethod(friendsBtns[1].method, j + num * (pageF - 1))"
                      >
                        <v-list-item-title>{{ friendsBtns[1].title }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item
                        v-show="!isSelf(j + num * (pageF - 1)) && isMaster() && isManager(j + num * (pageF - 1))"
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
          <div
            v-for="(item, j) in friends"
            :key=j
            style="width:30%; float: left; margin-left: 3%; background-color: green; border-radius: 10%"
          >
            <div>
              <v-img
                :src="item.photo"
                style="height: 100%; width: 100%;"
              ></v-img>
              <span>{{item.name.slice(0,5)}}</span>
            </div>
          </div>
          <!-- <v-card style="width: 100%; height:10%;">
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
          </v-card> -->
        </v-col>
        <!-- </v-row> -->
      </v-card>
    </v-card>
  </div>

</template>

<script>
import { getInfoMsg } from "../../../../../api/addresslist/index"
import { getGroupMember, changeGroupNickname, addGroupManager, delGroupManager, delGroup, updateGroup } from '../../../../../api/addresslist/index'
export default {
  props: ["id"],
  data () {
    return {
      photo: "",
      nameG: "",//群聊名称
      email: "",
      master: -1,
      memberShow: false,
      name: "",//群昵称
      iShow: true,
      kill: false,
      notice: "",
      num: 10,
      pageF: 1,
      allPageF: 1,
      friendsIndex: -1,
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
    this.initInfo()
  },

  methods: {
    method1 () {

    },

    init (photo, nameG, master, notice) {
      this.photo = photo
      this.nameG = nameG
      this.master = master
      this.notice = notice
    },

    initInfo () {
      const that = this
      getInfoMsg({
        "info_id": this.id,
        "type": "group"
      }).then(res => {
        if (!res.error) {
          that.init(
            res.photo,
            res.name,
            res.master,
            res.notice)
          this.getM()
        }
        console.log("获取好友信息成功:" + this.$store.state.currentChannelId)
      })
    },

    changeNotice () {
      const here = this
      console.log(this.notice)
      updateGroup({
        "id": this.$parent.$parent.id,
        "notice": this.notice
      }).then(res => {
        console.log("修改群公告成功")
        console.log(res)
        if (!res) {
          const that = here.$parent.$parent.$parent.$refs.memberList.groups
          for (this.i = 0; this.i < that.length; this.i++) {
            if (that[this.i].group_id == here.$parent.id) {
              that[this.i].notice = here.notice
              break
            }
          }
          here.iShowTrue()
        }
      })

    },

    getMember () {
      if (!this.memberShow) {
        this.getM()
      }
    },

    getM () {
      if (this.$parent.$parent.id != -1) {
        const that = this
        getGroupMember({
          "id": this.$parent.$parent.id,
        }).then(res => {
          this.friends = res
          this.friends.forEach(function (item) {
            item["show"] = false;
            item["quit"] = false;
            if (item.nick && item.nick != "") {
              item.name = item.nick
            }
            if (item.id == that.master) {
              item.name = item.name + "（群主）"
            }
            else if (item.type == "manager") {
              item.nick = item.name
              item.name = item.name + "（管理员）"
            }
          });
          this.friends = JSON.parse(JSON.stringify(this.friends))
          this.allPageF = Math.ceil(this.friends.length / this.num);
          this.memberShow = !this.memberShow
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
    iShowTrue () {
      this.iShow = true
    },
    showTextField (j) {
      if (this.friendsIndex != -1) {
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
      if (this.friendsIndex != -1) {
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
      return this.$store.getters.userId == this.master
    },

    isSelf (index) {
      return this.friends[index].id == this.$store.getters.userId
    },

    isManager (index) {
      return this.friends[index].type == "manager"
    },

    //以下三个方法，由于只有isself成立的时候才会调用，因此this.friends[index].id=this.friends[index].id

    changeName (index) {
      changeGroupNickname({
        "nickname": this.name,
        "group_id": this.$store.getters.infoId,
      }).then(res => {
        console.log(res)
        this.friends[index].name = this.name
        this.friends[this.friendsIndex].show = false;
      })
    },

    addSubMaster (index) {
      const that = this
      addGroupManager({
        "group_id": this.$parent.$parent.id,
        "id": that.friends[index].id
      }).then(res => {
        console.log(res)
        this.showQuitField(index)
        if (!res) {
          that.friends[index].name = that.friends[index].name + "（管理员）"
          that.friends[index].type = "manager"
        }
      })
    },

    subSubMaster (index) {
      const that = this
      delGroupManager({
        "group_id": this.$parent.$parent.id,
        "id": that.friends[index].id
      }).then(res => {
        console.log(res)
        if (!res) {
          that.friends[index].name = that.friends[index].nick
          that.friends[index].type = "normal"
        }
      })
    },

    showKill () {
      this.kill = !this.kill
    },

    subGroup () {
      const here = this.$parent.$parent
      delGroup({
        "group_id": this.$parent.$parent.id
      }).then(res => {
        console.log(res)
        this.$store.commit("changeSiderState", 1)
        const that = here.$parent.$refs.memberList.groups
        for (this.i = 0; this.i < that.length; this.i++) {
          if (that[this.i].group_id == here.id) {
            that.splice(this.i, 1)
            break
          }
        }
        this.about = -1
      })
    },

    iShowFalse () {
      this.iShow = false
    }
  }
}
</script>