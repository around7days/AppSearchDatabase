package main;

import static main.SearchDataBaseConst.*;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;

import org.apache.log4j.Logger;

import common.bean.MyDatabaseInfo;
import common.manager.MyDatabaseManager;
import common.utils.MyDateUtil;
import common.utils.MyFileUtil;
import common.utils.MyPropertyUtil;
import common.utils.MyStringUtil;

/**
 * データベース検索ロジッククラス
 * @author 7days
 */
public class SearchDatabaseLogic {

    /** Logger */
    private static final Logger logger = Logger.getLogger(SearchDatabaseLogic.class);

    /** データベース接続SQLServiceクラス */
    private static final MyDatabaseManager sqlService = MyDatabaseManager.INSTANCE;
    /** データベース検索SQLロジッククラス */
    private static SearchDatabaseSql sqlLogic = new SearchDatabaseSql();

    /**
     * 初期処理
     * @param bean 画面情報Bean
     */
    public void init(SearchDatabaseBean bean) {
        logger.debug("◆init");

        // CONFディレクトリが存在しない場合は生成
        File confDir = new File(CONF_DIR);
        if (!confDir.exists()) {
            logger.debug("create conf dir : " + CONF_DIR);
            confDir.mkdirs();
        }

        // 実行ディレクトリから「*.properties」ファイルを取得
        logger.debug("load conf dir : " + CONF_DIR);
        List<String> list = MyFileUtil.getFileList(CONF_DIR, ".*\\.properties");

        // コンボボックスに取得した内容をセット
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();

        if (!list.isEmpty()) {
            // propertiesファイルが存在する場合はコンボボックスにセット
            for (String filePath : list) {
                String fileNm = MyStringUtil.getFileNm(filePath);
                model.addElement(fileNm);
                logger.debug("load conf file : " + fileNm);
            }

            // コンボボックス内容を構築
            bean.getCbDbConf().setModel(model);

            // プロパティファイルの読み込み
            load(bean);

        } else {
            // propertiesファイルが存在しない場合はデフォルト名をセット
            model.addElement(DEFAULT_PROPERTY_FILE_NAME);

            // デフォルト値でプロパティファイルの作成
            bean.getDbName().setText(DEFAULT_DB_NAME);
            bean.getDbUrl().setText(DEFAULT_DB_URL);
            bean.getDbUser().setText(DEFAULT_DB_USER);
            bean.getDbPass().setText(DEFAULT_DB_PASS);

            // コンボボックス内容を構築
            bean.getCbDbConf().setModel(model);

            // デフォルト値のプロパティファイルを作成（保存）
            save(bean);
        }

    }

    /**
     * データベース情報保存処理
     * @return 結果 [true:正常 false:異常]
     * @param bean 画面情報Bean
     */
    public boolean save(SearchDatabaseBean bean) {
        logger.debug("◆save");

        // コンソール
        StringBuilder console = new StringBuilder(bean.getConsole().getText());

        // 保存するファイル名・ファイルパスの取得
        String propFileNm = (String) bean.getCbDbConf().getSelectedItem();
        String propFilePath = Paths.get(CONF_DIR, propFileNm).toString();
        logger.debug("save conf file : " + propFilePath);

        consoleLog(console, "ファイル書込開始：" + propFileNm);

        // フィールド情報の設定
        Map<String, String> map = new HashMap<String, String>();
        map.put(MyDatabaseInfo.KEY_DB_NAME, bean.getDbName().getText());
        map.put(MyDatabaseInfo.KEY_DB_URL, bean.getDbUrl().getText());
        map.put(MyDatabaseInfo.KEY_DB_USER, bean.getDbUser().getText());
        map.put(MyDatabaseInfo.KEY_DB_PASS, bean.getDbPass().getText());
        // 設定ファイルの書込
        boolean b = MyPropertyUtil.propertySave(map, propFilePath);
        if (b == true) {
            consoleLog(console, "ファイル書込終了：" + propFileNm);
        } else {
            consoleLog(console, "[error] ファイル書込失敗：" + propFileNm);
        }

        // コンソール出力
        bean.getConsole().setText(console.toString());

        return b;
    }

