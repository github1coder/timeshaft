<template>
  <!-- <v-dialog
    persistent
    v-model="show"
    max-width="600px"
  >
    <div style="width: 50%; float: left">123</div>
    <div style="width: 50%; float: right">
      <History
        :message="message"
        v-if="show"
      ></History>
    </div>
  </v-dialog> -->
  <div>
    <v-dialog
      v-model="show"
      persistent
      fullscreen
    >
      <v-dialog
        v-model="dialog"
        max-width="300"
      >
        <v-card>
          <v-card-title></v-card-title>
          <v-card-text style="font-size: 20px; font-weight: bold; margin-top: 10px;">
            删除该时间轴记录？
          </v-card-text>
          <v-card-actions>
            <v-btn
              color="green darken-1"
              @click="dialog = false; delNode()"
              style="width: 35%; margin-left:10%;"
            >
              确定
            </v-btn>

            <v-btn
              color="green darken-1"
              @click="dialog = false"
              style="width: 35%; margin-left:10%;"
            >
              取消
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-card style="width: 100%;height: 100%;">
        <div style="height: 100%; width: 50%; float: left;">
          <div style="width: 80%; height: 90%; margin: auto;">
            <v-card-title style=" font-size: 40px; font-weight: bold; width: 80%; line-height: 50px; text-align: left;">
              主题：{{data.title}}
            </v-card-title>
            <div style="position: fixed; top: 1rem; left: 40%;">
              <v-btn
                color="blue-grey lighten-4"
                rounded
                :loading="changing"
                @click="changeState"
                width="150px"
              >
                {{stateText}}
              </v-btn>
            </div>
            <v-divider></v-divider>
            <small style="align: left; text-align: left; font-size: 10px">{{data.name}}创建于{{data.startTime}}~{{data.endTime}}</small>
            <v-divider></v-divider>
            <small style="text-align: left;">时间轴链接：{{data.key}}</small>
            <v-divider></v-divider>
            <v-card-text
              style="text-align: left; font-size: 20px; font-weight: bold"
              v-if="!updateTags && isManager"
            >
              标签:
              <v-chip
                class="ma-2"
                color="pink"
                label
                text-color="white"
                v-if="data.tags && data.tags[0]"
              >
                <v-icon left>mdi-label</v-icon>
                {{data.tags[0]}}
              </v-chip>
              <v-chip
                class="ma-2"
                color="pink"
                label
                text-color="white"
                v-if="data.tags && data.tags[1]"
              >
                <v-icon left>mdi-label</v-icon>
                {{data.tags[1]}}
              </v-chip>
              <v-chip
                class="ma-2"
                color="pink"
                label
                text-color="white"
                v-if="data.tags && data.tags[2]"
              >
                <v-icon left>mdi-label</v-icon>
                {{data.tags[2]}}
              </v-chip>
              <v-icon @click="isupdateTags">
                mdi-pencil-outline
              </v-icon>
            </v-card-text>
            <v-card-text
              style="text-align: left; font-size: 20px; font-weight: bold"
              v-if="updateTags && isManager"
            >
              <v-layout
                row
                wrap
                style="text-align: center;"
              >
                标签:
                <v-flex
                  xs6
                  sm3
                  style="margin-left: 10px;"
                >
                  <v-combobox
                    id="tag0"
                    counter=5
                    requried
                    label="添加至少一个标签"
                    v-model="data.tags[0]"
                    :items="allTags"
                  ></v-combobox>
                </v-flex>
                <v-flex
                  xs4
                  sm3
                  style="margin-left: 10px;"
                >
                  <v-combobox
                    id="tag1"
                    counter=5
                    v-model="data.tags[1]"
                    :items="allTags"
                  ></v-combobox>
                </v-flex>
                <v-flex
                  xs6
                  sm3
                  style="margin-left: 10px;"
                >
                  <v-combobox
                    id="tag2"
                    counter=5
                    v-model="data.tags[2]"
                    :items="allTags"
                  ></v-combobox>
                </v-flex>
                <v-icon
                  @click="isupdateTags"
                  style="margin: auto"
                >
                  mdi-check-bold
                </v-icon>
              </v-layout>
            </v-card-text>
            <v-divider></v-divider>
            <v-card-subtitle style="font-size: 25px;">
              摘要
              <v-icon
                @click="isupdateConclude"
                v-if="!updateConclude && isManager"
              >
                mdi-pencil-outline
              </v-icon>
              <v-icon
                @click="isupdateConclude"
                v-if="updateConclude && isManager"
              >
                mdi-check-bold
              </v-icon>
            </v-card-subtitle>
            <v-card-text
              style="text-indent:2em; text-align: left; font-size: 20px;"
              v-if="!updateConclude"
            >
              {{data.conclude}}
            </v-card-text>
            <v-textarea
              style="text-indent:2em; text-align: left; font-size: 20px;"
              v-if="updateConclude"
              v-model="data.conclude"
              auto-grow
            >
            </v-textarea>
          </div>
          <div style="height: 5%;">
            <v-btn
              v-if="!self"
              color="gray darken-1"
              text
              style="font-size: 20px; margin-left: 10%; width: 20%; background-color: green; margin-top: auto; font-weight: bold"
              @click="starThis"
              :disabled="data.star"
            >
              {{starHint}}
            </v-btn>
            <v-btn
              v-if="self"
              color="gray darken-1"
              text
              style="font-size: 20px; margin-left: 10%; width: 20%; background-color: green; margin-top: auto; font-weight: bold"
              @click="notStarThis"
            >
              取消收藏
            </v-btn>
            <v-btn
              color="gray darken-1"
              text
              style="font-size: 20px; margin-left: 10%; width: 20%; background-color: pink; margin-top: auto; font-weight: bold"
              @click="close"
            >
              关闭
            </v-btn>
            <v-btn
              color="gray darken-1"
              text
              style="font-size: 20px; margin-left: 10%; width: 20%; background-color: red; margin-top: auto; font-weight: bold"
              @click="dialog = true"
              v-if="isManager"
            >
              删除
            </v-btn>
            <v-spacer></v-spacer>
          </div>
        </div>
        <div
          class="msg-info"
          style="height: 100%; width: 50%; float: right"
        >
          <History
            :messages="data.message"
            v-if="show"
          ></History>
        </div>
      </v-card>

    </v-dialog>
  </div>
