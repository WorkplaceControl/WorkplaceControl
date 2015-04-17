package br.com.gft.share;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class SystemLogs {

	public static File arquivo;
	public static FileReader fileReader;
	public static BufferedReader bufferedReader;
	public static FileWriter fileWriter;
	public static BufferedWriter bufferedWriter;
	public static String finalDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	public static String finalTime = new SimpleDateFormat("HH:mm:hh").format(Calendar.getInstance().getTime());
	

	public SystemLogs(String logs) {
		writeLogs(logs);
	}

	public static void writeLogs(String logs) {
		try {
			arquivo = new File("SystemLogs.log");
			fileReader = new FileReader(arquivo);
			bufferedReader = new BufferedReader(fileReader);
			
			Vector<String> texto = new Vector<String>();
			
			while (bufferedReader.ready()) {
				texto.add(bufferedReader.readLine());
			}
			
			fileWriter = new FileWriter(arquivo);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for (int i = 0; i < texto.size(); i++) {
				bufferedWriter.write(texto.get(i).toString());
				bufferedWriter.newLine();
			}
			
			bufferedWriter.write(finalDate + " - " + finalTime + " --- " + logs);
			bufferedReader.close();
			bufferedWriter.close();
		} catch (FileNotFoundException e) {
			try {
				arquivo.createNewFile();
				writeLogs(logs);
			} catch (IOException e1) {
				System.out.println("Erro ao Criar arquivos de logs");
			}
		} catch (IOException e2) {
			System.out.println("Erro ao Criar arquivos de logs");
		}
	}
}
