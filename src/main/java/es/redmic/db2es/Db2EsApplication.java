package es.redmic.db2es;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.Module;

import es.redmic.databaselib.common.repository.BaseRepositoryImpl;
import es.redmic.db.config.EntityManagerWrapper;
import es.redmic.db2es.config.OrikaScanBean;

@SpringBootApplication(scanBasePackages = { "es.redmic" }, exclude = { MongoAutoConfiguration.class, ElasticsearchAutoConfiguration.class })
@EnableTransactionManagement
@ComponentScan(basePackages = { "es.redmic" })
@EntityScan({ "es.redmic.databaselib", "es.redmic.db" })
@EnableJpaRepositories(basePackages = { "es.redmic.databaselib.user.repository",
		"es.redmic.db" }, repositoryBaseClass = BaseRepositoryImpl.class)
@EnableScheduling
public class Db2EsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Db2EsApplication.class, args);
	}

	@Bean
	public EntityManagerWrapper entityManagerWrapper() {
		return new EntityManagerWrapper();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
		p.setIgnoreUnresolvablePlaceholders(true);
		return p;
	}

	@PostConstruct
	@Bean
	public OrikaScanBean orikaScanBean() {
		return new OrikaScanBean();
	}

	@Bean
	public Module jtsModule() {
		return new JtsModule();
	}
}
