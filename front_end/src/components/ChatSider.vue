<template>
  <div class="sidebar">
    <div class="sb-container">
      <v-card height="100%" dark flat tile class="server-info">
        <v-card height="60" tile class="server-title">
          <!--          <div style="line-height:48px">Discord Clone</div>-->
          <div v-on:keyup.enter="search">
            <v-menu offset-y>
              <template v-slot:activator="{ on }">
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
              </template>
              <v-list v-if="items.length > 0" class="border-list" dense>
                <v-list-item v-for="(item, index) in items" :key="index" @click="itemClick(item)">
                  <v-list-item-title>{{ item.name }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </v-card>
        <v-card height="235" class="tips" tile>
          ChatSider
          <br />
          <v-btn color="indigo" class="ma-4">Ayo GG!!</v-btn>
        </v-card>

        <v-card tile class="channels">
          <v-list width="100%" rounded dense>
            <v-subheader>SERVER</v-subheader>
            <v-list-item-group v-model="item" color="primary">
              <v-list-item v-for="(item, i) in items" :key="i">
                <v-list-item-icon>
                  <v-icon v-text="item.icon"></v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title
                      class="channel-title"
                      v-text="item.text"
                  ></v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-card>
      </v-card>
    </div>

    <!--    <v-card-->
    <!--      height="52"-->
    <!--      dark-->
    <!--      tile-->
    <!--      flat-->
    <!--      class="mystats"-->
    <!--      @click="toggleAC"-->
    <!--    >-->
    <!--      <v-list-item class="grow">-->
    <!--        <v-list-item-avatar color="grey darken-3">-->
    <!--          <v-img class="elevation-6" :src="$store.state.myIcon"></v-img>-->
    <!--        </v-list-item-avatar>-->

    <!--        <v-list-item-content>-->
    <!--          <v-list-item-title>{{ $store.state.myNick }}</v-list-item-title>-->
    <!--        </v-list-item-content>-->
    <!--      </v-list-item>-->
    <!--    </v-card>-->
  </div>
</template>

<script>
export default {
  name: "ChatSider",
  data() {
    return {
      text: '',
      showSelect: true,
      items: [
        { text: "Announcements", icon: "mdi-bell-alert" },
        { text: "Rules", icon: "mdi-file-check" },
        { text: "Welcome", icon: "mdi-emoticon" }
      ],
    }
  },
  watch: {
    text: 'inputHandle'
  },
  methods: {
    itemClick (item) {
      this.text = item.name
      this.$refs.search.blur()
      // this.$router.push()
    },
    inputHandle (text) {
      if (text.trim() !== '') {
        this.showSelect = true
        setTimeout(() => {
          this.getEntity()
        }, 300)
      }
    },
    getEntity () {
      // 请求接口更新 items 数据
      this.items = [
        {
          key: '1234',
          name: '1234'
        },
        {
          key: 'abc',
          name: 'abc'
        },
        {
          key: 'def',
          name: 'def'
        },
        {
          key: 'ccc',
          name: 'ccc'
        },
        {
          key: 'ccc',
          name: 'ccc'
        },
        {
          key: 'ccc',
          name: 'ccc'
        }
      ]
    },
    search () {
      this.$refs.search.blur()
      console.log(this.text)
      // this.$router.push()
    },
    toggleAC() {
      this.$store.commit("toggleAC");
    }
  },
  updated() {
    this.$store.commit("changeChannel", this.item);
  },
  mounted() {
    this.$store.commit("updateChannels", this.items);
  }
}
</script>

<style scoped>

</style>