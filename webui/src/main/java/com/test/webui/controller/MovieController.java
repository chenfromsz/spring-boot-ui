package com.test.webui.controller;

import com.test.data.domain.Actor;
import com.test.data.domain.Movie;
import com.test.data.repositories.ActorRepository;
import com.test.data.repositories.MovieRepository;
import com.test.data.service.PagesService;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private static Logger logger = LoggerFactory.getLogger(MovieController.class);
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private PagesService<Movie> pagesService;

    @RequestMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("movie/index");
    }

    @RequestMapping(value="/{id}")
    public ModelAndView show(ModelMap model, @PathVariable Long id) {
        Movie movie = movieRepository.findOne(id);
        model.addAttribute("movie",movie);
        return new ModelAndView("movie/show");
    }

    @RequestMapping("/new")
    public ModelAndView create(ModelMap model){
        String[] files = {"/images/movie/西游记.jpg","/images/movie/西游记续集.jpg"};
        model.addAttribute("files",files);
        return new ModelAndView("movie/new");
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public String save(Movie movie) throws Exception{
        movieRepository.save(movie);
        logger.info("新增->ID={}", movie.getId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public ModelAndView update(ModelMap model, @PathVariable Long id){
        Movie movie = movieRepository.findOne(id);
        String[] files = {"/images/movie/西游记.jpg","/images/movie/西游记续集.jpg"};
        String[] rolelist = new String[]{"唐僧","孙悟空","猪八戒","沙僧"};
        Iterable<Actor> actors = actorRepository.findAll();

        model.addAttribute("files", files);
        model.addAttribute("rolelist",rolelist);
        model.addAttribute("movie",movie);
        model.addAttribute("actors",actors);

        return new ModelAndView("movie/edit");
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    public String update(Movie movie, HttpServletRequest request) throws Exception{
        String rolename = request.getParameter("rolename");
        String actorid = request.getParameter("actorid");

        Movie old = movieRepository.findOne(movie.getId());
        old.setName(movie.getName());
        old.setPhoto(movie.getPhoto());
        old.setCreateDate(movie.getCreateDate());

        if(!StringUtils.isEmpty(rolename) && !StringUtils.isEmpty(actorid)) {
            Actor actor = actorRepository.findOne(new Long(actorid));
            old.addRole(actor, rolename);
        }
        movieRepository.save(old);
        logger.info("修改->ID="+old.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    public String delete(@PathVariable Long id) throws Exception{
        Movie movie = movieRepository.findOne(id);
        movieRepository.delete(movie);
        logger.info("删除->ID="+id);
        return "1";
    }

    @RequestMapping(value="/list")
    public Page<Movie> list(HttpServletRequest request) throws Exception{
        String name = request.getParameter("name");
        String page = request.getParameter("page");
        String size = request.getParameter("size");
        Pageable pageable = new PageRequest(page==null? 0: Integer.parseInt(page), size==null? 10:Integer.parseInt(size),
                new Sort(Sort.Direction.DESC, "id"));

        Filters filters = new Filters();
        if (!StringUtils.isEmpty(name)) {
            Filter filter = new Filter("name", name);
            filters.add(filter);
        }

        return pagesService.findAll(Movie.class, pageable, filters);
    }
}
