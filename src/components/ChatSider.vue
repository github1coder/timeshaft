


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
              <v-list v-if="searchResult.length > 0" class="border-list" dense>
                <v-list-item v-for="(item, index) in searchResult" :key="index" @click="itemClick(item)">
                  <v-list-item-title>{{ item.name }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </v-card>
        <v-card tile class="channels">
          <!-- TODO expansion needed! -->
          <v-list width="100%" rounded dense>
            <v-list-item-group
                v-model="channelNum"
                color="primary">
              <v-list-item
                  v-for="(item, i) in chats"
                  @click="selectChannel(item.id, i)"
                  :key="i"
              >
                <v-list-item-icon>
                  <v-icon v-text="item.avatar"></v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title
                      class="channel-title"
                      v-text="item.chatName"
                  ></v-list-item-title>
                  <v-list-item-subtitle
                      v-if="item.data.length>0"
                      class="channel-title"
                      v-text="item.data[item.data.length-1].message">
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-card>
      </v-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChatSider",
  data() {
    return {
      text: '',
      showSelect: true,
      // chat1: [
      //   {id : 0, text: "Rose", icon: "mdi-emoticon-kiss-outline", message: "晚安哦~"},
      //   {id : 1,text: "Peter", icon: "mdi-emoticon-confused-outline", message: "上号！"},
      //   {id : 2,text: "Mike", icon: "mdi-emoticon-lol-outline", message: "bbzl"},
      //   {id : 3,text: "James", icon: "mdi-emoticon-wink-outline", message: "i want more ♂"},
      //   {id : 4,text: "Jason", icon: "mdi-emoticon-excited-outline", message: "lol"},
      //   {id : 5,text: "WeiHuang", icon: "mdi-emoticon-poop-outline", message: "summit issues!"}
      // ],
      friends : [
        {id: 0, chatName: "1", url: "", avatar: "mdi-emoticon-kiss-outline", data: [{ name: "Jiale Xu", avatar: "mdi-emoticon-kiss-outline", message: "test", time: "2022-4-28-16:35"},{ name: "Jiale Xu", avatar: "mdi-emoticon-kiss-outline", message: "test", time: "2022-4-28-16:35"},{ name: "Jiale Xu", avatar: "mdi-emoticon-kiss-outline", message: "test", time: "2022-4-28-16:35"},{ name: "Jiale Xu", avatar: "mdi-emoticon-kiss-outline", message: "test", time: "2022-4-28-16:35"},{ name: "Jiale Xu", avatar: "mdi-emoticon-kiss-outline", message: "test", time: "2022-4-28-16:35"},{ name: "Jiale Xu", avatar: "mdi-emoticon-kiss-outline", message: "test", time: "2022-4-28-16:35"},{ name: "Jiale Xu", avatar: "mdi-emoticon-kiss-outline", message: "test", time: "2022-4-28-16:35"},{ name: "Jiale Xu", avatar: "mdi-emoticon-kiss-outline", message: "test", time: "2022-4-28-16:35"},{ name: "Jiale Xu", avatar: "mdi-emoticon-kiss-outline", message: "test", time: "2022-4-28-16:35"},{ name: "Jiale Xu", avatar: "mdi-emoticon-kiss-outline", message: "test", time: "2022-4-28-16:35"}]},
        {id: 1, chatName: "2", url: "", avatar: "mdi-emoticon-kiss-outline", data: [{ name: "Jiale Xu", avatar: "mdi-emoticon-kiss-outline", message: "test", time: "2022-4-28-16:35"}]},
        {id: 2, chatName: "3", url: "", avatar: "mdi-emoticon-kiss-outline", data: [{ name: "Jiale Xu", avatar: "mdi-emoticon-kiss-outline", message: "test", time: "2022-4-28-16:35"}]},
      ],
      // chat2: [
      //   {text: "James", icon: "mdi-emoticon-wink-outline", message: "i want more ♂"},
      //   {text: "Jason", icon: "mdi-emoticon-excited-outline", message: "lol"},
      //   {text: "WeiHuang", icon: "mdi-emoticon-poop-outline", message: "summit issues!"}
      // ],
      searchResult: [],
      channelNum: null,
    }
  },
  watch: {
    text: 'inputHandle'
  },
  methods: {
    itemClick(item) {
      this.text = item.name
      this.$refs.search.blur()
      // this.$router.push()
    },
    inputHandle(text) {
      if (text.trim() !== '') {
        this.showSelect = true
        setTimeout(() => {
          this.getEntity()
        }, 300)
      }
    },
    getEntity() {
      // 请求接口更新 items 数据
      this.searchResult = [
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
    search() {
      this.$refs.search.blur()
      console.log(this.text)
      // this.$router.push()
    },
    toggleAC() {
      this.$store.commit("toggleAC");
    },
    selectChannel(id, idx) {
      // console.log(id + " " + idx)
      this.$store.commit("changeChannel", {id: id, idx: idx});
    }
  },
  updated() {

  },
  computed: {
    // TODO 如果订阅路径有变化则刷新 chat1
    chats() {
      return this.$store.state.listenerList
    }
  }
  ,

  //TODO 等待登陆注册能用了以后删除
  mounted() {
    this.$store.commit("updateChannels", this.friends)
  },
}
</script>

<style scoped>

</style>