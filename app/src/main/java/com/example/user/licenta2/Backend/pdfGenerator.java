package com.example.user.licenta2.Backend;


import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.user.licenta2.CV;
import com.example.user.licenta2.MainActivity;
import com.example.user.licenta2.MyClasses.Communication;
import com.example.user.licenta2.MyClasses.Education;
import com.example.user.licenta2.MyClasses.Experience;
import com.example.user.licenta2.MyClasses.Project;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class pdfGenerator {

    String dest = "/data/user/0/com.example.user.licenta2/pdfs/";
    String src = "/";
    float[] currentLocation;

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

    public pdfGenerator(Context context, String cvName, CV cv) throws IOException {
        //

        String img = "/data/user/0/com.example.user.licenta2/img/img.jpg";
        Document document = null;

        try {
            document = new Document();

            File customPrivateDir = context.getExternalFilesDir("CVs");
            File myPdfFile = new File(customPrivateDir, cvName + ".pdf");
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(myPdfFile));
            document.open();

            float w1 = (float) 0.035 * WIDTH_MAX;
            float h1 = w1;
            float w2 = w1 + 150;
            float h2 = (float) 0.97 * HEIGHT_MAX;
            currentLocation = new float[] {w1, h2};

            drawLine(writer, w1, h1, w2, h2, new BaseColor(0.95f, 0.55f, 0.33f, 0.8f));
            drawLine(writer, w1 + 10, h2 - 22, w2 - 10, h2 - 18, new BaseColor(0.1f, 0.1f, 0.1f, 0.9f));
            drawLine(writer, w1 + 10, h2 - 37, w2 - 10, h2 - 33, new BaseColor(0.1f, 0.1f, 0.1f, 0.9f));


//            /* coord for corners */
//            writeText(writer, "a", WIDTH_MIN, HEIGHT_MIN, FONT_SIZE_NORMAL);
//            writeText(writer, "b", WIDTH_MIN, HEIGHT_MAX, FONT_SIZE_MEDIUM);
//            writeText(writer, "c", WIDTH_MAX, HEIGHT_MIN, FONT_SIZE_BIG);
//            writeText(writer, "d", WIDTH_MAX, HEIGHT_MAX, FONT_SIZE_LARGE);


//            writeLongText(writer, "1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 ");

            fillPdf(writer, document, cv);


        } catch (DocumentException e) {
            Log.d("DEBUG", e.toString());
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            document.close();
        }

    }

    public void fillPdf(PdfWriter writer, Document document, CV cv) throws IOException, DocumentException {

//        String fullname = cv.getFirstName() + " " + cv.getMiddleName() + " " + cv.getLastName();
//        pdf_addName(writer, fullname, 0, 0);

//        String address = cv.getCod_postal() + "\t" + cv.getCity() + "\t" + cv.getCountry();
//        pdf_addAddress(writer, address, 0, 0);

//        pdf_addPhoneNumber(writer, cv.getPhoneNumber(), 0, 0);

//        pdf_addEmail(writer, cv.getEmail(), 0, 0);

        pdf_addSkills(writer, cv.getSkills(), 0, 0);

        pdf_addEducations(writer, cv.getEducation(), 0, 0);

        pdf_addExperiences(writer, cv.getExperiences(), 0, 0);

        pdf_addCommunication(writer, cv.getCommunications(), 0, 0);

        pdf_AddProjects(writer, document, cv.getProjects(), 0, 0);
    }

    private void pdf_addName(PdfWriter writer, String name, int locX, int locY) throws IOException, DocumentException {
        if(name.length() != 0) {
            currentLocation[0] = WIDTH_MAX / 3 + 10;
            currentLocation[1] -= 20;
            writeText(writer, name, locX, locY, FONT_SIZE_LARGE);
        }
    }

    private void pdf_addAddress(PdfWriter writer, String address, int locX, int locY) throws IOException, DocumentException {
        if(address.length() != 0) {
            currentLocation[0] = WIDTH_MAX / 3;
            currentLocation[1] -= 25;
            writeText(writer, address, currentLocation[0], currentLocation[1], FONT_SIZE_TINY);
        }
    }

    private void pdf_addPhoneNumber(PdfWriter writer, String phoneNumber, int locX, int locY) throws IOException, DocumentException {
        if (phoneNumber.length() != 0) {
            currentLocation[0] = WIDTH_MAX /3;
            currentLocation[1] -= 15;
            writeText(writer, "Telefon: " + phoneNumber, currentLocation[0], currentLocation[1], FONT_SIZE_TINY);
        }
    }

    private void pdf_addEmail(PdfWriter writer, String email, int locX, int locY) throws IOException, DocumentException {
        if(email.length() != 0) {
            currentLocation[0] = WIDTH_MAX / 3;
            currentLocation[1] -= 15;
            writeText(writer, "Email: " + email, currentLocation[0], currentLocation[1], FONT_SIZE_TINY);

        }
    }

    private void pdf_addSkills(PdfWriter writer, ArrayList<Skill> skills, int locX, int locY) throws IOException, DocumentException {

        if(skills.size() > 0)
        {
            currentLocation[0] = WIDTH_MAX / 3;
            currentLocation[1] -= 50;
            writeText(writer, "Aptitudini", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM);

            StringBuilder aptitudini = new StringBuilder("");
            for (Skill idx: skills) {
                aptitudini.append(idx.getNume()).append(", ");
            }
            aptitudini.setLength(aptitudini.length() - 2);

            currentLocation[0] = WIDTH_MAX / 3 + 20;
            currentLocation[1] -= 20;
            writeText(writer, aptitudini.toString(), currentLocation[0], currentLocation[1], FONT_SIZE_TINY);
        }
    }

    private void pdf_addExperiences(PdfWriter writer, ArrayList<Experience> experiences, int locX, int locY) throws IOException, DocumentException {
        if(experiences.size() > 0)
        {
            currentLocation[0] = WIDTH_MAX / 3;
            currentLocation[1] -= 50;
            writeText(writer, "Experienta", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM);

            currentLocation[1] -= 5;
            for(Experience exp: experiences)
            {
                // write name
                currentLocation[0] = WIDTH_MAX / 3 + 20;
                currentLocation[1] -= 20;
                writeText(writer, exp.getName(), currentLocation[0], currentLocation[1], FONT_SIZE_NORMAL);

                // write data_inceput + data_sfarsit
                currentLocation[0] = WIDTH_MAX - 100;
                currentLocation[1] -= 10;
                String data;
                data = exp.getStart_date() + " - " + exp.getEnd_date();

                writeText(writer, data, currentLocation[0], currentLocation[1], FONT_SIZE_SMALL);

                // write pozitia
                currentLocation[0] = WIDTH_MAX / 3 + 25;
                currentLocation[1] -= 5;
                writeText(writer, exp.getPosition(), currentLocation[0], currentLocation[1], FONT_SIZE_TINY);

            }
        }
    }

    private void pdf_addEducations(PdfWriter writer, ArrayList<Education> educations, int locX, int locY) throws IOException, DocumentException {
        if(educations.size() > 0)
        {
            currentLocation[0] = WIDTH_MAX / 3;
            currentLocation[1] -= 50;
            writeText(writer, "Education", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM);

            currentLocation[1] -= 5;
            for(Education education : educations)
            {
                // write name
                currentLocation[0] = WIDTH_MAX / 3 + 20;
                currentLocation[1] -= 20;
                writeText(writer, education.getNume(), currentLocation[0], currentLocation[1], FONT_SIZE_NORMAL);

                currentLocation[0] = WIDTH_MAX - 100;
                writeText(writer, education.getType().toUpperCase(), currentLocation[0], currentLocation[1], FONT_SIZE_TINY);


                // write data_inceput + data_sfarsit
                currentLocation[0] = WIDTH_MAX - 100;
                currentLocation[1] -= 10;
                String data;
                if(education.getData_sfarsit().length() == 0)
                    data = education.getData_inceput() + " - Prezent ";
                else
                    data = education.getData_inceput() + " - " + education.getData_sfarsit();

                writeText(writer, data, currentLocation[0], currentLocation[1], FONT_SIZE_SMALL);

                // write specializare
                currentLocation[0] = WIDTH_MAX / 3 + 25;
                currentLocation[1] -= 5;
                writeText(writer, education.getSpecializare(), currentLocation[0], currentLocation[1], FONT_SIZE_TINY);

            }
        }
    }

    private void pdf_addCommunication(PdfWriter writer, ArrayList<Communication> communications, int locX, int locY) throws IOException, DocumentException {
        if(communications.size() > 0)
        {

            currentLocation[0] = WIDTH_MAX / 3;
            currentLocation[1] -= 50;
            writeText(writer, "Communication", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM);

            currentLocation[1] -= 5;
            for(Communication communication : communications)
            {
                // write name
                currentLocation[0] = WIDTH_MAX / 3 + 20;
                currentLocation[1] -= 20;
                writeText(writer, communication.getName(), currentLocation[0], currentLocation[1], FONT_SIZE_SMALL);

                // write level
                currentLocation[0] = WIDTH_MAX - 100;
                writeText(writer, "Nivel " + communication.getLevel(), currentLocation[0], currentLocation[1], FONT_SIZE_NORMAL);
            }
        }
    }

    private void pdf_AddProjects(PdfWriter writer, Document document, ArrayList<Project> projects, int locX, int locY) throws IOException, DocumentException {
        if(projects.size() > 0)
        {
            currentLocation[0] = WIDTH_MAX / 3;
            currentLocation[1] -= 50;
            writeText(writer, "Proiecte", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM);

            currentLocation[1] -= 5;
            for(Project proiect : projects)
            {
                // write name
                currentLocation[0] = WIDTH_MAX / 3 + 20;
                currentLocation[1] -= 20;
                writeText(writer, proiect.getName(), currentLocation[0], currentLocation[1], FONT_SIZE_NORMAL);

                // write descriere
                currentLocation[0] = WIDTH_MAX / 3 + 25;
                currentLocation[1] -= 10;
//                    writeText(writer, proiect.getDescription(), currentLocation[0], currentLocation[1], FONT_SIZE_TINY);
                writeLongText(writer, document, proiect.getDescription(), 500, (float)(currentLocation[0]/1.25), 0, FONT_SIZE_TINY);
                int lines = proiect.getDescription().length() / CHARACTERS_TINY_PER_LINE + 1;
//                    Log.d("MyDebug", "currentLocation[0](WIDTH): " + currentLocation[0] + " --- currentLocation[1](HEIGHT): " + currentLocation[1]);
//                    currentLocation[0] -= lines * 10;
            }
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
