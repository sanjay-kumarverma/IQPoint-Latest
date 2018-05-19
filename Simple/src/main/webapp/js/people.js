$(document).ready(function() 	{
	
    var userId=$('#root').attr("value");
    var userLevelId=$('#userLevelId').val();
    var userLevelName=$('#userLevelName').val();
    var userRole=$('#userRole').val();
    
    $('#people-butSearchButton').attr('disabled','disabled');
    $('#people-butAssignUser').attr('disabled','disabled');
	
	populateRoles();
	populateLevel();
	$('#people-level').attr('disabled','disabled');
	
	
	function populateRoles() {
		
		 var selectStr="";
		 
		if (userRole=="ROLE_TEACHER") {
			selectStr="<option value='' disabled selected>Choose user role</option>"+
					  "<option value='Student'>Student</option>";
		} else if (userRole=="ROLE_ORGANIZATION") {
			selectStr="<option value='' disabled selected>Choose user role</option>"+
			  		  "<option value='Student'>Student</option>"+
			  		  "<option value='Teacher'>Teacher</option>";
		} else if (userRole=="ROLE_SUPERUSER") {
			selectStr="<option value='' disabled selected>Choose user role</option>"+
			  		  "<option value='Student'>Student</option>"+
			  		  "<option value='Teacher'>Teacher</option>"+
			  		  "<option value='Organization'>Organization</option>";

			}
		  
			$(selectStr).appendTo('#people-role');
		
		}
	
	function populateLevel() {
		$('#level > option').remove();
		$.get('resteasy/qb/questionbank/levels')
		 .done(function(levels){
			 var jsonLevels = JSON.parse(levels);
			 $('<option value="" disabled selected>Choose Class/Level</option>').appendTo('#people-level');
			 $.each(jsonLevels,function(index,level) {
				 $('<option value="'+level.id+'">'+level.levelName+' ( '+level.level+' ) </option>').appendTo('#people-level');
			 });
		 })
		 .fail(function(){
	           var msg='<p>There was some problem getting classes / levels information. Please retry after some time.</p>';
	           $('#people-errorMessage > p').remove();
	           $(msg).appendTo('#people-errorMessage');	 	        	  
	 	       document.getElementById('error').style.display='block';				
		});
		
	}
	
	
     $('#people-role').unbind().change(function(){
    	 
    	 $('#people-butSearchButton').removeAttr('disabled'); 
    	 $('#people-butAssignUser').attr('disabled','disabled');
    	 
    	 var selRole=$('#people-role option:selected').val();
    	 
    	 if (selRole=="Student")
    		 {
    		   $('#people-level').removeAttr('disabled');
    		 }
    	 else
    		 {
    		   $('#people-level').val("");
    		   $('#people-level').attr('disabled','disabled');
    		 }
    	 
     });
     
     $('#people-butCreateUser').unbind().click(function(){
    	 
    	 
	 		if (userRole=="ROLE_TEACHER") {
				selectStr="<option value='' disabled selected>Choose user role</option>"+
						  "<option value='Student'>Student</option>";
			} else if (userRole=="ROLE_ORGANIZATION") {
				selectStr="<option value='' disabled selected>Choose user role</option>"+
				  		  "<option value='Student'>Student</option>"+
				  		  "<option value='Teacher'>Teacher</option>";
			} else if (userRole=="ROLE_SUPERUSER") {
				selectStr="<option value='' disabled selected>Choose user role</option>"+
				  		  "<option value='Student'>Student</option>"+
				  		  "<option value='Teacher'>Teacher</option>"+
				  		  "<option value='Organization'>Organization</option>";

			}
		  
			$(selectStr).appendTo('#people-registerAs');  	 
    	 
    	 
    	 document.getElementById('people-createUser').style.display='block';
    	 
    	 $('#people-butCreate').unbind().click(function(){
    		createUser();
    	 });
    	 
    	 $('#people-butCancelCreate').unbind().click(function(){
    		 document.getElementById('people-createUser').style.display='none';
     	 });    	 
    	 
     }); 
     
     function createUser() {
    	 
			var _csrf = $("input[name='_csrf']").val();
			var firstname=$( "#firstname").val();
			var lastname=$('#lastname').val();
			var email=$( "#email").val();
			var phone=$('#phone').val();
			var password=email;
			var userRole=$('#people-registerAs option:selected').val(); 
			
			
			if (userRole=='Student')
				userRole="3";
			else if (userRole=='Teacher')
				userRole="2";
			else if (userRole=='Organization')
				userRole="4";			
    	 
    	 if (userInfoValid(firstname,lastname,email,phone,userRole)) {
    		 
    		 $( "#error_msg").html("");
    		 
    		var userdata={'userid':userId,'firstname':firstname,'lastname':lastname,'email':email,'phone':phone,'password':password,'userrole':userRole,'_csrf':_csrf}; 
    		 
 	        $.post("resteasy/qb/userservice/createUser",userdata)
 	        	  .done(function(data,status,xhr){ 
            				document.getElementById('people-createUser').style.display='none';
                      		 var jdata=JSON.parse(data);
                             var mId=jdata.id;
	                             if (mId == -1) {                                                	
			               		           var msg='<p>User with specified email id is already registered. Please use other valid email.</p>';
			               		           $('#people-alertMessage > p').remove();
			               		           $(msg).appendTo('#people-alertMessage');	 	        	  
			               		 	       document.getElementById('alert').style.display='block';                              	   
	                             	   
	                             	  }
	                             else  {
		                		           var msg='<p>User created successfully.</p>';
		                		           $('#people-alertMessage > p').remove();
		                		           $(msg).appendTo('#people-alertMessage');	 	        	  
		                		 	       document.getElementById('alert').style.display='block'; 
		                		 	       
		                		 	       addUserToDisplayList(jdata);
	
	                                    }

                             }
		                   )
		          .fail(function(data,status,xhr){
		        	   document.getElementById('people-createUser').style.display='none';
			           var msg='<p>There was some problem creating user. Please retry after some time.</p>';
			           $('#people-errorMessage > p').remove();
			           $(msg).appendTo('#people-errorMessage');	 	        	  
			 	       document.getElementById('error').style.display='block';
		        	  
		          });			    		 
    	 }

     }
     
     
	function addUserToDisplayList(value) {
		var peopleStr="";
		if ($('#people-list > table').length==0) {
			    peopleStr='<table id="people-list-table" class="w3-table-all w3-hoverable w3-small"><tr><th>Name</th><th>Email</th><th>Phone</th><th>Updated On</th><th>Updated By</th><th></th></tr>';
				peopleStr+= '<tr ><td class="w3-small">'+value.firstName.toUpperCase()+'&nbsp;'+value.lastName.toUpperCase()+'</td>';
				peopleStr+= '<td>'+value.email+'</td><td>'+value.phone+'</td><td>'+value.updateDate+'</td><td>'+value.updateBy+'</td><td><span id=view-'+value.id+'><i class="fa fa-eye"></i></span></td></tr>';
				peopleStr+='</table>';
				$(peopleStr).insertAfter('#people-ListTitle');
		} else {
			peopleStr= '<tr ><td class="w3-small">'+value.firstName.toUpperCase()+'&nbsp;'+value.lastName.toUpperCase()+'</td>';
			peopleStr+= '<td>'+value.email+'</td><td>'+value.phone+'</td><td>'+value.updateDate+'</td><td>'+value.updateBy+'</td><td><span id=view-'+value.id+'><i class="fa fa-eye"></i></span></td></tr>';
			$(peopleStr).appendTo('#people-list-table');
			
		}
			
		
	}     
     
     function userInfoValid(firstname,lastname,email,phone,userRole) {

			var isValid=true;
			if (firstname.trim()=="") {
				//$( "#firstname").attr("placeholder","Firstname is required.");
				$( "#error_msg").html("First name is required.");
				isValid=false;
			} else if (lastname.trim()=="") {
				//$( "#lastname").attr("placeholder","Lastname is required.");
				$( "#error_msg").html("last name is required.");
				isValid=false;
			} else if (email.trim()=="" || !validateEmail(email.trim())) {
				//$( "#email").attr("placeholder","email is required or email is not valid.");
				$( "#error_msg").html("email is missing or email is not valid.");
				isValid=false;
			} else if (phone.trim()=="") {
				//$( "#phone").attr("placeholder","Phone is required.");
				$( "#error_msg").html("Phone is required.");
				isValid=false;
			} else if (userRole.trim()=="") {
				$( "#error_msg").html("Please select role.");
				isValid=false;
			} else {
				isValid=true;
			}
    	 
    	return isValid;
     }
     
     

     
	
	
	 $('#people-butSearchButton').unbind().click(function(){
			var _csrf = $("input[name='_csrf']").val();
			var role=$( "#people-role option:selected").val();
			var dateFrom=$('#people-dateFrom').val();
			var dateTo=$('#people-dateTo').val();
			var freeText=$('#people-freeText').val().toUpperCase();
			var level=$('#people-level option:selected').val();
			
         var searchData={"userid":userId,"userRole":userRole,"role":role,'level':level,"datefrom":dateFrom,"dateto":dateTo,"freetext":freeText,"_csrf":_csrf};
         //console.log(searchData);
		           $.post("resteasy/qb/people/searchPeople",searchData)
		    		
		            .done( function(data,status,xhr){ 
		          	         var jsonData=JSON.parse(data);
		          	         
			          	         if (jsonData.length == 0) {
				      		           var msg='<p>No results found for this search criteria.</p>';
				    		           $('#people-alertMessage > p').remove();
				    		           $(msg).appendTo('#people-alertMessage');	 	        	  
				    		 	       document.getElementById('alert').style.display='block';  
				    		 	      
				    		 	      //remove people list if they were existing previously
				    		 	     // $('#questionsList > table').remove();
			          	         }
			          	         else {

		                           //console.log(data);
			          	           enableAssignButton(); // if user is organization and has searched students
		                           preparePeopleList(jsonData);
		                         }
		                         
			                   })
			          .fail(function(data,status,xhr){
				           var msg='<p>There was some problem getting the list of people. Please retry after some time.</p>';
				           $('#people-errorMessage > p').remove();
				           $(msg).appendTo('#people-errorMessage');	 	        	  
				 	       document.getElementById('error').style.display='block';
			        	  
			          });           
					
					
				 });
	 
	 function enableAssignButton() {
		 $('#people-butAssignUser').attr('disabled','disabled');
		 var role=$( "#people-role option:selected").val();
		 if (userRole=="ROLE_ORGANIZATION") {
			  if (role.toUpperCase()=='STUDENT') {
				  $('#people-butAssignUser').removeAttr('disabled');
			  }
		 }
		 
	 }
	 
	 
	 function preparePeopleList(ppldata) {

	     $('#people-list > table').remove();
			//var jsonData = JSON.parse(data);
	        var peopleStr='<table id="people-list-table" class="w3-table-all w3-hoverable w3-small"><tr><th>Name</th><th>Email</th><th>Phone</th><th>Updated On</th><th>Updated By</th><th></th></tr>';
			$.each(ppldata,function(index,value){
				peopleStr+= '<tr id="tr-'+value.id+'"><td class="w3-small">'+value.firstName.toUpperCase()+'&nbsp;'+value.lastName.toUpperCase()+'</td>';
				peopleStr+= '<td>'+value.email+'</td><td>'+value.phone+'</td><td>'+value.updateDate+'</td><td>'+value.updateBy+'</td><td><span id=view-'+value.id+'><i class="fa fa-eye"></i></span></td></tr>';
				});	
			peopleStr+='</table>';
			$(peopleStr).insertAfter('#people-ListTitle');
            $('span').unbind().click(function(){
            	//console.log($(this).attr('id'));
            	var clickedOn=$(this).attr('id').split("-");
            	
            	if (clickedOn[0]=="view") {
            		//console.log("display user details with id "+clickedOn[1]);
            		//document.getElementById('people-viewDetails').style.display='block';
            		showUserDetails(clickedOn[1]);
            	}
            	
            	
            	
            });
		 
	 }
	 
	 function showUserDetails(userid) {
		 
         $.get("resteasy/qb/showuser/"+userid)
 		
         .done( function(data,status,xhr){ 
        	 
        	     
       	         var jsonData=JSON.parse(data);
                        displayDetails(jsonData);
	                   })
	          .fail(function(data,status,xhr){
		           var msg='<p>There was some problem getting user details. Please retry after some time.</p>';
		           $('#people-errorMessage > p').remove();
		           $(msg).appendTo('#people-errorMessage');	 	        	  
		 	       document.getElementById('error').style.display='block';
	        	  
	          });
	 }
	 
	 function displayDetails(userData) {
		 
			var userStr='<table id="people-detailsTable" class="w3-table-all w3-small w3-center w3-border">';
			 userStr+='<tr><td><label class="w3-label w3-text-blue">Name</label></td><td colspan="3">'+userData.firstName+'&nbsp;'+userData.lastName+'</td><td ><label class="w3-label w3-text-blue">Level</label></td><td>'+userData.userLevel.level+'('+userData.userLevel.levelName+') </td></tr>';
			 userStr+='<tr><td><label class="w3-label w3-text-blue">Email</label></td><td colspan="2">'+userData.email+'</td><td><label class="w3-label w3-text-blue">Phone</label></td><td colspan="2">'+userData.phone+'</td></tr>';
			 if (userData.address.street.trim().toUpperCase()=="NOT AVAILABLE") {
			       userStr+='<tr><td><label class="w3-label w3-text-blue">Address</label></td><td colspan="4">'+userData.address.street+'</td></tr>';
			 } else {
				   userStr+='<tr><td><label class="w3-label w3-text-blue">Address</label></td><td colspan="4">'+userData.address.street+','+userData.address.city+','+userData.address.state+','+userData.address.zip+'</td></tr>';				 
			 }
			 userStr+='<tr><td><label class="w3-label w3-text-blue">Father</label></td><td>'+userData.userProfile.fatherName+'</td><td><label class="w3-label w3-text-blue">Email</label></td><td>'+userData.userProfile.fatherEmail+'</td><td><label class="w3-label w3-text-blue">Phone</label></td><td>'+userData.userProfile.fatherPhone+'</td></tr>';
			 userStr+='<tr><td><label class="w3-label w3-text-blue">Mother</label></td><td>'+userData.userProfile.motherName+'</td><td><label class="w3-label w3-text-blue">Email</label></td><td>'+userData.userProfile.motherEmail+'</td><td><label class="w3-label w3-text-blue">Phone</label></td><td>'+userData.userProfile.motherPhone+'</td></tr>';			 
			 userStr+='<tr><td><label class="w3-label w3-text-blue">Update date & time</label></td><td>'+userData.updateDate+' ('+userData.updateTime+')</td><td><label class="w3-label w3-text-blue">Updated by</label></td><td>'+userData.updateBy+'</td><td></td><td></td></tr>';
	
			 if (userRole=="ROLE_ORGANIZATION" || userRole=="ROLE_SUPERUSER") { //only super user and organization can promote
				   var selectStr="";
				 
	               if (userRole=="ROLE_ORGANIZATION") {
						selectStr="<option value='' disabled selected>Choose user role</option>"+
						  		  "<option value='2'>Teacher</option>";
					} else if (userRole=="ROLE_SUPERUSER") {
						selectStr="<option value='' disabled selected>Choose user role</option>"+
						  		  "<option value='2'>Teacher</option>"+
						  		  "<option value='4'>Organization</option>";
	
						}			 
			 
			 

			    	userStr+='<tr><td><label class="w3-label w3-text-blue">Promote to</label></td><td><select class="w3-select" id="promote-role">';
			    	userStr+=selectStr;
			    	userStr+='</select></td><td><span id="promoteMsg" class="w3-text-red"></span></td></tr>';

			 }
			 
			 userStr+='<tr><td colspan="6" class="w3-center"><button id="people-butPromote" class="w3-button w3-yellow w3-small w3-hover-red w3-margin">Promote</button><button id="people-butOk" class="w3-button w3-yellow w3-small w3-hover-red w3-margin">Close</button></td></tr>';
			 userStr+='</table>';
			 
			 $('#people-detailsInfo > table').remove();
			 $(userStr).appendTo('#people-detailsInfo');
			 
			 document.getElementById('people-viewDetails').style.display='block';
			 $('#people-butPromote').attr('disabled','disabled');
			 
			 $('#people-butOk').unbind().click(function(){
				 document.getElementById('people-viewDetails').style.display='none';
				 
			 });
			 
			 $('#promote-role').unbind().change(function(){
				 $('#people-butPromote').removeAttr('disabled'); 
			 });
			 
			 $('#people-butPromote').unbind().click(function(){
				 var promoteTo=$('#promote-role option:selected').val();
				 console.log(promoteTo);
				 if (promoteTo=="") {
                    $('#promoteMsg').html('Please select role first.');				 
				 } else {
					 $('#promoteMsg').html('');
					 var _csrf = $("input[name='_csrf']").val();
					 var promoteData={"userid":userData.id,"role":promoteTo,"_csrf":_csrf};
					 console.log(promoteData);
					 $.post("resteasy/qb/people/promote",promoteData)
			              .done( function(data,status,xhr){ 
			            	       document.getElementById('people-viewDetails').style.display='none';
			            	       
			            	       $('#tr-'+userData.id).remove();
			            	       
			      		           var msg='<p>User was promoted successfully.</p>';
			    		           $('#people-alertMessage > p').remove();
			    		           $(msg).appendTo('#people-alertMessage');	 	        	  
			    		 	       document.getElementById('alert').style.display='block';  
				                   })
				          .fail(function(data,status,xhr){
				        	   document.getElementById('people-viewDetails').style.display='none';
					           var msg='<p>There was some problem promoting selected user. Please retry after some time.</p>';
					           $('#people-errorMessage > p').remove();
					           $(msg).appendTo('#people-errorMessage');	 	        	  
					 	       document.getElementById('error').style.display='block';
				        	  
				          });					 
					 
				 }
				 
			 });			 
		 
	 }
	 
		function validateEmail(email) {
		    
		    var atpos = email.indexOf("@");
		    var dotpos = email.lastIndexOf(".");
		    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length) {
		        return false; }
		    else
		      { return true; }
		    }     
	     	 
	 
	
	
	
});