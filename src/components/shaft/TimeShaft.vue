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
              <div class="text-h5 font-weight-light" style="font-size: 30px">
                Time Shaft
              </div>
              <div class="text-uppercase font-weight-light" style="font-size: 15px">
                {{$data.time}}
              </div>
            </v-row>
          </v-row>
        </v-container>
      </v-img>
    </v-card>

    <v-card
        class="overflow-x-hidden overflow-y-auto"
        max-height="65vh"
    >
      <v-timeline
          align-top
          dense
      >
        <v-timeline-item
            small
            v-for="(item, i) in items"
            :key="i"
            :color="timecolor[i % 10]"

        >
          <v-row class="pt-1">
            <v-col cols="4" style="width: 50px">
              <strong style="margin-left: 0; font-size: 5px; padding-left: 0">{{item.date}}</strong>
            </v-col>
            <v-col>
              <strong>会议主题：{{item.title}}</strong>
              <div class="text-caption">
                会议摘要：{{ item.conclude }}
              </div>
              <v-row>
                <v-avatar>
                  <v-img
                      :src="item.img"
                  ></v-img>
                </v-avatar>
                <a style="color: #78909C; margin-top: 5%">会议发起者：{{item.host}}</a>
              </v-row>
            </v-col>
          </v-row>
        </v-timeline-item>

      </v-timeline>
    </v-card>
  </v-card>
</template>
<script>
export default {
  name: "TimeShft",
  data() {
    return {
      //显示时间
      time: '',
      timecolor: [
          'deep-orange','deep-orange lighten-5','deep-orange lighten-4', 'deep-orange lighten-3',
          'deep-orange lighten-2', 'deep-orange lighten-1', 'deep-orange darken-1','deep-orange darken-2',
          'deep-orange darken-3', 'deep-orange darken-4'
      ],
      items: [
        {title: 'GOGOGO', img: "https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned", date: '2022.5.3 11:20-14:25', conclude: 'vue标签属性绑定中的字符串拼接：写法有两种：:title="`字符串${xx}`"   或   :title="\'字符串\' + xx"  都可以。其中，{}里面可以写js方法', host: 'hw'},
        {title: 'Ahhaha', img: "https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned", date: '2022.5.3 16:23-17:30', conclude: '1111', host: 'zzy'},
        {title: 'Ahhaha', img: "https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned", date: '2022.5.3 16:23-17:30', conclude: '1111', host: 'zzy'},
        {title: 'Ahhaha', img: "https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned", date: '2022.5.3 16:23-17:30', conclude: '1111', host: 'zzy'},
        {title: 'Ahhaha', img: "https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned", date: '2022.5.3 16:23-17:30', conclude: '1111', host: 'zzy'},
        {title: 'GOGOGO', img: "https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned", date: '2022.5.3 11:20-14:25', conclude: 'vue标签属性绑定中的字符串拼接：写法有两种：:title="`字符串${xx}`"   或   :title="\'字符串\' + xx"  都可以。其中，{}里面可以写js方法', host: 'hw'},
        {title: 'GOGOGO', img: "https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned", date: '2022.5.3 11:20-14:25', conclude: 'vue标签属性绑定中的字符串拼接：写法有两种：:title="`字符串${xx}`"   或   :title="\'字符串\' + xx"  都可以。其中，{}里面可以写js方法', host: 'hw'},
        {title: 'GOGOGO', img: "https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned", date: '2022.5.3 11:20-14:25', conclude: 'vue标签属性绑定中的字符串拼接：写法有两种：:title="`字符串${xx}`"   或   :title="\'字符串\' + xx"  都可以。其中，{}里面可以写js方法', host: 'hw'},
        {title: 'GOGOGO', img: "https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned", date: '2022.5.3 11:20-14:25', conclude: 'vue标签属性绑定中的字符串拼接：写法有两种：:title="`字符串${xx}`"   或   :title="\'字符串\' + xx"  都可以。其中，{}里面可以写js方法', host: 'hw'},
        {title: 'GOGOGO', img: "https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned", date: '2022.5.3 11:20-14:25', conclude: 'vue标签属性绑定中的字符串拼接：写法有两种：:title="`字符串${xx}`"   或   :title="\'字符串\' + xx"  都可以。其中，{}里面可以写js方法', host: 'hw'},
        {title: 'GOGOGO', img: "https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned", date: '2022.5.3 11:20-14:25', conclude: 'vue标签属性绑定中的字符串拼接：写法有两种：:title="`字符串${xx}`"   或   :title="\'字符串\' + xx"  都可以。其中，{}里面可以写js方法', host: 'hw'},
        {title: 'GOGOGO', img: "https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned", date: '2022.5.3 11:20-14:25', conclude: 'vue标签属性绑定中的字符串拼接：写法有两种：:title="`字符串${xx}`"   或   :title="\'字符串\' + xx"  都可以。其中，{}里面可以写js方法', host: 'hw'},

      ]
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
  computed: {
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