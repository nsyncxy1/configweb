import request ,{shell}from '@/utils/request'
const documentApi = {
    // 执行命令
    handleShell(data){
      return request({
          method:'post',
          url:'/file/command',
          data
      })
    },
    // 获取文件目录
    getDocumentList(){
        return request({
            method:'get',
            url:'/file/getDirectory'
        })
    },
    // sftp前置请求
    getSftp(params){
        return shell({
            method:'get',
            url:'/sftp/setParams',
            params
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
    updateDocumentInfo(data){
        return request({
            method:'post',
            url:'/file/write',
            data
        })
    }
}
export default documentApi
