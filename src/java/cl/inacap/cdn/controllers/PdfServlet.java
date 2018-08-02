/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.controllers;

import cl.inacap.cdn.entities.AnhoProyect;
import cl.inacap.cdn.entities.Cuenta;
import cl.inacap.cdn.entities.FuenteF;
import cl.inacap.cdn.entities.Homologar;
import cl.inacap.cdn.entities.Usuario;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nicolas
 */
@WebServlet(name = "PdfServlet", urlPatterns = {"/PDF"})
public class PdfServlet extends HttpServlet {
	
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
		response.setCharacterEncoding("UTF-8");
		int cont;
		Usuario u = (Usuario)request.getSession(true).getAttribute("user");
		if (u != null) {
			try{
				if(request.getParameter("op") != null ){
					switch(request.getParameter("op")){
						case "1":
							getFuentes(request, response);
						break;
						case "2":
							// Escribir PDF!
							writePDF(request, response);
						break;
					}
				}else{
				// Peticion mediante formulario en "generarPDF.jsp"
					if(request.getMethod().equals("POST")){

					// Comprobar nuevas columnas agregadas a documento
						try{
						// Comparar encabezados Fijos Con Nuevos Encabezados Agregados En Formulario
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
								toGenerarPDF(request, response);
							}
						}catch(IOException | ServletException | NullPointerException ex){
							request.setAttribute("mensaje", "No Existe Cuenta Indicada, Por Favor Seleccione Una De La Lista "+ex.getMessage());
							toGenerarPDF(request, response);
						}
					}else{
					// Cargar vista de generación de PDF
						request.setAttribute("mensaje", "Algo");
						toGenerarPDF(request, response);
					}
				}
			}catch(NullPointerException ex){
				request.setAttribute("mensaje", "No Existe Opcion Indicada");
				toGenerarPDF(request, response);
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
	
	
	public void writePDF(HttpServletRequest request, HttpServletResponse response) 
			throws IOException{
		try{
			/***************** Variables *****************/
			String titulo;												// Variable De Título De Parrafo De Documento
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Cuenta cuenta		= Cuenta.findById(new BigInteger(request.getParameter("cuenta")));
			AnhoProyect anho	= AnhoProyect.findById(Integer.parseInt(request.getParameter("anho")));
			List<String[]> gastosMes = new ArrayList<>();				// Lista final con gastosMes
			Map<Integer, Map<String, String>> values = new HashMap<>();	// Arreglo multidimensional con Id Cuenta ,[ NombreCol , Valor Col]
			int mes				= Integer.parseInt(request.getParameter("mes"));
			FuenteF centro		= null;
			titulo = cuenta.getNombre();
			if(!request.getParameter("centro").equals("")){
				centro  = FuenteF.findById(new BigDecimal(request.getParameter("centro")));
				titulo += " - "+centro.getNombre();
			}
			/***************** Variables *****************/
			
			// Configuración de Documento
			response.setContentType("application/pdf");
			OutputStream out	= response.getOutputStream();
			// Fin De Configuración de Documento
			
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
			}catch(NullPointerException ex){
				System.out.println("Problema!: "+ex.getMessage());
			}
			try{
				// Crear Documento
				Document documento = new Document(PageSize.A2.rotate());
				PdfWriter.getInstance(documento, out);
				documento.open();
				
				// Configuración de Parrafo Informativo y Logo
				Paragraph par1 = new Paragraph();
				Font fuente = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL,BaseColor.BLACK);
				String cabecera = "Libro Mayor: "+titulo+"\n"
                               + "Fecha: "+(formatter.format(new Date()))+"\n"
                               + "Sociedad: Universidad Tecnologica de Chile INACAP"+"\n"
                               + "Rut: 72.012.000-3"+"\n"
                               + "Periodo:"+"Noviembre"+"\n";
				
                par1.add(new Phrase(cabecera, fuente));
                par1.setAlignment(Element.ALIGN_LEFT);
                par1.add(new Phrase(Chunk.NEWLINE));
                ServletContext context = request.getServletContext();
                System.out.print(context.getRealPath("/")+"files/logo.png");
                Image logo = Image.getInstance(context.getRealPath("/")+"files/logo.png");
                logo.setAlignment(Image.RIGHT | Image.TEXTWRAP);
                logo.scaleToFit(100, 100);
                documento.add(logo);
                documento.add(par1);
				// Fin Configuración de Parrafo y Logo
				
				// Configuración de Tabla
                PdfPTable tabla = new PdfPTable(100);
                tabla.setWidthPercentage(100);
                tabla.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				// Fin Configuración Tabla
				
				//Se establece la cabecera de la tabla
                Font titulos = new Font(Font.FontFamily.COURIER,8,Font.BOLD,BaseColor.BLACK);
                String[] enca = {"ID_COMP","COD_CUENTA","NOM_CUENTA","DEBE","HABER",
                "SALDO","IMPORTE","FECHA_CONTABLE","NUM_ORDEN_COMPRA","COD_PROYECTO",
                "PROYECTO","COD_CENTRO_RESP","NUM_FACTURA","RUT_PROVEEDOR","NOMBRE_PROVEEDOR",
                "ATRIBUTOS_DEL_PAGO","ASIENTO","HOMOLOGACION"};
                PdfPCell cell;
				
                int[] largo = new int[18];
                int total = 0;
                for(int i=0; i<enca.length; i++){
                    cell = new PdfPCell(new Paragraph(enca[i],titulos));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    if(enca[i].length() <= 6){
						total += 3;
						largo[i] = 3;
						cell.setColspan(3);
					}else if (enca[i].length() <= 8) {
						total += 4;
						largo[i] = 4;
						cell.setColspan(4);
					}else if (enca[i].length() <= 10) {
						total += 5;
						largo[i] = 5;
						cell.setColspan(5);
					}else if (enca[i].length() <= 12) {
						total += 6;
						largo[i] = 6;
						cell.setColspan(6);
					}else if (enca[i].length() <= 14) {
						total += 7;
						largo[i] = 7;
						cell.setColspan(7);
					}else if (enca[i].length() <= 16) {
						total += 8;
						largo[i] = 8;
						cell.setColspan(8);
					}else if (enca[i].length() <= 18) {
						total += 9;
						largo[i] = 9;
						cell.setColspan(9);
					}
					tabla.addCell(cell);
				}
				System.out.print(total);
				
				
				// Se agregan los gastos a la tabla
				String[] filasG1 = {"1081642","5011080","ASESORIAS ADMINISTRATIVAS","555.556","-",
				"11.479.965","555.556","15-11-2017","UTC815350","PE16VIPSERCDN02",
				"SAN FERNANDO","400060800","613","15120366-3","ANDREA NATALI CASTRO SILVA",
				"(CHK CHILE 008001968506 07180727 1000001)","CPT7108924","Capacitacion"};
				Font filas = new Font(Font.FontFamily.COURIER,7,Font.NORMAL,BaseColor.BLACK);
				for(int i=0; i<filasG1.length; i++){
					cell = new PdfPCell(new Paragraph(filasG1[i],filas));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setColspan(largo[i]);
					tabla.addCell(cell);
				}
				String[] filasG2 = {"15431","5012130","ESTADÍA EN EL PAÍS","85.556","-",
				"9.479.965","85.556","27-11-2017","","PE16VIPSERCDN02",
				"SAN FERNANDO","400060800","","","",
				"","EX17135335","Capacitacion"};
				for(int i=0; i<filasG2.length; i++){
					cell = new PdfPCell(new Paragraph(filasG2[i],filas));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setColspan(largo[i]);
					tabla.addCell(cell);
				}
				tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
				documento.add(tabla);
				documento.close();
			}catch(DocumentException | IOException ex){
				ex.getMessage();
			}
        }catch(Exception ex){
			System.out.println(ex);
        }
	}
	
	public LinkedHashMap<Integer, String> cargarColumnas(){
		String[] enca = {"ID_COMP","COD_CUENTA","NOM_CUENTA","DEBE","HABER",
			"SALDO","IMPORTE","FECHA_CONTABLE","NUM_ORDEN_COMPRA","COD_PROYECTO",
			"PROYECTO","COD_CENTRO_RESP","NUM_FACTURA","RUT_PROVEEDOR","NOMBRE_PROVEEDOR",
			"ATRIBUTOS_DEL_PAGO","NUM_CARTOLA","FECHA_CARTOLA","ASIENTO","TIPO_DOCUMENTO","HOMOLOGACION"};
		LinkedHashMap<Integer, String> columnas = new LinkedHashMap<>();
		int cont = 1;
		for(String col : enca){
			columnas.put(cont, col);
			cont++;
		}
		return columnas;
	}
	
	public void toGenerarPDF(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		request.setAttribute("columnas", cargarColumnas());
		request.setAttribute("cuentas", Cuenta.findAll());
		request.getRequestDispatcher("generarPDF.jsp").forward(request, response);
	}
	
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
		String[] gasto = null;
		
		System.out.println("VALORES : "+values.toString());
		System.out.println("GASTOS  : "+gastos.toString());
		System.out.println("COLUMNAS :"+columnas.toString());
		for(Homologar gst : gastos){
			System.out.println("GASTO :"+gst);
			System.out.println("Id Compra "+gst.getGastoMesId().getIdCompra().toString());
			System.out.println("Id Compra "+gst.getGastoMesId().getImporte().toString());
			int index = 1;
			gasto = new String[columnas.size()+1];
			for(String cols : columnas){
				String[] col = cols.split("-");
				System.out.println("cols :"+ cols);
				System.out.println("COL[0]: "+col[0]);
				//gasto[0] = gst.getGastoMesId().getId().toString();
				System.out.println("CONT"+index);
				System.out.println("Nombre Col: "+col[1]);
				System.out.println("GASTO:"+ gst.getGastoMesId().getId());
				switch(col[1]){
					case "ID_COMP": gasto[0]									= gst.getGastoMesId().getId().toString(); break;//gst.getGastoMesId().getIdCompra().toString() ;break;
					case "COD_CUENTA": gasto[Integer.parseInt(col[0])]			= gst.getGastoMesId().getGastoId().getCodCuenta().toString() ;break;
					case "NOM_CUENTA": gasto[Integer.parseInt(col[0])]			= gst.getCuentaId().getNombre() ;break;
					case "DEBE":  ;break;
					case "HABER":  ;break;
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
						System.out.println("Entra a Default!");
						for(Map.Entry<Integer, Map<String, String>> val : values.entrySet()){
							if(gst.getGastoMesId().getId().intValueExact() == (val.getKey())){
								for(Map.Entry<String, String> valu : val.getValue().entrySet()){
									gasto[Integer.parseInt(col[0])] = valu.getValue();
								}
							}
						}
					break;
				}
				index++;
			}
			System.out.println("GASTO COMPLETO: "+Arrays.toString(gasto));
			gastosM.add(gasto);
		}
		return gastosM;
	}
	
	/*
	*	Método tryParse
	*/
	public static boolean tryParseInt(String value) {  
		try {  
			Integer.parseInt(value);  
			return true;  
		} catch (NumberFormatException e) {  
			return false;  
		}  
	}
	
	/*
	* Metodo getFuentes Utilizado por AJAX
	*/
	public void getFuentes(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Gson gson = new Gson();
		HashMap<BigDecimal, String> map = new HashMap<>();
		List<FuenteF> fuentes = FuenteF.findAll();
		if (!fuentes.isEmpty()) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			try{
				fuentes.forEach((fte) -> {
					map.put(fte.getCodCentro(), fte.getNombre());
				});
				response.getWriter().print(gson.toJson(map));
				response.getWriter().flush();
				response.getWriter().close();
			}catch(java.lang.StackOverflowError ex){
				response.getWriter().print(ex);
			}
		}
	}
}
