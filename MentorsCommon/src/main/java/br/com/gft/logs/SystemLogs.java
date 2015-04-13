package br.com.gft.logs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class SystemLogs {

	File arquivo;
	FileReader fileReader;
	BufferedReader bufferedReader;
	FileWriter fileWriter;
	BufferedWriter bufferedWriter;
	
	public SystemLogs(String logs){
		
		writeLogs(logs);
	}
	private void writeLogs (String logs){
		
		try {
			arquivo = new File ("SystemLogs.log");
			fileReader = new FileReader(arquivo);
			bufferedReader = new BufferedReader(fileReader);
			Vector texto = new Vector();
			while(bufferedReader.ready())
			{
				texto.add(bufferedReader.readLine());
			}
			fileWriter = new FileWriter (arquivo);
			bufferedWriter = new BufferedWriter (fileWriter);
			for(int i=0; i < texto.size(); i++){
				bufferedWriter.write(texto.get(i).toString());
				bufferedWriter.newLine();
			}
				bufferedWriter.write(logs);
				bufferedReader.close();
				bufferedWriter.close();
		} catch (FileNotFoundException e) {
			
			try {
				arquivo.createNewFile();
				writeLogs(logs);
				
			} catch (IOException e1) {
				System.out.println("Erro ao Criar arquivos de logs");
			}
		}
		catch (IOException e2)
		{
			System.out.println("Erro ao Criar arquivos de logs");
		}
	}
}
