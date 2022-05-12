<template>
  <div class="sidebar">
    <div class="sb-container">
      <v-card
        height="100%"
        dark
        flat
        tile
        class="server-info"
      >
        <v-card
          height="60"
          tile
          class="server-title"
        >
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
              <v-list
                v-if="searchResult.length > 0"
                class="border-list"
                dense
              >
                <v-list-item
                  v-for="(item, index) in searchResult"
                  :key="index"
                  @click="itemClick(item)"
                >
                  <v-list-item-title>{{ item.name }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </v-card>
        <v-card
          tile
          class="channels"
        >
          <!-- TODO expansion needed! -->
          <v-list
            width="100%"
            rounded
            max-height="80px"
          >
            <v-list-item-group color="primary">
              <v-list-item
                v-for="(item, i) in this.chats"
                @click="selectChannel(item.id, i, item)"
                :id="'message-'+item.id.toString()"
                :key="i"
              >
                <v-list-item-avatar>
                  <v-img
                    max-height="70px"
                    max-width="50px"
                    :src="item.chatAvatar"
                  ></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title
                    class="channel-title"
                    v-text="item.chatName"
                  ></v-list-item-title>
                  <v-list-item-content
                    style="text-align: left; font-size: 5px"
                    v-if="item.data.length > 0"
                  > {{item.data.length-1 === item.haveRead? "": ("[未读 " + (item.data.length-1-item.haveRead).toString() +" 条]")  }} {{item.data[item.data.length-1].msg}}</v-list-item-content>
                </v-list-item-content>
                <v-list-item-content
                  style="text-align: right; font-size: 1px"
                  v-if="item.data.length > 0"
                >
                  {{item.data[item.data.length-1].time}}
                </v-list-item-content>
                <!--                <v-list-item-content>-->
                <!--                  1-->
                <!--                </v-list-item-content>-->
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-card>
      </v-card>
    </div>
  </div>
</template>

<script>
import { getHistoryMessage, haveRead } from "@/api/message";

export default {
  name: "MessageList",
  data () {
    return {
      text: '',
      showSelect: true,
      searchResult: [],
    }
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
    search () {
      this.$refs.search.blur()
      console.log(this.text)
      // this.$router.push()
    },
    toggleAC () {
      this.$store.commit("toggleAC");
    },
    selectChannel (id, idx, item) {
      //关闭工具栏
      this.$parent.toolsDrawer = false
      console.log(id)
      console.log(item)
      this.$store.commit("changeChannel", { id: id, idx: idx });
      if (item.data.length !== 0) {
        item.haveRead = item.data.length - 1
        haveRead({
          type: this.$store.state.messageList[this.$store.state.currentChannelIdx].type,
          chatId: this.$store.state.currentChannelId,
          userId: this.$store.state.userId,
          time: item.data[item.data.length - 1].time,
        }).then(res => {
          res
          console.log("have read this msg")
        })
      }
    },

    pull () {
      console.log("pull from server " + this.$store.state.messageList.length + " " + this.chats.length)
      for (let i in this.$store.state.messageList) {
        if (this.$store.state.messageList[i].data.length < 10) {
          getHistoryMessage({
            userId: this.$store.state.userId,
            type: this.$store.state.messageList[this.$store.state.currentChannelIdx].type,
            chatId: this.$store.state.messageList[i].id,
            index: this.$store.state.messageList[i].index,
          }).then(res => {
            console.log("get history message of " + i.toString())
            this.$store.state.more = res.more
            this.$store.state.messageList[i].index = res.index
            console.log(this.$store.state.messageList[i].data)
            for (let j = res.data.length - 1; j >= 0; j--) {
              this.$store.state.messageList[i].data.unshift(res.data[j])
            }
            this.$store.state.messageList[i].haveRead += res.data.length
            console.log(this.$store.state.messageList[i].data)
          })
        }
      }
    },
  },
  updated () {

  },
  computed: {
    chats () {
      return this.$store.state.messageList
    },
  },
  mounted () {
    setTimeout(this.pull, 500)
  },
  created () {

  }

}
</script>

<style scoped>
</style>