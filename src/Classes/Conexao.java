package Classes;

//import tx.utilitarios.TXProperties;

/**
 *
 * @author Taffrel Xavier <taffarel_deus@hotmail.com>
 */
public class Conexao {

    public static String salvar() {

        String codigo = "<?php\n"
                + "\n"
                + "/**\n"
                + " * Classe de conexão ao banco de dados usando PDO no padrão Singleton.\n"
                + " * Modo de Usar:\n"
                + " * require_once './Conexao.class.php';\n"
                + " * $db = Conexao::conexao();\n"
                + " * E agora use as funções do PDO (prepare, query, exec) em cima da variável $db.\n"
                + " */\n"
                + "class Conexao {\n"
                + "\n"
                + "    // Variável que guarda a conexão PDO.\n"
                + "    protected static $db;\n"
                + "\n"
                + "    // Private construct - garante que a classe só possa ser instanciada internamente.\n"
                + "    private function __construct() {\n"
                + "        $db_driver = \"mysql\";\n"
                + "        // Informações sobre o sistema:\n"
                + "        $sistema_titulo = \"Nome do Sistema\";\n"
                + "        $sistema_email = \"alguem@gmail.com\";\n"
                + "        try {\n"
                + "            // Atribui o objeto PDO à variável $db.\n"
                + "            self::$db = new PDO(\"$db_driver:host=\" . DB_HOST . \"; dbname=\" . DB_NAME, DB_USER, DB_PASSWORD);\n"
                + "            // Garante que o PDO lance exceções durante erros.\n"
                + "            self::$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);\n"
                + "            // Garante que os dados sejam armazenados com codificação UFT-8.\n"
                + "            self::$db->exec('SET NAMES utf8');\n"
                + "        } catch (PDOException $e) {\n"
                + "            // Envia um e-mail para o e-mail oficial do sistema, em caso de erro de conexão.\n"
                + "            mail($sistema_email, \"PDOException em $sistema_titulo\", $e->getMessage());\n"
                + "            // Então não carrega nada mais da página.\n"
                + "            die(\"Connection Error: \" . $e->getMessage());\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    // Método estático - acessível sem instanciação.\n"
                + "    public static function conn() {\n"
                + "        // Garante uma única instância. Se não existe uma conexão, criamos uma nova.\n"
                + "        if (!self::$db) {\n"
                + "            new Conexao();\n"
                + "        }\n"
                + "        // Retorna a conexão.\n"
                + "        return self::$db;\n"
                + "    }\n"
                + "\n"
                + "}\n"
                + "";

        return codigo;
    }
}
