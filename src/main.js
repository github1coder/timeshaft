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

new Vue({
    vuetify,
    router,
    store,
    icons: {
        iconfont: 'mdi',
    },
    render: h => h(App)
}).$mount('#app')