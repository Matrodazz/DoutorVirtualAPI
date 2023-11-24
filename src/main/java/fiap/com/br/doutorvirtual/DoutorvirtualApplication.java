package fiap.com.br.doutorvirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootApplication
public class DoutorvirtualApplication implements ApplicationListener<ContextClosedEvent> {

    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(DoutorvirtualApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}