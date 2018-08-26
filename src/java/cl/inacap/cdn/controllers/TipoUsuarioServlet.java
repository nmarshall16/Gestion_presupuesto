/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.CBanco;
import cl.inacap.cdn.entities.FuenteF;
import cl.inacap.cdn.entities.Permiso;
import cl.inacap.cdn.entities.Proyecto;
import cl.inacap.cdn.entities.TipoUsuario;
import cl.inacap.cdn.entities.Usuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nicolas
 */
public class TipoUsuarioServlet extends HttpServlet {
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
            Usuario us = (Usuario)request.getSession(true).getAttribute("user");
            errores.clear();
            if (us != null) {
                if(request.getParameter("op")!=null){
                    List<Permiso> permisos;
                    List<TipoUsuario> tUsuarios;
                    int op = Integer.parseInt(request.getParameter("op"));
                    String[] permisosL;
                    switch(op){
                        case 1:
                            // Cargar vista de tipos de usuarios
                            if(Permiso.validarPermiso(us.getTipoUsuarioId(), "16")){
                                tUsuarios = TipoUsuario.findAll();
                                request.setAttribute("tUsuarios", tUsuarios);
                                request.getRequestDispatcher("tiposUsu.jsp").forward(request, response);
                            }else{
                                errores.add("Lo sentimos no tiene acceso a esta funcionalidad");
                                request.setAttribute("errores", errores);
                                List<Proyecto> proyectos = us.getProyectoList();
                                if(proyectos.size()>1){
                                    request.setAttribute("tipoP", "Proyectos Activos");
                                    request.setAttribute("proyectos", proyectos);
                                    request.getRequestDispatcher("inicioAdmin.jsp").forward(request, response);
                                }else if(proyectos.size()>0){
                                    response.sendRedirect("Proyect.do?idProyect="+proyectos.get(0).getId()+"&op=4");
                                }else{
                                    request.getRequestDispatcher("login.jsp").forward(request, response);
                                }
                            }
                        break;

                        case 2:
                            // Carga la vista para crear o editar un tipo de usuario
                            if(Permiso.validarPermiso(us.getTipoUsuarioId(), "16")){
                                permisos = Permiso.findAll();
                                request.setAttribute("permisos", permisos);
                                if(request.getParameter("idTipo")!=null){
                                    TipoUsuario tipo = TipoUsuario.findById(new BigDecimal(request.getParameter("idTipo")));
                                    request.setAttribute("tipo", tipo);
                                    request.setAttribute("op", 4);
                                }else{
                                    request.setAttribute("op", 3);
                                }
                                request.getRequestDispatcher("infoTipoUsu.jsp").forward(request, response);
                            }else{
                                accesoDenegado(request, response, us);
                            }
                        break;

                        case 3:
                            // Registrar nuevo tipo de usuario
                            if(Permiso.validarPermiso(us.getTipoUsuarioId(), "17")){
                                permisosL = request.getParameterValues("permisos");
                                permisos = new ArrayList();
                                if(permisosL!=null){
                                    for(String permiso:permisosL){
                                        Permiso p = Permiso.findById(new BigDecimal(permiso));
                                        permisos.add(p);
                                    }
                                    TipoUsuario newTipo = new TipoUsuario(
                                            request.getParameter("nombre")
                                    );
                                    newTipo.addTipoUsuario(permisos);
                                    tUsuarios = TipoUsuario.findAll();
                                    request.setAttribute("tUsuarios", tUsuarios);
                                    request.setAttribute("notificacion", "Se a registrado correctamente el tipo de usuario "+request.getParameter("nombre"));
                                    request.getRequestDispatcher("tiposUsu.jsp").forward(request, response);
                                }else{
                                    tUsuarios = TipoUsuario.findAll();
                                    permisos = Permiso.findAll();
                                    request.setAttribute("permisos", permisos);
                                    request.setAttribute("op", 3);
                                    request.setAttribute("alerta", "Error debe seleccionar al menos un permiso");
                                    request.getRequestDispatcher("infoTipoUsu.jsp").forward(request, response);
                                }
                            }else{
                                accesoDenegado(request, response, us);
                            }
                        break;

                         case 4:
                             // Modificar tipo de usuario
                            if(Permiso.validarPermiso(us.getTipoUsuarioId(), "18")){
                                permisosL = request.getParameterValues("permisos");
                                permisos = new ArrayList();
                                TipoUsuario tipoU = TipoUsuario.findById(new BigDecimal(request.getParameter("idTipo")));
                                if(permisosL!=null){
                                    for(String permiso:permisosL){
                                        Permiso p = Permiso.findById(new BigDecimal(permiso));
                                        permisos.add(p);
                                    }
                                    TipoUsuario tipoN = new TipoUsuario(request.getParameter("nombre"));
                                    tipoU.modificarTipoUsu(tipoN, permisos);
                                    request.setAttribute("notificacion", "Se a modificado correctamente el tipo de usuario "+request.getParameter("nombre"));
                                    tUsuarios = TipoUsuario.findAll();
                                    request.setAttribute("tUsuarios", tUsuarios);
                                    request.getRequestDispatcher("tiposUsu.jsp").forward(request, response);
                                }else{
                                    tUsuarios = TipoUsuario.findAll();
                                    permisos = Permiso.findAll();
                                    request.setAttribute("permisos", permisos);
                                    request.setAttribute("op", 4);
                                    request.setAttribute("tipo", tipoU);
                                    request.setAttribute("alerta", "Error debe seleccionar al menos un permiso");
                                    request.getRequestDispatcher("infoTipoUsu.jsp").forward(request, response);
                                }
                            }else{
                                accesoDenegado(request, response, us);
                            }
                        break;

                        case 5:
                            // Eliminar tipo de usuario
                            if(Permiso.validarPermiso(us.getTipoUsuarioId(), "16")){
                                if(request.getParameter("idTipo")!=null){
                                    TipoUsuario tipoDele = TipoUsuario.findById(new BigDecimal(request.getParameter("idTipo")));
                                    String nombre = tipoDele.getNombre();
                                    tipoDele.removeTipoUsu();
                                    request.setAttribute("notificacion", "Se a eliminado correctamente el tipo de usuario "+nombre);
                                }
                                tUsuarios = TipoUsuario.findAll();
                                request.setAttribute("tUsuarios", tUsuarios);
                                request.getRequestDispatcher("tiposUsu.jsp").forward(request, response);
                            }else{
                                accesoDenegado(request, response, us);
                            }
                       break;
                    }
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
            List<TipoUsuario> tUsuarios = TipoUsuario.findAll();
            requerir.setAttribute("tUsuarios", tUsuarios);
            requerir.getRequestDispatcher("tiposUsu.jsp").forward(requerir, responder);
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
