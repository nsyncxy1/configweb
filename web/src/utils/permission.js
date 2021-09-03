import router from "@/router"
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {getToken} from "@/utils/auth";
import {Message} from "element-ui";

router.beforeEach((to,from,next) => {
    NProgress.start()
    if (to.path === '/login') {
        if (!getToken()) {
            NProgress.done()
            document.title = to.meta.title
            next()
        } else {
            NProgress.done()
            next({path: '/dash'})
        }
    } else {
        if (!getToken()) {
            NProgress.done()
            Message.error({
                message:'尚未登录或者登录信息已失效',
                duration:1800
            })
            next({path: '/login'})
        } else {
            NProgress.done()
            document.title = to.meta.title
            next()
        }
    }
})