import request from '../request'


export default {

    queryMesos(params) {
        return request({
            url: '/api/mesos/statistics',
            method: 'get',
            data: params,
        })
    },
}

