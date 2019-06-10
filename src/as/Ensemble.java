package as;
import java.util.ArrayList;
import java.util.Iterator;

import util.ArquivoBase;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.functions.SMO;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
/**
 * Classe responsavel por realizar etapa de classificacao. E abordado o metodo 
 * Ensemble de votacao onde foram escolhidos, por meio de experimento, os
 * classificadores com melhores resultados que sao: Naive Bayes, BayesNet e SMO 
 * 
 * Sessão: 4.3. e 4.4.
 * @author Alisson R Teles
 * @version 1.0
 * @see "https://www.overleaf.com/read/kppdttwznbjj"
 */
public class Ensemble {
	private SMO        smo;
	private NaiveBayes naiveB;
	private BayesNet   bayesN;
	private Instances  iBaseTreinamento;
	private Instances  iBaseTeste;
	private Evaluation[] evaluation;
	private String     cTreinamento;

	public Ensemble() {

		this.bayesN = new BayesNet();
		this.naiveB = new NaiveBayes();
		this.smo    = new SMO();
		
		this.cTreinamento = "treinamento.arff";
		this.evaluation = new Evaluation[3];

	}

	public void montaClassificadores() {

		try {
			DataSource data = new DataSource(this.getcTreinamento());
			iBaseTreinamento = data.getDataSet();
			iBaseTreinamento.setClass(iBaseTreinamento.attribute("@@class@@"));

			this.bayesN.buildClassifier(iBaseTreinamento);
			this.naiveB.buildClassifier(iBaseTreinamento);
			this.smo.buildClassifier(iBaseTreinamento);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int geraEnsemble(Instances baseTeste) {
		int nReturn = 0;

		try {
			evaluation[0] = new Evaluation(iBaseTreinamento);
			evaluation[0].evaluateModel(naiveB,baseTeste);

			evaluation[1] = new Evaluation(iBaseTreinamento);
			evaluation[1].evaluateModel(smo,baseTeste);


			evaluation[2] = new Evaluation(iBaseTreinamento);
			evaluation[2].evaluateModel(bayesN,baseTeste);

			if (evaluation[0].correct()<evaluation[1].correct()) {
				nReturn = 1;
			}

			if (evaluation[1].correct()<evaluation[2].correct()) {
				nReturn = 2;
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nReturn;
	}
	
	public Instances montaTeste(String[][] aTexto) {

		PreProcessamento preproc = new PreProcessamento();
		Instances teste = new Instances(iBaseTreinamento, 0);
		
		for (int j = 0;j<aTexto.length;j++) {
			String    cTeste = aTexto[j][0].trim();
	
			cTeste = preproc.exec(cTeste);
			
			ArrayList<String> wordArrayList = new ArrayList<String>();
			for(String word : cTeste.split(" ")) {
				wordArrayList.add(word);
			}
	
	
			int numAttributes = teste.numAttributes();
	
			// Cria a instância de teste
			Instance instance = new DenseInstance(numAttributes);
			instance.setDataset(teste);
	
			for (int i = 0; i < numAttributes; i++) {
				instance.setValue(i, 0);
	
				for (Iterator iterator = wordArrayList.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
	
					if (string.equalsIgnoreCase(instance.attribute(i).name())) {
						instance.setValue(i, 1);
					}
				}
			}
			
			int classe = Integer.parseInt(aTexto[j][1]);
	
			switch (classe) {
			case 0:
				instance.setClassValue("hate");
				break;
			case 1:
				instance.setClassValue("neutro");
				break;
	
			default:
				instance.setClassMissing();
				break;
			}
	
	
			teste.add(instance);
		}
		
		iBaseTeste = teste;
		
		return teste;

	}

	public Prediction predicao() {
		Prediction ev = null;
		int melhor = geraEnsemble(this.iBaseTeste);
	
		
		for (int i = 0; i < evaluation[1].predictions().size(); i++) {
			ev = evaluation[1].predictions().get(i);
			NominalPrediction np = (NominalPrediction) ev;
			
		}

		return ev;
	}


	public SMO getSmo() {
		return smo;
	}

	public void setSmo(SMO smo) {
		this.smo = smo;
	}

	public NaiveBayes getNaiveB() {
		return naiveB;
	}

	public void setNaiveB(NaiveBayes naiveB) {
		this.naiveB = naiveB;
	}

	public BayesNet getBayesN() {
		return bayesN;
	}

	public void setBayesN(BayesNet bayesN) {
		this.bayesN = bayesN;
	}

	public Instances getiBaseTreinamento() {
		return iBaseTreinamento;
	}

	public void setiBaseTreinamento(Instances iBaseTreinamento) {
		this.iBaseTreinamento = iBaseTreinamento;
	}

	public Instances getiBaseTeste() {
		return iBaseTeste;
	}

	public void setiBaseTeste(Instances iBaseTeste) {
		this.iBaseTeste = iBaseTeste;
	}

	public Evaluation[] getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation[] evaluation) {
		this.evaluation = evaluation;
	}



	public String getcTreinamento() {
		return cTreinamento;
	}

	public void setcTreinamento(String cTreinamento) {
		this.cTreinamento = cTreinamento;
	}

//	public static void main(String[] args) {
//		
//		Ensemble e = new Ensemble();
//		
//		String aTestes[][] = {{"Vai se foder seu filho da puta! espero que voce morra!","0"},
//							  {"Se eu te acho na rua eu te arrebendo sua puta","0"},
//							  {"Seu ladrão tem de apodrecer na cadeia!","0"},
//							  {"Quero mais que voce se foda, só faz piada sem graça!","0"},
//							  {"Sua baleia, não vai intalar na porta!! hahaha","0"},
//							  {"Parabéns pela sua conquista!","1"},
//							  {"Hoje esta friozinho mas tá otimo para assistir seriado","1"},
//							  {"Poxa é dia dos namorados e eu aqui sem ninguém","1"},
//							  {"Até que enfim chegou a sexta feira!!!","1"},
//							  {"Eu to morrendo de saudades suas!","1"},
//							  {"Pensando em ir no mc donalds comer um xis","1"},
//							  {"Quero chegar em casa e tomar um banho","1"},
//							  {"Gosto de melancia","1"},
//							  {"Essa corrupta terrorista apresenta uma ameaca ao honesto povo Uruguaio","0"},
//							  {"Esse mortadela ja bebeu tanta cachaca q nao esta falando coisa com coisa","0"},
//							  {"Vai ser dificil a minha ha anos esta no ceu a sua ta na zona","0"},
//							  {"Que fique por la essa ANTA","0"},
//							  {"Toma vergonha na cara seu boiola maconheiro infectado","0"},
//							  {"O cara vai chupar uma piroca  vai","0"},
//							  {"Esse negro e o seu pai Sua mae deu pra ele quando jovem","0"},
//							  {"Ragnar e nome de travesti da Escandinavia","0"},
//							  {"Alem de burro e analfabeto","0"},
//							  {"Jkkkkkkkkkbosta de clubenao tem dinheiro pra contratar pra comprar camisa mas tem Estadiokkkkkkkkkvice eternopiada do Brasil Segunda eu volto","0"},
//							  {"outro asno que nao sabe nada esse artur","0"},
//							  {"Jogando o milho e sentando no sabugo ne maria","0"},
//							  {"Entrou no seu fedidinho Junior  Esse ui e proprio da sua gayzisse","0"},
//							  {"O dia do seu macho vai chegar Aguarde compre lenco pro choro Esterco humano","0"},
//							  {"Alexandre esta maluco so pode comeu titica quando crianca","0"},
//							  {"hoje vou apresentar o trabalho mais inútil que já fiz na faculdade","1"},
//							  {"e meu, sério, não sei o que a vida tem comigo, mas meus roles nunca terminam de uma forma normal como os roles de todo mundo","1"},
//							  {"na boa, eu só destruo minha vida cada dia mais, desisto","1"},
//							  {"mano, ser um bicho social é a pior e a melhor caracterÌstica do ser humano","1"},
//							  {"Quase esqueci completamente que tinha que pagar a conta de luz. Acordei hoje desesperada.","1"},
//							  {"Vocês também ficam viciados nas músicas e ouvem elas em loop até a morte???????","1"},
//							  {"gente vocês têm noção de como morar sozinha e cuidar das próprias contas e dinheiro é estressante? não tem dinheiro que chegue!!!!","1"},
//							  {"não fui na aula porque não consegui levantar da cama (tava muito quentinho lá). sou dessas.","1"},
//							  {"tinha que fazer uma limpa no meu twitter, sigo um monte de famosinho que não serve pra nada (que eu adorava antes, mas graças a deus cresci)","1"},
//							  {"Sabe quando tú tudo tão bem que tu começa a colocar defeito nas coisas? Pois então...","1"},
//							  {"Apenas gostaria de deixar minha indignação aqui por não ter fim de semana e ter aula dois turnos praticamente a semana toda que vem","1"},
//							  {"Minha bunda vai ficar quadrada até o fim do ano, aquelas cadeiras necessitam de mais estofamento por favor  ","1"},
//							  };
//		
//		e.setcTreinamento("treinamento.arff");
//		e.montaClassificadores();
//		e.montaTeste(aTestes);
//		Prediction predicao = e.predicao();
//		
//		
//		NominalPrediction np = (NominalPrediction) predicao;
//		
//		String saida;
//		String cAux;
//		
//		try {
//			//saida  = e.getEvaluation()[0].toMatrixString("** Naive Bayes **");
//			saida = e.getEvaluation()[1].toMatrixString("** SMO **");
//			//saida += e.getEvaluation()[2].toMatrixString("** Bayes Net **");
//			
//			//saida += e.getEvaluation()[0].toClassDetailsString("** Naive Bayes **");
//			saida += e.getEvaluation()[1].toClassDetailsString("** SMO **");
//			//saida += e.getEvaluation()[2].toClassDetailsString("** Bayes Net **");
//			//saida += "Resultado Predição \n";
//			//saida += e.getiBaseTeste().classAttribute().value((int)np.predicted());
//			saida += e.getEvaluation()[1].toSummaryString();
//			System.out.println(saida);
//						
//			
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//	}

}
