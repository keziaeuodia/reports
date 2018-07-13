package com.vaadin.reports;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Service
public class FilmService {

    @Autowired
    FilmMapper filmMapper;

    public  void printTable() throws IOException {
        //Create the PDF documents, define the page size and rotate it
        String destination = "/home/kezia/films.pdf";
        PdfWriter writer = new PdfWriter(destination);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4.rotate());

        //Define the page format
        document.setMargins(20, 20, 20, 20);
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

        //Define a table
        Table table = new Table(new float[]{1, 5, 10, 2, 3, 2, 1,});
        table.setWidth(UnitValue.createPercentValue(100));

        //Populate an array with films from the DB
        ArrayList<Film> filmArrayList = getAllFilms();

        //Process the table's headers
        writeHeaders(table, bold);

        //Process the table's cells
        writeFilms(table, filmArrayList, font);

        //Add the table and close the file
        document.add(table);
        document.close();

    }

    public ArrayList<Film> getAllFilms() {
        //Calls getAllFilms method on FilmMapper
        return filmMapper.getAllFilms();
    }

    public void writeHeaders(Table table, PdfFont font){
        //Define the table's headers
        String headers =  "Film ID;Title;Description;Release Year;Language;Length;Rating";
        //Tokenize the headers
        StringTokenizer tokenizer = new StringTokenizer(headers, ";");
        //Go through the tokens printing them as headers
        while(tokenizer.hasMoreTokens())
            table.addHeaderCell(
                    new Cell().add(
                            new Paragraph(tokenizer.nextToken()).setFont(font)));
    }

    public  void writeFilms(Table table, ArrayList<Film> filmsArray, PdfFont font) {

        for (Film film: filmsArray) {
            table.addCell(  new Cell().add( new Paragraph(Integer.toString(film.getFilm_id()))));
            table.addCell(  new Cell().add( new Paragraph(film.getTitle())));
            table.addCell(  new Cell().add( new Paragraph(film.getDescription())));
            table.addCell(  new Cell().add( new Paragraph(Integer.toString(film.getRelease_year()))));
            table.addCell(  new Cell().add( new Paragraph(film.getLanguage())));
            table.addCell(  new Cell().add( new Paragraph(film.getLength() + " min.")));
            table.addCell(  new Cell().add( new Paragraph(film.getRating())));
        }
    }
}
