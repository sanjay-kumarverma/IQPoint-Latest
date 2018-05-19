$(document).ready(function(){
	
		//disabling elements on question bank load
	     var userId=$('#root').attr("value");
	     var userRole=$('#userRole').val();
	     var qpIndex=1;
	     var currentSearchedQuestionList=0;
	     var currentSearchedQuestionType=0;
	    //console.log("User id :"+userId);
		disableElements();
		populateLevel();
		
		//defining a blank question paper json
		var _csrf = $("input[name='_csrf']").val();
		var mQPaper={userid:userId,name:"",passpercent:"",level:"",subject:"",topic:"",sections:[]};
		//var mQPSection={"sectionid":"","sectionname":"","sectiontype":"","maxmarks":"","questions":[]};
		var mCurrentSection="";
		
		$('#qp-butSaveQuestionPaperAsTemplate').unbind().click(function(){
			var asTemplate=true;
			savePaper(asTemplate);
		});
		
		
		$('#qp-butSaveQuestionPaper').unbind().click(function(){
			var asTemplate=false;
			savePaper(asTemplate);		
		});
		
		function savePaper(asTemplate) {
					mQPaper.name=$('#qp-paperName').val();
					mQPaper.passpercent=$('#qp-passPercent').val();
					mQPaper.level=$( "#level option:selected").val();
					mQPaper.subject=$( "#subject option:selected").val();
					mQPaper.topic=$( "#topic option:selected").val();
					
					//var cannotSave=false;
					
					if (mQPaper.name.trim()=="" || mQPaper.passpercent.trim()=="" || mQPaper.sections.length==0 || isNaN(mQPaper.passpercent.trim())) {
				           var msg='<p>You have missed providing one or more question paper details or in right format. Please enter and try again.</p>';
				           $('#alertMessage > p').remove();
				           $(msg).appendTo('#alertMessage');	 	        	  
				 	       document.getElementById('alert').style.display='block'; 				
					}
					else {
							   var IssueInSection=false;
							   $.each(mQPaper.sections,function(index,section){
							     if (!asTemplate) {
								   if ( section.sectionname.trim() == "" || section.sectiontype.trim() == "" || section.maxmarks.trim()=="" || section.questions.length==0) 
								   {
									  IssueInSection=true; }
							     } else {
							    	 if ( section.sectionname.trim() == "" || section.sectiontype.trim() == "" || section.maxmarks.trim()=="") 
									   {
										  IssueInSection=true; }
								     }							    	 
							    	 
							   });
					        if (IssueInSection) {
					           var msg='<p>You have missed providing one or more section details. Please correct and try again.</p>';
					           $('#alertMessage > p').remove();
					           $(msg).appendTo('#alertMessage');	 	        	  
					 	       document.getElementById('alert').style.display='block'; 	
					        }
							   else {
								   //var jstr=JSON.stringify(mQPaper);
							   $.ajaxSetup({
									      headers: {"X-CSRF-Token": _csrf,
									                "Content-Type": "application/json"
									                }
									    });
								   
								   $.ajax({
									    url: 'resteasy/qb/questionbank/saveQuestionPaper',
									    type: "POST",
									    data: JSON.stringify(mQPaper),
									    processData: false,
									    contentType: "application/json"
									})
						            .done( function(data,status,xhr){ 
						          	               //var jsonData=JSON.parse(data);
							      		           var msg='<p>Question paper saved successfully.</p>';
							    		           $('#alertMessage > p').remove();
							    		           $(msg).appendTo('#alertMessage');	 	        	  
							    		 	       document.getElementById('alert').style.display='block';  
							    		 	       $('#qp-addToPaper').attr('disabled','disabled');
							    		 	       $('#qp-butSaveQuestionPaper').attr('disabled','disabled');
							    		 	      
							    		 	       //remove question paper details
							    		 	       //$('#questionsList > table').remove();
						                   })
							          .fail(function(data,status,xhr){
								           var msg='<p>There was some problem saving question paper. Please retry after some time.</p>';
								           $('#examErrorMessage > p').remove();
								           $(msg).appendTo('#examErrorMessage');	 	        	  
								 	       document.getElementById('error').style.display='block';
							        	  
							          }); 
								   
								   
								}			        
					       
					}	
		}
					
		
		$('#qp-butClearQuestionPaper').unbind().click(function(){
			$('#qp-questionPaper').remove();
			$('#qp-butSaveQuestionPaper').removeAttr('disabled');
			
			// clearing question paper text boxes
			$('#qp-paperName').val("");
			$('#qp-passPercent').val("");			
			$('#qp-section').val("");
			$('#qp-ques-type').val("");
			$('#qp-maxMarks').val("");
			
			//enabling the questions that were disabled when added
			
			        var items=$('#questionsList').find(':checkbox');
				    $.each(items,function(index,chkbox)
   				    		{
   				    	        if (chkbox.disabled) {
	    	        		  	       chkbox.disabled=false;
		    	        		  	   chkbox.checked=false;
   				    	        }
   				    		});
			//initializing Question Paper in-memory object
			mQPaper={userid:userId,name:"",passpercent:"",level:"",subject:"",topic:"",sections:[]};
			
			
		});		
		
		var removeByAttr = function(arr, attr, value){
		    var i = arr.length;
		    while(i--){
		       if( arr[i] 
		           && arr[i].hasOwnProperty(attr) 
		           && (arguments.length > 2 && arr[i][attr] === value ) ){ 

		           arr.splice(i,1);

		       }
		    }
		    return arr;
		}		
		
		$('#qp-butRemoveSection').unbind().click(function(){
			
			//If no section is currently selected show appropriate message
			
			if (mCurrentSection=="") {
		           var msg='<p>There are no sections or you have not currently selected any section.</p>';
		           $('#alertMessage > p').remove();
		           $(msg).appendTo('#alertMessage');	 	        	  
		 	       document.getElementById('alert').style.display='block'; 				
				
			}
			else {
						//removing the section from display
			        	var nextDiv=$('#'+mCurrentSection).next('div').attr('id');
			        	
			        	$('#'+nextDiv).remove();  //remove div that contains question
						$('#'+mCurrentSection).remove(); //remove section button
						
						//removing section from in-memory object mQPaper
						var sectionToRemove="";
						$.each(mQPaper.sections,function(index,section){
							//console.log(index);
							//console.log(section.sectionid);
							
							if (mCurrentSection==section.sectionid)
								{ sectionToRemove=section.sectionid;
								
								//enabling the questions that were disabled when added
						        var items=$('#questionsList').find(':checkbox');
							    $.each(items,function(index,chkbox)
			   				    		{
			   				    	        if (section.questions.indexOf(chkbox.id) != -1) {
				    	        		  	       chkbox.disabled=false;
					    	        		  	   chkbox.checked=false;
			   				    	        }
			   				    		});							
								
								}
							    	
							
						});
						
					    
						//now remove the section from in-memory object
						 //mQPaper.sections.splice(sectionIndexToRemove,1);
						removeByAttr(mQPaper.sections,'sectionid',sectionToRemove);					    
						
						//setting current section to pointing to nothing
						mCurrentSection="";	
						
						qpIndex-=1; //setting the Question paper index to sections
				
			}

			
			console.log(mQPaper);
			
		});
		

		
		$('#level').unbind().change(function() {
			var selvalue=$( "#level option:selected").val();
			if (selvalue != "") {
				populateSubject(selvalue);
				$('#subject').removeAttr('disabled');
 				if (userRole=="ROLE_TEACHER" || userRole=="ROLE_ORGANIZATION") 
					$('#qb-butAddSubjects').attr('disabled','disabled');
				else if (userRole=="ROLE_SUPERUSER")
					$('#qb-butAddSubjects').removeAttr('disabled');				

			  }
			});

		$('#subject').unbind().change(function() {
			var selvalue=$( "#subject option:selected").val();
			if (selvalue != "") {
				populateTopic(selvalue);
				populateQuestionTypes();
				$('#topic').removeAttr('disabled');
				$('#qb-butAddTopics').removeAttr('disabled'); }
			});
			
		$('#topic').unbind().change(function() {
			var selvalue=$( "#topic option:selected").val();
			if (selvalue != "") {
				$('#questionType').removeAttr('disabled');
				$('#dateFrom').removeAttr('disabled');
				$('#dateTo').removeAttr('disabled');
				$('#freeText').removeAttr('disabled'); 
				//$('#butAddQuestionType').removeAttr('disabled');
				//$('#butSearch').removeAttr('disabled');
				//$('#butAddQuestion').removeAttr('disabled');
			}
		});
			
		$('#qb-questionType').unbind().change(function() {
			var selvalue=$( "#qb-questionType option:selected").val();
			if (selvalue != "") {
				$('#butAddQuestion').removeAttr('disabled');
				$('#butSearch').removeAttr('disabled');
			}
		});
			
		 $('#butSearch').unbind().click(function(){
			var _csrf = $("input[name='_csrf']").val();
			var level=$( "#level option:selected").val();
			var subject=$( "#subject option:selected").val();
			var topic=$( "#topic option:selected").val();
			var questionType=$('#qb-questionType option:selected').val();
			var dateFrom=$('#dateFrom').val();
			var dateTo=$('#dateTo').val();
			var freeText=$('#freeText').val().toUpperCase();
			
			currentSearchedQuestionType=questionType;
			
            var searchData={"level":level,"subject":subject,"topic":topic,"questiontype":questionType,"datefrom":dateFrom,"dateto":dateTo,"freetext":freeText,"_csrf":_csrf};
            //console.log(searchData);
                   $('#spinner').show();
            
		           $.post("resteasy/qb/questionbank/searchQuestionBank",searchData)
		    		
		            .done( function(data,status,xhr){ 
		          	         var jsonData=JSON.parse(data);
		          	         
		          	         currentSearchedQuestionList=jsonData.length;
			          	         if (jsonData.length == 0) {
				      		           var msg='<p>No results found for this search criteria.</p>';
				    		           $('#alertMessage > p').remove();
				    		           $(msg).appendTo('#alertMessage');	 	        	  
				    		 	       document.getElementById('alert').style.display='block';  
				    		 	      $('#qp-addToPaper').attr('disabled','disabled');
				    		 	      
				    		 	      //remove list of questions if they were existing previously
				    		 	      $('#questionsList > table').remove();
			          	         }
			          	         else {
		                         //console.log(data);
		                         prepareQuestionList(jsonData);
		                         $('#qp-addToPaper').removeAttr('disabled'); }
		                         
			                   })
			          .fail(function(data,status,xhr){
				           var msg='<p>There was some problem searching question bank. Please retry after some time.</p>';
				           $('#examErrorMessage > p').remove();
				           $(msg).appendTo('#examErrorMessage');	 	        	  
				 	       document.getElementById('error').style.display='block';
			        	  
			          });           
					
				    $('#spinner').hide();
				 });
		 

				
				
			//});	
		
		$('#qb-butAddLevels').unbind().click(function(){
			document.getElementById('qb-addLevels').style.display='block';
			
			$('#qb-butOk').unbind().click(function() {
				document.getElementById('qb-addLevels').style.display='none';
				var mQualification=$('#qb-qualification').val().toUpperCase();
				var mCourse=$('#qb-course').val().toUpperCase();
				var _csrf = $("input[name='_csrf']").val();
				
                //validate course, it should not already exist
				if (!validateCourse(mCourse) || mCourse.trim()=="" || mQualification.trim()=="") {
					
			           var msg='<p>The course you entered already exists or any of the enteries is blank, please try again.</p>';
			           $('#examErrorMessage > p').remove();
			           $(msg).appendTo('#examErrorMessage');	 	        	  
			 	       document.getElementById('error').style.display='block';				
					
				}
				else {
					
					var topicData={"qualification":mQualification,"course":mCourse,"_csrf":_csrf};
					
							           $.post("resteasy/qb/questionbank/addNewCourse",topicData)
							            .done( function(data,status,xhr){ 
							            	     appendToCourseList(data);
					      		                 msg='<p>New qualification / course was added successfully.</p>';
						    		             $('#alertMessage > p').remove();
						    		             $(msg).appendTo('#alertMessage');	 	        	  
						    		 	         document.getElementById('alert').style.display='block';  
						    		 	         
								                   })
								          .fail(function(data,status,xhr){
									           var msg='<p>There was some problem adding new qualification / course. Please retry after some time.</p>';
									           $('#examErrorMessage > p').remove();
									           $(msg).appendTo('#examErrorMessage');	 	        	  
									 	       document.getElementById('error').style.display='block';
								        	  
								          });					
					
				}
					
				
			});
			$('#qb-butCancel').unbind().click(function() {
				document.getElementById('qb-addLevels').style.display='none';
			});			
			
			
	
           
           
		});
		
		
		
		function validateCourse(mCourse) {
			
			var retval=true;
			$("#level > option").each(function() {
			    if (this.value!="")
			    	{
			    	    var levelstr=this.text.split(" ");
			    	    var level=levelstr[0].toUpperCase();
			    	    if (level==mCourse.toUpperCase()) {
				    	    	retval=false;
			    	    }
			    	}
			    
			});		
			
			return retval;
		}
		
		function appendToCourseList(course) {
			
			var jsonCourse=JSON.parse(course);
			
		    $('#level').append(
		            $("<option></option>")
		              .attr("value", jsonCourse.id)
		              .text(jsonCourse.levelName+' ('+jsonCourse.level+' )')
		        );
			
		}		
		

			$('#qb-butAddSubjects').unbind().click(function(){
				document.getElementById('qb-addSubjects').style.display='block';
				var levelVal=$( "#level option:selected").val();
				var levelText=$( "#level option:selected").html();
				
				$('#qb-addSubjectLevel').val(levelText);
				$('#qb-addSubjectLevel').attr('disabled','disabled');
				
				$('#qb-butSubjectsOk').unbind().click(function() {
					document.getElementById('qb-addSubjects').style.display='none';
					
					var mSubject=$('#qb-addSubjectSubject').val().toUpperCase();
					var _csrf = $("input[name='_csrf']").val();
					
	                //validate course, it should not already exist
					if (!validateSubject(mSubject) || mSubject.trim()=="") {
						
				           var msg='<p>The subject you entered already exists or entry is blank, please try again.</p>';
				           $('#examErrorMessage > p').remove();
				           $(msg).appendTo('#examErrorMessage');	 	        	  
				 	       document.getElementById('error').style.display='block';				
						
					}
					else {
						
						var subjectData={"levelid":levelVal,"subject":mSubject,"_csrf":_csrf};
						
								           $.post("resteasy/qb/questionbank/addNewSubject",subjectData)
								            .done( function(data,status,xhr){ 
								            	     appendToSubjectList(data);
						      		                 msg='<p>New subject was added successfully.</p>';
							    		             $('#alertMessage > p').remove();
							    		             $(msg).appendTo('#alertMessage');	 	        	  
							    		 	         document.getElementById('alert').style.display='block';  
							    		 	         
									                   })
									          .fail(function(data,status,xhr){
										           var msg='<p>There was some problem adding new subject. Please retry after some time.</p>';
										           $('#examErrorMessage > p').remove();
										           $(msg).appendTo('#examErrorMessage');	 	        	  
										 	       document.getElementById('error').style.display='block';
									        	  
									          });					
						
					}				
					
					
					
					
				});
				$('#qb-butSubjectsCancel').unbind().click(function() {
					document.getElementById('qb-addSubjects').style.display='none';
				});			
				
			});	
			
			function validateSubject(mSubject) {
				
				var retval=true;
				$("#subject > option").each(function() {
				    if (this.value!="")
				    	{
				    	    var subject=this.text.toUpperCase();
				    	    if (subject==mSubject.toUpperCase()) {
					    	    	retval=false;
				    	    }
				    	}
				    
				});		
				
				return retval;
			}
			
			function appendToSubjectList(subject) {
				
				var jsonSubject=JSON.parse(subject);
				
			    $('#subject').append(
			            $("<option></option>")
			              .attr("value", jsonSubject.id)
			              .text(jsonSubject.subject)
			        );
				
			}				
	
			
			$('#qb-butAddTopics').unbind().click(function(){
				document.getElementById('qb-addTopics').style.display='block';
				var levelVal=$( "#level option:selected").val();
				var levelText=$( "#level option:selected").html();
				
				var subjectVal=$( "#subject option:selected").val();
				var subjectText=$( "#subject option:selected").html();	
				
				//var topicVal=$( "#topic option:selected").val();
				//var topicText=$( "#topic option:selected").html();				
				
				$('#qb-addLevelTopic').val(levelText);
				$('#qb-addSubjectTopic').val(subjectText);
				//$('#qb-addTopicTopic').val(topicText);
				
				$('#qb-addLevelTopic').attr('disabled','disabled');
				$('#qb-addSubjectTopic').attr('disabled','disabled');

				
				$('#qb-butTopicsOk').unbind().click(function() {
					document.getElementById('qb-addTopics').style.display='none';
					
					var mTopic=$('#qb-addTopicTopic').val().toUpperCase();
					var _csrf = $("input[name='_csrf']").val();
					
	                //validate topic, it should not already exist
					if (!validateTopic(mTopic) || mTopic.trim()=="") {
						
				           var msg='<p>The topic you entered already exists or entry is blank, please try again.</p>';
				           $('#examErrorMessage > p').remove();
				           $(msg).appendTo('#examErrorMessage');	 	        	  
				 	       document.getElementById('error').style.display='block';				
						
					}
					else {
						
						var topicData={"subjectid":subjectVal,"topic":mTopic,"_csrf":_csrf};
						
								           $.post("resteasy/qb/questionbank/addNewTopic",topicData)
								            .done( function(data,status,xhr){ 
								            	     appendToTopicList(data);
						      		                 msg='<p>New topic was added successfully.</p>';
							    		             $('#alertMessage > p').remove();
							    		             $(msg).appendTo('#alertMessage');	 	        	  
							    		 	         document.getElementById('alert').style.display='block';  
							    		 	         
									                   })
									          .fail(function(data,status,xhr){
										           var msg='<p>There was some problem adding new topic. Please retry after some time.</p>';
										           $('#examErrorMessage > p').remove();
										           $(msg).appendTo('#examErrorMessage');	 	        	  
										 	       document.getElementById('error').style.display='block';
									        	  
									          });					
						
					}									
					
					
					
					
					
				});
				$('#qb-butTopicsCancel').unbind().click(function() {
					document.getElementById('qb-addTopics').style.display='none';
				});			
				
			});	
			
			function validateTopic(mTopic) {
				
				var retval=true;
				$("#topic > option").each(function() {
				    if (this.value!="")
				    	{
				    	    var topic=this.text.toUpperCase();
				    	    if (topic==mTopic.toUpperCase()) {
					    	    	retval=false;
				    	    }
				    	}
				    
				});		
				
				return retval;
			}
			
			function appendToTopicList(topic) {
				
				var jsonTopic=JSON.parse(topic);
				
			    $('#topic').append(
			            $("<option></option>")
			              .attr("value", jsonTopic.id)
			              .text(jsonTopic.topic)
			        );
				
			}
			
			
			
		
		//function to create question paper	
		$('#butQuestionPaper').unbind().click(function(){
			
			populateQuestionTypesForQP();
			
			if ($('#questionPaper').is(':visible'))
				$('#questionPaper').hide();
			else
				$('#questionPaper').show();
			
			console.log(mQPaper.sections.length);
			
			if (mQPaper.sections.length>0)
				$('#qp-butRemoveSection').removeAttr('disabled');
			else
				$('#qp-butRemoveSection').attr('disabled','disabled');
			
		});	
		
		
		
		$('#qp-butAddSection').unbind().click(function(){
			
			var mSection = $('#qp-section').val();
			var mQuesType=$('#qp-ques-type option:selected').html();
			var mQuesTypeId=$('#qp-ques-type option:selected').val();
			var mMaxMarks=$('#qp-maxMarks').val();
			
			if (mSection.trim()=="" || mQuesType.trim()=="" || mMaxMarks.trim()=="" || isNaN(mMaxMarks)) {
		           var msg='<p>You need to provide all three values to add section to question paper and in right format. Please try again.</p>';
		           $('#examErrorMessage > p').remove();
		           $(msg).appendTo('#examErrorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';
				
			} else {
			
						//adding section to question paper
						var sectionExists=addSectionToPaper('qp-section'+qpIndex,mSection,mQuesTypeId,mMaxMarks,qpIndex-1);
						
						if (!sectionExists) {
								//displaying sections
								$(' <button id="qp-section'+qpIndex+'" class="w3-btn-block w3-left-align w3-leftbar">'+
					                    '<h8>'+mSection+' - '+mQuesType+' ( Max Marks : '+mMaxMarks+' )</h8>'+
					                        '</button>').appendTo('#qp-questionPaper');
								       var afterid=qpIndex;
								  
									   var divid="qp-section-ques"+afterid;
			
									   
									   $('<div id="'+divid+'" class="w3-accordion-content w3-container"></div>').insertAfter('#qp-section'+afterid);				   
									   
										if (qpIndex%2 >0)
											$('#qp-section'+qpIndex).addClass('w3-pale-yellow w3-border-yellow');
										else
											$('#qp-section'+qpIndex).addClass('w3-pale-blue w3-border-blue');	
										
								
										$('#qp-section'+qpIndex).click(function(){
											
											 //save this as current section
											if (mCurrentSection == "") {
											    mCurrentSection=$(this).attr('id'); 
											    }
											else
												{
												$('#'+mCurrentSection+' > i').remove();
												mCurrentSection=$(this).attr('id');
												}
											console.log(mCurrentSection);
											$('<i style="font-size:24px;color:green" class="fa w3-right">&#xf046;</i>').appendTo('#'+mCurrentSection);
											
									    	  var id=$(this).next().attr('id');
									    	  qp_openAccordian(id);
									      });	
										
										qpIndex+=1;
										
										//enabling remove section button
										if (mQPaper.sections.length>0)
											$('#qp-butRemoveSection').removeAttr('disabled');
						
						} //if section of same section type does not already exists
			}
		});
		
		//Question Paper preparation
		function addSectionToPaper(mSectionId,mSection,mQuesTypeId,mMaxMarks,index) {
			var mQPSection={"sectionid":"","sectionname":"","sectiontype":"","maxmarks":"","questions":[]};
			mQPSection.sectionid=mSectionId;
			mQPSection.sectionname=mSection;
			mQPSection.sectiontype=mQuesTypeId;
			mQPSection.maxmarks=mMaxMarks;
/*			console.log(mSectionId);
			console.log(mSection);
			console.log(mQuesTypeId);
			console.log(mMaxMarks);
			console.log(index);*/
			
			//first check if section with same question type is already added
			//if yes then do not add section
			var sectionExists=false;
			$.each(mQPaper.sections,function(index,section){
				if (section.sectiontype==mQuesTypeId)
					sectionExists=true;
			});
			
			//add section to paper
			if (!sectionExists) //if section of same type does not exists
			     mQPaper.sections[index]=mQPSection;
			
			console.log(mQPaper);
			
			return sectionExists;
			
		}
		
		$('#butAddQuestion').unbind().click(function(){
			var questionTypeVal=$( "#qb-questionType option:selected").val();
			
			if (questionTypeVal=="1" || questionTypeVal=="2") {
				addMultipleChoiceSingleOrMultiSelect(); // this method applies for both single-select and multi-select type of questions 
			} else if (questionTypeVal=="4") {
				addFillInBlank();
			}
			
			
		});
		
		function addFillInBlank() {
			document.getElementById('qb-addQuestions-fib').style.display='block';
			
			var levelVal=$( "#level option:selected").val();
			var levelText=$( "#level option:selected").html();
			
			var subjectVal=$( "#subject option:selected").val();
			var subjectText=$( "#subject option:selected").html();
			
			var topicVal=$( "#topic option:selected").val();
			var topicText=$( "#topic option:selected").html();
			
			var questionTypeVal=$( "#qb-questionType option:selected").val();
			var questionTypeText=$( "#qb-questionType option:selected").html();
				
			
			$('#qb-addLevelQuestion-fib').val(levelText);
			$('#qb-addSubjectQuestion-fib').val(subjectText);
			$('#qb-addTopicQuestion-fib').val(topicText);
			$('#qb-addQuestionType-fib').val(questionTypeText);
			
			$('#qb-addLevelQuestion-fib').attr('disabled','disabled');
			$('#qb-addSubjectQuestion-fib').attr('disabled','disabled');
			$('#qb-addTopicQuestion-fib').attr('disabled','disabled');
			$('#qb-addQuestionType-fib').attr('disabled','disabled');			
			
			$('#qb-butQuestionOk-fib').unbind().click(function() {
				var mQuestion=$('#qb-question-fib').val();
				var mOptionFirst="NA";
				var mOptionSecond="NA";
				var mOptionThird="NA";
				var mOptionFourth="NA";
				var mAnswer=$('#qb-answer-fib').val();
				var mMaxMarks=$('#qb-maxMarks-fib').val();	
				var mImageUrl=$('#imageUrl').val();
				var _csrf = $("input[name='_csrf']").val();
				
				document.getElementById('qb-addQuestions-fib').style.display='none';
                //validate all question related information is entered 
				if (mQuestion.trim()=="" || mAnswer.trim()=="" || mMaxMarks.trim()=="" || isNaN(mMaxMarks)) {
					
			           var msg='<p>One or more question enteries are blank or not in right format, please enter and try again.</p>';
			           $('#examErrorMessage > p').remove();
			           $(msg).appendTo('#examErrorMessage');	 	        	  
			 	       document.getElementById('error').style.display='block';				
					
				}
				else {
					var optionType="''";
					var questionData={"userId":userId,"subjectid":subjectVal,"topicid":topicVal,"questionType":questionTypeVal,"question":mQuestion,
									"optionFirst":mOptionFirst,"optionSecond":mOptionSecond,"optionThird":mOptionThird,"optionFourth":mOptionFourth,
									"optionType":optionType,"answer":mAnswer,"maxMarks":mMaxMarks,"imageUrl":mImageUrl,
									"_csrf":_csrf};
					
							           $.post("resteasy/qb/questionbank/addNewQuestion",questionData)
							            .done( function(data,status,xhr){ 
							            	     //appendToTopicList(data);
					      		                 msg='<p>New question was added successfully.</p>';
						    		             $('#alertMessage > p').remove();
						    		             $(msg).appendTo('#alertMessage');	 	        	  
						    		 	         document.getElementById('alert').style.display='block';  
						    		 	         
								                   })
								          .fail(function(data,status,xhr){
									           var msg='<p>There was some problem adding new question. Please retry after some time.</p>';
									           $('#examErrorMessage > p').remove();
									           $(msg).appendTo('#examErrorMessage');	 	        	  
									 	       document.getElementById('error').style.display='block';
								        	  
								          });					
					
				}
			});
			
			$('#qb-butQuestionCancel-fib').unbind().click(function() {
				document.getElementById('qb-addQuestions-fib').style.display='none';
			});	
			
			$('#qb-butQuestionClear-fib').unbind().click(function() {
				$('#qb-question-fib').val("");
				$('#qb-answer-fib').val("");
				$('#qb-maxMarks-fib').val("");	
			});			
			
			
		}
			
		//$('#butAddQuestion').unbind().click(function(){
		function addMultipleChoiceSingleOrMultiSelect() {
			document.getElementById('qb-addQuestions').style.display='block';
			var levelVal=$( "#level option:selected").val();
			var levelText=$( "#level option:selected").html();
			
			var subjectVal=$( "#subject option:selected").val();
			var subjectText=$( "#subject option:selected").html();
			
			var topicVal=$( "#topic option:selected").val();
			var topicText=$( "#topic option:selected").html();
			
			var questionTypeVal=$( "#qb-questionType option:selected").val();
			var questionTypeText=$( "#qb-questionType option:selected").html();
				
			
			$('#qb-addLevelQuestion').val(levelText);
			$('#qb-addSubjectQuestion').val(subjectText);
			$('#qb-addTopicQuestion').val(topicText);
			$('#qb-addQuestionType').val(questionTypeText);
			
			$('#qb-addLevelQuestion').attr('disabled','disabled');
			$('#qb-addSubjectQuestion').attr('disabled','disabled');
			$('#qb-addTopicQuestion').attr('disabled','disabled');
			$('#qb-addQuestionType').attr('disabled','disabled');
			
			
			
			$('#qb-butQuestionOk').unbind().click(function() {
				document.getElementById('qb-addQuestions').style.display='none';
				
/*				var mQuestion=$('#qb-question').val().toUpperCase();
				var mOptionFirst=$('#qb-optionFirst').val().toUpperCase();
				var mOptionSecond=$('#qb-optionSecond').val().toUpperCase();
				var mOptionThird=$('#qb-optionThird').val().toUpperCase();
				var mOptionFourth=$('#qb-optionFourth').val().toUpperCase();
				var mAnswer=$('#qb-answer').val().toUpperCase();
				var mMaxMarks=$('#qb-maxMarks').val().toUpperCase();*/
				
				var mQuestion=$('#qb-question').val();
				var mOptionFirst=$('#qb-optionFirst').val();
				var mOptionSecond=$('#qb-optionSecond').val();
				var mOptionThird=$('#qb-optionThird').val();
				var mOptionFourth=$('#qb-optionFourth').val();
				var mAnswer=$('#qb-answer').val();
				var mMaxMarks=$('#qb-maxMarks').val();	
				var mImageUrl=$('#imageUrl').val();
				var _csrf = $("input[name='_csrf']").val();
				
				document.getElementById('qb-addQuestions').style.display='none';
                //validate all question related information is entered 
				if (mQuestion.trim()=="" || mOptionFirst.trim()=="" || mOptionSecond.trim()=="" || mOptionThird.trim() == "" || mOptionFourth.trim()=="" 
					|| mAnswer.trim()=="" || mMaxMarks.trim()=="" || isNaN(mMaxMarks)) {
					
			           var msg='<p>One or more question enteries are blank or not in right format, please enter and try again.</p>';
			           $('#examErrorMessage > p').remove();
			           $(msg).appendTo('#examErrorMessage');	 	        	  
			 	       document.getElementById('error').style.display='block';				
					
				}
				else {
					var optionType="''";
					var questionData={"userId":userId,"subjectid":subjectVal,"topicid":topicVal,"questionType":questionTypeVal,"question":mQuestion,
									"optionFirst":mOptionFirst,"optionSecond":mOptionSecond,"optionThird":mOptionThird,"optionFourth":mOptionFourth,
									"optionType":optionType,"answer":mAnswer,"maxMarks":mMaxMarks,"imageUrl":mImageUrl,
									"_csrf":_csrf};
					
							           $.post("resteasy/qb/questionbank/addNewQuestion",questionData)
							            .done( function(data,status,xhr){ 
							            	     appendAddedQuestion(JSON.parse(data));
					      		                 msg='<p>New question was added successfully.</p>';
						    		             $('#alertMessage > p').remove();
						    		             $(msg).appendTo('#alertMessage');	 	        	  
						    		 	         document.getElementById('alert').style.display='block';  
						    		 	         
								                   })
								          .fail(function(data,status,xhr){
									           var msg='<p>There was some problem adding new question. Please retry after some time.</p>';
									           $('#examErrorMessage > p').remove();
									           $(msg).appendTo('#examErrorMessage');	 	        	  
									 	       document.getElementById('error').style.display='block';
								        	  
								          });					
					
				}								
				
				
				
				
			});				
			$('#qb-butQuestionCancel').unbind().click(function() {
				document.getElementById('qb-addQuestions').style.display='none';
			});			
			
			$('#qb-butQuestionClear').unbind().click(function() {
				$('#qb-question').val("");
				$('#qb-optionFirst').val("");
				$('#qb-optionSecond').val("");
				$('#qb-optionThird').val("");
				$('#qb-optionFourth').val("");
				$('#qb-answer').val("");
				$('#qb-maxMarks').val("");
			});					
			
		}	
		
		function appendAddedQuestion(value){
			
			$('<table class="w3-table-all"><tr><td style="width:95%"><button id="'+value.id+'" class="w3-btn-block w3-left-align w3-leftbar">'+
                    '<span class="w3-small">'+
                    value.question.toUpperCase()+'</span>'+
                    '<span class="w3-text-blue w3-right w3-small">( Date :<span class="w3-text-black">'+value.updateDate+'</span>&nbsp;&nbsp;Time :<span class="w3-text-black">'+value.updateTime+'</span> )</span>'+
                    /*</h6>'+
*/                        '</button></td><td><input class="w3-check" type="checkbox" id="'+value.id+'" value="'+value.question+','+value.updateDate+','+value.updateTime+'"/></td></tr></table>').insertAfter('#questions');
			       var afterid=value.id;
			  
				   var divid="question"+afterid;
				   var questionTypeVal=$( "#qb-questionType option:selected").val();
				   
				   var topicstr="";
				   if (questionTypeVal=="1") {
					   topicstr='<p class="w3-text-blue"><table id="questionTable" class="w3-table-all w3-hoverable w3-small w3-center">';
					   topicstr+='<tr><td class="w3-text-blue">Question</td><td class="w3-text-black"><input type="text" id="ques'+value.id+'" value="'+value.question+'"  size="70"/></td><td></td><td></td></tr>';
					   topicstr+='<tr><td class="w3-text-blue">Options</td><td class="w3-text-black"><input type="text" id="optionFirst'+value.id+'" value="'+value.optionFirst+'" size="50"/></td><td></td><td></td></tr>';
					   topicstr+='<tr><td></td><td><input type="text" id="optionSecond'+value.id+'" value="'+value.optionSecond+'" size="50"/></td><td></td><td></td></tr>';
					   topicstr+='<tr><td></td><td><input type="text" id="optionThird'+value.id+'" value="'+value.optionThird+'" size="50"/></td><td></td><td></td></tr>';
					   topicstr+='<tr><td></td><td><input type="text" id="optionFourth'+value.id+'" value="'+value.optionFourth+'" size="50"/></td><td></td><td></td></tr>';
					   topicstr+='<tr><td class="w3-text-blue">Answer </td><td class="w3-text-black"><input type="text" id="answer'+value.id+'" value="'+value.answer+'" size="50"/></td>';
					   topicstr+='<td class="w3-text-blue">Max Marks </td><td class="w3-text-black"><input type="text" id="maxMarks'+value.id+'" value="'+value.maxMarks+'" size="10"/></td></tr>';
					   topicstr+= '<tr><td></td><td></td><td></td><td><button id="'+divid+'Save" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">Save</button>';
					   topicstr+= '<button id="'+divid+'Edit" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">Edit</button></td></tr></table>';					   
				   } else if (questionTypeVal=="4") {
					   topicstr='<p class="w3-text-blue"><table id="questionTable" class="w3-table-all w3-hoverable w3-small w3-center">';
					   topicstr+='<tr><td class="w3-text-blue">Question</td><td class="w3-text-black"><input type="text" id="ques'+value.id+'" value="'+value.question+'"  size="70"/></td><td></td><td></td></tr>';
					   topicstr+='<tr><td class="w3-text-blue">Answer </td><td class="w3-text-black"><input type="text" id="answer'+value.id+'" value="'+value.answer+'" size="50"/></td>';
					   topicstr+='<td class="w3-text-blue">Max Marks </td><td class="w3-text-black"><input type="text" id="maxMarks'+value.id+'" value="'+value.maxMarks+'" size="10"/></td></tr>';
					   topicstr+= '<tr><td></td><td></td><td></td><td><button id="'+divid+'Save" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">Save</button>';
					   topicstr+= '<button id="'+divid+'Edit" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">Edit</button></td></tr></table>';
					   topicstr+='<input type="hidden" id="optionFirst'+value.id+'" value="'+value.optionFirst+'" />';
					   topicstr+='<input type="hidden" id="optionSecond'+value.id+'" value="'+value.optionSecond+'"/>';
					   topicstr+='<input type="hidden" id="optionThird'+value.id+'" value="'+value.optionThird+'" />';
					   topicstr+='<input type="hidden" id="optionFourth'+value.id+'" value="'+value.optionFourth+'"/>';						   
					   
				   }
				   
				   
				   $('<div id="'+divid+'" class="w3-accordion-content w3-container">'+topicstr+
				   '</div>').insertAfter('#'+afterid);	
				   
/*					   if (questionTypeVal=="4") {
						//hiding the options, not to be displayed
						$('#optionFirst'+afterid).hide();
						$('#optionSecond'+afterid).hide();
						$('#optionThird'+afterid).hide();
						$('#optionFourth'+afterid).hide();						   
					   
				   }*/
				   
/*					if (index%2 >0)
						$('#'+value.id).addClass('w3-pale-yellow w3-border-yellow');
					else
						$('#'+value.id).addClass('w3-pale-blue w3-border-blue');	*/
				   
				    $('#'+value.id).addClass('w3-pale-green w3-border-green');
					
					$('#'+divid+'Edit').unbind().click(function(){
						
						//var questionTypeVal=$( "#qb-questionType option:selected").val();
						//console.log(questionTypeVal);
						if (questionTypeVal=="1") { //if question type is multiple choice single select
							
							$('#'+divid).find('input').removeAttr('disabled'); 
							
						} else if (questionTypeVal=="4") { //if question type is fill in blanks
							
							$('#ques'+afterid).removeAttr('disabled');
							$('#answer'+afterid).removeAttr('disabled');
							$('#maxMarks'+afterid).removeAttr('disabled');
							
						}
						$(this).attr('disabled','disabled');
						$('#'+divid+'Save').removeAttr('disabled');
					});
					
					$('#'+divid+'Save').unbind().click(function(){
						$(this).attr('disabled','disabled');
						saveQuestion(value.id,divid);
					});
					
							$('#'+value.id).click(function(){
  					    	  var id=$(this).next().attr('id');
  					    	  openAccordian(id,divid);
  					      });
							
							//disable all questions first
							$('#'+value.id).find('input').attr('disabled','disabled');			
			
		}
		
		function prepareQuestionList(jsonData)
		{
			
		     $('#questionsList > table').remove();
			//var jsonData = JSON.parse(data);
			$.each(jsonData,function(index,value){
				$('<table class="w3-table-all"><tr><td style="width:95%"><button id="'+value.id+'" class="w3-btn-block w3-left-align w3-leftbar">'+
                        '<span class="w3-small">'+
                        value.question.toUpperCase()+'</span>'+
                        '<span class="w3-text-blue w3-right w3-small">( Date :<span class="w3-text-black">'+value.updateDate+'</span>&nbsp;&nbsp;Time :<span class="w3-text-black">'+value.updateTime+'</span> )</span>'+
                        /*</h6>'+
*/                        '</button></td><td><input class="w3-check" type="checkbox" id="'+value.id+'" value="'+value.question+','+value.updateDate+','+value.updateTime+'"/></td></tr></table>').insertAfter('#questions');
				       var afterid=value.id;
				  
					   var divid="question"+afterid;
					   var questionTypeVal=$( "#qb-questionType option:selected").val();
					   
					   var imageUrlStr="";
					   if(value.imageUrl == "" || value.imageUrl== null)
						   imageUrlStr='<td class="w3-text-blue">Image </td><td class="w3-text-black"><img id="imageUrl'+value.id+'" src="images/NoImage.jpg" style="width:10%;height:8%"/></td></tr>';
					   else
						   imageUrlStr='<td class="w3-text-blue">Image </td><td class="w3-text-black"><img id="imageUrl'+value.id+'" src="'+value.imageUrl+'" style="width:10%;height:8%"/></td></tr>';
					   
					   var topicstr="";
					   if (questionTypeVal=="1") {
						   topicstr='<p class="w3-text-blue"><table id="questionTable" class="w3-table-all w3-hoverable w3-small w3-center">';
						   topicstr+='<tr><td class="w3-text-blue">Question</td><td class="w3-text-black"><input type="text" id="ques'+value.id+'" value="'+value.question+'"  size="70"/></td><td></td><td></td></tr>';
						   topicstr+='<tr><td class="w3-text-blue">Options</td><td class="w3-text-black"><input type="text" id="optionFirst'+value.id+'" value="'+value.optionFirst+'" size="50"/></td><td></td><td></td></tr>';
						   topicstr+='<tr><td></td><td><input type="text" id="optionSecond'+value.id+'" value="'+value.optionSecond+'" size="50"/></td><td></td><td></td></tr>';
						   topicstr+='<tr><td></td><td><input type="text" id="optionThird'+value.id+'" value="'+value.optionThird+'" size="50"/></td><td></td><td></td></tr>';
						   topicstr+='<tr><td></td><td><input type="text" id="optionFourth'+value.id+'" value="'+value.optionFourth+'" size="50"/></td><td></td><td></td></tr>';
						   topicstr+='<tr><td class="w3-text-blue">Answer </td><td class="w3-text-black"><input type="text" id="answer'+value.id+'" value="'+value.answer+'" size="50"/></td>';
						   topicstr+='<td class="w3-text-blue">Max Marks </td><td class="w3-text-black"><input type="text" id="maxMarks'+value.id+'" value="'+value.maxMarks+'" size="10"/></td></tr>';
						   topicstr+=imageUrlStr;
						   topicstr+= '<tr><td></td><td colspan="3"><button id="'+divid+'Save" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">Save</button>';
						   topicstr+= '<button id="'+divid+'Edit" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">Edit</button>';
						   topicstr+= '<button id="'+divid+'loadphoto" class="w3-button w3-yellow w3-hover-red w3-right w3-margin-left">Photo</button></td></tr></table>';
					   } else if (questionTypeVal=="4") {
						   topicstr='<p class="w3-text-blue"><table id="questionTable" class="w3-table-all w3-hoverable w3-small w3-center">';
						   topicstr+='<tr><td class="w3-text-blue">Question</td><td class="w3-text-black"><input type="text" id="ques'+value.id+'" value="'+value.question+'"  size="70"/></td><td></td><td></td></tr>';
						   topicstr+='<tr><td class="w3-text-blue">Answer </td><td class="w3-text-black"><input type="text" id="answer'+value.id+'" value="'+value.answer+'" size="50"/></td>';
						   topicstr+='<td class="w3-text-blue">Max Marks </td><td class="w3-text-black"><input type="text" id="maxMarks'+value.id+'" value="'+value.maxMarks+'" size="10"/></td></tr>';
						   topicstr+=imageUrlStr;
						   topicstr+= '<tr><td></td><td></td><td colspan="2"><button id="'+divid+'Save" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">Save</button>';
						   topicstr+= '<button id="'+divid+'Edit" class="w3-button w3-yellow w3-hover-red w3-small w3-right w3-margin-left">Edit</button></td></tr></table>';
						   topicstr+= '<button id="'+divid+'loadphoto" class="w3-button w3-yellow w3-hover-red w3-right w3-margin-left">Photo</button>';
						   topicstr+='<input type="hidden" id="optionFirst'+value.id+'" value="'+value.optionFirst+'" />';
						   topicstr+='<input type="hidden" id="optionSecond'+value.id+'" value="'+value.optionSecond+'"/>';
						   topicstr+='<input type="hidden" id="optionThird'+value.id+'" value="'+value.optionThird+'" />';
						   topicstr+='<input type="hidden" id="optionFourth'+value.id+'" value="'+value.optionFourth+'"/>';						   
						   
					   }
					   
					   
					   $('<div id="'+divid+'" class="w3-accordion-content w3-container">'+topicstr+
					   '</div>').insertAfter('#'+afterid);	
					   
/*					   if (questionTypeVal=="4") {
							//hiding the options, not to be displayed
							$('#optionFirst'+afterid).hide();
							$('#optionSecond'+afterid).hide();
							$('#optionThird'+afterid).hide();
							$('#optionFourth'+afterid).hide();						   
						   
					   }*/
					   
						if (index%2 >0)
							$('#'+value.id).addClass('w3-pale-yellow w3-border-yellow');
						else
							$('#'+value.id).addClass('w3-pale-blue w3-border-blue');	
						
						$('#'+divid+'Edit').unbind().click(function(){
							
							//var questionTypeVal=$( "#qb-questionType option:selected").val();
							//console.log(questionTypeVal);
							if (questionTypeVal=="1") { //if question type is multiple choice single select
								
								$('#'+divid).find('input').removeAttr('disabled'); 
								
							} else if (questionTypeVal=="4") { //if question type is fill in blanks
								
								$('#ques'+afterid).removeAttr('disabled');
								$('#answer'+afterid).removeAttr('disabled');
								$('#maxMarks'+afterid).removeAttr('disabled');
								
							}
							$(this).attr('disabled','disabled');
							$('#'+divid+'Save').removeAttr('disabled');
							$('#'+divid+'loadphoto').removeAttr('disabled');
							
						});
						
						$('#'+divid+'Save').unbind().click(function(){
							$(this).attr('disabled','disabled');
							saveQuestion(value.id,divid);
						});
						
						$('#'+value.id).click(function(){
  					    	  var id=$(this).next().attr('id');
  					    	  openAccordian(id,divid);
  					      });
  							
						$('#'+divid+'loadphoto').unbind().click(function() {
							$('#upload-questionid').attr('value',value.id);
							document.getElementById('qb-photo').style.display='block';
						});	
  							
  							//disable all questions first
  							$('#'+value.id).find('input').attr('disabled','disabled');
							    
				});							
						


			
			
			}
		
		//function to add selected questions to question paper
        $('#qp-addToPaper').unbind().click(function(){
   			//console.log("coming to add paper");

        	if (mCurrentSection=="") {    //There should be at least one section and should be currently selected
                msg='<p>Either section in question paper do not exist or is not currently selected. Please create and select section and try again.</p>';
	             $('#alertMessage > p').remove();
	             $(msg).appendTo('#alertMessage');	 	        	  
	 	         document.getElementById('alert').style.display='block';       		
        	} 
        	else {
   			
				        	var items=$('#questionsList').find(':checkbox');
				
							//console.log($('#questionsList').find('button').length);
				   			//var checkedItems=$('#questionsList').find(':checkbox').prop('checked', true);
				   			console.log("items length-->"+items.length);
				   			var isQuesSelected=false;
				
				   			if (items.length==0)
				   				{
					                 msg='<p>There are no questions to be added.Please search questions first you wish to add.</p>';
						             $('#alertMessage > p').remove();
						             $(msg).appendTo('#alertMessage');	 	        	  
						 	         document.getElementById('alert').style.display='block';    				
				   				}
				   			else
				   				{
				   				    var quesRow='<table id="questionTable" class="w3-table-all w3-hoverable w3-small w3-center">';
				   				    var searchedQuestionType=$('#qb-questionType').val();
				   				    console.log("search question type-->"+searchedQuestionType);
				   				    $.each(items,function(index,chkbox)
				   				    		{
				   						//var mQPaper={"name":"","passpercent":"","sections":[]};
				   						//var mQPSection={"sectionid":"","sectionname":"","sectiontype":"","maxmarks":"","questions":[]};   				    	
				   				    	
				   				    	        if (chkbox.checked) {
				   				    	        	//console.log("select question--->"+chkbox.id);
				   				    	        	var quesid=chkbox.id;
				   				    	        	var quesinfo=chkbox.value;
				   				    	        	//console.log(chkbox);
				   				    	        	isQuesSelected=true;
				   				    	        	
				   				    	        	
				   				    	        	
				   				    	        	$.each(mQPaper.sections,function(index,section){
				   				    	        		console.log("section in loop-->"+section.sectionid);
				   				    	        		console.log("current section -->"+mCurrentSection);
				   				    	        		console.log("section type-->"+section.sectiontype);
				   				    	        		if (section.sectionid==mCurrentSection && section.sectiontype==currentSearchedQuestionType) {  //if section matches currently selected section and section type and question type matches, then add question
				   				    	        		  	var noOfQues=mQPaper.sections[index].questions.length;
				   				    	        		  	if (mQPaper.sections[index].questions.indexOf(quesid) == -1) // if question id is not already existing in array
				   				    	        		  	    { mQPaper.sections[index].questions[noOfQues]=quesid;
				   				    	        		  	    
				   				    	        		  	      var quesInfoArr=quesinfo.split(",");
				   				    	        		  	       quesRow+='<tr id="qp-sec-ques'+quesid+'"><td class="w3-text-black"><h8>'+quesInfoArr[0]+'</h8><span id="'+quesid+'"><i style="font-size:24px;color:red" class="fa w3-right">&#xf05c;</i></span></td></tr>';
				   				    	        		  	      //console.log(quesRow);
				   				    	        		  	       chkbox.disabled=true;
				   				    	        		  	       chkbox.checked=false;
				
				   				    	        		  	    
				   				    	        		  	    }
				   				    	        		}
				   				    	        		
				   				    	        	});
				
				   				    	        }
				   				    		});
				   				    
					    	        	quesRow+='</table>';
						    	        	
				    		  	        //displaying questions below section
					    	        	var nextDiv=$('#'+mCurrentSection).next('div').attr('id');
					    	        	console.log(nextDiv);
				    		  	        $(quesRow).appendTo('#'+nextDiv);
				    		  	        
				    		  	        //when user clicks on delete button against the question
				    		  	        $('#'+nextDiv).find('span').unbind().click(function(){
				    		  	        	console.log($(this));
				    		  	        	var id=$(this).attr('id');
				    		  	        	$('#qp-sec-ques'+id).remove(); //removing question from under section
				    		  	        	
						   				    $.each(items,function(index,chkbox)   //enabling the question in list that was disabled
						   				    		{
						   				    	         if (chkbox.id==id)
						   				    	        	 chkbox.disabled=false;
						   				    		});
						   				    
		   				    	        	$.each(mQPaper.sections,function(index,section){  //also remove the deleted question from in-memory question paper object
		   				    	        		//if (section.sectionid==mCurrentSection) {  //if section matches currently selected section
		   				    	        		  	//var noOfQues=mQPaper.sections[index].questions.length;
		   				    	        		  	if (mQPaper.sections[index].questions.indexOf(id) >= 0) // if question id is already existing in array
		   				    	        		  	    { 
		   				    	        		  	      var qindex=mQPaper.sections[index].questions.indexOf(id);
		   				    	        		  	      mQPaper.sections[index].questions.splice(qindex,1);
		   				    	        		  	    }
		   				    	        		//}
		   				    	        		  
		   				    	        	});						   				    
				    		  	        	
				    		  	        	
				    		  	        });
				   				    
				   				    console.log(mQPaper);
				   				    //console.log($('#questionsList > button').length);
				   				}
				   			
				   			if (!isQuesSelected) {
				                 msg='<p>You must select at least one question to add to question paper.</p>';
					             $('#alertMessage > p').remove();
					             $(msg).appendTo('#alertMessage');	 	        	  
					 	         document.getElementById('alert').style.display='block';      				
				   				
				   			}
        	}
           });
        
            $('#qb-fib-butUploadIt').unbind().click(function(){$('#qb-fib-fileUploadForm').submit(); })
            
            $('#qb-butUploadIt').unbind().click(function(){$('#qb-fileUploadForm').submit(); })
			
	        $('#qb-fib-fileUploadForm').submit( function(e) {

	    	    e.preventDefault();
	    	    var _csrf = $("input[name='_csrf']").val();
	    	    //console.log(_csrf);
	    	    //var data = $('#fileUploadForm').serialize(); // <-- 'this' is your form element
	    	    //console.log(data);
	    	    var form = new FormData($("#qb-fib-fileUploadForm")[0]);
	    	    console.log(form);
	 		   $.ajaxSetup({
				      headers: {"X-CSRF-Token": _csrf
				                }
				    }); 
	    	    $.ajax({
	    	            url: 'resteasy/qb/upload/question/'+userId,
	    	            //data: form,
	    	            data:form,
	    	            cache: false,
	    	            contentType: false,
	    	            processData: false,
	    	            type: 'POST',     
	    	            success: function(data){
	    	            	var jsonData=JSON.parse(data);
	    	            	var url=jsonData.url;
	    	            	if (url=="FileSizeOrTypeError") {
	  				           var msg='<p>File should be image (Gif/PNG/JPEG/BMP) and size should not be more than 1 mb. Please try again.</p>';
					           $('#alertMessage > p').remove();
					           $(msg).appendTo('#alertMessage');	 	        	  
					 	       document.getElementById('error').style.display='block';        	            		
	    	            	} else {
	        	            	$('#imageUrl').val(url);
	        	            	$('#qb-fib-image').attr('src',url);
	    	            	}
	    	            },
	    	            error:function(err){
					           var msg='<p>There was some problem uploading image. Please try after some time.</p>';
				           $('#examErrorMessage > p').remove();
				           $(msg).appendTo('#examErrorMessage');	 	        	  
				 	       document.getElementById('error').style.display='block';	
	    	            	}
	    	            });
	    	    
	     });
	  
	//});
        
	        $('#qb-fileUploadForm').submit( function(e) {

	    	    e.preventDefault();
	    	    var _csrf = $("input[name='_csrf']").val();
	    	    //console.log(_csrf);
	    	    //var data = $('#fileUploadForm').serialize(); // <-- 'this' is your form element
	    	    //console.log(data);
	    	    var form = new FormData($("#qb-fileUploadForm")[0]);
	    	    console.log(form);
	 		   $.ajaxSetup({
				      headers: {"X-CSRF-Token": _csrf
				                }
				    }); 
	    	    $.ajax({
	    	            url: 'resteasy/qb/upload/question/'+userId,
	    	            //data: form,
	    	            data:form,
	    	            cache: false,
	    	            contentType: false,
	    	            processData: false,
	    	            type: 'POST',     
	    	            success: function(data){
	    	            	var jsonData=JSON.parse(data);
	    	            	var url=jsonData.url;
	    	            	if (url=="FileSizeOrTypeError") {
	  				           var msg='<p>File should be image (Gif/PNG/JPEG/BMP) and size should not be more than 1 mb. Please try again.</p>';
					           $('#alertMessage > p').remove();
					           $(msg).appendTo('#alertMessage');	 	        	  
					 	       document.getElementById('error').style.display='block';        	            		
	    	            	} else {
	        	            	$('#imageUrl').val(url);
	        	            	$('#qb-image').attr('src',url);
	    	            	}
	    	            },
	    	            error:function(err){
					           var msg='<p>There was some problem uploading image. Please try after some time.</p>';
				           $('#examErrorMessage > p').remove();
				           $(msg).appendTo('#examErrorMessage');	 	        	  
				 	       document.getElementById('error').style.display='block';	
	    	            	}
	    	            });
	    	    
	     });   
	        
	        	
	        $('#qb-list-butUploadIt').unbind().click(function(){$('#qb-list-fileUploadForm').submit(); })
	        $('#qb-list-fileUploadForm').submit( function(e) {
                document.getElementById('qb-photo').style.display='none';
	    	    e.preventDefault();
	    	    var _csrf = $("input[name='_csrf']").val();
	    	    var currQuesId=$('#upload-questionid').val();
	    	    console.log(currQuesId);
	    	    //console.log(_csrf);
	    	    //var data = $('#fileUploadForm').serialize(); // <-- 'this' is your form element
	    	    //console.log(data);
	    	    var form = new FormData($("#qb-list-fileUploadForm")[0]);
	    	    //console.log(form);
	 		   $.ajaxSetup({
				      headers: {"X-CSRF-Token": _csrf
				                }
				    }); 
	    	    $.ajax({
	    	            url: 'resteasy/qb/upload/question/'+userId, //userid passed while uploading image for question is just for dummy purpose
	    	            //data: form,
	    	            data:form,
	    	            cache: false,
	    	            contentType: false,
	    	            processData: false,
	    	            type: 'POST',     
	    	            success: function(data){
	    	            	var jsonData=JSON.parse(data);
	    	            	var url=jsonData.url;
	    	            	if (url=="FileSizeOrTypeError") {
	  				           var msg='<p>File should be image (Gif/PNG/JPEG/BMP) and size should not be more than 1 mb. Please try again.</p>';
					           $('#alertMessage > p').remove();
					           $(msg).appendTo('#alertMessage');	 	        	  
					 	       document.getElementById('error').style.display='block';        	            		
	    	            	} else {
	        	            	//$('#imageUrl'+currQuesId).val(url); //url returned is actually stored in attribute and then saved to question in save operation
	        	            	$('#imageUrl'+currQuesId).attr('src',url);
	    	            	}
	    	            },
	    	            error:function(err){
					           var msg='<p>There was some problem uploading image. Please try after some time.</p>';
				           $('#examErrorMessage > p').remove();
				           $(msg).appendTo('#examErrorMessage');	 	        	  
				 	       document.getElementById('error').style.display='block';	
	    	            	}
	    	            });
	    	    
	     });   	        
        
		function saveQuestion(qid,divid) {
			var _csrf = $("input[name='_csrf']").val();
			var mQuestion=$('#ques'+qid).val();
			var moptionFirst=$('#optionFirst'+qid).val();
			var moptionSecond=$('#optionSecond'+qid).val();
			var moptionThird=$('#optionThird'+qid).val();
			var moptionFourth=$('#optionFourth'+qid).val();
			var manswer=$('#answer'+qid).val();
			var mmarks=$('#maxMarks'+qid).val();
			var questionTypeVal=$( "#qb-questionType option:selected").val();
			var imageUrl=$('#imageUrl'+qid).attr('src');
			console.log('save question image url'+imageUrl);
			
			//console.log(mQuestion);
			var isNotBlanks=checkBlanks(mQuestion,moptionFirst,moptionSecond,moptionThird,moptionFourth,manswer,mmarks);
			var answerMatchOption=true;
			if (questionTypeVal=="1" && manswer != moptionFirst && manswer != moptionSecond && manswer != moptionThird && manswer != moptionFourth) {
				answerMatchOption=false;
			} else if (questionTypeVal=="4") {
				answerMatchOption=true;
			}
				
/*			console.log(isNotBlanks);
			console.log(answerMatchOption);
			console.log(questionTypeVal);*/
			
			//if (isNotBlanks && manswer != moptionFirst && manswer != moptionSecond && manswer != moptionThird && manswer != moptionFourth )
			if (!isNotBlanks || !answerMatchOption )
				{
	             $('#alertMessage > p').remove();
	             $('<p>Answer does not matches with any of the given options or there is blank entry. Please correct and try again.</p>').appendTo('#alertMessage');	 	        	  
	 	         document.getElementById('alert').style.display='block'; 			
				}
			else {
			
					//prepare objet of data
					var qData={"id":qid,"question":mQuestion,"optionFirst":moptionFirst,"optionSecond":moptionSecond,"optionThird":moptionThird,"optionFourth":moptionFourth,"answer":manswer,"maxMarks":mmarks,"imageUrl":imageUrl,"_csrf":_csrf};
					
			           $.post("resteasy/qb/questionbank/saveQuestion",qData)
			    		
			            .done( function(data,status,xhr){ 
			          	         //var jsonData=JSON.parse(data);
			            	    var msg="";
			            	    if(data == null)
			            	    	 msg='<p>This question does not exist anymore. It was removed, kindly refresh your screen.</p>';
			            	    else
		      		                 msg='<p>Question was saved successfully.</p>';
			            	    
		    		             $('#alertMessage > p').remove();
		    		             $(msg).appendTo('#alertMessage');	 	        	  
		    		 	         document.getElementById('alert').style.display='block';      	        	 
				                   })
				          .fail(function(data,status,xhr){
					           var msg='<p>There was some problem saving question to question bank. Please retry after some time.</p>';
					           $('#examErrorMessage > p').remove();
					           $(msg).appendTo('#examErrorMessage');	 	        	  
					 	       document.getElementById('error').style.display='block';
				        	  
				          }); 
			}
			//enabling save button
			$('#'+divid+'Save').removeAttr('disabled');
			
			
		}
		
		function checkBlanks(mQuestion,moptionFirst,moptionSecond,moptionThird,moptionFourth,manswer,mmarks) {
			
			var isValid=false;
			if (mQuestion.trim()=="" || moptionFirst.trim()=="" || moptionSecond.trim()=="" || moptionThird.trim()=="" || moptionFourth.trim()=="" || manswer.trim()=="" || mmarks.trim()=="") {
				isValid=false;
			} else {
				isValid=true;
			}
			return isValid;
			
		}
		
		
	    function populateQuestionTypesForQP() {
			$('#qp-ques-type > option').remove();
			$.get('resteasy/qb/questionbank/questionTypes')
			 .done(function(qtypes){
				 var jsonQTypes = JSON.parse(qtypes);
				 $('<option value="" disabled selected>Choose question type</option>').appendTo('#qp-ques-type');
				 $.each(jsonQTypes,function(index,qtypes) {
					 $('<option value="'+qtypes.id+'">'+qtypes.questionType+'</option>').appendTo('#qp-ques-type');
				 });
			 })
			 .fail(function(){
		           var msg='<p>There was some problem getting question type information. Please retry after some time.</p>';
		           $('#examErrorMessage > p').remove();
		           $(msg).appendTo('#examErrorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';				
			});  	
	    }		
		
		
	    function populateQuestionTypes() {
			$('#qb-questionType > option').remove();
			$.get('resteasy/qb/questionbank/questionTypes')
			 .done(function(qtypes){
				 var jsonQTypes = JSON.parse(qtypes);
				 $('<option value="" disabled selected>Choose question type</option>').appendTo('#qb-questionType');
				 $.each(jsonQTypes,function(index,qtypes) {
					 $('<option value="'+qtypes.id+'">'+qtypes.questionType+'</option>').appendTo('#qb-questionType');
				 });
			 })
			 .fail(function(){
		           var msg='<p>There was some problem getting question type information. Please retry after some time.</p>';
		           $('#examErrorMessage > p').remove();
		           $(msg).appendTo('#examErrorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';				
			});  	
	    }			
		
	    function populateTopic(subjectid) {
			$('#topic > option').remove();
			$.get('resteasy/qb/questionbank/topicsForSubject/'+subjectid)
			 .done(function(topics){
				 var jsonTopics = JSON.parse(topics);
				 $('<option value="" disabled selected>Choose topic</option>').appendTo('#topic');
				 $.each(jsonTopics,function(index,topic) {
					 $('<option value="'+topic.id+'">'+topic.topic+'</option>').appendTo('#topic');
				 });
			 })
			 .fail(function(){
		           var msg='<p>There was some problem getting topic information. Please retry after some time.</p>';
		           $('#examErrorMessage > p').remove();
		           $(msg).appendTo('#examErrorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';				
			});  	
	    }		
		
    function populateSubject(levelid) {
		$('#subject > option').remove();
		$.get('resteasy/qb/questionbank/subjectsForLevel/'+levelid)
		 .done(function(subjects){
			 var jsonSubjects = JSON.parse(subjects);
			 $('<option value="" disabled selected>Choose subject</option>').appendTo('#subject');
			 $.each(jsonSubjects,function(index,subject) {
				 $('<option value="'+subject.id+'">'+subject.subject+'</option>').appendTo('#subject');
			 });
		 })
		 .fail(function(){
	           var msg='<p>There was some problem getting subject information. Please retry after some time.</p>';
	           $('#examErrorMessage > p').remove();
	           $(msg).appendTo('#examErrorMessage');	 	        	  
	 	       document.getElementById('error').style.display='block';				
		});  	
    }		
		
	function populateLevel() {
		$('#level > option').remove();
		$.get('resteasy/qb/questionbank/levels')
		 .done(function(levels){
			 var jsonLevels = JSON.parse(levels);
			 $('<option value="" disabled selected>Choose Class/Level</option>').appendTo('#level');
			 $.each(jsonLevels,function(index,level) {
				 $('<option value="'+level.id+'">'+level.levelName+' ( '+level.level+' ) </option>').appendTo('#level');
			 });
		 })
		 .fail(function(){
	           var msg='<p>There was some problem getting classes / levels information. Please retry after some time.</p>';
	           $('#examErrorMessage > p').remove();
	           $(msg).appendTo('#examErrorMessage');	 	        	  
	 	       document.getElementById('error').style.display='block';				
		});
		
	}
	
	function disableElements() {
		$('#questionPaper').hide();
		$('#subject').attr('disabled','disabled');
		$('#topic').attr('disabled','disabled');
		$('#questionType').attr('disabled','disabled');
		$('#dateFrom').attr('disabled','disabled');
		$('#dateTo').attr('disabled','disabled');
		$('#freeText').attr('disabled','disabled');
		
		$('#qb-butAddSubjects').attr('disabled','disabled');
		$('#qb-butAddTopics').attr('disabled','disabled');
		//$('#butAddQuestionType').attr('disabled','disabled');
		$('#butSearch').attr('disabled','disabled');
		$('#butAddQuestion').attr('disabled','disabled');
		$('#qp-addToPaper').attr('disabled','disabled');
		//$('#butQuestionPaper').attr('disabled','disabled');
		
		if (userRole=="ROLE_TEACHER" || userRole=="ROLE_ORGANIZATION") 
			$('#qb-butAddLevels').attr('disabled','disabled');
		else if (userRole=="ROLE_SUPERUSER")
			$('#qb-butAddLevels').removeAttr('disabled');
		
	}
	

	
	function openAccordian(id,divid) {
	    var x = document.getElementById(id);

	    if (x.className.indexOf("w3-show") == -1) {
	        x.className += " w3-show";
	    } else { 
	        x.className = x.className.replace(" w3-show", "");
	    }
	    $('#'+id).find('input').attr('disabled','disabled');
	    $('#'+divid+'Edit').removeAttr('disabled');
	    $('#'+divid+'Save').attr('disabled','disabled');
	    $('#'+divid+'loadphoto').attr('disabled','disabled');
	}	
	
	function qp_openAccordian(id) {
		
		//console.log(id);
	    var x = document.getElementById(id);

	    if (x.className.indexOf("w3-show") == -1) {
	        x.className += " w3-show";
	    } else { 
	        x.className = x.className.replace(" w3-show", "");
	    }

	}		
});


