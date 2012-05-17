package com.temula.image;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import junit.framework.TestCase;

public class TestImageResource extends TestCase {
	static final Logger logger = Logger.getLogger(TestImageResource.class.getName());
	
	public void testListGet()throws Exception{
		ImageResource ir = new ImageResource();
		String list = ir.getImageList();
		assertNotNull(list);
		logger.info(list);
	}
	public void testInstanceGet()throws Exception{
		ImageResource ir = new ImageResource();
		Response response = ir.getImage(1);
		assertNotNull(response);
		assertEquals(response.getStatus(),200);
		
		Object entity = response.getEntity();
		assertTrue(entity instanceof InputStream);
	}
	
	public void testPost()throws Exception{
		InputStream is = this.getClass().getResourceAsStream("/image.jpg");
		assertNotNull(is);
		ImageResource ir = new ImageResource();
		Response response = ir.postImage(is, null);
		assertEquals(response.getStatus(),200);
		is.close();
	}
}
