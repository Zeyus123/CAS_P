$(function() {

	$('.noSpace').keydown(function(e) {
		var regex = new RegExp(/^\S*$/);
		var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
		if(e.key != "Backspace"){
			if (regex.test(str)) {
				return true;
			} else {
				e.preventDefault();
				return false;
			}
		}
	});
	
	$('.NumbersOnlyWithoutZero').on('input', function(event) {
	    var regex = /^\d*[1-9]+\d*$/;
	    var inputValue = event.target.value;
	
	    if (!regex.test(inputValue)) {
	        var getkey = $(event.target);
	        getkey.val(getkey.val().replace(/[^1-9]+/g, ""));
	    }
	});

	
		$('.DoublesOnly').keydown(function (e) {
		    var regex = /^[+-]?\d*\.?\d*$/;
		    var inputValue = event.target.value;
		
		    if (!regex.test(inputValue)) {
		        var getkey = $(event.target);
		        getkey.val(getkey.val().replace(/[^\d.-]+/g, ""));
		    }
		});


	
$('.MobileNoRegex').keydown(function(e) {
  // Allow arrow keys, delete, backspace, and numbers (0-9)
  if (e.key === "ArrowLeft" || e.key === "ArrowRight" || e.key === "ArrowUp" || e.key === "ArrowDown" ||
    e.key === "Delete" || e.key === "Backspace" || /\d/.test(e.key)) {
    return true;
  }

  // Allow the '+' sign
  if (e.key === "+") {
    // Check if '+' is already present in the input
    if ($(this).val().indexOf('+') === -1) {
      return true;
    } else {
      e.preventDefault();
      return false;
    }
  }

  // Prevent all other characters
  e.preventDefault();
  return false;
});

	


	$('.AlphabetsOnly').keydown(function(e) {
		var regex = new RegExp(/^[a-zA-Z\s]+$/);
		var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
		if(e.key != "Backspace"){
			if (regex.test(str)) {
				return true;
			} else {
				e.preventDefault();
				return false;
			}
		}
	});
	$('.AlphaNumericAndSpecial').on('input', function(e) {
		var regex = new RegExp(/^[ A-Za-z0-9\/\.,\-_]*$/);
		var str = e.originalEvent.data;
		if (e.key === "Backspace" || regex.test(str)) {
			return true;
		} else {
			e.preventDefault();
			var index = $(this).val().length;
			$(this).val($(this).val().substring(0, index-1));
			return false;
		}
	});
	$('.uppercase-no-space').on('input', function() {
		// Replace spaces and convert to uppercase
		$(this).val($(this).val().replace(/\s+/g, '').toUpperCase());
	});
	$('.lowercase-no-space').on('input', function() {
		// Replace spaces and convert to lowercase
		$(this).val($(this).val().replace(/\s+/g, '').toLowerCase());
	});


	$('.emailsOnly').keydown(function (e) {
	    var regex = new RegExp("/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/");
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    else {
	        e.preventDefault();
	        return false;
	    }
	});

/*	$('.NumbersOnly').on(
			'keyup',
			function(event) {
				var regex = new RegExp("/^\d+$/");
				var key = String.fromCharCode(!event.charCode ? event.which
						: event.charCode);
				if (!regex.test(key)) {
					var getkey;
					getkey = $(event.target);
					getkey.val(getkey.val().replace(/[^0-9.]+/g, ""));
					//event.preventDefault();
					return false;
				}
		});*/
		
	$('.NumbersOnly').on('keypress', function(event) {
		var key = String.fromCharCode(event.which);
		if (!/^\d$/.test(key) && !event.ctrlKey && !event.metaKey && !event.altKey) {
			event.preventDefault();
		}
	});
	
	$('.WithOutZero').on('input', function(event) {
        var inputVal = $(this).val();
        if (inputVal.startsWith('0') && inputVal !== '0') {
            $(this).val(inputVal.substring(1));
        }
    });
		
		$('.NumbersOnlyWithoutDot').keydown(function(e) {
		var regex = new RegExp(/^[0-9]+|[\b]+$/); // space given because of issue
		var str = e.key;
		if (regex.test(str) || str=='Backspace' || str=='Tab') {
			return true;
		} else {
			e.preventDefault();
			return false;
		}
	});

	$('.AlphaNumericOnly').keydown(function(e) {
		var regex = new RegExp(/^[a-zA-Z0-9._\b\s]+$/); // space given because of issue
		var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
		console.log(str);
		if (regex.test(str)) {
			return true;
		} else {
			e.preventDefault();
			return false;
		}
	});
	
	$('.FlatsOnly').on(
			'keypress',
			function(event) {
				var regex = new RegExp(/^[a-zA-Z0-9\,\-]+$/);
				var key = String.fromCharCode(!event.charCode ? event.which
						: event.charCode);
				if (!regex.test(key)) {
					event.preventDefault();
					return false;
				}
		});
});

