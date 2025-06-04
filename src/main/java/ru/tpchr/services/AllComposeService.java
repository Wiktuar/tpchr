package ru.tpchr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tpchr.DTO.AllComposeDTO;
import ru.tpchr.DTO.CompositionDTO;
import ru.tpchr.entities.AllCompose;
import ru.tpchr.repository.AllComposeRepo;
import ru.tpchr.utils.Utils;

import java.util.Comparator;
import java.util.List;

@Service
public class AllComposeService {
    private AllComposeRepo allComposeRepo;

    @Autowired
    public void setAllComposeRepo(AllComposeRepo allComposeRepo) {
        this.allComposeRepo = allComposeRepo;
    }

    public List<AllComposeDTO> getAllCompose(String email){
        List<AllComposeDTO> acd = allComposeRepo.getAllComposeDto(email);
        acd.sort(new Comparator<AllComposeDTO>() {
            @Override
            public int compare(AllComposeDTO o1, AllComposeDTO o2) {
                return o2.getReleaseDate().compareTo(o1.getReleaseDate());
            }
        });
        acd.forEach(a -> a.setReleaseDate(Utils.getFormatedDate(a.getReleaseDate())));
        return acd;
    }

//  получение всех произведений конкретного пользователя
    public List<? extends CompositionDTO> getAllComposeDtoByAuthorId(String email, long id){
        return Utils.sortCompositionList(allComposeRepo.getAllComposeDtoByAuthorId(email, id));
    }

    public List<AllCompose> getAll(){
        return allComposeRepo.findAll();
    }
}
