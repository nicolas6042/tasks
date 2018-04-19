package com.domain.tasks.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String content;
}
