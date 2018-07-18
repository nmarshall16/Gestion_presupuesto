<%-- 
    Document   : proyecto
    Created on : 19-05-2018, 17:25:39
    Author     : Nicolas
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cl.inacap.cdn.entities.AnhoProyect"%>
<%@page import="cl.inacap.cdn.entities.Proyecto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%
        Proyecto proyecto = (Proyecto)request.getAttribute("proyecto");
    %>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title><%=proyecto.getNombre()%></title>
  <!-- Bootstrap core CSS-->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom fonts for this template-->
  <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Page level plugin CSS-->
  <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <!-- Custom styles for this template-->
  <link href="css/sb-admin.css" rel="stylesheet">
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
          <a class="nav-link" href="<%=request.getContextPath()%>/Proyect.do">
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
      <!-- Breadcrumbs-->
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="Proyect.do">Inicio</a>
        </li>
        <li class="breadcrumb-item active">Proyecto</li>
      </ol>
	  
      <%  String mensaje = (String)request.getAttribute("mensaje");
          if (mensaje != null) { %>
          
      <div id="mensaje" class="row">
        <div class="col-12 pt-1 pb-1">
            <div class="card card-outline-danger text-center">
                <div class="row">
                    <div class="col-11">
                        <div class="card-block">
                            <h3 class="text-center <% if(mensaje.contains("No")){out.print("text-danger");}else{out.print("text-info");} %> text-info"><%=(String)request.getAttribute("mensaje") %></h3>
                        </div>
                    </div>
                    <div class="col-1">
                        <div class="text-right">
                            <span class="right clickable close-icon pr-2" data-effect="fadeOut" ><i class="fa fa-times"></i></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
      </div>
      <% } %>
      <!-- Se obtene el proyecto enviado desde el Servlet y se cargan los datos
      y años de proyecto del mismo siempre y cuando se reciba un proyecto valido -->
      <% 
          if(proyecto!= null){
          SimpleDateFormat dateFormato = new SimpleDateFormat("dd/MM");
      %>
      <div class="row">
        <div class="col-lg-7 col-sm-8">
          <div class="card">
            <div class="card-header">
                <i class="fa fa-table"></i> <% out.print(proyecto.getNombre()); %>
            </div>
            <div class=" row card-body">
				<div class="row col-md-9 col-sm-8">
					<div class="col-md-12 col-sm-12">
						<p>Codigo de Proyecto: <strong><% out.print(proyecto.getCodigo()); %></strong></p>
					</div>
					<div class="col-md-12 col-sm-12">
						<p>N° Cuenta Corriente: <strong><% out.print(proyecto.getBancoId().getNumCuenta()); %></strong></p>
					</div>
					<div class="col-md-12 col-sm-12">
						<p>Banco: <strong><% out.print(proyecto.getBancoId().getNombre()); %></strong></p>
					</div>
					<div class="col-md-12 col-sm-12">
						<p>Fecha Inicio Proyecto: <strong><% out.print(dateFormato.format(proyecto.getFechaIni())); %></strong></p>
					</div>
					<div class="col-md-12 col-sm-12">
						<p>Fecha Termino Proyecto: <strong><% out.print(dateFormato.format(proyecto.getFechaFin())); %></strong></p>
					</div>              
				</div>
				<div class="row">
					<div class="col-md-12 col-sm-12">
						<a href="<%=request.getContextPath()%>/Proyect.do?idProyect=<%=proyecto.getId()%>&accion=modificarProyecto" style="text-decoration: none;">
							<p align="center"><i class="fa fa-cog fa-2x"></i><br>Modificar</p>
						</a>
					</div>
					<div class="col-md-12 col-sm-12 text-center">
						<button class="btn btn-link text-danger" data-toggle="modal" data-target="#eliminarProyecto" style="text-decoration: none;">
							<i class="fa fa-trash fa-2x"></i>
							<br>Eliminar
						</button>
					</div>	
				</div>
            </div>
          </div>
        </div>
        <div class="col-lg-5 col-sm-4">
          <a href="Year.do?pro=<%=proyecto.getId()%>" style="text-decoration: none;"><p align="center"><i class="fa fa-plus-square fa-2x"></i><br>Añadir Nuevo Año</p></a>
        </div>
      </div>
      <br>
      <%
        Collection<AnhoProyect> anhos = proyecto.getAnhoProyectCollection();
        if(anhos.size() > 0){
      %>
            <div class="card mb-3">
                <div class="card-header">
                    <i class="fa fa-table"></i> Años De Proyecto
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                            <thead>
                                <tr>
                                    <th>Año Proyecto</th>
                                    <th>Presupuesto</th>
                                    <th>Mes</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tfoot>
                                <tr>
                                    <th>Año Proyecto</th>
                                    <th>Presupuesto</th>
                                    <th>Mes</th>
                                    <th></th>
                                </tr>
                            </tfoot>
                            <tbody>
                            <%
                            for(AnhoProyect anho:anhos){        
                            %>
                                <tr>
                                    <td><% out.print(anho.getNum()); %></td>
                                    <td><a href="Presupuesto.do" class="btn btn-secondary btn-block" role="button">Presupuesto</a></td>
                                    <td class="meses">
                                        <select class="custom-select">
                                            <option selected value="1">Enero</option>
                                            <option value="2">Febrero</option>
                                            <option value="3">Marzo</option>
                                            <option value="4">Abril</option>
                                            <option value="5">Mayo</option>
                                            <option value="6">Junio</option>
                                            <option value="7">Julio</option>
                                            <option value="8">Agosto</option>
                                            <option value="9">Septiembre</option>
                                            <option value="10">Octubre</option>
                                            <option value="11">Noviembre</option>
                                            <option value="12">Diciembre</option>
                                        </select>
                                    </td>
                                    <td><button type="button" class="btn btn-primary btn-block selectAnho" value="<% out.print(anho.getId()); %>">Seleccionar</button></td>
                                </tr>
                            <%
                            }
                            %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
      <%
        }else{ %>
        <div class="row">
          <div class="col-12 text-center mt-3 mb-3">
            <h2 class="text-info">No Existe Ningun Año de Proyecto Registrado</h2>
          </div>
        </div>
      <% }
    }else{
      %>
        <div class="row">
            <div class="col-12 text-center mt-5 mb-5">
                <h2 class="text-danger">No Se Ha Podido Encontrar El Proyecto Señalado</h2>
            </div>
        </div>
    <% } %>
      <div class="col-md-1" align="center">
          <a href="Proyect.do" style="text-decoration: none;">
          <i class="fa fa-reply-all fa-2x"></i><br><strong>Volver</strong>
          </a>
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
	
	<div class="modal fade" id="eliminarProyecto" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" style="display: none;">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">¿Desea Eliminar Proyecto?</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">
					<p id="pModal">Al eliminar el proyecto, este será movido a "Proyectos Eliminados"</p>
				</div>
				<div class="modal-footer">
					<a href="#" class="btn btn-secondary" data-dismiss="modal">Cancelar</a>
					<a href="<%=request.getContextPath()%>/Proyect.do?idProyect=<%=proyecto.getId()%>&accion=eliminarProyecto" class="btn btn-danger">Eliminar Proyecto</a>
				</div>
			</div>
		</div>
	</div>
	
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
    <script src="js/card.js"></script>
    <script src="vendor/rut/rut.min.js"></script>
    <script src="js/main.js"></script>
  </div>
</body>

</html>
