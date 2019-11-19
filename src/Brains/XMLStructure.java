package Brains;

import java.util.Arrays;


public class XMLStructure {


	FunctionF_XML[] fFunc;
	FunctionG_XML[] gFunc;


	public FunctionF_XML[] getPointF() {
		return fFunc;
	}

	public double[] getFXPoints() {
		double[] x = new double[fFunc.length];
		for (int i = 0; i < fFunc.length; i++) {
			x[i] = fFunc[i].getX();
		}
		return x;
	}

	public double[] getFYPoints() {
		double[] y = new double[fFunc.length];
		for (int i = 0; i < fFunc.length; i++) {
			y[i] = fFunc[i].getY();

		}
		return y;
	}

	public void setPointF(double[] arrayX, double[] arrayY) {
		this.fFunc = new FunctionF_XML[arrayX.length];
		for (int i = 0; i < fFunc.length; i++) {
			fFunc[i] = new FunctionF_XML(arrayX[i], arrayY[i]);
		}

	}

	public XMLStructure() {
	}

	public FunctionG_XML[] getPoint() {
		return gFunc;
	}

	public double[] getGXPoints() {
		double[] x = new double[gFunc.length];
		for (int i = 0; i < gFunc.length; i++) {
			x[i] = gFunc[i].getX();

		}
		return x;
	}

	public double[] getGYPoints() {
		double[] y = new double[gFunc.length];
		for (int i = 0; i < gFunc.length; i++) {
			y[i] = gFunc[i].getY();

		}
		return y;
	}

	public void setPointG(double[] arrayX, double[] arrayY) {
		this.gFunc = new FunctionG_XML[arrayX.length];
		for (int i = 0; i < gFunc.length; i++) {
			gFunc[i] = new FunctionG_XML(arrayX[i], arrayY[i]);
		}

	}

	@Override
	public String toString() {
		return "XMLStructure [Points of F = " + Arrays.toString(fFunc) + ",\t" + " points of Y = "
				+ Arrays.toString(gFunc) + "]";
	}
}
