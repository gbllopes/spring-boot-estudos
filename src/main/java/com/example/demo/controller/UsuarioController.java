package com.example.demo.controller;

import com.example.demo.models.Usuario;
import com.example.demo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository  usuarioRepository;

    @GetMapping("/all")
    public @ResponseBody List<Usuario> findAll(){
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Usuario> findById(@PathVariable Long id){
        try {
            Usuario usuario = usuarioRepository.findById(id).get();
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> addUser(@RequestBody Usuario usuario) {

        Usuario usuarioResponse = usuarioRepository.save(usuario);
        return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<String> deleteAllUsers() {
        usuarioRepository.deleteAll();
        return new ResponseEntity<>("Todos os usuários foram deletados com sucesso!", HttpStatus.OK);
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return new ResponseEntity<>("Usuário deletado com sucesso!", HttpStatus.OK);
    }

    @PutMapping("editar/{id}")
    public ResponseEntity<Usuario> updateUser(@RequestBody Usuario usuario, @PathVariable Long id) {
        Optional<Usuario> usuarioResponse = usuarioRepository.findById(id);
        if(usuarioResponse.isPresent()) {
            Usuario user = usuarioResponse.get();
            user.setNome(usuario.getNome());
            return new ResponseEntity<>(usuarioRepository.save(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
