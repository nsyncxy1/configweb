<template>
    <div class="demo-wraper">

    </div>
</template>

<script>
    import '@/assets/static/css/xterm.css'
    import '@/assets/static/css/webShell.css'
    import '@/assets/static/js/xterm.js'
    //import $ from '@/assets/static/js/jquery-3.5.1.min.js'
    import '@/assets/static/js/web-socket.js'
    import '@/assets/static/js/crypto-js.min.js'
    import '@/assets/static/js/web-shell.js'
    export default {
        name: "shellDemo",
        data(){
            return {
            }
        },
        created() {
            this.init()
        },
        methods:{
            init(){
                // eslint-disable-next-line no-undef
                const termial = new Terminal({
                    rendererType: "canvas", //渲染类型
                    pid: 1,
                    name: 'terminal',
                    //rows: parseInt(rows), //行数
                    //cols: parseInt(cols),
                    convertEol: true, //启用时，光标将设置为下一行的开头
                    scrollback: 500,//终端中的回滚量
                    disableStdin: false, //是否应禁用输入。
                    cursorStyle: 'underline', //光标样式
                    cursorBlink: true, //光标闪烁
                    tabStopWidth: 8, //制表宽度
                    screenKeys: true, //Xterm下的快捷键
                    theme: {
                        foreground: '#58a6ff', //字体,LightGreen,Orange,SlateBlue,Magenta Purple Red Violet White Yellow
                        background: '#2B2B2B', //背景色
                        // foreground: "#7e9192", //字体
                        // background: "#002833", //背景色
                        cursor: "Orange", //设置光标
                        lineHeight: 16
                    }
                });
                console.log(termial)
            }
        }
    }
</script>

<style scoped>

</style>
