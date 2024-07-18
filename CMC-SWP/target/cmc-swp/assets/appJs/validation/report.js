$(document).ready(function(){
	var trs = $('table.tablee > tbody > tr');
	var colCnt = $(trs[0]).find('td').length;
	
	var cnts = Array.apply(null, Array(colCnt)).map(function (x, i) { return 0; }) /* Zero Initialize */
	
	$.each(trs, function(idx, elem){

		var cols = $(elem).find('td');
		$.each(cols, function(cidx, celem){
			cnts[cidx] = parseInt(cnts[cidx]) + parseInt($(celem).text());
		});
	});
	
	$('table.tablee > tfoot th').each(function(idx, elem){
		if (idx > 0)
		{
			let skip = $(elem).attr('data-skip');
			if (skip === undefined)
				$(elem).text(cnts[idx]);
		}
		
	});
});