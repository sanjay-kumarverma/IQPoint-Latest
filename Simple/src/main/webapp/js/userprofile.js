  $(document).ready(function() {
	     
	            var userId=$('#root').attr("value");
	            var _csrf = $("input[name='_csrf']").val();
	            var userRole=$('#userRole').val();
	            
	            if (userRole==="ROLE_TEACHER" || userRole==="ROLE_SUPERUSER" || userRole==="ROLE_ORGANIZATION") {
	            	    $('#hideForRole').hide();
	            }
	            

	  
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
						//alert("coming to submit handler");
						//updateUser();
			            chkForPassword();
						hideProgressBar();
		           }
				});	  	   
		    // }
				
		   function chkForPassword() {
			   var password= $("#passwd").val(); 
			   var confirmPassword = $("#confirmPasswd").val(); 

			   if (password!==null && confirmPassword!==null) {
				   
				   password=password.trim();
				   confirmPassword=confirmPassword.trim();
				   if (password==confirmPassword && (password.length >=6 && password.length <=12 )) {
					   updateUser();
				   }
				   else  {
			           var msg='<p>Password provided by you does not match confirm password or password should be between 6 to 12 characters. Try again.</p>';
			           $('#welcomeAlertMessage > p').remove();
			           $(msg).appendTo('#welcomeAlertMessage');	 	        	  
			 	       document.getElementById('alert').style.display='block'; 		
				   }
			    }
			   else {
                   updateUser();				  
			   }
			  
		   }
		   
		   function updateUser()
		   {
			   //alert("coming to update user in update user profile");
			   var _csrf = $("input[name='_csrf']").val();
			   var firstname=$('#firstName').val();
			   var lastname=$('#lastName').val();
			   var email=$('#display_email').text();
			   console.log(email);
			   var phone=$('#phone').val();
			   var bloodgroup=$('#bloodgroup').val();
			   var fathername=$('#fathername').val();
			   var fatherphone=$('#fatherphone').val();
			   var fatheremail=$('#fatheremail').val();
			   var mothername=$('#mothername').val();
			   var motherphone=$('#motherphone').val();
			   var motheremail=$('#motheremail').val();
			   var admissionNo=$('#admission-no').val();
			   var section=$('#section').val();
			   var studiesin=$('#studiesin').val();
			   var level=$('#level').val();
			   var street=$('#street').val();
			   var city=$('#city').val();
			   var state=$('#state').val();
			   var zip=$('#zip').val();
			   var otherinfo=$('#otherinfo').val();
			   var password = $('#passwd').val();
			   var confirmPassword = $('#confirmPasswd').val();
			       
			   
			   if (password===null || confirmPassword===null) { 
				    password="noChange"
			   }
			   
				   var profiledata={"firstName":firstname,"lastName":lastname,"email":email,"phone":phone,"bloodgroup":bloodgroup,"fathername":fathername,"fatherphone":fatherphone,"fatheremail":fatheremail,
						            "mothername":mothername,"motherphone":motherphone,"motheremail":motheremail,"admission-no":admissionNo,"section":section,"studiesin":studiesin,"level":level,
						            "street":street,"city":city,"state":state,"zip":zip,"otherinfo":otherinfo,"xyz":password,"_csrf":_csrf};
			   
			   
			   console.log(profiledata);
		       //$.post("resteasy/qb/updateUserProfile",$('#userProfileForm').serialize())
			   $.post("resteasy/qb/updateUserProfile",profiledata)
		       .done(			        		
				        		function(data,status,xhr){    
		                        var jdata=JSON.parse(data);
		                        var mId=jdata.id;
		                        if (mId == -1) {                                                	
	                		           var msg='<p>User with specified email id is not found. You need to register with IQPoint</p>';
	                		           $('#welcomeAlertMessage > p').remove();
	                		           $(msg).appendTo('#welcomeAlertMessage');	 	        	  
	                		 	       document.getElementById('alert').style.display='block';   
		                        	  }
		                        else  {
	                		           var msg='<p>You have successfully updated user profile.</p>';
	                		           $('#welcomeAlertMessage > p').remove();
	                		           $(msg).appendTo('#welcomeAlertMessage');	 	        	  
	                		 	       document.getElementById('alert').style.display='block'; 
	                		 	       
	                					//Save users Level-name and level-id to global parameters
	                					$('#userLevelId').val(studiesin);
	                					$('#userLevelName').val(level.toUpperCase());
	                		 	       
	                		 	       disableFields();
		                               }
			                   })
			          .fail(function(data,status,xhr){
				           var msg='<p>There was some problem updating your profile. Please try after some time.</p>';
				           $('#welcomeErrorMessage > p').remove();
				           $(msg).appendTo('#welcomeErrorMessage');	 	        	  
				 	       document.getElementById('error').style.display='block';	
			          });
		                  
		   			}
		   
			function disableFields()
			{
				$('input').attr('disabled','disabled');
				$('textarea').attr('disabled','disabled');
				$('#level').attr('disabled','disabled');
				$('#studiesin').attr('disabled','disabled');
				$('#_csrf').removeAttr('disabled'); 
				$('#pro-butloadphoto').attr('disabled','disabled');
				//remove disabled for _csrf token
				//$('#level').attr('background','#dddddd');
				//$('#studiesin').attr('background','#dddddd');
			
			}
			
          $('#pro-butloadphoto').unbind().click(function(){
        	  document.getElementById('photo').style.display='block';
        	  //$('#_csrf').attr('value',_csrf);
        	  //$('#userid').attr('value',userId);
        	  
          });
          
