import * as types from '../mutation-types'
import {parse} from 'qs'
import {api} from '../../api'


const state = {
    resources: {
        restMem: 0,
        usedMem: 0,
        restCpu: 0,
        usedCpu: 0,
        restDisk: 0,
        usedDisk: 0,
    },

    tasks: [
        {
            title: 'Killed',
            icon: 'el-icon-delete',
            value: 0,
            color: '#41b883'
        },
        {
            title: 'Running',
            icon: 'el-icon-loading',
            value: 0,
            color: '#428bca'
        },
        {
            title: 'Failed',
            icon: 'el-icon-circle-close',
            value: 0,
            color: '#f60000'
        },
        {
            title: 'Finished',
            icon: 'el-icon-circle-check',
            value: 0,
            color: '#ffa000'
        },
        {
            title: 'Lost',
            icon: 'el-icon-circle-cross',
            value: 0,
            color: '#656565'
        },
        {
            title: 'Killing',
            icon: 'el-icon-loading',
            value: 0,
            color: '#656565'
        }
    ]
}


// getters
const getters = {
    getMesosResource: state => state.resources,
    getMesosTask: state => state.tasks
}


// actions
const actions = {
    queryMesos({commit}, payload){
        api.mesosService.queryMesos(parse(payload))
            .then(
                function (res) {
                    commit(types.Load_Mesos, res);
                }.bind(this))
            .catch(
                function (err) {
                    console.log(err);
                }.bind(this));
    },

}

// mutations
const mutations = {
    [types.Load_Mesos](state, data){
        state.resources = {
            restMem: (data.data.totalMem - data.data.usedMem) / 1000,
            usedMem: data.data.usedMem / 1000,
            restCpu: data.data.totalCpus - data.data.usedCpus,
            usedCpu: data.data.usedCpus,
            restDisk: (data.data.totalDisk - data.data.usedDisk),
            usedDisk: data.data.usedDisk / 1000,
        };
        state.tasks = [
            {
                title: 'Killed',
                icon: 'el-icon-delete',
                value: data.data.killedTasks,
                color: '#41b883'
            },
            {
                title: 'Running',
                icon: 'el-icon-loading',
                value: data.data.runningTasks,
                color: '#428bca'
            },
            {
                title: 'Failed',
                icon: 'el-icon-circle-close',
                value: data.data.failedTasks,
                color: '#f60000'
            },
            {
                title: 'Finished',
                icon: 'el-icon-circle-check',
                value: data.data.finishedTasks,
                color: '#ffa000'
            },
            {
                title: 'Lost',
                icon: 'el-icon-circle-cross',
                value: data.data.lostTasks,
                color: '#656565'
            },
            {
                title: 'Killing',
                icon: 'el-icon-loading',
                value: data.data.killingTasks,
                color: '#656565'
            }
        ]
    }
}

export default {
    state,
    getters,
    actions,
    mutations
}
