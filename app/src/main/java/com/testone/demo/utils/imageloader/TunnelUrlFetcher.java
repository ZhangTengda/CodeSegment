package com.testone.demo.utils.imageloader;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.bumptech.glide.util.Synthetic;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A DataFetcher that retrieves an {@link InputStream} for a Url.
 */
public final class TunnelUrlFetcher implements DataFetcher<InputStream> {

    private static final HttpUrlFactory DEFAULT_FACTORY = null;
    private static final int MAXIMUM_REDIRECTS = 5;
    /**
     * Returned when a connection error prevented us from receiving an http error.
     */
    private static final int INVALID_STATUS_CODE = -1;
    public static Map<String, String> requestHeaders = new HashMap<>();

    private final Object lock = new Object();
    private final GlideUrl glideUrl;
    private final int timeout;
    private HttpUrlFactory factory;

    private HttpURLConnection urlConnection;
    private InputStream stream;
    private volatile boolean isCancelled;

    public TunnelUrlFetcher(GlideUrl glideUrl) {
        this(glideUrl, 30000);
    }

    public TunnelUrlFetcher(GlideUrl glideUrl, int timeout) {
        this(glideUrl, timeout, DEFAULT_FACTORY);
    }

    TunnelUrlFetcher(GlideUrl glideUrl, int timeout, HttpUrlFactory factory) {
        this.glideUrl = glideUrl;
        this.timeout = timeout;
        this.factory = factory;
    }

    // Referencing constants is less clear than a simple static method.
    private boolean isHttpOk(int statusCode) {
        return statusCode / 100 == 2;
    }

    // Referencing constants is less clear than a simple static method.
    private boolean isHttpRedirect(int statusCode) {
        return statusCode / 100 == 3;
    }

