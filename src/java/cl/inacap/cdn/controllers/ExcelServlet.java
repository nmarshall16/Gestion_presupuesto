/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import static cl.inacap.cdn.controllers.PdfServlet.sortGastos;
import static cl.inacap.cdn.controllers.PdfServlet.tryParseInt;
import cl.inacap.cdn.entities.AnhoProyect;
import cl.inacap.cdn.entities.Cuenta;
import cl.inacap.cdn.entities.FuenteF;
import cl.inacap.cdn.entities.Homologar;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dell
 */
public class ExcelServlet extends HttpServlet {
	
	public List<String> newCol = null;
	public List<String> colFij = null;
	public List<String> columns = null;
	public Map<Integer, String> sortColumns = null;
	
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
			
			System.out.println("LLEGA ACA!");
			
			Cuenta cuenta    = Cuenta.findById(new BigInteger(request.getParameter("cuenta")));
			AnhoProyect anho = AnhoProyect.findById(Integer.parseInt(request.getParameter("anho")));
			int			mes  = Integer.parseInt(request.getParameter("mes"));
			// Encabezados Fijos
			String[] enca = {"ID_COMP","COD_CUENTA","NOM_CUENTA","DEBE","HABER",
				"SALDO","IMPORTE","FECHA_CONTABLE","NUM_ORDEN_COMPRA","COD_PROYECTO",
				"PROYECTO","COD_CENTRO_RESP","NUM_FACTURA","RUT_PROVEEDOR","NOMBRE_PROVEEDOR",
				"ATRIBUTOS_DEL_PAGO","ASIENTO","HOMOLOGACION"};
			try{
				String[] cols = request.getParameterValues("newCols");
				columns		  = Arrays.asList(cols);				// Lista De Columnas!
				List<String> encbzd = Arrays.asList(enca); // Lista De Encabezados!
				newCol = new ArrayList();				  // Lista de nuevas Columnas!
				colFij = new ArrayList();				 // Lista de Columnas Fijas!
				// Comprobar Columnas Fijas Y Nuevas
				sortColumns = new HashMap<>();
				System.out.println(columns.toString());
				columns.forEach((c) -> {
					String[] co = c.split("-");
					if(!encbzd.contains(co[1])){
						newCol.add(co[1]);
					}else{
						colFij.add(co[1]);
					}
					sortColumns.put(Integer.parseInt(co[0]), co[1]);
				});
				System.out.println("Nuevas Columnas : "+newCol.size()+" - "+newCol.toString());
				System.out.println("Columnas Existentes : "+colFij.toString());
				request.setAttribute("newCols", newCol);
				request.setAttribute("columnas", columns);
				FuenteF fuente = null;
				if(cuenta != null){
					request.setAttribute("cuenta", cuenta);
					if(request.getParameter("centro") != null){
						fuente = FuenteF.findById(new BigDecimal(request.getParameter("centro")));
						request.setAttribute("centro", fuente);
					}
				}
				if(anho != null){request.setAttribute("anho", anho);}
				if(mes  > 0 && mes < 13){request.setAttribute("mes", mes);}

				request.setAttribute("gastos", Homologar.findHomologacionesV(cuenta, fuente, anho, mes));
				request.getRequestDispatcher("completePDF.jsp").forward(request, response);

			}catch(IOException | ServletException | NullPointerException ex){
				request.setAttribute("mensaje","Debe Ingresar Al Menos Una Columna");
			}
			/***************** Variables *****************/
			String titulo;												// Variable De Título De Parrafo De Documento
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			List<String[]> gastosMes = new ArrayList<>();				// Lista final con gastosMes
			Map<Integer, Map<String, String>> values = new HashMap<>();	// Arreglo multidimensional con Id Cuenta ,[ NombreCol , Valor Col]
			FuenteF centro		= null;
			titulo = cuenta.getNombre();
			if(!request.getParameter("centro").equals("")){
				centro  = FuenteF.findById(new BigDecimal(request.getParameter("centro")));
				titulo += " - "+centro.getNombre();
			}
			/***************** Variables *****************/
			
