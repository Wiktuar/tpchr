package ru.tpchr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tpchr.DTO.AllComposeDTO;
import ru.tpchr.entities.AllCompose;
import ru.tpchr.repository.AllComposeRepo;

import java.util.List;

@Service
public class AllComposeService {
    private AllComposeRepo allComposeRepo;

    @Autowired
    public void setAllComposeRepo(AllComposeRepo allComposeRepo) {
        this.allComposeRepo = allComposeRepo;
    }

    public List<AllComposeDTO> getAllCompose(String email){
        return allComposeRepo.getAllComposeDto(email);
    }

    public List<AllCompose> getAll(){
        return allComposeRepo.findAll();
    }
}
