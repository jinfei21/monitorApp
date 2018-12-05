package com.ppdai.monitor.api.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author haijiang
 * @Description 增加系统请求类
 * @Date 2018/11/13
 **/
@Api(value = "AddSystemReqDTO",description = "增加系统请求类")
public class AddSystemReqDTO {

    @ApiModelProperty(value = "系统名称，例如：订单系统等")
    private String name;

    @ApiModelProperty(value = "平台")
    private String depart;

    @ApiModelProperty(value = "负责人")
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }
}
