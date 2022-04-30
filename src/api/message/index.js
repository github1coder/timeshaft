import request from "../request"

export function getMessagesList(param) {
    return request({
        url: '/message/getMessagesList',
        method: 'post',
        data: param
    })
}