</template>

<script>
import History from "./History.vue"
import { getSingleTimeshaft, delTimeshaft, updateTimeNode, updateTimeState, starTimeNode } from "@/api/timeShaft"

export default {
  components: { History },

  props: ["id", "isManager", "allTags", "stared", "self"],

  mounted () {
    getSingleTimeshaft({
      timeshaft_id: this.id
    }).then(res => {
      console.log(res)
      this.data = res
      this.stateText = res.state ? "公开" : "本团队/好友可见"
      this.state = res.state
      this.starHint = res.star ? "已收藏" : "收藏"
    })
  },

  data () {
    return {
      show: true,
      data: {},
      messages: [],
      dialog: false,
      updateTags: false,
      updateConclude: false,
      selects: [false, false, false],
      flashT: false,
      stateText: "本团队/好友可见",
      state: false,
      changing: false,
      starHint: "收藏",
      starId: -1,
    }
  },

  beforeDestroy () {
  },
  methods: {
    starThis () {
      const that = this
      starTimeNode({
        "id": this.id,
        "star": true
      }).then(res => {
        if (!res || (res && !res.error)) {
          //正常返回
          that.data.star = true
          that.starHint = "已收藏"
          that.starId = that.id
        }
        else {
          //错误信息展示
        }
      })
    },

    notStarThis () {
      const that = this
      starTimeNode({
        "id": this.id,
        "star": false
      }).then(res => {
        if (!res || (res && !res.error)) {
          //正常返回
          that.starId = that.id
          that.close()
        }
        else {
          //错误信息展示
        }
      })
    },

    changeState () {
      console.log("修改时间轴状态")
      this.state = !this.state
      updateTimeState({
        "id": this.id,
        "state": this.state
      })
      if (!this.timer) {
        this.count = 1
        this.changing = true
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= 1) {
            this.count--
          } else {
            this.changing = false
            clearInterval(this.timer)
            this.timer = null
            this.stateText = this.state ? "公开" : "本团队/好友可见"
          }
        }, 1000)
      }
    },

    select (index) {
      this.selects[index] = true
    },

    isupdateTags () {
      if (this.updateTags == false) {
        this.updateTags = true
      }
      else {
        const tag0 = document.getElementById("tag0").value
        const tag1 = document.getElementById("tag1").value
        const tag2 = document.getElementById("tag2").value
        const that = this
        if (tag0.length == 0 || tag0.length > 5
          || (tag1 && tag1.length > 5)
          || (tag2 && tag2.length > 5)) {
          return
        }
        updateTimeNode({
          id: this.id,
          type: "tags",
          tags: [tag0, tag1, tag2],
          conclude: this.data.conclude,
        }).then(res => {
          if (!res || (res && !res.error)) {
            //正常返回
            that.updateTags = false
            that.flashT = true
            that.data.tags = [tag0, tag1, tag2]
          }
          else {
            //错误信息展示
          }
        })
      }
    },

    isupdateConclude () {
      if (this.updateConclude == false) {
        this.updateConclude = true
      }
      else {
        updateTimeNode({
          id: this.id,
          type: "conclude",
          tags: this.data.tags,
          conclude: this.data.conclude,
        })
        this.updateConclude = false
      }
    },
    close () {
      this.show = false
      this.$emit("closeT", this.show, this.flashT, this.starId)
    },

    copyClicked () {
      var input = document.createElement("input");   // js创建一个input输入框
      console.log(input)
      input.value = this.data.key;  // 将需要复制的文本赋值到创建的input输入框中
      document.body.appendChild(input);    // 将输入框暂时创建到实例里面
      input.select();   // 选中输入框中的内容
      console.log(input.value)
      document.execCommand("Copy") // 执行复制操作
      document.body.removeChild(input); // 最后删除实例中临时创建的input输入框，完成复制操作
    },

    delNode () {
      delTimeshaft({
        id: this.id,
      }).then(res => {
        if (!res || (res && !res.error)) {
          //正常返回
          this.close()
          if (this.$parent.$parent) {
            this.$parent.$parent.getShaft()
          }
        }
      })

    },
  }
}

</script>