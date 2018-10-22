package kakaopay.hw.domain;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Alias("work")
public class Work {
    /*
    ID int AUTO_INCREMENT primary  key,
    TODO varchar(500),
    CREATED_DATE  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_DATE  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    COMPLETED_YN BOOLEAN default FALSE ,
    COMPLETED_DATE TIMESTAMP
     */

    private int id;         // ID - 숫자
    private String todo;    // 할일 내용
    private Date createdDate;  // 할일 최초 생성날짜
    private Date modifiedDate; // 최종 수정시간
    private boolean completedYn;        // 완료 여부
    private Date completedDate;    // 완료 시간
    private List<Integer> refIds;       // 참조하는 할일의 ID 목록

    private String refidStr;    // 참조 할일의 id 목록을 하나의 문자열로 저장
    private boolean check;  // checkox 용
}
