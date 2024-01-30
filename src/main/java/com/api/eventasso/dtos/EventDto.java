package com.api.eventasso.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EventDto {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
