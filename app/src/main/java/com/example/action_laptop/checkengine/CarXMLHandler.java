package com.example.action_laptop.checkengine;

import android.content.res.XmlResourceParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Action-Laptop on 7/1/2017.
 */

public class CarXMLHandler extends XMLHandler {

    public boolean WriteCarXMLFile(CarValues carValues, String path){
        try {
            //builds XML file
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element car = document.createElement("car");

            Element engine = document.createElement("carSection");
            engine.setAttribute("attr", "engine");
            Element timingBelts = document.createElement("timingBelts");
            timingBelts.appendChild(document.createTextNode(Integer.toString(carValues.getTimingBelts())));
            Element solenoid = document.createElement("solenoid");
            solenoid.appendChild(document.createTextNode(Integer.toString(carValues.getSolenoid())));
            Element alternator = document.createElement("alternator");
            alternator.appendChild(document.createTextNode(Integer.toString(carValues.getAlternator())));
            Element starter = document.createElement("starter");
            starter.appendChild(document.createTextNode(Integer.toString(carValues.getStarter())));
            Element airFilter = document.createElement("airFilter");
            airFilter.appendChild(document.createTextNode(Integer.toString(carValues.getAirFilter())));
            engine.appendChild(timingBelts);
            engine.appendChild(solenoid);
            engine.appendChild(alternator);
            engine.appendChild(starter);
            engine.appendChild(airFilter);

            Element wheels = document.createElement("carSection");
            wheels.setAttribute("attr", "wheels");
            Element brakePads = document.createElement("brakePads");
            Element disc = document.createElement("disc");
            disc.appendChild(document.createTextNode(Integer.toString(carValues.getDiscBrakes())));
            Element drum = document.createElement("drum");
            drum.appendChild(document.createTextNode(Integer.toString(carValues.getDrumBrakes())));
            brakePads.appendChild(disc);
            brakePads.appendChild(drum);
            Element rotors = document.createElement("rotors");
            rotors.appendChild(document.createTextNode(Integer.toString(carValues.getRotors())));
            Element tires = document.createElement("tires");
            tires.appendChild(document.createTextNode(Integer.toString(carValues.getTires())));
            Element rotate = document.createElement("rotate");
            rotate.appendChild(document.createTextNode(Integer.toString(carValues.getRotation())));
            Element alignment = document.createElement("alignment");
            alignment.appendChild(document.createTextNode(Integer.toString(carValues.getAlignment())));
            wheels.appendChild(brakePads);
            wheels.appendChild(rotors);
            wheels.appendChild(tires);
            wheels.appendChild(rotate);
            wheels.appendChild(alignment);

            Element fluids = document.createElement("carSection");
            fluids.setAttribute("attr", "fluids");
            Element oil = document.createElement("oil");
            oil.appendChild(document.createTextNode(Integer.toString(carValues.getOil())));
            Element brake = document.createElement("brake");
            brake.appendChild(document.createTextNode(Integer.toString(carValues.getBrakeFluid())));
            Element transmission = document.createElement("transmission");
            Element automaticTransFluid = document.createElement("automatic");
            automaticTransFluid.appendChild(document.createTextNode(Integer.toString(carValues.getAutoTransmission())));
            Element manualTransFluid  = document.createElement("manual");
            manualTransFluid.appendChild(document.createTextNode(Integer.toString(carValues.getManualTransmission())));
            transmission.appendChild(automaticTransFluid);
            transmission.appendChild(manualTransFluid);
            Element auto = document.createElement("auto");
            auto.appendChild(document.createTextNode(Integer.toString(carValues.getAutoTransmission())));
            Element manual = document.createElement("manual");
            manual.appendChild(document.createTextNode(Integer.toString(carValues.getManualTransmission())));
            Element radiator = document.createElement("radiator");
            radiator.appendChild(document.createTextNode(Integer.toString(carValues.getRadiator())));
            Element powerSteering = document.createElement("powerSteering");
            powerSteering.appendChild(document.createTextNode(Integer.toString(carValues.getPowerSteering())));
            Element coolant = document.createElement("coolant");
            coolant.appendChild(document.createTextNode(Integer.toString(carValues.getCoolant())));
            fluids.appendChild(oil);
            fluids.appendChild(brake);
            fluids.appendChild(transmission);
            fluids.appendChild(radiator);
            fluids.appendChild(powerSteering);
            fluids.appendChild(coolant);

            car.appendChild(engine);
            car.appendChild(wheels);
            car.appendChild(fluids);

            document.appendChild(car);

            //send file to be written | returns true if successful else false
            return WriteFile(document, path);
        }catch (Exception e){
            //TODO Handle Exception
        }
        return false;
    }

    //Reads xml file based off the path sent and parses then returns a CarValues object
    //TODO Refactor
    public CarValues ParseCarXMLFile(String path) {
        //reads in xml file
        Document document = ReadFile(path);
        return ParseCarXMLFile(document);
    }

