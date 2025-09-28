package app;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.populators.HotelPopulator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        HotelPopulator.populateHotels();
        ApplicationConfig.startServer(7076);

    }
}