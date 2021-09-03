<template>
    <el-pagination
            class="data-page"
            background
            layout="prev, pager, next"
            :current-page="currentPage"
            :page-count="pageList"
            :total ="total"
            @current-change="changePage"
            >
    </el-pagination>
</template>

<script>
    export default {
        name: "pageCount",
        props:{
            total:{
                type:Number,
                default:1000
            },
            pageList: {
                type: Number,
                default: 10
            },
            currentPage: {
                type:Number,
                default:1
            }
        },
        data(){
            return {
                // pageList: 10
            }
        },
        methods:{
            changePage(currentPage){
                //console.log(currentPage)
                this.$emit('change-page',currentPage)
            }
        }
    }
</script>

<style scoped>

</style>