    /**
     * データベース情報読込処理
     * @return 結果 [true:正常 false:異常]
     * @param bean 画面情報Bean
     */
    public boolean load(SearchDatabaseBean bean) {
        logger.debug("◆load");

        // コンソール
        StringBuilder console = new StringBuilder(bean.getConsole().getText());

        // 選択されているpropertiesファイル名を取得
        String propFileNm = (String) bean.getCbDbConf().getSelectedItem();
        String propFilePath = Paths.get(CONF_DIR, propFileNm).toString();
        try {
            consoleLog(console, "ファイル読込開始：" + propFileNm);

            // 設定ファイルの読み込み
            Map<String, String> map = MyPropertyUtil.propertyLoad(propFilePath);
            // フィールドに設定
            bean.getDbName().setText(map.get(MyDatabaseInfo.KEY_DB_NAME));
            bean.getDbUrl().setText(map.get(MyDatabaseInfo.KEY_DB_URL));
            bean.getDbUser().setText(map.get(MyDatabaseInfo.KEY_DB_USER));
            bean.getDbPass().setText(map.get(MyDatabaseInfo.KEY_DB_PASS));

            consoleLog(console, "ファイル読込終了：" + propFileNm);

            return true;
        } catch (Exception e) {
            logger.error("file load error", e);
            consoleLog(console, "[error] ファイル読込失敗：" + propFileNm);

            return false;
        } finally {
            // コンソール出力
            bean.getConsole().setText(console.toString());
        }
    }

    /**
     * データベース検索処理
     * @param bean 画面情報Bean
     */
    public void search(SearchDatabaseBean bean) {
        logger.debug("◆search");

        // コンソール
        StringBuilder console = new StringBuilder(bean.getConsole().getText());

        try {
            consoleLog(console, "******************* 検索開始 *******************");

            // DB接続
            // データベース接続Serviceの設定
            MyDatabaseInfo dbInfo = new MyDatabaseInfo();
            dbInfo.setDbUrl(bean.getDbUrl().getText());
            dbInfo.setDbUser(bean.getDbUser().getText());
            dbInfo.setDbPass(bean.getDbPass().getText());
            sqlService.setProperty(dbInfo);

            // DB接続開始
            boolean isOpen = sqlService.DBOpen();
            if (!isOpen) {
                // DB接続失敗
                consoleLog(console, "[error] DB接続失敗");
                consoleLog(console, "******************* 検索エラー *****************");
                return;
            }

            /*
             * 検索処理開始
             */
            execute(bean, console);

            consoleLog(console, "******************* 検索終了 *******************");
        } catch (Exception e) {
            logger.error("search error", e);
            consoleLog(console, "******************* 検索エラー *****************");
        } finally {
            // コンソール出力
            bean.getConsole().setText(console.toString());
            // DB切断
            sqlService.DBClose();
        }
    }

    /**
     * 保存ボタン 入力チェック
     * @param bean 画面情報Bean
     * @return 結果 [true:エラーあり false:エラーなし]
     */
    public boolean isSaveError(SearchDatabaseBean bean) {
        // コンソール
        StringBuilder console = new StringBuilder(bean.getConsole().getText());

        // 結果 [true:エラーあり false:エラーなし]
        boolean ret = false;

        // ファイル名の取得
        String propFileNm = (String) bean.getCbDbConf().getSelectedItem();

        // 拡張子チェック結果
        if (!"properties".equals(MyStringUtil.getFileFormat(propFileNm))) {
            consoleLog(console, "[error] ファイル名の拡張子は「properties」を指定してください。");
            ret = true;
        }

        // コンソール出力
        bean.getConsole().setText(console.toString());

        return ret;
    }

