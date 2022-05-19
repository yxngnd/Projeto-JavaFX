package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class OperacoesAlbunsCSV implements OperacoesAlbuns {
	
	 private static final String SEPARADOR = ";";
	 
	 private static final Path ARQUIVO_SAIDA = Paths.get("./dados.csv");
	 
	  private List<Album> albuns;
	  
	  public OperacoesAlbunsCSV() {
	        carregaDados();
	    }
	  
	  @Override
	    public void salvar(Album album) {
	        album.setPos(ultimoPos() + 1);
	        albuns.add(album);
	        salvaDados();
	    }
	  
	  @Override
	    public void atualizar(Album album) {
	        Album albumAntigo = buscarPosicao(album.getPos());
	        albumAntigo.setNome(album.getNome());
	        albumAntigo.setAno(album.getAno());
	        albumAntigo.setArtista(album.getArtista());
	        salvaDados();
	    }

	    @Override
	    public List<Album> listarTodos() {
	        return albuns;
	    }

	    @Override
	    public void apagar(int pos) {
	        Album album = buscarPosicao(pos);
	        albuns.remove(album);
	        salvaDados();
	    }
	   
	    public Album buscarPosicao(int pos) {
	        return albuns.stream().filter(a -> a.getPos() == pos).findFirst().orElseThrow(() -> new Error("Album não encontrada"));
	    }

	    private void salvaDados() {
	        StringBuffer sb = new StringBuffer();
	        for (Album a : albuns) {
	            String linha = criaLinha(a);
	            sb.append(linha);
	            sb.append(System.getProperty("line.separator"));
	        }
	        try {
	            Files.delete(ARQUIVO_SAIDA);
	            Files.write(ARQUIVO_SAIDA, sb.toString().getBytes());
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.exit(0);
	        }
	    }
	    
	    private void carregaDados() {
	        try {
	            if(!Files.exists(ARQUIVO_SAIDA)) {
	                Files.createFile(ARQUIVO_SAIDA);
	            }
	            albuns = Files.lines(ARQUIVO_SAIDA).map(this::leLinha).collect(Collectors.toList());
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.exit(0);
	        }
	    }
	    
	    private int ultimoPos() {
	        return albuns.stream().mapToInt(Album::getPos).max().orElse(0);
	    }
	    
	    private Album leLinha(String linha) {
	        String colunas[] = linha.split(SEPARADOR);
	        int pos = Integer.parseInt(colunas[0]);
	        Album album = new Album();
	        album.setPos(pos);
	        album.setNome(colunas[1]);
	        album.setArtista(colunas[2]);
	        album.setAno(colunas[3]);
	        return album;
	    }
	    
	    private String criaLinha(Album a) {
	        String posStr = String.valueOf(a.getPos());
	        String linha = String.join(SEPARADOR, posStr, a.getNome(), a.getArtista(),
	                a.getAno());
	        return linha;
	    }
}
