package imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class calculadoraimc extends Application {

	private Label pesoLabel = new Label("Peso:");
	private Label alturaLabel = new Label("Altura:");
	private Label magnitudPeso = new Label("kg");
	private Label magnitudAlt = new Label("cm");
	private Label imcLabel = new Label("IMC: ");
	private Label imcForm = new Label("(peso*altura^2)");
	private Label resultadoLabel = new Label("Bajo peso | Normal | Sobrepeso | Obeso");
	private TextField pesoField = new TextField();
	private TextField alturaField = new TextField();
	private DoubleProperty pesoProperty;
	private DoubleProperty alturaProperty;
	private DoubleProperty imcProperty;
	private HBox pesoBox = new HBox(5);
	private HBox alturaBox = new HBox(5);
	private HBox imcBox = new HBox(5);
	private VBox rootBox = new VBox(5);

	@Override
	public void start(Stage primaryStage) throws Exception {

		pesoBox.getChildren().addAll(pesoLabel, pesoField, magnitudPeso);
		pesoBox.setAlignment(Pos.CENTER);
		alturaBox.getChildren().addAll(alturaLabel, alturaField, magnitudAlt);
		alturaBox.setAlignment(Pos.CENTER);
		imcBox.getChildren().addAll(imcLabel, imcForm);
		imcBox.setAlignment(Pos.CENTER);
		rootBox.getChildren().addAll(pesoBox, alturaBox, imcBox, resultadoLabel);
		rootBox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(rootBox, 320, 200);

		bindeosImc();

		primaryStage.setTitle("IMC");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void bindeosImc() {
		pesoProperty = new SimpleDoubleProperty();
		alturaProperty = new SimpleDoubleProperty();
		imcProperty = new SimpleDoubleProperty();
		Bindings.bindBidirectional(pesoField.textProperty(), pesoProperty, new NumberStringConverter());
		Bindings.bindBidirectional(alturaField.textProperty(), alturaProperty, new NumberStringConverter());
		imcProperty.bind(pesoProperty.divide((alturaProperty.divide(100)).multiply(alturaProperty.divide(100))));
		imcForm.textProperty().bind(imcProperty.asString("%.2f"));

		imcForm.textProperty().addListener(e -> {
			if (imcProperty.doubleValue() < 18.5)
				resultadoLabel.setText("Bajo peso");
			else if (imcProperty.doubleValue() >= 18.5 && imcProperty.doubleValue() < 25)
				resultadoLabel.setText("Normal");
			else if (imcProperty.doubleValue() >= 25 && imcProperty.doubleValue() < 30)
				resultadoLabel.setText("Sobrepeso");
			else if (imcProperty.doubleValue() > 30)
				resultadoLabel.setText("Obeso");
		});

	}


}