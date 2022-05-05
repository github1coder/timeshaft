<template>
  <div>
    <div
        class="moveband"
        v-if="draw"
    >
      <TimeShaft v-if="tools[0].show"></TimeShaft>
    </div>
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
  </div>
</template>

<script>
import TimeShaft from "@/components/Module/ChatsModule/ChatTools/TimeShaft";
export default {
  name: "ChatTools.vue",
  components: {TimeShaft},
  props: ['draw'],
  data () {
    return {
      tools: [{
        icon: 'mdi-timeline',
        text: '',
        show: false
      }, {
        icon: 'mdi-cloud-search-outline',
        text: '',
        show: false
      }, {
        icon: 'mdi-cog-outline',
        text: '',
        show: false
      },
      ],
      model: 1,
    }
  },
  methods: {
    changeBand (index) {
      if (!this.draw) {
        this.draw = !this.draw
        this.tools[index].show = true
      }
      else {
        if (this.tools[index].show) {
          this.draw = !this.draw
          this.tools[index].show = false
        }
        else {
          for (let i = 0; i < this.tools.length; i++) {
            if (i === index) {
              this.tools[i].show = true
            }
            else {
              this.tools[i].show = false
            }
          }
        }
      }

    },
  }
}

</script>

<style scoped>

</style>