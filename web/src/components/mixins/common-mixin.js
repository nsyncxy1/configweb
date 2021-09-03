export default {
    data(){
        return {
            // 表格
            listData:[], // 列表数据
        }
    },
    created(){
        this._getListData()
    },
    methods:{
        //获取数据
        async _getListData(){
           // console.log('_getListData:')
            const resData =await this.getListBack()
            //console.log('resData:')
            //console.log(resData)
            const res = resData.data || null
            //console.log('res:')
            //console.log(res)
            if(res.code && res.code== 200 )
            {
                this.handleResponse(res)
            }

        },
        // 删除提示
        delInfoWarn(row){
            this.$confirm('是否删除这条数据?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.deleteInfo(row)
            })
        },
        // 删除操作
        async deleteInfo(row){
            const id = row.id
            const resData =await this.delInfo(id)
            const res = resData.data
            if(res.code && res.code == 200)
            {
                this.$notify({
                    title: '成功',
                    message: '数据更新成功',
                    type: 'success',
                    duration:2000
                })
                this.refreshDelInfo()
            }
        },
        refreshDelInfo(){
            if(this.listData.length<=1)
            {
                if(this.currentPage > 1)
                {
                    this.currentPage = this.currentPage - 1
                }
            }
            this.refreshModifyInfo()
        },
        // 解决下拉框无响应
        changeSelectOption(){
            this.$forceUpdate()
        }
    }
}