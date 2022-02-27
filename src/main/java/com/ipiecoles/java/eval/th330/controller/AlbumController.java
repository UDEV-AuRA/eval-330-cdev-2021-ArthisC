package com.ipiecoles.java.eval.th330.controller;

import com.ipiecoles.java.eval.th330.model.Album;
import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.repository.AlbumRepository;
import com.ipiecoles.java.eval.th330.service.AlbumService;
import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ArtistService artistService;

    //7 - Ajout d'un album
    @RequestMapping(
            method = RequestMethod.POST,
            value = "",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public RedirectView createAlbum(Long artistId, String title){
        Artist artist = artistService.findById(artistId);
        Album album = new Album(title,artist);
        albumService.creerAlbum(album);
        //Redirection vers /artists/id
        return new RedirectView("/artists/" + artist.getId());
    }

    //8 - Suppression d'un album
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}/delete"
    )
    public RedirectView deleteArtist(@PathVariable(value = "id")Long id){
        Artist artist = artistService.findById(albumService.findById(id).getArtist().getId());
        albumService.deleteAlbum(id);
        return new RedirectView("/artists/" + artist.getId());
    }
}
