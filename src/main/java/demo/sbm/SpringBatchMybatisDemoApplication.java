package demo.sbm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "demo.sbm.mapper")
public class SpringBatchMybatisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchMybatisDemoApplication.class, args);
    }

}
