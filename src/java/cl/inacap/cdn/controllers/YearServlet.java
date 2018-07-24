/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dell
 */
public class YearServlet extends HttpServlet {

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
            int op = 1;
            if (request.getParameter("op") != null ) {
                op = Integer.parseInt(request.getParameter("op"));
            }
            switch(op){
                case 1: 
                    // CARGAR VISTA PARA CREACIÓN DE NUEVO AÑO
                    // Obteniendo cuentas
                        request.setAttribute("ctas", Cuenta.findAll());
                    // Obteniendo id de proyecto
                        BigDecimal idPro = BigDecimal.valueOf(Integer.parseInt(request.getParameter("pro"))); 
                    // Setear datos obtenidos y construir vista
                        request.setAttribute("proyecto", Proyecto.findById(idPro));
                        request.setAttribute("anho", String.valueOf(AnhoProyect.countYearsOfProyect(Proyecto.findById(idPro))));
                        request.getRequestDispatcher("nuevoAno.jsp").forward(request, response);
                    break;
                case 2: 
                    // GUARDAR NUEVO AÑO DE PROYECTO
                    try {
                    // Guardar Nuevo Año de proyecto
                        AnhoProyect anho = saveYear(request, response);
                    // GUARDAR PRESUPUESTOS
                        savePresupuestos(request, response, anho);
                        response.sendRedirect("Proyect.do?idProyect="+anho.getProyectoId().getId()+"&accion=mostrarProyecto");
                    } catch (NullPointerException | IOException | ServletException e) {
                    // AGREGAR SENTENCIAS EN CASO DE ERROR!
                        out.print(e.getClass()+"<br>");
                    // Imprimiendo detalle de error
                        StackTraceElement[] stack = e.getStackTrace();
                        String trace = "";
                        for(StackTraceElement linea : stack){
                           trace += linea.toString()+"<br>";
                        }
                        out.print(trace);
                        out.print(e.initCause(e.getCause()));
                        out.print("<br>");
                    }
                    break;
                case 3:
                    BigInteger mes = new BigInteger(request.getParameter("mes"));
                    AnhoProyect anho = AnhoProyect.findById(Integer.parseInt(request.getParameter("idAnho")));
                    List<GastoMes> gastos = GastoMes.findGastos(mes, anho);
                    if(gastos.size() > 0){
                        request.setAttribute("mes", mes);
                        request.setAttribute("anho", anho.getId());
                        request.setAttribute("gastos", gastos);
                        request.setAttribute("estado", GastoMes.validaEstadoGastos(gastos));
                        request.getRequestDispatcher("gastos.jsp").forward(request, response);
                    }else{
                        request.setAttribute("mes", mes);
                        request.setAttribute("anho", anho.getId());
                        request.setAttribute("opcion", "cargarGastos");
                        request.getRequestDispatcher("cargarArchivo.jsp").forward(request, response);
                    }
                    break;
                case 4: 
                    break;
                default: 
                    break;
            }
        }
    }
    
    private AnhoProyect saveYear(HttpServletRequest request, HttpServletResponse response) 
            throws IOException{
        
        int numAnho, totalDis, total;
        Date fechaIni, fechaTer;
        Proyecto proyecto;
        PrintWriter out = response.getWriter();
                    
        // ASIGNANDO PROYECTO PROVISORIO!!!!!!!!!!!!
        // Obteniendo Proyecto
        BigDecimal idPro = BigDecimal.valueOf(Integer.parseInt(request.getParameter("pro")));
        proyecto = Proyecto.findById(idPro);

        if (proyecto != null) {
            // Obteniendo numero de años de proyecto
            numAnho = AnhoProyect.countYearsOfProyect(proyecto);

            //Obteniendo y transformando fechas
            String[] fechas = getFechas(request.getParameter("fechas").split(" - "));
            fechaIni = new Date(fechas[0]);
            fechaTer = new Date(fechas[1]);
            
            // DATOS DE PRUEBA PROVISORIOS
            out.print("Impreso desde saveYear() <br><br>");
            out.print(BigInteger.valueOf(numAnho)+"<br>");
            out.print(fechaIni.toString()+"<br>");
            out.print(fechaTer.toString()+"<br>");
            out.print(calculateTotal(request)+"<br>");
            out.print(proyecto+"<br>");

            // CREAR AÑO DE PROYECTO Y VALIDAR DATOS
            AnhoProyect anhoPro = new AnhoProyect(
               BigInteger.valueOf(numAnho), 
               fechaIni, 
               fechaTer, 
               calculateTotal(request), 
               calculateTotal(request), 
               proyecto
            );                   

            // Guardar Nuevo Año de proyecto
            anhoPro = AnhoProyect.insAnho(anhoPro);
            
            // PROBANDO DATOS DE NUEVO AÑO INGRESADO
            AnhoProyect aPro = AnhoProyect.findById(anhoPro.getId().intValue());

            // DATOS DE PRUEBA PROVISORIOS
            out.print(aPro.getId()+"<br>");
            out.print(aPro.getInicio()+"<br>");
            out.print(aPro.getTermino()+"<br>");
            out.print(aPro.getNum()+"<br>");
            out.print("Id Pro "+aPro.getProyectoId().getId()+"<br>");
            
            request.setAttribute("mensaje", "Año Añadido Correctamente");
            
            return anhoPro;
        }
        request.setAttribute("mensaje", "El Año No Se Ha Podido Guardar");
        return null;
    }
   
    /*
    *  Guardar presupuestos para cada Cuenta y cada Fuente de Financiamiento
    *  para nuevo año creado
    */
    private void savePresupuestos(HttpServletRequest request, HttpServletResponse response, AnhoProyect anho)
            throws ServletException, IOException, NullPointerException{
        
        PrintWriter out = response.getWriter();

        out.print("<br>Impreso desde saveYear() <br><br>");

        Presupuesto presu = null;
        List<Cuenta> ctas = Cuenta.findAll();
        List<FuenteF> ff = FuenteF.findAll();
        BigInteger totalF = new BigInteger("0");
        
        String[] sercotec   = request.getParameterValues("sercotec");
        String[] inacap     = request.getParameterValues("inacap");
        String[] pecuniario = request.getParameterValues("pecuniarios");
        
        for (int i = 0; i <= ctas.size()-1 ; i++ ) {
            for (int j = 0; j <= ff.size()-1 ; j++) {
                switch(i){
                    case 0: totalF = new BigInteger(sercotec[j]); break;
                    case 1: totalF = new BigInteger(inacap[j]); break;
                    case 2: totalF = new BigInteger(pecuniario[j]); break;
                }
                
                Presupuesto presupuesto = new Presupuesto(
                    totalF, 
                    totalF, 
                    BigInteger.valueOf(0), 
                    anho, 
                    ctas.get(j), 
                    ff.get(i)
                );
                
                Presupuesto.insPresupuesto(presupuesto);
                
            }
        }
    }
    
     
    /*
    *  Calcular total de presupuesto de nuevo año
    */
    private BigInteger calculateTotal(HttpServletRequest request){
        
        int total = 0;
        String[] sercotec   = request.getParameterValues("sercotec");
        String[] inacap     = request.getParameterValues("inacap");
        String[] pecuniario = request.getParameterValues("pecuniarios");
        
        List<Cuenta> ctas = Cuenta.findAll();
        List<FuenteF> ff = FuenteF.findAll();
                
        for (int i = 0; i <= ctas.size()-1 ; i++ ) {
            for (int j = 0; j <= ff.size()-1 ; j++) {
                switch(i){
                    case 0: total += Integer.parseInt(sercotec[j]); break;
                    case 1: total += Integer.parseInt(inacap[j]); break;
                    case 2: total += Integer.parseInt(pecuniario[j]); break;
                }
            }
        }
        return BigInteger.valueOf(total);
    }
    
    // Convertir formato de fechas
    private String[] getFechas(String[] fechas){
        
        String[] f = fechas[0].split("/");
        fechas[0] = f[1]+'/'+f[0]+'/'+f[2];

        f = fechas[1].split("/");
        fechas[1] = f[1]+'/'+f[0]+'/'+f[2];
                        
        return fechas;
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
