package com.digi.springbootfirstapp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.InputStream;

public class XMLParsingExample {
    public String parseXML(InputStream inputStream, String targetAttribute) {
        StringBuilder parsedAttributeValues = new StringBuilder();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            Element parentElement = document.getDocumentElement();

            if (parentElement.hasAttributes()) {
                String attributeValue = parentElement.getAttribute(targetAttribute);
                if (!attributeValue.isEmpty()) {
                    parsedAttributeValues.append(attributeValue).append("<br>");
                }
            }

            NodeList childNodes = parentElement.getChildNodes();
            parseChildElements(childNodes, targetAttribute, parsedAttributeValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsedAttributeValues.toString();
    }

    private void parseChildElements(NodeList childNodes, String targetAttribute, StringBuilder parsedAttributeValues) {
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                if (childElement.hasAttributes()) {
                    String attributeValue = childElement.getAttribute(targetAttribute);
                    if (!attributeValue.isEmpty()) {
                        parsedAttributeValues.append(attributeValue).append("<br>");
                    }
                }
                if (childElement.hasChildNodes()) {
                    parseChildElements(childElement.getChildNodes(), targetAttribute, parsedAttributeValues);
                }
            }
        }
    }


    public void addAttributeToXML(String attributeName, String attributeValue, InputStream inputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            Element rootElement = document.getDocumentElement();
            rootElement.setAttribute(attributeName, attributeValue);

            // Write the changes back to the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("modified.xml")); // Modify the file name as needed
            transformer.transform(source, result);

            System.out.println("Attribute added successfully to XML.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


