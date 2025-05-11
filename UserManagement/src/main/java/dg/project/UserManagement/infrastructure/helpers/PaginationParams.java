package dg.project.UserManagement.infrastructure.helpers;

public class PaginationParams {
    private static final int MAX_PAGE_SIZE = 50;
    private int pageSize = 5;
    private int pageIndex = 1;
    private String search;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = (pageSize > MAX_PAGE_SIZE) ? MAX_PAGE_SIZE : pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = (pageIndex <= 0) ? 1 : pageIndex;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = (search != null && !search.isEmpty()) ? search.toLowerCase() : "";
    }
}
