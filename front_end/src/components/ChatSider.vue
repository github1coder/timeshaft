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
                <v-list-item v-for="(item, index) in items1" :key="index" @click="itemClick(item)">
                  <v-list-item-title>{{ item.name }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </v-card>
        <v-card tile class="channels">
          <!--          <v-expansion-panels flat>-->
          <!--            <v-expansion-panel-->
          <!--                class="pa-0"-->
          <!--                v-for="(item,i) in items"-->
          <!--                :key="i"-->
          <!--            >-->
          <!--              <v-expansion-panel-header class="pa-0" >-->
          <!--                {{item}}-->
          <!--              </v-expansion-panel-header>-->
          <!--              <v-expansion-panel-content class="pa-0">-->
          <!--                <v-list width="100%" rounded dense>-->
          <!--                  <v-list-item-group color="primary">-->
          <!--                    <v-list-item v-for="(item, i) in chat1" :key="i">-->
          <!--                      <v-list-item-icon>-->
          <!--                        <v-icon v-text="item.icon"></v-icon>-->
          <!--                      </v-list-item-icon>-->
          <!--                      <v-list-item-content>-->
          <!--                        <v-list-item-title-->
          <!--                            class="channel-title"-->
          <!--                            v-text="item.text"-->
          <!--                        ></v-list-item-title>-->
          <!--                      </v-list-item-content>-->
          <!--                    </v-list-item>-->
          <!--                  </v-list-item-group>-->
          <!--                  </v-list>-->
          <!--              </v-expansion-panel-content>-->
          <!--            </v-expansion-panel>-->
          <!--          </v-expansion-panels>-->
          <!-- TODO expansion needed! -->
          <v-list width="100%" rounded dense>
            <!--            <v-subheader>今天</v-subheader>-->
            <v-list-item-group
                v-model="channelNum"
                color="primary">
              <v-list-item
                  v-for="(item, i) in chat1"
                  @click="selectMessage(i)"
                  :key="i"

              >
                <v-list-item-icon>
                  <v-icon v-text="item.icon"></v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-list-item-title
                      class="channel-title"
                      v-text="item.text"
                  ></v-list-item-title>
                  <v-list-item-subtitle
                      class="channel-title"
                      v-text="item.message">
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
            <!--            <v-subheader>更早</v-subheader>-->
            <!--            <v-list-item-group  color="primary">-->
            <!--              <v-list-item-->
            <!--                  v-for="(item, i) in chat2"-->
            <!--                  @click="selectMessage(i)"-->
            <!--                  :key="i">-->
            <!--                <v-list-item-icon>-->
            <!--                  <v-icon v-text="item.icon"></v-icon>-->
            <!--                </v-list-item-icon>-->
            <!--                <v-list-item-content>-->
            <!--                  <v-list-item-title-->
            <!--                      class="channel-title"-->
            <!--                      v-text="item.text"-->
            <!--                  ></v-list-item-title>-->
            <!--                  <v-list-item-subtitle-->
            <!--                      class="channel-title"-->
            <!--                      v-text="item.message">-->
            <!--                  </v-list-item-subtitle>-->
            <!--                </v-list-item-content>-->
            <!--              </v-list-item>-->
            <!--            </v-list-item-group>-->
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
      chat1: [
        {text: "Rose", icon: "mdi-emoticon-kiss-outline", message: "晚安哦~"},
        {text: "Peter", icon: "mdi-emoticon-confused-outline", message: "上号！"},
        {text: "Mike", icon: "mdi-emoticon-lol-outline", message: "bbzl"},
        {text: "James", icon: "mdi-emoticon-wink-outline", message: "i want more ♂"},
        {text: "Jason", icon: "mdi-emoticon-excited-outline", message: "lol"},
        {text: "WeiHuang", icon: "mdi-emoticon-poop-outline", message: "summit issues!"}
      ],
      chat2: [
        {text: "James", icon: "mdi-emoticon-wink-outline", message: "i want more ♂"},
        {text: "Jason", icon: "mdi-emoticon-excited-outline", message: "lol"},
        {text: "WeiHuang", icon: "mdi-emoticon-poop-outline", message: "summit issues!"}
      ],
      items: [

      ],
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
    search() {
      this.$refs.search.blur()
      console.log(this.text)
      // this.$router.push()
    },
    toggleAC() {
      this.$store.commit("toggleAC");
    },
    selectMessage() {

    }
  },
  updated() {
    console.log(this.channelNum)
    this.$store.commit("changeChannel", this.channelNum);
  },
  mounted() {
    this.$store.commit("updateChannels", this.chat1);
  }
}
</script>

<style scoped>

</style>