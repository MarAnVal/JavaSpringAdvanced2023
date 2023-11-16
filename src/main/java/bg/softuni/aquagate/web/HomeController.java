package bg.softuni.aquagate.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public interface HomeController {
    @GetMapping("/")
    String index(Model model);

    @GetMapping("/about")
    public String about();

    //TODO refactor habitats pages in new controller and unify the methods and htmls?

    @GetMapping("/freshwater")
    public String freshwater();

    @GetMapping("/reef")
    public String reef();

    @GetMapping("/blackwater")
    public String blackwater();

    @GetMapping("/brackish-water")
    public String brackishWater();
}
