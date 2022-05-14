import request from "../request"

export function addTimeLine(param) {
    return request({
        url: '/timeshaft/beginTimeShaftSingle',
        method: 'post',
        data: param
    })
}

export function getTimeLine(param) {
    return request({
        url: '/timeshaft/getTimeshaft',
        method: 'post',
        data: param
    })
}

export function endTimeShaft(param) {
    return request({
        url: '/timeshaft/endTimeShaft',
        method: 'post',
        data: param
    })
}


export function beginTimeShaftSingle(param) {
    return request({
        url: '/timeshaft/beginTimeShaftSingle',
        method: 'post',
        data: param
    })
}