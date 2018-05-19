<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			<title>iQ Point</title>
			
			<meta name="viewport" content="width=device-width, initial-scale=1">
			
<!-- 			<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">	
			<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-indigo.css"> 
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">  -->
            
<!-- 			<link rel="stylesheet" href="css/w3.css">	
			<link rel="stylesheet" href="css/w3-theme-indigo.css"> -->
            <!-- <link rel="stylesheet" href="css/font-awesome.min.css">  -->
                       
            <script type="text/javascript" src="js/userprofile.js"></script>
            <style>
			   .w3-btn {width:150px;}
			   
			:disabled {
			    background: #dddddd;
			}			   
			   
			</style>
			
	<script>
		function submitUserProfile() {
			document.getElementById("userProfileForm").submit();
		}
		
		
	</script>  			
	</head>
<body>

   
   <div class="w3-container w3-theme-l4"> 
        
       <div class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">
                        
   						<div class="w3-cell w3-container w3-cell-middle"><button class="w3-button w3-light-grey w3-xlarge" onclick="w3_open()">&#9776;</button></div><div class="w3-cell w3-container w3-cell-middle"><h2 class="w3-left"> User Profile </h2></div>
   						<!-- <span class="fa fa-edit w3-right" style="font-size:24px;color:blue"></span> -->
						 <!-- <p><button class="w3-btn w3-white w3-border w3-border-blue w3-round-xlarge w3-right" id="editProfile">Edit</button></p> -->
						 <div class="w3-container w3-right w3-margin">
						 	<button id="pro-butloadphoto" class="w3-button w3-yellow w3-hover-red"><i class="fa fa-camera"></i></button>
						 	<button class="w3-button w3-yellow w3-hover-red" id="editProfile"><i class="fa fa-edit"></i></button>
						 </div>
   
			 <form action="#" id="userProfileForm" >
                         
                        <!-- <input type="hidden" id="userId" /> -->
                        <input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
                        <input type="hidden" id="email" name="email"/>
						<div class="w3-layout-container w3-panel"> 
						        <!--  showing photo thumbnail -->
								<div class="w3-container">
								  <img id="up-userPhoto" src="files/DefaultUser.jpg" style="width:20%;cursor:zoom-in"  onclick="document.getElementById('modal01').style.display='block'">
								  
								  <div id="modal01" class="w3-modal" onclick="this.style.display='none'">
								    <span class="w3-closebtn w3-hover-red w3-container w3-padding-16 w3-display-topright">&times;</span>
								    <div class="w3-modal-content w3-animate-zoom">
								       <img id="up-zoom-userPhoto" src="" style="width:50%">
								    </div>
								  </div>
								</div>	
																    
									    
									<div class="w3-row w3-section w3-layout-cell w3-panel" style="width:35%">
									  <!-- <div class="w3-col" style="width:50px"></div> -->
									    
									    <div class="w3-rest">
									      <label class="w3-label w3-text-blue">First Name</label>
									      <input class="w3-input w3-border" name="firstName" id="firstName" type="text" placeholder="First Name">
									    </div>
									</div>
									
									<div class="w3-row w3-section w3-layout-cell w3-panel" style="width:35%">
									  
									    <div class="w3-rest">
									      <label class="w3-label w3-text-blue">Last Name</label>
									      <input class="w3-input w3-border" name="lastName" id="lastName" type="text" placeholder="Last Name">
									    </div>
									</div>
						</div>
						<div class="w3-layout-container w3-panel"> 
								<div class="w3-row w3-section w3-layout-cell w3-panel">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <span name="display_email" id="display_email"></span>
								    </div>
								</div>
								
								<div class="w3-row w3-section w3-layout-cell w3-panel">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Phone</label>
								      <input class="w3-input w3-border" name="phone" id="phone" type="text" placeholder="Phone">
								    </div>
								</div>
					   </div>	
						<div class="w3-layout-container w3-panel"> 
								<div class="w3-row w3-section w3-layout-cell w3-panel">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Street</label>
								      <input class="w3-input w3-border" name="street" id="street" type="text" placeholder="Street">
								    </div>
								</div>
								
								<div class="w3-row w3-section w3-layout-cell w3-panel">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">City</label>
								      <input class="w3-input w3-border" name="city" id="city" type="text" placeholder="City">
								    </div>
								</div>
					   </div>
					   <div class="w3-layout-container w3-panel"> 
								<div class="w3-row w3-section w3-layout-cell w3-panel">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">State</label>
								      <input class="w3-input w3-border" name="state" id="state" type="text" placeholder="State">
								    </div>
								</div>
								
								<div class="w3-row w3-section w3-layout-cell w3-panel">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Zip</label>
								      <input class="w3-input w3-border" name="zip" id="zip" type="text" placeholder="Zip">
								    </div>
								</div>
					   </div>	
					   
					   <div class="w3-layout-container w3-panel"> 
						        <!--  showing photo thumbnail -->
								<div class="w3-container">
								  <img src="files/DefaultUser.jpg" style="width:20%;cursor:zoom-in"  onclick="document.getElementById('modal02').style.display='block'">
								
								  <div id="modal02" class="w3-modal" onclick="this.style.display='none'">
								    <span class="w3-closebtn w3-hover-red w3-container w3-padding-16 w3-display-topright">&times;</span>
								    <div class="w3-modal-content w3-animate-zoom">
								       <img src="files/DefaultUser.jpg" style="width:50%">
								    </div>
								  </div>
								</div>					   
					   
					   
								<div class="w3-row w3-section w3-layout-cell w3-panel" style="width:25%">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Father</label>
								      <input class="w3-input w3-border" name="fathername" id="fathername" type="text" placeholder="Father Name">
								    </div>
								</div>
								
								<div class="w3-row w3-section w3-layout-cell w3-panel" style="width:25%">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Phone</label>
								      <input class="w3-input w3-border" name="fatherphone" id="fatherphone" type="text" placeholder="Father Phone">
								    </div>
								</div>
								
								<div class="w3-row w3-section w3-layout-cell w3-panel" style="width:25%">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Email</label>
								      <input class="w3-input w3-border" name="fatheremail" id="fatheremail" type="text" placeholder="Father Email">
								    </div>
								</div>								
					   </div>	
					   
					   <div class="w3-layout-container w3-panel"> 
					   
						        <!--  showing photo thumbnail -->
								<div class="w3-container">
								  <img src="files/DefaultUser.jpg" style="width:20%;cursor:zoom-in"  onclick="document.getElementById('modal03').style.display='block'">
								
								  <div id="modal03" class="w3-modal" onclick="this.style.display='none'">
								    <span class="w3-closebtn w3-hover-red w3-container w3-padding-16 w3-display-topright">&times;</span>
								    <div class="w3-modal-content w3-animate-zoom">
								       <img src="files/DefaultUser.jpg" style="width:50%">
								    </div>
								  </div>
								</div>
													   
								<div class="w3-row w3-section w3-layout-cell w3-panel" style="width:25%">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Mother</label>
								      <input class="w3-input w3-border" name="mothername" id="mothername" type="text" placeholder="Mother Name">
								    </div>
								</div>
								
								<div class="w3-row w3-section w3-layout-cell w3-panel" style="width:25%">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Phone</label>
								      <input class="w3-input w3-border" name="motherphone" id="motherphone" type="text" placeholder="Mother Phone">
								    </div>
								</div>
								
								<div class="w3-row w3-section w3-layout-cell w3-panel" style="width:25%">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Email</label>
								      <input class="w3-input w3-border" name="motheremail" id="motheremail" type="text" placeholder="Mother Email">
								    </div>
								</div>								
					   </div>	
					   <div class="w3-layout-container w3-panel"> 
								<div class="w3-row w3-section w3-layout-cell w3-panel">
								  <div class="w3-col" style="width:40px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Level</label>
								      <!-- <input class="w3-input w3-border" name="level" id="level" type="text" placeholder="Level"> -->
								        <select class="w3-select" id="level" name="level">
											  <option value="" disabled selected>Choose Level</option>
								       </select>								      
								    </div>
								</div>					   
								<div class="w3-row w3-section w3-layout-cell w3-panel">
								  <div class="w3-col" style="width:40px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Studies in</label>
								      <!-- <input class="w3-input w3-border" name="studiesin" id="studiesin" type="text" placeholder="Studies in"> -->
								        <select class="w3-select" id="studiesin" name="studiesin">
											  <option value="" disabled selected>Choose class</option>
								       </select>								      
								    </div>
								</div>
								
								<div class="w3-row w3-section w3-layout-cell w3-panel">
								  <div class="w3-col" style="width:20px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Section</label>
								      <input class="w3-input w3-border" name="section" id="section" type="text" placeholder="Section">
								    </div>
								</div>
					   </div>	
					   <div class="w3-layout-container w3-panel"> 
								<div class="w3-row w3-section w3-layout-cell w3-panel">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Admission number</label>
								      <input class="w3-input w3-border" name="admission-no" id="admission-no" type="text" placeholder="Admission number">
								    </div>
								</div>
								
								<div class="w3-row w3-section w3-layout-cell w3-panel">
								  <div class="w3-col" style="width:50px"></div>
								    <div class="w3-rest">
								      <label class="w3-label w3-text-blue">Blood group</label>
								      <input class="w3-input w3-border" name="bloodgroup" id="bloodgroup" type="text" placeholder="Blood group">
								    </div>
								</div>
					   </div>
					   
					   <div class="w3-layout-container w3-panel w3-center"> 
							<div class="w3-row w3-section w3-panel">
							  <div class="w3-col" style="width:50px"></div>
							    <div class="w3-rest">
							      <label class="w3-label w3-text-blue">Comments</label>
							      <textarea class="w3-input w3-border" name="otherinfo" id="otherinfo" rows="5" cols="30" maxlength="500" placeholder="Other information" ></textarea>
							    </div>
							</div>
							
<!-- 							<button class="w3-btn-block w3-section w3-blue w3-ripple w3-padding w3-panel" onclick="javascript:submitUserProfile()">Save</button> -->
							<input id='UpdateProfile' value="Update Profile" type="submit" class="w3-button w3-yellow w3-hover-red w3-cell" />
			           </div>
					   <div class="w3-display-container w3-panel w3-text-blue w3-padding"> 
					      <div class="w3-display-bottomright">
							      <span id="lastUpdated"></span>
						  </div>
			           </div>			           
			</form>
       </div>
       

	</div>
    <div id="photo" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-pale-green">
      <header class="w3-container w3-green"> 
        <span onclick="document.getElementById('photo').style.display='none'" 
        class="w3-closebtn">&times;</span>
        <h5>Upload photo</h5>
      </header>     
      <div id="uploadPhoto" class="w3-container">
      
			<form id="fileUploadForm" action="#" method="post" enctype="multipart/form-data"> 
		
			   <p>
				Select photo file : <input type="file" name="uploadFile" size="50" />
			   </p>
		       <input type="hidden" id="userid" name="userid" />
			   <input type="submit" value="Upload It" />
			</form>    
        
      </div>
    </div>
  </div> 


  
  
  


</body>
</html>



