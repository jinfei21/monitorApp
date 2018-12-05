import request from '../request'


export default {

	query(params) {
		console.log("11111111111111111111111");
		console.log(params);
	  return request({
	    url: '/api/monitor/queryMetricDataBySId',
	    method: 'get',
	    data: params,
	  })
	},
	addMetric(params) {
		return request({
		    url: '/api/monitor/addMetric',
		    method: 'post',
		    data: params,
		})
	},
	deleteMetric(params) {
		return request({
		    url: '/api/monitor/delMetric?id='+params.id,
				method: 'delete',
		})
	},

}