    @Override
    public void loadData(@NonNull Priority priority,
                         @NonNull DataCallback<? super InputStream> callback) {
        try {
            InputStream result = loadDataWithRedirects(glideUrl.toURL(), 0, null, glideUrl.getHeaders());
            callback.onDataReady(result);
        } catch (IOException e) {
            callback.onLoadFailed(e);
        }
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                // Ignore
            }
        }
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        urlConnection = null;
    }

    @Override
    public void cancel() {
        // TODO: we should consider disconnecting the url connection here, but we can't do so
        // directly because cancel is often called on the main thread.
        isCancelled = true;
    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }

    private /*synchronized*/ InputStream loadDataWithRedirects(URL url, int redirects, URL lastUrl,
                                                               Map<String, String> headers) throws IOException {
        final long start = System.currentTimeMillis();
//        if (redirects >= MAXIMUM_REDIRECTS) {
//            final String message = MessageFormatter.format(
//                    "#loadDataWithRedirects() Too many (> {}) redirects!", MAXIMUM_REDIRECTS
//            ).getMessage();
//            throw new HttpException(message);
//        } else {
//            // Comparing the URLs using .equals performs additional network I/O and is generally broken.
//            // See http://michaelscharf.blogspot.com/2006/11/javaneturlequals-and-hashcode-make.html.
//            try {
//                if (lastUrl != null && url.toURI().equals(lastUrl.toURI())) {
//                    final String message = MessageFormatter.arrayFormat(
//                            "#loadDataWithRedirects() In re-direct loop {}",
//                            new Object[]{lastUrl}
//                    ).getMessage();
//                    throw new HttpException(message);
//                }
//            } catch (URISyntaxException e) {
//                // Do nothing, this is best effort.
//            }
//        }
        urlConnection = factory != null
                ? factory.open(url)
                : (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(timeout);
        urlConnection.setReadTimeout(timeout);
        urlConnection.setUseCaches(false);
        urlConnection.setDoInput(true);
        final Map<String, String> requestHeaders = new HashMap<>(headers);
        if (factory != null) {
            synchronized (lock) {
                factory.injectHeader(url.toString(), requestHeaders);
            }
        }
        for (Map.Entry<String, String> headerEntry : requestHeaders.entrySet()) {
            urlConnection.addRequestProperty(headerEntry.getKey(), headerEntry.getValue());
        }

        // Stop the urlConnection instance of HttpUrlConnection from following redirects so that
        // redirects will be handled by recursive calls to this method, loadDataWithRedirects.
        urlConnection.setInstanceFollowRedirects(false);

        // Connect explicitly to avoid errors in decoders if connection fails.
        urlConnection.connect();
        final String contentType = urlConnection.getContentType();
        // Set the stream so that it's closed in cleanup to avoid resource leaks. See #2352.
        stream = urlConnection.getInputStream();
        if (isCancelled) {
            return null;
        }
        final int statusCode = urlConnection.getResponseCode();
        // 当返回的数据是图片的二级制流时，会显示不出图片，所以改成以下判断
        // 返回 text/* 类型的数据时可能是因为 token 丢失导致
        if (contentType == null || contentType.startsWith("text")) {
            return null;
        }
        if (isHttpOk(statusCode)) {
            return getStreamForSuccessfulRequest(urlConnection);
        } else if (isHttpRedirect(statusCode)) {
//            String redirectUrlString = urlConnection.getHeaderField("Location");
//            if (TextUtils.isEmpty(redirectUrlString)) {
//                final String message = MessageFormatter.format(
//                        "#loadDataWithRedirects() Received empty or null redirect url {}, code {}",
//                        url, statusCode
//                ).getMessage();
//                throw new HttpException(message);
//            }
//            URL redirectUrl = new URL(url, redirectUrlString);
//            // Closing the stream specifically is required to avoid leaking ResponseBodys in addition
//            // to disconnecting the url connection below. See #2352.
//            cleanup();
//            return loadDataWithRedirects(redirectUrl, redirects + 1, url, headers);
        } else if (statusCode == INVALID_STATUS_CODE) {
//            final String message = MessageFormatter.format(
//                    "#loadDataWithRedirects() invalid status code {}, url {}",
//                    statusCode, url.toString()
//            ).getMessage();
//            throw new HttpException(message);
        } else {
//            final String message = MessageFormatter.arrayFormat(
//                    "#loadDataWithRedirects() error {}, code {}, url {}",
//                    new Object[]{urlConnection.getResponseMessage(), statusCode, url.toString()}
//            ).getMessage();
//            throw new HttpException(message);
        }
        return getStreamForSuccessfulRequest(urlConnection);
    }

    private InputStream getStreamForSuccessfulRequest(HttpURLConnection urlConnection)
            throws IOException {
        if (TextUtils.isEmpty(urlConnection.getContentEncoding())) {
            int contentLength = urlConnection.getContentLength();
            stream = ContentLengthInputStream.obtain(urlConnection.getInputStream(), contentLength);
        } else {
            stream = urlConnection.getInputStream();
        }
        return stream;
    }

    /**
     * 数据处理的核心接口
     */
    public interface HttpUrlFactory {

        /**
         * 对要打开的 URL 做一些操作，比如设置代理等
         *
         * @param url {@link URL}
         * @return {@link HttpURLConnection}
         * @throws IOException
         */
        HttpURLConnection open(URL url) throws IOException;

        /**
         * 对一次请求注入 header
         *
         * @param headers url 原有的 header
         * @param url
         */
        void injectHeader(String url, Map<String, String> headers);
    }

    static class DefaultHttpUrlFactory implements HttpUrlFactory {

        final Reference<Context> mRefContext;

        @Synthetic
        DefaultHttpUrlFactory(Context context) {
            mRefContext = new SoftReference<>(context);
        }

        @Override
        public synchronized HttpURLConnection open(URL url) throws IOException {
            final String scheme = Uri.parse(url.toString()).getScheme();
            final Context appContext = ContextProvider.getContext();
            if ("https".equalsIgnoreCase(scheme)) {
//                try {
//                    final TrustManager trustManager = MXAPI.getInstance(appContext).getTrustManager(appContext);
//                    TrustManager[] tm = {trustManager};
//                    SSLContext sslContext = SSLContext.getInstance("TLS");
//                    sslContext.init(null, tm, null);
//                    SSLSocketFactory socketFactory = new MXTls12SocketFactory(sslContext.getSocketFactory());
//                    HttpsURLConnection.setDefaultSSLSocketFactory(socketFactory);
//                } catch (Exception e) {
//                    MXLog.log(MXLog.IMAGE, "DefaultHttpUrlFactory#open() https verify error {}", e);
//                }
            }
//            if (MXKit.getInstance().getKitConfiguration().isVpnEnable(appContext)) {
//                return MXKit.getInstance().setHttpURLConnectionProxy(url);
//            }
            return (HttpURLConnection) url.openConnection();
        }

        @Override
        public void injectHeader(String url, Map<String, String> headers) {
//            if (!url.startsWith(MXKit.getInstance().getKitConfiguration().getServerHost())) {
//                return;
//            }
//            // user-agent
//            headers.put("User-Agent", MXKit.getInstance().getUseragent());
//            // keep-alive
//            headers.put("Connection", "keep-Alive");
//            // token 和 network-id
//            if (requestHeaders == null || requestHeaders.isEmpty()) {
//                WBSysUtils.refreshToken(mRefContext.get(), requestHeaders = new HashMap<>());
//            }
//            if (!requestHeaders.isEmpty()) {
//                for (final String key : requestHeaders.keySet()) {
//                    // MXLog.log(MXLog.IMAGE, "header key = {}, value = {}", key, requestHeaders.get(key));
//                    headers.put(key, requestHeaders.get(key));
//                }
//            }
        }
    }
}
