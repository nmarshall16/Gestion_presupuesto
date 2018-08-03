<%-- 
    Document   : cargarArchivo
    Created on : 18-07-2018, 0:33:46
    Author     : Nicolas
--%>

<%@page import="cl.inacap.cdn.entities.Homologar"%>
<%@page import="cl.inacap.cdn.entities.AnhoProyect"%>
<%@page import="java.math.BigDecimal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
    <title>CDN INACAP</title>
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
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    <a class="navbar-brand" href="Proyect.do">CDN INACAP</a>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Inicio">
          <a class="nav-link" href="Proyect.do">
            <i class="fa fa-fw fa-home"></i>
            <span class="nav-link-text">Inicio</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Proyectos">
          <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#proyectosNav" data-parent="#exampleAccordion">
            <i class="fa fa-fw fa-university"></i>
            <span class="nav-link-text">Proyectos</span>
          </a>
          <ul class="sidenav-second-level collapse" id="proyectosNav">
            <li>
              <a href="Proyect.do">Proyectos Activos</a>
            </li>
            <li>
              <a href="Proyect.do?op=1">Proyectos Eliminados</a>
            </li>
            <li>
              <a href="asignarProyect.jsp">Asignar Proyecto</a>
            </li>
          </ul>
        </li>
        <% 
            if(request.getAttribute("anho")!=null && request.getAttribute("mes")!=null){
        %>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Verificar Cuenta">
          <a class="nav-link" href="Validar.do?anho=<%=request.getAttribute("anho")%>&mes=<%=request.getAttribute("mes")%>&op=1">
            <i class="fa fa-fw fa-exclamation-triangle"></i>
            <span class="nav-link-text">
                Verificar Cuenta
            <span class="badge badge-primary badge-pill">
                    <%
                    BigDecimal bd = new BigDecimal(request.getAttribute("anho").toString());
                    int p = Homologar.getGastosP(AnhoProyect.findById(bd.intValue()), request.getAttribute("mes").toString());
                    out.print(p);
                    %>
            </span>
            </span>
          </a>
        </li>
        <%
          }  
        %>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Gastos">
          <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#gastosNav" data-parent="#exampleAccordion">
            <i class="fa fa-fw fa-sort-alpha-asc"></i>
            <span class="nav-link-text">Gastos</span>
          </a>
          <ul class="sidenav-second-level collapse" id="gastosNav">
            <li>
              <a href="#">Gastos</a>
            </li>
            <li>
              <a href="#">Gastos Excepcionales</a>
            </li>
          </ul>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Cuentas">
          <a class="nav-link" href="#">
            <i class="fa fa-fw fa-suitcase"></i>
            <span class="nav-link-text">Cuentas</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tipos de Usuario">
          <a class="nav-link" href="TipoUsu.do?op=1">
            <i class="fa fa-fw fa-sitemap"></i>
            <span class="nav-link-text">Tipos de Usuario</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Usuarios">
          <a class="nav-link" href="Usuario.do?op=1">
            <i class="fa fa-fw fa-users"></i>
            <span class="nav-link-text">Usuarios</span>
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
      <ul class="navbar-nav ml-auto">
        <li class="nav-item">
          <a class="nav-link" data-toggle="modal" data-target="#exampleModal">
            <i class="fa fa-fw fa-sign-out"></i>Salir</a>
        </li>
      </ul>
    </div>
  </nav>
  <div class="content-wrapper">
    <div class="container-fluid">
      <!-- Breadcrumbs-->
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="Proyect.do">Inicio</a></li>
        <li class="breadcrumb-item">
            <a href="Proyect.do">Proyecto</a>
        </li>
        <li class="breadcrumb-item active">Cargar Archivo</li>
      </ol>
      <!-- Example DataTables Card-->
      <div class="alert alert-danger" role="alert" style="display: none;">
        <label class="error"></label>
      </div>
      <div class="alert alert-success" role="alert" style="display: none;">
          Se han cargado los gastos correctamente
      </div>
      <!-- CARTA -->
      <div class="card">
        <div class="row card-body">
          <div class="col-md-12">
            <h3>
            <%
        char tipo = request.getAttribute("tipo").toString().charAt(0);
        switch(tipo){
            case 'G':
                out.print("Aportes Pecuniarios");
            break;
            
            case 'A':
                out.print("Aportes No Pecuniarios");
            break;
        }
      %>
            </h3>
            <label>No se encontro ningun archivo cargado, porfavor cargue su archivo aquí</label>
          </div>
          <form enctype="multipart/form-data" id="uploadFileForm" method="post" action="Gasto.do" class="form-inline">
            <input type="hidden" name="mes" value="<%=request.getAttribute("mes")%>">
            <input type="hidden" name="idAnho" value="<%=request.getAttribute("anho")%>">
            <input type="hidden" name="tipo" value="<%=request.getAttribute("tipo")%>">
            <input type="hidden" name="op" value="<%=request.getAttribute("opcion")%>">
            <div class="col-md-12">
              <div class="custom-file">
                    <input type="file" class="custom-file-input" id="customFileLang" lang="es" name="archivo">
                    <label class="custom-file-label" for="customFileLang">Seleccionar Archivo</label>
                </div>
          </div>
          <div class="col-md-12">
              <br>
              <button type="submit" class="btn btn-primary btn-sm btn-block">Cargar Archivo</button>
          </div>
          </form>
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
    <div class="modal" id="cargaModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body" style="text-align: center;">
              <p aling="center"><i class="fa fa-refresh fa-spin fa-3x fa-fw"></i><br>Cargando Archivo</p>
          </div>
        </div>
      </div>
    </div>
    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/jquery/jquery.form.min.js"></script>
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
    <script src="vendor/rut/rut.min.js"></script>
    <script src="js/main.js"></script>
  </div>
</body>
</html>
