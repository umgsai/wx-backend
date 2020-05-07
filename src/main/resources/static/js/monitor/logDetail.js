let logDetailData = new Vue({
    el: '#log-detail',
    data: {
        appName: null,
        uri: null,
        warnText: null,
        beginTime: null,
        endTime: null,
        page: 1,
        limit: 20,
        appNameList: [],
        uriList: [],
        reportList: [],
        totalCount: 0
    },
    watch: {
        appName(newValue, oldValue) {
            queryAppUriList(logDetailData.appName);
        }
    }
});

layui.use(['form', 'laydate', 'table'], function () {
    var form = layui.form;

    logDetailData.appName = getQueryVariable("appName");
    logDetailData.uri = getQueryVariable("uri");
    logDetailData.warnText = getQueryVariable("warnText");
    logDetailData.beginTime = getQueryVariable("beginTime");
    logDetailData.endTime = getQueryVariable("endTime");
    let page = getQueryVariable("page");
    if (page) {
        logDetailData.page = page;
    }
    let limit = getQueryVariable("limit");
    if (limit) {
        logDetailData.limit = limit;
    }
    if (logDetailData.warnText) {
        logDetailData.warnText = decodeURI(logDetailData.warnText);
    }

    let endTime = new Date();
    if (logDetailData.endTime) {
        logDetailData.endTime = decodeURI(logDetailData.endTime);
        logDetailData.endTime = logDetailData.endTime.replace(/-/g, "/");
        logDetailData.endTime = new Date(logDetailData.endTime);
        endTime = logDetailData.endTime;
    }
    let beginTime = new Date(endTime.getTime() - 60 * 60 * 1000);
    if (logDetailData.beginTime) {
        logDetailData.beginTime = decodeURI(logDetailData.beginTime);
        logDetailData.beginTime = logDetailData.beginTime.replace(/-/g, "/");
        logDetailData.beginTime = new Date(logDetailData.beginTime);
        beginTime = logDetailData.beginTime;
    }

    renderDate(beginTime.getTime(), endTime.getTime());

    if (logDetailData.appName && logDetailData.uri) {
        queryLogDetailData();
    }

    form.on('select(appName)', function (data) {
        if (!data.value) {
            return;
        }
        logDetailData.appName = data.value;
    });

    form.on('select(uri)', function (data) {
        if (!data.value) {
            return;
        }
        logDetailData.uri = data.value;
    });

    queryAppList();
});

let queryAppList = function () {
    $.ajax({
        url: "/monitor/queryAppList",
        data: {},
        type: "post",
        dataType: "json",
        success: function (data) {
            if (!data.success || !data.result) {
                console.log("查询错误或没有查询到数据");
                return;
            }
            let length = data.result.length;
            let appNameList = [];
            for (let i = 0; i < length; i++) {
                appNameList.push(data.result[i].appName);
            }
            logDetailData.appNameList = appNameList;
            setTimeout(function () {
                layui.form.render();
            }, 50);
        },
        error: function (data) {
            toastr.error("提交失败");
        }
    });
};

let queryAppUriList = function (appName) {
    $.ajax({
        url: "/monitor/queryAppUriList",
        data: {
            appName: appName
        },
        type: "post",
        dataType: "json",
        success: function (data) {
            if (!data.success || !data.result) {
                console.log("查询错误或没有查询到数据");
                return;
            }
            let length = data.result.length;
            let uriList = [];
            for (let i = 0; i < length; i++) {
                uriList.push(data.result[i].uri);
            }
            logDetailData.uriList = uriList;
            setTimeout(function () {
                layui.form.render();
            }, 50);
        },
        error: function (data) {
            toastr.error("提交失败");
        }
    });
};

let refreshUrl = function () {
    let stateObject = {};
    let newUrl = window.location.pathname;

    if (!logDetailData.appName) {
        logDetailData.appName = "";
    }
    if (!logDetailData.uri) {
        logDetailData.uri = "";
    }
    if (!logDetailData.warnText) {
        logDetailData.warnText = "";
    }
    newUrl = newUrl + "?appName=" + logDetailData.appName;
    newUrl += "&uri=" + logDetailData.uri;
    newUrl += "&warnText=" + logDetailData.warnText;
    newUrl += "&page=" + logDetailData.page;
    newUrl += "&limit=" + logDetailData.limit;
    newUrl += "&beginTime=" + logDetailData.beginTime;
    newUrl += "&endTime=" + logDetailData.endTime;
    let title = "null";
    history.pushState(stateObject, title, newUrl);
};

