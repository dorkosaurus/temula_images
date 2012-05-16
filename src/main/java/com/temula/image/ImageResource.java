package com.temula.image;



import java.io.InputStream;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.mysql.jdbc.Blob;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("images")
public class ImageResource {
	private static final char TEMPLATE_START_CHAR='^';
	private static final char TEMPLATE_END_CHAR='$';

	/** Making this an instance variable so it can be reused**/
	private HibernateDataProvider dataProvider = new HibernateDataProvider();
	
	static final Logger logger = Logger.getLogger(ImageResource.class.getName());
	

	
	@GET
	@Produces("text/plain")
	public String getLocationList(){
		return "testme";
	}
	
	@POST
	@Consumes("text/html")
	public Response postTemplate(		
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
	
		Clock clock = new Clock();
		int arrIncrSize=1024;
		byte[]arr = new byte[arrIncrSize];
		int i=0,limit=arrIncrSize;

		try{
			int byte_ = uploadedInputStream.read();
			while(byte_!=-1){
				if(i<limit)arr[i]=(byte)byte_;
				else{
					limit+=arrIncrSize;
					byte[]arr2 = new byte[arr.length+arrIncrSize];
					System.arraycopy(arr, 0, arr2, 0, arr.length);
					arr=arr2;
					arr[i]=(byte)byte_;
					arr2=null;
				}
				byte_ = uploadedInputStream.read();
				i++;
			}
			Blob blob = new Blob(arr);
		}
		catch(Exception e){
			return Response.serverError().build();
		}
		return Response.ok().build();
	}
	

}