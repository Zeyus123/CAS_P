$().ready(function(){

	reDrawDashbaord();
	
	$('#btnFilter').click(function(){
		reDrawDashbaord();
	});

});

	/* Dirty Function copied over from Reporting */
	
$(document).ready(function() {
	var finYr = 0;//${selFinYear};//isPrvCheck
//	var prvCheck = ${isPrvCheck};
	/* <c:if test="${not empty incomeReportGrp}">
	$(".dataCheck").removeAttr("hidden");
	</c:if> */
	if (finYr != 0) {
		$("#prevYear").removeClass("hidden");
		if (prvCheck) {
			$('#isPrvCheck').prop('checked', true);
		} else {
			$('#isPrvCheck').prop('checked', false);
		}
	}
});

function getFinYearStartEndDates(finYear) {

	var html = "<option value='0'>Select</option>";
	if (finYear != "0") {
		var fyear = $("#financialYear option:selected").text().split("-");
		if ($("#isPrvCheck").prop("checked") == true) {
			getPrevYrData();
			return false;
		}
		var financiyalYearFromDate = "01/04/" + fyear[0];
		var financiyalYearToDate = "31/03/" + fyear[1];
		$("#fromDate").val(financiyalYearFromDate);
		$("#toDate").val(financiyalYearToDate);
		$("#prevYear").removeClass("hidden");
		callForFinYear();
	} else {
		$("#monthId").empty().append(html);
		$('#isPrvCheck').prop('checked', false);
		/* $("#fromDate").attr("disabled","disabled");
		$("#toDate").attr("disabled","disabled"); */
		$("#fromDate").val(fromDateCheck);
		$("#toDate").val(toDateCheck);
		$("#prevYear").addClass("hidden");
		$("#fromDate").removeAttr("disabled", "disabled");
		$("#toDate").removeAttr("disabled", "disabled");
		callForFinYear();
	}
}

/*function getMonthByfinyr(finYear) {
	var html = "<option value='0'>Select</option>";
	var month = "";

	<c:forEach items="${monthList}" var="month">
	month = month
			+ '<option value="${month.monthId}">${month.monthNameEn}</option>';
	</c:forEach>

	$("#monthId").empty().append(html + "" + month);

}*/

function findDateRangeByMonth(month) {
	var fyear = $("#financialYear option:selected").text().split("-");
	var monthNo = month;
	var fromStDate = "";
	if (monthNo != 0) {
		if (monthNo == 1 || monthNo == 2 || monthNo == 3) {
			var fromStDate = "0" + monthNo + "/01/" + fyear[1];
		} else {
			var fromStDate = "0" + monthNo + "/01/" + fyear[0];

		}
		var formatted_date = function(date) {
			var m = ("0" + (date.getMonth() + 1)).slice(-2); // in javascript month start from 0.
			var d = ("0" + date.getDate()).slice(-2); // add leading zero 
			var y = date.getFullYear();
			return d + '/' + m + '/' + y;
		};
		//alert(fromStDate)
		var cdate = new Date(fromStDate);
		//alert(cdate)

		var first_day = new Date(cdate.getFullYear(), cdate.getMonth(), 1);

		var last_day = new Date(cdate.getFullYear(), cdate.getMonth() + 1,
				0);

		var month_start_date = formatted_date(first_day);

		var month_end_date = formatted_date(last_day);

	} else {
		$('#datepick>input').datepick("destroy");
		getFinYearStartEndDates($("#financialYear option:selected").val());
		return false;
	}

	/*
	 var month_end_date =formatted_date(new Date()); // limit current month date range upto current day.
	 */
	if (month != 0) {
		$("#fromDate").val(month_start_date);
		$("#toDate").val(month_end_date);
	}
	callForMonth();
}

function callForMonth() {
	$('#datepick>input').datepick("destroy");
	$('#datepick>input').datepick(
			{
				onShow : $.datepick.monthOnly,
				dateFormat : 'dd/mm/yyyy',
				yearRange : 'c-100:c+5',
				changeMonth : false,
				changeYear : false,
				showOnFocus : true,
				showTrigger : '<button type="button" class="trigger">'
						+ '<i class="fa fa-calendar"></i></button>'
			});
}

function callForFinYear() {
	$('#datepick>input').datepick("destroy");
	$('#datepick>input').datepick(
			{
				onShow : $.datepick.monthOnly,
				dateFormat : 'dd/mm/yyyy',
				yearRange : 'c-100:c+5',
				showOnFocus : true,
				showOnFocus : true,
				showTrigger : '<button type="button" class="trigger">'
						+ '<i class="fa fa-calendar"></i></button>'
			});
}


function reDrawDashbaord()
{
debugger;
	window.loadCounter = 0;
	
	const REPORTING_API_BASE = "/dashboard/";
	
	if (!window.boxPresenter)
	{
		window.boxPresenter = new BoxInfoPresenter(REPORTING_API_BASE, window.contextPath);
	}
	if (!window.beneficiaryChart)
	{
		window.beneficiaryChart = new beneficiaryChart(REPORTING_API_BASE, window.contextPath);
	}
	if(window.expenditurechart){
		window.expenditurechart = new expenditurechart(REPORTING_API_BASE, window.contextPath);
	}
	window.boxPresenter.doFetch();
	window.beneficiaryChart.doFetch();
	window.expenditurechart.doFetch();
}