package as;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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
	
	private Twitter twitter;
	private File    file; 
	private String  cNomeFile;
	private String  cConsulta;

	public Coletor() {
		this.twitter  = TwitterFactory.getSingleton();
		this.cNomeFile= "";
		this.cConsulta   = "";
	}


	//	desenvolvido para agilizar a busca por tweets neutros
	public void montaQuery(String cArquivo) {
		
		FileReader arq;
		try {
			arq = new FileReader(cArquivo);
			BufferedReader lerArq = new BufferedReader(arq);
			
			
			String linha = lerArq.readLine();
			
			while(linha != null) {
				linha = linha.trim();
				this.cNomeFile = linha + ".txt";
				this.cConsulta = linha + " lang:pt";
				exec();
				linha = lerArq.readLine();
			}
			
		} catch (FileNotFoundException e ) {
			e.printStackTrace();
		} catch (IOException e ) {
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
		
		
		this.file = new File(cNomeFile);

		FileWriter fileW;

		try {
			fileW = new FileWriter (file);
			BufferedWriter buffW = new BufferedWriter (fileW);


			Query query = new Query(this.cConsulta);
			
			System.out.println("Buscando pela consulta: " + cConsulta + " ... ");
			query.setCount(100);

			QueryResult result = twitter.search(query);
			List<Status> tweets = result.getTweets();

			for (Status tweet : tweets) {

				//buffW.write("@" + tweet.getUser().getScreenName() + ":" + tweet.getText().toUpperCase() + "\n");
				cReturn+= tweet.getText().toLowerCase() + "\n\n";
				
				buffW.write(tweet.getText().toLowerCase() + "\n\n");

			}

			buffW.flush();
			buffW.close();
		} catch ( IOException | TwitterException e) {

			e.printStackTrace();
		}
		
		return cReturn;
	}

	public ArrayList<Status> getDiscussion(Status status, Twitter twitter) {
		ArrayList<Status> replies = new ArrayList<>();

		ArrayList<Status> all = null;

		try {
			long id = status.getId();
			String screenname = status.getUser().getScreenName();

			Query query = new Query("@" + screenname + " since_id:" + id);

			System.out.println("query string: " + query.getQuery());

			try {
				query.setCount(100);
			} catch (Throwable e) {
				// enlarge buffer error?
				query.setCount(30);
			}

			QueryResult result = twitter.search(query);
			System.out.println("result: " + result.getTweets().size());

			all = new ArrayList<Status>();

			do {
				System.out.println("do loop repetition");

				List<Status> tweets = result.getTweets();

				for (Status tweet : tweets)
					if (tweet.getInReplyToStatusId() == id)
						all.add(tweet);

				if (all.size() > 0) {
					for (int i = all.size() - 1; i >= 0; i--)
						replies.add(all.get(i));
					all.clear();
				}

				query = result.nextQuery();

				if (query != null)
					result = twitter.search(query);

			} while (query != null);

		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return replies;
	}
	
	// area de getters e setters

	public Twitter getTwitter() {
		return twitter;
	}



	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}



	public File getFile() {
		return file;
	}



	public void setFile(File file) {
		this.file = file;
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

	public static void main(String[] args) throws TwitterException {

		Coletor c = new Coletor();
		System.out.println("Iniciando rotina de coleta de Tweets ... ");
		
		//c.montaQuery("query.txt");
		//c.exec();

		System.out.println("Finalizando rotina de coleta de Tweets ...");
	}
}
