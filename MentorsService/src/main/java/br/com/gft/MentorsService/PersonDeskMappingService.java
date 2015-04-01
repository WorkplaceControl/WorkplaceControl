package br.com.gft.MentorsService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Service
public class PersonDeskMappingService {

	private String tempDir = System.getProperty("user.dir") + File.separator + "tmpFiles";

	public String handleUpload(MultipartFile[] files) throws Exception {
		String[] fileNames = { "OutputMap.svg", "personDeskMap.xls" };

		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = fileNames[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("user.dir");
				File dir = new File(tempDir);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

			} catch (Exception e) {
				throw new Exception("Couldn't upload your file. Please check your input!");
			}
		}
		return updateSVG(fileNames);
	}

	private Map<String, String> getEmployeesFromExcel(String file) {
		Workbook wb;
		Map<String, String> employeesMap = new HashMap<String, String>();
		try {
			wb = new HSSFWorkbook(new FileInputStream(file));
			Sheet sheet = wb.getSheetAt(0);
			
			for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
				Row row = sheet.getRow(j);
				Cell desk = row.getCell(0);
				Cell person = row.getCell(1);
				employeesMap.put(desk.getStringCellValue(), person.getStringCellValue());
			}
		} catch (Exception e) {
			System.out.println("Check you Excel does not countains messy lines");
		}
		return employeesMap;
	}

	private String updateSVG(String[] fileNames) throws Exception {

		String svgTemplate = tempDir + File.separator + fileNames[0];
		String excelPersonDeskMap = tempDir + File.separator + fileNames[1];
		
		try {
			File svgFile = new File(svgTemplate);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setValidating(false);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document svgDocument = dBuilder.parse(svgFile);
			svgDocument.getDocumentElement().normalize();

			NodeList nList = svgDocument.getElementsByTagName("text");
			Map<String, String> personDeskMapFromExcel = getEmployeesFromExcel(excelPersonDeskMap);

			for (int counter = 0; counter < nList.getLength(); counter++) {

				Node nNode = nList.item(counter);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					Double xAxis = Double.valueOf(eElement.getAttribute("x"));
					String nodeText = eElement.getTextContent();
					String[] personDeskFromSVG = nodeText.split(":");

					if (personDeskMapFromExcel.containsKey(personDeskFromSVG[0])) {
						int diffInLetterNumbers = personDeskFromSVG[1].length() - personDeskMapFromExcel.get(personDeskFromSVG[0]).length();

						xAxis = xAxis + diffInLetterNumbers * 2.2;
						nodeText = nodeText.replace(personDeskFromSVG[1], " " + personDeskMapFromExcel.get(personDeskFromSVG[0]));

						eElement.setTextContent(nodeText);
						eElement.setAttribute("x", String.valueOf(xAxis));
					}
				}
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(svgDocument);
			String outputFilePath = tempDir + File.separator + "OutputMap.svg";
			StreamResult result = new StreamResult(new File(outputFilePath));
			transformer.transform(source, result);
			return outputFilePath;
		} catch (Exception e) {
			throw new Exception("Updating SGV failed. Check you files!");
		}
	}

}
