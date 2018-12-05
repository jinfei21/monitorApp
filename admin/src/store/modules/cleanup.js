import * as types from '../mutation-types'
import { parse } from 'qs'
import {api} from '../../api'

const state = {
	cleanup_fieldList :  [{
        value: 'appId',
        label: 'AppID'
      }, {
        value: 'deployId',
        label: 'DeployID'
      }],
	cleanup_currentField : "appId",
	cleanupList:[],
	cleanup_currentPage:1,
	cleanup_total:10,
	cleanup_pageSize:10
}

// getters
const getters = {
	getCleanupFieldList : state => state.cleanup_fieldList,
	getCleanupCurrentField : state => state.cleanup_currentField,
	getCleanupList : state => state.cleanupList,
	getCleanupCurrentPage : state => state.cleanup_currentPage,
	getCleanupTotal : state => state.cleanup_total,
	getCleanupPageSize : state => state.cleanup_pageSize
}

// actions
const actions = {
	queryCleanup({commit,dispatch},payload){
	   api.cleanupService.queryCleanup(parse(payload))
	        .then(function (res) {
		            commit(types.Load_Cleanup,res);
                  }.bind(this))
            .catch(function (err) {
            //todo 所有get方法exception的时候，此处用err的处理？
                  dispatch("displayPromptByResponseMsg", err);
            }.bind(this));
	},
	addCleanup({commit,dispatch},payload){
		api.cleanupService.addCleanup(parse(payload))
		    .then(function (res) {
		            dispatch("displayPromptByResponseMsg", res);
		            if(res.success){
		                dispatch('queryCleanup',payload.query);
		            }
			      }.bind(this))
		    .catch(function (err) {
		        dispatch("displayPromptByResponseMsg", err.response);
	              }.bind(this));
	},
	deleteCleanup({commit,dispatch},payload){
		api.cleanupService.deleteCleanup(parse(payload))
		    .then(function (res) {
		            dispatch("displayPromptByResponseMsg", res);
		            if(res.success){
		                dispatch('queryCleanup',payload.query);
		            }
	              }.bind(this))
	        .catch(function (err) {
		        dispatch("displayPromptByResponseMsg", err.response);
	              }.bind(this));
	},

}

// mutations
const mutations = {
	[types.Load_Cleanup](state,data){
		console.log(data);
		state.cleanupList = data.data;
		state.cleanup_pageSize = data.page.pageSize;
		state.cleanup_total = data.page.total;
		state.cleanup_currentPage = data.page.current;
	}
}


export default {
    state,
    getters,
    actions,
    mutations
}
