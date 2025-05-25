package fun.yozora.admin.app;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@MapperScan("fun.yozora.**.mapper")
@ComponentScan("fun.yozora.admin")
@EnableJpaRepositories("fun.yozora")
@EntityScan("fun.yozora")
@SpringBootApplication
@EnableEncryptableProperties
public class AdminApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(AdminApplication.class, args);
    }

}
