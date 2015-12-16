package main;

import static main.SearchDataBaseConst.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import common.manager.MyDatabaseManager;
import common.utils.MyStringUtil;

/**
 * データベース検索SQLクラス
 * @author 7days
 */
public class SearchDatabaseSql {

    /** Logger */
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(SearchDatabaseSql.class);

    /** データベース接続Serviceクラス */
    private static final MyDatabaseManager sqlService = MyDatabaseManager.INSTANCE;

    /**
     * テーブルリストの取得
     * @param bean 画面情報Bean
     * @return テーブルリスト
     * @throws SQLException
     */
    public List<Map<String, String>> getTableList(SearchDatabaseBean bean) throws SQLException {

        // SQL文の生成
        StringBuffer sql = new StringBuffer();

        sql.append("select distinct ");
        sql.append("    TABLE_NAME ");
        sql.append("from ");
        sql.append("    USER_TABLES ");
        sql.append("where ");
        sql.append("    TABLE_NAME is not null "); // 面倒なんで空条件
        if (!bean.getChkAllTable().isSelected()) {
            // 全検索以外
            // 比較演算子の取得（Like演算子 or =演算子）
            String comparisonOperator = bean.getChkLikeTable().isSelected() ? "like" : "=";
            sql.append("and TABLE_NAME " + comparisonOperator + " '" + bean.getSearchTableNm().getText() + "' ");
        }
        sql.append("order by TABLE_NAME ");

        return sqlService.doSelect(sql.toString());
    }

    /**
     * 項目名リストの取得
     * @param tableNm 検索対象テーブル名
     * @param bean 画面情報Bean
     * @return 項目名リスト
     * @throws SQLException
     */
    public List<Map<String, String>> getColumnList(String tableNm,
                                                   SearchDatabaseBean bean) throws SQLException {

        // SQL文の生成
        StringBuffer sql = new StringBuffer();

        sql.append("select ");
        sql.append("    TABLE_NAME, ");
        sql.append("    COLUMN_NAME, ");
        sql.append("    DATA_TYPE, ");
        sql.append("    DATA_LENGTH ");
        sql.append("from ");
        sql.append("    USER_TAB_COLUMNS ");
        sql.append("where ");
        sql.append("    TABLE_NAME = '" + tableNm + "' ");
        if (!bean.getChkAllCol().isSelected()) {
            // 全検索以外
            // 比較演算子の取得（Like演算子 or =演算子）
            String comparisonOperator = bean.getChkLikeCol().isSelected() ? "like" : "=";
            sql.append("and COLUMN_NAME " + comparisonOperator + " '" + bean.getSearchColNm().getText() + "' ");
        }
        sql.append("order by ");
        sql.append("    TABLE_NAME, ");
        sql.append("    COLUMN_ID ");

        return sqlService.doSelect(sql.toString());
    }

    /**
     * 検索値の存在チェック（全項目検索）
     * @param tableNm 検索対象テーブル名
     * @param colList 検索対象項目名リスト
     * @param bean 画面情報Bean
     * @return 結果 [true:存在する false:存在しない]
     * @throws SQLException
     */
    public boolean checkSeachValueAll(String tableNm,
                                      List<Map<String, String>> colList,
                                      SearchDatabaseBean bean) throws SQLException {

        // SQL文の生成
        StringBuffer sql = new StringBuffer();

        sql.append("select ");
        sql.append("    'X' ");
        sql.append("from ");
        sql.append("    " + tableNm + " ");
        sql.append("where ");
        boolean flg = false;
        for (Map<String, String> colMap : colList) {
            String whereCondition = getWhereCondition(colMap.get("COLUMN_NAME"), colMap.get("DATA_TYPE"), bean);
            if (MyStringUtil.isNotEmpty(whereCondition)) {
                sql.append(whereCondition + " or ");
                flg = true;
            }
        }

        if (flg == false) {
            // where区に対象となる項目が存在しない
            return false;
        }

        // 最後の接続詞(OR)を取り除く
        String sqlVal = sql.substring(0, sql.length() - 3);

        // 検索結果の取得
        List<Map<String, String>> list = sqlService.doSelect(sqlVal);
        if (MyStringUtil.isNotEmpty(list)) {
            // 存在する
            return true;
        }
        // 存在しない
        return false;
    }

    /**
     * 検索値の存在チェック（個別項目検索）
     * @param tableNm テーブル名
     * @param colMap 項目情報
     * @param bean 画面情報Bean
     * @return 結果 [true:存在する false:存在しない]
     * @throws SQLException
     */
    public boolean checkSeachValue(String tableNm,
                                   Map<String, String> colMap,
                                   SearchDatabaseBean bean) throws SQLException {

        String whereCondition = getWhereCondition(colMap.get("COLUMN_NAME"), colMap.get("DATA_TYPE"), bean);
        if (MyStringUtil.isEmpty(whereCondition)) {
            return false;
        }

        // SQL文の生成
        StringBuffer sql = new StringBuffer();

        sql.append("select ");
        sql.append("    'X' ");
        sql.append("from ");
        sql.append("    " + tableNm + " ");
        sql.append("where ");
        sql.append(whereCondition);

        // 検索結果の取得
        List<Map<String, String>> list = sqlService.doSelect(sql.toString());
        if (MyStringUtil.isNotEmpty(list)) {
            // 存在する
            return true;
        }
        // 存在しない
        return false;
    }

    /**
     * データ型に対応した検索条件を取得する
     * @param COLUMN_NAME 項目名
     * @param DATA_TYPE データ型
     * @param bean 検索値
     * @return 検索条件
     */
    private String getWhereCondition(String COLUMN_NAME,
                                     String DATA_TYPE,
                                     SearchDatabaseBean bean) {
        // 検索対象文字列
        String searchValue = bean.getSearchValue().getText();

        // 比較演算子の取得（Like演算子 or =演算子）
        String comparisonOperator = bean.getChkLikeVal().isSelected() ? "like" : "=";

        // 検索条件格納クラスの生成
        StringBuffer bf = new StringBuffer();
        if (DATA_TYPE_VARCHAR2.equals(DATA_TYPE) || DATA_TYPE_CHAR.equals(DATA_TYPE)) {
            // VARCHAR2型
            // CHAR型
            bf.append(COLUMN_NAME + " " + comparisonOperator + " '" + searchValue + "' ");
        } else if (DATA_TYPE_DATE.equals(DATA_TYPE) || searchValue.startsWith(DATA_TYPE_TIMESTAMP)) {
            // DATE型
            // TIMESTAMP型
            if (MyStringUtil.isDate(searchValue)) {
                bf.append("to_char(" + COLUMN_NAME + ", 'yyyy/mm/dd') " + comparisonOperator + " '" + searchValue
                          + "' ");
            }
        } else if (DATA_TYPE_NUMBER.equals(DATA_TYPE)) {
            // NUMBER型
            if (MyStringUtil.isInteger(searchValue)) {
                bf.append(COLUMN_NAME + " " + comparisonOperator + " '" + searchValue + "' ");
            }
        } else {
            // LONG型、FLOAT型 etc...
        }
        return bf.toString();
    }
}
