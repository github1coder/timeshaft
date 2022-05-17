<template>
  <v-data-table
      :headers="this.headers"
      :items="this.items"
      :items-per-page="5"
      class="elevation-1"
  ></v-data-table>
</template>

<script>
export default {
name: "test",
  data() {
    return {
      items: [
        {
          name: "International Workshop on Languages and Compilers for Parallel Computing",
          num: "803"
        },
        {
          name: "ACM Transactions on Architecture and Code Optimization",
          num: "813"
        },
        {
          name: "Proceedings of the VLDB Endowment",
          num: "3189"
        },
        {
          name: "ACM/IEEE Design Automation Conference",
          num: "7772"
        },
        {
          name: "International Conference on Architectural Support for Programming Languages and Operating Systems",
          num: "1284"
        },
        {
          name: "International Conference on Compiler Construction",
          num: "596"
        },
        {
          name: "Transactions on Embedded Computing Systems ",
          num: "1476"
        },
        {
          name: "International Workshop on Mobile Computing Systems and Applications",
          num: "487"
        },
        {
          name: "ACM SIGPLAN Notice",
          num: "2544"
        },
        {
          name: "USENIX Symposium on Operating Systems Design and Implementation",
          num: "648"
        },
      ],
      headers: [
        {
          text: "conference",
          value: 'name'
        },
        {
          text: "num",
          value: 'num'
        }
      ]
    }
  }
}
</script>

<style scoped>

</style>