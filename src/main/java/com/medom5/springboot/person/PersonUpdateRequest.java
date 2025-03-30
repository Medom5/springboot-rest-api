package com.medom5.springboot.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PersonUpdateRequest(
        @NotEmpty String  name,
        @NotNull @Min(16) Integer age,
        @NotNull @Email String email
        ) {
}
