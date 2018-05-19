$(document).ready(function(){
    var userId=$('#root').attr("value");
    var userLevelId=$('#userLevelId').val();
    var userLevelName=$('#userLevelName').val();
    var userRole=$('#userRole').val();
    
	$('#qps-butSearchButton').attr('disabled','disabled');
	$('#qps-subject').attr('disabled','disabled');
	populateLevel();
	
		
	function populateLevel() {
		$('#qps-level > option').remove();
		$.get('resteasy/qb/questionbank/levels')
		 .done(function(levels){
			 var jsonLevels = JSON.parse(levels);
			 $('<option value="" disabled selected>Choose Class/Level</option>').appendTo('#qps-level');
			 $.each(jsonLevels,function(index,level) {
				 console.log("level id "+level.id);
				 if (userRole=="ROLE_STUDENT" && userLevelId == level.id) {
				    $('<option value="'+level.id+'">'+level.levelName+' ( '+level.level+' ) </option>').appendTo('#qps-level');
				 } else if (userRole=="ROLE_TEACHER" && userLevelName == level.level) {
					 $('<option value="'+level.id+'">'+level.levelName+' ( '+level.level+' ) </option>').appendTo('#qps-level'); 
				 } else if (userRole=="ROLE_SUPERUSER"){
					 $('<option value="'+level.id+'">'+level.levelName+' ( '+level.level+' ) </option>').appendTo('#qps-level'); 
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
	
	$('#qps-level').unbind().change(function() {
		var selvalue=$( "#qps-level option:selected").val();
		if (selvalue != "") {
			populateSubject(selvalue);
			$('#qps-subject').removeAttr('disabled');
		  }
		});
	
    function populateSubject(levelid) {
		$('#qps-subject > option').remove();
		$.get('resteasy/qb/questionbank/subjectsForLevel/'+levelid)
		 .done(function(subjects){
			 var jsonSubjects = JSON.parse(subjects);
			 $('<option value="" disabled selected>Choose subject</option>').appendTo('#qps-subject');
			 $.each(jsonSubjects,function(index,subject) {
				 $('<option value="'+subject.id+'">'+subject.subject+'</option>').appendTo('#qps-subject');
			 });
		 })
		 .fail(function(){
	           var msg='<p>There was some problem getting subject information. Please retry after some time.</p>';
	           $('#examErrorMessage > p').remove();
	           $(msg).appendTo('#examErrorMessage');	 	        	  
	 	       document.getElementById('error').style.display='block';				
		});  	
    }
    
	$('#qps-subject').unbind().change(function() {
		var selvalue=$( "#qps-subject option:selected").val();
		if (selvalue != "") {
		    $('#qps-butSearchButton').removeAttr('disabled');
		  }
		});
	
	
	 $('#qps-butSearchButton').unbind().click(function(){
			var _csrf = $("input[name='_csrf']").val();
			var level=$( "#qps-level option:selected").val();
			var subject=$( "#qps-subject option:selected").val();
			var paperType=$('#qps-paperType option:selected').val();
			var dateFrom=$('#qps-dateFrom').val();
			var dateTo=$('#qps-dateTo').val();
			var freeText=$('#qps-freeText').val().toUpperCase();
			
			         var searchData={"userid":userId,"level":level,"subject":subject,"papertype":paperType,"datefrom":dateFrom,"dateto":dateTo,"freetext":freeText,"_csrf":_csrf};
			         //console.log(searchData);
		           $.post("resteasy/qb/questionpaper/searchQuestionPaper",searchData)
		    		
		            .done( function(data,status,xhr){ 
		          	         var jsonData=JSON.parse(data);
		          	         
		          	          currentSearchedQuestionList=jsonData.length;
			          	         if (jsonData.length == 0) {
				      		           var msg='<p>No results found for this search criteria.</p>';
				    		           $('#alertMessage > p').remove();
				    		           $(msg).appendTo('#alertMessage');	 	        	  
				    		 	       document.getElementById('alert').style.display='block';  
/*				    		 	      $('#qp-addToPaper').attr('disabled','disabled');
				    		 	      
				    		 	      //remove list of questions if they were existing previously
				    		 	      $('#questionsList > table').remove();*/
			          	         }
			          	         else {
				                         //console.log(data);
				                         prepareQuestionPaperList(jsonData);
		                             }
		                         
			                   })
			          .fail(function(data,status,xhr){
				           var msg='<p>There was some problem searching question papers. Please retry after some time.</p>';
				           $('#examErrorMessage > p').remove();
				           $(msg).appendTo('#examErrorMessage');	 	        	  
				 	       document.getElementById('error').style.display='block';
			        	  
			          });           
					
					
				 });
	 
	 
	 function prepareQuestionPaperList(paperList)
	 {
		 
		 $('#qps-paperList > table').remove();
		 
	 
		 $.each(paperList,function(index,paper){
			 
			var qPaperStr='<table class="w3-table-all"><tr><td style="width:95%"><button id="qps-'+paper.id+'" class="w3-btn-block w3-left-align w3-leftbar w3-margin-right w3-small">'+
		                  paper.qpName.toUpperCase()+'<div class="w3-right w3-small">'+
		                  '<span class="w3-margin-right w3-text-blue">( Date :<span class="w3-text-black">'+paper.updateDate+
		                  '&nbsp;&nbsp;'+paper.updateTime+'</span>&nbsp;&nbsp;  Pass :<span class="w3-text-black">'+Number(paper.qpPassPercent).toFixed(0)+'% </span> )</span></div></button></td></tr></table>';
			
			$(qPaperStr).appendTo('#qps-paperList')
			
			var afterid='qps-'+paper.id;
				  
		    var divid="qps-paperOptions"+afterid;
		    topicstr='<p class="w3-text-blue"><table id="questionTable" class="w3-table-all w3-hoverable w3-small w3-center">';
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
					$('#qps-'+paper.id).addClass('w3-pale-blue w3-border-blue');
			
				$('#qps-'+paper.id).click(function(){
			    	  var id=$(this).next().attr('id');
			    	  openAccordian(id,divid);
			      });
				
				$('#'+divid+'Export').attr('disabled','disabled');
				$('#'+divid+'AutoGenerate').attr('disabled','disabled');
				$('#'+divid+'Delete').attr('disabled','disabled');
				
				$('#'+divid+'View').unbind().click(function(){
					getQuestionPaper(paper.id);
					//document.getElementById('qps-questionPaper').style.display='block';
					
				});
				
				$('#'+divid+'ScheduleExam').unbind().click(function(){
					//getQuestionPaper(paper.id);
					document.getElementById('qp-scheduleExam').style.display='block';
					getStudents();
					
					$('#qp-butScheduleExamCancel').unbind().click(function(){
						document.getElementById('qp-scheduleExam').style.display='none';
					});
					
					$('#qp-butScheduleExamOk').unbind().click(function(){
						document.getElementById('qp-scheduleExam').style.display='none';
						scheduleExam(paper.id);
					});	
					
					$('#qp-butScheduleExamClear').unbind().click(function(){
						$('#qp-examName').val("");
						$('#qp-examDate').val("");
						$("#qp-examDuration").val("");
						$("#qp-examType").val("");
						$('#qp-examCummPercent').val("");						
					});
					
					$("#qp-examType").unbind().change(function() {
						
						var examtype=$("#qp-examType").val();
						if (examtype=="1") // practice test
							{
							  $('#qp-examCummPercent').val("0");
							  $('#qp-examCummPercent').attr('disabled','disabled');
							}
						else
							{
							  $('#qp-examCummPercent').val("");
							  $('#qp-examCummPercent').removeAttr('disabled');						  
							}
						
					});
					
					
				});				
				
			 
		 });
	 }
	 
	    //get students for whom exam to be scheduled
	    //List of all the users created by cuurent user is obtained and then students are filterted out
	 function getStudents() {
		    var levelid=$( "#qps-level option:selected").val();
		    var roleid="3"; //student role
		    var _csrf = $("input[name='_csrf']").val();
		    
		    var params={"ownerid":userId,"roleid":roleid,"levelid":levelid,"_csrf":_csrf};
		 
			$.post('resteasy/qb/questionpaper/getUsersByLevelRoleOwner',params)
			 .done(function(students){
                  showStudents(students);
			 })
			 .fail(function(){
		           var msg='<p>There was some problem getting student list. Please retry after some time.</p>';
		           $('#examErrorMessage > p').remove();
		           $(msg).appendTo('#examErrorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';				
			});		 
	 }
	 
	 
	 function showStudents(students) {
		 
		 var jsonStudents=JSON.parse(students);
		 
		 $('#qp-userList > table').remove();
		 $.each(jsonStudents,function(index,student){
			 
				$('<table class="w3-table-all"><tr><td>'+student.firstName+'&nbsp;'+student.lastName+'</td><td>'+student.email+'</td><td><input class="w3-check w3-center" type="checkbox" id="'+student.id+'" value=""/></td></tr></table>').insertAfter('#qp-students');			 
			 
		 });
		 //alert('coming to show students');
		 
	 }
	 
	    //schedule exam for the given question paper
	    function scheduleExam(paperId) {
	    	
			var _csrf = $("input[name='_csrf']").val();
			var examName=$('#qp-examName').val().toUpperCase();
			var examDate=$('#qp-examDate').val();
			var examDuration=$("#qp-examDuration option:selected").val();
			var examType=$("#qp-examType option:selected").val();
			var cummPercent=$('#qp-examCummPercent').val();
			
	    	if (isExamInfoValid(examName,examDate,examDuration,examType,cummPercent)) {
					var examData={"userId":userId,"examname":examName,"examdate":examDate,"examduration":examDuration,"examtype":examType,"cummpercent":cummPercent,
							"paperId":paperId,"students":[]};
					
					//adding student ids to object
					var items=$('#qp-userList').find(':checkbox');
					var stuindex=0;
					$.each(items,function(index,stud){
						if(stud.checked) {
							examData.students[stuindex]=stud.id; stuindex++;
						}
					});
					
					console.log(examData);
					//$.post('resteasy/qb/questionpaper/scheduleExam',examData)
					
				   $.ajaxSetup({
						      headers: {"X-CSRF-Token": _csrf,
						                "Content-Type": "application/json"
						                }
						    });
						   
				   $.ajax({
					    url: 'resteasy/qb/questionpaper/scheduleExam',
					    type: "POST",
					    data: JSON.stringify(examData),
					    processData: false,
					    contentType: "application/json"
					})					
		            .done( function(data,status,xhr){ 
			              msg='<p>Exam scheduled successfully.</p>';
			             $('#alertMessage > p').remove();
			             $(msg).appendTo('#alertMessage');	 	        	  
			 	         document.getElementById('alert').style.display='block';  
			 	         
		                  })
		           .fail(function(data,status,xhr){
			           var msg='<p>There was some problem scheduling exam. Please retry after some time.</p>';
			           $('#examErrorMessage > p').remove();
			           $(msg).appendTo('#examErrorMessage');	 	        	  
			 	       document.getElementById('error').style.display='block';
		       	  
		         });	
	    	}
	    	else
	    		{
		           var msg='<p>You have missed entering one or more information or have not selected any student. If no students appear, create students. Please enter and try again.</p>';
		           $('#examErrorMessage > p').remove();
		           $(msg).appendTo('#examErrorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';    		
	    		}
	    }
	    
	    function isExamInfoValid(examName,examDate,examDuration,examType,cummPercent){
	    	var isvalid=false;
	    	
	    	if(examName.trim() != "" && examDate.trim() !="" && examDuration.trim() != "" && examType.trim != "" && cummPercent.trim() != "" && !isNaN(cummPercent)) {
	    		
	        	var items=$('#qp-userList').find(':checkbox'); //checking if any student is selected

	   			if (items.length==0)
	   				{
                      isvalid=false; 				
	   				}
	   			else {
	   				 var isChkSelected=false;
	   				 $.each(items,function(index,chkbox){
	   					 if (chkbox.checked)
	   						isChkSelected=true;
	   				 });
	   				
		    		  if (isChkSelected) { isvalid = true; } else {isvalid = false;}
	   			}

	    	}
	    	else {
	    		isvalid=false;
	    	}
	    		
	      return isvalid;	
	    	
	    }
	    
	 
	    //get Question paper for given question paper id
	    function getQuestionPaper(paperId) {
			$.get('resteasy/qb/questionpaper/getPaper/'+paperId)
			 .done(function(paper){
                   dispPaper(paper);
                   document.getElementById('qps-questionPaper').style.display='block';
			 })
			 .fail(function(){
		           var msg='<p>There was some problem getting question paper. Please retry after some time.</p>';
		           $('#examErrorMessage > p').remove();
		           $(msg).appendTo('#examErrorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';				
			});  		    	
	    }
	    
	    
	    function dispPaper(paper) {
	    	
	    	var jsonPaper=JSON.parse(paper);

	    	$('#QpTable tr').remove();
	    	
	         var paperStr='<tr><td colspan="2"></td></tr>'+
	        	          '<tr><td>'+
	                      '<label class="w3-label">Paper - </label><b><span id="qp-paperName" ></span></b></td>'+
	                      '<td class="w3-right"><label class="w3-label">Pass Percent - </label><b><span id="qp-passPercent"></span></b></td>'+
	                      '</tr>'+
	                      '<tr class="w3-white"><td colspan="2">'+
	                      '<label class="w3-label">Topics - </label><b><span id="qp-topics"></span></b></td></tr>'+
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
	    	
	    	var paperTotal=0;
	    	var sectionTotalStr="";
	    	
	    	$.each(jsonPaper.qpSections,function(index,section){
	    		
                var sectionTotal=0;
	    		//var sectionStr='<tr class="w3-pale-blue"><td>'+section.sectionName+' ( '+section.sectionType+' )</td><td class="w3-right">(Max Marks :'+Number(section.maxMarks).toFixed(0)+')</td></tr>';
                var sectionStr='<tr class="w3-pale-blue"><td>'+section.sectionName+' ( '+section.sectionType+' )</td><td class="w3-right">(Max Marks : <span id="'+section.id+'"></span>)</td></tr>';
	    		
	    		$.each(section.questionsComplete,function(qindex,ques){
	    			sectionStr+='<tr class="w3-small"><td>'+ques.question+'</td><td class="w3-right">('+Number(ques.maxMarks).toFixed(0)+')</td></tr>';
	    			sectionTotal+=Number(ques.maxMarks);
	    		});

	    		sectionTotalStr+=sectionTotal.toString()+",";
	    		paperTotal+=sectionTotal;

	    	    $(sectionStr).appendTo('#QpTable');
	    		
	    		
	    	});
	    	
	    	var sectionTotalArr=sectionTotalStr.split(",");
	        //populating section total marks
	    	$.each(jsonPaper.qpSections,function(index,section){
	    		$('#'+section.id).html(sectionTotalArr[index]);
	    		
	    	});
	    	
	    	$('<tr class="w3-pale-blue"><td><b>Total Marks</b></td><td class="w3-right"><b>'+paperTotal+'</b></td></tr>').appendTo('#QpTable');
	    	
	    	$('<tr><td colspan="2"></td></tr>').appendTo('#QpTable');
	    	
	    	$('<tr><td colspan="2" class="w3-center"><button id="butClosePaper" class="w3-button w3-yellow w3-hover-red w3-small w3-margin">Close</button></td></tr>').appendTo('#QpTable');
	    	
	    	$('#butClosePaper').unbind().click(function(){
	    		document.getElementById('qps-questionPaper').style.display='none';
	    	});
	    	
	    }
    

		function openAccordian(id,divid) {
		    var x = document.getElementById(id);

		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else { 
		        x.className = x.className.replace(" w3-show", "");
		    }
/*		    $('#'+id).find('input').attr('disabled','disabled');
		    $('#'+divid+'Edit').removeAttr('disabled');
		    $('#'+divid+'Save').attr('disabled','disabled');*/
		}		
	
});
