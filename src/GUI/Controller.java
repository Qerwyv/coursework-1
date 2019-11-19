package GUI;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.DoubleStringConverter;
import Brains.FunctionF_Array;
import Brains.FunctionG_Array;
import Brains.Report;
import Brains.XML;
import Brains.FunctionF_XML;
import Brains.FunctionG_XML;
import Brains.XMLStructure;
import Brains.Main;

public class Controller implements Initializable {
	@FXML
	TableView<FunctionF_XML> tableViewF;
	@FXML
	TableView<FunctionG_XML> tableViewG;
	@FXML
	TableColumn<FunctionF_XML, Double> tableColumnFX;
	@FXML
	TableColumn<FunctionF_XML, Double> tableColumnFY;
	@FXML
	TableColumn<FunctionG_XML, Double> tableColumnGX;
	@FXML
	TableColumn<FunctionG_XML, Double> tableColumnGY;
	@FXML
	TextArea TextAreaRoots;
	@FXML
	TextField TextFieldA;
	@FXML
	TextField TextFieldB;
	@FXML
	TextField TextFieldAcurancy;
	private ObservableList<FunctionF_XML> observableListF;
	private ObservableList<FunctionG_XML> observableListG;
	FunctionF_Array fFunc;
	FunctionG_Array gFunc;
	Main Solution;
	Report Rep;

