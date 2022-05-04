import Vue from 'vue'
import Vuex from 'vuex'

import Stomp from 'stompjs'
import SockJS from 'sockjs-client'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        more: true,
        DEBUG: false,
        userId: -1,
        email: null,
        accessToken: null,
        groupId: -1,
        groupPhoto: null,
        groupName: null,
        about: 1,
        currentChannelIdx: 0,
        currentChannelId: -1,
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
        checkInterval: null,//断线重连时 检测连接是否建立成功
        websocket: null,
        stompClient: null,
        listenerList: [], //监听器列表，断线重连时 用于重新注册监听
        messageList: [], //聊天信息栏列表
        // item : {topic: "string", data: {}}
        //
    },
    getters: {
        userId: state => state.userId,
        groupId: state => state.groupId,
        groupPhoto: state => state.groupPhoto,
        groupName: state => state.groupName,
        about: state => state.about,
        stompClient(state) {
            return function() {
                return state.stompClient;
            }
        },
    },
    mutations: {
        setAbout(store, about) {
            store.about = about
        },
        setGroupId(store, groupId) {
            store.groupId = groupId
        },
        setGroupPhoto(store, groupPhoto) {
            store.groupPhoto = groupPhoto
        },
        setGroupName(store, groupName) {
            store.groupName = groupName
        },
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

        WEBSOCKET_INIT(state) {
            if (state.stompClient == null || !state.stompClient.connected) {
                state.url = state.DEBUG ? 'http://localhost:8080/websocket' : 'http://182.92.163.68:8080/websocket'
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
            stompClient.heartbeat.incoming = 0; //客户端不从服务端接收心跳包
            // 向服务器发起websocket连接
            stompClient.connect({ name: state.myNick }, //此处注意更换自己的用户名，最好以参数形式带入
                frame => { // eslint-disable-line no-unused-vars
                    console.log('链接成功！')
                    console.log(state.stompClient)
                    for (let listener in state.listenerList) {
                        state.stompClient.subscribe(state.listenerList[listener].url, payload => {
                            console.log("谢谢你 因为有你")
                            let json = JSON.parse(payload.body)
                            console.log("收到的json:")
                            console.log(json)
                            for (let friend in state.messageList) {
                                console.log(json.srcId.toString() + " "  + friend.toString() + " " + state.messageList[friend].id.toString())
                                if (json.srcId === state.messageList[friend].id) {
                                    state.messageList[friend].data.push(json)
                                    // this.commit("updateMessageList", {id: json.srcId, data: json})
                                    console.log("收到 id:" + json.srcId.toString() + " 消息:")
                                    console.log(json)
                                    break
                                }
                            }
                            console.log("温暖了四季")
                        })
                        console.log(state.listenerList[listener].url)
                    }
                },
                err => { // eslint-disable-line no-unused-vars
                    setTimeout(() => {
                        console.log("reconnecting...")
                        _this.commit('WEBSOCKET_INIT', state.url)
                    }, 20000)
                }
            );
            state.stompClient = stompClient
        },
        WEBSOCKET_SEND(state, p) {
            // const text = p.data.msgFromName + "," + p.data.msgFromAvatar + ","
            //     + p.data.msg + "," + p.data.time + ","
            //     + p.data.srcId + "," + p.data.dstId
            state.stompClient.send(p.url, {}, JSON.stringify(p.data));
            console.log("send + " + JSON.stringify(p.data) + " to " + p.url)
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

        // ---------------------- 以上是 Stomp 封装 请谨慎修改 --------------------------- //

        initListenerList(state, data) {
            for (let d in data) {
                state.listenerList.push(data[d])
            }
        },

        initMessageList(state, data) {
            console.log(state.messageList)
            for (let d in data) {
                state.messageList.push(data[d])
            }
            console.log(state.messageList)
        },
        // --------------------- 通讯录管理 -----------------------------//

        // ---- 工具 ----//
        // updateMessageList(state, payload) {
        //     // 找出messageList是否存在
        //     const idx = state.messageList.findIndex(
        //         message => {
        //             return message.id === payload.id
        //         }
        //     )
        //     // 若存在,那么删除原有的，加上目前的数据，插入头部
        //     if (idx !== -1) {
        //         const msg = state.messageList.splice(idx,1)
        //         msg[0].data.push(payload.data)
        //         state.messageList.unshift(msg[0])
        //         // this.commit("updateCurrentIdx")
        //     } else {
        //         // 若不存在，那么重新创建一个 messageList 待定
        //         console.log(" 功能尚未实现 ")
        //     }
        // },

        // updateCurrentIdx(state) {
        //     state.currentChannelIdx = state.messageList.findIndex(message => {
        //         return message.id === state.currentChannelId
        //     })
        // }

    },
    modules: {}
})