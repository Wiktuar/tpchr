package ru.tpchr.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.tritonus.share.sampled.file.TAudioFileFormat;
import ru.tpchr.DTO.CompositionDTO;
import ru.tpchr.entities.Composition;
import ru.tpchr.entities.security.Author;
import ru.tpchr.entities.security.Role;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {

    //  k - 24- часовой формат времени, h- 12-ти часовой формат времени
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
    private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    //метод преобразования ролей в GrantedAuthority для Spring Security
    public static Collection<? extends GrantedAuthority> mapRoleToAuthority(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    //метод сохранения аватара автора, обложек стихов и музыкальных альбомов
    public static void saveCircumcisedImage(String uploadPath, String imagePath, String fileName){
        String base64Image = imagePath.split(",")[1];
        System.out.println(base64Image);
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        try {
            BufferedImage bImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
            File outputFile = new File(uploadPath + fileName);
            ImageIO.write(bImg, "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Cохранили аватар пользователя");
    }

//  метод, который возвращает URL сайта
    public static String getSiteURL(HttpServletRequest request) {
//  в нашем случае siteUrl = http://localhost:8070/
//  в нашем случае request.getServletPath() = "/"; (для общего развития)
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }


//  метод, переводящий в строку текущее время. (в базе данных время хранится в виде строки)
    public static String convertTimeToString(){
        return LocalDateTime.now().format(formatter);
    }

    //  метод, возвращающий строку времени в нужноv для вывода порядке
    public static String getFormatedDate(String inputDate){
        LocalDateTime ldt = LocalDateTime.parse(inputDate, formatter);
        return ldt.format(outputFormatter);
    }


//  метод, берущий стизотворение и выделяющий из него первые четыре строфы
    public static String getPoemPreview(String[] data){
        return Arrays.stream(data).limit(4).collect(Collectors.joining("<br>"));
    }

//  метод, берущий текст и добавляющий к его строфам тег переноса строки
    public static String addBrTag(String[] data){
        return Arrays.stream(data).collect(Collectors.joining("<br>"));
    }

//  метод, берущий текст и удаляющий из строк тег переноса строки
    public static String removeBrTag(String data){
        String[] arr = data.split("<br>");
        return Arrays.stream(arr).collect(Collectors.joining());
    }

//  метод, добавляющий тег переноса строки в описание автора
    public static String addBrTagDescription(String data){
        return Arrays.stream(data.split("\\r\\n")).filter(s -> !s.isEmpty())
                .collect(Collectors.joining("<br><br>"));
    }

//  метод, добавляющий вместо <br> символы переноса строки
    public static String removeBrTagDescription(String data){
        String result = null;
        if(data != null){
            result = data.replaceAll("<br><br>", "&#10");
        }
        return result;
    }

//  метод, возвращающий времмя аудиотрека с минутаи и секундами
    public static String getMusicFileDuration(String fileName) throws IOException, UnsupportedAudioFileException {
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(new File(fileName));
        if (fileFormat instanceof TAudioFileFormat) {
            Map<?, ?> properties = fileFormat.properties();
            String key = "duration";
            Long microseconds = (Long) properties.get(key);
            int milli = (int) (microseconds / 1000);
            int sec = (milli / 1000) % 60;
            int min = (milli / 1000) / 60;
            if(min < 10) return String.format("0%d", min) + ":" + sec;
            else
                return min + ":" + sec;
        } else {
            throw new UnsupportedAudioFileException(String.format("Файл %s не поддерживается", fileName));
        }
    }

//  установление социаьным сетям автора и его описанию параметр null, если с формы пришла пустая строка
    public static void changeSocialNets(Author author){
        if(author.getDescription().isEmpty())author.setDescription(null);
        if(author.getVk().isEmpty())author.setVk(null);
        if(author.getYt().isEmpty())author.setYt(null);
        if(author.getTg().isEmpty())author.setTg(null);
        if(author.getRt().isEmpty())author.setRt(null);
    }

//  функцияб изменяющая пути к файлу у Сomposition если пользоваель поменял почту
    public static void changeFileName(Composition c, Pattern p, String email ){
        Matcher m = p.matcher(c.getFileName());
        c.setFileName(m.replaceFirst(email));
    }


//  метод сортировки полученного списка Composition
    public static List<? extends CompositionDTO> sortCompositionList(List<? extends CompositionDTO> compList){
        compList.sort(new Comparator<CompositionDTO>() {
            @Override
            public int compare(CompositionDTO o1, CompositionDTO o2) {
                return o2.getReleaseDate().compareTo(o1.getReleaseDate());
            }
        });
        compList.forEach(a -> a.setReleaseDate(Utils.getFormatedDate(a.getReleaseDate())));
        return compList;
    }
}
