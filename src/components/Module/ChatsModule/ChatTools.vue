<template>
  <div class="members hidden-sm-and-down">
    <v-card dark>
      <v-list
        dark
        v-for="(item, i) in tools"
        :key="i"
      >
        <v-list-item-icon>
          <v-icon
            v-text="item.icon"
            style="margin-left: 30%;"
            @click="changeBand(i)"
          ></v-icon>
        </v-list-item-icon>
      </v-list>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "ChatTools.vue",
  props: ['draw', 'tools'],
  data () {
    return {
      model: 1,
      tempDraw: false,
    }
  },
  methods: {
    changeBand (index) {
      if (!this.tempDraw) {
        this.tempDraw = !this.tempDraw
        this.tools[index].show = true
      }
      else {
        if (this.tools[index].show) {
          this.tempDraw = !this.tempDraw
          this.tools[index].show = false
        }
        else {
          for (let i = 0; i < this.tools.length; i++) {
            this.tools[i].show = i === index;
          }
        }
      }
      //用来初始化info
      // if (this.tools[1].show) {
      //   const that = this.$parent.$refs.infoPage
      //   that.id = this.$store.state.currentChannelId
      //   if (this.$store.state.currentChatType == 'private') {
      //     that.about = 0
      //     that.$refs.infoF.initInfo()
      //   }
      //   else {
      //     that.about = 1
      //     that.$refs.infoG.initInfo()
      //     that.$refs.infoG.memberShow = false
      //     that.$refs.infoG.iShowTrue()
      //   }
      // }
      this.$emit("callback", this.tempDraw)
    },
  },
  watch: {
    draw (newVal, oldVal) {
      oldVal
      this.tempDraw = this.draw
    }
  }
}

</script>

<style scoped>
</style>