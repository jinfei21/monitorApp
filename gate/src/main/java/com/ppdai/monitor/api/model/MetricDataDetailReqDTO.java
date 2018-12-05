package com.ppdai.monitor.api.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author haijiang
 * @Description 指标详细信息请求类
 * @Date 2018/11/27
 **/
@Api(value = "MetricDataDetailReqDTO",description = "指标详细信息请求类")
public class MetricDataDetailReqDTO {

    @ApiModelProperty(value = "指标id")
    private Long mId;

    @ApiModelProperty(value = "周期，传入days-10，months-1等等，days-10：最近前10天，months-1：最近前1个月等，输入错误后接口会有提示）")
    private String timeScale;

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(String timeScale) {
        this.timeScale = timeScale;
    }
}
