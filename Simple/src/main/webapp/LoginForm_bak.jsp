<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<title>Login Page</title>
       <link href="/css/Login.css" />
       <script src="lib/jquery-1.7.1.min.js" type="text/javascript" charset="utf-8"></script>
<!--        <script type="text/javascript" src="js/script.js"></script> -->
</head>
<body onload='document.loginForm.username.focus();'>
    <div id="backpage">
    
			<h1>Spring Security Login Form (Database Authentication)</h1>
		
			<div id="login-box">
		
				<h2>Login with Username and Password</h2>
		
				<c:if test="${not empty error}">
					<div class="error">${error}</div>
				</c:if>
				<c:if test="${not empty msg}">
					<div class="msg">${msg}</div>
				</c:if>
		
				<form name='loginForm'
				  action="<c:url value='/j_spring_security_check' />" method='POST'>
		
				<table>
					<tr>
						<td>User:</td>
						<td><input type='text' name='username'></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type='password' name='password' /></td>
					</tr>
					<tr>
						<td colspan='2'><input name="submit" type="submit"
						  value="submit" /></td>
					</tr>
				  </table>
		
				  <input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
		
				</form>
			</div>
      </div>
</body>
</html>