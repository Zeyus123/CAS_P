//package com.aashdit.prod.cmc.controller;
//
//
//import java.io.File;
//import java.io.FileInputStream; 
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
//import com.aashdit.prod.cmc.model.District;
//import com.aashdit.prod.cmc.service.CommonService;
//
//import lombok.extern.slf4j.Slf4j;
//
//
//@Slf4j
//@Controller
//public class JasperReportController {
//	
//	@Autowired
//	private CommonService commonService;
//	
//	@Autowired
//	private JasperGenerator jasperGenerator;
//	
//	ResourceBundle rb = ResourceBundle.getBundle("application");
//	
//	@GetMapping("/distListReport")
//	@ResponseBody
//	public ResponseEntity<?> generateAllStaffReport(HttpServletResponse response, @RequestParam(required = false) String reportFormat) {
//    try {
//        List<District> distList = commonService.getAllDistrictNameList();
//        ServiceOutcome<String> outcome = jasperGenerator.listToExcelInJasper(reportFormat, distList, "DistrictListReport.jrxml", "dist_list_report");
//			File checkFolderPath = new File(rb.getString("UPLOAD.JASPER.FILE.PATH"));
//			if (!checkFolderPath.exists()) {
//				checkFolderPath.mkdirs();
//			}
//        if (outcome !=null) {
//            Path reportPath = Paths.get(checkFolderPath + File.separator+ outcome.getData());
//            if (Files.exists(reportPath)) {
//                if ("PDF".equals(reportFormat)) {
//                    response.setHeader("Content-Disposition", "attachment; filename=StaffList.pdf");
//                } else {
//                    response.setHeader("Content-Disposition", "attachment; filename=all_staff_report.xlsx");
//                }
//
//                InputStream inputStream = new FileInputStream(reportPath.toFile());
//                org.apache.commons.io.IOUtils.copy(inputStream, response.getOutputStream());
//                response.flushBuffer();
//                return ResponseEntity.ok().build();
//            } else {
//                log.error("Generated report file does not exist: {}", reportPath);
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to find generated report file");
//            }
//        } else {
//            log.error("Failed to generate report: {}", outcome.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate report: " + outcome.getMessage());
//        }
//    } catch (Exception e) {
//        log.error("Exception occurred in generateAllStaffReport method at StaffReportController", e);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate report: " + e.getMessage());
//    }
//	}
//}
//
//
//
//
//
