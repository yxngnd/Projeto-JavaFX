package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AlbumController implements Initializable{

	@FXML
	private TableView<Album> tableAlbum = new TableView<Album>();
	
	@FXML
	private TableColumn<Album, String> colNome = new TableColumn<Album, String>();
	
	@FXML
	private TableColumn<Album, String> colArt = new TableColumn<Album, String>();
	
	@FXML
	private TableColumn<Album, String> colAno = new TableColumn<Album, String>();
	
	@FXML
	private TextField txtNome = new TextField();
	 
	@FXML
	private TextField txtArt = new TextField();
	
	@FXML
	private TextField txtAno = new TextField();
	
	@FXML
    private Button botaoSalva = new Button();
	
    @FXML
    private Button botaoAtual = new Button();
    
    @FXML
    private Button botaoApaga = new Button();
    
    @FXML
    private Button botaoLimpar = new Button();
	
    private OperacoesAlbuns sessao;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sessao = OperacoesAlbuns.getNewInstance();
        configuraColunas();
        configuraBindings();
        atualizaDadosTabela();
	}
	
	public void salvar() {
        Album a = new Album();
        pegaValores(a);
        sessao.salvar(a);
        atualizaDadosTabela();
    }

	 public void atualizar() {
	        Album a = tableAlbum.getSelectionModel().getSelectedItem();
	        pegaValores(a);
	        sessao.atualizar(a);
	        atualizaDadosTabela();
	    }
	 
	 public void apagar() {
	        Album a = tableAlbum.getSelectionModel().getSelectedItem();
	        sessao.apagar(a.getPos());
	        atualizaDadosTabela();
	    }
	 
	 public void limpar() {
	        tableAlbum.getSelectionModel().select(null);
	        txtNome.setText("");
	        txtArt.setText("");
	        txtAno.setText("");;
	    }
	 
	 private void pegaValores(Album a) {
	        a.setNome(txtNome.getText());
	        a.setArtista(txtArt.getText());
	        a.setAno(txtAno.getText());
	    }
	 
	  private void atualizaDadosTabela() {
	        tableAlbum.getItems().setAll(sessao.listarTodos());
	        limpar();
	    }
	  
	  private void configuraColunas() {
	        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	        colArt.setCellValueFactory(new PropertyValueFactory<>("artista"));
	        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
	    }
	  
	  private void configuraBindings() {
	        BooleanBinding camposPreenchidos = txtNome.textProperty().isEmpty().or(txtArt.textProperty().isEmpty()).or(txtAno.textProperty().isEmpty());
	        BooleanBinding algoSelecionado = tableAlbum.getSelectionModel().selectedItemProperty().isNull();
	        botaoApaga.disableProperty().bind(algoSelecionado);
	        botaoAtual.disableProperty().bind(algoSelecionado);
	        botaoLimpar.disableProperty().bind(algoSelecionado);
	        botaoSalva.disableProperty().bind(algoSelecionado.not().or(camposPreenchidos));
	        tableAlbum.getSelectionModel().selectedItemProperty().addListener((b, o, n) -> {
	            if (n != null) {
	            	txtNome.setText(n.getNome());
	            	txtArt.setText(n.getArtista());
	                txtAno.setText(n.getAno());
	            }
	        });
	    }
}
