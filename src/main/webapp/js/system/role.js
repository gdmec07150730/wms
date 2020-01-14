$(function () {

    $("#editForm").submit(function () {

        var isOk = $("#editForm").validate({
            rules: {
                name: "required",
                sn: "required"
            },
            messages: {
                name: "角色名称不能为空!",
                sn: "角色代码不能为空"
            }
        });

        return isOk.form();

    })


        $(".select_permission option").prop("selected",true);
        $(".select_menu option").prop("selected",true);
        $("#editForm").ajaxForm(function (data) {
            var dg = $.dialog({title: "温馨提示"});

            if (data.success) {
                dg.content(data.msg).button({
                    name: "完成",
                    callback: function () {
                        window.location.href = "/role/query.do";
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


$(function () {

    $("#selectAll").click(function () {
        $(".all_permission option").appendTo(".select_permission")
    });
    $("#deselectAll").click(function () {
        $(".select_permission option").appendTo(".all_permission")
    });
    $("#select").click(function () {
        $(".all_permission option:selected").appendTo(".select_permission")
    });
    $("#deselect").click(function () {
        $(".select_permission option:selected").appendTo(".all_permission")
    });

    var ids = $.map($(".select_permission option"), function (item) {
        return item.value
    })

    $.each($(".all_permission option"), function (index, item) {
        if($.inArray(item.value,ids) >= 0 ){
            $(item).remove();
        }
    })

    $("#mselectAll").click(function () {
        $(".all_menu option").appendTo(".select_menu")
    });
    $("#mdeselectAll").click(function () {
        $(".select_menu option").appendTo(".all_menu")
    });
    $("#mselect").click(function () {
        $(".all_menu option:selected").appendTo(".select_menu")
    });
    $("#mdeselect").click(function () {
        $(".select_menu option:selected").appendTo(".all_menu")
    });

    var ids = $.map($(".select_menu option"), function (item) {
        return item.value
    })

    $.each($(".all_menu option"), function (index, item) {
        if($.inArray(item.value,ids) >= 0 ){
            $(item).remove();
        }
    })
})