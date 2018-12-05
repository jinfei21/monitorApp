import request from '../request'

export default {
    query(params) {
        return request({
            url: '/api/tasks',
            method: 'get',
            data: params,
        })
    },
    stop(params){
        return request({
            url:'/api/task/'+params.taskId+'/stop',
            method:'PUT',
        })
    },
    queryTaskInfo(params) {
        return request({
            url: '/api/task/'+(params.taskId)+'/info',
            method: 'get'
        })
    },
    queryTaskFile(params) {
        return request({
            url: '/api/sandbox/'+(params.taskId)+'/browse?path=/',
            method: 'get'
        })
    },
    queryTaskResource(params) {
        return request({
            url: '/api/task/'+(params.taskId)+'/slaveInfo',
            method: 'get'
        })
    },
    queryTaskStatistics(params) {
        return request({
            url: '/api/task/'+(params.taskId)+'/statistics',
            method: 'get'
        })
    },
}
