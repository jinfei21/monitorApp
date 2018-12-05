var loading = false;
var pageSize = 10;
var pageNum = 1;

function append() {
    sendGet('/api/monitor/queryAllSystem', {pageSize: pageSize, pageNum: pageNum}, function (res) {
        if (res.ret == 0) {
            var list = $('#content');
            for (var i in res.data) {
                var item = $('<div class="card">'
                    + '<div class="card-content">'
                    + '      <div class="list-block media-list">'
                    + '         <ul>'
                    + '             <li class="item-content" data-id="' + res.data[i].id + '">'
                    + '                     <div class="item-media"><i class="icon icon-f7"></i></div>'
                    + '                     <div class="item-inner">'
                    + '                             <div class="item-title-row">'
                    + '                                 <div class="item-title">' + res.data[i].name + '</div>'
                    + '                             </div>'
                    + '                              <div class="item-subtitle">部门：' + res.data[i].depart + '|负责人：' + res.data[i].owner + '</div>'
                    + '                     </div>'
                    + '             </li>'
                    + '         </ul>'
                    + '     </div>'
                    + ' </div>'
                    + '</div>');
                list.append(item);
            }
        } else {
            alert(res.msg);
        }
        $('.infinite-scroll-preloader').css('display','none');
    });
}

var data = {
    init: function () {
        $('#content').html('');
        append();
    },
    binding: function () {
        $(document).on('click', '.item-content', function () {
            window.location.href = "metricList.html?id=" + $(this).attr('data-id');
        });
        $(document).on('refresh', '.pull-to-refresh-content', function () {
            setTimeout(function () {
                data.init();
                // done
                $.pullToRefreshDone('.pull-to-refresh-content');
            }, 1000);
        });
        $(document).on('infinite', '.infinite-scroll', function () {
            $('.infinite-scroll-preloader').css('display','block');
            // 如果正在加载，则退出
            if (loading) return;
            // 设置flag
            loading = true;

            setTimeout(function () {
                loading = false;

                if (pageNum >= 100) {
                    $.detachInfiniteScroll($('.infinite-scroll'));
                    $('.infinite-scroll-preloader').remove();
                    return;
                }

                pageNum++;
                append();
                $('.infinite-scroll-preloader').css('display','none');
            }, 1000);
        });
    }
}
$.init();
data.binding();
data.init();
