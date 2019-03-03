package com.example.user.licenta2.Backend;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
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
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;
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
    private final short TEMPLATE_1 = 1;
    private final short TEMPLATE_2 = 2;
    private final short CHAR_SIZE = 5;
    private final short CHARS_PER_LINE = 70;

    private float WIDTH, HEIGHT;

    private Document document = null;

    // Define some FONTS
    public static final Font RED_NORMAL = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
    public static final Font BLUE_BOLD = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLUE);
    public static final Font GREEN_ITALIC = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.GREEN);

    private int TOTAL_LINES = 0;
    private int LINE_NEWPAGE = 50;

    public pdfGenerator(Context context, String cvName, CV cv) throws IOException {
        //

        String img = "/data/user/0/com.example.user.licenta2/img/img.jpg";

        try {
            document = new Document();

            File customPrivateDir = context.getExternalFilesDir("CVs2");
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CVs3";
//            File dir = new File(path);

//            if(!dir.exists()) dir.mkdirs();

            File myPdfFile = new File(customPrivateDir, cvName + ".pdf");
            Log.d("MyDebug", "pdfGenerator: " + myPdfFile.getPath());
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(myPdfFile));
            Rectangle rectangle = writer.getPageSize();
            WIDTH = rectangle.getWidth();
            HEIGHT = rectangle.getHeight();
            document.open();



//            /* coord for corners */
//            writeText(writer, "a", WIDTH_MIN, HEIGHT_MIN, FONT_SIZE_NORMAL);
//            writeText(writer, "b", WIDTH_MIN, HEIGHT_MAX, FONT_SIZE_MEDIUM);
//            writeText(writer, "c", WIDTH_MAX, HEIGHT_MIN, FONT_SIZE_BIG);
//            writeText(writer, "d", WIDTH_MAX, HEIGHT_MAX, FONT_SIZE_LARGE);


//            writeLongText(writer, "1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890 ");

            fillPdf(TEMPLATE_2, writer, document, cv);


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

    public void fillPdf(short template, PdfWriter writer, Document document, CV cv) throws IOException, DocumentException {

        float w1 = (float)0.0;
        float h1 = (float)0.0;
        float w2 = (float)0.0;
        float h2 = (float)0.0;
        currentLocation = new float[] {w1, h2};

//        String temp = "AAAAAAAAAA__________AAAAAAAAAA__________AAAAAAAAAA__________AAAAAAAAAA__________AAAAAAAAAA__________";
//        Log.d("DEBUG", "HEIGHT: " + HEIGHT);
//        writeLongText(writer, temp, 14, 0, FONT_SIZE_SMALL, 200, 0, 0);
//
//        String temp2 = "AAAAAAAAAA__________AAAAAAAAAA__________AAAAAAAAAA__________AAAAAAAAAA__________AAAAAAAAAA__________";
//        writeLongText(writer, temp2, 0, 300, FONT_SIZE_SMALL, 0, 0, 0);
//
//        String temp3 = "AAAAAAAAAA__________AAAAAAAAAA__________AAAAAAAAAA__________AAAAAAAAAA__________AAAAAAAAAA__________";
//        writeLongText(writer, temp3, 300, 0, FONT_SIZE_SMALL, -50,0,0);

//        writeText(writer, "B", 100, 100, FONT_SIZE_NORMAL);

        template = 1;
        switch(template) {
            case TEMPLATE_1:
                w1 = (float) 0.035 * WIDTH_MAX;
                h1 = w1;
                w2 = w1 + 150;
                h2 = (float) 0.97 * HEIGHT_MAX;
                currentLocation[0] = w1;
                currentLocation[1] = h2;

                currentLocation[0] = (float) 0.035 * WIDTH_MAX;
                currentLocation[1] = (float) 0.97 * HEIGHT_MAX;

                drawLine(writer, w1, h1, w2, h2, new BaseColor(0.95f, 0.55f, 0.33f, 0.8f));
                drawLine(writer, w1 + 10, h2 - 22, w2 - 10, h2 - 18, new BaseColor(0.1f, 0.1f, 0.1f, 0.9f));
                drawLine(writer, w1 + 10, h2 - 37, w2 - 10, h2 - 33, new BaseColor(0.1f, 0.1f, 0.1f, 0.9f));

                currentLocation[0] = WIDTH_MAX / 3;
                currentLocation[1] -= 10;
                String fullname_template2 = "Abcdefghijkl Mnopqrstuv Wzxyz0wwww5"; //cv.getFirstName() + " " + cv.getMiddleName() + " " + cv.getLastName();
                pdf_addName(writer, fullname_template2, 150, 0, 50, 0);
//                (PdfWriter writer, String name, float marginLeft, float marginRight, float spaceAfter, float spaceBefore)

                currentLocation[0] = WIDTH_MAX - 160;
                currentLocation[1] += 10;
                String address = "700669, Iasi, Romania"; //cv.getCod_postal() + "\t" + cv.getCity() + "\t" + cv.getCountry();
                pdf_addAddress(writer, address, 150, 0, 0, 0);

                currentLocation[1] -= 10;
                pdf_addEmail(writer, "tanasapetrut@hotmail.com", 150, 0, 0, 0);

                currentLocation[1] -= 10;
                pdf_addPhoneNumber(writer, "0758990801", 150, 0, 30, 0);


                // Skill Park
                currentLocation[0] = WIDTH_MAX / 3;
                currentLocation[1] -= 50;
//                writeText(writer, "Skills", currentLocation[0], currentLocation[1], FONT_SIZE_BIG, BaseColor.BLACK, BaseFont.COURIER_BOLD);


                currentLocation[0] += 20;
                currentLocation[1] -= 30;
                pdf_addSkills(writer, cv.getSkills(), 150, 0, 5, 0);



                // Experience part
                currentLocation[0] = WIDTH_MAX / 3;
                currentLocation[1] -= 50;
//                writeText(writer, "Experience", currentLocation[0], currentLocation[1], FONT_SIZE_BIG, BaseColor.BLACK, BaseFont.COURIER_BOLD);



                currentLocation[0] += 20;
                currentLocation[1] -= 30;
//                pdf_addExperiences(writer, cv.getExperiences(), currentLocation[0], currentLocation[1]);
                pdf_addExperiences(writer, cv.getExperiences(), 150, 0, 5, 0);



                // Education part
                currentLocation[0] = WIDTH_MAX / 3;
                currentLocation[1] -= 50;
//                writeText(writer, "Education", currentLocation[0], currentLocation[1], FONT_SIZE_BIG, BaseColor.BLACK, BaseFont.COURIER_BOLD);


                currentLocation[0] += 20;
                currentLocation[1] -= 30;
//                pdf_addEducations(writer, cv.getEducation(), currentLocation[0], currentLocation[1]);
                pdf_addEducations(writer, cv.getEducation(), 150, 0, 5, 0);



                // Project part
                currentLocation[0] = WIDTH_MAX / 3;
                currentLocation[1] -= 50;
//                writeText(writer, "Projects", currentLocation[0], currentLocation[1], FONT_SIZE_BIG, BaseColor.BLACK, BaseFont.COURIER_BOLD);


                currentLocation[0] += 20;
                currentLocation[1] -= 30;
//                pdf_addProjects(writer, document, cv.getProjects(), currentLocation[0], currentLocation[1]);
                pdf_addProjects(writer, cv.getProjects(), 150, 0, 5, 0);



                // Communication part
                currentLocation[0] = WIDTH_MAX / 3;
                currentLocation[1] -= 50;
//                writeText(writer, "Communications", currentLocation[0], currentLocation[1], FONT_SIZE_BIG, BaseColor.BLACK, BaseFont.COURIER_BOLD);

                currentLocation[0] += 20;
                currentLocation[1] -= 30;
//                pdf_addCommunication(writer, cv.getCommunications(), currentLocation[0], currentLocation[1]);
                pdf_addCommunication(writer, cv.getCommunications(), 150, 0, 5, 0);



                break;

            case TEMPLATE_2:
                w1 = (float) 0.035 * WIDTH_MAX;
                h1 = w1;
                w2 = w1 + 150;
                h2 = (float) 0.97 * HEIGHT_MAX;
                short space = 5; // spade between "Skills" background and rectangle border;
                short borderTickness = 6;

                // Draw rectangle
                float temp_h2 = h2;
                h2 -= 40;
                BaseColor black = new BaseColor(0.0f, 0.0f, 0.0f, 0.9f);

                drawLine(writer, w1, h2, w1+borderTickness, h1, black); // left
                drawLine(writer, w1, h1, w1+150, h1+borderTickness, black); // bot
                drawLine(writer, w1+150, h1, w1+150+borderTickness, h2+borderTickness, black); // right
                drawLine(writer, w1+150, h2+borderTickness, w1, h2, black); // top

                h2 = temp_h2;

                currentLocation[0] = w1;
                currentLocation[1] = h2;
                fullname_template2 = "Abcdefghijkl Mnopqrstuv Wzxyz0wwww5"; //cv.getFirstName() + " " + cv.getMiddleName() + " " + cv.getLastName();
                pdf_addName(writer, fullname_template2, 100, 0, 50, 0);
//                (PdfWriter writer, String name, float marginLeft, float marginRight, float spaceAfter, float spaceBefore)

                currentLocation[0] = WIDTH_MAX - 160;
                currentLocation[1] += 10;
                address = "700669, Iasi, Romania"; //cv.getCod_postal() + "\t" + cv.getCity() + "\t" + cv.getCountry();
                pdf_addAddress(writer, address, 100, 0, 0, 0);

                currentLocation[1] -= 10;
                pdf_addEmail(writer, "tanasapetrut@hotmail.com", 100, 0, 50, 0);

                currentLocation[1] -= 10;
                pdf_addPhoneNumber(writer, "0758990801", 100, 0, 50, 0);
/*

                // Skills part
                currentLocation[0] = w1;
                currentLocation[1] -= 60;
                drawLine(writer, currentLocation[0] + borderTickness + space, currentLocation[1] - 5, currentLocation[0] + 150 - space, currentLocation[1] + 17, new BaseColor(0.0f, 0.0f, 0.0f, 0.5f));
                currentLocation[0] = w1 + 50;
                writeText(writer, "Skills", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM, BaseColor.WHITE, BaseFont.COURIER_BOLD);

                currentLocation[0] = WIDTH_MAX / 3;
                pdf_addSkills(writer, cv.getSkills(), currentLocation[0], currentLocation[1]);


                // Experience part
                currentLocation[0] = w1;
                currentLocation[1] -= 50;
                drawLine(writer, currentLocation[0] + borderTickness + space, currentLocation[1] - 5, currentLocation[0] + 150 - space, currentLocation[1] + 17, new BaseColor(0.0f, 0.0f, 0.0f, 0.5f));
                currentLocation[0] = w1 + 30;
                writeText(writer, "Experience", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM, BaseColor.WHITE, BaseFont.COURIER_BOLD);

                currentLocation[0] = WIDTH_MAX / 3;
                pdf_addExperiences(writer, cv.getExperiences(), currentLocation[0], currentLocation[1]);



                // Education part
                currentLocation[0] = w1;
                currentLocation[1] -= 50;
                drawLine(writer, currentLocation[0] + borderTickness + space, currentLocation[1] - 5, currentLocation[0] + 150 - space, currentLocation[1] + 17, new BaseColor(0.0f, 0.0f, 0.0f, 0.5f));
                currentLocation[0] = w1 + 30;
                writeText(writer, "Education", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM, BaseColor.WHITE, BaseFont.COURIER_BOLD);

                currentLocation[0] = WIDTH_MAX / 3;
                pdf_addEducations(writer, cv.getEducation(), currentLocation[0], currentLocation[1]);



                // Project part
                currentLocation[0] = w1;
                currentLocation[1] -= 50;
                drawLine(writer, currentLocation[0] + borderTickness + space, currentLocation[1] - 5, currentLocation[0] + 150 - space, currentLocation[1] + 17, new BaseColor(0.0f, 0.0f, 0.0f, 0.5f));
                currentLocation[0] = w1 + 35;
                writeText(writer, "Projects", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM, BaseColor.WHITE, BaseFont.COURIER_BOLD);

                currentLocation[0] = WIDTH_MAX / 3;
                pdf_addProjects(writer, document, cv.getProjects(), currentLocation[0], currentLocation[1]);


                // Communication part
                currentLocation[0] = w1;
                currentLocation[1] -= 50;
                drawLine(writer, currentLocation[0] + borderTickness + space, currentLocation[1] - 5, currentLocation[0] + 150 - space, currentLocation[1] + 17, new BaseColor(0.0f, 0.0f, 0.0f, 0.5f));
                currentLocation[0] = w1 + 20;
                writeText(writer, "Communication", currentLocation[0], currentLocation[1], FONT_SIZE_MEDIUM, BaseColor.WHITE, BaseFont.COURIER_BOLD);

                currentLocation[0] = WIDTH_MAX / 3;
                pdf_addCommunication(writer, cv.getCommunications(), currentLocation[0], currentLocation[1]);
*/
                break;

            default:
                break;
        }
    }

    private void pdf_addName(PdfWriter writer, String name, float marginLeft, float marginRight, float spaceAfter, float spaceBefore) throws IOException, DocumentException {
        if(name.length() != 0) {
            writeLongText(writer, name, marginLeft, marginRight, FONT_SIZE_LARGE, spaceAfter, spaceBefore, 0, Element.ALIGN_LEFT);
            TOTAL_LINES += 1;
        }
    }

    private void pdf_addAddress(PdfWriter writer, String address, float marginLeft, float marginRight, float spaceAfter, float spaceBefore) throws IOException, DocumentException {
        if(address.length() != 0) {
//            currentLocation[0] = WIDTH_MAX / 3;
//            currentLocation[1] -= 25;
//            writeText(writer, address, locX, locY, FONT_SIZE_TINY);
            writeLongText(writer, address, marginLeft, marginRight, FONT_SIZE_TINY, spaceAfter, spaceBefore, 0, Element.ALIGN_LEFT);
            TOTAL_LINES += 1;
        }
    }

    private void pdf_addPhoneNumber(PdfWriter writer, String phoneNumber, float marginLeft, float marginRight, float spaceAfter, float spaceBefore) throws IOException, DocumentException {
        if (phoneNumber.length() != 0) {
//            currentLocation[0] = WIDTH_MAX /3;
//            currentLocation[1] -= 15;
//            writeText(writer, "Telefon: " + phoneNumber, locX, locY, FONT_SIZE_TINY);
            writeLongText(writer, phoneNumber, marginLeft, marginRight, FONT_SIZE_TINY, spaceAfter, spaceBefore, 0, Element.ALIGN_LEFT);
            TOTAL_LINES += 1;
        }
    }

    private void pdf_addEmail(PdfWriter writer, String email, float marginLeft, float marginRight, float spaceAfter, float spaceBefore) throws IOException, DocumentException {
        if(email.length() != 0) {
//            currentLocation[0] = WIDTH_MAX / 3;
//            currentLocation[1] -= 15;
//            writeText(writer, "Email: " + email, locX, locY, FONT_SIZE_TINY);
            writeLongText(writer, email, marginLeft, marginRight, FONT_SIZE_TINY, spaceAfter, spaceBefore, 0, Element.ALIGN_LEFT);
            TOTAL_LINES += 1;
        }
    }

    private void pdf_addSkills(PdfWriter writer, ArrayList<Skill> skills, float marginLeft, float marginRight, float spaceAfter, float spaceBefore) throws IOException, DocumentException {
//        currentLocation[0] = locX;
//        currentLocation[1] = locY;
        skills.add(new Skill("Temp Skill", "1Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, "));
        skills.add(new Skill("Temp Skill", "2Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description. "));
        skills.add(new Skill("Temp Skill", "3Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, Skill description, "));
        skills.add(new Skill("Temp Skill", "4Skill "));
        if(skills.size() > 0)
        {
            if(TOTAL_LINES > LINE_NEWPAGE)
                newPage();

            writeLongText(writer, "Skills", 150, 0, FONT_SIZE_BIG, 10, 0, 0);
            TOTAL_LINES = TOTAL_LINES + 1;


            for (Skill skill: skills) {
                TOTAL_LINES = TOTAL_LINES + 1 + skill.getDescription().length() / 65;
            }

            for (Skill skill: skills) {
                if(TOTAL_LINES + 1 + (skill.getDescription().length()/65) > LINE_NEWPAGE)
                    newPage();

                writeLongText(writer, "- " + skill.getNume(), marginLeft+20, marginRight, FONT_SIZE_NORMAL, spaceAfter-5, spaceBefore, 20, Element.ALIGN_JUSTIFIED);
//                TOTAL_LINES = TOTAL_LINES + 1;


                writeLongText(writer, skill.getDescription(), marginLeft+50, marginRight, FONT_SIZE_SMALL, spaceAfter+10, spaceBefore, 0, Element.ALIGN_JUSTIFIED);
//                TOTAL_LINES = TOTAL_LINES + (1+skill.getDescription().length() / 65);

            }
        }
    }



    private void pdf_addExperiences(PdfWriter writer, ArrayList<Experience> experiences, float marginLeft, float marginRight, float spaceAfter, float spaceBefore) throws IOException, DocumentException {
        experiences.add(new Experience("Centric", textWithSpaces("Software Developer", 32), "20.10.2017", "20.10.2018"));
        experiences.add(new Experience("Continental", textWithSpaces("Software Test Engineer", 32), "20.10.2018", "Prezent"));
        if(experiences.size() > 0)
        {
            if (TOTAL_LINES > LINE_NEWPAGE)
                newPage();

            writeLongText(writer, "Experiences", 150, 0, FONT_SIZE_BIG, 10, 20, 0);
            TOTAL_LINES = TOTAL_LINES + 1;


            for(Experience exp: experiences)
            {
                String data;
                data = exp.getStart_date() + " - " + exp.getEnd_date();
                String position = exp.getPosition();
                String myText = position.toUpperCase() +  data;

                // write name
                writeLongText(writer, "- " + exp.getName(), marginLeft+20, marginRight, FONT_SIZE_NORMAL, spaceAfter-10, spaceBefore, 20, Element.ALIGN_LEFT);
                TOTAL_LINES = TOTAL_LINES + 1;

                // write startData_endData
                writeLongText(writer, myText, marginLeft+30, marginRight, FONT_SIZE_SMALL, spaceAfter, spaceBefore, 20, Element.ALIGN_LEFT);
                TOTAL_LINES = TOTAL_LINES + 1;

            }
        }
    }

    private void pdf_addEducations(PdfWriter writer, ArrayList<Education> educations, float marginLeft, float marginRight, float spaceAfter, float spaceBefore) throws IOException, DocumentException {
//        currentLocation[0] = locX;
//        currentLocation[1] = locY;
        educations.add(new Education("liceu", textWithSpaces("Colegiul National de Informatica", 40), textWithSpaces("Matematica Informatica", 30),"2010", "2015"));
        educations.add(new Education("facultate", textWithSpaces("Facultatea de Informatica", 40), textWithSpaces("Informatica", 30), "2015"));

        if(educations.size() > 0)
        {
            if (TOTAL_LINES > LINE_NEWPAGE)
                newPage();

            writeLongText(writer, "Education", 150, 10, FONT_SIZE_BIG, 10, 20, 0);
            TOTAL_LINES = TOTAL_LINES + 1;

            for(Education education : educations)
            {
                String data;
                data = textWithSpaces(education.getData_inceput() + " - " + education.getData_sfarsit(), 35);

                String type = education.getSpecializare();

                String myText = data + type;

                // write name
                writeLongText(writer, "- " + education.getNume(), marginLeft+20, marginRight, FONT_SIZE_NORMAL, spaceAfter-10, spaceBefore, 20, Element.ALIGN_LEFT);
                TOTAL_LINES = TOTAL_LINES + 1;

                // write startData_endData
                writeLongText(writer, myText, marginLeft+30, marginRight, FONT_SIZE_SMALL, spaceAfter, spaceBefore, 20, Element.ALIGN_LEFT);
                TOTAL_LINES = TOTAL_LINES + 1;

            }
        }
    }

    private void pdf_addCommunication(PdfWriter writer, ArrayList<Communication> communications, float marginLeft, float marginRight, float spaceAfter, float spaceBefore) throws IOException, DocumentException {
        communications.add(new Communication("Engleza", "B1"));
        communications.add(new Communication("Germana", "A1"));
        communications.add(new Communication("Engleza", "B1"));
        communications.add(new Communication("Germana", "A1"));
        communications.add(new Communication("Engleza", "B1"));
        communications.add(new Communication("Germana", "A1"));
        communications.add(new Communication("Engleza", "B1"));
        communications.add(new Communication("Germana", "A1"));
        communications.add(new Communication("Engleza", "B1"));
        communications.add(new Communication("Germana", "A1"));
        communications.add(new Communication("Engleza", "B1"));
        communications.add(new Communication("Germana", "A1"));
        communications.add(new Communication("Engleza", "B1"));
        communications.add(new Communication("Germana", "A1"));
        communications.add(new Communication("Engleza", "B1"));
        communications.add(new Communication("Germana", "A1"));
        communications.add(new Communication("Engleza", "B1"));
        communications.add(new Communication("Germana", "A1"));
        communications.add(new Communication("Engleza", "B1"));
        communications.add(new Communication("Germana", "A1"));
        communications.add(new Communication("Engleza", "B1"));
        communications.add(new Communication("Germana", "A1"));
        communications.add(new Communication("Engleza", "B1"));
        communications.add(new Communication("Germana", "A1"));

        if(communications.size() > 0)
        {
            if (TOTAL_LINES > LINE_NEWPAGE)
                newPage();

            writeLongText(writer, "Communication", 150, 10, FONT_SIZE_BIG, 10, 20, 0);
            TOTAL_LINES = TOTAL_LINES + 1;

            for(Communication communication : communications)
            {
                String myText = "| Nivel " + communication.getLevel() + " : " + communication.getName();
                writeLongText(writer, myText, marginLeft+20, marginRight, FONT_SIZE_NORMAL, spaceAfter-10, spaceBefore, 20, Element.ALIGN_LEFT);
                TOTAL_LINES = TOTAL_LINES + 1;
            }
        }
    }

    private void pdf_addProjects(PdfWriter writer, ArrayList<Project> projects, float marginLeft, float marginRight, float spaceAfter, float spaceBefore) throws IOException, DocumentException {
        projects.add(new Project("Project1", "project 1 descriptio nproject 1 description project 1 description project 1 desc ription project 1 descripti onpro ject 1 des criptio nproject 1 descri ption project 1 descriptionpr oject 1 des crip tionpro ject 1 descr iptionproj ect 1 descr iptionproje ct 1 descri ption p roject 1desc riptionpro ject 1des criptionproj ect 1 descript ion project 1 de scriptionproject 1 descriptionproject 1 description", "aa"));
        projects.add(new Project("Project1", "project 1 descriptio nproject 1 description project 1 description project 1 desc ription project 1 descripti onpro ject 1 des criptio nproject 1 descri ption project 1 descriptionpr oject 1 des crip tionpro ject 1 descr iptionproj ect 1 descr iptionproje ct 1 descri ption p roject 1desc riptionpro ject 1des criptionproj ect 1 descript ion project 1 de scriptionproject 1 descriptionproject 1 description", "aa"));
        projects.add(new Project("Project1", "project 1 descriptio nproject 1 description project 1 description project 1 desc ription project 1 descripti onpro ject 1 des criptio nproject 1 descri ption project 1 descriptionpr oject 1 des crip tionpro ject 1 descr iptionproj ect 1 descr iptionproje ct 1 descri ption p roject 1desc riptionpro ject 1des criptionproj ect 1 descript ion project 1 de scriptionproject 1 descriptionproject 1 description", "aa"));
        projects.add(new Project("Project1", "project 1 descriptio nproject 1 description project 1 description project 1 desc ription project 1 descripti onpro ject 1 des criptio nproject 1 descri ption project 1 descriptionpr oject 1 des crip tionpro ject 1 descr iptionproj ect 1 descr iptionproje ct 1 descri ption p roject 1desc riptionpro ject 1des criptionproj ect 1 descript ion project 1 de scriptionproject 1 descriptionproject 1 description", "aa"));
        projects.add(new Project("Project2", "project 2 description", "aa"));
        if(projects.size() > 0)
        {
            if(TOTAL_LINES > LINE_NEWPAGE)
                newPage();

            writeLongText(writer, "Projects", 150, 0, FONT_SIZE_BIG, 10, 0, 0);
            TOTAL_LINES = TOTAL_LINES + 1;


            for(Project proiect : projects) {
                TOTAL_LINES = TOTAL_LINES + 1 + proiect.getRezumat().length() / 65;
//                Log.d("MyDebug", "total_lines: " + TOTAL_LINES + " " + proiect.getRezumat());
            }

            for(Project proiect : projects) {
                writeLongText(writer, "- " + proiect.getName(), marginLeft+20, marginRight, FONT_SIZE_NORMAL, spaceAfter-5, spaceBefore, 20, Element.ALIGN_LEFT);
//                TOTAL_LINES = TOTAL_LINES + 1;

                writeLongText(writer, proiect.getRezumat(), marginLeft+50, marginRight, FONT_SIZE_SMALL, spaceAfter+10, spaceBefore, 0, Element.ALIGN_JUSTIFIED);
//                TOTAL_LINES = TOTAL_LINES + 1;
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

    private void writeText(PdfWriter writer, String text, float startLocX, float startLocY, int fontSize, BaseColor color, String fontType) throws IOException, DocumentException {
        PdfContentByte cb = writer.getDirectContent();
        BaseFont bf = BaseFont.createFont(fontType, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        cb.saveState();

        cb.beginText();
//        cb.setColorStroke(color);
        cb.setColorFill(color);
        cb.moveText(startLocX, startLocY);
        cb.setFontAndSize(bf, fontSize);

        cb.showText(text);

        cb.endText();
        cb.restoreState();
    }

    private void writeLongText(PdfWriter writer, String text, float paddingLeft, float paddingRight, int fontSize, float spaceAfter, float spaceBefore, float firstLineIndent) throws DocumentException {

//        if(TOTAL_LINES > 25) {
//            document.newPage();
//            TOTAL_LINES = -6;
//        }

//        TOTAL_LINES += 1;
//        Log.d("MyDebug", "" + TOTAL_LINES + " " + "1" + " " + text);

        writer.setSpaceCharRatio(PdfWriter.NO_SPACE_CHAR_RATIO);
        Paragraph paragraph = new Paragraph();
        paragraph.setIndentationLeft(paddingLeft);
        paragraph.setIndentationRight(paddingRight);
        paragraph.getFont().setSize(fontSize);
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraph.setFirstLineIndent(firstLineIndent);
        paragraph.setSpacingAfter(spaceAfter);
        paragraph.setSpacingBefore(spaceBefore);
//        paragraph.setTabSettings(new TabSettings(200f));
        paragraph.add(text);

        document.add(paragraph);
    }

    private void writeLongText(PdfWriter writer, String text, float paddingLeft, float paddingRight, int fontSize, float spaceAfter, float spaceBefore, float firstLineIndent, int align) throws DocumentException {
        writer.setSpaceCharRatio(PdfWriter.NO_SPACE_CHAR_RATIO);
        Paragraph paragraph = new Paragraph();
        paragraph.setIndentationLeft(paddingLeft);
        paragraph.setIndentationRight(paddingRight);
        paragraph.getFont().setSize(fontSize);
        paragraph.setAlignment(align);
        paragraph.setFirstLineIndent(firstLineIndent);
        paragraph.setSpacingAfter(spaceAfter);
        paragraph.setSpacingBefore(spaceBefore);
        paragraph.add(text);
//        int text_lines = text.length() / 65;
//        TOTAL_LINES = TOTAL_LINES + 1 + text_lines;

//        Log.d("MyDebug", "" + TOTAL_LINES + " " + text_lines + " " + text);


        document.add(paragraph);
    }

    private void writeText2(PdfWriter writer, String text, float startLocX, float startLocY, int fontSize, BaseColor color, String fontType) throws IOException, DocumentException {


    }

    private void drawRectangle(PdfWriter writer, float w1, float h1, float w2, float h2, int borderTickness, BaseColor color) {
        drawLine(writer, w1, h1, w1+borderTickness, h2, color);
        drawLine(writer, w1, h2, w2, h2+borderTickness, color);
        drawLine(writer, w2, h2, w2+borderTickness, h1, color);
        drawLine(writer, w2, h1, w1, h1+borderTickness, color);
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

    private String textWithSpaces(String text, int toSize) {
        int spacesNeeded = toSize - text.length();
        StringBuilder returnString = new StringBuilder(text);

        for (short i = 0; i < spacesNeeded; i++) {
            returnString.append("  ");
        }

//        Log.i("MyDebug", returnString.toString() + ".");
        return returnString.toString();
    }

    private void newPage() {
        document.newPage();
        TOTAL_LINES = -10;
    }
}