    /**
     * 検索ボタン 入力チェック
     * @param bean 画面情報Bean
     * @return 結果 [true:エラーあり false:エラーなし]
     */
    public boolean isSearchError(SearchDatabaseBean bean) {
        // コンソール
        StringBuilder console = new StringBuilder(bean.getConsole().getText());

        // 結果 [true:エラーあり false:エラーなし]
        boolean ret = false;

        // 入力チェック結果
        if (MyStringUtil.isEmpty(bean.getDbUrl().getText())) {
            consoleLog(console, "[error] 接続URLを入力してください。");
            ret = true;
        }
        if (MyStringUtil.isEmpty(bean.getDbUser().getText())) {
            consoleLog(console, "[error] ユーザー名を入力してください。");
            ret = true;
        }
        if (MyStringUtil.isEmpty(bean.getDbPass().getText())) {
            consoleLog(console, "[error] パスワードを入力してください。");
            ret = true;
        }
        if (MyStringUtil.isEmpty(bean.getSearchValue().getText())) {
            consoleLog(console, "[error] 検索値を入力してください。");
            ret = true;
        }
        if (MyStringUtil.isEmpty(bean.getSearchTableNm().getText()) && !bean.getChkAllTable().isSelected()) {
            consoleLog(console, "[error] テーブル名を入力してください。");
            ret = true;
        }
        if (MyStringUtil.isEmpty(bean.getSearchColNm().getText()) && !bean.getChkAllCol().isSelected()) {
            consoleLog(console, "[error] 項目名を入力してください。");
            ret = true;
        }

        // コンソール出力
        bean.getConsole().setText(console.toString());

        return ret;
    }

    /**
     * コンソールのクリア
     * @param bean 画面情報Bean
     */
    public void consoleClear(SearchDatabaseBean bean) {
        logger.debug("◆consoleClear");

        // コンソールのクリア
        bean.getConsole().setText("");
    }

    /**
     * メイン処理
     * @param bean 画面情報Bean
     * @param console コンソール
     * @throws Exception
     */
    private void execute(SearchDatabaseBean bean,
                         StringBuilder console) throws SQLException {

        // テーブル名リストの取得
        List<Map<String, String>> tableList = sqlLogic.getTableList(bean);
        logger.debug("table count :" + tableList.size());
        if (tableList.isEmpty()) {
            // 検索対象のテーブルが存在しない
            return;
        }

        // テーブル単位で検索処理の実施
        for (Map<String, String> tableMap : tableList) {
            // テーブル名の取得
            String tableNm = tableMap.get("TABLE_NAME");
            logger.debug("table name :" + tableNm);

            // 項目名リストの取得
            List<Map<String, String>> colList = sqlLogic.getColumnList(tableNm, bean);
            logger.debug("table column count :" + colList.size());
            if (colList.isEmpty()) {
                // 検索対象の項目が存在しない
                continue;
            }

            // 検索値の存在チェック
            boolean hasCheckSeachValueAll = sqlLogic.checkSeachValueAll(tableNm, colList, bean);
            if (hasCheckSeachValueAll == false) {
                // テーブルに検索値が存在しない
                continue;
            }

            if (bean.getRdTableCol().isSelected()) {
                // 項目名まで検索

                for (Map<String, String> colMap : colList) {
                    // 検索値の存在チェック（項目単位で確認）
                    boolean hasCheckSeachValue = sqlLogic.checkSeachValue(tableNm,
                                                                          colMap.get("COLUMN_NAME"),
                                                                          colMap.get("DATA_TYPE"),
                                                                          bean);
                    if (hasCheckSeachValue == false) {
                        // 該当しない
                        continue;
                    }

                    // コンソール出力（テーブル名＋項目名）
                    console.append("テーブル名：" + tableNm + " / ");
                    console.append("項目名：" + colMap.get("COLUMN_NAME") + NEW_LINE);

                }
            } else {
                // テーブル名のみ

                // コンソール出力（テーブル名）
                console.append("テーブル名：" + tableNm + NEW_LINE);
            }
        }

    }

    /**
     * コンソール出力内容を返却
     * @param console コンソール
     * @param message 追記メッセージ
     */
    private void consoleLog(StringBuilder console,
                            String message) {
        console.append("[" + MyDateUtil.getStringSysDate(MyDateUtil.HHMMSS) + "] " + message + NEW_LINE);
    }

}
