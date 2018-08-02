<%-- 
    Document   : usuarios
    Created on : 01-08-2018, 2:39:04
    Author     : Nicolas
--%>

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
    <a class="navbar-brand" href="index.html">CDN INACAP</a>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Inicio">
          <a class="nav-link" href="#">
            <i class="fa fa-fw fa-home"></i>
            <span class="nav-link-text">Inicio</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Proyectos Eliminados">
          <a class="nav-link" href="#">
            <i class="fa fa-fw fa-archive"></i>
            <span class="nav-link-text">Proyectos Eliminados</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tipos de usuarios">
          <a class="nav-link" href="#">
            <i class="fa fa-fw fa-sitemap"></i>
            <span class="nav-link-text">Tipos de usuarios</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Usuarios">
          <a class="nav-link" href="#">
            <i class="fa fa-fw fa-users"></i>
            <span class="nav-link-text">Usuarios</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Cerrar Sesión">
          <a class="nav-link" href="#">
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
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#">Dashboard</a>
        </li>
        <li class="breadcrumb-item active">Tables</li>
      </ol>
      <% 
        if(request.getAttribute("notificacion")!=null){
      %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <p><i class="fa fa-exclamation-circle" aria-hidden="true"></i> <%=request.getAttribute("notificacion")%></p>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
      <%
        }
      %>
      <div class="card">
        <div class="row card-body">
          <div class="col-md-10">
            <h3>Usuarios</h3>
            <label>Administrar usuarios del sistema</label>
          </div>
          <div class="col-md-2">
            <a href="Usuario.do?op=2" style="text-decoration: none;"><p align="center"><i class="fa fa-plus-square fa-2x"></i><br>Añadir Usuario</p></a>
          </div>
        </div>
      </div>
      <br>
      <!-- Example DataTables Card-->
      <div class="card mb-3">
        <div class="card-header">
          <i class="fa fa-table"></i> Usuarios Registrados</div>
        <div class="card-body">
          <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
              <thead>
                <tr>
                  <th>Nombre</th>
                  <th>Apellido</th>
                  <th>Correo Electronico</th>
                  <th>Tipo de usuario</th>
                  <th></th>
                </tr>
              </thead>
              <tfoot>
                <tr>
                  <th>Nombre</th>
                  <th>Apellido</th>
                  <th>Rut</th>
                  <th>Tipo de usuario</th>
                  <th></th>
                </tr>
              </tfoot>
              <tbody>
            <%
                if(request.getAttribute("usuarios")!=null){
                    List<Usuario> usuarios = (List<Usuario>)request.getAttribute("usuarios");
                    if(usuarios.size()>0){
                        for(Usuario usuario:usuarios){
            %>
                        <tr>
                          <td><%=usuario.getNombre()%></td>
                          <td><%=usuario.getApellido()%></td>
                          <td><%=usuario.getRut()%>-<%=usuario.getDv()%></td>
                          <td><%=usuario.getTipoUsuarioId().getNombre()%></td>
                          <td width="100">
                              <a href="Usuario.do?idUsuario=<%=usuario.getRut()%>&op=2">
                                <button type="button" class="btn btn-outline-primary" data-toggle="tooltip" data-placement="top" title="Modificar">
                                    <i class="fa fa-pencil fa-lg" aria-hidden="true"></i>
                                </button>   
                              </a>
                              <button type="button" class="btn btn-outline-danger deleteUsuario" data-toggle="tooltip" data-placement="top" title="Eliminar" value="<%=usuario.getRut()%>">
                                    <i class="fa fa-trash fa-lg" aria-hidden="true"></i>
                              </button>
                          </td>
                        </tr>
            <%
                        }
                    }
                }
            %>
              </tbody>
            </table>
          </div>
        </div>
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
    <div class="modal fade" id="eliminarUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">¿Desea Eliminar este Usuario?</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">×</span>
                        </button>
                </div>
                <div class="modal-body">
                        <p id="pModal">Esta seguro/a de que desea eliminar este usuario</p>
                </div>
                <div class="modal-footer">
                        <a href="#" class="btn btn-secondary" data-dismiss="modal">Cancelar</a>
                        <a href="#" id="modalEliminar" class="btn btn-danger">Eliminar Usuario</a>
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
