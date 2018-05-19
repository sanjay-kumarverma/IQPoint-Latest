<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
    <head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			<title>iQ Point</title>
			<link href="css/jquery.splitter.css" rel="stylesheet" type="text/css" />	
			<link href="css/jstree_default/style.min.css" rel="stylesheet" type="text/css" />
<!-- 			<link href="css/Login.css" rel="stylesheet" type="text/css" />
			<link href="css/registration/registration.css" rel="stylesheet" type="text/css" />	 -->
					
			<script src="lib/jquery-3.1.1.min.js" type="text/javascript" charset="utf-8"></script>
			
			<script type="text/javascript" src="lib/jstree.js"></script>
			<script type="text/javascript" src="lib/jquery.splitter.js"></script> 
			<script src="lib/jquery.validate.js" type="text/javascript" charset="utf-8"></script>

            <!-- <script src="lib/circle-progress.js" type="text/javascript" charset="utf-8"></script> -->

			     
            <script type="text/javascript" src="js/welcomescript.js"></script>
            <script type="text/javascript" src="js/AlertsAndMessages.js"></script>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

    </head>
<body>
    <c:url value="/logout" var="logoutUrl" />

	 <form action="${logoutUrl}" method="post" id="logoutForm">
	    <input type="hidden" id="${_csrf.parameterName}"	name="${_csrf.parameterName}" value="${_csrf.token}" />
	 </form>


    <p>
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h4>
				Welcome : <span id="loginid">${pageContext.request.userPrincipal.name}</span> | <a href="javascript:formSubmit()"> Logout</a>
			</h4>
			
		</c:if>
      </p>

      
 <!--       <div id="progressbar" class="w3-modal w3-animate-opacity">
		    <div class="w3-modal-content w3-card-8 w3-lightblue">
		
			      <div class="w3-container w3-khaki">
			        <p>Loading data...please wait.</p>
	   	         </div>
		    </div>

        </div> -->
        
         <input type="hidden" id="userRole" />
         <input type="hidden" id="userLevelId" />
         <input type="hidden" id="userLevelName" />
        
        
		   <div id="progressbar" class="w3-modal">
		    <div class="w3-modal-content w3-card-8 w3-pale-blue">
		      <div id='progressbarMessage' class="w3-container">
		        
		      </div>
		    </div>
		  </div>   
  
		  <div id="successMessage" class="w3-modal">
		    <div class="w3-modal-content">
		      <header class="w3-container w3-green"> 
		        <span class="w3-closebtn">&times;</span>
		        <h4>Success</h4>
		      </header>
		      <div class="w3-container">
		        <p>Information was saved successfully.</p>
		      </div>
		    </div>
		  </div>  
      
      
		  <div id="warningMessage" class="w3-modal">
		    <div class="w3-modal-content">
		      <header class="w3-container w3-orange"> 
		        <span class="w3-closebtn">&times;</span>
		        <h4>Warning</h4>
		      </header>
		      <div class="w3-container">
		        <p>User with specified email id was not found. You need to register.</p>
		      </div>
		    </div>
		  </div>  
		  
		  <div id="errorMessage" class="w3-modal">
		    <div class="w3-modal-content">
		      <header class="w3-container w3-red"> 
		        <span class="w3-closebtn">&times;</span>
		        <h4>Error</h4>
		      </header>
		      <div class="w3-container">
		        <p>Updating user profile failed, please try after sometime.</p>
		      </div>
		    </div>
		  </div>  		        
      
         
      <div class="container w-container">
       

      <div id="panes">
         
	    <div id="jstree">
  			<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>	    
	    <!-- in this example the tree is populated from inline HTML -->
		    <ul>
		      <li data-jstree='{"opened":true,"selected":true}' id='root' value="">Aditya Verma (Student)

		      </li>
		      <li id="QuestionPapers">Question Papers</li>
		      <li id="Discuss">Discuss</li>
	          <li id="QuestionBank">Question Bank</li>
	          <li id="ScheduledExams">Scheduled Exams</li>
	          <li id="Management">Management
	             <ul id="managementList">
		           <li id="People">People</li>	
		           <li id="Masters">Masters
		                <ul id="masterlist">
		                   <li>Class / Level</li>
		                   <li>Subject & Topic</li>
		                   <li>Exam type</li>
		                   <li>Question type</li>
		                </ul>
		           </li>
		        </ul>
		      </li>         
		    </ul>
		  </div>
		  
		  <div id="treecontents">
		        <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; open</span>
		  </div>
       </div>
       <span id="clickData" hidden="true" title=""></span> <!-- to store tree clicked value -->
<!--       <div class="w-form">
      

      </div> -->
      
      
      
    <!--   <div class="image-crop"><img sizes="(max-width: 479px) 95vw, (max-width: 767px) 86vw, (max-width: 991px) 586px, 798px" src="images/iphone-white-mockup.png" srcset="images/iphone-white-mockup-p-500x332.png 500w, images/iphone-white-mockup-p-800x531.png 800w, images/iphone-white-mockup-p-1080x717.png 1080w, images/iphone-white-mockup-p-1600x1062.png 1600w, images/iphone-white-mockup.png 1909w">
      </div> -->

  </div> 

 <div class="pagefooter"> </div>
 
  <div id="alert" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-pale-yellow">
      <header class="w3-container w3-yellow"> 
        <span id='alertButton' onclick="document.getElementById('alert').style.display='none'" 
        class="w3-closebtn">&times;</span>
        <h6>Alert!</h6>
      </header>    
       <div id='welcomeAlertMessage' class="w3-container">
       
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
      <div id="welcomeErrorMessage" class="w3-container">
        
      </div>
    </div>
  </div>  
 
 
</body>
</html>




<!-- 	 	<form class="form-container" name='loginForm' action="<c:url value='/j_spring_security_check' />" method='POST'>
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
          <div><a href="ilsWebPages/registration/registration.html"><h5 align="center">New user ? Register with iQ Point</h5></a></div>
        </div>  -->      
       

       <!--  <div class="w-form-fail">
          <div>Oops! Something went wrong while submitting the form</div>
        </div> -->


<!-- 	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>

	<c:url value="/logout" var="logoutUrl" />

	csrt for log out
	<form action="${logoutUrl}" method="post" id="logoutForm">
	  <input type="hidden"
		name="${_csrf.parameterName}"s
		value="${_csrf.token}" />
	</form>

	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} | <a
				href="javascript:formSubmit()"> Logout</a>
		</h2>
	</c:if>
     <div >
         <table id="datadump">
             <thead>
                <th>Id</th><th>First Name</th><th>Last Name</th><th>User Name</th><th>Password</th><th>Role</th><th>Address</th><th>City</th><th>State</th><th>Zip</th>
             </thead>
             <tbody>
             </tbody>
         
         
         </table>
     
          <input type="button" value="Show Data" id="getdata" />
     </div>  -->