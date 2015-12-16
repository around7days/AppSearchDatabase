package main;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

/**
 * データベース検索Beanクラス
 * @author 7days
 */
public class SearchDatabaseBean {

    /** Logger */
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(SearchDatabaseBean.class);

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
    /** カラム名 */
    private JTextField searchColNm = null;
    /** 検索値 LIKE検索チェックボックス */
    private JCheckBox chkLikeVal = null;
    /** テーブル名 LIKE検索チェックボックス */
    private JCheckBox chkLikeTable = null;
    /** カラム名 LIKE検索チェックボックス */
    private JCheckBox chkLikeCol = null;
    /** テーブル名 全検索チェックボックス */
    private JCheckBox chkAllTable = null;
    /** カラム名 全検索チェックボックス */
    private JCheckBox chkAllCol = null;
    /** コンソール */
    private JTextArea console = null;
    /** 抽出項目 テーブル名＋項目名ラジオボタン */
    private JRadioButton rdTableCol = null;
    /** 抽出項目 テーブル名ラジオボタン */
    private JRadioButton rdTable = null;
    /** 実行中ステータス */
    private JLabel statusLabel = null;

    /**
     * データベース設定ファイルリストを取得します。
     * @return データベース設定ファイルリスト
     */
    public JComboBox<String> getCbDbConf() {
        return cbDbConf;
    }

    /**
     * データベース設定ファイルリストを設定します。
     * @param cbDbConf データベース設定ファイルリスト
     */
    public void setCbDbConf(JComboBox<String> cbDbConf) {
        this.cbDbConf = cbDbConf;
    }

    /**
     * データベース名を取得します。
     * @return データベース名
     */
    public JTextField getDbName() {
        return dbName;
    }

    /**
     * データベース名を設定します。
     * @param dbName データベース名
     */
    public void setDbName(JTextField dbName) {
        this.dbName = dbName;
    }

    /**
     * 接続URLを取得します。
     * @return 接続URL
     */
    public JTextField getDbUrl() {
        return dbUrl;
    }

