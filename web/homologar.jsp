<%-- 
    Document   : homologar
    Created on : 22-07-2018, 14:37:20
    Author     : Nicolas
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="cl.inacap.cdn.entities.Cuenta"%>
<%@page import="java.math.BigInteger"%>
<%@page import="java.util.List"%>
<%@page import="cl.inacap.cdn.entities.GastoMes"%>
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
      <h3>Homologar</h3>
      <p>Seleccione la cuenta a la cual estan asociados estos gastos</p>
      <div class="alert alert-danger alert-dismissible fade show" role="alert" id="errorDiv" style="display:none;">
          <p style="font-size: 12px;"><i class="fa fa-times-circle" aria-hidden="true"></i><strong id="errorJS"></strong></p>
      </div>
      <% 
        ArrayList<String> advertencias = (ArrayList<String>)request.getAttribute("advertencias");
        if(advertencias.size()>0){
      %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%
                for(String advertencia:advertencias){
                %>
                <p style="font-size: 12px;"><i class="fa fa-exclamation-circle" aria-hidden="true"></i><strong> <% out.print(advertencia.toUpperCase()); %></strong></p>
                <%
                }
                %>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
      <%
        }
      %>
      <form method="post" action="Gasto.do" id="homologar">
        <input type="hidden" name="op" value="homologar">
        <input type="hidden" name="mes" value="<%=request.getAttribute("mes")%>" id="mes">
        <input type="hidden" name="idAnho" value="<%=request.getAttribute("anho")%>" id="idAnho">
        <div class="row" id="contentException">
          <%
          List<GastoMes> excepciones = (List<GastoMes>)request.getAttribute("excepciones");
          if(excepciones.size()>0){   
          %>
          <div class="col-md-5">
            <div class="card">
              <div class="card-header" align="center">
                <strong>GASTOS EXCEPCIONALES</strong>
              </div>
              <table class="table">
                <thead align="center">
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nombre</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody align="center">
                  <%
                    for(GastoMes gasto:excepciones){
                  %>
                  <tr id="exc-<% out.print(gasto.getId()); %>" class="filaExcepcion">
                      <th scope="row">
                      <label style="font-size: 13px;">
                      <% 
                        if(!gasto.getIdCompra().equals(new BigInteger("0"))){
                            out.print(gasto.getIdCompra());
                        }else{
                            out.print("-");
                        }
                      %>
                      </label>
                      <input type="hidden" name="gastosExc" value="<% out.print(gasto.getId()); %>" >
                      </th>
                      <td><label style="font-size: 13px;"><% out.print(gasto.getGastoId().getNombre().toUpperCase()); %></label></td>
                    <td><button type="button" class="btn btn-outline-primary deleteExcepcion" value="<% out.print(gasto.getId()); %>"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
                  </tr>
                  <%
                    }
                  %>
                </tbody>
              </table>
            </div>
          </div>
          <div class="col-md-2" align="center">
          </div>
          <div class="col-md-5">
            <div class="card">
              <div class="card-header" align="center">
                <strong>NOMBRE DE CUENTAS</strong>
              </div>
                <select class="custom-select" multiple size="7" name="cuentasExc" id="cuentasExc">
                  <%
                  List<Cuenta> cuentas = (List<Cuenta>)request.getAttribute("cuentas");
                  for(Cuenta cuenta:cuentas){
                  %>
                  <option value="<% out.print(cuenta.getId()); %>"><% out.print(cuenta.getNombre()); %></option>
                <%
                 }
                %>
              </select>
            </div>
            <br>
          </div>
          <%
          } // Terminan los gasto excepcionales
          %>
          </div>
          <div class="row">
          <div class="col-md-5">
            <div class="card">
              <div class="card-header" align="center">
                <strong>GASTOS SELECCIONADOS</strong>
              </div>
              <table class="table">
                <thead align="center">
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nombre</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody align="center">
                  <%
                    List<GastoMes> gastos = (List<GastoMes>)request.getAttribute("gastos");
                    for(GastoMes gasto:gastos){
                  %>
                  <tr id="row-<% out.print(gasto.getId()); %>" class="filaGasto">
                      <th scope="row">
                      <label style="font-size: 13px;">
                      <% 
                        if(!gasto.getIdCompra().equals(new BigInteger("0"))){
                            out.print(gasto.getIdCompra());
                        }else{
                            out.print("-");
                        }
                      %>
                      </label>
                      <input type="hidden" value="<% out.print(gasto.getId()); %>" name="gastos">
                      </th>
                      <td><label style="font-size: 13px;"><% out.print(gasto.getGastoId().getNombre().toUpperCase()); %></label></td>
                      <td><button type="button" class="btn btn-outline-primary deleteGasto" value="<% out.print(gasto.getId()); %>"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
                  </tr>
                  <%
                    }
                  %>
                </tbody>
              </table>
            </div>
          </div>
          <div class="col-md-2" align="center">
            <button type="submit" class="btn btn-outline-primary"><i class="fa fa-random fa-3x"></i><br><strong>Asociar</strong></button>
          </div>
          <div class="col-md-5">
            <div class="card">
              <div class="card-header" align="center">
                <strong>NOMBRE DE CUENTAS</strong>
              </div>
                <select class="custom-select" multiple size="7" name="cuentas" id="cuentas">
                  <%
                  List<Cuenta> cuentas = (List<Cuenta>)request.getAttribute("cuentas");
                  for(Cuenta cuenta:cuentas){
                  %>
                  <option value="<% out.print(cuenta.getId()); %>"><% out.print(cuenta.getNombre()); %></option>
                <%
                 }
                %>
              </select>
            </div>
          </div>
        </div>
      </form>
      <br>
      <div class="row">
        <div class="col-lg-2" align="center">
          <a href="#" style="text-decoration: none;">
          <i class="fa fa-reply-all fa-2x"></i><br><strong>Volver</strong>
          </a>
      </div>
      <div class="col-lg-8">
      </div>
      <div class="col-lg-2" align="center">
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
    <script src="vendor/jquery/jquery.form.min.js"></script>
    <script src="vendor/rut/rut.min.js"></script>
    <script src="js/main.js"></script>
  </div>
</body>
</html>
