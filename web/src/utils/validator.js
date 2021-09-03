export const sortValidata = (rule,val,callback) => {
    const reg = /^[1-9]\d*$/
    // console.log(typeof rule)
    if(isNaN(val))
    {
        callback(new Error('排序值必须是整数'))
    } else if(!reg.test(val)){
        callback(new Error('排序值必须是正整数'))
    } else {
        callback()
    }
}
export const phoneValidata = (rule,val,callback) => {
    const reg = /^1[0-9]{10}$/;
    if(!reg.test(val)){
        callback(new Error('请输入正确格式的手机号'))
    }else {
        callback()
    }
}