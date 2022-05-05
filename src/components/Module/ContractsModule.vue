<template>
  <div style="height:100%; padding: 0px; margin: 0px; border: 1px solid red;">
    <v-row style="height:93%; border: 1px solid red;">
      <div style="height:100%; width: 20%;  border: 1px solid red;">
        <group-and-friend v-if="show == 0"></group-and-friend>
        <add-member v-else-if="show == 1"></add-member>
        <apply-list v-else></apply-list>
      </div>
      <div style="height:100%; width: 80%; border: 1px solid red;">
        <info-tool></info-tool>
      </div>
    </v-row>
    <div style="height:7%; border: 1px solid red;">
      <v-bottom-navigation
        color="blue"
        dark
        mandatory
        height="100%"
        grow
        shift
      >
        <v-btn @click="show1">
          <span>通讯录</span>
          <v-icon>mdi-account-group</v-icon>
        </v-btn>

        <v-btn @click="show2">
          <span>新朋友/团队</span>
          <v-icon>mdi-account-multiple-plus</v-icon>
        </v-btn>

        <!-- <v-btn @click="show3">
        <v-icon>mdi-email-plus-outline</v-icon>
      </v-btn> -->
      </v-bottom-navigation>
    </div>
  </div>
</template>

<script>
import '../../api/addresslist/index'
import AddMember from './Contracts/AddMem/Addmember.vue'
import ApplyList from './Contracts/AddMem/ApplyList.vue'
import InfoTool from './Contracts/InfoTool.vue'
import GroupAndFriend from './Contracts/MemberList.vue'
export default {
  components: {
    AddMember,
    GroupAndFriend,
    ApplyList,
    InfoTool,
  },

  data () {
    return {
      itemss: [{
        text: "Announcements",
        icon: "mdi-bell-alert"
      }, {
        text: "Rules",
        icon: "mdi-file-check"
      }, {
        text: "Welcome",
        icon: "mdi-emoticon"
      }],
      member: 0,
      show: 0,
      values: [true, false, false]
    };
  },

  methods: {
    toggleAC () {
      this.$store.commit("toggleAC");
    },

    method1 () {
      console.assert(1)
    },
    show1 () {
      this.show = 0;
    },
    show2 () {
      this.show = 1;
    },
    show3 () {
      this.show = 2;
    },
  },

  mounted () {
  },
};
</script>