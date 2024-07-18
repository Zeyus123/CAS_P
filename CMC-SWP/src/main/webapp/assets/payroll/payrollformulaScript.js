
	$('#fb_plus').on('click', function() 
	{
		var caretPos = document.getElementById("formula").selectionStart;
		var textAreaTxt = jQuery("#formula").val();
		var txtToAdd = "+";
		if(caretPos == 0){
			$("#formula").val(txtToAdd);
		}else{
			var includesSigns = textAreaTxt[caretPos-1] === "+" || textAreaTxt[caretPos-1] === "-" || textAreaTxt[caretPos-1] === "*" || textAreaTxt[caretPos-1] === "/";
			var textMsg = includesSigns ? textAreaTxt.substring(0, caretPos-1) : textAreaTxt.substring(0, caretPos);
			$("#formula").val(textMsg + txtToAdd + textAreaTxt.substring(caretPos) );
		}
// 		$("#formula").val(textAreaTxt.substring(0, caretPos) + txtToAdd + textAreaTxt.substring(caretPos) );
	});

	$('#fb_minus').on('click', function() 
	{
		var caretPos = document.getElementById("formula").selectionStart;
		var textAreaTxt = jQuery("#formula").val();
		var txtToAdd = "-";
		if(caretPos == 0 ){
			$("#formula").val(txtToAdd);
		}else{
			var includesSigns = textAreaTxt[caretPos-1] === "+" || textAreaTxt[caretPos-1] === "-" || textAreaTxt[caretPos-1] === "*" || textAreaTxt[caretPos-1] === "/";
			var textMsg = includesSigns ? textAreaTxt.substring(0, caretPos-1) : textAreaTxt.substring(0, caretPos);
			$("#formula").val(textMsg + txtToAdd + textAreaTxt.substring(caretPos));
		}
// 		$("#formula").val(textAreaTxt.substring(0, caretPos) + txtToAdd + textAreaTxt.substring(caretPos) );
	});

	$('#fb_cross').on('click', function() 
	{
		var caretPos = document.getElementById("formula").selectionStart;
		var textAreaTxt = jQuery("#formula").val();
		var txtToAdd = "*";
		if(caretPos == 0){
			$("#formula").val(txtToAdd);
		}else{
			var includesSigns = textAreaTxt[caretPos-1] === "+" || textAreaTxt[caretPos-1] === "-" || textAreaTxt[caretPos-1] === "*" || textAreaTxt[caretPos-1] === "/";
			var textMsg = includesSigns ? textAreaTxt.substring(0, caretPos-1) : textAreaTxt.substring(0, caretPos);
			$("#formula").val(textMsg + txtToAdd + textAreaTxt.substring(caretPos));
		}
// 		$("#formula").val(textAreaTxt.substring(0, caretPos) + txtToAdd + textAreaTxt.substring(caretPos) );
	});

	$('#fb_divide').on('click', function() 
	{
		var caretPos = document.getElementById("formula").selectionStart;
		var textAreaTxt = jQuery("#formula").val();
		var txtToAdd = "/";
		if(caretPos == 0){
			$("#formula").val(txtToAdd);
		}else{
			var includesSigns = textAreaTxt[caretPos-1] === "+" || textAreaTxt[caretPos-1] === "-" || textAreaTxt[caretPos-1] === "*" || textAreaTxt[caretPos-1] === "/";
			var textMsg = includesSigns ? textAreaTxt.substring(0, caretPos-1) : textAreaTxt.substring(0, caretPos);
			$("#formula").val(textMsg + txtToAdd + textAreaTxt.substring(caretPos));
		}
// 		$("#formula").val(textAreaTxt.substring(0, caretPos) + txtToAdd + textAreaTxt.substring(caretPos) );
	});

	$('#fb_bracket_left').on('click', function() 
	{
		var caretPos = document.getElementById("formula").selectionStart;
		var textAreaTxt = jQuery("#formula").val();
		var txtToAdd = "(";
		
		$("#formula").val(textAreaTxt.substring(0, caretPos) + txtToAdd + textAreaTxt.substring(caretPos));
	});

	$('#fb_bracket_right').on('click', function() 
	{
		var caretPos = document.getElementById("formula").selectionStart;
		var textAreaTxt = jQuery("#formula").val();
		var txtToAdd = ")";
		$("#formula").val(textAreaTxt.substring(0, caretPos) + txtToAdd + textAreaTxt.substring(caretPos) );
	});

	$('#fb_backspace').click( function() 
	{
		var val = $('#formula').val();
		$('#formula').val(val.substring(0, val.length - 1));
	});

	$('#fb_clear').click( function() 
	{
		$('#formula').val('');
	});

	$('#fb_shift_value').click( function() 
	{
// 		var caretPos = document.getElementById("formula").selectionStart;
// 		var textAreaTxt = jQuery("#formula").val();
// 		var shiftval = $('#shift_input').val();
// 		$("#formula").val(textAreaTxt.substring(0, caretPos) + shiftval + textAreaTxt.substring(caretPos) );
		
		var caretPos = document.getElementById("formula").selectionStart;
		var textAreaTxt = jQuery("#formula").val();
		var txtToAdd = $('#shift_input').val();;
		debugger
		if(caretPos == 0 ){
			$("#formula").val(txtToAdd);
		}else{
			var includesSigns = textAreaTxt[caretPos-1] === "+" || textAreaTxt[caretPos-1] === "-" || textAreaTxt[caretPos-1] === "*" || textAreaTxt[caretPos-1] === "/";
			var textMsg = includesSigns ? textAreaTxt.substring(0, caretPos) : textAreaTxt.substring(0, caretPos);
			
			if (textAreaTxt[caretPos - 1] === "" || !["+","-","*","/"].includes(textAreaTxt[caretPos-1])) {
	            bootbox.alert("Please select an operator before adding the next payhead");
	        } else {
	        	$("#formula").val(textMsg + txtToAdd + textAreaTxt.substring(caretPos));
	        }
		}
		
	});