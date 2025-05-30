package com.systempaymentut.proyecto_fullstack_backend_ut.web;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.systempaymentut.proyecto_fullstack_backend_ut.entities.Estudiante;
import com.systempaymentut.proyecto_fullstack_backend_ut.entities.Pago;
import com.systempaymentut.proyecto_fullstack_backend_ut.enums.PagoStatus;
import com.systempaymentut.proyecto_fullstack_backend_ut.enums.TypePago;
import com.systempaymentut.proyecto_fullstack_backend_ut.repository.EstudianteRepository;
import com.systempaymentut.proyecto_fullstack_backend_ut.repository.PagoRepository;
import com.systempaymentut.proyecto_fullstack_backend_ut.services.PagoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




//define la clase como un cntrolador REST
@RestController
@CrossOrigin("*")//permite esta API sea accesible desde cualquier dominio
public class pagoController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoService pagoService;
    //METODOS PARA EL MANEJO DE ESTUDIANTES 
    //metodo que me devuelva la lista con todos los estudiante 
    @GetMapping("/estudiantes")
    public List<Estudiante> listarEstudiantes(){
        return estudianteRepository.findAll();//retorna todod los estudiantes desde la base de datos 
    } 
    //metodo que devuelva a uno en espesifico por su codigo
    @GetMapping("/estudiantes/{codigo}")
    public Estudiante listarEstudiantePorCodigo (@PathVariable String codigo){
        return estudianteRepository.findByCodigo(codigo); //busca estudiante por su codigo 
    }
    //lista estudiantes segun el programa academico 
    @GetMapping("/estudiantesPorPrograma")
    public List<Estudiante>listaEstudiantesPorPrograma(@RequestParam String programaId){
        return estudianteRepository.findByProgramaId(programaId);
    }

    //METODOS PARA EL MANEJO DE PAGOS 
    //metodo 1
    @GetMapping("/pagos")
    public List<Pago> listarPagos(){
        return pagoRepository.findAll();
    }
    //metodo 2
    @GetMapping("/pagos/{id}")
    public Pago lisPagoPorId(@PathVariable Long id){
        return pagoRepository.findById(id).get();//busca un pago por su id
    }
    //metodo 3
    @GetMapping("/estudiantes/{codigo}/pagos")
    public List<Pago> listarPagosPorCodigoEstudiante(@PathVariable String codigo){
        return pagoRepository.findByEstudianteCodigo(codigo);
    }
    //metodo 4
    @GetMapping("pagosPorStatus")
    public List<Pago> listarPagosPorStatus(@RequestParam PagoStatus status ){
        return pagoRepository.findByStatus(status);
    }
    //metodo 5
    @GetMapping("pagos/porTipo")
    public List<Pago> listarPagosPorTyme(@RequestParam TypePago type){
        return pagoRepository.findByType(type);
    }
    //metodo actualizar
    @PutMapping("pagos/{pagoId}/actualizarPago")
    public Pago actualizarStatusDePago(@RequestParam PagoStatus status, @PathVariable Long pagoId){
        return pagoService.actualizarPagoPorStatus(status, pagoId);
    }
    //metodo registro
    @PostMapping(path = "/pagos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Pago guardarPago(
        @RequestParam("file") MultipartFile file,
        double cantidad,
        TypePago type,
        LocalDate date,
        String codigoEstudiante) throws IOException{
            return pagoService.savePago(file, cantidad, type, date, codigoEstudiante);
        }

    //metodo descargar
    @GetMapping(value = "pagoFile/{pagoId}", produces = MediaType.APPLICATION_PDF_VALUE)
        public byte[] listarArchivoPorId(@PathVariable Long pagoId) throws IOException{
            return pagoService.getArchivoPorId(pagoId);
    }
    
}





    

