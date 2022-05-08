import request from "../request"

export function getTimeLine(param) {
    return request({
        url: '/message/getSubscribeUrlList',
        method: 'post',
        data: param
    })
}
