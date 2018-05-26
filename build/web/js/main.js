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
        $(location).attr('href', 'Proyect.do?idAnho='+$(this).val()+'&mes='+$(meses).val());
    });
});


