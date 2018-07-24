package net.thomaspreis.app.dfc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.thomaspreis.app.dfc.base.ConsoleTextAreaSingleton;
import net.thomaspreis.app.dfc.base.CopyProcessorManager;
import net.thomaspreis.app.dfc.config.AppConfigManager;
import net.thomaspreis.app.dfc.domain.AppResourcesEnum;
import net.thomaspreis.app.dfc.model.ExcelSheetConfigModel;
import net.thomaspreis.app.dfc.model.MainGridModel;

public class App extends Application {

	private static final Logger LOGGER = Logger.getLogger(App.class);

	private static final String APP_VERSION = "0.0.4-SNAPSHOT";

	private AppConfigManager appConfigManager;
	private MainGridModel mainGridModel;

	final BlockingQueue<String> messageQueue = new ArrayBlockingQueue<>(1);

	private void setupApp() {
		this.appConfigManager = new AppConfigManager();
		this.mainGridModel = this.appConfigManager.getMainGridModel();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		setupApp();

		primaryStage.setTitle("Djou File Copier - v" + APP_VERSION);
		// Create the registration form grid pane
		GridPane gridPane = createRegistrationFormPane();
		// Add UI controls to the registration form grid pane
		addUIControls(gridPane);
		// Create a scene with registration form grid pane as the root node
		Scene scene = new Scene(gridPane, 1200, 500);
		// Set the scene in primary stage
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	private GridPane createRegistrationFormPane() {
		// Instantiate a new Grid Pane
		final GridPane gridPane = new GridPane();

		// Position the pane at the center of the screen, both vertically and
		// horizontally
		gridPane.setAlignment(Pos.CENTER);

		// Set a padding of 20px on each side
		gridPane.setPadding(new Insets(40, 40, 40, 40));

		// Set the horizontal gap between columns
		gridPane.setHgap(10);

		// Set the vertical gap between rows
		gridPane.setVgap(10);

		// Add Column Constraints

		// columnOneConstraints will be applied to all the nodes placed in
		// column one.
		ColumnConstraints columnOneConstraints = new ColumnConstraints(150, 100, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.RIGHT);

		// columnTwoConstraints will be applied to all the nodes placed in
		// column two.
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);

		gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

		return gridPane;
	}

