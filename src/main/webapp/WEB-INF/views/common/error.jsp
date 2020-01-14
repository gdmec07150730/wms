<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>WMS</title>
<link href="/style/error_css.css" rel="stylesheet" type="text/css" />
</head>
<body>

	<div id="login_center">
		<div id="login_area">
			<div id="login_box">
				<div id="login_form">
					<H2>出错啦！</H2>
					<span>执行过程中发生了异常：</span>
					<span class="error">${ex.message}</span>
					<span>请联系李嘉豪解决！</span>

				</div>
			</div>
		</div>
	</div>
</body>
</html>