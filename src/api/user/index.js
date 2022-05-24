import request from "../request"


export function register(param) {
    return request({
        url: '/user/register',
        method: 'post',
        data: param
    })
}

export function getCheckCode(param) {
    return request({
        url: '/user/getCheckCode',
        method: 'get',
        params: param
    })
}

export function login(param) {
    return request({
        url: '/user/loginn',
        method: 'post',
        data: param
    })
}

export function logout(param) {
    return request({
        url: '/user/logout',
        method: 'get',
        params: param
    })
}

export function changePwd(param) {
    return request({
        url: '/user/changePwd',
        method: 'post',
        data: param
    })
}

export function updateSelf(param) {
    return request({
        url: '/user/updateSelf',
        method: 'post',
        data: param
    })
}