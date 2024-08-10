package ex02;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.test.context.ActiveProfiles;

@SpringBootConfiguration
public class TestProfileConfig {
    @ActiveProfiles({"test01", "test02", "test03", "test04", "test05"})
    @SpringBootConfiguration
    @EntityScan(basePackages = {"ex02.domain01_05"})
    public class ConfigDomain01_05 {
    }

    @ActiveProfiles({"test06", "test07"})
    @SpringBootConfiguration
    @EntityScan(basePackages = {"ex02.domain06_07"})
    public class ConfigDomain06_07 {
    }
}
