<template>
  <!-- <div style="overflow: auto; overflow-x: hidden; height: 100%; width: 100%;"> -->
  <v-card style="height: 100%; width: 100%;">
    <v-card style="height:100%; width:60%; margin: auto;">
      <v-tabs
        color="pink"
        left
        v-model="tab"
      >
        <v-tab>好友申请</v-tab>
        <v-tab>团队邀请</v-tab>
      </v-tabs>

      <v-tabs-items
        v-model="tab"
        style="flex:1; width:100%; margin: auto;"
      >
        <v-tab-item
          height="100%"
          style="overflow: auto; overflow-x: hidden"
        >
          <v-card
            height="50%"
            style="overflow: auto; overflow-x: hidden"
          >
            <v-row
              dense
              style="width: 100%; height: 64px;"
            >
              <v-card-title
                style="margin: auto;"
                v-show="showF"
              >
                暂无好友申请
              </v-card-title>
            </v-row>
            <v-divider style="margin-top: 10px;"></v-divider>
            <v-list>
              <v-list-item
                v-for="(subItem, j) in friendAns.slice(num * (pageF - 1), num * pageF)"
                :key="j + num * (pageF - 1)"
                @click="method1"
                v-show="subItem.show"
              >
                <v-list-item-avatar>
                  <v-img :src="subItem.photo"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title
                    v-text="subItem.name"
                    style="text-align: left"
                  ></v-list-item-title>
                </v-list-item-content>
                <!-- 后面的省略号 -->
                <v-list-item-action>
                  <v-row style="width: 10%;">
                    <v-btn
                      small
                      rounded
                      color="green lighten-3"
                      @click="acF(j + num * (pageF - 1))"
                    >
                      <v-icon>mdi-check-bold</v-icon>
                    </v-btn>
                    <v-btn
                      small
                      rounded
                      color="red lighten-3"
                      @click="reF(j + num * (pageF - 1))"
                    >
                      <v-icon>mdi-close-thick</v-icon>
                    </v-btn>
                  </v-row>

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
          </v-card>
        </v-tab-item>
        <v-tab-item
          height="100%"
          dark
          style="overflow: auto; overflow-x: hidden"
        >
          <v-card
            height="50%"
            style="overflow: auto; overflow-x: hidden"
          >
            <v-row
              dense
              style="width: 100%; height: 64px; margin: auto;"
            >
              <v-card-title
                style="margin: auto;"
                v-show="showG"
              >
                暂无团队邀请
              </v-card-title>
            </v-row>
            <v-divider style="margin-top: 10px;"></v-divider>
            <v-list>
              <v-list-item
                v-for="(subItem, j) in groupAns.slice(num * (pageG - 1), num * pageG)"
                :key="j + num * (pageG - 1)"
                @click="method1"
                v-show="subItem.show"
                two_line
              >
                <v-list-item-avatar>
                  <v-img :src="subItem.photo"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title
                    v-text="subItem.name"
                    style="text-align: left"
                  ></v-list-item-title>
                  <v-list-item-subtitle style="text-align: left">申请加入{{subItem.group_name}}</v-list-item-subtitle>
                </v-list-item-content>
                <!-- 后面的省略号 -->
                <v-list-item-action>
                  <v-row style="width: 10%;">
                    <v-btn
                      small
                      rounded
                      color="green lighten-3"
                      @click="acG(j + num * (pageG - 1))"
                    >
                      <v-icon>mdi-check-bold</v-icon>
                    </v-btn>
                    <v-btn
                      small
                      rounded
                      color="red lighten-3"
                      @click="reG(j + num * (pageG - 1))"
                    >
                      <v-icon>mdi-close-thick</v-icon>
                    </v-btn>
                  </v-row>
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
          </v-card>
        </v-tab-item>
      </v-tabs-items>
    </v-card>
  </v-card>
</template>
<script>
import { apply, getApplyList } from "../../../../api/addresslist/index"
export default {
  data () {
    return {
      tab: null,
      num: 10,
      pageF: 1,
      allPageF: 2,
      pageG: 1,
      allPageG: 2,
      friendAns: [],
      groupAns: [],
      showF: true,
      showG: true,
    };
  },

  mounted () {
    getApplyList({
      "type": "friend",
    }).then(res => {
      this.friendAns = res
      this.allPageF = Math.ceil(this.friendAns.length / this.num)
      console.log(this.friendAns)
      if (this.allPageF == 0) {
        this.pageF = 0;
        this.showF = true
      }
      else {
        this.showF = false
        this.pageF = 1
      }
    })
    getApplyList({
      "type": "group",
    }).then(res => {
      this.groupAns = res
      this.allPageG = Math.ceil(this.groupAns.length / this.num)
      console.log(this.groupAns)
      if (this.allPageG == 0) {
        this.pageG = 0;
        this.showG = true
      }
      else {
        this.showG = false
        this.pageG = 1
      }
    })
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

    acF (index) {
      apply({
        "type": "friend",
        "action": "accept",
        "id": this.friendAns[index].id,
        "memId": -1
      }
      ).then(res => {
        console.log(res)
        this.friendAns[index].show = false
        this.$store.state.applynum -= 1
        //todo: 用户发一句“你好”给被同意者
      })
    },

    reF (index) {
      apply({
        "type": "friend",
        "action": "refuse",
        "id": this.friendAns[index].id,
        "memId": -1
      }
      ).then(res => {
        console.log(res)
        this.friendAns[index].show = false
        this.$store.state.applynum -= 1
      })
    },

    acG (index) {
      apply({
        "type": "group",
        "action": "accept",
        "id": this.groupAns[index].group_id,
        "memId": this.groupAns[index].id,
      }
      ).then(res => {
        console.log(res)
        this.groupAns[index].show = false
        this.$store.state.applynum -= 1
      })
    },

    reG (index) {
      apply({
        "type": "group",
        "action": "refuse",
        "id": this.groupAns[index].group_id,
        "memId": this.groupAns[index].id,
      }
      ).then(res => {
        console.log(res)
        this.groupAns[index].show = false
        this.$store.state.applynum -= 1
      })
    },
    changeShowText () {
      this.type = "text";
    },
  },
}
</script>