function validateEmail(emailField) {
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

	if (reg.test(emailField.value) == false) {
		//bootbox.alert("Please Provide a Vaild Email Id");
		var result = emailField.value.replace(
				/[a-zA-Z!&#@^/#+()$~%&\\\|\.''"":;*?<>{}]/g, '');
		emailField.value = result;
		emailField.value = '';
		emailField.focus();
		return false;
	}
	return true;
}

function validateMobileNo(element) {
	/*alert("mobile vaildate function id---------->"+element);*/
	var re = /^[0-9]+$/;

	var str = element.toString();

	var str1 = element.value;

	element = (element) ? element : window.event;
	var charCode = (element.which) ? element.which : element.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}

	if (isNaN(str1) || str1.indexOf(" ") != -1) {
		//bootbox.alert("Please Provide a Vaild Mobile Number");
		var result = element.value.replace(
				/[a-zA-Z!&#@^/#+()$~%&\\\|\.''"":;*?<>{}]/g, '');
		element.value = result;
		element.value='';
		this.value='';
		//document.getElementById('txtMobile').focus();
		return false;
	}

	if (str1.length > 10 || str1.length < 10) {
		//bootbox.alert("Please Provide a Vaild Mobile Number");
		var result = element.value.replace(
				/[a-zA-Z!&#@^/#+()$~%&\\\|\.''"":;*?<>{}]/g, '');
		//element.value = result;
		element.value='';
		this.value='';
		//document.getElementById('txtMobile').value = '';
		return false;
	}

	$('.checkXss').keydown(function(event) {
	    var regex = new RegExp("[^a-zA-Z1-90_\\- \\.\\@\\#\\/\\,\\==+]*");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       return false;
	    }
	});
	
	/*$('textarea').on('keydown', function (event) {
	    var regex = new RegExp("^[a-zA-Z0-9. ]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       return false;
	    }
	});*/
}

function imageCheck(that){
	var ValidFileExtension = [ 'jpg','jpeg'];
	if($(that).val().split('.').length == 2 ) {
	if ($.inArray($("#"+that.id).val().split('.').pop().toLowerCase(), ValidFileExtension) == -1) {
		$("#"+that.id).val("");
		bootbox.alert("Sorry! allowed format is jpg or jpeg only.");
		event.preventDefault();
		return false;
	}
	if ((that.files[0].size) > 5242880) {
		$(that).val("");
		bootbox.alert("File size exceeds maximun image size of 5 MB!");
		return false;
	}
  }else{
	  	   bootbox.alert("Unsupported file format,Please check your file extension");
	  	   $(that).val("");
	}
}


function fileCheck(that){
		var ValidFileExtension = ['pdf'];

	if($(that).val().split('.').length == 2 ) {
		  if ($.inArray($(that).val().split('.').pop().toLowerCase(), ValidFileExtension) == -1) {
				bootbox.alert("Sorry! allowed format is pdf only.");
				$(that).val("");
				return false;
			}
			if ((that.files[0].size) > 2097152) {
				bootbox.alert("File size exceeds maximun file size of 2 MB!");
				$(that).val("");
				return false;
			}
		}else{
	  	   bootbox.alert("Unsupported file format,Please check your file extension");
	  	   $(that).val("");
	 }
	}
	
	function SpecificFileCheck(that){
			debugger;
			var ValidFileExtension = ['pdf','word','jpg','jpeg','png'];

		if($(that).val().split('.').length == 2 ) {
			  if ($.inArray($(that).val().split('.').pop().toLowerCase(), ValidFileExtension) == -1) {
					bootbox.alert("Sorry! allowed format is PDF , WORD , JPG , PNG only.");
					$(that).val("");
					return false;
				}
				if ((that.files[0].size) > 2097152) {
					bootbox.alert("File size exceeds maximun file size of 2 MB!");
					$(that).val("");
					return false;
				}
			}else{
		  	   bootbox.alert("Unsupported file format,Please check your file extension");
		  	   $(that).val("");
		 }
		}
	
	
function nonZeroFirstDigit(that) {
if (that.value.length == 1 && that.value == 0) {
	$("#"+that.id).val('');
}
}
	
function todayDateWithoutTime(dateTime) {
    var date = new Date(dateTime.getTime());
    date.setHours(0, 0, 0, 0);
    return date;
}

$('.AlphaNumericANDscAnd').keydown(function(e) {
debugger;
		var regex = new RegExp(/^[a-zA-Z0-9_\-@/.\s]+|[\b]+$/);
		var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
		if (regex.test(str)) {
			return true;
		} else {
			e.preventDefault();
			return false;
		}
	});

		$('.AllotmentSpcl').on(
			'keydown',
			function(event) {
				var refield11gex = new RegExp("^[1-9]\d*$");
				var key = String.fromCharCode(!event.charCode ? event.which
						: event.charCode);
				if (!regex.test(key)) {
					event.preventDefault();
					return false;
				}
			});
			
	$('.AlphaNumericWithLimitedSpecialChars').keydown(function(e) {
		var regex = new RegExp(/^[a-zA-Z0-9_\-@/./,/;/:/+/=\s]+$/);
		var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
		
		const key = event.key; // const {key} = event; ES6+
	    if (key === "Backspace") {
	       return true;
	    }
		
		if (regex.test(key)) {
			return true;
		} else {
			e.preventDefault();
			return false;
		}
	});
	
	
	function removeLastIndexCommasWithWhiteSpaces(that){
		var finalCheck ="";
		finalCheck =that.value.trim().replace(/,*(?=$)/,''); //.split('-').join('');
	    finalCheck = finalCheck.trim().replace(/-*(?=$)/,''); 		//that.value=that.value.replace(/,(\s+)?$/, '') //.split('-').join('');
		if(finalCheck.match(/,(\s+)?$/)){
		that.value=finalCheck.trim().replace(/,*(?=$)/,'');
		}else{
		that.value=finalCheck.trim().replace(/-*(?=$)/,'');
		}
		return false;  
	}
	
	function addInTotal(that, field10, field11, field12, field13){
	  
	  if(that != ""){
	     console.log(field10,field11,field12,field13);
	  }
	
	function currencyConverter(that,fieldId){
		if(that!=""){
			var value = that.replaceAll(",","");
			if(value.includes(".")){
				var splitValues = value.split(".");
				if(splitValues.length>=2){
					if(splitValues[0]==""){
						splitValues[0]="0";
					}
					value=splitValues[0]+"."+splitValues[1];
					if(splitValues[1].length==1){
						value=splitValues[0]+"."+splitValues[1]+"0";		
					}
					if(splitValues[1].length>2 || splitValues[1]==""){
						value=splitValues[0]+".00";		
					}
				}else{
					value=splitValues[0];
				}
			}else{
				value=value+".00";
			}
			//var hiddenFieldId = fieldId.split("Conv")[0];
			$("#"+fieldId+"Converted").val(value);
			value = value.replace(/(\d)(?=(\d{2})+\d\.)/g, '$1,');
			$("#"+fieldId).val(value);
		}
	}
	
		function currencyConverterForProject(fieldId,index){
		if($("#"+fieldId+""+index).val()!=""){
			var value = $("#"+fieldId+""+index).val().replaceAll(",","");
			if(value.includes(".")){
				var splitValues = value.split(".");
				if(splitValues.length>=2){
					if(splitValues[0]==""){
						splitValues[0]="0";
					}
					value=splitValues[0]+"."+splitValues[1];
					if(splitValues[1].length==1){
						value=splitValues[0]+"."+splitValues[1]+"0";		
					}
					if(splitValues[1].length>2 || splitValues[1]==""){
						value=splitValues[0]+".00";		
					}
				}else{
					value=splitValues[0];
				}
			}else{
				value=value+".00";
			}
			//var hiddenFieldId = fieldId.split("Conv")[0];
			$("#"+fieldId+"Converted"+index).val(value);
			value = value.replace(/(\d)(?=(\d{2})+\d\.)/g, '$1,');
			$("#"+fieldId+""+index).val(value);
		}
	}
	
	function CodeValidation(Type,fieldId,value){	
		$.ajax({
			type : "GET",
			url : window.contextPath+'/mst/validateCode',
			data : {
				"Code" : value,
				"type" :Type
			}, 
			success : function(response) {
			var data=JSON.parse(response);
			if(data[0].isDuplicate ==true){			
						bootbox.alert("Duplicate Code Not Allowed");
						$("#"+fieldId).val("");
					}			
			}, 
			error : function(error) {
			bootbox.alert("Time out");
			
			}
		});
	} 
	
	function changeCase(txt,fieldId) {
    let str1 = "";
    for (let i = 0; i < txt.length; i++) {
        if (/[A-Z]/.test(txt[i])) str1 += txt[i].toUpperCase();
        else str1 += txt[i].toUpperCase();
    }
   
    $("#"+fieldId).val(str1);
}


function checkSpaces(that){
debugger;
var id= that.id;
var value = that.value;
var replacedValue = value.replaceAll(" ","");
if(replacedValue.length==0){
	bootbox.alert("Field value cannot contain only spaces.");
	$("#"+id).val("");
	return false;
	}else{
	return true;
	}
}

	$('.DoublesOnly').on(
			'keyup',
			function(event) {
			debugger;
				var regex = new RegExp("^\d*\.?\d+$");
				var key = String.fromCharCode(!event.charCode ? event.which
						: event.charCode);
				if (!regex.test(key)) {
					var getkey;
					getkey = $(event.target);
					getkey.val(getkey.val().replace(/[^0-9.]+/g, ""));
					//event.preventDefault();
					return false;
				}
		});


		function DoublePercent(id){
			var benPercent = $('#'+id).val();
			//alert(mystring);
			if($.isNumeric(benPercent)){
				
				mystring=benPercent.replace(/\./g,'');
				// mystring = benPercent.substring(0, 4);
	
				// mystring1 = benPercent.substring(0, 2);
				if(!(mystring.indexOf(".") == 1)){
					//mystring2 = benPercent.replace(/\./g,'').substring(2, 4);
	
					//mystring2=""+mystring1+"."+mystring2+"";
					//alert(mystring2);
					if(benPercent!="" ){
						document.getElementById(id).value=mystring;
					}
				}else{
					$('#'+id).val('');
				}
				
			}else{
				$('#'+id).val('');
			}
		}
		
$('.NumberOnly').keydown(function(e) {
	var regex = new RegExp(/^[^\.\-\+]+$/);
	var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	
	const key = event.key; // const {key} = event; ES6+
	if (key === "Backspace") {
	   return true;
	}
	
	if (regex.test(key)) {
		return true;
	} else {
		e.preventDefault();
		return false;
	}
});


$('.NoSpace').keydown(function(e) {
 	debugger;
	var regex = new RegExp(/\S/);
	var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	
	const key = event.key; // const {key} = event; ES6+
	if (key === "Backspace") {
	   return true;
	}
	
	if (regex.test(key)) {
		return true;
	} else {
		e.preventDefault();
		return false;
	}
});

function currencyConverter(that, fieldId) {
	if (that != "") {
		var value = that.replaceAll(",", "");
		if (value.includes(".")) {
			var splitValues = value.split(".");
			if (splitValues.length >= 2) {
				if (splitValues[0] == "") {
					splitValues[0] = "0";
				}
				value = splitValues[0] + "." + splitValues[1];
				if (splitValues[1].length == 1) {
					value = splitValues[0] + "." + splitValues[1] + "0";
				}
				if (splitValues[1].length > 2 || splitValues[1] == "") {
					value = splitValues[0] + ".00";
				}
			} else {
				value = splitValues[0];
			}
		} else {
			value = value + ".00";
		}
		//var hiddenFieldId = fieldId.split("Conv")[0];
		$("#" + fieldId + "Converted").val(value);
		value = value.replace(/(\d)(?=(\d{2})+\d\.)/g, '$1,');
		$("#" + fieldId).val(value);
	}
}
}

function currencyConverter(that, fieldId,totalCount) {

//  if (that != "") {
//    var value = that.replaceAll(",", "");
//    if (value.includes(".")) {
//      var splitValues = value.split(".");
//      if (splitValues.length >= 2) {
//        if (splitValues[0] === "") {
//          splitValues[0] = "0";
//        }
//        value = splitValues[0] + "." + splitValues[1];
//        if (splitValues[1].length === 1) {
//          value = splitValues[0] + "." + splitValues[1] + "0";
//        }
//        if (splitValues[1].length > 2 || splitValues[1] === "") {
//          value = splitValues[0] + ".00";
//        }
//      } else {
//        value = splitValues[0];
//      }
//    } else {
//      value = value + ".00";
//    }
//    $("#" + fieldId + "Converted").val(value);
//    value = value.replace(/(\d)(?=(\d{2})+\d\.)/g, "$1,");
//    
//    $("#" + fieldId).val(value);
//    
//     var format = new Intl.NumberFormat('en-US', {
//		currency: 'INR',
//		minimumFractionDigits: 2,
//	});
//	
//    var totalSum = 0;
//
//$(".ppp" + totalCount).each(function() {
//  var value = parseInt($(this).val().replaceAll(",", "").split(".")[0]);
//  if (!isNaN(value)) {
//    totalSum += value;
//  }
//});
//
// $(".total_"+totalCount).empty().val(format.format(totalSum));
//
//  }else{
//  
//    var format = new Intl.NumberFormat('en-US', {
//		currency: 'INR',
//		minimumFractionDigits: 2,
//	});
//	
//	
//  $("#" + fieldId).val(format.format(0));
  
     
	
    var totalSum = 0;

$(".ppp" + totalCount).each(function() {
  //var value = parseInt($(this).val().replaceAll(",", "").split(".")[0]);
  var value = parseFloat($(this).val());
  if (!isNaN(value)) {
    totalSum += value;
  }
});
$(".total_"+totalCount).empty().val(totalSum);
// $(".total_"+totalCount).empty().val(format.format(totalSum));
  
//  }
}



function convertAmountToLakh(amount){
	var amountConv = parseFloat(amount);
    var lakh = amountConv / 100000.0;
	return lakh;
}

function convertLakhToAmount(lakh){
	var lakhToAmt = parseFloat(lakh)*100000.0;
	return lakhToAmt;
}

function ValidateEmail(input,fieldId) {

  var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

  if (input.match(validRegex)) {
    return true;

  } 
  else {
    bootbox.alert("Invalid email address!");
      $('#'+fieldId).val('');
    return false;

  }

}

function validateDocument(id,count){
    var mypdf=$('#'+id+count).val();
    var ext = mypdf.split('.').pop();
    if(ext != "pdf" && ext != "png" && ext != "jpeg" && ext != "docx"){
        bootbox.alert("Only PDF,PNG,JPEG,Word files allowed");
        $('#'+id+count).val("");
        return false;
    }
}

function validateProfilePhoto(id,count){
    var myPhoto=$('#'+id+count).val();
    var ext = myPhoto.split('.').pop();
    if(ext != "png" && ext != "jpeg" && ext != "jpg"){
        bootbox.alert("Only png,jpeg,jpg files allowed");
        $('#'+id+count).val("");
        return false;
    }
}

 $('.float-number').keypress(function(event) {
	 debugger
    if ((event.which != 46 || $(this).val().indexOf('.') != -1) && (event.which < 48 || event.which > 57)) {
        event.preventDefault();
    }
});



//common ajax call for reports -->return list of records based on the filter criteria




function reportWiseDetails(url, type, data) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: url,
            type: type,
            async: true,
            data: data,
            success: function (response) {
                resolve(response);
            },
            error: function (response) {
                reject(response);
            }
        });
    });
}
