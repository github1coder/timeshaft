<template>
  <div style="height: 100%; width: 100%">
    <div style="float:left; height: 100%; width: 40%; margin-left: 5%; border: 5px solid green;">
      <h1 style="text-align: center; height: 60px;">我收藏的时间轴</h1>
      <v-divider>
      </v-divider>
      <ShaftBody
        ref="selfBody"
        :chatId="0"
        :type="'self'"
        :self="true"
        :maxHeight="900"
      >
      </ShaftBody>
    </div>
    <div style="float: right; height: 100%; width: 40%; margin-right: 5%; border: 5px solid green;">
      <v-row style="width: 80%; margin: auto; height: 60px;">
        <v-chip
          class="ma-2"
          color="primary"
          label
          style="margin: aut0;"
        >
          <v-icon left>mdi-account-circle-outline</v-icon>
          频道
        </v-chip>
        <v-combobox
          v-model="name"
          id="name"
          style="width: 70%; margin: auto;"
          requried
          :items="names"
        ></v-combobox>
      </v-row>
      <v-divider>
      </v-divider>
      <ShaftBody
        ref="allBody"
        :chatId="chatId"
        :type="type"
        :self="false"
        :maxHeight="800"
      >
      </ShaftBody>
    </div>
  </div>
</template>

<script>
import ShaftBody from "./ChatsModule/ChatTools/Msg/ShaftBody"
import { getAllChannel } from "../../api/addresslist/index"
export default {
  components: {
    ShaftBody
  },

  data () {
    return {
      channelHint: [{
        name: "1",
        id: 2,
      }, {
        name: "1",
        id: 2,
      },],
      name: "所有聊天",
      names: [],
      chatIds: [],
      types: [],
      selfTags: [],
      allTags: [],
      ischange: false,
      chatId: 0,
      type: "all",
    }
  },

  mounted () {
    var i = 1
    const that = this
    that.names.push("所有聊天")
    that.chatIds.push(0)
    that.types.push("all")
    getAllChannel().then(res => {
      if (!res || (res && !res.error)) {
        //正常返回
        res.forEach(function (item) {
          that.names.push(item.name)
          that.chatIds.push(item.chatId)
          that.types.push(item.type)
          if (item.type == 'group') {
            that.names[i] += '(团队)'
          }
          i++
        });
      }
      else {
        //错误信息展示
      }
    })
  },

  watch: {
    'name' (newValue, oldValue) {
      console.log(newValue, oldValue);
      this.flash(newValue)
    }
  },

  methods: {
    flash (name) {
      const index = this.names.findIndex(item => {
        return item == name
      })
      if (index != - 1) {
        this.chatId = this.chatIds[index]
        this.type = this.types[index]
        this.$refs.allBody.flashTags(this.chatId, this.type)
      }
    }
  }
}

</script>