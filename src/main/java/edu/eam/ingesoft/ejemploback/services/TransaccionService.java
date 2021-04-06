package edu.eam.ingesoft.ejemploback.services;

        import edu.eam.ingesoft.ejemploback.model.Cliente;
        import edu.eam.ingesoft.ejemploback.model.Cuenta;
        import edu.eam.ingesoft.ejemploback.model.Transaccion;
        import edu.eam.ingesoft.ejemploback.repositories.CuentaRepository;
        import edu.eam.ingesoft.ejemploback.repositories.TransaccionRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;

@Service
public class TransaccionService {


    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public void crearTransaccion(Transaccion transaccion){

        Cuenta cuenta = cuentaRepository.findById(transaccion.getNumerocuenta()).orElse(null);

        if (cuenta==null){
            throw new RuntimeException("No existe la cuenta");
        }

        double dineroC = cuenta.getAmount();
        double dineroT = transaccion.getMonto();
        double dineroTotal= dineroC + dineroT;

        cuenta.setAmount(dineroTotal);

        transaccionRepository.save(transaccion);
    }

    public void consignarDinero(Transaccion transaccion) {

        Cuenta cuenta = cuentaRepository.findById(transaccion.getNumerocuenta()).orElse(null);

        if (cuenta == null) {
            throw new RuntimeException("No existe la cuenta");
        }

        double dineroC = cuenta.getAmount();
        double dineroT = transaccion.getMonto();
        double dineroTotal = dineroC + dineroT;

        cuenta.setAmount(dineroTotal);
        transaccionRepository.save(transaccion);
    }

    public void retirarDinero(Transaccion transaccion) {

        Cuenta cuenta = cuentaRepository.findById(transaccion.getNumerocuenta()).orElse(null);

        if (cuenta == null) {
            throw new RuntimeException("No existe la cuenta");
        }

        double dineroC = cuenta.getAmount();
        double dineroT = transaccion.getMonto();
        double dineroTotal = dineroC - dineroT;

        cuenta.setAmount(dineroTotal);
        transaccionRepository.save(transaccion);
    }
    public void transferirDinero(Transaccion transaccion){

        //Validar Existencia de la cuenta remitente
        Cuenta cuentaRemite = cuentaRepository.findById(transaccion.getNumerocuenta()).orElse(null);

        if (cuentaRemite==null){
            throw new RuntimeException("No existe la cuenta remitente ");
        }

        //Validar Existencia de la cuenta destino
        Cuenta cuentaDestino = cuentaRepository.findById(transaccion.getNumerocuenta2()).orElse(null);

        if (cuentaDestino==null){
            throw new RuntimeException("No existe la cuenta destino ");
        }

        //Validar que no exista transaccion
        Transaccion transaccion1 = transaccionRepository.findById(transaccion.getNumero()).orElse(null);

        if (transaccion1!=null){
            throw new RuntimeException("Ya existe la transaccion");
        }

        // Se obtiene el dinero de las cuentas y el de transaccion
        double dineroCR = cuentaRemite.getAmount();
        double dineroCD = cuentaDestino.getAmount();
        double dineroT = transaccion.getMonto();

        //Se aplica la resta y suma de las cuentas
        double dineroResta= dineroCR - dineroT;
        double dineroSuma= dineroCD + dineroT;

        cuentaRemite.setAmount(dineroResta);
        cuentaDestino.setAmount(dineroSuma);

        //Guardar la transaccion
        transaccionRepository.save(transaccion);
    }

    //listar transacciones de cliente
    public List<Transaccion> listarTransaccionesCuenta(String id) {
        return transaccionRepository.buscarTransaccionesCuenta(id);
    }
}