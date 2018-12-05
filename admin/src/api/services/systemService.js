import request from '../request'


export default {

    query(params) {
        return request({
            url: '/api/monitor/queryAllSystem',
            method: 'get',
            data: params,
        })
    },
	addSystem(params) {
        return request({
            url: '/api/monitor/addSystem',
            method: 'post',
            data: params,
        })
    },
    deleteSystem(params) {
        return request({
            url: '/api/monitor/delSystem?id='+params.id,
            method: 'delete',
            data: params,
        })
    }
}

