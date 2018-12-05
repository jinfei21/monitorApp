package com.ppdai.monitor.service.impl;

import com.google.gson.internal.LazilyParsedNumber;
import com.ppdai.monitor.common.CommonConstants;
import com.ppdai.monitor.service.IGraphicsService;
import com.ppdai.monitor.service.entity.BaseResult;
import com.ppdai.monitor.service.entity.HistogramBaseResult;
import com.ppdai.monitor.service.entity.HistogramDataResult;
import com.ppdai.monitor.util.FormatterUtils;
import com.ppdai.monitor.util.StringUtils;
import org.kairosdb.client.builder.DataPoint;
import org.kairosdb.client.response.Query;
import org.kairosdb.client.response.QueryResponse;
import org.kairosdb.client.response.Result;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author haijiang
 * @Description 柱状图
 * @Date 2018/11/27
 **/
@Service("histogram")
@Primary
public class HistogramServiceImpl implements IGraphicsService {
    @Override
    public BaseResult process(QueryResponse queryResponse) {
        try {
            HistogramBaseResult histogramResult = new HistogramBaseResult();
            List<String> tags = new ArrayList<>();
            List<String> xValues = new ArrayList<>();
            List<HistogramDataResult> series = new ArrayList<>();
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
                HistogramDataResult histogramDataResult = new HistogramDataResult();
                //设置标签
                if (StringUtils.isNotEmpty(listHost)) {
                    tags.add(listHost.get(0));
                    histogramDataResult.setName(listHost.get(0));
                }
                if (StringUtils.isNotEmpty(listInstanceIp)) {
                    tags.add(listInstanceIp.get(0));
                    histogramDataResult.setName(listInstanceIp.get(0));
                }
                List<Long> data = new ArrayList<>();
                for (DataPoint dataPoint : dataPoints) {
                    LazilyParsedNumber value = (LazilyParsedNumber) dataPoint.getValue();
                    data.add(value.longValue());
                }
                histogramDataResult.setData(data);
                series.add(histogramDataResult);
            }

            histogramResult.setTags(tags);
            histogramResult.setxValues(xValues);
            histogramResult.setSeries(series);
            return histogramResult;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
