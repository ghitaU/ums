App.utils.createGrid({
    dataUrl: "http://localhost:8080/user/student/list",
    columns: ['last_name','first_name', 'email'],
    target: '#grid'
});