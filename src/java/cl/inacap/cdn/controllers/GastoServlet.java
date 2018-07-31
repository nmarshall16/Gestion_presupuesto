/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.AnhoProyect;
import cl.inacap.cdn.entities.Cuenta;
import cl.inacap.cdn.entities.FuenteF;
import cl.inacap.cdn.entities.Gasto;
import cl.inacap.cdn.entities.GastoMes;
import cl.inacap.cdn.entities.Gastoexc;
import cl.inacap.cdn.entities.Homologar;
import cl.inacap.cdn.entities.Proyecto;
import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            String opcion = request.getParameter("op");
            switch(opcion){
                case "homologar":
                    // Homologar gastos 
                    ArrayList<String> errorH = new ArrayList<>();
                    try{ 
                        String[] gast = request.getParameterValues("gastos");
                        String cuen = request.getParameter("cuentas");
                        Cuenta cuenta = Cuenta.findById(new BigInteger(cuen));
                        String errorHom = "";
                        for (String resultado : gast) {
                            GastoMes gasto = GastoMes.findById(new BigInteger(resultado));
                            if(Gastoexc.validarGastoExc(gasto.getGastoId())){ // Se valida si el gasto es un gasto excepcional 
                                List<Homologar> homExc = Homologar.findHomologaciones(gasto); // Se busca alguna homologación asociada al gasto
                                if(homExc.size()>0){ 
                                    homExc.get(0).actualizarHomologacion(cuenta);
                                }else{
                                    Homologar homologar = new Homologar();
                                    homologar.setCuentaId(cuenta);
                                    homologar.setGastoMesId(gasto);
                                    if(GastoMes.validaCuenta(gasto)){ // Se valida que el gasto este asociado correctamente
                                        homologar.setEstado('V'); 
                                    }else{
                                        homologar.setEstado('P');
                                    }
                                    errorHom = homologar.addHomologacion(); // Se homologa el gasto y se modifica el presupuesto
                                    if(errorHom.equals("")){
                                            gasto.actualizarEstado('R'); // Se actualiza el estado del gasto para identificarse en la tabla de gastos.jsp
                                    }else{
                                        errorH.add("Error: "+errorHom+" "+gasto.getGastoId().getNombre()+" a la cuenta "+cuenta.getNombre());
                                        homologar.removeHomologacion(); // En caso de que exista error se borrara la homologacion realizada para evitar problemas
                                    }
                                }
                            }else{
                                // En caso de no ser un gasto excepcional se realiza el siguiente proceso
                                Homologar hom = Homologar.findHomologacion(gasto);
                                if(hom!=null){
                                   hom.actualizarHomologacion(cuenta);
                                }else{
                                    Homologar homologar = new Homologar();
                                    homologar.setCuentaId(cuenta);
                                    homologar.setGastoMesId(gasto);
                                    if(GastoMes.validaCuenta(gasto)){
                                        homologar.setEstado('V');
                                    }else{
                                        homologar.setEstado('P');
                                    }
                                    errorHom = homologar.addHomologacion();
                                    if(errorHom.equals("")){
                                            gasto.actualizarEstado('R');
                                    }else{
                                        errorH.add("Error: "+errorHom+" "+gasto.getGastoId().getNombre()+" a la cuenta "+cuenta.getNombre());
                                        homologar.removeHomologacion();
                                    }
                                }
                            }
                        }
                        gast = request.getParameterValues("gastosExc");
                        // Se optienen los gastos excepcionales en caso de que existan 
                        if(gast != null){
                            cuen = request.getParameter("cuentasExc");
                            cuenta = Cuenta.findById(new BigInteger(cuen));
                            // Se realiza el mismo proceso que se realizo anteriormente
                            for (String resultado : gast) {
                                GastoMes gasto = GastoMes.findById(new BigInteger(resultado));
                                if(Gastoexc.validarGastoExc(gasto.getGastoId())){
                                    List<Homologar> homExc = Homologar.findHomologaciones(gasto);
                                    if(homExc.size()>1){
                                        homExc.get(1).actualizarHomologacion(cuenta);
                                    }else{
                                        Homologar homologar = new Homologar();
                                        homologar.setCuentaId(cuenta);
                                        homologar.setGastoMesId(gasto);
                                        if(GastoMes.validaCuenta(gasto)){
                                            homologar.setEstado('V');
                                        }else{
                                            homologar.setEstado('P');
                                        }
                                        errorHom = homologar.addHomologacion();
                                        if(errorHom.equals("")){
                                            gasto.actualizarEstado('R');
                                        }else{
                                            errorH.add("Error: "+errorHom+" "+gasto.getGastoId().getNombre()+" a la cuenta "+cuenta.getNombre());
                                            homologar.removeHomologacion();
                                        }
                                    }
                                }
                            }
                        }
                    }catch(Exception ex){
                        errorH.add("Error: "+ex.getMessage()+" "+ex.toString());
                    }
                    BigInteger mesPend = new BigInteger(request.getParameter("mes"));
                    AnhoProyect anhoPend = AnhoProyect.findById(Integer.parseInt(request.getParameter("idAnho")));
                    List<GastoMes> gastosPend = GastoMes.findGastos(mesPend, anhoPend);
                    request.setAttribute("error", errorH);
                    request.setAttribute("mes", mesPend);
                    request.setAttribute("anho", anhoPend.getId());
                    request.setAttribute("gastos", gastosPend);
                    request.setAttribute("estado", GastoMes.validaEstadoGastos(gastosPend));
                    request.getRequestDispatcher("gastos.jsp").forward(request, response);
                break;
                case "marcarGastos":
                    // Se carga el formulario para homologar los gastos 
                    String[] result = request.getParameterValues("gastos");
                    ArrayList<GastoMes> gastos = new ArrayList<>();
                    ArrayList<GastoMes> excepciones = new ArrayList<>();
                    ArrayList<String> advertencias = new ArrayList<>();
                    for (String resultado : result) {
                        GastoMes gasto = GastoMes.findById(new BigInteger(resultado));
                        if(gasto.getStatus()!='P'){
                            advertencias.add("El gasto '"+gasto.getGastoId().getNombre()+"' ya a sido homologado");
                        }
                        if(Gastoexc.validarGastoExc(gasto.getGastoId())){
                           excepciones.add(gasto);
                           advertencias.add("El gasto '"+gasto.getGastoId().getNombre()+"' es un gasto excepcional por lo cual debe asociarlo a dos cuentas");
                        }
                        gastos.add(gasto);
                    }
                    List<Cuenta> cuentas = Cuenta.findAll();
                    BigInteger mesHo = new BigInteger(request.getParameter("mes"));
                    AnhoProyect anhoHo = AnhoProyect.findById(Integer.parseInt(request.getParameter("idAnho")));
                    request.setAttribute("mes", mesHo);
                    request.setAttribute("anho", anhoHo.getId());
                    request.setAttribute("gastos", gastos);
                    request.setAttribute("excepciones", excepciones);
                    request.setAttribute("advertencias", advertencias);
                    request.setAttribute("cuentas", cuentas);
                    request.getRequestDispatcher("homologar.jsp").forward(request, response);
                break;
                case "cargarGastos":
                    try{
                    final Part filePart = request.getPart("archivo"); // Obtiene el archivo
                    String name = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // el nombre del archivo
                    String ext = name.substring(name.length()-4); // Obtiene a extencion
                    if(ext.equals("xlsx")){ // Se valida la extencion
                        ServletContext context = request.getServletContext();
                        final String path= context.getRealPath("/")+"files"; // Se obtiene la ruta del proyecto
                        AnhoProyect anho = AnhoProyect.findById(Integer.parseInt(request.getParameter("idAnho")));
                        Proyecto proyecto = anho.getProyectoId();
                        BigInteger mes = new BigInteger(request.getParameter("mes"));
                        final String fileName = "File_"+proyecto.getId()+"_"+anho.getNum()+"_"+mes+"."+ext; // Se genera el nombre del archivo
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
                            boolean error = false;
                            String detalleError = "No error"; 
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
                                ArrayList<GastoMes> gastosAdd = new ArrayList<>();
                                Date fecha;
                                int fi = 0;
                                for(int i = 6; i <= numFilas; i++){
                                    Row fila = sheet.getRow(i);
                                    if(fila.getCell(11).getStringCellValue().toUpperCase().equals(proyecto.getCodigo().toUpperCase())){ // Se valida que el gasto pertenesca al proyecto seleccionado
                                        // Se obtienen los datos del archivo excel y se asignan a un objeto GastoMes
                                        GastoMes gastoMes = new GastoMes();
                                        gastoMes.setIdCompra(BigInteger.valueOf((long)fila.getCell(1).getNumericCellValue()));
                                        gastoMes.setImporte(BigInteger.valueOf((long)fila.getCell(5).getNumericCellValue()));
                                        try{
                                        fecha = fila.getCell(6).getDateCellValue();
                                        }catch(Exception e){
                                            fecha= null;
                                        }
                                        if(fecha!=null){
                                            gastoMes.setFecha(fila.getCell(6).getDateCellValue());
                                        }
                                        gastoMes.setOrdenCompra(fila.getCell(7).getStringCellValue());
                                        gastoMes.setNumFac(BigInteger.valueOf((long)fila.getCell(17).getNumericCellValue()));
                                        gastoMes.setRutProvedor(fila.getCell(18).getStringCellValue());
                                        gastoMes.setNombreProvedor(fila.getCell(19).getStringCellValue());
                                        gastoMes.setAtributoPago(fila.getCell(23).getStringCellValue());
                                        gastoMes.setAsiento(fila.getCell(27).getStringCellValue());
                                        gastoMes.setAnhoProyectId(anho);
                                        gastoMes.setMes(mes);
                                        gastoMes.setStatus('P');
                                        gastoMes.setTipo('G');
                                        Gasto gasto = new Gasto();
                                        gasto = gasto.findGasto(BigDecimal.valueOf(fila.getCell(15).getNumericCellValue()), BigInteger.valueOf((long)fila.getCell(3).getNumericCellValue()));
                                        // Se busca si el gasto ya se encuentra registrado en caso de no estarlo se registra en la base de datos
                                        if(gasto != null){
                                            gastoMes.setGastoId(gasto);
                                        }else{
                                            FuenteF financiamiento = FuenteF.findById(BigDecimal.valueOf(fila.getCell(15).getNumericCellValue()));
                                            Gasto newGasto = new Gasto(
                                            BigInteger.valueOf((long)fila.getCell(3).getNumericCellValue()),
                                            fila.getCell(4).getStringCellValue().toUpperCase(),
                                            financiamiento
                                            );
                                            newGasto.addGasto();
                                            gastoMes.setGastoId(newGasto);
                                        }
                                        gastoMes.addGastoMes(); // Se añade a la base de datos
                                        gastosAdd.add(gastoMes); // Se almacena en un arreglo de los gastos ya insertados a la base de datos
                                    }else{
                                        error = true;
                                        fi = i;
                                        break;
                                    }
                                }
                                if(error){
                                    GastoMes gastosB = new GastoMes();
                                    gastosB.removeGastosMes(gastosAdd); // Se borran los gasto insertados 
                                    detalleError = "Se a detectado un gasto correspondiente a otro proyecto en la fila "+fi+" valide que este asociado al proyecto "+proyecto.getNombre();
                                }
                            }else{
                                error = true;
                                detalleError = "El archivo que intenta subir no cuenta con el formato correcto";
                            }
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                // Return ajax response (e.g. write JSON or XML).
                                Map <String, Object> map = new HashMap<String, Object>();
                                    map.put("error", error);
                                    map.put("detalle", detalleError);
                                    map.put("anho", request.getParameter("idAnho"));
                                    map.put("mes", mes);
                                    response.getWriter().write(new Gson().toJson(map));
                            }
                        } catch (FileNotFoundException fne) {
                            String detalle = "No se pudo cargar el archivo, por favor cargue "
                                    + "el archivo nuevamente y verifique que el archivo no "
                                    + "este protegido.";
                            detalle+="<br/> ERROR: " + fne.getMessage();
                            /*LOGGER.log(Level.SEVERE, "Problemas durante la subida del archivo. Error: {0}",
                            new Object[]{fne.getMessage()});
                            */
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                errorAjax(response, detalle);
                            }
                        }catch (NullPointerException ex){
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                errorAjax(response, ex.toString());
                            }
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
                    }else{
                        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                            errorAjax(response, "Tipo de archivo incorrecto por favor validar que el archivo cuente con la extencion xlsx");
                        }
                    }
                    }catch(NullPointerException ex){
                        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                            errorAjax(response, "No se a cargado ningun archivo");
                        }
                    }catch(Exception ex){
                    
                    }
                break;
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
    public void errorAjax(HttpServletResponse response, String error) throws IOException{
        Map <String, Object> map = new HashMap<String, Object>();
        map.put("error", true);
        map.put("detalle", error);
        response.getWriter().write(new Gson().toJson(map));
    }
}
