/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.MD5;
import cl.inacap.cdn.entities.Proyecto;
import cl.inacap.cdn.entities.TipoUsuario;
import cl.inacap.cdn.entities.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nicolas
 */
public class UsuarioServlet extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           if(request.getParameter("op")!=null){
               List<Usuario> usuarios;
               List<Proyecto> proyectos;
               List<TipoUsuario> tUsuarios;
               int op = Integer.parseInt(request.getParameter("op"));
               switch(op){
                   case 1:
                       usuarios = Usuario.findAll();
                       request.setAttribute("usuarios", usuarios);
                       request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                   break;
                   
                   case 2:
                       tUsuarios = TipoUsuario.findAll();
                       request.setAttribute("tUsuarios", tUsuarios);
                       if(request.getParameter("idUsuario")!=null){
                           Usuario usu = Usuario.findById(new BigDecimal(request.getParameter("idUsuario")));
                           request.setAttribute("usuario", usu);
                           request.setAttribute("op", 4);
                       }else{
                           request.setAttribute("op", 3);
                       }
                       request.getRequestDispatcher("infoUsuario.jsp").forward(request, response);
                   break;
                   
                   case 3:
                       String run = request.getParameter("rut");
                       run = run.replace(".", "");
                       String[] rutDv = run.split("-");
                       BigDecimal rut = new BigDecimal(rutDv[0]);
                       char dv = rutDv[1].charAt(0);
                       if(!Usuario.validarUsuario(rut)){
                            TipoUsuario tipo = TipoUsuario.findById((new BigDecimal(request.getParameter("tipo"))));
                            String clave = MD5.Encriptar(request.getParameter("clave"));
                            String nombre = request.getParameter("nombre");
                            String apellido = request.getParameter("apellido");      
                            Usuario usuario = new Usuario(rut,dv,nombre,apellido,clave,tipo);
                            usuario.addUsuario();
                            usuarios = Usuario.findAll();
                            request.setAttribute("usuarios", usuarios);
                            request.setAttribute("notificacion", "Se a registrado correctamente al usuario "+nombre+" "+apellido);
                            request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                       }else{
                            tUsuarios = TipoUsuario.findAll();
                            request.setAttribute("op", 3);
                            request.setAttribute("tUsuarios", tUsuarios);
                            request.setAttribute("alerta", "Error ya se encuentre registrado un usuario con el rut '"+rut+"-"+dv+"'");
                            request.getRequestDispatcher("infoUsuario.jsp").forward(request, response);
                       }
                   break;
                   
                   case 4:
                       String runM = request.getParameter("rut");
                       runM = runM.replace(".", "");
                       String[] rutDvM = runM.split("-");
                       BigDecimal rutM = new BigDecimal(rutDvM[0]);
                       char dvM = rutDvM[1].charAt(0);
                       Usuario usuarioM = Usuario.findById(new BigDecimal(request.getParameter("rutUsu")));
                       if(!Usuario.validarUsuario(rutM) || usuarioM.getRut().equals(rutM)){  
                            String clave = "";
                            String nombre = request.getParameter("nombre");
                            String apellido = request.getParameter("apellido"); 
                            TipoUsuario tipo = TipoUsuario.findById((new BigDecimal(request.getParameter("tipo"))));
                            if(!request.getParameter("clave").equals("")){
                                clave = MD5.Encriptar(request.getParameter("clave"));
                            }else{
                                clave = usuarioM.getClave();
                            }
                            Usuario usuario = new Usuario(rutM,dvM,nombre,apellido,clave,tipo);
                            usuarioM.modificarUsuario(usuario);
                            usuarios = Usuario.findAll();
                            request.setAttribute("usuarios", usuarios);
                            request.setAttribute("notificacion", "Se a modificado correctamente al usuario "+nombre+" "+apellido);
                            request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                       }else{
                            tUsuarios = TipoUsuario.findAll();
                            request.setAttribute("op", 3);
                            request.setAttribute("tUsuarios", tUsuarios);
                            request.setAttribute("alerta", "Error ya se encuentre registrado un usuario con el rut '"+rutM+"-"+dvM+"'");
                            request.getRequestDispatcher("infoUsuario.jsp").forward(request, response);
                       }
                   break;
                   
                   case 5:
                       if(request.getParameter("idUsuario")!=null){
                           Usuario usuarioD = Usuario.findById(new BigDecimal(request.getParameter("idUsuario")));
                           String nombre = usuarioD.getNombre();
                           String apellido = usuarioD.getApellido();
                           usuarioD.removeUsuario();          
                           request.setAttribute("notificacion", "Se a eliminado correctamente al usuario "+nombre+" "+apellido);
                       }
                       usuarios = Usuario.findAll();
                       request.setAttribute("usuarios", usuarios);
                       request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                   break;
                   
                   case 6:
                       usuarios = Usuario.findAll();
                       proyectos = Proyecto.findByEstado('1');
                       request.setAttribute("usuarios", usuarios);
                       request.setAttribute("proyectos", proyectos);
                       request.getRequestDispatcher("asignarProyect.jsp").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
