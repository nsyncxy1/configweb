import request from "@/utils/request"
const menuApi={
    // 获取所有菜单
    getMenuInfo(){
        return request({
            url:'/userService/menu/getAllMenu',
            method:'get'
        })
    },
    // 新增菜单
    addMenuInfo(data){
        return request({
            url:'/userService/menu/save',
            method:'post',
            data
        })
    },
    // 修改菜单
    modifyMenuInfo(data){
        return request({
            url:'/userService/menu/update',
            method:'post',
            data
        })
    },
    // 删除菜单
    delMenuInfo(id){
        return request({
            url:`/userService/menu/remove/${id}`,
            method:'delete',
            params:id
        })
    }
}
export default menuApi