package io.pedro.aguilar.DTO;


//representa o retorno para o usuário
//ao se autenticar o usuário irá mandar as credenciais do CredenciaisDTO
//depois de autenticado irá ser retornado um objeto do tipo TokenDTO

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
      private String username;
      private String token;

}
