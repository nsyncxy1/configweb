import request from '@/utils/request'
const documentApi = {
    // 获取文件目录
    getDocumentList(){
        return request({
            method:'get',
            url:'/file/getDirectory'
        })
    },
    // 获取文件内容
    getDocumentInfo(params){
        return request({
            method:'post',
            url:'/file/read',
            params
        })
    },
    // 编辑文件内容
    updateDocumentInfo(params){
        return request({
            method:'post',
            url:'/file/write',
            params
        })
    }
}
export default documentApi
