<%-- 
    Document   : presupuesto
    Created on : 17-07-2018, 19:19:30
    Author     : dell
--%>

<%@page import="java.math.BigDecimal"%>
<%@page import="cl.inacap.cdn.entities.Presupuesto"%>
<%@page import="cl.inacap.cdn.entities.FuenteF"%>
<%@page import="cl.inacap.cdn.entities.Cuenta"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
	List<Cuenta>		cuentas			= (List)request.getAttribute("ctas");
	List<FuenteF>		fuentes			= (List)request.getAttribute("fuentes");
	List<Presupuesto>	presupuestos	= (List)request.getAttribute("presupuestos");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Proyecto</title>
  <!-- Bootstrap core CSS-->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom fonts for this template-->
  <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Page level plugin CSS-->
  <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <!-- Custom styles for this template-->
  <link href="css/sb-admin.css" rel="stylesheet">

  <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" />
  <link rel="stylesheet" type="text/css" href="vendor/datetimepicker/css/daterangepicker.css" />

</head>

<body class="fixed-nav sticky-footer bg-dark" id="page-top">
  <!-- Navigation-->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    <a class="navbar-brand" href="index.html">Start Bootstrap</a>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Dashboard">
          <a class="nav-link" href="index.html">
            <i class="fa fa-fw fa-home"></i>
            <span class="nav-link-text">Inicio</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Charts">
          <a class="nav-link" href="charts.html">
            <i class="fa fa-fw fa-archive"></i>
            <span class="nav-link-text">Proyectos Eliminados</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tables">
          <a class="nav-link" href="tables.html">
            <i class="fa fa-fw fa-sitemap"></i>
            <span class="nav-link-text">Tipos de usuario</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Components">
          <a class="nav-link" href="#collapseComponents">
            <i class="fa fa-fw fa-users"></i>
            <span class="nav-link-text">Usuarios</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Example Pages">
          <a class="nav-link" href="#collapseExamplePages">
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
      <!-- Breadcrumbs-->
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#">Dashboard</a>
        </li>
        <li class="breadcrumb-item active">Tables</li>
      </ol>
	  
      <!-- CARTA -->
      <h3>Presupuesto General</h3>
      <p>Detalle Presupuesto</p>
		<!-- Example DataTables Card-->
		<div class="card mb-3">
			<form action="Presupuesto.do?action=2" method="post">
				<table id="cuentas" class="table table-hover">
				  <thead>
					<tr>
					  <th scope="col">Cuentas</th>
						<c:forEach items="${requestScope.fuentes}" var="fte">
							<th scope="col">${fte.nombre}</th>
						</c:forEach>
					</tr>
				  </thead>
				  <tbody>
					<%  String nombre = "";
						for(Cuenta cta : cuentas){ %>
					<tr>
						<th scope="row"><%=cta.getNombre()%></th>
					<%	
						for(FuenteF fte : fuentes){
							for(Presupuesto pto : presupuestos){
								//out.print("--"+pto.toString()+"<br>");
								BigDecimal codFtePrto	= pto.getFuenteFCodCentro().getCodCentro();
								BigDecimal codFte		= fte.getCodCentro();
								//out.print(codFtePrto+" - "+codFte+"<br>");
								if(codFte.compareTo(codFtePrto) == 0 && pto.getCuentaId().getId().compareTo(cta.getId()) == 0){

									if (pto.getFuenteFCodCentro().getNombre().equals("Inacap")) {
										nombre = "inacap";
									}else{
										if(pto.getFuenteFCodCentro().getNombre().equals("Sercotec")){
											nombre = "sercotec";
										}else{
											nombre = "pecuniarios";
										}
									}
					%>
						<td><input name="<%=nombre%>" type="text" class="form-control" value="<%=pto.getMontoTot()%>"></td>
					<%					
								}
							}
						} %>
					</tr>
					<%	} %>
				  </tbody>
				  <tfoot>
					<tr>
					  <td>Total:</td>
					  <td id="Tsercotec">$0</td>
					  <td id="Tinacap">$0</td>
					  <td id="Tpecuniarios">$0</td>
					</tr>
					 <tr>
					  <td>Total General:</td>
					  <td id="total" colspan="3">$0</td>
					</tr>
				  </tfoot>
				</table>
			</form>
		</div>
		<div class="row">
		  <div class="col-lg-2" align="center">
			<a href="#" style="text-decoration: none;">
			<i class="fa fa-reply-all fa-2x"></i><br><strong>Volver Sin Guardar</strong>
			</a>
		</div>
		<div class="col-lg-8 col-md-8">
		</div>
		<div class="col-lg-2 col-md-2" align="center">
			<a href="Year.do?" style="text-decoration: none;">
				<i class="fa fa-floppy-o fa-2x"></i><br><strong>Guardar Cambios</strong>
			</a>
		</div>
		</div>
		</div>
		<!-- /.container-fluid-->
		<!-- /.content-wrapper-->
		<footer class="sticky-footer">
		  <div class="container">
			<div class="text-center">
			  <small>Copyright © Your Website 2018</small>
			</div>
		  </div>
		</footer>
		<!-- Scroll to Top Button-->
		<a class="scroll-to-top rounded" href="#page-top">
		  <i class="fa fa-angle-up"></i>
		</a>
		<!-- Logout Modal-->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
			<div class="modal-content">
			  <div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
				<button class="close" type="button" data-dismiss="modal" aria-label="Close">
				  <span aria-hidden="true">×</span>
				</button>
			  </div>
			  <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
			  <div class="modal-footer">
				<button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
				<a class="btn btn-primary" href="login.html">Logout</a>
			  </div>
			</div>
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

		<script type="text/javascript" src="vendor/datetimepicker/js/moment.min.js"></script>
		<script type="text/javascript" src="vendor/datetimepicker/js/daterangepicker.js"></script>
		<script type="text/javascript" src="vendor/datetimepicker/js/demo.js"></script>
		<script type="text/javascript" src="vendor/calculador/calculador.js"></script>

	</div>
</body>
</html>

