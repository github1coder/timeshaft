import request from "../request"



export function register(param) {
    return request({
        url: '/register',
        method: 'get',
        param
    })
}

export function getCheckCode(param) {
    return request({
        url: '/getCheckCode',
        method: 'get',
        param
    })
}

export function login(param) {
    return request({
        url: '/login',
        method: 'post',
        param
    })
}