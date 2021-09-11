import {login} from "@/utils/request"
const loginApi = {
    loginUser(data){
        return login({
            // post请求也可以用params方式传值
            url:'/login',
            method:'post',
            data
        })
    },
    /*exitUser(data){
        return request({
            url:'/userService/user/loginOut',
            method:'post',
            data
        })
    }*/
}
export default loginApi
