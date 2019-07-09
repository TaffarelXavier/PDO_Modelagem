//$("#dada_fim").mask('00/00/0000', {placeholder: "__/__/____"});
//$("#modal-progresso").modal("show");
$(document).ready(function () {

    jQuery.validator.addMethod("padrao_data", function (value, element) {
        return this.optional(element) || /(^(((0[1-9]|[12][0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/.test(value);
    }, "Insira uma data no padrão de data dd/MM/yyyy");

    $(".enviar").click(function () {
        $('#' + $(this).attr("data-form")).validate({
            errorElement: 'label', //default input error message container
            errorClass: 'help-inline', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                mensagem: {
                    required: true
                },
                hangout_id: {
                    required: true
                },
                disciplina_id: {
                    required: true
                }
            },
            messages: {
                mensagem: {
                    required: "Este campo é obrigatório"
                },
                hangout_id: {
                    required: "Este campo é obrigatório"
                },
                disciplina_id: {
                    required: "Este campo é obrigatório"
                }
            },
            submitHandler: function (form) {
                $.post("models/inserir-nova-mensagem.php", {
                    mensagem: form.mensagem.value,
                    hangout_id: form.hangout_id.value,
                    disciplina_id: form.disciplina_id.value
                }, function (data) {
                    if (data == "1") {
                        window.location.reload();
                    }
                    else if (data == "0") {
                        //alert("Falha");
                    }
                });
            }
        });
    });
});