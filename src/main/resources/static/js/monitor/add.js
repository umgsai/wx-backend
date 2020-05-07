let monitorAddData = new Vue({
    el: '#monitor-add',
    data: {
        appName: null,
        protocol: "HTTP",
        uri: null,
        uriDesc: null,
        classifyBy: "msg",
        defaultWarnText: null,
        rule: "ALL",
        rules: [],
        errorMsg: null
    }
});

layui.use(['form'], function () {
    let form = layui.form;

    form.on('radio(rule)', function (data) {
        monitorAddData.rule = data.value;
        if (data.value === 'SELF_DEFINITION') {
            if (monitorAddData.rules.length === 0) {
                monitorAddData.rules.push({
                    ruleDesc: null,
                    warnText: null,
                    ruleExpression: null,
                    priority: null,
                    enable: 'ENABLE'
                });
            }
        }
    });

    form.on('radio(classifyBy)', function (data) {
        monitorAddData.classifyBy = data.value;
    });

    form.on('radio(protocol)', function (data) {
        monitorAddData.protocol = data.value;
    });

    let monitorAddFormData = form.val("monitorAddForm");
    console.log(monitorAddFormData);
});

function addRule(obj) {
    monitorAddData.rules.push({
        ruleDesc: null,
        warnText: null,
        ruleExpression: null
    });
}

function deleteRule(obj) {
    let rules = monitorAddData.rules;
    if (rules.length === 1) {
        toastr.error("至少需要添加一条规则");
        return;
    }
    let index = $(obj).attr("index");
    let newRules = [];
    for (let i = 0; i < rules.length; i++) {
        if (i == index) {
            continue;
        }
        newRules.push(rules[i]);
    }
    monitorAddData.rules = newRules;
}

function submitData() {
    if (!monitorAddData.appName) {
        toastr.error("应用名不允许为空");
        return;
    }
    if (!monitorAddData.uri) {
        toastr.error("URI不允许为空");
        return;
    }
    if (!monitorAddData.rule) {
        toastr.error("请选择日志分类规则");
        return;
    }
    if (monitorAddData.rule === "SELF_DEFINITION") {
        if (monitorAddData.rules.length === 0) {
            toastr.error("请至少添加一条自定义分类规则");
            return;
        }
        for (let i = 0; i < monitorAddData.rules.length; i++) {
            let ruleData = monitorAddData.rules[i];
            if (!ruleData.ruleDesc) {
                toastr.error("请填写第" + (i + 1) + "条规则的描述");
                return;
            }
            if (!ruleData.warnText) {
                toastr.error("请填写第" + (i + 1) + "条规则的告警文案");
                return;
            }
            if (!ruleData.ruleExpression) {
                toastr.error("请填写第" + (i + 1) + "条规则的规则表达式");
                return;
            }
        }
    }
    console.log(JSON.stringify(monitorAddData._data));

    $.ajax({
        url: "/monitor/addUri",
        data: JSON.stringify(monitorAddData._data),
        type: "post",
        contentType: 'application/json',
        dataType: "json",
        success: function (data) {
            if (data && data.success) {
                window.location = "/monitor/appList.html?appName=" + monitorAddData.appName + "&uri=" + monitorAddData.uri;
            } else {
                monitorAddData.errorMsg = data.msg;
                toastr.error(data.msg);
            }
        },
        error: function (data) {
            toastr.error("提交失败");
        }
    });
}