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
import java.util.ArrayList;
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
	
	AnhoProyect anho = null;

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
			request.setAttribute("fuentes", FuenteF.findAll());
                    // Obteniendo id de proyecto
                        BigDecimal idPro = BigDecimal.valueOf(Integer.parseInt(request.getParameter("pro"))); 
                    // Setear datos obtenidos y construir vista
                        request.setAttribute("proyecto", Proyecto.findById(idPro));
                        request.setAttribute("anho", String.valueOf(AnhoProyect.countYearsOfProyect(Proyecto.findById(idPro))));
                        request.getRequestDispatcher("newYear.jsp").forward(request, response);
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
                    anho = AnhoProyect.findById(Integer.parseInt(request.getParameter("idAnho")));
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
                    try{
                            anho = AnhoProyect.findById(Integer.parseInt(request.getParameter("idAnho")));
                            savePresupuestos(request, response, anho);
                    }catch(IOException | NullPointerException | ServletException ex){

                    }
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
        List<Cuenta> ctas	= Cuenta.findAll();
	String[] fuentes   = request.getParameterValues("fuentes");
        BigInteger montoP = null;
        String[] presupues = null;
        FuenteF fuenteF = null;
        for(Cuenta cuenta:ctas){
            for(String fuente:fuentes){
                try{
                    fuenteF = FuenteF.findById(new BigDecimal(fuente));
                    if(fuenteF!=null){
                        montoP = new BigInteger(request.getParameter(cuenta.getId()+"-"+fuente));
                    }
                }catch(Exception ex){
                    montoP = new BigInteger("0");
                }
                Presupuesto presupuesto = new Presupuesto(
                    montoP, 
                    montoP, 
                    BigInteger.valueOf(0), 
                    anho, 
                    cuenta, 
                    fuenteF
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
        String[] fuentes   = request.getParameterValues("fuentes");
        List<Cuenta> ctas = Cuenta.findAll(); 
        int monto = 0;
        boolean validacion = true;
        for(Cuenta cuenta:ctas){
            for(String fuente:fuentes){
                try{
                    monto = Integer.parseInt(request.getParameter(cuenta.getId()+"-"+fuente));
                    validacion = true;
                }catch(NumberFormatException ex){
                    validacion = false;
                }
                if(validacion){
                    total += monto;
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
