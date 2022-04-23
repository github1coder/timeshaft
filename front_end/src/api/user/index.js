import request from "../request"



export function register(param) {
    return request({
        url: '/user/register',
        method: 'post',
        param
    })
}

export function getCheckCode(param) {
    return request({
        url: '/user/getCheckCode',
        method: 'get',
        param
    })
}

export function login(param) {
    return request({
        url: '/user/login',
        method: 'post',
        param
    })
}