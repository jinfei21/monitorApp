import * as types from '../mutation-types'
import {parse} from 'qs'
import {api} from '../../api'


//initial state
const state = {
    task_fieldList: [{
        value: 'appId',
        label: 'AppID',
    }, {
        value: 'deployId',
        label: 'DeployID'
    }],
    task_currentField: "appId",
    taskList: [],
    task_currentPage: 1,
    task_total: 10,
    task_pageSize: 20,


    taskInfo: {
//        id: this.$route.params.taskId,
//        deployId: 0,
//        appId: '',
//        status: 'RUNNING',
//        host: '',
//        port: 0,
//        slaveId: '',
//        instanceNo: 0,
//        scheduleTime: '',
//        modifyTime: ''
    },
    taskResource: {
        "slave": {"numCpus": 1, "mem": "1", "diskSpace": "1"},
        "allocate": {"numCpus": 1, "mem": "1", "diskSpace": "1"}
    },
    taskStatics: {
        "cpuUsed": 1,
        "memRssBytes": 1
    },
    taskFile: [
//        {"name":'test',"size":'120M',"lastModify":'2017-12-28'},
//        {"name":'test',"size":'120M',"lastModify":'2017-12-28'},
//        {"name":'test',"size":'120M',"lastModify":'2017-12-28'}
    ]
}

// getters
const getters = {
    getTaskFieldList: state => state.task_fieldList,
    getTaskList: state => state.taskList,
    getTaskTotal: state => state.task_total,
    getTaskPageSize: state => state.task_pageSize,
    getTaskInfo: state => state.taskInfo,
    getTaskResource: state => state.taskResource,
    getTaskFile: state => state.taskFile,
    getTaskStatics: state => state.taskStatics
}

// actions
const actions = {
    queryTask({commit}, payload) {
        api.taskService.query(parse(payload)).then(function (res) {
            commit(types.Load_Task, res);
        }.bind(this)).catch(function (err) {
            console.log(err);
        }.bind(this));
    },
    stopTask({commit, dispatch}, payload) {
        console.log(payload)
        api.taskService.stop(parse(payload)).then(function (res) {
            dispatch("displayPromptByResponseMsg", res);
            dispatch("queryTask", payload.query)
        }.bind(this)).catch(function (err) {
            console.log(err);
        }.bind(this));
    },
    //详情页面关闭
    stopTaskInfo({commit, dispatch}, payload) {
        console.log(payload)
        api.taskService.stop(parse(payload)).then(function (res) {
            dispatch("queryTaskInfo", payload)
        }.bind(this)).catch(function (err) {
            console.log(err);
        }.bind(this));
    },
    queryTaskInfo({commit}, payload) {
        console.log(payload)
        api.taskService.queryTaskInfo(parse(payload)).then(function (res) {
            //commit(types.Load_Task, res);
            commit("queryTaskInfo", res)
        }.bind(this)).catch(function (err) {
            console.log(err);
        }.bind(this));
    },
    queryTaskFile({commit}, payload) {
        console.log(payload)
        api.taskService.queryTaskFile(parse(payload)).then(function (res) {
            //commit(types.Load_Task, res);
            commit("queryTaskFile", res)
        }.bind(this)).catch(function (err) {
            console.log(err);
        }.bind(this));
    },
    queryTaskResource({commit}, payload) {
        console.log("queryTaskResource:" + payload);
        var self = this;
        api.taskService.queryTaskResource(parse(payload)).then(function (res) {
            commit("queryTaskResource", res)
        }.bind(self)).catch(function (err) {
            console.log(err);
        }.bind(self));
    },
    queryTaskStatistics({commit}, payload) {
        console.log("queryTaskStatistics:" + payload);
        var self = this;
        setInterval(function () {
            api.taskService.queryTaskStatistics(parse(payload)).then(function (res) {
                commit("queryTaskStatistics", res)
            }.bind(self)).catch(function (err) {
                console.log(err);
            }.bind(self));
        }, 1500);
    }
}


// mutations
const mutations = {
    [types.Load_Task](state, data) {
        console.log(data);
        state.taskList = data.data;
        state.task_pageSize = data.page.pageSize;
        state.task_total = data.page.total;
        state.task_currentPage = data.page.current;
    },
    ["queryTaskInfo"](state, data) {
        console.log(data);
        state.taskInfo = data.data;
    },
    ["queryTaskFile"](state, data) {
        console.log(data);
        state.taskFile = data.data;
    },
    ["queryTaskResource"](state, data) {
        console.log(data);
        //debugger;
        state.taskResource = data.data;
    },
    ["queryTaskStatistics"](state, data) {

        if (!state.taskStatics1) {
            state.taskStatics1 = data.data;
        }
        if (data.data.timestamp - state.taskStatics1.timestamp == 0) {
            data.data.cpuUsed = 0;
        }
        else {
            data.data.cpuUsed = ((data.data.cpusUserTimeSecs - state.taskStatics.cpusUserTimeSecs) / (data.data.timestamp - state.taskStatics1.timestamp)).toFixed(3);
        }
        state.taskStatics = data.data;
        state.taskStatics1 = data.data;
    }
}

export default {
    state,
    getters,
    actions,
    mutations
}



