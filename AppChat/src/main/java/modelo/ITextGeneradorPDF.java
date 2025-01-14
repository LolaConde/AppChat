package modelo;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.AreaBreakType;

public class ITextGeneradorPDF extends GeneradorPDF {
	
	private PdfDocument pdfDoc;
	private Document doc;
	private PdfWriter writer;
	
	private Paragraph parrafoActual;

	@Override
	public boolean nuevoPDF(String url) {
		// Nuevo documento
		try {
			writer = new PdfWriter(url);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		pdfDoc = new PdfDocument(writer);
		doc = new Document(pdfDoc);
		
		// Párrafo actual
		parrafoActual = new Paragraph();
		return true;
	}

	@Override
	public void añadirMetadatos(String titulo, String autor, String asunto, String palabrasClave) {
		PdfDocumentInfo metadatos = pdfDoc.getDocumentInfo();
		metadatos.setTitle(titulo);
		metadatos.setAuthor(autor);
		metadatos.setSubject(asunto);
		metadatos.setKeywords(palabrasClave);
	}

	@Override
	public void añadirTextoNormal(String texto) {
		parrafoActual.add(texto);
	}

	@Override
	public void añadirTextoNegrita(String texto) {
		Text text = new Text(texto);
		text.simulateBold();
		parrafoActual.add(text);
	}

	@Override
	public void añadirTextoCursiva(String texto) {
		Text text = new Text(texto);
		text.simulateItalic();
		parrafoActual.add(text);
	}

	@Override
	public void añadirImagen(BufferedImage imagen) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(imagen, "png", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image iTextImage = new Image(ImageDataFactory.create(baos.toByteArray()));
		parrafoActual.add(iTextImage);
	}

	@Override
	public void finalizarParrafo() {
		doc.add(parrafoActual);
		parrafoActual = new Paragraph();
	}

	@Override
	public void siguientePagina() {
		pdfDoc.addNewPage();
		doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
	}

	@Override 
	public void finalizarPDF() {
		doc.close();
		// Evitar modificarlo ya cerrado
		doc = null;
		pdfDoc = null;
		writer = null;
		parrafoActual = null;
	}

}
