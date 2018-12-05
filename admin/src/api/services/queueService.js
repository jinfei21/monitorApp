import request from '../request'

export default {

    queryEvent(params) {
        return request({
            url: '/api/queue/listevent',
            method: 'get',
            data: params,
        })
    },

    deleteEvent(params) {
        return request({
            url: '/api/queue/'+params.id+'/deleteEvent',
            method: 'delete',
        })
    },

    listOffset(params) {
        return request({
            url: '/api/queue/listoffset',
            method: 'get',
        })
    },

    resetOffset(params) {
        return request({
            url: '/api/queue/resetoffset',
            method: 'PUT',
            data: params,
        })
    },

    deleteOffset(params) {
        return request({
            url: '/api/queue/'+params.id+'/deleteOffset',
            method: 'delete',
        })
    },


}