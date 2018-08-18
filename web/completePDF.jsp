<%-- 
    Document   : completePDF
    Created on : 31-07-2018, 1:11:56
    Author     : dell
--%>

<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>
		<meta charset="utf-8">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>Proyectos Activos</title>
		<!-- Bootstrap core CSS-->
		<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<!-- Custom fonts for this template-->
		<link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
		<!-- Custom styles for this template-->
		<link href="css/sb-admin.css" rel="stylesheet">
		<!-- Page level plugin CSS-->
		<link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

	</head>

    <body class="fixed-nav sticky-footer bg-dark" id="page-top">
	<!-- Navigation-->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
		<a class="navbar-brand" href="index.html">CDN INACAP</a>
		<button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
		  <span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarResponsive">
			<ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
			  <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Inicio">
				<a class="nav-link" href="#">
				  <i class="fa fa-fw fa-home"></i>
				  <span class="nav-link-text">Inicio</span>
				</a>
			  </li>
			  <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Proyectos Eliminados">
				<a class="nav-link" href="#">
				  <i class="fa fa-fw fa-archive"></i>
				  <span class="nav-link-text">Proyectos Eliminados</span>
				</a>
			  </li>
			  <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tipos de usuarios">
				<a class="nav-link" href="#">
				  <i class="fa fa-fw fa-sitemap"></i>
				  <span class="nav-link-text">Tipos de usuarios</span>
				</a>
			  </li>
			  <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Usuarios">
				<a class="nav-link" href="#">
				  <i class="fa fa-fw fa-users"></i>
				  <span class="nav-link-text">Usuarios</span>
				</a>
			  </li>
			  <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Cerrar Sesión">
				<a class="nav-link" href="#">
				  <i class="fa fa-fw fa-sign-out"></i>
				  <span class="nav-link-text">Cerrar Sesión</span>
				</a>
			  </li>
			</ul>
			<ul class="navbar-nav sidenav-toggler">
			  <li class="nav-item">
				<a class="nav-link text-center" id="sidenavToggler">
				  <i class="fa fa-fw fa-angle-left"></i>
				</a>
			  </li>
			</ul>
		</div>
	</nav>
	<div class="content-wrapper">
		<div class="container-fluid">
			<% if(request.getAttribute("mensaje")!= null){ %>
			<div class="row">
				<div class="col-md-3"></div>
				<div class="alert alert-danger col-md-6">
					<%=request.getAttribute("mensaje")%>
				</div>
				<div class="col-md-3"></div>
			</div>
			<% } %>
			<form action="" method="POST">
				<div class="row mt-4 ml-2">
					<div class="col-lg-8 col-sm-8">
						<h2>Completar Datos De Nuevas Columnas</h2>
						<p class='text-danger'>Hemos Detectado Nuevas Columas, Por Favor Complete Para Generar Documeto PDF</p>
					</div>
					<div class="col-lg-2 col-sm-2">
						<button type="submit" class="btn btn-link" name="btn" value="PDF"><i class="fa fa-file-text fa-2x"></i><br>Generar PDF</button>
					</div>
					<div class="col-lg-2 col-sm-2">
						<button type="submit" class="btn btn-link" name="btn" value="EXCEL"><i class="fa fa-file-excel-o fa-2x"></i><br>Generar Excel</button>
					</div>
				</div>
				<input type="hidden" value="<%=(request.getParameter("cuenta")!=null)?request.getParameter("cuenta"):""%>" name="cuenta">
				<input type="hidden" value="<%=(request.getParameter("centro")!=null)?request.getParameter("centro"):""%>" name="centro">
				<input type="hidden" value="<%=(request.getParameter("anho")!=null)?request.getParameter("anho"):""%>" name="anho">
				<input type="hidden" value="<%=(request.getParameter("mes")!=null)?request.getParameter("mes"):""%>" name="mes">
				<input type='hidden' value='<%=(request.getParameter("tipo")!=null)?request.getParameter("tipo"):""%>' name='tipo'>
				<input type="hidden" value="2" name="op">
				<% if(request.getParameter("tipo").equals("G")){ %>
				<div class="row">
					<div class="col-md-12 mt-4 ml-2">
						<table id="tablaGastos" class="table" style="border-top: none;">
							<thead>
							<th>
								<br>ID COMPRA
								<br><small>CÓD. CUENTA</small><small class="text-muted"> - Importe</small>
							</th>
								<c:forEach items="${requestScope.newCols}" var="col">
									<th>${col}</th>
								</c:forEach>
							</thead>
							<tfoot>
								<th>GASTO</th>
								<c:forEach items="${requestScope.newCols}" var="col">
									<th>${col}</th>
								</c:forEach>
							</tfoot>
							<tbody>
								<c:forEach items="${requestScope.gastos}" var="gasto">
									<tr>
										<th>
											${gasto.gastoMesId.idCompra == 0?'-':gasto.gastoMesId.idCompra}
											<br><small>${gasto.gastoMesId.gastoId.codCuenta}</small>
											<small class="text-muted"> - $${gasto.gastoMesId.importe}</small><br>
										</th>
									<c:forEach items="${requestScope.newCols}" var="col">
										<td><c:choose><c:when test="${col == 'TIPO_DOCUMENTO'}"><select class="form-control" name="${gasto.gastoMesId.id}-${col}"><option value="0" selected disabled>--Seleccione Tipo Doc--</option><option value="1">Víatico</option><option value="2">Remuneración</option><option value="3">Factura</option><option value="4">Boleta De Honorarios</option><option value="5">Comprobante</option></select></c:when><c:when test="${col != 'TIPO_DOCUMENTO'}"><input type='<c:choose><c:when test = "${fn:containsIgnoreCase(col, 'fecha_')}">datetime-local</c:when><c:when test = "${fn:containsIgnoreCase(col, 'num_')}">number</c:when></c:choose>' name="${gasto.gastoMesId.id}-${col}" class="form-control " <c:if test = "${fn:containsIgnoreCase(col, 'fecha_')}"> min='2000-01-01'</c:if>></c:when></c:choose></td>
									</c:forEach>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<% } %>
			</form>
		</div>
	</div>
						
	<!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    <!-- Page level plugin JavaScript-->
    <script src="vendor/datatables/jquery.dataTables.js"></script>
    <script src="vendor/datatables/dataTables.bootstrap4.js"></script>
    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin.min.js"></script>
    <!-- Custom scripts for this page-->
    <script src="js/sb-admin-datatables.min.js"></script>
	<script>
		$(document).ready(function(){
			$('#tablaGastos').DataTable();
		});
	</script>
  </body>

</html>
