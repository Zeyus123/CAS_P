const context = window.contextPath;



/**
 * @author Tapan
 * @since  16/01/2024
 * @descr  Mask inner portion of a string
 */
function maskString(inString,startPosition,maskTillPosition)
{
	if (!inString || startPosition < 0 || startPosition >= inString.length || maskTillPosition < 0 || maskTillPosition > inString.length || startPosition >= maskTillPosition) {
		return inString;
	}
	const maskLength = maskTillPosition - startPosition;
	const maskedStr = inString.substring(0, startPosition) + "*".repeat(maskLength) + inString.substring(maskTillPosition);

	return maskedStr;
}

/* 
 * @author : Tapan
 * @since  : 15 Apr 2017
 * @descrn : validate all decimal text-boxes
*/
function validateDecimalField(fieldId,decimalLength,maxLength,msgLabel)
{
	// Decimal Length : Scale of the datatype of the field.
	// Max Length : Max Length including decimal.
	if($("#"+fieldId).val() != "" && parseFloat($("#"+fieldId).val()) > 0)
	{
		var fieldValue = $("#"+fieldId).val();
		if(isNaN(fieldValue))
		{
			bootbox.alert("Please enter a numeric value for " +msgLabel+ ".");
			$("#"+fieldId).val("");
			setTimeout(function(){$("#"+fieldId).focus();}, 100);
			//$("#"+fieldId).focus();
			return false;
		}

		var result = "";

		/*if(fieldValue.indexOf(".") == -1)
		{
			alert("inside if block...");
			result = (parseFloat(fieldValue)).toFixed(decimalLength);
		}
		*/
		result = (parseFloat(fieldValue)).toFixed(decimalLength);

		$("#"+fieldId).val(result);
	
		if($("#"+fieldId).val().length > maxLength)
		{
			bootbox.alert("The value entered for " +msgLabel+ " exceeds the allowed max length of " +maxLength+ " digits including decimal.");
			$("#"+fieldId).val("");
			//$("#"+fieldId).focus();
			return false;
		}
		return true;
	}
	else if(isNaN($("#"+fieldId).val()))
	{
		bootbox.alert("Invalid entry found for " +msgLabel+ ".");
		$("#"+fieldId).val("");
		//$("#"+fieldId).focus();
		return false;
	}
	else
		return true;
}

/* 
 * @author : Tapan
 * @since  : 15 Apr 2017
 * @descrn : Validate the PAN No. format
*/
function validatePAN(vPANnoObj)
{
	if (vPANnoObj == null) 
		vPANnoObj = window.event.srcElement;

	var panVal = vPANnoObj.value;
	var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;

	if(regpan.test(panVal))
	{
	}
	else 
	{
		bootbox.alert("PAN Number is not valid. It should be in the format : 'ABCDE1234F' .");
		vPANnoObj.value = '';
		return false;
	}
}

/* 
 * @author : Akash
 * @since  : 05/02/2024
 * @descrn : returns current date in 'dd/MM/yyyy' format
*/

function getCurrentDateInStr() {
	// format current date to 'dd/MM/yyyy' format
	const currentDate = new Date();
	const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
	const cDateStr = currentDate.toLocaleDateString('en-US', options); // date coming in format "MM/dd/yyyy"
	const month = cDateStr.substring(0,2);
	const day = cDateStr.substring(3,5);
	const year = cDateStr.substring(6,10);
	const currentDateStr = day+'/'+month+'/'+year; // converted to format "dd/MM/yyyy"
    return currentDateStr;
}

/* 
 * @author : Akash
 * @since  : 05/02/2024
 * @descrn : Get month id from given date in string format
*/

function getMonthIdFromDate(dateStr) {
    const monthIndex = dateStr.substring(3,5);
    return monthIndex;
}

/* 
 * @author : Akash
 * @since  : 05/02/2024
 * @descrn : Get year from given date in string format
*/
function getYearFromDateStr(dateStr) {
    const year = dateStr.substring(6,10);
    return year;
}

/* 
 * @author : Akash
 * @since  : 05/02/2024
 * @descrn : Get month name from month id
*/

function getMonthName(monthId) {
    const monthNames = [
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];

    // Ensure monthId is within the valid range (1 to 12)
    if (monthId >= 1 && monthId <= 12) {
        return monthNames[monthId - 1];
    } else {
        return "Invalid month ID";
    }
}

