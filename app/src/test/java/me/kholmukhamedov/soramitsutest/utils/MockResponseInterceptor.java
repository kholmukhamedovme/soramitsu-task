package me.kholmukhamedov.soramitsutest.utils;

import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * OkHttp3 interceptor which provides a mock response from local resource file
 *
 * @see <a href="https://gist.github.com/mbunyard/78311b25aaba8b9bfec380f66c868644">Gist</a>
 */
public class MockResponseInterceptor implements Interceptor {

    private static final int BUFFER_SIZE = 1024 * 4;

    private String mFilename;
    private int mHttpCode;
    private boolean mThrowException;

    public MockResponseInterceptor(String filename, int httpCode, boolean throwException) {
        mFilename = filename;
        mHttpCode = httpCode;
        mThrowException = throwException;
    }

    private static byte[] toByteArray(InputStream is) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] b = new byte[BUFFER_SIZE];
            int n;
            while ((n = is.read(b)) != -1) {
                output.write(b, 0, n);
            }
            return output.toByteArray();
        }
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        // Throw exception if needed
        if (mThrowException) {
            throw new IOException();
        }

        // Get input stream and mime type for mock response file.
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(mFilename);
        String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
        if (mimeType == null) {
            mimeType = "application/json";
        }

        // Build and return mock response.
        return new Response.Builder()
                .addHeader("content-type", mimeType)
                .body(ResponseBody.create(MediaType.parse(mimeType), toByteArray(inputStream)))
                .code(mHttpCode)
                .message("Mock response from resources/" + mFilename)
                .protocol(Protocol.HTTP_1_0)
                .request(chain.request())
                .build();
    }

}
