package ovchip.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ovchip.domain.Reiziger;

import java.time.LocalDate;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {

    private final SessionFactory factory;

    public ReizigerDAOHibernate(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(reiziger);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.update(reiziger);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(reiziger);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        try (Session session = factory.openSession()) {
            return session.get(Reiziger.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(LocalDate datum) {
        try (Session session = factory.openSession()) {
            Query<Reiziger> query = session.createQuery("FROM Reiziger WHERE geboortedatum = :datum", Reiziger.class);
            query.setParameter("datum", datum);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Reiziger", Reiziger.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

