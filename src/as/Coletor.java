package as;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import util.ArquivoBase;
/**
 * Classe responsavel por realizar etapa de coleta dentro da analise de sentimentos.
 * Aqui a API utilizada foi a do Twitter.
 * 
 * Sessao: 4.1.
 * @author Alisson R Teles
 * @version 1.0
 * @see "https://www.overleaf.com/read/kppdttwznbjj"
 * 
 */
public class Coletor {
	private Twitter        twitter;
	private ArquivoBase	   arquivo; 
	private String         cNomeFile;
	private String         cConsulta;

	public Coletor() {
		this.twitter  = TwitterFactory.getSingleton();
		this.cNomeFile   = "";
		this.cConsulta   = "";
	}


	//	desenvolvido para agilizar a busca por tweets neutros
	public void montaQuery(ArquivoBase arquivoOrigem) {
		LinkedList<String> lista;
		try {
			lista = arquivoOrigem.ArquivoParaMatriz();

			for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
				this.cConsulta = (String) iterator.next() + "lang:pt";
				this.cNomeFile = (String) iterator.next();

				exec();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String exec() {

		String cReturn = "";

		if (this.cNomeFile.isEmpty() && this.cConsulta.isEmpty()) {
			System.out.println("Digite o nome do arquivo:");
			Scanner input = new Scanner(System.in);
			this.cNomeFile = input.nextLine();

			System.out.println("Digite a pesquisa:");
			input = new Scanner(System.in);
			this.cConsulta = input.nextLine();
		}


		this.arquivo = new ArquivoBase(cNomeFile);


		Query query = new Query(this.cConsulta);

		System.out.println("Buscando pela consulta: " + cConsulta + " ... ");
		query.setCount(100);

		QueryResult result;
		try {
			result = twitter.search(query);

			List<Status> tweets = result.getTweets();
			LinkedList<String> retorno = new LinkedList<String>();
				
			for (Status tweet : tweets) {

				cReturn+= tweet.getText().toLowerCase() + "\n";	
				retorno.add(tweet.getText().toLowerCase());

			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.arquivo.MatrixParaArquivo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cReturn;
	}


	// area de getters e setters

	public Twitter getTwitter() {
		return twitter;
	}



	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}



	public String getcNomeFile() {
		return cNomeFile;
	}



	public void setcNomeFile(String cNomeFile) {
		this.cNomeFile = cNomeFile;
	}



	public String getcConsulta() {
		return cConsulta;
	}



	public void setcConsulta(String cConsulta) {
		this.cConsulta = cConsulta;
	}

//	public static void main(String[] args) throws TwitterException {
//
//		Coletor c = new Coletor();
//		System.out.println("Iniciando rotina de coleta de Tweets ... ");
//
//		//c.montaQuery("query.txt");
//		//c.exec();
//
//		System.out.println("Finalizando rotina de coleta de Tweets ...");
//	}
}
