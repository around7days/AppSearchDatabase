package common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * ファイル操作の共通クラス<br>
 * @author 7days
 */
public class MyFileUtil {

    /** Logger */
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(MyFileUtil.class);

    /**
     * 選択されたパスに存在するファイルリストを取得<br>
     * 単一階層のみ、正規表現にて検索
     * @param searchDir 検索対象フォルダ
     * @param regex ファイル名（正規表現）
     * @return 検索結果List
     */
    public static List<String> getFileList(String searchDir,
                                           String regex) {
        // 検索結果の格納List
        List<String> list = new ArrayList<String>();

        File dir = new File(searchDir);
        if (!dir.isDirectory()) {
            return list;
        }

        for (String str : dir.list()) {
            if (str.matches(regex)) {
                list.add(dir.toPath().resolve(str).toString());
            }
        }

        return list;
    }

}
