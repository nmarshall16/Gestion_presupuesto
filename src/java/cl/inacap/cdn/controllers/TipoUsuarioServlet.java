/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.Permiso;
import cl.inacap.cdn.entities.TipoUsuario;
import cl.inacap.cdn.entities.Usuario;
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
public class TipoUsuarioServlet extends HttpServlet {

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
            if(request.getParameter("op")!=null){
                List<Permiso> permisos;
                List<TipoUsuario> tUsuarios;
                int op = Integer.parseInt(request.getParameter("op"));
                String[] permisosL;
                switch(op){
                    case 1:
                        tUsuarios = TipoUsuario.findAll();
                        request.setAttribute("tUsuarios", tUsuarios);
                        request.getRequestDispatcher("tiposUsu.jsp").forward(request, response);
                    break;
                    
                    case 2:
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
                    break;
                    
                    case 3:
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
                    break;
                    
                     case 4:
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
                    break;
                    
                    case 5:
                       if(request.getParameter("idTipo")!=null){
                           TipoUsuario tipoDele = TipoUsuario.findById(new BigDecimal(request.getParameter("idTipo")));
                           String nombre = tipoDele.getNombre();
                           tipoDele.removeTipoUsu();
                           request.setAttribute("notificacion", "Se a eliminado correctamente el tipo de usuario "+nombre);
                       }
                       tUsuarios = TipoUsuario.findAll();
                       request.setAttribute("tUsuarios", tUsuarios);
                       request.getRequestDispatcher("tiposUsu.jsp").forward(request, response);
                   break;
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
