import request from '../request'


export default {

    queryCleanup(params) {
	  return request({
	    url: '/api/cleanups',
	    method: 'get',
	    data: params,
	  })
	},
	addCleanup(params) {
		return request({
		    url: '/api/cleanup',
		    method: 'post',
		    data: params,
		})
	},
	deleteCleanup(params) {
		return request({
		    url: '/api/cleanup/'+params.id+'/delete',
		    method: 'delete',
		})
	},

}

