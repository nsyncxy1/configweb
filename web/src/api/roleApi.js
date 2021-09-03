import request from "@/utils/request"
const roleApi = {
    // 获取角色分页列表
    getRoleInfo(params){
        return request({
            url:`/userService/role/${params.page}/${params.limit}`,
            method:'get',
            //params
        })
    },
    // 获取特定角色详情
    getRoleDetail(id){
        return request({
            url:`/userService/role/get/${id}`,
            method:'get',
        })
    },
    // 新增角色
    addRoleInfo(data){
        return request({
            url:'/userService/role/save',
            method:'post',
            data
        })
    },
    // 修改角色
    modifyRoleInfo(data){
        return request({
            url:'/userService/role/update',
            method:'post',
            data
        })
    },
    // 删除单个角色
    delRoleInfo(id){
        return request({
            url:`/userService/role/remove/${id}`,
            method:'delete'
        })
    },
    // 批量删除角色
    delRoleInfos(data){
        return request({
            url:'/userService/role/batchRemove',
            method:'delete',
            data
        })
    },
    // 获取角色权限
    getRoleConfig(id){
        return request({
            url:`/userService/menu/toAssign/${id}`,
            method:'get'
        })
    },
    // 设置角色权限
    setRoleConfig(params){
        return request({
            url:`/userService/menu/doAssign?roleId=${params.roleId}&menuId=${params.menuId}`,
            method:'post',
        })
    }
}
export default roleApi