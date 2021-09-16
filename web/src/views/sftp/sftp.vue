<template>
    <div class="sftp-container">
        <div class="sftp-file-tree-div">
            <input id="current_path" placeholder="当前目录" value="/">
            <form action="#" enctype="multipart/form-data" id="upload_form">
                <a href="javascript:" class="select-file">选择文件上传
                    <input type="text" name="path" id="path">
                    <input type="file" name="file" id="file" multiple>
                </a>
            </form>
            <div id="file_tree" class="sftp-file-tree"></div>
        </div>
        <div id="file_detail" class="sftp-file-detail">
            <table class="file-detail-table">
                <tr>
                    <th>文件名</th>
                    <th>文件类型</th>
                    <th>文件属性</th>
                    <th>目录/链接个数</th>
                    <th>所有者</th>
                    <th>组</th>
                    <th>文件大小</th>
                    <th>修改时间</th>
                    <th>操作</th>
                </tr>
            </table>
        </div>
        <div id="message_console" class="sftp-message-console"></div>
    </div>
</template>

<script>
    import {getToken} from "@/utils/auth";
    import $ from 'jquery'
    import '@/assets/static/css/webShell.css'
    import '@/assets/static/css/jstree/style.min.css'
    //import $ from '@/assets/static/js/jquery-3.5.1.min'
    //import '@/assets/static/js/jquery-3.5.1.min'
    import '@/assets/static/js/web-socket'
    import {uploadFile,checkErr,showTips,refreshTree} from '@/assets/static/js/web-shell'
    import '@/assets/static/js/jstree'
    // eslint-disable-next-line no-unused-vars
    import documentApi from "@/api/documentApi";
    export default {
        name: "sftp",
        data(){
            return {

            }
        },
        mounted(){
            //const _this = this;
            this.$nextTick(()=>{
                this.init()
            })
        },
        methods:{
            async init(){
                const _this = this;
                /*const params = JSON.parse(localStorage.getItem('loginParams'))
                console.log(params)
                // eslint-disable-next-line no-unused-vars
                const res = await documentApi.getSftp(params)*/
                let url = window.location.href;
                let urlParams = window.location.search;
                history.replaceState(null, null, url.replace(urlParams, ""))
                $("#file").change(function () {
                    console.log('#file:')
                    if($(this).val() !== ""){
                        uploadFile();
                    }
                })
                let $fileTree = $('#file_tree');
                let $currentPathInput = $("#current_path");
                // 根目录
                let rootDirectory = { "id": "/", "parent": "#", "text": "/", "icon": "jstree-folder", 'state': { 'opened': true }}
                function init(){
                    console.log('init:')
                    console.log(getToken())
                    $.ajax({
                        url: "/sftp/getFileTree",
                        dataType: "json",
                        data: {path: '/'},
                        type: "get",
                        beforeSend: function (request) {
                            request.setRequestHeader("Authorization", getToken());
                        },
                        success: function (res) {
                            checkErr(res)
                            console.log(res)
                            let fileTree = res.data
                            if (fileTree) {
                                loadTableData(fileTree);
                                fileTree.unshift(rootDirectory)
                                //callback.call(this, fileTree);
                                $fileTree.on("changed.jstree", function (e, data) {
                                    console.log('changedJstree:')
                                    if(data.selected.length) {
                                        let selected = data.instance.get_node(data.selected[0])
                                        console.log('The selected node is: ' + selected.id);
                                        $currentPathInput.val(selected.id)
                                        // 只查询文件夹的下一级文件
                                        if ("jstree-folder" === selected.icon) {
                                            getFileTree(data.instance, selected)
                                        }
                                    }
                                }).jstree({
                                    "multiple" : false,
                                    'check_callback': true,
                                    'data':fileTree
                                })
                            } else {
                                showTips("暂无数据！");
                                $(".file-detail-table .data-cell").remove();
                            }
                        },
                        error: function (xml, err) {
                            console.log(xml, err)
                            _this.$message.error('接口访问异常');
                        }
                    })
                }
                init()
                /*$fileTree.on("changed.jstree", function (e, data) {
                    console.log('changedJstree:')
                    if(data.selected.length) {
                        let selected = data.instance.get_node(data.selected[0])
                        console.log('The selected node is: ' + selected.id);
                        $currentPathInput.val(selected.id)
                        // 只查询文件夹的下一级文件
                        if ("jstree-folder" === selected.icon) {
                            getFileTree(data.instance, selected)
                        }
                    }
                }).jstree({
                    "core" : {
                        "multiple" : false,
                        'check_callback': true,
                        'data' : function (obj, callback){
                            console.log(getToken())
                            $.ajax({
                                url : "/sftp/getFileTree",
                                dataType : "json",
                                data: { path: '/' },
                                type : "get",
                                beforeSend: function(request) {
                                    request.setRequestHeader("Authorization",getToken());
                                },
                                success : function(res) {
                                    /!*res = {
                                        "code":200,
                                        "msg":"OK",
                                        "data":[
                                            {
                                                "id":"/boot",
                                                "parent":"/",
                                                "text":"boot",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"r-xr-xr-x",
                                                "numberOfDir":"5",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"4.0KB",
                                                "modifiedDate":"Aug 31 10:43"
                                            },
                                            {
                                                "id":"/dev",
                                                "parent":"/",
                                                "text":"dev",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"rwxr-xr-x",
                                                "numberOfDir":"19",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"2.9KB",
                                                "modifiedDate":"May 24 09:39"
                                            },
                                            {
                                                "id":"/etc",
                                                "parent":"/",
                                                "text":"etc",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"rwxr-xr-x",
                                                "numberOfDir":"83",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"4.0KB",
                                                "modifiedDate":"Sep  8 15:59"
                                            },
                                            {
                                                "id":"/home",
                                                "parent":"/",
                                                "text":"home",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"rwxr-xr-x",
                                                "numberOfDir":"3",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"4.0KB",
                                                "modifiedDate":"Aug 31 10:50"
                                            },
                                            {
                                                "id":"/lost+found",
                                                "parent":"/",
                                                "text":"lost+found",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"rwx------",
                                                "numberOfDir":"2",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"16.0KB",
                                                "modifiedDate":"Jul 11  2019"
                                            },
                                            {
                                                "id":"/media",
                                                "parent":"/",
                                                "text":"media",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"rwxr-xr-x",
                                                "numberOfDir":"2",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"4.0KB",
                                                "modifiedDate":"Apr 11  2018"
                                            },
                                            {
                                                "id":"/mnt",
                                                "parent":"/",
                                                "text":"mnt",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"rwxr-xr-x",
                                                "numberOfDir":"2",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"4.0KB",
                                                "modifiedDate":"Apr 11  2018"
                                            },
                                            {
                                                "id":"/opt",
                                                "parent":"/",
                                                "text":"opt",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"rwxr-xr-x",
                                                "numberOfDir":"5",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"4.0KB",
                                                "modifiedDate":"Sep 10 17:05"
                                            },
                                            {
                                                "id":"/proc",
                                                "parent":"/",
                                                "text":"proc",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"r-xr-xr-x",
                                                "numberOfDir":"131",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"0B",
                                                "modifiedDate":"May 24 09:37"
                                            },
                                            {
                                                "id":"/root",
                                                "parent":"/",
                                                "text":"root",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"r-xr-x---",
                                                "numberOfDir":"12",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"4.0KB",
                                                "modifiedDate":"Sep 15 09:53"
                                            },
                                            {
                                                "id":"/run",
                                                "parent":"/",
                                                "text":"run",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"rwxr-xr-x",
                                                "numberOfDir":"27",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"820B",
                                                "modifiedDate":"Sep  8 18:07"
                                            },
                                            {
                                                "id":"/srv",
                                                "parent":"/",
                                                "text":"srv",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"rwxr-xr-x",
                                                "numberOfDir":"2",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"4.0KB",
                                                "modifiedDate":"Apr 11  2018"
                                            },
                                            {
                                                "id":"/sys",
                                                "parent":"/",
                                                "text":"sys",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"r-xr-xr-x",
                                                "numberOfDir":"13",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"0B",
                                                "modifiedDate":"May 24 17:37"
                                            },
                                            {
                                                "id":"/tmp",
                                                "parent":"/",
                                                "text":"tmp",
                                                "icon":"jstree-folder",
                                                "fileType":null,
                                                "fileAttr":null,
                                                "numberOfDir":null,
                                                "owner":null,
                                                "group":null,
                                                "size":null,
                                                "modifiedDate":null
                                            },
                                            {
                                                "id":"/usr",
                                                "parent":"/",
                                                "text":"usr",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"rwxr-xr-x",
                                                "numberOfDir":"13",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"4.0KB",
                                                "modifiedDate":"Jul 11  2019"
                                            },
                                            {
                                                "id":"/var",
                                                "parent":"/",
                                                "text":"var",
                                                "icon":"jstree-folder",
                                                "fileType":"目录",
                                                "fileAttr":"rwxr-xr-x",
                                                "numberOfDir":"19",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"4.0KB",
                                                "modifiedDate":"Jul 11  2019"
                                            },
                                            {
                                                "id":"/bin",
                                                "parent":"/",
                                                "text":"bin",
                                                "icon":"/static/img/l.png",
                                                "fileType":"链接文件",
                                                "fileAttr":"rwxrwxrwx",
                                                "numberOfDir":"1",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"7B",
                                                "modifiedDate":"Jul 11  2019"
                                            },
                                            {
                                                "id":"/lib",
                                                "parent":"/",
                                                "text":"lib",
                                                "icon":"/static/img/l.png",
                                                "fileType":"链接文件",
                                                "fileAttr":"rwxrwxrwx",
                                                "numberOfDir":"1",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"7B",
                                                "modifiedDate":"Jul 11  2019"
                                            },
                                            {
                                                "id":"/lib64",
                                                "parent":"/",
                                                "text":"lib64",
                                                "icon":"/static/img/l.png",
                                                "fileType":"链接文件",
                                                "fileAttr":"rwxrwxrwx",
                                                "numberOfDir":"1",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"9B",
                                                "modifiedDate":"Jul 11  2019"
                                            },
                                            {
                                                "id":"/sbin",
                                                "parent":"/",
                                                "text":"sbin",
                                                "icon":"/static/img/l.png",
                                                "fileType":"链接文件",
                                                "fileAttr":"rwxrwxrwx",
                                                "numberOfDir":"1",
                                                "owner":"root",
                                                "group":"root",
                                                "size":"8B",
                                                "modifiedDate":"Jul 11  2019"
                                            }
                                        ]
                                    }*!/
                                    checkErr(res)
                                    console.log(res)
                                    let fileTree = res.data
                                    if(fileTree) {
                                        loadTableData(fileTree);
                                        fileTree.unshift(rootDirectory)
                                        console.log(this)
                                        console.log(fileTree)
                                        callback.call(this, fileTree);
                                        console.log('callback:')
                                        console.log(callback)
                                        return fileTree
                                    }else{
                                        showTips("暂无数据！");
                                        $(".file-detail-table .data-cell").remove();
                                    }
                                },
                                error:function (xml,err) {
                                    console.log(xml,err)
                                    _this.$message.error('接口访问异常');
                                }
                            });
                        }
                    },
                    "checkbox" : {
                        "keep_selected_style" : false
                    },
                    "plugins" : [ "checkbox" ]
                });*/
                function loadTableData(data) {
                    console.log('loadTableData:')
                    let fileDetail = "";
                    $(".file-detail-table .data-cell").remove();
                    console.log('0')
                    // 根目录以外的目录下新增上级目录项
                    if (data[0].id.substring(1).indexOf('/') > 0) {
                        let id = data[0].id;
                        let pid = id.substring(0, id.lastIndexOf('/', id.lastIndexOf('/') - 1) + 1).replace(/(\/*$)/g,"") || '/';
                        fileDetail = "<tr class='data-cell'><td class='file-name jstree-default' id='" + pid + "'>" +
                            "<i class='jstree-icon jstree-folder'></i>..</td><td>上级目录</td>" +
                            "<td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>";
                    }
                    console.log(1)
                    for (const d in data) {
                        let item = data[d];
                        let icon = item.icon;
                        if(icon && !icon.startsWith('jstree')){
                            icon = "' style='background-image: url(" + icon
                                + ");background-position: center center;background-size: auto;'";
                        }
                        fileDetail += "<tr class='data-cell'>";
                        fileDetail += "<td class='file-name jstree-default' id='" + item.id + "'>" +
                            "<i class='jstree-icon " + icon + "'></i>"+ item.text +"</td>";
                        fileDetail += "<td>"+ item.fileType +"</td>";
                        fileDetail += "<td>"+ item.fileAttr +"</td>";
                        fileDetail += "<td>"+ item.numberOfDir +"</td>";
                        fileDetail += "<td>"+ item.owner +"</td>";
                        fileDetail += "<td>"+ item.group +"</td>";
                        fileDetail += "<td>"+ item.size +"</td>";
                        fileDetail += "<td>"+ item.modifiedDate +"</td>";
                        fileDetail += "<td><button class='delete-data' value='" + item.id + "'>删除</button></td></tr>";
                    }
                    console.log(2)
                    //console.log(fileDetail)
                    $(".file-detail-table").append(fileDetail);
                    //console.log('fileDetail:')
                    //console.log(fileDetail)
                    $('.data-cell .file-name').bind('click', function() {
                        console.log('.data-cell .file-name:')
                        let tree = $fileTree.jstree(true);
                        let val = $(this).attr('id');
                        let file = tree.get_node(val)
                        // 单击文件夹设置输入框数据
                        if (file && "jstree-folder" === file.icon) {
                            $currentPathInput.val(val)
                            tree.select_node(val);
                        }
                        $currentPathInput.focus()
                    }).bind('dblclick', function() {
                        let tree = $fileTree.jstree(true);
                        let val = $(this).attr('id');
                        let file = tree.get_node(val)
                        // 双击 除文件夹外都下载
                        if (file && "jstree-folder" !== file.icon) {
                            window.open(window.location.origin + "/sftp/download?path=" + val, '_blank')
                        }
                    });

                    // 删除数据
                    $('.delete-data').bind('click', function() {
                        console.log('delete:')
                        let val = $(this).attr("value");
                        if(window.confirm('你确定要删除“' + val + '”吗？')){
                            console.log(getToken())
                            $.ajax({
                                url : "/sftp",
                                type : "delete",
                                dataType : "json",
                                data: { path: val },
                                beforeSend: function(request) {
                                    request.setRequestHeader("Authorization",getToken());
                                },
                                success: function(res) {
                                    if (checkErr(res)) {
                                        refreshTree();
                                    }
                                },
                                error:function (xml,err) {
                                    console.log(xml,err)
                                    _this.$message.error('接口访问异常');
                                }
                            });
                        }
                    });
                }
                function getFileTree(instance, selectedNode) {
                    console.log('getFileTree:')
                    console.log(getToken())
                    $.ajax({
                        url : "/sftp/getFileTree",
                        dataType : "json",
                        data: { 'path': selectedNode.id },
                        type : "get",
                        beforeSend: function(request) {
                            request.setRequestHeader("Authorization",getToken());
                        },
                        success : function(res) {
                            console.info(res);
                            checkErr(res)
                            let fileTree = res.data
                            if(fileTree && fileTree.length > 0) {
                                selectedNode.children = [];
                                for (const i in fileTree) {
                                    let item = fileTree[i];
                                    instance.create_node(selectedNode, item, "last");
                                }
                                instance.open_node(selectedNode);
                                //全不选择
                                instance.deselect_all();
                                loadTableData(fileTree);
                            }else{
                                showTips("暂无数据！");
                                $(".file-detail-table .data-cell").remove();
                            }
                        },
                        error:function (xml,err) {
                            console.log(xml,err)
                            _this.$message.error('接口访问异常');
                        }
                    });
                }
                function selectNode(nodeId, pNodeId) {
                    console.log('selectNode:')
                    let tree = $fileTree.jstree(true);
                    if (tree.get_node(nodeId)){
                        tree.select_node(nodeId);
                        return true;
                    } else if (pNodeId && tree.get_node(pNodeId)){
                        tree.select_node(pNodeId);
                        if (nodeId !== pNodeId) {
                            $currentPathInput.val(nodeId)
                            $currentPathInput.focus()
                            setTimeout(function () {
                                selectNode(nodeId);
                            }, 1000)
                        }
                        return true;
                    } else {
                        return selectNode(nodeId, nodeId.substring(0, nodeId.lastIndexOf('/')))
                    }
                }
                $currentPathInput.bind("keypress", function(event){
                    console.log('$currentPathInput.bind:')
                    if(event.keyCode === 13){
                        // 删除最后一个/
                        let val = $(this).val().replace(/(\/*$)/g,"");
                        console.log(val)
                        if (val) {
                            selectNode(val);
                        }
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>
