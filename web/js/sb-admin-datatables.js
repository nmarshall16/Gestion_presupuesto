// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable( {
        "language": {
            "lengthMenu": "Mostrar _MENU_",
            "zeroRecords": "No se encontró nada",
            "info": "_TOTAL_ Resultados",
            "infoEmpty": "No hay registros",
        }
    });
});
