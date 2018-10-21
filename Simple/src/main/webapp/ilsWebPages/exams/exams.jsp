<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			<title>iQ Point</title>
			
			<meta name="viewport" content="width=device-width, initial-scale=1">
			
<!-- 			<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">	
			<link rel="stylesheet" href="http://www.w3schools.com/lib/w3-theme-indigo.css"> -->
	        <link rel="stylesheet" href="css/w3.css">	
			<link rel="stylesheet" href="css/w3-theme-indigo.css">				
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
			<script type="text/javascript" src="js/examscript.js"></script>
			<script type="text/javascript" src="js/AlertsAndMessages.js"></script>
			
	</head>
 <body>
 
  
   <div class="w3-container w3-theme-l4" style="height:100%"> 
        
       <div id='accordian' class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">
       
	       <div id='title' class="w3-panel  w3-light-grey">
				<!-- <div class="w3-cell-row"> -->
				       <div id='navButton' class=" w3-container w3-cell w3-cell-middle"></div>
				       <div class="w3-cell w3-container w3-cell-middle"><h2 class="w3-left w3-text-blue" id="examSubject"></h2></div>
				       
				<!-- </div> -->
	       </div>
	       <div><h2></h2><h2></h2><h2></h2><h2></h2><h2></h2><h2></h2></div>
		</div>	
		   
   </div>
   
   <!-- Test Section -->
   
  <div id="testSection" class="w3-modal">
    <div class="w3-modal-content w3-card-8 w3-animate-zoosm" style="max-width:900px">
      <div class="w3-display-container "><div class="w3-yellow"><b>Paper :</b><span id="rs-paperName"></span></div>
                                                  <div class="w3-right w3-blue"><span id="ts-questionPointer"><div></div></span><span>&nbsp;&nbsp;</span><span id="ts-timeLeft"><b>Time left</b> : XX minutes</span></div>
                                                  
      </div>
       
<!--       <form id='questionForm' class="w3-container" action="#" method="post" accept-charset=utf-8> -->
        <div class="w3-section">
              <div id="questionSection">
                    <input type="hidden" id="userId" name="userId" />
			        <input type="hidden" id="questionPaperId" name="questionPaperId" />
			        <input type="hidden" id="questionId" name="questionId" />
			        <input type="hidden" id="questionTypeId" name="questionTypeId" />
              </div>
   	         <div class="w3-center">
   	            <button id="prevQuestion" class="w3-button w3-blue" >Prev</button>
                <button id="nextQuestion" class="w3-button w3-green" >Next</button>
                <button id="saveQuestion" class="w3-button w3-yellow" >Save</button>
             </div>   

			 					<div id="exam-PhotoDiv" class="w3-container">
			 					  <div id="exam-dispPhoto">
<!-- 								  <span>Image attached, click to zoom : </span><img id="questionPhoto-tag" src="" style="width:5%;cursor:zoom-in"  onclick="document.getElementById('questionPhoto').style.display='block'">
								  
								  <div id="questionPhoto" class="w3-modal" onclick="this.style.display='none'">
								    <span class="w3-closebtn w3-hover-red w3-container w3-padding-16 w3-display-topright">&times;</span>
								    <div class="w3-modal-content w3-animate-zoom">
								       <img id="exam-zoom-questionPhoto" src="" style="width:70%">
								    </div>
								  </div> -->
								  </div>
								</div>	  
                  
        </div>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />  
<!--       </form> -->

      <div class="w3-container w3-border-top w3-padding-16 w3-light-grey">
     	         <div class="w3-center"> 
		     	        <button id="ts-finishTest" class="w3-button w3-blue w3-hover-yellow" >Finish Test</button>
		                <button id="TestMap" class="w3-button w3-blue w3-hover-yellow" >Questions Map</button>
		                <button id="Help" class="w3-button w3-blue w3-hover-yellow" >Help</button>
                </div>     
      
