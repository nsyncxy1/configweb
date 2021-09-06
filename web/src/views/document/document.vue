<template>
    <div class="app-container document-wraper">
        <el-form :inline="true"  class="document-form-inline">
            <el-form-item label="编辑模式">
                <el-select
                        v-model="cmEditorMode"
                        placeholder="请选择"
                        size="middle"
                        style="width:150px"
                        @change="onEditorModeChange"
                >
                    <el-option

                            v-for="item in cmEditorModeOptions"

                            :key="item"

                            :label="item"

                            :value="item"

                    ></el-option>

                </el-select>
            </el-form-item>
            <el-form-item label="主题模式">
                <el-select v-model="cmTheme" placeholder="请选择" size="middle" style="width:150px">

                    <el-option v-for="item in cmThemeOptions" :key="item" :label="item" :value="item"></el-option>

                </el-select>
            </el-form-item>
            <el-form-item label="内容">
                <code-mirror-editor

                        ref="cmEditor"

                        :cmTheme="cmTheme"

                        :cmMode="cmMode"

                        :autoFormatJson="autoFormatJson"

                        :jsonIndentation="jsonIndentation"

                ></code-mirror-editor>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="handleShell">执行</el-button>
            </el-form-item>
        </el-form>
        <el-table v-loading="loading" :data="listData"
                  row-key="path" :tree-props="treeProp"
                  :cell-style="listCellStyle">
            <el-table-column label="文件名" align="center" prop="name" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope" v-if="scope.row.type === false">
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-edit"
                            @click="handleUpdate(scope.row)"
                    >修改</el-button>
                    <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-delete"
                            @click="handleDelete(scope.row)"
                    >删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--<el-pagination
                class="pagination"
                background
                v-show="total"
                :total="total"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                layout="prev, pager, next,jumper"
                @pagination="getList"
        />-->
    </div>
</template>

<script>
    import CodeMirrorEditor from '@/components/editor/codeMirror'
    // eslint-disable-next-line no-unused-vars
    import documentApi from "../../api/documentApi";
    export default {
        name: "index",
        data() {
            return {
                treeProp:{
                    children: 'child',
                    hasChildren: '!type'
                },
                command:'',
                infoFlag:false,
                // 遮罩层
                loading: true,
                listData:[],
                cmTheme: "default", // codeMirror主题
                // codeMirror主题选项
                cmThemeOptions: [

                    "default",

                    "3024-day",

                    "3024-night",

                    "abcdef",

                    "ambiance",
                    "base16-dark",

                    "base16-light",

                    "bespin",

                    "blackboard",

                    "cobalt",

                    "colorforth",

                    "darcula",

                    "dracula",

                    "duotone-dark",

                    "duotone-light",

                    "eclipse",

                    "elegant",

                    "erlang-dark",

                    "gruvbox-dark",

                    "hopscotch",

                    "icecoder",

                    "idea",

                    "isotope",

                    "lesser-dark",

                    "liquibyte",

                    "lucario",

                    "material",


                    "mbo",

                    "mdn-like",

                    "midnight",

                    "monokai",


                    "neat",

                    "neo",

                    "night",

                    "nord",

                    "oceanic-next",

                    "panda-syntax",

                    "paraiso-dark",

                    "paraiso-light",

                    "pastel-on-dark",

                    "railscasts",

                    "rubyblue",

                    "seti",

                    "shadowfox",


                    "the-matrix",

                    "tomorrow-night-bright",

                    "tomorrow-night-eighties",

                    "ttcn",

                    "twilight",

                    "vibrant-ink",

                    "xq-dark",

                    "xq-light",

                    "yeti",

                    "yonce",

                    "zenburn"

                ],
                cmEditorMode: "markdown", // 编辑模式
                // 编辑模式选项
                cmEditorModeOptions: [
                    "markdown",
                    "json",

                    "sql",

                    "javascript",

                    "css",

                    "xml",

                    "html",

                    "yaml",

                    "python"

                ],
                cmMode: "text/plain", //codeMirror模式
                jsonIndentation: 2, // json编辑模式下，json格式化缩进 支持字符或数字，最大不超过10，默认缩进2个空格
                autoFormatJson: true // json编辑模式下，输入框失去焦点时是否自动格式化，true 开启， false 关闭
            };
        },
        components: {
            CodeMirrorEditor
        },
        created() {
            this.getList();
        },
        mounted(){
            let value = localStorage.getItem('codeStorage')
            this.$refs.cmEditor.setValue(value);
        },
        methods: {
            async handleShell(){
                const command = this.getValue()
                const params = {
                    command
                }
                const res = await documentApi.handleShell(params)
                if(res.code == 200)
                {
                    this.$notify({
                        title: '成功',
                        message: res.msg,
                        type: 'success'
                    });
                }
            },
            /** 查询巡查任务列表 */
            async getList() {
                this.loading = true;
                const res = await documentApi.getDocumentList()
                console.log(res)
                if(res.code == 200)
                {
                    this.listData = res.data
                    this.loading = false;
                    console.log(this.listData)
                    this.$forceUpdate()
                }
            },
            /** 修改按钮操作 */
            // eslint-disable-next-line no-unused-vars
            handleUpdate(row) {
                const path = row.path
                const name = row.name
                this.$router.push({
                    name:'documentConfig',
                    params:{
                        name,
                        path
                    }
                })
            },
            /** 删除按钮操作 */
            handleDelete(row) {
                const taskIds = row.taskId || this.ids;
                this.$confirm('是否确认删除巡查任务编号为"' + taskIds + '"的数据项?', "警告", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(function() {
                    return
                }).then(() => {
                    this.getList();
                    this.msgSuccess("删除成功");
                })
            },
            listCellStyle({columnIndex}){
                if(columnIndex == 0){
                    return 'text-align:left'
                }else{
                    return 'text-align:center'
                }
            },
            // 切换编辑模式事件处理函数

            onEditorModeChange(value) {

                switch (value) {

                    case "json":

                        this.cmMode = "application/json";

                        break;

                    case "sql":

                        this.cmMode = "sql";

                        break;

                    case "javascript":

                        this.cmMode = "javascript";

                        break;

                    case "xml":

                        this.cmMode = "xml";

                        break;

                    case "css":

                        this.cmMode = "css";

                        break;

                    case "html":

                        this.cmMode = "htmlmixed";

                        break;

                    case "yaml":

                        this.cmMode = "yaml";

                        break;

                    case "markdown":

                        this.cmMode = "markdown";

                        break;

                    case "python":

                        this.cmMode = "python";

                        break;

                    default:

                        this.cmMode = "application/json";

                }

            },
            //获取内容

            getValue() {
                let content = this.$refs.cmEditor.getValue();
                const contents =localStorage.getItem('codeStorage')
                if(content === contents)
                {
                    return content
                }
                else {
                    localStorage.setItem('codeStorage',content)
                    return content
                }
            },
            //修改内容

            setValue() {

                let jsonValue = {

                    name: "laiyu",

                    addr: "广东省深圳市",

                    other: "nothing",

                    tel: "168888888",

                    intro: [{ item1: "item1" }]

                };



                this.$refs.cmEditor.setValue(JSON.stringify(jsonValue));

            }
        }
    }
</script>
