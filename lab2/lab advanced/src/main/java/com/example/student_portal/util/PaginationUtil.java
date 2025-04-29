package com.example.student_portal.util;

import java.util.ArrayList;
import java.util.List;

public class PaginationUtil<T> {

    private List<T> items;
    private int pageSize;
    private int currentPage;
    private int totalPages;

    public PaginationUtil(List<T> items, int pageSize, int currentPage) {
        this.items = new ArrayList<>(items);
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil((double) items.size() / pageSize);
        this.currentPage = Math.max(1, Math.min(currentPage, totalPages));
    }

    public List<T> getCurrentPageItems() {
        if (items.isEmpty()) {
            return new ArrayList<>();
        }

        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, items.size());

        if (start >= items.size()) {
            return new ArrayList<>();
        }

        return items.subList(start, end);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean hasPreviousPage() {
        return currentPage > 1;
    }

    public boolean hasNextPage() {
        return currentPage < totalPages;
    }

    public int getPreviousPage() {
        return hasPreviousPage() ? currentPage - 1 : currentPage;
    }

    public int getNextPage() {
        return hasNextPage() ? currentPage + 1 : currentPage;
    }
}