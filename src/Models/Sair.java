/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Taffrel Xavier <taffarel_deus@hotmail.com>
 */
public class Sair {

    public static String sair() {
        return "<?php\n"
                + "session_start();"
                + "unset($_SESSION[\"is_conectado\"]);\n"
                + "unset($_SESSION[\"login_token\"]);\n"
                + "unset($_SESSION[\"avaliacao_sec\"]);\n"
                + "session_regenerate_id();\n"
                + "session_destroy();\n"
                + "header('location: ../');";
    }
}
