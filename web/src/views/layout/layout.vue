<template>
    <div class="layout-wraper">
        <el-container class="layout-container">
            <el-aside width="12rem">
                <el-menu :default-active="this.$route.path" router
                         class="layout-menu"
                         background-color="#304156"
                         text-color="#fff"
                         active-text-color="#27bffe">
                    <el-submenu index="/documentIndex">
                        <template slot="title">文件管理</template>
                        <el-menu-item index="/documentIndex/document">文件信息查询</el-menu-item>
                    </el-submenu>
                    <!--<el-submenu index="/userIndex">
                        <template slot="title">用户信息管理</template>
                        <el-menu-item index="/userIndex/userInfo">用户信息查询</el-menu-item>
                    </el-submenu>
                    <el-submenu index="/roleIndex">
                        <template slot="title">角色信息管理</template>
                        <el-menu-item index="/roleIndex/roleInfo">角色信息查询</el-menu-item>
                    </el-submenu>
                    <el-submenu index="/menuIndex">
                        <template slot="title">菜单信息管理</template>
                        <el-menu-item index="/menuIndex/menuInfo">菜单信息查询</el-menu-item>
                    </el-submenu>-->
                </el-menu>
            </el-aside>
            <el-container>
                <!--<el-header  class="layout-header" style="position: relative">

                </el-header>-->
                <el-main class="layout-main">
                    <div class="layout-header">
                        <el-breadcrumb separator="/" class="bread-title">
                            <el-breadcrumb-item v-for="(item,index) in breadData" :key="index"
                                                :to="{path:item.path}">{{item.meta.title}}</el-breadcrumb-item>
                        </el-breadcrumb>
                        <el-dropdown class="layout-dropdown" trigger="hover" @command="handleCommand">
                        <span class="el-dropdown-link layout-dropdown-link">
                            <i class="el-icon-switch-button el-icon--right layout-settting-icon"></i>
                        </span>
                            <el-dropdown-menu slot="dropdown">
                                <!--<el-dropdown-item :command="0">修改密码</el-dropdown-item>-->
                                <el-dropdown-item command="-1">退出</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </div>
                    <div class="layout-box">
                        <router-view></router-view>
                    </div>
                </el-main>
            </el-container>
        </el-container>
        <modify-dialog ref="modifyDialog"></modify-dialog>
    </div>
</template>

<script>
    import modifyDialog from './components/modify-dialog'
    export default {
        name: "layout",
        data(){
            return {
                activeDefault:'/userInfo',
                breadData:[]
            }
        },
        components:{modifyDialog},
        created(){
            this.breadData = this.$route.matched
        },
        watch:{
            $route(val){
                // console.log(val)
                this.breadData = val.matched
            }
        },
        methods:{
            handleCommand(command){
                if(command === 0)
                {
                    this.$refs.modifyDialog.showDialog(0)
                }
                if(command == -1)
                {
                    this.$confirm('此操作将退出大开信息后台管理系统，是否继续？', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning',
                        center: true
                    }).then(() => {
                        this.$store.dispatch('user/exitUser').then(()=>{
                            // this.$router.push({path:'/login'})
                        })
                    }).catch(() => {
                        // console.log(error)
                    });
                }
                //console.log(command)
            }
        }
    }
</script>

<style scoped>

</style>
