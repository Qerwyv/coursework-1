package Brains;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Report extends AbstractDich {
	@SuppressWarnings("serial")
	public static class FileException extends Exception {
		private String fileName;

		public FileException(String fileName) {
			this.fileName = fileName;
		}

		public String getFileName() {
			return fileName;
		}
	}

	@SuppressWarnings("serial")
	public static class FileReadException extends FileException {
		public FileReadException(String fileName) {
			super(fileName);
		}
	}

	@SuppressWarnings("serial")
	public static class FileWriteException extends FileException {
		public FileWriteException(String fileName) {
			super(fileName);
		}
	}

	public Report saveReport(String fileName, String imageName, FunctionF_Array fFunc, FunctionG_Array gFunc,
							 AbstractDich solution, double t) throws FileWriteException {
		try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))) {
			out.printf("<html>%n");

			out.printf("<head>%n");
			out.printf("<meta http-equiv='Content-Type' content='text/html; " + "charset=UTF-8'>%n");
			out.printf("</head>%n");
			out.printf("<body>%n");
			out.printf("<h2>Звіт</h2>%n");
			out.printf("</table>%n");
			out.printf("<h4>Дані для функції <span style='font-family:Times, Serif;'>" + "<em>f(t)</em></span></h4>%n");
			out.printf("<table border = '1' cellpadding=4 cellspacing=0>%n");
			out.printf("<tr>%n");
			out.printf("<th>Індекс</th>%n");
			out.printf("<th>x</th>%n");
			out.printf("<th>y</th>%n");
			out.printf("</tr>%n");
			out.printf("<td>%n");
			for (int i = 0; i < fFunc.getArrayX().length; i++) {
				out.printf("<tr>%n");
				out.printf("<td>%d</td>", i);
				out.printf("<td>%8.3f</td>%n", fFunc.getArrayX()[i]);
				out.printf("<td>%8.3f</td>%n", fFunc.getArrayY()[i]);
				out.printf("</tr>%n");
			}
			out.printf("</table>%n");
			out.printf("<h4>Дані для функції <span style='font-family:Times, Serif;'>" + "<em>g(t)</em></span></h4>%n");
			out.printf("<table border = '1' cellpadding=4 cellspacing=0>%n");
			out.printf("<tr>%n");
			out.printf("<th>Індекс</th>%n");
			out.printf("<th>x</th>%n");
			out.printf("<th>y</th>%n");
			for (int i = 0; i < gFunc.getArrayX().length; i++) {
				out.printf("<tr>%n");
				out.printf("<td>%d</td>", i);
				out.printf("<td>%8.3f</td>%n", gFunc.getArrayX()[i]);
				out.printf("<td>%8.3f</td>%n", gFunc.getArrayY()[i]);
				out.printf("</tr>%n");
			}
			out.printf("</table>%n");
			out.printf("<p>Точність: <em>t</em>: %8.3f</p>%n", t);
			out.printf("<p>Був отриманий такий мінімум:</p>%n");
			for (Double root : solution.getRoots()) {
				out.printf("%8.3f<br>%n", root);
			}
			if (imageName != null) {
				out.printf("<img src = \"" + imageName + "\"/>");
			}
			out.printf("</body>%n");
			out.printf("</html>%n");
			return this;
		} catch (IOException e) {
			throw new FileWriteException(fileName);
		}
	}
}
