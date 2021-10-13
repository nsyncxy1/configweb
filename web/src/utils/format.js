import {Base64} from "js-base64";
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
// 生成随机字母
export function randomCoding(){
    let ranNum = Math.ceil(Math.random() * 25);
    return String.fromCharCode(65+ranNum)
}
export function handleSqlBase64(sql) {
    let str = sql
    if(str)
    {
        str = randomCoding() + Base64.encode(str)
        str = Base64.encode(str)
        return str
    }else {
        console.log('sql is empty!!!')
    }
}
