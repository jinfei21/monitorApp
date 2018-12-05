package com.ppdai.monitor.service;

import com.ppdai.monitor.service.entity.BaseResult;
import org.kairosdb.client.response.QueryResponse;

import java.util.List;

/**
 * @Author haijiang
 * @Description 图形service
 * @Date 2018/11/27
 **/
public interface IGraphicsService {

    BaseResult process(QueryResponse queryResponse);
}
