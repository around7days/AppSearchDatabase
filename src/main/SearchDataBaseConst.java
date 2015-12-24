package main;

import java.nio.file.Paths;

/**
 * データベース検索コード定義クラス<br>
 * @author 7days
 */
public class SearchDataBaseConst {

    /*
     * 環境系
     */
    /** 実行ディレクトリパス */
    private static final String RUN_DIR = System.getProperty("user.dir");
    /** confディレクトリパス */
    public static final String CONF_DIR = Paths.get(RUN_DIR, "conf").toString();
    /** コンソール改行コード(\n) */
    public static final String NEW_LINE = "\n";

    /*
     * デフォルト設定値
     */
    /** デフォルト設定値（ファイル名） */
    public static final String DEFAULT_PROPERTY_FILE_NAME = "SearchDatabase.properties";
    /** デフォルト設定値（DB名） */
    public static final String DEFAULT_DB_NAME = "サンプルDB";
    /** デフォルト設定値（DB URL） */
    public static final String DEFAULT_DB_URL = "jdbc:oracle:thin:@localhost:1521:sampleSID";
    /** デフォルト設定値（DBユーザー） */
    public static final String DEFAULT_DB_USER = "user";
    /** デフォルト設定値（DBパスワード） */
    public static final String DEFAULT_DB_PASS = "pass";

    /*
     * Oracleデータ型
     */
    /** Oracle データ型(CHAR) */
    public static final String DATA_TYPE_CHAR = "CHAR";
    /** Oracle データ型(VARCHAR2) */
    public static final String DATA_TYPE_VARCHAR2 = "VARCHAR2";
    /** Oracle データ型(NUMBER) */
    public static final String DATA_TYPE_NUMBER = "NUMBER";
    /** Oracle データ型(DATE) */
    public static final String DATA_TYPE_DATE = "DATE";
    /** Oracle データ型(TIMESTAMP) */
    public static final String DATA_TYPE_TIMESTAMP = "TIMESTAMP";

    /*
     * Mapキー
     */
    /** 検索結果格納Mapキー(テーブル名) */
    public static final String RESULT_MAP_KEY_TABLE_NAME = "tableNm";
    /** 検索結果格納Mapキー(カラム名) */
    public static final String RESULT_MAP_KEY_COLUMN_NAME = "colNm";

}
