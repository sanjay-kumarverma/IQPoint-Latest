<!DOCTYPE html>
<html>
<head>
		<title>Question Papers</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 		<link rel="stylesheet" href="css/w3.css">	
		<link rel="stylesheet" href="css/w3-theme-indigo.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
		<script type="text/javascript" src="js/questionpaper.js"></script>
		<script type="text/javascript" src="js/AlertsAndMessages.js"></script>
</head>
<body>

    <div class="w3-container w3-theme-l4" style="height:100%" > 
	       
	      <div class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">
	       <!-- <div id='title' class="w3-panel  w3-light-grey "> -->
	           
				     <div class="w3-cell w3-container w3-cell-middle"><button class="w3-button w3-light-grey w3-xlarge" onclick="w3_open()">&#9776;</button></div><div class="w3-cell w3-container w3-cell-middle"><h2 class="w3-left w3-text-blue"  id="examSubject">Question Papers</h2></div>
                
					<table id="attemptsTable" class="w3-table-all w3-small w3-center w3-border">
							<tr><td><label class="w3-label w3-text-blue">Class/Level :</label>
							        <select class="w3-select" id="qps-level">
									  <option value="" disabled selected>Choose your</option>
									  <option value="1">Option 1</option>
									  <option value="2">Option 2</option>
									  <option value="3">Option 3</option>
									</select></td>
							
							 <td><label class="w3-label w3-text-blue">Subject :</label>
							        <select class="w3-select" id="qps-subject">
									  <option value="" disabled selected>Choose your option</option>
									  <option value="1">Option 1</option>
									  <option value="2">Option 2</option>
									  <option value="3">Option 3</option>
									</select></td>
							</tr>
 
							<tr>        
							<td colspan="2"><label class="w3-label w3-text-blue">Paper type :</label>
							        <select class="w3-select" id="qps-paperType">
									  <option value="" disabled selected>Choose your option</option>
									  <option value="1">Class Test</option>
									  <option value="2">Monthly Assessment</option>
									  <option value="3">Quaterly Assessment</option>
									</select></td> 
							 </tr> 
							<tr><td><label class="w3-label w3-text-blue">Date from :</label>
							        <input class="w3-input" type="date" id="qps-dateFrom"></td>
							 <td><label class="w3-label w3-text-blue">Date to :</label>
							        <input class="w3-input" type="date" id="qps-dateTo"></td></tr>
							<tr>  
							<td colspan="2"><label class="w3-label w3-text-blue">Free text :</label>
							        <input class="w3-input" type="text" id="qps-freeText"></td>        
							 </tr>        
					 </table>
                     <div class="w3-container w3-margin">
						 <!-- <p class="w3-margin"> -->
						 <button id="qps-butSearchButton" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Search<i class="w3-margin-left fa fa-search"></i></button>
	                     <!-- </p>  -->
                     </div>
                     
              
					<div class="w3-container  w3-margin" >		
					   <div id='qps-paperList' class="w3-panel w3-light-grey w3-middle">			
						     <h3 class="w3-left w3-text-blue" id="examSubject">List of question papers</h3>
						</div>
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
						      <div id="examErrorMessage" class="w3-container">
						        
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
						       <div id='alertMessage' class="w3-container">
						       
						      </div>
						    </div>
						  </div> 
				 
				 
							<!--  Question Paper -->  
							<div id="qps-questionPaper" class=" w3-modal">
								     	      <div id="dispQuestionPaper" class="w3-modal-content w3-animate-zoom w3-card-8">
											      <header class="w3-container w3-light-grey w3-center"> 
											        <!-- <span id='alertButton' onclick="document.getElementById('qps-questionPaper').style.display='none'" class="w3-closebtn">&times;</span> -->
											        <span class="w3-xlarge">Question Paper</span>
											      </header>
								     	         <table id="QpTable" class="w3-table w3-border w3-small w3-center  w3-margin-bottom">
				<!-- 				     	              <tr><td>
										                 <label class="w3-label w3-large">Paper :</label><span id="qp-paperName" class="w3-large"></span></td>
										                 <td><label class="w3-label w3-large">Pass Percent :</label><span id="qp-passPercent" class="w3-large"></span></td>
										              </tr> -->
								                 </table>				     	      
				
									      </div>
								  </div> 
								  
							  <!-- Modal to schedule exam (only for admins) -->
							   <div id="qp-scheduleExam" class="w3-modal">
							    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-padding">
							      <header class="w3-container"> 
							        <span id='qb-closeButton' onclick="document.getElementById('qp-scheduleExam').style.display='none'" 
							        class="w3-closebtn">&times;</span>
							        <h4>Schedule Exam</h4>
							      </header>    
							       <div id='qp-scheduleExamForm' class="w3-container">
										<table id="qp-scheduleExamTable" class="w3-table-all w3-small w3-center w3-border"> 
										 <tr><td colspan="2"><label class="w3-label w3-text-blue">Exam / Test Name</label></td><td colspan="2"><input type="text" class="w3-input" id="qp-examName" /></td></tr>
										 <tr><td><label class="w3-label w3-text-blue">Exam date</label></td><td><input type="date" class="w3-input" id="qp-examDate" /></td>
										 <td><label class="w3-label w3-text-blue">Exam duration (mins)</label></td><td><select class="w3-select" id="qp-examDuration">
													  <option value="" disabled selected>Choose your option</option>
													  <option value="30">30 (mins) </option>
													  <option value="60">60 (mins)</option>
													  <option value="90">90 (mins)</option>
													  <option value="120">120 (mins)</option>
													  <option value="150">150 (mins)</option>
													  <option value="180">180 (mins)</option>									  
													</select></td></tr>
										 <tr><td><label class="w3-label w3-text-blue">Exam type</label></td>
										 <td><select class="w3-select" id="qp-examType">
													  <option value="" disabled selected>Choose your option</option>
													  <option value="1">Practice test</option>
													  <option value="2">Cumulative</option>
													</select></td>
										 <td><label class="w3-label w3-text-blue">Cumulative %</label></td><td><input type="text" class="w3-input" id="qp-examCummPercent" /></td></tr>						 
										 <tr><td colspan="4" class="w3-center">
										 					<button id="qp-butScheduleExamClear" class="w3-button w3-yellow w3-small w3-hover-red">Clear</button>
										 					<button id="qp-butScheduleExamOk" class="w3-button w3-yellow w3-small w3-hover-red">Schedule</button>
										 	               <button id="qp-butScheduleExamCancel" class="w3-button w3-yellow w3-small w3-hover-red">Cancel</button></td>
										 </tr>
										</table>
							      </div>
							      <div id="qp-userList" class="w3-container">
							      
										<div id='qp-students' class="w3-panel  w3-light-grey w3-middle">
											<h3 class="w3-left w3-text-blue" id="examSubject">List of students</h3>
										</div>			      
							      </div>
							      
							    </div>
							  </div>		  				   

</body>
</html>