	@FXML
	private LineChart<Number, Number> Line;
	private XYChart.Series series = new XYChart.Series();
	private XYChart.Series series1 = new XYChart.Series();
	private XYChart.Series series2 = new XYChart.Series();
	private XYChart.Series series3 = new XYChart.Series();
	private XYChart.Series series4 = new XYChart.Series();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		doNew();
		tableViewF.setPlaceholder(new Label(""));
		tableViewG.setPlaceholder(new Label(""));
	}

	public static void showMessage(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText(message);
		alert.showAndWait();
	}

	public static void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Помилка");
		alert.setHeaderText(message);
		alert.showAndWait();
	}

	public static FileChooser getFileChooser(String title) {
		FileChooser fileChooser = new FileChooser();
		// Починаємо шукати з поточної теки:
		fileChooser.setInitialDirectory(new File("."));
		// Встановлюємо фільтри для пошуку файлів:
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML-файли (*.xml)", "*.xml"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Усі файли (*.*)", "*.*"));
		// Вказуемо заголовк вікна:
		fileChooser.setTitle(title);
		return fileChooser;
	}

	public void doSave() {
		FileChooser fileChooser = getFileChooser("Зберегти XML-файл");
		File file;
		if ((file = fileChooser.showSaveDialog(null)) != null) {
			try {
				updateSourceData(); // оновлюємо дані в моделі
				XMLStructure str = new XMLStructure();
				str.setPointF(fFunc.getArrayX(), fFunc.getArrayY());
				str.setPointG(gFunc.getArrayX(), gFunc.getArrayY());
				XML.writeToXml(file.getCanonicalPath(), str);
				showMessage("Результати успішно збережені");
			} catch (IOException e) {
				showError("Помилка запису в файл");
			}
		}
	}

	public void doExit() {
		Platform.exit();
	}

	public void doAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Про програму...");
		alert.setHeaderText("Побудова графіку різниці двох функцій ");
		alert.setContentText(" ПЗ виконує розв'язання рівняння типу"
				+ "f(x)-g(x)->max методом дихотомії. Функції задаються поліномами Лагранжа");
		alert.showAndWait();
	}

	public void doHelp() {
		Desktop desktop = null;
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
		}
		try {
			desktop.open(new File("Main.html"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void doNew() {
		fFunc = new FunctionF_Array();
		gFunc = new FunctionG_Array();
		Solution = new Main();
		Rep = new Report();
		TextFieldA.setText("");
		TextFieldB.setText("");
		TextFieldAcurancy.setText("");
		TextAreaRoots.setText("");
		tableViewF.setItems(null);
		tableViewF.setPlaceholder(new Label(""));
		tableViewG.setItems(null);
		tableViewG.setPlaceholder(new Label(""));
		observableListF = null;
		series = new XYChart.Series();
		series1 = new XYChart.Series();
		series2 = new XYChart.Series();
		series3= new XYChart.Series();
		series4 = new XYChart.Series();
		Line.getData().clear();
		updateTables();
	}

	public void doOpen() {
		FileChooser fileChooser = getFileChooser("Відкрити XML-файл");
		File file;
		if ((file = fileChooser.showOpenDialog(null)) != null) {
			try {
				XMLStructure str = new XMLStructure();
				str = XML.readFromXml(file.getCanonicalPath());
				Solution = new Main();
				fFunc = new FunctionF_Array(str.getFXPoints(), str.getFYPoints());
				gFunc = new FunctionG_Array(str.getGXPoints(), str.getGYPoints());
				TextAreaRoots.setText("");
				tableViewF.refresh();
				tableViewG.refresh();
				updateTables();
				showMessage("Файл був успішно відкритий");
			} catch (IOException e) {
				showError("Файл не знайдено");
			}
			catch(Exception  e1) {
				showError("Помилка читання файлу");
			}
		}
	}

	private void updateFX(CellEditEvent<FunctionF_XML, Double> t) {
		FunctionF_XML c = (FunctionF_XML) t.getTableView().getItems().get(t.getTablePosition().getRow());
		c.setX(t.getNewValue());
	}

	private void updateFY(CellEditEvent<FunctionF_XML, Double> t) {
		FunctionF_XML c = (FunctionF_XML) t.getTableView().getItems().get(t.getTablePosition().getRow());
		c.setY(t.getNewValue());
	}

	private void updateGX(CellEditEvent<FunctionG_XML, Double> t) {
		FunctionG_XML c = (FunctionG_XML) t.getTableView().getItems().get(t.getTablePosition().getRow());
		c.setX(t.getNewValue());
	}

	private void updateGY(CellEditEvent<FunctionG_XML, Double> t) {
		FunctionG_XML c = (FunctionG_XML) t.getTableView().getItems().get(t.getTablePosition().getRow());
		c.setY(t.getNewValue());
	}


	public void generateReport () {
		double a = Double.parseDouble(TextFieldA.getText());
		double b = Double.parseDouble(TextFieldB.getText());
		double exp = Double.parseDouble(TextFieldAcurancy.getText());

		Rep = new Report();
		Solution.setFG(fFunc, gFunc);
		Solution.solve(a, b, exp);
		try {
			ImageIO.write(grabScreen().getSubimage(671, 135, 581,770), "png",
					new File("D:\\million\\projects [Java & OOP]\\coursework", "screen.png"));
		} catch (IOException | HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Rep.saveReport("Result.html", "screen.png", fFunc, gFunc, Solution, exp);
		} catch (Report.FileWriteException e) {
			e.printStackTrace();
		}
		showMessage("Звіт у форматі .html був успішно збережений");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })

	@FXML
	public void doDelRowF(ActionEvent event) {
		FunctionF_XML selectedItem = tableViewF.getSelectionModel().getSelectedItem();
		tableViewF.getItems().remove(selectedItem);
		observableListF.remove(selectedItem);
		for (int i = 0; i < fFunc.getArrayX().length; i++) {
			if (fFunc.getArrayX()[i] == selectedItem.getX()) {
				fFunc.setArrayX(remove(fFunc.getArrayX(), i));
				fFunc.setArrayY(remove(fFunc.getArrayY(), i));
			}
		}
	}

	@FXML
	public void doDelRowG(ActionEvent event) {
		FunctionG_XML selectedItem = tableViewG.getSelectionModel().getSelectedItem();
		tableViewG.getItems().remove(selectedItem);
		observableListG.remove(selectedItem);
		for (int i = 0; i < gFunc.getArrayX().length; i++) {
			if (gFunc.getArrayX()[i] == selectedItem.getX()) {
				gFunc.setArrayX(remove(gFunc.getArrayX(), i));
				gFunc.setArrayY(remove(gFunc.getArrayY(), i));
			}
		}
	}

	public static double[] remove(double[] symbols, int index) {
		if (index >= 0 && index < symbols.length) {
			double[] copy = new double[symbols.length - 1];
			System.arraycopy(symbols, 0, copy, 0, index);
			System.arraycopy(symbols, index + 1, copy, index, symbols.length - index - 1);
			return copy;
		}
		return symbols;
	}

	@FXML
	public void doAddF(ActionEvent event) {
		if (observableListF == null) {
			updateTables(); // створюємо нові дані
		}
		fFunc.addPoint(0, 0);
		observableListF.add(new FunctionF_XML(0, 0));

	}

	@FXML
	public void doAddG(ActionEvent event) {
		if (observableListG == null) {
			updateTables(); // створюємо нові дані
		}
		gFunc.addPoint(0, 0);
		observableListG.add(new FunctionG_XML(0, 0));

	}

	@FXML
	public void doCreate() {
		try {
			XYChart.Series dot = new XYChart.Series();
			series = new XYChart.Series();
			series1 = new XYChart.Series();
			series2 = new XYChart.Series();
			series3 = new XYChart.Series();
			series4 = new XYChart.Series();
			updateSourceData();
			double a = Double.parseDouble(TextFieldA.getText());
			double b = Double.parseDouble(TextFieldB.getText());
			double exp = Double.parseDouble(TextFieldAcurancy.getText());
			if (exp <= 0) {
				exp = 0.001;
				showError("Хибне значення точності.\n Значення змінено на 0.001");
				TextFieldAcurancy.setText("0.001");
			}
			try {
				if (a > b) {
					throw new Main.BorderException(a, b);
				}
			} catch (Exception e) {
				showMessage("Границі інтервалу введені некоректно");
				TextFieldA.setText(String.valueOf(b));
				TextFieldB.setText(String.valueOf(a));
				a = Double.parseDouble(TextFieldA.getText());
				b = Double.parseDouble(TextFieldB.getText());
			}
			try {
				for (double i = a; i <= b; i += 0.1) {
					series.getData().add(new XYChart.Data(i, fFunc.applyAsDouble(i) - gFunc.applyAsDouble(i)));
					series3.getData().add(new XYChart.Data(i, gFunc.applyAsDouble(i)));
					series4.getData().add(new XYChart.Data(i, fFunc.applyAsDouble(i)));
				}
				for(int i = 0; i < gFunc.getArrayX().length;i++) {
					series1.getData().add(new XYChart.Data(gFunc.getX(i), gFunc.getY(i)));
				}
				for(int i = 0; i < fFunc.getArrayX().length;i++) {
					series2.getData().add(new XYChart.Data(fFunc.getX(i), fFunc.getY(i)));
				}
				Line.setAnimated(false);
				Line.setCreateSymbols(true);
				Line.getData().setAll(series1, series2, series, series3, series4);
				series1.setName("");
				series2.setName("");
				series.setName("f(x)-g(x)");
				series3.setName("g(x)");
				series4.setName("f(x)");
			} catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
				if (e instanceof ArrayIndexOutOfBoundsException)
					showError("Кількість точок g(x) має бути >=2");
				else
					showError("Помилка введення даних. Значення Х не повинні співпадати");
			}
			Solution.setFG(fFunc, gFunc);
			try {
				dot.setName("Max");
				Solution.solve(a, b, exp);
				double res = Solution.getRoots().get(0);
				dot.getData().add(new XYChart.Data(res, fFunc.applyAsDouble(res) - gFunc.applyAsDouble(res)));
				Line.getData().add(dot);
			} catch (Exception e) {
				showError("h(leftBodrer) * h(rightBorder) > 0");
			}
			TextAreaRoots.setText(Solution.getRoots().toString());
		} catch (NumberFormatException e1) {
			showError("Неправильно введені дані в поля значень інтервалу або точності");
		}

	}

	public void updateSourceData() {
		int i = 0;
		for (FunctionF_XML x : observableListF) {
			fFunc.setX(i, x.getX());
			fFunc.setY(i, x.getY());
			i++;
		}
		i = 0;
		for (FunctionG_XML x : observableListG) {
			gFunc.setX(i, x.getX());
			gFunc.setY(i, x.getY());
			i++;
		}
	}

	public void updateTables() {
		List<FunctionF_XML> listFFunc = new ArrayList<FunctionF_XML>();
		List<FunctionG_XML> listGFunc = new ArrayList<FunctionG_XML>();
		for (int i = 0; i < fFunc.getArrayX().length; i++) {
			listFFunc.add(new FunctionF_XML(fFunc.getX(i), fFunc.getY(i)));
			System.out.println(listFFunc.get(i).toString());
		}
		for (int i = 0; i < gFunc.getArrayX().length; i++) {
			listGFunc.add(new FunctionG_XML(gFunc.getX(i), gFunc.getY(i)));
			System.out.println(listGFunc.get(i).toString());
		}
		observableListF = FXCollections.observableArrayList(listFFunc);
		observableListG = FXCollections.observableArrayList(listGFunc);

		tableViewF.setItems(observableListF);
		tableViewG.setItems(observableListG);

		tableColumnFX.setCellValueFactory(new PropertyValueFactory<>("x"));
		tableColumnFX
				.setCellFactory(TextFieldTableCell.<FunctionF_XML, Double>forTableColumn(new DoubleStringConverter()));
		tableColumnFX.setOnEditCommit(t -> updateFX(t));
		tableColumnFY.setCellValueFactory(new PropertyValueFactory<>("y"));
		tableColumnFY
				.setCellFactory(TextFieldTableCell.<FunctionF_XML, Double>forTableColumn(new DoubleStringConverter()));
		tableColumnFY.setOnEditCommit(t -> updateFY(t));
		updateSourceData();
		tableColumnGX.setCellValueFactory(new PropertyValueFactory<>("x"));
		tableColumnGX
				.setCellFactory(TextFieldTableCell.<FunctionG_XML, Double>forTableColumn(new DoubleStringConverter()));
		tableColumnGX.setOnEditCommit(t -> updateGX(t));
		tableColumnGY.setCellValueFactory(new PropertyValueFactory<>("y"));
		tableColumnGY
				.setCellFactory(TextFieldTableCell.<FunctionG_XML, Double>forTableColumn(new DoubleStringConverter()));
		tableColumnGY.setOnEditCommit(t -> updateGY(t));
		updateSourceData();

	}

	private static BufferedImage grabScreen() {
		try {
			return new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		} catch (SecurityException e) {
		} catch (AWTException e) {
		}
		return null;
	}

}
