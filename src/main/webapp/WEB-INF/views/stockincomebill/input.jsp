<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-vlidate/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/plugins/iframeTools.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-form/jquery.form.min.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript" src="/js/system/stockincomebill.js"></script>

</head>
<body>
<form name="editForm" action="/stockIncomeBill/saveOrUpdate.do" method="post" id="editForm">
    <input type="hidden" name="id" value="${stockIncomeBill.id}"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">用户编辑</span>
            <div id="page_close">
                <a>
                    <img src="images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">订单编号</td>
                    <td class="ui_text_lt">
                        <input name="sn" class="ui_input_txt02" value="${stockIncomeBill.sn}"/>
                    </td>
                </tr>

                <tr>
                    <td class="ui_text_rt" width="140">仓库</td>
                    <td class="ui_text_lt">
                        <select name="depot.id" class="ui_select03 depotSelect">
                            <option value="-1">请选择</option>
                            <c:forEach items="${depots}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <fmt:formatDate value="${stockIncomeBill.vdate}" pattern="yyyy-MM-dd" var="fmtdate"/>
                        <input name="vdate" class="ui_input_txt02 Wdate" value="${fmtdate}"/>
                    </td>
                </tr>
                <script>
                    $(".depotSelect option[value='${stockIncomeBill.depot.id}']").prop("selected",true)
                </script>
                <tr>
                    <td class="ui_text_rt" width="140">单据明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0" style="width: auto">
                            <thead>
                            <tr>
                                <th width="10"></th>
                                <th width="200">货品</th>
                                <th width="120">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="80">金额小计</th>
                                <th width="150">备注</th>
                                <th width="160"></th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">
                            <c:if test="${empty stockIncomeBill}">
                                <tr>
                                    <td></td>
                                    <td>
                                        <input disabled="true" readonly="true" class="ui_input_txt02" tag="name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <input type="hidden" name="items[0].product.id" tag="pid"/>
                                    </td>
                                    <td><span tag="brand"></span></td>
                                    <td><input tag="costPrice" name="items[0].costPrice"
                                               class="ui_input_txt00"/></td>
                                    <td><input tag="number" name="items[0].number"
                                               class="ui_input_txt00"/></td>
                                    <td><span tag="amount"></span></td>
                                    <td><input tag="remark" name="items[0].remark"
                                               class="ui_input_txt02"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${not empty stockIncomeBill}">
                                <c:forEach items="${stockIncomeBill.items}" var="item">
                                    <tr>
                                        <td></td>
                                        <td>
                                            <input disabled="true" readonly="true" value="${item.product.name}" class="ui_input_txt02"
                                                   tag="name"/>
                                            <img src="/images/common/search.png" class="searchproduct"/>
                                            <input type="hidden" value="${item.product.id}" name="items[0].product.id" tag="pid"/>
                                        </td>
                                        <td><span tag="brand">${item.product.brandName}</span></td>
                                        <td><input tag="costPrice" name="items[0].costPrice"
                                                   class="ui_input_txt00" value="${item.costPrice}"/></td>
                                        <td><input tag="number" name="items[0].number"
                                                   class="ui_input_txt00" value="${item.number}"/></td>
                                        <td><span tag="amount">${item.amount}</span></td>
                                        <td><input tag="remark" name="items[0].remark"
                                                   class="ui_input_txt02" value="${item.remark}"/></td>
                                        <td>
                                            <a href="javascript:;" class="removeItem">删除明细</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                        </table>
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
