package com.ppdai.monitor.controller;

import com.ppdai.monitor.api.MonitorApi;
import com.ppdai.monitor.api.model.*;
import com.ppdai.monitor.common.ApiResult;
import com.ppdai.monitor.common.ErrorEnum;
import com.ppdai.monitor.common.FunConstant;
import com.ppdai.monitor.common.TimeScaleConstant;
import com.ppdai.monitor.service.IKairosDbService;
import com.ppdai.monitor.service.IMonitorMetricService;
import com.ppdai.monitor.service.IMonitorSystemService;
import com.ppdai.monitor.service.entity.BaseResult;
import com.ppdai.monitor.util.StringUtils;
import com.ppdai.monitor.util.VerifyUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.kairosdb.client.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Author haijiang
 * @Description 监控api实现类
 * @Date 2018/11/13
 **/
@RestController
public class MonitorController implements MonitorApi {

    @Autowired
    private IMonitorSystemService monitorSystemService;
    @Autowired
    private IMonitorMetricService monitorMetricService;
    @Autowired
    private IKairosDbService kairosDbService;


    @ApiOperation(value = "查询所有系统信息")
    @Override
    public ApiResult<List<MonitorSystemResDTO>> queryAllSystem(Long sId) {
        ApiResult<List<MonitorSystemResDTO>> apiResult = new ApiResult<>();
        apiResult.setData(monitorSystemService.queryMonitorSystemAll(sId));
        return apiResult;
    }

    @ApiOperation(value = "通过系统Id查询系统指标详细信息")
    @Override
    public ApiResult<List<MonitorMetricResDTO>> queryMetricDataBySId(Long sId) {
        ApiResult<List<MonitorMetricResDTO>> apiResult = new ApiResult<>();
        apiResult.setData(monitorMetricService.queryMonitorMetricBySId(sId));
        return apiResult;
    }

    @ApiOperation(value = "通过id查询系统指标详细信息")
    @Override
    public ApiResult<BaseResult> queryMetricDataById(@RequestBody MetricDataDetailReqDTO req) {
        ApiResult<BaseResult> apiResult = new ApiResult<>();
        if (StringUtils.isEmpty(req) || StringUtils.isEmpty(req.getmId())) {
            apiResult.setRet(ErrorEnum.PARAMETER_ERROR.getErrorCode())
                    .setMsg(ErrorEnum.PARAMETER_ERROR.getErrorMsg());
            return apiResult;
        }
        if (StringUtils.isNotEmpty(req.getTimeScale())){
            if (!VerifyUtils.isTimeScale(req.getTimeScale())) {
                apiResult.setRet(ErrorEnum.ERROR_TIME_SCALE.getErrorCode())
                        .setMsg(String.format(ErrorEnum.ERROR_TIME_SCALE.getErrorMsg(),TimeScaleConstant.timeScaleStr()));
                return apiResult;
            }
        }
        apiResult.setData(monitorMetricService.queryMonitorMetricById(req));
        return apiResult;
    }

    @ApiOperation(value = "新增系统监控信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "req", value = "增加系统监控请求类", dataType = "AddSystemReqDTO", paramType = "body", required = true)})
    @Override
    public ApiResult<AddSystemResDTO> addSystem(@RequestBody AddSystemReqDTO req) {
        ApiResult<AddSystemResDTO> apiResult = new ApiResult<>();
        if (StringUtils.isEmpty(req) || StringUtils.isEmpty(req.getDepart())
                || StringUtils.isEmpty(req.getName()) || StringUtils.isEmpty(req.getOwner())) {
            apiResult.setRet(ErrorEnum.PARAMETER_ERROR.getErrorCode())
                    .setMsg(ErrorEnum.PARAMETER_ERROR.getErrorMsg());
            return apiResult;
        }

        apiResult.setData(monitorSystemService.addMonitorSystem(req));
        return apiResult;
    }

    @ApiOperation(value = "删除系统监控信息")
    @Override
    public ApiResult<Boolean> delSystem(@RequestParam String id) {
        ApiResult<Boolean> apiResult = new ApiResult<>();
        if (StringUtils.isEmpty(id)) {
            apiResult.setRet(ErrorEnum.PARAMETER_ERROR.getErrorCode())
                    .setMsg(ErrorEnum.PARAMETER_ERROR.getErrorMsg());
            return apiResult;
        }
        apiResult.setData(monitorSystemService.delMonitorSystem(Long.valueOf(id)));
        return apiResult;
    }

    @ApiOperation(value = "新增指标详细信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "req", value = "增加指标请求类", dataType = "AddMetricReqDTO", paramType = "body", required = true)})
    @Override
    public ApiResult<AddMetricResDTO> addMetric(@RequestBody AddMetricReqDTO req) {
        ApiResult<AddMetricResDTO> apiResult = new ApiResult<>();
        if (StringUtils.isEmpty(req) || StringUtils.isEmpty(req.getChart())
                || StringUtils.isEmpty(req.getMetric()) || StringUtils.isEmpty(req.getFunc())
                || StringUtils.isEmpty(req.getTagName()) || StringUtils.isEmpty(req.getTitle())) {
            apiResult.setRet(ErrorEnum.PARAMETER_ERROR.getErrorCode())
                    .setMsg(ErrorEnum.PARAMETER_ERROR.getErrorMsg());
            return apiResult;
        }
        if (!FunConstant.FUN_FORMS.contains(req.getFunc())) {
            apiResult.setRet(ErrorEnum.ERROR_FUNC.getErrorCode())
                    .setMsg(String.format(ErrorEnum.ERROR_FUNC.getErrorMsg(),
                            Arrays.asList(FunConstant.FUN_FORMS).toString().replaceAll("\\[|\\]", "").substring(2)));
            return apiResult;
        }
        apiResult.setData(monitorMetricService.addMonitorMetric(req));
        return apiResult;
    }

    @ApiOperation(value = "删除指标详细信息")
    @Override
    public ApiResult<Boolean> delMetric(@RequestParam String id) {
        ApiResult<Boolean> apiResult = new ApiResult<>();
        apiResult.setData(monitorMetricService.delMonitorMetric(Long.valueOf(id)));
        return apiResult;
    }

    @ApiOperation(value = "测试")
    @Override
    public ApiResult<QueryResponse> queryDataPoints(@RequestParam String metric) {
        ApiResult<QueryResponse> apiResult = new ApiResult<>();
        apiResult.setData(kairosDbService.queryDataPointsTest(metric));
        return apiResult;
    }

    @ApiOperation(value = "查询系统中所有Metric的名称")
    @Override
    public ApiResult<List<String>> queryDataPointsMetricNames() {
        ApiResult<List<String>> apiResult = new ApiResult<>();
        apiResult.setData(kairosDbService.queryDataPointsMetricNames());
        return apiResult;
    }

}
