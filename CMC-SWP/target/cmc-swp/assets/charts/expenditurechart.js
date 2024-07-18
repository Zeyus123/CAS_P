class expenditurechart {
  constructor(apiPath, contextPath){
    this.apiPath = apiPath;
    this.worker = null;
    this.contextPath = contextPath;
    this.data = null;
    this.context = "STATE";
  }
  doFetch() {
    if (this.worker == null ){
      this.worker = new Worker(this.contextPath + '/assets/appJs/CommonWorker.js');
      this.worker.addEventListener('message', (e) => this.processData(e));
    }
    let url = this.contextPath + this.apiPath + "chartData" ;
    url = url + "?chartCall=Expenditure";
    window.loadCounter++;
    this.worker.postMessage({
      "method" : "GET" ,
      "url" : url ,
      "payload" : null
    });
  }
  processData(e){
    if (e.data.length > 0) {
      this.data = e.data;

      // Themes begin
  am4core.useTheme(am4themes_animated);
  // Themes end
  
  // Create chart instance
  var chart = am4core.create("expenditurechart", am4charts.PieChart);

  this.data.forEach((v, i, ar) => {
      let moduleData = {};
      moduleData.litres = v.name;
      moduleData.country = v.value;
      // random color
      moduleData.color = am4core.color("#" + Math.floor(Math.random() * 16777215).toString(16));
      chart.data.push(moduleData);
    });


  // Set inner radius
  chart.innerRadius = am4core.percent(50);
  
  // Add and configure Series
  var pieSeries = chart.series.push(new am4charts.PieSeries());
  pieSeries.dataFields.value = "litres";
  pieSeries.dataFields.category = "country";
  pieSeries.slices.template.propertyFields.fill = "color";
  pieSeries.slices.template.strokeWidth = 2;
  pieSeries.slices.template.strokeOpacity = 1;
  
  // This creates initial animation
  pieSeries.hiddenState.properties.opacity = 1;
  pieSeries.hiddenState.properties.endAngle = -90;
  pieSeries.hiddenState.properties.startAngle = -90;

  pieSeries.ticks.template.disabled = true;

  pieSeries.labels.template.fill = am4core.color("#ffffff");
  pieSeries.tooltip.label.fontSize = 12;
  // Add a legend
  chart.legend = new am4charts.Legend();
  chart.legend.position = "right";
  chart.legend.fontSize = 12;
  chart.logo.disabled=true;

  

}
}
}


  
  
  
  
  
  
 