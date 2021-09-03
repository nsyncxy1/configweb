'use strict'
module.exports = {
    publicPath:'./',
    devServer:{
        open:true,
        proxy:{
            '/api':{
                target:'https://nm.dktec.cn/prod-api', //测试服务器
                changeOrigin:true,
                pathRewrite:{
                    '^/api':''
                }
            }
        }
    },
    configureWebpack:(config) => {
        if(process.env.NODE_ENV === 'production')
        {
            config.optimization.minimizer[0].options.terserOptions.compress.drop_console = true
        }
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
