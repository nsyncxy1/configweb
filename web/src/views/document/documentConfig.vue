<template>
    <div class="doc-config-wraper">
        <el-form>
            <el-form-item label="文件名" prop="taskName">
                <el-input v-model="ruleForm.name"   placeholder="请输入文件名" :disabled="true"/>
            </el-form-item>
            <el-form-item label="内容">
                <div class="tool-bar">

                    <span>请选择主题</span>

                    <el-select v-model="cmTheme" placeholder="请选择" size="small" style="width:150px">

                        <el-option v-for="item in cmThemeOptions" :key="item" :label="item" :value="item"></el-option>

                    </el-select>

                    <span style="margin-left: 10px">请选择编辑模式</span>

                    <el-select

                            v-model="cmEditorMode"

                            placeholder="请选择"

                            size="small"

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
                </div>
            </el-form-item>
            <el-form-item>
                <div class="code-mirror-div">
                    <code-mirror-editor

                            ref="cmEditor"

                            :cmTheme="cmTheme"

                            :cmMode="cmMode"

                            :autoFormatJson="autoFormatJson"

                            :jsonIndentation="jsonIndentation"

                    ></code-mirror-editor>

                </div>
            </el-form-item>
        </el-form>
        <div  class="footer">
            <el-button type="primary" @click="submit">确定</el-button>
            <el-button @click="cancel">取消</el-button>
        </div>
    </div>

</template>

<script>
    import CodeMirrorEditor from '@/components/editor/codeMirror'
    import documentApi from "../../api/documentApi";
    export default {
        name: "index",
        data() {
            return {
                ruleForm:{
                    name:'',
                    data:''
                },
                path:null,
                name:null,
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
                cmEditorMode:  "markdown", // 编辑模式
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
        mounted() {
            const _this = this
            _this.$nextTick(() => {
                _this.name = _this.$route.params.name
                _this.path = _this.$route.params.path
                _this.ruleForm.name = _this.name
                console.log("name:"+_this.name+",path:"+_this.path)
                _this.init()
            })
        },
        methods: {
            async init(){
                const res = await documentApi.getDocumentInfo({
                    path:this.path
                })
                /*const res = {
                    code:200,
                    data:'aa\n bb'
                }*/
                console.log(res)
                if(res.code === 200)
                {
                    const data = res.data
                    if(data)
                    {
                        this.setValue(data)
                    }

                }
            },
            async submit(){
                const content = this.getValue()
                const data = {
                    content,
                    path:this.path
                }
                const res = await documentApi.updateDocumentInfo(data)
                console.log(res)
                if(res.code == 200)
                {
                    this.$notify({
                        title: '成功',
                        message: '数据已更新',
                        type: 'success'
                    });
                    this.$router.replace({
                        name:'document'
                    })
                }
            },
            cancel(){
              this.$router.back()
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
                console.log(content);
                return content
            },
            //修改内容
            setValue(data) {
                if(data)
                {
                    console.log('setValue:')
                    this.$refs.cmEditor.setValue(data);
                }
            }
        }
    }
</script>
