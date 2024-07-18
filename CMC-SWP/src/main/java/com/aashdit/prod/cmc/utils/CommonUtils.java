package com.aashdit.prod.cmc.utils;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class CommonUtils {

    /**
     * Handles binding errors and populates the model with error information.
     *
     * @param bindingResult the result of the binding process
     * @param model         the model to be populated with error information
     * @param redirectUrl   the URL to redirect to in case of errors
     * @return the view name for error display or null if no errors
     */
    public static String handleBindingErrors(BindingResult bindingResult, Model model, String redirectUrl) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();

            // Loop through field errors and collect error messages
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String defaultMessage = fieldError.getDefaultMessage();
                errors.add(defaultMessage);
                log.error("Field error in {}: {}", fieldError.getField(), defaultMessage);
            }


            // Add error information to the model
            model.addAttribute("error_msg", errors);
            model.addAttribute("code", HttpStatus.BAD_REQUEST.value());
            model.addAttribute("url", StringUtils.isEmpty(redirectUrl) ? "/" : redirectUrl);


            // Return the view name for error display
            return "site.400.error";
        }

        // No errors, return null to indicate success
        return null;
    }
}
