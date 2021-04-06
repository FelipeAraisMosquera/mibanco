package edu.eam.ingesoft.ejemploback.repositories;


import edu.eam.ingesoft.ejemploback.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// lo extiende a la transaccion que como llave primaria tiene un string
public interface TransaccionRepository extends JpaRepository <Transaccion, String> {

    //listar transacciones de cliente
    @Query("SELECT o FROM Transaccion o  WHERE o.numerocuenta = :id")
    List<Transaccion> buscarTransaccionesCuenta(String id);
}

