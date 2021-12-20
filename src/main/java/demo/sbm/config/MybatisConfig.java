package demo.sbm.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Mybatis 配置多个 SqlSessionFactory
 *
 * @author Victor Zhou
 */
@Configuration
public class MybatisConfig {

    @Primary
    @Bean(name = "ssf")
    public SqlSessionFactory settlementSqlSessionFactory(DataSource ds) throws Exception {
        return getSqlSessionFactory(ds);
    }


    @Bean(name = "batchSsf")
    public SqlSessionFactory priceEngineSqlSessionFactory(DataSource ds) throws Exception {
        return getSqlSessionFactory(ds);
    }

    private SqlSessionFactory getSqlSessionFactory(DataSource ds) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(ds);
        factory.setVfs(SpringBootVFS.class);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        factory.setConfiguration(configuration);
        return factory.getObject();
    }
}
