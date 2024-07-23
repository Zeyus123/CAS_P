//package com.aashdit.prod.cmc.controller;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.ResourceBundle;
//
//import javax.servlet.ServletContext;
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
//import com.aashdit.prod.cmc.utils.CommonUploadFile;
//
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
//import net.sf.jasperreports.export.SimpleExporterInput;
//import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
//import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
//
//	@Slf4j
//	@Component
//	public class JasperGenerator {
//
//		
//		ResourceBundle rb = ResourceBundle.getBundle("application");
//		
//		@Autowired
//		private ServletContext servletContext;
//		
//		@Autowired
//		private DataSource dataSource;
//
//		
//		public com.aashdit.prod.cmc.framework.core.ServiceOutcome<String> listToExcelInJasper(String reportFormat,List<?> list, String reportFilePath, String reportName) throws IOException, JRException {
//		    ServiceOutcome<String> outcome = new ServiceOutcome<>();
//		    Boolean flag = true;
//		    String fileName = "";
//		    Connection connection = null;
//		    try {
//		        String filePath = servletContext.getRealPath("/WEB-INF/jasperReports/" + reportFilePath);
//		        File file = new File(filePath);
//		        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//		        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
//		        Map<String, Object> parameters = new HashMap<>();
//		        
//		        try {
//						connection = dataSource.getConnection();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					if(connection == null) {
//						connection = getDbaseConnection();
//					}
//
//					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
//					File checkFolderPath = new File(rb.getString("UPLOAD.JASPER.FILE.PATH"));
//					if (!checkFolderPath.exists()) {
//						checkFolderPath.mkdirs();
//					}
//		        if(reportFormat.equals("PDF")) {
//					fileName = CommonUploadFile.addCurrenDateTimeToDocAndRenameItModified(checkFolderPath.getAbsolutePath() + File.separator +reportName, "pdf");
//					JasperExportManager.exportReportToPdfFile(jasperPrint,checkFolderPath.getAbsolutePath() + File.separator+fileName);
//					fileName = new File(checkFolderPath.getAbsolutePath() + File.separator+fileName).getName();
//				}else if(reportFormat.equals("EXCEL")) {
//					fileName = CommonUploadFile.addCurrenDateTimeToDocAndRenameItModified(checkFolderPath.getAbsolutePath() + File.separator +reportName, "xlsx");
//					FileOutputStream fos = new FileOutputStream(checkFolderPath.getAbsolutePath() + File.separator +fileName);
//		            JRXlsxExporter exporter = new JRXlsxExporter();
//		            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//		            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fos));
//		            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
//		            configuration.setOnePagePerSheet(false); // If you want multiple sheets
//		            configuration.setDetectCellType(true); // Detect cell data types
//		            configuration.setCollapseRowSpan(false);
//		            configuration.setIgnorePageMargins(false);
//		            exporter.setConfiguration(configuration);
//		            exporter.exportReport();
//		            System.out.println("Report exported to Excel (XLSX) successfully.");
//					}
//		        closeDataBaseConnection(connection);
//		    } catch (FileNotFoundException e) {
//		        e.printStackTrace();
//		        flag = false;
//		    } catch (JRException e) {
//		        e.printStackTrace();
//		        flag = false;
//		    }
//
//		    outcome.setData(fileName);
//		    outcome.setOutcome(flag);
//		    return outcome;
//			}
//
//
//	    public ServiceOutcome<String> reportForJasper(String reportFormat ,String reportFilePath,String reportName,Map map){
//	    	ServiceOutcome<String> outcome = new ServiceOutcome<>();
//	    	Boolean flag = true;
//	    	String fileName = "";
//	    	Connection connection = null;
//			try {
//				String filePath = servletContext.getRealPath("/WEB-INF/jasperReports/"+reportFilePath);
//				File file = new File(filePath);
//				JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
//				connection = dataSource.getConnection();
//				if(connection == null) {
//					connection = getDbaseConnection();
//				}
//				JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,map,connection);
//				File checkFolderPath = new File(rb.getString("UPLOAD.JASPER.FILE.PATH"));
//				if (!checkFolderPath.exists()) {
//					checkFolderPath.mkdirs();
//				}
//				if(reportFormat.equals("PDF")) {
//					fileName = CommonUploadFile.addCurrenDateTimeToDocAndRenameItModified(checkFolderPath.getAbsolutePath() + File.separator +reportName, ".pdf");
//					JasperExportManager.exportReportToPdfFile(jasperPrint,fileName);
//					 fileName = new File(fileName).getName();
//					
//					closeDataBaseConnection(connection);
//				}else if(reportFormat.equals("EXCEL")) {
//					JRXlsxExporter exporter = new JRXlsxExporter();
//					exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//					fileName=CommonUploadFile.addCurrenDateTimeToDocAndRenameItModified(rb.getString("UPLOAD.JASPER.FILE.PATH")+ File.separator+reportName,".xlsx");
//					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new FileOutputStream(fileName)));
//					SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
//					configuration.setOnePagePerSheet(false); // 
//					configuration.setDetectCellType(true); // 
//					exporter.setConfiguration(configuration);
//					exporter.exportReport();
//					fileName = new File(fileName).getName();
//					closeDataBaseConnection(connection);
//			    }
//			} catch (Exception e) {
//				flag = false;
//				log.error("Exception Occured in JasperReportGenerator at reportForJasper() ==>" + e);
//			} 
//			outcome.setData(fileName);
//			outcome.setOutcome(flag);
//			return outcome;
//	    }
//		
//		public  Connection getDbaseConnection()
//		{
//			Connection connection = null;
//			try {
//				connection = DriverManager.getConnection(rb.getString("db.url"),rb.getString("db.username"),rb.getString("db.password"));
//				if(Optional.ofNullable(connection).isPresent()) {
//					log.info("Database Connection Open For Jasper Report.");
//				}
//			} catch (SQLException e) {
//				log.error("Exception Occured in JasperReportGenerator at getDbaseConnection() ==>"+ e);
//			}
//			return connection;
//		}
//		
//		public void closeDataBaseConnection(Connection connection)
//		{
//			try {
//				if(Optional.ofNullable(connection).isPresent()) {
//					connection.close();
//				}
//			}catch(Exception e) {
//				log.error("Exception Occured in JasperReportGenerator at closeDataBaseConnection() ==>"+ e);
//			}
//		}
//	}
//
//
