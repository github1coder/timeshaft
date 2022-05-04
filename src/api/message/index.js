import request from "../request"

export function getMessagesList(param) {
    return request({
        url: '/message/getMessagesList',
        method: 'post',
        data: param
    })
}

export function getSubscribeUrlList(param) {
    return request({
        url: '/message/getSubscribeUrlList',
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
