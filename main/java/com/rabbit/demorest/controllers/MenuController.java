package com.rabbit.demorest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rabbit.demorest.entities.Menu;
import com.rabbit.demorest.services.MenuServiceImplement;


@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuServiceImplement menuService;

    @PostMapping
    public ResponseEntity<?> createMenu(@RequestBody Menu menu) {
        return menuService.createMenu(menu);
    }

    @GetMapping
    public ResponseEntity<?> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenu(@PathVariable Long id, @RequestBody Menu updatedMenu) {
        return menuService.updateMenu(id, updatedMenu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long id) {
        return menuService.deleteMenu(id);
    }
}
