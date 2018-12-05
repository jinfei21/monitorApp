import * as types from '../mutation-types'
import {parse} from 'qs'
import {api} from '../../api'

const state = {
    subnet_fieldList: [{
        value: 'host',
        label: 'HOST',
    },{
        value:'subnet',
        label:'子网'
    }],
    subnetList:[],
    subnet_total:10,
}

// getters
const getters = {
    getSubnetList : state => state.subnetList,
    getSubnetFieldList : state => state.subnet_fieldList,
    getSubnetTotal : state => state.subnet_total,
}

// actions
const actions = {

    querySubnet({commit,dispatch},payload){
        api.ipamService.querySubnet(parse(payload))
            .then(
                function (res) {
                    commit(types.Load_Subnet,res);
                }.bind(this))
            .catch(
                function (err) {
                    //todo 所有get方法exception的时候，此处用err的处理？
                    dispatch("displayPromptByResponseMsg", err);
                }.bind(this));
    },

    addSubnet({commit,dispatch},payload){
        api.ipamService.createSubnet(parse(payload))
            .then(function(res){
                dispatch("displayPromptByResponseMsg", res);
                if(res.success){
                    dispatch('querySubnet',payload.query);
                }
            }.bind(this))
            .catch(function(err){
                    dispatch("displayPromptByResponseMsg", err.response);
                }.bind(this)
            );
    },

    deleteSubnet({commit,dispatch},payload){
        api.ipamService.deleteSubnet(parse(payload))
            .then(function (res) {
                dispatch("displayPromptByResponseMsg", res);
                if(res.success){
                    dispatch('querySubnet',payload.query);
                }
            }.bind(this))
            .catch(function (err) {
                dispatch("displayPromptByResponseMsg", err.response);
            }.bind(this));
    },

}

// mutations
const mutations = {

    [types.Load_Subnet](state,data){
        state.subnetList = data.data;
        state.subnet_pageSize = data.page.pageSize;
        state.subnet_total = data.page.total;
        state.subnet_currentPage =  data.page.current;
    },
}

export default {
    state,
    getters,
    actions,
    mutations
}