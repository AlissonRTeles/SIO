package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import weka.core.Instances;

public class ArquivoBase {
	private String               sNomeArquivo;
	private LinkedList<String>   aLinhasArquivo;
	private Instances            instancias;
	private File     			 file;
	
	
	public ArquivoBase(String sNomeArquivo) {
		super();
		this.sNomeArquivo  = sNomeArquivo;
		this.file          = new File(sNomeArquivo);
		this.aLinhasArquivo= new LinkedList<String>();
		
		try {
			this.aLinhasArquivo= ArquivoParaMatriz();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArquivoBase(LinkedList<String> aLinhasArquivo) {
		super();
		this.aLinhasArquivo = aLinhasArquivo;
		this.sNomeArquivo   = "LinkedList";
	}
	
	public ArquivoBase(Instances instancias) {
		super();
		this.instancias = instancias;
		this.sNomeArquivo = "Instances";
	}
	
	public ArquivoBase () {
	}

	public void AbreArquivo() {
		this.file = new File(sNomeArquivo);
		try {
			this.aLinhasArquivo= ArquivoParaMatriz();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public LinkedList<String> ArquivoParaMatriz() throws IOException {
		FileReader arq;
		
		aLinhasArquivo = new LinkedList<String>();
		
		arq = new FileReader(file);
		BufferedReader lerArq = new BufferedReader(arq);
	
		String linha = lerArq.readLine();
		
		while(linha != null) {
			
			linha = linha.trim();
			
			aLinhasArquivo.add(linha);
			
			linha  = lerArq.readLine();
		}
		
		this.sNomeArquivo = file.getName();
		return aLinhasArquivo;
	}
	
	
	public File MatrixParaArquivo() throws IOException{
		String aux = null;
		File arqProc = new File(this.sNomeArquivo);
		FileWriter fileW = new FileWriter(arqProc);
		BufferedWriter buffW = new BufferedWriter (fileW);
	
			
		for (int i = 0; i < aLinhasArquivo.size(); i++) {
			aux+= aLinhasArquivo.get(i) + "\n";
		}
		
		buffW.write(aux);
		
		buffW.flush();
		buffW.close();

		file = arqProc;
		
		return file;
	}

	public String getsNomeArquivo() {
		return sNomeArquivo;
	}

	public void setsNomeArquivo(String sNomeArquivo) {
		this.sNomeArquivo = sNomeArquivo;
	}

	public LinkedList<String> getaLinhasArquivo() {
		return aLinhasArquivo;
	}

	public void setaLinhasArquivo(LinkedList<String> aLinhasArquivo) {
		this.aLinhasArquivo = aLinhasArquivo;
	}

	public Instances getInstancias() {
		return instancias;
	}

	public void setInstancias(Instances instancias) {
		this.instancias = instancias;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}


