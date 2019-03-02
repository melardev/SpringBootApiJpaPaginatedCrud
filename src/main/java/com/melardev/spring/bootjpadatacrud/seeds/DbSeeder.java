package com.melardev.spring.bootjpadatacrud.seeds;

import com.github.javafaker.Faker;
import com.melardev.spring.bootjpadatacrud.entities.Todo;
import com.melardev.spring.bootjpadatacrud.repositories.TodosRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;


@Service
public class DbSeeder implements CommandLineRunner {
    @Autowired
    TodosRepository todosRepository;

    @Autowired
    DataSource dataSource;

    @Override
    public void run(String... args) {
        System.out.printf("[+] We are using the following database connection string : %s\n" +
                "Go ahead into http://localhost:8080/api/h2-console and paste that connection string,\nusername=user,password=password, to access" +
                "the h2 database console ;)", ((HikariDataSource) dataSource).getJdbcUrl());
        long todosCount = this.todosRepository.count();
        Faker faker = new Faker(new Random(System.currentTimeMillis()));
        long todosToSeed = 43;
        todosToSeed -= todosCount;


        Date startDate, endDate;
        startDate = Date.from(LocalDateTime.of(2016, 1, 1, 0, 0).toInstant(ZoneOffset.UTC));
        endDate = Date.from(LocalDateTime.of(2020, 1, 1, 0, 0).toInstant(ZoneOffset.UTC));

        LongStream.range(0, todosToSeed).forEach(i -> {
            Todo todo = new Todo(
                    StringUtils.collectionToDelimitedString(faker.lorem().words(faker.random().nextInt(2, 5)), "\n"),
                    org.apache.commons.lang3.StringUtils.join(faker.lorem().sentences(faker.random().nextInt(1, 3)), "\n"),
                    faker.random().nextBoolean());

            Date dateForCreatedAt = faker.date().between(startDate, endDate);
            LocalDateTime createdAt = dateForCreatedAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            Date dateForUpdatedAt = faker.date().future(2 * 365, TimeUnit.DAYS, dateForCreatedAt);
            LocalDateTime updatedAt = dateForUpdatedAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            todo.setCreatedAt(createdAt);
            todo.setUpdatedAt(updatedAt);
            todosRepository.save(todo);
        });
    }
}
