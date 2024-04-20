package com.rabbit.demorest.services;

import org.springframework.http.ResponseEntity;

import com.rabbit.demorest.entities.Menu;

public interface IMenuService {
    public ResponseEntity<?> createMenu(Menu menu); 
    public ResponseEntity<?> getAllMenus();
    public ResponseEntity<?> getMenuById(Long id);
    public ResponseEntity<?> updateMenu(Long id, Menu updatedMenu);
    public ResponseEntity<?> deleteMenu(Long id);
}
