let reportData = new Vue({
    el: '#report-data',
    data: {
        appName: null,
        uri: null,
        beginTime: null,
        endTime: null,
        appNameList: [],
        uriList: [],
        reportList: [],
        totalCount: 0
    },
    watch: {
        appName(newValue, oldValue) {
            queryAppUriList(reportData.appName);
        }
    }
});

layui.use(['form', 'laydate', 'table'], function () {
    var form = layui.form;

    reportData.appName = getQueryVariable("appName");
    reportData.uri = getQueryVariable("uri");
    reportData.beginTime = getQueryVariable("beginTime");
    reportData.endTime = getQueryVariable("endTime");
    if (reportData.warnText) {
        reportData.warnText = decodeURI(reportData.warnText);
    }

    let endTime = new Date();
    if (reportData.endTime) {
        reportData.endTime = decodeURI(reportData.endTime);
        reportData.endTime = reportData.endTime.replace(/-/g, "/");
        reportData.endTime = new Date(reportData.endTime);
        endTime = reportData.endTime;
    }
    let beginTime = new Date(endTime.getTime() - 60 * 60 * 1000);

    renderDate(beginTime.getTime(), endTime.getTime());

    if (reportData.appName && reportData.uri) {
        queryReportData();
    }

    form.on('select(appName)', function (data) {
        if (!data.value) {
            return;
        }
        reportData.appName = data.value;
        queryAppUriList(reportData.appName);
    });

    form.on('select(uri)', function (data) {
        if (!data.value) {
            return;
        }
        reportData.uri = data.value;
        queryReportData();
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
            reportData.appNameList = appNameList;
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
    let uri = window.location.pathname;
    let newUrl = uri + "?appName=" + reportData.appName;
    newUrl += "&uri=" + reportData.uri;
    newUrl += "&beginTime=" + reportData.beginTime;
    newUrl += "&endTime=" + reportData.endTime;
    let title = "null";
    history.pushState(stateObject, title, newUrl);
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
            reportData.uriList = uriList;
            setTimeout(function () {
                layui.form.render();
            }, 50);
        },
        error: function (data) {
            toastr.error("提交失败");
        }
    });
};

let queryReportData = function () {
    refreshUrl();
    if (!reportData.appName) {
        toastr.error("应用名不允许为空");
        return;
    }
    if (!reportData.uri) {
        toastr.error("URI不允许为空");
        return;
    }
    if (!reportData.beginTime) {
        toastr.error("开始时间不允许为空");
        return;
    }
    if (!reportData.endTime) {
        toastr.error("结束时间不允许为空");
        return;
    }
    layui.table.init('report-table', {
        url: '/monitor/queryReportData?appName=' + reportData.appName + '&uri=' + reportData.uri + '&beginTime=' + reportData.beginTime + '&endTime=' + reportData.endTime,
        skin: 'row',
        // initSort: {field: 'total', type: 'desc'},
        even: true,
        page: {
            layout: ['count', 'prev', 'page', 'next', 'skip', 'refresh'],
            curr: reportData.page,
            limit: reportData.limit
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
                reportData.reportList = [];
                reportData.totalCount = 0;
            } else {
                reportData.reportList = res.data;
                reportData.totalCount = count;
            }
        }
    });
};

let queryNow = function () {
    let endTime = new Date();
    let beginTime = new Date(endTime.getTime() - 60 * 60 * 1000);

    renderDate(beginTime.getTime(), endTime.getTime());

    queryReportData();
};

let queryNextHour = function () {
    let beginTime = reportData.beginTime.substring(0, 19);
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

    queryReportData();
};

let queryPrevHour = function () {
    let beginTime = reportData.beginTime.substring(0, 19);
    beginTime = beginTime.replace(/-/g, '/');
    beginTime = new Date(beginTime);
    let minutes = beginTime.getMinutes();
    var seconds = beginTime.getSeconds();
    let beginTimeStamp = beginTime.getTime();
    beginTimeStamp = beginTimeStamp - seconds * 1000 - minutes * 60 * 1000;
    beginTimeStamp = beginTimeStamp - 60 * 60 * 1000;
    let endTimeStamp = beginTimeStamp + 60 * 60 * 1000;

    renderDate(beginTimeStamp, endTimeStamp);

    queryReportData();
};

let queryNextDay = function () {
    let beginTime = reportData.beginTime.substring(0, 19);
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

    queryReportData();
};

let queryPrevDay = function () {
    let beginTime = reportData.beginTime.substring(0, 19);
    beginTime = beginTime.replace(/-/g, '/');
    beginTime = new Date(beginTime);
    let minutes = beginTime.getMinutes();
    var seconds = beginTime.getSeconds();
    let beginTimeStamp = beginTime.getTime();
    beginTimeStamp = beginTimeStamp - seconds * 1000 - minutes * 60 * 1000;
    beginTimeStamp = beginTimeStamp - 24 * 60 * 60 * 1000;
    let endTimeStamp = beginTimeStamp + 60 * 60 * 1000;

    renderDate(beginTimeStamp, endTimeStamp);

    queryReportData();
};

let renderDate = function (beginTimeStamp, endTimeStamp) {
    layui.laydate.render({
        elem: '#beginTime', //指定元素
        type: 'datetime',
        value: new Date(beginTimeStamp),
        format: 'yyyy-MM-dd HH:mm',
        done: function (value, date) {
            reportData.beginTime = value;
        }
    });
    layui.laydate.render({
        elem: '#endTime',
        type: 'datetime',
        value: new Date(endTimeStamp),
        format: 'yyyy-MM-dd HH:mm',
        done: function (value, date) {
            reportData.endTime = value;
        }
    });
    reportData.beginTime = $("#beginTime").val();
    reportData.endTime = $("#endTime").val();
};

let showChart = function (obj, title) {
    let treeMap;
    let itemList = [];
    for (let i = 0; i < reportData.reportList.length; i++) {
        let report = reportData.reportList[i];
        if (report.title === title) {
            treeMap = report.treeMap;
            itemList = report.itemList;
        }
    }
    let tr = $(obj).parent().parent().parent();
    if (!tr.next() || tr.next().attr("data-title") !== title) {
        $(obj).text("[hide]");
        tr.after("<tr data-title='" + title + "' style='height: 300px;'><td colspan='8' style='line-height: 300px'><div style='width: 100%; height: 300px' id='" + title + "'></div></td></tr>");
    } else {
        $(obj).text("[show]");
        tr.next().remove();
        return;
    }

    let myChart = echarts.init(document.getElementById(title));

    // 指定图表的配置项和数据
    let option = {
        tooltip: {},
        xAxis: {
            type: 'category',
            data: []
        },
        yAxis: {},
        series: [{
            data: [],
            type: 'bar'
        }]
    };

    for (let item of itemList) {
        option.xAxis.data.push(item.itemKey.split(" ")[1]);
        option.series[0].data.push(item.count);
    }

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}