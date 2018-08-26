/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.Cuenta;
import cl.inacap.cdn.entities.Permiso;
import cl.inacap.cdn.entities.Proyecto;
import cl.inacap.cdn.entities.TipoUsuario;
import cl.inacap.cdn.entities.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class CuentaServlet extends HttpServlet {
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
            try{
                Usuario u = (Usuario)request.getSession(true).getAttribute("user");
                List<Cuenta> cuentas;
                errores.clear();
            if (u != null) {
                if(request.getParameter("op") != null){
                    int op = Integer.parseInt(request.getParameter("op"));
                    switch(op){
                        // Agregar cuenta
                        case 1:
                            if(Permiso.validarPermiso(u.getTipoUsuarioId(), "22")){
                                if(request.getParameter("name-cuenta") != null && !request.getParameter("name-cuenta").equals("")){
                                    Cuenta new_cuenta = new Cuenta(request.getParameter("name-cuenta"));
                                    new_cuenta.addCuenta();
                                    request.setAttribute("notificacion", "Se a agregado correctamente la cuenta "+new_cuenta.getNombre());
                                }else{
                                    request.setAttribute("alerta", "No sé ha podido agregar la cuenta correctamente inténtelo nuevamente");
                                }
                                cuentas = Cuenta.findAll();
                                request.setAttribute("cuentas", cuentas);
                                request.getRequestDispatcher("cuentas.jsp").forward(request, response);
                            }else{
                                accesoDenegado(request, response, u);
                            }
                        break;
                        // Eliminar cuenta
                        case 2:
                            if(Permiso.validarPermiso(u.getTipoUsuarioId(), "23")){
                                if(request.getParameter("id_cuenta") != null && !request.getParameter("id_cuenta").equals("")){
                                    Cuenta cuenta = Cuenta.findById(new BigInteger(request.getParameter("id_cuenta")));
                                    cuenta.removeCuenta();
                                    request.setAttribute("notificacion", "Se a eliminado correctamente la cuenta "+cuenta.getNombre());
                                }else{
                                    request.setAttribute("alerta", "No sé ha podido eliminar la cuenta correctamente inténtelo nuevamente");
                                }
                                cuentas = Cuenta.findAll();
                                request.setAttribute("cuentas", cuentas);
                                request.getRequestDispatcher("cuentas.jsp").forward(request, response);
                            }else{
                                accesoDenegado(request, response, u);
                            }
                        break;
                        // Modificar cuenta
                        case 3:
                            if(Permiso.validarPermiso(u.getTipoUsuarioId(), "24")){
                                if(request.getParameter("id_cuenta") != null && 
                                    !request.getParameter("id_cuenta").equals("") &&
                                    request.getParameter("name-cuenta") != null && 
                                    !request.getParameter("name-cuenta").equals("")){
                                    Cuenta cuenta = Cuenta.findById(new BigInteger(request.getParameter("id_cuenta")));
                                    cuenta.modificarCuenta(request.getParameter("name-cuenta"));
                                    request.setAttribute("notificacion", "Se a modificado correctamente la cuenta "+cuenta.getNombre());
                                }else{
                                    request.setAttribute("alerta", "No sé ha podido modificar la cuenta correctamente inténtelo nuevamente");
                                }
                                cuentas = Cuenta.findAll();
                                request.setAttribute("cuentas", cuentas);
                                request.getRequestDispatcher("cuentas.jsp").forward(request, response);
                            }else{
                                accesoDenegado(request, response, u);
                            }
                        break;
                    }
                }else{
                    if(Permiso.validarPermiso(u.getTipoUsuarioId(), "21")){
                        cuentas = Cuenta.findAll();
                        request.setAttribute("cuentas", cuentas);
                        request.getRequestDispatcher("cuentas.jsp").forward(request, response);
                    }else{
                        errores.add("Lo sentimos no tiene acceso a esta funcionalidad");
                        request.setAttribute("errores", errores);
                        List<Proyecto> proyectos = u.getProyectoList();
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
                }
            }else{
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            }catch(IOException | NumberFormatException | ServletException ex){
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
    }
    
    public void accesoDenegado(HttpServletRequest requerir, HttpServletResponse responder, Usuario u)
        throws ServletException, IOException{
            errores.add("Lo sentimos no tiene acceso a esta funcionalidad");
            requerir.setAttribute("errores", errores);
            List<Cuenta> cuentas = Cuenta.findAll();
            requerir.setAttribute("cuentas", cuentas);
            requerir.getRequestDispatcher("cuentas.jsp").forward(requerir, responder);
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
