package as;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import util.ArquivoBase;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
/**
 * Classe responsavel por realizar etapa de pre processamento dos dados 
 * Aqui tambem e gerada a base de treinamento a partir dos arquivos coletados
 * 
 * Sessão: 4.2.
 * @author Alisson R Teles
 * @version 1.0
 * @see "https://www.overleaf.com/read/kppdttwznbjj"
 */
public class PreProcessamento {
	private String cArquivoOrigem;
	private String cPastaDestino;
	private Instances iBaseTreinamento;
	
	public PreProcessamento() {
		this.cArquivoOrigem = "";
		this.cPastaDestino = "";
	}
	
	////REMOVE CARACTERES ESPECIAIS
	public String especiais(String passa){

		passa = passa.replaceAll("[ÂÀÁÄÃ]", "A");
		passa = passa.replaceAll("[âãàáåä]", "a");
		passa = passa.replaceAll("[ÊÈÉË]", "E");
		passa = passa.replaceAll("[êèéë]", "e");
		passa = passa.replaceAll("[ÎÍÌÏ]", "I");
		passa = passa.replaceAll("[îíìï]", "i");
		passa = passa.replaceAll("[ÔÕÒÓÖ]", "O");
		passa = passa.replaceAll("[ôõòóö]", "o");
		passa = passa.replaceAll("[ÛÙÚÜ]", "U");
		passa = passa.replaceAll("[ûúùü]", "u");
		passa = passa.replaceAll("Ç", "C");
		passa = passa.replaceAll("ç", "c");
		passa = passa.replaceAll("[ýÿ]", "y");
		passa = passa.replaceAll("Ý", "Y");
		passa = passa.replaceAll("ñ", "n");
		passa = passa.replaceAll("Ñ", "N");
		passa = passa.replaceAll("[-+=*&;%$#@!_?]", "");
		passa = passa.replaceAll("['\"]", "");
		passa = passa.replaceAll("[<>()\\{\\}]", "");
		passa = passa.replaceAll("['\\\\.,()|/]", "");
		passa = passa.replaceAll("[^!-ÿ]{1}[^ -ÿ]{0,}[^!-ÿ]{1}|[^!-ÿ]{1}", " ");
		passa = passa.replaceAll("null", ""); 

		return(passa);
	}


