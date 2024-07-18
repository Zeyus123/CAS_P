var ACLConfigPageController = (function(){
	
	function ACLConfigPageController(options)
	{
		this.userId = -1;
		wireupUI.call(this);
		this.skipFields = ["created_by","created_on","last_updated_by","last_updated_on"];
		
		this.pageSize = options.pageSize || 10;
		this.currentPage = options.currentPage || 0;
		this.csrfToken = options.csrf; 
		this.searchTerm = "";
		this.totalPages = options.totalPages || 0;
	}

	ACLConfigPageController.prototype.setupAutoComplete = function()
	{
		var me = this;
		$('#userName').autocomplete({
			serviceUrl: ctxPath + '/admin/user/search',
		    paramName: "qry",
		    delimiter:",",
		    width:'100%',
		    transformResult: function(response) {
		      return {
		        //must convert json to javascript object before process
		        suggestions: $.map($.parseJSON(response), function(item) {
		          return {
		            value: item.userName + ' (' +item.firstName + ' ' + item.lastName + ')',
		            id : item.userId
		          };
		        })
		      };
		    },
		    onSelect:function(data){
		    	$('#userName').val(data.value);
		    	$('#userId').val(data.id);
		    	me.userId = data.id;
		    	
		    	getRolesForUser.call(me, data.id);
		    }

		});
	}
	
	ACLConfigPageController.prototype.fetchConfigData = function()
	{
		var me = this;
		
		var roleId = $('#roleId').val();
		var aclId = $('#accessLevelId').val();
		var userId = me.userId;
		var userName=$("#userName").val();
		
		if (userName == "")
		{
			bootbox.alert("Please Search User");
			return false;
		}
		if (roleId == "")
		{
			bootbox.alert("Please select the Role");
			return false;
		}
		
		if (aclId == "")
		{
			bootbox.alert("Please select the Level");
			return false;
		}
		
		var postData = 'userId=' + userId + '&roleId=' + roleId + '&roleLevelMasterId=' + aclId;
		postData = postData + "&_csrf=" + this.csrfToken  + "&searchTerm=" + this.searchTerm + "&page=" + this.currentPage + "&size=" + this.pageSize;
		
		fetch(window.ctxPath + '/admin/acl/config/list', {
			method : 'POST',
			body : postData,
			 headers: {
			        "Content-Type": "application/x-www-form-urlencoded",
			        "X-Requested-With" : "XMLHttpRequest",
			 }
		}).then(resp => resp.json())
		.then(json => {
			buildTable.call(me, json);
			setUpPagination.call(me, json);
		});
	}
	
	ACLConfigPageController.prototype.saveConfig = function(elem)
	{
		var objectId = $(elem).attr('data-id');
		var roleId = $('#roleId').val();
		var aclId = $('#accessLevelId').val();
		var userId = this.userId;
		
		var isChecked = $(elem).prop("checked");
		
		if (roleId == "")
		{
			bootbox.alert("Please select the Role");
			$(elem).prop('checked', !isChecked);
			return false;
		}
		
		if (aclId == "")
		{
			bootbox.alert("Please select the Level");
			$(elem).prop('checked', !isChecked);
			return false;
		}
		
		if (userId == "")
		{
			bootbox.alert("Please select the User");
			$(elem).prop('checked', !isChecked);
			return false;
		}
		
		var postData = 'userId=' + userId + '&roleId=' + roleId + '&roleLevelMasterId=' + aclId + '&objectId=' + objectId;
		postData = postData + "&_csrf=" + this.csrfToken;
		
		fetch(window.ctxPath + '/admin/acl/config/save', {
			method : 'POST',
			body : postData,
			 headers: {
			        "Content-Type": "application/x-www-form-urlencoded",
			        "X-Requested-With" : "XMLHttpRequest",
			 }
		}).then(resp => resp.json())
		.then(json => {
			bootbox.alert(json.message == "" ? json.error : json.message);
		});
	}
	
	/* private methods */
	
	function wireupUI()
	{
		let me = this;
		
		$('#userName').change(function(){

			$('#roleId').val('').trigger('change');
			$('#roleId').prop('disabled', true);
			
			$('#accessLevelId').val('').trigger('change');
			$('#accessLevelId').prop('disabled', true);
			
			$('#tblAccess').remove();
			$('#pnl').empty();

		});
		
		$('#roleId').change(function(){
			var currRoleId = $('#roleId').val();
			if (currRoleId != "" && currRoleId != null)
			{
				$('#accessLevelId').empty();
				fetch(window.ctxPath + '/admin/acl/byRole?roleId=' + currRoleId)
				.then(resp => resp.json())
				.then(json => {
					var html = '<option value="">--SELECT--</option>';
					$.each(json, (idx, elem) => {
						html += '<option value=' + elem.roleRightLevelId + '>'+ elem.displayName + '</option>';
					});
					$('#accessLevelId').append(html);
				});
				$('#accessLevelId').prop('disabled', false);
			}
			else
			{
				$('#accessLevelId').val('').trigger('change');
				$('#accessLevelId').prop('disabled', true);
			}
		});
		
		document.getElementById("pageSize").addEventListener('change', (e) => {
			const searchTerm = document.getElementById("searchTerm").value;
			me.searchTerm = searchTerm;
			const newPagesize = document.getElementById("pageSize").value;
			/* if pageSize changes show page 1 */
			if (me.pageSize != newPagesize)
			{
				me.currentPage = 0;
			}
			me.pageSize = newPagesize;
			me.fetchConfigData();
		});
		
		document.getElementById("searchTerm").addEventListener('keyup', (e) => {
			const searchTerm = document.getElementById("searchTerm").value;
			if (e.keyCode == 13)
			{
				
				if (me.searchTerm != searchTerm)
				{
					me.currentPage = 0;
				}
				me.searchTerm = searchTerm;
				me.fetchConfigData();
			}
			else
			{
				const table = document.getElementsByTagName("table")[0];
				const rows = table.rows; 
				
				if (searchTerm != "")
				{
					for (var i = 1; i < rows.length; i+=1) {
						const text = rows[i].innerText;
						const searchregEx = new RegExp(searchTerm,'i');
						const match = text.match(searchregEx);
						if (match == null)
						{
							rows[i].classList.add("dt-hide");
						}
						else
						{
							rows[i].classList.remove("dt-hide");
						}
					}
				}
				else
				{
					for (var i = 1; i < rows.length; i+=1) {
						rows[i].classList.remove("dt-hide");
					}
				}
			}
		});
		
		document.getElementById("dt-start").addEventListener('click', (e) => {
			const searchTerm = document.getElementById("searchTerm").value;
			me.searchTerm = searchTerm;
			me.pageSize = document.getElementById("pageSize").value;
			me.currentPage = 0;
			me.fetchConfigData();
		});

		document.getElementById("dt-next").addEventListener('click', (e) => {
			const searchTerm = document.getElementById("searchTerm").value;
			me.searchTerm = searchTerm;
			me.pageSize = document.getElementById("pageSize").value;
			me.currentPage ++; 
			me.fetchConfigData();
		});

		document.getElementById("dt-end").addEventListener('click', (e) => {
			const searchTerm = document.getElementById("searchTerm").value;
			me.searchTerm = searchTerm;
			me.pageSize = document.getElementById("pageSize").value;
			me.currentPage = this.totalPages - 1;
			me.fetchConfigData();
		});

		document.getElementById("dt-previous").addEventListener('click', (e) => {
			const searchTerm = document.getElementById("searchTerm").value;
			me.searchTerm = searchTerm;
			me.pageSize = document.getElementById("pageSize").value;
			me.currentPage -- ;
			me.fetchConfigData();
		});
		
	}
	
	function setUpPagination(json)
	{
		const lblCounter = document.getElementById("dt-counter");
		lblCounter.innerText = "Showing page " + (json.currentPage + 1) + " of " + json.totalPages + " pages.";
		
		this.pageSize = json.pageSize ;
		this.currentPage = json.currentPage ;
		this.totalPages = json.totalPages;
	}
	
	function getRolesForUser(userId)
	{
		$('#roleId').empty();
		fetch(window.ctxPath + '/admin/role/byUser?userId=' + userId)
			.then(resp => resp.json())
			.then(json => {
				var html = '<option value="">--SELECT--</option>';
				$.each(json, (idx, elem) => {
					html += '<option value=' + elem.roleId + '>'+ elem.displayName + '</option>';
				});
				$('#roleId').append(html);
			});
		$('#roleId').prop('disabled', false);
	}
	
	function buildTable(json)
	{
		var dataJSON = JSON.parse(json.data);
		var html = "";
		var hdrRow = "";
		
		$('#pnl').empty();
		
		/* Find Index of PK Column */
		Object.keys(json.columnMetaData).forEach((elem) =>{
			var columnName = json.columnMetaData[elem]
			/* Add this column only if it is not in SkipFields */
			if (this.skipFields.indexOf(columnName) == -1)
			{
				if (columnName == json.primaryKey)
				{
					hdrRow += '<th>Select</th>';
				}
				else
				{
					hdrRow += '<th>' + dbColNameToUILabel(columnName) + '</th>';
				}
				
			}
			
		});
		hdrRow += '</tr></thead>'

		/* Build HTML Table */
		html = '<table id="tblAccess" class="table table-bordered table-striped table-sm"><thead><tr>' + hdrRow;
		html += '<tbody>';
		$.each(dataJSON, (idx, elem) => {
			html += '<tr>' ;
			/* Make CheckBox For PK Column */
			var rowPKVal = parseInt(elem[json.primaryKey]);
			if (json.allotedRowIds.indexOf(rowPKVal) != -1)
			{
				html += '<td><input type="checkbox" class="x-config" data-id="'+ elem[json.primaryKey] + '" checked/></td>';
			}
			else
			{
				html += '<td><input type="checkbox" class="x-config" data-id="'+ elem[json.primaryKey] + '"/></td>';
			}

			Object.values(json.columnMetaData).forEach((columnName) =>{
				/* Add this column only if it is not in SkipFields */
				if (this.skipFields.indexOf(columnName) == -1 && columnName != json.primaryKey)
				{
					html += '<td>' + elem[columnName] + '</td>';
				}
			});
			html += '</tr>';
		});
		$('#pnl').append(html);
		
	}
	

	
	return ACLConfigPageController;
	
})();