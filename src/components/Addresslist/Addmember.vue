<template>
  <div class="sb-container">
    <v-card
      height="100%"
      dark
      flat
      tile
      class="server-info"
    >
      <v-card
        height="50%"
        style="overflow: auto; overflow-x: hidden; height: 460px;"
      >
        <v-row
          dense
          style="width: 100%; height: 64px; border: white 0px solid; margin: auto;"
        >
          <v-text-field
            clearable
            outlined
            dense
            dark
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
        <v-list>
          <v-list-item
            v-for="(subItem, j) in friendAns.slice(num * (pageF - 1), num * pageF)"
            :key="j + num * (pageF - 1)"
            @click="method1"
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
              <v-btn
                small
                @click="newApplyF(j + num * (pageG - 1))"
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
      </v-card>
      <v-card height="50%">
        <v-row
          dense
          style="width: 100%; height: 64px; border: white 0px solid; margin: auto;"
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
        <v-list>
          <v-list-item
            v-for="(subItem, j) in groupAns.slice(num * (pageG - 1), num * pageG)"
            :key="j + num * (pageG - 1)"
            @click="method1"
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
      </v-card>
    </v-card>
  </div>
</template>
<script>
import { apply, search } from "../../api/addresslist/index"
export default {
  data () {
    return {
      num: 5,
      pageF: 1,
      allPageF: 0,
      pageG: 1,
      allPageG: 0,
      textF: "",
      textG: "",
      friendAns: [],
      groupAns: [],
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
      if (this.textF == null || this.textF == "") {
        return;
      }
      search(
        {
          "user_id": this.$store.getters.userId,
          "name": this.textF,
          "type": "friend",
          "ACCESS_TOKEN": null,
        }
      ).then(res => {
        this.friendAns = res
        this.friendAns.forEach(function (item) {
          item["show"] = false;
        });
        this.friendAns = JSON.parse(JSON.stringify(this.friendAns))
        this.allPageF = Math.ceil(this.friendAns.length / this.num)
      })
    },
    searchGroup () {
      search(
        {
          "user_id": this.$store.getters.userId,
          "name": this.textG,
          "type": "group",
          "ACCESS_TOKEN": null,
        }
      ).then(res => {
        this.groupAns = res
        this.groupAns.forEach(function (item) {
          item["show"] = false;
        });
        this.groupAns = JSON.parse(JSON.stringify(this.groupAns))
        this.allPageG = Math.ceil(this.groupAns.length / this.num)
      })
    },

    newApplyF (index) {
      apply({
        "user_id": this.$store.getters.userId,
        "type": "friend",
        "action": "new",
        "id": this.friendAns[index].id,
        "ACCESS_TOKEN": null,
      }
      ).then(res => {
        console.log(res)
        this.friendAns[index].show = true
      })
    },
    newApplyG (index) {
      apply({
        "user_id": this.$store.getters.userId,
        "type": "group",
        "action": "new",
        "id": this.groupAns[index].id,
        "ACCESS_TOKEN": null,
      }
      ).then(res => {
        console.log(res)
        this.groupAns[index].show = true
      })
    },
  },
}
</script>