package kakaopay.hw.controller;

import kakaopay.hw.domain.Work;
import kakaopay.hw.service.WorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/work")
public class WorkController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WorkService workService;

    // 전체 할일 목록 보여주기, 페이징 처리
    // 목록 보여주기 - GET
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getList(HttpServletRequest request) {
        logger.info("list");
        // 파라미터에서 정보를 가져온다
        String pageNumStr = request.getParameter("pageNum");
        int pageNum = 1; // 시작 시 페이지 넘버 전달이 없어서 1로 설정
        if (pageNumStr != null && !"".equals(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        // 현재 페이지에서 보여줄 데이터
        Optional<List<Work>> optionalWorks = this.workService.getWorkList(pageNum);
        List<Work> list = null;
        if (optionalWorks.isPresent()) {
            list = optionalWorks.get();
        } else {
            list = new ArrayList<>();
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list");

        // JSP에 보여줄 데이터 정리
        int workCount = this.workService.getWorkCount();
        int pageCnt = (workCount / 10) + 1;
        modelAndView.addObject("list", list);           // 할일 목록
        modelAndView.addObject("pageNum", pageNum);     // 페이지 번호
        modelAndView.addObject("workCount", workCount); // 전체 할일 수
        modelAndView.addObject("pageCnt", pageCnt);     // 페이지 수

        return modelAndView;
    }

    // 할일 등록화면으로 이동
    // 화면 이동 - GET
    @RequestMapping(value = "/insertWork", method = RequestMethod.GET)
    public ModelAndView insertWork() {
        logger.info("/insertWork");
        // 참조 목록을 보여주어야 해서 기존에 등록된 할일 데이터 가져온다
        List<Work> list = this.workService.getWorks();

        return new ModelAndView("content", "list", list);
    }

    // 할일 수정 화면으로 이동
    // 화면 이동 - GET
    @RequestMapping(value = "/updateWork", method = RequestMethod.GET)
    public ModelAndView updateWork(ServletRequest request) {
        logger.info("updateWork");
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);

        // 수정하는 할일 정보
        Work work = this.workService.getWork(id);

        // 할일 참조 수정 위하여 기존 모든 정보 가져온다
        List<Work> works = this.workService.getWorks();


        int removeId = -1;
        List<Integer> refIds = work.getRefIds();
        for (int i = 0; i < works.size(); i++) {
            Work w = works.get(i);

            if (w.getId() == id) {   // 자기 자신은 제외
                removeId = i;
            }

            if (refIds.contains(w.getId())) {
                w.setCheck(true);
            } else {
                w.setCheck(false);
            }
        }
        works.remove(removeId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contentUpdate");
        modelAndView.addObject("work", work);
        modelAndView.addObject("works", works);

        return modelAndView;
    }

    // 할일 등록 처리 - DB INSERT
    // 데이터 생성 - POST
    @RequestMapping(value = "/processInsert", method = RequestMethod.POST)
    public void insertProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("/processInsert");
        String todoStr = request.getParameter("todo");

        Map<String, String[]> paramMap = request.getParameterMap();
        List<Integer> refIds = new ArrayList<>();
        Set<String> keys = paramMap.keySet();
        for (String key : keys) {
            if ("todo".equals(key)) {
                continue;
            }
            String value = request.getParameter(key);
            refIds.add(Integer.parseInt(value.trim()));
        }

        this.workService.insertNewWork(todoStr, refIds);

        response.sendRedirect("/work/list");
    }

    // 할일 수정 처리 - DB UPDATE
    // 데이터 수정 - PUT
    @RequestMapping(value = "/processUpdate", method = RequestMethod.PUT)
    public void updateProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("processUpdate");
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        String todoStr = request.getParameter("todo");

        Map<String, String[]> paramMap = request.getParameterMap();
        List<Integer> refIds = new ArrayList<>();
        Set<String> keys = paramMap.keySet();
        for (String key : keys) {
            if ("todo".equals(key) || "id".equals(key) || "_method".equals(key)) {
                continue;
            }
            String value = request.getParameter(key);
            refIds.add(Integer.parseInt(value.trim()));
        }

        this.workService.updateWork(id, todoStr, refIds);

        response.sendRedirect("/work/list");
    }

    // 할일 완료 처리 -> 전체 목록으로 이동
    // 할일 완료 -> 상태 수정 -> PUT
    @RequestMapping(value = "/completed", method = RequestMethod.PUT)
    public void completedWork(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("completed");
        Set<String> keys = request.getParameterMap().keySet();

        List<Integer> completeIdList = new ArrayList<>();
        for (String key : keys) {
            if (key.startsWith("completedCheck")) { // checkbox인 경우
                completeIdList.add(Integer.parseInt(request.getParameter(key)));
            }
        }

        if (completeIdList.size() > 0) { // 완료 대상이 있다면..
            this.workService.completeWorks(completeIdList);
        }

        response.sendRedirect("/work/list");
    }
}
