<%-- 
    Document   : nuevoProyecto
    Created on : 01-06-2018, 16:47:56
    Author     : dell
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cl.inacap.cdn.entities.Banco"%>
<%@page import="cl.inacap.cdn.entities.Proyecto"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Nuevo Proyecto</title>
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
  
  <!-- Switch Button -->
  <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">  
  
  <!-- Custom CSS -->
  <link href="css/custom.css" rel="stylesheet">


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
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Inicio">
          <a class="nav-link" href="<%=request.getContextPath()%>/Proyect.do">
            <i class="fa fa-fw fa-home"></i>
            <span class="nav-link-text">Inicio</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Proyectos Eliminados">
          <a class="nav-link" href="charts.html">
            <i class="fa fa-fw fa-archive"></i>
            <span class="nav-link-text">Proyectos Eliminados</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tipos de usuario">
          <a class="nav-link" href="tables.html">
            <i class="fa fa-fw fa-sitemap"></i>
            <span class="nav-link-text">Tipos de usuario</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Usuarios">
          <a class="nav-link" href="#collapseComponents">
            <i class="fa fa-fw fa-users"></i>
            <span class="nav-link-text">Usuarios</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Cerrar Sesión">
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
<% if (request.getAttribute("titulo") != null) { 
		SimpleDateFormat dateFormato = new SimpleDateFormat("dd/MM/yyyy");
		Proyecto proyecto = (Proyecto)request.getAttribute("proyecto"); %> 
		<!-- Breadcrumbs-->
		<ol class="breadcrumb">
		  <li class="breadcrumb-item">
			<a href="Proyect.do">Inicio</a>
		  </li>
		  <%=(proyecto != null)?("<li class='breadcrumb-item'><a href='"+request.getContextPath()+"/Proyect.do?idProyect="+proyecto.getId()+"'>"+proyecto.getNombre()+"</a></li>"):("")%>
		  <li class="breadcrumb-item active"><%=request.getAttribute("titulo")%></li>
		</ol>

		<!-- CARTA -->
		<div class="row">
			<div class="col-lg-9 col-md-9 col-sm-9">
				<h3><%=request.getAttribute("titulo")%></h3>
				<%= (proyecto != null) ? 
				"<div class='col-sm-12 col-md-12 col-lg-12 card pt-2'>"
					+"<p><strong>Id Proyecto</strong> : "+proyecto.getId()+"</p>"
					+"<p><strong>Nombre Proyecto</strong> : "+proyecto.getNombre()+"</p>"
				+"</div>" : "" %>
				<br>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-3">
				<% if ((request.getAttribute("titulo") != null) && (request.getAttribute("titulo") == "Modificar Proyecto")){ %>  
				<p class="text-info"><strong>Modificar Proyecto</strong></p>
				<input id="switch" type='checkbox' data-toggle='toggle' data-on='<i class="fa fa-unlock"></i> Habilitado' data-off='<i class="fa fa-lock"></i> Bloqueado' data-onstyle='success' data-offstyle='danger'>				
				<% } %>
			</div>
			
		</div>
		
		<% if (!((List<String>)request.getAttribute("errores")).isEmpty()) { %>
		<div class="alert alert-danger alert-dismissible fade show" role="alert">
			<p><strong>Debes Completar Todos Los Datos</strong><p>
			<ul>
		<% for(String mensaje : ((List<String>)request.getAttribute("errores"))) {
				out.println("<li>"+mensaje+"</li>");
			} %>
			</ul>
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
		<% } %>
		
	<form action="Proyect.do?<%=(proyecto != null)?"idProyect="+proyecto.getId()+"&":""%>accion=guardarProyecto" method="post">
        <div class="form-row">
			<div class="form-group col-md-6">
				<label for="nameProyect">Nombre del Proyecto</label>
				<input <%=(proyecto!=null)?("value='"+proyecto.getNombre()+"'"):("")%>
				type="text" class="form-control <%=(proyecto!=null)?"lock":""%>" name="nameProyect" id="nameProyect">
			</div>
			<div class="form-group col-md-6">
				<label for="codProyect">Codigo de Proyecto</label>
				<input <%=(proyecto!=null)?("value='"+proyecto.getCodigo()+"'"):("")%>
				type="text" class="form-control <%=(proyecto!=null)?"lock":""%>" name="codProyect" id="codProyect">
			</div>
        </div>
        <div class="form-row">
			<div class="form-group col-md-6">
				<label for="fechaInicio">Fecha de Inicio</label>
				<input <%=(proyecto!=null)?("value='"+dateFormato.format(proyecto.getFechaIni())+"'"):("")%>
				type="text" class="form-control <%=(proyecto!=null)?"lock":""%>" id="fechaInicio" name="fechaInicio" placeholder="Seleccionar Fecha">
			</div>
			<div class="form-group col-md-6">
				<label for="fechaTermino">Fecha de Termino</label>
				<input <%=(proyecto!=null)?("value='"+dateFormato.format(proyecto.getFechaFin())+"'"):("")%>
				type="text" class="form-control <%=(proyecto!=null)?"lock":""%>" id="fechaTermino" name="fechaTermino" placeholder="Seleccionar Fecha">
			</div>
        </div>
		<div class="card mb-3">
			<div class="card-header">
				<i class="fa fa-university"></i>
				Datos Bancarios
			</div>
			<div class="card-body">
				<div id="datosBancarios">
					<div id="datos1" class="form-row">
						<div class="form-group col-md-4 banco">
							<label for="banco">Banco</label>
							<div class="input-group bco">
								<select id="banco" type="text" class="form-control bank <%=(proyecto!=null)?"lock":""%>" name="banco" onchange="selectBanco(this)">
									<option value="0" disabled <%=(proyecto == null) ? "selected" : "" %>>-- Seleccione Banco --</option>
									<c:forEach items="${requestScope.bancos}" var="banco">
									<option value="${banco.id}">${banco.nombre}</option>
									</c:forEach>
									<option value="otro">** Agregar Banco **</option>
								</select>
							</div>
						</div>
						<div class="form-group col-md-3 cuenta">
							<label for="cuenta">Cuenta</label>
							<div class="input-group cta">
								<input type="number" min="0" class="form-control <%=(proyecto!=null)?"lock":""%>" placeholder="Ingrese Cuenta" name="cuenta">
							</div>
						</div>
						<div class="form-group col-md-4 fuente">
							<label for="fuente">Fuente De Financiamiento</label>
							<select type="text" class="form-control <%=(proyecto!=null)?"lock":""%>" name="fuente">
								<option value="0" disabled <%=(proyecto == null) ? "selected" : "" %>>-- Seleccione Fuente De Financiamiento --</option>
								<c:forEach items="${requestScope.fuentes}" var="fte">
									<option value="${fte.codCentro}">${fte.nombre}</option>
								</c:forEach>
							</select>
						</div>
						<input name="divDato" type="hidden" value="datos1">
					</div>
				</div>
			</div>
			<div class="card-footer small text-right">
				<button type="button" class="btn btn-link" onclick="agregarDatosBancarios()">Nueva Cuenta <i class="fa fa-plus"></i></button>
				<input name="cantDatos" type="hidden" value="1">
			</div>
		</div>
		<div class="row">
			<div class="col-lg-2" align="center">
				<a href="<%=request.getContextPath()%>/Proyect.do" style="text-decoration: none;">
					<i class="fa fa-reply-all fa-2x"></i><br><strong>Volver Sin Guardar</strong>
				</a>
			</div>
			<div class="col-lg-8">
			</div>
			<div class="col-lg-2" align="center">
				<button type="submit" class="btn btn-link" style="text-decoration: none;">
					<i class="fa fa-floppy-o fa-2x"></i><br><strong>Guardar Cambios</strong>
				</button>
			</div>
		</div>
	</form>
		
	<% }else{ %>
	<div class="row">
		<div class="col-md-12 text-center mt-5">
			<h1 class="text-danger">Proyecto No Encontrado</h1>
			<p class="text-info mb-5">Por Favor, Busque Con Identificador Válido</p>
			<a href="<%=request.getContextPath()%>/Proyect.do" class="btn btn-primary btn-lg mt-5">Volver A Proyectos</a>
		</div>
	</div>
	<% } %>	
    </div><!-- /.container-fluid-->
  </div><!-- /.content-wrapper-->
	
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
	<!-- Switch Button -->
	<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>


    <script type="text/javascript" src="vendor/datetimepicker/js/moment.min.js"></script>
    <script type="text/javascript" src="vendor/datetimepicker/js/daterangepicker.js"></script>
    <script type="text/javascript" src="vendor/datetimepicker/js/demo.js"></script>
	<script type="text/javascript" src="js/myScript.js"></script>
	
	<script>
	   
		function selectBanco(elem) {
			if ($(elem).val() === "otro") {
				$(elem).replaceWith(
					'<input type="text" class="form-control" name="banco" placeholder="Ingresar Nuevo Banco">'
					+"<div class='input-group-append'>"
						+"<button class='btn btn-outline-danger' type='button' onclick='selectBack(this)'><i class='fa fa-times'></i></button>"
					+"</div>"
				);
			}
		}
		
		function selectBack(btn){
			$(btn).parent().parent('.bco').html(
				'<select id="banco" class="form-control bank" name="banco" onchange="selectBanco(this)">'
					+'<option value="0" disabled selected>-- Seleccione Banco --</option>'
				+'</select>'
			);
			cargarBancos();
		}
		
		function cargarBancos(){
			$.ajax({
				url : 'Proyect.do',
				data : {
					accion:'getBancos',
				},
				type : 'POST',
				dataType : 'json',
				success : function(data) {
					for(var bco in data){
						$("#datos"+(contarDatosBancarios()-1)).children('.banco').children('.input-group').children('.form-control').append(($('<option>', {value: bco, text: data[bco]})));
					}
					$("#datos"+(contarDatosBancarios()-1)).children('.banco').children('.input-group').children('.form-control').append($('<option value="otro">*** Agregar Cuenta ***</option>'));
				},
				error : function(xhr, status, detalle) {
					$("#datos"+(contarDatosBancarios()-1)).children('.banco').children('.input-group').children('.form-control').append($('<option value="otro">*** Agregar Cuenta ***</option>'));
				}
			});
		}
		
	</script>
  </div>
</body>
</html>
