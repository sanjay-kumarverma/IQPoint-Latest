$(document).ready(function(){
/*    $('.pageheader').load('PageHeader.html');
	$('.pagefooter').load('PageFooter.html');*/
	$('#successMessage').hide();
	$('#warningMessage').hide();
	$('#errorMessage').hide();
	var loginId=$('#loginid').text();
	var userId="";
	var userRole="";
	var subjectTreeStr="";
	var subjectArray=[];
	var userLevelId="";
	var userLevelName="";
	

	//get userid
	$.get('resteasy/qb/getuserbyloginname/'+loginId)
			 .done(function(userobj){
                  jsonuser=JSON.parse(userobj);
                  $('#root').attr('value',jsonuser.id);
                  //var userTreeStr=jsonuser.firstName+'&nbsp;'+jsonuser.lastName+' ('+jsonuser.role.role+' )';
                  userRole=jsonuser.role.role;
                  $('#userRole').val(userRole);
                  var userTreeStr=jsonuser.firstName+' '+jsonuser.lastName;
                  userTreeStr=userTreeStr.toUpperCase();
                  
  			    if (userRole=="ROLE_TEACHER" || userRole=="ROLE_SUPERUSER" || userRole=="ROLE_ORGANIZATION")
		    	{
  			    	$('#root').html(userTreeStr);
  			    	setSplitterAndTree();
		    	}
  			    else {
  			    	getSubjectTree(jsonuser.id,userTreeStr);
  			    }
                  
			 })
			 .fail(function(){
		           var msg='<p>There was some problem getting user information. Please try after some time.</p>';
		           $('#welcomeErrorMessage > p').remove();
		           $(msg).appendTo('#welcomeErrorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';				
			});		
	

function getSubjectTree(userid,userTreeStr) {
	//console.log('I am here'+userid);
	//get user subjects
	
	$.get('resteasy/qb/getusersubjects/'+userid)
			 .done(function(userSubjects){
                  var jsonsubjects=JSON.parse(userSubjects);
                  prepareSubjectTree(jsonsubjects,userTreeStr);

                      //console.log(subjectTreeStr);
                      //return subjectTreeStr;
                      
			 })
			 .fail(function(){
		           var msg='<p>There was some problem getting your subjects. Please try after some time.</p>';
		           $('#welcomeErrorMessage > p').remove();
		           $(msg).appendTo('#welcomeErrorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';				
			});	

}

function prepareSubjectTree(jsonsubjects,userTreeStr) {

		subjectTreeStr='<ul>'+
				'<li data-jstree=\'{"icon" : "icons/myexams.png"}\' id="exams" value="exams">My Exams'+
					'<ul id="subjectlist">';
		$.each(jsonsubjects,function(index,subject){
			
			var trimSubject=subject.subject.split(" ");
			subjectArray[index]=trimSubject[0];
			subjectTreeStr+='<li data-jstree=\'{"icon" : "icons/TestSubject.png"}\' id="'+trimSubject[0]+'" value="'+subject.id+'">'+trimSubject[0]+'</li>';
		
		});
		

		subjectTreeStr+='</ul>'+
		'</li>'+
		'</ul>';
		
        userTreeStr+=subjectTreeStr;
        
        $('#root').html(userTreeStr);	
        
        setSplitterAndTree();

}
	 
function setSplitterAndTree()	{
				  //saving the reference of welcome.jsp
				userId=$('#root').attr("value");
/*				var splitter = $('#panes').height(600).split({
				    orientation: 'vertical',
				    limit: 200,
				    position: '15%', // if there is no percentage it interpret it as pixels
				    onDrag: function(event) {
				        console.log(splitter.position());
				    }
				});*/
				
				$('#jstree').jstree({
					"core" : {
					    "themes" : {
					      "variant" : "large"
					    }
					  },
					  "checkbox" : {
					    "keep_selected_style" : false
					  },
					  "plugins" : []
				});
				
				
				
				 $('<div class="w3-cell w3-container w3-cell-middle"><button class="w3-bar-item w3-button w3-large w3-margin"  onclick="w3_close()">Close &times;</button></div>').appendTo('#jstree');
				
				$('#jstree').on("changed.jstree", function (e, data) {
					  var clickedData = data.selected;
					  //console.log(clickedData);
					  $('#clickData').attr("title",clickedData);
					  $('#clickData').text($('#'+clickedData).attr("value"));
					  if (clickedData=="root")
					     { //load the data
						  $('#treecontents').load('ilsWebPages/profile/userprofile.jsp'); 
							  showProgressBar("Loading user profile data, please wait...");
							     loadUserProfile(userId);	//loading user profile data
							  hideProgressBar();
					     }
					  else if (clickedData=="exams")
						  {
						      $('#treecontents').load('ilsWebPages/subjects/subjects.jsp');
						  }
					  //else if (clickedData=="Science" || clickedData=="Maths" || clickedData=="SocialStudies" ||  clickedData=="Geography")  { 
					   else if (subjectArray.indexOf(clickedData[0]) >=0 )  {
						  
						     $('#treecontents').load('ilsWebPages/exams/exams.jsp');
						  
					       }
					  else if (clickedData=="QuestionBank")  { 
						        $('#treecontents').load('ilsWebPages/questionbank/QuestionBank.jsp');
					       }
					  else if (clickedData=="QuestionPapers")  { 
						  
						     $('#treecontents').load('ilsWebPages/questionpapers/QuestionPapers.jsp');
						  
					       }
					  else if (clickedData=="ScheduledExams")  { 
							    	$('#treecontents').load('ilsWebPages/scheduledexams/ScheduledExams.jsp');
					       }
					  else if (clickedData=="Management")  { 
						  $('#treecontents').load('ilsWebPages/misc/UnderDevelopment.jsp');
				       }	
					  else if (clickedData=="Discuss")  { 
						  $('#treecontents').load('ilsWebPages/misc/UnderDevelopment.jsp');
				       }
					  else if (clickedData=="People")  { 
						  $('#treecontents').load('ilsWebPages/people/People.jsp');
				       }	
					  else if (clickedData=="Masters")  { 
						  if (userRole=="ROLE_TEACHER" || userRole=="ROLE_ORGANIZATION") {
						       $('#treecontents').load('ilsWebPages/misc/NotAuthorized.jsp');
						  }
					  }
					  else if (clickedData=="ClassLevel" || clickedData=="Subject" 
						       || clickedData=="ExamType" || clickedData=="QuestionType")  { 

						  if (userRole=="ROLE_TEACHER" || userRole=="ROLE_ORGANIZATION") {
						       $('#treecontents').load('ilsWebPages/misc/NotAuthorized.jsp');
						  }
				       }	
					  
					  });
				
			    if (userRole=="ROLE_STUDENT") {
					    	$('#QuestionBank').hide();
					    	$('#ScheduledExams').hide();
					    	$('#Management').hide();
			        }
			    
			    
	    
           
	
   }
	
	function loadUserProfile(userId)
	{
	   
		  //alert("trying to get data for"+userId);
		          
		          
	              $.ajax(
		           		{                             
		                   type: 'GET',
		                   url :'resteasy/qb/showuser/'+userId,
		                   success : function(data) {
		                   	                           //alert("User data received!");
		                	   
		                   	                           populateFields(data);
		                   	                           setEvents();
		                	                           disableFields();	
		                	                           //setValidation();
		                                            },
		           		   fail : function() {
				           		   var msg='<p>There was some problem loading user profile. Please try after some time.</p>';
						           $('#welcomeErrorMessage > p').remove();
						           $(msg).appendTo('#welcomeErrorMessage');	 	        	  
						 	       document.getElementById('error').style.display='block';	
				 	       }
		      
		           		});
	               //$('progressbar').fadeOut();
		  
		  
		  
	}
	
	function setEvents() {
		$('#editProfile').click(function(){
			if (userRole=="ROLE_STUDENT") { // for teachers and organization level attribute is not applicable
			    $('#level').removeAttr('disabled');
				$('#studiesin').removeAttr('disabled');
			}

			 $('input').removeAttr('disabled');
			 $('textarea').removeAttr('disabled');
			 $('#pro-butloadphoto').removeAttr('disabled');
		   });
	}
	
	function populateFields(data) {
		
		var userData =JSON.parse(data);
		
		//populating fields
		//$('#userId').val(userData.id);
		$('#firstName').val(userData.firstName);
		$('#lastName').val(userData.lastName);
		$('#phone').val(userData.phone);
		$('#email').val(userData.email);
		$('<p>'+userData.email+'</p>').appendTo('#display_email')
		
		//populate level information
		populateLevel(userData.userProfile.levelName);
		
		//$('#password').val(userData.password);		
		//$('#role').val(userData.role.role);
		if (userData.userProfile != null) {
				$('#bloodgroup').val(userData.userProfile.bloodGroup);
				$('#fathername').val(userData.userProfile.fatherName);
				$('#fatherphone').val(userData.userProfile.fatherPhone);	
				$('#fatheremail').val(userData.userProfile.fatherEmail);
				
				$('#mothername').val(userData.userProfile.motherName);
				$('#motherphone').val(userData.userProfile.motherPhone);	
				$('#motheremail').val(userData.userProfile.motherEmail);	
				
				$('#admission-no').val(userData.userProfile.admissionNumber);
				$('#section').val(userData.userProfile.studiesInSection);
				$('#studiesin').val(userData.userProfile.studiesIn);
				
            	$('#up-userPhoto').attr('src',userData.userProfile.photoUrl);
            	$('#up-zoom-userPhoto').attr('src',userData.userProfile.photoUrl);			
				

				populateClass(userData.userProfile.levelName,userData.userLevel.id);
				
				//Save users Level-name and level-id to global parameters
				$('#userLevelId').val(userData.userLevel.id);
				$('#userLevelName').val(userData.userProfile.levelName.toUpperCase());
		}
		$('#street').val(userData.address.street);
		$('#city').val(userData.address.city);
		$('#state').val(userData.address.state);	
		$('#zip').val(userData.address.zip);
		$('#otherinfo').val(userData.comments);
		//$('#country').val(userData.address.country);
		
		//$('#firstName').val(userData.recordStatus.updatedOn);
		//$('#firstName').val(userData.recordStatus.updatedBy);
		//$('#firstName').val(userData.recordStatus.isDeleted);		
		$('#lastUpdated').html('( Update on :'+userData.updateDate+'&nbsp;&nbsp;'+userData.updateTime+' | Updated by :'+userData.updateBy+')');
		
		//populate level information
		//populateLevel();
		
		$('#level').unbind().change(function() {
			var selvalue=$( "#level option:selected").val();
			if (selvalue != "") {
				populateClass(selvalue.toUpperCase(),"0");
			  }
			});
	}
	
	function populateLevel(levelname) {
		$('#level > option').remove();
		$.get('resteasy/qb/questionbank/levels')
		 .done(function(levels){
			 var jsonLevels = JSON.parse(levels);
			 $('<option value="" disabled selected>Choose Level</option>').appendTo('#level');
			 var prevLevel="dummy";
			 $.each(jsonLevels,function(index,level) {
				 if (prevLevel.toUpperCase() != level.level.toUpperCase()) {
					 if (level.level.toUpperCase() == levelname.toUpperCase()) {
				         $('<option value="'+level.level.toUpperCase()+'" selected>'+level.level.toUpperCase()+'</option>').appendTo('#level');
					 }
					 else {
						 $('<option value="'+level.level.toUpperCase()+'">'+level.level.toUpperCase()+'</option>').appendTo('#level'); 
					 }
				    prevLevel=level.level;
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
	
	function populateClass(level,levelid) {
		$('#studiesin > option').remove();
		$.get('resteasy/qb/questionbank/level/classes/'+level.toUpperCase())
		 .done(function(levels){
			 var jsonLevels = JSON.parse(levels);
			 //console.log(jsonLevels);
			 $('<option value="" disabled selected>Choose Class</option>').appendTo('#studiesin');
			 $.each(jsonLevels,function(index,level) {
				   //console.log(level.id);
				   if(level.id==levelid) {
				    $('<option value="'+level.id+'" selected>'+level.levelName.toUpperCase()+'</option>').appendTo('#studiesin');
				   } else {
					$('<option value="'+level.id+'">'+level.levelName.toUpperCase()+'</option>').appendTo('#studiesin'); 
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
	
	function disableFields()
	{
		$('input').attr('disabled','disabled');
		$('textarea').attr('disabled','disabled');
		$('#level').attr('disabled','disabled');
		$('#studiesin').attr('disabled','disabled');
		$('#pro-butloadphoto').attr('disabled','disabled');
		$('#_csrf').removeAttr('disabled'); //remove disabled for _csrf token
	
	}
	
  /* function setValidation()
   {
		$('#userProfileForm').validate({
		    errorPlacement: function (error, element) {
		        error.css({'color':'red', 'border':'none'});
		        error.insertAfter(element);
		    },
			rules: {
				firstName : {
					required:true
				},
				lastName : {
					required:true
				},
				phone : {
					required:true
				},				
				bloodgroup : {
					required:true
				},
				fathername : {
				    required:true
				},
				fatherphone : {
				    required:true,
				    digits:true
				},
				fatheremail : {
				    required:true,
				    email:true
				},	
				mothername : {
				    required:true
				},
				motherphone : {
				    required:true,
				    digits:true
				},
				motheremail : {
				    required:true,
				    email:true
				},	
				'admission-no' : {
				    required:true
				},
				section : {
				    required:true
				},	
				studiesin : {
				    required:true
				},
				street : {
				    required:true
				},
				city : {
				    required:true
				},	
				state : {
				    required:true
				},
				zip : {
				    required:true
				}
			},
			submitHandler: function(form) {
				showProgressBar("Updating user profile, please wait...");
				alert("coming to submit handler");
				updateUser();
				hideProgressBar();
           }
		});	  	   
     }
   
   function updateUser()
   {
	   alert("coming to update user");
	   var _csrf = $("input[name='_csrf']").val();
	   var firstname=$('#firstName').val();
	   var lastname=$('#lastName').val();
	   var email=$('#email').text();
	   var phone=$('#phone').val();
	   var bloodgroup=$('#bloodgroup').val();
	   var fathername=$('#fathername').val();
	   var fatherphone=$('#fatherphone').val();
	   var fatheremail=$('#fatherenail').val();
	   var mothername=$('#mothername').val();
	   var motherphone=$('#motherphone').val();
	   var motheremail=$('#motheremail').val();
	   var admissionNo=$('#admission-no').val();
	   var section=$('#section').val();
	   var studiesin=$('#studiesin').val();
	   var street=$('#street').val();
	   var city=$('#city').val();
	   var state=$('#state').val();
	   var zip=$('#zip').val();
	   
	   var profiledata={"firstName":firstname,"lastName":lastname,"email":email,"phone":phone,"bloodgroup":bloodgroup,"fathername":fathername,"fatherphone":fatherphone,"fatheremail":fatheremail,
			            "mothername":mothername,"motherphone":motherphone,"motheremail":motheremail,"admission-no":admissionNo,"section":section,"studiesin":studiesin,
			            "street":street,"city":city,"state":state,"zip":zip,"_csrf":_csrf};
	   console.log(profiledata);
       //$.post("resteasy/qb/updateUserProfile",$('#userProfileForm').serialize())
	   $.post("resteasy/qb/updateUserProfile",profiledata)
       .done(			        		
		        		function(data,status,xhr){    
                        var jdata=JSON.parse(data);
                        var mId=jdata.id;
                        if (mId == -1) {                                                	
                        	   $('#registrationStatus').html("User with specified email id is already registered. Please use other valid email.").fadeIn();
                        	   //$('#growl').notify('User with specified email id was not found. You need to register.','warn',{position:"bottom"});
                        	   //alert('User with specified email id was not found. You need to register.');
                        	   document.getElementById('warningMessage').style.display='block';
                        	  }
                        else  {
                        	   $('#registrationStatus').html("Congratulations! you have successfully registered with iQPoint.").fadeIn();
                        	   //$('#growl').notify('Congratulations! you have successfully registered with iQPoint.','success',{position:"bottom"});
                        	   
                        	   //$('#clickToLogin').show();
                                alert('Congratulations! you have successfully registered with iQPoint.');
                                document.getElementById('successMessage').style.display='block';
                               }
	                   })
	          .fail(function(data,status,xhr){
		           var msg='<p>There was some problem updating your profile. Please try after some time.</p>';
		           $('#welcomeErrorMessage > p').remove();
		           $(msg).appendTo('#welcomeErrorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';	
	          });
                  
   			}*/
   
     $('.w3-closebtn').click(function(){
    	 document.getElementById('successMessage').style.display='none';
     });
});
   
   
/*   $('#dialog').dialog({
		autoOpen: false,
		height: 280,
		modal: true,
		resizable: false,
		butCont:false,

		buttons: {
		'Yes continue': function() {
			        _ref.dialog('close');

			        $.post("../../resteasy/qb/updateUserProfile",$('#userProfileForm').serialize())
			                  .done(			        		
							        		function(data,status,xhr){    
                                               var jdata=JSON.parse(data);
                                               var mId=jdata.id;
                                               if (mId == -1) {                                                	
                                               	   $('#registrationStatus').html("User with specified email id is already registered. Please use other valid email.").fadeIn();
                                               	   $('#growl').notify('User with specified email id was not found. You need to register.','warn',{position:"bottom"});
                                               	  }
                                               else  {
                                               	   $('#registrationStatus').html("Congratulations! you have successfully registered with iQPoint.").fadeIn();
                                               	   $('#growl').notify('Congratulations! you have successfully registered with iQPoint.','success',{position:"bottom"});
                                               	   
                                               	   //$('#clickToLogin').show();

                                                      }
						                   })
						          .fail(function(data,status,xhr){
						        	    alert("User registration failed, please try after sometime."+status);
						        	  $('#growl').notify('Updating user profile failed, please try after sometime.','error',{position:"bottom"});
						        	  
						          });
		                                 
                    },
		'Cancel': function() {
		      _ref.dialog('close');
		     // Update Rating
		    }
		  }
		});	*/
   
	


	
	//var loginid=$('#loginid').text();
	//alert(loginid);
/*	$('button').on('click', function () {
		  $('#jstree').jstree(true).select_node('child_node_1');
		  $('#jstree').jstree('select_node', 'child_node_1');
		  $.jstree.reference('#jstree').select_node('child_node_1');
		});*/
  
/*	$('#getdata').click(function() { 
		                           $.ajax(
		                        		{                             
		                                type: 'GET',
	                                    url :'http://localhost:8080/Simple/resteasy/qb/showusers',
	                                    success : function(data) {
	                                    	                       $.each(JSON.parse(data),function(i,item){
	                                    	                    	   $('<tr><td>'+item.id+'</td>'
	                                    	                    		 +'<td>'+item.firstName+'</td>'
	                                    	                    		 +'<td>'+item.lastName+'</td>'
	                                    	                    		 +'<td>'+item.userName+'</td>'
	                                    	                    		 +'<td>'+item.password+'</td>'
	                                    	                    		 +'<td>'+item.role.role+'</td>'
	                                    	                    		 +'<td>'+item.address.street+'</td>'	
	                                    	                    		 +'<td>'+item.address.city+'</td>'
	                                    	                    		 +'<td>'+item.address.state+'</td>'	                                    	                    		 
	                                    	                    		 +'<td>'+item.address.zip+'</td></tr>').appendTo('#datadump tbody');                              	                    	   
	                                    	                       });
	                                    	                      
	                                                             },
		                        		fail : function() {alert('call failed');}
		                        		//complete : function() { alert('ajax call completed'); }
		                        		});
	                                  });
		
	                      $('#datadump tr:even').css({'background-color':'yellow'});
	
	       });*/
