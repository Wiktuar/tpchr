package ru.tpchr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tpchr.entities.Composition;
import ru.tpchr.repository.CompositionRepo;

import java.util.List;

@Service
public class ComposeService {
    private CompositionRepo compositionRepo;

    @Autowired
    public void setCompositionRepo(CompositionRepo compositionRepo) {
        this.compositionRepo = compositionRepo;
    }

    //  метод, сохраняющий произведение в базе данных.
    public void save(Composition comp){
        compositionRepo.save(comp);
    }

    //  метод, возвращающий список авторов, поставивших лайк произведению.
    public Composition getListOfLikes(long id){ ;
        return compositionRepo.getListOfLikes(id);
    }

    public List<Composition> findAll(){
        return compositionRepo.findAll();
    }

    public List<Composition> findAllByAuthorId(long id){
        return compositionRepo.findAllByAuthorId(id);
    }

    public void saveAllCompositions(List<Composition> composeList){
        compositionRepo.saveAllAndFlush(composeList);
    }
}
