import request from "@/utils/request";
const userApi = {
    // 获取用户分页列表
    getUserInfo(params){
        return request({
            url:`/userService/user/${params.page}/${params.limit}`,
            method:'get',
        })
    },
    // 新增用户信息
    addUserInfo(data){
        return request({
            url:'/userService/user/save',
            method:'post',
            data
        })
    },
    // 修改用户信息
    modifyUserInfo(data) {
        return request({
            url:'/userService/user/update',
            method:'post',
            data
        })
    },
    // 删除单个用户信息
    deleteUserInfo(id){
        return request({
            url:`/userService/user/remove/${id}`,
            method:'delete'
        })
    },
    // 批量删除用户信息
    deleteUserInfos(data){
        return request({
            url:'/userService/user/batchRemove',
            method:'delete',
            data
        })
    },
    // 为用户分配角色
    setUserInfo(params){
        return request({
            url:`/userService/user/doAssign?userId=${params.userId}&roleIds=${params.roleIds}`,
            method:'post',
            //params
        })
    },
    // 用户角色详情
    accessUserInfo(id){
        return request({
            url:`/userService/user/toAssign/${id}`,
            method:'get'
        })
    }
}
export default userApi