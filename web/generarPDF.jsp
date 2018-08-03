<%-- 
    Document   : generarPDF
    Created on : 26-07-2018, 16:41:54
    Author     : Nicolas
--%>

<%@page import="cl.inacap.cdn.entities.FuenteF"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="cl.inacap.cdn.entities.Cuenta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
  <link href="css/custom.css" rel="stylesheet">
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
            <i class="fa fa-fw fa-archive"></i>
            <span class="nav-link-text">Mayor General</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Charts">
          <a class="nav-link" href="charts.html">
            <i class="fa fa-fw fa-archive"></i>
            <span class="nav-link-text">Aporte No Pecuniario</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tables">
          <a class="nav-link" href="tables.html">
            <i class="fa fa-exclamation-triangle"></i>
            <span class="nav-link-text">Verificar Cuenta</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Components">
          <a class="nav-link" href="#collapseComponents">
            <i class="fa fa-folder-open"></i>
            <span class="nav-link-text">Documentos</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Example Pages">
          <a class="nav-link" href="#collapseExamplePages">
            <i class="fa fa-fw fa-sign-out"></i>
            <span class="nav-link-text">Volver</span>
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

		<% String mensaje = (String)request.getAttribute("mensaje");
			if(mensaje != null && mensaje.contains("Existe")){ %>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="alert alert-danger col-md-8 text-center"><%=mensaje%></div>
			<div class="col-md-2"></div>
		</div>
		<% } %>
      <!-- CARTA -->
      <h3><%=(request.getParameter("tipo").equals("G")) ? "Crear Mayor Contable De Gastos" :"Crear Mayor Contable De Gastos No Pecuniarios"%></h3><br>
      <!-- Example DataTables Card-->
      <form action="PDF" method="POST">
		  <input type='hidden' value='<%=request.getParameter("mes")%>' name='mes'>
		  <input type='hidden' value='<%=request.getParameter("anho")%>' name='anho'>
		  <input type='hidden' value='<%=request.getParameter("tipo")%>' name='tipo'>
		<div class="row">
			<div class="col-md-6 col-sm-12">
				<table id="tablaColumnas" class="table table-hover">
					<thead>
						<tr>
							<th style="width: 5em">N°</th>
							<th>Columna</th>
							<th style="width: 5em"><button type="button" class="btn btn-primary btn-sm pull-right" data-toggle="modal" data-target="#nuevaColumna"><i class="fa fa-plus"></i></button></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.columnas}" var="col">
							<tr>							
								<td>${col.key}</td>
								<td>${col.value}</td>
								<td>
									<button type="button" class="btn btn-sm btn-danger ml-1 btnDel">
										<i class="fa fa-trash"></i>
									</button>
									<i class='fa fa-arrows-v pull-right text-info'></i>
									<input type="hidden" value="${col.key}-${col.value}" name="newCols">
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="conf" class="col-md-6 col-sm-12 ">
				<div class="card-header" align="center">
					<strong>Seleccionar Cuenta</strong>
				</div>
				<select class="custom-select" multiple size="7" name="cuenta">
					<c:forEach items="${requestScope.cuentas}" var="cta">
						<option value="${cta.id}">${cta.nombre}</option>
					</c:forEach>
				</select>
				<div class="form-group row mt-3">
					<label class="col-sm-8">¿Desea Validar Por Centro Responsable?</label>
					<div class="col-sm-4">
					  <div class="form-check">
						  <input class="form-check-input" type="checkbox" id="cF">
					  </div>
					</div>
				</div>
			</div>
		</div>
      <br>
		<div class="row">
			<div class="col-lg-2" align="center">
				<a href="#" style="text-decoration: none;">
					<i class="fa fa-reply-all fa-2x"></i><br><strong>Volver Sin Guardar</strong>
				</a>
			</div>
			<div class="col-lg-8"></div>
			<div class="col-lg-2" align="center">
				<button type="submit" class="btn btn-link"><i class="fa fa-file-text fa-2x"></i><br><strong>Generar Documento</strong></button>
			</div>
		</div>
	</form>
	<footer class="sticky-footer">
      <div class="container">
        <div class="text-center">
          <small>Copyright © Your Website 2018</small>
        </div>
      </div>
    </footer>
	</div><!-- /.container-fluid-->
