package com.robert.fileRead;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


public class ReadLines {
	
	private File f;
	private Boolean checked;
	private String name;
	
	
	
	public ReadLines(File f, boolean checked, String name) {
		super();
		this.f = f;
		this.checked = checked;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ReadLines() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public File getF() {
		return f;
	}

	public void setF(File f) {
		this.f = f;
	}

	public static void readLines(File f) throws IOException{
		String extension = FilenameUtils.getExtension(f.getAbsolutePath());
		if(extension.equals("docx") || extension.equals("doc")) {
			try {
				   FileInputStream fis = new FileInputStream(f);
				   XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
				   XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
				   System.out.println(extractor.getText());
				   extractor.close();
				} catch(Exception ex) {
				    ex.printStackTrace();
				}
		}
		else if (extension.equals("txt")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"ISO-8859-1"));
			String line;
			int numberOfLines = 0;
			while((line=br.readLine()) != null) {
				System.out.println(line);
				numberOfLines++;
			}
			System.out.println("Number of lines in this file : "+numberOfLines);
			br.close();
		}
		else if (extension.equals("pdf")) {
			PDDocument document = PDDocument.load(new File(f.getAbsolutePath()));
			if (!document.isEncrypted()) {
			    PDFTextStripper stripper = new PDFTextStripper();
			    String text = stripper.getText(document);
			    System.out.println(text);
			}
			document.close();
		}
	}
	
	// public static void main(String[] args) {  // to be used as autonomous batch application 
	public static void fileRead(String name) {
		
		File f = new File(name);
		try {
			readLines(f);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
