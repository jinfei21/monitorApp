import Vue from 'vue'
import Vuex from 'vuex'
import deploy from './modules/deploy'
import task from './modules/task'
import cleanup from './modules/cleanup'
import rack from './modules/rack'
import slave from './modules/slave'
import mesos from './modules/mesos'
import pauth from './modules/pauth'
import promptmessage from './modules/promptmessage'
import ip from './modules/ip'
import subnet from './modules/subnet'
import event from './modules/event'
import offset from './modules/offset'
import metric from './modules/metric'
import system from './modules/system'

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production';

export default new Vuex.Store({
    modules: {
        deploy,
        task,
        cleanup,
        rack,
        slave,
        mesos,
        pauth,
        subnet,
        ip,
        event,
        offset,
        promptmessage,
        metric,
        system
    },
    strict: debug
})

