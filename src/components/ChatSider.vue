


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
                  v-for="(item, i) in this.chats"
                  @click="selectChannel(item.id, i)"
                  :key="i"
              >
                <v-list-item-icon>
                  <p>123</p>
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
      searchResult: [],
      channelNum: null,
      data: [1, 2, 3, 4]
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
      this.$store.commit("changeChannel", {id: id, idx: idx});
    }
  },
  updated() {
    console.log("?")
    console.log(this.chats)
  }
  ,
  computed: {
    chats() {
      let array
      for (let listenerListKey in this.$store.state.listenerList) {
        array.push(this.$store.state.listenerList[listenerListKey])
      }
      return array
      // return this.$store.state.listenerList
    }

  },
  mounted() {

  }

}
</script>

<style scoped>

</style>