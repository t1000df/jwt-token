package br.com.postalisonline.identitymanager.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.postalisonline.identitymanager.api.entity.User;
import br.com.postalisonline.identitymanager.api.model.UserCredential;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

	public User findByEmail(String email);
	
	@Query(value = "Select F.NUM_INSCRICAO as inscricao, F.NUM_MATRICULA as matricula , E.CPF_CGC as cpf , E.NOME_ENTID as nome , S.TX_SENHA_CRIPTO as senha from dbo.CS_FUNCIONARIO F INNER JOIN dbo.EE_ENTIDADE E ON F.COD_ENTID = E.COD_ENTID LEFT JOIN dbo.WEB_SSE_SENHA S ON F.NUM_MATRICULA = S.CD_USUARIO WHERE E.CPF_CGC = :cpf", nativeQuery = true)
	public UserCredential findByCPF(@Param("cpf") String cpf) ;

}
