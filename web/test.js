var App = {};


App.getUsers = function() {
    jQuery.ajax({
        method: "POST",
        dataType: 'jsonP',
        url: "http://localhost:8080/user/list",
        success: function(response) {
            console.log(response)
        },
        error: function(resp) {
            console.log ("nu mere")
        }
    })
};

App.getUsers();