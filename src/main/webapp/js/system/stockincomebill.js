$(function () {


    $("#edit_table_body").on("click", ".searchproduct", function () {
        //获取第一个父级元素
        var currentTr = $(this).closest("tr");

        $.dialog.open("/product/selectProductList.do", {
            title: "选择货品",
            width: '70%',
            height: 700,
            close: function () {

                var ret = $.dialog.data("json");
                //如果接收的数据为空,则不传递参数
                if (ret != null) {
                    currentTr.find("[tag=name]").val(ret["name"]);
                    currentTr.find("[tag=pid]").val(ret["id"]);
                    currentTr.find("[tag=brand]").html(ret["brandName"]);
                    currentTr.find("[tag=costPrice]").val(ret["costPrice"]);
                }

            }
        });
    }).on("change", "[tag=costPrice],[tag=number]", function () {

        var currentTr = $(this).closest("tr");

        var price = currentTr.find("[tag=costPrice]").val() || 0;
        var number = currentTr.find("[tag=number]").val() || 0;
        currentTr.find("[tag=amount]").html((price * number).toFixed(2));

    }).on("click", ".removeItem", function () {
        var currentTr = $(this).closest("tr");
        if ($("#edit_table_body tr").size() > 1) {
            currentTr.remove();
        } else {
            currentTr.find("[tag=name]").val("");
            currentTr.find("[tag=pid]").val("");
            currentTr.find("[tag=costPrice]").val("");
            currentTr.find("[tag=number]").val("");
            currentTr.find("[tag=remark]").val("");
            currentTr.find("[tag=brand]").html("");
            currentTr.find("[tag=amount]").html("");
        }
    })


    $(".appendRow").click(function () {

        //找到tbody中的第一条tr
        var copy = $("#edit_table_body tr:first").clone(true);
        //清空tr中的数据
        copy.find("[tag=name]").val("");
        copy.find("[tag=pid]").val("");
        copy.find("[tag=brand]").html("");
        copy.find("[tag=costPrice]").val("");
        copy.find("[tag=number]").val("");
        copy.find("[tag=amount]").html("");
        copy.find("[tag=remark]").val("");

        //追加到tbody中
        copy.appendTo("#edit_table_body");

    });

    //提交之前修改明细的名称,添加索引

    $("#editForm").submit(function () {
        //找到tbody中所有的行
        $.each($("#edit_table_body tr"), function (index, item) {

            //找到当前行中所有需要提交的元素
            $(item).find("[tag=pid]").prop("name", "items[" + index + "].product.id");
            $(item).find("[tag=costPrice]").prop("name", "items[" + index + "].costPrice");
            $(item).find("[tag=number]").prop("name", "items[" + index + "].number");
            $(item).find("[tag=remark]").prop("name", "items[" + index + "].remark");
        });
    });

    //将表单变成ajaxFrom
    $("#editForm").ajaxForm(function (data) {
        var dg = $.dialog({title: "温馨提示"});

        if (data.success) {
            dg.content(data.msg).button({
                name: "完成",
                callback: function () {
                    window.location.href = "/stockIncomeBill/query.do";
                }
            })
        } else {
            dg.content(data.msg).button({
                name: "完成",
                callback: true
            })
        }
    });


    //审核操作
    $(".btn_audit").click(function () {
        var url = $(this).data("url");

        $.dialog({
            title: "温馨提示",
            content: "是否确认审核",
            ok: function () {
                var dg = $.dialog({title: "温馨提示"});
                $.post(url, function (data) {
                    if (data.success) {
                        dg.content(data.msg).button({
                            name: "确定",
                            callback: function () {
                                window.location.reload();
                            }
                        })
                    } else {
                        dg.content(data.msg).button({
                            name: "确定",
                            callback: true
                        })
                    }
                }, "json")
            },
            cancel: true
        })
    });

    $("[name=vdate]").click(function () {
        WdatePicker({
            readOnly: true
        });
    });
    $("[name=beginDate]").click(function () {
        WdatePicker({
            maxDate: $("[name=endDate]").val()
        })
    });
    $("[name=endDate]").click(function () {
        WdatePicker({
            minDate: $("[name=beginDate]").val()
        })
    });

})