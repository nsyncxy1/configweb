<template>
    <div class="shells-wraper" id="shells">
 <!--       <a href='https://gitee.com/zmzhou-star/web-shell' style="float: right;">
            <img src='https://gitee.com/zmzhou-star/web-shell/widgets/widget_1.svg' alt='Fork me on Gitee'/>
        </a>-->
        <div id="login" class="login">
            <h1>登录Linux远程主机</h1>
            <form action="#">
                <input type="text" id="host" required="required" placeholder="主机IP" value="127.0.0.1"/>
                <input type="text" id="username" value="root" required="required" placeholder="用户名" />
                <input type="password" id="password" required="required" placeholder="密码"/>
                <input type="text" id="port" value="22" required="required" placeholder="端口" />
                <button class="button" @click="login">登录</button>
            </form>
<!--            <p class="github-star">
                <a href="https://gitee.com/zmzhou-star/web-shell/stargazers">
                    <img src="https://gitee.com/zmzhou-star/web-shell/badge/star.svg?theme=dark" alt="star">
                </a>
                <a href="https://gitee.com/zmzhou-star/web-shell/members">
                    <img src="https://gitee.com/zmzhou-star/web-shell/badge/fork.svg?theme=dark" alt="fork">
                </a>
                <a href="https://github.com/zmzhou-star/web-shell/stargazers" target="_blank" rel="noopener">
                    <img src="https://badgen.net/github/stars/zmzhou-star/web-shell?icon=github&color=4ab8a1" data-origin="https://badgen.net/github/stars/zmzhou-star/web-shell?icon=github&color=4ab8a1" alt="stars">
                </a>
                <a href="https://github.com/zmzhou-star/web-shell/members" target="_blank" rel="noopener">
                    <img src="https://badgen.net/github/forks/zmzhou-star/web-shell?icon=github&color=4ab8a1" data-origin="https://badgen.net/github/forks/zmzhou-star/web-shell?icon=github&color=4ab8a1" alt="forks">
                </a>
            </p>-->
        </div>
        <div id="menu" class="menu" style="display: none">
            <label>菜单</label>
            <div @click="loginSftp">SFTP</div>
        </div>
        <div id="terminal" class="indexContainer"></div>
    </div>
</template>

