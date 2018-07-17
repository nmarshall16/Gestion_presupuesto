/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.Banco;
import cl.inacap.cdn.entities.Proyecto;
import cl.inacap.cdn.entities.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * @author Nicolas
 */
public class ProyectServlet extends HttpServlet {
	
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
            Usuario u = (Usuario)request.getSession(false).getAttribute("user");
            if (u != null) {
                String action = request.getParameter("accion");
                /* Se recibe el parametro accion enviado desde el jsp si el paremetro
                es null se cargara el inicio del administrador con todos los proyectos activos */
                if(action != null){
                    /*
                    Acciones del switch 
                        - mostrarProyecto: Busca un proyecto determinado y carga la
                          vista con todos sus datos y funcionalidades.
                        - 
                    */
                    switch(action){
                        case "mostrarProyecto":
                            BigDecimal idProyecto = new BigDecimal(Integer.parseInt(request.getParameter("idProyect")));
                            request.setAttribute("proyecto", Proyecto.findById(idProyecto));
                            request.getRequestDispatcher("proyecto.jsp").forward(request, response);
                        break;
                        case "crearProyecto":
                            request.setAttribute("bancos", Banco.findAll());                            
							request.setAttribute("errores", errores);
                            request.getRequestDispatcher("nuevoProyecto.jsp").forward(request, response);
                        break;
						case "guardarProyecto":
							errores.clear();
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							try{
								if (valNewProyect(request).isEmpty()) {
									Proyecto pro = Proyecto.insProyecto(
										new Proyecto(
											request.getParameter("nameProyect").trim(),
											request.getParameter("codProyect").trim(),
											df.parse(request.getParameter("fechaInicio")),
											df.parse(request.getParameter("fechaInicio")),
											'1',
											new Banco(new BigDecimal(request.getParameter("banco")))
										)
									);
									response.sendRedirect("Proyect.do?idProyect="+pro.getId()+"&accion=mostrarProyecto");
								}else{
									request.setAttribute("errores", errores);
									request.setAttribute("bancos", Banco.findAll());
									request.getRequestDispatcher("Proyect.do?accion=crearProyecto").forward(request, response);
								}
							}catch(IOException | ParseException | ServletException e){
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
						default:
						break;
							
                    }
                }else{
                    request.setAttribute("proyectos", Proyecto.findByEstado('1'));
                    request.getRequestDispatcher("inicioAdmin.jsp").forward(request, response);
                }
            }else{
                String mensaje = "Su Sesión Ha Expirado\nInicie Sesión Nuevamente";
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
	
	public List<String> valNewProyect(HttpServletRequest request){
		if(request.getParameter("nameProyect").trim().equals("")){
			errores.add("Debe Ingresar Nombre De Proyecto");
		}
		if(request.getParameter("codProyect").trim().equals("")){
			errores.add("Debe Ingresar Código De Proyecto");
		}
		if(request.getParameter("banco") == null){
			errores.add("Debe Ingresar Banco");
		}
		if(request.getParameter("numCuenta").equals("")){
			errores.add("Debe Ingresar Número De Cuenta");
		}
		return errores;
	}
	
}
