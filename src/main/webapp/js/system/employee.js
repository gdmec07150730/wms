
/*表单验证*/
$(function () {
    $("#editForm").submit(function () {
        var isOK = $("#editForm").validate({

            rules:{
                name:"required",
                password:{
                    required:true,
                    minlength:6
                },
                repassword:{
                    required:true,
                    minlength:6,
                    equalTo:"#password"
                },
                email:{
                    required:true,
                    email:true
                },
                age:{
                    required:true,
                    range:[10,70]
                }
            },
            messages:{
                name:"请输入用户名",
                password:{
                    required:"请输入密码",
                    minlength:"请输入6位以上字符"
                },
                repassword:{
                    required:"请再次输入密码",
                    minlength:"请输入6位以上字符",
                    equalTo:"密码不一致"
                },
                email:{
                    required:"请输入email",
                    email:"请输入正确的邮箱"
                },
                age:{
                    required:"请输入年龄",
                    range:"年龄为10-70岁"
                }
            }

        });

        return isOK.form();
    });

    $(".select_role option").prop("selected",true);

    $("#editForm").ajaxForm(function (data) {
        var dg = $.dialog({title: "温馨提示"});

        if (data.success) {
            dg.content(data.msg).button({
                name: "完成",
                callback: function () {
                    window.location.href = "/employee/query.do";
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
        $(".all_role option").appendTo(".select_role")
    });
    $("#deselectAll").click(function () {
        $(".select_role option").appendTo(".all_role")
    });
    $("#select").click(function () {
        $(".all_role option:selected").appendTo(".select_role")
    });
    $("#deselect").click(function () {
        $(".select_role option:selected").appendTo(".all_role")
    });

    var ids = $.map($(".select_role option"), function (item) {
        return item.value
    })

    $.each($(".all_role option"), function (index, item) {
        if($.inArray(item.value,ids) >= 0 ){
            $(item).remove();
        }
    })

})