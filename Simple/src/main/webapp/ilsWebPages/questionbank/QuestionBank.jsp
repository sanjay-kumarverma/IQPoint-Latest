<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
 <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Question Bank</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 		<link rel="stylesheet" href="css/w3.css">	
		<link rel="stylesheet" href="css/w3-theme-indigo.css">				
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
		<script type="text/javascript" src="js/questionbank.js"></script>
		<script type="text/javascript" src="js/AlertsAndMessages.js"></script>
</head>      
<body>
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
<div class="w3-container"> 
	       <!-- <div id='title' class="w3-panel  w3-light-grey "> -->
	       <div class="w3-container w3-card-4 w3-light-grey w3-text-blue w3-margin">
				    <div class="w3-cell w3-container w3-cell-middle"><button class="w3-button w3-light-grey w3-xlarge" onclick="w3_open()">&#9776;</button></div><div class="w3-cell w3-container w3-cell-middle"> <h2 class="w3-left w3-text-blue"  id="examSubject">Question Bank </h2></div>

					<table id="attemptsTable" class="w3-table-all w3-small w3-center w3-border">
							<tr>
								   <td><label class="w3-label w3-text-blue">Class/Level :</label>
								        <select class="w3-select" id="level" name="level">
											  <option value="" disabled selected>Choose Class/Level</option>
											  <option value="1">Option 1</option>
											  <option value="2">Option 2</option>
											  <option value="3">Option 3</option>
								       </select></td>
								   <td><button id="qb-butAddLevels" class="w3-button w3-yellow w3-small w3-margin w3-hover-red"> <i class="fa fa-plus-square-o"></i></button></td>
							
							 		<td><label class="w3-label w3-text-blue">Subject :</label>
								        <select class="w3-select" id="subject" name="subject">
											  <option value="" disabled selected>Choose your option</option>
											  <option value="1">Option 1</option>
											  <option value="2">Option 2</option>
											  <option value="3">Option 3</option>
											</select>
									</td>
									 <td><button class="w3-button w3-yellow w3-small w3-margin w3-hover-red" id="qb-butAddSubjects" > <i class="fa fa-plus-square-o"></i></button></td>
							</tr>
							<tr>        
										<td colspan="3"><label class="w3-label w3-text-blue">Topic :</label>
										        <select class="w3-select" id="topic" name="topic">
												  <option value="" disabled selected>Choose topic</option>
												  <option value="1">Option 1</option>
												  <option value="2">Option 2</option>
												  <option value="3">Option 3</option>
												</select>
										</td> 
										<td><button class="w3-button w3-yellow w3-small w3-margin w3-hover-red" id="qb-butAddTopics"> <i class="fa fa-plus-square-o"></i></button></td>
							 </tr> 
							<tr>        
									<td colspan="3"><label class="w3-label w3-text-blue">Question type :</label>
									        <select class="w3-select" id="qb-questionType" name="qb-questionType">
											  <option value="" disabled selected>Choose your option</option>
											  <option value="1">Option 1</option>
											  <option value="2">Option 2</option>
											  <option value="3">Option 3</option>
											</select></td>
											<td></td>
									<!-- <td><button class="w3-button w3-yellow w3-small w3-margin w3-hover-red" id="butAddQuestionType" name="butAddQuestionType"> <i class="fa fa-plus-square-o"></i></button></td> -->
							 </tr> 
							 <tr><td colspan="2"><label class="w3-label w3-text-blue">Date from :</label>
							        <input class="w3-input" type="date" id="dateFrom" name="dateFrom"></td>
									 <td colspan="2"><label class="w3-label w3-text-blue">Date to :</label>
									   <input class="w3-input" type="date" id="dateTo" name="dateTo"></td>
							  </tr>
							<tr>  
							<td colspan="4"><label class="w3-label w3-text-blue">Free text :</label>
							        <input class="w3-input" type="text" id="freeText" name="freeText"></td>        
							 </tr>        
					 </table>
                     <div class="w3-container">
					 <p class="w3-margin">
					 <button id="butQuestionPaper" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Question Paper<i class="w3-margin-left fa fa-plus-square-o"></i></button>
					 <button id="butAddQuestion" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Add Question<i class="w3-margin-left fa fa-plus-square-o"></i></button>
					 <button id="butSearch" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Search<i class="w3-margin-left fa fa-search"></i></button>
					                      
                     </p> </div>
                     
				 <!-- <div id="questionPaper"> -->
				     <div id="questionPaper" class=" w3-card-8">
				     	      <div id="dispQuestionPaper" class="w3-padding" style="width:100%">
				     	      <h2 class="w3-left w3-text-blue" id="ques-paper">Question Paper</h2>
				     	         <table id="QpTable" class="w3-table-all w3-small w3-center w3-border w3-margin-bottom">
				     	              <tr><td>
						                 <label class="w3-label w3-text-blue">Paper name :</label><input class="w3-input" type="text" id="qp-paperName" /></td>
						                 <td><label class="w3-label w3-text-blue">Pass Percent :</label><input class="w3-input" type="text" id="qp-passPercent" name="qp-passPercent" size="10"/></td>
						              </tr>
				                 </table>				     	      
				     	         <table id="sectionTable" class="w3-table-all w3-small w3-center w3-border">
				     	              <tr><td>
						                 <label class="w3-label w3-text-blue">Section name :</label><input class="w3-input" type="text" id="qp-section" name="qp-section" /></td>
						                 <td><label class="w3-label w3-text-blue">Type of questions :</label>
						                 <select class="w3-select" id="qp-ques-type">
											  <option value="" disabled selected>Choose your option</option>
											  <option value="1">Option 1</option>
											  <option value="2">Option 2</option>
											  <option value="3">Option 3</option>
											</select>
						                 </td>
						                 <td><label class="w3-label w3-text-blue">Max Marks :</label><input class="w3-input" type="text" id="qp-maxMarks" name="qp-maxMarks" size="10"/></td>
						                 <td><button id="qp-butAddSection" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red"><i class="fa fa-plus-square-o"></i></button></td>
						              </tr>
						              <tr>
						                 <td ></td><td colspan="3">
						                 <button id="qp-butRemoveSection" class="w3-button w3-yellow w3-small w3-right w3-hover-red w3-margin-left">Remove Section<i class="w3-margin-left fa fa-plus-square-o"></i></button>
						                 <button id="qp-butClearQuestionPaper" class="w3-button w3-yellow w3-small w3-right w3-hover-red w3-margin-left">Clear Paper<i class="w3-margin-left fa fa-plus-square-o"></i></button>
						                 <button id="qp-butSaveQuestionPaper" class="w3-button w3-yellow w3-small w3-right w3-hover-red w3-margin-left">Save Paper<i class="w3-margin-left fa fa-plus-square-o"></i></button>
						                 <button id="qp-butSaveQuestionPaperAsTemplate" class="w3-button w3-yellow w3-small w3-right w3-hover-red">Save As Template<i class="w3-margin-left fa fa-plus-square-o"></i></button></td>
						              </tr>
				                 </table>
						  		 <div class="w3-center w3-container w3-padding">
								       <div id='qp-questionPaper' class="w3-panel  w3-light-grey">
											
											
	     							   </div>									
 
								  </div>
					      </div>
				  </div>                   
				<div id="questionsList" class="w3-container w3-margin">
										       <div id='questions' class="w3-panel w3-light-grey w3-middle">
													<h3 class="w3-left w3-text-blue" id="examSubject">List of questions</h3>
													<button id='qp-addToPaper' class="w3-button w3-yellow w3-hover-red w3-cell w3-right w3-small">Add to Paper</button>
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
  
  <!-- Modal to add Levels (only for admins) -->
   <div id="qb-addLevels" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-padding">
      <header class="w3-container"> 
        <span id='qb-closeButton' onclick="document.getElementById('qb-addLevels').style.display='none'" 
        class="w3-closebtn">&times;</span>
        <h4>Add Levels / Classes</h4>
      </header>    
       <div id='qb-addLevelsForm' class="w3-container">
			<table id="attemptsTable" class="w3-table-all w3-small w3-center w3-border"> 
			 <tr><td><label class="w3-label w3-text-blue">Qualification (Engg./Commerce/Science)</label></td><td><input type="text" class="w3-input" id="qb-qualification" /></td></tr>
			 <tr><td><label class="w3-label w3-text-blue">Class / Course</label></td><td><input type="text" class="w3-input" id="qb-course" /></td></tr>
			 <tr><td colspan="2" class="w3-center"><button id="qb-butOk" class="w3-button w3-yellow w3-small w3-hover-red">OK</button>
			 	               <button id="qb-butCancel" class="w3-button w3-yellow w3-small w3-hover-red">Cancel</button></td>
			 </tr>
			</table>     
      </div>
    </div>
  </div>  
  
  <!-- Modal to add subjects (only for admins) -->
   <div id="qb-addSubjects" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-padding">
      <header class="w3-container"> 
        <span id='qb-closeButton' onclick="document.getElementById('qb-addSubjects').style.display='none'" 
        class="w3-closebtn">&times;</span>
        <h4>Add Subject</h4>
      </header>    
       <div id='qb-addSubjectsForm' class="w3-container">
			<table id="attemptsTable" class="w3-table-all w3-small w3-center w3-border"> 
			 <tr><td><label class="w3-label w3-text-blue">Level </label></td><td><input type="text" class="w3-input" id="qb-addSubjectLevel" /></td></tr>
			 <tr><td><label class="w3-label w3-text-blue">Subject</label></td><td><input type="text" class="w3-input" id="qb-addSubjectSubject" /></td></tr>
			 <tr><td colspan="2" class="w3-center"><button id="qb-butSubjectsOk" class="w3-button w3-yellow w3-small w3-hover-red">OK</button>
			 	               <button id="qb-butSubjectsCancel" class="w3-button w3-yellow w3-small w3-hover-red">Cancel</button></td>
			 </tr>
			</table>     
      </div>
    </div>
  </div>  
  
  <!-- Modal to add topics (only for admins) -->
   <div id="qb-addTopics" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-padding">
      <header class="w3-container"> 
        <span id='qb-closeButton' onclick="document.getElementById('qb-addTopics').style.display='none'" 
        class="w3-closebtn">&times;</span>
        <h4>Add Topic</h4>
      </header>    
       <div id='qb-addTopicsForm' class="w3-container">
			<table id="attemptsTable" class="w3-table-all w3-small w3-center w3-border"> 
			 <tr><td><label class="w3-label w3-text-blue">Level </label></td><td><input type="text" class="w3-input" id="qb-addLevelTopic" /></td></tr>
			 <tr><td><label class="w3-label w3-text-blue">Subject</label></td><td><input type="text" class="w3-input" id="qb-addSubjectTopic" /></td></tr>
			 <tr><td><label class="w3-label w3-text-blue">Topic</label></td><td><input type="text" class="w3-input" id="qb-addTopicTopic" /></td></tr>
			 <tr><td colspan="2" class="w3-center"><button id="qb-butTopicsOk" class="w3-button w3-yellow w3-small w3-hover-red">OK</button>
			 	               <button id="qb-butTopicsCancel" class="w3-button w3-yellow w3-small w3-hover-red">Cancel</button></td>
			 </tr>
			</table>     
      </div>
    </div>
  </div>    
  
    <!-- Modal to add questions of multiple choice single select (only for admins and teachers) -->
   <div id="qb-addQuestions" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-padding">
      <header class="w3-container"> 
        <span id='qb-closeButton' onclick="document.getElementById('qb-addQuestions').style.display='none'" 
        class="w3-closebtn">&times;</span>
        <h4>Add Question</h4>
      </header>    
       <div id='qb-addQuestionForm' class="w3-container">
			<table id="attemptsTable" class="w3-table-all w3-small w3-center w3-border"> 
			 <tr><td><label class="w3-label w3-text-blue">Level </label></td><td colspan="3"><input type="text" class="w3-input" id="qb-addLevelQuestion" /></td></tr>
			 <tr><td><label class="w3-label w3-text-blue">Subject</label></td><td colspan="3"><input type="text" class="w3-input" id="qb-addSubjectQuestion" /></td></tr>
			 <tr><td><label class="w3-label w3-text-blue">Topic</label></td><td colspan="3"><input type="text" class="w3-input" id="qb-addTopicQuestion" /></td></tr>
			 <tr><td><label class="w3-label w3-text-blue">Type</label></td><td colspan="3"><input type="text" class="w3-input" id="qb-addQuestionType" /></td></tr>
		     <tr><td class="w3-text-blue">Question</td><td class="w3-text-black" colspan="3"><input type="text" class="w3-input" id="qb-question"  size="70"/></td></tr>
		     <tr><td class="w3-text-blue">Options</td><td class="w3-text-black" colspan="3"><input type="text" class="w3-input" id="qb-optionFirst" size="50"/></td></tr>
		     <tr><td></td><td colspan="3"><input type="text" class="w3-input" id="qb-optionSecond" size="50"/></td></tr>
			 <tr><td></td><td colspan="3"><input type="text" class="w3-input" id="qb-optionThird"  size="50"/></td></tr>
			 <tr><td></td><td colspan="3"><input type="text" class="w3-input" id="qb-optionFourth" size="50"/></td></tr>
			 <tr><td class="w3-text-blue">Answer </td><td class="w3-text-black"><input type="text" class="w3-input" id="qb-answer"  size="50"/></td>
			 <td class="w3-text-blue">Max Marks </td><td class="w3-text-black"><input type="text" class="w3-input"id="qb-maxMarks" size="10"/></td></tr>
			 <tr><td class="w3-text-blue">Upload image</td><td>
					 <form id="qb-fileUploadForm" action="#" method="post" enctype="multipart/form-data"> 
					   <p>
						Select photo file : <input type="file" name="uploadFile" size="50" />
					   </p>
				       <input type="hidden" id="userid" name="userid" />
				       <input type="button" id="qb-butUploadIt" value="Upload It" />
					   <!-- <input type="submit" value="Upload It" /> -->
					</form>
			 <input type="hidden" id="imageUrl" />
			 <td colspan="2"><img id="qb-image" src="" style="width:15%;height:10%"/></td></tr>			 			 
			 <tr><td colspan="4" class="w3-center">
							    <button id="qb-butQuestionClear" class="w3-button w3-yellow w3-small w3-hover-red">Clear</button>			 
			 					<button id="qb-butQuestionOk" class="w3-button w3-yellow w3-small w3-hover-red">Save</button>
			 	                <button id="qb-butQuestionCancel" class="w3-button w3-yellow w3-small w3-hover-red">Cancel</button></td>
			 </tr>
			</table>     
      </div> 
    </div> 
    </div> 
  
      <!-- Modal to add questions of fill in blanks (only for admins and teachers) -->
   <div id="qb-addQuestions-fib" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-padding">
      <header class="w3-container"> 
        <span id='qb-closeButton-fib' onclick="document.getElementById('qb-addQuestions-fib').style.display='none'" 
        class="w3-closebtn">&times;</span>
        <h4>Add Question</h4>
      </header>    
       <div id='qb-addQuestionForm-fib' class="w3-container">
			<table id="attemptsTable-fib" class="w3-table-all w3-small w3-center w3-border"> 
			 <tr><td><label class="w3-label w3-text-blue">Level </label></td><td colspan="3"><input type="text" class="w3-input" id="qb-addLevelQuestion-fib" /></td></tr>
			 <tr><td><label class="w3-label w3-text-blue">Subject</label></td><td colspan="3"><input type="text" class="w3-input" id="qb-addSubjectQuestion-fib" /></td></tr>
			 <tr><td><label class="w3-label w3-text-blue">Topic</label></td><td colspan="3"><input type="text" class="w3-input" id="qb-addTopicQuestion-fib" /></td></tr>
			 <tr><td><label class="w3-label w3-text-blue">Type</label></td><td colspan="3"><input type="text" class="w3-input" id="qb-addQuestionType-fib" /></td></tr>
		     <tr><td class="w3-text-blue">Question</td><td class="w3-text-black" colspan="3"><input type="text" class="w3-input" id="qb-question-fib"  size="70"/></td></tr>
