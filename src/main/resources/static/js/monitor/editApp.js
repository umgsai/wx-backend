let appData = new Vue({
    el: '#app-data',
    data: {
        id: 0,
        appName: null,
        uri: null,
        protocol: null,
        uriDesc: null,
        classifyBy: null,
        classifyRule: null,
        defaultWarnText: null,
        appUriRuleList: [],
        isEnableMonitor: false,
        isEnableWarn: false
    },
    watch: {}
});

let addRule = function () {
    appData.appUriRuleList.push({
        ruleDesc: null,
        warnText: null,
        ruleExpression: null
    });
    setTimeout(function () {
        layui.form.render();
    }, 50);
    setTimeout(function () {
        pageEnd.scrollIntoView();
    }, 50);
};

let submitRuleData = function (form) {
    if (!$.fn.serializeObject) {
        initSerializeObject();
    }
    let id = $(form).attr("id");
    let ruleData = $("#" + id).serializeObject();
    if (!ruleData.appName) {
        toastr.error("应用名不允许为空");
        return;
    }
    if (!ruleData.uri) {
        toastr.error("URI不允许为空");
        return;
    }
    if (!ruleData.ruleDesc) {
        toastr.error("规则描述不允许为空");
        return;
    }
    if (!ruleData.ruleExpression) {
        toastr.error("规则表达式不允许为空");
        return;
    }
    if (!ruleData.warnText) {
        toastr.error("告警文案不允许为空");
        return;
    }
    $.ajax({
        url: "/monitor/submitAppUriRule",
        data: JSON.stringify(ruleData),
        type: "POST",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (data) {
            console.log(data);
            if (!data.success) {
                toastr.error(data.msg);
                return;
            }
            toastr.success("提交保存成功");
            queryAppUriRuleList();
        },
        error: function (data) {
            toastr.error("提交失败");
        }
    });
};

let deleteRule = function (obj) {
    let index = layui.layer.confirm('确认删除吗？', {
        btn: ['确认', '取消'] //按钮
    }, function () {
        // layer.msg('的确很重要', {icon: 1});
        let id = $(obj).attr("id");
        if (!id) {
            //还没有提交保存
            queryAppUriRuleList();
            layui.layer.close(index);
            return;
        }
        $.ajax({
            url: "/monitor/deleteAppUriRule",
            data: JSON.stringify({
                id: id
            }),
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            success: function (data) {
                console.log(data);
                layui.layer.close(index);
                if (!data.success) {
                    toastr.error("删除异常");
                    return;
                }
                layui.layer.msg('删除成功', {icon: 1});
                queryAppUriRuleList();
            },
            error: function (data) {
                toastr.error("提交失败");
            }
        });
    }, function () {
        layui.layer.close(index);
        console.log("取消删除");
    });
};

let submitData = function () {
    if (!appData.protocol) {
        toastr.error("接口类型不允许为空");
        return;
    }
    if (!appData.uriDesc) {
        toastr.error("接口描述不允许为空");
        return;
    }
    if (!appData.classifyBy) {
        toastr.error("分类字段不允许为空");
        return;
    }
    if (!appData.classifyRule) {
        toastr.error("分类规则不允许为空");
        return;
    }
    if (!appData.defaultWarnText) {
        toastr.error("默认告警文案不允许为空");
        return;
    }
    $.ajax({
        url: "/monitor/updateAppUri",
        data: JSON.stringify(appData._data),
        type: "POST",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (data) {
            console.log(data);
            if (!data.success) {
                toastr.error("更新异常");
                return;
            }
            toastr.success("更新成功");
            queryAppUriRuleList();
        },
        error: function (data) {
            toastr.error("提交失败");
        }
    });
};

let queryAppUriRuleList = function () {
    $.ajax({
        url: "/monitor/queryAppUriRuleList",
        data: {
            appName: appData.appName,
            uri: appData.uri
        },
        type: "POST",
        dataType: "json",
        async: false,
        success: function (data) {
            console.log(data);
            if (!data.success || !data.result) {
                console.log("查询错误或没有查询到数据");
                return;
            }
            appData.appUriRuleList = [];
            setTimeout(function () {
                appData.appUriRuleList = data.result;
            }, 10);
            setTimeout(function () {
                layui.form.render();
            }, 20);
        },
        error: function (data) {
            toastr.error("提交失败");
        }
    });
};

