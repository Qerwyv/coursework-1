package Brains;

public class FunctionF_XML {
	
    private Double x;
    
    private Double y;

    @Override
	public String toString() {
		return " [x = " + x + ";\t y = " + y + "]";
	}

	public FunctionF_XML(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public FunctionF_XML() {

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
}

