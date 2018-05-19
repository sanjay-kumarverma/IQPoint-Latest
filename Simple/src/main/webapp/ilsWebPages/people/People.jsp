<!DOCTYPE html>
<html>
<head>
		<title>Scheduled Exams</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 		<link rel="stylesheet" href="css/w3.css">	
		<link rel="stylesheet" href="css/w3-theme-indigo.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
		<script type="text/javascript" src="js/people.js"></script>
		<script type="text/javascript" src="js/AlertsAndMessages.js"></script>
</head>
<body>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
<div class="w3-container"> 
	       <!-- <div id='title' class="w3-panel  w3-light-grey "> -->
	       <div class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">
				     <div class="w3-cell w3-container w3-cell-middle"><button class="w3-button w3-light-grey w3-xlarge" onclick="w3_open()">&#9776;</button></div><div class="w3-cell w3-container w3-cell-middle"><h2 class="w3-left w3-text-blue"  id="se-title">People</h2></div>
                
					<table id="se-searchParaTable" class="w3-table-all w3-small w3-center w3-border">
							<tr><td colspan="2"><label class="w3-label w3-text-blue">Role :</label>
							        <select class="w3-select" id="people-role">
									</select></td>
							</tr>
							<tr>
						   	 <td colspan="2"><label class="w3-label w3-text-blue">Class/Level :</label>
						        <select class="w3-select" id="people-level" >
<!-- 											  <option value="" disabled selected>Choose Class/Level</option>
											  <option value="1">Option 1</option>
											  <option value="2">Option 2</option>
											  <option value="3">Option 3</option> -->
						       </select></td>
						     </tr>							

							<tr><td><label class="w3-label w3-text-blue">Added from :</label>
							        <input class="w3-input" type="date" id="people-dateFrom" alt="When the user was added to the system."></td>
							 <td><label class="w3-label w3-text-blue">Added to :</label>
							        <input class="w3-input" type="date" id="people-dateTo" alt="When the user was added to the system."></td></tr>
							<tr>  
							<td colspan="2"><label class="w3-label w3-text-blue">Free text :</label>
							        <input class="w3-input" type="text" id="people-freeText" alt="Enter partial name, address or any such information to search."></td>        
							 </tr>        
					 </table>
                     <div class="w3-container">
							 <p class="w3-margin">
							    <button id="people-butAssignUser" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Assign<i class="w3-margin-left fa fa-search"></i></button>
							    <button id="people-butCreateUser" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Create User<i class="w3-margin-left fa fa-search"></i></button>
							 	<button id="people-butSearchButton" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Search<i class="w3-margin-left fa fa-search"></i></button>
		                     </p> 
                     </div>
                     
              
					<div class="w3-container w3-margin" id="people-list">					
						<h3 class="w3-left w3-text-blue" id="people-ListTitle">List of people</h3>
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
      <div id="people-errorMessage" class="w3-container">
        
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
       <div id='people-alertMessage' class="w3-container">
       
      </div>
    </div>
  </div> 
  
  <!-- Modal to show student details -->
   <div id="people-viewDetails" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-padding">
      <header class="w3-container"> 
        <span id='qb-closeButton' onclick="document.getElementById('people-viewDetails').style.display='none'" 
        class="w3-closebtn">&times;</span>
        <h4>Detailed view</h4>
      </header>    
       <div id='people-detailsInfo' class="w3-container">
    
      </div>
    </div>
  </div> 
  
  
   <div id="people-createUser" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-padding">
      <header class="w3-container"> 
        <span id='qb-closeButton' onclick="document.getElementById('people-createUser').style.display='none'" 
        class="w3-closebtn">&times;</span>
        <h4>Create new user</h4>
      </header>    
       <div id='people-detailsInfo' class="w3-container">
			<table id="people-userTable" class="w3-table-all w3-small w3-center w3-border">
				<tr><td>First name</td><td><input class="w3-input" type="text" id="firstname" /></td></tr>
				<tr><td>Last name</td><td><input class="w3-input" type="text" id="lastname" /></td></tr>
				<tr><td>Email</td><td><input  type="text" class="w3-input" id="email" /></td></tr>
				<tr><td>Phone</td><td><input class="w3-input" type="text" id="phone" /></td></tr>
				<tr><td>Register as</td><td><select class="w3-select" id="people-registerAs" >
			
					</select></td></tr>
				<tr><td colspan="2" class="w3-center">
				    <span id="error_msg" class="w3-text-red"></span>
					<button id="people-butCancelCreate" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Cancel<i class="w3-margin-left fa fa-search"></i></button>
					<button id="people-butCreate" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Create<i class="w3-margin-left fa fa-search"></i></button>
				 									  
				</td></tr>
			</table>        
    
      </div>
    </div>
  </div> 
 
  
  
  
  
  
    

</body>
</html>
