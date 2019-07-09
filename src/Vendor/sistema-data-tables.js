$(document).ready(function () {
    if (!jQuery().dataTable) {
        return;
    }

    /**
     * Essa função serve para paginar as tabelas do sistema.
     * @param {type} selector
     * @returns {undefined}
     */
    var dataTablesSistema = function (selector) {

        $(selector).dataTable({
            "aLengthMenu": [
                [10, 25, 50, -1],
                [5, 15, 20, "All"]
            ],
            "sAjaxDataProp": "aaData",
            "sPaginationType": "bootstrap",
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
                "sEmptyTable": "Nenhum dado foi encontrado na tabela",
                "sInfo": "Mostrando (_START_ até _END_) de _TOTAL_ registros."
            }
        });
    };

    /**
     * Aqui eu paginei essas duas tabelas:
     */
    dataTablesSistema('#alunosPorTurmaDT');
    dataTablesSistema('#table147');
    dataTablesSistema('#todosAlunosTable');
    dataTablesSistema('.data-table-atividades-extras');
    dataTablesSistema('#tableAulasArquivos');
    dataTablesSistema('#tableComunicadosAdministrativos');

});