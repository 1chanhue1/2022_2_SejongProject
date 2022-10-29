package com.example.table1.service;

import com.example.table1.entity.Board;
import com.example.table1.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    public BoardRepository boardRepository;

    //글작성 관리
    public void write(Board board, MultipartFile file,MultipartFile file2) throws Exception
    {



        String projectPath = System.getProperty("user.dir") + "\\table1\\src\\main\\resources\\static\\files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        String filename2 = uuid+"_"+file2.getOriginalFilename();   //파일 이름은 UUID의 있는 랜덤값 + 원래 파일 이름으로 설정된다.
        File saveFile2 = new File(projectPath,filename2);         // 위에 젃힌 경로에, name으로 저장된다.
        file2.transferTo(saveFile2);




        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);
        board.setFilename2(filename2);                            //DB에 파일 이름 저장
        board.setFilepath2("/files/"+filename2);


        boardRepository.save(board);
    }
    public void write1(Board board, MultipartFile file) throws Exception{



        String projectPath = System.getProperty("user.dir") + "\\table1\\src\\main\\resources\\static\\files";


        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);


        board.setFilename(fileName);
        board.setOutput("/files/" + fileName);
        board.setId(120);
        board.setTitle("나야");
        board.setContent("와우");

        boardRepository.save(board);
    }


    // 게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable)          // Board 타입의 리스트를 사용한다. Board는 Entity에 있는 Board
    {
        return boardRepository.findAll(pageable);    // 해당 테이블에 있는 모든것을 가져온다.
    }
    public Page<Board> boardList(String searchKeyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }
    //특정 게시글 불러오기
    public Board boardView(Integer id){

        return boardRepository.findById(id).get();
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);

    }



}