</div> <!-- /.content-wrapper-->
    
    
    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
      <i class="fa fa-angle-up"></i>
    </a>
	
	<!-- Small modal -->
	<div id="nuevaColumna" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="nuevaColumna" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
				  <h5 class="modal-title" id="exampleModalLabel">Nueva Columna</h5>
				  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				  </button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<input class="form-control" type="text" id="newCol" value="" placeholder="Ingrese Nombre De Columna">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
					<button type="button" class="btn btn-primary" id="btnNewCol">Crear Columna</button>
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

    <script type="text/javascript" src="vendor/datetimepicker/js/moment.min.js"></script>
    <script type="text/javascript" src="vendor/datetimepicker/js/daterangepicker.js"></script>
    <script type="text/javascript" src="vendor/datetimepicker/js/demo.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

	<script>
		$(document).ready(function(){
			/* 
			 * función de columnas ordenables
			 */
			var tabla = $( "#tablaColumnas tbody" ).sortable( {
				update: function(event, ui){
						$(this).children().each(function(index) {
						$(this).find('td').first().html(index + 1);
					});
				}
			});
			
			// Agregar Nueva Columna
			$('#btnNewCol').click(function(){
				var num = $('#tablaColumnas tbody').children().last().children().first().text();
				if($('#newCol').val() !== ""){
					$('#tablaColumnas tbody').append(
						"<tr>"
							+"<td>"+(parseInt(num)+1)+"</td>"
							+"<td>"+$('#newCol').val().toUpperCase().replace(/ /g, "_")+"</td>"						
							+"<td>"
								+"<button type='button' class='btn btn-sm btn-danger ml-1 btnDel'>"
									+"<i class='fa fa-trash'></i>"
								+"</button>"
								+"<i class='fa fa-arrows-v pull-right text-info'></i>"
								+"<input type='hidden' value='"+(parseInt(num)+1)+'-'+($('#newCol').val().toUpperCase().replace(/ /g, "_"))+"' name='newCols'>"
							+"</td>"
						+"</tr>"
					);
				}
				
				$('#nuevaColumna').modal('hide');
				$('#newCol').val('');
			});
			
			// Eliminar Columna
			$('.btnDel').click(function(){
				$(this).parent().parent().remove();
				console.log($(this).parent().parent());
				var cant = $('tbody').children().length;
				$('tbody').children().children().first().text(1);
				for(var i = 2 ; i <= cant ; i++){
					$('tbody').children().eq(i-1).children().first().text(i);
				}
			});
			
			// Seleccionar Centro Responsable
			$('#cF').prop('checked', false);
			$( '#cF' ).on( 'click', function() {
				if( $(this).is(':checked') ){
					$('#conf').append(
						"<div class='form-group row mt-3 fte alert alert-info'>"
							+"<label class='col-sm-7 align-middle' for='centro'>Seleccione Centro Responsable</label>"
							+"<div class='col-sm-5'>"
								+"<select class='form-control' name='centro'>"
								+"</select>"
							+"</div>"
						+"</div>"
					);
					cargarFuentes();
				} else {
					$('.fte').remove();
				}
			});
			
			function cargarFuentes(){
				$.ajax({
					url : 'PDF',
					data : {
						op:'1',
					},
					type : 'POST',
					dataType : 'json',
					success : function(data) {
						for(var fte in data){
							$("select[name=centro]").append(($('<option>', {value: fte, text: data[fte]})));
						}
					},
					error : function(xhr, status, detalle) {
						console.log(status);
						console.log(xhr);
						console.log(detalle);
					}
				});
			}
		});
		
		
		
	</script>
	
  </div>
</body>
</html>

