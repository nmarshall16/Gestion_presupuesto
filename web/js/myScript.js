/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Contar cantidad de etiquetas div dentrode #datosBancarios
function contarDatosBancarios(){
	var cantidad = $('#datosBancarios > div').length;
	return cantidad+1;
}

function agregarDatosBancarios(){
	$('#datosBancarios').append(
		"<div id='datos"+contarDatosBancarios()+"' class='form-row'>"
			+"<div class='form-group col-md-4 banco'>"
				+"<select type='text' class='form-control' name='banco'>"
					+"<option value='0' disabled selected>-- Seleccione Banco --</option>"
				+"</select>"
			+"</div>"
			+"<div class='form-group col-md-3 cuenta'>"
				+"<select type='text' class='form-control' name='cuenta'>"
					+"<option value='0' disabled selected>-- Seleccione Cuenta --</option>"
				+"</select>"
			+"</div>"
			+"<div class='form-group col-md-4 fuente'>"
				+"<select type='text' class='form-control' name='fuente'>"
					+"<option value='0' disabled selected>-- Seleccione Fte. De Financiamiento --</option>"
				+"</select>"
			+"</div>"
			+"<div class='col-md-1 pt-1'>"
				+"<button type='button' class='btn btn-danger btn-sm' onclick='borrarDatosBancarios()'><i class='fa fa-trash'></i></button>"
			+"</div>"
			+"<input name='idDivDato' type='hidden' value='datos"+contarDatosBancarios()+"' class='form-row'>"
		+"</div>"
	);
	changeCant(contarDatosBancarios());
	getBancos();
};

function getBancos(){
	$.ajax({
		url : 'Proyect.do',
		data : {
			accion:'getBancos',
		},
		type : 'POST',
		dataType : 'json',
		success : function(data) {
			$.each(data, function(i, item){
				$("#datos"+(contarDatosBancarios()-1)).children('.banco').children().append(($('<option>', {value: i, text: item})));
			});
		},
		error : function(xhr, status, detalle) {
			$("#datos"+(contarDatosBancarios()-1)).children('.banco').children().append($('<option value="otro">*** Agregar Cuenta ***</option>'));
		}
	});
}

function borrarDatosBancarios(){
	var id = contarDatosBancarios()-1;
	$('#datos'+id).remove();
}

function changeCant(cant){
	$("input[name*='cantDatos']").val(cant-1);
}

$(function() {
	$('#switch').change(function() {
		if ($(this).prop('checked')) {
			$(':input.lock').each(function () {
				$(this).removeClass('lock');
			});
		}else{
			$(':input.form-control').each(function () {
				$(this).addClass('lock');
			});
		}
	});
});
