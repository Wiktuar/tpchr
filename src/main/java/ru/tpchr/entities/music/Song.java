package ru.tpchr.entities.music;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "header")
    private String header;

    @Column(name = "file_url")
    private String urlToMusicFile;

    @Column(name = "duration")
    private String duration;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,
                cascade = CascadeType.DETACH)
    @JoinColumn(name = "album_id")
    private Album album;
}
