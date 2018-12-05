import * as types from '../mutation-types'
import {parse} from 'qs'
import {api} from '../../api'

const state = {

    ip_fieldList: [{
        value: 'host',
        label: 'HOST',
    },{
        value:'ip',
        label:'IP'
    },{
        value:'status',
        label:'状态'
    }],
    ipList:[],
    ip_total:10,
}

// getters
const getters = {
    getIPList : state => state.ipList,
    getIPFieldList : state => state.ip_fieldList,
    getIPTotal : state => state.ip_total,
}

// actions
const actions = {

    queryIP({commit,dispatch},payload){
        api.ipamService.queryIPS(parse(payload))
            .then(
                function (res) {
                    commit(types.Load_IP,res);
                }.bind(this))
            .catch(
                function (err) {
                    //todo 所有get方法exception的时候，此处用err的处理？
                    dispatch("displayPromptByResponseMsg", err);
                }.bind(this));
    },

    addIP({commit,dispatch},payload){
        api.ipamService.createIP(parse(payload))
            .then(function(res){
                dispatch("displayPromptByResponseMsg", res);
                if(res.success){
                    dispatch('queryIP',payload.query);
                }
            }.bind(this))
            .catch(function(err){
                    dispatch("displayPromptByResponseMsg", err.response);
                }.bind(this)
            );
    },

    deleteIP({commit,dispatch},payload){
        api.ipamService.deleteIP(parse(payload))
            .then(function (res) {
                dispatch("displayPromptByResponseMsg", res);
                if(res.success){
                    dispatch('queryIP',payload.query);
                }
            }.bind(this))
            .catch(function (err) {
                dispatch("displayPromptByResponseMsg", err.response);
            }.bind(this));
    },

    disableIP({commit,dispatch},payload){
        api.ipamService.disableIP(parse(payload))
            .then(function (res) {
                dispatch("displayPromptByResponseMsg", res);
                if(res.success){
                    dispatch('queryIP',payload.query);
                }
            }.bind(this))
            .catch(function (err) {
                dispatch("displayPromptByResponseMsg", err.response);
            }.bind(this));
    },

    enableIP({commit,dispatch},payload){
        api.ipamService.enableIP(parse(payload))
            .then(function (res) {
                dispatch("displayPromptByResponseMsg", res);
                if(res.success){
                    dispatch('queryIP',payload.query);
                }
            }.bind(this))
            .catch(function (err) {
                dispatch("displayPromptByResponseMsg", err.response);
            }.bind(this));
    },

}

// mutations
const mutations = {
    [types.Load_IP](state,data){
        state.ipList = data.data;
        state.ip_total = data.page.total;
    },
}

export default {
    state,
    getters,
    actions,
    mutations
}

