import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Register from '../components/Register.vue'
import WelcomeOverlay from '../components/WelcomeOverlay.vue'
// import Test from "@/views/test";

Vue.use(VueRouter)

const router = new VueRouter({
    // mode: 'history',
    routes: [{
            path: '/',
            name: 'WelcomeOverlay',
            component: WelcomeOverlay
        },
        {
            path: '/login',
            name: 'login',
            component: WelcomeOverlay
        },
        {
            path: '/register',
            name: 'Register',
            component: Register
        },
        {
            path: '/home',
            name: 'Home',
            component: Home
        },
    ]
})

export default router