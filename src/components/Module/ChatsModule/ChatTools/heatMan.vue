<template>
  <div class="gogogo" style="position: fixed; top: 50%; right:30rem; font-weight: bold; height: 300px; width: 90px">
    <div id="js_wrap"
    :class="{'charector-wrap paused':this.heat === 0, 'charector-wrap slow':this.heat === 1, 'charector-wrap normal':this.heat === 2,
    'charector-wrap fast':this.heat === 3, 'charector-wrap xfast':this.heat === 4}"
    >
      <div class="charector"></div>
    </div>
  </div>
</template>


<!--<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>-->
<script>
import { getHeat } from "../../../../api/message";
export default {
  name: "heatMan",
  props: ["chatId", "type"],
  data () {
    return {
      heat: 0,
      timer: ''
    }
  },
  methods: {
    getMan () {
      console.log("the group id is")
      console.log(this.chatId)
      let para = {
        group_id: this.chatId,
        type: this.type === "group" ? "group" : "friend",
      }
      getHeat(para).then(res => {
        this.heat = res.heat
        console.log("aaaaaaaaaaaaaaaaaaaaaaaaaa")
        console.log(res)
      })
    },
  },
  created() {
    this.getMan()
  },
  mounted() {
    this.timer = setInterval(this.getMan, 300000);
    console.log(this.timer)
  },
  beforeDestroy() {
    clearInterval(this.timer);
  }
}

</script>

<style lang="scss">
@import "css/heat.css";
</style>