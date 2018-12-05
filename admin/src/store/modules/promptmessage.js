import * as types from '../mutation-types';

// initial state
const state = {
    promptMessage: {
        success: null,
        message: null
    },
};

// getters
const getters = {
    getPromptMessage: state => state.promptMessage,
};

// actions
const actions = {

    /**
     * 根据后端服务返回的响应消息，更新提示消息，使得在UI上显示
     * @param commit     store state更新提交者
     * @param response  后端服务返回的响应消息
     */
    displayPromptByResponseMsg({commit}, response){
        if (response.status == 200 && response.success) {
            commit(types.REFRESH_PROMPT_MESSAGE,
                {success: response.success, message: "成功."});
        } else {
            let errorMsg = "请求失败";
            if (response.status == 200) {
                errorMsg += ",返回消息：" + response.message;
            } else {
                //todo 可以根据400 或者 500 做更细分的提示
                //todo 此处要结合controller中返回result的内容做统一、做定制
                errorMsg += ",返回码：" + response.status + ",返回消息：" + response.message;
            }
            commit(types.REFRESH_PROMPT_MESSAGE,
                {success: response.success, message: errorMsg});
        }

    },

    /**
     * 清空提示消息
     * @param commit     store state更新提交者
     */
    clearPrompt({commit}){
        commit(types.REFRESH_PROMPT_MESSAGE, {success: null, message: null});
    },
};

// mutations
const mutations = {
    [types.REFRESH_PROMPT_MESSAGE] (state, data){
        state.promptMessage = data;
    },
};

export default {
    state,
    getters,
    actions,
    mutations
}