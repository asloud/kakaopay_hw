package kakaopay.hw.service;

import kakaopay.hw.domain.Work;
import kakaopay.hw.repository.WorkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WorkMapper workMapper;

    // 1. 전체 목록 조회
    public Optional<List<Work>> getWorkList(int page) {
        List<Work> list = null;

        // 페이징 기능
        int endRow = page * 10;     // 보여줄 페이지의 마지막 글 idx
        int startRow = endRow - 10; // 보여줄 페이지의 첫 글 idx

        logger.info("START ROW :: " + startRow);
        logger.info("END ROW :: " + endRow);

        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("startRow", startRow);
        paramMap.put("endRow", endRow);

        list = this.workMapper.getWorkList(paramMap);
        logger.info("SIZE :: " + list.size());
        Work w = null;
        List<Integer> refIds = null;
        for(int i=0; i<list.size(); i++) {
            w = list.get(i);
            refIds = this.workMapper.getRefIds(w.getId());
            w.setRefIds(refIds);
            StringBuilder buf = new StringBuilder();
            for(int id : refIds) {
                buf.append("@").append(id).append(", ");
            }
            w.setRefidStr(buf.toString());
        }

        return list == null ? Optional.empty() : Optional.of(list);
    }

    // 전체 글 등록 수 - 페이징 처리 위함
    public int getWorkCount() {
        return this.workMapper.getCount();
    }

    // 2. 할일 등록
    public void insertNewWork(String todo, List<Integer> refIds) {
        // 새로운 ID 얻어온다
        int newId = this.workMapper.getNewId();
        logger.info("New ID : " + newId);
        Work newWork = new Work();
        newWork.setId(newId);
        newWork.setTodo(todo);

        this.workMapper.insertWork(newWork);

        Map<String, Integer> paramMap = new HashMap<>();
        for(int refId : refIds) {
            paramMap.put("newId", newId);
            paramMap.put("refId", refId);
            this.workMapper.insertRefId(paramMap);
        }
    }

    // 수정 기능에서 사용 - id에 해당하는 할일 정보
    public Work getWork(int id) {
        Work work = this.workMapper.getWork(id);
        List<Integer> refIds = this.workMapper.getRefIds(id);
        work.setRefIds(refIds);

        return work;
    }

    // 참조를 하기 위해 전체 할일 목록 가져온다
    public List<Work> getWorks() {
        return this.workMapper.getAllWork();
    }

    // 3. 할일 수정
    public void updateWork(int id, String todo, List<Integer> refIds) {
        Work updateWork = new Work();
        updateWork.setId(id);
        updateWork.setTodo(todo);
        this.workMapper.updateWork(updateWork);

        // 기존 참조 삭제\
        this.workMapper.deleteRefIds(id);

        // 다시 저장
        Map<String, Integer> paramMap = new HashMap<>();
        for(int refId : refIds) {
            paramMap.put("newId", id);
            paramMap.put("refId", refId);
            this.workMapper.insertRefId(paramMap);
        }
    }

    public void completeWorks(List<Integer> ids) {

        boolean checkIdRed = false;
        for(int id : ids) {
            checkIdRed = this.checkRefIds(id);
            logger.info("Completed? :: " + checkIdRed);

            if(checkIdRed) { // 참조한 할일이 모두 완료된 경우
                this.workMapper.completeWork(id);
            }
        }
    }

    // ID의 REF ID 완료여부 확인
    boolean checkRefIds(int id) {
        int check_cnt = this.workMapper.checkRedWork(id);
        if(check_cnt == 0) {
            return true;
        } else {
            return false;
        }
    }

}
