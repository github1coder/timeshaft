import request from "../request"

export function beginTimeShaftSingle(param) {
    return request({
        url: '/timeshaft/beginTimeShaftSingle',
        method: 'post',
        data: param
    })
}

export function getTimeshaft(param) {
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

export function getSingleTimeshaft(param) {
    return request({
        url: '/timeshaft/getSingleTimeshaft',
        method: 'post',
        data: param
    })
}