package fun.yozora.admin.web.controller;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbTestController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/test-db")
    public String testConnection() {
        try (Connection conn = dataSource.getConnection()) {
            return "数据库连接成功！Database: " + conn.getMetaData().getDatabaseProductName();
        } catch (SQLException e) {
            return "数据库连接失败：" + e.getMessage();
        }
    }
}