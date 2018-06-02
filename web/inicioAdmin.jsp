<%-- 
    Document   : inicioAdmin
    Created on : 15-05-2018, 23:39:42
    Author     : Nicolas
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="cl.inacap.cdn.entities.Proyecto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Proyectos Activos</title>
  <!-- Bootstrap core CSS-->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom fonts for this template-->
  <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
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
          <a href="#">Inicio</a>
        </li>
      </ol>
      <!-- CARTA -->
      <div class="row">
        <div class="col-lg-8 col-sm-8">
          <h2>Proyectos Activos</h2>
        </div>
        <div class="col-lg-4 col-sm-4">
          <a href="Proyect.do?accion=crearProyecto" style="text-decoration: none;"><p align="center"><i class="fa fa-plus-square fa-2x"></i><br>Añadir Nuevo Proyecto</p></a>
        </div>
      </div>
      <!-- Icon Cards-->
      <div class="row">
        <%
            List<Proyecto> proyectos = (List)request.getAttribute("proyectos");
            if(proyectos.size() > 0){
                for(Proyecto proyecto:proyectos){
        %>
                    <div class="col-xl-3 col-sm-6 mb-3">
                        <div class="card text-white bg-primary o-hidden h-100">
                            <div class="card-body">
                                <div class="card-body-icon">
                                    <i class="fa fa-fw fa-university"></i>
                                </div>
                                <div class="mr-5"><% out.print(proyecto.getNombre());%></div>
                            </div>
                            <a class="card-footer text-white clearfix small z-1" href="Proyect.do?idProyect=<% out.print(proyecto.getId()); %>&accion=mostrarProyecto">
                                <span class="float-left">Ver Más</span>
                                <span class="float-right">
                                    <i class="fa fa-angle-right"></i>
                                </span>
                            </a>
                        </div>
                    </div>
        <%
                }
            }else{
                out.print("<div class='col-xl-12 col-sm-12 mb-12'>No se a encontrado ningun proyecto registrado</div>");
            }
        %>
      </div>
    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin.min.js"></script>
  </div>
</body>

</html>

