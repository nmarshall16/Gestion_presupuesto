<%-- 
    Document   : validar
    Created on : 29-07-2018, 1:07:42
    Author     : Nicolas
--%>

<%@page import="cl.inacap.cdn.entities.AnhoProyect"%>
<%@page import="java.math.BigDecimal"%>
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

<body class="fixed-nav sticky-footer bg-dark" id="page-top" onload="cargarCuenta()">
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
        <li class="breadcrumb-item active">Validar Cuenta</li>
      </ol>
      <div class="card">
        <div class="row card-body">
          <div class="col-md-12">
            <h3>Validar Cuenta</h3>
          </div>
        </div>
      </div><br>
      <div class="card">
        <div class="row card-body">
          <%
            Homologar homol = (Homologar)request.getAttribute("homologacion");
            BigInteger cuenta = GastoMes.getNumCuenta(homol.getGastoMesId());
           %>
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
          <div class="col-md-12">
           <br><hr>
           <form action="Validar.do" method="post">
              <input type="hidden" name="op" value="4">
              <input type="hidden" name="idProyect" value="<%=homol.getGastoMesId().getAnhoProyectId().getProyectoId().getId()%>" id="vaIdProyecto">
              <input type="hidden" name="idHomol" value="<%=homol.getId()%>">
              <div class="form-row">
                <div class="form-group col-md-2">
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
                <div class="form-group col-md-2">
                  <label style="text-align: left; float: left;">Cuenta Contable</label>
                    <select class="custom-select mr-sm-2" id="vaCuenta" name="cuenta">
                      <option>-</option>
                    </select>
                </div>
                <div class="form-group col-md-2">
                  <label style="text-align: left; float: left;">Tipo de pago</label>
                    <select class="custom-select mr-sm-2" name="pago">
                        <option value="CHK">Cheque</option>
                        <option value="EFT">Transferencia electrónica</option>
                     </select>
                </div>
                  <div class="form-group col-md-2">
                  <label style="text-align: left; float: left;">Banco</label>
                  <input type="text" class="form-control mr-sm-2" placeholder="Banco" name="banco">
                </div>
                <div class="form-group col-md-2">
                  <label style="text-align: left; float: left;">N° Documento</label>
                  <input type="number" class="form-control mr-sm-2" placeholder="número de documento" name="documento">
                </div>
                    <div class="form-group col-md-2">
                    <label style="text-align: left; float: left;">-</label>
                  <button type="submit" class="btn btn-primary btn-block mr-sm-2">Modificar</button>
                </div>
              </div>
          </form>
          </div>
        </div>
      </div><br>
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

