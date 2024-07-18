package com.aashdit.prod.cmc.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MessageCodeMapper {
    private static final Map<String, String> ERROR_CODE_MAP = new HashMap<>();
    private static final Map<String, String> SUCCESS_CODE_MAP = new HashMap<>();

    static {
        // Error code mapping for master data verification
        ERROR_CODE_MAP.put("BG001", "Given blood group detail is invalid.");
        ERROR_CODE_MAP.put("GD001", "Given gender detail is invalid.");
        ERROR_CODE_MAP.put("RELATION001", "Given relation detail is invalid.");
        ERROR_CODE_MAP.put("CGT001", "Given category detail is invalid.");
        ERROR_CODE_MAP.put("BNK001", "Given bank detail is invalid.");
        ERROR_CODE_MAP.put("BRCH001", "Given bank branch detail is invalid.");
        ERROR_CODE_MAP.put("ADDRESTYPE001", "Given bank branch detail is invalid.");
        ERROR_CODE_MAP.put("DOCMSTTYPE001", "Given document type detail is invalid.");


        ERROR_CODE_MAP.put("STD001", "No student information is available.");
        ERROR_CODE_MAP.put("STD_ENROLL_001", "student enrollment details  are not  available.");
        ERROR_CODE_MAP.put("STD002", "Unable to get the Student basic details .");
        ERROR_CODE_MAP.put("STD_PROF001", "Student profile picture must not be empty.");
        ERROR_CODE_MAP.put("STD_DISB001", "Student disability certificate document must not be empty.");
        ERROR_CODE_MAP.put("DEF001", "Something went wrong, try after some time .");
        ERROR_CODE_MAP.put("VALD001", "Unable to validation detail, try after sometimes.");
        ERROR_CODE_MAP.put("DUPL001", "Duplicate data present in student basic details. ");
        ERROR_CODE_MAP.put("DUPLADDRESS001", "Student duplicate address are not allowed.");
        ERROR_CODE_MAP.put("DUPLDOCUMENT001", "Student duplicate documents are  not allowed.");
        ERROR_CODE_MAP.put("STD_DOC001", "Student profile picture must not be empty.");

        ERROR_CODE_MAP.put("STD_DOC002", "Unable to upload student document ,try after sometimes .");
        ERROR_CODE_MAP.put("STD_DOC003", "Student document attachment must not be empty.");

        ERROR_CODE_MAP.put("STD_ROLL003", "Student roll no is not unique.");
        ERROR_CODE_MAP.put("STD_REGNO003", "Student registration no is not unique.");


        ERROR_CODE_MAP.put("DUE_001", "Given due clearance detail is invalid.");
        ERROR_CODE_MAP.put("STD_EXIT_REASON_001", "Given exit reason detail is invalid.");
        ERROR_CODE_MAP.put("STD_EXIT_DATE_001", "Given exit date detail is invalid.");

        ERROR_CODE_MAP.put("STD_EXIT_DATE_002", "Exit date must be after admission date and before current date");


        ERROR_CODE_MAP.put("STD_EXIT_ERROR_001", "Student is invalid for proceeding with the exit process..");
        ERROR_CODE_MAP.put("STD_EXIT_ERROR_002", "The student's due process is not yet complete. Please complete the due clearance process to proceed with the exit");


        ERROR_CODE_MAP.put("COLLEGE001", "College detail not found.");
        ERROR_CODE_MAP.put("PROGRAM001", "Program detail not found.");
        ERROR_CODE_MAP.put("STREAM001", "Stream detail not found.");
        ERROR_CODE_MAP.put("BATCH001", "Student roll no is not unique.");
        ERROR_CODE_MAP.put("SEMESTER001", "Semester detail not found.");
        ERROR_CODE_MAP.put("SEC001", "Section detail not found.");
        ERROR_CODE_MAP.put("ADMISSION_TYPE001", "Admission type detail not found.");
        ERROR_CODE_MAP.put("ENTRANCE_EXAM_TYPE001", "Entrance exam type detail not found.");
        ERROR_CODE_MAP.put("SEAT_CATEGORY001", "Seat category detail not found.");
        //Added for GIA module
        ERROR_CODE_MAP.put("GIA_INVALID_APPLICABLETILL_DATE", "Invalid applicable till date has been selected by user.");


        //TimeTable Configuration Error Codes


        ERROR_CODE_MAP.put("TT_CONFIG_CODE_001", "Time Table Configuration Code is already present");
        ERROR_CODE_MAP.put("TT_CONFIG_CODE_002", "TimeTable is already published. You can not modify it.");
        ERROR_CODE_MAP.put("WEEK_001", "Weekday detail not found.");
        ERROR_CODE_MAP.put("PAPER_001", "Paper detail not found.");
        ERROR_CODE_MAP.put("PERIOD_001", "Period detail not found.");
        ERROR_CODE_MAP.put("FACULTY_001", "Faculty detail not found.");
        ERROR_CODE_MAP.put("FACILITY_001", "Facility detail not found.");
        ERROR_CODE_MAP.put("TT_MST_001", "Timetable master detail not found.");
        ERROR_CODE_MAP.put("FACILITY_002", "Given facility is already assigned to the given period. Please select another facility.");
        ERROR_CODE_MAP.put("FACULTY_PAPER_001", "Faculty paper map details not found.");
        ERROR_CODE_MAP.put("FACULTY_PAPER_002", "Unable to assign faculty to the given paper. Please try after some time.");




    }

    static {

        SUCCESS_CODE_MAP.put("STD_SUCC_DEF_001", "Student details processed successfully.");
        SUCCESS_CODE_MAP.put("STD_DOC_MST_SUCC_001", "Document type details saved successfully.");
        SUCCESS_CODE_MAP.put("STD_DOC_MST_SUCC_002", "Document type details updated successfully.");

        SUCCESS_CODE_MAP.put("DOC_MST_ISACT_001", "Document type details activate successfully.");
        SUCCESS_CODE_MAP.put("DOC_MST_ISACT_002", "Document type details inactivate successfully.");

        SUCCESS_CODE_MAP.put("DOC_MST_ISMAND_001", "Document type details is mandetory.");
        SUCCESS_CODE_MAP.put("DOC_MST_ISMAND_002", "Document type details is not mandetory .");

        SUCCESS_CODE_MAP.put("STD_SUCC_001", "Student basic details saved successfully.");
        SUCCESS_CODE_MAP.put("STD_SUCC_002", "Student basic details updated successfully.");
        SUCCESS_CODE_MAP.put("DEFT_SUCC_001", "Details Processed successfully .");


        SUCCESS_CODE_MAP.put("STD_ENROLL_SUCC_001", "Student enrollment details saved successfully.");
        SUCCESS_CODE_MAP.put("STD_ENROLL_SUCC_002", "Student enrollment details updated successfully.");

        SUCCESS_CODE_MAP.put("STD_FAMILY_SUCC_001", "Student family details saved successfully.");
        SUCCESS_CODE_MAP.put("STD_FAMILY_SUCC_002", "Student family details updated successfully.");


        SUCCESS_CODE_MAP.put("STD_ADDRESS_SUCC_001", "Student address details saved successfully.");
        SUCCESS_CODE_MAP.put("STD_ADDRESS_SUCC_002", "Student address details updated successfully.");

        SUCCESS_CODE_MAP.put("STD_DOCUMENT_SUCC_001", "Student document details saved successfully.");
        SUCCESS_CODE_MAP.put("STD_DOCUMENT_SUCC_002", "Student document details updated successfully.");

        SUCCESS_CODE_MAP.put("STD_ADM_TYPE_MST_SUCC_001", "Student admission type details saved successfully.");
        SUCCESS_CODE_MAP.put("STD_ADM_TYPE_MST_SUCC_002", "Student admission type details updated successfully.");

        SUCCESS_CODE_MAP.put("STD_ADM_TYPE_MST_ACT_001", "Student admission type details activated successfully.");
        SUCCESS_CODE_MAP.put("STD_ADM_TYPE_MST_ACT_002", "Student admission type details inactivated successfully.");

        SUCCESS_CODE_MAP.put("STD_ADM_TYPE_MST_DOC_001", "  Further info is not required for this admission type.");
        SUCCESS_CODE_MAP.put("STD_ADM_TYPE_MST_DOC_002", "  Further info is  required for this admission type.");

        SUCCESS_CODE_MAP.put("STD_ENROLL_TYPE_MST_DOC_001", "  Student enrollment document details saved successfully.");
        SUCCESS_CODE_MAP.put("STD_ENROLL_TYPE_MST_DOC_002", " Student enrollment document details updated successfully.");

        SUCCESS_CODE_MAP.put("STD_SUBJECT_DETAILS_001", "  Student subject  details saved successfully.");
        SUCCESS_CODE_MAP.put("STD_SUBJECT_DETAILS_002", " Student subject  details updated successfully.");

        SUCCESS_CODE_MAP.put("STD_EXIT_SUC_001", "  Student exit process completed successfully.");

        SUCCESS_CODE_MAP.put("TT_CONFIG_SUC_001", "Time Table Configuration details saved successfully.");
        SUCCESS_CODE_MAP.put("TT_CONFIG_SUC_002", "Time Table Configuration details updated successfully.");

        SUCCESS_CODE_MAP.put("TT_CONFIG_SUC_003", "Time Table  published successfully.");

    }


    public static String getErrorMessage(String errorCode) {
        return Optional.ofNullable(ERROR_CODE_MAP.getOrDefault(errorCode, "DEF001"))
                .orElse("DEF001");
    }

    public static String getErrorCode(String errorMessage) {
        return ERROR_CODE_MAP.entrySet().stream()
                .filter(entry -> entry.getValue().equals(errorMessage))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("DEF001");
    }

    public static String getSuccessMessage(String successCode) {
        return Optional.ofNullable(SUCCESS_CODE_MAP.getOrDefault(successCode, "STD_SUCC_DEF_001"))
                .orElse("STD_SUCC_DEF_001");
    }

}


