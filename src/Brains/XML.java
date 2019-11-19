package Brains;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.*;

public class XML {
	
	public static void writeToXml(String nameOfFile, XMLStructure m) {
		try {
			DichData data = new DichData();
			JAXBContext jaxbContext = JAXBContext.newInstance("Brains");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			data = (DichData) unmarshaller.unmarshal(new FileInputStream("start.xml"));
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			DichData.FXYCoefs.XYCoef tempF = new DichData.FXYCoefs.XYCoef();
			tempF.setFX(m.getFXPoints()[0]);
			tempF.setFY(m.getFYPoints()[0]);
			data.getFXYCoefs().getXYCoef().set(0, tempF);
			for(int i = 1; i < m.getFXPoints().length; i++) {
				tempF = new DichData.FXYCoefs.XYCoef();
				tempF.setFX(m.getFXPoints()[i]);
				tempF.setFY(m.getFYPoints()[i]);
				data.getFXYCoefs().getXYCoef().add(tempF);
			}
			DichData.GXYCoefs.XYCoef tempG = new DichData.GXYCoefs.XYCoef();
			System.out.println(m.getGXPoints()[0]);
			tempG.setGX(m.getGXPoints()[0]);
			tempG.setGY(m.getGYPoints()[0]);
			data.getGXYCoefs().getXYCoef().set(0, tempG);
			for(int i = 1; i < m.getGXPoints().length; i++) {
				tempG = new DichData.GXYCoefs.XYCoef();
				tempG.setGX(m.getGXPoints()[i]);
				tempG.setGY(m.getGYPoints()[i]);
				data.getGXYCoefs().getXYCoef().add(tempG);
			}
			marshaller.marshal(data, new FileWriter(nameOfFile + ".xml"));
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		} 

	}

	public static XMLStructure readFromXml(String nameOfFile) {
		try {
			DichData data = new DichData();
			JAXBContext jaxbContext = JAXBContext.newInstance("Brains");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			XMLStructure us = new XMLStructure();
			data = (DichData) unmarshaller.unmarshal(new FileInputStream(nameOfFile));
			double[] arrayFX = new double[data.getFXYCoefs().getXYCoef().size()];
			double[] arrayFY = new double[data.getFXYCoefs().getXYCoef().size()];
			for(int i = 0; i < data.getFXYCoefs().getXYCoef().size(); i++) {
				arrayFX[i] = data.getFXYCoefs().getXYCoef().get(i).getFX();
				arrayFY[i] = data.getFXYCoefs().getXYCoef().get(i).getFY();
			}
			us.setPointF(arrayFX, arrayFY);
			double[] arrayGX = new double[data.getGXYCoefs().getXYCoef().size()];
			double[] arrayGY = new double[data.getGXYCoefs().getXYCoef().size()];
			for(int i = 0; i < data.getGXYCoefs().getXYCoef().size(); i++) {
				arrayGX[i] = data.getGXYCoefs().getXYCoef().get(i).getGX();
				arrayGY[i] = data.getGXYCoefs().getXYCoef().get(i).getGY();
			}
			us.setPointG(arrayGX, arrayGY);
			return us;
		} catch (JAXBException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}