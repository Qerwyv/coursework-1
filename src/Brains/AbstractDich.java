package Brains;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDich {
	private ExtendedFunction f;
	private ExtendedFunction g;
	private List<Double> roots = new ArrayList<Double>();

	public void setRoots(double a) {
		roots.add(a);
	}

	public AbstractDich clearRoots() {
		roots.clear();
		return this;
	}

	public ExtendedFunction getF() {
		return f;
	}

	public ExtendedFunction getG() {
		return g;
	}

	public List<Double> getRoots() {
		return roots;
	}

	public AbstractDich setF(ExtendedFunction f) {
		this.f = f;
		return clearRoots();
	}

	public AbstractDich setG(ExtendedFunction g) {
		this.g = g;
		return clearRoots();
	}

	public AbstractDich setFG(ExtendedFunction f, ExtendedFunction g) {
		this.f = f;
		this.g = g;
		return clearRoots();
	}

	public AbstractDich solve(double leftBorder, double rightBorder, double eps){
        double x;
    	double sigma = eps/2-eps/4;
    	int i = 1;
        do {
            x = (leftBorder + rightBorder) / 2;
            double l = x - sigma;
            double r = x + sigma;
            if ((f.applyAsDouble(l) - g.applyAsDouble(l)) >=  (f.applyAsDouble(r)-g.applyAsDouble(r)))
            {
                rightBorder = r;
            }
            else if ((f.applyAsDouble(l) - g.applyAsDouble(l)) <  (f.applyAsDouble(r)-g.applyAsDouble(r)))
            {
                leftBorder = l;
            }
            System.out.println("Step " + i + " = " + x);
            i++;
        } while (Math.abs(leftBorder - rightBorder) > eps);
        roots.add(x);
        return this;
	}

	@Override
	public String toString() {
		if (getRoots().size() == 0) {
			return "Немає корнів";
		}
		if (getRoots() != null) {
			return "Корні функції =" + getRoots().size() + getRoots();
		}
		return "Помилка";
	}
}
