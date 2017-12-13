var App = App || {};

App.constants = {
    baseApiPath: 'http://localhost:8080/',
    baseUrl: 'http://localhost:63342/ums/web/'
};

App.utils = App.utils || {};

App.redirect = function (url) {
    if (!url) {
        return false;
    }
    if (url.replace(App.baseUrl) !== url) {
        url = App.baseUrl + url;

    }
    window.location.href = url;
}

App.utils.createGrid = function (config) {
    if (!config) {
        throw new error("Cannot initialize the grid", config);
    }
    var dataUrl = config.dataUrl,
        columns = config.columns,
        target = config.target || 'body',
        data,
        generateRow = function (item) {
            var tr = jQuery('<tr>');
            columns.forEach(function (column) {
                var td = jQuery('<td>');
                td.text(item[column]);
                tr.append(td);
            });
            return tr;
        },
        generateHeader = function () {
            var thead = jQuery('<thead>'),
                tr = jQuery('<tr>');
            columns.forEach(function (column) {
                var th = jQuery('<th>');
                th.text(column);
                tr.append(th);
            })
            thead.append(tr);
            return thead;
        },
        generateBody = function () {
            var tbody = $('<tbody>');
            data.forEach(function (item) {
                var row;
                row = generateRow(item);
                tbody.append(row);
            });
            return tbody;
        },
        generateTable = function () {
            var table = $('<table>');
            table.append(generateHeader());
            table.append(generateBody());
            table.addClass('table')
            return table;
        },
        buildGrid = function () {
            var table = generateTable();
            $(target).append(table);
        };

    jQuery.ajax({
        method: "POST",
        data: JSON.stringify({"token": "6"}),
        url: dataUrl,
        success: function (resp) {
            data = resp.items;
            buildGrid();
        },
        error: function (e) {
            console.log(e)
        }
    })

};

App.utils.serializeFrom = function (target) {

    var objectify = function (formArray) {
        var returnArray = {}
            , i = 0
            , ss = App.ss.get('auth')
            , token = ss && ss['token'].toString()
        ;

        token && (returnArray.token = token);

        for (i = 0; i < formArray.length; i++) {
            returnArray[formArray[i]['name']] = formArray[i]['value'];
        }
        return returnArray;

    };

    return objectify($(target).serializeArray());
};

App.ss = {
    get: function (key) {
        return JSON.parse(sessionStorage.getItem(key));
    },
    set: function (key, value) {
        value = JSON.stringify(value);
        sessionStorage.setItem(key, value);
    },
    addMessage: function (text) {
        var ssMessage = this.get('message') || [];
        ssMessage.push(text);
        this.set('message', ssMessage);
    }
};

(function () {
    var auth = App.ss.get('auth');
    if (!auth.token && window.location.href == window.location.href.replace('login.html')) {
        window.location.href = App.constants.baseUrl + 'login.html';
    }

}());

$(document).ready(function() {
    var messages = App.ss.get('message') || [];
    if (messages.length) {
        messages.forEach(function (item) {
            var element = $('<div class="alert alert-success fade in alert-dismissable" style="margin-top:18px;">' +
                '<a href="#" class="close" data-dismiss="alert" aria-label="close" title="close">×</a>' +
                item +
                '</div>');
            $('body').prepend(element);
        })

    }
    setTimeout(function(){
        $('.alert').remove();
    }, 5000);

    App.ss.set('message', []);

})