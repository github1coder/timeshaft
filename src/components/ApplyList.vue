<template>
  <div class="sb-container">
    <v-card
      height="100%"
      dark
      flat
      tile
      class="server-info"
    >
      <v-card height="50%">
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
              <v-row style="width: 10%; border: 1px black solid">
                <v-btn
                  small
                  @click="acF(j + num * (pageF - 1))"
                >
                  <v-icon>mdi-check-bold</v-icon>
                </v-btn>
                <v-btn
                  small
                  @click="acF(j + num * (pageF - 1))"
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
      <v-card height="50%">
        <v-row
          dense
          style="width: 100%; height: 64px; border: white 0px solid; margin: auto;"
        >
          <v-card-title style="margin: auto;">
            团队邀请
          </v-card-title>
        </v-row>
        <v-list>
          <v-list-item
            v-for="(subItem, j) in friendAns.slice(num * (pageG - 1), num * pageG)"
            :key="j + num * (pageG - 1)"
            @click="method1"
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
import { apply } from "../api/addresslist/index"
export default {
  data () {
    return {
      num: 5,
      pageF: 1,
      allPageF: 2,
      pageG: 1,
      allPageG: 2,
      friendAns: [{
        friend_id: 1,
        friend_name: 'Breakfast & brunch',
        friend_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
      }, {
        friend_id: 1,
        friend_name: 'Breakfast & brunch',
        friend_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
      }, {
        friend_id: 1,
        friend_name: 'Breakfast & brunch',
        friend_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
      }, {
        friend_id: 1,
        friend_name: 'Breakfast & brunch',
        friend_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
      }, {
        friend_id: 1,
        friend_name: 'Breakfast & brunch',
        friend_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
      }, {
        friend_id: 1,
        friend_name: 'Breakfast & brunch',
        friend_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
      }, {
        friend_id: 1,
        friend_name: 'Breakfast & brunch',
        friend_photo: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
        show: false,
      },],
      groupAns: [{

      }],
    };
  },

  mounted () {

  },

  methods: {
    method1 () {

    },

    downPageF () {
      if (this.pageF != 1) {
        this.pageF -= 1
      }
    },

    upPageF () {
      if (this.pageF != this.allPageF) {
        this.pageF += 1
      }
    },

    downPageG () {
      if (this.pageG != 1) {
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
        type: "friend",
        action: "accept",
        id: this.friendAns[index].id,
      }
      ).then(res => {
        console.log(res)

      })
    },

    reF (index) {
      apply({
        type: "friend",
        action: "refuse",
        id: this.friendAns[index].id,
      }
      ).then(res => {
        console.log(res)

      })
    },

    acG (index) {
      apply({
        type: "group",
        action: "accept",
        id: this.groupAns[index].id,
      }
      ).then(res => {
        console.log(res)

      })
    },

    reG (index) {
      apply({
        type: "group",
        action: "refuse",
        id: this.groupAns[index].id,
      }
      ).then(res => {
        console.log(res)

      })
    },
    changeShowText () {
      this.type = "text";
    },
  },
}
</script>