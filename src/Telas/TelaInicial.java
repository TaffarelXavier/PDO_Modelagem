package Telas;

import Classes.Autoload;
import Classes.CRUD_TX;
import Classes.Conexao;
import Classes.ConfigIniPHP;
import Classes.CriadorDeSQL;
import Classes.CriarProjeto;
import Classes.FormularioHTML;
import Classes.IndexPHP;
import Classes.Javascript;
import Classes.Login;
import Classes.Modelos;
import Funcoes.Funcoes;
import static Funcoes.Projeto.criarPastaDoProjeto;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import tx.utilitarios.TXProperties;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Taffarel Xavier <taffarel_deus@hotmail.com>
 */
public class TelaInicial extends javax.swing.JFrame {

    public static String raizDoProjeto = "";
    public static String nameDoProjeto = "";

    static {
        String path = TelaInicial.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        TXProperties.inicializador(path);
        criarPastaDoProjeto();
    }

    String varNomeDaClasse = "";

    DefaultListModel MODELO;

    int enter = 0;

    private final String[] itensDoPHP = {"modelo", "view", "classe", "modelo-e-classe"};
    //
    private final String[] itensDoJavascript = {"JQuery"};

    private final String[] petStrings = {"PARAM_BOOL", "PARAM_NULL", "PARAM_INT", "PARAM_STR", "PARAM_STR_NATL", "PARAM_STR_CHAR"};

    private final String EXTENSAO_PROJETO_CRUDTX = "properties";

    public TelaInicial() {
        initComponents();
        String audaPHP = "<div style='color:gray;'><b>[fazer <span>php</span> classe ]</b> <b style='color:red;'>Nome_da_classe</b> <span>nomeDaTabela</span> </div> <br/> ";
        String login = "<div style='color:gray;'><b>[fazer <span>php</span> login ]</b> <b style='color:red;'>nome_da_tabela</b> </div> ";
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setRowSelectionAllowed(true);
        listarSubdiretorios();
        //this.setExtendedState(this.MAXIMIZED_BOTH);
        criarMenu();
        createPanel3();
    }
    JFileChooser fileChooser;

    private void createPanel3() {

        jPanel3.setLayout(new BorderLayout());
        jPanel3.setPreferredSize(new Dimension(400, 100));
        jPanel3.setMinimumSize(new Dimension(100, 50));

        fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Browse naar de  locatie waar je de gesorteerde bestanden wil zetten en klik op \"OPEN\"");
        jPanel3.add(fileChooser, BorderLayout.NORTH);

