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
