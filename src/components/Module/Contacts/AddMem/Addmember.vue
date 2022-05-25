<template>
  <!-- <div style="overflow: auto; overflow-x: hidden; height: 100%; width: 100%;"> -->
  <v-card style="height: 100%; width: 100%;">
    <v-card style="height:100%; width:60%; margin: auto;">
      <v-tabs
        color="pink"
        left
        v-model="tab"
      >
        <v-tab>添加好友</v-tab>
        <v-tab>添加团队</v-tab>
      </v-tabs>

      <v-tabs-items
        v-model="tab"
        style="flex:1; width:100%; margin: auto;"
      >
        <v-tab-item
          height="100%"
          style="overflow: auto; overflow-x: hidden"
        >
          <v-row
            dense
            style="width: 100%; height: 64px; margin: auto;"
          >
            <v-text-field
              clearable
              outlined
              dense
              hide-details
              label="好友昵称/邮箱"
              v-model="textF"
              class="input-search mt-3"
              autocomplete="off"
              style="width: 70%; margin: auto;"
            ></v-text-field>
            <v-btn
              style="width: 15%; height: 64%; margin: 12px 0px auto;"
              @click="searchFriend"
            >
              <v-icon style="width: 100%; height: 100%;">
                mdi-magnify
              </v-icon>
            </v-btn>
          </v-row>
          <v-divider style="margin-top: 10px;"></v-divider>
          <v-btn
            style="width: 100%; margin-top: 20px;"
            color=blue
            @click="findmore('friend')"
            v-if="isMore"
          >
            发现周围
          </v-btn>
          <v-card-title
            style="margin: auto;"
            v-show="showF"
          >
            搜索结果为空
          </v-card-title>
          <v-list>
            <v-list-item
              v-for="(subItem, j) in friendAns.slice(num * (pageF - 1), num * pageF)"
              :key="j + num * (pageF - 1)"
              @click="method1"
              two-line
            >
              <v-list-item-avatar>
                <v-img :src="subItem.photo"></v-img>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title
                  v-text="subItem.name"
                  style="text-align: left"
                ></v-list-item-title>
                <v-list-item-subtitle style="text-align: left">{{subItem.master}}的群聊</v-list-item-subtitle>
              </v-list-item-content>
              <!-- 后面的省略号 -->
              <v-list-item-action>
                <v-btn
                  small
                  @click="newApplyF(j + num * (pageF - 1))"
                  :disabled="subItem.show"
                >
                  <v-icon>mdi-plus</v-icon>
                </v-btn>
              </v-list-item-action>
            </v-list-item>
          </v-list>
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
        </v-tab-item>
        <v-tab-item
          height="100%"
          dark
          style="overflow: auto; overflow-x: hidden"
        >
          <v-row
            dense
            style="width: 100%; height: 64px; margin: auto;"
          >
            <v-text-field
              clearable
              outlined
              dense
              dark
              hide-details
              label="团队名字"
              v-model="textG"
              class="input-search mt-3"
              autocomplete="off"
              style="width: 70%; margin: auto;"
            ></v-text-field>
            <v-btn
              style="width: 15%; height: 64%; margin: 12px 0px auto;"
              @click="searchGroup"
            >
              <v-icon style="width: 100%; height: 100%;">
                mdi-magnify
              </v-icon>
            </v-btn>
          </v-row>
          <v-divider style="margin-top: 10px;"></v-divider>
          <v-btn
            style="width: 100%; margin-top: 20px;"
            color=blue
            @click="findmore('group')"
            v-if="isMore"
          >
            发现周围
          </v-btn>
          <v-card-title
            style="margin: auto;"
            v-show="showG"
          >
            搜索结果为空
          </v-card-title>
          <v-list>
            <v-list-item
              v-for="(subItem, j) in groupAns.slice(num * (pageG - 1), num * pageG)"
              :key="j + num * (pageG - 1)"
              @click="method1"
            >
              <v-list-item-avatar>
                <span class="white--text text-h5">{{ subItem.name[0] }}</span>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title
                  v-text="subItem.name"
                  style="text-align: left"
                ></v-list-item-title>
              </v-list-item-content>
              <!-- 后面的省略号 -->
              <v-list-item-action>
                <v-btn
                  small
                  @click="newApplyG(j + num * (pageG - 1))"
                  :disabled="subItem.show"
                >
                  <v-icon>mdi-plus</v-icon>
                </v-btn>
              </v-list-item-action>
            </v-list-item>
          </v-list>
          <v-row style="padding-bottom: 0;">
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
        </v-tab-item>
      </v-tabs-items>
    </v-card>
  </v-card>
