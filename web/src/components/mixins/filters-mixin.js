export default {
    data(){
        return {
        }
    },
    filters:{
        // 用户状态
        statusUser(status){
            const statusMap = {
                '0': '默认',
                '1': '停用'
            };
            return statusMap[status]
        },
        // 用户所属部门
        statusPart(status){
            const statusMap = {
                '0': '研发部',
                '1': '财务部',
                '2': '总裁办',
                '3': '项目部',
                '4': '运营部',
                '5': '方案部'
            };
            return statusMap[status]
        },
        // 角色状态
        statusRole(status){
            const statusMap = {
                '0': '默认',
                '1': '停用'
            };
            return statusMap[status]
        },
        // 菜单类型
        statusType(status){
            const statusMap = {
                '1': '菜单',
                '2': '按钮'
            };
            return statusMap[status]
        },
        // 菜单状态
        statusMenu(status){
            const statusMap = {
                '0': '默认',
                '1': '隐藏',
            };
            return statusMap[status]
        },
        // 标签类型
        statusElTag(status){
            const statusMap = {
                '0': 'default',
                '1': 'success',
                '2': 'info',
                '3': 'warning',
                '4': 'danger',
            };
            return statusMap[status]
        },
        // 后台返回数据时间格式化
        formatTime(time) {
            let date = new Date(time)
            date = `${date.getFullYear()}-${date.getMonth()+1}-${date.getDate()}`
            return date
        },
        elTagRole(status){
            const statusMap = {
                '0': 'success',
                '1': 'warning'
            };
            return statusMap[status]
        },
        elTagMenu(status){
            const statusMap = {
                '0': 'success',
                '1': 'warning'
            };
            return statusMap[status]
        },
        elTagUser(status){
            const statusMap = {
                '0': 'success',
                '1': 'warning'
            };
            return statusMap[status]
        },
    }
}