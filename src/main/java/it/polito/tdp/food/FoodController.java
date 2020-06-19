/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	
    	
    	try {
    		txtResult.appendText("Cerco cammino peso massimo...\n");
    		
	    	Integer passi = Integer.valueOf(this.txtPassi.getText());
	    	
	    	String sorgente = this.boxPorzioni.getValue();
	    	
	    	List<String> cammino = model.cercaCammino(sorgente, passi);
	    	
	    	if(cammino.size()>0) {
		    	for(String s : cammino) {
		    		this.txtResult.appendText(s+"\n");
		    	}
		    	this.txtResult.appendText("\n\nPeso cammino: "+model.pesoMax());
	    	}else {
	    		this.txtResult.appendText("Cammino non trovato");
	    	}
	    	
	    	
    	}catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserisci valore corretto");
    	}
    }
    	

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
   		
    	String porzione = this.boxPorzioni.getValue();
    	
    	this.txtResult.appendText("Porzioni direttamente connesse a "+porzione+":\n");
    	
    	List<Arco> collegati = this.model.listaCollegati(porzione);
    	for(Arco a: collegati) {
    		this.txtResult.appendText(a.getPorzione2()+"  "+a.getPeso()+"\n");
    	}
    	
    	
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...\n");
    	
    	try {
	    	Integer calorie = Integer.valueOf(this.txtCalorie.getText());
	    	
	    	model.creaGrafo(calorie);
	    	
	    	this.txtResult.appendText("#vertici: "+model.vertici().size()+"\n#archi: "+model.nArchi()+"\n");
	    	
	    	this.boxPorzioni.getItems().addAll(model.vertici());
	    	this.boxPorzioni.setValue(model.vertici().get(0));
	    	
    	}catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserisci valore corretto");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    
    }
}
