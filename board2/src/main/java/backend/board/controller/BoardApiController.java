package backend.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.board.dto.BoardRequestDto;
import backend.board.dto.BoardResponseDto;
import backend.board.service.BoardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardApiController {
	private final BoardService boardSerivce;

	// 게시글 생성

   @PostMapping("/boards")
   public Long save(@RequestBody final BoardRequestDto params) {
	   return boardSerivce.save(params);
   }

   // 게시글 리스트 조회

   @GetMapping("/boards")
   public List<BoardResponseDto> findAll(){
	   return boardSerivce.findAll();
   }

   // 게시글 수정

   @PatchMapping("/boards/{id}")
   public Long save(@PathVariable final Long id, @RequestBody final BoardRequestDto params) {
	   return boardSerivce.update(id, params);
   }

}