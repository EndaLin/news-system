package service;


/**
 * @Author: wt
 * @Date: 2018/11/26 20:22
 */
public class IsExcel2003ServiceImpl{
    public boolean isExcel2003(String path) {
        return path.matches("^.+\\.(?i)(xls)$");
    }
}
