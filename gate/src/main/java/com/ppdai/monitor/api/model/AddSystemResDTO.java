package com.ppdai.monitor.api.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author haijiang
 * @Description 新增系统监控响应类
 * @Date 2018/11/15
 **/
@Api(value = "AddSystemResDTO",description = "新增系统监控响应类")
public class AddSystemResDTO {

    @ApiModelProperty(value = "新增系统监控状态")
    private Boolean status;

    @ApiModelProperty(value = "系统监控id")
    private Long sId;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }
}
