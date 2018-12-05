package com.ppdai.monitor.api;

import com.ppdai.monitor.api.model.*;
import com.ppdai.monitor.common.ApiResult;
import com.ppdai.monitor.service.entity.BaseResult;
import io.swagger.annotations.Api;
import org.kairosdb.client.response.QueryResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author haijiang
 * @Description 监控api
 * @Date 2018/11/13
 **/
@Api(tags = {"monitor-service"}, description = "monitor-service服务")
@RestController
@RequestMapping("api/monitor")
public interface MonitorApi {

    /**
     * 查询所有系统信息
     *
     * @return MonitorSystemResDTO
     */
    @RequestMapping(value = "queryAllSystem", method = RequestMethod.GET)
    ApiResult<List<MonitorSystemResDTO>> queryAllSystem(Long sId);

    /**
     * 通过系统Id查询系统指标详细信息
     *
     * @param sId 系统Id
     * @return ApiResult<MonitorMetricResDTO>
     */
    @RequestMapping(value = "queryMetricDataBySId", method = RequestMethod.GET)
    ApiResult<List<MonitorMetricResDTO>> queryMetricDataBySId(Long sId);

    /**
     * 通过id查询系统指标详细信息
     *
     * @param req req
     * @return ApiResult<MonitorMetricResDTO>
     */
    @RequestMapping(value = "queryMetricDataById", method = RequestMethod.POST)
    ApiResult<BaseResult> queryMetricDataById(MetricDataDetailReqDTO req);

    /**
     * 新增系统信息
     *
     * @param req req
     * @return ApiResult<Boolean>
     */
    @RequestMapping(value = "addSystem", method = RequestMethod.POST)
    ApiResult<AddSystemResDTO> addSystem(AddSystemReqDTO req);

    /**
     * 删除系统信息
     *
     * @param id id
     * @return ApiResult<Boolean>
     */
    @RequestMapping(value = "delSystem", method = RequestMethod.DELETE)
    ApiResult<Boolean> delSystem(String id);

    /**
     * 新增系统指标详细信息
     *
     * @param req req
     * @return ApiResult<Boolean>
     */
    @RequestMapping(value = "addMetric", method = RequestMethod.POST)
    ApiResult<AddMetricResDTO> addMetric(AddMetricReqDTO req);

    /**
     * 删除系统指标详细信息
     *
     * @param id id
     * @return ApiResult<Boolean>
     */
    @RequestMapping(value = "delMetric", method = RequestMethod.DELETE)
    ApiResult<Boolean> delMetric(String id);

    /**
     * 测试
     *
     * @return ApiResult<QueryResponse>
     */
    @RequestMapping(value = "test", method = RequestMethod.GET)
    ApiResult<QueryResponse> queryDataPoints(String mertic);

    /**
     * 测试
     *
     * @return ApiResult<List<String>>
     */
    @RequestMapping(value = "metricNames", method = RequestMethod.GET)
    ApiResult<List<String>> queryDataPointsMetricNames();

}