layui.use(['form', 'layer'], function () {
    var form = layui.form;

    appData.appName = getQueryVariable("appName");
    appData.uri = getQueryVariable("uri");

    if (!appData.appName || !appData.uri) {
        toastr.error("获取参数失败");
        return;
    }

    $.ajax({
        url: "/monitor/queryAppUri",
        data: {
            appName: appData.appName,
            uri: appData.uri
        },
        type: "GET",
        dataType: "json",
        success: function (data) {
            console.log(data);
            if (!data.success || !data.result) {
                toastr.error("未查询到应用信息，请检查应用名和URI");
                return;
            }
            let result = data.result;

            appData.id = result.id;
            appData.protocol = result.protocol;
            appData.uriDesc = result.uriDesc;
            appData.classifyBy = result.classifyBy;
            appData.classifyRule = result.classifyRule;
            appData.defaultWarnText = result.defaultWarnText;
            appData.isEnableMonitor = result.isEnableMonitor;
            appData.isEnableWarn = result.isEnableWarn;

            queryAppUriRuleList();

            setTimeout(function () {
                layui.form.render();
            }, 50);
        },
        error: function (data) {
            toastr.error("提交失败");
        }
    });

    layui.form.on('radio(protocol)', function (data) {
        appData.protocol = data.value;

    });

    layui.form.on('radio(classifyBy)', function (data) {
        appData.classifyBy = data.value;
    });

    layui.form.on('radio(classifyRule)', function (data) {
        appData.classifyRule = data.value;
    });

    layui.form.on('switch(enable)', function (data) {
        let enableStatus = data.elem.checked ? "ENABLE" : "DISABLE";
        let enableStatusDesc = data.elem.checked ? "启用" : "禁用";
        let id = $(data.elem).attr("data-id");
        if (!id) {
            console.log("未获取到规则ID，暂不更新");
            return;
        }
        $.ajax({
            url: "/monitor/updateAppUriEnableStatus",
            data: {
                id: id,
                enableStatus: enableStatus
            },
            type: "POST",
            dataType: "json",
            success: function (data) {
                console.log(data);
                if (!data.success || !data.result) {
                    toastr.error("更新失败");
                    return;
                }
                layui.layer.msg('已将规则状态更新为' + enableStatusDesc, {icon: 1});
            },
            error: function (data) {
                toastr.error("提交失败");
            }
        });
    });

    layui.form.on('switch(isEnableMonitor)', function (data) {
        let enableStatus = data.elem.checked;
        let enableStatusDesc = data.elem.checked ? "启用" : "禁用";
        if (!appData.appName || !appData.uri) {
            console.log("未获取到规则ID，暂不更新");
            return;
        }
        $.ajax({
            url: "/monitor/updateAppUriMonitorStatus",
            data: {
                appName: appData.appName,
                uri: appData.uri,
                enableStatus: enableStatus
            },
            type: "POST",
            dataType: "json",
            success: function (data) {
                console.log(data);
                if (!data.success) {
                    toastr.error("更新失败");
                    return;
                }
                layui.layer.msg('已将监控状态更新为' + enableStatusDesc, {icon: 1});
            },
            error: function (data) {
                toastr.error("提交失败");
            }
        });
    });

    layui.form.on('switch(isEnableWarn)', function (data) {
        let enableStatus = data.elem.checked;
        let enableStatusDesc = data.elem.checked ? "启用" : "禁用";
        if (!appData.appName || !appData.uri) {
            console.log("未获取到规则ID，暂不更新");
            return;
        }
        $.ajax({
            url: "/monitor/updateAppUriWarnStatus",
            data: {
                appName: appData.appName,
                uri: appData.uri,
                enableStatus: enableStatus
            },
            type: "POST",
            dataType: "json",
            success: function (data) {
                console.log(data);
                if (!data.success) {
                    toastr.error("更新失败");
                    return;
                }
                layui.layer.msg('已将告警状态更新为' + enableStatusDesc, {icon: 1});
            },
            error: function (data) {
                toastr.error("提交失败");
            }
        });
    });
});