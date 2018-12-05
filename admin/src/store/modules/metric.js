import * as types from '../mutation-types'
import { parse } from 'qs'
import { api } from '../../api'

const state = {
	metric_fieldList: [{
		value: 'sId',
		label: 'SystemId'
	}],
	metric_currentField: "sId",
	metricList: [],
	metric_currentPage: 1,
	metric_total: 10,
	metric_pageSize: 10
}

// getters
const getters = {
	getMetricFieldList: state => state.metric_fieldList,
	getMetricCurrentField: state => state.metric_currentField,
	getMetricList: state => state.metricList,
	getMetricCurrentPage: state => state.metric_currentPage,
	getMetricTotal: state => state.metric_total,
	getMetricPageSize: state => state.metric_pageSize
}

// actions
const actions = {
	queryMetric({ commit, dispatch }, payload) {
		api.metricService.query(parse(payload))
			.then(function (res) {
				commit(types.Load_Metric, res);
			}.bind(this))
			.catch(function (err) {
				//todo 所有get方法exception的时候，此处用err的处理？
				dispatch("displayPromptByResponseMsg", err);
			}.bind(this));
	},
	addMetric({ commit, dispatch }, payload) {
		api.metricService.addMetric(parse(payload.metric))
			.then(function (res) {
				dispatch("displayPromptByResponseMsg", res);
				if (res.success) {
					dispatch('queryMetric', payload.query)
				}
			}.bind(this))
			.catch(function (err) {
				dispatch("displayPromptByResponseMsg", err.response);
			}.bind(this)
			);
	},
	//UI未使用
	deleteMetric({ commit, dispatch }, payload) {
		api.metricService.deleteMetric(parse(payload))
			.then(function (res) {
				dispatch("displayPromptByResponseMsg", res);
				if (res.success) {
					dispatch('queryMetric', payload.query)
				}
			}.bind(this))
			.catch(function (err) {
				dispatch("displayPromptByResponseMsg", err.response);
			}.bind(this));
	},

}

// mutations
const mutations = {
	[types.Load_Metric](state, data) {
		console.log(data);
		state.metricList = data.data;
		state.metric_pageSize = (!data.data) ? data.data.length : 10;
		state.metric_total = (!data.data) ? data.data.length : 0;
		state.metric_currentPage = 1;
	}
}

export default {
	state,
	getters,
	actions,
	mutations
}
