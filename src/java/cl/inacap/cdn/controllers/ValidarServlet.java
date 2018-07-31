/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.AnhoProyect;
import cl.inacap.cdn.entities.Banco;
import cl.inacap.cdn.entities.CBanco;
import cl.inacap.cdn.entities.FuenteF;
import cl.inacap.cdn.entities.GastoMes;
import cl.inacap.cdn.entities.Homologar;
import cl.inacap.cdn.entities.Presupuesto;
import cl.inacap.cdn.entities.Proyecto;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class ValidarServlet extends HttpServlet {

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
                int accion = Integer.parseInt(request.getParameter("op"));
                switch(accion){
                    case 1:
                        // Se cargan las homologaciones que se encuentre pendientes por validar en la vista de verificar.jsp
                        BigDecimal bd = new BigDecimal(request.getParameter("anho"));
                        String mes = request.getParameter("mes");
                        ArrayList<Homologar> gastos = Homologar.getGastosPendientes(AnhoProyect.findById(bd.intValue()), mes);
                        request.setAttribute("gastos", gastos);
                        request.setAttribute("mes", mes);
                        request.setAttribute("anho", bd);
                        request.getRequestDispatcher("verificar.jsp").forward(request, response);
                    break;

                    case 2:
                        // Se recibe la cuenta que se quiere validar y se envia a formulario de validacion 
                        if(request.getParameter("id")!=null){
                            Homologar homologacion = Homologar.findById(new BigDecimal(request.getParameter("id")));
                            request.setAttribute("homologacion", homologacion);
                            request.getRequestDispatcher("validar.jsp").forward(request, response);
                        }
                    break;
                    
                    case 3:
                        // Respuesta ajax al solicitar las cuentas asociadas a una fuente de financiamiento
                        Gson gson = new Gson();
                        Map <String, String> map = new HashMap<String, String>();
                        if (request.getParameter("idProyect")!=null && request.getParameter("codFuente")!=null) {
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            try{
                                Proyecto proyect = Proyecto.findById(new BigDecimal(request.getParameter("idProyect")));
                                FuenteF fuente = FuenteF.findById(new BigDecimal(request.getParameter("codFuente")));
                                CBanco cbanco = CBanco.findCuenta(fuente, proyect);
                                map.put("cuenta" , cbanco.getNumCuenta().toString());
                                out.print(gson.toJson(map));
                                out.flush();
                                out.close();
                            }catch(java.lang.StackOverflowError ex){
                                map.put("error" , ex.toString());
                                out.print(ex);
                            }
                        }else{
                            map.put("error" , "Parametros equivocados");
                            out.print(gson.toJson(map));
                            out.flush();
                            out.close();
                        }
                    break;
                    
                    case 4:
                        // Se reciben el formulario de validacion y se valida el gasto en caso que no ocurra ningun error
                        if(request.getParameter("idHomol")!=null){
                            Homologar homologar = Homologar.findById(new BigDecimal(request.getParameter("idHomol")));
                            GastoMes gastoH = homologar.getGastoMesId();
                            String pago = request.getParameter("pago");
                            String banco = request.getParameter("banco").toUpperCase();
                            String cuenta = request.getParameter("cuenta");
                            String documento = request.getParameter("documento");
                            FuenteF fuenteH = FuenteF.findById(new BigDecimal(request.getParameter("fuente")));
                            String[] gasto = homologar.getGastoMesId().getAtributoPago().split(" ");
                            String monto = gasto[4];
                            String atributo = "("+pago+" "+banco+" "+cuenta+" "+documento+" "+monto; // Se genera el formato para el atributo de pago 
                            Presupuesto presupuestoV = Presupuesto.findByFuenteAndCuenta(gastoH.getGastoId().getFuenteFCodCentro(), homologar.getCuentaId(), gastoH.getAnhoProyectId());
                            // Se cambia el atributo de pago y se actualiza el presupuesto afectado por el gasto 
                            String errorV = gastoH.actualizarAtributoPago(fuenteH, atributo,presupuestoV);
                            // En caso que no exista error se cambia el estado de la homologacion a validado
                            if(errorV.equals("")){
                                homologar.actualizarEstado('V');
                            }else{
                                request.setAttribute("error", errorV);
                            }
                            ArrayList<Homologar> gastosH = Homologar.getGastosPendientes(gastoH.getAnhoProyectId(), gastoH.getMes().toString());
                            request.setAttribute("gastos", gastosH);
                            request.setAttribute("mes", gastoH.getMes());
                            request.setAttribute("anho", gastoH.getAnhoProyectId().getId());
                            request.getRequestDispatcher("verificar.jsp").forward(request, response);
                        }
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