<!--         <div class="w3-container w3-cell"><span  id="ts-finishTest" class="w3-blue w3-hover-yellow ">Finish Test</span></div>
                <div class="w3-container w3-cell"><span>&nbsp;&nbsp;</span></div>
        <div class="w3-container w3-cell"> <span  id="TestMap" class="w3-blue w3-hover-yellow ">Test Map</span></div>
        <div class="w3-container w3-cell"><span>&nbsp;&nbsp;</span></div>
        <div class="w3-container w3-cell"> <span  id="Help" class="w3-blue w3-hover-yellow ">Help</span></div> -->
      </div>

    </div> 
    </div> 
    
  <div id="rulesSection" class="w3-modal">
		    <div class="w3-modal-content w3-card-8 w3-animate-zoom" style="width:60%">
				     <div class="w3-container">
						      <div class="w3-yellow"><b>Paper :</b><span id="ts-paperName"></span></div>
						      <h4>Read the following carefully before you take the exam.</h4>
						      <p>1. All the questions are compulsory to be attempted by student.</p>
						      <p>2. Once test is started it cannot be cancelled. You can only finish test by clicking submit button.</p>
						      <p>3. Once you submit the test, marks for only attempted questions will be awarded. Result will be displayed immediately.</p>
						      <p>4. For more information you can click on help link.</p>
						      <p>5. You can use previous and next buttons to move from one question to other, your answers will be preserved.</p>
						      <p>6. There are XXX questions in this question paper.</p>
						      <p>7. Duration of this test is <span id="rs-duration"></span> minutes</p>
						      <p>8. You must score <span id="rs-passPercent"></span> % to clear the exam</p>
						
						      <div class="w3-container w3-border-top w3-padding-8 w3-light-grey">
						        <div class="w3-blue w3-hover-yellow w3-container w3-cell"><span  id="cancelTest" >Take test later</span></div>
						        <div class="w3-container w3-cell"><span>&nbsp;&nbsp;&nbsp;</span></div>
						        <div class="w3-blue w3-hover-red w3-container w3-cell"> <span  id="moveToTest" >Proceed to test</span></div>
						      </div>
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
 
    <div id="progressbar" class="w3-modal">
    <div class="w3-modal-content w3-card-8  w3-pale-blue" >
      <div id='progressbarMessage' class="w3-container">
        
      </div>
    </div>
  </div> 
  
   <div id="warningMessageWithButtons" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-pale-amber">
	      <header class="w3-container w3-amber"> 
	        <h6>Warning!</h6>
	      </header>    
	       <div id="warningMessage" class="w3-container">
	      </div>
	      <div class="w3-section w3-padding-8">
		      <div class="w3-center w3-container">
			        <button id='warningCancelButton' class=" w3-button w3-blue w3-hover-red w3-cell">Cancel</button>      
		            <span >&nbsp;&nbsp;&nbsp;</span>
		            <button id='warningOkButton' class="w3-button w3-blue w3-hover-red w3-cell">Ok</button>      
		     </div>
	     </div>
    </div>
  </div> 
  
  
<div id="candidateScore" class="w3-modal">
  <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-pale-amber">
	      <header class="w3-container w3-amber"> 
	        <h6>Your score</h6>
	      </header>
	  	<div class="w3-container w3-margin">
			<div class="w3-light-grey">
			  		<div id="maxBar" class="w3-container w3-blue w3-center" style="width:100%">100%</div>
			</div>
			<div><p></p></div>
			<div class="w3-light-grey">
			  <div id="candidateScoreBar" class="w3-container w3-green w3-center" style="width:0%">0%</div>
			</div>
			<br>
			<div class="w3-center w3-container w3-margin">
			    <button class="w3-button w3-yellow w3-hover-red" id="seenScore">Finish</button> 
			</div>
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
  
  <div id="questionPaper" class="w3-modal">
     <div class="w3-modal-content w3-animate-zoom w3-card-8">
     	      <div id="dispQuestionPaper" class="w3-padding" style="width:100%">
	          </div>
  		      <div class="w3-center w3-container w3-padding">
		            <button id='closeQuestionPaper' class="w3-button w3-blue w3-hover-red w3-cell">Close</button>      
		     </div>
	</div>
  </div> 

</body>
</html>




