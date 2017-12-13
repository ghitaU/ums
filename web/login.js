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
            url: App.constants.baseApiPath + 'user/authenticate',
            success: function(resp) {
                App.ss.set('auth', resp);
                window.location.href = App.constants.baseUrl + 'dashboard.html'
            },
            error: function(e) {
                console.log(e)
            }
        })
    })
})