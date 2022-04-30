import request from "../request"

//member和friend是否要统一
//friendid就是friend的user_id吗


export function getFriends(param) {
    return request({
        url: '/addresslist/getFriends',
        method: 'get',
        params: param
    })
}

export function addFriend(param) {
    return request({
        url: '/addresslist/addFriend',
        method: 'get',
        params: param
    })
}

export function delFriend(param) {
    return request({
        url: '/addresslist/addFriend',
        method: 'get',
        params: param
    })
}

export function changeNickname(param) {
    return request({
        url: '/addresslist/changeNickname',
        method: 'post',
        data: param
    })
}

export function getGroups(param) {
    return request({
        url: '/addresslist/getGroups',
        method: 'get',
        params: param
    })
}

export function addGroup(param) {
    return request({
        url: '/addresslist/addGroup',
        method: 'post',
        data: param
    })
}

export function updateGroup(param) {
    return request({
        url: '/addresslist/updateGroup',
        method: 'post',
        data: param
    })
}

export function joinGroup(param) {
    return request({
        url: '/addresslist/joinGroup',
        method: 'post',
        data: param
    })
}

export function quitGroup(param) {
    return request({
        url: '/addresslist/quitGroup',
        method: 'post',
        data: param
    })
}

export function delGroup(param) {
    return request({
        url: '/addresslist/delGroup',
        method: 'get',
        params: param
    })
}

export function delGroupManager(param) {
    return request({
        url: '/addresslist/delGroupManager',
        method: 'post',
        data: param
    })
}

export function addGroupManager(param) {
    return request({
        url: '/addresslist/addGroupManager',
        method: 'post',
        data: param
    })
}

//修改群昵称

export function changeGroupNickname(param) {
    return request({
        url: '/addresslist/changeGroupNickname',
        method: 'post',
        data: param
    })
}

//添加好友
export function search(param) {
    return request({
        url: '/addresslist/searchByNick',
        method: 'get',
        params: param
    })
}

export function apply(param) {
    return request({
        url: '/addresslist/apply',
        method: 'get',
        params: param
    })
}

export function getApplyList(param) {
    return request({
        url: '/addresslist/getApplyList',
        method: 'get',
        params: param
    })
}

export function getGroupMember(param) {
    return request({
        url: '/addresslist/getGroupMember',
        method: 'get',
        params: param
    })
}