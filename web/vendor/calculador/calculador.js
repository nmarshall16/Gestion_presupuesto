/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$( document ).ready(function (){
    
    loadInputs();
    
    $($('#cuentas tbody tr td input[name=sercotec]')).change(function (){
        calculateFF('sercotec');
    });
    
    $($('#cuentas tbody tr td input[name=inacap]')).change(function (){
        calculateFF('inacap');
    });
    
    $($('#cuentas tbody tr td input[name=pecuniarios]')).change(function (){
        calculateFF('pecuniarios');
    });
    
});

function loadInputs(){
    for (var i = 0; i<= $("#cuentas tbody tr td input").length-1 ; i++ ) {
        var ctas = $("#cuentas tbody tr td input");
        if(ctas[i].value > 0){
            calculateFF('sercotec');
            calculateFF('inacap');
            calculateFF('pecuniarios');
        }
    }
}

function calculateFF(ff){
       
    var ctas   = $('#cuentas tbody tr');
    var inputs = $("#cuentas tbody tr td input[name="+ff+"]");
    var total  = 0;
    var cont   = 0;
    
    for (i = 0; i <= ctas.length-1 ; i++ ) {
        switch(ff){
            case 'sercotec':     total += parseInt(inputs[i].value); break;
            case 'inacap':       total += parseInt(inputs[i].value); break;
            case 'pecuniarios':  total += parseInt(inputs[i].value); break;
        }
        if(inputs[i].value === 0){
            cont++;
        }
    }
    
    $('#T'+ff).text('$'+formatNumber(total));

    calculateTotal();
}

function calculateTotal(){
        
    var total = 0;
    
    var tSerc = $('#Tsercotec').text().replace('$' ,'');
        tSerc = tSerc.toString().replace(/\./g, '');
    var tInac = $('#Tinacap').text().replace('$' ,'');
        tInac = tInac.toString().replace(/\./g, '');
    var tPecu = $('#Tpecuniarios').text().replace('$' ,'');
        tPecu = tPecu.toString().replace(/\./g, '');
    
    total = parseInt(tSerc)+parseInt(tInac)+parseInt(tPecu);
        
    $('#total').text('$'+formatNumber(total));

}

function formatNumber(num) {
    if (!num || num == 'NaN') return '0';
    if (num == 'Infinity') return '&#x221e;';
    num = num.toString().replace(/\$|\,/g, '');
    if (isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 100 + 0.50000000001);
    //cents = num % 100;
    num = Math.floor(num / 100).toString();
    //if (cents < 10)
    //    cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3) ; i++)
        num = num.substring(0, num.length - (4 * i + 3)) + '.' + num.substring(num.length - (4 * i + 3));
    return (((sign) ? '' : '-') + num/* + ',' + cents*/);
}


