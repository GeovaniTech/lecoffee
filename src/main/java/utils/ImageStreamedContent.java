package utils;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.primefaces.model.StreamedContent;

public class ImageStreamedContent implements StreamedContent, Serializable {
	private static final long serialVersionUID = -9075021090360718046L;
	
	private final byte[] image;

    public ImageStreamedContent(byte[] image) {
        this.image = image;
    }

    @Override
    public Supplier<InputStream> getStream() {
        return () -> new ByteArrayInputStream(image);
    }

    @Override
    public String getContentType() {
        return "image/png";
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getContentLength() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Consumer<OutputStream> getWriter() {
		// TODO Auto-generated method stub
		return null;
	}
}