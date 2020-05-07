let appData = new Vue({
    el: '#app-data',
    data: {
        appName: null,
        uri: null,
        appNameList: [],
        uriList: [],
        appUriList: [],
        appUriRuleList: [],
        appUri: null
    },
    watch: {
        appName(newValue, oldValue) {
            refreshUrl();
        },
        uri(newValue, oldValue) {
            refreshUrl();
        }
    }
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
            appData.appNameList = appNameList;
            setTimeout(function () {
                layui.form.render();
            }, 50);
        },
        error: function (data) {
            toastr.error("提交失败");
        }
    });
};

let queryAppUriList = function () {
    $.ajax({
        url: "/monitor/queryAppUriList",
        data: {
            appName: appData.appName
        },
        type: "post",
        dataType: "json",
        async: false,
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
            appData.uriList = uriList;
            appData.appUriList = data.result;
        },
        error: function (data) {
            toastr.error("提交失败");
        }
    });
};


var refreshUrl = function () {
    let stateObject = {};
    let uri = window.location.pathname;
    let appName = appData.appName;
    if (!appName) {
        appName = "";
    }
    let newUrl = uri + "?appName=" + appName;
    let appUri = appData.uri;
    if (!appUri) {
        appUri = "";
    }
    newUrl += "&uri=" + appUri;
    let title = "null";
    history.pushState(stateObject, title, newUrl);
};

let showUriDetailFunction = function (obj) {
    showUriDetail($(obj).attr("value"));
};

let showUriDetail = function (newUri) {
    // 旧uri
    let originUri = appData.uri;
    // 新选择的uri
    appData.uri = newUri;
    for (let i = 0; i < appData.appUriList.length; i++) {
        if (appData.appUriList[i].uri === appData.uri) {
            appData.appUri = appData.appUriList[i];
        }
    }

    $.ajax({
        url: "/monitor/queryAppUriRuleList",
        data: {
            appName: appData.appName,
            uri: appData.uri
        },
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (!data.success || !data.result) {
                console.log("查询错误或没有查询到数据");
                return;
            }
            appData.appUriRuleList = data.result;

            setTimeout(function () {
                layui.form.render();
            }, 50);
        },
        error: function (data) {
            toastr.error("提交失败");
        }
    });
};

layui.use(['form'], function () {
    let form = layui.form;

    appData.appName = getQueryVariable("appName");
    appData.uri = getQueryVariable("uri");

    queryAppList();

    form.on('select(appName)', function (data) {
        console.log(data.elem); //得到select原始DOM对象
        console.log(data.value); //得到被选中的值
        console.log(data.othis); //得到美化后的DOM对象

        if (!data.value) {
            return;
        }
        appData.appName = data.value;

        queryAppUriList();
    });

    if (appData.appName) {
        queryAppUriList();
    }

    if (appData.uri) {
        showUriDetail(appData.uri);
    }
});