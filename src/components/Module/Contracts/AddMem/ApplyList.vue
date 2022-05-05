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
      <v-card
        height="50%"
        style="overflow: auto; overflow-x: hidden"
      >
        <v-row
          dense
          style="width: 100%; height: 64px; border: white 0px solid; margin: auto;"
        >
          <v-card-title style="margin: auto;">
            好友邀请
          </v-card-title>
        </v-row>
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
              <v-row style="width: 10%; border: 1px black solid">
                <v-btn
                  small
                  @click="acF(j + num * (pageF - 1))"
                >
                  <v-icon>mdi-check-bold</v-icon>
                </v-btn>
                <v-btn
                  small
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
      <v-card
        height="50%"
        style="overflow: auto; overflow-x: hidden"
      >
        <v-row
          dense
          style="width: 100%; height: 64px; border: white 0px solid; margin: auto;"
        >
          <v-card-title style="margin: auto;">
            团队申请
          </v-card-title>
        </v-row>
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
              <v-row style="width: 10%; border: 1px black solid">
                <v-btn
                  small
                  @click="acG(j + num * (pageG - 1))"
                >
                  <v-icon>mdi-check-bold</v-icon>
                </v-btn>
                <v-btn
                  small
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
    </v-card>
  </div>
</template>
<script>
import { apply, getApplyList } from "../../../../api/addresslist/index"
export default {
  data () {
    return {
      num: 5,
      pageF: 1,
      allPageF: 2,
      pageG: 1,
      allPageG: 2,
      friendAns: [],
      groupAns: [],
    };
  },

  mounted () {
    getApplyList({
      "user_id": this.$store.getters.userId,
      "type": "friend",
      "ACCESS_TOKEN": null,
    }).then(res => {
      this.friendAns = res
      this.allPageF = Math.ceil(this.friendAns.length / this.num)
      console.log(this.friendAns)
      if (this.allPageF == 0) {
        this.pageF = 0;
      }
    })
    getApplyList({
      "user_id": this.$store.getters.userId,
      "type": "group",
      "ACCESS_TOKEN": null,
    }).then(res => {
      this.groupAns = res
      this.allPageG = Math.ceil(this.groupAns.length / this.num)
      console.log(this.groupAns)
      if (this.allPageG == 0) {
        this.pageG = 0;
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
        "user_id": this.$store.getters.userId,
        "type": "friend",
        "action": "accept",
        "id": this.friendAns[index].id,
        "ACCESS_TOKEN": null,
      }
      ).then(res => {
        console.log(res)
        this.friendAns[index].show = false
      })
    },

    reF (index) {
      apply({
        "user_id": this.$store.getters.userId,
        "type": "friend",
        "action": "refuse",
        "id": this.friendAns[index].id,
        "ACCESS_TOKEN": null,
      }
      ).then(res => {
        console.log(res)
        this.friendAns[index].show = false
      })
    },

    acG (index) {
      apply({
        "user_id": this.groupAns[index].id,
        "type": "group",
        "action": "accept",
        "id": this.groupAns[index].group_id,
        "ACCESS_TOKEN": null,
      }
      ).then(res => {
        console.log(res)
        this.groupAns[index].show = false
      })
    },

    reG (index) {
      apply({
        "user_id": this.groupAns[index].id,
        "type": "group",
        "action": "refuse",
        "id": this.groupAns[index].group_id,
        "ACCESS_TOKEN": null,
      }
      ).then(res => {
        console.log(res)
        this.groupAns[index].show = false
      })
    },
    changeShowText () {
      this.type = "text";
    },
  },
}
</script>