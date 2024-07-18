
	function makeOTPRequest()
	{
		showTimer();
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		$.ajax({
		      type: 'POST',
		      url: "./generateOTP.htm?v=" + new Date().getTime(),
		      async : false,
		      beforeSend: function(xhr) {
		            // here it is
		            xhr.setRequestHeader(header, token);
		        },
		      success: function(resultData) { 
		    	  var json = JSON.parse(resultData);
		    	  var data = json[0];
		    	  if (data.outcome == "success")
	    		  {
		    		  $('#ref').val(data.ref);
			    	  $('#myModal').modal('show');
	    		  }
		    	  else
	    		  {
	    		  	alert("OTP Generation failed : " + data.message);
	    		  }
		    	
		   	  }
		});
	}
	
	function verifyOTP(otpStatus)
	{
		var userName = $("#otpUserName").val();
		var sOTP = "";
		var check= false;
		
		$('div#otp input[type="text"]').each(function(idx, elem){
			if($(elem).val() == ""){
				bootbox.alert("Please Enter OTP Properly");
				check=true;
				return false;
			}else{
				sOTP += $(elem).val();
				check=false;
			}
		});
		if(!check){
			if(otpStatus == "LOGIN"){
				$('#txtOTP').val(sOTP);
				$("#otpUser").val(userName);
		 		$("#otpLogin").submit();
		 		$("#otpFunCall").attr("disabled","disabled");
		 		 $('#loader').removeClass('hidden');
			}else{
				 if ($("#dateOfbirth").val() == ""){
				 $("#dateOfbirth").val(new Date());
				}
					$("#userInputOtp").val(sOTP);
					event.preventDefault(); //prevent default action 
				    var post_url = $("#userForm").attr("action"); //get form action url
				    var request_method = $("#userForm").attr("method"); //get form GET/POST method
					var data=$("#userForm");
				    var form_data = new FormData(data[0]);//$("#userForm").serializeArray(); //Encode form elements for submission
				    
				    	//window.setTimeout(function(){
							//const hasAlert = $('.bootbox.modal.fade.bootbox-alert.in').length > 0;
							//if (!hasAlert){
								bootbox.confirm("Do you want to continue ?", function(result) {
						  			if (result == true) {
								 		 $('#loader').removeClass('hidden');
						  			   $.ajax({
									        url: post_url,
									        type: request_method,
									        data: form_data,
									        contentType: false,
									        cache: false,
									        processData: false,
									      }).done(function(response) {
									     	  $('#loader').addClass('hidden');;
									        $('#myModal').modal('hide');
									        var val = JSON.parse(response); 
											if (val[0].isSignedUp == true) {
												 bootbox.alert({
							  						    message: "Public User Signed Up Successfully",
							  						    callback: function () {$('#otpModal').modal('hide'); location.reload(true); }
							  						 });
											}else{
												if(val[0].badOtp == false){
												 bootbox.alert({
							  						    message: "Unable To Signed Up Public User,Please Try Again Later",
							  						    callback: function () {$('#otpModal').modal('hide'); location.reload(true); }
							  						 });
												}else{
												 bootbox.alert({
							  						    message: "Bad Credentials - Invalid OTP",
							  						    callback: function () {$('#otpModal').modal('hide'); location.reload(true); }
							  						 });
												}
											}
									      }).fail(function (response) {
									      		 $('#loader').addClass('hidden');;
									    	  $('#myModal').modal('hide');
									    	  bootbox.alert("Something Went Wrong... Please Try Again Later");
									      });
						  			}
						  			$('body').css('cursor', 'default');
						  			$("#benSignUp").attr("disabled",false);
						  		});
				//			}
				//			else
				//			{
				//				$('body').css('cursor', 'default');
				//				$("#benSignUp").attr("disabled",false);
				//			}
							
						//}, 1000);
				}
		}
	}
	
	function showTimer()
	{
		const second = 1000,
	      minute = second * 60,
	      hour = minute * 60;


		countDown = new Date().getTime() + 2 *60000;
	    
	    x = setInterval(function() {

	          let now = new Date().getTime(),
	          distance = window.countDown - now;

	        $('#minutes').text( Math.floor((distance % (hour)) / (minute)) );
	        $('#seconds').text( Math.floor((distance % (minute)) / second) );
	      
	      //do something later when date is reached
	      if (distance <= 1) {
		$('#otpModal').modal('hide');
	        clearInterval(x);
	      }

	    }, second);
	}
	
	function getOtp(number,status){
		$.get("./otp/request",{number: ""+number+"",status: ""+status+"",otpCheck:"ONLINE"},function(data){
		var obj = data;
		$.each(obj, function(key,value) {
			var data = value.status;
			if(data == "valid"){
				
					showTimer();
				//alert("is Otp Come :"+data.status);
				$('#publicUserLogin').modal('hide');
				$(".otpCheck").val("");
				$('#otpModal').modal('show');
				$('#otpModal').modal({
				    backdrop: 'static',
				    keyboard: false
				});
			}else{
				
				
			bootbox.alert("Your OTP Request has not been completed due to some reasons,Please check your mobile number carefully & try again later :(");
			}
		});
	});
	}