import * as types from '../mutation-types'
import {parse} from 'qs'
import {api} from '../../api'

const state = {

    event_fieldList: [{
        value: 'deployId',
        label: 'DeployID',
    },{
        value:'type',
        label:'类型'
    }],
    eventList:[],
    event_total:10,
}

// getters
const getters = {
    getEventList : state => state.eventList,
    getEventFieldList : state => state.event_fieldList,
    getEventTotal : state => state.event_total,
}

// actions
const actions = {

    queryEvent({commit,dispatch},payload){
        api.queueService.queryEvent(parse(payload))
            .then(
                function (res) {
                    commit(types.Load_Event,res);
                }.bind(this))
            .catch(
                function (err) {
                    //todo 所有get方法exception的时候，此处用err的处理？
                    dispatch("displayPromptByResponseMsg", err);
                }.bind(this));
    },

    deleteEvent({commit,dispatch},payload){
        api.queueService.deleteEvent(parse(payload))
            .then(function (res) {
                dispatch("displayPromptByResponseMsg", res);
                if(res.success){
                    dispatch('queryEvent',payload.query);
                }
            }.bind(this))
            .catch(function (err) {
                dispatch("displayPromptByResponseMsg", err.response);
            }.bind(this));
    },
}

// mutations
const mutations = {
    [types.Load_Event](state,data){
        state.eventList = data.data;
        state.event_total = data.page.total;
    },
}

export default {
    state,
    getters,
    actions,
    mutations
}

