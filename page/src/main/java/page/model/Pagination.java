package page.model;

public class Pagination {
	int pg = 1;
	int sz = 15;
	int recordCount;

	public int getPg() {
		return pg;
	}

	public void setPg(int pg) {
		this.pg = pg;
	}

	public int getSz() {
		return sz;
	}

	public void setSz(int sz) {
		this.sz = sz;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public String getQueryString() {
		return String.format("pg=%d&sz=%d",pg,sz);
	}
}
