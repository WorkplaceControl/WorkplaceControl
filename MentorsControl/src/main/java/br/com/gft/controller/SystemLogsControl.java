package br.com.gft.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping(value = "/DownloadLogs", method = RequestMethod.GET)
    public void downloadLogs(HttpServletResponse response) throws IOException {
        File file = new File("SystemLogs.log");
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment; filename=SystemLogs - " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " .log"); 

        IOUtils.copy(in, out);
        response.flushBuffer();
    }
	
}


