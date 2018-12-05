import * as types from '../mutation-types'
import { parse } from 'qs'
import {api} from '../../api'

const state = {
    deploy_fieldList :  [{
        value: 'appId',
        label: '应用ID'
    }, {
        value: 'tag',
        label: '标签'
    }, {
        value: 'appType',
        label: '类型'
    }, {
        value: 'state',
        label: '状态'
    }, {
        value: 'instances',
        label: '实例数量'
    }, {
        value: 'appName',
        label: '应用名称'
    }, {
        value: 'id',
        label: 'Deploy编号'
    }],
    deploy_appTypeList :[{
        value: 'service',
        label: 'service'
    },{
        value: 'worker',
        label: 'worker'
    },{
        value: 'job',
        label: 'job'
    },{
        value: 'oneoff',
        label: 'oneoff'
    }],
    deploy_packageTypeList:[{
        value: 'image',
        label: 'image'
    },{
        value: 'tar',
        label: 'tar'
    },{
        value: 'zip',
        label: 'zip'
    },{
        value: 'jar',
        label: 'jar'
    },{
        value: 'gz',
        label: 'gz'
    }],
    deployList:[],
    deploy_total:10,
}

// getters
const getters = {
    getPackageTypeList:state => state.deploy_packageTypeList,
    getAppTypeList : state => state.deploy_appTypeList,
	getDeployFieldList : state => state.deploy_fieldList,
	getDeployList : state => state.deployList,
	getDeployTotal : state => state.deploy_total,
}

// actions
const actions = {

	queryDeploy({commit,dispatch},payload){
	   api.deployService.queryDeploy(parse(payload))
	        .then(
	            function (res) {
		            commit(types.Load_Deploy,res);
                }.bind(this))
            .catch(
                function (err) {
                //todo 所有get方法exception的时候，此处用err的处理？
                  dispatch("displayPromptByResponseMsg", err);
                }.bind(this));
	},
	createDeploy({commit,dispatch},payload){
		api.deployService.createDeploy(parse(payload))
            .then(function(res){
		            dispatch("displayPromptByResponseMsg", res);
		            if(res.success){
		                dispatch('queryDeploy',payload.query);
		            }
		        }.bind(this))
		    .catch(function(err){
		        dispatch("displayPromptByResponseMsg", err.response);
		        }.bind(this)
		    );
	},
	activeDeploy({commit,dispatch},payload){
        api.deployService.activeDeploy(parse(payload))
            .then(function(res){
		            dispatch("displayPromptByResponseMsg", res);
		            if(res.success){
		                dispatch('queryDeploy',payload.query);
		            }
		        }.bind(this))
		    .catch(function(err){
		        dispatch("displayPromptByResponseMsg", err.response);
		        }.bind(this)
		    );
	},
	pauseDeploy({commit,dispatch},payload){
    	api.deployService.pauseDeploy(parse(payload))
            .then(function(res){
		            dispatch("displayPromptByResponseMsg", res);
		            if(res.success){
		                dispatch('queryDeploy',payload.query);
		            }
		        }.bind(this))
		    .catch(function(err){
		        dispatch("displayPromptByResponseMsg", err.response);
		        }.bind(this)
		    );
    },
    resizeDeploy({commit,dispatch},payload){
        api.deployService.resizeDeploy(parse(payload))
            .then(function(res){
		            dispatch("displayPromptByResponseMsg", res);
		            if(res.success){
		                dispatch('queryDeploy',payload.query);
		            }
		        }.bind(this))
		    .catch(function(err){
		        dispatch("displayPromptByResponseMsg", err.response);
		        }.bind(this)
		    );
    },
    stopDeploy({commit,dispatch},payload){
        api.deployService.stopDeploy(parse(payload))
            .then(function(res){
		            dispatch("displayPromptByResponseMsg", res);
		            if(res.success){
		                dispatch('queryDeploy',payload.query);
		            }
		        }.bind(this))
		    .catch(function(err){
		        dispatch("displayPromptByResponseMsg", err.response);
		        }.bind(this)
		    );
    },

}

// mutations
const mutations = {
	[types.Load_Deploy](state,data){
		state.deployList = data.data;
		state.deploy_pageSize = data.page.pageSize;
		state.deploy_total = data.page.total;
		state.deploy_currentPage =  data.page.current;
	}
}


export default {
    state,
    getters,
    actions,
    mutations
}
