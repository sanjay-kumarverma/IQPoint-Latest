  	function showProgressBar(message) {
		$('#progressbarMessage > p').remove();
		$('<p>'+message+'</p>').appendTo('#progressbarMessage');
		document.getElementById('progressbar').style.display='block';
	}
	
	function hideProgressBar() {
		document.getElementById('progressbar').style.display='none';
	}
	
	//function to show warning message with ok and cancel buttons
    function showWarning(message) {
    	//console.log("in warning method");
    	//var buttonClicked="";
    	var messageDiv=$('#warningMessageWithButtons').find('#warningMessage');
    	$(messageDiv).find('p').remove();
    	$('<p>'+message+'</p>').appendTo(messageDiv);
    	document.getElementById('warningMessageWithButtons').style.display='block';	
    	//console.log("button clicked  "+buttonClicked);
    	//return buttonClicked;
    }
    
