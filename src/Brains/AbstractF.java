package Brains;

public abstract class AbstractF implements ExtendedFunction {
	
	public abstract int xyCount();

	public abstract double getX(int i);
	public abstract double getY(int i);

	public abstract void setX(int i, double x);
	public abstract void setY(int i, double y);

	public abstract void setXY(double x, double y);

	@Override
	public double applyAsDouble(double x) {

		for (int i = 0; i < xyCount(); i++) {
			for (int j = 0; j < xyCount(); j++) {
				if (i != j) {
					if (getX(i) == getX(j)) {
						throw new ArithmeticException("the same x value->division for ziro");
					}

				}
			}
		}
		double lagrange = 0;
		double h = getX(1) - getX(0);
		for (int i = 0; i < xyCount(); i++) {
			double tmp = 1;
			for (int j = 0; j < xyCount(); j++) {
				if (i != j)
					tmp *= (x - getX(0) - h * j) / h / (i - j);
			}
			lagrange += tmp * getY(i);
		}
		return lagrange;
	}
}