	////////////////REMOVE STOPWORDS
	public String stopwords(String passa){

		passa = passa.replaceAll(" a ", " ");
		passa = passa.replaceAll(" agora ", " ");
		passa = passa.replaceAll(" ainda ", " ");
		passa = passa.replaceAll(" alguem ", " ");
		passa = passa.replaceAll(" algum ", " ");
		passa = passa.replaceAll(" alguma ", " ");
		passa = passa.replaceAll(" algumas ", " ");
		passa = passa.replaceAll(" alguns ", " ");
		passa = passa.replaceAll(" ampla ", " ");
		passa = passa.replaceAll(" amplas ", " ");
		passa = passa.replaceAll(" amplo ", " ");
		passa = passa.replaceAll(" amplos ", " ");
		passa = passa.replaceAll(" ante ", " ");
		passa = passa.replaceAll(" antes ", " ");
		passa = passa.replaceAll(" ao ", " ");
		passa = passa.replaceAll(" aos ", " ");
		passa = passa.replaceAll(" apos ", " ");
		passa = passa.replaceAll(" aquela ", " ");
		passa = passa.replaceAll(" aquelas ", " ");
		passa = passa.replaceAll(" aquele ", " ");
		passa = passa.replaceAll(" aqueles ", " ");
		passa = passa.replaceAll(" aquilo ", " ");
		passa = passa.replaceAll(" as ", " ");
		passa = passa.replaceAll(" ate ", " ");
		passa = passa.replaceAll(" atraves ", " ");
		passa = passa.replaceAll(" cada ", " ");
		passa = passa.replaceAll(" coisa ", " ");
		passa = passa.replaceAll(" coisas ", " ");
		passa = passa.replaceAll(" com ", " ");
		passa = passa.replaceAll(" como ", " ");
		passa = passa.replaceAll(" contra ", " ");
		passa = passa.replaceAll(" contudo ", " ");
		passa = passa.replaceAll(" da ", " ");
		passa = passa.replaceAll(" daquele ", " ");
		passa = passa.replaceAll(" daqueles ", " ");
		passa = passa.replaceAll(" das ", " ");
		passa = passa.replaceAll(" de ", " ");
		passa = passa.replaceAll(" dela ", " ");
		passa = passa.replaceAll(" delas ", " ");
		passa = passa.replaceAll(" dele ", " ");
		passa = passa.replaceAll(" deles ", " ");
		passa = passa.replaceAll(" depois ", " ");
		passa = passa.replaceAll(" dessa ", " ");
		passa = passa.replaceAll(" dessas ", " ");
		passa = passa.replaceAll(" desse ", " ");
		passa = passa.replaceAll(" desses ", " ");
		passa = passa.replaceAll(" desta ", " ");
		passa = passa.replaceAll(" destas ", " ");
		passa = passa.replaceAll(" deste ", " ");
		passa = passa.replaceAll(" deste ", " ");
		passa = passa.replaceAll(" destes ", " ");
		passa = passa.replaceAll(" deve ", " ");
		passa = passa.replaceAll(" devem ", " ");
		passa = passa.replaceAll(" devendo ", " ");
		passa = passa.replaceAll(" dever ", " ");
		passa = passa.replaceAll(" devera ", " ");
		passa = passa.replaceAll(" deverao ", " ");
		passa = passa.replaceAll(" deveria ", " ");
		passa = passa.replaceAll(" deveriam ", " ");
		passa = passa.replaceAll(" devia ", " ");
		passa = passa.replaceAll(" deviam ", " ");
		passa = passa.replaceAll(" disse ", " ");
		passa = passa.replaceAll(" disso ", " ");
		passa = passa.replaceAll(" disto ", " ");
		passa = passa.replaceAll(" dito ", " ");
		passa = passa.replaceAll(" diz ", " ");
		passa = passa.replaceAll(" dizem ", " ");
		passa = passa.replaceAll(" do ", " ");
		passa = passa.replaceAll(" dos ", " ");
		passa = passa.replaceAll(" e ", " ");
		passa = passa.replaceAll(" ela ", " ");
		passa = passa.replaceAll(" elas ", " ");
		passa = passa.replaceAll(" ele ", " ");
		passa = passa.replaceAll(" eles ", " ");
		passa = passa.replaceAll(" em ", " ");
		passa = passa.replaceAll(" enquanto ", " ");
		passa = passa.replaceAll(" entre ", " ");
		passa = passa.replaceAll(" era ", " ");
		passa = passa.replaceAll(" essa ", " ");
		passa = passa.replaceAll(" essas ", " ");
		passa = passa.replaceAll(" esse ", " ");
		passa = passa.replaceAll(" esses ", " ");
		passa = passa.replaceAll(" esta ", " ");
		passa = passa.replaceAll(" estamos ", " ");
		passa = passa.replaceAll(" estao ", " ");
		passa = passa.replaceAll(" estas ", " ");
		passa = passa.replaceAll(" estava ", " ");
		passa = passa.replaceAll(" estavam ", " ");
		passa = passa.replaceAll(" estavamos ", " ");
		passa = passa.replaceAll(" este ", " ");
		passa = passa.replaceAll(" estes ", " ");
		passa = passa.replaceAll(" estou ", " ");
		passa = passa.replaceAll(" eu ", " ");
		passa = passa.replaceAll(" fazendo ", " ");
		passa = passa.replaceAll(" fazer ", " ");
		passa = passa.replaceAll(" feita ", " ");
		passa = passa.replaceAll(" feitas ", " ");
		passa = passa.replaceAll(" feito ", " ");
		passa = passa.replaceAll(" feitos ", " ");
		passa = passa.replaceAll(" foi ", " ");
		passa = passa.replaceAll(" for ", " ");
		passa = passa.replaceAll(" foram ", " ");
		passa = passa.replaceAll(" fosse ", " ");
		passa = passa.replaceAll(" fossem ", " ");
		passa = passa.replaceAll(" grande ", " ");
		passa = passa.replaceAll(" grandes ", " ");
		passa = passa.replaceAll(" ha ", " ");
		passa = passa.replaceAll(" isso ", " ");
		passa = passa.replaceAll(" isto ", " ");
		passa = passa.replaceAll(" ja ", " ");
		passa = passa.replaceAll(" la ", " ");
		passa = passa.replaceAll(" lhe ", " ");
		passa = passa.replaceAll(" lhes ", " ");
		passa = passa.replaceAll(" lo ", " ");
		passa = passa.replaceAll(" mas ", " ");
		passa = passa.replaceAll(" me ", " ");
		passa = passa.replaceAll(" mesma ", " ");
		passa = passa.replaceAll(" mesmas ", " ");
		passa = passa.replaceAll(" mesmo ", " ");
		passa = passa.replaceAll(" mesmos ", " ");
		passa = passa.replaceAll(" meu ", " ");
		passa = passa.replaceAll(" meus ", " ");
		passa = passa.replaceAll(" minha ", " ");
		passa = passa.replaceAll(" minhas ", " ");
		passa = passa.replaceAll(" muita ", " ");
		passa = passa.replaceAll(" muitas ", " ");
		passa = passa.replaceAll(" muito ", " ");
		passa = passa.replaceAll(" muitos ", " ");
		passa = passa.replaceAll(" na ", " ");
		passa = passa.replaceAll(" nao ", " ");
		passa = passa.replaceAll(" nas ", " ");
		passa = passa.replaceAll(" nem ", " ");
		passa = passa.replaceAll(" nenhum ", " ");
		passa = passa.replaceAll(" nessa ", " ");
		passa = passa.replaceAll(" nessas ", " ");
		passa = passa.replaceAll(" nesta ", " ");
		passa = passa.replaceAll(" nestas ", " ");
		passa = passa.replaceAll(" ninguem ", " ");
		passa = passa.replaceAll(" no ", " ");
		passa = passa.replaceAll(" nos ", " ");
		passa = passa.replaceAll(" nos ", " ");
		passa = passa.replaceAll(" nossa ", " ");
		passa = passa.replaceAll(" nossas ", " ");
		passa = passa.replaceAll(" nosso ", " ");
		passa = passa.replaceAll(" nossos ", " ");
		passa = passa.replaceAll(" num ", " ");
		passa = passa.replaceAll(" numa ", " ");
		passa = passa.replaceAll(" nunca ", " ");
		passa = passa.replaceAll(" o ", " ");
		passa = passa.replaceAll(" os ", " ");
		passa = passa.replaceAll(" ou ", " ");
		passa = passa.replaceAll(" outra ", " ");
		passa = passa.replaceAll(" outras ", " ");
		passa = passa.replaceAll(" outro ", " ");
		passa = passa.replaceAll(" outros ", " ");
		passa = passa.replaceAll(" para ", " ");
		passa = passa.replaceAll(" pela ", " ");
		passa = passa.replaceAll(" pelas ", " ");
		passa = passa.replaceAll(" pelo ", " ");
		passa = passa.replaceAll(" pelos ", " ");
		passa = passa.replaceAll(" pequena ", " ");
		passa = passa.replaceAll(" pequenas ", " ");
		passa = passa.replaceAll(" pequeno ", " ");
		passa = passa.replaceAll(" pequenos ", " ");
		passa = passa.replaceAll(" per ", " ");
		passa = passa.replaceAll(" perante ", " ");
		passa = passa.replaceAll(" pode ", " ");
		passa = passa.replaceAll(" pode ", " ");
		passa = passa.replaceAll(" podendo ", " ");
		passa = passa.replaceAll(" poder ", " ");
		passa = passa.replaceAll(" poderia ", " ");
		passa = passa.replaceAll(" poderiam ", " ");
		passa = passa.replaceAll(" podia ", " ");
		passa = passa.replaceAll(" podiam ", " ");
		passa = passa.replaceAll(" pois ", " ");
		passa = passa.replaceAll(" por ", " ");
		passa = passa.replaceAll(" porem ", " ");
		passa = passa.replaceAll(" porque ", " ");
		passa = passa.replaceAll(" posso ", " ");
		passa = passa.replaceAll(" pouca ", " ");
		passa = passa.replaceAll(" poucas ", " ");
		passa = passa.replaceAll(" pouco ", " ");
		passa = passa.replaceAll(" poucos ", " ");
		passa = passa.replaceAll(" primeiro ", " ");
		passa = passa.replaceAll(" primeiros ", " ");
		passa = passa.replaceAll(" propria ", " ");
		passa = passa.replaceAll(" proprias ", " ");
		passa = passa.replaceAll(" proprio ", " ");
		passa = passa.replaceAll(" proprios ", " ");
		passa = passa.replaceAll(" quais ", " ");
		passa = passa.replaceAll(" qual ", " ");
		passa = passa.replaceAll(" quando ", " ");
		passa = passa.replaceAll(" quanto ", " ");
		passa = passa.replaceAll(" quantos ", " ");
		passa = passa.replaceAll(" que ", " ");
		passa = passa.replaceAll(" quem ", " ");
		passa = passa.replaceAll(" sao ", " ");
		passa = passa.replaceAll(" se ", " ");
		passa = passa.replaceAll(" seja ", " ");
		passa = passa.replaceAll(" sejam ", " ");
		passa = passa.replaceAll(" sem ", " ");
		passa = passa.replaceAll(" sempre ", " ");
		passa = passa.replaceAll(" sendo ", " ");
		passa = passa.replaceAll(" sera ", " ");
		passa = passa.replaceAll(" serao ", " ");
		passa = passa.replaceAll(" seu ", " ");
		passa = passa.replaceAll(" seus ", " ");
		passa = passa.replaceAll(" si ", " ");
		passa = passa.replaceAll(" sido ", " ");
		passa = passa.replaceAll(" so ", " ");
		passa = passa.replaceAll(" sob ", " ");
		passa = passa.replaceAll(" sobre ", " ");
		passa = passa.replaceAll(" sua ", " ");
		passa = passa.replaceAll(" suas ", " ");
		passa = passa.replaceAll(" talvez ", " ");
		passa = passa.replaceAll(" tambem ", " ");
		passa = passa.replaceAll(" tampouco ", " ");
		passa = passa.replaceAll(" te ", " ");
		passa = passa.replaceAll(" tem ", " ");
		passa = passa.replaceAll(" tendo ", " ");
		passa = passa.replaceAll(" tenha ", " ");
		passa = passa.replaceAll(" ter ", " ");
		passa = passa.replaceAll(" teu ", " ");
		passa = passa.replaceAll(" teus ", " ");
		passa = passa.replaceAll(" ti ", " ");
		passa = passa.replaceAll(" tido ", " ");
		passa = passa.replaceAll(" tinha ", " ");
		passa = passa.replaceAll(" tinham ", " ");
		passa = passa.replaceAll(" toda ", " ");
		passa = passa.replaceAll(" todas ", " ");
		passa = passa.replaceAll(" todavia ", " ");
		passa = passa.replaceAll(" todo ", " ");
		passa = passa.replaceAll(" todos ", " ");
		passa = passa.replaceAll(" tu ", " ");
		passa = passa.replaceAll(" tua ", " ");
		passa = passa.replaceAll(" tuas ", " ");
		passa = passa.replaceAll(" tudo ", " ");
		passa = passa.replaceAll(" ultima ", " ");
		passa = passa.replaceAll(" ultimas ", " ");
		passa = passa.replaceAll(" ultimo ", " ");
		passa = passa.replaceAll(" ultimos ", " ");
		passa = passa.replaceAll(" um ", " ");
		passa = passa.replaceAll(" uma ", " ");
		passa = passa.replaceAll(" umas ", " ");
		passa = passa.replaceAll(" uns ", " ");
		passa = passa.replaceAll(" vendo ", " ");
		passa = passa.replaceAll(" ver ", " ");
		passa = passa.replaceAll(" vez ", " ");
		passa = passa.replaceAll(" vindo ", " ");
		passa = passa.replaceAll(" vir ", " ");
		passa = passa.replaceAll(" vos ", " ");
		passa = passa.replaceAll(" vc ", " ");
		passa = passa.replaceAll(" vcs ", " ");

		return passa;              
	}
	
