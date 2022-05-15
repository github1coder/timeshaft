import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        more: true,
        DEBUG: false,
        userId: -1,
        email: null,
        accessToken: null,
        infoId: -1,
        infoPhoto: null,
        infoEmail: null,
        infoName: null,
        infoNick: null,
        master: -1,
        about: -1, //0代表朋友，1代表群聊
        currentChannelIdx: -1,
        currentChannelId: -1,
        myIcon: "guest.png",
        ownerIcon: "guest.png",
        myNick: "noname",
        loggedIn: false,
        siderState: 0,
        // socket 相关
        checkInterval: null, //断线重连时 检测连接是否建立成功
        chatSocket: null,
        contactSocket: null,
        chatClient: null,
        contactClient: null,
        //
        currentChatType: null,
        currentChatIndex: null,
        currentChatMore: null,
        currentChatHaveRead: null,
        currentChatName: null,
    },
    getters: {
        userId: state => state.userId,
        accessToken: state => state.accessToken,
        email: state => state.email,
        myNick: state => state.myNick,
        myIcon: state => state.myIcon,
        infoId: state => state.infoId,
        infoPhoto: state => state.infoPhoto,
        infoName: state => state.infoName,
        infoEmail: state => state.infoEmail,
        about: state => state.about,
        master: state => state.master,
        infoNick: state => state.infoNick,
        stompClient(state) {
            return function() {
                return state.stompClient;
            }
        },
    },
    mutations: {
        setAccessToken(store, AccessToken) {
            store.AccessToken = AccessToken
        },
        setInfoNick(store, infoNick) {
            store.infoNick = infoNick
        },
        setInfoEmail(store, infoEmail) {
            store.infoEmail = infoEmail
        },
        setMaster(store, master) {
            store.master = master
        },
        setAbout(store, about) {
            store.about = about
        },
        setInfoId(store, infoId) {
            store.infoId = infoId
        },
        setInfoPhoto(store, infoPhoto) {
            store.infoPhoto = infoPhoto
        },
        setInfoName(store, infoName) {
            store.infoName = infoName
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
            store.more = true
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

        // WEBSOCKET_INIT(state) {
        //     if (state.stompClient == null || !state.stompClient.connected) {
        //         state.url = state.DEBUG ? 'http://localhost:8080/websocket' : 'http://182.92.163.68:8080/websocket'
        //         if (state.stompClient != null && state.websocket.readyState === SockJS.OPEN) {
        //             state.stompClient.disconnect(() => {
        //                 this.commit('WEBSOCKET_CONNECT')
        //             })
        //         } else if (state.stompClient != null && state.websocket.readyState === SockJS.CONNECTING) {
        //             console.log("连接正在建立")
        //             return;
        //         } else {
        //             console.log("第一次建立")
        //             this.commit('WEBSOCKET_CONNECT')
        //         }
        //         if (!state.checkInterval) {
        //             state.checkInterval = setInterval(() => {
        //                 console.log("检测连接：" + state.websocket.readyState)
        //                 if (state.stompClient != null && state.stompClient.connected) {
        //                     clearInterval(state.checkInterval)
        //                     state.checkInterval = null
        //                     console.log('重连成功')
        //                 } else if (state.stompClient != null && state.websocket.readyState !== SockJS.CONNECTING) {
        //                     //经常会遇到websocket的状态为open 但是stompClient的状态却是未连接状态，故此处需要把连接断掉 然后重连
        //                     state.stompClient.disconnect(() => {
        //                         this.commit('WEBSOCKET_CONNECT')
        //                     })
        //                 }
        //             }, 2000)
        //         }
        //     } else {
        //         console.log("连接已建立成功，不再执行")
        //     }
        // },
        // WEBSOCKET_CONNECT(state) {
        //     console.log("--")
        //     const _this = this
        //     const websock = new SockJS(state.url);
        //     console.log("--")
        //     console.log(websock)
        //     console.log(state.url)
        //     console.log("--")
        //     state.websocket = websock
        //         // 获取STOMP子协议的客户端对象
        //     const stompClient = Stomp.over(websock);
        //     console.log(stompClient)
        //     console.log("---")
        //     stompClient.debug = null //关闭控制台打印
        //     stompClient.heartbeat.outgoing = 20000;
        //     stompClient.heartbeat.incoming = 0; //客户端不从服务端接收心跳包
        //     state.stompClient = stompClient
        //     // 向服务器发起websocket连接
        //     stompClient.connect({ name: state.myNick }, //此处注意更换自己的用户名，最好以参数形式带入
        //         frame => { // eslint-disable-line no-unused-vars
        //             console.log('链接成功！')
        //             console.log(state.stompClient)
        //             for (let listener in state.listenerList) {
        //                 state.stompClient.subscribe(state.listenerList[listener].url, payload => {
        //                     let json = JSON.parse(payload.body)
        //                     console.log("收到的json:")
        //                     console.log(json)
        //                     if (state.listenerList[listener].type === 0) {
        //                         console.log("即时通信服务收到消息")
        //                         const idx = state.messageList.findIndex(
        //                             message => {
        //                             return json.chatId === message.id
        //                         })
        //                         if (idx !== -1) {
        //                             state.messageList[idx].data.push(json)
        //                             console.log("收到 chat:" + json.chatId.toString() + " 消息:")
        //                             console.log(json)
        //                         } else {
        //                             console.log("未知好友关系id:" + json.chatId.toString())
        //                         }
        //                     } else if (state.listenerList[listener].type === 1) {
        //                         console.log("添加好友服务收到消息")
        //                         state.messageList.push(json)
        //                         console.log(json)
        //                     } else if(state.listenerList[listener].type === 2) {
        //                         console.log("添加群聊服务收到消息")
        //                         state.messageList.push(json)
        //                         console.log(json)
        //                     } else {
        //                         console.log("未知url类型:" + state.listenerList[listener].type.toString())
        //                     }
        //                 })
        //                 console.log(state.listenerList[listener].url)
        //             }
        //         },
        //         err => { // eslint-disable-line no-unused-vars
        //             setTimeout(() => {
        //                 console.log("reconnecting...")
        //                 _this.commit('WEBSOCKET_INIT', state.url)
        //             }, 20000)
        //         }
        //     );
        //
        // },
        // WEBSOCKET_SEND(state, p) {
        //     // const text = p.data.msgFromName + "," + p.data.msgFromAvatar + ","
        //     //     + p.data.msg + "," + p.data.time + ","
        //     //     + p.data.userId + "," + p.daconsole.log(JSON.parse(sessionStorage.getItem("data")))ta.dstId
        //     console.log(state.stompClient)
        //     state.stompClient.send(p.url, {}, JSON.stringify(p.data));
        //     console.log("send + " + JSON.stringify(p.data) + " to " + p.url)
        // },
        // WEBSOCKET_UNSUBSRCIBE(state, p) {
        //     state.stompClient.unsubscribe(p)
        //     for (let i = 0; i < state.listenerList.length; i++) {
        //         if (state.listenerList[i].url === p) {
        //             state.listenerList.splice(i, 1)
        //             console.log("解除订阅：" + p + " size:" + state.listenerList.length)
        //             break;
        //         }
        //     }
        // },
        //
        // WEBSOCKET_DISCONNECT(state) {
        //     state.stompClient.disconnect(function() {
        //         console.log("断开连接")
        //     })
        // },
        // ---------------------- 以上是 Stomp 封装 请谨慎修改 --------------------------- //
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