let queryLogDetailData = function () {
    refreshUrl();
    if (!logDetailData.appName) {
        toastr.error("应用名不允许为空");
        return;
    }
    if (!logDetailData.uri) {
        toastr.error("URI不允许为空");
        return;
    }
    if (!logDetailData.beginTime) {
        toastr.error("开始时间不允许为空");
        return;
    }
    if (!logDetailData.endTime) {
        toastr.error("结束时间不允许为空");
        return;
    }
    layui.table.init('detail-table', {
        url: '/monitor/queryLogDetail?appName=' + logDetailData.appName + '&uri=' + logDetailData.uri + '&warnText=' + logDetailData.warnText + '&beginTime=' + logDetailData.beginTime + '&endTime=' + logDetailData.endTime,
        skin: 'row',
        // initSort: {field: 'total', type: 'desc'},
        even: true,
        page: {
            layout: ['count', 'prev', 'page', 'next', 'skip', 'refresh'],
            curr: logDetailData.page,
            limit: logDetailData.limit
        },
        done: function (res, curr, count) {
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            console.log(res);
            //得到当前页码
            console.log(curr);
            //得到数据总量
            console.log(count);

            if (!count || count <= 0) {
                logDetailData.reportList = [];
                logDetailData.totalCount = 0;
            } else {
                logDetailData.reportList = res.data;
                logDetailData.totalCount = count;
            }
        }
    });
};

let renderDate = function (beginTimeStamp, endTimeStamp) {
    layui.laydate.render({
        elem: '#beginTime', //指定元素
        type: 'datetime',
        value: new Date(beginTimeStamp),
        format: 'yyyy-MM-dd HH:mm',
        done: function (value, date) {
            logDetailData.beginTime = value;
        }
    });
    layui.laydate.render({
        elem: '#endTime',
        type: 'datetime',
        value: new Date(endTimeStamp),
        format: 'yyyy-MM-dd HH:mm',
        done: function (value, date) {
            logDetailData.endTime = value;
        }
    });
    logDetailData.beginTime = $("#beginTime").val();
    logDetailData.endTime = $("#endTime").val();
};

let queryNow = function () {
    let endTime = new Date();
    let beginTime = new Date(endTime.getTime() - 60 * 60 * 1000);
    renderDate(beginTime.getTime(), endTime.getTime());
    queryLogDetailData();
};

let queryNextHour = function () {
    let beginTime = logDetailData.beginTime.substring(0, 19);
    beginTime = beginTime.replace(/-/g, '/');
    beginTime = new Date(beginTime);
    let minutes = beginTime.getMinutes();
    var seconds = beginTime.getSeconds();
    let beginTimeStamp = beginTime.getTime();
    beginTimeStamp = beginTimeStamp - seconds * 1000 - minutes * 60 * 1000;
    beginTimeStamp = beginTimeStamp + 60 * 60 * 1000;
    let endTimeStamp = beginTimeStamp + 60 * 60 * 1000;

    if (endTimeStamp > new Date().getTime()) {
        console.log("endTime超过现在。");
        return;
    }

    renderDate(beginTimeStamp, endTimeStamp);

    queryLogDetailData();
};

let queryPrevHour = function () {
    let beginTime = logDetailData.beginTime.substring(0, 19);
    beginTime = beginTime.replace(/-/g, '/');
    beginTime = new Date(beginTime);
    let minutes = beginTime.getMinutes();
    var seconds = beginTime.getSeconds();
    let beginTimeStamp = beginTime.getTime();
    beginTimeStamp = beginTimeStamp - seconds * 1000 - minutes * 60 * 1000;
    beginTimeStamp = beginTimeStamp - 60 * 60 * 1000;
    let endTimeStamp = beginTimeStamp + 60 * 60 * 1000;

    renderDate(beginTimeStamp, endTimeStamp);

    queryLogDetailData();
};

let queryNextDay = function () {
    let beginTime = logDetailData.beginTime.substring(0, 19);
    beginTime = beginTime.replace(/-/g, '/');
    beginTime = new Date(beginTime);
    let minutes = beginTime.getMinutes();
    var seconds = beginTime.getSeconds();
    let beginTimeStamp = beginTime.getTime();
    beginTimeStamp = beginTimeStamp - seconds * 1000 - minutes * 60 * 1000;
    beginTimeStamp = beginTimeStamp + 24 * 60 * 60 * 1000;
    let endTimeStamp = beginTimeStamp + 60 * 60 * 1000;

    if (endTimeStamp > new Date().getTime()) {
        console.log("endTime超过现在。");
        return;
    }

    renderDate(beginTimeStamp, endTimeStamp);

    queryLogDetailData();
};

let queryPrevDay = function () {
    let beginTime = logDetailData.beginTime.substring(0, 19);
    beginTime = beginTime.replace(/-/g, '/');
    beginTime = new Date(beginTime);
    let minutes = beginTime.getMinutes();
    var seconds = beginTime.getSeconds();
    let beginTimeStamp = beginTime.getTime();
    beginTimeStamp = beginTimeStamp - seconds * 1000 - minutes * 60 * 1000;
    beginTimeStamp = beginTimeStamp - 24 * 60 * 60 * 1000;
    let endTimeStamp = beginTimeStamp + 60 * 60 * 1000;

    renderDate(beginTimeStamp, endTimeStamp);

    queryLogDetailData();
};