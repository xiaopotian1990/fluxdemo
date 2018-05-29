package com.example.flux.repository;

import com.example.flux.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {
    /**
     * 查找年龄区间段的用户
     * @param start
     * @param end
     * @return
     */
    Flux<User> findByAgeBetween(Integer start,Integer end);
}
