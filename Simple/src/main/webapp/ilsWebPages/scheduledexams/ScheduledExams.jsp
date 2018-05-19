<!DOCTYPE html>
<html>
<head>
		<title>Scheduled Exams</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 		<link rel="stylesheet" href="css/w3.css">	
		<link rel="stylesheet" href="css/w3-theme-indigo.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
		<script type="text/javascript" src="js/scheduledexams.js"></script>
		<script type="text/javascript" src="js/AlertsAndMessages.js"></script>
</head>
<body>

<div class="w3-container"> 
	       <!-- <div id='title' class="w3-panel  w3-light-grey "> -->
	       <div class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">
				     <div class="w3-cell w3-container w3-cell-middle"><button class="w3-button w3-light-grey w3-xlarge" onclick="w3_open()">&#9776;</button></div><div class="w3-cell w3-container w3-cell-middle"><h2 class="w3-left w3-text-blue"  id="se-title">Scheduled Exams</h2></div>
                
					<table id="se-searchParaTable" class="w3-table-all w3-small w3-center w3-border">
							<tr><td><label class="w3-label w3-text-blue">Class/Level :</label>
							        <select class="w3-select" id="se-level">
									</select></td>
							
							 <td><label class="w3-label w3-text-blue">Subject :</label>
							        <select class="w3-select" id="se-subject">
									</select></td>
							</tr>
 
							<tr>        
							<td colspan="2"><label class="w3-label w3-text-blue">Exam type :</label>
							        <select class="w3-select" id="se-examType">
									  <option value="" disabled selected>Choose your option</option>
									  <option value="1">Class Test</option>
									  <option value="2">Monthly Assessment</option>
									  <option value="3">Quaterly Assessment</option>
									</select></td> 
							 </tr> 
							<tr><td><label class="w3-label w3-text-blue">Date from :</label>
							        <input class="w3-input" type="date" id="se-dateFrom"></td>
							 <td><label class="w3-label w3-text-blue">Date to :</label>
							        <input class="w3-input" type="date" id="se-dateTo"></td></tr>
							<tr>  
							<td colspan="2"><label class="w3-label w3-text-blue">Free text :</label>
							        <input class="w3-input" type="text" id="se-freeText"></td>        
							 </tr>        
					 </table>
                     <div class="w3-container">
							 <p class="w3-margin">
							 	<button id="se-butSearchButton" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Search<i class="w3-margin-left fa fa-search"></i></button>
		                     </p> 
                     </div>
                     
              
					<div class="w3-container w3-margin" id="se-examList">					
						<h3 class="w3-left w3-text-blue" id="se-ListTitle">List of scheduled exams</h3>
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
      <div id="se-errorMessage" class="w3-container">
        
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
       <div id='se-alertMessage' class="w3-container">
       
      </div>
    </div>
  </div> 
 
   <div id="answerSheet" class="w3-modal" >
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-pale-amber">
	      <header class="w3-container w3-amber"> 
	        <h6>Answer Sheet</h6>
	      </header>    
	          <div id="answers" style="width:100%">
	          </div>
		      <div class="w3-center w3-container w3-padding">
		            <button id='answerSheetOkButton' class="w3-button w3-blue w3-hover-red w3-cell">Ok</button>      
		     </div>
    </div>
  </div> 
  
  			<!--  Question Paper -->  
			<div id="qps-questionPaper" class=" w3-modal">
				     	      <div id="dispQuestionPaper" class="w3-modal-content w3-animate-zoom w3-card-8">
							      <header class="w3-container w3-light-grey"> 
							        <!-- <span id='alertButton' onclick="document.getElementById('qps-questionPaper').style.display='none'" class="w3-closebtn">&times;</span> -->
							        <span class="w3-xlarge w3-text-blue">Question Paper</span>
							      </header>
				     	         <table id="QpTable" class="w3-table w3-border w3-small w3-center  w3-margin-bottom">

				                 </table>				     	      

					      </div>
				  </div>
				  
		     <!--  Consolidated score sheet -->  
			<div id="se-scoreSheet" class=" w3-modal">
				     	      <div id="dispScoreSheet" class="w3-modal-content w3-animate-zoom w3-card-8">
							      <header class="w3-container w3-light-grey"> 
							        <span class="w3-xlarge w3-text-blue">Score Sheet</span>
							      </header>
				     	         <table id="scoreTable" class="w3-table w3-border w3-small w3-center w3-margin-bottom">

				                 </table>				     	      

					          </div>
			</div>	

</body>
</html>
