$(document).ready(function () {
    $('#loginForm').on('submit',function (event) {
        event.preventDefault();

        var payload = {
            email: $('#email').val(),
            password: $('#password').val()
        };

        jQuery.ajax({
            method: "POST",
            data: JSON.stringify(payload),
            url: App.constants.basePath + 'user/authenticate',
            success: function(resp) {
                console.log(resp);
            },
            error: function(e) {
                console.log(e)
            }
        })
    })
})