package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import br.com.KauanPaulino.ListaObject.Lista_Object;
import br.kauanPaulino.filaObject.FilaObject;
import model.Serie;

public class NetflixController implements INetflixController {
	
	Lista_Object[] vtlista;
	public NetflixController() {
		vtlista = new Lista_Object[7];
		for(int i=0;i<7;i++) {
			vtlista[i]=new Lista_Object();
		}
	}

	@Override
	public void GeraFilaObjetos(String path, String name) {
		try {
			GeraFila(path, name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void GeraFila(String path, String name) throws IOException {
		File arquivo = new File(path, name);

		if (arquivo.exists() && arquivo.isFile()) {
			FileInputStream fluxo = new FileInputStream(arquivo);
			InputStreamReader leitorfluxo = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitorfluxo);
			String linha = buffer.readLine();
			linha = buffer.readLine();
			while (linha != null) {
				String vtlinha[] = linha.split(";");
				String genero = vtlinha[0];
				FilaObject filaaux = new FilaObject();
				while (genero.contains(vtlinha[0]) && linha != null) {
					Serie s = new Serie();
					s.major_genre = vtlinha[0];
					s.title = vtlinha[1];
					s.subgenre = vtlinha[2];
					s.premiere_year = vtlinha[4];
					s.status = vtlinha[6];
					s.imdb_rating = Integer.parseInt(vtlinha[11]);
					s.episodes = (vtlinha[10]);
					filaaux.insert(s);
					linha = buffer.readLine();
					if (linha != null) {
						vtlinha = linha.split(";");
					}
				}
				GeraArquivos(filaaux, genero);
			}
		} else {
			throw new IOException("Arquivo invalido");
		}

	}

	private void GeraArquivos(FilaObject filas, String genero) throws IOException {
		File file = new File("C:\\Temp", genero + ".csv");
		StringBuffer buffer = new StringBuffer();
		// Linha De titulo
		buffer.append("Genero;Titulo;Subgenero;Ano;Episodios;Status;Nota\n");
		FileWriter filewrite = new FileWriter(file);
		PrintWriter print = new PrintWriter(filewrite);
		print.write(buffer.toString());
		print.flush();
		print.close();
		filewrite.close();

		int tamanho = filas.size();
		for (int i = 0; i < tamanho; i++) {
			Serie s = new Serie();
			try {
				s = (Serie) filas.remove();
				buffer.append(s);
				FileWriter filewrite1 = new FileWriter(file);
				PrintWriter print1 = new PrintWriter(filewrite1);
				print1.write(buffer.toString());
				print1.flush();
				print1.close();
				filewrite1.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// Gera lista
	public void GeraListaObjetos(String path, String name) {
		try {
			GeraLista(path, name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void GeraLista(String path, String name) throws IOException {
		File arquivo = new File(path, name);

		if (arquivo.exists() && arquivo.isFile()) {
			FileInputStream fluxo = new FileInputStream(arquivo);
			InputStreamReader leitorfluxo = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitorfluxo);
			String linha = buffer.readLine();
			linha = buffer.readLine();
			Lista_Object lista = new Lista_Object();
			while (linha != null) {
				String vtlinha[] = linha.split(";");
				String status = vtlinha[6];
				if (status.equals("Renewed")) {
					Serie s = new Serie();
					s.major_genre = vtlinha[0];
					s.title = vtlinha[1];
					s.subgenre = vtlinha[2];
					s.premiere_year = vtlinha[4];
					s.status = vtlinha[6];
					s.imdb_rating = Integer.parseInt(vtlinha[11]);
					s.episodes = (vtlinha[10]);
					if (lista.isEmpty()) {
						lista.addFirst(s);
					} else {
						try {
							lista.addLast(s);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				linha = buffer.readLine();
			}
//			GravaLista(lista, "Reneward");
			TrataLista(lista);
		}
	}

	private void TrataLista(Lista_Object lista) throws IOException {
		Lista_Object listaaux = lista;
		int tamanho = listaaux.size();
		String ano = "";
		while (!listaaux.isEmpty()) {
			Lista_Object listaGravar = new Lista_Object();
			Serie s = new Serie();
			try {
				s = (Serie) listaaux.get(0);
				ano = s.premiere_year;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (int i = 0; i < tamanho; i++) {
				Serie s1 = new Serie();
				try {
					s1 = (Serie) listaaux.get(i);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (ano.equals(s1.premiere_year)) {
					if (listaGravar.isEmpty()) {
						listaGravar.addFirst(s1);
						try {
							listaaux.remove(i);
							tamanho = listaaux.size();
							i--;

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						try {
							listaGravar.addLast(s1);
							listaaux.remove(i);
							tamanho = listaaux.size();
							i--;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}
			GravaLista(listaGravar, ano);

		}

	}

	private void GravaLista(Lista_Object listaGravar, String ano) throws IOException {
		File file = new File("C:\\Temp", "Ano" + ano + ".csv");
		StringBuffer buffer = new StringBuffer();
		// Linha De titulo
		buffer.append("Genero;Titulo;Subgenero;Ano;Episodios;Status;Nota\n");
		FileWriter filewrite = new FileWriter(file);
		PrintWriter print = new PrintWriter(filewrite);
		print.write(buffer.toString());
		print.flush();
		print.close();
		filewrite.close();

		int tamanho = listaGravar.size();
		for (int i = 0; i < tamanho; i++) {
			Serie s = new Serie();
			try {
				s = (Serie) listaGravar.get(i);
				buffer.append(s);
				FileWriter filewrite1 = new FileWriter(file);
				PrintWriter print1 = new PrintWriter(filewrite1);
				print1.write(buffer.toString());
				print1.flush();
				print1.close();
				filewrite1.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void TabelaEspalhamento(String path, String name) throws IOException {
		File arquivo = new File(path, name);

		if (arquivo.exists() && arquivo.isFile()) {
			FileInputStream fluxo = new FileInputStream(arquivo);
			InputStreamReader leitorfluxo = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(leitorfluxo);
			String linha = buffer.readLine();
			linha = buffer.readLine();
			
			while (linha != null) {
				String[] vtlinha = linha.split(";");
				Serie s = new Serie();
				s.major_genre = vtlinha[0];
				s.title = vtlinha[1];
				s.subgenre = vtlinha[2];
				s.premiere_year = vtlinha[4];
				s.status = vtlinha[6];
				s.imdb_rating = Integer.parseInt(vtlinha[11]);
				s.episodes = (vtlinha[10]);
				int hash=HashCode(s.imdb_rating);
				if(vtlista[hash].isEmpty()) {
					vtlista[hash].addFirst(s);
				}
				else {
					try {
						vtlista[hash].addLast(s);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				linha = buffer.readLine();
			}
		}
	}

	// HashCode
	private int HashCode(int numero) {
		int hash = (int) (numero/15.1);
		if(hash>6) {
			hash--;
		}
		return hash;
		
	}
	
	public void mostraTabela() {
		int Numero =Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de estrela da serie"));
		if(Numero>6 || Numero<0) {
			JOptionPane.showMessageDialog(null, "Numero Invalido");
		}
		else {
			int tamanho = vtlista[Numero].size();
			for(int i=0;i<tamanho;i++) {
				try {
					Serie s = new Serie();
					s=(Serie) vtlista[Numero].get(i);
					System.out.println(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
}