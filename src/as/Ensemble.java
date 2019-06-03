package as;
import java.util.ArrayList;
import java.util.Iterator;

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

			//System.out.println(evaluation[0].toSummaryString());
			//System.out.println(evaluation[1].toClassDetailsString());
			//System.out.println(evaluation[2].toSummaryString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nReturn;
	}

	public Instances montaTeste(String cTexto,int classe) {
		PreProcessamento preproc = new PreProcessamento();
		String    cTeste = cTexto.trim();
		Instances teste = new Instances(iBaseTreinamento, 0);

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

	public static void main(String[] args) {
		Ensemble e = new Ensemble();
		e.montaClassificadores();
		e.montaTeste("seu filho da puta arrombado do caralho",0);
		e.predicao();


	}

}
