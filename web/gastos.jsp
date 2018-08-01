<%-- 
    Document   : gastos
    Created on : 21-07-2018, 1:15:36
    Author     : Nicolas
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="cl.inacap.cdn.entities.AnhoProyect"%>
<%@page import="cl.inacap.cdn.entities.Homologar"%>
<%@page import="java.math.BigInteger"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
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
</head>

<body class="fixed-nav sticky-footer bg-dark" id="page-top">
  
  <!-- Navigation-->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    <a class="navbar-brand" href="#">CDN INACAP</a>
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
            <a class="nav-link" href="Validar.do?anho=<%=request.getAttribute("anho")%>&mes=<%=request.getAttribute("mes")%>&op=1">
            <i class="fa fa-exclamation-triangle"></i>
            <span class="nav-link-text">
                Verificar Cuenta 
                <span class="badge badge-primary badge-pill">
                    <%
                    BigDecimal bd = new BigDecimal(request.getAttribute("anho").toString());
                    char tipo = request.getAttribute("tipo").toString().charAt(0);
                    Locale cl = new Locale("es", "CL");
                    NumberFormat formatter = NumberFormat.getCurrencyInstance(cl);
                    SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                    int p = Homologar.getGastosP(AnhoProyect.findById(bd.intValue()), request.getAttribute("mes").toString());
                    out.print(p);
                    %>
                </span>
            </span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Components">
          <a class="nav-link" href="PDF?mes=<%=request.getAttribute("mes").toString()%>&anho=<%=request.getAttribute("anho")%>">
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
      <div class="row">
		  <div class="col-md-8">
			  <h3>Mayor Contable Mensual</h3>
		  </div>
		  <div class="col-md-4">
			  <h4>Mes De Proyecto : <strong><%=request.getAttribute("mes")%></strong></h4>
		  </div>
      </div>
      <p>Estado de documento : <%=((Boolean)request.getAttribute("estado"))?"<strong class='text-success'>Terminado</strong>":"<strong class='text-danger'>En Proceso</strong>"%></p>
      <br>
      <%
        if(request.getAttribute("error")!=null){
            ArrayList<String> errores = (ArrayList<String>)request.getAttribute("error");
            if(errores.size()>0){
      %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <c:forEach items="${requestScope.error}" var="error">
                    <p>${error}</p>
                </c:forEach>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
            </div>
      <%
            }
        }
      %>
      <!-- Example DataTables Card-->
      <div class="card mb-3">
        <div class="card-header">
          <i class="fa fa-table"></i>
        <%
            switch(tipo){
                case 'G':
                    out.print("Gastos Mensuales");
                break;

                case 'A':
                    out.print("Aportes Mensuales");
                break;
            }
        %>
       </div>
        <div class="card-body">
          <div class="table-responsive">
            <form method="post" action="Gasto.do">
              <input type="hidden" value="marcarGastos" name="op">
              <input type="hidden" name="mes" value="<%=request.getAttribute("mes")%>">
              <input type="hidden" name="idAnho" value="<%=request.getAttribute("anho")%>">
              <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
              <%
                switch(tipo){
                    case 'G':
              %>
                    <thead align="center">
                        <tr>
                          <th></th>
                          <th>ID</th>
                          <th>Nombre</th>
                          <th>Importe</th>
                          <th>Fecha Contable</th>
                          <th>Numero Factura</th>
                          <th>Rut Proveedor</th>
                          <th>Nombre Proveedor</th>
                          <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody align="center">
                        <%
                        List<GastoMes> gastos = (List<GastoMes>)request.getAttribute("gastos");
                        for(GastoMes gasto:gastos){
                        %>
                      <tr>
                          <td><input type="checkbox" value="<%=gasto.getId()%>" name="gastos"></td>
                          <td><%=(!gasto.getIdCompra().equals(new BigInteger("0")))?(gasto.getIdCompra()):("-")%></td>
                          <td><%=(gasto.getGastoId().getNombre().toUpperCase())%></td>
                          <td><%=formatter.format(gasto.getImporte()).substring(2)%></td>
                          <td><%=formateador.format(gasto.getFecha())%></td>
                          <td><%=(!gasto.getNumFac().equals(new BigInteger("0")))?(gasto.getIdCompra()):("-")%></td>
                          <td><%=(gasto.getRutProvedor()!= null)?(gasto.getRutProvedor()):("-")%></td>
                          <td><%=(gasto.getNombreProvedor()!= null)?(gasto.getNombreProvedor()):("-")%></td>
                          <td>
                          <%=(gasto.getStatus() != 'P')?"<i class='fa fa-check fa-2x text-success'></i>":"<i class='fa fa-times fa-2x text-danger'></i>"%></td>
                      </tr>
                      <% 
                       }
                      %>
                    </tbody>
              <%
                    break;

                    case 'A':
              %>
                    <thead align="center">
                        <tr>
                          <th></th>
                          <th>Fecha del asiento</th>
                          <th>Descripción linea</th>
                          <th>Importe</th>
                          <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody align="center">
                        <%
                        List<GastoMes> aportes = (List<GastoMes>)request.getAttribute("gastos");
                        for(GastoMes gasto:aportes){
                        %>
                      <tr>
                          <td><input type="checkbox" value="<%=gasto.getId()%>" name="gastos"></td>
                          <td><%=formateador.format(gasto.getFecha())%></td>
                          <td><%=(gasto.getDescripcion())%></td>
                          <td><%=formatter.format(gasto.getImporte()).substring(2)%></td>
                          <td>
                            <%=(gasto.getStatus() != 'P')?"<i class='fa fa-check fa-2x text-success'></i>":"<i class='fa fa-times fa-2x text-danger'></i>"%>
                          </td>
                      </tr>
                      <% 
                       }
                      %>
                    </tbody>
              <%
                    break;
                }
              %>
            </table>
            <br>
            <button type="submit" class="btn btn-primary" style="margin-left: 1%;">Homologar</button>
            </form>
          </div>
        </div>
        <div class="card-footer small text-muted"></div>
      </div>
      <div class="col-md-1" align="center">
          <a href="#" style="text-decoration: none;">
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

  </div>
</body>

</html>

