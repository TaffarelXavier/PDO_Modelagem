/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 * <p>
 * Cria um arquivo Index dentro da pasta root.</p>
 *
 * @author Taffrel Xavier <taffarel_deus@hotmail.com>
 */
public class IndexPHP {

    public static String salvar(String titulo, String formulario) {
        return "<?php\n"
                + "include 'autoload.php';\n"
                + "?>\n"
                + "<!DOCTYPE html>\n"
                + "<html lang=\"pt-br\">\n"
                + "    <head>\n"
                + "        <meta charset=\"utf-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> \n"
                + "        <title>" + titulo + "</title>\n"
                + "       <script src=\"assets/js/jquery.js\"></script>"
                + "       <script src=\"assets/js/jquery.form.js\"></script>"
                + "    </head>\n"
                + "    <body>\n"
                + "        <p>Tabela(s) do banco <b><mark><?php echo DB_NAME; ?></mark></b>:</p>\n"
                + "        <?php \n"
                + "        $st = $connection->prepare(\"show tables;\");\n"
                + "        $st->execute();\n"
                + "        print_r($st->fetch(PDO::FETCH_ASSOC));\n"
                + formulario
                + "        ?>"
                + "</body>\n"
                + "</html>";
    }
}
