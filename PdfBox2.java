package com.baeldung.pdf;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PdfBox2 {

	public static void main(String[] args) throws IOException {
		 PDDocument doc = new PDDocument();
		    PDPage page = new PDPage();
		    doc.addPage( page );
		    PDPageContentStream contentStream = new PDPageContentStream(doc, page);

		    drawTable(page, contentStream, 700, 100, getHeaders(), getData());

		    contentStream.close();
		    doc.save("C:\\temp\\test1.pdf" );
		    System.out.println("Done");

	}

	public static void drawTable(PDPage page, PDPageContentStream contentStream, float y, float margin,
			List<String> headers, List<DataObj> data) throws IOException {
		final int rows = data.size();
		final int cols = headers.size();
		final float rowHeight = 20f;
		final float tableWidth = page.getCropBox().getWidth() - margin - margin;
		final float tableHeight = rowHeight * (rows+1);
		final float colWidth = tableWidth / (float) cols;
		final float cellMargin = 5f;

		//draw the rows
		float nexty = y;
		for (int i = 0; i <= rows+1; i++) {
			drawLine(contentStream,margin, nexty, margin + tableWidth, nexty);
			nexty -= rowHeight;
		}

		//draw the columns
		float nextx = margin;
		for (int i = 0; i <= cols+1; i++) {
			drawLine(contentStream, nextx, y, nextx, y - tableHeight);
			nextx += colWidth;
		}

		//now add the text
		float textx = margin + cellMargin;
		float texty = y - 15;
		
			for (int i = 0; i < headers.size(); i++) {
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 13);
				printText(contentStream,textx, texty, colWidth, headers.get(i));
				textx += colWidth;
			}
			texty -= rowHeight;
			textx = margin + cellMargin;
			
			printData(contentStream, data,textx, texty,colWidth, rowHeight, margin, cellMargin);
				
	}
	
	private static void printData(PDPageContentStream contentStream, List<DataObj> data,
			float textx, float texty,
			float colWidth , float rowHeight, float margin, float cellMargin) throws IOException {
		for (DataObj eachObj : data) {
			contentStream.setFont(PDType1Font.HELVETICA, 12);
			printText(contentStream,textx, texty,colWidth, eachObj.getConfigName());
			textx += colWidth;
			
			printText(contentStream,textx, texty,colWidth, eachObj.getName());
			textx += colWidth;
		
			printText(contentStream,textx, texty,colWidth, eachObj.getInvoiceType());
			textx += colWidth;
		
			printText(contentStream,textx, texty,colWidth, eachObj.getFrequency());
			textx += colWidth;
			
			texty -= rowHeight;
			textx = margin + cellMargin;
			
		
		}
		
	}

	private static void printText(PDPageContentStream contentStream,
			float textx, float texty, float colWidth, String text) throws IOException {
		contentStream.beginText();
		contentStream.newLineAtOffset(textx, texty);
		contentStream.showText(text);
		contentStream.endText();
		//textx += colWidth;
	}
	private static void drawLine( PDPageContentStream content, float xstart, float ystart, float xend, float yend ) throws IOException{
	    content.moveTo(xstart, ystart); // moves "pencil" to a position
	    content.lineTo(xend, yend);     // creates an invisible line to another position
	    content.stroke();               // makes the line visible
	}
	
	
	 private static List<String> getHeaders(){
	    	return List.of("Billing","Name","Invoice","Frequency");
	    }
	    
	    private static List<DataObj> getData(){
	    	return List.of(new DataObj("SaTest", "BlueCross", "Client","Biweekly"),
	    			new DataObj("SaTest1", "BlueCross1", "Client1","Biweekly1"),
	    			new DataObj("SaTest2", "BlueCross2", "Client2","Biweekly2"),
	    			new DataObj("SaTest3", "BlueCross3", "Client3","Biweekly3"));
	    }
	    

		static class DataObj{
			String configName;
			String name;
			String invoiceType;
			String frequency;
			public DataObj(String configName, String name, String invoiceType, String frequency) {
				super();
				this.configName = configName;
				this.name = name;
				this.invoiceType = invoiceType;
				this.frequency = frequency;
			}
			@Override
			public String toString() {
				return configName + "," + name + "," + invoiceType+"," + frequency;
			}
			public String getConfigName() {
				return configName;
			}
			public void setConfigName(String configName) {
				this.configName = configName;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getInvoiceType() {
				return invoiceType;
			}
			public void setInvoiceType(String invoiceType) {
				this.invoiceType = invoiceType;
			}
			public String getFrequency() {
				return frequency;
			}
			public void setFrequency(String frequency) {
				this.frequency = frequency;
			}
			
		}
}
