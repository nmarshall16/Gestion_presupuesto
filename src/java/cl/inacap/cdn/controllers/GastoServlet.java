/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.AnhoProyect;
import cl.inacap.cdn.entities.Gasto;
import cl.inacap.cdn.entities.GastoMes;
import cl.inacap.cdn.entities.Proyecto;
import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Nicolas
 */
@WebServlet(name = "GastoServlet", urlPatterns = {"/Gasto.do"})
@MultipartConfig
public class GastoServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            final Part filePart = request.getPart("archivo"); // Obtiene el archivo
            String name = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // el nombre del archivo
            String ext = name.substring(name.length()-4); // Obtiene a extencion
            if(ext.equals("xlsx")){ // Se valida la extencion
                ServletContext context = request.getServletContext();
                final String path= context.getRealPath("/")+"files"; // Se obtiene la ruta del proyecto
                AnhoProyect anho = AnhoProyect.findById(Integer.parseInt(request.getParameter("idAnho")));
                Proyecto proyecto = anho.getProyectoId();
                final String fileName = "File_"+proyecto.getId()+"_"+anho.getNum()+"_"+request.getParameter("mes")+"."+ext; // Se genera el nombre del archivo
                OutputStream salida = null;
                InputStream filecontent = null;
                final PrintWriter writer = response.getWriter();
                try {
                    // Se guarda el archivo en la ruta se indicada 
                    salida = new FileOutputStream(new File(path + File.separator
                    + fileName));
                    filecontent = filePart.getInputStream();
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = filecontent.read(bytes)) != -1) {
                        salida.write(bytes, 0, read);
                    }
                    FileInputStream file = new FileInputStream(new File(path+"\\"+fileName)); // Se indica el archivo
                    XSSFWorkbook wb = new XSSFWorkbook(file);
                    XSSFSheet sheet = wb.getSheetAt(0); // Se obtiene la primera hoja del archivo
                    Row validacion = sheet.getRow(5); // Se obtiene la Secta fila del archivo
                    // Se valida el encabezado del archivo
                    if(validacion.getCell(1).getStringCellValue().toLowerCase().equals("id_comp")
                        && validacion.getCell(3).getStringCellValue().toLowerCase().equals("cod_cuenta")
                        && validacion.getCell(4).getStringCellValue().toLowerCase().equals("nom_cuenta")
                        && validacion.getCell(5).getStringCellValue().toLowerCase().equals("importe")
                        && validacion.getCell(6).getStringCellValue().toLowerCase().equals("fecha_contable")
                        && validacion.getCell(7).getStringCellValue().toLowerCase().equals("num_orden_compra")
                        && validacion.getCell(11).getStringCellValue().toLowerCase().equals("cod_proyecto")
                        && validacion.getCell(12).getStringCellValue().toLowerCase().equals("proyecto")
                        && validacion.getCell(15).getStringCellValue().toLowerCase().equals("cod_centro_resp")
                        && validacion.getCell(17).getStringCellValue().toLowerCase().equals("num_factura")
                        && validacion.getCell(18).getStringCellValue().toLowerCase().equals("rut_proveedor")
                        && validacion.getCell(19).getStringCellValue().toLowerCase().equals("nombre_proveedor")
                        && validacion.getCell(23).getStringCellValue().toLowerCase().equals("atributos_del_pago")
                        && validacion.getCell(27).getStringCellValue().toLowerCase().equals("asiento")
                    ){
                        int numFilas = sheet.getLastRowNum(); // Se obtiene el numero total de filas
                        boolean error = false;
                        for(int i = 6; i <= numFilas; i++){
                            Row fila = sheet.getRow(i);
                            if(fila.getCell(11).getStringCellValue().toUpperCase().equals(proyecto.getCodigo().toUpperCase())){
                                GastoMes gastoMes = new GastoMes();
                                gastoMes.setIdCompra(BigInteger.valueOf((long)fila.getCell(1).getNumericCellValue()));
                                gastoMes.setImporte(BigInteger.valueOf((long)fila.getCell(5).getNumericCellValue()));
                                gastoMes.setFecha(fila.getCell(6).getDateCellValue());
                                gastoMes.setOrdenCompra(fila.getCell(7).getStringCellValue());
                                gastoMes.setNumFac(BigInteger.valueOf((long)fila.getCell(17).getNumericCellValue()));
                                gastoMes.setRutProvedor(fila.getCell(18).getStringCellValue());
                                gastoMes.setNombreProvedor(fila.getCell(19).getStringCellValue());
                                gastoMes.setAtributoPago(fila.getCell(23).getStringCellValue());
                                gastoMes.setAsiento(fila.getCell(27).getStringCellValue());
                                gastoMes.setAnhoProyectId(anho);
                                Gasto gasto = new Gasto();
                                //out.print(fila.getCell(1).getNumericCellValue()+"-"); // id_comp
                                out.print(fila.getCell(3).getNumericCellValue()+"-"); // cod_cuenta
                                out.print(fila.getCell(4).getStringCellValue()+"-"); // nom_cuenta
                            //out.print(fila.getCell(5).getNumericCellValue()+"-"); // importe
                            //out.print(fila.getCell(6).getDateCellValue()+"-"); // fecha 
                            //out.print(fila.getCell(7).getStringCellValue()+"-"); // num_orden
                            //out.print(fila.getCell(11).getStringCellValue()+"-"); // cod_proyecto
                            //out.print(fila.getCell(12).getStringCellValue()+"-"); // proyecto
                            out.print(fila.getCell(15).getNumericCellValue()+"-"); // cod_centro_resp
                            //out.print(fila.getCell(17).getNumericCellValue()+"-"); // num_factura
                            //out.print(fila.getCell(18).getStringCellValue()+"-"); // rut_proveedor
                            //out.print(fila.getCell(19).getStringCellValue()+"-"); // nombre_proveedor
                            //out.print(fila.getCell(23).getStringCellValue()+"-"); // atributo_pago
                            //out.print(fila.getCell(27).getStringCellValue()+"<br>"); // asiento
                            }else{
                                error = true;
                                break;
                            }
                        }
                        if(error){
                            out.print("Error");
                        }
                    }else{
                        out.print("Archivo invalido");
                    }
                } catch (FileNotFoundException fne) {
                    writer.println("No se pudo cargar el archivo, por favor cargue "
                    + "el archivo nuevamente y verifique que el archivo no "
                    + "este protegido.");
                    writer.println("<br/> ERROR: " + fne.getMessage());
                    LOGGER.log(Level.SEVERE, "Problemas durante la subida del archivo. Error: {0}",
                    new Object[]{fne.getMessage()});
                }catch (NullPointerException ex){
                    out.print("Formato invalido");
                } finally {
                    if (salida != null) {
                        salida.close();
                    }
                    if (filecontent != null) {
                        filecontent.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                }
            }
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
