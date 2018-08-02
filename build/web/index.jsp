<%-- 
    Document   : index
    Created on : 15-05-2018, 19:14:48
    Author     : Nicolas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Inicio</title>
        <!-- Bootstrap core CSS-->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom fonts for this template-->
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <!-- Custom styles for this template-->
        <link href="css/sb-admin.css" rel="stylesheet">
    </head>
    <body class="bg-dark">
        <div class="container">
            <div class="card card-login mx-auto mt-5">
                <div class="card-header" align="center">CDN INACAP</div>
                <div class="card-body">
                    <% 
                        if(request.getAttribute("alerta")!=null){
                    %>
                            <div class="alert alert-danger" role="alert">
                               <i class="fa fa-exclamation-circle" aria-hidden="true"></i> <%=request.getAttribute("alerta")%>
                            </div>
                    <%
                        }
                    %>
                    <form action="Login.do" method="post">
                        <div class="form-group">
                            <input class="form-control" type="text" placeholder="Rut" name="rut" id="rut">
                        </div>
                        <div class="form-group">
                            <input class="form-control" type="password" placeholder="Contraseña" name="clave">
                        </div>
                        <div class="form-group">
                            <div class="form-check">
                                <label class="form-check-label">
                                <input class="form-check-input" type="checkbox"> Recordar Contraseña</label>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary btn-block">Iniciar Sesión</button>
                    </form>
                </div>
            </div>
        </div>
        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="vendor/rut/rut.min.js"></script>
        <script src="js/main.js"></script>
        <!-- Core plugin JavaScript-->
    </body>
</html>
