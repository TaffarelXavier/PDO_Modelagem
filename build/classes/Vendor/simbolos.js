
/**
 * <p>EmotIcons</p>
 * @author Taffarel Xavier
 * @type type
 */
var EmotIconsApp = {
    /**
     * 
     * @param {type} caminhoBasicoPasta
     * @returns {undefined}
     */
    initApp: function(caminhoBasicoPasta, caminhoIcones) {
        var a = ['←', '↑', '→', '↓', '↔', '↕', '↖', '↗', '↘', '↙', '↚', '↛', '↜', '↝', '↞', '↟', '↠', '↡', '↢', '↣', '↤', '↥', '↦', '↧', '↨', '↩', '↪', '↫', '↬', '↭', '↮', '↯', '↰', '↱', '↲', '↳', '↴', '↵', '↶', '↷', '↸', '↹', '↺', '↻', '↼', '↽', '↾', '↿', '⇀', '⇁', '⇂', '⇃', '⇄', '⇅', '⇆', '⇇', '⇈', '⇉', '⇊', '⇋', '⇌', '⇍', '⇎', '⇏', '⇐', '⇑', '⇒', '⇓', '⇔', '⇕', '⇖', '⇗', '⇘', '⇙', '⇚', '⇛', '⇜', '⇝', '⇞', '⇟', '⇠', '⇡', '⇢', '⇣', '⇤', '⇥', '⇦', '⇧', '⇨', '⇩', '⇪'];

        var b = ['∀', '∁', '∂', '∃', '∄', '∅', '∆', '∇', '∈', '∉', '∊', '∋', '∌', '∍', '∎', '∏', '∐', '∑', '−', '∓', '∔', '∕', '∖', '∗', '∘', '∙', '√', '∛', '∜', '∝', '∞', '∟', '∠', '∡', '∢', '∣', '∤', '∥', '∦', '∧', '∨', '∩', '∪', '∫', '∬', '∭', '∮', '∯', '∰', '∱', '∲', '∳', '∴', '∵', '∶', '∷', '∸', '∹', '∺', '∻', '∼', '∽', '∾', '∿', '≀', '≁', '≂', '≃', '≄', '≅', '≆', '≇', '≈', '≉', '≊', '≋', '≌', '≍', '≎', '≏', '≐', '≑', '≒', '≓', '≔', '≕', '≖', '≗', '≘', '≙', '≚', '≛', '≜', '≝', '≞', '≟', '≠', '≡', '≢', '≣', '≤', '≥', '≦', '≧', '≨', '≩', '≪', '≫', '≬', '≭', '≮', '≯', '≰', '≱', '≲', '≳', '≴', '≵', '≶', '≷', '≸', '≹', '≺', '≻', '≼', '≽', '≾', '≿', '⊀', '⊁', '⊂', '⊃', '⊄', '⊅', '⊆', '⊇', '⊈', '⊉', '⊊', '⊋', '⊌', '⊍', '⊎', '⊏', '⊐', '⊑', '⊒', '⊓', '⊔', '⊕', '⊖', '⊗', '⊘', '⊙', '⊚', '⊛', '⊜', '⊝', '⊞', '⊟', '⊠', '⊡', '⊢', '⊣', '⊤', '⊥', '⊦', '⊧', '⊨', '⊩', '⊪', '⊫', '⊬', '⊭', '⊮', '⊯', '⊰', '⊱', '⊲', '⊳', '⊴', '⊵', '⊶', '⊷', '⊸', '⊹', '⊺', '⊻', '⊼', '⊽', '⊾', '⊿', '⋀', '⋁', '⋂', '⋃', '⋄', '⋅', '⋆', '⋇', '⋈', '⋉', '⋊', '⋋', '⋌', '⋍', '⋎', '⋏', '⋐', '⋑', '⋒', '⋓', '⋔', '⋕', '⋖', '⋗', '⋘', '⋙', '⋚', '⋛', '⋜', '⋝', '⋞', '⋟', '⋠', '⋡', '⋢', '⋣', '⋤', '⋥', '⋦', '⋧', '⋨', '⋩', '⋪', '⋫', '⋬', '⋭', '⋮', '⋯', '⋰', '⋱'];

        var c = ['╪', '╫', '╬', '╭', '╮', '╯', '╰', '╱', '╲', '╳', '╴', '╵', '╶', '╷', '╸', '╹', '╺', '╻', '╼', '╽', '╾', '╿', ']', '℀', '℁', 'ℂ', '℃', '℄', '℅', '℆', 'ℇ', '℈', '℉', 'ℊ', 'ℋ', 'ℌ', 'ℍ', 'ℎ', 'ℏ', 'ℐ', 'ℑ', 'ℒ', 'ℓ', '℔', 'ℕ', '№', '℗', '℘', 'ℙ', 'ℚ', 'ℛ', 'ℜ', 'ℝ', '℞', '℟', '℠', '℡', '™', '℣', 'ℤ', '℥', 'Ω', '℧', 'ℨ', '℩', 'K', 'Å', 'ℬ', 'ℭ', '℮', 'ℯ', 'ℰ', 'ℱ', 'Ⅎ', 'ℳ', 'ℴ', 'ℵ', 'ℶ', 'ℷ', 'ℸ', '⅍', 'ⅎ', '▀', '▁', '▂', '▃', '▄', '▅', '▆', '▇', '█', '▉', '▊', '▋', '▌', '▍', '▎', '▏', '▐', '░', '▒', '▓', '▔', '▕', '■', '□', '▢', '▣', '▤', '▥', '▦', '▧', '▨', '▩', '▪', '▫', '▬', '▭', '▮', '▯', '▰', '▱', '▲', '△', '▴', '▵', '▶', '▷', '▸', '▹', '►', '▻', '▼', '▽', '▾', '▿', '◀', '◁', '◂', '◃', '◄', '◅', '◆', '◇', '◈', '◉', '◊', '○', '◌', '◍', '◎', '●', '◐', '◑', '◒', '◓', '◔', '◕', '◖', '◗', '◘', '◙', '◚', '◛', '◜', '◝', '◞', '◟', '◠', '◡', '◢', '◣', '◤', '◥', '◦', '◧', '◨', '◩', '◪', '◫', '◬', '◭', '◮', '◯'];

        var d = ['=)', ' :\'( ', '=(', '☀', '☁', '☂', '☃', '☄', '★', '☆', '☇', '☈', '☉', '☊', '☋', '☌', '☍', '☎', '☏', '☐', '☑', '☒', '☓', '☖', '☗', '☚', '☛', '☜', '☝', '☞', '☟', '☠', '☡', '☢', '☣', '☤', '☥', '☦', '☧', '☨', '☩', '☪', '☫', '☬', '☭', '☮', '☯', '☰', '☱', '☲', '☳', '☴', '☵', '☶', '☷', '☸', '☹', '☺', '☻', '☼', '☽', '☾', '☿', '♀', '♁', '♂', '♃', '♄', '♅', '♆', '♇', '♈', '♉', '♊', '♋', '♌', '♍', '♎', '♏', '♐', '♑', '♒', '♓', '♔', '♕', '♖', '♗', '♘', '♙', '♚', '♛', '♜', '♝', '♞', '♟', '♠', '♡', '♢', '♣', '♤', '♥', '♦', '♧', '♨', '♩', '♪', '♫', '♬', '♭', '♮', '♯', '✁', '✂', '✃', '✄', '✅', '✆', '✇', '✈', '✉', '✊', '✋', '✌', '✍', '✎', '✏', '✐', '✑', '✒', '✓', '✔', '✕', '✖', '✗', '✘', '✙', '✚', '✛', '✜', '✝', '✞', '✟', '✠', '✡', '✢', '✣', '✤', '✥', '✦', '✧', '✨', '✩', '✪', '✫', '✬', '✭', '✮', '✯', '✰', '✱', '✲', '✳', '✴', '✵', '✶', '✷', '✸', '✹', '✺', '✻', '✼', '✽', '✾', '✿', '❀', '❁', '❂', '❃', '❄', '❅', '❆', '❇', '❈', '❉', '❊', '❋', '❌', '❍', '❎', '❏', '❐', '❑', '❒', '❖', '❗', '❘', '❙', '❚', '❛', '❜', '❝', '❞', '❟', '❠', '❡', '❢', '❣', '❤', '❥', '❦', '❧', '❶', '❷', '❸', '❹', '❺', '❻', '❼', '❽', '❾', '❿', '➀', '➁', '➂', '➃', '➄', '➅', '➆', '➇', '➈', '➉', '➊', '➋', '➌', '➍', '➎', '➏', '➐', '➑', '➒', '➓', '➔', '➘', '➙', '➚', '➛', '➜', '➝', '➞', '➟', '➠', '➡', '➢', '➣', '➤', '➥', '➦', '➧', '➨', '➩', '➪', '➫', '➬', '➭', '➮', '➯', '➱', '➲', '➳', '➴', '➵', '➶', '➷', '➸', '➹', '➺', '➻', '➼', '➽', '➾'];

        /*"blue" aqui é o nome da pasta*/
        var _blues = ['emojibluemini emojibluemin1', 'emojibluemini emojibluemin2', 'emojibluemini emojibluemin3', 'emojibluemini emojibluemin4', 'emojibluemini emojibluemin5', 'emojibluemini emojibluemin6', 'emojibluemini emojibluemin7', 'emojibluemini emojibluemin8', 'emojibluemini emojibluemin9', 'emojibluemini emojibluemin10', 'emojibluemini emojibluemin11', 'emojibluemini emojibluemin12', 'emojibluemini emojibluemin13', 'emojibluemini emojibluemin14', 'emojibluemini emojibluemin15', 'emojibluemini emojibluemin16', 'emojibluemini emojibluemin17', 'emojibluemini emojibluemin18', 'emojibluemini emojibluemin19', 'emojibluemini emojibluemin20', 'emojibluemini emojibluemin21', 'emojibluemini emojibluemin22', 'emojibluemini emojibluemin23', 'emojibluemini emojibluemin24', 'emojibluemini emojibluemin25', 'emojibluemini emojibluemin26', 'emojibluemini emojibluemin27', 'emojibluemini emojibluemin28', 'emojibluemini emojibluemin29', 'emojibluemini emojibluemin30', 'emojibluemini emojibluemin31', 'emojibluemini emojibluemin32', 'emojibluemini emojibluemin33', 'emojibluemini emojibluemin34', 'emojibluemini emojibluemin35', 'emojibluemini emojibluemin36', 'emojibluemini emojibluemin37', 'emojibluemini emojibluemin38'];

        var _array = ['ftxbemoji icnblck1', 'ftxbemoji icnblck2', 'ftxbemoji icnblck3', 'ftxbemoji icnblck4', 'ftxbemoji icnblck5', 'ftxbemoji icnblck6', 'ftxbemoji icnblck7', 'ftxbemoji icnblck8', 'ftxbemoji icnblck9', 'ftxbemoji icnblck10', 'ftxbemoji icnblck11', 'ftxbemoji icnblck12', 'ftxbemoji icnblck13', 'ftxbemoji icnblck14', 'ftxbemoji icnblck15', 'ftxbemoji icnblck16', 'ftxbemoji icnblck17', 'ftxbemoji icnblck18', 'ftxbemoji icnblck19', 'ftxbemoji icnblck20', 'ftxbemoji icnblck21', 'ftxbemoji icnblck22', 'ftxbemoji icnblck23', 'ftxbemoji icnblck24', 'ftxbemoji icnblck25', 'ftxbemoji icnblck26', 'ftxbemoji icnblck27', 'ftxbemoji icnblck28', 'ftxbemoji icnblck29', 'ftxbemoji icnblck30'];


        /*  Setas: a,
         Letras: b,
         Simbolos: c,
         Matematicos: d*/
        var TX_EmotIcons = {
            Blues: _blues,
            Blacks: _array
        };

        var i1 = i2 = 0;

        var resultado = '<ul class="nav nav-tabs" role="tablist" style="margin-bottom: 0px;">';

        /*Pega o Título*/
        for (var i in TX_EmotIcons) {

            ++i1;
            var _act = '';
            if (i1 === 1) {
                //_act = 'active';
            }
            if (TX_EmotIcons.hasOwnProperty(i)) {
                resultado += '<li role="presentation" title="Dê um duplo clique para fechar/abrir." class="' + _act + ' closeTabs"><a href="#tab-' + i + '" aria-controls="home" role="tab" data-toggle="tab" style="cursor:pointer;"><b>' + i + '</b></a></li>';
            }

        }
        resultado += '</ul><div class="tab-content" style="height: auto;overflow:auto;">';

        for (var i in TX_EmotIcons) {
            ++i2;

            var _active = '';

            if (i2 === 1) {
                /*_active = 'active';*/
            }
            if (TX_EmotIcons.hasOwnProperty(i)) {

                var dss = '';

                TX_EmotIcons[i].forEach(function(ev, r) {

                    var regexs = [/emoji\semoji/gmi, /(emoji\s)/gmi, /emojibluemin/gmi];

                    if (regexs[1].test(ev) == true || regexs[2].test(ev) == true) {
                        dss += '<img class="' + ev + '" src="" alt="alt" data-emoji="' + ev + '" />';
                    }
                    else {
                        dss += '<span class="simbolos-smiles">' + ev + '</span>';
                    }

                });
                resultado += '<div role="tabpanel" class="tab-pane txtabs ' + _active + '" id="tab-' + i + '">' + dss + '</div>';
            }
        }
        resultado += '</div>';

        $('#emoctions').html(resultado);

        $('.closeTabs').dblclick(function() {
            $('.txtabs').removeClass('active');
        });

        textoPrev = undefined;

        $('.simbolos-smiles').click(function() {
            var emoji = $(this).attr('data-emoji');
            Publicacoes.elementInsert('#TX_editor', '&nbsp;<span class="simbolos-smiles" contenteditable="false">' + $(this).text() + '</span>&nbsp;');
        });

        /*Black*/
        $('.ftxbemoji,.emojibluemini').click(function() {
            var emoji = $(this).attr('data-emoji');
            Publicacoes.elementInsert('#TX_editor', '&nbsp;<img class="' + emoji + '" src="" alt="" >&nbsp;');
        });
    }
};