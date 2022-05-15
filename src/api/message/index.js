import request from "../request"

export function getMessagesList(param) {
    return request({
        url: '/message/getMessagesList',
        method: 'post',
        data: param
    })
}

export function haveRead(param) {
    return request({
        url: '/message/haveRead',
        method: 'post',
        data: param
    })
}

export function getHistoryMessage(param) {
    return request({
        url: '/message/getHistoryMessage',
        method: 'post',
        data: param
    })
}

export function chatUrl(param) {
    return request({
        url: '/message/chatUrl',
        method: 'get',
        data: param
    })
}

export function contactUrl(param) {
    return request({
        url: '/message/contactUrl',
        method: 'get',
        data: param
    })
}
