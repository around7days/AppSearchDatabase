package common.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

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
     * 文字列の比較を行う。<br>
     * （可変長）
     * @param str 文字列
     * @param checkStr 比較対象文字列（配列）
     * @return boolean [true:一致 false:全て不一致]
     */
    public static boolean equalsIn(String str,
                                   String... checkStr) {
        if (str == null || "".equals(str)) return false;

        boolean b = false;
        for (String check : checkStr) {
            if (str.equals(check)) {
                b = true;
                break;
            }
        }

        return b;
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
     * Listの空白(null含む)チェック<br>
     * @param list リスト
     * @return boolean [true:空白(null含む) false:空白でない]
     */
    public static boolean isEmpty(List<?> list) {
        return (list == null || list.isEmpty());
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
     * Listの空白(null含む)チェック<br>
     * @param list リスト
     * @return boolean [true:空白でない false:空白(null含む)]
     */
    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    /**
     * 文字列がnullの時、空白("")を返す。<br>
     * @param str 文字列
     * @return String 文字列
     */
    public static String nullNvl(String str) {
        return (str == null ? "" : str);
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
     * ファイルパスから親ディレクトリパスを取り出す。<br>
     * 出力例）C:\hoge
     * @param root ファイルパス
     * @return ファイル名
     */
    public static String getFileParent(String root) {
        if (root == null || "".equals(root)) return "";

        return new File(root).getParent();
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
