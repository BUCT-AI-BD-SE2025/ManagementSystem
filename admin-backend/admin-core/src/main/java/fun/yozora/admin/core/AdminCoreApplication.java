package fun.yozora.admin.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("fun.yozora.admin.core.mapper")
@SpringBootApplication
public class AdminCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminCoreApplication.class, args);
	}

}
