package com.temula.image;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import com.google.common.io.ByteStreams;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ExceptionInterceptor;
import com.sun.jersey.multipart.FormDataParam;

@Path("images")
public class ImageResource {
	private static final char TEMPLATE_START_CHAR='^';
	private static final char TEMPLATE_END_CHAR='$';

	/** Making this an instance variable so it can be reused**/
	private HibernateDataProvider dataProvider = new HibernateDataProvider();
	
	static final Logger logger = Logger.getLogger(ImageResource.class.getName());

	@GET 
	@Produces("text/html")
	public String getImageList(){
		String path2File = this.getClass().getResource("/templates/image/image.stg").getPath();
		STGroup g = new STGroupFile(path2File,TEMPLATE_START_CHAR,TEMPLATE_END_CHAR);
		ST st = g.getInstanceOf("list");
		List<Image> list = this.dataProvider.get(new Image());
		
		st.add("list",list);
		String ret = st.render();
		return ret;
	}

	
	@GET
	@Path("{imageId}")
	@Produces("image/gif")
	public Response getImage(@PathParam("imageId") int imageId){
		Image img = new Image();
		img.setImageId(imageId);
		logger.info("fetching image "+imageId);
		
		List<Image>images = dataProvider.get(img );
		if(images!=null && images.size()>0){
			Image image = images.get(0);
			return Response.ok(new ByteArrayInputStream(image.getImage())).build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postImage(		
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("imageName") String imageName) {
		
		imageName = (imageName==null || imageName.trim().length()==0)?"image name":imageName;
		logger.info("uploading image named "+imageName);

		try{
			/** Note I'm using Google Guava here...*/
			byte[]imageArr = ByteStreams.toByteArray(uploadedInputStream);
			Image image = new Image();
			image.setImage(imageArr);
			image.setImageName(imageName);
			List<Image>list = new ArrayList<Image>();
			list.add(image);
			dataProvider.post(list);
            logger.info("saved "+image.getImageName());
		}
		catch(Exception e){
			logger.severe("error:"+e.getMessage());
			e.printStackTrace();
			return Response.serverError().build();
		}
		Response response =  Response.ok().build();
		logger.info("all good...returning code "+response.getStatus());
		return response;
	}
	
	private static class ExceptionInterceptorImpl implements ExceptionInterceptor {

		public void destroy() {
			// TODO Auto-generated method stub
			
		}

		public void init(Connection arg0, Properties arg1) throws SQLException {
			// TODO Auto-generated method stub
			
		}

		public SQLException interceptException(SQLException arg0,
				Connection arg1) {
			return arg0;
		}
		
	}
	

}