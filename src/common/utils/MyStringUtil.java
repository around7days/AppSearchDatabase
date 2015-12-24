package common.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;

import org.apache.log4j.Logger;

/**
 * 文字列操作の共通クラス<br>
 * @author 7days
 */
public class MyStringUtil {

    /** Logger */
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(MyStringUtil.class);

    /** 文字コード シフトJIS */
    public static final String ENCODE_TYPE_SJIS = "Shift_JIS";
    /** 文字コード UTF-8 */
    public static final String ENCODE_TYPE_UTF8 = "UTF8";
    /** 文字コード EUC */
    public static final String ENCODE_TYPE_EUC = "EUC-JP";
    /** 文字コード Windows-31J */
    public static final String ENCODE_TYPE_WIN31J = "Windows-31J";

    /**
     * 数字（半角）のチェックを行う。<br>
     * @param str 対象文字列
     * @return boolean [true:数字 false:数字以外]
     */
    public static boolean isInteger(String str) {
        if (str == null || "".equals(str)) return false;

        try {
            Long.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 日付の妥当性チェックを行います。<br>
     * （yyyyMMdd or yyyy/MM/dd or yyyy-MM-dd）
     * @param strDate チェック対象の文字列
     * @return boolean [true:日付 false:日付以外]
     */
    public static boolean isDate(String strDate) {
        if (strDate == null || "".equals(strDate)) return false;

        strDate = strDate.replace('-', '/');
        DateFormat format = DateFormat.getDateInstance();
        format.setLenient(false);
        try {
            format.parse(strDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 空白(null含む)チェック<br>
     * @param str 文字列
     * @return boolean [true:空白(null含む) false:空白でない]
     */
    public static boolean isEmpty(String str) {
        return (str == null || "".equals(str));
    }

    /**
     * 空白(null含む)チェック<br>
     * @param str 文字列
     * @return boolean [true:空白でない false:空白(null含む)]
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * ファイルパスからファイル名のみを取り出す。<br>
     * 出力例）hoge.txt
     * @param root ファイルパス
     * @return ファイル名
     */
    public static String getFileNm(String root) {
        if (root == null || "".equals(root)) return "";

        return new File(root).getName();
    }

    /**
     * 拡張子を取り出す。<br>
     * 出力例）txt
     * @param root ファイルパスorファイル名
     * @return 拡張子
     */
    public static String getFileFormat(String root) {
        if (root == null || "".equals(root)) return "";

        int lastIndex = root.lastIndexOf(".");
        if (lastIndex == -1) return "";

        return root.substring(lastIndex + 1);
    }

}
