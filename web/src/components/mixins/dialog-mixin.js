export default {
    data(){
        return {}
    },
    methods:{
        showDialog(type,row){
            if(type === 0)
            {
                this.type = 0
                this.dialogFormVisible = true
            }
            if(type === 1 && row && (typeof row) === 'object')
            {
                this.type = 1
                this.dialogFormVisible = true
                let dialogForm = this.dialogForm
                Object.getOwnPropertyNames(row).forEach(function(key){
                    dialogForm[key] = row[key]
                });
                if(typeof (this.handleForm) === 'function')
                {
                    this.handleForm()
                }
            }
        },
        handleClose(formName='dialogForm'){
            //console.log('handleClose:')
            this.type = null
            this.title = ''
            let dialogForm = this[formName]
            Object.getOwnPropertyNames(dialogForm).forEach(function(key){
                dialogForm[key] = ''
            });
            //this._getListData()
        },
        submit(formName='dialogForm'){
            //console.log(formName)
            //console.log(this.$refs[formName])
            this.$refs[formName].validate((vaild)=>{
                if(vaild)
                {
                    this.submitForm(formName)
                }else {
                    console.log('submit error')
                }
            })
        },
        async submitForm(formName){
            if(this.type === 0)
            {
                const resData = await this.handleAddInfo(formName)
                //console.log('resData:')
                //console.log(resData)
                const res = resData.data
                //console.log('res:')
                console.log(res)
                if(res.code == 200)
                {
                    this.$notify({
                        title: '成功',
                        message: '数据更新成功',
                        type: 'success',
                        duration:2000
                    });
                    this.dialogFormVisible = false
                    this.$emit('refreshModify')
                }
            }
            if(this.type === 1)
            {
                const resData =await this.handleUpdataInfo(formName)
                const res = resData.data
                if(res.code == 200)
                {
                    this.$notify({
                        title: '成功',
                        message: '数据更新成功',
                        type: 'success',
                        duration:2000
                    });
                    this.dialogFormVisible = false
                    this.$emit('refreshModify')
                }
            }
        },
        changeSelectOption(){
            this.$forceUpdate()
        }
    }
}