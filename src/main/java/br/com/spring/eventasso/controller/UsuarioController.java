package br.com.spring.eventasso.controller;
import br.com.spring.eventasso.entity.Usuario;
import br.com.spring.eventasso.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/usuario")
    public void saveUsuario(@RequestBody Usuario usuario){

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        this.usuarioRepository.save(usuario);
    }

    @GetMapping("/usuario")
    public List<Usuario> getUsers(){
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @DeleteMapping("/usuario/{id}")
    @RolesAllowed("ADMIN")
    public void delete(@PathVariable Long id){
        this.usuarioRepository.deleteById(id);
    }

}
