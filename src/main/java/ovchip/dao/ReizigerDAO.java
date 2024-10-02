package ovchip.dao;

import ovchip.domain.Reiziger;

import java.time.LocalDate;
import java.util.List;

public interface ReizigerDAO {
    boolean save(Reiziger reiziger);
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);
    Reiziger findById(int id);
    List<Reiziger> findByGbdatum(LocalDate datum);
    List<Reiziger> findAll();
}
