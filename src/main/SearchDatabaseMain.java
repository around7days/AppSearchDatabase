package main;

import static main.SearchDataBaseConst.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

/**
 * データベース検索メインクラス
 * @author 7days
 */
public class SearchDatabaseMain extends JFrame {

    /** Logger */
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(SearchDatabaseMain.class);

    private JPanel jContentPane = null;
    private JLabel jLabel = null;
    private JLabel jLabel1 = null;
    private JLabel jLabel2 = null;
    private JLabel jLabel3 = null;
    private JLabel jLabel4 = null;
    private JLabel jLabel5 = null;
    private JLabel jLabel6 = null;
    private JLabel jLabel7 = null;
    private JLabel jLabel8 = null;
    private JLabel jLabel9 = null;
    private JLabel statusLabel = null;
    private JTextArea memo = null;
    /** データベース設定ファイルリスト */
    private JComboBox<String> cbDbConf = null;
    /** データベース名 */
    private JTextField dbName = null;
    /** 接続URL */
    private JTextField dbUrl = null;
    /** ユーザー名 */
    private JTextField dbUser = null;
    /** パスワード */
    private JTextField dbPass = null;
    /** 検索値 */
    private JTextField searchValue = null;
    /** テーブル名 */
    private JTextField searchTableNm = null;
    /** 項目名 */
    private JTextField searchColNm = null;
    /** 検索値 LIKE検索チェックボックス */
    private JCheckBox chkLikeVal = null;
    /** テーブル名 LIKE検索チェックボックス */
    private JCheckBox chkLikeTable = null;
    /** 項目名 LIKE検索チェックボックス */
    private JCheckBox chkLikeCol = null;
    /** テーブル名 全検索チェックボックス */
    private JCheckBox chkAllTable = null;
    /** 項目名 全検索チェックボックス */
    private JCheckBox chkAllCol = null;
    /** コンソール */
    private JTextArea console = null;
    private JScrollPane jScrollPane = null;
    /** 抽出項目 テーブル名＋項目名ラジオボタン */
    private JRadioButton rdTableCol = null;
    /** 抽出項目 テーブル名ラジオボタン */
    private JRadioButton rdTable = null;
    /** 保存ボタン */
    private JButton jButton = null;
    /** 読込ボタン */
    private JButton jButton1 = null;
    /** 検索ボタン */
    private JButton jButton2 = null;
    /** 消去ボタン */
    private JButton jButton3 = null;

    /** ロジッククラスの生成 */
    private static SearchDatabaseLogic logic = new SearchDatabaseLogic(); // @jve:decl-index=0:
    /** Beanクラスの生成 */
    private static SearchDatabaseBean bean = new SearchDatabaseBean(); // @jve:decl-index=0:

    /**
     * コンストラクタ
     */
    public SearchDatabaseMain() {
        super();
        initialize();
        initialize1();
        initialize2();
    }

