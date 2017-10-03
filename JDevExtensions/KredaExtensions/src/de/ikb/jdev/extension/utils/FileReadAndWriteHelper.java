package de.ikb.jdev.extension.utils;

import freemarker.core.Environment;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import java.nio.charset.Charset;

import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

public class FileReadAndWriteHelper {

    public static void generateAndWriteFile(Map root, Template template, String path) {
        generateAndWriteFile(root, template, path, false);
    }    

    public static void generateAndAppendToFile(Map root, Template template, String path) {
        generateAndWriteFile(root, template, path, true);
    }
    
    private static void generateAndWriteFile(Map root, Template template, String path, boolean append){
        File outputFile = new File(path);
        outputFile.getParentFile().mkdirs();
        Writer fileWriter = null;
        try {         
            fileWriter = new OutputStreamWriter(new FileOutputStream(outputFile, append), Charset.forName("UTF-8"));
            Environment env = template.createProcessingEnvironment(root, fileWriter);
            env.setOutputEncoding("UTF-8");
            env.process();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void writeXML(String outputPath, Document doc) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(outputPath));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {  
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    public static Node parseXML(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Node parsedNode = null;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));
            parsedNode = document.getFirstChild();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parsedNode;
    }

    public static String readFile(String path) {

        String fileAsString = null;
        try {
            InputStream is = new FileInputStream(path);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
                fileAsString = sb.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileAsString;
    }
    
    public static void generateAndInsertIntoXML(String path, String xpath, Map dataModel, Template template) {
        try {

            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            // generiere XML-Permission-Eintrag
            Writer out = new StringWriter();
            template.process(dataModel, out);
            String transformedTemplate = out.toString();

            // parse den generierten Eintrag
            Node generatedPermission = FileReadAndWriteHelper.parseXML(transformedTemplate);

            // machen den merge zweier xml-Dokumente moeglich
            generatedPermission = doc.importNode(generatedPermission, true);

            // finde permissions-XMLElement zu grantee "authenticated-role"
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodes =
                (NodeList) xPath.evaluate(xpath, doc.getDocumentElement(), XPathConstants.NODESET);
            Node permissionsNode = nodes.item(0);

            // haenge den generierten XML-Eintrag in den "Original-DOM" ein
            permissionsNode.appendChild(generatedPermission);

            // schreibe das XML-Dokument weg
            writeXML(path, doc);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    /* public static TrainModel readJson() {
        String jsonString = readFile("src/in/trainGeneratorInput.json");
        Gson gson = new GsonBuilder().create();
        TrainModel trainModel = gson.fromJson(jsonString, TrainModel.class);
        return trainModel;
    }  */
}
