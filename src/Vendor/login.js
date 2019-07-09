$(document).ready(function () {

    $('#carregandoPagina').show();
    
    $('#formLoginMain').slideDown();

    $('#otimicacao').modal('show');

    $('#data_nascimento').mask('##/##/####');
    /**
     * 
     */
    $("#formRecuperarSenha").ajaxForm({
        type: 'post',
        url: 'models/recuperar_senha.php',
        beforeSend: function () {
            var _svg = "<i class='fa fa-spinner fa-pulse fa-fw'></i>";
            $('#btnEnviarEmail').attr('disabled', true).html('Enviando...' + _svg);
        },
        success: function (data) {
            if (data == '1') {
                $.post('views/criar_nova_senha.php', function (d) {
                    $('#getFormsContentsRecuperacaoSenha').html(d);
                });
            } else if (data == '0') {
                $('#captcha').val('').focus();
                alert('Dados incorretos! \nVerifique os dados inseridos no formulário e tente novamente.');
                $('#btnEnviarEmail').attr('disabled', false).
                        html('<i class="m-icon-swapright m-icon-white"></i> Enviar');
                document.getElementById('captchaImg').src = 'models/captcha.php?rnd=' + Math.random();
            } else {
                $('#captcha').val('').focus();
                alert(data);
                $('#btnEnviarEmail').attr('disabled', false).
                        html('<i class="m-icon-swapright m-icon-white"></i> Enviar');
                document.getElementById('captchaImg').src = 'models/captcha.php?rnd=' + Math.random();
            }
        }
    });

    $('#btnMostrarSenha').click(function () {
        var t = $(this).attr('class');
        var _th = $(this);
        if (t == 'fa fa-eye') {
            $('#password').attr('type', 'text');
            _th.removeClass('fa fa-eye').addClass('fa fa-eye-slash');
        } else {
            $('#password').attr('type', 'password');
            _th.removeClass('fa fa-eye-slash').addClass('fa fa-eye');
        }
    }).mouseup(function () {
        /*$('#password').attr('type','password');*/
    });

    /*Atualizar o Captcha*/
    $('#btnAtualizarCaptcha').click(function () {
        $(this).text('Atualizando...');
        document.getElementById('captchaImg').src = 'models/captcha.php?rnd=' + Math.random();
        $(this).text('');
    });

    $('#file').change(function (ev) {
        if (this.value != '') {
            $('#labelHelpChangeFile').html('Alterar Arquivo');
            var fileName = this.value.split(/(\\|\/)/g).pop();
            $('#getLoginHelpNameFile').html(fileName).attr('title', fileName);
        } else {
            $('#labelHelpChangeFile').html('Escolher Arquivo');
            $('#file').val('');
        }
    });

    var selectNivel = document.getElementById("selectNivel");

    selectNivel.selectedIndex = "<?php echo $selected; ?>";

    /*Retira os valores que não sejam letras e retira os espaços do início e fim da String*/
    $("#username").blur(function () {
        if ($(this).val().length == 11) {
            selectNivel.selectedIndex = 2;

        } else {
            selectNivel.selectedIndex = 3;
        }
    });

    $('#relembrar').change(function () {
        if (this.checked == true) {
            this.value = '1';
        } else {
            this.value = '0';
        }
    });

    var handleLoginForm = function () {

        $('.login-form').validate({
            errorElement: 'label', /*default input error message container*/
            errorClass: 'help-inline', /*default input error message class*/
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                },
                nivel: {
                    required: true
                }
            },
            messages: {
                username: {
                    required: "O nome do usuário é obrigatório"
                },
                password: {
                    required: "A senha é obrigatória"
                },
                nivel: {
                    required: "Selecione seu nível"
                }
            },
            invalidHandler: function (event, validator) { /*display error alert on form submit   */
                $('.alert-error', $('.login-form')).show();
            },
            highlight: function (element) { /*hightlight error inputs*/
                $(element)
                        .closest('.control-group').addClass('error'); /*set error class to the control group*/

            },
            success: function (label) {
                label.closest('.control-group').removeClass('error');
                label.remove();
            },
            errorPlacement: function (error, element) {
                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
            },
            submitHandler: function (form) {
                var _svg = "<i class='fa fa-spinner fa-pulse fa-fw'></i>";
                $("#login-btn-entrar").attr('disabled', true).html("Entrando...." + _svg);
                $.post('contollers/submit-login.php', {
                    username: form.username.value,
                    password: form.password.value,
                    nivel: form.nivel.value,
                    key: form.key.value,
                    token: form.token.value,
                    relembrar: form.relembrar.value
                }, function (data) {
                    if (data == 'professor') {
                        window.location.href = "../" + data;
                    } else if (data == 'aluno') {
                        window.location.href = "../" + data;
                    } else if (data == 'administrativo') {
                        window.location.href = "../" + data;
                    } else {
                        $.gritter.add({
                            title: 'CEMVS-Alerta',
                            text: data,
                            fade_out_speed: 1000,
                            time: 3000,
                            class_name: 'gritter-light gritter-center'
                        });
                        $("#login-btn-entrar").attr('disabled', false).html("Entrar");
                        return false;
                    }
                });
            }
        });

        /*Formulário de ajuda*/

        (function () {

            var bar = $('.bar');
            var percent = $('.percent');
            var status = $('#status');

            $('#formLoginAjuda').ajaxForm({
                type: 'post',
                url: 'models/ajuda.php',
                beforeSend: function () {
                    $('#btnFormAjudaLogin').attr('disabled', true).html('Enviando,aguarde...');
                    status.empty();
                    var percentVal = '0%';
                    bar.width(percentVal);
                    percent.html(percentVal);
                },
                uploadProgress: function (event, position, total, percentComplete) {
                    var percentVal = percentComplete + '%';
                    bar.width(percentVal);
                    percent.html(percentVal);
                },
                success: function (data) {
                    var percentVal = '100%';
                    bar.width(percentVal);
                    percent.html(percentVal);
                    if (data == '1') {
                        $('#btnFormAjudaLogin').attr('disabled', false).html('Enviando com sucesso!');
                        alert('Formulário enviado com sucesso!');
                        jQuery('#modalErroLogin').hide();
                        jQuery('.login-form').show();
                        jQuery('.forget-form').hide();
                        $('#username').focus();
                    } else {
                        $('#btnFormAjudaLogin').attr('disabled', false).html('Tentar Novamente');
                        alert('Houve um erro!' + data);
                    }
                },
                complete: function (xhr) {
                    if (xhr.responseText == '1') {
                        /*status.html('Enviado com sucesso!');*/
                    } else {
                        status.html('Ops, houve um erro!' + xhr.responseText);
                    }
                }
            });

        })();

        $('#btnFecharModalLogin,.btnClassFecharModalLogin').click(function () {
            jQuery('#modalErroLogin').hide();
            jQuery('.login-form').show();
            jQuery('.forget-form').hide();
            $('#username').focus();
        });

        $('#btnModalErroLogin').click(function () {
            jQuery('.login-form').hide();
            jQuery('.forget-form').hide();
            jQuery('#modalErroLogin').show();
            $('#e-email').focus();
        });

        jQuery('#forget-password').click(function () {
            jQuery('.login-form').hide();
            jQuery('.forget-form').show();
            $('#getFormsContentsRecuperacaoSenha').show();
            $('#e-email').focus();
        });

        jQuery('#back-btn').click(function () {
            jQuery('.login-form').show();
            jQuery('.forget-form').hide();
        });

        /*Faz o login pressionando ENTER do teclado*/
        $('.login-form input').keypress(function (e) {
            if (e.keyCode == 13) {
                $('.login-form').submit();
                return false;
            }
        });
    };
    handleLoginForm();
    $('#carregandoPagina').hide();
});