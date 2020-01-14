/*ajax表单提交*/
$(function () {

    $("#editForm").submit(function () {

        var isOk = $("#editForm").validate({
            rules: {
                name: "required",
                sn: "required"
            },
            messages: {
                name: "部门名称不能为空!",
                sn: "部门代码不能为空"
            }
        });

        return isOk.form();

    })
        $("#editForm").ajaxForm(function (data) {
            var dg = $.dialog({title: "温馨提示"});

            if (data.success) {
                dg.content(data.msg).button({
                    name: "完成",
                    callback: function () {
                        window.location.href = "/department/query.do";
                    }
                })
            } else {
                dg.content(data.msg).button({
                    name: "完成",
                    callback: true
                })
            }
        });

})
