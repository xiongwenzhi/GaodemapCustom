package com.android.orangetravel.base.logger;//package com.yang.base.rx;
//
//import com.yang.base.utils.log.YLog;
//
//import java.io.BufferedInputStream;
//import java.io.EOFException;
//import java.io.IOException;
//import java.net.URLDecoder;
//import java.nio.charset.Charset;
//import java.util.Arrays;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.Connection;
//import okhttp3.Headers;
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import okhttp3.internal.Util;
//import okhttp3.internal.http.HttpHeaders;
//import okio.Buffer;
//import okio.BufferedSource;
//
///**
// * An OkHttp interceptor which logs request and response information. Can be applied as an
// * {@linkplain OkHttpClient#interceptors() application interceptor} or as a {@linkplain
// * OkHttpClient#networkInterceptors() network interceptor}. <p> The format of the logs created by
// * this class should not be considered stable and may change slightly between releases. If you need
// * a stable logging format, use your own interceptor.
// */
//public final class LoggingInterceptor implements Interceptor {
//
//    private static final Charset UTF8 = Charset.forName("UTF-8");
//
//    public enum Level {
//        /**
//         * No logs.
//         */
//        NONE,
//        /**
//         * Logs request and response lines.
//         * <p>
//         * <p>Example:
//         * <pre>{@code
//         * --> POST /greeting http/1.1 (3-byte body)
//         *
//         * <-- 200 OK (22ms, 6-byte body)
//         * }</pre>
//         */
//        BASIC,
//        /**
//         * Logs request and response lines and their respective headers.
//         * <p>
//         * <p>Example:
//         * <pre>{@code
//         * --> POST /greeting http/1.1
//         * Host: example.com
//         * Content-Type: plain/text
//         * Content-Length: 3
//         * --> END POST
//         *
//         * <-- 200 OK (22ms)
//         * Content-Type: plain/text
//         * Content-Length: 6
//         * <-- END HTTP
//         * }</pre>
//         */
//        HEADERS,
//        /**
//         * Logs request and response lines and their respective headers and bodies (if present).
//         * <p>
//         * <p>Example:
//         * <pre>{@code
//         * --> POST /greeting http/1.1
//         * Host: example.com
//         * Content-Type: plain/text
//         * Content-Length: 3
//         *
//         * Hi?
//         * --> END POST
//         *
//         * <-- 200 OK (22ms)
//         * Content-Type: plain/text
//         * Content-Length: 6
//         *
//         * Hello!
//         * <-- END HTTP
//         * }</pre>
//         */
//        BODY
//    }
//
//    public interface Logger {
//        void logRequest(String message);
//
//        void logRespond(String message);
//
//        /**
//         * A {@link Logger} defaults output appropriate for the current platform.
//         */
//        Logger DEFAULT = new Logger() {
//            @Override
//            public void logRequest(String message) {
//                YLog.eNormal("HTTP.Request", message);
//            }
//
//            @Override
//            public void logRespond(String message) {
//                YLog.eNormal("HTTP.Respond", message);
//            }
//            /*public void log(String tag, String message) {
//                Platform.get().log(INFO, message, null);
//            }*/
//        };
//    }
//
//    public LoggingInterceptor() {
//        this(Logger.DEFAULT);
//    }
//
//    public LoggingInterceptor(Logger logger) {
//        this.logger = logger;
//    }
//
//    private final Logger logger;
//
//    private volatile Level level = Level.NONE;
//
//    /**
//     * Change the level at which this interceptor logs.
//     */
//    public LoggingInterceptor setLevel(Level level) {
//        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
//        this.level = level;
//        return this;
//    }
//
//    public Level getLevel() {
//        return level;
//    }
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//
//        Level level = this.level;
//        Request request = chain.request();
//
//        // 不打印Log,继续请求
//        if (level == Level.NONE) {
//            return chain.proceed(request);
//        }
//
//        boolean logBody = level == Level.BODY;
//        boolean logHeaders = logBody || level == Level.HEADERS;
//
//        RequestBody requestBody = request.body();
//        boolean hasRequestBody = requestBody != null;
//
//        logger.logRequest("╔══════ 请求 ════════════════════════════════════════════════════════════════════════════════════════");
//        Connection connection = chain.connection();
//        String requestMsg = "║ " + request.method() + " " + request.url() + (null != connection ? " " + connection.protocol() : "");
//        if (!logHeaders && hasRequestBody) {
//            requestMsg += " (" + requestBody.contentLength() + "-byte body)";
//        }
//        logger.logRequest(requestMsg);
//
//        if (logHeaders) {
//            if (hasRequestBody) {
//                // Request body headers are only present when installed as a network interceptor.
//                // Force them to be included (when available) so there values are known.
//                if (null != requestBody.contentType()) {
//                    logger.logRequest("║ Content-Type: " + requestBody.contentType());
//                }
//                if (-1 != requestBody.contentLength()) {
//                    logger.logRequest("║ Content-Length: " + requestBody.contentLength());
//                }
//                logger.logRequest("║ Parameters: " + getParameters(requestBody));
//            }
//
//
//            Headers headers = request.headers();
//            for (int i = 0, count = headers.size(); i < count; i++) {
//                String name = headers.name(i);
//                // Skip headers from the request body as they are explicitly logged above.
//                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
//                    logger.logRequest("║ " + name + ": " + headers.value(i));
//                }
//            }
//
//            if (!logBody || !hasRequestBody) {
//                logger.logRequest("║ END " + request.method());
//            } else if (bodyEncoded(request.headers())) {
//                logger.logRequest("║ END " + request.method() + " (encoded body omitted)");
//            } else {
//                Buffer buffer = new Buffer();
//                requestBody.writeTo(buffer);
//
//                Charset charset = UTF8;
//                MediaType contentType = requestBody.contentType();
//                if (null != contentType) {
//                    charset = contentType.charset(UTF8);
//                }
//
//                logger.logRequest("║ ");
//                if (isPlaintext(buffer)) {
//                    logger.logRequest("║ " + buffer.readString(charset));
//                    logger.logRequest("║ END " + request.method() + " (" + requestBody.contentLength() + "-byte body)");
//                } else {
//                    logger.logRequest("║ END " + request.method() + " (binary " + requestBody.contentLength() + "-byte body omitted)");
//                }
//            }
//        }
//        logger.logRequest("╚════════════════════════════════════════════════════════════════════════════════════════════════════");
//
//        long startNs = System.nanoTime();
//        Response response;
//        logger.logRespond("╔══════ 响应 ════════════════════════════════════════════════════════════════════════════════════════");
//        try {
//            response = chain.proceed(request);
//        } catch (Exception e) {
//            logger.logRespond("║ HTTP FAILED: " + e);
//            throw e;
//        }
//        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
//
//        ResponseBody responseBody = response.body();
//        long contentLength = responseBody.contentLength();
//        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
//        logger.logRespond("║ " + "HTTP状态码:" + response.code()
//                + "  Message:" + (response.message().isEmpty() ? "null" : response.message())
//                + "  URL:" + response.request().url()
//                + "  (时间:" + tookMs + "ms" + (!logHeaders ? ", " + bodySize + " body" : "") + ')');
//
//        if (logHeaders) {
//            Headers headers = response.headers();
//            logger.logRespond("║ Headers:");
//            for (int i = 0, count = headers.size(); i < count; i++) {
//                logger.logRespond("║ \u3000\u3000" + headers.name(i) + ": " + headers.value(i));
//            }
//            if (!logBody || !HttpHeaders.hasBody(response)) {
//                logger.logRespond("║ END HTTP");
//            } else if (bodyEncoded(response.headers())) {
//                logger.logRespond("║ END HTTP (encoded body omitted)");
//            } else {
//                BufferedSource source = responseBody.source();
//                source.request(Long.MAX_VALUE);// Buffer the entire body.
//                Buffer buffer = source.buffer();
//
//                Charset charset = UTF8;
//                MediaType contentType = responseBody.contentType();
//                if (null != contentType) {
//                    charset = contentType.charset(UTF8);
//                }
//                logger.logRespond("║ ");
//                if (!isPlaintext(buffer)) {
//                    logger.logRespond("║ END HTTP (binary " + buffer.size() + "-byte body omitted)");
//                    return response;
//                }
//                if (contentLength != 0) {
//                    logger.logRespond("║ " + buffer.clone().readString(charset));
//                    logger.logRespond("║ ");
//                }
//                logger.logRespond("║ END HTTP (" + buffer.size() + "-byte body)");
//            }
//        }
//        logger.logRespond("╚════════════════════════════════════════════════════════════════════════════════════════════════════");
//        return response;
//    }
//
//    /**
//     * Returns true if the body in question probably contains human readable text. Uses a small sample
//     * of code points to detect unicode control characters commonly used in binary file signatures.
//     */
//    static boolean isPlaintext(Buffer buffer) {
//        try {
//            Buffer prefix = new Buffer();
//            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
//            buffer.copyTo(prefix, 0, byteCount);
//            for (int i = 0; i < 16; i++) {
//                if (prefix.exhausted()) {
//                    break;
//                }
//                int codePoint = prefix.readUtf8CodePoint();
//                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
//                    return false;
//                }
//            }
//            return true;
//        } catch (EOFException e) {
//            return false;// Truncated UTF-8 sequence.
//        }
//    }
//
//    private boolean bodyEncoded(Headers headers) {
//        String contentEncoding = headers.get("Content-Encoding");
//        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
//    }
//
//    public String getParameters(RequestBody requestBody) {
//        try {
//            final Buffer buffer = new Buffer();
//            requestBody.writeTo(buffer);
//            //
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(buffer.inputStream());
//            final StringBuffer resultBuffer = new StringBuffer();
//            byte[] inputBytes = new byte[1024];
//            while (true) {
//                int count = bufferedInputStream.read(inputBytes);
//                if (count <= 0) {
//                    break;
//                }
//                resultBuffer.append(new String(Arrays.copyOf(inputBytes, count), Util.UTF_8));
//            }
//            final String parameter = URLDecoder.decode(resultBuffer.toString(), Util.UTF_8.name());
//            bufferedInputStream.close();
//
//            return parameter;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}