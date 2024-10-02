package ovchip.main;

import org.hibernate.SessionFactory;
import ovchip.dao.ReizigerDAO;
import ovchip.dao.ReizigerDAOHibernate;
import ovchip.domain.Reiziger;
import ovchip.util.HibernateUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        SessionFactory factory = HibernateUtil.getSessionFactory();

        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(factory);

        try {
            testReizigerDAO(rdao);
        } finally {
            HibernateUtil.shutdown();
        }
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     * <p>
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = null;
        reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum).toLocalDate());
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Test update operatie
        System.out.println("[Test] Update reiziger 77 (Sietske Boers) achternaam naar 'Jansen'");
        sietske.setAchternaam("Jansen");
        rdao.update(sietske);
        Reiziger updatedReiziger = null;
        updatedReiziger = rdao.findById(77);
        System.out.println("Geüpdatete reiziger: " + updatedReiziger);
        System.out.println();

        // Test delete operatie
        System.out.println("[Test] Verwijder reiziger 77 (Sietske Jansen)");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println("Aantal reizigers na verwijderen: " + reizigers.size() + "\n");

        // Controleer of reiziger 77 succesvol is verwijderd
        Reiziger deletedReiziger = null;
        deletedReiziger = rdao.findById(77);
        if (deletedReiziger == null) {
            System.out.println("Reiziger 77 is succesvol verwijderd.");
        } else {
            System.out.println("Reiziger 77 is NIET succesvol verwijderd: " + deletedReiziger);
        }
    }
}
