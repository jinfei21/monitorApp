package com.ppdai.monitor.service.impl;

import com.google.gson.internal.LazilyParsedNumber;
import com.ppdai.monitor.common.CommonConstants;
import com.ppdai.monitor.service.IGraphicsService;
import com.ppdai.monitor.service.entity.BaseResult;
import com.ppdai.monitor.service.entity.LineBaseResult;
import com.ppdai.monitor.service.entity.LineDataResult;
import com.ppdai.monitor.util.FormatterUtils;
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
@Service("line")
public class LineServiceImpl implements IGraphicsService {
    @Override
    public BaseResult process(QueryResponse queryResponse) {
        try {
            LineBaseResult lineResult = new LineBaseResult();
            List<String> tags = new ArrayList<>();
            List<String> xValues = new ArrayList<>();
            List<LineDataResult> series = new ArrayList<>();
            List<Query> queries = queryResponse.getQueries();
            Query query = queries.get(0);
            List<org.kairosdb.client.response.Result> results = query.getResults();
            for (Result resultData : results) {
                //设置x轴
                List<DataPoint> dataPoints = resultData.getDataPoints();
                if (xValues.size() == 0) {
                    for (DataPoint dataPoint : dataPoints) {
                        xValues.add(FormatterUtils.longToDateStr(dataPoint.getTimestamp()));
                    }
                }
                List<String> listHost = resultData.getTags().get(CommonConstants.TAG_HOST);
                List<String> listInstanceIp = resultData.getTags().get(CommonConstants.TAG_INSTANCE_IP);
                //设置详细信息
                LineDataResult lineDataResult = new LineDataResult();
                //设置标签
                if (StringUtils.isNotEmpty(listHost)) {
                    tags.add(listHost.get(0));
                    lineDataResult.setName(listHost.get(0));
                }
                if (StringUtils.isNotEmpty(listInstanceIp)) {
                    tags.add(listInstanceIp.get(0));
                    lineDataResult.setName(listInstanceIp.get(0));
                }
                List<Long> data = new ArrayList<>();
                for (DataPoint dataPoint : dataPoints) {
                    LazilyParsedNumber value = (LazilyParsedNumber) dataPoint.getValue();
                    data.add(value.longValue());
                }
                lineDataResult.setData(data);
                series.add(lineDataResult);

            }
            lineResult.setTags(tags);
            lineResult.setxValues(xValues);
            lineResult.setSeries(series);
            return lineResult;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
