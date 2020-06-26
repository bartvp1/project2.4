package MeetUpAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class MeetUpAPI implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(MeetUpAPI.class);

  public static void main(String[] args) {
    LOG.info("Starting API");
    SpringApplication.run(MeetUpAPI.class, args);
  }

  @Override
  public void run(String... args) {
    LOG.info("Running...");
  }

}
