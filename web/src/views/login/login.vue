<template>
    <div class="login-wraper">
        <div class="login-box">
            <div class="login-title">大开信息后台管理系统</div>
            <el-form class="login-form" :model="loginForm" :rules="loginRules" ref="loginForm" >
                <el-form-item label="" prop="username">
                    <el-input class="login-input" v-model="loginForm.username" placeholder="账号" autocomplete="on" autofocus="true">
                        <i slot="prefix" class="el-input__icon el-icon-user"></i>
                    </el-input>
                </el-form-item>
                <el-form-item label="" prop="password">
                    <el-input :loading="loading" class="login-input" type="password" v-model="loginForm.password" placeholder="密码">
                        <i slot="prefix" class="el-input__icon el-icon-key"></i>
                    </el-input>
                </el-form-item>
                <el-form-item label-width="0">
                    <el-button class="login-submit" type="primary" @click="submitForm('loginForm')">提交</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import loginApi from "@/api/loginApi";
    export default {
        name: "login",
        data(){
            return {
                loading:false,
                loginForm:{
                    username:'',
                    password:'',
                },
                loginRules:{
                    username: [
                        {required:true,trigger:'blur',message:"请输入账号"}
                    ],
                    password: [
                        {required: true,trigger: 'blur',message: "请输入密码"}
                    ]
                }
            }
        },
        created(){
            //console.log('login:')
        },
        methods:{
            submitForm(formName){
                this.$refs[formName].validate((valid)=>{
                    if(valid)
                    {
                        console.log('come in...');
                        // this.submitLogin(formName)
                        this.loading = true
                        this.$store.dispatch('user/loginUser', {
                            username:this.loginForm.username,
                            password:this.loginForm.password
                        }).then(()=>{
                            this.loading = false
                            console.log('success..');
                        }).catch(()=>{
                            this.loading = false
                            console.log('error..');
                        })
                    }else{
                        console.log('error submit!')
                        return false
                    }
                })
            },
            async submitLogin(formName){
                const res = await loginApi.loginUser(this[formName])
                return res
                // console.log(res)
            }
        }
    }
</script>

<style scoped>
    .login-wraper{
        background: url('../../assets/images/information.jpg') center center no-repeat;
        background-size: cover;
    }
</style>
