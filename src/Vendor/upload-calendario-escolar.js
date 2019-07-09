
//Esse arquivo é responsável por fazer upload na área calendário escolar
$(document).ready(function () {

    var bar = $('.bar');
    var percent = $('.percent');
    var status = $('#status');
    var progressoUploadEstudantil = $('.progresso-upload-estudantil');
    var options = {
        url: '../../controllers/upload-calendario-escolar.php',
        beforeSend: function () {
            progressoUploadEstudantil.hide();
            status.empty();
            var percentVal = '0%';
            bar.width(percentVal);
            percent.html(percentVal);
        },
        uploadProgress: function (event, position, total, percentComplete) {
            progressoUploadEstudantil.show();
            var percentVal = percentComplete + '%';
            bar.width(percentVal);
            percent.html(percentVal);
        },
        success: function (responseText) {
            alert(responseText);
            var percentVal = '100%';
            bar.width(percentVal);
            percent.html(percentVal);
        },
        complete: function (xhr) {
            status.html(xhr.responseText);
        }
    };

    $('#myForm').ajaxForm(options);

});