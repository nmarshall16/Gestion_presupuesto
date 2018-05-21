/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.Proyecto;
import cl.inacap.cdn.entities.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
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
            String action = request.getParameter("accion");
            if(action != null){
                switch(action){
                    case "mostrarProyecto":
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
                        EntityManager em = emf.createEntityManager();
                        BigDecimal idProyecto = new BigDecimal(Integer.parseInt(request.getParameter("idProyect")));
                        Proyecto proyecto = em.find(Proyecto.class, idProyecto);
                        request.setAttribute("proyecto", proyecto);
                        em.close();
                        emf.close();
                        request.getRequestDispatcher("proyecto.jsp").forward(request, response);
                    break;
                }
            }else{
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
                EntityManager em = emf.createEntityManager();
                TypedQuery<Proyecto> proyectosActivos = em.createQuery("select p from Proyecto p where p.estado=:estado", Proyecto.class);
                proyectosActivos.setParameter("estado", '1');
                ArrayList<Proyecto> proyectos = new ArrayList<>();
                proyectosActivos.getResultList().forEach((proyecto) -> {
                    proyectos.add(proyecto);
                });
                request.setAttribute("proyectos", proyectos);
                em.close();
                emf.close();
                request.getRequestDispatcher("inicioAdmin.jsp").forward(request, response);
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
