<%-- 
    Document   : newUsuario
    Created on : 01-08-2018, 16:32:23
    Author     : Nicolas
--%>

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
    <%
    if(request.getAttribute("op")!=null && request.getAttribute("permisos")!=null){
        int op = (Integer)request.getAttribute("op");
        List<Permiso> permisos = (List<Permiso>)request.getAttribute("permisos");
    %>
    <div class="container-fluid">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="Proyect.do">Inicio</a></li>
        <li class="breadcrumb-item"><a href="TipoUsu.do?op=1">Tipo de Usuarios</a></li>
        <li class="breadcrumb-item active">Informacion Tipo Usuario</li>
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
              <h3>
                <%
                    switch(op){
                        case 3: out.print("Añadir Tipo de Usuario"); break;
                        case 4: out.print("Modificar Tipo de Usuario"); break;
                    }
                %>
              </h3>
          </div>
        </div>
      </div>
      <br>
      <div class="card">
        <div class="card-header">
          <i class="fa fa-table"></i> Información Tipo de Usuario
        </div>
        <div class="row card-body">
          <div class="col-md-12">
              <form method="post" action="TipoUsu.do" class="needs-validation" novalidate id="usuarioForm">
                <input type="hidden" name="op" value="<%=op%>">
                <%
                    switch(op){
                        case 3: 
                %>
                        <div class="form-group">
                            <label for="inputNombre">Nombre</label>
                            <input type="text" class="form-control" id="inputNombre" placeholder="Nombre" name="nombre" required>
                        </div>
                        <hr>  
                        <label>Permisos</label>
                        <div class="form-row">
                            <%
                              for(Permiso permiso:permisos){
                            %>
                                <div class="col-md-3 mb-3">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="permiso-<%=permiso.getId()%>" name="permisos" value="<%=permiso.getId()%>">
                                        <label class="custom-control-label" for="permiso-<%=permiso.getId()%>"><%=permiso.getNombre()%></label>
                                    </div>
                                </div>
                            <%
                              }  
                            %>
                        </div>
                        <hr> 
                <%
                        break;
                        case 4:
                        TipoUsuario tipo = (TipoUsuario)request.getAttribute("tipo");
                %>
                        <input type="hidden" name="idTipo" value="<%=tipo.getId()%>">
                        <div class="form-group">
                            <label for="inputNombre">Nombre</label>
                            <input type="text" class="form-control" id="inputNombre" placeholder="Nombre" name="nombre" value="<%=tipo.getNombre()%>" required>
                        </div>
                        <hr>  
                        <label>Permisos</label>
                        <div class="form-row">
                        <%
                            boolean valida = false;
                              for(Permiso permiso:permisos){
                                  valida = false;
                                  for(Permiso permi:tipo.getPermisoList()){
                                      if(permi.equals(permiso)){
                                          valida = true;
                                      }
                                  }
                                  if(valida){
                        %>
                                    <div class="col-md-3 mb-3">
                                        <div class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" id="permiso-<%=permiso.getId()%>" name="permisos" value="<%=permiso.getId()%>" checked>
                                            <label class="custom-control-label" for="permiso-<%=permiso.getId()%>"><%=permiso.getNombre()%></label>
                                        </div>
                                    </div>
                        <%
                                  }else{
                                  
                        %>
                                    <div class="col-md-3 mb-3">
                                        <div class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" id="permiso-<%=permiso.getId()%>" name="permisos" value="<%=permiso.getId()%>">
                                            <label class="custom-control-label" for="permiso-<%=permiso.getId()%>"><%=permiso.getNombre()%></label>
                                        </div>
                                    </div>
                        <%
                                  }
                        %>
                            <%
                              }  
                            %>
                        </div>
                        <hr> 
                <%
                        break;
                    }
                %>
              <div class="form-row">
                <div class="form-group col-md-11">
                    <a href="TipoUsu.do?op=1">
                        <button type="button" class="btn btn-outline-primary"><i class="fa fa-times fa-lg"></i><br>Cancelar</button>
                    </a>
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
    <%
      }  
    %>
  </div>
</body>
</html>

