package com.example.flux.controller;

import com.example.flux.domain.User;
import com.example.flux.repository.UserRepository;
import com.example.util.CheckUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * ==========================================
 * Created with IntelliJ IDEA.
 * User: 小破天
 * Date: 2018-05-17
 * Time: 0:39
 * 博客园：http://www.cnblogs.com/xiaopotian/
 * ===========================================
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 以数组形式一次性返回数据
     *
     * @return
     */
    @GetMapping("/")
    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * 以SSE形式多次返回数据
     *
     * @return
     */
    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return userRepository.findAll();
    }

    /**
     * 新增数据
     *
     * @param user
     * @return
     */
    @PostMapping("/")
    public Mono<User> add(@Valid @RequestBody User user) {
        //spring data jpa里面，新增跟修改都是save。有id是修改，没有则为置空
        //根据实际情况是否置空id

        CheckUtil.checkName(user.getName());
        user.setId(null);
        return this.userRepository.save(user);
    }

    /**
     * 根据id删除用户 存在的时候返回200, 不存在返回404
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        // deleteByID 没有返回值, 不能判断数据是否存在
        // this.repository.deleteById(id)
        return this.userRepository.findById(id)
                // 当你要操作数据, 并返回一个Mono 这个时候使用flatMap
                // 如果不操作数据, 只是转换数据, 使用map
                .flatMap(u -> this.userRepository.delete(u)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 修改数据 存在的时候返回200 和修改后的数据, 不存在的时候返回404
     *
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> update(@PathVariable String id, @RequestBody User user) {
        return this.userRepository.findById(id)
                // flatMap 操作数据
                .flatMap(u -> {
                    u.setName(user.getName());
                    u.setAge(user.getAge());
                    return this.userRepository.save(u);
                })
                // map: 转换数据
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK)).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据ID查找用户 存在返回用户信息, 不存在返回404
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> findUserById(
            @PathVariable("id") String id) {
        return this.userRepository.findById(id)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 查找年龄区间段的用户
     *
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/age/{start}/{end}")
    public Flux<User> findByAgeBetween(@PathVariable Integer start,@PathVariable Integer end) {
        return this.userRepository.findByAgeBetween(start, end);
    }

    /**
     * 查找年龄区间段的用户
     *
     * @param start
     * @param end
     * @return
     */
    @GetMapping(value = "/stream/age/{start}/{end}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamFindByAgeBetween(@PathVariable Integer start,@PathVariable Integer end) {
        return this.userRepository.findByAgeBetween(start, end);
    }
}
