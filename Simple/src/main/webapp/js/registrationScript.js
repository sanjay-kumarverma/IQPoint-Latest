$(document).ready(function(){
	
 
/*	$('#registerUser').click(function() { 	  
	    	$('#dialog').dialog('open');
	
	       });*/
	
/*	$('.pageheader').load('../../PageHeader.html');
	$('.pagefooter').load('../../PageFooter.html');*/
	//var whichButton="continue";
	
/*	$('.form-field').hover(function() { $(this).addClass('zebraHover'); }, 
			               function() { $(this).removeClass('zebraHover');} ); */
	
	$('form').validate({
	    errorPlacement: function (error, element) {
	        error.css({'color':'red', 'border':'none'});
	        error.insertAfter(element);
	    },
		rules: {
			firstname : {
				required:true
			},
			lastname : {
				required:true
			},
			email: {
				required:true,
			    email:true
			},
			password:{
				required:true,
			    minlength:8,
			    maxlength:12
			},
			confirmPassword : {
				equalTo: "#password"
			},
			registerAs : {
			    required:true
			    //notEqualTo:'select'
			}
		
		},
		submitHandler: function(form) {
			console.log("coming to submit");
			registerUser();

        }
	});
	

	//$('#registerUser').unbind().click(function(){
	
/*	$('#registerNow').unbind().click(function(){
		
		var firstname=$( "#firstname").val();
		var lastname=$( "#lastname").val();
		var email=$('#email').val();
		var password=$('#password').val();
		var confirmPassword=$('#confirmPassword').val();
		var registerAs=$('#registerAs option:selected').val();
		
		console.log(firstname);
		console.log(lastname);
		console.log(email);
		console.log(password);
		console.log(confirmPassword);
		console.log(registerAs);
		
		if (infoValid=validateInfo(firstname,lastname,email,password,confirmPassword,registerAs))
			{
			 registerUser(firstname,lastname,email,password,confirmPassword,registerAs);
			}
		else
			{
	           var msg='<p>All values are mandatory and password, confirm password should match. Please correct information and retry.</p>';
	           $('#reg-errorMessage > p').remove();
	           $(msg).appendTo('#reg-errorMessage');	 	        	  
	 	       document.getElementById('error').style.display='block';			
			}
		
	});
	
	function validateInfo(firstname,lastname,email,password,confirmPassword,registerAs){
		
		var isValid=false;
		
		console.log(validateEmail(email));
		
		if (firstname != "" && lastname != "" && email != "" && validateEmail(email) && password != "" && confirmPassword != "" && registerAs != "")
			{
			    if (password.trim() == confirmPassword.trim()) {
			    	isValid=true
			    }
			}
		
		console.log(isValid);
		return isValid;
		
	}*/
	

	
	function registerUser(firstname,lastname,email,password,confirmPassword,registerAs) {
		
/*				var _csrf = $("input[name='_csrf']").val();

		
		         var userData={"firstname":firstname,"lastname":lastname,"email":email,"password":password,"registerAs":registerAs,"_csrf":_csrf};	
		         
		         console.log(userData);*/
		
		document.getElementById('reg-dialog').style.display='block';
		$('#reg-YesButton').unbind().click(function(){
			console.log("coming here");
			document.getElementById('reg-dialog').style.display='none';

			
	        $.post("../../resteasy/qb/userservice/registerUser",$('form').serialize())
            .done(			
            				
			        		function(data,status,xhr){    
                              var jdata=JSON.parse(data);
                              var mId=jdata.id;
                              if (mId == -1) {                                                	
                              	   //$('#registrationStatus').html("User with specified email id is already registered. Please use other valid email.").fadeIn();
                              	   //$('#growl').notify('User with specified email id is already registered. Please use other valid email.','warn',{position:"bottom"});
                		           var msg='<p>User with specified email id is already registered. Please use other valid email.</p>';
                		           $('#reg-alertMessage > p').remove();
                		           $(msg).appendTo('#reg-alertMessage');	 	        	  
                		 	       document.getElementById('alert').style.display='block';                              	   
                              	   
                              	  }
                              else  {
	                		           var msg='<p>Congratulations! you have successfully registered with iQPoint.</p>';
	                		           $('#reg-alertMessage > p').remove();
	                		           $(msg).appendTo('#reg-alertMessage');	 	        	  
	                		 	       document.getElementById('alert').style.display='block'; 

                                     }
		                   })
		          .fail(function(data,status,xhr){
			           var msg='<p>There was some problem registering user. Please retry after some time.</p>';
			           $('#reg-errorMessage > p').remove();
			           $(msg).appendTo('#reg-errorMessage');	 	        	  
			 	       document.getElementById('error').style.display='block';
		        	  
		          });			
		});
		$('#reg-CancelButton').unbind().click(function(){
			document.getElementById('reg-dialog').style.display='none';
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

