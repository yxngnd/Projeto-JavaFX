package application;

import java.util.List;

public interface OperacoesAlbuns {

	public void salvar(Album album);
	
	public List<Album> listarTodos();
	
	public Album buscarPosicao(int pos);
	
	public void apagar(int pos);
	
	public void atualizar(Album album);
	
	public static OperacoesAlbuns getNewInstance() {
		return new OperacoesAlbunsCSV();
	}
	
}
