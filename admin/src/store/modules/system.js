import * as types from '../mutation-types'
import { parse } from 'qs'
import { api } from '../../api'

const state = {
	system_fieldList: [{
		value: 'id',
		label: '系统名称'
	}],
	system_currentField: "id",
	systemList: [],
	system_currentPage: 1,
	system_total: 10,
	system_pageSize: 10
}

// getters
const getters = {
	getSystemFieldList: state => state.system_fieldList,
	getSystemCurrentField: state => state.system_currentField,
	getSystemList: state => state.systemList,
	getSystemCurrentPage: state => state.system_currentPage,
	getSystemTotal: state => state.system_total,
	getSystemPageSize: state => state.system_pageSize
}

// actions
const actions = {
	querySystem({ commit, dispatch }, payload) {
        api.systemService.query(parse(payload))
            .then(function (res) {
                commit(types.Load_System, res);
            }.bind(this))
            .catch(function (err) {
                //todo 所有get方法exception的时候，此处用err的处理？
                dispatch("displayPromptByResponseMsg", err);
            }.bind(this));
    },
	addSystem({ commit, dispatch }, payload) {
		api.systemService.addSystem(parse(payload.system))
			.then(function (res) {
				dispatch("displayPromptByResponseMsg", res);
				if (res.success) {
					dispatch('querySystem', payload.query)
				}
			}.bind(this))
			.catch(function (err) {
				dispatch("displayPromptByResponseMsg", err.response);
			}.bind(this)
			);
	},
	//UI未使用
	deleteSystem({ commit, dispatch }, payload) {
		api.systemService.deleteSystem(parse(payload))
			.then(function (res) {
				dispatch("displayPromptByResponseMsg", res);
				if (res.success) {
					dispatch('querySystem', payload.query)
				}
			}.bind(this))
			.catch(function (err) {
				dispatch("displayPromptByResponseMsg", err.response);
			}.bind(this));
	},

}

// mutations
const mutations = {
	[types.Load_System](state, data) {
		console.log(data);
		state.systemList = data.data;
		state.system_pageSize = (!data.data) ? data.data.length : 10;
		state.system_total = (!data.data) ? data.data.length : 0;
		state.system_currentPage = 1;
	}
}

export default {
	state,
	getters,
	actions,
	mutations
}
