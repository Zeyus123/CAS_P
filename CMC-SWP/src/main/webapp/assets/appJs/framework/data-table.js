class svrDatatable
{
	constructor(url , options){
		this.pageSize = options.pageSize || 10;
		this.currentPage = options.currentPage || 0;
		this.postBackUrl = url;
		this.csrf = options.csrf; 
		this.searchTerm = "";
		this.totalPages = options.totalPages;

		document.getElementById("searchTerm").addEventListener('keyup', (e) => {
			const searchTerm = document.getElementById("searchTerm").value;
			if (e.keyCode == 13)
			{
				
				if (this.searchTerm != searchTerm)
				{
					this.currentPage = 0;
				}
				this.searchTerm = searchTerm;
				this.search();
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

		document.getElementById("pageSize").addEventListener('change', (e) => {
			const searchTerm = document.getElementById("searchTerm").value;
			this.searchTerm = searchTerm;
			const newPagesize = document.getElementById("pageSize").value;
			/* if pageSize changes show page 1 */
			if (this.pageSize != newPagesize)
			{
				this.currentPage = 0;
			}
			this.pageSize = newPagesize;
			this.search();
		});

		document.getElementById("dt-start").addEventListener('click', (e) => {
			const searchTerm = document.getElementById("searchTerm").value;
			this.searchTerm = searchTerm;
			this.pageSize = document.getElementById("pageSize").value;
			this.currentPage = 0;
			this.search();
		});

		document.getElementById("dt-next").addEventListener('click', (e) => {
			const searchTerm = document.getElementById("searchTerm").value;
			this.searchTerm = searchTerm;
			this.pageSize = document.getElementById("pageSize").value;
			this.currentPage ++; 
			this.search();
		});

		document.getElementById("dt-end").addEventListener('click', (e) => {
			const searchTerm = document.getElementById("searchTerm").value;
			this.searchTerm = searchTerm;
			this.pageSize = document.getElementById("pageSize").value;
			this.currentPage = this.totalPages - 1;
			this.search();
		});

		document.getElementById("dt-previous").addEventListener('click', (e) => {
			const searchTerm = document.getElementById("searchTerm").value;
			this.searchTerm = searchTerm;
			this.pageSize = document.getElementById("pageSize").value;
			this.currentPage -- ;
			this.search();
		});


	}

	search()
	{
		if (this.postBackUrl != "")
		{
			let frmHtml = '<form id="frmGyame" method="POST" action="' + this.postBackUrl + '">';
			frmHtml += '<input type="hidden" name="_csrf" value="' + this.csrf + '"/>'; 
			frmHtml += '<input type="hidden" name="searchTerm" value="' + this.searchTerm + '"/>';
			frmHtml += '<input type="hidden" name="page" value="' + this.currentPage + '"/>';
			frmHtml += '<input type="hidden" name="size" value="' + this.pageSize + '"/>';
			frmHtml += '</form>';

			const frmElem = document.createElement("div");
			frmElem.innerHTML =  frmHtml;
			document.body.appendChild(frmElem);
			document.getElementById("frmGyame").submit();
		}
		else
		{
			console.error("The postBackUrl parameter has not been set.")
		}

	}
	
}