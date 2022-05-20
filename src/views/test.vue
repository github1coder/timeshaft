<template>
  <v-container fluid>
    <p>{{ selected }}</p>
    <v-checkbox
        v-model="selected"
        label=""
        value="John"
    ></v-checkbox>
    <v-checkbox
        v-model="selected"
        label=""
        value="Jacob"
    ></v-checkbox>
  </v-container>
</template>
<script>
export default {
  name: "test",
  data () {
    return {
      selected: ['John'],
    }
  },
}
</script>

<style scoped>

</style>