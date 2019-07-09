$(document).ready(function() {

    Publicacoes.btnReplyComment();

    Publicacoes.tocarAudio();

    Publicacoes.getAlunosMarcados('views/get_marcacoes.php');

    _publicacaoArrayID = undefined;

    $('.button-curtir').click(function() {
        alert('Curtir');
    });

    $('.button-nao-curtir').click(function() {
        alert('Não curtir');
    });

    $('.abrirSpriteEmoticon').click(function() {
        /**/
        var _thisID = $(this).attr("id");
        /*O Id limpo da publicação*/
        var _dataPubID = $(this).attr("data-id");

        var _painelSmiles = $('#painel-smiles-' + _dataPubID);

        var regex = /txiwts41/gmi;

        if (regex.test($(this).find('span').attr('class')) == true) {
            if (_painelSmiles.css('display') == 'none') {
                if ($('#get-smilies-uni-' + _dataPubID).html().length == 0) {
                    $('#get-smilies-uni-' + _dataPubID).html('Carregando...');
                    $('#comment-' + _dataPubID).focus();
                    SpriteApp.rumApp('#get-smilies-uni-' + _dataPubID, '#comment-' + _dataPubID, _thisID);
                }
                _painelSmiles.show();
            }
            else {
                _painelSmiles.hide();
            }
        }
    });

    /*Fecha o painel de EmotIcons*/
    $('.fechar-painel').click(function() {
        var dataId = $(this).attr('data-id');
        var _painelSmiles = $('#painel-smiles-' + dataId);
        _painelSmiles.hide();
    });

    /*Botão - Compartilhar*/
    $('.pb_compartilhar,.card-button-share').click(function() {

        var _pbId = $(this).attr('data-pub-id');

        _publicacaoArrayID = _pbId;

        $('#id_modal_compartilhamento').modal('show');

        $('.class_modal_compartilhamento').html('Carregado, publicação...');

        $.post('publicacoes/get_dado_publicacao.php', {publicacao_id: _pbId},
        function(data) {
            $('.class_modal_compartilhamento').html(data);
        });

        return false;
    });


    $('.enviar-comentario').click(function() {

        /*Pega o ID no formato ID_PUBLICACAO-ID_ALUNO*/
        var dataId = $(this).attr('data-id');

        var nId = dataId.split('-');

        /*O elemento*/
        var _thisElement = $('#comment-' + dataId);

        var alunoLogadoId = $(this).attr('data-aluno-id');

        nId[1] = nId[0];
        /*Split o Id do elemento Primeiro Index: comment; 1º o Id da publicação; o 2º o id do usuário*/
        nId[2] = alunoLogadoId;
        /*Terceiro Index =  Texto a ser enviado*/
        nId[3] = _thisElement.html();

        var regex = [/\<img\s+class\=\"(txemoji\s+.*?)\".*?(\>|>)/gmi];

        nId[3] = nId[3].replace(regex[0], '&lt;txicon&gt;$1&lt;/txicon&gt;');

        var _painelSmiles = $('#painel-smiles-' + dataId);

        _painelSmiles.hide();

        /*Caso não tenha nenhum texto*/
        if (_thisElement.text().trim().length == 0) {
            alert('Escreva alguma mensagem.');
            return false;
        }
        $.post('publicacoes/models/comentar_publicacao.php', {
            comment: nId
        }, function(data) {
            if (data == 1) {
                $.post('publicacoes/publicacoes_comentarios.php', {pb_id: nId[1]}, function(data) {
                    _thisElement.html('Escreva um comentário...');
                    _thisElement.text('');
                    $('#publicacao-id-' + nId[1]).html(data);
                });
            }
            else {
                alert(data);
            }
        });

        return false;
    });

    /*Pera enviar clicando no botão.*/
    $('.send-comentario').click(function(ev) {

        var _pbId = $(this).attr('id');

        /*Pega o ID no formato ID_PUBLICACAO-ID_ALUNO*/
        var dataId = $(this).attr('data-id');

        /*O elemento*/
        var _thisElement = $('#comment-' + dataId);

        var alunoLogadoId = $(this).attr('data-aluno-id');

        /*Split o Id do elemento Primeiro Index: comment; 1º o Id da publicação; o 2º o id do usuário*/

        var nId = _pbId.split('-');

        nId[2] = alunoLogadoId;

        /*Terceiro Index =  Texto a ser enviado*/
        nId[3] = _thisElement.html();

        var regex = [/\<img\s+class\=\"(txemoji\s+.*?)\".*?(\>|>)/gmi];

        nId[3] = nId[3].replace(regex, '&lt;txicon&gt;$1&lt;/txicon&gt;');/*&lt; = < &gt; = > */

        var _painelSmiles = $('#painel-smiles-' + dataId);

        _painelSmiles.hide();

        $.post('publicacoes/models/comentar_publicacao.php', {
            comment: nId
        }, function(data) {
            if (data == 1) {
                $.post('publicacoes/publicacoes_comentarios.php', {pb_id: nId[1]}, function(data) {
                    _thisElement.html('Escreva um comentário...');
                    _thisElement.text('');
                    $('#publicacao-id-' + nId[1]).html(data);
                });
            }
            else {
                alert(data);
            }
        });

        return false;

    });


    $('.remover-caractere').click(function() {
        var _thisId = $(this).attr('data-id');

        /*Pega o elemento de enviar o comentário*/
        var _element = $('#comment-' + _thisId);

        var removeCarat = _element.text();

        var string = removeCarat.substring(0, removeCarat.length - 1);

        _element.text(string);
    });

    /*Dropdown - Botão - Excluir*/
    $('.pb_excluir').click(function() {
        /*Pega o id da publicação. Formato. idpublicacao[number]-idalumo[number]*/
        var _pbId = $(this).attr('data-pub-id');

        /*Adiciona o id da publicação dentro do input*/
        $('#id_inp_public_id').val(_pbId);
        /*Abre o modal de confirmação*/
        $('#id_modal_excluir_publicacao').modal('show');

        return false;

    });
    /*Exclui uma publicação*/
    $('#id_btn_excl_publ').click(function() {
        var _pbId = $('#id_inp_public_id').val();
        /*Cria um array com dois índexs. O primeiro [0] é o id da publicação, o segundo [1] é o ID do usuário*/
        var _arrayId = _pbId.split('-');

        Publicacoes.removerElemento('publicacao', _pbId);
        Publicacoes.removerElemento('pbalert', _pbId);
        Publicacoes.removerElemento('div', _pbId);
        Publicacoes.removerElemento('card-separador-horizontal', _pbId);

        $.post('publicacoes/excluir_publicacao.php', {
            publicacao_id: parseInt(_arrayId[0]),
            aluno_id: parseInt(_arrayId[1])
        }, function(data) {
            if (data == '1') {
                Publicacoes.carregarFeed('publicacoes/publicacoes.php', '#get_publicacoes');
            }
        });
    });

    /*Botão -  Ocultar*/
    $('.pb_ocultar').click(function() {
        var _pbId = $(this).attr('data-pub-id');
        alert(_pbId);
        return false;
    });

    /*Botão - Editar*/
    $('.pb_editar').click(function() {
        var _pbId = $(this).attr('data-pub-id');
        alert(_pbId);
        return false;
    });

    $('.card-button-comment').click(function() {
        var _thisId = $(this).attr('data-publicacao-id');
        $('#comment-' + _thisId).text('').focus();
    });

    $('.abrirModal').click(function() {
        var crsr = '<div id="alunosQueCompartilharam"></div>';
        $('body').append(crsr);
        var _id = $(this).attr('data-pub-id');
        var string = '<div id="getAlunosCompartilharam">Carregando...</div>';

        $('#alunosQueCompartilharam')._neoPluginModal({
            mensagem: string,
            abrir: true,
            tempo: 10,
            fecharComTempo: false
        });

        $.post('publicacoes/get_alunos_compartilharam.php', {
            publicacao_id: _id
        }, function(data) {
            string += data;
            $('#getAlunosCompartilharam').html(data);
        });
    });

    $('.sumary-title').click(function() {

        var _this = $(this);

        var _dataID = _this.attr('data-id');

        if ($('#sumary-body-' + _dataID).css('display') == 'none') {
            _this.html('<i class="icon-chevron-down"></i> Clique para encolher...');
            $('#sumary-body-' + _dataID).slideDown().css('text-align','justify');
        } else {
             _this.html('<i class="icon-chevron-right"></i> Clique para expandir...');
            $('#sumary-body-' + _dataID).slideUp();
        }

    });
});