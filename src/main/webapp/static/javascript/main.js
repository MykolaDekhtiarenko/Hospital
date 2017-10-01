$(document).ready(function () {
    // $("#assignment").hide();

    $(".add-medicine").click(function() {
        $(this).parent().parent().find(".submit").show();
        $(medicineInput).insertBefore($(this).parent());
    });

    $(".add-procedure").click(function() {
        $(this).parent().parent().find(".submit").show();
        $(procedureInput).insertBefore($(this).parent());
    });

    $(".add-surgery").click(function() {
        $(this).parent().parent().find(".submit").show();
        $(surgeryInput).insertBefore($(this).parent());
    });
});