			// Ordenar Nuevas Columnas, Con sus respectivos valores para cada Gasto
			// Retorna HashMap<Integer, HashMap<String, String>>
			try{
				// Obtener gastos de documento
				System.out.println("GASTOS OBTENIDOS : ");
				List<Homologar> gastos = Homologar.findHomologacionesV(cuenta, centro, anho, mes);
				gastos.forEach((g) -> {
					System.out.println(g.getGastoMesId().getId());
				});
				values = sortColumns(request, response);					// ORDENAR COLUMNAS!
				System.out.println("NAMES INPUTS DE GASTOS OBTENIDOS : "+values.toString());
				gastosMes = sortGastos(gastos, values, columns);			// ORDENAR GASTOS MES CON RESPECTIVOS VALORES
			
				writeExcel(request, response, gastosMes);
			
			}catch(NullPointerException ex){
				System.out.println("Problema!: "+ex.getMessage());
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
	
	
	/*
	*	Ordenar Columnas, Dejar Id De gasto, junto a sus respectivas nuevas columnas y sus valores
	*	**Se omiten los Valores en Blanco
	*/
	public Map<Integer, Map<String, String>> sortColumns(HttpServletRequest request, HttpServletResponse response){
		Map<Integer, Map<String, String>> values = new HashMap<>();	// Arreglo multidimensional con Id Cuenta ,[ NombreCol , Valor Col]
		List<String> inNam  = Collections.list(request.getParameterNames());  // Nombres De Inputs De Nuevas Columnas Agregadas

		// Comprobar nuevas columnas de documento
		System.out.println("Nuevas Columnas desde WritePDF : "+newCol.toString());
		System.out.println("HashMap De Columnas desde WritePDF : "+sortColumns.toString());
		System.out.println("Input Names : "+inNam.toString());
		inNam.forEach((String col) -> {
			String[] colm = col.split("-");
			System.out.println("Name Obtenido "+col);
			// Si El input contiene id de gasto en la primera posición
			if(tryParseInt(colm[0])){
				String val = request.getParameter(col);
				if(!val.equals("")){
					System.out.println("Value OF column : "+request.getParameter(col));
					// Si el Map ya tiene el id agregado, agregar Columna y valor
					if(values.containsKey(Integer.parseInt(colm[0]))){
						values.get(Integer.parseInt(colm[0])).put(colm[1], val);
					}else{
						Map<String, String> valu = new HashMap<>();
						if(!val.equals("")){
							valu.put(colm[1], val);
						}else{
							valu.put(colm[1], "-");
						}
						values.put(Integer.parseInt(colm[0]), valu);
					}
				}
			}
		});
		return values;
	}
	
	/*
	*	Ordenar gastosMes, con respectivas columnas (incluyendo nuevas y eliminadas) y sus valores 
	*		Se encuentran ordenadas segun definido por usuario
	*/
	public static List<String[]> sortGastos(List<Homologar> gastos, Map<Integer, Map<String, String>> values, List<String> columnas){
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		List<String[]> gastosM = new ArrayList<>();
		String[] gasto;

		for(Homologar gst : gastos){
			gasto = new String[columnas.size()+1];
			for(String cols : columnas){
				String[] col = cols.split("-");
				gasto[0] = gst.getGastoMesId().getId().toString();
				switch(col[1]){
					case "ID_COMP": gasto[Integer.parseInt(col[0])]				= gst.getGastoMesId().getIdCompra().toString(); break;//gst.getGastoMesId().getIdCompra().toString() ;break;
					case "COD_CUENTA": gasto[Integer.parseInt(col[0])]			= gst.getGastoMesId().getGastoId().getCodCuenta().toString() ;break;
					case "NOM_CUENTA": gasto[Integer.parseInt(col[0])]			= gst.getCuentaId().getNombre() ;break;
					case "DEBE":  gasto[Integer.parseInt(col[0])]				= gst.getGastoMesId().getImporte().toString() ;break;
					case "HABER":  gasto[Integer.parseInt(col[0])]				= "-" ;break;
					case "SALDO":  ;break;
					case "IMPORTE":  gasto[Integer.parseInt(col[0])]            = gst.getGastoMesId().getImporte().toString() ;break;
					case "FECHA_CONTABLE": gasto[Integer.parseInt(col[0])]		= formatter.format(gst.getGastoMesId().getFecha()) ;break;
					case "NUM_ORDEN_COMPRA": gasto[Integer.parseInt(col[0])]	= gst.getGastoMesId().getOrdenCompra() ;break;
					case "COD_PROYECTO": gasto[Integer.parseInt(col[0])]		= gst.getGastoMesId().getAnhoProyectId().getProyectoId().getCodigo() ;break;
					case "PROYECTO": gasto[Integer.parseInt(col[0])]			= gst.getGastoMesId().getAnhoProyectId().getProyectoId().getNombre();break;
					case "COD_CENTRO_RESP":gasto[Integer.parseInt(col[0])]		= gst.getGastoMesId().getGastoId().getFuenteFCodCentro().getCodCentro().toString() ;break;
					case "NUM_FACTURA": gasto[Integer.parseInt(col[0])]			= gst.getGastoMesId().getNumFac().toString() ;break;
					case "RUT_PROVEEDOR": gasto[Integer.parseInt(col[0])]		= gst.getGastoMesId().getRutProvedor() ;break;
					case "NOMBRE_PROVEEDOR": gasto[Integer.parseInt(col[0])]	= gst.getGastoMesId().getNombreProvedor() ;break;
					case "ATRIBUTOS_DEL_PAGO": gasto[Integer.parseInt(col[0])]  = gst.getGastoMesId().getAtributoPago() ;break;
					case "ASIENTO": gasto[Integer.parseInt(col[0])]				= gst.getGastoMesId().getAsiento() ;break;
					case "HOMOLOGACION": gasto[Integer.parseInt(col[0])]		= gst.getCuentaId().getNombre() ;break;
					default:
						for(Map.Entry<Integer, Map<String, String>> val : values.entrySet()){
							if(gst.getGastoMesId().getId().intValueExact() == (val.getKey())){
								for(Map.Entry<String, String> valu : val.getValue().entrySet()){
									if(valu.getKey().equals(col[1])){
										if(col[1].equals("TIPO_DOCUMENTO")){
											String tipo = "";
											switch(Integer.parseInt(valu.getValue())){
												case 1: tipo = "Viático" ;break;
												case 2: tipo = "Remuneración" ;break;
												case 3: tipo = "Factura" ;break;
												case 4: tipo = "Boleta De Honorarios" ;break;
												case 5: tipo = "Comprobante" ;break;
											}
											gasto[Integer.parseInt(col[0])] = tipo;
										}else{
											System.out.println("Valores "+valu.toString());
											gasto[Integer.parseInt(col[0])] = valu.getValue();
										}
									}
								}
							}
						}
					break;
				}
			}
			System.out.println("GASTO COMPLETO: "+Arrays.toString(gasto));
			gastosM.add(gasto);
		}
		return gastosM;
	}
	
	public void writeExcel(HttpServletRequest request, HttpServletResponse response, List<String[]> gatosMes){
		
		System.out.println("Gastos Mes : "+gatosMes.toString());
		
	}
	
}