    /**
     * メイン処理
     * @param args
     */
    public static void main(String[] args) {
        // logger.debug("search database main");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SearchDatabaseMain thisClass = new SearchDatabaseMain();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
            }
        });
    }

    /**
     * 初期処理
     */
    private void initialize() {
        this.setSize(622, 708);
        this.setContentPane(getJContentPane());
        this.setTitle("データベース検索");
    }

    /**
     * 初期処理１<br>
     * 画面入力項目オブジェクトをbeanに格納
     */
    private void initialize1() {
        bean.setStatusLabel(statusLabel);
        bean.setChkLikeVal(chkLikeVal);
        bean.setChkLikeTable(chkLikeTable);
        bean.setChkLikeCol(chkLikeCol);
        bean.setChkAllTable(chkAllTable);
        bean.setChkAllCol(chkAllCol);
        bean.setRdTableCol(rdTableCol);
        bean.setRdTable(rdTable);
        bean.setConsole(console);
        bean.setCbDbConf(cbDbConf);
        bean.setDbName(dbName);
        bean.setDbUrl(dbUrl);
        bean.setDbUser(dbUser);
        bean.setDbPass(dbPass);
        bean.setSearchValue(searchValue);
        bean.setSearchTableNm(searchTableNm);
        bean.setSearchColNm(searchColNm);
    }

    /**
     * 初期処理２<br>
     * 初期画面時の制御設定
     */
    private void initialize2() {
        // ラジオボタンのグループ化
        ButtonGroup group = new ButtonGroup();
        group.add(rdTableCol);
        group.add(rdTable);
        // テーブル名のみを選択
        group.setSelected(rdTable.getModel(), true);
        // テーブル名　全検索チェックボックスON
        chkAllTable.setSelected(true);
        chkLikeTable.setEnabled(false);
        searchTableNm.setEnabled(false);
        // 項目名　全検索チェックボックスON
        chkAllCol.setSelected(true);
        chkLikeCol.setEnabled(false);
        searchColNm.setEnabled(false);
        // コンボボックスの入力を可能に
        cbDbConf.setEditable(true);

        // データベースファイルリストの読込
        logic.init(bean);
    }

    /**
     * This method initializes jContentPane
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            statusLabel = new JLabel();
            statusLabel.setBounds(new Rectangle(28, 493, 394, 17));
            statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
            statusLabel.setText("Status : ");
            jLabel9 = new JLabel();
            jLabel9.setBounds(new Rectangle(15, 20, 106, 16));
            jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel9.setText("ファイル選択");
            jLabel8 = new JLabel();
            jLabel8.setBounds(new Rectangle(28, 219, 106, 16));
            jLabel8.setText("コンソール");
            jLabel7 = new JLabel();
            jLabel7.setBounds(new Rectangle(16, 183, 106, 13));
            jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel7.setText("抽出項目");
            jLabel6 = new JLabel();
            jLabel6.setBounds(new Rectangle(16, 165, 106, 13));
            jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel6.setText("項目名");
            jLabel5 = new JLabel();
            jLabel5.setBounds(new Rectangle(16, 147, 106, 13));
            jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel5.setText("テーブル名");
            jLabel4 = new JLabel();
            jLabel4.setBounds(new Rectangle(16, 129, 106, 13));
            jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel4.setText("検索値");
            jLabel3 = new JLabel();
            jLabel3.setBounds(new Rectangle(16, 105, 106, 13));
            jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel3.setText("パスワード");
            jLabel2 = new JLabel();
            jLabel2.setBounds(new Rectangle(16, 87, 106, 13));
            jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel2.setText("ユーザー名");
            jLabel1 = new JLabel();
            jLabel1.setBounds(new Rectangle(16, 69, 106, 13));
            jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel1.setText("接続URL");
            jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(16, 51, 106, 13));
            jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel.setText("データベース名");
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(jLabel, null);
            jContentPane.add(jLabel1, null);
            jContentPane.add(jLabel2, null);
            jContentPane.add(jLabel3, null);
            jContentPane.add(jLabel4, null);
            jContentPane.add(jLabel5, null);
            jContentPane.add(jLabel6, null);
            jContentPane.add(jLabel7, null);
            jContentPane.add(jLabel8, null);
            jContentPane.add(jLabel9, null);
            jContentPane.add(statusLabel, null);
            jContentPane.add(getCbDbConf(), null);
            jContentPane.add(getDbName(), null);
            jContentPane.add(getDbUrl(), null);
            jContentPane.add(getDbUser(), null);
            jContentPane.add(getDbPass(), null);
            jContentPane.add(getSearchValue(), null);
            jContentPane.add(getSearchTableNm(), null);
            jContentPane.add(getSearchColNm(), null);
            jContentPane.add(getChkLikeVal(), null);
            jContentPane.add(getChkLikeTable(), null);
            jContentPane.add(getChkLikeCol(), null);
            jContentPane.add(getChkAllTable(), null);
            jContentPane.add(getChkAllCol(), null);
            jContentPane.add(getConsole(), null);
            jContentPane.add(getJButton(), null);
            jContentPane.add(getJButton1(), null);
            jContentPane.add(getJButton2(), null);
            jContentPane.add(getJButton3(), null);
            jContentPane.add(getRdTableCol(), null);
            jContentPane.add(getRdTable(), null);
            jContentPane.add(getJScrollPane(), null);
            jContentPane.add(getMemo(), null);
        }
        return jContentPane;
    }

    /**
     * DB接続設定コンボボックス
     * @return javax.swing.JComboBox
     */
    private JComboBox<?> getCbDbConf() {
        if (cbDbConf == null) {
            cbDbConf = new JComboBox<String>();
            cbDbConf.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // コンボボックス変更時
                    // // クリック時
                    // if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(null, "読込みます。宜しいですか？")) {
                    // // データベース情報読込処理
                    // logic.load(bean);
                    // }
                }
            });
            cbDbConf.setBounds(new Rectangle(136, 20, 286, 16));
        }
        return cbDbConf;
    }

    /**
     * This method initializes dbName
     * @return javax.swing.JTextField
     */
    private JTextField getDbName() {
        if (dbName == null) {
            dbName = new JTextField();
            dbName.setBounds(new Rectangle(136, 51, 286, 16));
        }
        return dbName;
    }

    /**
     * This method initializes dbUrl
     * @return javax.swing.JTextField
     */
    private JTextField getDbUrl() {
        if (dbUrl == null) {
            dbUrl = new JTextField();
            dbUrl.setBounds(new Rectangle(136, 69, 286, 16));
        }
        return dbUrl;
    }

    /**
     * This method initializes dbUser
     * @return javax.swing.JTextField
     */
    private JTextField getDbUser() {
        if (dbUser == null) {
            dbUser = new JTextField();
            dbUser.setBounds(new Rectangle(136, 87, 286, 16));
        }
        return dbUser;
    }

    /**
     * This method initializes dbPass
     * @return javax.swing.JTextField
     */
    private JTextField getDbPass() {
        if (dbPass == null) {
            dbPass = new JTextField();
            dbPass.setBounds(new Rectangle(136, 105, 286, 16));
        }
        return dbPass;
    }

    /**
     * This method initializes searchValue
     * @return javax.swing.JTextField
     */
    private JTextField getSearchValue() {
        if (searchValue == null) {
            searchValue = new JTextField();
            searchValue.setBounds(new Rectangle(136, 129, 286, 16));
        }
        return searchValue;
    }

    /**
     * This method initializes searchTableNm
     * @return javax.swing.JTextField
     */
    private JTextField getSearchTableNm() {
        if (searchTableNm == null) {
            searchTableNm = new JTextField();
            searchTableNm.setBounds(new Rectangle(136, 147, 286, 16));
        }
        return searchTableNm;
    }

    /**
     * This method initializes searchColNm
     * @return javax.swing.JTextField
     */
    private JTextField getSearchColNm() {
        if (searchColNm == null) {
            searchColNm = new JTextField();
            searchColNm.setBounds(new Rectangle(136, 165, 286, 16));
        }
        return searchColNm;
    }

    /**
     * This method initializes chkLikeVal
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getChkLikeVal() {
        if (chkLikeVal == null) {
            chkLikeVal = new JCheckBox();
            chkLikeVal.setBounds(new Rectangle(430, 129, 79, 16));
            chkLikeVal.setText("LIKE検索");
        }
        return chkLikeVal;
    }

    /**
     * This method initializes chkLikeTable
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getChkLikeTable() {
        if (chkLikeTable == null) {
            chkLikeTable = new JCheckBox();
            chkLikeTable.setBounds(new Rectangle(430, 147, 79, 16));
            chkLikeTable.setText("LIKE検索");
        }
        return chkLikeTable;
    }

    /**
     * This method initializes chkLikeCol
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getChkLikeCol() {
        if (chkLikeCol == null) {
            chkLikeCol = new JCheckBox();
            chkLikeCol.setBounds(new Rectangle(430, 165, 79, 16));
            chkLikeCol.setText("LIKE検索");
        }
        return chkLikeCol;
    }

    /**
     * テーブル名　全検索チェックボックス
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getChkAllTable() {
        if (chkAllTable == null) {
            chkAllTable = new JCheckBox();
            chkAllTable.setBounds(new Rectangle(520, 147, 79, 16));
            chkAllTable.setText("全検索");
            chkAllTable.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // クリック時
                    if (chkAllTable.isSelected()) {
                        // チェックボックスON
                        searchTableNm.setEnabled(false);
                        chkLikeTable.setEnabled(false);
                    } else {
                        // チェックボックスOFF
                        searchTableNm.setEnabled(true);
                        chkLikeTable.setEnabled(true);
                    }
                }
            });
        }
        return chkAllTable;
    }

    /**
     * 項目名　全検索チェックボックス
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getChkAllCol() {
        if (chkAllCol == null) {
            chkAllCol = new JCheckBox();
            chkAllCol.setBounds(new Rectangle(520, 165, 79, 16));
            chkAllCol.setText("全検索");
            chkAllCol.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // クリック時
                    if (chkAllCol.isSelected()) {
                        // チェックボックスON
                        searchColNm.setEnabled(false);
                        chkLikeCol.setEnabled(false);
                    } else {
                        // チェックボックスOFF
                        searchColNm.setEnabled(true);
                        chkLikeCol.setEnabled(true);
                    }
                }
            });
        }
        return chkAllCol;
    }

    /**
     * コンソール
     * @return javax.swing.JTextArea
     */
    private JTextArea getConsole() {
        if (console == null) {
            console = new JTextArea();
            console.setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
            console.setBounds(new Rectangle(30, 238, 565, 220));
        }
        return console;
    }

    /**
     * 保存ボタン
     * @return javax.swing.JButton
     */
    private JButton getJButton() {
        if (jButton == null) {
            jButton = new JButton();
            jButton.setBounds(new Rectangle(504, 16, 61, 28));
            jButton.setText("保存");
            jButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // クリック時
                    if (logic.isSaveError(bean)) {
                        // 入力チェック　エラーあり
                        return;
                    }
                    if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(null, "保存します。宜しいですか？")) {
                        // データベース情報保存処理
                        logic.save(bean);
                    }
                }
            });
        }
        return jButton;
    }

    /**
     * 読込ボタン
     * @return javax.swing.JButton
     */
    private JButton getJButton1() {
        if (jButton1 == null) {
            jButton1 = new JButton();
            jButton1.setBounds(new Rectangle(432, 16, 61, 28));
            jButton1.setText("読込");
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // クリック時
                    if (JOptionPane.YES_NO_OPTION == JOptionPane.showConfirmDialog(null, "読込みます。宜しいですか？")) {
                        // データベース情報読込処理
                        logic.load(bean);
                    }
                }
            });
        }
        return jButton1;
    }

    /**
     * 検索ボタン
     * @return javax.swing.JButton
     */
    private JButton getJButton2() {
        if (jButton2 == null) {
            jButton2 = new JButton();
            jButton2.setBounds(new Rectangle(430, 487, 61, 28));
            jButton2.setText("検索");
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // クリック時
                    if (logic.isSearchError(bean)) {
                        // 入力チェック　エラーあり
                        return;
                    }
                    // 検索処理開始
                    logic.search(bean);

                    JOptionPane.showMessageDialog(null, "検索が完了しました。");
                }
            });
        }
        return jButton2;
    }

    /**
     * 消去ボタン
     * @return javax.swing.JButton
     */
    private JButton getJButton3() {
        if (jButton3 == null) {
            jButton3 = new JButton();
            jButton3.setBounds(new Rectangle(504, 487, 61, 28));
            jButton3.setText("消去");
            jButton3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // クリック時
                    logic.consoleClear(bean);
                }
            });
        }
        return jButton3;
    }

    /**
     * This method initializes rdTableCol
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRdTableCol() {
        if (rdTableCol == null) {
            rdTableCol = new JRadioButton();
            rdTableCol.setBounds(new Rectangle(136, 183, 151, 16));
            rdTableCol.setText("テーブル名＋項目名");
        }
        return rdTableCol;
    }

    /**
     * This method initializes rdTable
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRdTable() {
        if (rdTable == null) {
            rdTable = new JRadioButton();
            rdTable.setBounds(new Rectangle(298, 183, 121, 17));
            rdTable.setText("テーブル名のみ");
        }
        return rdTable;
    }

    /**
     * This method initializes jScrollPane
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane(console);
            jScrollPane.setBounds(new Rectangle(29, 237, 555, 235));
        }
        return jScrollPane;
    }

    /**
     * This method initializes memo
     * @return javax.swing.JTextArea
     */
    private JTextArea getMemo() {
        if (memo == null) {
            //@formatter:off
            String line = "・Oracleのみ対応しています" + NEW_LINE +
                          "・Like検索の「%」「_」は自分で入力してください。" + NEW_LINE +
                          "・データベースの日付型（DATE、TIMESTAMP）は「YYYY/MM/DD」で取得して比較します。" + NEW_LINE +
                          "・Viewは検索対象外です。" + NEW_LINE +
                          "・項目は「VARCHAR2、CHAR、DATE、TIMESTAMP、NUMBER」型のみ検索します。" + NEW_LINE +
                          " （「LONG, FLOAT, CLOB」型等 は検索対象外）";
            //@formatter:on

            memo = new JTextArea(line);
            memo.setFont(new Font("MS UI Gothic", Font.PLAIN, 12));
            memo.setDisabledTextColor(Color.RED);
            memo.setBackground(jContentPane.getBackground());
            memo.setEnabled(false);
            memo.setBounds(new Rectangle(27, 525, 556, 120));
        }
        return memo;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
