import request from "../request"

export function addTimeLine(param) {
    return request({
        url: '/timeshaft/beginTimeShaftSingle',
        method: 'post',
        data: param
    })
}
