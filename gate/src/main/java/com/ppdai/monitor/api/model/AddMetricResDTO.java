package com.ppdai.monitor.api.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author haijiang
 * @Description 新增指标响应类
 * @Date 2018/11/15
 **/
@Api(value = "AddMetricResDTO",description = "新增指标响应类")
public class AddMetricResDTO {
    @ApiModelProperty(value = "新增指标状态")
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
