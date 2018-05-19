<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			<title>iQ Point</title>
			<!-- <link href="css/Login.css" rel="stylesheet" type="text/css" /> -->
			<link href="css/registration/registration.css" rel="stylesheet" type="text/css" />			
			<script src="lib/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
			<script src="lib/jquery.validate.js" type="text/javascript" charset="utf-8"></script>
			<script src="lib/jquery.dd.js" type="text/javascript" charset="utf-8"></script>
			<script src="lib/jquery-ui-1.8.17.custom.min.js" type="text/javascript" charset="utf-8"></script>
			<script src="lib/notify.min.js" type="text/javascript" charset="utf-8"></script>			
			<script src="js/loginScript.js" type="text/javascript" charset="utf-8"></script>	
						
	</head>
<body>

      <div class="pageheader"></div>
      
      <div class="container w-container">
      
      <div class="w-form">
      
	 	<form class="form-container" name='loginForm' action="<c:url value='/j_spring_security_check' />" method='POST'>
			<div class="form-title"><h2>Login to iQ Point</h2></div>
			<div class="form-title">Name
				<input class="form-field" id="username"  name='username' placeholder="Enter your name" type="text" maxlength="256" size="25" required="required"/></div>
			<div class="form-title">Password
				<input class="form-field" id="password"  name="password" placeholder="Enter your password" required="required" type="password" maxlength="256" size="25"/></div>
			<div class="submit-container">
				<input class="submit-button" data-wait="Please wait..." name="submit" type="submit" value="Submit"/>
			</div>
		    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />  
		</form>      

         <div class="w-form-done">
          <div><a href="ilsWebPages/registration/registration.jsp"><h5 align="center">New user ? Register with iQ Point</h5></a></div>
        </div>        
       
         <div id='loginError'>
         		<c:if test="${not empty error}">
					<div class="error">${error}</div>
				</c:if>
         </div>
      </div>
      
      
      

    </div>
    <div id="pagefooter"></div>
  

</body>
</html>



  