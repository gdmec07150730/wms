/** table鼠标悬停换色* */
$(function () {
    // 如果鼠标移到行上时，执行函数
    $(".table tr").mouseover(function () {
        $(this).css({background: "#CDDAEB"});
        $(this).children('td').each(function (index, ele) {
            $(ele).css({color: "#1D1E21"});
        });
    }).mouseout(function () {
        $(this).css({background: "#FFF"});
        $(this).children('td').each(function (index, ele) {
            $(ele).css({color: "#909090"});
        });
    });
});

/**
 重定向跳转页面
 */
$(function () {
    $(".btn_redirect").click(function () {
        window.location.href = $(this).data("url");
    });

})

/*翻页操作*/

$(function () {

    $(".btn_page").click(function () {
        var pageNo = $(this).data("page") || $(":input[name='currentPage']").val()
        $(":input[name='currentPage']").val(pageNo);
        $("#searchForm").submit();
    });

    $(":input[name='pageSize']").change(function () {
        $(":input[name='currentPage']").val();
        $("#searchForm").submit();
    });

})



/*批量删除*/
$(function () {

    $("#all").change(function () {
        $(".acb").prop("checked", this.checked);
    })

})

$(function () {
    $(".btn_batchDelete").click(function () {
        var ids = $.map($(":input[name='IDCheck']:checked"), function (item) {
            return $(item).data("id");
        })

        var url = $(this).data("url");
        console.log(ids);
        console.log(url);
        if (ids.length == 0) {
            alert("请选择删除选项");
        } else {

            $.dialog({
                title: "温馨提示",
                content: "确认删除",
                ok: function () {
                    var dg = $.dialog({title: "温馨提示"});
                    $.post(url, {ids: ids}, function (data) {
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
                                callback:true
                            })
                        }
                    }, "json")
                },
                cancel: true
            })

        }
    });
})

/*单个删除*/
$(function () {
    $(".btn_delete").click(function () {
        var deleteurl = $(this).data("url");

        $.dialog({
            title: "温馨提示",
            content: "是否确认删除",
            ok: function () {
                var dg = $.dialog({title: "温馨提示"});
                $.post(deleteurl, function (data) {
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
})

