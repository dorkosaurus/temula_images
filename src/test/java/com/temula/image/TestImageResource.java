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
		Response response = ir.getImage(50);
		assertNotNull("response is null",response);
		
		int code = response.getStatus();
		assertTrue("code != 200 or 204",code == 200 || code==204);
		
		if(code==204)return;//nothing more to do if there is no content...

		Object entity = response.getEntity();
		assertTrue(entity instanceof InputStream);
	}
	
	public void testPost()throws Exception{
		InputStream is = this.getClass().getResourceAsStream("/image.jpg");
		assertNotNull(is);
		ImageResource ir = new ImageResource();
		Response response = ir.postImage(is, null);
		logger.info("client received code "+response.getStatus());
		assertEquals(200,response.getStatus());
		is.close();
	}
}
