package com.ipiecoles.java.eval.th330.service;

import com.ipiecoles.java.eval.th330.model.Album;
import com.ipiecoles.java.eval.th330.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public Album findById(Long id){
        Optional<Album> album = this.albumRepository.findById(id);
        if(!album.isPresent()){
            throw new EntityNotFoundException("Impossible de trouver l'album d'identifiant " + id);
        }
        return album.get();
    }

    public Album creerAlbum(Album album) {
        return albumRepository.save(album);
    }

    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }
}
