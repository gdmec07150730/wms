<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-vlidate/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/jquery-form/jquery.form.min.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {

            $("#editForm").submit(function () {

                var isOk = $("#editForm").validate({
                    rules: {
                        name: "required",
                        sn: "required"
                    },
                    messages: {
                        name: "仓库名称不能为空!",
                        sn: "仓库代码不能为空"
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
                            window.location.href = "/depot/query.do";
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

    </script>

</head>
<body>
<form name="editForm" action="/depot/saveOrUpdate.do" method="post" id="editForm">
    <input type="hidden" name="id" value="${depot.id}"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">仓库编辑</span>
            <div id="page_close">
                <a>
                    <img src="images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">仓库名字</td>
                    <td class="ui_text_lt">
                        <input name="name" value="${depot.name}" class="ui_input_txt02"/>
                    </td>
                </tr>

                <tr>
                    <td class="ui_text_rt" width="140">仓库地址</td>
                    <td class="ui_text_lt">
                        <input name="location" value="${depot.location}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input type="submit" value="确定保存" class="ui_input_btn01"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    </form>
</body>
</html>