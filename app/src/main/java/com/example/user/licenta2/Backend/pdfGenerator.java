package com.example.user.licenta2.Backend;


import android.util.Log;

import com.example.user.licenta2.CV;
import com.example.user.licenta2.MyClasses.Skill;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;


public class pdfGenerator {

    String dest = "/data/user/0/com.example.user.licenta2/pdfs/";
    String src = "/";

    private final int WIDTH_MAX = 580;
    private final int HEIGHT_MAX = 820;
    private final int WIDTH_MIN = 3;
    private final int HEIGHT_MIN = 3;
    private final int FONT_SIZE_TINY = 8;
    private final int FONT_SIZE_SMALL = 10;
    private final int FONT_SIZE_NORMAL = 12;
    private final int FONT_SIZE_MEDIUM = 16;
    private final int FONT_SIZE_BIG = 18;
    private final int FONT_SIZE_LARGE = 20;
    private final int FONT_SIZE_HUGE = 22;
    private final int LINE_LENGTH = 370;
    private final int CHARACTERS_TINY_PER_LINE = 106;

    // Define some FONTS
    public static final Font RED_NORMAL = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
    public static final Font BLUE_BOLD = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLUE);
    public static final Font GREEN_ITALIC = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.GREEN);

    public pdfGenerator(String cvName, CV cv) throws IOException {
        dest = "/data/user/0/com.example.user.licenta2/pdfs/" + cvName;
        String img = "/data/user/0/com.example.user.licenta2/img/img.jpg";

        try {

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();

            float w1 = (float) 0.035 * WIDTH_MAX;
            float h1 = w1;
            float w2 = w1 + 150;
            float h2 = (float) 0.97 * HEIGHT_MAX;
            float[] currentLocation = new float[] {w1, h2};

            drawLine(writer, w1, h1, w2, h2, new BaseColor(0.95f, 0.55f, 0.33f, 0.8f));
            drawLine(writer, w1 + 10, h2 - 22, w2 - 10, h2 - 18, new BaseColor(0.1f, 0.1f, 0.1f, 0.9f));
            drawLine(writer, w1 + 10, h2 - 37, w2 - 10, h2 - 33, new BaseColor(0.1f, 0.1f, 0.1f, 0.9f));
//            w2 += 20;

            /* coord for corners */
            writeText(writer, "a", WIDTH_MIN, HEIGHT_MIN, FONT_SIZE_NORMAL);
            writeText(writer, "b", WIDTH_MIN, HEIGHT_MAX, FONT_SIZE_MEDIUM);
            writeText(writer, "c", WIDTH_MAX, HEIGHT_MIN, FONT_SIZE_BIG);
            writeText(writer, "d", WIDTH_MAX, HEIGHT_MAX, FONT_SIZE_LARGE);


            // Add full-name to .pdf
            String fullname = cv.getFirstName() + " " + cv.getMiddleName() + " " + cv.getLastName();
            if(fullname.length() != 0) {
                currentLocation[0] = WIDTH_MAX / 3 + 10;
                currentLocation[1] -= 20;
                writeText(writer, fullname, currentLocation[0], currentLocation[1], FONT_SIZE_LARGE);
            }

            // Add address to .pdf
            String address = cv.getCod_postal() + "\t" + cv.getCity() + "\t" + cv.getCountry();
            if(address.length() != 0) {
                currentLocation[0] = WIDTH_MAX / 3;
                currentLocation[1] -= 25;
                writeText(writer, address, currentLocation[0], currentLocation[1], FONT_SIZE_TINY);
            }

            // Add phoneNumber to .pdf
            if (cv.getPhoneNumber().length() != 0) {
                currentLocation[0] = WIDTH_MAX /3;
                currentLocation[1] -= 15;
                writeText(writer, "Telefon: " + cv.getPhoneNumber(), currentLocation[0], currentLocation[1], FONT_SIZE_TINY);
            }

            // Add email to .pdf
            if(cv.getEmail().length() != 0){
                currentLocation[0] = WIDTH_MAX / 3;
                currentLocation[1] -= 15;
                writeText(writer, "Email: " + cv.getEmail(), currentLocation[0], currentLocation[1], FONT_SIZE_TINY);

            }

            // Add <Skills> to .pdf
            if(cv.getSkills().size() > 0)
            {
                currentLocation[0] = WIDTH_MAX / 3;
                currentLocation[1] -= 50;
                writeText(writer, "Aptitudini", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM);

                StringBuilder aptitudini = new StringBuilder("");
                for (Skill idx:cv.getSkills()) {
                    aptitudini.append(idx.getNume()).append(", ");
                }
                aptitudini.setLength(aptitudini.length() - 2);

                currentLocation[0] = WIDTH_MAX / 3 + 20;
                currentLocation[1] -= 20;
                writeText(writer, aptitudini.toString(), currentLocation[0], currentLocation[1], FONT_SIZE_TINY);
            }

//            // Add <Experience> to .pdf
//            if(cv.getExperiences().size() > 0)
//            {
//                currentLocation[0] = WIDTH_MAX / 3;
//                currentLocation[1] -= 50;
//                writeText(writer, "Experienta", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM);
//
//                currentLocation[1] -= 5;
//                for(Experience exp: cv.getExperiences())
//                {
//                    // write name
//                    currentLocation[0] = WIDTH_MAX / 3 + 20;
//                    currentLocation[1] -= 20;
//                    writeText(writer, exp.getName(), currentLocation[0], currentLocation[1], FONT_SIZE_NORMAL);
//
//                    // write data_inceput + data_sfarsit
//                    currentLocation[0] = WIDTH_MAX - 100;
//                    currentLocation[1] -= 10;
//                    String data;
////                    if(exp.getEnd_date().length() < 1)
////                        data = exp.getStart_date() + " - Prezent";
////                    else
//                    data = exp.getStart_date() + " - " + exp.getEnd_date();
//
//                    writeText(writer, data, currentLocation[0], currentLocation[1], FONT_SIZE_NORMAL);
//
//                    // write pozitia
//                    currentLocation[0] = WIDTH_MAX / 3 + 25;
//                    currentLocation[1] -= 5;
//                    writeText(writer, exp.getPosition(), currentLocation[0], currentLocation[1], FONT_SIZE_TINY);
//
//                }
//            }
//
//            // Add <Education> to .pdf
//            if(cv.getEducation().size() > 0)
//            {
//                currentLocation[0] = WIDTH_MAX / 3;
//                currentLocation[1] -= 50;
//                writeText(writer, "Education", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM);
//
//                currentLocation[1] -= 5;
//                for(Education studiu : cv.getEducation())
//                {
//                    // write name
//                    currentLocation[0] = WIDTH_MAX / 3 + 20;
//                    currentLocation[1] -= 20;
//                    writeText(writer, studiu.getNume(), currentLocation[0], currentLocation[1], FONT_SIZE_NORMAL);
//
//                    currentLocation[0] = WIDTH_MAX - 100;
//                    writeText(writer, studiu.getType().toUpperCase(), currentLocation[0], currentLocation[1], FONT_SIZE_TINY);
//
//
//                    // write data_inceput + data_sfarsit
//                    currentLocation[0] = WIDTH_MAX - 100;
//                    currentLocation[1] -= 10;
//                    String data;
//                    if(studiu.getData_sfarsit().length() == 0)
//                        data = studiu.getData_inceput() + " - Prezent";
//                    else
//                        data = studiu.getData_inceput() + " - " + studiu.getData_sfarsit();
//
//                    writeText(writer, data, currentLocation[0], currentLocation[1], FONT_SIZE_SMALL);
//
//                    // write specializare
//                    currentLocation[0] = WIDTH_MAX / 3 + 25;
//                    currentLocation[1] -= 5;
//                    writeText(writer, studiu.getSpecializare(), currentLocation[0], currentLocation[1], FONT_SIZE_TINY);
//
//                }
//            }
//
//            // Add <Proiecte> to .pdf
//            if(cv.getProjects().size() > 0)
//            {
//                currentLocation[0] = WIDTH_MAX / 3;
//                currentLocation[1] -= 50;
//                writeText(writer, "Proiecte", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM);
//
//                currentLocation[1] -= 5;
//                for(Project proiect : cv.getProjects())
//                {
//                    // write name
//                    currentLocation[0] = WIDTH_MAX / 3 + 20;
//                    currentLocation[1] -= 20;
//                    writeText(writer, proiect.getName(), currentLocation[0], currentLocation[1], FONT_SIZE_NORMAL);
//
//                    // write descriere
//                    currentLocation[0] = WIDTH_MAX / 3 + 25;
//                    currentLocation[1] -= 10;
////                    writeText(writer, proiect.getDescription(), currentLocation[0], currentLocation[1], FONT_SIZE_TINY);
//                    writeLongText(writer, document, proiect.getDescription(), 500, (float)(currentLocation[0]/1.25), 0, FONT_SIZE_TINY);
//                    int lines = proiect.getDescription().length() / CHARACTERS_TINY_PER_LINE + 1;
//                    Log.d("MyDebug", "currentLocation[0](WIDTH): " + currentLocation[0] + " --- currentLocation[1](HEIGHT): " + currentLocation[1]);
////                    currentLocation[0] -= lines * 10;
//                }
//            }
//
//
//            // Add <Communication> to .pdf
//            if(cv.getCommunications().size() > 0)
//            {
//                currentLocation[0] = WIDTH_MAX / 3;
//                currentLocation[1] -= 50;
//                writeText(writer, "Communication", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM);
//
//                currentLocation[1] -= 5;
//                for(Communication communication : cv.getCommunications())
//                {
//                    // write name
//                    currentLocation[0] = WIDTH_MAX / 3 + 20;
//                    currentLocation[1] -= 20;
//                    writeText(writer, communication.getName(), currentLocation[0], currentLocation[1], FONT_SIZE_NORMAL);
//
//                    // write level
//                    currentLocation[0] = WIDTH_MAX - 100;
//                    writeText(writer, "Nivel " + communication.getLevel(), currentLocation[0], currentLocation[1], FONT_SIZE_NORMAL);
//                }
//            }

//            writeLongText(writer, "1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 ");
            document.close();
        } catch (DocumentException e) {
            Log.d("DEBUG", e.toString());
            e.printStackTrace();
        }

    }

    private void drawLine(PdfWriter writer, float width_1, float height_1, float width_2, float height_2, BaseColor color) {
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = new Rectangle(width_1, height_1, width_2, height_2);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(0);
        rect.setBackgroundColor(color);

        canvas.rectangle(rect);
    }

    private void writeText(PdfWriter writer, String text, float width, float height, int fontSize) throws IOException, DocumentException {
        PdfContentByte cb = writer.getDirectContent();
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        cb.saveState();

        cb.beginText();
        cb.moveText(width, height);
        cb.setFontAndSize(bf, fontSize);
        cb.showText(text);

        cb.endText();
        cb.restoreState();
    }

    private void writeLongText(PdfWriter writer, Document doc, String text, float paddingTop, float paddingLeft, float paddingRight, int fontSize) throws DocumentException {
        writer.setSpaceCharRatio(PdfWriter.NO_SPACE_CHAR_RATIO);
        Paragraph paragraph = new Paragraph();
        paragraph.setPaddingTop(paddingTop);
        paragraph.setIndentationLeft(paddingLeft);
        paragraph.setIndentationRight(paddingRight);
        paragraph.getFont().setSize(fontSize);
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraph.add(text);

        doc.add(paragraph);
    }

    private void putImage(Document document, PdfWriter writer, String IMAGE) throws IOException, DocumentException {
//        Image image = Image.getInstance(img);
//        image.scaleToFit(400, 700);
//        document.add(image);
        PdfContentByte canvas = writer.getDirectContentUnder();
        Image image = Image.getInstance(IMAGE);
        image.scaleAbsolute(PageSize.A4.rotate());
        image.setAbsolutePosition(0, 0);
        canvas.saveState();
        PdfGState state = new PdfGState();
        state.setFillOpacity(0.6f);
        canvas.setGState(state);
        canvas.addImage(image);
        canvas.restoreState();
    }
}
