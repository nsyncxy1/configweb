<template>
    <div class="app-container">
        <!--<el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="任务名" prop="taskName">
            <el-input
              v-model="queryParams.taskName"
              placeholder="请输入任务名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>-->
        <!--<el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button
                        type="primary"
                        plain
                        icon="el-icon-plus"
                        size="mini"
                        @click="handleAdd"
                        v-hasPermi="['farming:patrol_task:add']"
                >新增</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>-->
        <el-form :inline="true"  class="demo-form-inline">
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
                <el-button type="primary" @click="getValue">执行</el-button>
            </el-form-item>
        </el-form>
        <el-table v-loading="loading" :data="patrol_taskList"
                  row-key="taskId" :tree-props="treeProp" :cell-style="listCellStyle" :header-cell-style="listStyle">
            <el-table-column label="文件名" align="center" prop="taskName" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope" v-if="scope.row.type === 2">
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

        <el-pagination
                class="pagination"
                background
                v-show="total"
                :total="total"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                layout="prev, pager, next,jumper"
                @pagination="getList"
        />

        <!-- 添加或修改巡查任务对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="文件名" prop="taskName">
                    <el-input v-model="form.taskId" placeholder="请输入文件名"/>
                </el-form-item>
                <el-form-item label="内容">
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer" v-show="infoFlag">
                <el-button type="primary" @click="submitForm">确定</el-button>
                <el-button @click="cancel">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import CodeMirrorEditor from '@/components/editor/codeMirror'
    export default {
        name: "index",
        data() {
            return {
                treeProp:{
                    children: 'children',
                    hasChildren: 'children.length'
                },
                infoFlag:false,
                // 遮罩层
                loading: true,
                // 选中数组
                ids: [],
                // 非单个禁用
                single: true,
                // 非多个禁用
                multiple: true,
                // 显示搜索条件
                showSearch: true,
                // 总条数
                total: 0,
                // 巡查任务表格数据
                patrol_taskList: [],
                // 弹出层标题
                title: "",
                // 是否显示弹出层
                open: false,
                // 任务状态字典
                taskStatusOptions: [],
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                },
                // 表单参数
                form: {},
                // 表单校验
                rules: {
                },
                cmTheme: "default", // codeMirror主题

                // codeMirror主题选项

                cmThemeOptions: [

                    "default",

                    "3024-day",

                    "3024-night",

                    "abcdef",

                    "ambiance",

                    "ayu-dark",

                    "ayu-mirage",

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

                    "material-darker",

                    "material-palenight",

                    "material-ocean",

                    "mbo",

                    "mdn-like",

                    "midnight",

                    "monokai",

                    "moxer",

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

                    "solarized dark",

                    "solarized light",

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

                cmEditorMode: "default", // 编辑模式

                // 编辑模式选项

                cmEditorModeOptions: [

                    "default",

                    "json",

                    "sql",

                    "javascript",

                    "css",

                    "xml",

                    "html",

                    "yaml",

                    "markdown",

                    "python"

                ],

                cmMode: "application/json", //codeMirror模式

                jsonIndentation: 2, // json编辑模式下，json格式化缩进 支持字符或数字，最大不超过10，默认缩进2个空格

                autoFormatJson: true // json编辑模式下，输入框失去焦点时是否自动格式化，true 开启， false 关闭
            };
        },
        components: {
            CodeMirrorEditor
        },
        created() {
            this.getList();
            /*this.getDicts("sys_yes_no").then(response => {
                this.taskStatusOptions = response.data;
            });*/
        },
        mounted(){
            let value = localStorage.getItem('codeStorage')
            this.$refs.cmEditor.setValue(JSON.stringify(value));
        },
        methods: {
            /** 查询巡查任务列表 */
            getList() {
                this.loading = true;
                console.log('getList:')
                const response = {
                    rows:[
                        {
                            taskId:1,
                            taskName:'1',
                            type:1,
                            children:[
                                {
                                    taskId:11,
                                    taskName:11,
                                    type:1,
                                    children:[
                                        {
                                            taskId:111,
                                            taskName:111,
                                            type:2,
                                        },
                                        {
                                            taskId:112,
                                            taskName:112,
                                            type:2,
                                        }
                                    ]
                                },
                                {
                                    taskId: 12,
                                    taskName: 12,
                                    type:1,
                                    children: []
                                }
                            ]
                        }
                    ],
                    total:100
                }
                this.patrol_taskList = response.rows;
                this.total = response.total;
                this.loading = false;
            },
            // 取消按钮
            cancel() {
                this.open = false;
                this.reset();
            },
            // 表单重置
            reset() {
                this.form = {
                    taskId: null,
                    taskName: null,
                };
                //this.resetForm("form");
            },
            /** 修改按钮操作 */
            // eslint-disable-next-line no-unused-vars
            handleUpdate(row) {
                this.$router.push({
                    name:'documentConfig'
                })
                /*this.reset();
                // eslint-disable-next-line no-unused-vars
                const taskId = row.taskId
                this.form = row;
                this.open = true;
                this.title = "修改文件";
                this.infoFlag = true*/
            },
            /** 提交按钮 */
            submitForm() {
                console.log(this.form.taskName)
                /*this.$refs["form"].validate(valid => {
                    if (valid) {
                        if (this.form.taskId != null) {
                            this.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        } else {
                            this.msgSuccess("新增成功");
                            this.open = false;
                            this.getList();
                        }
                    }
                });*/
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
                localStorage.setItem('codeStorage',content)
                console.log(content);

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

<style scoped>
.pagination{
    margin-top: 20px;
}
.demo-form-inline{
    display: flex;
    justify-content: flex-start;
}
</style>
<style>

    .CodeMirror {
        text-align: left;
        width: 400px;
        line-height: 30px;
        /*position: absolute;

        top: 80px;

        left: 2px;

        right: 5px;

        bottom: 0px;*/

    //padding: 2px;

    //height: 100px;

    //overflow-y: auto;
    //overflow-x:auto;
    }

</style>
<style lang="scss" scoped>

    .code-mirror-div {

        /*position: absolute;

        top: 30px;

        left: 2px;

        right: 5px;

        bottom: 0px;*/

        //padding: 2px;

        /*.tool-bar {

            top: 20px;

            margin: 30px 2px 0px 20px;

        }*/

    }

</style>
