/**
 * Created by mykola.dekhtiarenko on 30.09.17.
 */
$(document).ready(function () {
$(".locale").on('click', function () {
    var locale = $(this).attr("locale");
    $.ajax({
        method: "POST",
        url: "/rest/locale",
        dataType: 'json',
        data: {"locale": locale},
    }).always(function () {
        location.reload();
    });
});
});