	public String numericos(String passa) {
		passa = passa.replaceAll("0","");
		passa = passa.replaceAll("1","");
		passa = passa.replaceAll("2","");
		passa = passa.replaceAll("3","");
		passa = passa.replaceAll("4","");
		passa = passa.replaceAll("5","");
		passa = passa.replaceAll("6","");
		passa = passa.replaceAll("7","");
		passa = passa.replaceAll("8","");
		passa = passa.replaceAll("9","");

		return passa;
	}
	
	public String removePontRep(String passa) {
		passa = passa.replaceAll("!!","EXCLAMACAO");
		passa = passa.replaceAll("??","DUVIDA");
		passa = passa.replaceAll("?!?!","ESPANTO");
		return passa;
	}
	
	public String upperCase(String passa) {
		return passa.toUpperCase();
	}
	
	public String removePont(String passa) {
		passa = passa.replaceAll("\\!","");
		passa = passa.replaceAll("\\?","");
		passa = passa.replaceAll("\\.","");
		
		return passa;
	}
	
	public String exec(String passa) {
		
		passa = especiais(passa);
		passa = stopwords(passa);
		passa = numericos(passa);
		passa = upperCase(passa);
		
		return(passa);
	}
	
	public String geraArquivos(ArquivoBase antes) {
		String cReturn = "";

		try {
			LinkedList<String> lista_antes = antes.getaLinhasArquivo();
			
			for (Iterator iterator = lista_antes.iterator(); iterator.hasNext();) {
				
				String string = exec( (String) iterator.next());
				
				if (!this.cPastaDestino.isEmpty()) {
					
					ArquivoBase destino = new ArquivoBase();
					LinkedList<String> lista_destino = new LinkedList<String>();
					
					lista_destino.add(string);
					
					destino.setsNomeArquivo(cPastaDestino+string);
					destino.setaLinhasArquivo(lista_destino);
					
					destino.MatrixParaArquivo();
					
					cReturn +=  string+ "\n";
					
				}
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return cReturn;
		
	}
	
	public Instances geraBaseTreinamento() {
		TextDirectoryLoader loader = new TextDirectoryLoader();
		try {
			loader.setDirectory(new File(getcPastaDestino()));
			Instances dataRaw = loader.getDataSet();
			
			dataRaw.setClass(dataRaw.attribute("@@class@@"));
			
			StringToWordVector filter = new StringToWordVector();
			
			filter.setInputFormat(dataRaw);
			
			Instances trainFiltered = Filter.useFilter(dataRaw, filter);
			trainFiltered.setClass(trainFiltered.attribute("@@class@@"));
			
			ArffSaver saver = new ArffSaver();
            saver.setInstances(trainFiltered);
            
            saver.setFile(new java.io.File(getcArquivoOrigem(), ""));
            saver.writeBatch();
            
            iBaseTreinamento = trainFiltered;
         
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return iBaseTreinamento;
	}

	// area de getters e setters
	
	public String getcArquivoOrigem() {
		return cArquivoOrigem;
	}

	public void setcArquivoOrigem(String cArquivoOrigem) {
		this.cArquivoOrigem = cArquivoOrigem;
	}

	public String getcPastaDestino() {
		return cPastaDestino;
	}

	public void setcPastaDestino(String cPastaDestino) {
		this.cPastaDestino = cPastaDestino;
	}
	
	
//	public static void main(String[] args) {
//		PreProcessamento p = new PreProcessamento();
//		ArquivoBase      arqOrigem = new ArquivoBase("teste1.txt");
//		
//		p.setcPastaDestino("classes/hate/");
//		p.geraArquivos(arqOrigem);
//		
//
//		arqOrigem.setFile(new File("teste2.txt"));
//		p.setcPastaDestino("classes/neutro/");
//		
//		try {
//			arqOrigem.ArquivoParaMatriz();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		p.geraArquivos(arqOrigem);
//				
//		p.setcArquivoOrigem("Teste_alisson_final.arff");
//		p.setcPastaDestino("classes/");
//		p.geraBaseTreinamento();
//	}
}
