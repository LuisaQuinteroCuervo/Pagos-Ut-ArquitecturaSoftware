package com.systempaymentut.proyecto_fullstack_backend_ut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systempaymentut.proyecto_fullstack_backend_ut.entities.Pago;
import com.systempaymentut.proyecto_fullstack_backend_ut.enums.PagoStatus;
import com.systempaymentut.proyecto_fullstack_backend_ut.enums.TypePago;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    //lista de pagos asociados a un estudiante
    List<Pago> findByEstudianteCodigo(String codigo);

    //lista para buscar los pagos por su estado
    List<Pago> findByStatus(PagoStatus status);    

    List<Pago> findByType(TypePago type);    



    
}
