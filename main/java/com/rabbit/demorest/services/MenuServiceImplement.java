package com.rabbit.demorest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.rabbit.demorest.entities.Menu;
import com.rabbit.demorest.repositories.IMenuRepo;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImplement implements IMenuService {
    @Autowired
    private IMenuRepo menuRepo;

    public ResponseEntity<?> createMenu(Menu menu) {
        try {
            Menu createdMenu = menuRepo.save(menu);
            return new ResponseEntity<>(createdMenu, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo crear el menú", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllMenus() {
        List<Menu> menus = menuRepo.findAll();
        if (menus.isEmpty()) {
            return new ResponseEntity<>("No se encontraron menús", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    public ResponseEntity<?> getMenuById(Long id) {
        Optional<Menu> menuOptional = menuRepo.findById(id);
        if (menuOptional.isPresent()) {
            return new ResponseEntity<>(menuOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Menú no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateMenu(Long id, Menu updatedMenu) {
        try {
            Optional<Menu> menuOptional = menuRepo.findById(id);
            if (menuOptional.isPresent()) {
                updatedMenu.setId(id);
                Menu savedMenu = menuRepo.save(updatedMenu);
                return new ResponseEntity<>(savedMenu, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Menú no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo actualizar el menú", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteMenu(Long id) {
        try {
            menuRepo.deleteById(id);
            return new ResponseEntity<>("Menú eliminado exitosamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Menú no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo eliminar el menú", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
