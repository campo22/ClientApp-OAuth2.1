package com.diver.clientapp.Controllers;

import com.diver.clientapp.Model.Message;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:8080")
@RestController
@RequestMapping("/api") // Prefijo común para las rutas
public class AppController {

    // Método para listar mensajes
    @GetMapping("/list")
    public List<Message> list() {
        return Collections.singletonList(new Message("Hello World")); // Devuelve la lista de mensajes
    }

    // Método para crear un nuevo mensaje
    @PostMapping("/create")
    public Message create(@RequestBody Message message) {
        System.out.println("Mensaje guardado: " + message);
        return message; // Devuelve el mensaje creado
    }

    // Método para manejar la autorización

    @GetMapping("/authorized")
    public Map<String, String> authorized(@RequestParam(required = false) String code,
                                          @RequestParam(required = false) String error) {
        if (error != null) {
            System.out.println("Error received: " + error);
            return Collections.singletonMap("error", error);
        }

        if (code != null) {
            System.out.println("Authorization code received: " + code);
            return Collections.singletonMap("code", code);
        } else {
            System.out.println("No authorization code received.");
            return Collections.singletonMap("error", "No authorization code received.");
        }
    }



}
