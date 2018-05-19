$(document).ready(function(){
	/*	   $('.pageheader').load('PageHeader.html');
	   $('.pagefooter').load('PageFooter.html');	*/
	
	$('.form-field').hover(function() { $(this).addClass('zebraHover'); }, 
			               function() { $(this).removeClass('zebraHover');} );
	
	$('.w-form form').validate({
	    errorPlacement: function (error, element) {
	        error.css({'color':'red', 'border':'none'});
	        error.insertAfter(element);
	    },
		rules: {
			username : {
				required:true
			},
			password:{
				required:true,
			    minlength:6,
			    maxlength:100
			}
		}
	});
	

	
});
	
	