/*         $('#butUploadFile').unbind().click(function(){
           
		   $.ajaxSetup({
				      headers: {"X-CSRF-Token": _csrf,
				                "Content-Type": "application/json"
				                }
				    }); 
		   var uploadFile=$('#uploadFle').val();
		   
	        	  //$('#_csrf').attr('value',_csrf);
	        	  //$('#userid').attr('value',userId);        	 
        	 var form = new FormData($("#fileUploadForm")[0]);
        	 $.ajax({
        	         url: your_url,
        	         method: "POST",
        	         dataType: 'json',
        	         data: form,
        	         processData: false,
        	         contentType: false,
        	         success: function(result){},
        	         error: function(er){}
        	 });        	 
        	 
         }); */
         
         $('#fileUploadForm').submit( function(e) {
        	    document.getElementById('photo').style.display='none';
        	    e.preventDefault();

        	   // var data = $('#fileUploadForm').serialize(); // <-- 'this' is your form element
        	    var form = new FormData($("#fileUploadForm")[0]);
     		   $.ajaxSetup({
				      headers: {"X-CSRF-Token": _csrf
				                }
				    }); 
        	    $.ajax({
        	            url: 'resteasy/qb/upload/user/'+userId,
        	            data: form,
        	            cache: false,
        	            contentType: false,
        	            processData: false,
        	            type: 'POST',     
        	            success: function(data){
        	            	var jsonData=JSON.parse(data);
        	            	var url=jsonData.url;
        	            	if (url=="FileSizeOrTypeError") {
      				           var msg='<p>File should be image (Gif/PNG/JPEG/BMP) and size should not be more than 1 mb. Please try again.</p>';
    				           $('#welcomeErrorMessage > p').remove();
    				           $(msg).appendTo('#welcomeErrorMessage');	 	        	  
    				 	       document.getElementById('error').style.display='block';        	            		
        	            	} else {
	        	            	$('#up-userPhoto').attr('src',url)
	        	            	$('#up-zoom-userPhoto').attr('src',url)
        	            	}
        	            },
        	            error:function(err){
 				           var msg='<p>There was some problem uploading profile photo. Please try after some time.</p>';
				           $('#welcomeErrorMessage > p').remove();
				           $(msg).appendTo('#welcomeErrorMessage');	 	        	  
				 	       document.getElementById('error').style.display='block';	
        	            	}
        	            });
        	    
         });
	  
  });

