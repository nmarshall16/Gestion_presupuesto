<%-- 
    Document   : presupuesto
    Created on : 17-07-2018, 19:19:30
    Author     : dell
--%>

<%@page import="cl.inacap.cdn.entities.Homologar"%>
<%@page import="cl.inacap.cdn.entities.AnhoProyect"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="cl.inacap.cdn.entities.Presupuesto"%>
<%@page import="cl.inacap.cdn.entities.FuenteF"%>
<%@page import="cl.inacap.cdn.entities.Cuenta"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
	List<Cuenta> cuentas = (List)request.getAttribute("ctas");
	List<FuenteF> fuentes = (List)request.getAttribute("fuentes");
	List<Presupuesto> presupuestos = (List)request.getAttribute("presupuestos");
%>

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
        <li class="breadcrumb-item">
          <a href="Proyect.do">Inicio</a>
        </li>
        <li class="breadcrumb-item"><a href="Proyect.do">Proyecto</a></li>
        <li class="breadcrumb-item active">Presupuesto</li>
      </ol>
      <div class="card">
        <div class="row card-body">
          <div class="col-md-12">
            <h3>Presupuesto General</h3>
          </div>
        </div>
      </div>
      <br>
      <div class="card">
        <div class="card-header">
          <i class="fa fa-table"></i> Información del Usuario
        </div>
        <div class="row card-body">
          <div class="col-md-12">
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
                  <hr>
              <div class="form-row">
                <div class="form-group col-md-11">
                    <a href="Proyect.do">
                        <button type="button" class="btn btn-outline-primary"><i class="fa fa-times fa-lg"></i><br>Cancelar</button>
                    </a>
                </div>
                <div class="form-group col-md-1">
                  <button type="submit" class="btn btn-outline-primary"><i class="fa fa-floppy-o fa-lg"></i><br>Guardar</button>
                </div>
              </div>
          </div>
                  <br><br>
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

