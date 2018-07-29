/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nicolas
 */
@WebServlet(name = "PdfServlet", urlPatterns = {"/PDF"})
public class PdfServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();
        String cuenta = request.getParameter("cuenta");
        System.out.print(cuenta);
        try{
           try{
               Document documento = new Document(PageSize.A2.rotate());
               PdfWriter.getInstance(documento, out);
               documento.open();
               Paragraph par1 = new Paragraph();
               Font fuente = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL,BaseColor.BLACK);
               String cabecera = "Libro Mayor:"+"Capacitacion"+"\n"
                               + "Fecha:"+"31-10-2017"+"\n"
                               + "Sociedad: Universidad Tecnologica de Chile INACAP"+"\n"
                               + "Rut: 72.012.000-3"+"\n"
                               + "Periodo:"+"Noviembre"+"\n";
               par1.add(new Phrase(cabecera, fuente));
               par1.setAlignment(Element.ALIGN_LEFT);
               par1.add(new Phrase(Chunk.NEWLINE));
               ServletContext context = request.getServletContext();
               System.out.print(context.getRealPath("/")+"files/logo.png");
               Image logo = Image.getInstance(context.getRealPath("/")+"files/logo.png");
               logo.setAlignment(Image.RIGHT | Image.TEXTWRAP);
               logo.scaleToFit(100, 100);
               documento.add(logo);
               documento.add(par1);
               PdfPTable tabla = new PdfPTable(100);
               tabla.setWidthPercentage(100);
               tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
               tabla.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
               //Se establece la cabecera de la tabla
               Font titulos = new Font(Font.FontFamily.COURIER,8,Font.BOLD,BaseColor.BLACK);
               String[] enca = {"ID_COMP","COD_CUENTA","NOM_CUENTA","DEBE","HABER",
               "SALDO","IMPORTE","FECHA_CONTABLE","NUM_ORDEN_COMPRA","COD_PROYECTO",
               "PROYECTO","COD_CENTRO_RESP","NUM_FACTURA","RUT_PROVEEDOR","NOMBRE_PROVEEDOR",
               "ATRIBUTOS_DEL_PAGO","ASIENTO","HOMOLOGACION"};
               PdfPCell cell;
               int[] largo = new int[18];
               int total = 0;
               for(int i=0; i<enca.length; i++){
                   cell = new PdfPCell(new Paragraph(enca[i],titulos));
                   cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                   if(enca[i].length() <= 6){
                       total += 3;
                       largo[i] = 3;
                       cell.setColspan(3);
                   }
                   else if (enca[i].length() <= 8) {
                       total += 4;
                       largo[i] = 4;
                       cell.setColspan(4);
                   }
                   else if (enca[i].length() <= 10) {
                       total += 5;
                       largo[i] = 5;
                       cell.setColspan(5);
                   }
                   else if (enca[i].length() <= 12) {
                       total += 6;
                       largo[i] = 6;
                       cell.setColspan(6);
                   }
                   else if (enca[i].length() <= 14) {
                       total += 7;
                       largo[i] = 7;
                       cell.setColspan(7);
                   }
                   else if (enca[i].length() <= 16) {
                       total += 8;
                       largo[i] = 8;
                       cell.setColspan(8);
                   }
                   else if (enca[i].length() <= 18) {
                       total += 9;
                       largo[i] = 9;
                       cell.setColspan(9);
                   }
                   tabla.addCell(cell);
               }
               System.out.print(total);
               // Se agregan los gastos a la tabla
               String[] filasG1 = {"1081642","5011080","ASESORIAS ADMINISTRATIVAS","555.556","-",
               "11.479.965","555.556","15-11-2017","UTC815350","PE16VIPSERCDN02",
               "SAN FERNANDO","400060800","613","15120366-3","ANDREA NATALI CASTRO SILVA",
               "(CHK CHILE 008001968506 07180727 1000001)","CPT7108924","Capacitacion"};
               Font filas = new Font(Font.FontFamily.COURIER,7,Font.NORMAL,BaseColor.BLACK);
               for(int i=0; i<filasG1.length; i++){
                   cell = new PdfPCell(new Paragraph(filasG1[i],filas));
                   cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                   cell.setColspan(largo[i]);
                   tabla.addCell(cell);
               }
               String[] filasG2 = {"15431","5012130","ESTADÍA EN EL PAÍS","85.556","-",
               "9.479.965","85.556","27-11-2017","","PE16VIPSERCDN02",
               "SAN FERNANDO","400060800","","","",
               "","EX17135335","Capacitacion"};
               for(int i=0; i<filasG2.length; i++){
                   cell = new PdfPCell(new Paragraph(filasG2[i],filas));
                   cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                   cell.setColspan(largo[i]);
                   tabla.addCell(cell);
               }
               tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
               documento.add(tabla);
               documento.close();
           }catch(Exception ex){
               ex.getMessage();
           }
        }finally{
        
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
