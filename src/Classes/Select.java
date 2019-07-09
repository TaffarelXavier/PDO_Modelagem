package Classes;

/**
 *
 * @author Taffrel Xavier <taffarel_deus@hotmail.com>
 */
public class Select {

    public static String insert() {
        String codigo = " public function insert() {\n"
                + "        try {\n"
                + "            $this->conexao->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);\n"
                + "\n"
                + "            $stmt = $this->conexao->prepare(\"INSERT INTO `boletos` (`boleto_id`, `boleto_file_md5`, \"\n"
                + "                    . \"`boleto_file_realname`, `boleto_cliente_fk_id`, `boleto_pago`) VALUES (NULL,?,?,?,'nao');\");\n"
                + "            $stmt->bindParam(1, $file_md5, PDO::PARAM_STR);\n"
                + "            $stmt->bindParam(2, $file_realname, PDO::PARAM_STR);\n"
                + "            $stmt->bindParam(3, $cliente_id, PDO::PARAM_INT);\n"
                + "            $stmt->execute();\n"
                + "            return (int) $stmt->rowCount();\n"
                + "        } catch (Exception $exc) {\n"
                + "            echo $exc->getMessage();\n"
                + "        }\n"
                + "    }\n"
                + "";
        return codigo;
    }

    public static String select() {

        String codigo = "\n\n    public function select($param1) {\n"
                + "        try {\n"
                + "\n"
                + "            $sth = $this->conexao->prepare('SELECT * FROM ' . $this->tableName);\n"
                + "            $sth->bindParam(1, $param1, PDO::PARAM_STR);\n"
                + "\n"
                + "            $sth->execute();\n"
                + "\n"
                + "            return $sth;\n"
                + "        } catch (Exception $exc) {\n"
                + "            echo $exc->getTraceAsString();\n"
                + "        }\n"
                + "    }";
        return codigo;

    }

    public static String delete() {

        String codigo = "\n\npublic function delete($param1) {\n"
                + "\n"
                + "        $sth = $this->conexao->prepare('DELETE FROM '.$this->tableName.' WHERE ');\n"
                + "\n"
                + "        $sth->bindValue(2, $param1, PDO::PARAM_INT);\n"
                + "\n"
                + "        $sth->execute();\n"
                + "\n"
                + "        return (int) $sth->rowCount();\n"
                + "    }"
                + "\n";
        return codigo;

    }

    /**
     *
     * @return type
     */
    public static String update() {
        String codigo = "  public function update($param1) {\n"
                + "        try {\n"
                + "\n"
                + "            $sth = $this->conexao->prepare('UPDATE `'.$this->tableName.'` SET `col` = ? WHERE ?;');\n"
                + "\n"
                + "            $sth->bindParam(1, $param1, PDO::PARAM_INT);\n"
                + "\n"
                + "            $sth->execute();\n"
                + "\n"
                + "            return (int) $sth->rowCount();\n"
                + "        } catch (Exception $exc) {\n"
                + "            echo $exc->getTraceAsString();\n"
                + "        }\n"
                + "    }\n";
        return codigo;
    }

}
