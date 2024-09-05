package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article; // 해당 댓글의 게시물

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDTO commentDTO, Article article) {
        //예외 발생
        if (commentDTO.getId() !=null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 한다.");
        if (commentDTO.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐다.");
        //엔티티 생성 및 반환
        return new Comment(
                commentDTO.getId(),
                article,
                commentDTO.getNickname(),
                commentDTO.getBody()
        );
    }

    public void patch(CommentDTO commentDTO) {
            //예외 발생
            if (this.id != commentDTO.getId())
                throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
            // 객체 갱신
            if (commentDTO.getNickname() != null)
                this.nickname = commentDTO.getNickname();
            if (commentDTO.getBody() != null)
                this.body = commentDTO.getBody();
    }
}
