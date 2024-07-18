/**
 * 
 */
package com.aashdit.prod.cmc.utils;

import java.text.SimpleDateFormat;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Tapan K.
 * @date 31/08/2017
 */
public class PageFooterTextWithPageNo extends PdfPageEventHelper 
{
	PdfTemplate total;
    Font ffont = new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC);
    Font totalfont = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
    String printDateTimeText = "Generated On - " + ApplicationDateUtils.getStringTodayAsDDMMYYYY() + ",  at - " + ApplicationDateUtils.getStringNowAsHrMiAm();

    /** * Creates the PdfTemplate that will hold the total number of pages. */
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 20);
    }
    
    /** * Adds a Footer to every page */
    public void onEndPage(PdfWriter writer, Document document) 
    {
        PdfPTable table = new PdfPTable(3);
        try 
        {
            table.setWidths(new int[]{24, 24, 2});
            table.getDefaultCell().setFixedHeight(30);
            table.getDefaultCell().setBorder(Rectangle.TOP);

            PdfPCell cell = new PdfPCell();
            cell.setBorder(0);
            cell.setBorderWidthTop(0.2f);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setPhrase(new Phrase(printDateTimeText, ffont));
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(0);
            cell.setBorderWidthTop(0.2f);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPhrase(new Phrase(String.format("Page # %d of", writer.getPageNumber()), ffont));
            table.addCell(cell);

            cell = new PdfPCell(Image.getInstance(total));
            cell.setBorder(0);
            cell.setBorderWidthTop(0.2f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            table.setTotalWidth(document.getPageSize().getWidth()-document.leftMargin()-document.rightMargin());
            table.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin()+1, writer.getDirectContent());
        }
        catch(DocumentException de) 
        {
            throw new ExceptionConverter(de);
        }
    }
        
    /** * Fills out the total number of pages before the document is closed. */
    public void onCloseDocument(PdfWriter writer, Document document) 
    {
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber()-1), totalfont), 3, 7, 0);
	}
}



