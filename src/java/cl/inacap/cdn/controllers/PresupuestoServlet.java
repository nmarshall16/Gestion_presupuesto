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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nicolas
 */
public class PresupuestoServlet extends HttpServlet {
List<String> errores = new ArrayList<>();
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
            // Consultar sesión de usuario
            Usuario u = (Usuario)request.getSession(true).getAttribute("user");
            errores.clear();
            if (u != null) {
                int action = Integer.parseInt(request.getParameter("accion"));
                /* Se recibe el parametro accion enviado desde el jsp si el paremetro
                es null se cargara el inicio del administrador con todos los proyectos activos */
                switch(action){
                    case 1:
                        if(Permiso.validarPermiso(u.getTipoUsuarioId(), "6")){
                            try{
                                // Cargar vista de presupuestos
                                request.setAttribute("ctas", Cuenta.findAll());			
                                request.setAttribute("fuentes", FuenteF.findAll());
                                request.setAttribute("presupuestos", Presupuesto.findAllByAnho(AnhoProyect.findById(Integer.parseInt(request.getParameter("anhoProyecto")))));
                            }catch(Exception ex){
                                request.setAttribute("mensaje", "Problemas Al Cargar Presupuestos Del Año Indicado");			
                            }
                            request.getRequestDispatcher("presupuesto.jsp").forward(request, response);
                        }else{
                            AnhoProyect anho = AnhoProyect.findById(Integer.parseInt(request.getParameter("anhoProyecto")));
                            request.setAttribute("proyecto", anho.getProyectoId());
                            accesoDenegado(request, response,u);
                        }
                    break;
                    case 2:
                            // Modificar Presupuesto
                    break;
                    default:
                    break;
                }
            }else{
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
    }
    
    public void accesoDenegado(HttpServletRequest requerir, HttpServletResponse responder, Usuario u)
        throws ServletException, IOException{
            errores.add("Lo sentimos no tiene acceso a esta funcionalidad");
            requerir.setAttribute("errores", errores);
            requerir.getRequestDispatcher("proyecto.jsp").forward(requerir, responder);
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
