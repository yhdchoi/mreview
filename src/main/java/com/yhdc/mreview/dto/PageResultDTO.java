package com.yhdc.mreview.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class PageResultDTO<DTO, EN> {

	// DTO List
	private List<DTO> dtoList;

	private int totalPage;

	// Current Page
	private int page;
	// List Size
	private int size;

	private int start, end;

	private boolean prev, next;

	// Page Number List
	private List<Integer> pageList;

	// Converts Object from JPQL into DTO
	public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {

		dtoList = result.stream().map(fn).collect(Collectors.toList());

		totalPage = result.getTotalPages();

		makePageList(result.getPageable());
	}

	// Returns 10 Boards per Page
	private void makePageList(Pageable pageable) {
		
		// Starts from 0 so add 1
		this.page = pageable.getPageNumber() + 1;
		this.size = pageable.getPageSize();

		// Temporary End Page
		int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;

		start = tempEnd - 9;

		prev = start > 1;

		end = totalPage > tempEnd ? tempEnd : totalPage;

		next = totalPage > tempEnd;

		pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}
}
