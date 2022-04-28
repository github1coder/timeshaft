<template>
  <div class="sb-container">
    <v-card
      height="100%"
      dark
      flat
      tile
      class="server-info"
    >
      <v-text-field
        clearable
        outlined
        dense
        dark
        hide-details
        label="请输入关键词"
        v-model="text"
        class="input-search mt-3"
        autocomplete="off"
        v-on="on"
        ref="search"
      ></v-text-field>
      <v-list>
        <v-list-item
          v-for="(subItem, j) in ans"
          :key="j"
          @click="method1"
        >
          <v-list-item-avatar>
            <v-img :src="subItem.group_photo"></v-img>
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title
              v-show="!subItem.show && !subItem.quit"
              v-text="subItem.group_name"
              style="text-align: left"
            ></v-list-item-title>
          </v-list-item-content>
          <!-- 后面的省略号 -->
          <v-list-item-action>
            <v-icon>mdi-plus</v-icon>
          </v-list-item-action>
        </v-list-item>
      </v-list>
    </v-card>
  </div>
</template>
<script>

export default {
  data () {
    return {
      text: "",
      ans: [{

      }],
    };
  },

  mounted () {
  },

  methods: {
    metohd1 () {

    },
    changeShowText () {
      this.type = "text";
    },
  },
}
</script>