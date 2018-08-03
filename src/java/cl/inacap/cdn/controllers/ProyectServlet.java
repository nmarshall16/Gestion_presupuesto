/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nicolas
 */
@WebServlet(urlPatterns = {"/index.html"})
public class ProyectServlet extends HttpServlet {
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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
		
		Proyecto pro = null;
		Gson gson	 = null;
		Map <String, String> map = null;
		
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            // Consultar sesión de usuario
            try{
                Usuario u = (Usuario)request.getSession(true).getAttribute("user");
            if (u != null) {
                if(request.getParameter("op") != null){
                    int op = Integer.parseInt(request.getParameter("op"));
                    switch(op){
                        // Mostrar Proyectos eliminados
                        case 1:
                            request.setAttribute("tipoP", "Proyectos Eliminados");
                            request.setAttribute("proyectos", Proyecto.findByEstado('0'));
                            request.getRequestDispatcher("inicioAdmin.jsp").forward(request, response);
                        break;
                        // Crear Proyecto 
                        case 2:
                            request.setAttribute("titulo", "Crear Proyecto");
                            request.setAttribute("proyecto", null);
                            request.setAttribute("mensaje", null);
                            request.setAttribute("bancos", Banco.findAll());                            
                            request.setAttribute("fuentes", FuenteF.findAll());                            
                            request.setAttribute("errores", errores);
                            request.getRequestDispatcher("nuevoProyecto.jsp").forward(request, response);
                        break;
                        
                        // Eliminar Proyecto
                        case 3:
                            pro = Proyecto.findById(new BigDecimal(request.getParameter("idProyect")));
                            if (pro != null) {
                                Proyecto.hideProyecto(pro);
                            }
                            response.sendRedirect(request.getContextPath()+"/Proyect.do");
                        break;
                        // Modificar Proyecto
                        case 4:
                            pro = Proyecto.findById(new BigDecimal(request.getParameter("idProyect")));
                            if (pro != null){
                                request.setAttribute("titulo", "Modificar Proyecto");
                                request.setAttribute("proyecto", Proyecto.findById(new BigDecimal(request.getParameter("idProyect"))));
                                request.setAttribute("bancos", Banco.findAll());
                                request.setAttribute("errores", errores);
                            }else{
                                request.setAttribute("titulo", null);
                            }
                            request.getRequestDispatcher("nuevoProyecto.jsp").forward(request, response);
                        break;
                        // Guardar Proyecto
                        case 5:
                            errores.clear();
                            try{
                                if (valNewProyect(request).isEmpty()) {
                                    // Guardar Nuevo Proyecto
                                    if(request.getParameter("idProyect") == null){	
                                        pro = Proyecto.insProyecto(
                                            new Proyecto(
                                                    request.getParameter("nameProyect").trim(),
                                                    request.getParameter("codProyect").trim(),
                                                    df.parse(request.getParameter("fechaInicio")),
                                                    df.parse(request.getParameter("fechaTermino")),
                                                    '1'
                                            )
                                        );
                                    }else{
                                    // Actualizar Proyecto Exitente
                                        pro = Proyecto.findById(new BigDecimal(request.getParameter("idProyect")));
                                        if (pro != null) {
                                            if(valNewProyect(request).isEmpty()){
                                                try{
                                                    pro.setNombre(request.getParameter("nameProyect").trim());
                                                    pro.setCodigo(request.getParameter("codProyect").trim());
                                                    pro.setFechaIni(df.parse(request.getParameter("fechaInicio")));
                                                    pro.setFechaFin(df.parse(request.getParameter("fechaTermino")));
                                                    Proyecto.updateProyecto(pro);
                                                }catch(ParseException ex){

                                                }
                                            }
                                        }
                                    }
                                    response.sendRedirect("Proyect.do?idProyect="+pro.getId()+"&op=4");
                                }else{
                                    request.setAttribute("errores", errores);
                                    request.setAttribute("bancos", Banco.findAll());
                                    if(request.getParameter("idProyect")!=null){
                                        request.setAttribute("errores", request.getParameter("idProyect"));
                                        request.getRequestDispatcher("Proyect.do?op=4").forward(request, response);
                                    }else{
                                        request.getRequestDispatcher("Proyect.do?op=2").forward(request, response);
                                    }
                                    //request.getRequestDispatcher("Proyect.do?op=5").forward(request, response);
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
                        // Obtener cuentas 
                        case 6:
                            gson = new Gson();
                            map = new HashMap<String, String>();
                            Banco banco = Banco.findById(new BigDecimal(request.getParameter("banco")));
                            if (banco != null) {
                                List<CBanco> cuentasB = CBanco.findAllByBanco(banco);
                                if (!cuentasB.isEmpty()) {
                                    response.setContentType("application/json");
                                    response.setCharacterEncoding("UTF-8");
                                    try{
                                        int cont = 1;
                                        for (CBanco cta : cuentasB) {
                                                map.put("cuenta"+cont , cta.getNumCuenta().toString());
                                                cont++;
                                        }
                                        out.print(gson.toJson(map));
                                        out.flush();
                                        out.close();
                                    }catch(java.lang.StackOverflowError ex){
                                        out.print(ex);
                                    }
                                }
                            }
                        break;
                        // Obtener Bancos
                        case 7:
                            gson = new Gson();
                            map = new HashMap<String, String>();
                            List<Banco> bancos = Banco.findAll();
                            if (!bancos.isEmpty()) {
                                response.setContentType("application/json");
                                response.setCharacterEncoding("UTF-8");
                                try{
                                    for (Banco bco : bancos) {
                                        map.put(bco.getId().toString() , bco.getNombre());
                                    }
                                    out.print(gson.toJson(map));
                                    out.flush();
                                    out.close();
                                }catch(java.lang.StackOverflowError ex){
                                    out.print(ex);
                                }
                            }
                        break;
                        //Mostrar Proyecto
                        case 8:
                            BigDecimal idProyecto = new BigDecimal(Integer.parseInt(request.getParameter("idProyect")));
                            request.setAttribute("proyecto", Proyecto.findById(idProyecto));
                            request.getRequestDispatcher("proyecto.jsp").forward(request, response);
                        break;
                        default:
                        break;
                    }
                }else{
                    List<Proyecto> proyectos = Proyecto.findByEstado('1');
                    if(proyectos.size()>1){
                        request.setAttribute("tipoP", "Proyectos Activos");
                        request.setAttribute("proyectos", Proyecto.findByEstado('1'));
                        request.getRequestDispatcher("inicioAdmin.jsp").forward(request, response);
                    }
                }
            }else{
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            }catch(Exception ex){
                request.getRequestDispatcher("login.jsp").forward(request, response);
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
		if(request.getParameter("cuenta") == null){
			errores.add("Debe Ingresar Número De Cuenta");
		}
		return errores;
	}
	
}
