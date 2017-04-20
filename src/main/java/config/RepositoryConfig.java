package config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import rest.RepositoryController;
import service.NetworkService;

@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories("repository")
public class RepositoryConfig extends Neo4jConfiguration{

    @Bean
    public RepositoryController repositoryController(){
        return new RepositoryController();
    }

    @Bean
    public NetworkService networkService(){
        return new NetworkService();
    }

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory("domain");
    }
}
