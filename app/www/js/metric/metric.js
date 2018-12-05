function pie(title, names, data) {
    var option = {
        title: {
            text: title,
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            y: 'bottom',
            data: names
        },
        toolbox: {
            show: true,
            feature: {
                dataView: { show: true, readOnly: false },
                restore: { show: true }
            }
        },
        calculable: true,
        series: [
            {
                name: title,
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: data
            }
        ]
    };

    return option;
}

function line(title, tags, xValues, series) {
    for (var i in series) {
        series[i].type = 'line';
    }

    var option = {
        title: {
            text: title
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            type: 'scroll',
            y: 'bottom',
            data: tags
        },
        grid: {
            x: 60, //默认是80px
            y: 60, //默认是60px
            x2: 40, //默认80px
            y2: 45 //默认60px
        },
        toolbox: {
            show: true,
            feature: {
                dataView: { show: true, readOnly: false },
                dataZoom: {
                    show: true,
                    title: {
                        dataZoom: '区域缩放',
                        dataZoomReset: '区域缩放-后退'
                    }
                },
                restore: { show: true }
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: xValues
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: series
    };
    return option;
}

function bar(title, tags, xValues, series) {
    for (var i in series) {
        series[i].type = 'bar';
    }
    var option = {
        title: {
            text: title
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            type: 'scroll',
            y: 'bottom',
            data: tags
        },
        grid: {
            x: 60, //默认是80px
            y: 60, //默认是60px
            x2: 40, //默认80px
            y2: 45 //默认60px
        },
        toolbox: {
            show: true,
            feature: {
                dataView: { show: true, readOnly: false },
                dataZoom: {
                    show: true,
                    title: {
                        dataZoom: '区域缩放',
                        dataZoomReset: '区域缩放-后退'
                    }
                },
                restore: { show: true }
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: xValues
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: series
    };
    return option;
}

var timeScale = [];

function refresh(mid) {
    var param = {};
    param.mId = mid;
    var chart = null;
    var title = null;
    for (var i in timeScale) {
        if (timeScale[i].mid == mid) {
            param.timeScale = timeScale[i].timeScale;
            chart = timeScale[i].chart;
            title = timeScale[i].title;
        }
    }
    sendPost('/api/monitor/queryMetricDataById', param, function (res) {
        if (res.ret == 0 && res.data) {
            if (chart == 'single') {
                $('#single' + mid).html('');
                $('#single' + mid).html(res.data.data);
            } else if (chart == 'pie') {
                var option = pie(title, res.data.names, res.data.data);
                echarts.init(document.getElementById('main' + mid)).setOption(option, true);
            } else if (chart == 'line') {
                var option = line(title, res.data.tags, res.data.xValues, res.data.series);
                echarts.init(document.getElementById('main' + mid)).setOption(option, true);
            } else if (chart == 'histogram') {
                var option = bar(title, res.data.tags, res.data.xValues, res.data.series);
                echarts.init(document.getElementById('main' + mid)).setOption(option, true);
            }
        }
    }, false);
}

var data = {
    init: function () {
        var s_id = $_GET['id'];
        sendGet('/api/monitor/queryMetricDataBySId', { sId: s_id }, function (sysRes) {
            if (sysRes.ret == 0) {
                var list = $('#content');
                list.html('');
                for (var i in sysRes.data) {
                    var item = $('<div class="card demo-card-header-pic">'
                        + '    <div valign="bottom" class="card-header color-white no-border no-padding">'
                        + '     <div id="main' + sysRes.data[i].mId + '" style="width: 400px;height:300px;"></div>'
                        + '    </div>'
                        + '    <div class="card-footer no-border">'
                        + '        <a href="#" class="link" data-title="' + sysRes.data[i].title + '" data-chart="' + sysRes.data[i].chart + '" data-id="' + sysRes.data[i].mId + '" data-scope="days-1">最近一天</a>'
                        + '        <a href="#" class="link" data-title="' + sysRes.data[i].title + '" data-chart="' + sysRes.data[i].chart + '" data-id="' + sysRes.data[i].mId + '" data-scope="weeks-1">最近一周</a>'
                        + '        <a href="#" class="link" data-title="' + sysRes.data[i].title + '" data-chart="' + sysRes.data[i].chart + '" data-id="' + sysRes.data[i].mId + '" data-scope="months-1">最近一月</a>'
                        + '    </div>'
                        + '</div>');
                    list.append(item);
                }

                for (var i in sysRes.data) {
                    var metric = sysRes.data[i];
                    sendPost('/api/monitor/queryMetricDataById', { mId: metric.mId }, function (res) {
                        if (res.ret == 0 && res.data) {
                            if (metric.chart == 'single') {
                                $('#main' + metric.mId).html('<div style="font-weight:bold;">' + metric.title +
                                    '</div><div id="single' + metric.mId + '" style="font-size:50px;text-align:center;line-height:300px;">' + res.data.data + '</div>');
                            } else if (metric.chart == 'pie') {
                                var option = pie(metric.title, res.data.names, res.data.data);
                                echarts.init(document.getElementById('main' + metric.mId)).setOption(option);
                            } else if (metric.chart == 'line') {
                                var option = line(metric.title, res.data.tags, res.data.xValues, res.data.series);
                                echarts.init(document.getElementById('main' + metric.mId)).setOption(option);
                            } else if (metric.chart == 'histogram') {
                                var option = bar(metric.title, res.data.tags, res.data.xValues, res.data.series);
                                echarts.init(document.getElementById('main' + metric.mId)).setOption(option);
                            }
                        }
                    }, false);
                }
            } else {
                alert(sysRes.msg);
            }
        }, false);

        // setInterval(function () {
        //     var s_id = $_GET['id'];
        //     sendGet('/api/monitor/queryMetricDataBySId', { sId: s_id }, function (sysRes) {
        //         if (sysRes.ret == 0) {
        //             for (var i in sysRes.data) {
        //                 refresh(sysRes.data[i].mId);
        //             }
        //         }
        //     }, false);
        // }, 10000);
    },
    binding: function () {
        $(document).on('refresh', '.pull-to-refresh-content', function (e) {
            setTimeout(function () {
                data.init();
                $.pullToRefreshDone('.pull-to-refresh-content');
            }, 2000);
        });
        $(document).on('click', '.pull-left', function () {
            window.location.href = "main.html";
        });
        $(document).on('click', '.link', function () {
            var mId = $(this).attr('data-id');
            var exists = false;
            for (var i in timeScale) {
                if (timeScale[i].mid == mId) {
                    timeScale[i].timeScale = $(this).attr('data-scope');
                    timeScale[i].chart = $(this).attr('data-chart');
                    timeScale[i].title = $(this).attr('data-title');
                    exists = true;
                }
            }
            if (!exists) {
                timeScale.push({ mid: mId, timeScale: $(this).attr('data-scope'), chart: $(this).attr('data-chart'), title: $(this).attr('data-title') });
            }
            refresh(mId);
        });
    }
}
$.init();
data.binding();
data.init();
