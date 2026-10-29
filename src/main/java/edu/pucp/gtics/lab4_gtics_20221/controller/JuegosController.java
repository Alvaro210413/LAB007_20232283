package edu.pucp.gtics.lab4_gtics_20221.controller;

import edu.pucp.gtics.lab4_gtics_20221.entity.Juegos;
import edu.pucp.gtics.lab4_gtics_20221.repository.DistribuidorasRepository;
import edu.pucp.gtics.lab4_gtics_20221.repository.GenerosRepository;
import edu.pucp.gtics.lab4_gtics_20221.repository.JuegosRepository;
import edu.pucp.gtics.lab4_gtics_20221.repository.PlataformasRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/juegos")
public class JuegosController {

    // Inyección por constructor (Spring nos pasa los repositorios solo)
    final JuegosRepository juegosRepository;
    final GenerosRepository generosRepository;
    final PlataformasRepository plataformasRepository;
    final DistribuidorasRepository distribuidorasRepository;

    public JuegosController(JuegosRepository juegosRepository,
                            GenerosRepository generosRepository,
                            PlataformasRepository plataformasRepository,
                            DistribuidorasRepository distribuidorasRepository) {
        this.juegosRepository = juegosRepository;
        this.generosRepository = generosRepository;
        this.plataformasRepository = plataformasRepository;
        this.distribuidorasRepository = distribuidorasRepository;
    }

    // GET /juegos/lista -> lista de juegos ordenada por precio (mayor a menor)
    @GetMapping({"", "/", "/lista"})
    public String listaJuegos(Model model) {
        model.addAttribute("listaJuegos", juegosRepository.findAllByOrderByPrecioDesc());
        return "juegos/lista";
    }

    // GET /juegos/nuevo -> formulario vacío.
    // @ModelAttribute("juego") crea un objeto Juegos vacío y lo manda a la vista con el nombre "juego"
    // (ese nombre debe ser el mismo que usa el th:object="${juego}" del editarFrm.html)
    @GetMapping("/nuevo")
    public String nuevoJuegos(@ModelAttribute("juego") Juegos juego, Model model) {
        enviarListas(model);
        return "juegos/editarFrm";
    }

    // GET /juegos/editar?id=7 -> mismo formulario pero lleno con los datos del juego
    @GetMapping("/editar")
    public String editarJuegos(@RequestParam("id") int id, Model model) {
        Optional<Juegos> optJuego = juegosRepository.findById(id);
        if (optJuego.isPresent()) {
            model.addAttribute("juego", optJuego.get());
            enviarListas(model);
            return "juegos/editarFrm";
        } else {
            // si el id no existe, regresamos a la lista
            return "redirect:/juegos/lista";
        }
    }

    // POST /juegos/guardar -> sirve para crear Y para actualizar
    // @Valid revisa las anotaciones de la entidad (@Size, @Min, @NotNull...)
    // BindingResult guarda los errores. IMPORTANTE: debe ir justo después del objeto validado.
    @PostMapping("/guardar")
    public String guardarJuegos(@ModelAttribute("juego") @Valid Juegos juego,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            // Hay errores: volvemos al formulario SIN redirect (si hacemos redirect se pierden los errores
            // y lo que escribió el usuario). Hay que volver a mandar las listas de los <select>.
            enviarListas(model);
            return "juegos/editarFrm";
        }

        // Si no tiene id es nuevo; si ya tiene id es una edición (el id viaja en el input hidden)
        if (juego.getIdjuego() == null) {
            attr.addFlashAttribute("msg", "Juego creado exitosamente");
        } else {
            attr.addFlashAttribute("msg", "Juego actualizado exitosamente");
        }
        // Una imagen vacía la guardamos como null
        if (juego.getImage() != null && juego.getImage().isBlank()) {
            juego.setImage(null);
        }
        juegosRepository.save(juego);   // INSERT o UPDATE según el id
        // addFlashAttribute = el mensaje sobrevive al redirect y se muestra una sola vez
        return "redirect:/juegos/lista";
    }

    // GET /juegos/borrar?id=3
    @GetMapping("/borrar")
    public String borrarJuegos(@RequestParam("id") int id, RedirectAttributes attr) {
        if (juegosRepository.existsById(id)) {
            try {
                juegosRepository.deleteById(id);
                attr.addFlashAttribute("msg", "Juego borrado exitosamente");
            } catch (Exception e) {
                // Falla si otra tabla lo usa como llave foránea (ej: ya fue comprado en juegosxusuario)
                attr.addFlashAttribute("msgError", "No se puede borrar el juego porque tiene compras registradas");
            }
        }
        return "redirect:/juegos/lista";
    }

    // GET /juegos/vista?id=3 -> detalle de un juego (no lo pide el lab, pero el template está en la estructura)
    @GetMapping("/vista")
    public String vistaJuego(@RequestParam("id") int id, Model model) {
        Optional<Juegos> optJuego = juegosRepository.findById(id);
        if (optJuego.isPresent()) {
            model.addAttribute("juego", optJuego.get());
            return "juegos/vista";
        }
        return "redirect:/juegos/lista";
    }

    // Método de apoyo: manda las listas que necesitan los <select> del formulario.
    // Si cambias estos nombres ("listaGeneros", etc.) cámbialos también en editarFrm.html
    private void enviarListas(Model model) {
        model.addAttribute("listaGeneros", generosRepository.findAll());
        model.addAttribute("listaPlataformas", plataformasRepository.findAll());
        model.addAttribute("listaDistribuidoras", distribuidorasRepository.findAll());
    }
}
