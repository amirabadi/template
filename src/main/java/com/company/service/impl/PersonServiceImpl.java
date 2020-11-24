package com.company.service.impl;/*
package com.company.service.impl;


import com.company.common.mapper.PersonMapper;
import com.company.dto.PersonDTO;
import com.company.repository.PersonRepo;
import com.company.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepo personRepo;

    public List<Person> allPerosns() {
        return personRepo.findAll();
    }


    public Page<PersonDTO> getPersonPageable(Integer page, Integer pagesize){
        Pageable pageRequest = createPageRequest(page , pagesize);
        Page<Person> personPage = personRepo.getAll(pageRequest);
        Page<PersonDTO> personDTOS=personPage.map(x->
            PersonMapper.INSTANCE.toDto(x));
        return personDTOS;
    }
    public Person savePerson(Person person) {
        try {
            return personRepo.saveAndFlush(person);
        } catch (Exception exc) {
            return null;
        }
    }

    public Person editPerson(Person person) {
        try {
            Person temp = personRepo.getOne(person.getIdPerson());
            temp.setName(person.getName());
            temp.setLName(person.getLName());
            temp.setNationalCode(person.getNationalCode());
            temp.setTel(person.getTel());
            return personRepo.saveAndFlush(temp);

        } catch (Exception exc) {
            return null;
        }
    }

    public Optional<Person> getPerson(Integer idPerson) {
        try {
            Optional<Person> temp = personRepo.findById(idPerson);
            return temp;
        } catch (Exception exc) {
            return null;

        }
    }

    public boolean deletePerson(Integer id) {
        return false;
    }

}
*/
