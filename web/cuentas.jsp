<%-- 
    Document   : usuarios
    Created on : 01-08-2018, 2:39:04
    Author     : Nicolas
--%>

<%@page import="cl.inacap.cdn.entities.Cuenta"%>
<%@page import="cl.inacap.cdn.entities.Homologar"%>
<%@page import="cl.inacap.cdn.entities.AnhoProyect"%>
<%@page import="cl.inacap.cdn.entities.AnhoProyect"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="cl.inacap.cdn.entities.Usuario"%>
<%@page import="java.util.List"%>
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
          <a href="#">Dashboard</a>
        </li>
        <li class="breadcrumb-item active">Tables</li>
      </ol>
      <% 
        if(request.getAttribute("errores")!=null){
            List<String> errores = (List<String>)request.getAttribute("errores");
            if(errores.size()>0){
      %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <%
                      for(String error:errores){  
                    %>
                    <p><i class="fa fa-exclamation-circle" aria-hidden="true"></i> <%=error%></p>
                    <%
                      }  
                    %>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
      <%
            }
        }
      %>
      <% 
        if(request.getAttribute("notificacion")!=null){
      %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <p><i class="fa fa-check" aria-hidden="true"></i> <%=request.getAttribute("notificacion")%></p>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
      <%
        }
        if(request.getAttribute("alerta")!=null){
      %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <p><i class="fa fa-exclamation-circle" aria-hidden="true"></i> <%=request.getAttribute("alerta")%></p>
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
            <h3>Cuentas</h3>
          </div>
        </div>
      </div>
      <br>
      <%
          if(request.getAttribute("cuentas")!=null){
              List<Cuenta> cuentas = (List<Cuenta>)request.getAttribute("cuentas");
      %>
      <div class="card">
        <div class="row card-body">
          <div class="col-md-12">
            <table id="tablaColumnas" class="table table-hover">
              <thead>
                <tr>
                  <th style="width: 10em">ID</th>
                  <th>Columna</th>
                  <th style="width: 5.8em"><button type="button" class="btn btn-primary btn-sm pull-right" data-toggle="tooltip" data-placement="top" title="Añadir" id="btn-add-cuenta"><i class="fa fa-plus"></i></button></th>
                </tr>
              </thead>
              <tbody>
                  <% 
                    for(Cuenta cuenta:cuentas){
                  %>
                  <tr>              
                    <td><%=cuenta.getId()%></td>
                    <td><%=cuenta.getNombre()%></td>
                    <td>
                        <button type="button" class="btn btn-outline-primary btn-sm btn-edit-cuenta" data-toggle="tooltip" data-placement="top" title="Modificar" value="<%=cuenta.getId()%>" nombre="<%=cuenta.getNombre()%>">
                            <i class="fa fa-pencil fa-lg" aria-hidden="true"></i>
                        </button>   
                        <button type="button" class="btn btn-outline-danger btn-sm btn-delete-cuenta" value="<%=cuenta.getId()%>" data-toggle="tooltip" data-placement="top" title="Eliminar">
                            <i class="fa fa-trash"></i>
                        </button>
                    </td>
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
        }              
    %>
      <br>
      <!-- Agregar cuenta -->
      <div id="nuevaCuenta" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="nuevaCuenta" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
              <form action="Cuenta.do" method="post">
              <input type="hidden" name="op" value="1">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Nueva Cuenta</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <div class="row">
                  <div class="col-md-12">
                      <input class="form-control" type="text" value="" name="name-cuenta" placeholder="Ingrese nombre de cuenta">
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                <button type="submit" class="btn btn-primary" id="btnNewCol">Crear Cuenta</button>
              </div>
            </form>
          </div>
        </div>
      </div>
      
      <!-- Modificar cuenta -->
      <div id="editCuenta" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="nuevaCuenta" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
              <form action="Cuenta.do" method="post">
              <input type="hidden" name="op" value="3">
              <input type="hidden" name="id_cuenta" value="" id="id-cuenta">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Modificar Cuenta</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <div class="row">
                  <div class="col-md-12">
                      <input class="form-control" type="text" value="" name="name-cuenta" placeholder="Ingrese nombre de cuenta" id="name-cuenta">
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                <button type="submit" class="btn btn-primary" id="btnNewCol">Modificar Cuenta</button>
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
    <!-- Borrar cuenta -->
    <div id="borrarCuenta" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="borrarCuenta" aria-hidden="true">
      <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">¿Desea Eliminar este Usuario?</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                        </button>
                </div>
                <div class="modal-body">
                        <p id="pModal">Esta seguro/a de que desea eliminar esta cuenta</p>
                </div>
                <div class="modal-footer">
                        <a href="#" class="btn btn-secondary" data-dismiss="modal">Cancelar</a>
                        <a href="#" id="a-delete-cuenta" class="btn btn-danger">Eliminar Cuenta</a>
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
    <script src="vendor/rut/rut.min.js"></script>
    <script src="js/main.js"></script>
  </div>
</body>

</html>
