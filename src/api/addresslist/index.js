import request from "../request"

//member和friend是否要统一
//friendid就是friend的user_id吗


export function getFriends(param) {
    return request({
        url: '/addresslist/getFriends',
        method: 'get',
        param
    })
}

export function addFriend(param) {
    return request({
        url: '/addresslist/addFriend',
        method: 'get',
        param
    })
}

export function delFriend(param) {
    return request({
        url: '/addresslist/addFriend',
        method: 'get',
        param
    })
}

export function changeNickname(param) {
    return request({
        url: '/addresslist/changeNickname',
        method: 'post',
        param
    })
}

export function getGroups(param) {
    return request({
        url: '/addresslist/getGroups',
        method: 'get',
        param
    })
}

export function addGroups(param) {
    return request({
        url: '/addresslist/addGroup',
        method: 'post',
        param
    })
}

export function updateGroup(param) {
    return request({
        url: '/addresslist/updateGroup',
        method: 'post',
        param
    })
}

export function joinGroup(param) {
    return request({
        url: '/addresslist/joinGroup',
        method: 'post',
        param
    })
}

export function quitGroup(param) {
    return request({
        url: '/addresslist/quitGroup',
        method: 'post',
        param
    })
}

export function delGroup(param) {
    return request({
        url: '/addresslist/delGroup',
        method: 'get',
        param
    })
}

export function delGroupManager(param) {
    return request({
        url: '/addresslist/delGroupManager',
        method: 'post',
        param
    })
}

export function addGroupManager(param) {
    return request({
        url: '/addresslist/addGroupManager',
        method: 'post',
        param
    })
}

//修改群昵称

export function changeGroupNickname(param) {
    return request({
        url: '/addresslist/changeGroupNickname',
        method: 'post',
        param
    })
}