<script>
    import $ from 'jquery'
    import '@/assets/static/css/xterm.css'
    import '@/assets/static/css/webShell.css'
    import {Terminal} from '@/assets/static/js/xterm.js'
    //import '@/assets/static/js/jquery-3.5.1.min.js'
    import WebSocketClient from '@/assets/static/js/web-socket.js'
    // eslint-disable-next-line no-unused-vars
    import '@/assets/static/js/crypto-js.min.js'
    import {encrypt} from'@/assets/static/js/web-shell.js'
    // eslint-disable-next-line no-unused-vars
    import documentApi from "@/api/documentApi";
    export default {
        name: "shell",
        data(){
            return {
                api:process.env["VUE_APP_BASE_API"],
                term:null,
                client:null,
                params:null,
                loginParmas:null,
                selection:null,
                rows:null,
                cols: null,
            }
        },
        mounted(){

            /*this.$nextTick(()=>{
                this.init()
            })*/
            this.init()
        },
        methods:{
            init(){
                //console.log('init:')
                /*$("#login").hide();
                $("#menu").show();*/
                //const _this = this
                // eslint-disable-next-line no-unused-vars
                let url = window.location.href;
                /*if (!url.endsWith("/#")) {
                    window.location.href = url;
                }*/
                let num1 = Math.round(Math.random()* 360);
                let num2 = Math.round(Math.random()* 360);
                $('#shells').css({background: 'linear-gradient(to left bottom, hsl(' + num1 + ', 100%, 85%) 0%,hsl(' + num2 + ', 100%, 85%) 100%)'})
                // 获取容器宽高/字号大小，定义行数和列数
                this.rows = document.querySelector(".indexContainer").offsetHeight / 16 - 5;
                this.cols = document.querySelector(".indexContainer").offsetWidth / 9;
                this.term = new Terminal({
                    rendererType: "canvas", //渲染类型
                    pid: 1,
                    name: 'terminal',
                    rows: parseInt(this.rows), //行数
                    cols: parseInt(this.cols),
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
                //console.log(this.term)
                this.term.write('Hello from \x1B[1;3;31mxterm.js\x1B[0m $ ')
                // 换行并输入起始符“$”
                this.term.prompt = () => {
                    this.term.write("\r\n$ ");
                };
                // eslint-disable-next-line no-undef
                this.client = new WebSocketClient();
                this.params = {
                    operate:'connect',
                    host: '',//IP
                    port: '22',//端口号
                    username: '',//用户名
                    password: ''//密码
                }
                this.loginParams = {};
                //选中复制
                this.term.onSelectionChange(function() {
                    if (this.term.hasSelection()) {
                        this.selection = this.term.getSelection();
                    }
                });
                this.term.attachCustomKeyEventHandler(function(ev) {
                    //粘贴 ctrl+v
                    if (ev.keyCode === 86 && ev.ctrlKey) {
                        this.client.sendClientData("\x00" + this.selection);
                        this.selection = "";
                    }
                });
                window.addEventListener("resize", this.resizeScreen);
            },
            login() {
                //console.log('login:')
                //const this = this;
                //console.log(this.term)
                //console.log( $("#username").val())
                let params = {
                    operate:'connect',
                    host: $("#host").val(),//IP
                    port: $("#port").val(),//端口号
                    username: $("#username").val(),//用户名
                    password:encrypt($("#password").val())//密码
                }
                //console.log(params)
                this.loginParams = params;
                // 修改页面标签
                document.title += " " + params.host;
                this.connect(params, 'terminal')
            },
            connect(params, divId) {
                //console.log('connect:')
                const _this = this
                this.term.open(document.getElementById(divId), true);
                //在页面上显示连接中...
                this.term.write('\r\nConnecting...');
                //console.log(this.client)
                //执行连接操作
                this.client.connect({
                    onError: function (error) {
                        //连接失败回调
                        _this.term.write('Error: ' + error + '\r\n');
                    },
                    onConnect: function () {
                        //console.log('onConnect:')
                        //连接主机
                        //console.log(params)
                        //console.log(_this.client)
                        _this.client.send(params);
                        $("#login").hide();
                        $("#menu").show();
                    },
                    onClose: function () {
                        //连接关闭回调
                        _this.term.write("\rconnection closed");
                        $("#login").show();
                    },
                    onData: function (data) {
                        //收到数据时回调
                        _this.term.write(data);
                    }
                });
                this.term.onData(e => {
                    //键盘输入时的回调函数
                    this.client.sendClientData(e);
                })
            },
            loginSftp() {
                //const this = this
                //console.log('loginSftp:')
                const params = {
                    params:this.loginParams
                }
                // eslint-disable-next-line no-unused-vars
                documentApi.getSftp(params).then(response => {
                    const res = response.data
                    if(res.code === 200)
                    {
                        localStorage.setItem('sessionId',res.data)
                        //localStorage.setItem('loginParams',JSON.stringify(params))
                        this.loginParams.operate = 'sftp'
                        const host = window.location.origin
                        window.open( host+this.api+'/#/sftp?params=' + encodeURIComponent(JSON.stringify(this.loginParams)))
                    }
                 })
            },
            // 内容全屏显示-窗口大小发生改变时
            resizeScreen(size) {
                console.log('resizeScreen:')
                console.log("size", size);
                this.term.scrollToBottom();
                try {
                    // fitAddon.fit();
                    // 窗口大小改变时触发xterm的resize方法，向后端发送行列数，格式由后端决定
                    // eslint-disable-next-line no-unused-vars
                    this.term.onResize(size => {
                        this.client.send({ Op: "resize", Cols: this.cols, Rows: this.rows });
                    });
                } catch (e) {
                    console.log("e", e.message);
                }
            }
        }
    }
</script>

<!--<style scoped>
    .xterm {
        font-feature-settings: "liga" 0;
        position: relative;
        user-select: none;
        -ms-user-select: none;
        -webkit-user-select: none;
    }

    .xterm.focus,
    .xterm:focus {
        outline: none;
    }

    .xterm .xterm-helpers {
        position: absolute;
        top: 0;
        /**
         * The z-index of the helpers must be higher than the canvases in order for
         * IMEs to appear on top.
         */
        z-index: 5;
    }

    .xterm .xterm-helper-textarea {
        padding: 0;
        border: 0;
        margin: 0;
        /* Move textarea out of the screen to the far left, so that the cursor is not visible */
        position: absolute;
        opacity: 0;
        left: -9999em;
        top: 0;
        width: 0;
        height: 0;
        z-index: -5;
        /** Prevent wrapping so the IME appears against the textarea at the correct position */
        white-space: nowrap;
        overflow: hidden;
        resize: none;
    }

    .xterm .composition-view {
        /* TODO: Composition position got messed up somewhere */
        background: #000;
        color: #FFF;
        display: none;
        position: absolute;
        white-space: nowrap;
        z-index: 1;
    }

    .xterm .composition-view.active {
        display: block;
    }

    .xterm .xterm-viewport {
        /* On OS X this is required in order for the scroll bar to appear fully opaque */
        background-color: #000;
        overflow-y: scroll;
        cursor: default;
        position: absolute;
        right: 0;
        left: 0;
        top: 0;
        bottom: 0;
    }

    .xterm .xterm-screen {
        position: relative;
    }

    .xterm .xterm-screen canvas {
        position: absolute;
        left: 0;
        top: 0;
    }

    .xterm .xterm-scroll-area {
        visibility: hidden;
    }

    .xterm-char-measure-element {
        display: inline-block;
        visibility: hidden;
        position: absolute;
        top: 0;
        left: -9999em;
        line-height: normal;
    }

    .xterm {
        cursor: text;
    }

    .xterm.enable-mouse-events {
        /* When mouse events are enabled (eg. tmux), revert to the standard pointer cursor */
        cursor: default;
    }

    .xterm.xterm-cursor-pointer {
        cursor: pointer;
    }

    .xterm.column-select.focus {
        /* Column selection mode */
        cursor: crosshair;
    }

    .xterm .xterm-accessibility,
    .xterm .xterm-message {
        position: absolute;
        left: 0;
        top: 0;
        bottom: 0;
        right: 0;
        z-index: 10;
        color: transparent;
    }

    .xterm .live-region {
        position: absolute;
        left: -9999px;
        width: 1px;
        height: 1px;
        overflow: hidden;
    }

    .xterm-dim {
        opacity: 0.5;
    }

    .xterm-underline {
        text-decoration: underline;
    }
</style>
<style scoped>
    html, body{
        width: 100%;
        height: 100%;
    }
    body{
        font-family: 'Open Sans',sans-serif;
        margin: 0;
        background-color: slategray;
    }
    .indexContainer{
        width: 100%;
        height: calc(100% - 30px);
    }
    .menu {
        color: #34495e;
        width: 100%;
        height: 30px;
        line-height: 30px;
        background-color: rgba(243, 242, 238, 1) !important;
    }
    .menu div {
        padding: 0 10px;
        display: inline-block;
        cursor: pointer;
    }
    .menu div:hover {
        background-color: #4a77d4;
    }
    .menu div:active {
        background-color: #3762bc;
    }
    #login{
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
        margin: 0 auto;
        width: 300px;
    }
    .login h1{
        color: #34495e;
        text-shadow: 0 0 5px;
        letter-spacing: 1px;
        text-align: center;
    }
    h1{
        font-size: 2em;
        margin: 0.67em 0;
    }
    .login input{
        width: 278px;
        height: 18px;
        margin-bottom: 10px;
        outline: none;
        padding: 10px;
        font-size: 13px;
        color: #fff;
        border: 1px solid #312E3D;
        border-bottom: 1px solid #56536A;
        border-radius: 4px;
        background-color: #2D2D3F;
    }
    .button{
        width: 300px;
        min-height: 20px;
        display: block;
        background-color: #58a6ff;
        border: 1px solid #37b6ff;
        color: #fff;
        padding: 9px 14px;
        font-size: 15px;
        line-height: normal;
        border-radius: 5px;
        margin: 0;
    }
    .sftp-container > div{
        float: left;
        border: 2px solid #3762bc;
    }
    .sftp-container{
        width: 100%;
        height: 100%;
    }
    .sftp-file-tree-div{
        width: calc(30% - 8px);
        height: 80%;
    }
    .sftp-file-tree-div > input{
        padding: 0 15px;
        height: 30px;
        width: calc(100% - 180px);
        background-color: slategray;
        color: #fff;
        border: 1px solid #99D3F5;
        outline: none;
        border-radius: 5px;
    }
    .sftp-file-tree-div .sftp-file-tree{
        width: 100%;
        height: calc(100% - 40px);
        overflow-y: scroll;
    }
    .sftp-file-detail{
        width: 70%;
        height: 80%;
        overflow-y: scroll;
    }
    .sftp-message-console {
        width: calc(100% - 4px);
        height: calc(20% - 8px);
        color: whitesmoke;
        font-size: 20px;
    }
    input::-webkit-input-placeholder{
        color: whitesmoke;
    }
    /* Mozilla Firefox 19+ */
    input::-moz-placeholder{
        color: whitesmoke;
    }

    .file-detail-table {
        width: 100%;
    }
    .file-detail-table tr{
        width: 100%;
        height: 24px;
        line-height: 24px;
    }
    table.file-detail-table {
        font-family: verdana,arial,sans-serif;
        font-size:11px;
        color:#333333;
        border-width: 1px;
        border-color: #a9c6c9;
        border-collapse: collapse;
    }
    table.file-detail-table th {
        background-color: #3F3F3F;
        color: white;
    }
    table.file-detail-table th:first-child, table.file-detail-table td:first-child {
        padding-left: 10px;
    }
    table.file-detail-table th, table.file-detail-table td {
        border-bottom: 1px solid #a9c6c9;
        padding: 6px 0;
        text-align: left;
    }
    table.file-detail-table tr:nth-child(even) { background-color:#c3dde0; }
    table.file-detail-table tr:nth-child(odd) { background-color:#d4e3e5; }
    table.file-detail-table tr:hover{
        background-color: #dcddc0;
    }
    .data-cell .file-name {
        cursor: pointer;
    }
    .sftp-file-tree-div form {
        padding-left: 10px;
        width: 130px;
        display: inline-block;
    }
    .select-file {
        position: relative;
        display: inline-block;
        background: #D0EEFF;
        border: 1px solid #99D3F5;
        border-radius: 5px;
        padding: 5px 15px;
        color: #1E88C7;
        text-decoration: none;
        line-height: 20px;
    }
    .select-file input {
        position: absolute;
        width: 120px;
        height: 30px;
        font-size: 100px;
        right: 0;
        top: 0;
        opacity: 0;
    }
    .select-file:hover {
        background: #AADFFD;
        border-color: #78C3F3;
        color: #004974;
        text-decoration: none;
    }
    .delete-data {
        background-color: #EA633E;
        cursor: pointer;
        color: white;
        border: 0;
    }
    .github-star a {
        padding: 10px 10px 0;
    }
    .github-star {
        display: flex;
        flex-wrap: nowrap;
        flex-direction: row;
        align-content: center;
        justify-content: center;
    }
</style>-->
