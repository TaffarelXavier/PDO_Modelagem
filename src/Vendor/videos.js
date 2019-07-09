$(document).ready(function() {

    //O visualizador no modal
    var videoVisualizador = document.getElementById("videoView");

    //Play no vídeo
    $(".playVideo").click(function() {
        myId = $(this).attr("data-id");
        var video = document.getElementById("video-" + myId);
        if (video.paused) {
            video.play();
            $(this).find("i").removeClass("icon-play").addClass("icon-pause");
        } else {
            video.pause();
            $(this).find("i").removeClass("icon-pause").addClass("icon-play");
        }
    });

    //Stop vídeo
    $(".stopVideo").click(function() {
        var dataStopId = $(this).attr("data-id");
        var video = document.getElementById("video-" + dataStopId);
        video.currentTime = 0;
        video.pause();
        $("#play-" + dataStopId).find("i").removeClass("icon-pause").addClass("icon-play");
    });

    $(".abrir_video").click(function() {

        var dataId = $(this).attr('data-id');
        var video = document.getElementById('video-' + dataId + '');
        video.pause();
        var dataSource = $(this).attr("data-source");
        var titulo = $(this).attr("data-title");
        var descrição = $(this).attr("data-descricao");
        $('.visualizar-detalhes').html(descrição);
        //O id do H1 do modal onde será mostrado o título do vídeo.
        var neo_Dfb_787 = document.getElementById("neo_Dfb_787");
        neo_Dfb_787.innerText = titulo;
        videoVisualizador.src = dataSource;
        videoVisualizador.play();
    });


    $("button[data-dismiss='modal'").click(function() {
        videoVisualizador.pause();
    });


    //Ao clica na tag vídeo executa-o
    $("#videoVisualizador").click(function() {
        if (videoVisualizador.paused == false) {
            videoVisualizador.play();
        } else {
            videoVisualizador.pause();
        }
    });

    $('.videos').click(function() {
        if (this.paused == false) {
            this.pause();
        } else {
            this.play();
        }
    });
    //Ao clica na tag vídeo executa-o
    $("video").click(function() {
        var _video = this.id;
        var i = _video.split("-");
        if (this.paused) {
            this.play();
            $("#play-" + i[1]).find("i").removeClass("icon-play").addClass("icon-pause");
        } else {
            this.pause();
            $("#play-" + i[1]).find("i").removeClass("icon-pause").addClass("icon-play");
        }
    });


    /**
     * Impede do usuário usar acessar ao menu de contexto
     */
    $(this).contextmenu(function() {
        //return false;
    });

    /**
     * Impede do usuário usar a tecla CTRL+U para vê o código fonte
     */
    $(this).keydown(function(e) {
        if (e.ctrlKey == true) {
            //return false;
        }
    });
});
