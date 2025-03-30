package com.medom5.springboot.person;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewPersonRequest(@NotEmpty String name,
                               @NotNull @Min(16) Integer age,
                               @NotNull Gender gender) {

}
