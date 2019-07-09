package Classes;

import Funcoes.Funcoes;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Taffrel Xavier <taffarel_deus@hotmail.com>
 */
public class CriarProjeto {

    private static String projectXml(String nomeDoProjeto) {

        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<project xmlns=\"http://www.netbeans.org/ns/project/1\">\n"
                + "    <type>org.netbeans.modules.php.project</type>\n"
                + "    <configuration>\n"
                + "        <data xmlns=\"http://www.netbeans.org/ns/php-project/1\">\n"
                + "            <name>" + nomeDoProjeto + "</name>\n"
                + "        </data>\n"
                + "    </configuration>\n"
                + "</project>\n"
                + "";
    }

    private static String projectProperties() {

        return "include.path=${php.global.include.path}\n"
                + "php.version=PHP_56\n"
                + "source.encoding=UTF-8\n"
                + "src.dir=.\n"
                + "tags.asp=false\n"
                + "tags.short=false\n"
                + "web.root=.";

    }

    /**
     *
     * @param nomeDoProjeto
     * <p>
     * O nome que vai aparecer no navegador, isto é, o domínio:</p>
     * Por exemplo: http://localhost/nomedapasta
     * @return
     */
    private static String privateProperties(String nomeDoProjeto) {

        return "copy.src.on.open=false\n"
                + "run.as=LOCAL\n"
                + "url=http://localhost/" + nomeDoProjeto + "\n"
                + "";

    }

    /**
     *
     * @param caminhoRaiz <p style="font-weight:900;">O caminho do projeto</p>
     * @param nomeDoProjeto
     * @return
     */
    public static boolean criarProjeto(String caminhoRaiz, String nomeDoProjeto) {

        if (nomeDoProjeto.equals("")) {  //Nome do projeto
            JOptionPane.showMessageDialog(null, "Digite o nome do projeto.", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            return false;
        } else if (caminhoRaiz.equals("")) {//Raiz do projeto
            JOptionPane.showMessageDialog(null, "Digite o caminho da raiz do projeto.", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            return false;
        } else {

            //Primeiro cria a pasta do projeto
            File diretorio = new File(caminhoRaiz); // ajfilho é uma pasta!

            if (!diretorio.exists()) {

                if (diretorio.mkdirs()) {
                    JOptionPane.showMessageDialog(null, "Nome do projeto criado: " + nomeDoProjeto + "\n"
                            + "Raiz do projeto:" + caminhoRaiz + "\n"
                            + "Criado com sucesso!",
                            "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                    criarSubPastas(caminhoRaiz, nomeDoProjeto);
                    return true;
                }

            } else {

                int dialogResult = JOptionPane.showConfirmDialog(null, "O diretório " + caminhoRaiz + " já existe,\ndeseja sobreescrevê-lo?\n",
                        "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (dialogResult == JOptionPane.YES_OPTION) {

                    diretorio.mkdir();

                    boolean dir = diretorio.exists();

                    if (dir == true) { //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.
                        criarSubPastas(caminhoRaiz, nomeDoProjeto);
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "YSAasdfsda", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    return false;
                }

            }
        }
        return false;
    }

    /**
     *
     * @param caminhoRaiz
     * @param nomeDoProjeto
     */
    private static void criarSubPastas(String caminhoRaiz, String nomeDoProjeto) {
        //Primeiro cria a pasta 'nbproject', que é usada para o netbeans

        File nbproject = new File(caminhoRaiz + "\\nbproject"); // ajfilho é uma pasta!

        if (!nbproject.exists()) {

            nbproject.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.
        }

        //Cria o arquivo 'project.xml'
        Funcoes.salvar(nbproject.getAbsolutePath(), "project.xml", projectXml(nomeDoProjeto));
        Funcoes.salvar(nbproject.getAbsolutePath(), "project.properties", projectProperties());

        //Cria a pasta 'private'
        File pastPrivate = new File(caminhoRaiz + "\\nbproject\\private"); // ajfilho é uma pasta!

        if (!pastPrivate.exists()) {

            pastPrivate.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.
        }
        //Cria os arquivos 'config.properties','private.properties','private.xml'
        Funcoes.salvar(pastPrivate.getAbsolutePath(), "config.properties", projectProperties());
        Funcoes.salvar(pastPrivate.getAbsolutePath(), "project.xml", projectXml(nomeDoProjeto));
        Funcoes.salvar(pastPrivate.getAbsolutePath(), "project.properties", projectProperties());
        Funcoes.salvar(pastPrivate.getAbsolutePath(), "private.properties", privateProperties(nomeDoProjeto));
    }

}
