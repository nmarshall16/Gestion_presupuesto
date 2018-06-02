/* 
 Created on : Jul 17, 2017, 12:31:12 AM
 Author     : Atta-Ur-Rehman Shah (http://attacomsian.com)
 */
$(function () {
    //default date range picker
    $('#daterange').daterangepicker({
        "autoApply": false,
        "locale": {
            "format": "DD/MM/YYYY",
            "separator": " - ",
            "applyLabel": "Aplicar",
            "cancelLabel": "Cancelar",
            "daysOfWeek": [
                "Do",
                "Lu",
                "Ma",
                "Mi",
                "Ju",
                "Vi",
                "Sa"
            ],
            "monthNames": [
                "Enero",
                "Febrero",
                "Marzo",
                "Abril",
                "Mayo",
                "Junio",
                "Julio",
                "Augosto",
                "Septiembre",
                "Octubre",
                "Noviembre",
                "Diciembre"
            ],
            "firstDay": 1
        }
    });

    //date time picker
    $('#datetime').daterangepicker({
        timePicker: true,
        timePickerIncrement: 30,
        locale: {
            format: 'MM/DD/YYYY h:mm A'
        }
    });

    //single date
    $('#date').daterangepicker({
        singleDatePicker: true
    });

    $('#fechaInicio').daterangepicker({
        singleDatePicker: true,
        "locale": {
            "format": "DD/MM/YYYY",
            "separator": " - ",
            "applyLabel": "Aplicar",
            "cancelLabel": "Cancelar",
            "daysOfWeek": [
                "Do",
                "Lu",
                "Ma",
                "Mi",
                "Ju",
                "Vi",
                "Sa"
            ],
            "monthNames": [
                "Enero",
                "Febrero",
                "Marzo",
                "Abril",
                "Mayo",
                "Junio",
                "Julio",
                "Augosto",
                "Septiembre",
                "Octubre",
                "Noviembre",
                "Diciembre"
            ],
            "firstDay": 1
        }
    });

    $('#fechaTermino').daterangepicker({
        singleDatePicker: true,
        "locale": {
            "format": "DD/MM/YYYY",
            "separator": " - ",
            "applyLabel": "Aplicar",
            "cancelLabel": "Cancelar",
            "daysOfWeek": [
                "Do",
                "Lu",
                "Ma",
                "Mi",
                "Ju",
                "Vi",
                "Sa"
            ],
            "monthNames": [
                "Enero",
                "Febrero",
                "Marzo",
                "Abril",
                "Mayo",
                "Junio",
                "Julio",
                "Augosto",
                "Septiembre",
                "Octubre",
                "Noviembre",
                "Diciembre"
            ],
            "firstDay": 1
        }
    });
});