package com.company.controller;/*
package com.qorb.controller;

import com.qorb.common.mapper.PersonMapper;
import com.qorb.dto.DataTableDTO;
import com.qorb.dto.PersonDTO;
import com.qorb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;
  */
/*  @GetMapping("/")
    public String helloWorld() {
        return "Hello world!";
    }
    @GetMapping("/getAllPersons")
    @PreAuthorize("hasPermission(this,'view')")
    public List<Person> AllPerson(){
        return personService.allPerosns();
    }*//*


    @GetMapping("/")
    public String helloWorld() throws Exception {

        return "Hello world!";
    }
    @PostMapping("/getAllPersons")
    public List<Person> AllPerson() throws Exception {
        //  return personServiceGetServiceFactory.getPaln()
        return personService.allPerosns();
    }
    @PostMapping(value="/getPersonsPage",consumes= MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(this,'view')")
    public Page<PersonDTO> AllPersonPage(@RequestBody DataTableDTO dataTableDTO) {
        //  return personServiceGetServiceFactory.getPaln()

        return personService.getPersonPageable(dataTableDTO.getPageNumber(),dataTableDTO.getRowsIntoPage());
    }
    @GetMapping(value="/getPerson")
    public Person getPerson(Integer idPerson) throws Exception {
        //  return personServiceGetServiceFactory.getPaln()
        Optional<Person> person=personService.getPerson(idPerson);
        return person.orElse(new Person());
    }
    @PutMapping("/editPerson")
    public Person editPerson(@RequestBody PersonDTO newPerson) {

        return personService.editPerson(PersonMapper.INSTANCE.toEntity(newPerson));

    }
    @PostMapping("/deletePerson")
    public boolean deletePerson(@RequestBody Integer id) {
        return personService.deletePerson(id);

    }
}
*/
