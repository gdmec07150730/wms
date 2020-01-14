$(function () {

    //数据表格
    $("#emp_datagrid").datagrid({
        url: "/employee/list.do",
        singleSelect: true,
        toolbar: '#toolbar',
        pagination:true,
        fitColumns: true,
        columns: [[
            {field: 'id', title: '编号', width: 100},
            {field: 'name', title: '用户名', width: 100},
            {field: 'email', title: 'EMAIL', width: 100},
            {field: 'age', title: '年龄', width: 100},
            {field: 'roleNames', title: '角色', width: 100},
            {field: 'dept', title: '所属部门', width: 100,
                formatter: function (value, row, index) {
                    return value.name?value.name:"";
                }
            },
        ]],
        onRowContextMenu: function (e, index, row) {
            console.log(e);
            e.preventDefault();
            $('#mm').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        }

    });

    //高级查询  部门下拉框
    $("#dept").combobox({
        height:30,
        url:"/employee/dept.do",
        valueField:'id',
        textField:'name'
    });

})
//高级查询 load : 通常可以通过传递一些参数执行一次查询，通过调用这个方法从服务器加载新数据
function query(){
    $("#emp_datagrid").datagrid('load',{
        keyword:$("[name=keyword]").val(),
        deptId:$("#dept").val()
    });
}