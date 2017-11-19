package com.gianfranco.trabajoparcial.web.controller;

import com.gianfranco.trabajoparcial.domain.Client;
import com.gianfranco.trabajoparcial.service.CityService;
import com.gianfranco.trabajoparcial.service.ClientService;
import com.gianfranco.trabajoparcial.service.HobbyService;
import com.gianfranco.trabajoparcial.service.exception.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ClientController {
    public static final String ERROR_FORM = "There was an error with the form. See below for more details";
    public static final String DUPLICATED_DNI = "The DNI is duplicated. Please, enter another DNI";
    public static final String CLIENT_ADDED = "Client was successfully added";
    public static final String CLIENT_UPDATED = "Client was successfully updated";
    public static final String CLIENT_DELETED = "Client was successfully deleted";
    public static final String FLASH_OK = "ok";
    public static final String FLASH_ERROR = "error";

    @Autowired
    private CityService cityService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private HobbyService hobbyService;

    @RequestMapping(path = "/")
    public String index(Model model) {
        Iterable<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "dashboard";
    }

    @RequestMapping(path = "/clients/add")
    public String formNewClient(Model model) {
        if (!model.containsAttribute("client")) {
            model.addAttribute("client", new Client());
        }
        model.addAttribute("cities", cityService.findAll());
        model.addAttribute("hobbies", hobbyService.findAll());
        model.addAttribute("action", "/clients/add");
        model.addAttribute("heading", "Add client");
        model.addAttribute("submit", "Add client");
        return "form";
    }

    @RequestMapping(value = "/clients/add", method = RequestMethod.POST)
    public String addClient(@Valid Client client, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors())
                return errorAddClient(client, result, redirectAttributes, ERROR_FORM);
            clientService.save(client);
            redirectAttributes.addFlashAttribute(FLASH_OK, CLIENT_ADDED);
            return "redirect:/";
        } catch (Exception e) {
            return errorAddClient(client, result, redirectAttributes, DUPLICATED_DNI);
        }
    }

    private String errorAddClient(@Valid Client client, BindingResult result, RedirectAttributes redirectAttributes, String errorMessage) {
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.client", result);
        redirectAttributes.addFlashAttribute("client", client);
        redirectAttributes.addFlashAttribute(FLASH_ERROR, errorMessage);
        return "redirect:/clients/add";
    }

    @RequestMapping(path = "/clients/edit/{clientDni}")
    public String formUpdateClient(Model model, @PathVariable String clientDni) {
        if (!model.containsAttribute("client")) {
            model.addAttribute("client", clientService.findByDni(clientDni));
        }
        model.addAttribute("cities", cityService.findAll());
        model.addAttribute("hobbies", hobbyService.findAll());
        model.addAttribute("action", "/clients/edit");
        model.addAttribute("heading", "Edit client");
        model.addAttribute("submit", "Update client");
        return "form";
    }

    @RequestMapping(value = "/clients/edit", method = RequestMethod.POST)
    public String updateClient(@Valid Client client, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors())
                return errorUpdateClient(client, result, redirectAttributes, ERROR_FORM);
            clientService.save(client);
            redirectAttributes.addFlashAttribute(FLASH_OK, CLIENT_UPDATED);
            return "redirect:/";
        } catch (DataIntegrityViolationException e) {
            return errorUpdateClient(client, result, redirectAttributes, DUPLICATED_DNI);
        } catch (Exception e) {
            return errorUpdateClient(client, result, redirectAttributes, e.getMessage());
        }
    }

    private String errorUpdateClient(@Valid Client client, BindingResult result, RedirectAttributes redirectAttributes, String errorMessage) {
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.client", result);
        redirectAttributes.addFlashAttribute("client", client);
        redirectAttributes.addFlashAttribute(FLASH_ERROR, errorMessage);
        return String.format("redirect:/clients/edit/%s", client.getDni());
    }

    @RequestMapping(value = "/clients/delete/{clientDni}")
    public String deleteClient(@PathVariable String clientDni, RedirectAttributes redirectAttributes) {
        try {
            clientService.delete(clientDni);
            redirectAttributes.addFlashAttribute(FLASH_OK, CLIENT_DELETED);
            return "redirect:/";
        } catch (Exception e) {
            return errorDeleteClient(redirectAttributes, e.getMessage());
        }
    }

    private String errorDeleteClient(RedirectAttributes redirectAttributes, String errorMessage) {
        redirectAttributes.addFlashAttribute(FLASH_ERROR, errorMessage);
        return "redirect:/";
    }

    @ExceptionHandler(ClientNotFoundException.class)
    private String clientNotFound(Model model, Exception ex) {
        model.addAttribute(FLASH_ERROR, ex.getMessage());
        model.addAttribute("heading", "Error");
        return "error";
    }
}
