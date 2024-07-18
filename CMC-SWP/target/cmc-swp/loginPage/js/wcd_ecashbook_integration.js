class WCDIntegration
{
	constructor(options){
		this.key = options.key;
		this.amount = options.amount;
		this.currency = options.currency ||"INR";
		this.name = options.name; 
		this.description = options.description || "";
		this.image = options.image;
		this.order_id = options.order_id;
		this.theme = options.theme;
		this.encData = JSON.stringify(options);
		this.options = options;
		this.projectCode=options.projectCode;
	}
    open(){
	 let opt = this.options;
	  $.ajax({
		type: "GET",
		url: "http://localhost:2323/wcd_ecashbook-web/public/expenditure/filter",
		data: {
			"encData": this.encData,
		},
		success: function (response1) {
		    $("body div").last().html(response1);
		     document.getElementById("dt-start").addEventListener('click', (e) => {
		                $.ajax({
			 			    url : $('#expndForm').attr('action'),
			 			    method : 'post',
			 			    data: new FormData($('#expndForm')[0]),
			 			    enctype: 'multipart/form-data',
			 			    processData: false,
			 			    contentType: false,
			 			    cache: false, 
			 			    success: function (response) { 
			 			       if(response.outcome==true){
			 			    	  opt.success(response);
			 			       }else{
			 			    	  opt.error(response);
			 			       }
			 			    },
			 			    error: function (response) {
			 			      console.log('An error occurred.');
			 			      console.log(response);
			 			    },
			 			  });
	        });
		},
		error: function () {
			console.error("The postBackUrl parameter has not been set.") 
		}
	});  
    }
}

