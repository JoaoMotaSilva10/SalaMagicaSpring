package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Perfil;
import br.com.itb.miniprojetospring.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil")
@CrossOrigin(origins = "*")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/{email}")
    public ResponseEntity<Perfil> buscarPerfil(@PathVariable String email) {
        Perfil perfil = perfilService.buscarPorUsuarioEmail(email);
        if (perfil != null) {
            return ResponseEntity.ok(perfil);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<Perfil> atualizarPerfil(@RequestBody Perfil perfil) {
        Perfil perfilAtualizado = perfilService.atualizarPerfil(perfil);
        return ResponseEntity.ok(perfilAtualizado);
    }
}