$(document).ready(function() {
/*
    if (typeof (EventSource) !== "undefined") {

        var descEventSource = new EventSource(window.location.origin + '/ava/aluno/models/tx_desconectar_alunos.php');

        var desconectarAlunosLogOff = "<div id='modalDesconectarAlunos' class='modal hide fade' tabindex='-1' role='dialog' aria-labelledby='myModalLabel3' aria-hidden='true'><div class='modal-header'><button type='button' class='close btnDesconectarDoSistema' data-dismiss='modal' aria-hidden='true'></button><h3 id='myModalLabel3'>Atenção</h3></div><div class='modal-body'><div id='divGetMensagemDesconectar'></div></div><div class='modal-footer'><button class='btn red btnDesconectarDoSistema' >Sair</button></div></div>";

        $('body').append(desconectarAlunosLogOff);


        descEventSource.addEventListener('desconectar', function(ev) {
            var obj = JSON.parse(ev.data);
            if (obj.total > 0) {
                $('#modalDesconectarAlunos').modal('show');
                $('#divGetMensagemDesconectar').html('<h3>' + obj.dados.tx_desc_mensagem + '</h3>');
            }
            else{
                console.log('Nenhuma mensagem');
            }
        });

        function desconctarAlunos() {
            $.post(window.location.origin + '/ava/aluno/models/desc_atualizar_mensagem_como_lida.php', function(data) {
                if (data == '1') {
                    $.post(window.location.origin + '/ava/controllers/sair.php', function(data) {
                        window.location.href = window.location.origin + '/ava/login/?nivel=aluno';
                    });
                }
            });
        }

        $('#modalDesconectarAlunos').keyup(function(ev) {
            if (ev.keyCode == 27) {
                desconctarAlunos();
            }
            return false;
        });

        $('.btnDesconectarDoSistema').click(function() {
            desconctarAlunos();
        });

        if (descEventSource.readyState == 2) {
            descEventSource = new EventSource(window.location.origin + '/ava/aluno/models/tx_desconectar_alunos.php');
        }
    }
    */
});