<!-- 		     <tr><td class="w3-text-blue">Options</td><td class="w3-text-black"><input type="text" class="w3-input" id="qb-optionFirst" size="50"/></td><td></td><td></td></tr>
		     <tr><td></td><td><input type="text" class="w3-input" id="qb-optionSecond" size="50"/></td><td></td><td></td></tr>
			 <tr><td></td><td><input type="text" class="w3-input" id="qb-optionThird"  size="50"/></td><td></td><td></td></tr>
			 <tr><td></td><td><input type="text" class="w3-input" id="qb-optionFourth" size="50"/></td><td></td><td></td></tr> -->
			 <tr><td class="w3-text-blue">Answer </td><td class="w3-text-black"><input type="text" class="w3-input" id="qb-answer-fib"  size="50"/></td>
			 <td class="w3-text-blue">Max Marks </td><td class="w3-text-black"><input type="text" class="w3-input"id="qb-maxMarks-fib" size="10"/></td></tr>
			 <tr><td class="w3-text-blue">Upload image</td><td class="w3-text-black">
					 <form id="qb-fib-fileUploadForm" action="#" method="post" enctype="multipart/form-data"> 
					   <p>
						Select photo file : <input type="file" name="uploadFile" size="50" />
					   </p>
				       <input type="hidden" id="userid" name="userid" />
					   <input type="button" id="qb-fib-butUploadIt" value="Upload It" />
					   <!-- <input type="submit" value="Upload It" /> -->
					</form>			 
			 <input type="hidden" id="imageUrl" />
			 </td><td colspan="2"><img id="qb-fib-image" src="" style="width:15%;height:10%"/></td></tr>
			 <tr><td colspan="4" class="w3-center">
			                   <button id="qb-butQuestionClear-fib" class="w3-button w3-yellow w3-small w3-hover-red">Clear</button>
			                   <button id="qb-butQuestionOk-fib" class="w3-button w3-yellow w3-small w3-hover-red">Save</button>
			 	               <button id="qb-butQuestionCancel-fib" class="w3-button w3-yellow w3-small w3-hover-red">Cancel</button></td>
			 </tr>
			</table>     
      </div> 
  </div>
  </div>  
  
    <div id="qb-photo" class="w3-modal">
    <div class="w3-modal-content w3-animate-zoom w3-card-8 w3-pale-green">
      <header class="w3-container w3-green"> 
        <span onclick="document.getElementById('qb-photo').style.display='none'" 
        class="w3-closebtn">&times;</span>
        <h5>Upload photo</h5>
      </header>     
      <div id="qb-uploadPhoto" class="w3-container">
      
			<form id="qb-list-fileUploadForm" action="#" method="post" enctype="multipart/form-data"> 
		
			   <p>
				Select photo file : <input type="file" name="uploadFile" size="50" />
			   </p>
		       <input type="hidden" id="upload-questionid" name="upload-questionid" />
			   <input type="button" id="qb-list-butUploadIt" value="Upload It" />
			</form>    
        
      </div>
    </div>
  </div>  
   
  <div id="spinner">
    <div>
      <p><i class="fa fa-spinner w3-spin" style="font-size:64px"></i></p>
    </div>
  </div>

</body>
</html>
