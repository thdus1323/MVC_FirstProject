package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDTO;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    //post
    public Coffee create(CoffeeDTO coffeeDTO){
        Coffee coffee = coffeeDTO.toEntity();
        if (coffee.getId() != null){
            return null;
        }
        return coffeeRepository.save(coffee);
    }

    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee update(Long id, CoffeeDTO coffeeDTO) {
        //db에 넣을 수정용 엔티티 생성
        Coffee coffee = coffeeDTO.toEntity();
        log.info("id : {}, coffee: {}", id, coffee.toString());

        //db에 대상 엔티티 있는지 조회하고
        Coffee target = coffeeRepository.findById(id).orElse(null);

        //대상 엔티티가 없거나 수정하려는 id가 없는 잘못된 요청왓을 때 처리
        if (target == null || id != coffee.getId()) {
            log.info("잘못된 요청 ! id : {}, coffee : {}", id, coffee.toString());
            return null;
        }

        //대상 엔티티 있다. 수정 내용으로 바꾸고 정상 응답 보내삼
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return updated;
    }

    public Coffee delete(Long id) {
        //대상 찾기
        Coffee target = coffeeRepository.findById(id).orElse(null);
        //찾는 대상이 없으면 잘못된 요청 처리
        if (target == null){
            return null;
        }
        //찾는 대상이 있으면, 대상 삭제
        coffeeRepository.delete(target);
        return target;
    }
}
