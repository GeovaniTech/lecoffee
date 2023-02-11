package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.file.UploadedFile;

import keep.file.KeepFileSBean;
import model.File;

public class FileUtil {
	private static KeepFileSBean sBean;
	
	static {
		sBean = new KeepFileSBean();
	}
	
	public static File convertPrimefacesFile(UploadedFile primefacesFile) throws IOException {
		byte[] arquivoByte = toByteArrayUsingJava(primefacesFile.getInputStream());
		
		File file = new File();
		
		file.setBytes(arquivoByte);
		file.setName(primefacesFile.getFileName());
		file.setType(primefacesFile.getContentType());
		
		sBean.save(file);
		
		return file;
	}
	
	public static byte[] toByteArrayUsingJava(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int reads = is.read();
		
		while(reads != -1) {
			baos.write(reads);
			reads = is.read();
		}
		
		return baos.toByteArray();
	}
	
	public static void download(File file) throws IOException {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		response.addHeader("Content-Disposition", "attachment; filename=" + file.getName());
		response.setContentType("application/octet-stream");
		response.setContentLength(file.getBytes().length);
		response.getOutputStream().write(file.getBytes());
		response.flushBuffer();
		
		FacesContext.getCurrentInstance().responseComplete();
	}

	public KeepFileSBean getsBean() {
		return sBean;
	}

	public void setsBean(KeepFileSBean sBean) {
		this.sBean = sBean;
	}
}
