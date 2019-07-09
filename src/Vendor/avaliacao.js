jQuery(document).ready(function () {

    $('.tooltips').tooltip();

    $('.erro_ao_tentar_buscar_avaliacao,.error_avaliacao_nao_found')._neoPluginAlert({
        mensagem: 'Ops, nenhuma avaliação encontrada.',
        tamanho: '0',
        tipoCor: 'warning',
        link: './'
    });

    jQuery.validator.addMethod("padrao_data", function (value, element) {
        return this.optional(element) || /(^(((0[1-9]|[12][0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/.test(value);
    }, "Insira uma data no padrão de data dd/MM/yyyy");


    var btnAvancarAvaliacao = $('#btnAvancarAvaliacao');

    btnAvancarAvaliacao.click(function () {
        //------------------------------------------------
        //      VERIFICAÇÃO DE IGUALDADE DE AVALIAÇÕES
        //------------------------------------------------
        var _btn = $(this);
        _btn.text('Verificando avaliação anteriores, aguarde...').attr('disabled', true);
        $.post('models/verificar_igualdade_avaliacoes.php', {
            disciplina_id: $('#ava_elet_select_disciplinas').val().trim(),
            ava_descricao: $('#ava_descricao').val().trim()
        }, function (data) {
            if (data == "1") {
                $('#form_incluir_avaliacao').submit();
            } else if (data == "2") {
                $('#form_incluir_avaliacao').submit();
            } else {
                if (confirm(data)) {
                    $('#form_incluir_avaliacao').submit();
                } else {
                    _btn.text('Avançar').attr('disabled', false);
                }
            }
            $(this).text('Avançar').attr('disabled', false);
        });

    });


    /**
     * 
     * @param {type} elementoFocus
     * @param {type} mensagem
     * @returns {undefined}
     */
    function disabledBtnSalvarNovaAvaliacao(elementoFocus, mensagem) {
        $(elementoFocus).focus();
        $('#btnAvancarAvaliacao').attr('disabled', false).html('<i class="icon-save"></i> Tentar novamente.');
        alert(mensagem);
    }

    /**
     * Estrutura para incluir mais questões.
     * @type type
     */
    $('#form_incluir_avaliacao').ajaxForm({
        beforeSend: function () {
            var _selectedIndex = $('.selecionarTurmas').find('select')[0];
            if ($('#ava_descricao').val().trim() == "") {
                disabledBtnSalvarNovaAvaliacao('#ava_descricao', 'Insira a descrição...');
                return false;
            } else if ($('#ava_elet_select_disciplinas').val().trim() == '') {
                disabledBtnSalvarNovaAvaliacao('#ava_elet_select_disciplinas', "Selecione a disciplina.");
                return false;
            } else if ($("select[name='ava_turma[]']").attr('value') == '' || _selectedIndex.selectedIndex == '-1') {
                disabledBtnSalvarNovaAvaliacao("select[name='ava_turma[]']", "Selecione uma, duas ou mais turmas.");
                return false;
            } else if ($('#date').val() == '') {
                disabledBtnSalvarNovaAvaliacao('#date', 'Selecione a data.');
                return false;
            } else if (document.getElementById('tipo_das_questoes').selectedIndex == 0) {
                disabledBtnSalvarNovaAvaliacao('#tipo_das_questoes', 'Selecione o tipo das questões.');
                return false;
            } else if (document.getElementById('BimestreId').selectedIndex == 0) {
                disabledBtnSalvarNovaAvaliacao('#BimestreId', 'Escolha corretamente os bimestres.');
                return false;
            }
            btnAvancarAvaliacao.text('Avançando...').attr('disabled', true);
        },
        success: function (responseText) {
            var patt = /\d+\-\d+/g;
            if (patt.test(responseText) === true) {
                btnAvancarAvaliacao.text('Sucesso!').attr('disabled', false);
                $(this)._neoBeforeUnload(true, 'Você está inserindo uma nova avaliação.');
                var arr = responseText.split('-');
                window.location.href = '?exe=editar&avaliacao-id=' + arr[1] + '&quant_questoes=10';
                $.cookie('nova_avaliacao', '1');
            } else {
                btnAvancarAvaliacao.text('Avançar').attr('disabled', false);
                alert(responseText);
            }
        },
        complete: function (xhr) {
        }
    });

    /**
     * Estrutura para excluir uma questão
     */
    $(".excluir_questoes").click(function () {
        var idQuestao = $(this).attr("data-exluir-id");
        if (confirm("Deseja realmente excluir esta questão?")) {
            $.post("models/excluir-questao.php", {idqst: parseInt(idQuestao)}, function (_d) {
                if (_d === "1") {
                    alert("Questão excluída com sucesso!");
                    window.location.reload();
                } else {
                    alert("Houve um erro.\n" + _d);
                }

            });
        }
    });
    /**
     * 
     */
    $('.pesoPorQuestao').keyup(function (ev) {

        var idQuestao = $(this).attr('id');

        if (ev.keyCode == 13) {

            novoID = idQuestao.match(/\d+/gmi);

            id = "#" + novoID;

            mybtn = $('#btn-salvar-alteracao-' + novoID);

            mybtn.text("Salvando...");

            desc = CKEDITOR.instances[novoID + 'desc'].getData();
            resp_a = $(id + "resp_a").val().trim();
            resp_b = $(id + "resp_b").val().trim();
            resp_c = $(id + "resp_c").val().trim();
            resp_d = $(id + "resp_d").val().trim();
            resp_e = $(id + "resp_e").val().trim();
            peso = $(id + "peso").val().trim();
            qc = $(id + "qc").val().trim();

            var options = {
                id: idQuestao,
                desc: desc,
                resp_a: resp_a,
                resp_b: resp_b,
                resp_c: resp_c,
                resp_d: resp_d,
                resp_e: resp_e,
                peso: peso,
                qc: qc
            };

            $.post("models/atualizar-questao.php", options, function (data) {
                if (data === "1") {
                    mybtn.text("Sucesso").slideUp(300).delay(800).text("Sucesso").fadeIn(400).text("Salvar alterações");
                } else if (data == '2') {
                    alert("A operação não pôde ser realizada porque está fora do prazo para alteração.");
                    mybtn.text("Salvar alterações");
                } else {
                    mybtn.text("Salvar alterações");
                }
            });
        }
    });
    /**
     * Estrutura para salvar questão
     */
    $(".salvar_questao").click(function () {

        mybtn = $(this);

        var tipoDasQuestoes = $('#tipo-das-questoes').val();

        mybtn.text("Salvando...");

        var idQuestao = $(this).attr("data-exluir-id");

        id = "#" + idQuestao;

        desc = CKEDITOR.instances[idQuestao + 'desc'].getData();

        switch (tipoDasQuestoes) {
            case '1':
                resp_a = $(id + "resp_a").val().trim();
                resp_b = $(id + "resp_b").val().trim();
                resp_c = $(id + "resp_c").val().trim();
                resp_d = $(id + "resp_d").val().trim();
                resp_e = $(id + "resp_e").val().trim();
                peso = $(id + "peso").val().trim();
                qc = $(id + "qc").val().trim();

                file_a = $('#input_' + idQuestao + '_a').val().trim();
                file_b = $('#input_' + idQuestao + '_b').val().trim();
                file_c = $('#input_' + idQuestao + '_c').val().trim();
                file_d = $('#input_' + idQuestao + '_d').val().trim();
                file_e = $('#input_' + idQuestao + '_e').val().trim();

                var options = {
                    id: idQuestao,
                    desc: desc,
                    resp_a: resp_a,
                    resp_b: resp_b,
                    resp_c: resp_c,
                    resp_d: resp_d,
                    resp_e: resp_e,
                    peso: peso,
                    qc: qc,
                    file_a: file_a,
                    file_b: file_b,
                    file_c: file_c,
                    file_d: file_d,
                    file_e: file_e
                };
                break;
            case '2': /*Cajo seja uma questão discursiva*/
                peso = $(id + "peso").val().trim();
                var options = {
                    id: idQuestao,
                    desc: desc,
                    peso: peso,
                    qc: '1'
                };
                break;

                break;
        }
        console.log(options);

        $.post("models/atualizar-questao.php", options, function (data) {

            if (data === '0') {
                alert("Nenhum dado foi atualizado.");
                mybtn.text("Salvar alterações");
            } else if (data === "1") {
                mybtn.text("Sucesso").slideUp(300).delay(800).text("Sucesso").fadeIn(400).text("Salvar alterações");
            } else if (data === '2') {
                alert("A operação não pôde ser realizada porque está fora do prazo para alteração.");
                mybtn.text("Salvar alterações");
            } else {
                alert("Houve um erro.\nMensagem do erro:" + data);
                mybtn.text("Salvar alterações");
            }
        });

    });

    /**
     * Estrutura para alterar avaliação.
     */
    $("#form_alterar_avaliacao").ajaxForm({
        type: 'post',
        url: "models/alterar-avaliacao.php",
        beforeSend: function () {
            $('#btnSalvarAlteracao').attr('disabled', true).html('Salvando alterações, aguarde. <i class="icon-save"></i>');
        },
        success: function (data) {
            if (data == "10") {
                alert("Dados atualizados com sucesso!")
                window.location.reload();
            } else if (data == "11") {
                alert("Dados atualizados com sucesso!")
                window.location.reload();
            } else if (data == "00") {
                alert("Nenhum dados foi atualizado.");
                $('#btnSalvarAlteracao').attr('disabled', false).html('Tentar Novamente <i class="icon-save"></i>');
            } else if (data == "0") {
                alert("Nenhum dados foi atualizado.");
                $('#btnSalvarAlteracao').attr('disabled', false).html('Tentar Novamente <i class="icon-save"></i>');
            } else {
                alert(data);
                $('#btnSalvarAlteracao').attr('disabled', false).html('Tentar Novamente <i class="icon-save"></i>');
            }
        }
    });

    /**
     * Estrutura para excluir toda avaliação
     */
    $("#excluir-avaliacao-permanente").click(function () {
        var dataKey = $(this).attr("data-key");

        var lugar = $(this).attr("data-lugar");

        var avaliacaoId = parseInt($(this).attr("data-avaliacao-id"));

        $.post("models/excluir-avaliacao.php", {
            key: dataKey,
            lugar: lugar,
            avaliacao_id: avaliacaoId
        }, function (data) {
            $.post("models/apagar-sessoes-avaliativas.php");
            if (data == "1") {
                alert('Avaliação excluída com sucesso!');
                window.location.href = "./";
            } else if (data == "0") {
                alert('Não foi possível fazer a exclusão.');
            } else {
                alert('Houve um erro.\n[' + data + ']');
            }
        });
    });

    $('#selecionar_tudo').click(function () {
        $('option').prop('selected', true);
        $('#taffarel_xavier').trigger('liszt:updated');
    });

    $('#remover_tudo').click(function () {
        $('option').prop('selected', false);
        $('#taffarel_xavier').trigger('liszt:updated');
    });
    if (jQuery().datepicker) {
        $('.date-picker').datepicker({dateFormat: 'dd/mm/yyyy'});
    }

    var neoInovatHandleTables = function () {
        if (!jQuery().dataTable) {
            return;
        }
        // begin first table
        jQuery('.table-taffarel').dataTable({
            "aLengthMenu": [
                [10, 25, 50, -1],
                [5, 15, 20, "All"]
            ],
            "bOrder": [[0, 'asc'], [1, 'desc']],
            "sAjaxDataProp": "aaData",
            "sPaginationType": "bootstrap",
            "sScrollY": "500",
            "bScrollCollapse": true,
            "oSearch": {"sSearch": ""},
            "iDisplayLength": 5,
            "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
            "oLanguage": {
                "oPaginate": {
                    "sPrevious": "Anterior",
                    "sNext": "Próximo",
                    "sFirst": "Primeira página"
                },
                "sZeroRecords": "Nenhum dado encontrado",
                "sSearch": "Pesquisar",
                "sEmptyTable": "No data available in table",
                "sInfo": "Mostrando (_START_ até _END_) de _TOTAL_ registros."
            },
            "aoColumnDefs": [
                {
                    'bSortable': true,
                    'aTargets': [0]
                },
                {
                    'bSortable': false,
                    'aTargets': [6]
                }
            ],
            "aaSorting": [
                [0, 'desc']
            ]
        });
        jQuery('.table-taffarel .group-checkable').change(function () {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            jQuery(set).each(function () {
                if (checked) {
                    $(this).attr("checked", true);
                } else {
                    $(this).attr("checked", false);
                }
            });
            jQuery.uniform.update(set);
        });
    };
    neoInovatHandleTables();
});