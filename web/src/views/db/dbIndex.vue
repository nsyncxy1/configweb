<template>
    <div class="db-wraper">
        <div>
            <div class="__app-header__"></div>
        </div>
        <div>
             <router-view/>
        </div>
    </div>
</template>
    <router-view></router-view>
<script>
    export default {
        name: "dbIndex",
        data(){
            return {

            }
        }
    }
</script>

<style scoped>
    .__app-header__{
        height: 10px;
        background: black;
    }
</style>
