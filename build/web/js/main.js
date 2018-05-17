/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    $("form #rut").rut({formatOn: 'keyup', validateOn: 'keyup'}).on('rutInvalido', function(){ 
        $(this).parents(".control-group").addClass("error")
    }).on('rutValido', function(){ 
        $(this).parents(".control-group").removeClass("error")
    });
});