</template>
<script>
import { apply, finding, search } from "../../../../api/addresslist/index"
export default {
  data () {
    return {
      tab: null,
      num: 10,
      pageF: 0,
      allPageF: 0,
      pageG: 0,
      allPageG: 0,
      textF: "",
      textG: "",
      friendAns: [],
      groupAns: [],
      showF: false,
      showG: false,
      isMore: true,
    };
  },

  mounted () {

  },

  methods: {
    method1 () {

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

    downPageG () {
      if (this.pageG != 1 && this.pageG != 0) {
        this.pageG -= 1
      }
    },

    upPageG () {
      if (this.pageG != this.allPageG) {
        this.pageG += 1
      }
    },

    searchFriend () {
      if (this.textF == null || this.textF == "" || this.$store.getters.userId == -1) {
        return;
      }
      search(
        {
          "name": this.textF,
          "type": "friend",
        }
      ).then(res => {
        // this.isMore = false
        this.friendAns = res
        this.friendAns.forEach(function (item) {
          item["show"] = false;
        });
        this.friendAns = JSON.parse(JSON.stringify(this.friendAns))
        this.allPageF = Math.ceil(this.friendAns.length / this.num)
        if (this.allPageF != 0) {
          this.pageF = 1
          this.showF = false
        }
        else {
          this.showF = true
          this.pageF = 0
        }
      })
    },
    searchGroup () {
      if (this.textG == null || this.textG == "" || this.$store.getters.userId == -1) {
        return;
      }
      search(
        {
          "name": this.textG,
          "type": "group",
        }
      ).then(res => {
        // this.isMore = false
        this.groupAns = res
        this.groupAns.forEach(function (item) {
          item["show"] = false;
        });
        this.groupAns = JSON.parse(JSON.stringify(this.groupAns))
        this.allPageG = Math.ceil(this.groupAns.length / this.num)
        if (this.allPageG != 0) {
          this.pageG = 1
          this.showG = false
        }
        else {
          this.showG = true
          this.pageG = 0
        }
      })
    },

    findmore (type) {
      finding({
        "type": type
      }).then(res => {
        if (type == "friend") {
          this.friendAns = res
          this.friendAns.forEach(function (item) {
            item["show"] = false;
          });
          this.friendAns = JSON.parse(JSON.stringify(this.friendAns))
          this.allPageF = Math.ceil(this.friendAns.length / this.num)
          if (this.allPageF != 0) {
            this.pageF = 1
            this.showF = false
          }
          else {
            this.showF = true
            this.pageF = 0
          }
        }
        else {
          this.groupAns = res
          this.groupAns.forEach(function (item) {
            item["show"] = false;
          });
          this.groupAns = JSON.parse(JSON.stringify(this.groupAns))
          this.allPageG = Math.ceil(this.groupAns.length / this.num)
          if (this.allPageG != 0) {
            this.pageG = 1
            this.showG = false
          }
          else {
            this.showG = true
            this.pageG = 0
          }
        }
      })

    },

    newApplyF (index) {
      apply({
        "type": "friend",
        "action": "new",
        "id": this.friendAns[index].id,
      }
      ).then(res => {
        console.log(res)
        this.friendAns[index].show = true
      })
    },
    newApplyG (index) {
      apply({
        "type": "group",
        "action": "new",
        "id": this.groupAns[index].id,
      }
      ).then(res => {
        console.log(res)
        this.groupAns[index].show = true
      })
    },
  },
}
</script>