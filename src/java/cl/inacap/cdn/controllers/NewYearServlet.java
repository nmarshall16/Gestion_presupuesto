/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dell
 */
public class NewYearServlet extends HttpServlet {

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
                if(request.getParameter("op").equals("2")){
                    op = 2;
                }
            }
            
            switch(op){
                case 1: 
                    if (Cuenta.findAll() != null) {
                        request.setAttribute("ctas", Cuenta.findAll());
                        request.getRequestDispatcher("nuevoAno.jsp").forward(request, response);
                    }
                    break;
                case 2: 
                    // GUARDAR NUEVO AÑO DE PROYECTO
                    int numAnho, totalDis, total;
                    Date fechaIni, fechaTer;
                    
                    Proyecto proyecto;
                    
                    try {
                        /*
                        * RECOPILACIÓN DE DATOS DE AÑO DE PROYECTO
                        */
                        
                        // ASIGNANDO PROYECTO PROVISORIO!!!!!!!!!!!!
                        // Obteniendo Proyecto
                        proyecto = Proyecto.findById(BigDecimal.valueOf(1));

                        // Obteniendo numero de años de proyecto
                        numAnho = AnhoProyect.countYearsOfProyect(proyecto);
                        
                        //Obteniendo y transformando fechas
                        String[] fechas = getFechas(request.getParameter("fechas").split(" - "));
                        fechaIni = new Date(fechas[0]);
                        fechaTer = new Date(fechas[1]);

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
                        
                        // GUARDAR PRESUPUESTOS
                        savePresupuestos(request, response);
                        
                        
                        // PROBANDO DATOS DE NUEVO AÑO INGRESADO
                        AnhoProyect aPro = AnhoProyect.findById(anhoPro.getId().intValue());
                        
                        out.print(aPro.getId()+"<br>");
                        out.print(aPro.getInicio()+"<br>");
                        out.print(aPro.getTermino()+"<br>");
                        out.print(aPro.getNum()+"<br>");
                        out.print("Id Pro "+aPro.getProyectoId().getId()+"<br>");
                        
                        
                    } catch (NullPointerException | IOException | ServletException e) {
                        out.print(e.getClass()+"<br>");
                        
                        StackTraceElement[] stack = e.getStackTrace();
                        String trace = "";
                        for(StackTraceElement linea : stack){
                           trace += linea.toString()+"<br>";
                        }
                        out.print(trace);

                        out.print(e.initCause(e.getCause()));
                        out.print("<br>");
                        
                    }
                    
                    /*
                    // Guardar presupuestos!
                    try {
                        
                        // Guardar Presupuestos de nuevo año
                        savePresupuestos(request, response);
                        
                    } catch (IOException | ServletException e) {
                        out.print(e.getClass());
                        out.print(e.getCause());                    
                    }
                    
                    //out.println("Total: "+calculateTotal(request)+"<br>");
                    //out.println("Num de año: "+(numAnho));
                    
                    totalDis = Integer.parseInt(request.getParameter("totalDisp[]"));                    
                    total = Integer.parseInt(request.getParameter("total"));
                    proyecto = Proyecto.findById(Integer.parseInt(request.getParameter("idPro")));
                    
                    out.print((num+1)+" - "+totalDis+" - "+total+" - "+proyecto.getNombre());
                    
                    */

                    break;
                case 3: 
                    break;
                case 4: 
                    break;
                default: 
                    break;
            }
        }
    }
    
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
    
    private void savePresupuestos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NullPointerException{
        
        Presupuesto presu = null;
        List<Cuenta> ctas = Cuenta.findAll();
        List<FuenteF> ff = FuenteF.findAll();
        
        String[] sercotec   = request.getParameterValues("sercotec");
        String[] inacap     = request.getParameterValues("inacap");
        String[] pecuniario = request.getParameterValues("pecuniarios");
        
        PrintWriter out = response.getWriter();
        
        for (int i = 0; i <= ctas.size()-1 ; i++ ) {
            for (int j = 0; j <= ff.size()-1 ; j++) {
                switch(i){
                    case 0:
                        out.println("Sercotec    "+i+"-"+j+" "+sercotec[j]+"<br>");
                        break;
                    case 1:
                        out.println("Inacap      "+i+"-"+j+" "+inacap[j]+"<br>");
                        break;
                    case 2:
                        out.println("Pecuniarios "+i+"-"+j+" "+pecuniario[j]+"<br>");
                        break;
                }
            }
        }
    }
    
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
