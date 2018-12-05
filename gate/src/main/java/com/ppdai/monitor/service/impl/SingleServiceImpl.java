package com.ppdai.monitor.service.impl;

import com.google.gson.internal.LazilyParsedNumber;
import com.ppdai.monitor.service.IGraphicsService;
import com.ppdai.monitor.service.entity.BaseResult;
import com.ppdai.monitor.service.entity.SingleBaseResult;
import org.kairosdb.client.builder.DataPoint;
import org.kairosdb.client.response.Query;
import org.kairosdb.client.response.QueryResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Author haijiang
 * @Description
 * @Date 2018/11/28
 **/
@Service("single")
public class SingleServiceImpl implements IGraphicsService {
    @Override
    public BaseResult process(QueryResponse queryResponse) {
        try {
            SingleBaseResult singleResult = new SingleBaseResult();
            List<Query> queries = queryResponse.getQueries();
            Query query = queries.get(0);
            List<org.kairosdb.client.response.Result> results = query.getResults();
            org.kairosdb.client.response.Result resultData = results.get(0);

            //设置详细信息
            List<DataPoint> dataPoints = resultData.getDataPoints();
            LazilyParsedNumber value = (LazilyParsedNumber) dataPoints.get(0).getValue();
            singleResult.setData(value.longValue());
            return singleResult;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
