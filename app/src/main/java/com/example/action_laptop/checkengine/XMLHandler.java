package com.example.action_laptop.checkengine; /**
 * Created by Action-Laptop on 6/28/2017.
 */

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLHandler {

    public boolean WriteFile(Document document, String path){
        try{
            //create tmp file
            DOMSource domSource = new DOMSource(document);
            File file = new File(path);
            //used to write the file created above
            StreamResult result = new StreamResult(file);
            //sets configuration for outputted file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //writes file from tmp to destination
            transformer.transform(domSource, result);

            return true;
        } catch (Exception efinal) {
            //TODO Handle Exceptions
            //Final exception output unable to create file

            System.out.println("Unable to create file, default file creation failed");


        }

        return false;
    }

    public Document ReadFile(String path){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(path);
            //removes potential errors in file
            document.getDocumentElement().normalize();

            return document;
        } catch (Exception e) {
            //TODO Handle Exceptions
        }

        return null;
    }

//    public Document ReadFile(InputStream inputStream){
//        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//
//        try{
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//            Document document = documentBuilder.parse(path);
//            //removes potential errors in file
//            document.getDocumentElement().normalize();
//
//            return document;
//        } catch (Exception e) {
//            //TODO Handle Exceptions
//        }
//
//        return null;
//    }
}