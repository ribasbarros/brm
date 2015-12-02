package br.com.brm.scp.api.service.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.repositories.PedidoRepository;
import br.com.brm.scp.api.service.repositories.SkuRepository;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)
public class PedidoServiceDELETE_LIXOTest extends AbstractTestNGSpringContextTests{

	@Autowired
	private SkuRepository skuRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Test(groups = "CRUD", priority = 1)
	public void test(){
		List<SkuDocument> findAll = skuRepository.findAll();
		for(SkuDocument document : findAll){
			if( null == document.getUsuarioCriacao() ){
				skuRepository.delete(document);
			}else{
				document.setNivelServico(0D);
				document.setEstoqueAtual(0L);
				document.setEstoqueSeguranca(0L);
				document.setEstoqueMaximo(0L);
				document.setNivelServico(0.25);
				document.setLoteReposicao(7);
				skuRepository.save(document);
			}
		}
		
		pedidoRepository.deleteAll();
		
		
	}
	
	
	
}
