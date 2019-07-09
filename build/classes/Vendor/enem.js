var ls = window.localStorage;

var isEditor = $('#comEditor').val();

//if (isEditor == 'true') {
    var editor = CKEDITOR.replace('editor', {
        colorButton_colors: '00923E,F8C100,28166F',
        on: {
            instanceReady: function () {
                editor.focus();
                if (ls.getItem('_gettexteditor') != null) {

                    var value = ls.getItem('_gettexteditor');

                    editor.insertHtml(value);
                }
            },
            key: function (ev) {
                var arrKeys = [27, 1114203, 116];
                _strg = editor.getData();
                if (jQuery.inArray(ev.data.keyCode, arrKeys) !== -1) {
                } else {
                }
            }
        },
        height: 630,
        removePlugins: 'save,newpage,preview,print,source,templates,find,selectall,scayt,form,numberedlist,outdent,blockquote,bidiltr,link,image,about,strike,subscript,superscript,smiley,specialChar,pagebreak,iframe,horizontalrule,flash,checkbox,radio,textfield,textarea,select,imagebutton,hiddenfield',
        title: 'Editor',
        toolbarLocation: 'top',
        uiColor: '#cccccc',
        tabSpaces: 5
    });

    editor.on('change', function (evt) {
        var texto = evt.editor.getData();
        $('#getTextEditor').val(texto);
        ls.setItem('_gettexteditor', texto);
    });
//}


if (ls.getItem('coll_11') != null) {
    $('#coll_11').css({height: '0px'}).removeClass('in');
    $('#a_coll_11').attr('data-evento', 'fechar').html("<i class='icon-caret-right'></i> Expandir");
}
if (ls.getItem('coll_22') != null) {
    $('#coll_22').css({height: '0px'}).removeClass('in');
    $('#a_coll_22').attr('data-evento', 'fechar').html("<i class='icon-caret-right'></i> Expandir");
}

$('.accordion-toggle').click(function () {
    var _href = $(this).attr('data-href');
    var _evt = $(this).attr('data-evento');
    var d = $(this);
    if (_evt == 'abrir') {
        d.attr('data-evento', 'fechar').html("<i class='icon-caret-right'></i> Expandir");
        ls.setItem(_href, _href);
    } else {
        d.attr('data-evento', 'abrir').html("<i class='icon-caret-right'></i> Ocultar");
        ls.removeItem(_href);
    }
});

if (ls.getItem('tabActive') == null) {
    $('#myTabId a[href="#tab_1_1"]').tab('show');
} else {
    var _tabActive = ls.getItem('tabActive');
    $('#myTabId a[href="#' + _tabActive + '"]').tab('show');
}

var temasCarregados = false;

var $rows = $('#ulTemasDois li');

$('#tituloDaRedacao').keyup(function () {
    var texto = this.value;
    ls.setItem('tituloDaRedacao', texto);
});

if (ls.getItem('tituloDaRedacao') != null) {
    $('#tituloDaRedacao').val(ls.getItem('tituloDaRedacao'));
}

if (ls.getItem('tipoDeEnvio') != null) {
    $('#tipoDeEnvio').val(ls.getItem('tipoDeEnvio'));
}

$('#pesquisarTema').keyup(function () {

    var val = $.trim($(this).val()).replace(/ +/g, ' ').toLowerCase().split(' ');

    var _thisValue = this.value;

    if (_thisValue == '') {
        $('#ulTemasDois').hide();
    } else {
        $('#ulTemasDois').show();
    }
    $rows.hide().filter(function () {
        var text = $(this).text().replace(/\s+/g, ' ').toLowerCase();
        var matchesSearch = true;
        $(val).each(function (index, value) {
            matchesSearch = (!matchesSearch) ? false : ~text.indexOf(value);
        });
        return matchesSearch;
    }).show();
});

$("#formEnviarRedacao").submit(function () {
    var n = $('#getTemaId').val();
    if (!Number.isInteger(parseInt(n)) && $.isNumeric(n) == false) {
        $('#pesquisarTema').focus();
        alert("Escolha algum tema, por favor para poder prosseguir.");
        return false;
    }
   if(editor.getData().trim()==''){
       editor.focus();
       alert('Digite sua redação.');
       return false;
   }
}).ajaxForm({
    beforeSend: function () {
        $('#btnEnviarRedacao').attr('disabled', true).html("Enviando, aguarde, por favaor...");
    },
    url: 'models/enviar_redacao.php',
    uploadProgress: function (event, position, total, percentComplete) {
        var percentVal = percentComplete + '%';
        $('#btnEnviarRedacao').attr('disabled', true).html("Enviando[" + percentVal + "], aguarde, por favaor...");
    }, success: function (data) {
        if (data == "1") {
            if ($('#formAcao').val() == 'incluir') {
                alert("Redação Enviada com Sucesso!");
            } else {
                alert("Redação Alterada com Sucesso!");
            }
            $('#btnEnviarRedacao').attr('disabled', false).html("Enviar Redação");
        } else if (data == "0") {
            $('#btnEnviarRedacao').attr('disabled', false).html("Enviar Redação");
        } else {
            $('#btnEnviarRedacao').attr('disabled', false).html("Enviar Redação");
            alert(data);
        }
    }
});

