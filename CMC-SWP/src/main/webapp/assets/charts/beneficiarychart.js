class beneficiaryChart{
	constructor(apiPath, contextPath)
	{
		this.apiPath =  apiPath;
		this.worker = null;
		this.contextPath = contextPath;
		this.data = null;
		this.context = "STATE";
	}
	doFetch() {
    		if (this.worker == null )
		{
			this.worker = new Worker(this.contextPath + '/assets/appJs/CommonWorker.js');
			this.worker.addEventListener('message', (e) => this.processData(e));
		}
		let url = this.contextPath + this.apiPath + "chartData" ;
		url = url + "?chartCall=Revenue";
		window.loadCounter++;
		this.worker.postMessage({
			"method" : "GET" ,
			"url" : url ,
			"payload" : null
		});
	}
  processData(e){
  debugger;
		if (e.data.length > 0) {
			this.data = e.data;
        // Themes begin
      am4core.useTheme(am4themes_animated);
        // Themes end
        // Create chart instance
      var chart = am4core.create("beneficiarychart", am4charts.PieChart);

      // Add data
      this.data.forEach((v, i, ar) => {
      debugger;
				let moduleData = {};
				moduleData.litres = v.name;
				moduleData.country = v.value;
        // random color
        moduleData.color = am4core.color("#" + Math.floor(Math.random() * 16777215).toString(16));
				chart.data.push(moduleData);
			});
     // Add and configure Series
      var pieSeries = chart.series.push(new am4charts.PieSeries());
      pieSeries.dataFields.value = "litres";
      pieSeries.dataFields.category = "country";
      
      // Let's cut a hole in our Pie chart the size of 30% the radius
      chart.innerRadius = am4core.percent(30);
      
      // Put a thick white border around each Slice
      pieSeries.slices.template.stroke = am4core.color("#000");
      pieSeries.slices.template.strokeWidth = 2;
      pieSeries.slices.template.strokeOpacity = 1;
      pieSeries.slices.template
        // change the cursor on hover to make it apparent the object can be interacted with
        .cursorOverStyle = [
          {
            "property": "cursor",
            "value": "pointer"
          }
        ];
        pieSeries.alignLabels = false;
        pieSeries.labels.template.bent = true;
        pieSeries.labels.template.radius = 3;
        pieSeries.labels.template.padding(0,0,0,0);
        pieSeries.tooltip.label.fontSize = 11;
      
        pieSeries.ticks.template.disabled = true;
        pieSeries.alignLabels = false;
        pieSeries.labels.template.disabled = true;
        pieSeries.slices.template.propertyFields.fill = "color";
        pieSeries.tooltip.label.fontSize = 12;
          // Create a base filter effect (as if it's not there) for the hover to return to
          var shadow = pieSeries.slices.template.filters.push(new am4core.DropShadowFilter);
          shadow.opacity = 0;
          
          // Create hover state
          var hoverState = pieSeries.slices.template.states.getKey("hover"); // normally we have to create the hover state, in this case it already exists
          
          // Slightly shift the shadow and make it more prominent on hover
          var hoverShadow = hoverState.filters.push(new am4core.DropShadowFilter);
          hoverShadow.opacity = 0.7;
          hoverShadow.blur = 5;
          
          // Add a legend
          chart.legend = new am4charts.Legend();
          chart.legend.position = "right";
          chart.legend.fontSize = 12;
          chart.logo.disabled=true;
    }
  }
}
