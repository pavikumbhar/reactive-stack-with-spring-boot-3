package com.pavikumbhar.mapper;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageDto<T> {
    
    @Builder.Default
    private List<T> content=new ArrayList<>();
    private long totalElements;
}
