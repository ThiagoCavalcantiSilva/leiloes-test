package br.com.alura.leilao.unidade.template;

import br.com.alura.leilao.model.Usuario;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class UsuarioTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Usuario.class).addTemplate("usuario", new Rule() {
            {
                add("id", 1L);
                add("nome", "fulano");
                add("senha", "1234");
                add("email", "fulano@email.com");
            }
        });
    }
}