import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import router from './router'
import store from './store'
import VueContextMenu from "vue-contextmenu";
// import 'material-design-icons-iconfont/dist/material-design-icons.css'
import '../public/MaterialDesign/css/materialdesignicons.min.css'


Vue.config.productionTip = false

Vue.use(VueContextMenu)

const whiteList = ["/", "/login", "/register"];
router.beforeEach((to, from, next) => {
    to
    from
    next
    // to and from are both route objects. must call `next`.
    if (sessionStorage.getItem("login") == "no" && whiteList.indexOf(to.path) == -1) {
        next({ name: "login" })
    } else {
        next()
    }

})

new Vue({
    vuetify,
    router,
    store,
    icons: {
        iconfont: 'mdi',
    },
    render: h => h(App)
}).$mount('#app')