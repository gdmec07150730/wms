<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>WMS-采购报表管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>
    <script>
        $(function () {
            $("[name=beginDate]").click(function () {
                WdatePicker({
                    maxDate:$("[name=endDate]").val()
                })
            });
            $("[name=endDate]").click(function () {
                WdatePicker({
                    minDate:$("[name=beginDate]").val()
                })
            });
        })
    </script>
</head>
<body>
<form id="searchForm" action="/chart/order.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                   <div id="box_center">
                        业务时间
                        <fmt:formatDate value="${qo.beginDate}" pattern="yyyy-MM-dd" var="fbeginDate"/>
                        <fmt:formatDate value="${qo.endDate}" pattern="yyyy-MM-dd" var="fendDate"/>
                        <input type="text" value="${fbeginDate}" class="ui_input_txt02 Wdate" name="beginDate" /> ~
                        <input type="text" value="${fendDate}" class="ui_input_txt02 Wdate" name="endDate" />
                       货品<input type="text" name="keyword" class="ui_input_txt02">
                       供应商
                        <select class="ui_select01 supplierSelect" name="supplierId">
                            <option value="-1">请选择</option>
                           <c:forEach items="${suppliers}" var="item">
                               <option value="${item.id}">${item.name}</option>
                           </c:forEach>
                        </select>
                       品牌
                        <select id="brand" class="ui_select01 brandSelect" name="brandId">
                            <option value="-1">请选择</option>
                            <c:forEach items="${brands}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                       分组
                        <select class="ui_select01 groupTypeSelect" name="groupType">
                            <c:forEach items="${groupTypes}" var="item">
                                <option value="${item.key}">${item.value}</option>
                            </c:forEach>
                        </select>

                        <script>
                            /*回显供应商和品牌*/
                            $(".supplierSelect option[value='${qo.supplierId}']").prop("selected",true);
                            $(".brandSelect option[value='${qo.brandId}']").prop("selected",true);
                            $(".groupTypeSelect option[value='${qo.groupType}']").prop("selected",true);
                        </script>
                    </div>
                    <div id="box_bottom">
                        <input type="submit" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th>分组类型</th>
                        <th>采购总数量</th>
                        <th>采购总金额</th>

                    </tr>
                    <tbody>
                    <c:forEach items="${datas}" var="item">
                    <tr>
                        <td>${item.groupByType}</td>
                        <td>${item.totalNumber}</td>
                        <td>${item.totalAmount}</td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</form>
</body>
</html>

