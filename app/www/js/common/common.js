var domain = 'http://172.20.19.23:1200';

function sendPost(url, param, recallFunction, async) {
    if (!async){
        async = false;
    }
    var msg  = 0;
    // addPayChecked();
    $.ajax({
        url:domain + url,
        type:'POST',
        data:JSON.stringify(param),
        dataType:'JSON',
        contentType:'application/json; charset=UTF-8',
        async:async,
        dataFilter:function (data,type) {
            return data;
        },
        success: function (res) {
            if(!res){
                return;
            }
            msg = recallFunction(res);
        }
    });
    return msg;
}

function sendGet(url,param,recallFunction,async) {
    if (!async){
        async=false;
    }
    var msg  = 0;
    $.ajax({
        url:domain + url,
        data:param,
        async: async,
        dataFilter:function (data,type) {
            return data;
        },
        success:function(res){
            if(!res){
                return;
            }
            msg = recallFunction(res);
        }
    })
    return msg ? msg:0;
}

var $_GET = (function () {
    var url = window.document.location.href.toString();
    var u = url.split("?");
    if (typeof (u[1]) == "string") {
        u = u[1].split("&");
        var get = {};
        for (var i in u) {
            var j = u[i].split("=");
            get[j[0]] = j[1];
        }
        return get;
    } else {
        return {};
    }
})();
