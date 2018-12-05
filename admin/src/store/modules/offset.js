import * as types from '../mutation-types'
import {parse} from 'qs'
import {api} from '../../api'

const state = {
    offsetList:[],
    offset_total:10,
}

// getters
const getters = {
    getOffsetList : state => state.offsetList,
    getOffsetTotal : state => state.offset_total,
}

// actions
const actions = {

    listOffset({commit,dispatch},payload){
        api.queueService.listOffset()
            .then(
                function (res) {
                    commit(types.Load_Offset,res);
                }.bind(this))
            .catch(
                function (err) {
                    //todo 所有get方法exception的时候，此处用err的处理？
                    dispatch("displayPromptByResponseMsg", err);
                }.bind(this));
    },

    resetOffset({commit,dispatch},payload){
        api.queueService.resetOffset(parse(payload))
            .then(function(res){
                dispatch("displayPromptByResponseMsg", res);
                if(res.success){
                    dispatch('listOffset',payload.query);
                }
            }.bind(this))
            .catch(function(err){
                    dispatch("displayPromptByResponseMsg", err.response);
                }.bind(this)
            );
    },

    deleteOffset({commit,dispatch},payload){
        api.queueService.deleteOffset(parse(payload))
            .then(function (res) {
                dispatch("displayPromptByResponseMsg", res);
                if(res.success){
                    dispatch('listOffset',payload.query);
                }
            }.bind(this))
            .catch(function (err) {
                dispatch("displayPromptByResponseMsg", err.response);
            }.bind(this));
    },



}

// mutations
const mutations = {
    [types.Load_Offset](state,data){
        state.offsetList = data.data;
        state.offset_total = 10;

    },
}

export default {
    state,
    getters,
    actions,
    mutations
}

