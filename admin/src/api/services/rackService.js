import request from '../request'


export default {

    queryRack(params) {
	  return request({
	    url: '/api/racks',
	    method: 'get',
	    data: params,
	  })
	},
	queryOne(params) {
	  return request({
	    url: '/api/rack/'+params.rackId,
	    method: 'get',
	    data: params,
	  })
	},
	addRack(params) {
		return request({
		    url: '/api/rack',
		    method: 'post',
		    data: params,
		})
	},
	upRack(params) {
		return request({
		    url: '/api/rack/'+params.id+'/up',
		    method: 'put',
		    data: params,
		})
	},
	downRack(params) {
		return request({
		    url: '/api/rack/'+params.id+'/down',
		    method: 'put',
		    data: params,
		})
	},
	deleteRack(params) {
		return request({
		    url: '/api/rack/'+params.id+'/delete',
		    method: 'delete',
		})
	},

}

