import Vue from 'vue'
import VueRouter from "vue-router"
Vue.use(VueRouter)
const Login = () => import('@/views/login/login')
const Layout = () => import('@/views/layout/layout')
const documentIndex = () => import('@/views/document/documentIndex')
const document = () => import('@/views/document/document')
const documentConfig = () => import('@/views/document/documentConfig')
/*const userIndex = () => import('@/views/userInfo/userIndex')
const userInfo = () => import('@/views/userInfo/userInfo')
const userConfig = () => import('@/views/userInfo/userConfig')
const roleIndex = () => import('@/views/roleInfo/index')
const roleInfo = () => import('@/views/roleInfo/roleInfo')
const roleConfig = () => import('@/views/roleInfo/roleConfig')
const menuIndex = () => import('@/views/menuInfo/index')
const menuInfo = () => import('@/views/menuInfo/menuInfo')
const menuConfig = () => import('@/views/menuInfo/menuConfig')
const menuModify = () => import('@/views/menuInfo/menuModify')*/
const notFound = () => import('@/views/notFound/notFound')
const constantRoutes = [
    {
        path:'/login',
        name:'login',
        component:Login,
        meta:{
            title:'用户登录'
        }
    },
    {
      path: '/',
      redirect:'/login'
    },
    {
        path: '/dash',
        name: 'dash',
        redirect: '/documentIndex/document',
        component:Layout,
        meta: {
            title: '大开信息'
        },
        children:[
            {
                path:'/documentIndex',
                name:'documentIndex',
                component:documentIndex,
                meta:{
                    title:'文件管理'
                },
                children:[
                    {
                        path:'document',
                        name:'document',
                        component:document,
                        meta:{
                            title:'文件信息管理'
                        }
                    },
                    {
                        path: 'documentConfig',
                        name: 'documentConfig',
                        component:documentConfig,
                        meta: {
                            title: '文件信息编辑'
                        }
                    }
                ]
            }
            /*{
                path:'/userIndex',
                name:'userIndex',
                component:userIndex,
                meta:{
                    title:'用户信息管理',
                },
                children: [
                    {
                        path:'userInfo',
                        name:'userInfo',
                        component:userInfo,
                        meta:{
                            title:'用户信息查询',
                        },
                    },
                    {
                        path: 'userConfig/:userId',
                        name: 'userConfig',
                        component:userConfig,
                        meta: {
                            title: '用户信息设置'
                        }
                    }
                ]
            },
            {
                path:'/roleIndex',
                name:'roleIndex',
                component: roleIndex,
                redirect: '/roleIndex/roleInfo',
                meta: {
                    title: '角色信息管理'
                },
                children: [
                    {
                        path:'roleInfo',
                        name:'roleInfo',
                        component: roleInfo,
                        meta: {
                            title: '角色信息查询'
                        }
                    },
                    {
                        path: 'roleConfig/:roleId',
                        name: 'roleConfig',
                        component:roleConfig,
                        meta: {
                            title: '角色信息设置'
                        }
                    }
                ]
            },
            {
                path:'/menuIndex',
                name:'menuIndex',
                component:menuIndex,
                redirect:'/menuIndex/menuInfo',
                meta: {
                    title: '菜单信息管理'
                },
                children:[
                    {
                        path:'menuInfo',
                        name:'menuInfo',
                        component:menuInfo,
                        meta: {
                            title: '菜单信息查询'
                        }
                    },
                    {
                        path:'menuConfig',
                        name:'menuConfig',
                        component:menuConfig,
                        meta:{
                            title:'新增菜单信息'
                        }
                    },
                    {
                        path:'menuConfig/:pid',
                        name:'menuConfigPid',
                        component:menuConfig,
                        meta:{
                            title:'新增菜单信息'
                        }
                    },
                    {
                        path: 'menuModify',
                        name: 'menuModify',
                        component:menuModify,
                        meta: {
                            title: '修改菜单信息'
                        }
                    }
                ]
            },*/
        ]
    },
    {
        path: '/404',
        name:'404',
        component:notFound,
        meta: {
            title: '404'
        }
    },
    {
        path:'*',
        redirect: '/404'
    }
]
const router = new VueRouter({
    routes:constantRoutes,
})
export const asyncRoutes = [
    {
        path:'/documentIndex',
        name:'documentIndex',
        component:documentIndex,
        meta:{
            title:'文件管理'
        },
        children:[
            {
                path:'document',
                name:'document',
                component:document,
                meta:{
                    title:'文件信息管理'
                }
            }
        ]
    }
    /*{
        path:'/userInfo',
        name:'userInfo',
        component:userInfo,
        meta:{
            title:'用户管理',
        }
    },
    {
        path:'/roleInfo',
        name:'roleInfo',
        component: roleInfo,
        meta: {
            title: '角色管理'
        }
    },
    {
        path:'/menuInfo',
        name:'menuInfo',
        component:menuInfo,
        meta: {
            title: '菜单管理'
        }
    }*/
]
//console.log(asyncRoutes)
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
    if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
    return originalPush.call(this, location).catch(err => err)
}
export default router
