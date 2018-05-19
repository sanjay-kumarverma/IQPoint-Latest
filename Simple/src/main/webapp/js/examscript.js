  $(document).ready(function() {
	  
      

	  var userId=$('#root').attr('value');
      var subjectName=$('#clickData').attr('title');
      var subjectId=$('#clickData').text();
      var questionPaperId="";
      var currentExamId="";
      var examExecutionId="";
      var currentPassPercent=0;
      
		var qpointer=0;
		var questionids=[];
		var answers=[];
		var buttonPressed="";
		var answerSelectedJson="";
		var _ref = $(this);
      
      //console.log("subject id-->"+subjectId);
      console.log("subject name-->"+subjectName);
      
      //$('#examSubject').text(subjectName);
      $('#navButton').html('<button class="w3-button w3-light-grey w3-xlarge" onclick="w3_open()">&#9776;</button>');
      $('#examSubject').html(subjectName);
      $('#examTitle').html('List of scheduled exams for you.');
      
      showProgressBar("Loading exams, please wait...");
         loadExams(userId,subjectName); //loading exams
      hideProgressBar();
      

      
		function loadExams(userId,subjectName)
		{
		   
			  //alert("trying to get data for"+userId);
			          
			          
/*		              $.ajax(
			           		{                             
			                   type: 'GET',
			                   url :'resteasy/qb/exams/subject/'+subjectId+'/user/'+userId,
			                   success : function(data) {
			                   	                           prepareExamList(data);
			                                            },
			           		   fail : function() {alert('There was some problem getting exam information. Please try after sometime.');}
			      
			           		});
*/
			  $.get('resteasy/qb/exams/subject/'+subjectId+'/user/'+userId)
			   .done(function(exams){
				   prepareExamList(exams);
			   })
			   .fail(function(){
	                  var msg='<p>There was some problem getting exams information. Please retry after some time.</p>';
	                  $('#examErrorMessage > p').remove();
	                  $(msg).appendTo('#examErrorMessage');	 	        	  
	        	      document.getElementById('error').style.display='block';				   
			   });
			
		}  
		
/*		function showProgressBar(message) {
			$('#progressbarMessage').remove();
			$('<p>'+message+'</p>').appendTo('#progressbarMessage');
			document.getElementById('progressbar').style.display='block';
		}
		
		function hideProgressBar() {
			document.getElementById('progressbar').style.display='none';
		}*/
		
		function prepareExamList(data)
		{
			
		      
			var jsonData = JSON.parse(data);
			$.each(jsonData,function(index,value){
				$('<table class="w3-table-all"><tr><td style="width:95%"> <button id="'+value.id+'" class="w3-btn-block w3-left-align w3-leftbar">'+
                        '<span class="w3-small">'+value.exam.toUpperCase()+'</span><span class="w3-text-blue w3-right w3-small">(&nbsp;&nbsp;Exam date :<span class="w3-text-black">'+value.examDate+'</span>&nbsp;&nbsp;Duration :<span class="w3-text-black">'+value.examDuration+'&nbsp;mins</span>&nbsp;&nbsp;Assigned by :<span class="w3-text-black">'+value.updatedBy+'</span>&nbsp;&nbsp;)</span>'+
                        //'<div style="float:right"></div>'+
                        '</button></td></tr></table>').insertAfter('#title');
				       var afterid=value.id;
				  
					   var divid="topic"+afterid;
					   topicstr='<p class="w3-padding w3-small"> Paper :&nbsp;<span class="w3-text-blue">'+value.questionPaperPlain.qpName+'</span>&nbsp;&nbsp;&nbsp;Pass:&nbsp;<span class="w3-text-blue">'+value.questionPaperPlain.qpPassPercent+'%</span>';
					   topicstr+= '<button id="'+divid+'ViewPaperButton" class="w3-button w3-yellow w3-small w3-right w3-margin-left">Question Paper</button>';
					   topicstr+= '<button id="'+divid+'PrevAttemptsButton" class="w3-button w3-yellow w3-small w3-right w3-margin-left">Previous Attempts</button>';
					   topicstr+= '<button id="'+divid+'StartButton" class="w3-button w3-yellow w3-small w3-right w3-margin-left">Start Test</button></p>';
					   $('<div id="'+divid+'" class="w3-accordion-content w3-container">'+topicstr+
					   '</div>').insertAfter('#'+afterid);	
					   
						if (index%2 >0)
							$('#'+value.id).addClass('w3-pale-yellow w3-border-yellow');
						else
							$('#'+value.id).addClass('w3-pale-blue w3-border-blue');	
						
						$('#'+divid+'StartButton').click(function(){
							getQuestionPaper(value.questionPaperPlain.id,value.questionPaperPlain.qpName,value.questionPaperPlain.qpPassPercent,value.examDuration);
						});
						
						$('#'+divid+'PrevAttemptsButton').click(function(){
							//alert("Exam id :"+value.id);
							getPreviousAttempts(value.id); // we are passing exam id here
						});	
						
						$('#'+divid+'ViewPaperButton').click(function(){
								var dispStr=getQuestionPaperString();
							    $(dispStr).appendTo('#dispQuestionPaper');
							    document.getElementById('questionPaper').style.display='block';
							    
							    $('#closeQuestionPaper').unbind().click(function(){
							    	document.getElementById('questionPaper').style.display='none';
							    });
							    
							    $('#butDemo2').unbind().click(function(){
							    	openAccordian("Demo2");
							    });
							    
						});							
						
					      $('#'+value.id).click(function(){
					    	  
					          currentExamId=value.id;
					          currentPassPercent=value.questionPaperPlain.qpPassPercent;
					          console.log("current Exam Id"+currentExamId);
					          console.log("current pass percent"+currentPassPercent);
					    	  var id=$(this).next().attr('id');
					    	  openAccordian(id);
					      });
			});
			
		}
		
		function getQuestionPaperString() {
			var dispStr=
			'<h2>Question Paper</h2>'+
			'<p>You can also download / print this question paper.</p>'+
			'<button id="butDemo2" class="w3-button w3-block w3-pale-yellow">'+
			'Section A - Multiple chioce (Max Marks : 25)</button>'+

			'<div id="Demo2" class="w3-container w3-hide">'+
			' <p>Lorem ipsum...</p>'+
			'</div>'+

			'<button id="butDemo3" class="w3-btn w3-block w3-pale-green">'+
			'Section B - Fill in the blanks (Max Marks :30)</button>'+

			'<div id="Demo3" class="w3-container">'+
			  '<p>Centered content as well!</p>'+
			'</div>';

            return dispStr;
			
			
		}
		
		function getQuestionPaper(paperId,qpname,passpercent,duration)
		{
			
	        questionPaperId=paperId; //setting question paper id as global value
	        
/*            $.ajax(
	           		{                             
	                   type: 'GET',
	                   url :'resteasy/qb/questionpaper/'+paperId+'/exam/'+currentExamId+'/user/'+userId,
	                   success : function(qpaper) {
	                	                          var jsonstr=JSON.parse(qpaper);
	                	                          if (jsonstr.id=="-1")
	                	                        	  {
	                	                        	    $('#alertMessage > p').remove();
	                	                        	    $('<p>Question paper for selected exam is not ready. Please try after sometime.</p>').appendTo('#alertMessage');
	                	                        	    document.getElementById('alert').style.display='block';
	                	                        	  }
	                	                          else
	                   	                              showTest(qpaper,duration);
	                                            },
	           		   fail : function() {document.getElementById('error').style.display='block';}
	      
	           		});	*/
	        
	        $.get('resteasy/qb/questionpaper/'+paperId+'/exam/'+currentExamId+'/user/'+userId)
	          .done(function(qpaper){
                  var jsonstr=JSON.parse(qpaper);
                  if (jsonstr.id=="-1")
                	  {
                	    $('#alertMessage > p').remove();
                	    $('<p>Question paper for selected exam is not ready. Please try after sometime.</p>').appendTo('#alertMessage');
                	    document.getElementById('alert').style.display='block';
                	  }
                  else
                         showTest(qpaper,duration);	        	  
	        	  
	          })
	          .fail(function(){
	                  var msg='<p>There was some problem getting question paper for the exam. Please retry after some time.</p>';
	                  $('#examErrorMessage > p').remove();
	                  $(msg).appendTo('#examErrorMessage');	 	        	  
	        	      document.getElementById('error').style.display='block';
	          });
			
			
		}
		
		function getPreviousAttempts(examId)
		{
			
/*           $.ajax(
	           		{                             
	                   type: 'GET',
	                   url :'resteasy/qb/exam/previousAttempts/'+examId,
	                   success : function(attempts) {
	                	                          var jsonAttempts=JSON.parse(attempts);
	                	                          if (jsonAttempts.length==0)
	                	                        	  {
	                	                        	    $('#alertMessage > p').remove();
	                	                        	    $('<p>No attempts for the test were found. Possibly question paper for this exam may not be ready.</p>').appendTo('#alertMessage');
	                	                        	    document.getElementById('alert').style.display='block';
	                	                        	  }
	                	                          else
	                	                        	  {   
	                	                        	     //var jsonAttempts=JSON.parse(attempts);
	                   	                                 //showAttempts(jsonAttempts);
	                	                        	     console.log(jsonAttempts);
	                	                        	     showAttempts(jsonAttempts,examId);
	                	                        	  }
	                                            },
	           		   fail : function() {document.getElementById('error').style.display='block';}
	      
	           		});	*/
	          $.get('resteasy/qb/exam/previousAttempts/exam/'+examId+'/user/'+userId)
	               .done(function(attempts){
                       var jsonAttempts=JSON.parse(attempts);
                       if (jsonAttempts.length==0)
                     	  {
                     	    $('#alertMessage > p').remove();
                     	    $('<p>No attempts for the test were found. Possibly question paper for this exam may not be ready.</p>').appendTo('#alertMessage');
                     	    document.getElementById('alert').style.display='block';
                     	  }
                       else
                     	  {   
                     	     showAttempts(jsonAttempts,examId);
                     	  }	            	   
	            	   
	                 })
	               .fail(function(){
 	                  var msg='<p>There was some problem getting exam attempts data. Please retry after some time.</p>';
	                  $('#examErrorMessage > p').remove();
	                  $(msg).appendTo('#examErrorMessage');	            	   
	            	   document.getElementById('error').style.display='block';
	               });
			
		}	
		
		function showAttempts(attempts,examId)
		{
			
			var dispStr='<p id="attempts">'+
			            '<table id="attemptsTable" class="w3-table-all w3-hoverable w3-small w3-center">'+
					    '<thead><tr class="w3-light-blue"><th>S.No.</th><th>Attempt Date</th><th>Attempt Time</th><th>Time taken</th><th>Marks</th><th>Pass/Fail</th><th>Answers</th><th>Remove</th></tr></thead>';
			$.each(attempts,function(index,value){
				
				  var scoreClass="";
				  var scoreInt=parseInt(value.score);
				  if (scoreInt >= currentPassPercent)
					  scoreClass="<i class='fa fa-thumbs-o-up' style='font-size:24px;color:green' </i>"; 
				  else					  
				      scoreClass="<i class='fa fa-thumbs-o-down' style='font-size:24px;color:red' </i>";
				  
				  rowStr='<tr>'+
				         '<td>'+(index+1)+'</td>'+
				         '<td>'+value.attemptDate+'</td>'+
				         '<td>'+value.attemptTime+'</td>'+
				         '<td>'+value.hours+':'+value.minutes+':'+value.seconds+'</td>'+
				         /*'<td>'+value.venue+'</td>'+*/
				         '<td>'+value.score+'</td>'+
				         '<td>'+scoreClass+'</td>'+
				         '<td id="answer-'+index+'"><i class="fa fa-file-text-o" style="font-size:24px;color:teal"></i></td>'+
				         '<td id="delete-'+index+'"><i style="font-size:24px;color:teal" class="fa">&#xf00d;</i></td>'+
				         
				         '</tr>';
				  
				  dispStr+=rowStr;
				  

			});
            dispStr+='</table></p>';
            
            if ($('#topic'+examId).find('#attempts').is(':visible')) {
            	$('#topic'+examId).find('#attempts').remove();
            }
            else {   
            	$('#topic'+examId).find('#attempts').remove();
                $(dispStr).insertAfter('#topic'+examId+' > p');
            }
            
			   $(document).unbind().on("click", "td", function() {
				    var parentRow=$(this).parent();
				    console.log(parentRow);
				    var splitstr=$( this ).attr("id").split("-");
				    if (splitstr[0]=="answer") {
				    	getAnswerSheet(attempts[splitstr[1]].id);
				    }
				    else if (splitstr[0]=="delete") {
				    	deleteAttempt(attempts[splitstr[1]].id,parentRow);
				    }
				    	

				   });
		}
		
		function deleteAttempt(attemptId,parentRow) {

			
			var strMsg="<p>This test attempt will be permanently deleted. Press Cancel to ignore and OK to continue deleting.</p>";
            showWarning(strMsg);
			$('#warningCancelButton').unbind().click(function(){
				document.getElementById('warningMessageWithButtons').style.display='none';
			});
			$('#warningOkButton').unbind().click(function(){
				document.getElementById('warningMessageWithButtons').style.display='none';
				
				//proceed to deleting the attempt here, it will be soft delete
				$.get('resteasy/qb/exam/deleteAttempt/'+attemptId)
                .done(function(attemptObj) {
            	                               //var jsonAttemptObj=JSON.parse(attemptObj);
            	                        	    $('#alertMessage > p').remove();
            	                        	    $('<p>Selected test attempt was deleted.</p>').appendTo('#alertMessage');
            	                        	    document.getElementById('alert').style.display='block';
            	                        	    parentRow.remove();
            	                        	  })
                     .fail(function() {
                    	                  var msg='<p>There was some problem deleting the attempt. Please retry after some time.</p>';
                    	                  $('#examErrorMessage > p').remove();
                    	                  $(msg).appendTo('#examErrorMessage');
                    	                  document.getElementById('error').style.display='block';});				
				
				
				
			});			
			
			document.getElementById('warningMessageWithButtons').style.display='block';
		}
		
		function getAnswerSheet(attemptId) {
			

			 $.get('resteasy/qb/exam/answerSheet/'+attemptId)
			                    .done(function(answersheet) {
		                	                          var jsonAnswerSheet=JSON.parse(answersheet);
		                	                          if (jsonAnswerSheet.length==0)
		                	                        	  {
		                	                        	    $('#alertMessage > p').remove();
		                	                        	    $('<p>No answers were found for select attempt. You finished this test without answering any question</p>').appendTo('#alertMessage');
		                	                        	    document.getElementById('alert').style.display='block';
		                	                        	  }
		                	                          else
		                	                        	  {   

		                	                        	     showAnswerSheet(jsonAnswerSheet);
		                	                        	  }
		                                            })
		                         .fail(function() {
		                        	                  var msg='<p>There was some problem showing answer sheet. It could be that no answers for test were given.</p>';
		                        	                  $('#examErrorMessage > p').remove();
		                        	                  $(msg).appendTo('#examErrorMessage');
		                        	                  document.getElementById('error').style.display='block';});
				
			
			
		}
		
/*		$("#error")
	    .ajaxError(
	        function(e, x, settings, exception) {
	            var message;
	            var statusErrorMap = {
	                '400' : "Server understood the request, but request content was invalid.",
	                '401' : "Unauthorized access.",
	                '403' : "Forbidden resource can't be accessed.",
	                '500' : "Internal server error.",
	                '503' : "Service unavailable."
	            };
	            if (x.status) {
	                message =statusErrorMap[x.status];
	                                if(!message){
	                                      message="Unknown Error \n.";
	                                  }
	            }else if(exception=='parsererror'){
	                message="Error.\nParsing JSON Request failed.";
	            }else if(exception=='timeout'){
	                message="Request Time out.";
	            }else if(exception=='abort'){
	                message="Request was aborted by the server";
	            }else {
	                message="Unknown Error \n.";
	            }
	            $(this).css("display","inline");
	            $(this).html(message);
	            $('#error > p').remove();
	            $('<p>'+message+'</p>').appendTo('#error');
	            document.getElementById('error').style.display='block';
	                 });	*/	
		
		function showAnswerSheet(jsonAnswerSheet) {
			$('#answers > table').remove();
			//var dispStr='<p id="answerSheetDetails">'+
			var dispStr='<table id="answerSheetDetails" class="w3-table-all w3-hoverable w3-small w3-center">'+
		    '<thead><tr class="w3-light-blue"><th>S.No.</th><th>Question</th><th>Correct Answer</th><th>Answer Given</th><th>Max Marks</th><th>Score</th></tr></thead>';
			var dispMaxMarks=0;
			var dispScore=0;
			$.each(jsonAnswerSheet,function(index,answer){
				
				  rowStr='<tr>'+
			         '<td>'+(index+1)+'</td>'+
			         '<td>'+answer.question+'</td>'+
			         '<td>'+answer.correctAnswer+'</td>'+
			         '<td>'+answer.answerGiven+'</td>'+
			         '<td>'+answer.maxMarks+'</td>'+
			         '<td>'+answer.score+'</td>'+
			         '</tr>';
			  
			      dispStr+=rowStr;	
			      
			      dispMaxMarks+=Number(answer.maxMarks);
			      dispScore+=Number(answer.score);
				
				//$(rowStr).appendTo('#answers');
				
			});
			  rowStr='<tr>'+
		         '<td></td>'+
		         '<td></td>'+
		         '<td></td>'+
		         '<td><b>Total</b></td>'+
		         '<td><b>'+dispMaxMarks+'</b></td>'+
		         '<td><b>'+dispScore+'</b></td>'+
		         '</tr>';
			//dispStr+='</table></p>';
			dispStr+=rowStr;
			
			var dispPercent=(dispScore/dispMaxMarks)*100;
			
			  rowStr='<tr>'+
		         '<td></td>'+
		         '<td></td>'+
		         '<td></td>'+
		         '<td><b>Percentage Marks</b></td>'+
		         '<td><b>'+dispPercent.toFixed(2)+'%</b></td>'+
		         '<td></td>'+
		         '</tr>';
			//dispStr+='</table></p>';
			dispStr+=rowStr;			
			
			dispStr+='</table>';

	        $(dispStr).appendTo('#answers')
			document.getElementById('answerSheet').style.display='block';
			
			$('#answerSheetOkButton').unbind().click(function() {
				document.getElementById('answerSheet').style.display='none';
			})
			
			
			
			
			
		}
      
		function showTest(qPaper,duration)
		{
			//console.log("question paper is :"+paperId);
			//$('#testSection').attr('display','block');
			
			//reset any previously stored info in global variables
			//console.log("Question ids before"+questionids);
			qpointer=0;
			questionids=[];
			answers=[];
			buttonPressed="";
			answerSelectedJson="";
			//global variable data reset is done
			//console.log("Question ids after"+questionids);
			
			var qpjson = JSON.parse(qPaper);
			var qpname=qpjson.qpName;
			var passpercent=qpjson.qpPassPercent;
			
			document.getElementById('rulesSection').style.display='block';
			$('#rs-paperName').html('<b>'+qpname+'</b>');
			$('#ts-paperName').html('<b>'+qpname+'</b>');
			$('#rs-passPercent').html(passpercent);
			$('#rs-duration').html(duration);
			
/*			$('#gotoTest').click(function(){
				document.getElementById('rulesSection').style.display='none';
				document.getElementById('testSection').style.display='block';	
			});*/

			$('#moveToTest').unbind().click(function(){
				document.getElementById('rulesSection').style.display='none';
				$('#questionSection > div').remove();
				scrollThroughQuestions(qpjson);
				document.getElementById('testSection').style.display='block';	
			});
			
			$('#cancelTest').unbind().click(function(){
				document.getElementById('rulesSection').style.display='none';
			});
			
			$('#ts-finishTest').unbind().click(function(){

				//check if some answers were give
				if (answers.length==0 || answers.length < questionids.length)
					showWarning("No answers or some answers were given. Press <b>Cancel</b> to continue test. Press <b>OK</b> to finish the test anyways.");
				else 
					showWarning("You are about to finish your test. Marks will be awarded now. Press <b>Cancel</b> to continue or <b>OK</b> to finish the test. ");
				
				
		    	$('#warningOkButton').unbind().click(function(){
					//save this test session
					saveTestSession();
					$('#questionSection > div').remove();
					document.getElementById('testSection').style.display='none';
		    		document.getElementById('warningMessageWithButtons').style.display='none';	
		    		
		    		//display score dialog box
		    		//showCandidateScore();
		    	});
		    	
		    	$('#warningCancelButton').unbind().click(function(){
		    		//buttonClicked="cancel";
		    		document.getElementById('warningMessageWithButtons').style.display='none';	
		    	});	    	
  

			});	
			
			
			
			$('#TestMap').unbind().click(function(){
				$('#questionSection > div').remove();
				$('#nextQuestion').hide();
				$('#prevQuestion').hide();
				$('#saveQuestion').hide();
				$('#ts-finishTest').hide();
				$('#TestMap').hide();
				$('#Help').hide();
				
	        	var qstr=prepareQuestionMap();
 	         
		          $(qstr).appendTo('#questionSection');
		          $('#mapId').find('span').unbind().click(function(){
		        	  //console.log($(this));
		        	  //console.log($(this).attr('id'));
		        	  $('#mapId').remove();
		        	  qpointer=parseInt($(this).attr('value'));
		        	  //console.log(qpointer);
		        	  getQuestion($(this).attr('id'),questionids);
		        	  
		        	  //setting the answer if it already exists
		        	  var answerSelected=answers[qpointer];
                      if (answerSelected !== undefined ) {
                    	    answerSelectedJson=JSON.parse(answerSelected);
					        var setstr='input[type="radio"][name="qoption"][value="'+answerSelectedJson.answer+'"]';
				            $(setstr).prop('checked',true); }		        	  
		        	  
		        	  
		        	  
						$('#nextQuestion').show();
						$('#prevQuestion').show();
						$('#saveQuestion').show();
						$('#ts-finishTest').show();
						$('#TestMap').show();
						$('#Help').show();

						//setting the buttons in right condition
						$('#nextQuestion').removeAttr('disabled');
						$('#prevQuestion').removeAttr('disabled');
						  if (qpointer==questionids.length-1) 
				              $('#nextQuestion').attr('disabled','disabled'); 
						  if (qpointer==0) 
				              $('#prevQuestion').attr('disabled','disabled');
				          
		        	  
		          });
				
				//document.getElementById('testSection').style.display='none';
			});		
						
			
			$('alertButton').unbind().click(function(){
				onclick=document.getElementById('alert').style.display='none';
			});				
			
			
			$('#nextQuestion').removeAttr('disabled');
			$('#prevQuestion').attr('disabled','disabled');
			
			$('#nextQuestion').unbind().click(function(){
                //console.log("Question Pointer --->"+qpointer);
				buttonPressed="next";
				if (qpointer<=questionids.length)	
					{ 
					  ++qpointer; 
					  if (qpointer==questionids.length-1) 
					          {$('#nextQuestion').attr('disabled','disabled'); //--qpointer;
					          }
                      if (qpointer > 0)
                    	  { $('#prevQuestion').removeAttr('disabled'); }
					    
					  //submitAnswerOnNextAndPrev(questionids[qpointer],questionids,qpointer);
                      //}

					  
					  //after getting the question set the answer
					  //console.log("answers length --->"+answers.length);
					  //console.log(qpointer);
					  if (answers.length>0 && answers[qpointer] != null) {
						  
						        answerSelectedJson=JSON.parse(answers[qpointer]);
/*						        console.log(answerStr.answer);
							        var setstr='input[type="radio"][name="qoption"][value="'+answerStr.answer+'"]';
							        console.log(setstr);
						            $(setstr).prop('checked',true); 	*/					        	
						        	

					            //$('input[name=my_name][value=123]').prop("checked",true)          
					  }
					  getQuestion(questionids[qpointer],questionids);  
					} 
			});
			
			$('#prevQuestion').unbind().click(function(){
				buttonPressed="previous";
				if (qpointer>=0)
					{ //submitAnswerOnNextAndPrev();
					  --qpointer; 
					  
					  if (qpointer<questionids.length) 
			             {$('#nextQuestion').removeAttr('disabled'); }
                      if (qpointer == 0)
            	        { $('#prevQuestion').attr('disabled','disabled');}
					  
					  //submitAnswerOnNextAndPrev(questionids[qpointer],questionids,qpointer); 
                     // }
                      
					
					  
					  //after getting the question set the answer
/*					  if (answers.length>0 && qpointer==answers.length-1) {	
						        answerStr=answers[qpointer].answer;
						        console.log(answerStr);
						        
					            $('input:radio[name="qoption"][value="'+answerStr+'"]').attr('checked',true); }	*/	
					  
					  if (answers.length>0 && answers[qpointer] != null) {						  
					        answerSelectedJson=JSON.parse(answers[qpointer]);}
         
					  getQuestion(questionids[qpointer],questionids); 			  
					
					}
			});	
			
			$('#saveQuestion').unbind().click(function(){
				
				 //immediatly diable button that is enabled currenly
				 var nextDisabled=$('#nextQuestion').is(':disabled');
				 var prevDisabled=$('#prevQuestion').is(':disabled');
				 
				 //console.log("Next button-->"+nextDisabled);
				 //console.log("Prev button-->"+prevDisabled);
				 
				 if (nextDisabled)
					 $('#nextQuestion').attr('disabled','disabled');
				 if(prevDisabled)
					 $('#prevQuestion').attr('disabled','disabled');

     	        
     	        
	        	var qid = $('#questionId').val();
	        	var questionType=$('#questionTypeId').val();
	        	var _csrf = $("input[name='_csrf']").val();
	        	//alert("question id"+qid);
	        	var answer="";
	        	   if (questionType=='1') {
	        	       answer = $("input[name='qoption']:checked"). val(); 
	        	   }
	        	   else if (questionType=='2') {
	        	       //answer = $("input[name='qoption']:checked"). val(); 
	        	       $.each($("input[name='qoption']:checked"), function(index,value) {
	        	    	   if (index==0) 
	        	       	        answer=answer+$(this).val();
	        	           else
	        	        	    answer=answer+","+$(this).val();
	        	    	 });
			           console.log(answer);
	        	   }	        	   
	        	   else if (questionType=='4') {
	        		   answer = $('#qanswer').val();
	        	   }
	        		   
	        		   
	        	var answerobj={"userId":userId,"questionPaperId":questionPaperId,"questionId":qid,"qoption":answer,"_csrf":_csrf};
	        	
	        	if (typeof answer === "undefined")
	        		{   $('#alertMessage > p').remove();
	        		    $('<p>You must select the answer to save.</p>').appendTo('#alertMessage');
	        		    document.getElementById('alert').style.display='block';
	        		    
	        		}
	        		//alert('Please select the answer first.');
	        	else {
	        		      $('#saveQuestion').attr('disabled','disabled');
	        		      submitAnswer(answerobj);
	        		      
	        		      
	        	   }
				   //submitAnswerOnNextAndPrev(questionids[qpointer],questionids,qpointer);
	        	
				 if (!nextDisabled)
					 $('#nextQuestion').removeAttr('disabled');
				 if(!prevDisabled)
					 $('#prevQuestion').removeAttr('disabled');	        	
			});
			

			
		}
		
	   function showCandidateScore() {
		   //get candidate score

		   
		   $.get('resteasy/qb/exam/getScore/'+examExecutionId)
		    .done(function(score){
	     		   var scoredMarks=0;
	    		   var maxMarks=0;
	    		   var numOfQuestions=0;	                	   
	               var jsonScore=JSON.parse(score);
	               
	               if (jsonScore.length >0) {
	
	                  numOfQuestions=jsonScore.length;
	                  $.each(jsonScore,function(index,value){
	                	 maxMarks+=parseInt(value.maxMarks); 
	                	 scoredMarks+=parseInt(value.score);
	                  });
	               }
	    		   $('#candidateScoreBar').removeClass("w3-green"); // remove both the classes before displaying
	    		   $('#candidateScoreBar').removeClass("w3-red");
				   document.getElementById('candidateScore').style.display='block';
				   scoreDisplayer(jsonScore,scoredMarks,maxMarks,numOfQuestions);
				   $('#seenScore').unbind().click(function(){
					   document.getElementById('candidateScore').style.display='none';   
			         });		    	
		    })
		    .fail(function(){
		           var msg='<p>There was some problem getting test score. Please retry after some time.</p>';
		           $('#examErrorMessage > p').remove();
		           $(msg).appendTo('#examErrorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';	    	
		    });
		   

	   
	   }
	   
	   function scoreDisplayer(jsonScore,scoredMarks,maxMarks,numOfQuestions) {
           
		   var elem = document.getElementById("candidateScoreBar");   
		   var width = 0;
		   var steps = 0;
		   var percent=0;
		   
		   
		   if (numOfQuestions>0)
		      steps=100/numOfQuestions;

           //var scorePerQuestion=scoredMarks/numOfQuestions;
		   var scoreCount=0;
		   var id = setInterval(frame, 70);
		   var x=0;
		   function frame() {
                  if (numOfQuestions >0) {
				     x++;
				     if (x > numOfQuestions) {
				       clearInterval(id);
					   if (percent >= currentPassPercent )
						   $('#candidateScoreBar').addClass("w3-green");
					   else
						   $('#candidateScoreBar').addClass("w3-red");				       
				     } else {
				          if(jsonScore[x-1].score>0)
				               width+=steps; 
				           scoreCount+=parseInt(jsonScore[x-1].score);
				           percent= (scoreCount/maxMarks)*100;
					       elem.style.width = Math.round(width) + '%'; 
					       //elem.innerHTML = Math.round(percent)   + '%';
					       elem.innerHTML = percent.toFixed(2)   + '%';
				     }
                  }
                  else
                	  {
                	   percent=0;
				       elem.style.width = Math.round(0) + '%'; 
				       elem.innerHTML = Math.round(0)   + '%';
                	  }
		    }
		   
		 }	   
		
       function prepareQuestionMap()
       {
    	   var mapString='<div id="mapId" class="w3-cell-row w3-margin" style="width:50%">';
    	   var qcolor="w3-grey";
    	   console.log(answers);
    	   for(x=0,y=1;x<=questionids.length-1;x++,y++)
    		   {  

                 qcolor="w3-grey";
    		     for(z=0;z<answers.length;z++)
    		    	 {
    		    	   console.log(answers[z]);
    		    	   if (answers[z] !== undefined) {
	    		    	    var jsonstr=JSON.parse(answers[z]);
	    		    	    if (questionids[x]==jsonstr.questionId)
	    		    	    	 qcolor="w3-green";
	    		    	    	
    		    	   }	
    		    	 }
    		      
    		      var qstr='<div class="w3-container '+qcolor+' w3-cell  w3-cell-middle w3-border">'+
	        	              '<span id="'+questionids[x]+'" value="'+x+'">Q-'+y+'</span>'+
		        	       '</div>';
    		      mapString+=qstr;
    		   }
    	   mapString+='</div>';
    	   return mapString;
       }
		
		
		function submitAnswer(answerobj)
		{   //console.log("coming in submit");
		    //console.log("Question Pointer --->"+qpointer);
			//$('#questionForm').one('submit',function(e) {

		        /*$.post("resteasy/qb/submitanswer",$('#questionForm').serialize())*/
				$.post("resteasy/qb/submitanswer",answerobj)
                      .done( function(data,status,xhr){ 
                    	       var jsondata=JSON.parse(data);
                   	           answers[qpointer]=data;
                   	          // console.log("answer array-->"+answers);
                   	    /*  if(qpointer>=0 || qpointer<questionids.length-1)
                    	          getQuestion(questionId,questionids);*/
			                   })
			          .fail(function(data,status,xhr){
			        	  console.log("form submission failed"); 
			        	  
			          });
		}
				
		function saveTestSession()
		{  	 var _csrf = $("input[name='_csrf']").val();	
			 var sessionObj={"userId":userId,"questionPaperId":questionPaperId,"examExecutionId":examExecutionId,"_csrf":_csrf};
			 $.post("resteasy/qb/saveTestSession",sessionObj)
		
                      .done( function(data,status,xhr){ 
                    	       //var jsondata=JSON.parse(data);
                                 //console.log(data);
	           		    		//display score dialog box
	           		    		 showCandidateScore();
			                   })
			          .fail(function(data,status,xhr){
			        	  console.log("Final submission of answer failed, please retry submitting the answer."); 
			        	  
			          });

	
             }

		function scrollThroughQuestions(qpjson)
		{  
			//set exam execution id
			examExecutionId=qpjson.examExecutionId;
			
			var qindex=0;
			$.each(qpjson.qpSections,function(index,value){
				//console.log("section id "+value.id);
				//console.log("section type "+value.sectionType);
				$.each(value.questions,function(index,question){
					//console.log("Question id -->"+question.id);
					//getQuestion(question.id,qindex);
					questionids[qindex]=question.id;
					qindex++;
				});
				
			});
			getQuestion(questionids[qpointer],questionids);
			//qpointer++;
		
		}
		
		function getQuestion(questionId,questionids)
		{    var bp=buttonPressed;
			 $.ajax(
		           		{                             
		                   type: 'GET',
		                   url :'resteasy/qb/question/'+questionId,
		                   success : function(question) {
		                	   						  var jsonQuestion = JSON.parse(question);
		                	                          displayQuestion(jsonQuestion,questionids);
		                	                          //console.log("current answer"+answerSelectedJson.answer);
		                	                          //setting answer if answer is already given
		                	                          if (typeof answerSelectedJson !== undefined && answerSelectedJson !="") {
		                	                        	  if (jsonQuestion.questionType=='1') {
				          							        var setstr='input[type="radio"][name="qoption"][value="'+answerSelectedJson.answer+'"]';
				          						            $(setstr).prop('checked',true); }
		                	                        	  else if (jsonQuestion.questionType=='2') {
		                	                        		 //first split the answers into array
		                	                        		  var ansarray=answerSelectedJson.answer.split(",");
		                	                        		  //console.log(ansarray);
			                	           	        	       $.each(ansarray, function(index,value) {
			                	           	        	    	   //now set each of matching answer
			                	           	        	    	   //console.log(value);
				                	           	        	    	var setstr='input[type="checkbox"][name="qoption"][value="'+value+'"]';
						          						            $(setstr).prop('checked',true);
			                	        	        	    	 });
		                	                        	    }
		                	                        	  else if (jsonQuestion.questionType=='4') {
		                	                        		  $('#qanswer').attr('value',answerSelectedJson.answer);
		                	                        	  }
		                	                        	  
		                	                          }			                	                          
		                	                          
		                                            },
		           		   fail : function() {alert('There was problem in getting question. Please check internet connection.');}
		      
		           		});	
		}
			
			
        function displayQuestion(jsonQuestion,questionids)
        {
        	
        	//setting question paper Id and question Id
        	$('#userId').attr('value',userId);
        	$('#questionPaperId').attr('value',questionPaperId);
        	$('#questionId').attr('value',jsonQuestion.id);
        	$('#questionTypeId').attr('value',jsonQuestion.questionType);
        	
        	if (qpointer > 0 && buttonPressed=="next")
        	     $('#questionSection > div:first').remove();
        	if (qpointer>=0 && buttonPressed=="previous")
        		 $('#questionSection > div:first').remove();
        	
        	//$('#qdiv').remove();
        	
        	var qMarkerText='<div><b>Question</b>:'+(qpointer+1)+' of '+questionids.length+'</div>';
        	console.log(qMarkerText);
        	$('#ts-questionPointer > div:first').remove();
        	$(qMarkerText).appendTo('#ts-questionPointer');
        	
        	var qstr="";
        	if (jsonQuestion.questionType=="1") {
        	     qstr='<div class="w3-section w3-margin">'+
        	         '<p><b>Question :'+ jsonQuestion.question+'</b>&nbsp;&nbsp;('+jsonQuestion.maxMarks+')</p>'+
        	         '<p><input class="w3-radio-large" type="radio" name="qoption"  value="'+jsonQuestion.optionFirst+'">'+ jsonQuestion.optionFirst+'</p>'+
        	         '<p><input class="w3-radio-large" type="radio" name="qoption"  value="'+jsonQuestion.optionSecond+'">'+ jsonQuestion.optionSecond+'</p>'+
        	         '<p><input class="w3-radio-large" type="radio" name="qoption"  value="'+jsonQuestion.optionThird+'">'+ jsonQuestion.optionThird+'</p>'+
        	         '<p><input class="w3-radio-large" type="radio" name="qoption"  value="'+jsonQuestion.optionFourth+'">'+ jsonQuestion.optionFourth+'</p>'+
        	         '</div>';
        	} 
        	else if (jsonQuestion.questionType=="2") {
       	     	 qstr='<div class="w3-section w3-margin">'+
       	         '<p><b>Question :'+ jsonQuestion.question+'</b>&nbsp;&nbsp;('+jsonQuestion.maxMarks+')</p>'+
       	         '<p><input class="w3-check-large" type="checkbox" name="qoption"  value="'+jsonQuestion.optionFirst+'">'+ jsonQuestion.optionFirst+'</p>'+
       	         '<p><input class="w3-check-large" type="checkbox" name="qoption"  value="'+jsonQuestion.optionSecond+'">'+ jsonQuestion.optionSecond+'</p>'+
       	         '<p><input class="w3-check-large" type="checkbox" name="qoption"  value="'+jsonQuestion.optionThird+'">'+ jsonQuestion.optionThird+'</p>'+
       	         '<p><input class="w3-check-large" type="checkbox" name="qoption"  value="'+jsonQuestion.optionFourth+'">'+ jsonQuestion.optionFourth+'</p>'+
       	         '</div>';
       	       }        	
        	else if (jsonQuestion.questionType=="4") {
	       	     qstr='<div class="w3-section w3-margin">'+
			         '<p><b>Question :'+ jsonQuestion.question+'</b>&nbsp;&nbsp;('+jsonQuestion.maxMarks+')</p>'+
			         '<p><input class="w3-input" type="text" id="qanswer" name="qanswer" /></p>'+
			         '</div>';       		
        	}
      	         
        	
        	//console.log(qstr);
        	$(qstr).appendTo('#questionSection');
        	if (jsonQuestion.imageUrl=="")
        		$('#quesPhoto').attr('disabled','disabled');

        	$('#saveQuestion').attr('disabled','disabled');
        	
        	if (jsonQuestion.questionType=="1") {
				 $('input[type=radio][name=qoption]').change(function() {
					    $('#saveQuestion').removeAttr('disabled');
				    }); 
        	}
			else if (jsonQuestion.questionType=="2") {
				 $('input[type=checkbox][name=qoption]').change(function() {
					    $('#saveQuestion').removeAttr('disabled');
				   });
			  }
        	else if (jsonQuestion.questionType=="4") {
        		$('#qanswer').focus();
        		$('#qanswer').blur(function(){
        			
        			if ($('#qanswer').val()==""){
        				$('#qanswer').focus();	
        			} else {
        			      $('#saveQuestion').removeAttr('disabled');
        			}
        		});
        		
        		
        	}
			 
		 var photoid='questionPhoto';
		 if (jsonQuestion.imageUrl!="") {
			  var photohtml='<div id="exam-dispPhoto"><span>Image attached, click to zoom : </span><img id="questionPhoto-tag" src="" style="width:5%;cursor:zoom-in"  onclick="document.getElementById(\''+photoid+'\').style.display=\'block\'">'+
			  '<div id="questionPhoto" class="w3-modal" onclick="this.style.display=\'none\'">'+
			  '<span class="w3-closebtn w3-hover-red w3-container w3-padding-16 w3-display-topright">&times;</span>'+
			  '<div class="w3-modal-content w3-animate-zoom">'+
			  '<img id="exam-zoom-questionPhoto" src="" style="width:100%">'+
			  '</div>'+
			  '</div></div>';
			  $('#exam-dispPhoto').remove();
			  $('#exam-PhotoDiv').append(photohtml);
			  $('#questionPhoto-tag').attr('src',jsonQuestion.imageUrl);
			  $('#exam-zoom-questionPhoto').attr('src',jsonQuestion.imageUrl);
		      //$('#exam-zoom-questionPhoto').removeAttr('disabled');
		  }
			 
			 
			//setup next and prev buttons
        	
        	//document.getElementById('testSection').style.display='block';
        	
        	
        }
		/*	        <div class="w3-section">
	          <p><b>Question : What is Photosynthesis ?</b></p>
	          <p><input class="w3-radio-large" type="radio" name="qoption"> Light falling on water.</p>
	          <p><input class="w3-radio-large" type="radio" name="qoption"> Light falling on rocks.</p>
	          <p><input class="w3-radio-large" type="radio" name="qoption"> Light falling on sand.</p>
	          <p><input class="w3-radio-large" type="radio" name="qoption"> Light falling on plant leaves.</p>
	  
	         
	         <div class="w3-center"> <button class="w3-button w3-blue" > Prev</button>
	                    <button class="w3-button w3-green" > Next</button></div>
	        </div>	*/		
		
	      
		function openAccordian(id) {
		    var x = document.getElementById(id);

		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else { 
		        x.className = x.className.replace(" w3-show", "");
		    }
		}
		
  });
		

	      
		
		
	  
  