	private void addUIControls(final GridPane gridPane) {
		int rowNum = 1;
		// Add Header
		final Label headerLabel = new Label("Djou File Copier - v" + APP_VERSION);
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		gridPane.add(headerLabel, 0, 0, 2, 1);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

		// Add source excel folder Label
		Label sourceExcelFolderLabel = new Label(AppResourcesEnum.EXCEL_FOLDER_LABEL.getValue());
		gridPane.add(sourceExcelFolderLabel, 0, rowNum);

		// Add source excel folder Text Field
		final TextField sourceExcelFolderField = new TextField();
		sourceExcelFolderField.setText(this.mainGridModel.getSourceExcelFolder());
		gridPane.add(sourceExcelFolderField, 1, rowNum++);

		// Add file extension Label
		Label sourceFileExtensionLabel = new Label(AppResourcesEnum.FILE_TYPE_LABEL.getValue());
		gridPane.add(sourceFileExtensionLabel, 0, rowNum);

		// Add source file extension Field
		final TextField sourceFileExtensionField = new TextField();
		sourceFileExtensionField.setText(this.mainGridModel.getFileExtension());
		gridPane.add(sourceFileExtensionField, 1, rowNum++);

		// Add source folder Label
		final Label sourceFolderLabel = new Label(AppResourcesEnum.SOURCE_FOLDER_LABEL.getValue());
		gridPane.add(sourceFolderLabel, 0, rowNum);

		// Add source folder Text Field
		final TextField sourceFolderField = new TextField();
		sourceFolderField.setText(this.mainGridModel.getSourceFolder());
		gridPane.add(sourceFolderField, 1, rowNum++);

		// Add target folder Label
		final Label targetFolderLabel = new Label(AppResourcesEnum.TARGET_FOLDER_LABEL.getValue());
		gridPane.add(targetFolderLabel, 0, rowNum);

		// Add target folder Field
		final TextField targetFolderField = new TextField();
		targetFolderField.setText(this.mainGridModel.getTargetFolder());
		gridPane.add(targetFolderField, 1, rowNum++);

		// Add Submit Button
		final Button submitButton = new Button(AppResourcesEnum.PROCESS_BUTTON.getValue());
		submitButton.setPrefHeight(10);
		submitButton.setDefaultButton(true);
		submitButton.setPrefWidth(100);
		gridPane.add(submitButton, 0, rowNum++, 2, 1);

		final TextArea consoleTextArea = new TextArea();
		consoleTextArea.setEditable(Boolean.FALSE);
		gridPane.add(consoleTextArea, 0, rowNum++, 2, 1);

		GridPane.setHalignment(submitButton, HPos.CENTER);
		GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (sourceExcelFolderField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
							"Ah, voce esqueceu de informar o " + sourceExcelFolderLabel.getText());
					return;
				}
				if (sourceFolderField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
							"Hey, voce precisa informar o " + sourceFolderLabel.getText());
					return;
				}
				if (sourceFileExtensionField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
							"Bitcho! voce precisa informar o " + sourceFileExtensionLabel.getText());
					return;
				}
				if (targetFolderField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
							"Pera aí, faltou informar o " + targetFolderLabel.getText());
					return;
				}

				consoleTextArea.setText("");

				CopyProcessorManagerProducer producer = new CopyProcessorManagerProducer(messageQueue);
				producer.excelCfg = appConfigManager.getExcelSheetConfigModel();
				producer.sourceExcelFolder = sourceExcelFolderField.getText();
				producer.fileExtension = sourceFileExtensionField.getText();
				producer.sourceFolder = sourceFolderField.getText();
				producer.targetFolder = targetFolderField.getText();
				Thread t = new Thread(producer);
				t.setDaemon(true);
				t.start();

				saveConfig(sourceExcelFolderField.getText(), sourceFileExtensionField.getText(),
						sourceFolderField.getText(), targetFolderField.getText());
			}
		});

		final LongProperty lastUpdate = new SimpleLongProperty();
		final long minUpdateInterval = 0; // nanoseconds. Set to higher number
											// to slow output.
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (now - lastUpdate.get() > minUpdateInterval) {
					final String message = messageQueue.poll();
					if (message != null) {
						consoleTextArea.appendText(message + "\n");
					}
					lastUpdate.set(now);
				}
			}
		};
		timer.start();
	}

	private void saveConfig(String sourceExcelFolder, String fileExtension, String sourceFolder, String targetFolder) {
		// Update props
		this.mainGridModel.setFileExtension(fileExtension);
		this.mainGridModel.setSourceExcelFolder(sourceExcelFolder);
		this.mainGridModel.setSourceFolder(sourceFolder);
		this.mainGridModel.setTargetFolder(targetFolder);
		this.appConfigManager.setPropery(this.mainGridModel);
		this.appConfigManager.setPropery(this.appConfigManager.getExcelSheetConfigModel());
		this.appConfigManager.writeProperties();
	}

	private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}

	private static class CopyProcessorManagerProducer implements Runnable {
		String sourceExcelFolder;
		String fileExtension;
		String sourceFolder;
		String targetFolder;
		ExcelSheetConfigModel excelCfg;

		public CopyProcessorManagerProducer(BlockingQueue<String> messageQueue) {
			ConsoleTextAreaSingleton.getInstance().setMessageQueue(messageQueue);
		}

		@Override
		public void run() {
			CopyProcessorManager copyProcessorManager = new CopyProcessorManager(sourceExcelFolder, fileExtension,
					sourceFolder, targetFolder, excelCfg);
			try {
				copyProcessorManager.process();
			} catch (Exception e) {
				LOGGER.error(e);
				ConsoleTextAreaSingleton.getInstance().append(e.getLocalizedMessage());
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
