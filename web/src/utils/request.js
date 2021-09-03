import axios from "axios"
import {Message} from "element-ui";
const request = axios.create({
    baseURL:process.env["VUE_APP_BASE_API"],
    timeout:100000,
})
request.interceptors.request.use(function (config) {
    return config
},function (error) {
    console.error(error)
    return Promise.reject(error)
})
request.interceptors.response.use( (response) => {
    const res = response.data || null
    if((!res.code || res.code != 200) && res.message)
    {
        Message.error({
            message:res.message,
            duration:1800
        })
        return Promise.reject('error')
    }
    return res
},function (error) {
    //console.error(error)
    return Promise.reject(error)
})
export default request
