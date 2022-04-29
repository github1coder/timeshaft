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
            v-for="(subItem, j) in friendAns.slice(num * (page - 1), num * page)"
            :key="j + num * (page - 1)"
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
                <v-btn small>
                  <v-icon>mdi-check-bold</v-icon>
                </v-btn>
                <v-btn small>
                  <v-icon>mdi-close-thick</v-icon>
                </v-btn>
              </v-row>

            </v-list-item-action>
          </v-list-item>
        </v-list>
        <v-row>
          <v-btn
            width="33%"
            @click="downPage"
          >
            <v-icon>mdi-next</v-icon>
          </v-btn>
          <v-btn
            width="33%"
            disabled
          >
            {{page}}/{{allPage}}
          </v-btn>
          <v-btn
            width="33%"
            @click="upPage"
          >
            <v-icon>mdi-next</v-icon>
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
            v-for="(subItem, j) in friendAns.slice(num * (page - 1), num * page)"
            :key="j + num * (page - 1)"
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
                <v-btn small>
                  <v-icon>mdi-check-bold</v-icon>
                </v-btn>
                <v-btn small>
                  <v-icon>mdi-close-thick</v-icon>
                </v-btn>
              </v-row>
            </v-list-item-action>
          </v-list-item>
        </v-list>
        <v-row style="padding-bottom: 0;">
          <v-btn
            width="33%"
            @click="downPage"
          >
            <v-icon>mdi-next</v-icon>
          </v-btn>
          <v-btn
            width="33%"
            disabled
          >
            {{page}}/{{allPage}}
          </v-btn>
          <v-btn
            width="33%"
            @click="upPage"
          >
            <v-icon>mdi-next</v-icon>
          </v-btn>
        </v-row>
      </v-card>
    </v-card>
  </div>
</template>
<script>

export default {
  data () {
    return {
      num: 4,
      page: 1,
      allPage: 2,
      text: "",
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

    downPage () {
      if (this.page != 1) {
        this.page -= 1
      }
    },
    upPage () {
      if (this.page != this.allPage) {
        this.page += 1
      }
    },
    changeShowText () {
      this.type = "text";
    },
  },
}
</script>