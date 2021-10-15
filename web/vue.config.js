'use strict'
module.exports = {
    publicPath:'./',
    devServer:{
        open:true,
        proxy:{
            '/api':{
                //target:'http://8.136.153.61:8083', //测试服务器
                //target:'http://localhost:8083', //测试服务器
                //target:'https://nm.dktec.cn/configweb',
                target:'http://172.16.33.185:8083',
                //target:'http://ydyy.nbxczl.cn:444',
                changeOrigin:true,
                pathRewrite:{
                    '^/api':''
                }
            }
        }
    },
    // eslint-disable-next-line no-unused-vars
    configureWebpack:(config) => {
        /*if(process.env.NODE_ENV === 'production')
        {
            config.optimization.minimizer[0].options.terserOptions.compress.drop_console = true
        }*/
    },
    chainWebpack:(config) => {
        // 修改项目默认title
        config.plugin('html')
            .tap(args => {
                args[0].title = '后台管理系统';
                return args
            })
    }
}