/**
 * 
 */
$("#formRedacaoProposta").submit(function () {
    var n = $('#getTemaId').val();
}).ajaxForm({
    beforeSend: function () {
        $('#btnFormEnviarRedacao').attr('disabled', true).html("Enviando, aguarde, por favaor...");
    },
    url: 'models/enviar_redacao.php',
    uploadProgress: function (event, position, total, percentComplete) {
        var percentVal = percentComplete + '%';
        $('#btnFormEnviarRedacao').attr('disabled', true).html("Enviando[" + percentVal + "], aguarde, por favaor...");
    }, success: function (data) {
        if (data == "1") {
            if ($('#formAcao').val() == 'incluir') {
                alert("Redação Enviada com Sucesso!");
                window.location.reload();
            } else {
                alert("Redação Alterada com Sucesso!");
                window.location.reload();
            }
            $('#btnFormEnviarRedacao').attr('disabled', false).html("Enviar Redação");
        } else if (data == "0") {
            $('#btnFormEnviarRedacao').attr('disabled', false).html("Enviar Redação");
        } else {
            $('#btnFormEnviarRedacao').attr('disabled', false).html("Enviar Redação");
            alert(data);
        }
    }
});

$('#downloadArquivoRedacao')._neoPluginUpload({
    accept: ".pdf,.jpeg,.png,.jpg",
    tiposPermitidos: ['image/png', 'image/jpeg', 'image/jpg', 'application/pdf', 'application/force-download'],
    tamanhoPermitido: 15728640
});

/**
 * 
 * @return {undefined}
 */
function fCarregarTemas() {
    $.post('views/get_temas.php', function (data) {
        $('#getTemas').html(data);
    });
}

var ArsenalRedacaoCarregado = false;
var MinhasRedacoesCarregada = false;
var PropostaCarregada = false;

$('.tituloENEMAbout').click(function () {

    var _tr = $(this).attr('data-id');

    switch (_tr) {
        case 'tab_1_3':
            $('#pesquisarTema').focus().select();
            break;
        case 'tab_1_4':
            $('#btnCarregarTemas').attr('disabled', true).html('Carregando temas, aguarde...');
            if (temasCarregados == false) {
                fCarregarTemas();
            }
            break;
        case 'tab_1_5':
            if (MinhasRedacoesCarregada == false) {
                $.post('views/minhas_redacoes.php', function (data) {
                    $("#getMinhasRedacoes").html(data);
                });
            }
            break;
        case 'tab_1_6':
            if (ArsenalRedacaoCarregado == false) {
                $('#btnCarregarArsenalDeRed').hide();
                $.post('views/banco_de_redacoes.php', function (data) {
                    $("#getBancoDeRedacao").html(data);
                });
            }
            break;
        case 'tab_1_7':
            if (PropostaCarregada == false) {
                $('#getListaPropostas').html('Carregando <b>propostas</b>, aguarde...');
                $.post('views/listar_propostas.php', function (data) {
                    $('#getListaPropostas').html(data);
                });
            }
            break;
    }
});

var act = ls.getItem('tabActive');

switch (act) {
    case 'tab_1_3':
        $('#pesquisarTema').focus().select();
        break;
    case 'tab_1_4':
        $('#btnCarregarTemas').attr('disabled', true).html('Carregando temas, aguarde...');
        if (temasCarregados == false) {
            fCarregarTemas();
        }
        break;
    case 'tab_1_5':
        if (MinhasRedacoesCarregada == false) {
            $.post('views/minhas_redacoes.php', function (data) {
                $("#getMinhasRedacoes").html(data);
            });
        }
        break;
    case 'tab_1_6':
        if (ArsenalRedacaoCarregado == false) {
            $('#btnCarregarArsenalDeRed').hide();
            $.post('views/banco_de_redacoes.php', function (data) {
                $("#getBancoDeRedacao").html(data);
            });
        }
        break;
    case 'tab_1_7':
        if (PropostaCarregada == false) {
            $.post('views/listar_propostas.php', function (data) {
                $('#getListaPropostas').html(data);
            });
        }
        break;
}

$('#btnCarregarArsenalDeRed').click(function () {
    $(this).html('<i class="icon-refresh"></i> Carregando, aguarde...');
    $.post('views/banco_de_redacoes.php', function (data) {
        $("#getBancoDeRedacao").html(data);
        $('#btnCarregarArsenalDeRed').hide();
    });
});

$('#btnCarregarTemas').click(function () {
    $(this).attr('disabled', true).html('Carregando temas, aguarde...');
    fCarregarTemas();
});

