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
//import okhttp3.Headers;
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import okhttp3.internal.Util;
//import okio.Buffer;
//import okio.BufferedSource;
//
///**
// * 日志请求和响应信息拦截器
// *
// * @author yangfei
// */
//public final class LogInterceptor implements Interceptor {
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//
//        // 请求
//        Request request = chain.request();
//        // return chain.proceed(request);
//
//        // 请求体
//        RequestBody requestBody = request.body();
//        // 是否有请求体
//        boolean hasRequestBody = (null != requestBody);
//        logRequest("╔══════ 请求 ════════════════════════════════════════════════════════════════════════════════════════");
//        logRequest("║ " + request.url() + "\u3000" + request.method());
//        logRequest("║ Headers:");
//
//        Headers requestHeaders = request.headers();
//        for (int i = 0, count = requestHeaders.size(); i < count; i++) {
//            String name = requestHeaders.name(i);
//            // Skip headers from the request body as they are explicitly logged above.
//            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
//                logRequest("║ - " + name + ": " + requestHeaders.value(i));
//            }
//        }
//
//        if (hasRequestBody) {
//            logRequest("║ Body:");
//            // Request body headers are only present when installed as a network interceptor.
//            // Force them to be included (when available) so there values are known.
//            if (null != requestBody.contentType()) {
//                logRequest("║ - Content-Type: " + requestBody.contentType());
//            }
//            if (-1 != requestBody.contentLength()) {
//                logRequest("║ - Content-Length: " + requestBody.contentLength() + "-byte");
//            }
//
//            Buffer requestBuffer = new Buffer();
//            requestBody.writeTo(requestBuffer);
//
//            if (isPlaintext(requestBuffer)) {
//                // logRequest("║ Body: " + getParameter(requestBody));
//                String body = URLDecoder.decode(requestBuffer.readString(Util.UTF_8), Util.UTF_8.name());
//                String[] bodyArray = body.split(System.getProperty("line.separator"));
//                for (int i = 0; i < bodyArray.length; i++) {
//                    logRespond("║ " + bodyArray[i]);
//                }
//            }
//        }
//
////        Buffer buffer = new Buffer();
////        requestBody.writeTo(buffer);
////
////        Charset charset = Util.UTF_8;
////        MediaType contentType = requestBody.contentType();
////        if (null != contentType) {
////            charset = contentType.charset(Util.UTF_8);
////        }
////
////        logRequest("║ ");
////        if (isPlaintext(buffer)) {
////            logRequest("║ " + buffer.readString(charset));
////            logRequest("║ END " + request.method() + " (" + requestBody.contentLength() + "-byte body)");
////        } else {
////            logRequest("║ END " + request.method() + " (binary " + requestBody.contentLength() + "-byte body omitted)");
////        }
//        logRequest("╚════════════════════════════════════════════════════════════════════════════════════════════════════");
//
//        long startNs = System.nanoTime();
//        Response response;
//        try {
//            response = chain.proceed(request);
//        } catch (Exception e) {
//            logRespond("╔══════ 响应 ════════════════════════════════════════════════════════════════════════════════════════");
//            logRespond("║ HTTP FAILED: " + e);
//            throw e;
//        }
//        // 响应时间
//        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
//        // 响应体
//        ResponseBody responseBody = response.body();
//        logRespond("╔══════ 响应 ════════════════════════════════════════════════════════════════════════════════════════");
//        logRespond("║ " + response.request().url() + "\u3000" + response.code() + "\u3000" + tookMs + "ms");
//        logRespond("║ Headers:");
//        Headers responseHeaders = response.headers();
//        for (int i = 0, count = responseHeaders.size(); i < count; i++) {
//            logRespond("║ - " + responseHeaders.name(i) + ": " + responseHeaders.value(i));
//        }
//        logRespond("║ Body:");
//        if (null != responseBody.contentType()) {
//            logRespond("║ - Content-Type: " + responseBody.contentType());
//        }
//        BufferedSource source = responseBody.source();
//        source.request(Long.MAX_VALUE);// Buffer the entire body.
//        Buffer buffer = source.buffer();
//
//        Charset charset = Util.UTF_8;
//        MediaType contentType = responseBody.contentType();
//        if (null != contentType) {
//            charset = contentType.charset(Util.UTF_8);
//        }
//        if (isPlaintext(buffer)) {
//
//            logRespond("║ " + buffer.clone().readString(charset));
//            logRespond("║ END HTTP (" + buffer.size() + "-byte body)");
//        } else {
//            logRespond("║ END HTTP (binary " + buffer.size() + "-byte body omitted)");
//        }
//        logRespond("╚════════════════════════════════════════════════════════════════════════════════════════════════════");
//        return response;
//    }
//
//    /**
//     * Returns true if the body in question probably contains human readable text.
//     * Uses a small sample of code points to detect unicode control characters commonly used in binary file signatures.
//     */
//    private boolean isPlaintext(Buffer buffer) {
//        try {
//            Buffer newBuffer = new Buffer();
//            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
//            buffer.copyTo(newBuffer, 0, byteCount);
//            for (int i = 0; i < 16; i++) {
//                if (newBuffer.exhausted()) {
//                    break;
//                }
//                int codePoint = newBuffer.readUtf8CodePoint();
//                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
//                    return false;
//                }
//            }
//            return true;
//        } catch (EOFException e) {
//            // Truncated UTF-8 sequence.
//            return false;
//        }
//    }
//
//    private boolean bodyEncoded(Headers headers) {
//        String contentEncoding = headers.get("Content-Encoding");
//        return (null != contentEncoding) && (!contentEncoding.equalsIgnoreCase("identity"));
//    }
//
//    /**
//     * 获取参数
//     */
//    private String getParameter(RequestBody requestBody) {
//        try {
//            final Buffer buffer = new Buffer();
//            requestBody.writeTo(buffer);
//            //
//            BufferedInputStream bis = new BufferedInputStream(buffer.inputStream());
//            final StringBuffer resultSb = new StringBuffer();
//            byte[] bytes = new byte[1024];
//            while (true) {
//                int count = bis.read(bytes);
//                if (count <= 0) {
//                    break;
//                }
//                resultSb.append(new String(Arrays.copyOf(bytes, count), Util.UTF_8));
//            }
//            final String parameterStr = URLDecoder.decode(resultSb.toString(), Util.UTF_8.name());
//            bis.close();
//            //
//            return parameterStr;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private void logRequest(String msg) {
//        YLog.eNormal("HTTP.Request", msg);
//    }
//
//    private void logRespond(String msg) {
//        YLog.eNormal("HTTP.Respond", msg);
//    }
//
//}