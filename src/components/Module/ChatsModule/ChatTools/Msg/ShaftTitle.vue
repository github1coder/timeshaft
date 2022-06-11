<template>
  <v-card
    dark
    flat
  >
    <v-img
      src="https://cdn.vuetifyjs.com/images/cards/forest.jpg"
      gradient="to top, rgba(0,0,0,.44), rgba(0,0,0,.44)"
      height="150px"
    >
      <v-container class="fill-height">
        <div style="
                      color: white;
                      margin: auto;
                      font-size: 30px;
                      width: 100%;
                    ">
          事件·Time Shaft
        </div>
        <div style="font-size: 15px; margin: auto;">
          {{$data.time}}
        </div>
      </v-container>
    </v-img>
  </v-card>
</template>

<script>

export default {
  components: {
  },

  data () {
    return {
    }
  },
  mounted () {
    this.dateShow();
  },
  created () {
    this.dateFormat();
  },
  beforeDestroy () {
    this.dataDestroy();
  },
  methods: {
    dateShow () {
      this.timer = setInterval(() => {
        this.dateFormat()
      }, 1000)
    },
    dateFormat () {
      let date = new Date();
      let year = date.getFullYear();
      let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      this.time = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    },

    dataDestroy () {
      if (this.timer) {
        clearInterval(this.timer); // 在Vue实例销毁前，清除我们的定时器
      }
    },
  }
}

</script>