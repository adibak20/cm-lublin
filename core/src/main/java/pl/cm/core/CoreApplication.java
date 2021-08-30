package pl.cm.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.cm.core.services.IntService;

@SpringBootApplication
public class CoreApplication {

    @Autowired
    IntService intService;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CoreApplication.class, args);
        CoreApplication application = context.getBean(CoreApplication.class);
        application.init();

    }

    public void init(){
        intService.run();
    }


}
