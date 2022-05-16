<template>
  <div style="height:100%; padding: 0px; margin: 0px;">
    <v-row
      style="height:93%;"
      v-if="show == 0"
    >
      <div style="height:100%; width: 20%;">
        <member-list
          ref="MemberList"
          v-if="show == 0"
        ></member-list>
      </div>
      <div style="height:100%; width: 80%;">
        <info-tool ref="infoTool"></info-tool>
      </div>
    </v-row>
    <add-tool
      style="height:93%;"
      v-if="show == 1"
      ref="addTool"
    ></add-tool>
    <apply-tool
      style="height:93%;"
      v-if="show == 2"
      ref="applyTool"
    ></apply-tool>
    <div style="height:7%;">
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
          <span>添加朋友/团队</span>
          <v-icon>mdi-account-multiple-plus</v-icon>
        </v-btn>

        <v-btn @click="show3">
          <span>好友/团队邀请</span>
          <v-icon>mdi-email-plus-outline</v-icon>
        </v-btn>
      </v-bottom-navigation>
    </div>
  </div>
</template>

<script>
import '../../api/addresslist/index'
import ApplyTool from './Contacts/ApplyTool.vue'
import AddTool from './Contacts/AddTool.vue'
import InfoTool from './Contacts/InfoTool.vue'
import MemberList from './Contacts/MemberList.vue'
export default {
  components: {
    AddTool,
    MemberList,
    InfoTool,
    ApplyTool,
  },

  data () {
    return {
      member: 0,
      show: 0,
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