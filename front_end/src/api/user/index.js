import axios from 'axios'

axios.defaults.withCredentials = true

const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,
    timeout: 10000,
})

service.interceptors.request.use(

    config => {
        return config
    },
    error => {
        Promise.reject(error)
    }
)

service.interceptors.response.use(
    response => {
        const res = response.data;
        if (res.success) {
            return Promise.resolve(res)
        } else {
            // 内部错误码处理
            if (res.code === 1401) {
                errorMsg(res.message || '登录已过期，请重新登录！')
                router.replace({ path: `${BASE_URL}/login` })
            } else {
                // 默认的错误提示
                errorMsg(res.message || '网络异常，请稍后重试！')
            }
            return Promise.reject(res);
        }
    },
    error => {
        if (/timeout\sof\s\d+ms\sexceeded/.test(error.message)) {
            // 超时
            errorMsg('网络出了点问题，请稍后重试！')
        }
        if (error.response) {
            // http状态码判断
            switch (error.response.status) {
                // http status handler
                case 404:
                    errorMsg('请求的资源不存在！')
                    break
                case 500:
                    errorMsg('内部错误，请稍后重试！')
                    break
                case 503:
                    errorMsg('服务器正在维护，请稍等！')
                    break
            }
        }
        return Promise.reject(error.response)
    }
)

export default service