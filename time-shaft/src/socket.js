const debug = true;
const socket = window.io.connect(debug ? "ws://127.0.0.1:8081" : "wss://g.eclare.work:8443");

export default {
    sid: null,
    sendToken(token, callback) {
        socket.on("loggedIn", sid => {
            console.say("loggedin", sid);
            callback(true);
            this.sid = sid;
        });

        socket.on("loginErr", data => {
            console.say("login err", data);
            callback(false);
        });

        console.say("send token:", token);
        socket.emit("login", token);
    },

    join(nick, callback) {
        socket.on("join", data => {
            console.say(data);
            this.sid = data.id;
            callback(data.token);
        })
        socket.emit("join", nick);
    },

    getChat(callback) {
        socket.on("allChats", data => {
            callback(data);
        });
        socket.emit("getChats");
    },

    sendChat(msg) {
        socket.emit("chat", msg);
    },

    getMembers(callback) {
        console.say("request get members");
        socket.on("allMembers", data => {
            callback(data);
        })
        socket.emit("getMembers");
    },

    addEvent(eventName, callback) {
        socket.on(eventName, callback);
    },

    updateAvatar(url, callback) {
        socket.on("avatarUpdated", () => {
            callback();
        });
        socket.emit("updateAvatar", url);
    }
}