package br.com.gft.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * this Class is to control requests and responses of User(CRUD)
 * 
 * @author 
 * 
 */
@Controller
public class SystemLogsControl {
	@RequestMapping(value = "/SystemLogs", method = RequestMethod.GET)
	public String showLogs(Model model){
		String linha;
		List<String> logs = null;
		try{
			BufferedReader reader = new BufferedReader(new FileReader("SystemLogs.log"));
			logs = new ArrayList<String>();
			
			while((linha = reader.readLine()) != null)
				logs.add(linha);
			
			reader.close();
	        
	        model.addAttribute("logs", logs);
	        
		}catch(IOException e){
			
			model.addAttribute("logs", null);
			
		}
		return "SystemLogs";
	}
	
	@RequestMapping(value = "/ClearLogs", method = RequestMethod.POST)
	public String clearLogs(Model model){
		
		File logs = new File("SystemLogs.log"); 
		logs.delete();
		
		model.addAttribute("message", 0);
		
		return "SystemLogs";
	
	}
}


