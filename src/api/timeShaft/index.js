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

export function getTimeShaftData(param) {
    return request({
        url: '/timeshaft/getTimeShaftData',
        method: 'get',
        params: param
    })
}

export function genTimeShaftFromMessages(param) {
    return request({
        url: '/timeshaft/genTimeShaftFromMessages',
        method: 'post',
        data: param
    })
}

export function queryTimeShaftId(param) {
    return request({
        url: 'timeshaft/queryTimeShaftId',
        method: 'post',
        data: param
    })
}

export function meetingChange(param) {
    return request({
        url: 'timeshaft/meetingChange',
        method: 'post',
        data: param
    })
}


export function delTimeshaft(param) {
    return request({
        url: 'timeshaft/delTimeshaft',
        method: 'post',
        data: param
    })
}


export function getTimeTags(param) {
    return request({
        url: 'timeshaft/getTimeTags',
        method: 'post',
        data: param
    })
}

export function searchTimeByTag(param) {
    return request({
        url: 'timeshaft/searchTimeByTag',
        method: 'post',
        data: param
    })
}

export function updateTimeNode(param) {
    return request({
        url: 'timeshaft/updateTimeNode',
        method: 'post',
        data: param
    })
}

export function updateTimeState(param) {
    return request({
        url: 'timeshaft/updateTimeState',
        method: 'post',
        data: param
    })
}

export function starTimeNode(param) {
    return request({
        url: 'timeshaft/starTimeNode',
        method: 'post',
        data: param
    })
}