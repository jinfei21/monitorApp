import request from '../request'


export default {

	query(params) {
	  return request({
	    url: '/api/slaves',
	    method: 'get',
	    data: params,
	  })
	},
	addSlave(params) {
		return request({
		    url: '/api/slave',
		    method: 'post',
		    data: params,
		})
	},
	deleteSlave(params) {
		return request({
		    url: '/api/slave/'+params.id+'/delete',
		    method: 'delete',
		})
	},
	upSlave(params) {
		return request({
		    url: '/api/slave/'+params.id+'/up',
		    method: 'put',
		    data: params,
		})
	},
	downSlave(params) {
		return request({
		    url: '/api/slave/'+params.id+'/down',
		    method: 'put',
		    data: params,
		})
	},

}

