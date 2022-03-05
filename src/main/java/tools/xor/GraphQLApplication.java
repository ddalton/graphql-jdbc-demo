package tools.xor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:spring-graphql.xml"})
public class GraphQLApplication
{
    public static void main(String[] args) {
        SpringApplication.run(GraphQLApplication.class, args);
    }
}
