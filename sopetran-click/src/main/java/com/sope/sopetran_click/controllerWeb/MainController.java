package com.sope.sopetran_click.controllerWeb;

import com.sope.sopetran_click.dto.public_Entities.EventsResponseDTO;
import com.sope.sopetran_click.service.public_Entities.EventsService;
import com.sope.sopetran_click.service.public_Entities.NewsService;
import com.sope.sopetran_click.service.public_Entities.SpecialDateService;
import com.sope.sopetran_click.service.public_Entities.HistoryEntryService;
import com.sope.sopetran_click.service.trade.DishService;
import com.sope.sopetran_click.service.trade.LocalService;
import com.sope.sopetran_click.service.trade.ProductService;
import com.sope.sopetran_click.service.trade.RestaurantService;
import com.sope.sopetran_click.service.ecotourism.SiteService;
import com.sope.sopetran_click.service.ecotourism.Iconic_placeService;
import com.sope.sopetran_click.service.transport.BuseService;
import com.sope.sopetran_click.service.transport.MotorbikeService;
import com.sope.sopetran_click.service.transport.DriverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * MainController — Renderiza las vistas Thymeleaf.
 *
 * CAMBIO ARQUITECTURAL: se eliminaron los iframes.
 * Ahora el Index incluye todos los módulos como fragmentos Thymeleaf
 * con th:insert.md="~{mennu/NombreVista :: nombre-fragmento}".
 * Esto significa que el Index necesita TODOS los datos de todos los módulos
 * en su Model, porque los fragmentos se renderizan en el mismo request.
 *
 * Las rutas individuales (/alojamiento, /comercio, etc.) se mantienen
 * por si se necesita acceder directo a un módulo desde otra parte.
 */
@Controller
public class MainController {

    private final EventsService eventsService;
    private final RestaurantService restaurantService;
    private final LocalService localService;
    private final ProductService productService;
    private final DishService dishService;
    private final SiteService siteService;
    private final Iconic_placeService iconicPlaceService;
    private final BuseService buseService;
    private final MotorbikeService motorbikeService;
    private final DriverService driverService;
    private final NewsService newsService;
    private final SpecialDateService specialDateService;
    private final HistoryEntryService historyEntryService;

    public MainController(EventsService eventsService,
                          RestaurantService restaurantService,
                          LocalService localService,
                          ProductService productService,
                          DishService dishService,
                          SiteService siteService,
                          Iconic_placeService iconicPlaceService,
                          BuseService buseService,
                          MotorbikeService motorbikeService,
                          DriverService driverService,
                          NewsService newsService,
                          SpecialDateService specialDateService,
                          HistoryEntryService historyEntryService) {
        this.eventsService = eventsService;
        this.restaurantService = restaurantService;
        this.localService = localService;
        this.productService = productService;
        this.dishService = dishService;
        this.siteService = siteService;
        this.iconicPlaceService = iconicPlaceService;
        this.buseService = buseService;
        this.motorbikeService = motorbikeService;
        this.driverService = driverService;
        this.newsService = newsService;
        this.specialDateService = specialDateService;
        this.historyEntryService = historyEntryService;
    }

    /**
     * Llena el Model con los datos de Comercio (restaurantes + locales).
     * Reusado por index() (SPA) y comercio() (ruta directa).
     */
    private void populateComercio(Model model) {
        model.addAttribute("restaurantes", restaurantService.listarTodos());
        model.addAttribute("locales", localService.listarTodos());
    }

    /**
     * Llena el Model con los datos de Ecoturismo (veredas + lugares icónicos).
     * Reusado por index() (SPA) y ecoturismo() (ruta directa).
     */
    private void populateEcoturismo(Model model) {
        model.addAttribute("veredas", siteService.getAllSites());
        model.addAttribute("lugaresIconicos", iconicPlaceService.getAllIconicPlaces());
    }

    /**
     * Llena el Model con los datos de Transporte (buses + motos + conductores).
     * Reusado por index() (SPA) y transporte() (ruta directa).
     */
    private void populateTransporte(Model model) {
        model.addAttribute("buses", buseService.listarTodos());
        model.addAttribute("motos", motorbikeService.listarTodos());
        model.addAttribute("conductores", driverService.listarTodos());
    }

    /**
     * Llena el Model con los datos de Cultural (eventos + noticias + fechas + historia).
     * Reusado por index() (SPA) y cultural() (ruta directa).
     */
    private void populateCultural(Model model) {
        model.addAttribute("noticias", newsService.listarTodos());
        model.addAttribute("fechasEspeciales", specialDateService.listarTodos());
        model.addAttribute("historiaEntries", historyEntryService.listarTodos());
    }

    /**
     * GET / → Página principal con TODOS los módulos embebidos como fragmentos.
     * Alojamiento carga sus datos por su cuenta desde el cliente
     * (MenuAlojamiento.js hace fetch a /api/hotels y /api/estates), así que
     * este Model ya no necesita pasarle hoteles/fincas server-side.
     */
    @GetMapping("/")
    public String index(Model model) {
        // Datos para la sección de eventos del inicio (máx 4)
        List<EventsResponseDTO> eventos = eventsService.listarTodos();
        model.addAttribute("eventos",
                eventos.size() > 4 ? eventos.subList(0, 4) : eventos);

        // Datos para el fragmento de comercio
        populateComercio(model);

        // Datos para el fragmento de ecoturismo
        populateEcoturismo(model);

        // Datos para el fragmento de transporte
        populateTransporte(model);

        // Datos para el fragmento cultural
        populateCultural(model);

        return "Index";
    }

    // ── Rutas individuales — redirigen al SPA con la sección correspondiente ──
    // Estos fragmentos (mennu/*.html) NO tienen <head>/nav/CSS/JS propios: solo
    // están pensados para incluirse dentro de Index.html vía th:insert. Servirlos
    // como página completa dejaría una vista rota, así que redirigimos a "/"
    // con "?seccion=X" y app.js navega a esa sección apenas termina el intro.

    @GetMapping("/alojamiento")
    public String alojamiento() {
        return "redirect:/?seccion=alojamiento";
    }

    @GetMapping("/comercio")
    public String comercio() {
        return "redirect:/?seccion=comercio";
    }

    @GetMapping("/ecoturismo")
    public String ecoturismo() {
        return "redirect:/?seccion=ecoturismo";
    }

    @GetMapping("/transporte")
    public String transporte() {
        return "redirect:/?seccion=transporte";
    }

    @GetMapping("/cultural")
    public String cultural() {
        return "redirect:/?seccion=entidades";
    }

    @GetMapping("/exploracion")
    public String exploracion() {
        return "Exploracion";
    }

    @GetMapping("/quienes-somos")
    public String quienesSomos() {
        return "QuienesSomos";
    }
}