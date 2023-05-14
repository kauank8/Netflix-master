package controller;

import java.io.IOException;

public interface INetflixController {
	public void GeraFilaObjetos(String path, String name);
	public void GeraListaObjetos(String path, String name);
	public void TabelaEspalhamento(String path, String name) throws IOException;
	public void mostraTabela();
}
