/**
 * ajax请求配置
 */
import axios from 'axios'
import store from '../store/index'
// import { Message } from 'element-ui'

const DEBUG = true;
// axios.defaults.withCredentials = true;

const service = axios.create({
    baseURL: DEBUG ? 'http://localhost:8080' : 'http://182.92.163.68:8080',
    timeout: 20000,
})

// service.defaults.withCredentials = true //是否携带cookie

service.interceptors.request.use(
    config => {
        if (store.state.userId != -1) {
            config.headers['user_id'] = store.state.userId
            config.headers['ACCESS_TOKEN'] = store.state.accessToken
        }
        config.headers["Content-Type"] = 'application/json'
        return config
    },
    error => {
        console.log(error)
        return Promise.reject(error)
    }
)

// 路由响应拦截
service.interceptors.response.use(
    response => {
        console.log("响应")
        console.log(response)
        if (response.data.code === 0) {
            return response.data.data
        } else {
            return { "error": response.data.msg }
        }


        // (response.data.code === 40000) {
        //     return Message.error("请求错误")
        // } else if (response.data.code === 40001) {
        //     return Message.error("未授权，请登录")
        // } else if (response.data.code === 40003) {
        //     return Message.error("拒绝访问")
        // } else if (response.data.code === 40004) {
        //     return Message.error("请求地址出错，未找到指定资源")
        // } else if (response.data.code === 40005) {
        //     return Message.error("请求方法不允许")
        // } else if (response.data.code === 40006) {
        //     return Message.error("请求地址未授权，拒绝访问")
        // } else if (response.data.code === 40008) {
        //     return Message.error("请求超时")
        // } else if (response.data.code === 40009) {
        //     return Message.error("并发冲突，尝试创建资源已存在")
        // } else if (response.data.code === 50000) {
        //     return Message.error("服务器内部错误")
        // } else if (response.data.code === 50001) {
        //     return Message.error("服务器未实现该api方法")
        // } else if (response.data.code === 50002) {
        //     return Message.error("网关错误")
        // } else if (response.data.code === 50004) {
        //     return Message.error("请求错误")
        // } else if (response.data.code === 5005) {
        //     return Message.error("HTTP版本不支持")
        // }
    },
    error => {
        return Promise.reject(error.response) // 返回接口返回的错误信息
    })

export default service