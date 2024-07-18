package com.aashdit.prod.cmc.utils;

import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;


/**
 * Implementation of the detector for Adobe PDF document.
 */
public class PdfDocumentDetectorImpl {

    /**
     * LOGGER
     */
    private static final Logger log = LoggerFactory.getLogger(PdfDocumentDetectorImpl.class);

    public boolean isSafe(InputStream is) {
        boolean safeState = false;
        try {
            if (is != null) {
                // Load stream in PDF parser
                // If the stream is not a PDF then exception will be throwed
                // here and safe state will be set to FALSE
                PdfReader reader = new PdfReader(is);
                // Check 1:
                // Detect if the document contains any JavaScript code
                String jsCode = reader.getJavaScript();
                if (jsCode == null) {
                    safeState = true;
                    // OK no JS code then when pass to check 2:
                    // Detect if the document has any embedded files

                    PdfDictionary root = reader.getCatalog();
                    PdfDictionary names = root.getAsDict(PdfName.NAMES);
                    PdfArray namesArray = null;
                    try {
                        if (names != null) {
                            PdfDictionary embeddedFiles = names.getAsDict(PdfName.EMBEDDEDFILES);
                            namesArray = embeddedFiles.getAsArray(PdfName.NAMES);

                            // It is Safe from JS and embedded Files
                            safeState = safeState && ((namesArray == null) || namesArray.isEmpty());
                        }
                    } catch (Exception ex) {
                        // At times the names is an empty Dictionary instead of
                        // null;
                        // In this case the embeddedFiles comes as null and the
                        // embeddedFiles.getAsArray(PdfName.NAMES) will throw a
                        // nullpointerexception;
                        log.warn("embeddedFiles in PDF File threw and exception -> " + ex.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            safeState = false;
            log.warn("Error during Pdf file analysis !", e);
        }
        log.debug("isSafeFile=======================" + safeState);
        return safeState;
    }

}