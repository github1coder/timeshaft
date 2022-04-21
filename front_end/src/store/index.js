import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    channels: [],
    currentChannel: 0,
    myIcon: "guest.png",
    ownerIcon: "https://media.discordapp.net/attachments/603940670914297867/685302017165623352/plusrekt1.jpg?width=513&height=513",
    myNick: "noname",
    members: [],
    member: 0,
    membersComponentMounted: false,
    showAC: false,
    chats: [],
    loggedIn: false,
  },
  mutations: {
    updateChannels(store, list) {
      store.channels = list;
    },
    changeChannel(store, channel) {
      store.currentChannel = channel;
    },
    updateAvatar(store, url) {
      store.myIcon = url;
    },
    membersMounted(store) {
      store.membersComponentMounted = true;
    },
    pushMembers(store, data) {
      if (!data.id)
        for (let i in data) {
          store.members.push({
            name: data[i].nick,
            img: data[i].img || "guest.png",
            sid: i
          });
        }
      else
        store.members.push({
          name: data.user.nick,
          img: data.user.img || "guest.png",
          sid: data.id
        });
    },
    removeMember(store, sid) {
      for (let i in store.members) {
        if (store.members[i].sid == sid) {
          store.members.splice(i, 1);
        }
      }
    },
    toggleAC(store) {
      store.showAC = !store.showAC;
    },

    pushChat(store, data) {
      store.chats.push({
        avatar: data.user.img || "guest.png",
        title: data.user.nick,
        subtitle: data.msg
      });
      setTimeout(() => {
        let msgElm = window.document.getElementsByClassName("messages")[0];
        //msgElm.scrollTo(0, msgElm.scrollHeight);
        msgElm.scrollTop = msgElm.scrollHeight;
      }, 10)
    },

    setNick(store, nick) {
      store.myNick = nick;
    },

    setLogin(store, bool) {
      store.loggedIn = bool;
    }
  },
  actions: {
  },
  modules: {
  }
})
