package com.systempaymentut.proyecto_fullstack_backend_ut.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.systempaymentut.proyecto_fullstack_backend_ut.entities.Estudiante;
import com.systempaymentut.proyecto_fullstack_backend_ut.entities.Pago;
import com.systempaymentut.proyecto_fullstack_backend_ut.enums.PagoStatus;
import com.systempaymentut.proyecto_fullstack_backend_ut.enums.TypePago;
import com.systempaymentut.proyecto_fullstack_backend_ut.repository.EstudianteRepository;
import com.systempaymentut.proyecto_fullstack_backend_ut.repository.PagoRepository;

import jakarta.transaction.TransactionScoped;

@Service
@TransactionScoped
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository; // inyeccion de dependencias de PagoRep.

    @Autowired
    private EstudianteRepository estudianteRepository;

    public Pago savePago(MultipartFile file, double cantidad, TypePago type, LocalDate date, String codigoEstudiante)
            throws IOException {

        Path folderPath = Paths.get(System.getProperty("user.home"), "enset-data", "pagos");

        if (Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        // Generador de nombre unico con UUID (identificador unico universal)
        String fileName = UUID.randomUUID().toString();

        // Ruta completa del archivo con .PDF
        Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", "pagos", fileName + ".pdf");

        // Guardar el archivo en la ubicacion especifica en el sistema
        Files.copy(file.getInputStream(), filePath);

        // Busqueda del estudiante que hace el pago con su codigo
        Estudiante estudiante = estudianteRepository.findByCodigo(codigoEstudiante);

        // Objeto Pago utilizando el patron de diseño builder
        Pago pago = Pago.builder()
                .type(type)
                .status(PagoStatus.CREADO) // Estado inicial del pago
                .fecha(date)
                .estudiante(estudiante)
                .file(filePath.toUri().toString()) // Ruta del archivo almacenado
                .build(); // construccion final del objeto

        return pagoRepository.save(pago);

    }

    public byte[] getArchivoPorId(PagoStatus status, Long pagoId) throws IOException {

        // busca un objeto Pago en la base de datos por su ID
        Pago pago = pagoRepository.findById(pagoId).get();

        return Files.readAllBytes(Path.of(URI.create(pago.getFile())));

    }

    public Pago actualizarPagoPorStatus(PagoStatus status, Long id) {

        // busca un objeto Pago en la base de datos por su ID
        Pago pago = pagoRepository.findById(id).get();

        // Actualiza el estado de pago (validado o rechazado)
        pago.setStatus(status);

        // guarda el objeto Pago actualizado en la base de datos y lo devuelve
        return pagoRepository.save(pago);

    }

}
