package backend.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonParams {

	private int page; // 현재 페이지 번호
	private int recordPage; // 페이지당 출력 데이터 개수
	private int pageSize; // 화면 하단 출력할 페이지 개수
	private String keyword; // 검색 키워드
	private String serachType; // 검색 유형
	private Pagination pagination;
}
