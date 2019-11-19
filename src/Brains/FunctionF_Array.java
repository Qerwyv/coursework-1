package Brains;

import java.util.Arrays;

public class FunctionF_Array extends AbstractF{
	private double[] arrayX;
	private double[] arrayY;
	
	public void setArrayX(double[] arrayX) {
		this.arrayX = arrayX;
	}
	
	public void setArrayY(double[] arrayY) {
		this.arrayY = arrayY;
	}

	public FunctionF_Array(double[] arrayX, double[] arrayY) {
		if (arrayX.length != arrayY.length) {
			throw new RuntimeException("wrong date");
		}
		this.arrayX = arrayX;
		this.arrayY = arrayY;
	}

	public FunctionF_Array() {
		arrayX = new double[0];
		arrayY = new double[0];
	}

	@Override
	public int xyCount() {
		return arrayX.length;
	}

	@Override
	public double getX(int i) {
		return arrayX[i];
	}

	public double[] getArrayX() {
		return arrayX;
	}

	public double[] getArrayY() {
		return arrayY;
	}

	@Override
	public double getY(int i) {
		return arrayY[i];
	}

	@Override
	public void setX(int i, double x) { arrayX[i] = x; }

	public void addPoint(double fx, double fy) {
		double[] Ax2 = new double[arrayX.length + 1];
		double[] Ay2 = new double[arrayY.length + 1];

		Ax2 = Arrays.copyOf(arrayX, Ax2.length);
		Ay2 = Arrays.copyOf(arrayY, Ay2.length);
		Ax2[Ax2.length - 1] = fx;
		Ay2[Ay2.length - 1] = fy;
		arrayX = Ax2;
		arrayY = Ay2;
	}

	@Override
	public void setY(int i, double fy) {
		arrayY[i] = fy;
	}

	@Override
	public void setXY(double fx, double fy) {
		throw new UnsupportedOperationException();
	}

}
