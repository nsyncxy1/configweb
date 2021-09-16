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
                const token = payload
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
            console.log('loginUser:')
            //console.log(payload)
            // const res= await loginApi.loginUser(payload)
            loginApi.loginUser(payload).then(res => {
                console.log("res:")
                console.log(res)
                if(res.code && res.code == 200)
                {
                    commit('SET_TOKEN',res.data.token)
                    //console.log(getToken())
                    Message.success({
                        message:res.msg,
                        duration:1000
                    })
                    router.push({path:'/dash'})
                }
                else if(res.code && res.code == 201)
                {
                    Message.error({
                        message:res.msg,
                        duration:1800
                    })
                    return false
                }else {
                    Message.error({
                        message:res.msg,
                        duration:1800
                    })
                    return false
                }
            });

        },
        async exitUser({commit}){
            /*const resData = await loginApi.exitUser()
            const res = resData.data
            if(res.code && res.code == 200)
            {
                commit('REMOVE_TOKEN')
                router.push({path:'/login'})
            }*/
            commit('REMOVE_TOKEN')
            router.push({path:'/login'})
        }
    }
}
export default user
