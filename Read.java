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

/**
 * this class Read svg file
 * set fist zero point from field viebox
 * if plate create with mirror, then create mirror constructor
 *
 */
class Read{
    /**
     *
     */
    private Construct constructor;
    private List<String> out = new ArrayList<>();

     void read(boolean mirrorX, boolean mirrorY) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = null;
         try {
             builder = factory.newDocumentBuilder();
         } catch (ParserConfigurationException e) {
             e.printStackTrace();
         }

         Document document = null;
         try {
             assert builder != null;
             document = builder.parse(InFile.getInstance().getFile());
         } catch (NullPointerException | SAXException | IOException  e) {
             e.printStackTrace();
         }


         if(document == null)throw new NullPointerException("document = null.Its not impasible " + getClass());
         Element root = document.getDocumentElement();
         root.getAttribute("viewBox");
         Pattern pat = Pattern.compile("\\d+\\.?\\d+");
         Matcher mat = pat.matcher(root.getAttribute("viewBox"));
         while(mat.find()){
             out.add(mat.group());
         }


//         if side Mirror
         if(mirrorX && mirrorY)constructor = new Mirror(out, new Matrix(out).getMatrixXY());
         else if(mirrorX) constructor = new Mirror(out, new Matrix(out).getMatrixX());
         else if(mirrorY) constructor = new Mirror(out, new Matrix(out).getMatrixY());
//         if side not Mirror
        if(constructor == null){ constructor = new Constructor(out);}
        out.clear();

         NodeList list = root.getChildNodes();
         constructor.getOutFile().write("G21\nG90");
         getNode(list, "ELLIPSE");
         getNode(list, "RECT");
         getNode(list, "OVAL");
         getNode(list, "polyline");
         constructor.getOutFile().write("\nG28 X"+0+"\nM84");
     }




   //this metod finding require Node
    private void getNode(NodeList list, String figure){
        for(int a = 0; a < list.getLength(); a++){
            if(list.item(a).hasAttributes()){
                for(int s = 0; s < list.item(a).getAttributes().getLength(); s++){
                    if(list.item(a).getAttributes().item(s).getNodeValue().equals(figure)) {
                        tryCreate(list.item(a), figure);
                    }
                }
                if(list.item(a).getNodeName().equals(figure))tryCreate(list.item(a), figure);
            }
            if(list.item(a).hasChildNodes()) getNode(list.item(a).getChildNodes(), figure);
        }
    }

    private void tryCreate(Node n, String figure){
        switch(figure){
            case"ELLIPSE":{
                out.add(n.getChildNodes().item(0).getAttributes().getNamedItem("cx").getNodeValue());
                out.add(n.getChildNodes().item(0).getAttributes().getNamedItem("cy").getNodeValue());
                out.add(n.getChildNodes().item(0).getAttributes().getNamedItem("rx").getNodeValue());
                out.add(n.getChildNodes().item(0).getAttributes().getNamedItem("ry").getNodeValue());
                out.add(n.getChildNodes().item(1).getAttributes().getNamedItem("r").getNodeValue());
                constructor.factory(figure, out);
                out.clear();
                break;
            }
            case"RECT":{
                out.add(n.getChildNodes().item(0).getAttributes().getNamedItem("points").getNodeValue());
                out.add(n.getChildNodes().item(1).getAttributes().getNamedItem("r").getNodeValue());
                constructor.factory(figure, out);
                out.clear();
                break;
            }
            case"polyline":{
                out.add(n.getAttributes().getNamedItem("points").getNodeValue());
                out.add(n.getAttributes().getNamedItem("stroke-width").getNodeValue());
                int layer = Integer.parseInt(n.getAttributes().getNamedItem("layerid").getNodeValue());
                if(layer != 10 && layer != 11) {
                    constructor.factory(figure, out);
                }
                out.clear();
                break;
            }
            case"OVAL":{
                out.add(n.getChildNodes().item(0).getAttributes().getNamedItem("points").getNodeValue());
                out.add(n.getChildNodes().item(0).getAttributes().getNamedItem("stroke-width").getNodeValue());
                out.add(n.getAttributes().getNamedItem("c_rotation").getNodeValue());
                out.add(n.getChildNodes().item(1).getAttributes().getNamedItem("cx").getNodeValue());
                out.add(n.getChildNodes().item(1).getAttributes().getNamedItem("cy").getNodeValue());
                out.add(n.getChildNodes().item(1).getAttributes().getNamedItem("r").getNodeValue());
                constructor.factory(figure, out);
                out.clear();
                break;
            }
        }
    }
}