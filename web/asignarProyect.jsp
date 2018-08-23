<%-- 
    Document   : newUsuario
    Created on : 01-08-2018, 16:32:23
    Author     : Nicolas
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="cl.inacap.cdn.entities.Proyecto"%>
<%@page import="cl.inacap.cdn.entities.Homologar"%>
<%@page import="cl.inacap.cdn.entities.AnhoProyect"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="cl.inacap.cdn.entities.Permiso"%>
<%@page import="cl.inacap.cdn.entities.Usuario"%>
<%@page import="cl.inacap.cdn.entities.TipoUsuario"%>
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
      <div class="alert alert-danger alert-dismissible fade show" role="alert" id="claveError" style="display: none;">
          <p>Las contraseñas no coinciden por favor valide de que ambas contraseñas sean iguales</p>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <% 
        if(request.getAttribute("alerta")!=null){
      %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <% 
                ArrayList<String> alertas = (ArrayList<String>)request.getAttribute("alerta");
                for(String alerta:alertas){
            %>
                <p><i class="fa fa-exclamation-circle" aria-hidden="true"></i> <%=alerta%></p>
            <%
                }
            %>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
      <%
        }
        if(request.getAttribute("usuarios")!=null && request.getAttribute("proyectos")!=null){
      %>
      <div class="card">
        <div class="row card-body">
          <div class="col-md-12">
              <h3>Asignar Proyecto</h3>
          </div>
        </div>
      </div>
      <br>
      <div class="card">
        <div class="row card-body">
          <div class="col-md-12">
              <form action="Usuario.do" method="post">
                <input type="hidden" name="op" value="8">
                <input type="hidden" id="idProyect" value="" name="id_proyecto">
                <div class="row">
                  <div class="col-5">
                    <div class="card">
                      <div class="card-header">
                        <i class="fa fa-fw fa-university"></i> Proyectos
                      </div>
                      <div class="list-group">
                      <%
                          List<Proyecto> proyectos = (List<Proyecto>)request.getAttribute("proyectos");
                          if(proyectos.size()>0){
                              for(Proyecto proyecto:proyectos){  
                      %>

                                  <button type="button" class="list-group-item list-group-item-action btn-proyectos" value="<%=proyecto.getId()%>">
                                      <%=proyecto.getNombre()%>
                                  </button>
                      <%
                              }
                          }
                      %>
                      </div>
                    </div>
                  </div>
                  <div class="col-1">
                  </div>
                  <div class="col-6">
                      <div class="card">
                          <div class="card-header">
                          <i class="fa fa-fw fa-users"></i> Usuarios
                          </div>
                          <ul class="list-group list-group-flush">
                              <div style="overflow-y:auto;  max-height: 300px;">
                                  <%
                                      List<Usuario> usuarios = (List<Usuario>)request.getAttribute("usuarios");
                                      if(usuarios.size()>0){
                                          for(Usuario usuario:usuarios){  
                                  %>
                                  <li class="list-group-item">
                                      <div class="custom-control custom-checkbox">
                                          <input type="checkbox" class="custom-control-input btn-usuarios" id="usuario-<%=usuario.getRut()%>" name="usuarios" value="<%=usuario.getRut()%>">
                                          <label class="custom-control-label" for="usuario-<%=usuario.getRut()%>"><%=usuario.getNombre()%> <%=usuario.getApellido()%> <small class="text-muted">(<%=usuario.getRut()%>-<%=usuario.getDv()%>)</small></label>
                                      </div>
                                  </li>
                                  <%
                                          }
                                      }
                                  %>
                              </div>
                          </ul>
                      </div>
                  </div>
                </div>
                <br><hr>
                <div class="form-row">
                  <div class="form-group col-md-11">
                    <button type="button" class="btn btn-outline-primary"><i class="fa fa-times fa-lg"></i><br>Cancelar</button>
                  </div>
                  <div class="form-group col-md-1">
                    <button type="submit" class="btn btn-outline-primary"><i class="fa fa-floppy-o fa-lg"></i><br>Guardar</button>
                  </div>
                </div>
            </form>
          </div>
        </div>
      </div>
      <br>
      <%
        }
      %>
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
    <script>
    // Example starter JavaScript for disabling form submissions if there are invalid fields
    (function() {
      'use strict';
      window.addEventListener('load', function() {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
          form.addEventListener('submit', function(event) {
            if (form.checkValidity() === false) {
              event.preventDefault();
              event.stopPropagation();
            }
            form.classList.add('was-validated');
          }, false);
        });
      }, false);
    })();
    </script>
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

