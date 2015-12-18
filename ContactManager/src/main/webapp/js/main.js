
$('#get').click(function () {
    $.ajax({
        type: "GET",
        /*cache: false,*/
        url: '/api/person/' + $("#data").val(),
        /*data: "",*/
        success: function (response) {
            var html = "";
            $.each(response.data, function (i) {
                html = html + response.data[i] + "<br/>";
            });
            $('#container').html(html);
        }
    });
});

$('#post').click(function () {
    if (!$("#data").val()) {
        alert("Enter your data!");
    } else {
        $.ajax({
            type: "POST",
            cache: false,
            url: '/api/person',
            data: {
                'data': $("#data").val()
            },
            success: function (response) {
                //$('#get').click();
            }
        });
    }

});