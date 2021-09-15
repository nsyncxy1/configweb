import Vue from 'vue'
import App from './App.vue'
import router from "@/router"
import store from "@/store"
import '@/utils/permission'
//import '@/assets/static/js/web-socket.js'
//import '@/styles/reset.scss'
import 'normalize.css/normalize.css'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/styles/index.scss'
import jshint from "jshint";
window.JSHINT = jshint.JSHINT;
import { codemirror } from "vue-codemirror";
import "codemirror/lib/codemirror.css";
// import formCreate from "@form-create/element-ui"
// Vue.use(formCreate)
//导入mock数据
//import '@/mock'
Vue.use(ElementUI)
Vue.use(codemirror);
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