    public CarValues ParseCarXMLFile(Document document){
        if (document != null){
            CarValues carValues = new CarValues();
            // System.out.println("Starting");
            NodeList carSectionNodeList = document.getElementsByTagName("carSection");

            for (int i = 0; i < carSectionNodeList.getLength(); i++ ) {
                // System.out.println("cycling through xml");
                Node node = carSectionNodeList.item(i);
                // System.out.println(node.getNodeName().toString());

                //check node type is correct so we can safely cast it
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element)node;

                    if(element.getAttribute("attr").equals("engine")){
                        // System.out.println("Populating Engine");
                        // System.out.println(element.getElementsByTagName("airFilter").item(0).getTextContent());
                        // System.out.println(element.getElementsByTagName("alternator").item(0).getTextContent());
                        carValues.setAirFilter(Integer.parseInt(element.getElementsByTagName("airFilter").item(0).getTextContent()));
                        carValues.setTimingBelts(Integer.parseInt(element.getElementsByTagName("timingBelts").item(0).getTextContent()));
                        carValues.setSolenoid(Integer.parseInt(element.getElementsByTagName("solenoid").item(0).getTextContent()));
                        carValues.setAlternator(Integer.parseInt(element.getElementsByTagName("alternator").item(0).getTextContent()));
                        carValues.setStarter(Integer.parseInt(element.getElementsByTagName("starter").item(0).getTextContent()));
                    }

                    if(element.getAttribute("attr").equals("wheels")){
                        // System.out.println("Populating Wheels");
                        carValues.setDiscBrakes(Integer.parseInt(element.getElementsByTagName("disc").item(0).getTextContent()));
                        carValues.setDrumBrakes(Integer.parseInt(element.getElementsByTagName("drum").item(0).getTextContent()));
                        carValues.setTimingBelts(Integer.parseInt(element.getElementsByTagName("rotors").item(0).getTextContent()));
                        carValues.setSolenoid(Integer.parseInt(element.getElementsByTagName("tires").item(0).getTextContent()));
                        carValues.setRotation(Integer.parseInt(element.getElementsByTagName("rotate").item(0).getTextContent()));
                        carValues.setStarter(Integer.parseInt(element.getElementsByTagName("alignment").item(0).getTextContent()));
                    }

                    if(element.getAttribute("attr").equals("fluids")){
                        // System.out.println("Populating fluids");
                        // carValues.setAutoTransmission(Integer.parseInt(element.getElementsByTagName("transmission").item(0).getTextContent()));
                        carValues.setAutoTransmission(Integer.parseInt(element.getElementsByTagName("automatic").item(0).getTextContent()));
                        carValues.setManualTransmission(Integer.parseInt(element.getElementsByTagName("manual").item(0).getTextContent()));
                        carValues.setOil(Integer.parseInt(element.getElementsByTagName("oil").item(0).getTextContent()));
                        carValues.setBrakeFluid(Integer.parseInt(element.getElementsByTagName("brake").item(0).getTextContent()));
                        carValues.setRadiator(Integer.parseInt(element.getElementsByTagName("radiator").item(0).getTextContent()));
                        carValues.setPowerSteering(Integer.parseInt(element.getElementsByTagName("powerSteering").item(0).getTextContent()));
                        carValues.setCoolant(Integer.parseInt(element.getElementsByTagName("coolant").item(0).getTextContent()));
                    }
                }
            }
            return carValues;
        }
        return null;
    }

    public CarValues ParseCarXMLHandler(XmlResourceParser xmlResourceParser) {
        if (xmlResourceParser != null) {
            CarValues carValues = new CarValues();
            String value = null;
            int eventType;
            // System.out.println("Starting");
            try {
                eventType = xmlResourceParser.getEventType();

                while(eventType != xmlResourceParser.END_DOCUMENT){
                    String tagName = xmlResourceParser.getName();

                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            break;
                        case XmlPullParser.TEXT:
                            value = xmlResourceParser.getText();
                            break;
                        case XmlPullParser.END_TAG:
                            switch (tagName) {
                                case "TimingBelts":
                                    carValues.setTimingBelts(Integer.parseInt(value));
                                    break;
                                case "solenoid":
                                    carValues.setSolenoid(Integer.parseInt(value));
                                    break;
                                case "alternator":
                                    carValues.setAlternator(Integer.parseInt(value));
                                    break;
                                case "starter":
                                    carValues.setStarter(Integer.parseInt(value));
                                    break;
                                case "airFilter":
                                    carValues.setAirFilter(Integer.parseInt(value));
                                    break;
                                case "disc":
                                    carValues.setDiscBrakes(Integer.parseInt(value));
                                    break;
                                case "drum":
                                    carValues.setDrumBrakes(Integer.parseInt(value));
                                    break;
                                case "rotors":
                                    carValues.setRotors(Integer.parseInt(value));
                                    break;
                                case "tires":
                                    carValues.setTires(Integer.parseInt(value));
                                    break;
                                case "rotate":
                                    carValues.setRotation(Integer.parseInt(value));
                                    break;
                                case "automatic":
                                    carValues.setAutoTransmission(Integer.parseInt(value));
                                    break;
                                case "manual":
                                    carValues.setManualTransmission(Integer.parseInt(value));
                                    break;
                                case "radiator":
                                    carValues.setRadiator(Integer.parseInt(value));
                                    break;
                                case "powerSteering":
                                    carValues.setPowerSteering(Integer.parseInt(value));
                                    break;
                                case "coolant":
                                    carValues.setCoolant(Integer.parseInt(value));
                                    break;
                                default:
                                    break;
                            }
                        default:
                            break;
                    }
                    eventType = xmlResourceParser.next();
                }
                return carValues;
            } catch (Exception ex){
                // TODO Handle Exceptions
            }
        }
        return null;
    }
}