$('.escolherTemaJs').click(function () {

    var _temaId = $(this).attr('data-tema-id');

    var _imagem = $(this).attr('data-imagem');

    var _textoMotivador = $(this).attr('data-text-motivador');

    var temaNome = $(this).attr('data-tema');

    $('#myTabId a[href="#tab_1_3"]').tab('show');

    $('#getTema').html('<img src="../../../imagens/check-azul.png" /><span style="position:relative;top:2px;">' + temaNome.trim() + '</span>');

    $('#getTemaSpan,#nomeDoTema').html(temaNome.trim());

    $('#textoMotivador').html(_textoMotivador);

    if (_imagem == '') {
        $('#imagemReflexao').attr('src', '../../../arquivos/redacao-tema-imagem/imagem-default.jpg');
    } else {
        $('#imagemReflexao').attr('src', '../../../arquivos/redacao-tema-imagem/' + _imagem);
    }

    $('#getTemaId').val(_temaId);

    $('#ulTemasDois').hide();

    $('#tituloDaRedacao').focus();

    ls.setItem('temaImagem', _imagem);
    ls.setItem('temaTexto', temaNome.trim());
    ls.setItem('temaTextoMotivador', _textoMotivador);
    ls.setItem('temaId', _temaId);

});

if (ls.getItem('temaId') != null) {
    $('#textoMotivador').html(ls.getItem('temaTextoMotivador'));

    $('#nomeDoTema').html('<img src="../../../imagens/check-azul.png" /><span style="position:relative;top:2px;">' + ls.getItem('temaTexto') + '</span>');

    $('#getTemaSpan').html(ls.getItem('temaTexto'));

    if (ls.getItem('temaImagem') == '') {
        $('#imagemReflexao').attr('src', '../../../arquivos/redacao-tema-imagem/imagem-default.jpg');
    } else {
        $('#imagemReflexao').attr('src', '../../../arquivos/redacao-tema-imagem/' + ls.getItem('temaImagem'));
    }

    $('#getTemaId').val(ls.getItem('temaId'));

    $('#ulTemasDois').hide();

    $('#tituloDaRedacao').focus();

    if (ls.getItem('tipoDeEnvio') == 'texto') {
        $('#displayComoImagem').addClass('hidden').slideUp();
        $('#displayComoTexto').removeClass('hidden').slideDown();
        $('#escreverComoTexto').show();
        $('#tipoDeEnvio').val('texto');
        $('#textoRedacao').focus();
        $('#btnEnviarRedacao').attr('disabled', false);
        $('#btnFormEnviarRedacao').attr('disabled', false);
        $('#linha_1').focus();
    } else {
        $('#displayComoImagem').removeClass('hidden').slideDown();
        $('#displayComoTexto').addClass('hidden').slideUp();
        $('#escreverComoTexto').hide();
        $('#tipoDeEnvio').val('imagem');
        $('#btnEnviarRedacao').attr('disabled', false);
        $('#btnFormEnviarRedacao').attr('disabled', false);
    }
}

/**/
$('#enviarComoTexto').click(function () {
    $('#displayComoImagem').addClass('hidden').slideUp();
    $('#displayComoTexto').removeClass('hidden').slideDown();
    $('#escreverComoTexto').show();
    $('#tipoDeEnvio').val('texto');
    $('#textoRedacao').focus();
    $('#btnEnviarRedacao').attr('disabled', false);
    $('#btnFormEnviarRedacao').attr('disabled', false).select().focus();
    $('#linha_1').focus();
    ls.setItem('tipoDeEnvio', 'texto');
});

$('#enviarComoFoto').click(function () {
    $('#displayComoImagem').removeClass('hidden').slideDown();
    $('#displayComoTexto').addClass('hidden').slideUp();
    $('#escreverComoTexto').hide();
    $('#tipoDeEnvio').val('imagem');
    $('#btnEnviarRedacao').attr('disabled', false);
    ls.setItem('tipoDeEnvio', 'imagem');
    $(this).attr('for', $('#downloadArquivoRedacao input').attr('id'));
    $('#btnFormEnviarRedacao').attr('disabled', false).select().focus();
});

$('#btnCriarRedacaoAgora').click(function () {
    $('#myTabId a[href="#tab_1_3"]').tab('show');
});

$('.tituloENEMAbout').click(function () {
    var _tdId = $(this).attr('data-id');
    ls.setItem('tabActive', _tdId);
});

$.post('views/get_ultimas_redacoes.php', function (data) {
    $('#getUltimasRedacoes').html(data);
});

var angle = 0, img = document.getElementById('redacaoImagemView');
var btnRodarRedacao = document.getElementById('button');

if (btnRodarRedacao != null) {
    btnRodarRedacao.onclick = function () {
        angle = (angle + 90) % 360;
        img.className = "rotate" + angle;
    };
}