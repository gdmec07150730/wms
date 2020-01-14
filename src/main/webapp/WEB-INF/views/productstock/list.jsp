<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>PSS-货品库存管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>
    <script>

    </script>
</head>
<body>
<form id="searchForm" action="/productStock/query.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        货品名称或编号
                        <input type="text" class="ui_input_txt02" value="${qo.keyword}" name="keyword">
                        仓库
                        <select class="ui_select01 depotSelect" name="depotId">
                            <option value="-1">请选择</option>
                            <c:forEach items="${depots}" var="item">
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
                        库存阈值
                        <input type="text" class="ui_input_txt02" value="${qo.limitNumber}" name="limitNumber">
                        <script>
                            /*回显供应商和状态*/
                            $(".depotSelect option[value='${qo.depotId}']").prop("selected",true);
                            $(".brandSelect option[value='${qo.brandId}']").prop("selected",true);
                        </script>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th>仓库</th>
                        <th>货品编码</th>
                        <th>货品名称</th>
                        <th>品牌</th>
                        <th>库存数量</th>
                        <th>成本</th>
                        <th>库存汇总</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${result.datas}" var="item">
                        <tr>
                            <td>${item.depot.name}</td>
                            <td>${item.product.sn}</td>
                            <td>${item.product.name}</td>
                            <td>${item.product.brandName}</td>
                            <td>${item.storeNumber}</td>
                            <td>${item.price}</td>
                            <td>${item.amount}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <%--分页--%>
            <jsp:include page="../common/common_page.jsp"/>
        </div>
    </div>
</form>
</body>
</html>

