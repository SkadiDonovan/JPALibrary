package ru.skadidonovan.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.skadidonovan.library.models.Person;
import ru.skadidonovan.library.service.PersonService;
import ru.skadidonovan.library.util.PersonValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;

    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model){
        Person person = personService.findOne(id);

        model.addAttribute("person", person);
        model.addAttribute("books", personService.findBooksByOwner(person));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model){
        model.addAttribute("person", personService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute @Valid Person person,
                         BindingResult bindingResult, @PathVariable int id){
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()){
            return "people/edit";
        }
        personService.update(id, person);
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute Person person){
        return "people/new";
    }


    @PostMapping()
    public String create(@ModelAttribute @Valid Person person,
                         BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "people/new";
        }
        personService.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        personService.delete(id);
        return "redirect:/people";
    }
}