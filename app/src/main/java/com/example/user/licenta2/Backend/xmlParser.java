package com.example.user.licenta2.Backend;

import android.util.Log;

import com.example.user.licenta2.CV;
import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.MyClasses.Communication;
import com.example.user.licenta2.MyClasses.Education;
import com.example.user.licenta2.MyClasses.Experience;
import com.example.user.licenta2.MyClasses.Project;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class xmlParser {

    private File myXML;
    private String dest; // = "/data/user/0/com.example.user.licenta2/XMLs/";

    public xmlParser(String _dest, String _xmlName) throws IOException {
        this.dest = _dest;
        File dir = new File(dest);
        dir.mkdirs();

        myXML = new File(dir, _xmlName);
        myXML.createNewFile();
    }

    public xmlParser() {

    }

    public void createXML(CV cv) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("CV");
            doc.appendChild(rootElement);

            // nameData element
            Attr rootAttr_cvName = doc.createAttribute("name");
            rootAttr_cvName.setValue(cv.getCvName());
            rootElement.setAttributeNode(rootAttr_cvName);


//            // data type="contact" element
//            Element contactData = doc.createElement("data");
//            rootElement.appendChild(contactData);
//            Attr type_contact = doc.createAttribute("dataType");
//            type_contact.setValue("contact");
//            contactData.setAttributeNode(type_contact);
//
//            // dataContact.name element
//            Element dataContact_name = doc.createElement("contactData");
//            contactData.appendChild(dataContact_name);
//            Attr type_dataContactType_name = doc.createAttribute("dataContactType");
//            type_dataContactType_name.setValue("name");
//            dataContact_name.setAttributeNode(type_dataContactType_name);
//
//            // first_name element
//            Element first_name = doc.createElement("first_name");
//            first_name.appendChild(doc.createTextNode(cv.getFirstName()));
//            dataContact_name.appendChild(first_name);
//
//            // middle_name element
//            Element middle_name = doc.createElement("middle_name");
//            middle_name.appendChild(doc.createTextNode(cv.getMiddleName()));
//            dataContact_name.appendChild(middle_name);
//
//            // last_name element
//            Element last_name = doc.createElement("last_name");
//            last_name.appendChild(doc.createTextNode(cv.getLastName()));
//            dataContact_name.appendChild(last_name);
//
//
//            // dataContact.address element
//            Element dataContact_address = doc.createElement("contactData");
//            contactData.appendChild(dataContact_address);
//            Attr type_dataContactType_address = doc.createAttribute("dataContactType");
//            type_dataContactType_address.setValue("address");
//            dataContact_address.setAttributeNode(type_dataContactType_address);
//
//            // country element
//            Element country = doc.createElement("country");
//            country.appendChild(doc.createTextNode(cv.getCountry()));
//            dataContact_address.appendChild(country);
//
//            // city element
//            Element city = doc.createElement("city");
//            city.appendChild(doc.createTextNode(cv.getCity()));
//            dataContact_address.appendChild(city);
//
//            // cod_postal element
//            Element cod_postal = doc.createElement("cod_postal");
//            cod_postal.appendChild(doc.createTextNode(cv.getCod_postal()));
//            dataContact_address.appendChild(cod_postal);
//
//
//            // dataContact.phone element
//            Element dataContact_phone = doc.createElement("contactData");
//            contactData.appendChild(dataContact_phone);
//            Attr type_dataContactType_phone = doc.createAttribute("dataContactType");
//            type_dataContactType_phone.setValue("phoneNumber");
//            dataContact_phone.setAttributeNode(type_dataContactType_phone);
//
//            // phoneNumber element
//            Element phoneNumber = doc.createElement("phoneNumber");
//            phoneNumber.appendChild(doc.createTextNode(cv.getPhoneNumber()));
//            dataContact_phone.appendChild(phoneNumber);
//
//
//
//            // dataContact.phone element
//            Element dataContact_email = doc.createElement("contactData");
//            contactData.appendChild(dataContact_email);
//            Attr type_dataContactType_email = doc.createAttribute("dataContactType");
//            type_dataContactType_email.setValue("email");
//            dataContact_email.setAttributeNode(type_dataContactType_email);
//
//            // emailData element
//            Element emailData = doc.createElement("email");
//            emailData.appendChild(doc.createTextNode(cv.getEmail()));
//            dataContact_email.appendChild(emailData);
//







            /* ################################################################
            ##################### Skills ##################################
            ################################################################### */
            // skillData element
            Element skillsData = doc.createElement("data");
            rootElement.appendChild(skillsData);
            Attr type_aptitudini = doc.createAttribute("dataType");
            type_aptitudini.setValue("skills");
            skillsData.setAttributeNode(type_aptitudini);

            short idx = 0;
            for (Skill skill : cv.getSkills()) {

                Element aptitudine = doc.createElement("skillData");

                // add ID attribute
                Attr attr_aptitudine_id = doc.createAttribute("id");
                rootAttr_cvName.setValue(Integer.toString(idx));
                aptitudine.setAttributeNode(attr_aptitudine_id);
                skillsData.appendChild(aptitudine);

                // skill.nume element
                Element skillName = doc.createElement("name");
                skillName.appendChild(doc.createTextNode(skill.getNume()));
                aptitudine.appendChild(skillName);

                idx++;
            }







            /* ################################################################
            ##################### Education ######################################
            ################################################################### */
            // studiiData element
            Element studiiData = doc.createElement("data");
            rootElement.appendChild(studiiData);
            Attr type_studii = doc.createAttribute("dataType");
            type_studii.setValue("education");
            studiiData.setAttributeNode(type_studii);

            idx = 0;
            for (Education st : cv.getEducation()) {

                Element institutie = doc.createElement("educationData");

                // add ID attribute
                Attr attr_institutie_id = doc.createAttribute("id");
                rootAttr_cvName.setValue(Integer.toString(idx));
                institutie.setAttributeNode(attr_institutie_id);
                studiiData.appendChild(institutie);


                // institutie.type element
                Element typeInstitutie = doc.createElement("type");
                typeInstitutie.appendChild(doc.createTextNode(st.getType()));
                institutie.appendChild(typeInstitutie);

                // institutie.nume element
                Element numeInstitutie = doc.createElement("name");
                numeInstitutie.appendChild(doc.createTextNode(st.getNume()));
                institutie.appendChild(numeInstitutie);

                // institutie.data_inceput element
                Element data_inceput_institutie = doc.createElement("start_date");
                data_inceput_institutie.appendChild(doc.createTextNode(st.getData_inceput()));
                institutie.appendChild(data_inceput_institutie);

                // institutie.data_sfarsit element
                Element data_sfarsit_institutie = doc.createElement("end_date");
                data_sfarsit_institutie.appendChild(doc.createTextNode(st.getData_sfarsit()));
                institutie.appendChild(data_sfarsit_institutie);

                // institutie.specializare element
                Element specializare = doc.createElement("specialization");
                specializare.appendChild(doc.createTextNode(st.getSpecializare()));
                institutie.appendChild(specializare);

                idx++;
            }



//
//
//
//            /* ################################################################
//            ##################### Experience ##################################
//            ################################################################### */
//
//            // experienceData element
//            Element experientaData = doc.createElement("data");
//            rootElement.appendChild(experientaData);
//            Attr type_experienta = doc.createAttribute("dataType");
//            type_experienta.setValue("experience");
//            experientaData.setAttributeNode(type_experienta);
//
//            idx = 0;
//            for (Experience exp : cv.getExperiences()) {
//
//                Element firma = doc.createElement("experienceData");
//
//                // add ID attribute
//                Attr attr_institutie_id = doc.createAttribute("id");
//                rootAttr_cvName.setValue(String.valueOf(idx));
//                firma.setAttributeNode(attr_institutie_id);
//                experientaData.appendChild(firma);
//
//                // firma.nume element
//                Element numeInstitutie = doc.createElement("name");
//                numeInstitutie.appendChild(doc.createTextNode(exp.getName()));
//                firma.appendChild(numeInstitutie);
//
//                // firma.pozitie element
//                Element pozitieInstitutie = doc.createElement("position");
//                pozitieInstitutie.appendChild(doc.createTextNode(exp.getPosition()));
//                firma.appendChild(pozitieInstitutie);
//
//                // firma.data_inceput element
//                Element data_inceput_experienta = doc.createElement("start_date");
//                data_inceput_experienta.appendChild(doc.createTextNode(exp.getStart_date()));
//                firma.appendChild(data_inceput_experienta);
//
//                // firma.data_sfarsit element
//                Element data_sfarsit_experienta = doc.createElement("end_date");
//                data_sfarsit_experienta.appendChild(doc.createTextNode(exp.getEnd_date()));
//                firma.appendChild(data_sfarsit_experienta);
//
//                idx++;
//            }
//
//
//
//
//
//
//            /* ################################################################
//            ##################### Proiecte ####################################
//            ################################################################### */
//
//            // proiecteData element
//            Element proiecteData = doc.createElement("data");
//            rootElement.appendChild(proiecteData);
//            Attr type_proiecte = doc.createAttribute("dataType");
//            type_proiecte.setValue("projects");
//            proiecteData.setAttributeNode(type_proiecte);
//
//            idx = 0;
//            for (Project pr : cv.getProjects()) {
//
//                Element proiect = doc.createElement("projectData");
//
//                // add ID attribute
//                Attr attr_proiect_id = doc.createAttribute("id");
//                rootAttr_cvName.setValue(String.valueOf(idx));
//                proiect.setAttributeNode(attr_proiect_id);
//                proiecteData.appendChild(proiect);
//
//                // proiect.nume element
//                Element numeProiect = doc.createElement("name");
//                numeProiect.appendChild(doc.createTextNode(pr.getName()));
//                proiect.appendChild(numeProiect);
//
////                // proiect.rezumatProiect element
////                Element rezumatProiect = doc.createElement("rezumat");
////                rezumatProiect.appendChild(doc.createTextNode(pr.getRezumat()));
////                proiect.appendChild(rezumatProiect);
//
//                // proiect.descriereProiect element
//                Element descriereProiect = doc.createElement("description");
//                descriereProiect.appendChild(doc.createTextNode(pr.getDescription()));
//                proiect.appendChild(descriereProiect);
//
//
////                // firma.data_inceput element
////                Element data_inceput_experienta = doc.createElement("data_inceput");
////                data_inceput_experienta.appendChild(doc.createTextNode(exp.getStart_date()));
////                proiect.appendChild(data_inceput_experienta);
////
////                // firma.data_sfarsit element
////                Element data_sfarsit_experienta = doc.createElement("data_sfarsit");
////                data_sfarsit_experienta.appendChild(doc.createTextNode(exp.getEnd_date()));
////                proiect.appendChild(data_sfarsit_experienta);
//
//                idx++;
//            }
//
//
//
//
//            /* ################################################################
//            ##################### Communication ##################################
//            ################################################################### */
//
//            // comunicareData element
//            Element comunicareData = doc.createElement("data");
//            rootElement.appendChild(comunicareData);
//            Attr type_comunicare = doc.createAttribute("dataType");
//            type_comunicare.setValue("communication");
//            comunicareData.setAttributeNode(type_comunicare);
//
//            idx = 0;
//            for (Communication c : cv.getCommunications()) {
//
//                Element lb_straina = doc.createElement("communicationData");
//
//                // add ID attribute
//                Attr attr_limba_id = doc.createAttribute("id");
//                rootAttr_cvName.setValue(String.valueOf(idx));
//                lb_straina.setAttributeNode(attr_limba_id);
////
////                institutie.appendChild(doc.createTextNode(st.getName()));
//                proiecteData.appendChild(lb_straina);
//
//                // lb_straina.nume element
//                Element numeLimba = doc.createElement("name");
//                numeLimba.appendChild(doc.createTextNode(c.getName()));
//                lb_straina.appendChild(numeLimba);
//
//                // lb_straina.nivel element
//                Element nivelLimba = doc.createElement("level");
//                nivelLimba.appendChild(doc.createTextNode(c.getLevel()));
//                lb_straina.appendChild(nivelLimba);
//
//                idx++;
//            }


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(myXML);
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("MyErr", e.toString());
        }
    }

    public CV readXML(String path, String fileName, String cvName) {
        CV cvToReturn = new CV();
        cvToReturn.setCvName(cvName);

        try {
            File inputFile = new File(path + "/XMLs/" + fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList data_nodes = doc.getElementsByTagName("data");
            NodeList contactData_nodes = doc.getElementsByTagName("contactData");
            NodeList skillsData_nodes = doc.getElementsByTagName("skillData");
            NodeList experienceData_nodes = doc.getElementsByTagName("experienceData");
            NodeList educationData_nodes = doc.getElementsByTagName("educationData");
            NodeList projectData_nodes = doc.getElementsByTagName("projectData");
            NodeList communicationData_nodes = doc.getElementsByTagName("communicationData");

//            // extract ContactData - fullname
//            Node contactData_node_name = contactData_nodes.item(0);
//            if(contactData_node_name.getNodeType() == Node.ELEMENT_NODE) {
//                Element element_name = (Element) contactData_node_name;
//                cvToReturn.setFirstName(element_name.getElementsByTagName("first_name").item(0).getTextContent());
//                cvToReturn.setMiddleName(element_name.getElementsByTagName("middle_name").item(0).getTextContent());
//                cvToReturn.setLastName(element_name.getElementsByTagName("last_name").item(0).getTextContent());
//            }
//
//            // extract ContactData - address
//            Node contactData_node_address = contactData_nodes.item(1);
//            if(contactData_node_address.getNodeType() == Node.ELEMENT_NODE) {
//                Element element_address = (Element) contactData_node_address;
//                cvToReturn.setCountry(element_address.getElementsByTagName("country").item(0).getTextContent());
//                cvToReturn.setCity(element_address.getElementsByTagName("city").item(0).getTextContent());
//                cvToReturn.setCod_postal(element_address.getElementsByTagName("cod_postal").item(0).getTextContent());
//            }
//
//            Node contactData_node_phone = contactData_nodes.item(2);
//            if(contactData_node_phone.getNodeType() == Node.ELEMENT_NODE) {
//                Element element_phone = (Element) contactData_node_phone;
//                cvToReturn.setPhoneNumber(element_phone.getElementsByTagName("phoneNumber").item(0).getTextContent());
//            }
//
//            Node contactData_node_email = contactData_nodes.item(3);
//            if(contactData_node_email.getNodeType() == Node.ELEMENT_NODE) {
//                Element element_email = (Element) contactData_node_email;
//                cvToReturn.setEmail(element_email.getElementsByTagName("email").item(0).getTextContent());
//            }



            // extract SkillsData
            ArrayList<Skill> mySkills = new ArrayList<>();

            for(short idx = 0; idx < skillsData_nodes.getLength(); idx++) {
                Node skillNode = skillsData_nodes.item(idx);

                if (skillNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) skillNode;
                    Skill skill = new Skill();
                    skill.setNume(element.getElementsByTagName("name").item(0).getTextContent());

                    mySkills.add(skill);
                }
            }

            cvToReturn.setSkills(mySkills);



//            // extract ExperienceData
//            ArrayList<Experience> myExperiences = new ArrayList<>();
//
//            for(short idx = 0; idx < experienceData_nodes.getLength(); idx++) {
//                Node experienceNode = experienceData_nodes.item(idx);
//
//                if (experienceNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element) experienceNode;
//                    Experience experience = new Experience();
//                    experience.setName(element.getElementsByTagName("name").item(0).getTextContent());
//                    experience.setPosition(element.getElementsByTagName("position").item(0).getTextContent());
//                    experience.setStart_date(element.getElementsByTagName("start_date").item(0).getTextContent());
//                    String endDate = element.getElementsByTagName("end_date").item(0).getTextContent();
//                    if(endDate.length() > 0) {
//                        experience.setEnd_date(endDate);
//                    }
//
//                    myExperiences.add(experience);
//                }
//            }
//
//            cvToReturn.setExperiences(myExperiences);



            // extract EducationData
            ArrayList<Education> myEducations = new ArrayList<>();

            for(short idx = 0; idx < educationData_nodes.getLength(); idx++) {
                Node educationNode = educationData_nodes.item(idx);

                if (educationNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) educationNode;
                    Education education = new Education();
                    education.setType(element.getElementsByTagName("type").item(0).getTextContent());
                    education.setNume(element.getElementsByTagName("name").item(0).getTextContent());
                    education.setData_inceput(element.getElementsByTagName("start_date").item(0).getTextContent());
                    education.setData_sfarsit(element.getElementsByTagName("end_date").item(0).getTextContent());
                    education.setSpecializare(element.getElementsByTagName("specialization").item(0).getTextContent());

                    myEducations.add(education);
                }
            }

            cvToReturn.setEducation(myEducations);



//            // extract ProjectData
//            ArrayList<Project> myProjects = new ArrayList<>();
//
//            for(short idx = 0; idx < projectData_nodes.getLength(); idx++) {
//                Node projectNode = projectData_nodes.item(idx);
//
//                if (projectNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element) projectNode;
//                    Project project = new Project();
//                    project.setName(element.getElementsByTagName("name").item(0).getTextContent());
//                    project.setDescription(element.getElementsByTagName("description").item(0).getTextContent());
//
//                    myProjects.add(project);
//                }
//            }
//
//            cvToReturn.setProjects(myProjects);
//
//
//
//            // extract CommunicationData
//            ArrayList<Communication> myCommunications = new ArrayList<>();
//
//            for(short idx = 0; idx < communicationData_nodes.getLength(); idx++) {
//                Node communicationNode = communicationData_nodes.item(idx);
//
//                if (communicationNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element) communicationNode;
//                    Communication communication = new Communication();
//                    communication.setName(element.getElementsByTagName("name").item(0).getTextContent());
//                    communication.setLevel(element.getElementsByTagName("level").item(0).getTextContent());
//
//                    myCommunications.add(communication);
//                }
//            }
//
//            cvToReturn.setCommunications(myCommunications);

        } catch(ParserConfigurationException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
            Log.d("MyErr", e.toString());
        } catch(SAXException e) {
            e.printStackTrace();
        }

        return cvToReturn;
    }
}
