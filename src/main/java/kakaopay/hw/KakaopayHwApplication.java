package kakaopay.hw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "kakaopay.hw")
public class KakaopayHwApplication {

    public static void main(String[] args) {
        SpringApplication.run(KakaopayHwApplication.class, args);
    }
}
