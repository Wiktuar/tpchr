package ru.tpchr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tpchr.entities.poem.Content;
import ru.tpchr.repository.ContentRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

// в связи с односторонней связью между Content и Poem операции с превью стихотворения
// осуществляются через Content
@Service
public class ContentService {
    @Value("${upload.path}")
    private String deletePath;

    private ContentRepo contentRepo;

    private PoemService poemService;

    @Autowired
    public void setContentRepo(ContentRepo contentRepo) {
        this.contentRepo = contentRepo;
    }

    @Autowired
    public void setPoemService(PoemService poemService) {
        this.poemService = poemService;
    }

    //  сохранеие стихотворения вместе с его превью через его содержимое
    public Content savePoemInDB(Content content){
        return contentRepo.save(content);
    }

//  метод удаления всех стихотворений
    public void deleteAllPoems(){
        Set<String> poemFileNameUrls = poemService.getAllPoemFileNames();
        poemFileNameUrls.forEach(p -> {
            try {
                Files.delete(Paths.get(deletePath + p));
            } catch (IOException e) {
                System.out.println("не удалось удалить обложку стихотворения");
            }
        });
        contentRepo.deleteAll();
    }

//  метод удаления стихотворения через его ID
    public void deletePoemById(long id) {
        String poemFileNameUrl = poemService.getPoemFileName(id);
        try {
            if(!poemFileNameUrl.contains("poemCover.jpg"))
                Files.delete(Paths.get(deletePath + poemFileNameUrl));
            contentRepo.deleteById(id);
        } catch (IOException e) {
            System.out.println("не удалось удалить обложку стихотворения");
        }
    }

//  получение содержания стихотворения без дополнительных данных
    public Content findById(long id){
        return contentRepo.findById(id).get();
    }

}
