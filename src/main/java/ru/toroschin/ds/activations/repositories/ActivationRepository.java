package ru.toroschin.ds.activations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.toroschin.ds.activations.models.Activation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivationRepository extends JpaRepository<Activation, String> {
    @Query("select a from Activation a where a.name not in ('Dell', 'Lenovo', 'Дедал-сервис', '') and a.code not in ('VI', 'GUEST', 'AndreiTwsst') and a.dateDisconnect not like '%.%' and a.dateDisconnect not like '%/18' and NOTEUTENTE not like 'VI%' order by a.name")
    List<Activation> findAllActiv();

    @Query("select a from Activation a where a.name not in ('') and a.code not in ('GUEST', 'AndreiTwsst', '(test)') order by a.name")
    List<Activation> findAllLK();

    @Query(value = "select TOP 1 * from [Ecadmaster].[dbo].[STATISTICHESV] a where a.RAGSOC not in ('Dell', 'Lenovo', 'Дедал-сервис', '') and a.CODCLI not in ('VI', 'GUEST', 'AndreiTwsst', '(test)')", nativeQuery = true)
    Optional<Activation> findOneForDate();
}

