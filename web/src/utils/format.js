export function formatTime(time) {
    let date = new Date(time)
    date = `${date.getFullYear()}-(${date.getMonth()+1})-${date.getDay()}`
    return date
}
export function phoneLength(val) {
    if(val && typeof val === "string")
    {
        val = val.substring(0,11)
        return val
    }
}
// 获取文件名及后缀
export function getFileName(value){
    let reg = /[^\\/]*[\\/]+/g
    const name = value.replace(reg,'')
    const lastIndex = name.lastIndexOf('.')
    if(lastIndex != -1)
    {
        const postfix = name.substr(lastIndex+1)
        return postfix
    }else {
        return false
    }
}
