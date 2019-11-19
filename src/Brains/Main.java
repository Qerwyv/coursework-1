package Brains;

import java.util.Scanner;

public class Main extends Report {
	public static class BorderException extends Exception {

		public BorderException(double a, double b) {
			Change(a, b);
		}

		public void Change(double a, double b) {
			double tmp = 0;
			tmp = a;
			a = b;
			b = tmp;
		}

		public BorderException(String string) {
			System.out.print(string);
		}
	}

	public static void main(String[] args) throws FileException, BorderException {
		Scanner s = new Scanner(System.in);
		System.out.println("Введіть кількість точок для функції F = ");
		int countF = s.nextInt();
		double[] arrayFX = new double[countF];
		double[] arrayFY = new double[countF];
		for (int i = 0; i < countF; i++) {
			System.out.println("\nВведіть точку " + i + " x = ");
			arrayFX[i] = s.nextDouble();
			System.out.println("y = ");
			arrayFY[i] = s.nextDouble();
		}
		FunctionF_Array fFunc = new FunctionF_Array(arrayFX, arrayFY);
		int countG = s.nextInt();
		double[] arrayGX = new double[countG];
		double[] arrayGY = new double[countG];
		for (int i = 0; i < countG; i++) {
			System.out.println("\nВведіть точку " + i + " x = ");
			arrayGX[i] = s.nextDouble();
			System.out.println("y = ");
			arrayGY[i] = s.nextDouble();
		}
		FunctionG_Array gFunc = new FunctionG_Array(arrayGX, arrayGY);
		Main solution = new Main();
		solution.setFG(fFunc, gFunc);
		System.out.println("Введіть ліву границю");
		double a = s.nextDouble();
		System.out.println("\nВведіть праву границю");
		double b = s.nextDouble();
		try {
			if (a > b) {
				throw new BorderException(a, b);
			}
		} catch (Exception e) {
			System.out.println("Неправильне значення границь, змінено значення");
		}
		double eps = 0.01;
		
		try {
			solution.solve(a, b, eps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(solution);
		XMLStructure str = new XMLStructure();
		str.setPointF(arrayFX, arrayFY);
		str.setPointG(arrayGX, arrayGY);
		XML.writeToXml("data1", str);
	}
}
