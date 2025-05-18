package fun.yozora.admin.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("fun.yozora.admin.core.mapper")
@ComponentScan("fun.yozora.admin")
@SpringBootApplication
public class AdminAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminAppApplication.class, args);
    }

}
