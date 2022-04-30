import Vue from 'vue'
import Vuex from 'vuex'

import Stomp from 'stompjs'
import SockJS from 'sockjs-client'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        userId: -1,
        email: null,
        accessToken: null,
        currentChannelIdx: 0,
        currentChannelId: null,
        myIcon: "guest.png",
        // ownerIcon: "https://media.discordapp.net/attachments/603940670914297867/685302017165623352/plusrekt1.jpg?width=513&height=513",
        ownerIcon: "guest.png",
        myNick: "noname",
        members: [],
        member: 0,
        membersComponentMounted: false,
        showAC: false,
        chats: [],
        loggedIn: false,
        siderState: 0,
        // socket 相关
        url: "http://182.92.163.68:8080/websocket",
        checkInterval: null,//断线重连时 检测连接是否建立成功
        websocket: null,
        stompClient: null,
        listenerList: {},//监听器列表，断线重连时 用于重新注册监听
        // item : {topic: "string", data: {}}
        //
    },
    getters: {
        userId: state => state.userId,
        stompClient(state) {
            return function () {
                return state.stompClient;
            }
        },
    },
    mutations: {

        setUserId(store, userId) {
            store.userId = userId
        },
        setEmail(store, email) {
            store.email = email
        },
        setMyIcon(store, myIcon) {
            store.myIcon = myIcon
        },
        setMyNick(store, myNick) {
            store.myNick = myNick
        },
        changeSiderState(store, s) {
            console.log(s)
            store.siderState = s
            console.log(s)
        },
        updateChannels(store, list) {
            store.listenerList = list;
        },
        changeChannel(store, data) {
            store.currentChannelId = data.id;
            store.currentChannelIdx = data.idx;
            // console.log(store.currentChannelId + " " + store.currentChannelIdx);
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
                if (store.members[i].sid === sid) {
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

        setLogin(store, bool) {
            store.loggedIn = bool;
        },

        // ------------------------------- 以上全是原版 目前原封不动 ----------------------------------//

        WEBSOCKET_INIT(state, url) {
            if (state.stompClient == null || !state.stompClient.connected) {
                state.url = url
                if (state.stompClient != null && state.websocket.readyState === SockJS.OPEN) {
                    state.stompClient.disconnect(() => {
                        this.commit('WEBSOCKET_CONNECT')
                    })
                } else if (state.stompClient != null && state.websocket.readyState === SockJS.CONNECTING) {
                    console.log("连接正在建立")
                    return;
                } else {
                    console.log("第一次建立")
                    this.commit('WEBSOCKET_CONNECT')
                }
                if (!state.checkInterval) {
                    state.checkInterval = setInterval(() => {
                        console.log("检测连接：" + state.websocket.readyState)
                        if (state.stompClient != null && state.stompClient.connected) {
                            clearInterval(state.checkInterval)
                            state.checkInterval = null
                            console.log('重连成功')
                        } else if (state.stompClient != null && state.websocket.readyState !== SockJS.CONNECTING) {
                            //经常会遇到websocket的状态为open 但是stompClient的状态却是未连接状态，故此处需要把连接断掉 然后重连
                            state.stompClient.disconnect(() => {
                                this.commit('WEBSOCKET_CONNECT')
                            })
                        }
                    }, 2000)
                }
            } else {
                console.log("连接已建立成功，不再执行")
            }
        },
        WEBSOCKET_CONNECT(state) {
            console.log("--")
            const _this = this
            const websock = new SockJS(state.url);
            console.log("--")
            console.log(websock)
            console.log(state.url)
            console.log("--")
            state.websocket = websock
            // 获取STOMP子协议的客户端对象
            const stompClient = Stomp.over(websock);
            console.log(stompClient)
            console.log("---")
            stompClient.debug = null //关闭控制台打印
            stompClient.heartbeat.outgoing = 20000;
            stompClient.heartbeat.incoming = 0;//客户端不从服务端接收心跳包
            // 向服务器发起websocket连接
            stompClient.connect(
                {name: state.myNick},  //此处注意更换自己的用户名，最好以参数形式带入
                frame => { // eslint-disable-line no-unused-vars
                    console.log('链接成功！')
                    for (let listenerListKey in state.listenerList) {
                        console.log(state.listenerList[listenerListKey].url + "?")
                        state.stompClient.subscribe(state.listenerList[listenerListKey].url, state.listenerList[listenerListKey].callback)
                    }
                },
                err => {// eslint-disable-line no-unused-vars
                    setTimeout(() => {
                        console.log("reconnecting...")
                        _this.commit('WEBSOCKET_INIT', state.url)
                    }, 20000)
                }
            );
            state.stompClient = stompClient
        },
        WEBSOCKET_SEND(state, p) {
            state.stompClient.send(p.url, {}, p.message);
        },
        WEBSOCKET_UNSUBSRCIBE(state, p) {
            state.stompClient.unsubscribe(p)
            for (let i = 0; i < state.listenerList.length; i++) {
                if (state.listenerList[i].url === p) {
                    state.listenerList.splice(i, 1)
                    console.log("解除订阅：" + p + " size:" + state.listenerList.length)
                    break;
                }
            }
        },

        WEBSOCKET_RECEIVE(id, data) {
            let friend;
            for (friend in this.$store.state.listenerList) {
                if (id === friend.id) {
                    break;
                }
            }
            friend.historyData.push(data)
        },

        // ---------------------- 以上是 Stomp 封装 请谨慎修改 --------------------------- //

        initListenerList(state, res) {
            for (let itemKey in res) {
                if (state.listenerList[res[itemKey].id] === undefined) {
                    state.listenerList[res[itemKey].id] = res[itemKey]
                } else {
                    // 更新已有id的信息
                    let tmp = state.listenerList[res[itemKey].id]
                    tmp.chatName = res[itemKey].chatName
                    tmp.avatar = res[itemKey].avatar
                    tmp.data.append(res[itemKey].data)
                    state.listenerList[res[itemKey].id] = tmp
                }
            }
            console.log("!!!")
            console.log(state.listenerList)
        }
        // --------------------- 通讯录管理 -----------------------------//
    },
    modules: {}
})