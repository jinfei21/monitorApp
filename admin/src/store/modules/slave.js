import * as types from '../mutation-types'
import { parse } from 'qs'
import {api} from '../../api'

const state = {
	slave_fieldList :  [{
        value: 'slaveId',
        label: 'SlaveID'
      }, {
        value: 'rackId',
        label: 'RackID'
      }],
	slave_currentField : "slaveId",
	slaveList:[],
	slave_currentPage:1,
	slave_total:10,
	slave_pageSize:10
}

// getters
const getters = {
	getSlaveFieldList : state => state.slave_fieldList,
	getSlaveCurrentField : state => state.slave_currentField,
	getSlaveList : state => state.slaveList,
	getSlaveCurrentPage : state => state.slave_currentPage,
	getSlaveTotal : state => state.slave_total,
	getSlavePageSize : state => state.slave_pageSize
}

// actions
const actions = {
	querySlave({commit,dispatch},payload){
	   api.slaveService.query(parse(payload))
	        .then(function (res) {
		            commit(types.Load_Slave,res);
                  }.bind(this))
            .catch(function (err) {
            //todo 所有get方法exception的时候，此处用err的处理？
                  dispatch("displayPromptByResponseMsg", err);
            }.bind(this));
	},
	addSlave({commit,dispatch},payload){
		api.slaveService.addSlave(parse(payload))
		    .then(function(res){
		            dispatch("displayPromptByResponseMsg", res);
		            if(res.success){
		                dispatch('querySlave',payload.query);
		            }
		        }.bind(this))
		    .catch(function(err){
		        dispatch("displayPromptByResponseMsg", err.response);
		        }.bind(this)
		    );
	},
	upSlave({commit,dispatch},payload){
		api.slaveService.upSlave(parse(payload))
		    .then(function (res) {
		            dispatch("displayPromptByResponseMsg", res);
			        if(res.success){
                        dispatch('querySlave',payload.query)
                    }
	              }.bind(this))
	        .catch(function (err) {
	                dispatch("displayPromptByResponseMsg", err.response);
	              }.bind(this));
	},
	downSlave({commit,dispatch},payload){
		api.slaveService.downSlave(parse(payload))
		    .then(function (res) {
		            dispatch("displayPromptByResponseMsg", res);
                    if(res.success){
		                dispatch('querySlave',payload.query)
		            }
	              }.bind(this))
	        .catch(function (err) {
	                dispatch("displayPromptByResponseMsg", err.response);
	              }.bind(this));
	},
	//UI未使用
	deleteSlave({commit,dispatch},payload){
		api.slaveService.deleteSlave(parse(payload))
		    .then(function (res) {
		            dispatch("displayPromptByResponseMsg", res);
		            if(res.success){
		                dispatch('querySlave',payload.query)
		            }
	              }.bind(this))
	        .catch(function (err) {
		            dispatch("displayPromptByResponseMsg", err.response);
	              }.bind(this));
	},

}

// mutations
const mutations = {
	[types.Load_Slave](state,data){
		console.log(data);
		state.slaveList = data.data;
		state.slave_pageSize = data.page.pageSize;
		state.slave_total = data.page.total;
		state.slave_currentPage = data.page.current;
	}
}

export default {
    state,
    getters,
    actions,
    mutations
}
