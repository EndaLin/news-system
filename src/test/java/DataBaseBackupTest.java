import service.DataBaseBackupService;

/**
 * @Author: wt
 * @Date: 2019/1/31 14:25
 */
public class DataBaseBackupTest {
    public static void main(String[] args) {
        DataBaseBackupService dataBaseBackupService = new DataBaseBackupService();
        dataBaseBackupService.backup();
    }
}
