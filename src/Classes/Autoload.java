/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Taffrel Xavier <taffarel_deus@hotmail.com>
 */
public class Autoload {

    public static String criarAutoload() {

        return "<?php\n"
                + "\n"
                + "ob_start();\n"
                + "\n"
                + "error_reporting(1);\n"
                + "\n"
                + "date_default_timezone_set('America/Araguaina');\n"
                + "\n"
                + "$expl = explode(DIRECTORY_SEPARATOR, dirname(__FILE__));\n"
                + "\n"
                + "$raiz_do_site = $expl[count($expl) - 1];\n"
                + "\n"
                + "/**\n"
                + " * <p>Função para carregar as classes</p>\n"
                + " * @param type $classname\n"
                + " */\n"
                + "function TXAutoLoad($classname)\n"
                + "{\n"
                + "    global $raiz_do_site;\n"
                + "    //Can't use __DIR__ as it's only in PHP 5.3+\n"
                + "    $_s = DIRECTORY_SEPARATOR;\n"
                + "\n"
                + "    //Para incluir alguma classe, é só incluir no final do array o nome da pasta onde ela está encontrada. \"$dir_array\"\n"
                + "    $dir_array = array(\n"
                + "        'classes'\n"
                + "    );\n"
                + "\n"
                + "    foreach ($dir_array as $value)\n"
                + "    {\n"
                + "        //Insere as classes do sistema\n"
                + "        $filename = dirname(__DIR__) . $_s . $raiz_do_site . $_s . $value . $_s . \"class.\" . strtolower($classname) . '.php';\n"
                + "\n"
                + "        if (is_readable($filename))\n"
                + "        {\n"
                + "            require $filename;\n"
                + "        }\n"
                + "    }\n"
                + "}\n"
                + "\n"
                + "if (version_compare(PHP_VERSION, '5.1.2', '>='))\n"
                + "{\n"
                + "    if (version_compare(PHP_VERSION, '5.3.0', '>='))\n"
                + "    {\n"
                + "        spl_autoload_register('TXAutoLoad', true, true);\n"
                + "    } else\n"
                + "    {\n"
                + "        spl_autoload_register('TXAutoLoad');\n"
                + "    }\n"
                + "} else\n"
                + "{\n"
                + "\n"
                + "    function __autoload($classname)\n"
                + "    {\n"
                + "        TXAutoLoad($classname);\n"
                + "    }\n"
                + "\n"
                + "}\n"
                + "//Se a sessão não estive iniciada, então inicia-se.\n"
                + "if (session_status() == PHP_SESSION_NONE)\n"
                + "{\n"
                + "    session_start();\n"
                + "    //session_regenerate_id();\n"
                + "}\n"
                + "\n"
                + "include 'config.ini.php';\n"
                + "";

    }

}
