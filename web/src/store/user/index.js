import loginApi from "@/api/loginApi";
import {getToken,setToken,removeToken} from "@/utils/auth"
import router from "@/router"
import {Message} from "element-ui";
const user = {
    namespaced:true,
    state : () => ({
        token:getToken()
    }),
    mutations:{
        SET_TOKEN(state,payload){
            if(payload)
            {
                const token = JSON.stringify(payload)
                setToken(token)
                state.token = getToken()
            }
        },
        REMOVE_TOKEN(state){
            removeToken()
            state.token = getToken()
        }
    },
    actions:{
        async loginUser({commit},payload){
            //console.log('loginUser:')
            //console.log(payload)
            const resData= await loginApi.loginUser(payload)
            const res = resData.data
            //console.log("res:"+res)
            if(res.code && res.code == 200)
            {
                commit('SET_TOKEN',res.data.userInfo)
                //console.log(getToken())
                router.push({path:'/dash'})
            }
            if(res.code && res.code == 201)
            {
                Message.error({
                    message:res.message,
                    duration:1800
                })
                return false
            }
        },
        async exitUser({commit}){
            const resData = await loginApi.exitUser()
            const res = resData.data
            if(res.code && res.code == 200)
            {
                commit('REMOVE_TOKEN')
                router.push({path:'/login'})
            }
        }
    }
}
export default user