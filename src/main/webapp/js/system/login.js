
function submitFrom(){
	$("#submitForm").ajaxSubmit(function (data) {
		if (data.success){
			window.location.href="/main.do";
		}else{
			$("#login_err").html(data.msg);
		}
	});
}
$(document).ready(function() {
	$("#login_sub").click(function() {
		submitFrom();
	});
	$('#pwd').keypress(enterLogin).keydown(enterLogin);
});

function enterLogin(e) { // 传入 event
	var e = e || window.event;
	if (e.keyCode == 13) {
		submitFrom();
	}
}