    /**
     * 接続URLを設定します。
     * @param dbUrl 接続URL
     */
    public void setDbUrl(JTextField dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * ユーザー名を取得します。
     * @return ユーザー名
     */
    public JTextField getDbUser() {
        return dbUser;
    }

    /**
     * ユーザー名を設定します。
     * @param dbUser ユーザー名
     */
    public void setDbUser(JTextField dbUser) {
        this.dbUser = dbUser;
    }

    /**
     * パスワードを取得します。
     * @return パスワード
     */
    public JTextField getDbPass() {
        return dbPass;
    }

    /**
     * パスワードを設定します。
     * @param dbPass パスワード
     */
    public void setDbPass(JTextField dbPass) {
        this.dbPass = dbPass;
    }

    /**
     * 検索値を取得します。
     * @return 検索値
     */
    public JTextField getSearchValue() {
        return searchValue;
    }

    /**
     * 検索値を設定します。
     * @param searchValue 検索値
     */
    public void setSearchValue(JTextField searchValue) {
        this.searchValue = searchValue;
    }

    /**
     * テーブル名を取得します。
     * @return テーブル名
     */
    public JTextField getSearchTableNm() {
        return searchTableNm;
    }

    /**
     * テーブル名を設定します。
     * @param searchTableNm テーブル名
     */
    public void setSearchTableNm(JTextField searchTableNm) {
        this.searchTableNm = searchTableNm;
    }

    /**
     * カラム名を取得します。
     * @return カラム名
     */
    public JTextField getSearchColNm() {
        return searchColNm;
    }

    /**
     * カラム名を設定します。
     * @param searchColNm カラム名
     */
    public void setSearchColNm(JTextField searchColNm) {
        this.searchColNm = searchColNm;
    }

    /**
     * 検索値 LIKE検索チェックボックスを取得します。
     * @return 検索値 LIKE検索チェックボックス
     */
    public JCheckBox getChkLikeVal() {
        return chkLikeVal;
    }

    /**
     * 検索値 LIKE検索チェックボックスを設定します。
     * @param chkLikeVal 検索値 LIKE検索チェックボックス
     */
    public void setChkLikeVal(JCheckBox chkLikeVal) {
        this.chkLikeVal = chkLikeVal;
    }

    /**
     * テーブル名 LIKE検索チェックボックスを取得します。
     * @return テーブル名 LIKE検索チェックボックス
     */
    public JCheckBox getChkLikeTable() {
        return chkLikeTable;
    }

    /**
     * テーブル名 LIKE検索チェックボックスを設定します。
     * @param chkLikeTable テーブル名 LIKE検索チェックボックス
     */
    public void setChkLikeTable(JCheckBox chkLikeTable) {
        this.chkLikeTable = chkLikeTable;
    }

    /**
     * カラム名 LIKE検索チェックボックスを取得します。
     * @return カラム名 LIKE検索チェックボックス
     */
    public JCheckBox getChkLikeCol() {
        return chkLikeCol;
    }

    /**
     * カラム名 LIKE検索チェックボックスを設定します。
     * @param chkLikeCol カラム名 LIKE検索チェックボックス
     */
    public void setChkLikeCol(JCheckBox chkLikeCol) {
        this.chkLikeCol = chkLikeCol;
    }

    /**
     * テーブル名 全検索チェックボックスを取得します。
     * @return テーブル名 全検索チェックボックス
     */
    public JCheckBox getChkAllTable() {
        return chkAllTable;
    }

    /**
     * テーブル名 全検索チェックボックスを設定します。
     * @param chkAllTable テーブル名 全検索チェックボックス
     */
    public void setChkAllTable(JCheckBox chkAllTable) {
        this.chkAllTable = chkAllTable;
    }

    /**
     * カラム名 全検索チェックボックスを取得します。
     * @return カラム名 全検索チェックボックス
     */
    public JCheckBox getChkAllCol() {
        return chkAllCol;
    }

    /**
     * カラム名 全検索チェックボックスを設定します。
     * @param chkAllCol カラム名 全検索チェックボックス
     */
    public void setChkAllCol(JCheckBox chkAllCol) {
        this.chkAllCol = chkAllCol;
    }

    /**
     * コンソールを取得します。
     * @return コンソール
     */
    public JTextArea getConsole() {
        return console;
    }

    /**
     * コンソールを設定します。
     * @param console コンソール
     */
    public void setConsole(JTextArea console) {
        this.console = console;
    }

    /**
     * 抽出項目 テーブル名＋項目名ラジオボタンを取得します。
     * @return 抽出項目 テーブル名＋項目名ラジオボタン
     */
    public JRadioButton getRdTableCol() {
        return rdTableCol;
    }

    /**
     * 抽出項目 テーブル名＋項目名ラジオボタンを設定します。
     * @param rdTableCol 抽出項目 テーブル名＋項目名ラジオボタン
     */
    public void setRdTableCol(JRadioButton rdTableCol) {
        this.rdTableCol = rdTableCol;
    }

    /**
     * 抽出項目 テーブル名ラジオボタンを取得します。
     * @return 抽出項目 テーブル名ラジオボタン
     */
    public JRadioButton getRdTable() {
        return rdTable;
    }

    /**
     * 抽出項目 テーブル名ラジオボタンを設定します。
     * @param rdTable 抽出項目 テーブル名ラジオボタン
     */
    public void setRdTable(JRadioButton rdTable) {
        this.rdTable = rdTable;
    }

    /**
     * 実行中ステータスを取得します。
     * @return 実行中ステータス
     */
    public JLabel getStatusLabel() {
        return statusLabel;
    }

    /**
     * 実行中ステータスを設定します。
     * @param statusLabel 実行中ステータス
     */
    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

}
