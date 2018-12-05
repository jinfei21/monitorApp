package com.ppdai.monitor.service.impl;

import com.google.gson.internal.LazilyParsedNumber;
import com.ppdai.monitor.common.CommonConstants;
import com.ppdai.monitor.service.IGraphicsService;
import com.ppdai.monitor.service.entity.*;
import com.ppdai.monitor.util.StringUtils;
import org.kairosdb.client.builder.DataPoint;
import org.kairosdb.client.response.Query;
import org.kairosdb.client.response.QueryResponse;
import org.kairosdb.client.response.Result;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author haijiang
 * @Description
 * @Date 2018/11/27
 **/
@Service("pie")
public class PieServiceImpl implements IGraphicsService {
    @Override
    public BaseResult process(QueryResponse queryResponse) {
        try {
            PieBaseResult pieResult = new PieBaseResult();
            List<String> names = new ArrayList<>();
            List<PieDataResult> data = new ArrayList<>();
                List<Query> queries = queryResponse.getQueries();
                Query query = queries.get(0);
                List<org.kairosdb.client.response.Result> results = query.getResults();
            for (Result resultData : results) {
                //设置标签
                List<String> listHost = resultData.getTags().get(CommonConstants.TAG_HOST);
                List<String> listInstanceIp = resultData.getTags().get(CommonConstants.TAG_INSTANCE_IP);
                //设置详细信息
                PieDataResult pieDataResult = new PieDataResult();
                if (StringUtils.isNotEmpty(listHost)){
                    names.add(listHost.get(0));
                    pieDataResult.setName(listHost.get(0));
                }
                if (StringUtils.isNotEmpty(listInstanceIp)){
                    names.add(listInstanceIp.get(0));
                    pieDataResult.setName(listInstanceIp.get(0));
                }

                long sum = 0;
                List<DataPoint> dataPoints = resultData.getDataPoints();
                for (DataPoint dataPoint : dataPoints) {
                    LazilyParsedNumber value = (LazilyParsedNumber) dataPoint.getValue();
                    sum += value.longValue();
                }
                pieDataResult.setValue(sum);
                data.add(pieDataResult);
            }

            pieResult.setNames(names);
            pieResult.setData(data);
            return pieResult;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
