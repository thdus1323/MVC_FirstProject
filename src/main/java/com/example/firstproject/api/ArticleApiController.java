package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleDTO;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired
    private ArticleRepository articleRepository;

    //GET
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    //POST
    @PostMapping("/api/articles")
    public  Article create(@RequestBody ArticleDTO articleDTO){
        Article article = articleDTO.toEntity();
        return articleRepository.save(article);
    }

    //PATCH

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleDTO articleDTO){
        //수정용 엔티티 생성
        Article article = articleDTO.toEntity();
        log.info("id: {}, article: {}", id, article.toString());
        //db에 대상 엔티티가 있는지 조회하기
        Article target = articleRepository.findById(id).orElse(null);
        //대상 엔티티가 없거나 수정하려는 id가 잘못됐을 때, 처리
        if(target == null || id != article.getId() ){
            log.info("잘못된 요청 ! id : {}, article : {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //대상 엔티티가 있으면, 수정 내용으로 업데이트하고 정상 응답 보내기
            //업데이트 및 정상 응답하기
            target.patch(article);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);



    }
    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        //대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //잘못된 요청 처리하기
        if (target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //대상 삭제하기
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
