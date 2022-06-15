<template>
  <div style="width: 100%; height: 100%">
    <v-card
      style="width: 100%; height: 100%;"
      flat
      tile
    >
      <v-card
        tile
        style="width: 100%; height: 100%;overflow: auto; overflow-x: hidden"
      >
        <v-card style="width: 50%; height: 100%; float: left;">
          <v-navigation-drawer
            permanent
            style="width: 100%; height:90%;"
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
            <div style="font-size: 50px;">
              {{this.nameG}}
            </div>
            <v-btn
              color="blue-grey lighten-4"
              rounded
              width="50%"
              style="margin: auto;font-size: 15px; font-weight: bold"
              @click="updateState()"
              :loading="isState"
              v-show="isMaster()"
            >
              {{stateText}}
            </v-btn>
            <v-divider style="margin-top: 5%;"></v-divider>
            <div style="font-size: 25px; margin-top: 5%;">
              群公告
            </div>
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
            <v-btn
              class="mx-2"
              color="deep-orange lighten-4"
              rounded
              style="font-size: 15px; font-weight: bold;"
              v-show="iShow && this.$store.getters.userId == this.master"
              @click="iShowFalse"
            >
              修改群公告
            </v-btn>
            <v-btn
              v-show="!iShow"
              class="mx-2"
              rounded
              color="green lighten-3"
              width="40%"
              style="font-size: 15px; font-weight: bold;"
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
              style="font-size: 15px; font-weight: bold;"
              @click="iShowTrue"
            >
              取消
            </v-btn>
          </v-navigation-drawer>
          <v-card style="width: 100%; height:10%;">
            <v-btn
              v-show="isMaster() && !this.kill"
              @click="showKill"
              rounded
              style="margin-top: 20px;font-weight: bold"
              color="error"
            >
              解散群聊
            </v-btn>
            <div
              style="width: 100%;"
              v-show="kill"
            >
              <v-btn
                class="mx-2 white--text"
                color="red lighten-3"
                rounded
                width="40%"
                style="margin-top: 20px;font-weight: bold;"
                @click="subGroup"
              >
                确认解散
              </v-btn>
              <v-btn
                class="mx-2 white--text"
                color="blue"
                rounded
                width="40%"
                style="margin-top: 20px;font-weight: bold"
                @click="showKill"
              >
                取消
              </v-btn>
            </div>
          </v-card>
        </v-card>

        <v-card style="width: 50%; height:100%; float: right;">
          <v-list style="width: 100%; height:100%; overflow: auto; overflow-x: hidden;">
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
                v-for="(subItem, j) in mems.slice(num * (pageMem - 1), num * pageMem)"
                :key="j + num * (pageMem - 1)"
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
                    @keydown.esc="showTextField(j + num * (pageMem - 1))"
                    @keydown.enter="changeName(j + num * (pageMem - 1))"
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
                      @click="addSubMaster(j + num * (pageMem - 1))"
                    >
                      确定
                    </v-btn>
                    <v-btn
                      class="mx-2"
                      color="success"
                      fab
                      x-small
                      @click="showQuitField(j + num * (pageMem - 1))"
                    >
                      取消
                    </v-btn>
                  </div>
                </v-list-item-content>
                <!-- 后面的省略号 -->
                <v-list-item-action v-show="isSelf(j + num * (pageMem - 1)) || isMaster() || notFriend(j + num * (pageMem - 1))">
                  <v-menu right>
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn
                        icon
                        v-bind="attrs"
                        v-on="on"
                      >
                        <v-icon>mdi-dots-vertical</v-icon>
                      </v-btn>
                    </template>
                    <v-list hover>
                      <!-- <v-list-item
                      v-for="(btnn, i) in memsBtns"
                      :key="i"
                      @click="getMethod(btnn.method, j + num * (pageMem - 1))"
                    >
                      <v-list-item-title>{{ btnn.title }}</v-list-item-title>
                    </v-list-item> -->
                      <v-list-item
                        v-show="isSelf(j + num * (pageMem - 1))"
                        @click="getMethod(memsBtns[0].method, j + num * (pageMem - 1))"
                      >
                        <v-list-item-title>{{ memsBtns[0].title }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item
                        v-show="!isSelf(j + num * (pageMem - 1)) && isMaster() && !isManager(j + num * (pageMem - 1))"
                        @click="getMethod(memsBtns[1].method, j + num * (pageMem - 1))"
                      >
                        <v-list-item-title>{{ memsBtns[1].title }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item
                        v-show="!isSelf(j + num * (pageMem - 1)) && isMaster() && isManager(j + num * (pageMem - 1))"
                        @click="getMethod(memsBtns[2].method, j + num * (pageMem - 1))"
                      >
                        <v-list-item-title>{{ memsBtns[2].title }}</v-list-item-title>
                      </v-list-item>
                      <v-list-item
                        v-show="!isSelf(j + num * (pageMem - 1)) && notFriend(j + num * (pageMem - 1))"
                        @click="getMethod(memsBtns[3].method, j + num * (pageMem - 1))"
                      >
                        <v-list-item-title>{{ memsBtns[3].title }}</v-list-item-title>
                      </v-list-item>
                    </v-list>
                  </v-menu>
                </v-list-item-action>
              </v-list-item>
              <v-card style="width: 100%; height: 100%;">
                <v-btn
                  width="33%"
                  @click="downPageMem"
                  depressed
                >
                  <v-icon>mdi-chevron-left</v-icon>
                </v-btn>
                <v-btn
                  width="33%"
                  disabled
                >
                  {{pageMem}}/{{allPageMem}}
                </v-btn>
                <v-btn
                  width="33%"
                  @click="upPageMem"
                  depressed
                >
                  <v-icon>mdi-chevron-right</v-icon>
                </v-btn>
              </v-card>
            </v-list-group>
            <v-list-group
              prepend-icon="mdi-account-multiple-plus"
              @click="getFriend"
              :value="friendShow"
            >
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>邀请好友</v-list-item-title>
                </v-list-item-content>
              </template>
              <v-list-item
                v-for="(subItem, j) in friendAns.slice(num * (pageF - 1), num * pageF)"
                :key="j + num * (pageF - 1)"
                @click="method1"
                two-line
              >
                <v-list-item-avatar>
                  <v-img :src="subItem.friend_photo"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title
                    v-text="subItem.friend_name"
                    style="text-align: left"
                  ></v-list-item-title>
                </v-list-item-content>
                <!-- 后面的省略号 -->
                <v-list-item-action>
                  <v-btn
                    small
                    rounded
                    color="brown lighten-5"
                    @click="newApplyI(j + num * (pageF - 1))"
                    :disabled="subItem.show"
                  >
                    <v-icon>mdi-plus</v-icon>
                  </v-btn>
                </v-list-item-action>
              </v-list-item>
              <v-card style="width: 100%; height: 100%;">
                <v-btn
                  width="33%"
                  @click="downPageF"
                  depressed
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
                  depressed
                >
                  <v-icon>mdi-chevron-right</v-icon>
                </v-btn>
              </v-card>
            </v-list-group>
          </v-list>
        </v-card>
        <!-- </v-row> -->
      </v-card>
    </v-card>
  </div>

</template>

<script>
import { apply, getGroupMember, changeGroupNickname, addGroupManager, delGroupManager, delGroup, updateGroup, getFNotInG } from '../../../../api/addresslist/index'
export default {
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
      foreNotice: "",
      num: 10,
      pageMem: 1,
      allPageMem: 1,
      memsIndex: -1,
      mems: [],
      memsBtns: [{
        title: '修改群昵称',
        method: 'showTextField'
      }, {
        title: '设置管理员',
        method: 'showQuitField'
      }, {
        title: '取消管理员',
        method: 'subSubMaster'
      }, {
        title: '添加好友',
        method: 'addFriend'
      }],
      stateText: "公开团队信息",
      isState: false,
      friendAns: [],
      pageF: 1,
      allPageF: 1,
      friendShow: false,
    };
  },

  mounted () {
  },

  methods: {

    newApplyI (index) {
      apply({
        "type": "group",
        "action": "new",
        "id": this.$parent.$parent.id,
        "memId": this.friendAns[index].friend_id,
        "invite": this.$store.state.userId,
      }
      ).then(res => {
        res
        this.friendAns[index].show = true
      })
    },

    getFriend () {
      const that = this
      if (this.$parent.$parent.id != -1 && !this.friendShow) {
        getFNotInG({
          "id": this.$parent.$parent.id,
        }).then(res => {
          that.friendAns = res
          this.friendAns.forEach(function (item) {
            item["show"] = false;
          });
          this.friendAns = JSON.parse(JSON.stringify(this.friendAns))
          that.allPageF = Math.ceil(that.friendAns.length / that.num);
          if (this.allPageF == 0) {
            this.pageF = 0
          }
          that.friendShow = !that.friendShow
        })
      }
    },

    method1 () {

    },

    initBtns () {
      if (this.memsIndex != -1) {
        this.mems[this.memsIndex].show = false;
        this.mems[this.memsIndex].quit = false;
      }
    },

    addFriend (index) {
      apply({
        "type": "friend",
        "action": "new",
        "id": this.mems[index].id,
        "memId": -1,
        "invite": 0,
      }).then(res => {
        res
      })
    },


    updateState () {
      const that = this.$parent.$parent.$parent.$refs.memberList
      const here = this

      updateGroup({
        "id": parseInt(this.$parent.$parent.id),
        "name": this.nameG,
        "state": !that.groups[that.indexG].state ? true : false,
        "notice": this.notice,
      }).then(res => {
        if (!res || (res && !res.error)) {
          that.groups[that.indexG].state = !that.groups[that.indexG].state
        }
      })
      if (!this.timer) {
        this.count = 1
        this.isState = true
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= 1) {
            this.count--
            here.stateText = this.count
          } else {
            this.isState = false
            clearInterval(this.timer)
            this.timer = null
            here.stateText = that.groups[that.indexG].state ? "公开团队信息" : "未公开团队信息"
          }
        }, 1000)
      }
    },

    init (photo, nameG, master, notice, state) {
      this.photo = photo
      this.nameG = nameG
      this.master = master
      this.notice = notice
      this.stateText = state ? "公开团队信息" : "未公开团队信息"
    },


    changeNotice () {
      const here = this
      //console.log(this.notice)
      updateGroup({
        "id": parseInt(this.$parent.$parent.id),
        "name": this.nameG,
        "state": this.stateText == "公开团队信息" ? true : false,
        "notice": this.notice
      }).then(res => {
        //console.log("修改群公告成功")
        //console.log(res)
        if (!res) {
          const that = here.$parent.$parent.$parent.$refs.memberList
          // for (this.i = 0; this.i < that.length; this.i++) {
          //   if (that[this.i].group_id == here.$parent.id) {
          //     that[this.i].notice = here.notice
          //     break
          //   }
          // }
          that.groups[that.indexG].notice = here.notice
          here.foreNotice = here.notice
          here.iShowTrue()
        }
      })

    },

    getMember () {
      if (this.$parent.$parent.id != -1 && !this.memberShow) {
        const that = this
        getGroupMember({
          "id": this.$parent.$parent.id,
        }).then(res => {
          this.mems = res
          this.mems.forEach(function (item) {
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
          this.mems = JSON.parse(JSON.stringify(this.mems))
          this.allPageMem = Math.ceil(this.mems.length / this.num);
          this.memberShow = !this.memberShow
        })
      }
    },

    getMethod: function (methodName, index) {
      this[methodName](index);
    },
    downPageMem () {
      this.initBtns()
      if (this.pageMem != 1 && this.pageMem != 0) {
        this.pageMem -= 1
      }
    },
    upPageMem () {
      this.initBtns()
      if (this.pageMem != this.allPageMem) {
        this.pageMem += 1
      }
    },
    downPageF () {
      if (this.pageF != 1 && this.pageF != 0) {
        this.pageF -= 1
      }
    },
    upPageF () {
      if (this.pageF != this.allPageF) {
        this.pageF += 1
      }
    },
    iShowTrue () {
      this.iShow = true
      this.notice = this.foreNotice
    },
    showTextField (j) {
      if (this.memsIndex != -1) {
        if (this.memsIndex != j) {
          this.mems[this.memsIndex].show = false;
          this.mems[this.memsIndex].quit = false;
        }
        this.mems[j].show = !this.mems[j].show;
        this.mems[j].quit = false;
      }
      else {
        this.mems[j].show = !this.mems[j].show;
        this.mems[j].quit = false;
      }
      this.memsIndex = j;
      this.name = "";
    },

    showQuitField (j) {
      if (this.memsIndex != -1) {
        if (this.memsIndex != j) {
          this.mems[this.memsIndex].quit = false;
          this.mems[this.memsIndex].show = false;
        }
        this.mems[j].quit = !this.mems[j].quit;
        this.mems[j].show = false;
      }
      else {
        this.mems[j].quit = !this.mems[j].quit;
        this.mems[j].show = false;
      }
      this.memsIndex = j;
      this.name = "";
    },

    notFriend (index) {
      return this.mems[index].chat_id == -1
    },

    isMaster () {
      return this.$store.getters.userId == this.master
    },

    isSelf (index) {
      return this.mems[index].id == this.$store.getters.userId
    },

    isManager (index) {
      return this.mems[index].type == "manager"
    },

    //以下三个方法，由于只有isself成立的时候才会调用，因此this.mems[index].id=this.mems[index].id

    changeName (index) {
      changeGroupNickname({
        "nickname": this.name,
        "group_id": this.$parent.$parent.id,
      }).then(res => {
        res
        this.mems[index].name = this.name
        this.mems[this.memsIndex].show = false;
      })
    },

    addSubMaster (index) {
      const that = this
      addGroupManager({
        "group_id": this.$parent.$parent.id,
        "id": that.mems[index].id
      }).then(res => {
        res
        this.showQuitField(index)
        if (!res) {
          that.mems[index].name = that.mems[index].name + "（管理员）"
          that.mems[index].type = "manager"
        }
      })
    },

    subSubMaster (index) {
      const that = this
      delGroupManager({
        "group_id": this.$parent.$parent.id,
        "id": that.mems[index].id
      }).then(res => {
        res
        if (!res) {
          that.mems[index].name = that.mems[index].nick
          that.mems[index].type = "normal"
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
      this.foreNotice = this.notice
    }
  }
}
</script>