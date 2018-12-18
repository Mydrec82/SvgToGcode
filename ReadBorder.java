package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ReadBorder {
    private Construct constructor;
    private List<String> out = new ArrayList<>();

    void read(boolean mirrorX, boolean mirrorY){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document document = null;
        try {
            document = builder.parse(InFile.getInstance().getFile());
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        Element root = document.getDocumentElement();
        root.getAttribute("viewBox");
        Pattern pat = Pattern.compile("\\d+\\.?\\d+");
        Matcher mat = pat.matcher(root.getAttribute("viewBox"));
        while(mat.find()){
            out.add(mat.group());
        }


        if(mirrorX && mirrorY)constructor = new Mirror(out, new Matrix(out).getMatrixXY());
        else if(mirrorX) constructor = new Mirror(out, new Matrix(out).getMatrixX());
        else if(mirrorY) constructor = new Mirror(out, new Matrix(out).getMatrixY());

        if (constructor == null) {
            constructor = new Constructor(out);
        }
        out.clear();




        NodeList list = root.getChildNodes();
        for(int a = 0; a < list.getLength(); a++){
            if(list.item(a).hasChildNodes()){
                NodeList fist = list.item(a).getChildNodes();
                for(int s = 0; s < fist.getLength(); s++){
                    if(fist.item(s).getNodeName().equals("polyline")){
                        int m = Integer.parseInt(fist.item(s).getAttributes().getNamedItem("layerid").getNodeValue());
                        if(m == 10 || m == 11) {
                            out.add(fist.item(s).getAttributes().getNamedItem("points").getNodeValue());
                            out.add(fist.item(s).getAttributes().getNamedItem("stroke-width").getNodeValue());
                        }
                    }
                }
            }
        }

        constructor.getOutFile().write("G21\nG90");
        constructor.factory("polyline", out);
        out.clear();
        constructor.getOutFile().write("\nG28 X0\nM84");

    }
}
