var ModuleController = (function(){
	
	/* Constructor */
	function ModuleController()
	{
	};
	
	
	ModuleController.prototype.showIconDialog = function()
	{
		$('#glp').empty();
		
		fetch(window.ctxPath + '/assets/fonts/fontawesome/fa-solid-900.svg')
		.then(response => response.text())
		.then(xml => {
			$xml = $($.parseXML(xml));
			$elems = $xml.find('glyph');
			$.each($elems, (idx, elem) => {
				$('#glp').append('<div class="col-sm-1 x-icon"><i class="fa fa-2x fa-' + $(elem).attr('glyph-name') + '"></i></div>');
			});

		})
		.then(() => {
				fetch(window.ctxPath + '/assets/fonts/fontawesome/fa-regular-400.svg')
				.then(response => response.text())
				.then(xml => {
					$xml = $($.parseXML(xml));
					$elems = $xml.find('glyph');
					$.each($elems, (idx, elem) => {
						$('#glp').append('<div class="col-sm-1 x-icon"><i class="fa fa-2x fa-' + $(elem).attr('glyph-name') + '"></i></div>');
					});
					$('#pnlChooseIcon').modal();
				});
		}); //end then of 2nd fetch
	}
	
	return ModuleController;
	
})();

/* Page Level Event handlers */
$().ready(function(){
	var moduleController = new ModuleController();
	
	$('#btnFindIcon').click(function(){
		moduleController.showIconDialog();
	});
	
	$(document).on('click', '.col-sm-1.x-icon', function(){
		var clazz = $($(this).find("i")[0]).attr('class');
		clazz = clazz.replace('fa-2x','');
		$('#menuIcon').val(clazz);
		$('#viewIcon').attr('class',clazz + ' fa-2x');
		
		$('#pnlChooseIcon').modal('hide');
	});
	
	/*$('#btnSubmit').click(function(){
		$('#frmModule').submit();
	});*/
});