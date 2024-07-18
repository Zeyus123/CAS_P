am4core.ready(function() {

  // Themes begin
  am4core.useTheme(am4themes_animated);
  // Themes end
  
  // Create chart instance
  var chart = am4core.create("activitychart", am4charts.XYChart);
  
  // Add data
  chart.data = [
       {
    "Month": "Apr",
    "Planed":3.9,
    "Actual": 3.2
  
      },
{
    "Month": "May",
    "Planed":5.9,
    "Actual": 3.5
   
      },
{
    "Month": "Jun",
    "Planed":6.1,
    "Actual": 4.9
  
      },
{
    "Month": "Jul",
    "Planed":3.9,
    "Actual": 0
    
  },
      {
        "Month": "Aug",
        "Planed":6.1,
        "Actual":0
   
      },
      {
        "Month": "Sep",
        "Planed":3.9,
        "Actual": 0
  
      },
      {
        "Month": "Oct",
        "Planed":3.9,
        "Actual":0
  
      },
      {
        "Month": "Nov",
        "Planed":3.9,
        "Actual":0
  
      },
      {
        "Month": "Dec",
        "Planed":3.9,
        "Actual":0
  
      },
      {
        "Month": "Jan",
        "Planed":3.9,
        "Actual":0
  
      },
      {
        "Month": "Feb",
        "Planed":3.9,
        "Actual":0
  
      },
      {
        "Month": "Mar",
        "Planed":3.9,
        "Actual":0
  
      },
    
    
    
   
  ];
  
// Create axes
  var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
  categoryAxis.renderer.labels.template.horizontalCenter = "right";
  categoryAxis.renderer.labels.template.verticalCenter = "middle";
  categoryAxis.renderer.labels.template.rotation = 0;
  categoryAxis.renderer.minHeight = 0;
  
  categoryAxis.dataFields.category = "Month";
  categoryAxis.renderer.grid.template.location = 0;
  categoryAxis.renderer.minGridDistance = 10;
  categoryAxis.renderer.cellStartLocation = 0.1;
  categoryAxis.renderer.cellEndLocation = 0.6;
  categoryAxis.renderer.labels.template.fontSize = 12

categoryAxis.renderer.labels.template.horizontalCenter = "right";
categoryAxis.renderer.labels.template.verticalCenter = "middle";
categoryAxis.renderer.labels.template.rotation = 270;

  
  categoryAxis.tooltip.label.fontSize = 12;
 
  
  var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
  valueAxis.title.text = "";
  valueAxis.title.fontWeight = 600;

  // Legend
chart.legend = new am4charts.Legend();
chart.legend.useDefaultMarker = true;
var markerTemplate = chart.legend.markers.template;
markerTemplate.width = 15;
markerTemplate.height = 15;
markerTemplate.stroke = am4core.color("#ccc");

  
  // Create series
  var series = chart.series.push(new am4charts.ColumnSeries());
  series.dataFields.valueY = "Planed";
  series.dataFields.categoryX = "Month";
  series.tooltipText = "Planed: [bold]{valueY}[/]";
  series.name = "Planed";
  series.fill = am4core.color("#3c9ea7");
  series.stroke = am4core.color("#3c9ea7");
  //series.stacked = true;
  //series.columns.template.column.cornerRadiusTopLeft = 10;
  //series.columns.template.column.cornerRadiusTopRight = 10;
  //series.columns.template.column.fillOpacity = 0.8;
  
  //series2.stacked = true;
  //series2.columns.template.column.cornerRadiusTopLeft = 10;
  //series2.columns.template.column.cornerRadiusTopRight = 10;
  //series2.columns.template.column.fillOpacity = 0.8;
  
  
  var series3 = chart.series.push(new am4charts.ColumnSeries());
  series3.dataFields.valueY = "Actual";
  series3.dataFields.categoryX = "Month";
  series3.columns.template.width = am4core.percent(50);
  series3.tooltipText = "Actual: [bold]{valueY}[/]";
  series3.name = "Actual";
  series3.fill = am4core.color("#2b5e86");
  series3.stroke = am4core.color("#2b5e86");
  //series3.stacked = true;
  //series3.columns.template.column.cornerRadiusTopLeft = 10;
  //series3.columns.template.column.cornerRadiusTopRight = 10;
  //series3.columns.template.column.fillOpacity = 0.8;
valueAxis.renderer.labels.template.fontSize = 10;
  chart.cursor = new am4charts.XYCursor();
  chart.cursor.lineX.disabled = true;
  chart.cursor.lineY.disabled = true;
  chart.logo.disabled=true;
  chart.colors.list = [
    am4core.color("#008000"),
    am4core.color("#ffa500"),
    am4core.color("#028900"),
    am4core.color("#ffeb3b"),
    am4core.color("#fb5607"),
    am4core.color("#1992ca"),
    am4core.color("#4363d8"),
    am4core.color("#911eb4"),
    am4core.color("#f032e6")

  ];

 }); // end am4core.ready()
  // end am4core.ready