/* 
 * @author : Akash
 * @since  : 30/01/2024
 * @descrn : Validate the file size and shouldn't exceed 2 MB
*/
function fileCheckDocument(that) {
    // Allowed file extensions
  	var ValidFileExtension = ['png','jpg','pdf'];

	if($(that).val().split('.').length == 2 ) {
		if ($.inArray($(that).val().split('.').pop().toLowerCase(), ValidFileExtension) == -1) {
			bootbox.alert("Sorry! allowed format is png,Jpg,pdf only.");
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


//############################### Convert amount to words ##################################

/**
 * @author Tapan
 * @version 1.0
 * @since 30-05-2017
 * @param 
 * @description Convert the amount to Words (Rupees & Paise)
 */

function convertAmountToWords(amountToConvert) 
{
	var amountInWordsOut = "";
	if(parseFloat(amountToConvert)==0)
	{
		amountInWordsOut = "";
	}
	else if(parseFloat(amountToConvert)>0)
	{
		var fraction = Math.round(getFraction(amountToConvert)*100);
		var paiseInWords  = "";
	
		var amountInWordsWithoutPaise = convertToNumber(amountToConvert);
		
		if(fraction > 0)
		{
			if(amountInWordsWithoutPaise.lastIndexOf(" and ") != -1)
			{
				amountInWordsWithoutPaise = amountInWordsWithoutPaise.replace(" and "," ");
			}
			paiseInWords = "and "+convertToNumber(fraction)+" Paise ";
		}
		amountInWordsOut = "Rupees " + amountInWordsWithoutPaise +" "+ paiseInWords +"only";
	}
	else
	{
		amountInWordsOut = "Incorrect amount !!!";
	}
	
	return amountInWordsOut;
}

/**
 * @author Tapan
 * @description Part of the Convert to Words function
 */
function getFraction(fractionAmount) 
{
	return fractionAmount % 1;
}

/**
 * @author Tapan
 * @description Part of the Convert to Words function
 */
function convertToNumber(amountInNum)
{
	/*if ((amountInNum < 0) || (amountInNum > 999999999)) 
	{
		return "Incorrect amount found !!!";
	}*/
	var amtInCrore = Math.floor(amountInNum / 10000000);	/* Crore */ 
	amountInNum -= amtInCrore * 10000000;

	var amtInLakhs = Math.floor(amountInNum / 100000);		/* lakhs */ 
	amountInNum -= amtInLakhs * 100000;
	
	var amtInThousands = Math.floor(amountInNum / 1000);	/* thousand */ 
	amountInNum -= amtInThousands * 1000;
	
	var amtInHundreds = Math.floor(amountInNum / 100);		/* Tens (deca) */ 
	amountInNum = amountInNum % 100;						/* Ones */ 
	
	var amtInTens = Math.floor(amountInNum / 10);
	var amtInOnes = Math.floor(amountInNum % 10);
	
	var result = "";

	if (amtInCrore>0)
	{
		result += (convertToNumber(amtInCrore) + " Crore");
	}
	if (amtInLakhs>0)
	{
		result += (((result=="") ? "" : " ") + 
		convertToNumber(amtInLakhs) + " Lakh");
	}
	if (amtInThousands>0) 
	{
		result += (((result=="") ? "" : " ") + convertToNumber(amtInThousands) + " Thousand");
	}

	if (amtInHundreds) 
	{
		result += (((result=="") ? "" : " ") + convertToNumber(amtInHundreds) + " Hundred");
	}

	var arrValuesInOnes = Array("", "One", "Two", "Three", "Four", "Five", "Six","Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen","Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen","Nineteen");
	var arrValuesInTens = Array("", "", "Twenty", "Thirty", "Fourty", "Fifty", "Sixty","Seventy", "Eighty", "Ninety");

	if (amtInTens>0 || amtInOnes>0) 
	{
		if (result!="")
		{
			result += " and ";
		}
		if (amtInTens < 2) 
		{
			result += arrValuesInOnes[amtInTens * 10 + amtInOnes];
		}
		else 
		{
			result += arrValuesInTens[amtInTens];
			if (amtInOnes>0) 
			{
				result += (" " + arrValuesInOnes[amtInOnes]);
			}
		}
	}

	if (result=="")
	{
		result = "zero";
	}
	return result;
}

//* @description : Convert value to INR Format (i.e. 1,23,45,789.00)
//* @author : Tapan
//* @since : 06 Dec 2017
function convertValueToINRFormat(strInputAmount) 
{
	strInputAmount=strInputAmount.toString();
	var decimalVal = '';

	if(strInputAmount.indexOf('.') > 0)
	   decimalVal = strInputAmount.substring(strInputAmount.indexOf('.'),strInputAmount.length);
	strInputAmount = Math.floor(strInputAmount);
	strInputAmount=strInputAmount.toString();

	var lastThree = strInputAmount.substring(strInputAmount.length-3);
	var otherNumbers = strInputAmount.substring(0,strInputAmount.length-3);

	if(otherNumbers != '')
	    lastThree = ',' + lastThree;

	if(decimalVal.substr(1).length == 0)
		decimalVal = ".00";
	else if(decimalVal.substr(1).length == 1)
		decimalVal = decimalVal + "0";
	else
		decimalVal = decimalVal;

	var formattedValue = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree + decimalVal;

	return formattedValue;
}


//* @description : Convert value to INR Format (i.e. 1,23,45,789), but no Decimal
//* @author : Tapan
//* @since : 06 Dec 2017
function convertValueToINRFormatWithoutDecimal(strInputAmount) 
{
	strInputAmount=strInputAmount.toString();
	var decimalVal = '';

	if(strInputAmount.indexOf('.') > 0)
	   decimalVal = strInputAmount.substring(strInputAmount.indexOf('.'),strInputAmount.length);
	strInputAmount = Math.floor(strInputAmount);
	strInputAmount=strInputAmount.toString();

	var lastThree = strInputAmount.substring(strInputAmount.length-3);
	var otherNumbers = strInputAmount.substring(0,strInputAmount.length-3);

	if(otherNumbers != '')
	    lastThree = ',' + lastThree;

	var formattedValue = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree;

	return formattedValue;
}


//#################################################### VALIDATE ARREAR BILL NO ##########################################################
function validateBillNoEitherExistsRNot(id,salType,fromDate,toDate,fromDivId,toDivId){
	var data = $("#"+id).val();
	if(data != "0"){
		showLoader();
		$.ajax({
			url : context+'/core/get-from-and-todate-by-billno',
			data : {
				"billNo" : data,
				"salType" : salType,
			},
			success : function(response) 
			{
				hideLoader();
				var outcome = response.outcome;
				if(outcome){
					var data=response.data;
					if(data.isBillNoExists){
						$('#'+fromDate).val(data.fromDate);
						$('#'+toDate).val(data.toDate);
						$("#"+fromDivId).addClass("frezee");
						$("#"+toDivId).addClass("frezee");
					}else{
						$('#'+fromDate).val("");
						$('#'+toDate).val("");
						$("#"+fromDivId).removeClass("frezee");
						$("#"+toDivId).removeClass("frezee");
					}
				
				}else{
					$('#'+fromDate).val("");
					$('#'+toDate).val("");
					$("#"+fromDivId).removeClass("frezee");
					$("#"+toDivId).removeClass("frezee");
				}
			}
		});
	}else{
		$('#'+fromDate).val("");
		$('#'+toDate).val("");
		$("#"+fromDivId).removeClass("frezee");
		$("#"+toDivId).removeClass("frezee");
	}
}


//###################################################### ARREAR BILL NO BY FINANCIAL YEAR #############################################################
function getBillNoByFinYear(id,apndId,salType,emplType,billNo){
	var data = $("#"+id).val();
	var emplTypeCode = $("#"+emplType).val();
	var html = "<option value='0'>-Select-</option>";
	$('#arrStaffListTbodyId').empty().append('');
	$('#arrStaffListDivId').addClass("hidden");
	if(data != "0" && emplTypeCode != "0"){
		showLoader();
		$.ajax({
			url : context+'/core/get-bill-no-by-financial-year',
			data : {
				"finYear" : data,
				"salType" : salType,
				"employTypeCode" : emplTypeCode,
			},
			success : function(response) 
			{
				hideLoader();
				var data=response.data;
				$.each(data, function(index, value) {
					var isSelected = billNo === value ? 'selected' : '';
					html=html+"<option value="+value+" "+isSelected+">"+value+"</option>";
				});
				$('#'+apndId).empty().append(html);
			}
		});
	}else{
		$('#'+apndId).empty().append(html);
	}
}


//################################################## STAFF DETAILS AJAX CALL ###########################################################

function getStaffListByEmploymentId(id,apndId,staffDtlsDiv){
	var data = $("#"+id).val();
	var html = "<option value='0'>-Select-</option>";
	$("#"+staffDtlsDiv).addClass("hidden");
	if(data != "0"){
		showLoader();
		$.ajax({
			url : context+'/core/get-staff-list-by-employment-id',
			data : {
				"employmentId": data,
			},
			success : function(response) 
			{
				hideLoader();
				var data=response.data;
				$.each(data, function(index, value) {
					html=html+"<option value="+value.staffId+">"+value.staffName+" ("+value.staffCode+")"+"</option>";
				});
				$('#'+apndId).empty().append(html);
				$("#"+apndId).selectpicker('destroy');
				$("#"+apndId).selectpicker('rebuild');
			}
		});
	}else{
		$('#'+apndId).empty().append(html);
		$("#"+apndId).selectpicker('destroy');
		$("#"+apndId).selectpicker('rebuild');
	}
}


function getStaffDetails(id,divId)
{
	var staffId = $("#"+id).val();
	var trHTML = "";
	if(staffId != "0")
	{
		showLoader();
		$.ajax({	
			url : context+'/core/get-staff-details',
			type : 'GET',
			data: {
				"staffId" : staffId,
			},
			success : function(ajaxResp) 
			{
				hideLoader();
				var data = ajaxResp;
				if(data != null && data != "")
				{
					$("#staffName").text(data.staffName);
					$("#staffCode").text(data.staffCode);
					$("#dateOfBirthAsDdMmYyyy").text(data.dateOfBirthAsDdMmYyyy);
					$("#gender").text(data.genderName);
					$("#mobileNo").text(data.mobileNo);
					$("#aadharNo").text(maskString(data.aadharNo,2,10));
					$("#panNo").text(maskString(data.panNo,2,8));
					$("#bloodGroup").text(data.bloodGroupName);
					$("#email").text(data.emailId);
					$("#category").text(data.casteName);
					$("#maritalStatus").text(data.maritalStatusName);
					$("#college").text(data.collegeName ? data.collegeName +" ("+data.collegeCode+")" : "N/A");
					$("#department").text(data.departmentName ? data.departmentName +" ("+data.departmentCode+")" : "N/A");
					$("#designation").text(data.designationName ? data.designationName : "N/A");
					$("#doj").text(data.dateOfJoiningAsDdMmYyyy);
					$("#accountNo").text(data.accountNo);
					$("#bankName").text(data.bankName);
					$("#branchName").text(data.branchName +" ("+data.ifscCode+")");

					$("#serviceStatus").text(data.serviceStatusName);
					$("#eosDate").text(data.eosDateAsDdMmYyyy ? data.eosDateAsDdMmYyyy : "N/A");

					$("#payGrade").text(data.paygradeName ? data.paygradeName : "N/A");
					$("#payBand").text(data.paybandName ? data.paybandName : "N/A");
					$("#payCommission").text(data.payCommsnName ? data.payCommsnName : "N/A");
					
					$("#religion").text(data.religionName);
					$("#staffType").text(data.staffType);
					$("#emplmntType").text(data.emplmntTypeName);
					
					$("#"+divId).removeClass("hidden");
				}
				else{
					bootbox.alert("Something went wrong");
					return false;
				}
			},
		});
	}else{
		$("#"+divId).addClass("hidden");
	}
}

// ################################################### DEMOGRAPHY ############################################################### //

function getState(selectedVal){
	$('#loader').removeClass('hidden');
	$.ajax({
		type : "GET",
		url : context+'/core/getStateList',
		dataType : "json",
		success : function(response) {
			$('#loader').addClass('hidden');
			var html = "<option value='0'>-Select-</option>";
			var selectData = '';
			var data = response;
			if (data != "" || data != null) {
				$.each(data, function(index, value) {
						selectData = (value.stateId == selectedVal) ? "selected" : "";
						html += "<option value='"+value.stateId+"' "+selectData+">"+value.stateName+"</option>";
					});
			}
			$('#stateId').empty().append(html);
		},
		error : function(error) {
			$('#loader').addClass('hidden');
			bootbox.alert("No State Found");
		}
	});
}

function getDistrict(stateId,selectedVal){
	// var stateId = $("#stateId").val();
	// var stateId = selectedVal;
	$('#loader').removeClass('hidden');
	$.ajax({
		type : "GET",
		url : context+'/core/findDistrictListByStateId',
		dataType : "json",
		data : {
			"stateId" : stateId,
		},
		success : function(response) {
			$('#loader').addClass('hidden');
			var html = "<option value='0'>-Select-</option>";
			var selectData = '';
			var data = response;
			if (data != "" || data != null) {
				$.each(data, function(index, value) {
						selectData = (value.districtId == selectedVal) ? "selected" : "";
						html += "<option value='"+value.districtId+"' "+selectData+">"+value.districtName+"</option>";
					});
			}
			$('#distId').empty().append(html);
			$('#blockId').empty().append("<option value='0'>-Select-</option>");
			$('#gpId').empty().append("<option value='0'>-Select-</option>");
			$('#villageId').empty().append("<option value='0'>-Select-</option>");
		},
		error : function(error) {
			$('#loader').addClass('hidden');
			bootbox.alert("No District Found");
		}
	});
}
	
function getBlock(id,idToApnd)
{
	var distId = $("#"+id).val();
	var html = "<option value='0'>- Select -</option>";
	if(distId != "0"){
		$('#loader').removeClass('hidden');
		$.ajax({
			type : "GET",
			url : context+'/core/findBlockListByDistrictId',
			dataType : "json",
			data : {
				"districtId" : distId,
			},
			success : function(response) {
				$('#loader').addClass('hidden');
				
				var data = response;
				if (data != "" || data != null) {
					$.each(data, function(index, value) {
						html = html + "<option value="+value.blockId+">"
								+ value.blockName + "</option>";
					});
				}
				$('#'+idToApnd).empty().append(html);
			},
			error : function(error) {
				$('#loader').addClass('hidden');
				bootbox.alert("No Block Found");
			}
		});
	}else{
		$('#'+idToApnd).empty().append("<option value='0'>-Select-</option>");
	}
}

function getGP(blockId){
	$('#loader').removeClass('hidden');
	$.ajax({
		type : "GET",
		url : context+'/core/findGpListByBlockId',
		dataType : "json",
		data : {
			"blockId" : blockId,
		},
		success : function(response) {
			$('#loader').addClass('hidden');
			var html = "<option value='0'>-Select-</option>";
			var data = response;
			if (data != "" || data != null) {
				$.each(data, function(index, value) {
					html = html + "<option value="+value.gpId+">"
							+ value.gpName + "</option>";
				});
			}
			$('#gpId').empty().append(html);
			$('#villageId').empty().append("<option value='0'>-Select-</option>");
		},
		error : function(error) {
			$('#loader').addClass('hidden');
			bootbox.alert("No Grampanchayat Found");
		}
	});
}

function getVillage(gpId){
	$('#loader').removeClass('hidden');
	$.ajax({
		type : "GET",
		url : context+'/core/findVillageListByGpId',
		dataType : "json",
		data : {
			"gpId" : gpId,
		},
		success : function(response) {
			$('#loader').addClass('hidden');
			var html = "<option value='0'>-Select-</option>";
			var data = response;
			if (data != "" || data != null) {
				$.each(data, function(index, value) {
					html = html + "<option value="+value.villageId+">"
							+ value.villageName + "</option>";
				});
			}
			$('#villageId').empty().append(html);
		},
		error : function(error) {
			$('#loader').addClass('hidden');
			bootbox.alert("No Village Found");
		}
	});
}

function getDemographicDataById(demographyCall,id,idToAppend){
	var data = $("#"+id).val();
	 var html = '<option value="0">-Select-</option>';
	if(data != "0"){
		showLoader();
		  $.ajax({
			    type: 'GET',
			    url: context+'/ajax/findDemographyById',
			    data: {
			      "demoId": data,
			      "dmographyCall": demographyCall,
			    },
			    success: function(response) {
					hideLoader();
			      var data = response;
			      $.each(data, function(index, value) {
			        html += '<option value="' + value.demographyId + '">' + value.demographyName + '</option>';
			      });
			      $("#" + idToAppend).empty().html(html);
			    },
			    error: function() {
					hideLoader();
			      alert("Server Error");
			    }
			});
		
	}else{
		$("#"+idToAppend).empty().html(html);
		if(demographyCall == "STATE"){
			$("#distId").empty().html(html);
			$("#blockId").empty().html(html);
			$("#gpId").empty().html(html);
			$("#villageId").empty().html(html);
		}
		else if(demographyCall == "DISTRICT"){
			$("#blockId").empty().html(html);
			$("#gpId").empty().html(html);
			$("#villageId").empty().html(html);
		}
		else if(demographyCall == "BLOCK"){
			$("#gpId").empty().html(html);
			$("#villageId").empty().html(html);
		}
		else if(demographyCall == "GP"){
			$("#villageId").empty().html(html);
		}
	}
}

function getAgencydataByAgencyCall(agencyCall,id,idToAppend,dependentId){
	var data = $("#"+id).val();
	 var html = '<option value="0">-All-</option>';
	if(data != "0"){
		showLoader();
		  $.ajax({
			    type: 'GET',
			    url: context+'/ajax/getAgencyDataByParentAgencyId',
				async: false,
			    data: {
			      "agencyId": data,
			      "agencycall": agencyCall,
			    },
			    success: function(response) {
					hideLoader();
			      var data = response.data;
			      $.each(data, function(index, value) {
			        html += '<option value="' + value.agencyId + '">' + value.agencyName + '</option>';
			      });
			      $("#" + idToAppend).empty().html(html);
			    },
			    error: function() {
					hideLoader();
			      alert("Server Error");
			    }
			});
		
	}else{
		$("#"+id).val("0");
		$("#"+idToAppend).empty().html(html);
		$("#"+dependentId).empty().html(html);
	}
}

function serializeKeyForDuplicateCheck(validData,validCall,formId){
	const dataMap = new Map();
	dataMap.set('validData', validData);
	dataMap.set('validCall', validCall);
	dataMap.set('formId', formId);
	
	// Convert Map to Object and Serialize to JSON
	const serializedDataMap = JSON.stringify(Object.fromEntries(dataMap));
	return serializedDataMap;
}

function getDuplicateCheckData(id,validCall,formId){
	var data = $("#"+id).val().trim();
	var formData = $("#"+formId).val();
	if(data != ""){
		var bizContent = serializeKeyForDuplicateCheck(data,validCall,formData);
		var cipherText = enc_password(bizContent);
		showLoader();
	 	$.ajax({
			url: context+'/ajax/validateDuplicateData',
			data: {
				"cipherText" : cipherText,
			},
			success: function(response) {
				hideLoader();
				var responseData = response;
				if (responseData.outcome) {
					if(responseData.data == "YES"){
						bootbox.alert(responseData.message);
						$("#"+id).val("");
						return false;
					}
					else{
						return true;
					}
				} else {
					bootbox.alert("Something went wrong");
					$("#"+id).val("");
					return false;
				}
			},
		});
	}
}


// ############################################################# MISC DATE FUNCTIONS ############################################################### //

/**
 * @author Tapan
 * @since  02/01/2024
 * @descr  Get the last day from a Year & Month
*/
function getLastDayOfYearAndMonth(intYear, intMonth)
{
	return new Date(intYear, intMonth, 0).getDate();
}

/**
 * @author Tapan
 * @since  02/01/2024
 * @descr  Convert the timestamp (Long value) format to dd/mm/yyyy hh:mi AM/PM format
*/ 
function convertTimestampToUserDateAndTimeFormat(timestampFormatVal)
{
	let dateTimeStr = "";
	const dateInDtFormat = new Date(timestampFormatVal);
	
	let hourInt = dateInDtFormat.getHours();
	let ampm = hourInt >= 12 ? 'PM' : 'AM';
	
	if(parseInt(hourInt) > 12 && parseInt(hourInt) <= 23)
		hourInt = parseInt(hourInt) - 12;
	if(parseInt(hourInt) == 0)
		hourInt = 12;
		
	let yearStr  = dateInDtFormat.getFullYear();
	let monthStr = (dateInDtFormat.getMonth() + 1).toString().padStart(2, "0");
	let dayStr   = dateInDtFormat.getDate().toString().padStart(2, "0");
	let hourStr = hourInt.toString().padStart(2, "0");
	let minStr = dateInDtFormat.getMinutes().toString().padStart(2, "0");
	
	if(isNaN(dayStr) && isNaN(monthStr) && isNaN(yearStr) && isNaN(hourStr) && isNaN(minStr))
		dateTimeStr = "";
	else
		dateTimeStr = dayStr +"/"+ monthStr +"/"+ yearStr +", &nbsp;"+ hourStr +":"+ minStr +" "+ ampm;
						 
	return dateTimeStr;
}

/**
 * @author Tapan
 * @since  02/01/2024
 * @descr  Convert the timestamp (Long value) format to dd/mm/yyyy format
*/ 
function convertTimestampToUserDateFormat(timestampFormatVal)
{
	const dateInDtFormat = new Date(timestampFormatVal);
	
	let yearStr  = dateInDtFormat.getFullYear();
	let monthStr = (dateInDtFormat.getMonth() + 1).toString().padStart(2, "0");
	let dayStr   = dateInDtFormat.getDate().toString().padStart(2, "0");
	
	let dateStr = (isNaN(dayStr)?'00':dayStr) +"/"+ (isNaN(monthStr)?'00':monthStr) +"/"+ (isNaN(yearStr)?'0000':yearStr);
	
	return dateStr;
}


/**
 * @author : Tapan  
 * @description : get current-date as dd/mm/yyyy
*/ 
function getCurrentDateAsDDMMYYYY()
{
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;
	var yyyy = today.getFullYear();

	if(dd<10)
	{
		dd='0'+dd;
	} 
	if(mm<10)
	{
		mm='0'+mm;
	}
	var currDate = dd+'/'+mm+'/'+yyyy;
	return currDate;
}


/** 
 * @author Akash
 * @since  30/10/2023
 * @description Checks if the 1st date is bigger/smaller than the 2nd date.
 */
function isFirstDateBigger(biggerDate,smallerDate)
{
	debugger;
	var firstDateForBiggerValue = moment(biggerDate, 'DD/MM/YYYY');
	var secondDateForSmallerValue = moment(smallerDate, 'DD/MM/YYYY');
	if(firstDateForBiggerValue > secondDateForSmallerValue)
		return true;
	else
		return false;
}

/** 
 * @author Akash
 * @since  30/10/2023
 * @description Checks if the 1st date is bigger/smaller or equal than the 2nd date.
 */
function isFirstDateBiggerOrEqual(biggerDate,smallerDate)
{
	debugger;
	var firstDateForBiggerValue = moment(biggerDate, 'DD/MM/YYYY');
	var secondDateForSmallerValue = moment(smallerDate, 'DD/MM/YYYY');
	if(firstDateForBiggerValue >= secondDateForSmallerValue)
		return true;
	else
		return false;
}


// * @description : Compare 2 dates (dd/mm/yyyy) only LessThan
// * @author : Tapan
function checkFirstDateIsSmaller(smallerDate,biggerDate)
{
	if(typeof smallerDate == "undefined" || smallerDate == "")
		smallerDate = getCurrentDateAsDDMMYYYY();
	if(typeof biggerDate == "undefined" || biggerDate == "")
		biggerDate = getCurrentDateAsDDMMYYYY();
		
	var biggerYear = biggerDate.substring(6,10);
	var biggerMonth = biggerDate.substring(3,5);
	var biggerDay = biggerDate.substring(0,2);

	eIntMonth = parseInt(biggerMonth);
	endMonth = ("00" + eIntMonth.toString()).slice(-2);
	var biggerDateFinal = biggerYear + endMonth + biggerDay;
	biggerDateFinal = parseInt(biggerDateFinal);	

	var smallerYear = smallerDate.substring(6,10);
	var smallerMonth = smallerDate.substring(3,5);
	var smallerDay = smallerDate.substring(0,2);
	
	var sIntMonth = parseInt(smallerMonth);
	var startMonth = ("00" + sIntMonth.toString()).slice(-2);
	var smallerDateFinal = smallerYear + startMonth + smallerDay;
	smallerDateFinal = parseInt(smallerDateFinal);

	if (smallerDateFinal < biggerDateFinal)
		return true;
	else
		return false;
}

// * @description : Compare 2 dates (dd/mm/yyyy) only LessThanOrEqual 
// * @author : Tapan
function checkFirstDateIsSmallerOrEqual(smallerDate,biggerDate)
{
	if(typeof smallerDate == "undefined" || smallerDate == "")
		smallerDate = getCurrentDateAsDDMMYYYY();
	if(typeof biggerDate == "undefined" || biggerDate == "")
		biggerDate = getCurrentDateAsDDMMYYYY();
		
	var biggerYear = biggerDate.substring(6,10);
	var biggerMonth = biggerDate.substring(3,5);
	var biggerDay = biggerDate.substring(0,2);

	eIntMonth = parseInt(biggerMonth);
	endMonth = ("00" + eIntMonth.toString()).slice(-2);
	var biggerDateFinal = biggerYear + endMonth + biggerDay;
	biggerDateFinal = parseInt(biggerDateFinal);	

	var smallerYear = smallerDate.substring(6,10);
	var smallerMonth = smallerDate.substring(3,5);
	var smallerDay = smallerDate.substring(0,2);
	
	var sIntMonth = parseInt(smallerMonth);
	var startMonth = ("00" + sIntMonth.toString()).slice(-2);
	var smallerDateFinal = smallerYear + startMonth + smallerDay;
	smallerDateFinal = parseInt(smallerDateFinal);

	if (smallerDateFinal <= biggerDateFinal)
		return true;
	else
		return false;
}

// * @author : Tapan
// * @descr  : Get the no. of full years between 2 dates as dd/mm/yyyy
function getNoOfFullYearsBetween2DatesDDMMYYYY(smallerDateStrDDMMYYYY, biggerDateStrDDMMYYYY)
{
	var smallerDateObj = formatStringDateDDMMYYYYtoDateObject(smallerDateStrDDMMYYYY);
	var biggerDateObj = formatStringDateDDMMYYYYtoDateObject(biggerDateStrDDMMYYYY);

	let yearDifference = biggerDateObj.getFullYear() - smallerDateObj.getFullYear();
	if (smallerDateObj.getMonth() > biggerDateObj.getMonth()) 
	{
		yearDifference--;
	}
	else if (smallerDateObj.getMonth() === biggerDateObj.getMonth()) 
	{
		if (smallerDateObj.getDate() > biggerDateObj.getDate()) 
		{
			yearDifference--;
		}
		else if (smallerDateObj.getDate() === biggerDateObj.getDate()) 
		{
			if (smallerDateObj.getHours() > biggerDateObj.getHours()) 
			{
				yearDifference--;
			}
			else if (smallerDateObj.getHours() === biggerDateObj.getHours()) 
			{
				if (smallerDateObj.getMinutes() > biggerDateObj.getMinutes()) 
				{
					yearDifference--;
				}
			}
		}
	}
	
    return yearDifference;
}

// * @author : Tapan
// * @descr  : Convert date from dd/mm/yyyy format to date object
function formatStringDateDDMMYYYYtoDateObject(inDateStrDdMmYyyy)
{
	let [dayNum, monthNum, yearNum] = inDateStrDdMmYyyy.split('/');
	const dateObj = new Date(+yearNum, +monthNum - 1, +dayNum);
	
	return dateObj;
}



/** =======================================================  BANK - BRANCH - IFSC FUNCTIONS  ======================================================= */

function getBankBranchByBankId(val,selectedValue) 
{
	var bank = val;
	
	$.ajax({
		url : context+'/core/getBankBranchByBank',
		type : 'GET',
		data : ({
			bank : bank
		}),
		cache : false,
		asynch : false,
		success : function(response) {
			var selIfscCode = "";
			var html = '<option value="">Select</option>';
			var obj = jQuery.parseJSON(response);

			$.each(obj,function(key, value) {
				if(selectedValue == value.bankBranchName)
				{
					selIfscCode = value.branchIfsc;
					html = html + "<option value='"+value.branchCode+"~"+value.branchIfsc+"~"+value.bankBranchName+"' selected>"+ value.branchName + "</option>";
				}
				else
					html = html + "<option value='"+value.branchCode+"~"+value.branchIfsc+"~"+value.bankBranchName+"'>"+ value.branchName + "</option>";
			});

			$("#newBranchName").empty().append(html);
			if(selectedValue!=0){
				$('#newBranchName').select2("val",selectedValue);
				getIFSCByBranchId(selIfscCode,selIfscCode);
			}
			else
				$("#newIFSC").empty();
		},
		error : function(error) {
			bootbox.alert(error);
		}
	});
}

function getIFSCByBranchId(val,selectedValue) 
{
	if (val == null) {
		return;
	}
	var bank = $("#newBankName").val();
	var spBranchDDL = val.split("~");

	var branchCode = spBranchDDL[0];
	var branchName = spBranchDDL[2];
	$("#bankBranchCode").val(branchCode);
	$("#bankBranchName").val(branchName);
	
	$.ajax({
		url : context+'/core/getIFSCByBankBranch',
		type : 'GET',
		data : ({
			bank : bank,
			branchCode : branchCode,
			branchName : branchName
		}),
		cache : false,
		asynch : false,
		success : function(response) {
			var html = "";
			var obj = jQuery.parseJSON(response);
			html = html + '<option value="">Select</option>';
			$.each(obj, function(key, value) {
				html = html + "<option value='"+value.ifsc+"'>" + value.ifsc + "</option>";
			});
			$("#newIFSC").empty().append(html);
			
			if(selectedValue != 0){
				$('#newIFSC').select2("val",selectedValue);
			}
			
			if($('#newIFSC > option').length == 2)
			{
				$('#newIFSC :nth-child(2)').prop('selected', true);
			}
		},
		error : function(error) {
			bootbox.alert(error);
		}
	});
}

function getRegBankBranchByBankIdAndBlockId(val,selectedValue) 
{
	$("#regIFSCode").empty().append('<option value="">Select</option>').trigger('change.select2');
	$("#regBranch").empty().append('<option value="">Select</option>').trigger('change.select2');
	var bank = val;
		
	$.ajax({
		url : context+'/core/getRegBankBranchByBank',
		type : 'GET',
		data : ({
			bank : bank
		}),
		cache : false,
		asynch : false,
		success : function(response) {
			var html = '<option value="">Select</option>';
			var obj = jQuery.parseJSON(response);
			$.each(obj,function(key, value) {
				html = html + "<option value='"+value.branchCode+"~"+value.branchIfsc+"~"+value.bankBranchName+"'>"+ value.branchName + "</option>";
			});
			$("#regBranch").empty().append(html);
			if(selectedValue!=0){
				$('#regBranch').select2("val",selectedValue);
			}
		},
		error : function(error) {
			bootbox.alert(error);
		}
	});
}

function getRegBankBranchByBankId(val,selectedValue) 
{
	$("#regBranchName").empty().append('<option value="">Select</option>');

	var bank = val;
	
	$.ajax({
		url : context+'/core/getRegBankBranchByBank',
		type : 'GET',
		data : ({
			bank : bank
		}),
		cache : false,
		asynch : false,
		success : function(response) {
			var selIfscCode = "";
			var html = '<option value="">Select</option>';
			var obj = jQuery.parseJSON(response);

			$.each(obj,function(key, value) {
				if(selectedValue == value.bankBranchName)
				{
					selIfscCode = value.branchIfsc;
					html = html + "<option value='"+value.branchCode+"~"+value.branchIfsc+"~"+value.bankBranchName+"' selected>"+ value.branchName + "</option>";
				}
				else
					html = html + "<option value='"+value.branchCode+"~"+value.branchIfsc+"~"+value.bankBranchName+"'>"+ value.branchName + "</option>";
			});
			$("#regBranchName").empty().append(html);
			
			if(selectedValue != 0)
			{
				$('#regBranchName').select2("val",selectedValue);
				getRegIFSCByBranchId(selIfscCode,selIfscCode);
			}
			else
				$("#regIFSC").empty();

			if($('#regBranchName > option').length == 2)
			{
				$('#regBranchName :nth-child(2)').prop('selected', true);
				getRegIFSCByBranchId($("#regBranchName").val(),0);
			}
		},
		error : function(error) {
			bootbox.alert(error);
		}
	});
}

function getRegIFSCByBranchId(val,selectedVal) 
{
	var bank=$("#regBankName").val();

	var spBranchDDL = val.split("~");
	var branchCode = spBranchDDL[0];
	var branchName = spBranchDDL[2];
	
	$.ajax({
		url : context+'/core/getRegIFSCByBankBranch',
		type : 'GET',
		data : ({
			bank : bank,
			branchCode : branchCode,
			branchName : branchName,
		}),
		cache : false,
		asynch : false,
		success : function(response) {

			var html = "";
			var obj = jQuery.parseJSON(response);
			html = html + '<option value="">Select</option>';
			$.each(obj, function(key, value) {
				html = html + "<option value='"+value.ifsc+"'>" + value.ifsc + "</option>";
			});
			$("#regIFSC").empty().append(html);
			
			if($('#regIFSC > option').length == 2)
			{
				$('#regIFSC :nth-child(2)').prop('selected', true);
			}
			
			if(selectedVal != 0)
				$('#regIFSC').select2("val",selectedVal);
		},
		error : function(error) {
			$('#loader').addClass('hidden');
			bootbox.alert(error);
		}
	});
}

/** =======================================================  END : BANK - BRANCH - IFSC FUNCTIONS  ======================================================= */


/** =======================================================  BANK - BRANCH - IFSC FUNCTIONS (FOR BENEFICIARY ONLY)  ======================================================= */
function getBranchByBankNameForBeneficiary(val,selectedValue) 
{
	showAjaxLoader();
	var bank = val;
	
	$.ajax({
		url : context+'/core/getBankBranchByBank',
		type : 'GET',
		data : ({
			bank : bank
		}),
		cache : false,
		asynch : false,
		success : function(response) {
			hideAjaxLoader();

			var selIfscCode = "";
			var html = '<option value="">- ବାଛନ୍ତୁ -</option>';
			var obj = jQuery.parseJSON(response);

			$.each(obj,function(key, value) {
				if(selectedValue == value.bankBranchName)
				{
					selIfscCode = value.branchIfsc;
					html = html + "<option value='"+value.branchCode+"~"+value.branchIfsc+"~"+value.bankBranchName+"' selected>"+ value.branchName + "</option>";
				}
				else
					html = html + "<option value='"+value.branchCode+"~"+value.branchIfsc+"~"+value.bankBranchName+"'>"+ value.branchName + "</option>";
			});

			$("#newBranchName").empty().append(html);
			if(selectedValue!=0){
				$('#newBranchName').select2("val",selectedValue);
				getIfscForSelBranchForBeneficiary(selIfscCode,selIfscCode);
			}
			else
				$("#newIFSC").empty();
		},
		error : function(error) {
			hideAjaxLoader();
			bootbox.alert(error);
		}
	});
}

function getIfscForSelBranchForBeneficiary(val,selectedValue) 
{
	if (val == null || val == null || val == "") {
		$('#newIFSC').empty();
		return;
	}
	var bank = $("#newBankName").val();
	var spBranchDDL = val.split("~");

	var branchCode = spBranchDDL[0];
	var branchName = spBranchDDL[2];
	$("#bankBranchCode").val(branchCode);
	$("#bankBranchName").val(branchName);
	
	$.ajax({
		url : context+'/core/getIFSCByBankBranch',
		type : 'GET',
		data : ({
			bank : bank,
			branchCode : branchCode,
			branchName : branchName
		}),
		cache : false,
		asynch : false,
		success : function(response) {
			var html = "";
			var obj = jQuery.parseJSON(response);
			html = html + '<option value="">- ବାଛନ୍ତୁ -</option>';
			$.each(obj, function(key, value) {
				html = html + "<option value='"+value.ifsc+"'>" + value.ifsc + "</option>";
			});
			$("#newIFSC").empty().append(html);
			
			if(selectedValue != 0){
				$('#newIFSC').select2("val",selectedValue);
			}
			
			if($('#newIFSC > option').length == 2)
			{
				$('#newIFSC :nth-child(2)').prop('selected', true);
			}
		},
		error : function(error) {
			bootbox.alert(error);
		}
	});
}

function getRegBranchByBankNameForBeneficiary(val,selectedValue) 
{
	showAjaxLoader();
	$("#regBranchName").empty().append('<option value="">- ବାଛନ୍ତୁ -</option>');

	var bank = val;
	
	$.ajax({
		url : context+'/core/getRegBankBranchByBank',
		type : 'GET',
		data : ({
			bank : bank
		}),
		cache : false,
		asynch : false,
		success : function(response) {
			hideAjaxLoader();

			var selIfscCode = "";
			var html = '<option value="">- ବାଛନ୍ତୁ -</option>';
			var obj = jQuery.parseJSON(response);

			$.each(obj,function(key, value) {
				if(selectedValue == value.bankBranchName)
				{
					selIfscCode = value.branchIfsc;
					html = html + "<option value='"+value.branchCode+"~"+value.branchIfsc+"~"+value.bankBranchName+"' selected>"+ value.branchName + "</option>";
				}
				else
					html = html + "<option value='"+value.branchCode+"~"+value.branchIfsc+"~"+value.bankBranchName+"'>"+ value.branchName + "</option>";
			});
			$("#regBranchName").empty().append(html);
			
			if(selectedValue != 0)
			{
				$('#regBranchName').select2("val",selectedValue);
				getRegIfscFromBranchForBeneficiary(selIfscCode,selIfscCode);
			}
			else
				$("#regIFSC").empty();

			if($('#regBranchName > option').length == 2)
			{
				$('#regBranchName :nth-child(2)').prop('selected', true);
				getRegIfscFromBranchForBeneficiary($("#regBranchName").val(),0);
			}
		},
		error : function(error) {
			hideAjaxLoader();
			bootbox.alert(error);
		}
	});
}

function getBankBranchByBankId(val,idToAppend,dependentId){
	var html="<option value='0'>--Select--</option>";
    var bank=val;
    if(bank != "0"){
		showLoader();
		$.ajax({
	        url : context+'/core/getBranchBybankId',
	        type : 'GET',
	        data : ({
	            bankId : bank
	        }),
	        cache : true,
	        asynch : false,
	        success : function(response) {
				hideLoader();
	            var obj = response.data;
	                 $.each(obj, function(key,value) {
	                	 html += "<option value='"+value.bankBranchId+"'>"+value.branchName+"</option>";
	                }); 
	                $("#"+idToAppend).empty().append(html);
	            },
	        error : function(error) {
				hideLoader();
	            bootbox.alert(error);
	        }
	    });
	}else{
		$("#"+idToAppend).empty().append(html);
		$("#"+dependentId).empty().append(html);
	}
}

function getIFSCByBranchId(val,idToSet) {	
    var branchId=val;
    if(branchId != "0"){
		showLoader();
		$.ajax({
	        url : context+'/core/getIfscByBranchId',
	        type : 'GET',
	        data : ({
	            branchId : branchId
	        }),
	        cache : false,
	        asynch : false,
	        success : function(response) {
	           hideLoader();
	            var obj = response.data;
	            $("#"+idToSet).val(obj);
	        },
	        error : function(error) {
				hideLoader();
	            bootbox.alert(error);
	        }
	    });
	}else{
		$("#"+idToSet).val("");
	}

}

/* 
 * @author : Suvendu
 * @since  : 01/02/2024
 * @descrn : Loan Application By Staff Details
*/


function getSelectedLoanAttributesForPreClosure(value){
	
	var loanTypeId = $("#loanTypeId").val();
	
	 showLoader();
	$.ajax({
        url : context+'/loan/advance/getSelectedLoanAttributesForPreClosure',
        type : 'GET',
        data : ({
        	loanTypeId : loanTypeId,
        }),
        success: function(response) {
            hideLoader();
           // var responseData = response;
           var responseData = response;
        	var outcome = response.outcome;
        	var trHTML = "";
        	if(outcome){
        		  $.each(responseData.data, function (index, value){
        			$("#scanctionAmt").val(value.scanctionAmount);
              		$("#totalInstalment").val(value.totalInstal);
              		$("#hdnloanAplId").val(value.loanApplayId);
              		$("#deductionStart").val(value.deductionFromDate);
              		$("#deductionEnd").val(value.deductionEndDate);
        		  });
        	}else{
        		bootbox.alert("Something went wrong");
        	}
        	calculateMonthlyPrincInstallment()
	    },
	    error: function() {
	      bootbox.alert("Server Error");
	    }
	});
	
	}

function calculateMonthlyPrincInstallment()
{
	debugger
	var loanAmt=$("#scanctionAmt").val();
	var installment=$("#totalInstalment").val();
	var totalAmount = parseFloat(loanAmt) / parseInt(installment);

	var roundedMonthlyEMI = Math.round(totalAmount);	

	if(!isNaN(totalAmount))
		$("#monthlyDedEn").val(Math.round(totalAmount));
	else
		$("#monthlyDedEn").val("");

	var balanceLoanAmount = "0";
	var totalCalculatedAmtOnEMI = Math.round(totalAmount) * (parseInt(installment)-1);

	if(!isNaN(totalCalculatedAmtOnEMI))
		$("#lastMonthDed").val(parseFloat(loanAmt)-totalCalculatedAmtOnEMI);
	else
		$("#lastMonthDed").val("");

	validateAndSetPrincipalToMonthYear(); 
}


function validateAndSetPrincipalToMonthYear() {
	var fromDate = $("#deductionStart").val();
	if (fromDate != ''){
	}
}


/** =======================================================  END : BANK - BRANCH - IFSC FUNCTIONS (FOR BENEFICIARY ONLY)  ======================================================= */



/** ==========================================  CHECK IF ATTENDANCE PROCESSED FOR COLLEGE-MONTH-YEAR-EMPLMNTTYPE  ========================================== */
function AJAX_checkIfAttendanceProcessedForCollegeMonthYearEmplmntType(collegeId, monthNum, yearNum, emplmntTypeId)
{
	var ajaxResponse = "";
	
	if(collegeId != "0" && monthNum != "0" && monthNum != "" && yearNum != "0" && yearNum != "" && emplmntTypeId != "" && emplmntTypeId != "0")
	{
		showLoader();
		$.ajax({	
			url : context+"/staff/payroll/checkIfAttendanceProcessedForCollegeMonthYearEmplmntType",
			async: false,
			type : 'GET',
			data: {
				"collegeId" : collegeId,
				"monthAsInt" : monthNum,
				"yearAsInt" : yearNum,
				"emplmntTypeId" : emplmntTypeId,
			},
			success : function(ajaxResp) 
			{
				hideLoader();
				ajaxResponse = ajaxResp;
			},
			error : function(error) {
				hideLoader();
				console.log("Error ==>"+error);
			}
		});
	}
	
	return ajaxResponse;
}
/** ==========================================  END : CHECK IF ATTENDANCE PROCESSED FOR COLLEGE-MONTH-YEAR-EMPLMNTTYPE  ========================================== */


/** ==========================================  GENERATE PAYBILL NO & GET OTHER DETAILS BY MONTH-YEAR ONCHANGE  ========================================== */

function AJAX_generatePaybillAndGetOtherDetailsBySalTypeColgIdMonthYear(salaryType, collegeId, monthNum, yearNum, emplmntTypeId)
{
	var ajaxResponse = "";
	
	if(salaryType != "" && collegeId != "0" && monthNum != "0" && monthNum != "" && yearNum != "0" && yearNum != "" && emplmntTypeId != "" && emplmntTypeId != "0")
	{
		showLoader();
		$.ajax({	
			url : context+"/staff/payroll/getPaybillAndOtherDetailsBySalTypeColgIdMonthYear",
			async: false,
			type : 'GET',
			data: {
				"salaryType" : salaryType,
				"collegeId" : collegeId,
				"monthAsInt" : monthNum,
				"yearAsInt" : yearNum,
				"emplmntTypeId" : emplmntTypeId,
			},
			success : function(ajaxResp) 
			{
				hideLoader();
				ajaxResponse = ajaxResp;
			},
			error : function(error) {
				hideLoader();
				console.log("Error ==>"+error);
			}
		});
	}
	
	return ajaxResponse;
}
/** ==========================================  END : GENERATE PAYBILL NO & GET OTHER DETAILS BY MONTH-YEAR ONCHANGE  ========================================== */


/** ==========================================  CHECK IF ATTENDANCE PROCESSED FOR A MONTH, YEAR, COLLEGE & EMPLOYMENT-TYPE  ========================================== */

function checkIfAttendanceProcessedForCollegeMonthYearEmplmntType(collegeId, monthNum, yearNum, emplmntTypeId)
{
	var ajaxResponse = "";
	
	if(collegeId != "0" && monthNum != "0" && monthNum != "" && yearNum != "0" && yearNum != "" && emplmntTypeId != "" && emplmntTypeId != "0")
	{
		showLoader();
		$.ajax({	
			url : context+"/staff/payroll/checkIfAttendanceProcessedForCollegeMonthYearEmplmntType",
			async: false,
			type : 'GET',
			data: {
				"collegeId" : collegeId,
				"monthAsInt" : monthNum,
				"yearAsInt" : yearNum,
				"emplmntTypeId" : emplmntTypeId,
			},
			success : function(ajaxResp) 
			{
				hideLoader();
				ajaxResponse = ajaxResp;
			},
			error : function(error) {
				hideLoader();
				console.log("Error ==>"+error);
			}
		});
	}
	
	return ajaxResponse;
}
/** ==========================================  END : CHECK IF ATTENDANCE PROCESSED FOR A MONTH, YEAR, COLLEGE & EMPLOYMENT-TYPE  ========================================== */



