import request from '../request'

export default {

    querySubnet(params) {
        return request({
            url: '/api/ipam/subnets',
            method: 'get',
            data: params,
        })
    },

    queryIPS(params) {
        return request({
            url: '/api/ipam/ips',
            method: 'get',
            data: params,
        })
    },

    createSubnet(params) {
        return request({
            url: '/api/ipam/addsubnet',
            method: 'post',
            data: params,
        })
    },

    deleteSubnet(params) {
        return request({
            url: '/api/ipam/'+params.id+'/deletesubnet',
            method: 'delete',
        })
    },

    createIP(params) {
        return request({
            url: '/api/ipam/addip',
            method: 'post',
            data: params,
        })
    },

    deleteIP(params) {
        return request({
            url: '/api/ipam/'+params.id+'/deleteip',
            method: 'delete',
        })
    },

    disableIP(params) {
        return request({
            url: '/api/ipam/'+params.ip+'/pauseip',
            method: 'PUT',
        })
    },

    enableIP(params) {
        return request({
            url: '/api/ipam/'+params.ip+'/active',
            method: 'PUT',
        })
    },

}