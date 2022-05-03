<template>
  <v-card
      class="mx-auto"
      height="100%"
  >
    <v-card
        dark
        flat
    >
      <v-btn
          absolute
          bottom
          color="pink"
          right
          fab
      >
        <v-icon>mdi-plus</v-icon>
      </v-btn>

      <v-img
          src="https://cdn.vuetifyjs.com/images/cards/forest.jpg"
          gradient="to top, rgba(0,0,0,.44), rgba(0,0,0,.44)"
          height="150px"
      >
        <v-container class="fill-height">
          <v-row align="center">
            <v-row justify="end">
              <div class="text-h5 font-weight-light" style="font-size: 50px">
                Time Shaft
              </div>
              <div class="text-uppercase font-weight-light" style="font-size: 25px">
                {{$data.time}}
              </div>
            </v-row>
          </v-row>
        </v-container>
      </v-img>
    </v-card>
    <v-card-text class="py-0">
      <v-timeline
          align-top
          dense
      >
        <v-timeline-item
            color="pink"
            small
        >
          <v-row class="pt-1">
            <v-col cols="3">
              <strong>5pm</strong>
            </v-col>
            <v-col>
              <strong>New Icon</strong>
              <div class="text-caption">
                Mobile App
              </div>
            </v-col>
          </v-row>
        </v-timeline-item>

        <v-timeline-item
            color="teal lighten-3"
            small
        >
          <v-row class="pt-1">
            <v-col cols="3">
              <strong>3-4pm</strong>
            </v-col>
            <v-col>
              <strong>Design Stand Up</strong>
              <div class="text-caption mb-2">
                Hangouts
              </div>
              <v-avatar>
                <v-img
                    src="https://avataaars.io/?avatarStyle=Circle&topType=LongHairFrida&accessoriesType=Kurt&hairColor=Red&facialHairType=BeardLight&facialHairColor=BrownDark&clotheType=GraphicShirt&clotheColor=Gray01&graphicType=Skull&eyeType=Wink&eyebrowType=RaisedExcitedNatural&mouthType=Disbelief&skinColor=Brown"
                ></v-img>
              </v-avatar>
              <v-avatar>
                <v-img
                    src="https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned"
                ></v-img>
              </v-avatar>
              <v-avatar>
                <v-img
                    src="https://avataaars.io/?avatarStyle=Circle&topType=LongHairMiaWallace&accessoriesType=Sunglasses&hairColor=BlondeGolden&facialHairType=Blank&clotheType=BlazerSweater&eyeType=Surprised&eyebrowType=RaisedExcited&mouthType=Smile&skinColor=Pale"
                ></v-img>
              </v-avatar>
            </v-col>
          </v-row>
        </v-timeline-item>

        <v-timeline-item
            color="pink"
            small
        >
          <v-row class="pt-1">
            <v-col cols="3">
              <strong>12pm</strong>
            </v-col>
            <v-col>
              <strong>Lunch break</strong>
            </v-col>
          </v-row>
        </v-timeline-item>

        <v-timeline-item
            color="teal lighten-3"
            small
        >
          <v-row class="pt-1">
            <v-col cols="3">
              <strong>9-11am</strong>
            </v-col>
            <v-col>
              <strong>Finish Home Screen</strong>
              <div class="text-caption">
                Web App
              </div>
            </v-col>
          </v-row>
        </v-timeline-item>
        <v-timeline-item
            color="teal lighten-3"
            small
        >
          <v-row class="pt-1">
            <v-col cols="3">
              <strong>9-11am</strong>
            </v-col>
            <v-col>
              <strong>Finish Home Screen</strong>
              <div class="text-caption">
                Web App
              </div>
            </v-col>
          </v-row>
        </v-timeline-item>
        <v-timeline-item
            color="teal lighten-3"
            small
        >
          <v-row class="pt-1">
            <v-col cols="3">
              <strong>9-11am</strong>
            </v-col>
            <v-col>
              <strong>Finish Home Screen</strong>
              <div class="text-caption">
                Web App
              </div>
            </v-col>
          </v-row>
        </v-timeline-item>
        <v-timeline-item
            color="teal lighten-3"
            small
        >
          <v-row class="pt-1">
            <v-col cols="3">
              <strong>9-11am</strong>
            </v-col>
            <v-col>
              <strong>Finish Home Screen</strong>
              <div class="text-caption">
                Web App
              </div>
            </v-col>
          </v-row>
        </v-timeline-item>
        <v-timeline-item
            color="teal lighten-3"
            small
        >
          <v-row class="pt-1">
            <v-col cols="3">
              <strong>9-11am</strong>
            </v-col>
            <v-col>
              <strong>Finish Home Screen</strong>
              <div class="text-caption">
                Web App
              </div>
            </v-col>
          </v-row>
        </v-timeline-item>
      </v-timeline>
    </v-card-text>
  </v-card>
</template>
<script>
export default {
  name: "TimeShft",
  data() {
    return {
      //显示时间
      time: '',

    }
  },
  mounted() {
    this.dateShow();
  },
  created() {
    this.dateFormat();
  },
  beforeDestroy() {
    this.dataDestroy();
  },
  methods: {
    dataDestroy() {
      if (this.timer) {
        clearInterval(this.timer); // 在Vue实例销毁前，清除我们的定时器
      }

    },
    dateShow() {
      this.timer = setInterval(() => {
        this.dateFormat()
      }, 1000)

    },
    dateFormat() {
      let date = new Date();
      let year = date.getFullYear();
      let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      this.time = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    },
  }
}
</script>

<style scoped>

</style>