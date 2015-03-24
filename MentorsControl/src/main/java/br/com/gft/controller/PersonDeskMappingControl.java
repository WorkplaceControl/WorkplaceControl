package br.com.gft.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.gft.MentorsService.PersonDeskMappingService;

@Controller
public class PersonDeskMappingControl {

	
	@Autowired
	private PersonDeskMappingService service;

		@RequestMapping(value = "/Mapping")
	public String index() {
		return "Mapping";
	}

	/**
	 * Upload multiple file using Spring Controller
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public void uploadMultipleFileHandler(@RequestParam("file") MultipartFile[] files, HttpServletResponse response) throws Exception {

		String filepath = service.handleUpload(files);

		File downloadFile = new File(filepath);
        FileInputStream inputStream = new FileInputStream(downloadFile);
        
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment; filename=OutputMap.svg"); 
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
        inputStream.close();
	}
}
