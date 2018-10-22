package kakaopay.hw.repository;

import kakaopay.hw.domain.Work;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WorkMapper {

    // 새로운 ID
    int getNewId();

    // 할일 전체 크기
    int getCount();

    // 전체 할일 목록 조회
    List<Work> getWorkList(Map<String, Integer> paramMap);

    // getWorkList에서 조회한 할일의 참조 할일의 ID 조회
    List<Integer> getRefIds(int id);

    // 현재 등록된 할일 ID 목록
    List<Work> getAllWork();

    // id에 해당하는 할일 정보 가져오기
    Work getWork(int id);

    // 할일 등록 - 1건
    void insertWork(Work work);

    // 등록된 할일의 참조할일의 ID 입력
    void insertRefId(Map<String, Integer> paramMap);

    // 할일 수정 - 1건
    void updateWork(Work work);

    // 할일 삭제
    void deleteRefIds(int id);

    // 할일 완료
    int completeWork(int id);

    // 할일 참조 확인
    int checkRedWork(int id);
}
