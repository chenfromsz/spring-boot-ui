package com.test.data.test;

import com.test.data.AppForTest;
import com.test.data.config.Neo4jConfig;
import com.test.data.domain.Actor;
import com.test.data.domain.Movie;
import com.test.data.domain.Role;
import com.test.data.repositories.ActorRepository;
import com.test.data.repositories.MovieRepository;
import com.test.data.service.PagesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Neo4jConfig.class, AppForTest.class})
@SpringBootTest
public class MovieTest {
    private static Logger logger = LoggerFactory.getLogger(MovieTest.class);

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ActorRepository actorRepository;

    @Before
    public void initData(){
        movieRepository.deleteAll();
        actorRepository.deleteAll();

        Movie xyj = new Movie();
        xyj.setName("西游记");
        xyj.setPhoto("/images/movie/西游记.jpg");
        xyj.setCreateDate(new Date());

        Actor lxnt = new Actor();
        lxnt.setName("六小龄童");

        Actor mdh = new Actor();
        mdh.setName("马德华");

        Actor ccr = new Actor();
        ccr.setName("迟重瑞");

        Actor yhl = new Actor();
        yhl.setName("闫怀礼");


        xyj.addRole(lxnt,  "孙悟空");
        xyj.addRole(mdh,  "猪八戒");
        xyj.addRole(ccr,  "唐僧");
        xyj.addRole(yhl,  "沙僧");

        movieRepository.save(xyj);
        Assert.notNull(xyj.getId(), "create error");

    }

    @Test
    public void get(){
        Movie movie = movieRepository.findByName("西游记");
        Assert.notNull(movie, "find error");
        logger.info("===movie=== movie:{}, {}",movie.getName(), movie.getCreateDate());
        for(Role role : movie.getRoles()){
            logger.info("====== actor:{}, role:{}", role.getActor().getName(), role.getName());
        }
    }

    @Autowired
    private PagesService<Movie> pagesService;

    //@Test
    public void getPage(){
        Pageable pageable = PageRequest.of(0, 10,Sort.by(Sort.Direction.DESC, "id"));

        Filters filters = new Filters();
        Filter filter = new Filter("name", ComparisonOperator.LIKE, "西游记");
        filters.add(filter);

        Page<Movie> page = pagesService.findAll(Movie.class, pageable, filters);

        Assert.notEmpty(page.getContent(), "find error");

        for(Movie movie : page.getContent()) {
            logger.info("=====movice:{}", movie.getName());
            for(Role role : movie.getRoles()){
                logger.info("========role:{}, atctor:{}",
                        role.getName(), role.getActor().getName());
            }
        }
    }

    //@Test
    public void find(){
        Movie movie = movieRepository.findByNameLike("西游记");
        Assert.notNull(movie, "find error");
        logger.info("===movie=== movie:{}, {}",movie.getName(), movie.getCreateDate());
        for(Role role : movie.getRoles()){
            logger.info("====== actor:{}, role:{}", role.getActor().getName(), role.getName());
        }
    }
}
