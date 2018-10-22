package kakaopay.hw;

import kakaopay.hw.domain.Work;
import kakaopay.hw.service.WorkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaopayHwApplicationTests {

    @Autowired
    private WorkService workService;

    @Test
    public void workListTest() {
        int pageNum = 1;
        Optional<List<Work>> optional = this.workService.getWorkList(pageNum);

        assertTrue(optional.isPresent());
        assertNotNull(optional.get());
    }

    @Test
    public void totalCount() {
        int cnt = this.workService.getWorkCount();
        // data.sql 파일에 16개 저장함
        assertEquals(cnt, 16);
    }

    @Test
    public void insertNewWork() {
        List<Integer> refIds = new ArrayList<>();

        // DB 저장
        this.workService.insertNewWork("디버깅", refIds);

        // DB에 저장되었나?
        Work work = this.workService.getWork(17);

        assertEquals("디버깅", work.getTodo());
    }

    @Test
    public void savedWorkTest() {
        // insert into work_list(ID, TODO) VALUES (12, '방청소');
        // insert into work_list(ID, TODO) VALUES (13, '집안일');

        Work w1 = this.workService.getWork(12);
        Work w2 = this.workService.getWork(13);

        assertEquals(w1.getId(), 12);
        assertEquals(w1.getTodo(), "방청소");

        assertEquals(w2.getId(), 13);
        assertEquals(w2.getTodo(), "집안일");
    }

    @Test
    public void totalWorkCount() {
        int cnt = this.workService.getWorks().size();
        // data.sql에서 16개 넣음
        assertEquals(cnt, 16);
    }

    @Test
    public void updateWork() {
        // insert into work_list(ID, TODO) VALUES (15, '청소');
        List<Integer> refIds = new ArrayList<>();
        refIds.add(7);
        refIds.add(8);

        this.workService.updateWork(10, "출근", refIds);

        // DB에서 갱신되었나 확인
        Work work = this.workService.getWork(10);

        assertEquals(work.getId(), 10);
        assertEquals(work.getTodo(), "출근");

        List<Integer> ids = work.getRefIds();
        assertEquals(refIds.toString(), ids.toString());
    }

    @Test
    public void completeWork() {
        // insert into work_list(ID, TODO) VALUES (8, '방청소');
        // insert into work_list(ID, TODO) VALUES (9, '집안일');

        List<Integer> ids = new ArrayList<>();
        ids.add(8);
        ids.add(9);
        this.workService.completeWorks(ids);

        Work w1 = workService.getWork(8);
        Work w2 = workService.getWork(9);

        assertTrue(w1.isCompletedYn());
        assertTrue(w2.isCompletedYn());
    }
}
