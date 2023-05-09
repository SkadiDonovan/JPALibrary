package ru.skadidonovan.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.skadidonovan.library.models.Person;
import ru.skadidonovan.library.service.PeopleService;
import ru.skadidonovan.library.util.PeopleValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    private final PeopleValidator peopleValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PeopleValidator peopleValidator) {
        this.peopleService = peopleService;
        this.peopleValidator = peopleValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model){
        Person person = peopleService.findOne(id);

        model.addAttribute("person", person);
        model.addAttribute("books", peopleService.getBooksByPersonId(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute @Valid Person person,
                         BindingResult bindingResult, @PathVariable int id){
        peopleValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()){
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute Person person){
        return "people/new";
    }


    @PostMapping()
    public String create(@ModelAttribute @Valid Person person,
                         BindingResult bindingResult){
        peopleValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        peopleService.delete(id);
        return "redirect:/people";
    }
}