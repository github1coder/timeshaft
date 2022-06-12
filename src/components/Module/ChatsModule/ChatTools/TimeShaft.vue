<template>
  <div style="height: 100%; width: 100%">
    <ShaftTitle>
    </ShaftTitle>
    <ShaftBody
      ref="shaftBody"
      :chatId="chatId"
      :type="type"
      :self="false"
      :maxHeight="700"
    >
    </ShaftBody>
  </div>
</template>

<script>
import ShaftTitle from "./Msg/ShaftTitle.vue"
import ShaftBody from "./Msg/ShaftBody.vue"
export default {
  props: ['chatId', 'type'],
  components: {
    ShaftTitle,
    ShaftBody
  },

  data () {
    return {
    }
  },
  methods: {
  }
}

</script>