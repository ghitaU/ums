$(document).ready(function () {
    $('#newStudent').on('submit',function (event) {
        event.preventDefault();

        var payload = App.utils.serializeFrom('#newStudent');
        console.log(payload);

        jQuery.ajax({
            method: "POST",
            data: JSON.stringify(payload),
            url: App.constants.baseApiPath + 'user/student/add',
            success: function(resp) {
                if (resp.id) {
                    App.ss.addMessage("Student successfully saved!");
                    App.redirect('students.html');
                }
            },
            error: function(e) {
                console.log(e)
            }
        })
    })
})