<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
	<head>
			<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> -->
			<meta name="viewport" content="width=device-width, initial-scale=1">
			<title>iQ Point</title>
			
	 
<!--             <link href="../../css/Login.css" rel="stylesheet" type="text/css" />
			<link href="../../css/registration/registration.css" rel="stylesheet" type="text/css" />
			<link rel="stylesheet" href="../../css/ui-lightness/jquery-ui-1.8.17.custom.css" type="text/css" media="screen" charset="utf-8"/>
			
			<script src="../../lib/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
 		    <script src="../../lib/jquery.dd.js" type="text/javascript" charset="utf-8"></script>
			<script src="../../lib/jquery-ui-1.8.17.custom.min.js" type="text/javascript" charset="utf-8"></script>
			<script src="../../lib/jquery.validate.js" type="text/javascript" charset="utf-8"></script>			
			<script src="../../lib/notify.min.js" type="text/javascript" charset="utf-8"></script>
			<script src="../../js/registrationScript.js" type="text/javascript" charset="utf-8"></script>	 -->
			
		
			<script src="../../lib/jquery-3.1.1.min.js" type="text/javascript"></script>	
			<script src="../../lib/jquery.validate.js" type="text/javascript" charset="utf-8"></script>					
			<link rel="stylesheet" href="https://www.w3schools.com/w3css/3/w3.css">
			<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
			<script src="../../js/registrationScript.js" type="text/javascript"></script>
			<script type="text/javascript" src="../../js/AlertsAndMessages.js"></script>		

	
			
				

	</head>
<body>

  
<div class="w3-container w3-cell-row" >
           <div class="w3-container w3-cell" ></div>
	       <div id='title' class="w3-panel w3-light-grey w3-cell" style="width:50%">
				     <h2 class="w3-left w3-text-blue"  id="se-title">Register with IQ Point</h2>
				 <form id="registrationForm" action="" method="post">  
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
					<table id="se-searchParaTable" class="w3-table-all w3-small w3-center w3-border">
						<tr><td>First name</td><td><input class="w3-input" type="text" id="firstname" name="firstname" placeholder="Enter your first name"/></td></tr>
						<tr><td>Last name</td><td><input class="w3-input" type="text" id="lastname" name="lastname" placeholder="Enter your last name"/></td></tr>
						<tr><td>Email</td><td><input  type="text" class="w3-input" id="email" name="email" placeholder="Enter your valid emal id"/></td></tr>
						<tr><td>Password</td><td><input class="w3-input" type="password" id="password" name="password" placeholder="Enter your password"/></td></tr>
						<tr><td>Confirm password</td><td><input class="w3-input" type="password" name="confirmPassword" id="confirmPassword" placeholder="Reenter your password"/></td></tr>
						<tr><td>Register as</td><td><select class="w3-select" id="registerAs" name="registerAs">
								<option value=""  >--Select--</option> 
				      			<option value="ROLE_STUDENT" >Student</option>
<!--  				      			<option value="ROLE_TEACHER" >Teacher</option>
				      			<option value="ROLE_ORGANIZATION">Organization</option>	 -->			
							</select></td></tr>
						<tr><td colspan="2" class="w3-center"><input id='registerNow' value="Register Now" type="submit" class="w3-button w3-blue w3-hover-red w3-cell" /></td></tr>
					</table>
					</form>
					<p></p>
					<div id="clickToLogin" class="w3-center"><a href="../../Login.jsp">Go to login page</a></div>
					<p></p>	
			</div>
			<div class="w3-container w3-cell"></div>
</div>
  
  <!--     <div class="container w-container">

      
      <div class="w-form" >
	 	<form class="form-container" action="#" method="post" id="registrationForm">
			<div class="form-title"><h2>Register with iQ Point</h2></div>
			<div class="form-title">First name
				<input class="form-field" type="text" name="firstname" id="firstname" placeholder="Enter your first name"/></div>
			<div class="form-title">Last name
				<input class="form-field" type="text" name="lastname" id="lastname" placeholder="Enter your last name"/></div>				
			<div class="form-title">Email
				<input class="form-field" type="text" name="email" id="email" placeholder="Enter your valid email id"/></div>
			<div class="form-title">Password
				<input class="form-field" type="password" name="password" id="password" placeholder="Enter password for iQ Point"/></div>	
			<div class="form-title">Confirm Password
				<input class="form-field" type="password" name="confirmPassword" id="confirmPassword" placeholder="Re-enter password to confirm"/></div>
			<div class="form-title">Register as 
				 <select name="registerAs" class="form-field" id="registerAs" name="registerAs" >
				 	  <option value=""  data-description="select how you would use iQ Point" data-image="../../images/msdropdown/icons/icon_email.gif">--Select--</option> 
				      <option value="ROLE_STUDENT" data-description="Use iQ Point as student" data-image="../images/student.png">Student</option>
				      <option value="ROLE_TEACHER" data-description="Use iQ Point as teacher" data-image="../images/teacher.png">Teacher</option>
				      <option value="ROLE_ORGANIZATION" data-description="Use iQ Point as organization" data-image="../images/organization.png" name="cd">Organization</option>
    			</select></div>													
			<div class="submit-container">
				<input class="submit-button" type="submit" value="Submit" id="registerUser" />
			</div> -->
 
		   		
<!-- 		</form>
		  
		  	
		  <div id="clickToLogin" class="w3-center"><a href="../../Login.jsp">Click here to login</a></div>	
      </div> -->
      
      
<!--    <div id="reg-dialog" title="Are you sure ?">
    <p>Is information provided correct ?</p>
    <p>Ensure your email id is accurate to receive various notifications.</p>
  </div>  -->
  
  <div id="reg-dialog" class="w3-modal" >
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-pale-amber">
	      <header class="w3-container w3-amber"> 
	        <h6>Are you sure ?</h6>
	      </header>    
		    <p>Is information provided correct ?</p>
		    <p>Ensure your email id is accurate to receive various notifications.</p>
		      <div class="w3-center w3-container w3-padding">
		            <button id='reg-YesButton' class="w3-button w3-blue w3-hover-red w3-cell">Yes continue</button>
		            <button id='reg-CancelButton' class="w3-button w3-blue w3-hover-red w3-cell">Cancel</button>       
		     </div>
    </div>
  </div> 
  
  <div id="error" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-pale-red">
      <header class="w3-container w3-red"> 
        <span onclick="document.getElementById('error').style.display='none'" 
        class="w3-closebtn">&times;</span>
        <h5>Error!</h5>
      </header>     
      <div id="reg-errorMessage" class="w3-container">
        
      </div>
    </div>
  </div> 
  
  <div id="alert" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-pale-yellow">
      <header class="w3-container w3-yellow"> 
        <span id='alertButton' onclick="document.getElementById('alert').style.display='none'" 
        class="w3-closebtn">&times;</span>
        <h6>Alert!</h6>
      </header>    
       <div id='reg-alertMessage' class="w3-container">
       
      </div>
    </div>
  </div>   
  
<!--       <div id="growl"></div>
      <div id="registrationStatus"></div>      
  
      

    </div>
    
    <div class='pagefooter'></div> -->



</body>
</html>



