package com.ipiecoles.java.eval.th330.controller;

import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    //1 - Afficher un artiste
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}"
    )
    public ModelAndView artistById(@PathVariable(value = "id")Long id){
        ModelAndView modelAndView = new ModelAndView("detailArtist");
        modelAndView.addObject("artist", artistService.findById(id));
        return modelAndView;
    }

    //2 - Recherche par nom
    @RequestMapping(
            method = RequestMethod.GET,
            value = "",
            params = "name"
    )
    public ModelAndView artistsByName(@RequestParam("name")String name){
        ModelAndView modelAndView = new ModelAndView("listeArtists");
        Page<Artist> artists = artistService.findByNameLikeIgnoreCase(name,0,10,"name","ASC");
        modelAndView.addObject("artists", artists);
        modelAndView.addObject("start", 1);
        modelAndView.addObject("end", artists.getNumberOfElements());
        modelAndView.addObject("page", 0);
        return modelAndView;
    }

    //3 - Liste des artistes
    @RequestMapping(
            method = RequestMethod.GET,
            value = ""
    )
    public ModelAndView artistsList(@RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(defaultValue = "name") String sortProperty,
                                    @RequestParam(defaultValue = "ASC") String sortDirection){
        ModelAndView modelAndView = new ModelAndView("listeArtists");
        Page<Artist> artists = artistService.findAllArtists(page, size, sortProperty, sortDirection);
        modelAndView.addObject("artists", artists);
        modelAndView.addObject("start", page * size + 1);
        modelAndView.addObject("end", page * size + artists.getNumberOfElements());
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    //4 - Création d'un artiste
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/new"
    )
    public ModelAndView newArtist(){
        ModelAndView modelAndView = new ModelAndView("detailArtist");
        modelAndView.addObject("artist", new Artist());
        return modelAndView;
    }

    //4 - Création d'un artiste et 5 - Modification d'un artiste
    @RequestMapping(
            method = RequestMethod.POST,
            value = "",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public RedirectView createArtist(Artist artist){
        if(artist.getId() == null){
            //Création
            artist = artistService.creerArtiste(artist);
        }
        else {
            //Modification
            artist = artistService.updateArtiste(artist.getId(), artist);
        }
        //Redirection vers /artists/id
        return new RedirectView("/artists/" + artist.getId());
    }

    //6 - Suppression d'un artiste
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}/delete"
    )
    public RedirectView deleteArtist(@PathVariable(value = "id")Long id){
        artistService.deleteArtist(id);
        return new RedirectView("/artists?page=0&size=10&sortProperty=name&sortDirection=ASC");
    }
}
