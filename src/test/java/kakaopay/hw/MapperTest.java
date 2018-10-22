package kakaopay.hw;

import kakaopay.hw.domain.Work;
import kakaopay.hw.repository.WorkMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    private WorkMapper workMapper;

    // 새로운 ID
 //   int getNewId();
    @Test
    public void getNewId() {
        int newId = workMapper.getNewId();
        // data.xml -> insert 16개 / 다른 테스트 먼저 하면 17개
//        assertEquals(17, newId);
        assertEquals(18, newId);
    }

    // 할일 전체 크기
//    int getCount();
    @Test
    public void getcountTest() {
        int cnt = this.workMapper.getCount();
        assertEquals(16, cnt);
    }

    // 전체 할일 목록 조회
//    List<Work> getWorkList(Map<String, Integer> paramMap);
    @Test
    public void getWorkListTest() {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("startRow", 0);
        paramMap.put("endRow", 10);
        List<Work> list = this.workMapper.getWorkList(paramMap);

        assertEquals("빨래", list.get(1).getTodo());
        assertEquals("집안일", list.get(4).getTodo());
    }

    // getWorkList에서 조회한 할일의 참조 할일의 ID 조회
//    List<Integer> getRefIds(int id);
    @Test
    public void getRefIDsTest() {
        int id = 4;
        List<Integer> list = this.workMapper.getRefIds(4);

        assertEquals(2, list.size());

        Integer[] arr = new Integer[2];
        arr[0] = 1;
        arr[1] = 3;

        assertArrayEquals(arr, list.toArray());
    }

    // 현재 등록된 할일 ID 목록
//    List<Work> getAllWork();
    @Test
    public void getAllWorkTest() {
        List<Work> list = this.workMapper.getAllWork();

        assertEquals(16, list.size());
    }

    // id에 해당하는 할일 정보 가져오기
//    Work getWork(int id);
    @Test
    public void getWorkTest() {
        // insert into work_list(ID, TODO) VALUES (2, '빨래');
        Work work = this.workMapper.getWork(2);

        assertEquals(2, work.getId());
        assertEquals("빨래", work.getTodo());
    }

    // 할일 등록 - 1건
//    void insertWork(Work work);
    @Test
    public void insertWorkTest() {
        int id = this.workMapper.getNewId();
        Work work = new Work();
        work.setId(id);
        work.setTodo("퇴근");

        this.workMapper.insertWork(work);
        Work work1 = this.workMapper.getWork(id);

        assertEquals(work.getId(), work1.getId());
        assertEquals(work.getTodo(), work1.getTodo());
    }

    // 등록된 할일의 참조할일의 ID 입력
//    void insertRefId(Map<String, Integer> paramMap);
    @Test
    public void insertRedIdTest() {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("newId", 5);
        paramMap.put("refId", 9);

        this.workMapper.insertRefId(paramMap);

        List<Integer> list = this.workMapper.getRefIds(5);

        assertEquals(9, (int)list.get(0));
    }

    // 할일 수정 - 1건
//    void updateWork(Work work);
    @Test
    public void updateWorkTest() {
        // 먼저 하나 가져와 확인
        int id = 5;
        Work work = this.workMapper.getWork(id);

        // 업데이트
        work.setTodo("칼퇴근");
        this.workMapper.updateWork(work);

        Work work1 = this.workMapper.getWork(id);

        // 확인
        assertEquals(work.getId(), work1.getId());
        assertEquals(work.getTodo(), work1.getTodo());
    }

    // 할일 완료
//    int completeWork(int id);
    @Test
    public void completeWorkTest() {
        int id = 10;
        this.workMapper.completeWork(id);

        // 완료여부 확인
        Work work = this.workMapper.getWork(id);

        assertTrue(work.isCompletedYn());
    }

    // 할일 참조 확인
//    int checkRedWork(int id);
    @Test
    public void checkRefWorkTest() {
        int cnt1 = this.workMapper.checkRedWork(1);
        int cnt2 = this.workMapper.checkRedWork(10);

        assertEquals(3, cnt1);
        assertEquals(0, cnt2);
    }
}
