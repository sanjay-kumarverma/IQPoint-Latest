  $(document).ready(function() {
	  
      

      
      var userId=$('#root').attr('value');
      loadUserSubjects(userId);

      

		
		
		function loadUserSubjects(userId)
		{
		   
			  //alert("trying to get data for"+userId);
			          
			          
		              $.ajax(
			           		{                             
			                   type: 'GET',
			                   url :'resteasy/qb/subject/subjectlist/'+userId,
			                   success : function(data) {
			                   	                           prepareSubjectList(data);
			                                            },
			           		   fail : function() {alert('There was some problem getting user information. Please try after sometime.');}
			      
			           		});

		}
		
		function prepareSubjectList(data)
		{
			
		      
			var jsonData = JSON.parse(data);
			$.each(jsonData,function(index,value){
				//$(' <button id="'+value.id+'" class="w3-btn-block w3-left-align w3-pale-blue w3-leftbar w3-border-blue w3-border">'+
				$(' <button id="'+value.id+'" class="w3-btn-block w3-left-align w3-pale-blue w3-leftbar">'+
                        '<h4>'+value.subject+'</h4>'+
                        '</button>').insertAfter('#title');
				  var afterid=value.id;
				  

			      
				  if (value.topics.length>0)
					{
					   var topicstr="";
					   for(x=0;x<value.topics.length;x++) {
						   
						   //if there are topics in above subject change its color to green
						   $('#'+afterid).removeClass("w3-pale-blue w3-border-blue").addClass("w3-pale-yellow w3-border-yellow");
						   //$('#'+afterid).addClass("w3-pale-yellow w3-border-yellow");
						   
/*						   //getting exam information
						     var maxMarks=0;
						     var currScore=0;
						     var passPercent=0;
						     var topicClass="w3-text-blue";
						     var numberOfTests=0;
						   if (value.topics[x].exams.length > 0) // if exams information is there get the exams and aggregate marks
							   {
							     //console.log("exam array length--->"+value.topics[x].exams.length);

							     //console.log("max score just initiated  -->"+maxMarks);
							     for(e=0;e<value.topics[x].exams.length;e++) {
							    	 
						    	 
							    	maxMarks+=Number(value.topics[x].exams[e].maxScore);
							    	//console.log("max score  -->"+maxMarks);
							    	currScore+=Number(value.topics[x].exams[e].currentScore);
							    	passPercent+=Number(value.topics[x].exams[e].passPercent);
							    	
							    	 
							     }

							    maxMarks=Math.round(maxMarks/e);
							    currScore=Math.round(currScore/e);
							    passPercent=Math.round(passPercent/e);
							    numberOfTests=e;
							    console.log("average max marks -->"+maxMarks);
							    console.log("average curr marks -->"+currScore);
							    console.log("average pass marks -->"+passPercent);
							    
							    var avgScore=Math.round((currScore/maxMarks)*100);
							    

							    
							    //console.log("Average score -->"+Math.round(avgScore*100));
							    //console.log("Pass percent -->"+passPercent);
							    var result="Fail";
							    if (avgScore < passPercent)
							    	topicClass="w3-red";
							    else  
							    	{ topicClass="w3-green"; result="Pass"; } 
							   
							   }
						   
 						   
						   //exams information ends
						         if (numberOfTests >0) {
						   
		                              topicstr+='<p class="'+topicClass+'"><b>'+value.topics[x].topic+'&nbsp;&nbsp;</b>(&nbsp;'+result+'&nbsp;)<div>Tests taken :<b>'+numberOfTests+'</b>&nbsp;&nbsp;Marks scored :<b>'+avgScore
		                                                                                                                       +'</b>&nbsp;&nbsp;Passing score:<b>'+
		                                                                                                                         passPercent+'</b></div></p>';
						         }
						         else
						        	 {
				                      topicstr+='<p class="w3-blue"><b>'+value.topics[x].topic+'</b><div>Tests taken :<b>'+numberOfTests+'</b></div></p>';		        	 
						        	 }*/
						   
						   topicstr+='<p><b>'+value.topics[x].topic+'</b></p>';
					   }	
					   
					   var divid="topic"+afterid;
					   $('<div id="'+divid+'" class="w3-accordion-content w3-container">'+topicstr+
					   '</div>').insertAfter('#'+afterid);
					}
				  else //when there are no topics
					  {
					   var divid="topic"+afterid;
					   topicstr='<p>No assigned topics currently!</p>';
					   $('<div id="'+divid+'" class="w3-accordion-content w3-container">'+topicstr+
					   '</div>').insertAfter('#'+afterid);				  
					  }
				  

			});
		      $('button').click(function(){
		    	  var id=$(this).next().attr('id');
		    	  openAccordian(id);
		      });		
			
			
		}
		
		

	      
	      
			function openAccordian(id) {
			    var x = document.getElementById(id);

			    if (x.className.indexOf("w3-show") == -1) {
			        x.className += " w3-show";
			    } else { 
			        x.className = x.className.replace(" w3-show", "");
			    }
			}		
		
	  
  });

