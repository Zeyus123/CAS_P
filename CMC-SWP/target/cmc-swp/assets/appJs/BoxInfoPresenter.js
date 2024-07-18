class BoxInfoPresenter{
	constructor(apiPath, contextPath)
	{
		this.apiPath =  apiPath;
		this.worker = null;
		this.contextPath = contextPath;
		this.data = null;
	}
	
	doFetch() {
		
		if (this.worker == null )
		{
			this.worker = new Worker(this.contextPath + '/assets/appJs/CommonWorker.js');
			this.worker.addEventListener('message', (e) => this.processData(e));
		}
		
		const url = this.contextPath + this.apiPath + "boxInfo";
		window.loadCounter++;
		this.worker.postMessage({
			"method" : "GET" ,
			"url" : url 
		});
	}
	
	processData(e){

		if (e.data!= "" || e.data!= null )
		{
			this.data = e.data;

			console.log(this.data);	
			$('#TOTAL_BNF').text((this.getValueFor("total_bnf")));
			$('#DRCT_BNF').text((this.getValueFor("total_drct_bnf")));
			$('#INDRCT_BNF').text((this.getValueFor("total_indrct_bnf")));
			$('#TOTAL_CLSTR').text((this.getValueFor("total_clstr")));
			$('#RVNU_GRTH').text((this.getValueFor("")));
			$('#BST_PRFMG_SPV').text((this.getValueFor("")));
			$('#QMULTV_RVNU').text((this.getValueFor("total_rvnu")));
			$('#QMULTV_RVNU_CRNT_MONTH').text((this.getValueFor("monthly_rvnu")));
			$('#QMULTV_RVNU_CRNT_QTR').text((this.getValueFor("qtrly_rvnu")));
			$('#QMULTV_XPNDTR').text((this.getValueFor("total_expndtr")));
			$('#QMULTV_XPNDTR_CRNT_MONTH').text((this.getValueFor("monthly_expnd")));
			$('#QMULTV_XPNDTR_CRNT_QTR').text((this.getValueFor("qtrly_expnd")));
			$('#QMULTV_GRS_PRFT_LS').text((this.getValueFor("grsProfitLoss")));
			$('#QMULTV_GRS_PRFT_LS_CRNT_MONTH').text((this.getValueFor("monthlyProfitLoss")));
            $('#QMULTV_GRS_PRFT_LS_CRNT_QTR').text((this.getValueFor("qtrlyProfitLoss")));
            $('#TOTAL_FND_RCVD').text((this.getValueFor("fund_rcvd")));
            $('#TOTAL_UC').text((this.getValueFor("total_expndtr")));
            //$('#UC_2_B_SBMTED').text((this.getValueFor("")));




			}
		window.loadCounter--;
	}
	
		getValueFor(key){
    		let retVal = 0;
    		var result = Object.entries(this.data);
    		result.filter( el => {
    			if( el[0] == key)
    			{
    				retVal =el[1];

    			}
    		});

    		return retVal;
    	}
}
