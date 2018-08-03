<%-- 
    Document   : homologar
    Created on : 22-07-2018, 14:37:20
    Author     : Nicolas
--%>

<%@page import="cl.inacap.cdn.entities.Homologar"%>
<%@page import="cl.inacap.cdn.entities.AnhoProyect"%>
<%@page import="java.math.BigDecimal"%>
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
            <a href="Year.do?idAnho=<%=request.getAttribute("anho")%>&mes=<%=request.getAttribute("mes")%>&tipo=<%=request.getAttribute("tipo")%>&op=3">Gastos</a>
        </li>
        <li class="breadcrumb-item active">Homologar</li>
      </ol>
      <div class="alert alert-danger alert-dismissible fade show" role="alert" id="errorDiv" style="display:none;">
          <p style="font-size: 12px;"><i class="fa fa-times-circle" aria-hidden="true"></i><strong id="errorJS"></strong></p>
      </div>
      <% 
        ArrayList<String> advertencias = (ArrayList<String>)request.getAttribute("advertencias");
        char tipo = request.getAttribute("tipo").toString().charAt(0);
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
      <div class="card">
        <div class="row card-body">
          <div class="col-md-12">
            <h3>Homologar</h3>
            <label>Seleccione la cuenta a la cual estan asociados estos gastos</label>
          </div>
        </div>
      </div><br>
      <div class="card">
        <div class="row card-body">
          <div class="col-md-12">
              <form method="post" action="Gasto.do" id="homologar">
                <input type="hidden" name="op" value="homologar">
                <input type="hidden" name="mes" value="<%=request.getAttribute("mes")%>" id="mes">
                <input type="hidden" name="idAnho" value="<%=request.getAttribute("anho")%>" id="idAnho">
                <input type="hidden" name="tipo" value="<%=tipo%>" id="tipoG">
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

                                if(gasto.getIdCompra()!=null && !gasto.getIdCompra().equals(new BigInteger("0")) && tipo!='A'){
                                    out.print(gasto.getIdCompra());
                                }else{
                                    out.print("-");
                                }
                              %>
                              </label>
                              <input type="hidden" name="gastosExc" value="<% out.print(gasto.getId()); %>" >
                              </th>
                              <td><label style="font-size: 13px;"><%=(tipo != 'A')?gasto.getGastoId().getNombre().toUpperCase():gasto.getDescripcion()%></label></td>
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
                                if(gasto.getIdCompra()!=null && !gasto.getIdCompra().equals(new BigInteger("0")) && tipo!='A'){
                                    out.print(gasto.getIdCompra());
                                }else{
                                    out.print("-");
                                }
                              %>
                              </label>
                              <input type="hidden" value="<% out.print(gasto.getId()); %>" name="gastos">
                              </th>
                              <td><label style="font-size: 13px;"><%=(tipo != 'A')?gasto.getGastoId().getNombre().toUpperCase():gasto.getDescripcion()%></label></td>
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
                <div class="form-row">
                <div class="form-group col-md-11">
                    <a href="Year.do?idAnho=<%=request.getAttribute("anho")%>&mes=<%=request.getAttribute("mes")%>&tipo=<%=tipo%>&op=3">
                        <button type="button" class="btn btn-outline-primary"><i class="fa fa-times fa-lg"></i><br>Cancelar</button>
                    </a>
                </div>
              </div>
              </form>
          </div>
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
