package com.test.data.test;

import com.test.data.config.Neo4jConfig;
import com.test.data.domain.Actor;
import com.test.data.domain.Movie;
import com.test.data.domain.Role;
import com.test.data.repositories.ActorRepository;
import com.test.data.repositories.MovieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Neo4jConfig.class})
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

        Movie matrix1 = new Movie();
        matrix1.setName("西游记");
        matrix1.setPhoto("/images/movie/西游记.jpg");
        matrix1.setCreateDate(new Date());

        Actor swk = new Actor();
        swk.setName("六小龄童");

        Actor zbj = new Actor();
        zbj.setName("马德华");

        Actor ccr = new Actor();
        ccr.setName("迟重瑞");

        Actor yhl = new Actor();
        yhl.setName("闫怀礼");


        matrix1.addRole(swk,  "孙悟空");
        matrix1.addRole(zbj,  "猪八戒");
        matrix1.addRole(ccr,  "唐僧");
        matrix1.addRole(yhl,  "沙僧");

        movieRepository.save(matrix1);
        Assert.notNull(matrix1.getId());

    }

    @Test
    public void get(){
        Movie movie = movieRepository.findByName("西游记");
        Assert.notNull(movie);
        logger.info("===movie=== movie:{}, {}",movie.getName(), movie.getCreateDate());
        for(Role role : movie.getRoles()){
            logger.info("====== actor:{}, role:{}", role.getActor().getName(), role.getName());
        }
    }
}
