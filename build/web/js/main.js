/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    // Funcion encargada de dar el formato rut al input rut
    $("form #rut").rut({formatOn: 'keyup', validateOn: 'keyup'}).on('rutInvalido', function(){ 
        $(this).parents(".control-group").addClass("error")
    }).on('rutValido', function(){ 
        $(this).parents(".control-group").removeClass("error")
    });
    // Funcion encargada obtener el mes del año del proyecto que se haya seleccionado y para enviarlo al respectivo serlvet 
    $(".selectAnho").on('click', function(){
        var fila = $(this).parent().parent();
        var meses = $(fila).children(".meses").children(); // Se optiene el select con los meses del año que se haya selecionado
        var tipo = $(fila).children(".tipo").children(); // Se optiene el select con los meses del año que se haya selecionado
        $(location).attr('href', 'Year.do?idAnho='+$(this).val()+'&mes='+$(meses).val()+'&tipo='+$(tipo).val()+'&op=3');
    });
    $("#uploadFileForm").ajaxForm({
       beforeSend: function(){
           $('#cargaModal').modal('show');
       },
       success: function(data){
           console.log(data);
           var datos = $.parseJSON(data);
           if(datos.error){
               $(".error").text("ERROR: "+datos.detalle);
               $('#cargaModal').modal('toggle');
               $(".alert-danger").show('slow/400/fast');
           }else{
               $('#cargaModal').modal('toggle');
               $(".alert-success").show('slow/400/fast');
               $(location).attr('href', 'Year.do?idAnho='+datos.anho+'&mes='+datos.mes+'&tipo='+datos.tipo+'&op=3');
           }
       },
       error: function(data){
           $(".error").text("ERROR: "+data);
           $(".alert-danger").show('slow/400/fast');
       }
    });
    $(".deleteGasto").click(function(){
        if(confirm("¿Seguro que quiere borrar este gasto?")){
            $("#row-"+$(this).val()).remove();
            var filas = $(".filaGasto");
            if(filas.length <= 0){
                $(location).attr('href', 'Year.do?idAnho='+$("#idAnho").val()+'&mes='+$("#mes").val()+'&op=3');
            }
            return true;
        }else{
            return false;
        }		
    });
    $(".deleteExcepcion").click(function(){
        if(confirm("¿Seguro que quiere borrar este gasto?")){
            $("#exc-"+$(this).val()).remove();
            var filas = $(".filaExcepcion");
            if(filas.length <= 0){
                $("#contentException").hide("slow/400/fast");
            }
            return true;
        }else{
            return false;
        }		
    });
    $("#usuarioForm").submit(function(){
        console.log("hola");
        if($("#inputClave").val()!==""){
            if($("#inputClave").val() !== $("#inputConfirmacion").val()){
                $("#claveError").show('slow/400/fast');
                $('body,html').animate({scrollTop : 0}, 500);
                return false;
            }
        }
    });
    $( "form" ).submit(function() {
        var filas = $(".filaExcepcion");
        var error = false;
        if(filas.length > 0){
            if($("#cuentasExc").val().length <= 0){
                error = true;
            }
        }
        if($("#cuentas").val().length <= 0){
          error = true;
        }
        if(error){
            $('body,html').animate({scrollTop : 0}, 500);
            $("#errorDiv").show("slow/400/fast");
            $("#errorJS").text(" SELECCIONE LA CUENTA A LA CUAL DESEA ASOCIAR LOS GASTOS");
            return false;
        }
    });
    
    $("#vaFuente").change(function(){
        cargarCuenta();
    });
    
    $(document).on('click', '.deleteUsuario', function(){
        var rutUsu = $(this).val();
        $("#modalEliminar").attr('href', 'Usuario.do?idUsuario='+rutUsu+'&op=5');
        $('#eliminarUsuario').modal('show'); 
    });
    
    $(document).on('click', '.deleteTipo', function(){
        var idTipo = $(this).val();
        $("#modalEliminar").attr('href', 'TipoUsu.do?idTipo='+idTipo+'&op=5');
        $('#eliminarTipo').modal('show'); 
    });
});

function cargarCuenta(){
    var fuente = parseInt($("#vaFuente option:selected").text());
    var proyecto = parseInt($("#vaIdProyecto").val());
    $.ajax({
        url : 'Validar.do',
        data : {
            op: 3,
            idProyect: proyecto,
            codFuente: fuente,
        },
        type : 'POST',
        dataType : 'json',
        success : function(data) {
            if(data['cuenta']!=null){
                $("#vaCuenta").empty();
                $("#vaCuenta").append(($('<option>', {value: data['cuenta'], text: data['cuenta']})));
            }
            /*
            $.each(data, function(i, item){
                $("#datos"+(contarDatosBancarios()-1)).children('.banco').children().append(($('<option>', {value: i, text: item})));
            });
            */
        },
        error : function(xhr, status, detalle) {
            /*
            $("#datos"+(contarDatosBancarios()-1)).children('.banco').children().append($('<option value="otro">*** Agregar Cuenta ***</option>'));
            */
        }
    });
}

