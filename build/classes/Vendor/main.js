$(document).ready(function () {
    
    
    /*Desfazer esse comentário Plantão*/
   // $(this)._neoBeforeUnload(false, "Você está inserindo um novo plantão...");

    _id = null;
    //============================Início============================
    // Estrutura para excluir um calendário. Chamado pelo arquivo ../controllers/excluir_calendario_escolar.php
    //==============================================================
    $(".excluir-calendario").click(function () {
        var id_calendario = $(this).attr("data-id");
        if (confirm("Deseja realmente excluir este calendário?")) {
            $.post("../controllers/excluir_calendario_escolar.php", {
                id_calendario: id_calendario
            }, function (data) {
                alert(data);
                window.location.reload();
            });
        }
    });

    ////////////////////////////////////////Fim////////////////////////////////////////

    var bar = $('.bar');
    var percent = $('.percent');
    var status = $('#status');
    var progressoUploadEstudantil = $('.progresso-upload-estudantil');
    var options = {
        url: 'models/upload-video.php',
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
            var percentVal = '100%';
            bar.width(percentVal);
            percent.html(percentVal);
            $("#neo_Eeb_125").html(responseText);
        },
        complete: function () {

        }
    };

    $("#form_upload_video").ajaxForm(options);

    getTurmasIDsSelect = $("#getTurmasIDsSelect");

    $(".select_neo_plugin")._neoPluginSelect();

    /**
     * Estrutura para INCLUIR uma nova avaliação
     */
    $("#neo_Ec_07").ajaxForm({
        beforeSend: function () {
            if($('#getDatetimepicker2').val().trim()==''){
                $('#datetimepicker').select();
                alert('Escolha uma data.');
                return false;
            }
        },
        url: 'models/incluir-plantao.php',
        success: function (data) {
            if (data == "1") {
                $(true)._neoBeforeUnload(false, "Você está inserindo um novo plantão...");
                alert("Plantão inserido com sucesso!");
                window.location.href = "./";
            }
            else {
                alert(data);
            }
        }
    });
});