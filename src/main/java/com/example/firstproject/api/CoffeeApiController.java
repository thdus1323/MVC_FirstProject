package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDTO;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CoffeeApiController {
    @Autowired
    private CoffeeRepository coffeeRepository;

    //GET
    @GetMapping("/api/coffees")
    public List<Coffee> coffees(){ return coffeeRepository.findAll();
    }

    @GetMapping("/api/coffees/{id}")
    public Coffee showCoffee(@PathVariable Long id){
        return coffeeRepository.findById(id).orElse(null);
    }
    //Post
    @PostMapping("/api/coffees")
    public Coffee make(@RequestBody CoffeeDTO coffeeDTO){
        Coffee coffee = coffeeDTO.toEntity();
        return coffeeRepository.save(coffee);
    }

    //UPDATE
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeDTO coffeeDTO) {
        //db에 넣을 수정용 엔티티 생성
        Coffee coffee = coffeeDTO.toEntity();
        log.info("id : {}, coffee: {}", id, coffee.toString());

        //db에 대상 엔티티 있는지 조회하고
        Coffee target = coffeeRepository.findById(id).orElse(null);

        //대상 엔티티가 없거나 수정하려는 id가 없는 잘못된 요청왓을 때 처리
        if (target == null || id != coffee.getId()) {
            log.info("잘못된 요청 ! id : {}, coffee : {}", id, coffee.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //대상 엔티티 있다. 수정 내용으로 바꾸고 정상 응답 보내삼
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //DELETE
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id){
        //대상 찾기
        Coffee target = coffeeRepository.findById(id).orElse(null);
        //찾는 대상이 없으면 잘못된 요청 처리
        if (target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //찾는 대상이 있으면, 대상 삭제
        coffeeRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
