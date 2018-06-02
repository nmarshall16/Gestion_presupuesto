<%@page import="java.math.BigInteger"%>
<%@page import="cl.inacap.cdn.entities.Proyecto"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <a class="navbar-brand" href="index.html">CDN INACAP</a>
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
    
      <% Proyecto proyecto = (Proyecto)request.getAttribute("proyecto");  
          String anho = (String)request.getAttribute("anho"); %>
      
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="Proyect.do">Proyectos</a></li>
        <li class="breadcrumb-item">
            <a href="Proyect.do?idProyect=<%=proyecto.getId()%>&accion=mostrarProyecto"><%=proyecto.getNombre()%></a>
        </li>
        <li class="breadcrumb-item active">Nuevo Año</li>
      </ol>

    <%  if (request.getAttribute("ctas") != null) { %> 
      <!-- CARTA -->
      <form action="Year.do" method="post">
        <div class="row">
          <div class="col-lg-4">
            <h3>Añadir Nuevo Año</h3>
            <p>Rango Para Nuevo Año De Proyecto</p>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon1"><i class="fa fa-table"></i></span>
              </div>
              <input type="text" name="fechas" class="form-control" id="daterange" placeholder="Select value">            
              <input type="hidden" name="op" value="2">               
              <input type="hidden" name="pro" value="<%=proyecto.getId()%>"> 
            </div>
          </div>
          <div class=" row col-lg-8" >
            <div class="col-lg-8">
                <div class="mt-4">
                    <h5>Proyecto : <span style="font-size: 15px"><%=proyecto.getNombre()%></span></h5>
                    <h5>Número de año : <span style="font-size: 15px"><%=anho %></span></h5>
                </div>
            </div>
            <div class="col-lg-4" align="center">
                <button type="button" class="btn btn-link" data-toggle="modal" data-target="#confirmacion" style="text-decoration: none;">
                    <i class="fa fa-plus-square fa-2x"></i>
                    <br>Guardar Año
                </button>
            </div>
              
          </div>
        </div>
        <br>
        <!-- Example DataTables Card-->
        <div class="card mb-3">
          <table id="cuentas" class="table table-hover">
            <thead>
              <tr>
                <th scope="col">Cuentas</th>
                <th scope="col">Sercotec</th>
                <th scope="col">Inacap</th>
                <th scope="col">Aporte No Pecuniario</th>
              </tr>
            </thead>
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
            <tbody>

              <c:forEach items="${requestScope.ctas}" var="cta">
                  <tr>
                      <th scope="row">${cta.id}</th>
                      <td><input type="number" name="sercotec" class="form-control" value="0"></td>
                      <td><input type="number" name="inacap" class="form-control" value="0"></td>
                      <td><input type="number" name="pecuniarios" class="form-control" value="0"></td>
                  </tr>
              </c:forEach>

            </tbody>
          </table>
        </div>
        
        <!-- Confirmación Modal -->
        <div class="modal fade" id="confirmacion" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">¿Guardar Nuevo Año?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                  <p id="pModal">¿Desea guardar nuevo año ingresado?</p>
              </div>
              <div class="modal-footer">
                <a href="#" class="btn btn-secondary" data-dismiss="modal">Cancelar</a>
                <button type="submit" class="btn btn-primary">Guardar Nuevo Año</button>
              </div>
            </div>
          </div>
        </div>
        
      </form>
      <div class="col-md-2" align="center">
          <a href="#" style="text-decoration: none;">
          <i class="fa fa-reply-all fa-2x"></i><br><strong>Volver Sin Guardar</strong>
          </a>
      </div>
      <% }else{  %>
      <div class="row">
          <div class="col-12 text-center mt-5 mb-5">
            <h2 class="text-danger">Aún No Existen Cuentas Para Crear Nuevo Año</h2>
          </div>
          <div class="col-12 text-center mt-5 mb-5">
            <button class="btn btn-dark">Registrar Cuentas</button>
          </div>
      </div>
      <div class="col-md-2" align="center">
          <a href="#" style="text-decoration: none;">
          <i class="fa fa-reply-all fa-2x"></i><br><strong>Volver</strong>
          </a>
      </div>
      <% } %>
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
              <span aria-hidden="true">Ã—</span>
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
