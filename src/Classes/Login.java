/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Taffrel Xavier <taffarel_deus@hotmail.com>
 */
public class Login {

    private static String tableName = "";

    /**
     *
     * @return
     */
    protected static String getTableName() {
        return tableName;
    }

    /**
     *
     * @param tableName
     */
    protected static void setTableName(String tableName) {
        Login.tableName = tableName;
    }

    /**
     *
     * @return
     */
    protected static String header() {
        return "<?php\n\nclass Login {\n"
                + "\n"
                + "    private $conexao = null;\n"
                + "    \n"
                + "    private $tableName = '" + getTableName() + "';\n"
                + "\n"
                + "    public function __construct($connection) {\n"
                + "        $this->conexao = $connection;\n"
                + "    }";
    }

    /**
     *
     * @return
     */
    protected static String criarSenha() {

        String codigo = "public function criarSenha($novaSenha) {\n"
                + "        return password_hash($novaSenha,PASSWORD_BCRYPT);\n"
                + "    }";
        return codigo;
    }

    /**
     *
     * @param nomeDaTabela
     * @param jTable1
     * @return
     */
    protected static String verificarSenha(String nomeDaTabela, JTable jTable1) {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        String colunaNomeUsuario = model.getValueAt(0, 1).toString();

        String colunaSenha = model.getValueAt(1, 1).toString();

        String textoCodigo = "public function fazerLogin($user_name, $senha) {\n"
                + "        try {\n"
                + "\n"
                + "            $sth = $this->conexao->prepare('SELECT * FROM ' . $this->tableName . ' WHERE `" + colunaNomeUsuario + "` = ?;');\n"
                + "\n"
                + "            $sth->bindParam(1, $user_name, PDO::PARAM_STR);\n"
                + "\n"
                + "            $sth->execute();\n"
                + "            \n"
                + "            $dados = $sth->fetch(PDO::FETCH_ASSOC);\n"
                + "            \n"
                + "            return  password_verify($senha, $dados['" + colunaSenha + "']);\n"
                + "            \n"
                + "        } catch (Exception $exc) {\n"
                + "            echo $exc->getMessage();\n"
                + "        }\n"
                + "    }";

        return textoCodigo;
    }

    /**
     *
     * @return
     */
    protected static String footer() {
        return "\n}\n";
    }

    /**
     *
     * @param nomeDaTabela
     * @param jTable1
     * @return
     */
    public static String salvar(String nomeDaTabela, JTable jTable1) {
        setTableName(nomeDaTabela);
        return header() + criarSenha() + verificarSenha(nomeDaTabela, jTable1) + footer();
    }
}
