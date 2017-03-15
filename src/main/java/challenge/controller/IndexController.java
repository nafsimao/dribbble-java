package challenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador básico para retornar a página html.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    String index() {
        return "index.html";
    }
}