package main;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextSearchController {

    TextSearchAlgorithms search = new TextSearchAlgorithms();
    String text;
    @FXML
    TextField txtFileName;
    @FXML
    TextArea txtPattern;
    @FXML
    TextArea txtResults;
    @FXML
    Button btnBuscar;
    @FXML
    Button btnLimpiar;
    @FXML
    RadioButton rbtnBRF;
    @FXML
    RadioButton rbtnKMP;
    @FXML
    RadioButton rbtnBMH;
    @FXML
    RadioButton rbtnBMS;

    public void loadFile(){
        FileChooser fileChooser = new FileChooser();
        StringBuilder data = new StringBuilder();
        File file = fileChooser.showOpenDialog(null);
        if(file!=null){
            try {
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    data.append(reader.nextLine());
                    data.append("\n");
                }
                text = data.toString();
                this.txtFileName.setText(file.getName());
                this.btnBuscar.setDisable(false);
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText( "NO SE SELECCIONO NINGUN ARCHIVO");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/main/icon.png").toString()));
            alert.showAndWait();
        }
    }

    public void search(){
        String pattern = txtPattern.getText();
        if(!pattern.isEmpty()){
            if(rbtnBRF.isSelected()){
                txtResults.setText(search.BRF(pattern, text));
            }else if(rbtnKMP.isSelected()){
                txtResults.setText(search.KMP(pattern, text));
            }else if(rbtnBMH.isSelected()){
                txtResults.setText(search.BMH(pattern, text));
            }else if(rbtnBMS.isSelected()){
                txtResults.setText(search.BMS(pattern, text));
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText( "SELECCIONE UNO DE LOS ALGORITMOS");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/main/icon.png").toString()));
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText( "INGRESE UN PATRON PARA BÚSQUEDA");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/main/icon.png").toString()));
            alert.showAndWait();
        }
    }

    public  void  clear(){
        this.btnBuscar.setDisable(true);
        txtResults.clear();
        txtFileName.clear();
        txtPattern.clear();
        rbtnBRF.setSelected(false);
        rbtnKMP.setSelected(false);
        rbtnBMH.setSelected(false);
        rbtnBMS.setSelected(false);
    }
}
