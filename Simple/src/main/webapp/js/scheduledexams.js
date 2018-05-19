$(document).ready(function(){
	
    var userId=$('#root').attr("value");
    var userLevelId=$('#userLevelId').val();
    var userLevelName=$('#userLevelName').val();
    var userRole=$('#userRole').val();    
	var _gref=$(this);
	
	$('#se-butSearchButton').attr('disabled','disabled');
	$('#se-subject').attr('disabled','disabled');
	
	populateLevel();
	
	
/*	function populateLevel() {
		$('#se-level > option').remove();
		$.get('resteasy/qb/questionbank/levels')
		 .done(function(levels){
			 var jsonLevels = JSON.parse(levels);
			 $('<option value="" disabled selected>Choose Class/Level</option>').appendTo('#se-level');
			 $.each(jsonLevels,function(index,level) {
				 $('<option value="'+level.id+'">'+level.levelName+' ( '+level.level+' ) </option>').appendTo('#se-level');
			 });
		 })
		 .fail(function(){
	           var msg='<p>There was some problem getting classes / levels information. Please retry after some time.</p>';
	           $('#se-errorMessage > p').remove();
	           $(msg).appendTo('#se-errorMessage');	 	        	  
	 	       document.getElementById('error').style.display='block';				
		});
		
	}*/
	
	function populateLevel() {
		$('#level > option').remove();
		$.get('resteasy/qb/questionbank/levels')
		 .done(function(levels){
			 var jsonLevels = JSON.parse(levels);
			 $('<option value="" disabled selected>Choose Class/Level</option>').appendTo('#se-level');
			 $.each(jsonLevels,function(index,level) {
				 console.log("level id "+level.id);
				 if (userRole=="ROLE_STUDENT" && userLevelId == level.id) {
				    $('<option value="'+level.id+'">'+level.levelName+' ( '+level.level+' ) </option>').appendTo('#se-level');
				 } else if (userRole=="ROLE_TEACHER" && userLevelName == level.level) {
					 $('<option value="'+level.id+'">'+level.levelName+' ( '+level.level+' ) </option>').appendTo('#se-level'); 
				 } else if (userRole=="ROLE_SUPERUSER"){
					 $('<option value="'+level.id+'">'+level.levelName+' ( '+level.level+' ) </option>').appendTo('#se-level'); 
				 }
				 
			 });
		 })
		 .fail(function(){
	           var msg='<p>There was some problem getting classes / levels information. Please retry after some time.</p>';
	           $('#examErrorMessage > p').remove();
	           $(msg).appendTo('#examErrorMessage');	 	        	  
	 	       document.getElementById('error').style.display='block';				
		});
		
	}	
	
	$('#se-level').unbind().change(function() {
		var selvalue=$( "#se-level option:selected").val();
		if (selvalue != "") {
			populateSubject(selvalue);
			$('#se-subject').removeAttr('disabled');
		  }
		});
	
    function populateSubject(levelid) {
		$('#se-subject > option').remove();
		$.get('resteasy/qb/questionbank/subjectsForLevel/'+levelid)
		 .done(function(subjects){
			 var jsonSubjects = JSON.parse(subjects);
			 $('<option value="" disabled selected>Choose subject</option>').appendTo('#se-subject');
			 $.each(jsonSubjects,function(index,subject) {
				 $('<option value="'+subject.id+'">'+subject.subject+'</option>').appendTo('#se-subject');
			 });
		 })
		 .fail(function(){
	           var msg='<p>There was some problem getting subject information. Please retry after some time.</p>';
	           $('#se-errorMessage > p').remove();
	           $(msg).appendTo('#se-errorMessage');	 	        	  
	 	       document.getElementById('error').style.display='block';				
		});  	
    }
    
	$('#se-subject').unbind().change(function() {
		var selvalue=$( "#se-subject option:selected").val();
		if (selvalue != "") {
		    $('#se-butSearchButton').removeAttr('disabled');
		  }
		});	
	
	 $('#se-butSearchButton').unbind().click(function(){
			var _csrf = $("input[name='_csrf']").val();
			var level=$( "#se-level option:selected").val();
			var subject=$( "#se-subject option:selected").val();
			var examType=$('#se-examType option:selected').val();
			var dateFrom=$('#se-dateFrom').val();
			var dateTo=$('#se-dateTo').val();
			var freeText=$('#se-freeText').val().toUpperCase();
			
			         var searchData={"userid":userId,"level":level,"subject":subject,"examtype":examType,"datefrom":dateFrom,"dateto":dateTo,"freetext":freeText,"_csrf":_csrf};
			         //console.log(searchData);
		           $.post("resteasy/qb/scheduledexam/searchScheduledExams",searchData)
		    		
		            .done( function(data,status,xhr){ 
		          	         var jsonData=JSON.parse(data);
		          	         
		          	          currentSearchedExamList=jsonData.length;
			          	         if (jsonData.length == 0) {
				      		           var msg='<p>No results found for this search criteria.</p>';
				    		           $('#se-alertMessage > p').remove();
				    		           $(msg).appendTo('#se-alertMessage');	 	        	  
				    		 	       document.getElementById('alert').style.display='block';  

			          	         }
			          	         else {
				                         //console.log(data);
				                         prepareScheduledExamsList(jsonData);
		                             }
		                         
			                   })
			          .fail(function(data,status,xhr){
				           var msg='<p>There was some problem searching scheduled exams. Please retry after some time.</p>';
				           $('#se-errorMessage > p').remove();
				           $(msg).appendTo('#se-errorMessage');	 	        	  
				 	       document.getElementById('error').style.display='block';
			        	  
			          });           
					
					
				 });	
	 
	 function prepareScheduledExamsList(examsList) {
		 $('#se-examList > table').remove();
		 
		 
		 $.each(examsList,function(index,exam){
			 
			var qPaperStr='<table class="w3-table-all"><tr><td style="width:95%"><button id="se-'+exam.id+'" class="w3-btn-block w3-left-align w3-leftbar w3-margin-right w3-small">'+
		                  exam.exam.toUpperCase()+'<div class="w3-right w3-small">'+
		                  '<span class="w3-margin-right w3-text-blue">( Created Date :<span class="w3-text-black">'+exam.updateDate+
		                  '</span>&nbsp;&nbsp;Time :<span class="w3-text-black">'+exam.updateTime+'</span> )</span></div></button></td><td><span id="se-del-'+exam.id+'" class="w3-closebtn w3-small">&times;</span></td></tr></table>';
			
			$(qPaperStr).appendTo('#se-examList');
			
			if (index%2 >0)
				$('#se-'+exam.id).addClass('w3-pale-yellow w3-border-yellow');
			else
				$('#se-'+exam.id).addClass('w3-pale-blue w3-border-blue');			
			
			var afterid='se-'+exam.id;
				  
		    var divid="se-studentsList-"+afterid;
			   $('<div id="'+divid+'" class="w3-accordion-content w3-container">'+
			   '</div>').insertAfter('#'+afterid);
/*		    topicstr='<p class="w3-text-blue"><table id="questionTable" class="w3-table-all w3-hoverable w3-small w3-center">';
		    topicstr+='<tr><td><button id="'+divid+'View" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">View Paper</button></td>';
		    topicstr+='<td><button id="'+divid+'Export" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">Export Paper</button></td>';
		    topicstr+='<td><button id="'+divid+'AutoGenerate" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">Auto Generate</button></td>';
		    topicstr+='<td><button id="'+divid+'ScheduleExam" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">Schedule Exam</button></td>';
		    topicstr+='<td><button id="'+divid+'Delete" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">Delete Paper</button></td></tr></table></p>';
		    
			   $('<div id="'+divid+'" class="w3-accordion-content w3-container">'+topicstr+
			   '</div>').insertAfter('#'+afterid);		    
			
				if (index%2 >0)
					$('#qps-'+paper.id).addClass('w3-pale-yellow w3-border-yellow');
				else
					$('#qps-'+paper.id).addClass('w3-pale-blue w3-border-blue');*/
			   
			   $('#se-del-'+exam.id).unbind().click(function(){
					$.get('resteasy/qb/scheduledexam/students/'+exam.id)
					 .done(function(students){
						 
					 })
					 .fail(function(){
				           var msg='<p>There was some problem removing scheduled exam. Please retry after some time.</p>';
				           $('#se-errorMessage > p').remove();
				           $(msg).appendTo('#se-errorMessage');	 	        	  
				 	       document.getElementById('error').style.display='block';							 
					 });
			   });
			
				$('#se-'+exam.id).unbind().click(function(){
			    	   var id=$(this).next().attr('id');
			    	   var qpId=exam.qpId;
			    	   console.log("Question Paper:"+qpId);
			    	  
						$.get('resteasy/qb/scheduledexam/students/'+exam.id)
						 .done(function(students){
							 var jsonStudents = JSON.parse(students);
			                 //prepare student string and append here
                                 $('#'+divid+' > p').remove();
                                 $('#'+divid+' > table').remove();
                                 $('#'+divid+' > button').remove();
                                 
								 var titlestr='<p class="w3-text-blue w3-medium">Students assigned to exam &nbsp;&nbsp;'+
					              			  '<span class=" w3-text-blue w3-small w3-right">( Exam Date :<span class="w3-text-black">'+exam.examDate+'</span>'+								 
								              '&nbsp;&nbsp;Duration :<span class="w3-text-black">'+Number(exam.examDuration).toFixed(0)+' mins </span>)</span></p>';

								 //titlestr+='<button id="se-but'+divid+'" class="w3-btn-block w3-left-align w3-leftbar w3-margin-right w3-small">Unassign student</button>';
								 titlestr+='<button id="se-butUnAssign'+divid+'" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Unassign student<i class="w3-margin-left fa fa-search"></i></button>';
								 titlestr+='<button id="se-butAnswerSheet'+divid+'" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Answer Sheet<i class="w3-margin-left fa fa-search"></i></button>';
								 titlestr+='<button id="se-butResultSheet'+divid+'" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Summarized Result<i class="w3-margin-left fa fa-search"></i></button>';
								 titlestr+='<button id="se-butQuesPaper'+divid+'" class="w3-button w3-yellow w3-small w3-right w3-margin w3-hover-red">Question Paper<i class="w3-margin-left fa fa-search"></i></button>';								 
								 titlestr+='<table class="w3-table-all">';
								 
								 //console.log(jsonStudents);
								 
								 if (jsonStudents.length == 0) {
									 $('#se-butUnAssign').attr('disabled','disabled');
									 $('#se-butAnswerSheet').attr('disabled','disabled');
									 $('#se-butResultSheet').attr('disabled','disabled');
									 $('#se-butQuesPaper').attr('disabled','disabled');
								 }
								 
								 //$(titlestr).appendTo('#'+divid);
								 $.each(jsonStudents,function(index,student){
									 
										//$('<tr style="width:95%"><td class="w3-small">'+student.firstName+'&nbsp;'+student.lastName+'</td><td class="w3-small">'+student.email+'</td><td><input class="w3-check" type="checkbox" id="'+student.id+'" value=""/></td></tr>').appendTo('#'+divid);			 
									 titlestr+='<tr id=row-'+student.id+' style="width:95%"><td class="w3-small">'+student.firstName+'&nbsp;'+student.lastName+'</td><td class="w3-small">'+student.email+'</td>';
									 if (Number(student.eep.id) == -1 ) {
										 titlestr+='<td></td><td class="w3-right"><input class="w3-check-medium" type="checkbox" id="'+student.id+'" value=""/></td></tr>';
									 } else {
										 titlestr+='<td><span class=" w3-text-blue w3-small w3-right">Attemped on :<span class="w3-text-black">'+student.eep.attemptDate+'</span>&nbsp;&nbsp;Score :<span class="w3-text-black">'+student.eep.score+'</span></span></td><td><input class="w3-check-medium" type="checkbox" id="'+student.eep.id+'" value="attempted"/></td></tr>';
									 }
									 
/*									 $('#ast'+student.id).unbind().click(function(){
										 alert("show answer sheet"+student.id);
									 });*/
									 
								 });
								 titlestr+='</table>';
								 //$('</table>').appendTo('#'+divid);
								 $(titlestr).appendTo('#'+divid);
								 

								 
								 $('#se-butUnAssign'+divid).unbind().click(function(){
									 
									 unAssignStudents(divid,jsonStudents);

								   });
								 
								 
								 $('#se-butAnswerSheet'+divid).unbind().click(function(){
									 
									 getAnswerSheet(divid);

								   });
								 							 
								 $('#se-butResultSheet'+divid).unbind().click(function(){
									 
									 showResultSheet(qpId,jsonStudents);

								   });								 
								 
									$('#se-butQuesPaper'+divid).unbind().click(function(){
										getQuestionPaper(qpId);
										//document.getElementById('qps-questionPaper').style.display='block';
										
									});							 
								 
								 
								 
								 

						 })
						 .fail(function(){
					           var msg='<p>There was some problem getting list of students scheduled for exam. Please retry after some time.</p>';
					           $('#se-errorMessage > p').remove();
					           $(msg).appendTo('#se-errorMessage');	 	        	  
					 	       document.getElementById('error').style.display='block';				
						}); 
			    	  
			    	  openAccordian(id,divid);
			      });
				
		 });
	 }
	 

function unAssignStudents(divid,jsonStudents) {

	 var items=$('#'+divid).find(':checkbox'); //checking if any student is selected
	 var checkedItems=$('#'+divid).find('input:checked');
	 // console.log(jsonStudents.length);
	 //console.log(items.length);
	 //console.log(checkedItems.length);

			if (checkedItems.length==0)
				{
		           var msg='<p>Please select at least one student.</p>';
	           $('#se-alertMessage > p').remove();
	           $(msg).appendTo('#se-alertMessage');	 	        	  
	 	       document.getElementById('alert').style.display='block';  				
				}
			else if (jsonStudents.length == 1 || jsonStudents.length == checkedItems.length) //there should be at least one student 
			       {
		           var msg='<p>You cannot unassign all the students.</p>';
	           $('#se-alertMessage > p').remove();
	           $(msg).appendTo('#se-alertMessage');	 	        	  
	 	       document.getElementById('alert').style.display='block'; 
			      }	
			else
				 {
              //console.log('coming');
				  //unassigning students
				   $.each(checkedItems,function(index,stud){
   				   //only those students who have not attempted the exam are unassigned
					   //console.log(stud.value);
					   if (stud.value !== "attempted") { //unassign students only if they have not attempted
						   
		   				   $.get('resteasy/qb/scheduledexam/unassign/student/'+stud.id)
		   				   .done(function(result){
		   					   var jsonResult=JSON.parse(result); // object received is {unassigned:true}
		   					     if (jsonResult.unassigned) {
		   					    	 //remove student row from display
		   					    	 $('#row-'+stud.id).remove();
		   					    	 
		   					    	// $.jsonStudents.find(":id="+stud.id)
		   					    	var index = jsonStudents.findIndex(function(item, i){
		   					    	  return item.id === stud.id
		   					    	});
		   					    	jsonStudents.splice(index,1);
		   					    	//console.log(jsonStudents);
		   					    	 
		   					     }
		   				      })
		   				   .fail(function(){
					           var msg='<p>There was some problem unassigning students. Please retry after some time.</p>';
					           $('#se-errorMessage > p').remove();
					           $(msg).appendTo('#se-errorMessage');	 	        	  
					 	       document.getElementById('error').style.display='block';									   					   
		   				   });
					   }
					   
				   });
				   
				
				 }	
        }


		function getAnswerSheet(divid) {
			
			 var checkedItems=$('#'+divid).find('input:checked');
			 // console.log(jsonStudents.length);
			 //console.log(items.length);
			 //console.log(typeof checkedItems.length);
             var attemptId="";
             
				if (checkedItems.length == 0 || checkedItems.length > 1 )
					{
/*				       var msg='<p>Please select at least one student and only one student.</p>';
			           $('#se-alertMessage > p').remove();
			           $(msg).appendTo('#se-alertMessage');	 	        	  
			 	       document.getElementById('alert').style.display='block'; */ 				
					}
				else {
					$.each(checkedItems,function(index,stud){
						if (stud.value=="attempted") {
							attemptId=stud.id;  	
						}
					});
				}
			
		     //console.log("attempt id -"+attemptId);
		     if (attemptId != "") {
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
				                       	                  $('#se-errorMessage > p').remove();
				                       	                  $(msg).appendTo('#se-errorMessage');
				                       	                  document.getElementById('error').style.display='block';});
				
			
		     }
		     else {
			       var msg='<p>Either selected student has not attempted the exam or you have not selected single student.</p>';
		           $('#se-alertMessage > p').remove();
		           $(msg).appendTo('#se-alertMessage');	 	        	  
		 	       document.getElementById('alert').style.display='block';  			    	 
		     }
		}



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

     
	    //get Question paper for given question paper id
	    function getQuestionPaper(paperId) {
			$.get('resteasy/qb/questionpaper/getPaper/'+paperId)
			 .done(function(paper){
                   displayPaper(paper);
                   document.getElementById('qps-questionPaper').style.display='block';
			 })
			 .fail(function(){
		           var msg='<p>There was some problem getting question paper. Please retry after some time.</p>';
		           $('#se-errorMessage > p').remove();
		           $(msg).appendTo('#se-errorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';				
			});  		    	
	    }
	    
	    
	    function displayPaper(paper) {
	    	
	    	var jsonPaper=JSON.parse(paper);

	    	$('#QpTable tr').remove();
	    	
	         var paperStr='<tr><td colspan="2"></td></tr>'+
	        	          '<tr><td>'+
	                      '<label class="w3-label w3-large">Paper :</label><span id="qp-paperName" class="w3-large"></span></td>'+
	                      '<td class="w3-right"><label class="w3-label w3-large">Pass Percent :</label><span id="qp-passPercent" class="w3-large"></span></td>'+
	                      '</tr>'+
	                      '<tr class="w3-white"><td colspan="2">'+
	                      '<label class="w3-label w3-medium">Topics :</label><span id="qp-topics" class="w3-medium"></span></td></tr>'+
	                      '<tr><td colspan="2"></td></tr>';
	         
	         $(paperStr).appendTo('#QpTable');
	         var topicStr="";
	         $.each(jsonPaper.qpTopics,function(index,topic){
	        	 topicStr+=topic.topic+' , ';
	        	 
	         });
	         
	         var pos=topicStr.lastIndexOf(",");
	         topicStr=topicStr.substring(0, pos);
	         
	         $('#qp-topics').html(topicStr);
	    	
	    	$('#qp-paperName').html(jsonPaper.qpName);
	    	$('#qp-passPercent').html(Number(jsonPaper.qpPassPercent).toFixed(0));
	    	
	    	$.each(jsonPaper.qpSections,function(index,section){
	    		

	    		var sectionStr='<tr class="w3-medium w3-pale-blue"><td><i>'+section.sectionName+' ( '+section.sectionType+' )</i></td><td class="w3-right"><i>(Max Marks :'+Number(section.maxMarks).toFixed(0)+')</i></td></tr>';
	    		
	    		$.each(section.questionsComplete,function(qindex,ques){
	    			sectionStr+='<tr class="w3-small"><td>'+ques.question+'</td><td class="w3-right">('+Number(ques.maxMarks).toFixed(0)+')</td></tr>';
	    			
	    		});
	    		

	    	    $(sectionStr).appendTo('#QpTable');
	    		
	    		
	    	});
	    	
	    	$('<tr><td colspan="2"></td></tr>').appendTo('#QpTable');
	    	
	    	$('<tr><td colspan="2" class="w3-center"><button id="butClosePaper" class="w3-button w3-yellow w3-hover-red w3-small w3-margin">Close</button></td></tr>').appendTo('#QpTable');
	    	
	    	$('#butClosePaper').unbind().click(function(){
	    		document.getElementById('qps-questionPaper').style.display='none';
	    	});
	    	
	    }   
     
     function showResultSheet(paperId,jsonStudents) {
			$.get('resteasy/qb/questionpaper/getBriefPaper/'+paperId)
			 .done(function(paper){
                  displayScoreSheet(paper,jsonStudents);
                  document.getElementById('se-scoreSheet').style.display='block';
			 })
			 .fail(function(){
		           var msg='<p>There was some problem getting question paper. Please retry after some time.</p>';
		           $('#se-errorMessage > p').remove();
		           $(msg).appendTo('#se-errorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';				
			}); 
     }
     
     function displayScoreSheet(paper,jsonStudents) {
    	 
    	    var jsonPaper=JSON.parse(paper);
    	 
	    	$('#dispScoreSheet > header').remove();
	    	$('#dispScoreSheet > table').remove();
	    	
	    	var paperStr='<header class="w3-container w3-light-grey">'+ 
	        				'<span class="w3-xlarge w3-text-blue">Score Sheet</span>'+
	        			  '</header>'+
	        			  '<table id="scoreTable" class="w3-table w3-border w3-small w3-center w3-margin-bottom">';

           
	    	
	         paperStr+='<tr><td colspan="2"></td></tr>'+
	        	          '<tr><td>'+
	                      '<label class="w3-label w3-large">Paper :</label><span id="qp-paperName" class="w3-large">'+jsonPaper.qpName+'</span></td>'+
	                      '<td class="w3-right"><label class="w3-label w3-large">Pass Percent :</label><span id="qp-passPercent" class="w3-large">'+Number(jsonPaper.qpPassPercent).toFixed(0)+'</span></td>'+
	                      '</tr>'+
	                      '<tr class="w3-white"><td td colspan="2">'+
	                      '<label class="w3-label w3-medium">Prepared on :</label><span id="qp-updatedOn" class="w3-medium">'+jsonPaper.updateDate+'</span></td></tr>'+
	                      '<tr><td colspan="2"><label class="w3-label w3-medium">Prepared by :</label><span id="qp-updatedBy" class="w3-medium">'+jsonPaper.updatedBy+'</span></td></tr></table>';
	         
/*		    	$('#qp-paperName').html();
		    	$('#qp-passPercent').html(); 
		    	$('#qp-updatedOn').html();
		    	$('#qp-updatedBy').html();*/
	         
	         $(paperStr).appendTo('#dispScoreSheet');

	         
	         
/*	         var topicStr="";
	         $.each(jsonPaper.qpTopics,function(index,topic){
	        	 topicStr+=topic.topic+' , ';
	        	 
	         });
	         
	         var pos=topicStr.lastIndexOf(",");
	         topicStr=topicStr.substring(0, pos);
	         
	         $('#qp-topics').html(topicStr);*/
	    	

	    	
	    	
	    	
	         $('<tr><td colspan="2"><div id="se-studentScoreList" class="w3-center"></div></td></tr>').appendTo('#scoreTable');
	         
		     var studScoreStr='<table id="studentScores" class="w3-table-all w3-hoverable w3-small w3-center">'+
				   '<thead><tr class="w3-light-blue"><th>Student</th><th>Email</th><th>Phone</th><th>Score</th><th>Attempted on</th></thead>';
		     
		     $.each(jsonStudents,function(index,student){
		    	 var phoneStr="Not available";
		    	 if (student.phone!=null)
		    		 {
		    		   phoneStr=student.phone;
		    		 }
		    	 studScoreStr+='<tr><td>'+student.firstName+'&nbsp;'+student.lastName+'</td><td>'+student.email+'</td><td>'+ phoneStr +'</td><td>'+student.eep.score+'</td><td>'+student.eep.attemptDate+'</td></tr>';
		    	 
		    	 
		     });
	         
	         studScoreStr+='</table>';
	         $(studScoreStr).appendTo('#se-studentScoreList');	    	
	    	
	    	
	    	
	    	
	    	$('<tr><td colspan="2" class="w3-center"><button id="butCloseScoreSheet" class="w3-button w3-yellow w3-hover-red w3-small w3-margin">Close</button></td></tr>').appendTo('#scoreTable');
	    	
	    	$('#butCloseScoreSheet').unbind().click(function(){
	    		document.getElementById('se-scoreSheet').style.display='none';
	    	});	    	
     }

	 
		function openAccordian(id,divid) {
		    var x = document.getElementById(id);

		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else { 
		        x.className = x.className.replace(" w3-show", "");
		    }

		}		 
	
});