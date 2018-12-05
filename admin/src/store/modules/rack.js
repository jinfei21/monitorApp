import * as types from '../mutation-types'
import {parse} from 'qs'
import {api} from '../../api'

const state = {
    rack_fieldList: [{
        value: 'rackId',
        label: 'RackID',
    }],
    rack_currentField: "rackId",
    rackList: [],
    rack_currentPage: 1,
    rack_total: 10,
    rack_pageSize: 10
}

// getters
const getters = {
    getRackFieldList: state => state.rack_fieldList,
    getRackCurrentField: state => state.rack_currentField,
    getRackList: state => state.rackList,
    getRackCurrentPage: state => state.rack_currentPage,
    getRackTotal: state => state.rack_total,
    getRackPageSize: state => state.rack_pageSize
}

// actions
const actions = {
    queryRack({commit, dispatch}, payload){
        api.rackService.queryRack(parse(payload))
            .then(function (res) {
                commit(types.Load_Rack, res);
            }.bind(this))
            .catch(function (err) {
                //todo 所有get方法exception的时候，此处用err的处理？
                dispatch("displayPromptByResponseMsg", err);
            }.bind(this));
    },
    queryOne({commit, dispatch}, payload){
        api.rackService.queryOne(parse(payload))
            .then(function (res) {
                commit(types.Load_Rack, res);
            }.bind(this))
            .catch(function (err) {
                //todo 所有get方法exception的时候，此处用err的处理？
                dispatch("displayPromptByResponseMsg", err);
            }.bind(this));
    },
    addRack({commit, dispatch}, payload){
        api.rackService.addRack(parse(payload))
            .then(function (res) {
                dispatch("displayPromptByResponseMsg", res);
                if (res.success) {
                    dispatch('queryRack', payload.query);
                }
            }.bind(this))
            .catch(function (err) {
                    dispatch("displayPromptByResponseMsg", err.response);
                }.bind(this)
            );
    },
    upRack({commit, dispatch}, payload){
        api.rackService.upRack(parse(payload))
            .then(function (res) {
                dispatch("displayPromptByResponseMsg", res);
                if (res.success) {
                    dispatch('queryRack', payload.query);
                }
            }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    downRack({commit, dispatch}, payload){
        api.rackService.downRack(parse(payload))
            .then(function (res) {
                dispatch("displayPromptByResponseMsg", res);
                if (res.success) {
                    dispatch('queryRack', payload.query);
                }
            }.bind(this)).catch(function (err) {
            dispatch("displayPromptByResponseMsg", err.response);
        }.bind(this));
    },
    deleteRack({commit, dispatch}, payload){
        api.rackService.deleteRack(parse(payload))
            .then(function (res) {
                dispatch("displayPromptByResponseMsg", res);
                if (res.success) {
                    dispatch('queryRack', payload.query);
                }
            }.bind(this))
            .catch(function (err) {
                dispatch("displayPromptByResponseMsg", err.response);
            }.bind(this));
    },

}

// mutations
const mutations = {
    [types.Load_Rack](state, data){
        console.log(data);
        state.rackList = data.data;
        state.rack_pageSize = data.page.pageSize;
        state.rack_total = data.page.total;
        state.rack_currentPage = data.page.current;
    }
}


export default {
    state,
    getters,
    actions,
    mutations
}
