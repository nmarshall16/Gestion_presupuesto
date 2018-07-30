<%-- 
    Document   : validar
    Created on : 29-07-2018, 1:07:42
    Author     : Nicolas
--%>

<%@page import="java.util.List"%>
<%@page import="cl.inacap.cdn.entities.FuenteF"%>
<%@page import="java.math.BigInteger"%>
<%@page import="cl.inacap.cdn.entities.GastoMes"%>
<%@page import="cl.inacap.cdn.entities.Homologar"%>
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

  <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" />
  <link rel="stylesheet" type="text/css" href="vendor/datetimepicker/css/daterangepicker.css" />

</head>

<body class="fixed-nav sticky-footer bg-dark" id="page-top" onload="cargarCuenta()">
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

      <!-- CARTA -->
      <h3>Validar Cuenta</h3><br>
      <!-- Example DataTables Card-->
       <%
        Homologar homol = (Homologar)request.getAttribute("homologacion");
        BigInteger cuenta = GastoMes.getNumCuenta(homol.getGastoMesId());
       %>
       <form class="form-inline" action="Validar.do" method="post">
          <input type="hidden" name="op" value="4">
          <input type="hidden" name="idProyect" value="<%=homol.getGastoMesId().getAnhoProyectId().getProyectoId().getId()%>" id="vaIdProyecto">
          <input type="hidden" name="idHomol" value="<%=homol.getId()%>">
          <div class="col-md-4">
            <ul class="list-group">
                <li class="list-group-item active">Centro Responsable Actual</li>
                <li class="list-group-item"><%=homol.getGastoMesId().getGastoId().getFuenteFCodCentro().getCodCentro()%></li>
            </ul>
          </div>
          <div class="col-md-4">
            <ul class="list-group">
                <li class="list-group-item active">Cuenta Contable Actual</li>
                <li class="list-group-item"><%=cuenta%></li>
            </ul>
          </div>
          <div class="col-md-4">
            <ul class="list-group">
                <li class="list-group-item active">ID Compra</li>
                <li class="list-group-item">
                    <% 
                        if(!homol.getGastoMesId().getIdCompra().equals(0)){
                            out.print(homol.getGastoMesId().getIdCompra()); 
                        }else{
                            out.print("-");
                        }
                    %>
                </li>
            </ul>
          </div>
          <div class="col-md-12"><br></div>
          <div class="form-row align-items-center">
            <div class="col-md-2 my-1">
                <label style="text-align: left; float: left;">Centro Responsable</label>
                <select class="custom-select mr-sm-2" id="vaFuente" name="fuente">
                    <%
                      List<FuenteF> fuentes = FuenteF.findAll();
                      if(fuentes.size()>0){
                       for(FuenteF fuente:fuentes){
                    %>
                           <option value="<%=fuente.getCodCentro()%>">
                               <%=fuente.getCodCentro()%>
                           </option>
                    <%
                       }
                      }
                    %>
                </select>
            </div>
            <div class="col-md-2 my-1">
                <label style="text-align: left; float: left;">Cuenta Contable</label>
                <select class="custom-select mr-sm-2" id="vaCuenta" name="cuenta">
                  <option>-</option>
                </select>
            </div>
            <div class="col-md-2 my-1">
                <label style="text-align: left; float: left;">Tipo de pago</label>
                <select class="custom-select mr-sm-2" name="pago">
                    <option value="CHK">Cheque</option>
                    <option value="EFT">Transferencia electrónica</option>
                 </select>
            </div>
            <div class="col-auto my-1"><br>
                <input type="text" class="form-control mr-sm-2" placeholder="Banco" name="banco">
            </div>
            <div class="col-auto my-1"><br>
                <input type="number" class="form-control mr-sm-2" placeholder="número de documento" name="documento">
            </div>
            <div class="col-auto my-1"><br>
              <button type="submit" class="btn btn-primary btn-block mr-sm-2">Modificar</button>
            </div>
            </div>
      </form>
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

