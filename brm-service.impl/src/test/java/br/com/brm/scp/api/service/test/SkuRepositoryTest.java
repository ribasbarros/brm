package br.com.brm.scp.api.service.test;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.brm.scp.api.service.repositories.SkuRepository;
import br.com.brm.scp.security.config.AppConfigurationTest;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfigurationTest.class)
public class SkuRepositoryTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private SkuRepository skuRepository;

	@BeforeClass
	public void setup() throws Exception {
	}

	@AfterClass
	public void tearDown() throws Exception {
	}

	@Test(enabled = true, groups = "Repo", priority = 1)
	public void test() {
		
		ObjectId[] tags = new ObjectId[]{new ObjectId("55ec7e9b601f8294f3e57cfa")};
		int size = tags.length;
		
		System.out.println(tags);
		System.out.println(size);
		
		System.out.println(skuRepository.findSku("55ec7e9b601f8294f3e57cf9", tags, size));
	}

}
