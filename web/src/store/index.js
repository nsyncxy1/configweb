import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
import getters from "@/store/getters";
import user from "@/store/user";
const store = new Vuex.Store({
    getters,
    modules: {
        user
    }
})
export default store