        fileChooser.addActionListener((ActionEvent e) -> {
            if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                System.out.println("File selected: " + fileChooser.getSelectedFile());
                jTextField1.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Arquivo");

        //Abrir
        JMenuItem menuAbrir = new JMenuItem("Abrir");

        //Salvar
        JMenuItem menuSalvar = new JMenuItem("Salvar");
        //Sair
        JMenuItem menuSair = new JMenuItem("Sair");
        //Sair
        JMenuItem menuCopiarPara = new JMenuItem("Copiar Para");
        menu.add(menuAbrir);
        menu.add(menuSalvar);
        menu.add(menuCopiarPara);
        menu.addSeparator();
        menu.add(menuSair);
        menuBar.add(menu);

        this.setJMenuBar(menuBar);

        //Abrir
        menuAbrir.addActionListener((ActionEvent e) -> {
            //Seu código
            JFileChooser jFileChooser = new JFileChooser();
            //jFileChooser.setSelectedFile(new File("fileToSave.txt"));
            FileFilter filter = new FileNameExtensionFilter("Arquivo de Configuração", EXTENSAO_PROJETO_CRUDTX);
            jFileChooser.setFileFilter(filter);
            jFileChooser.setPreferredSize(new Dimension(900, 600));

            int result = jFileChooser.showOpenDialog(this);

            File file = jFileChooser.getSelectedFile();

            //Definindo Propriedades
            preencherCampoDeProjeto(file.getAbsoluteFile().toString());

            listarTudoQuandoAbrirUmProjeto();
        });

        menuCopiarPara.addActionListener((ActionEvent e) -> {

            String novoArquivoDestino = getCaminhoRootComSubPasta();

            String diretorioCurrenty = jTextField1.getText();

            if (TXProperties.keyExists("diretorio.currenty")) {
                diretorioCurrenty = TXProperties.getProp("diretorio.currenty");
            }

            //Seu código
            JFileChooser jFileChooser = new JFileChooser();

            jFileChooser.setCurrentDirectory(new File(diretorioCurrenty));
            //Altera o título
            jFileChooser.setDialogTitle("Copiar para: '" + novoArquivoDestino + "'");
            //Altera para somente files
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            jFileChooser.setMultiSelectionEnabled(true);

            //jFileChooser.setSelectedFile(new File("fileToSave.txt"));
            FileFilter phpFilter = new FileNameExtensionFilter(" Hypertext Preprocessor(.php)", "php");
            FileFilter jsFilter = new FileNameExtensionFilter("Javascript(.js)", "js");
            FileFilter cssFilter = new FileNameExtensionFilter("Cascading Style Sheets(.css)", "css");

            jFileChooser.addChoosableFileFilter(phpFilter);
            jFileChooser.addChoosableFileFilter(jsFilter);
            jFileChooser.addChoosableFileFilter(cssFilter);

            //jFileChooser.setFileFilter(docx);
            jFileChooser.setPreferredSize(new Dimension(900, 600));

            int result = jFileChooser.showOpenDialog(this);

            File[] files = jFileChooser.getSelectedFiles();

            if (result == JFileChooser.APPROVE_OPTION) {

                try {
                    TXProperties.addItem("diretorio.currenty", files[0].getParentFile().getAbsolutePath());
                } catch (IOException ex) {
                    Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (File file : files) {
                    try {
                        Funcoes.copyFileUsingFileChannels(file, new File(novoArquivoDestino + "\\" + file.getName()));
                        JOptionPane.showMessageDialog(null, "Copiados com sucesso!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.println("Cancel was selected");
            }

        });
    }
    private String rootSystem = "";

    /**
     *
     */
    private void listarSubdiretorios() {

        rootSystem = jTextField1.getText();
        
        File file = new File(jTextField1.getText());

        if (file.exists()) {

            String[] directories = file.list((File current, String name1) -> new File(current, name1).isDirectory());

            DefaultListModel listModel = new DefaultListModel();

            for (String directorie : directories) {
                if (!directorie.equals("nbproject")) {
                    listModel.addElement(directorie);
                }
            }

            jList1.setModel(listModel);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Lista = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        prefixoClasse = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldNomeDaClasse = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTFNomeDaTabela = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboSubItens = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSpinnerQuantidadeColuna = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        prefixoDaColuna = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel11 = new javax.swing.JLabel();
        nomeDoProjeto = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        nomeDaPasta = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Criador de CRUDTX");
        setState(2);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        prefixoClasse.setText("class");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Prefixo:");

        jTextFieldNomeDaClasse.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldNomeDaClasseKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Nome da Classe PHP:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Nome da tabela SGDB:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione...", "php", "js", "css", "sql" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboSubItens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboSubItensActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Sub-item:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Fazer:");

        jSpinnerQuantidadeColuna.setModel(new javax.swing.SpinnerNumberModel(5, 1, null, 1));
        jSpinnerQuantidadeColuna.setRequestFocusEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Quant. de Coluna:");

        jButton2.setText("Add Linhas");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Limpar tabela");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        prefixoDaColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prefixoDaColunaActionPerformed(evt);
            }
        });
        prefixoDaColuna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                prefixoDaColunaKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Prefixo das colunas:");

        jButton1.setBackground(new java.awt.Color(0, 153, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Criar CRUD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ORDEM", "NOME DA COLUNA", "TIPO", "VALOR", "NULO", "AUTOINCREMENT", "PRIMARY", "Opções"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(25);
        jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTable1MouseMoved(evt);
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTable1MouseExited(evt);
            }
        });
        jTable1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTable1InputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Saída de Ajax/Javascript/Form", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea1FocusGained(evt);
            }
        });
        jScrollPane3.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modelo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jButton5.setText("jButton5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("jButton6");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap(370, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jSpinnerQuantidadeColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(prefixoDaColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jButton2)
                                .addGap(5, 5, 5)
                                .addComponent(jButton3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(105, 105, 105)
                                .addComponent(jLabel8)
                                .addGap(150, 150, 150)
                                .addComponent(jLabel9)
                                .addGap(83, 83, 83)
                                .addComponent(jLabel10)
                                .addGap(44, 44, 44)
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jComboSubItens, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jTextFieldNomeDaClasse, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jTFNomeDaTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(prefixoClasse, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7))
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel4))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboSubItens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldNomeDaClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFNomeDaTabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prefixoClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerQuantidadeColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(prefixoDaColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2)
                            .addComponent(jButton3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Configurações do Projeto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Root do Sistema:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Sub-pastas:");

        jList1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jList1FocusGained(evt);
            }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jList1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jList1PropertyChange(evt);
            }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Nome do Projeto:");

        nomeDoProjeto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nomeDoProjetoKeyReleased(evt);
            }
        });

        jButton4.setText("Criar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Criar Pasta:");

        nomeDaPasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomeDaPastaActionPerformed(evt);
            }
        });

        jLabel13.setText("Abrir Configurações");
        jLabel13.setText("<html><body><a style='color:blue;text-decoration:underline;'>Abrir Configurações</a></body></html>");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        jLabel5.setText("jLabel5");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(nomeDaPasta)
                        .addGap(2, 2, 2))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addGap(3, 3, 3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel12)
                            .addComponent(jLabel2)
                            .addComponent(jLabel13)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(nomeDoProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeDoProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(2, 2, 2)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(2, 2, 2)
                .addComponent(nomeDaPasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel13))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Choose File", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(485, 485, 485)
                        .addComponent(Lista, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Lista))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(46, 46, 46))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField1KeyReleased

    public void criarCrud() {

        try {

            String tipo = jComboBox1.getSelectedItem().toString();

            String subItem = jComboSubItens.getSelectedItem().toString();

            String _nomeDaClasse = jTextFieldNomeDaClasse.getText();

            String nomeDaTabbela = jTFNomeDaTabela.getText().toLowerCase();

            switch (tipo) {

                case "php":

                    switch (subItem) {
                        case "classe":
                            int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja criar?\n" + "Caminho:" + getCaminhoRootComSubPasta()
                                    + "\nNome do arquivo:" + prefixoClasse.getText() + "." + _nomeDaClasse.toLowerCase() + ".php"
                                    + "\nNome da classe:" + _nomeDaClasse
                                    + "\nNome da tabela:" + nomeDaTabbela,
                                    "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                            if (dialogResult == JOptionPane.YES_OPTION) {
                                boolean result = Funcoes.salvar(getCaminhoRootComSubPasta(),
                                        prefixoClasse.getText() + "." + _nomeDaClasse.toLowerCase() + ".php",
                                        CRUD_TX.conteudo(_nomeDaClasse, nomeDaTabbela, _nomeDaClasse, jTable1, prefixoDaColuna.getText()));
                                if (result == true) {
                                    CriadorDeSQL.executarSQL(jTFNomeDaTabela.getText().trim(), jTable1, prefixoDaColuna.getText().trim());
                                    JOptionPane.showMessageDialog(null, "Sucesso!", "Operação realizada com sucesso!", 1);
                                }
                            }
                            break;
                        case "login": //Cria uma classe de login
                            System.out.println("AA");
                            boolean result = Funcoes.salvar(getCaminhoRootComSubPasta(), "class.login.php", Login.salvar(nomeDaTabbela.toLowerCase(), jTable1));
                            if (result == true) {
                                JOptionPane.showMessageDialog(null, "Sucesso!", "Operação realizada com sucesso!", 1);
                            }
                            break;
                        case "modelo":

                            int dialogResult2 = JOptionPane.showConfirmDialog(null, "Deseja criar?\n" + "Caminho:" + getCaminhoRootComSubPasta()
                                    + "\nNome do arquivo:" + "model." + _nomeDaClasse.toLowerCase() + ".php",
                                    "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                            if (dialogResult2 == JOptionPane.YES_OPTION) {
                                Funcoes.salvar(getCaminhoRootComSubPasta(), "model." + _nomeDaClasse.toLowerCase() + ".php",
                                        Modelos.salvar(jTable1, prefixoDaColuna.getText()));
                            }

                            break;

                        case "autoload":

                            int dialogResult3 = JOptionPane.showConfirmDialog(null, "Deseja criar o arquivo de Autoload?\n"
                                    + "Caminho:" + jTextField1.getText()
                                    + "\nNome do arquivo:" + "autoload.php",
                                    "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                            if (dialogResult3 == JOptionPane.YES_OPTION) {
                                Funcoes.salvar(jTextField1.getText(), "autoload.php", Autoload.criarAutoload());
                            }

                            break;
                        case "conexao":
                            Funcoes.salvar(getCaminhoRootComSubPasta(), "class.conexao.php", Conexao.salvar());
                            break;
                        case "config.php.ini"://
                            Funcoes.salvar(getCaminhoRootComSubPasta(), "config.ini.php", ConfigIniPHP.criarArquivoDeConfiguracao());
                            break;
                        case "modelo-e-classe":
                            int dialResul = JOptionPane.showConfirmDialog(null, "Deseja criar?\n" + "Caminho:" + getCaminhoRootComSubPasta()
                                    + "\nNome do arquivo:" + "model." + _nomeDaClasse.toLowerCase() + ".php",
                                    "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                            if (dialResul == JOptionPane.YES_OPTION) {
                                //Salva sempre dentro da pasta de classes
                                boolean result2 = Funcoes.salvar(jTextField1.getText() + "\\classes",
                                        prefixoClasse.getText() + "." + _nomeDaClasse.toLowerCase() + ".php",
                                        CRUD_TX.conteudo(_nomeDaClasse, nomeDaTabbela, _nomeDaClasse, jTable1, prefixoDaColuna.getText()));
                                if (result2 == true) {
                                    CriadorDeSQL.executarSQL(jTFNomeDaTabela.getText().trim(), jTable1, prefixoDaColuna.getText().trim());
                                    JOptionPane.showMessageDialog(null, "Sucesso!", "Operação realizada com sucesso!", 1);
                                }
                                //Já aqui, salva o arquivo de modelo onde o usuário escolher no jList
                                Funcoes.salvar(getCaminhoRootComSubPasta(), "model." + _nomeDaClasse.toLowerCase() + ".php",
                                        Modelos.salvar(jTable1, prefixoDaColuna.getText()));
                            }

                            break;
                    }
                    break;

                case "js":
                    switch (subItem) {
                        case "JQuery":

                            Funcoes.salvar(getCaminhoRootComSubPasta(), "jquery.js", Javascript.JQuerypProducao());

                            break;

                    }
                    break;
                case "css":

                    System.out.println("css");
                    break;
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private String getNomeDoProjeto() {
        return nomeDoProjeto.getText().toLowerCase() + '.' + EXTENSAO_PROJETO_CRUDTX;
    }

    private void criarPropriedade(String key, String value) {
        try {
            System.out.println(jTextField1.getText() + "\\" + getNomeDoProjeto());
            
            TXProperties.setFilePath(jTextField1.getText() + "\\" + getNomeDoProjeto());
            TXProperties.addItem(key, value);
        } catch (IOException ex) {
            Logger.getLogger(TelaInicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void importarClassesOBrigatorias() {
        Funcoes.salvar(getCaminhoRootComSubPasta() + "\\classes", "class.conexao.php", Conexao.salvar());
        Funcoes.salvar(getCaminhoRootComSubPasta(), "config.ini.php", ConfigIniPHP.criarArquivoDeConfiguracao());
        Funcoes.salvar(getCaminhoRootComSubPasta(), "autoload.php", Autoload.criarAutoload());
        Funcoes.salvar(getCaminhoRootComSubPasta(), "index.php", IndexPHP.salvar(nomeDoProjeto.getText(), nomeDoProjeto.getText()));
        
        criarPropriedade("nome.do.projeto", nomeDoProjeto.getText().toLowerCase());
        criarPropriedade("raiz.do.projeto", getCaminhoRootComSubPasta());
    }

    /**
     *
     * @return
     */
    public String getCaminhoRootComSubPasta() {
        if (jList1.getSelectedValue() == null) {
            return jTextField1.getText();
        }
        return jTextField1.getText() + "\\" + jList1.getSelectedValue();
    }

    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseMoved

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        //Duplo clique
        int SelectedRow = jTable1.getSelectedRow();

        int SelectedColumn = jTable1.getSelectedColumn();

        if (evt.getClickCount() == 2) {

            if (SelectedColumn == 7) {

                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

                model.removeRow(jTable1.convertRowIndexToModel(SelectedRow));

                int quantLinhas = jTable1.getRowCount();

                for (int x = 0; x < quantLinhas; ++x) {
                    model.setValueAt((x + 1), x, 0);
                }

            }
        }
        salvarNovoFormulario();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseExited
        // TODO add your handling code here:
        jTable1.repaint();
    }//GEN-LAST:event_jTable1MouseExited

    private void jTable1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTable1InputMethodTextChanged
        // TODO add your handling code here:
        jTable1.repaint();
    }//GEN-LAST:event_jTable1InputMethodTextChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        int quantLinhas = Integer.parseInt(jSpinnerQuantidadeColuna.getValue().toString());

        //Adiciona as linhas
        for (int x = 0; x < quantLinhas; ++x) {
            int modelCountRows = model.getRowCount() + 1;
            model.addRow(new Object[]{modelCountRows, prefixoDaColuna.getText() + modelCountRows, "PARAM_STR", "255", "true", "false", "false", "Excluir"});
        }
        //Altera a primeira linha, 1 param=valor, 2 param=linha, 3 param=coluna
        model.setValueAt("PARAM_INT", 0, 2);
        model.setValueAt("", 0, 3);
        model.setValueAt("false", 0, 4);
        model.setValueAt("true", 0, 5);
        model.setValueAt("true", 0, 6);

        //Adiciona os combos na primeira coluna
        TableColumn column1 = jTable1.getColumnModel().getColumn(2);

        //Adiciona os combos na segunda coluna
        TableColumn column2 = jTable1.getColumnModel().getColumn(4);

        TableColumn column3 = jTable1.getColumnModel().getColumn(5);

        TableColumn column4 = jTable1.getColumnModel().getColumn(6);

        JComboBox petList = new JComboBox(petStrings);
        JCheckBox cb = new JCheckBox("Selecione", false);
        cb.setSelected(false);

        column1.setCellEditor(new DefaultCellEditor(petList));
        column2.setCellEditor(new DefaultCellEditor(cb));
        column3.setCellEditor(new DefaultCellEditor(cb));
        column4.setCellEditor(new DefaultCellEditor(cb));

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int reply = JOptionPane.showConfirmDialog(
                null,
                "Deseja realmente limpar a tabela?",
                "Atenção",
                JOptionPane.YES_NO_OPTION);

        if (reply == JOptionPane.YES_OPTION) {
            DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                dm.removeRow(i);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    public void salvarNovoFormulario() {
        jTextArea1.setText(FormularioHTML.salvar(jTable1, prefixoDaColuna.getText()));
    }
    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:

        salvarNovoFormulario();

        int[] codigosExcluidos = {10, 16, 17, 37, 38, 39, 40};

        System.out.println(evt.getKeyCode());

        boolean contains = IntStream.of(codigosExcluidos).anyMatch(x -> x == evt.getKeyCode());

        if (contains == false) {

            int SelectedRow = jTable1.getSelectedRow();

            int SelectedColumn = jTable1.getSelectedColumn();

            jTable1.setValueAt("", SelectedRow, SelectedColumn);
        }

    }//GEN-LAST:event_jTable1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        criarCrud();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void prefixoDaColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prefixoDaColunaActionPerformed
        // TODO add your handling code here:
        criarCrud();
    }//GEN-LAST:event_prefixoDaColunaActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        String tipo = jComboBox1.getSelectedItem().toString();

        switch (tipo) {
            case "php":
                jComboSubItens.setModel(new javax.swing.DefaultComboBoxModel<>(itensDoPHP));
                break;
            case "js":
                jComboSubItens.setModel(new javax.swing.DefaultComboBoxModel<>(itensDoJavascript));
                break;
        }
        criarPropriedade("fazer", tipo);
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        // TODO add your handling code here:
        listarSubdiretorios();
    }//GEN-LAST:event_jTextField1FocusLost

    private void jComboSubItensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboSubItensActionPerformed
        // TODO add your handling code here:
        jTextFieldNomeDaClasse.requestFocus();

    }//GEN-LAST:event_jComboSubItensActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (CriarProjeto.criarProjeto(jTextField1.getText().trim(), nomeDoProjeto.getText().trim())==true) {
            importarClassesOBrigatorias();
        }
        else{
            JOptionPane.showMessageDialog(null, "Não foi possível criar o projeto. Tente outra vez ou reinicei o programa.",
                    "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void nomeDaPastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomeDaPastaActionPerformed
        // TODO add your handling code here:
        Funcoes.criarPasta(jTextField1.getText().trim(), nomeDaPasta.getText().trim());
        criarPropriedade("ultima.pasta.criada", nomeDaPasta.getText().trim());
        listarSubdiretorios();
        nomeDaPasta.requestFocus();
        nomeDaPasta.selectAll();
    }//GEN-LAST:event_nomeDaPastaActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        /*  String path = TelaInicial.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        TXProperties.inicializador(path);
        if (!TXProperties.keyExists("db.name")) {
            this.setVisible(false);
            new TelaBancoDeDados().setVisible(true);
        }*/
    }//GEN-LAST:event_formWindowActivated

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        new TelaBancoDeDados().setVisible(true);
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jTextFieldNomeDaClasseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNomeDaClasseKeyReleased
        // TODO add your handling code here:
        criarPropriedade("nome.da.ultima.classe", jTextFieldNomeDaClasse.getText());
    }//GEN-LAST:event_jTextFieldNomeDaClasseKeyReleased

    private void prefixoDaColunaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prefixoDaColunaKeyReleased
        // TODO add your handling code here:
        criarPropriedade("prefixo.coluna", prefixoDaColuna.getText());

        salvarNovoFormulario();
    }//GEN-LAST:event_prefixoDaColunaKeyReleased

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        String fileName = jList1.getSelectedValue();
        if (fileName != null) {
            // jTextField1.setText(jTextField1.getText() + "\\" + fileName);
        }
    }//GEN-LAST:event_jList1MouseClicked
    private void listarTudoQuandoAbrirUmProjeto() {
        listarSubdiretorios();
        fileChooser.setCurrentDirectory(new File(jTextField1.getText()));
    }
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

        listarTudoQuandoAbrirUmProjeto();

        String dirNameRoot = jTextField1.getText(); //O root do sistema

        File folder = new File(dirNameRoot);

        if (folder.exists()) {

            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().contains(".properties")) {
                    preencherCampoDeProjeto(dirNameRoot + "\\" + file.getName());
                }
            }
        }
    }//GEN-LAST:event_jTextField1ActionPerformed
    /**
     * <p style="font-weight:900;">Preenche todos os campos do projeto, quando encontra o arquivo de configuração, isto é, o arquivo de propriedade.</p>
     *
     * @param nomeDoArquivoDePropriedade
     */
    public void preencherCampoDeProjeto(String nomeDoArquivoDePropriedade) {
        TXProperties.setFilePath(nomeDoArquivoDePropriedade);
        nomeDoProjeto.setText(TXProperties.getProp("nome.do.projeto"));
        raizDoProjeto = TXProperties.getProp("raiz.do.projeto");
        nameDoProjeto = TXProperties.getProp("nome.do.projeto");
        jTextField1.setText(raizDoProjeto);
        jComboBox1.setSelectedItem(TXProperties.getProp("fazer"));
        jTextFieldNomeDaClasse.setText(TXProperties.getProp("nome.da.ultima.classe"));
        prefixoDaColuna.setText(TXProperties.getProp("prefixo.coluna"));
        setTitle(TXProperties.getProp("nome.do.projeto") + " - " + "Criador de CRUD_TX");
    }

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jList1ValueChanged

    private void jList1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jList1PropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_jList1PropertyChange

    private void jList1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jList1FocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_jList1FocusGained

    private void jTextArea1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea1FocusGained
        // TODO add your handling code here:
        jTextArea1.selectAll();
    }//GEN-LAST:event_jTextArea1FocusGained

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_jTable1KeyTyped

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
        salvarNovoFormulario();
    }//GEN-LAST:event_formKeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton6ActionPerformed

    private void nomeDoProjetoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nomeDoProjetoKeyReleased
        // TODO add your handling code here:
        jTextField1.setText(rootSystem + "\\" + nomeDoProjeto.getText());
    }//GEN-LAST:event_nomeDoProjetoKeyReleased

    /**
     * <p>
     * Quebra de linha</p>
     *
     * @param total
     * @return
     */
    public String br(int total) {
        String br = "";
        for (int x = 0; x < total; ++x) {
            br += "\n"; //Quebra de linha
        }
        return br;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaInicial.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TelaInicial().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> Lista;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboSubItens;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinnerQuantidadeColuna;
    private javax.swing.JTextField jTFNomeDaTabela;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldNomeDaClasse;
    private javax.swing.JTextField nomeDaPasta;
    private javax.swing.JTextField nomeDoProjeto;
    private javax.swing.JTextField prefixoClasse;
    private javax.swing.JTextField prefixoDaColuna;
    // End of variables declaration//GEN-END:variables

}
