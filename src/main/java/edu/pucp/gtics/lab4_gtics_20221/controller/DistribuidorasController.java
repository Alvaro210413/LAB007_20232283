package edu.pucp.gtics.lab4_gtics_20221.controller;

import edu.pucp.gtics.lab4_gtics_20221.entity.Distribuidoras;
import edu.pucp.gtics.lab4_gtics_20221.repository.DistribuidorasRepository;
import edu.pucp.gtics.lab4_gtics_20221.repository.PaisesRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

// Misma lógica que JuegosController, pero para distribuidoras
@Controller
@RequestMapping("/distribuidoras")
public class DistribuidorasController {

    final DistribuidorasRepository distribuidorasRepository;
    final PaisesRepository paisesRepository;

    public DistribuidorasController(DistribuidorasRepository distribuidorasRepository,
                                    PaisesRepository paisesRepository) {
        this.distribuidorasRepository = distribuidorasRepository;
        this.paisesRepository = paisesRepository;
    }

    // GET /distribuidoras/lista -> ordenadas por nombre en forma descendente (Z-A)
    @GetMapping({"", "/", "/lista"})
    public String listaDistribuidoras(Model model) {
        model.addAttribute("listaDistribuidoras", distribuidorasRepository.findAllByOrderByNombreDesc());
        return "distribuidoras/lista";
    }

    // GET /distribuidoras/nuevo -> formulario vacío (objeto "distribuidora" vacío)
    @GetMapping("/nuevo")
    public String nuevaDistribuidora(@ModelAttribute("distribuidora") Distribuidoras distribuidora, Model model) {
        model.addAttribute("listaPaises", paisesRepository.findAll());
        return "distribuidoras/editarFrm";
    }

    // GET /distribuidoras/editar?id=2 -> formulario lleno
    @GetMapping("/editar")
    public String editarDistribuidoras(@RequestParam("id") int id, Model model) {
        Optional<Distribuidoras> optDistribuidora = distribuidorasRepository.findById(id);
        if (optDistribuidora.isPresent()) {
            model.addAttribute("distribuidora", optDistribuidora.get());
            model.addAttribute("listaPaises", paisesRepository.findAll());
            return "distribuidoras/editarFrm";
        }
        return "redirect:/distribuidoras/lista";
    }

    // POST /distribuidoras/guardar -> crear o actualizar
    @PostMapping("/guardar")
    public String guardarDistribuidora(@ModelAttribute("distribuidora") @Valid Distribuidoras distribuidora,
                                       BindingResult bindingResult,
                                       Model model,
                                       RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("listaPaises", paisesRepository.findAll());
            return "distribuidoras/editarFrm";
        }

        if (distribuidora.getIddistribuidora() == null) {
            attr.addFlashAttribute("msg", "Distribuidora creada exitosamente");
        } else {
            attr.addFlashAttribute("msg", "Distribuidora actualizada exitosamente");
        }
        distribuidorasRepository.save(distribuidora);
        return "redirect:/distribuidoras/lista";
    }

    // GET /distribuidoras/borrar?id=2
    @GetMapping("/borrar")
    public String borrarDistribuidora(@RequestParam("id") int id, RedirectAttributes attr) {
        if (distribuidorasRepository.existsById(id)) {
            try {
                distribuidorasRepository.deleteById(id);
                attr.addFlashAttribute("msg", "Distribuidora borrada exitosamente");
            } catch (Exception e) {
                // Falla si hay juegos que usan esta distribuidora (llave foránea)
                attr.addFlashAttribute("msgError", "No se puede borrar la distribuidora porque tiene juegos asociados");
            }
        }
        return "redirect:/distribuidoras/lista";
    }
}
