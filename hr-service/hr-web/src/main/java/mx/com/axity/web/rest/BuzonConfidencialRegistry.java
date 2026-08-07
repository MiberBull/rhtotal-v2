package mx.com.axity.web.rest;

import mx.com.axity.commons.context.TenantContext;
import mx.com.axity.commons.to.BuzonConfidencialTO;
import mx.com.axity.services.facade.impl.BuzonConfidencialFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("buzon")
public class BuzonConfidencialRegistry {

    @Autowired private BuzonConfidencialFacadeImpl buzonFacade;

    /** Enviar reporte — no requiere autenticación fuerte (anónimo) */
    @PostMapping("/submit")
    public ResponseEntity<BuzonConfidencialTO> submit(@RequestBody BuzonConfidencialTO buzonTO) {
        return ResponseEntity.ok(buzonFacade.submit(buzonTO, TenantContext.getCurrentTenant()));
    }

    /** Listar reportes por tenant — solo RH/ADMIN */
    @GetMapping("/list")
    public ResponseEntity<List<BuzonConfidencialTO>> list() {
        return ResponseEntity.ok(buzonFacade.findByTenant(TenantContext.getCurrentTenant()));
    }

    /** Actualizar estatus + comentario de RH */
    @PutMapping("/{id}/estatus")
    public ResponseEntity<BuzonConfidencialTO> updateEstatus(
            @PathVariable("id") Long id,
            @RequestBody Map<String, String> body) {
        String estatus = body.get("estatus");
        String comentario = body.get("comentario");
        return ResponseEntity.ok(buzonFacade.updateEstatus(id, estatus, comentario));
    }
}
