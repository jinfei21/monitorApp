import request from '../request'


export default {

    queryDeploy(params) {
	  return request({
	    url: '/api/deploys',
	    method: 'get',
	    data: params,
	  })
	},
	queryDeployInfo(params) {
	  return request({
	    url: '/api/deploy/'+params.deployId+'/info',
	    method: 'get',
	    data: params,
	  })
	},
    createDeploy(params) {
		return request({
		    url: '/api/deploy',
		    method: 'post',
		    data: params,
		})
	},
	pauseDeploy(params) {
		return request({
		    url: '/api/deploy/'+params.deployId+'/pause',
		    method: 'PUT',
		})
	},
	activeDeploy(params) {
		return request({
		    url: '/api/deploy/'+params.deployId+'/active',
		    method: 'PUT',
		    data: params,
		})
	},
	stopDeploy(params) {
		return request({
		    url: '/api/deploy/'+params.deployId+'/stop',
		    method: 'PUT',
		})
	},
    resizeDeploy(params) {
		return request({
		    url: '/api/deploy/resize',
		    method: 'post',
		    data: params,
		})
	},



}

