package connection;

import model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionFactorySingleton {

    private SessionFactorySingleton() {
    }

    private static class LazyHolder {
        static SessionFactory INSTANCE;

        static {
            var registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            INSTANCE = new MetadataSources(registry)
                    .addAnnotatedClass(Address.class)
                    .addAnnotatedClass(Admin.class)
                    .addAnnotatedClass(Comment.class)
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Order.class)
                    .addAnnotatedClass(Proposal.class)
                    .addAnnotatedClass(Service.class)
                    .addAnnotatedClass(Specialist.class)
                    .addAnnotatedClass(SubService.class)
                    .buildMetadata()
                    .buildSessionFactory();
        }
    }

    public static SessionFactory getInstance() {
        return LazyHolder.INSTANCE;
    }
}
