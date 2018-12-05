import Vue from 'vue';

import store from './store';
import axios from 'axios';
import router from './router';
import jwtTokenUtil from "./utils/jwtTokenUtil";

import ElementUI from 'element-ui';
Vue.use(ElementUI);
//import VCharts from 'v-charts';
import 'element-ui/lib/theme-default/index.css';
import './assets/css/color-dark.css';
import './assets/css/main.css';

import iView from 'iview'
Vue.use(iView)
import '!style-loader!css-loader!less-loader!./theme/index.less'
// import VueParticles from 'vue-particles'
// Vue.use(VueParticles);
import VueRouter from 'vue-router'
Vue.use(VueRouter);

Vue.prototype.$http = axios;


//Vue.use(VCharts)
// http request 拦截器
axios.interceptors.request.use(
    config => {
        console.log(config.method)
        let jwtToken = jwtTokenUtil.readAccess();
        // 给http请求的header加上jwt-token
        config.headers['jwt-token'] = jwtToken;
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

new Vue({
    store,
    router,
}).$mount('#app');

