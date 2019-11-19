package Brains;

import java.util.Arrays;

public class FunctionG_Array extends AbstractG {
	private double[] arrayX;
	private double[] arrayY;

	public void setArrayX(double[] arrayX) {
		this.arrayX = arrayX;
	}

	public void setArrayY(double[] arrayY) {
		this.arrayY = arrayY;
	}

	public FunctionG_Array(double[] arrayX, double[] arrayY) {
		if (arrayX.length != arrayY.length) {
			throw new RuntimeException("wrong date");
		}
		this.arrayX = arrayX;
		this.arrayY = arrayY;
	}

	public FunctionG_Array() {
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
	public void setX(int i, double gx) {
		arrayX[i] = gx;
	}

	public void addPoint(double gx, double gy) {
		double[] Ax2 = new double[arrayX.length + 1];
		double[] Ay2 = new double[arrayY.length + 1];

		Ax2 = Arrays.copyOf(arrayX, Ax2.length);
		Ay2 = Arrays.copyOf(arrayY, Ay2.length);
		Ax2[Ax2.length - 1] = gx;
		Ay2[Ay2.length - 1] = gy;
		arrayX = Ax2;
		arrayY = Ay2;
	}

	@Override
	public void setY(int i, double gy) {
		arrayY[i] = gy;
	}

	@Override
	public void setXY(double gx, double gy) {
		throw new UnsupportedOperationException();
	}

	
}
