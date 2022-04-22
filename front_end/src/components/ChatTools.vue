<template>
  <v-card
    :class="draw ? 'members-open' : 'members-close'"
    dark
  >
    <v-list>
      <v-list-item-group
          v-model="model"
          mandatory
          color="indigo"
      >
        <v-list-item
            v-for="(item, i) in items"
            :key="i"
        >
          <v-list-item-icon>
            <v-icon @click.stop="draw = !draw" v-text="item.icon"></v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title v-text="item.text"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>

    <v-navigation-drawer
      temporary
      v-model="draw"
      dark
      absolute
      right
       >
      <v-timeline>
        <v-timeline-item>timeline item</v-timeline-item>
        <v-timeline-item class="text-right">
          timeline item
        </v-timeline-item>
        <v-timeline-item>timeline item</v-timeline-item>
      </v-timeline>
    </v-navigation-drawer>
  </v-card>
</template>

<script>
export default {
  name: "ChatTools.vue",
  data () {
    return {
      draw: null,
      items: [
        {
          icon: 'mdi-wifi',
          text: 'Timeline',
        },
        {
          icon: 'mdi-bluetooth',
          text: '',
        },
        {
          icon: 'mdi-chart-donut',
          text: '',
        },
      ],
      model: 1,
    }
  },
}

</script>

<style scoped>

</style>