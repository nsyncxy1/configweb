import Mock from 'mockjs'
Mock.mock(/\/login/,'get',{
     "state" : 1,
     "code":200,
     "data" :function () {
        return {
            token : 'DKUSER20210309'
        }
     }
})
Mock.mock(/\/logout/,'get',{
    "state":1,
    "code":200,
})
Mock.mock(/\/dk_admin\/user/,'post',{
    "state":1,
    "code":200,
    "data":function () {
        let length = 10
        let listData = []
        for(let i=0;i<length;i++)
        {
            let list = {
                ID:i,
                user:'user'+i,
                name:'name'+i,
                part:parseInt(Math.random()*6),
                mobile:'12345678',
                state:1,
                time:new Date(),
                roleID:[0,1]
            }
            listData.push(list)
        }
        return listData
    }
})
export default Mock