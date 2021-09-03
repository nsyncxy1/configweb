<template>
    <el-dialog :title="title" v-if="dialogFormVisible" class="dialog-modify" :visible.sync="dialogFormVisible" width="40%" :center="false"
               modal modal-append-to-body :lock-scroll="true" @close="handleColse">
        <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="5rem" class="ruleForm">
            <el-form-item label="原密码" prop="password">
                <el-input v-model="ruleForm.password" type="password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="pass" required>
                <el-input v-model="ruleForm.pass"  type="password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass" required>
                <el-input v-model="ruleForm.checkPass"  type="password" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import dialogMixin from '@/components/mixins/dialog-mixin'
    export default {
        name: "modify-dialog",
        mixins:[dialogMixin],
        data(){
            const validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            const validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                title:'密码修改',
                type:null,
                dialogFormVisible:false,
                ruleForm:{
                },
                rules:{
                    password:[
                        {required:true,trigger:'blur',message:'请输入原密码'}
                    ],
                    pass: [
                        { required:true,trigger: 'blur',message:'请输入新密码'},
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    checkPass: [
                        { required:true,trigger: 'blur',message:'请输入确认密码'},
                        { validator: validatePass2, trigger: 'blur' }
                    ],
                }
            }
        },
        methods:{
            handleColse(){
                this.type = null
                this.ruleForm = {}
                //this.$refs.ruleForm.resetField()
            }
        }
    }
</script>

<style scoped>

</style>