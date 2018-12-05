package com.ppdai.monitor.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author haijiang
 * @Description 最外层返回实体
 * @Date 2018/09/18
 **/
@ApiModel(value = "最外层返回实体")
public class ApiResult<T> {

    /**
     * ret = 0: 正确返回 ret > 0: 调用API时发生错误，需要开发者进行相应的处理. -50 <= ret <= -1 接口调用不能通过接口代理机校验，需要开发者进行相应的处理
     * ret <-50: 系统内部错误，请联系技术支持，调查问题原因并获得解决方案。 1000< ret <1100 调用三方接口时未能正确返回，通常msg为三方返回的错误提示信息 具体参考
     * http://confluence.ppdai.com/pages/viewpage.action?pageId=10071880
     */
    @ApiModelProperty(value = "返回code，具体参考 http://confluence.ppdai.com/pages/viewpage.action?pageId=23172847", example = "0")
    public Integer ret;

    /**
     * 如果错误，返回错误信息。 正确时为success（小写）
     */
    @ApiModelProperty(value = "返回错误信息。 正确时为success（小写）", example = "success")
    public String msg;

    /**
     * 请求序列号
     */
    @ApiModelProperty(value = "请求序列号")
    public String serialNo;
    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据,无需返回数据或者根据条件未查询到数据时此字段不返回，否则为对应的数据", example = "")
    public T data;

    public Integer getRet() {
        return ret;
    }

    public ApiResult setRet(Integer ret) {
        this.ret = ret;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ApiResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public ApiResult(Integer ret, String msg) {
        this.ret = ret;
        this.msg = msg;
    }


    public ApiResult() {
        this(0, "success");
    }

    public T getData() {
        return data;
    }

    public ApiResult setData(T data) {
        this.data = data;
        return this;
    }


}

