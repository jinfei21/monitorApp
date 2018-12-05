package com.ppdai.monitor.api.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.kairosdb.client.response.QueryResponse;

/**
 * @Author haijiang
 * @Description 监控系统信息请求类
 * @Date 2018/11/13
 **/
@Api(value = "MonitorSystemResDTO",description = "监控系统信息请求类")
public class MonitorSystemResDTO {

    @ApiModelProperty(value = "系统id")
    private Long id;

    @ApiModelProperty(value = "系统名称")
    private String name;

    @ApiModelProperty(value = "平台")
    private String depart;

    @ApiModelProperty(value = "负责人")
    private String owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
