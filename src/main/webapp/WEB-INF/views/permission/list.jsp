
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/jquery-form/jquery.form.min.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            $(".btn_reload").click(function () {

                var url = $(this).data("url");

                $.dialog({
                    title:"温馨提示",
                    content:"重新加载需要很长时间,确定加载吗?",
                    icon:"face-smile",
                    ok: function () {
                        //初始化一个提示窗口  loading....
                        var dg = $.dialog({title:"温馨提示"});
                        $.get(url, function (data) {
                            if (data.success){
                                dg.content(data.msg).button({
                                    name:"确定",
                                    callback: function () {
                                        window.location.reload();
                                    }
                                });
                            }else{
                                dg.content(data.msg).button({
                                    name:"确定",
                                    callback:true
                                });
                            }
                        })
                    },
                    cancel:true
                });

            });
        })
    </script>
    <title>PSS-权限管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>
</head>
<body>
<form id="searchForm" action="/permission/query.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>

                    <div id="box_bottom">
                        <input type="button" value="加载权限" class="ui_input_btn01 btn_reload" data-url="/permission/reload.do"/>
                        <input type="button" value="批量删除" class="ui_input_btn01 btn_batchDelete" data-url="/permission/batchDelete.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" /></th>
                        <th>编号</th>
                        <th>权限名称</th>
                        <th>权限表达式</th>
                        <th></th>
                    </tr>
                    <tbody>

                    <c:forEach items="${result.datas}" var="item">
                    <tr>
                        <td><input type="checkbox" name="IDCheck" class="acb" data-id="${item.id}" /></td>
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>${item.expression}</td>
                        <td>
                            <a href="javascript:;" class="btn_delete" data-url="/permission/delete.do?id=${item.id}">删除</a>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <jsp:include page="../common/common_page.jsp"/>
        </div>
    </div>
</form>
</body>
</html>
