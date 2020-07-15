package br.com.exemplo.appclientevip.Controller;

import br.com.exemplo.appclientevip.model.Cliente;

public class ClienteController {

    public static boolean validarDadosDoCliente(Cliente cliente, String email, String senha){
        boolean retorno = (cliente.getEmail().equals(email)) && (cliente.getSenha().equals(senha));

        return retorno;
    }

    public static Cliente getClienteFake() {
        Cliente fake = new Cliente();
        fake.setPrimeiroNome("Marco");
        fake.setSobreNome("Caiu");
        fake.setEmail("marco@gmail.com");
        fake.setSenha("12345");
        fake.setPessoaFisica(true);

        return fake;
    }
}
