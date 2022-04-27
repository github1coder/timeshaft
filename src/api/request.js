/**
 * ajax请求配置
 */
import axios from 'axios'
import { Message } from 'element-ui'

// axios默认配置
axios.defaults.timeout = 10000 // 超时时间
    // axios.defaults.baseURL 请求地址前缀
    // User地址
    // axios.defaults.baseURL = 'http://127.0.0.1:8001'
    // tools地址
    // axios.defaults.baseURL = 'http://127.0.0.1:8088'
    // 微服务地址
// axios.defaults.baseURL = 'http://182.92.163.68:8080'
axios.defaults.baseURL = 'http://localhost:8080'

// 整理数据
axios.defaults.transformRequest = function(data) {
    data = JSON.stringify(data)
    return data
}

// 路由请求拦截
axios.interceptors.request.use(
    config => {
        config.headers['Content-Type'] = 'application/json;charset=UTF-8'

        return config
    },
    error => {
        return Promise.reject(error.response)
    })

// 路由响应拦截
axios.interceptors.response.use(
    response => {
        if (response.code === 0) {
            return response.data
        } else if (response.code === 40000) {
            return Message.error("请求错误")
        } else if (response.code === 40001) {
            return Message.error("未授权，请登录")
        } else if (response.code === 40003) {
            return Message.error("拒绝访问")
        } else if (response.code === 40004) {
            return Message.error("请求地址出错，未找到指定资源")
        } else if (response.code === 40005) {
            return Message.error("请求方法不允许")
        } else if (response.code === 40006) {
            return Message.error("请求地址未授权，拒绝访问")
        } else if (response.code === 40008) {
            return Message.error("请求超时")
        } else if (response.code === 40009) {
            return Message.error("并发冲突，尝试创建资源已存在")
        } else if (response.code === 50000) {
            return Message.error("服务器内部错误")
        } else if (response.code === 50001) {
            return Message.error("服务器未实现该api方法")
        } else if (response.code === 50002) {
            return Message.error("网关错误")
        } else if (response.code === 50004) {
            return Message.error("请求错误")
        } else if (response.code === 5005) {
            return Message.error("HTTP版本不支持")
        }
    },
    error => {
        return Promise.reject(error.response) // 返回接口返回的错误信息
    })
export default axios