import request from "@/utils/request"
const loginApi = {
    loginUser(params){
        return request({
            // post请求也可以用params方式传值
            url:'/userService/user/login',
            method:'post',
            params
        })
    },
    exitUser(data){
        return request({
            url:'/userService/user/loginOut',
            method:'post',
            data
        })
    }
}
export default loginApi