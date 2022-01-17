package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@ComponentScan({"repo","srv"})
@SpringBootApplication
public class RestServer {
    public static void main(String[] args) {
        SpringApplication.run(RestServer.class, args);
    }

    @Bean(name="props")
    public Properties getBdProperties(){
        Properties props = new Properties();
        try {
            System.out.println("Searching config in directory "+((new File(".")).getAbsolutePath()));
            props.load(new FileReader("config.properties"));
        } catch (IOException e) {
            System.err.println("Configuration file config.properties not found" + e);

        }
        return props;
    }
}
