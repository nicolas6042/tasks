package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mbaranowicz
 */
@Getter
@AllArgsConstructor
public class TaskDto {

    private Long id;
    private String title;
    private String content;
}
