package ru.practicum.shareit.user.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;
}
