package io.pedro.aguilar.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

//representa a requisição
public class CredenciaisDTO {
    private String username;
    private String password;
}
