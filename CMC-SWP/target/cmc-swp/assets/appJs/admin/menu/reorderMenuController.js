var ReorderMenuController = (function(){
	
	function ReorderMenuController()
	{
		$("#treegrid").fancytree({
			  checkbox: false,
			  extensions: ["dnd5", "wide", "table"],
			  selectMode: 1,
			  source: {
				    url: window.ctxPath + "/admin/menu/reorder"
				  },
				  
			  dnd5: {
				  preventRecursion: true, 
				  dragStart: function(node, data) { 
					  if (node.data.isParent == true)
					  {
					  	return false;
					  }

					  return true; 
				  },
				  dragEnter: function(node, data) { return true; },
				  dragOver: function(node, data) {
					  
					  
				        // Assume typical mapping for modifier keys
				        data.dropEffect = data.dropEffectSuggested;
				        data.dropEffect = "move";
				      },
			      dragDrop: function(node, data) { 
			    	  data.otherNode.moveTo(node, data.hitMode); 
			    	  
			    	  var childId = data.otherNode.data.id;
			    	  console.log('Child ->' + childId);
			    	  
			    	  //console.log('New Parent ->' + node.parent.title);
			    	  var parentId = node.data.id
			    	  console.log('New Parent Grp ->' + parentId);
			    	  
			    	  
			    	  $.each(node.children, function(idx, elem){
			    		 if (elem.data.id == data.otherNode.data.id)
		    			 {
			    			 var newLvl = parseInt(idx);
			    			 newLvl ++;
			    			 
			    			 saveMenuOrder(childId, parentId, newLvl)
			    			 console.log('New Level ->' + newLvl);
		    			 }
			    	  });
			    	  
			      }
			  },
			  
			  table: {
			        indentation: 20,      // indent 20px per node level
			        nodeColumnIdx: 0,     // render the node title into the 2nd column
		      },
			      
			 
			  renderColumns: function(event, data) {
		        var node = data.node,
		          $tdList = $(node.tr).find(">td");

		          $tdList.eq(1).text(node.data.url);
		          
		          node.selected = false;
		        
		      }
		 });
	}
	
	function saveMenuOrder(menuId, parentMenuId , level)
	{
		$('#menuId').val(menuId);
		$('#parentMenuId').val(parentMenuId);
		$('#order').val(level);
		
		$.ajax({
            url: window.ctxPath + '/admin/menu/saveOrder',
            type: 'post',
            contentType: 'application/x-www-form-urlencoded',
            data: $('#frmMoveMenu').serialize(),
            success: function( data, textStatus, jQxhr ){
                console.log(data);
            },
            error: function( jqXhr, textStatus, errorThrown ){
                console.log( errorThrown );
            }
        });
	}
	
	
	return ReorderMenuController;

})();

$().ready(function(){
	var reorderMenuController = new ReorderMenuController();
})