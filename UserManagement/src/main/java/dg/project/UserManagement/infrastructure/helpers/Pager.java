package dg.project.UserManagement.infrastructure.helpers;

import java.util.List;

public class Pager<T> {
    private String search;
    private int pageIndex;
    private int pageSize;
    private int total;
    private List<T> registers;

    public Pager(List<T> registers, int total, int pageIndex, int pageSize, String search) {
        this.registers = registers;
        this.total = total;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.search = search;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) total / pageSize);
    }

    public boolean hasPreviousPage() {
        return pageIndex > 1;
    }

    public boolean hasNextPage() {
        return pageIndex < getTotalPages();
    }

    public List<T> getRegisters() {
        return registers;
    }

    public void setRegisters(List<T> registers) {
        this.registers = registers;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
