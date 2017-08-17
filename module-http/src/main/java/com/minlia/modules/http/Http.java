package com.minlia.modules.http;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Fluent builder for the {@linkplain HttpClient} >= 4.2 to simplify its usage.
 * <p/>
 * <p>To execute a request, use {@linkplain #get(String) Http.get("http://w...")}
 * or {@linkplain #post(String) Http.post("http://w...")}, specify at least the
 * {@linkplain HttpClient client} to use for this request and execute it with
 * one of the execution methods.
 * <p/>
 * <h2>Request execution methods overview:</h2>
 * <dl>
 * <dt>{@link HttpRequestBuilder#asResponse() .asResponse()}</dt>
 * <dd>execute and return the {@linkplain HttpResponse}</dd>
 * <p/>
 * <dt>{@link HttpRequestBuilder#asString() .asString()}</dt>
 * <dd>execute and return the payload body as a String</dd>
 * <p/>
 * <dt>{@link HttpRequestBuilder#asFile(File) .asFile(File)}</dt>
 * <dd>execute and save the stream to a file</dd>
 * <p/>
 * <dt>{@link HttpRequestBuilder#as(ResponseHandler) .as(ResponseHandler)}</dt>
 * <dd>execute and process the response with the {@link ResponseHandler}</dd>
 * <p/>
 * <dt>{@link HttpRequestBuilder#consumeResponse() .consumeResponse()}</dt>
 * <dd>execute and consume any available payload on the stream</dd>
 * <p/>
 * <dt>{@link HttpRequestBuilder#throwAwayResponse() .throwAwayResponse()}</dt>
 * <dd>execute and abort immediately</dd>
 * </dl>
 * <p/>
 * <h2>Usage examples:</h2>
 * <p><dl>
 * <dt>Simple GET-request</dt>
 * <dd><pre>final String site =
 *     Http.get("http://somesite.com")
 *         .use(client)
 *         .asString();</pre></dd>
 * <p/>
 * <dt>Extended GET-request using a {@linkplain RequestCustomizer request
 * customizer} and a custom {@linkplain ResponseHandler response handler}</dt>
 * <dd><pre>final String ok = Http.get("http://somesite.com")
 *    .use(client)
 *    .header("User-Agent", "HttpClient Wrapper")
 *    .charset("UTF-8")
 *    .followRedirects(true)
 *    .customize(new RequestCustomizer() {
 *        &#064;Override
 *        public void customize(final HttpUriRequest request) {
 *            HttpProtocolParams.useExpectContinue(request.getParams());
 *        }
 *    })
 *    .as(new ResponseHandler<String>() {
 *        &#064;Override
 *        public String handleResponse(final HttpResponse response) throws IOException {
 *            final int statusCode = response.getStatusLine().getStatusCode();
 *            return statusCode == HttpStatus.SC_OK ? "YES" : "NO";
 *        }
 *    });</pre></dd>
 * <p/>
 * <dt>Simple POST-request</dt>
 * <dd><pre>final HttpResponse serachResult =
 *     Http.post("http://somesite.com")
 *         .data("search_name", "cat")
 *         .data("search_gender", "m")
 *         .asResponse();</pre></dd>
 * </dl></p>
 *
 * @author Thomas Dudek (mastacode@gmail.com)
 * @author will (will@minlia.com
 * @version 1.0.0.RELEASE
 * @copyright Copyright (c) Thomas Dudek
 */
public final class Http {

    /**
     * May be used to modify the {@linkplain HttpUriRequest request} just
     * before it is being executed.
     *
     * @see {@link HttpRequestBuilder#customize(RequestCustomizer)}
     */
    public static interface RequestCustomizer {

        /**
         * Customizes the request before the execution is done.
         *
         * @param request the request to customize
         */
        void customize(final HttpUriRequest request);
    }

    Http() {
    }

    /**
     * Creates a builder object for a POST-request. Supports data and entity
     * modifications.
     *
     * @param url the URL to use for this request.
     * @return the builder object for this URL.
     */
    public static HttpRequestBuilder post(final String url) {
        return new HttpPostRequestBuilder(url);
    }

    /**
     * Creates a builder object for a GET-request. Supports no data nor entity
     * modifications.
     *
     * @param url the URL to use for this request.
     * @return the builder object for the this URL.
     */
    public static HttpRequestBuilder get(final String url) {
        return new HttpGetRequestBuilder(url);
    }

    /**
     * Converts a {@linkplain HttpResponse} to a String by calling
     * {@link EntityUtils#toString(HttpEntity)} on its {@linkplain HttpEntity
     * entity}.
     *
     * @param response       the {@linkplain HttpResponse response} to convert.
     * @param defaultCharset character set to be applied if none found in the entity.
     * @return the response body as a String or {@code null}, if no
     * response body exists or an error occurred while converting.
     * @throws NullPointerException if the given response was null
     */
    public static String asString(final HttpResponse response, final String defaultCharset) {
        if (response == null) {
            throw new NullPointerException();
        }

        final HttpEntity entity = response.getEntity();
        if (entity == null) {
            return null;
        }

        try {
            return EntityUtils.toString(entity, defaultCharset == null ? (Charset) null : Charset.forName(defaultCharset));
        } catch (final Exception e) {
            return null;
        }
    }

    /**
     * Contains a superset of builder methods for all request-type dependent
     * builders.
     */
    public abstract static class HttpRequestBuilder {

        protected final String url;
        protected HttpClient client;
        protected List<Header> headers;
        protected List<NameValuePair> data;
        protected List<RequestCustomizer> customizers;
        protected String charset = null;
        protected Boolean followRedirects;
        protected HttpUriRequest request;

        /**
         * Creates a new builder object for the given URL.
         *
         * @param url the URL for the current request.
         * @throws NullPointerException if the given URL was null
         */
        protected HttpRequestBuilder(final String url) {
            if (url == null) {
                throw new NullPointerException("URL must not be null.");
            }
            this.url = url;
        }

        /**
         * Sets the entity to send with this request.
         *
         * @param entity the entity to set for this request
         * @return this builder
         * @throws UnsupportedOperationException if this request not supports entity modifications
         */
        public HttpRequestBuilder entity(final HttpEntity entity) {
            throw new UnsupportedOperationException(
                    "This HTTP-method doesn't support to add an entity.");
        }

        /**
         * Appends data to send with this request.
         *
         * @param data the data to append to this request
         * @return this builder
         * @throws UnsupportedOperationException if this request not supports data modifications
         */
        public HttpRequestBuilder data(final NameValuePair... data) {
            if (data != null) {
                final List<NameValuePair> dataList = getData();
                for (final NameValuePair d : data) {
                    if (d != null) {
                        dataList.add(d);
                    }
                }
            }
            return this;
        }


        public static ArrayList<NameValuePair> getObjectNameValuePairs(Object obj) {
            ArrayList<NameValuePair> list = Lists.newArrayList();
            try {
                list = new ArrayList<NameValuePair>();
                for (Field field : obj.getClass().getDeclaredFields()) {
                    field.setAccessible(true); // if you want to modify private fields
                    if (field.get(obj) != null) {
                        String value = field.get(obj).toString();
                        if (!StringUtils.isEmpty(value)) {
                            NameValuePair nameValuePair = new BasicNameValuePair(field.getName(), value);
                            list.add(nameValuePair);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }


        public HttpRequestBuilder data(Object data) {
            if (data != null) {
                final List<NameValuePair> dataList = getData();
                dataList.addAll(getObjectNameValuePairs(data));
            }
            return this;
        }

        /**
         * Appends a new {@link NameValuePair}, specified by the given
         * {@code name} and {@code value}, to this request.
         *
         * @param name  the name of the parameter to add to this request
         * @param value the value of the parameter to add to this request
         * @return this builder
         * @throws UnsupportedOperationException if this request not supports data modifications
         */
        public HttpRequestBuilder data(final String name, final String value) {
            getData().add(new BasicNameValuePair(name, value));
            return this;
        }

        /**
         * Appends the String representation of each key-value-pair of the given
         * map to this request.
         *
         * @param data the {@link Map} containing the data to append to this request
         * @return this builder
         * @throws UnsupportedOperationException if this request not supports data modifications
         */
        public HttpRequestBuilder data(final Map<?, ?> data) {
            final List<NameValuePair> dataList = getData();
            for (Entry<?, ?> entry : data.entrySet()) {
                if (entry.getKey() == null) {
                    continue;
                }

                final String name = entry.getKey().toString();
                final String value = entry.getValue() == null ? null : entry.getValue().toString();
                dataList.add(new BasicNameValuePair(name, value));
            }
            return this;
        }

        /**
         * Specifies the {@linkplain HttpClient client} to use for this request.
         *
         * @param client the client
         * @return this builder
         * @throws NullPointerException if the given {@link HttpClient} was null
         */
        public HttpRequestBuilder use(final HttpClient client) {
            if (client == null) {
                throw new NullPointerException("HttpClient must not be null.");
            }
            this.client = client;
            return this;
        }

        /**
         * Adds the given {@linkplain RequestCustomizer request customizer} to
         * this request. All customizers are being applied sequentially just
         * before the request is being executed.
         *
         * @param customizer the customizer to add to this request
         * @return this builder
         */
        public HttpRequestBuilder customize(final RequestCustomizer customizer) {
            getCustomizers().add(customizer);
            return this;
        }

        /**
         * Adds a header with the given {@code name} and {@code value}
         * to this request.
         *
         * @param name
         * @param value
         * @return this builder
         */
        public HttpRequestBuilder header(final String name, final String value) {
            getHeaders().add(new BasicHeader(name, value));
            return this;
        }

        /**
         * Adds the given {@linkplain Header header} to this request.
         *
         * @param header
         * @return this builder
         */
        public HttpRequestBuilder header(final Header header) {
            getHeaders().add(header);
            return this;
        }

        /**
         * Sets the encoding for this request.
         *
         * @param charset
         * @return this builder
         */
        public HttpRequestBuilder charset(final String charset) {
            this.charset = charset;
            return this;
        }

        /**
         * Sets the behavior of redirection following for this request. The
         * behavior effects for this request only.
         *
         * @param follow {@code true}, if redirects should be followed, otherwise
         *               {@code false}
         * @return this builder
         */
        public HttpRequestBuilder followRedirects(final boolean follow) {
            followRedirects = follow;
            return this;
        }

        /**
         * Executes this request and returns the result as a
         * {@linkplain HttpResponse} object.
         *
         * @return the response of this request
         * @throws IllegalStateException if no {@link HttpClient} was specified
         * @throws IOException           if an error occurs while execution
         */
        public HttpResponse asResponse() throws IOException {
            if (client == null) {
                throw new IllegalStateException(
                        "Please specify a HttpClient instance to use for this request.");
            }

            request = createFinalRequest();

            final HttpResponse response = client.execute(request);

            return response;
        }

        /**
         * Executes this request and returns the payload body of the result as a
         * String. If no response body exists, this returns {@code null}.
         *
         * @return the response body as a String or {@code null} if
         * no response body exists.
         * @throws IOException if an error occurs while execution
         */
        public String asString() throws IOException {
            return Http.asString(asResponse(), charset);
        }

        /**
         * Executes this request and saves the response stream to a file. The
         * stream is going to be copied if and only if the response was
         * successful ({@code 2xx}) and a response body exists. If the response
         * code was {@code >= 300}, a {@link FileNotFoundException} is thrown.
         * If no body exists, this returns {@code false} and no exception is
         * thrown.
         *
         * @param target the file in which the stream should be copied.
         * @return {@code true} if the stream was copied successful to the file,
         * otherwise {@code false}.
         * @throws IOException           if an error occurs while execution
         * @throws FileNotFoundException if the response code was {@code >= 300}.
         */
        public boolean asFile(final File target) throws IOException {
            final HttpResponse response = asResponse();
            return new FileResponseHandler(target, url).handleResponse(response);
        }

        /**
         * Executes this request and processes the response using the given
         * response handler.
         *
         * @param handler the response handler
         * @return the response object generated by the response handler.
         * @throws IOException if an error occurs while execution
         */
        public <T> T as(final ResponseHandler<? extends T> handler) throws IOException {
            if (handler == null) {
                throw new NullPointerException("ResponseHandler must not be null.");
            }

            final HttpResponse response = asResponse();
            return handler.handleResponse(response);
        }

        /**
         * Executes this request and aborts immediately after execution using
         * the {@linkplain HttpUriRequest#abort() abort} method of this request.
         *
         * @throws IOException if an error occurs while execution
         */
        public void throwAwayResponse() throws IOException {
            asResponse();
            request.abort();
        }

        /**
         * Executes this request and consumes any available payload on the
         * response stream.
         *
         * @throws IOException if an error occurs while execution
         */
        public void consumeResponse() throws IOException {
            final HttpResponse response = asResponse();
            final HttpEntity entity = response.getEntity();
            if (entity == null) {
                return;
            }

            EntityUtils.consume(entity);
        }

        abstract protected HttpUriRequest createRequest() throws IOException;

        protected List<NameValuePair> getData() {
            if (data == null) {
                data = new ArrayList<NameValuePair>();
            }
            return data;
        }

        protected String getUrl() {
            return url;
        }

        protected List<Header> getHeaders() {
            if (headers == null) {
                headers = new ArrayList<Header>();
            }
            return headers;
        }

        protected List<RequestCustomizer> getCustomizers() {
            if (customizers == null) {
                customizers = new ArrayList<RequestCustomizer>();
            }
            return customizers;
        }

        protected HttpClient getClient() {
            return client;
        }

        private HttpUriRequest createFinalRequest() throws IOException {
            final HttpUriRequest request = createRequest();

            applyHeaders(request);
            if (followRedirects != null) {
                HttpClientParams.setRedirecting(request.getParams(), followRedirects);
            }
            applyCustomizers(request);

            return request;
        }

        private void applyHeaders(final HttpRequest request) {
            if (headers != null) {
                for (final Header h : headers) {
                    request.setHeader(h);
                }
            }
        }

        private void applyCustomizers(final HttpUriRequest request) {
            if (customizers != null) {
                for (final RequestCustomizer modifier : customizers) {
                    modifier.customize(request);
                }
            }
        }

    }

    // Response handlers

    /**
     * Saves the stream to a file and returns <code>true</code>, if no error
     * occurred while saving.
     *
     * @see {@link HttpRequestBuilder#asFile(File)}
     */
    private static class FileResponseHandler implements ResponseHandler<Boolean> {

        protected final File file;
        protected final String url;

        public FileResponseHandler(final File file, final String url) {
            this.file = file;
            this.url = url;
        }

        @Override
        public Boolean handleResponse(final HttpResponse response) throws IOException {
            final int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode >= 300) {
                throw new FileNotFoundException("Source not found at " + url
                        + ", response code " + statusCode);
            }

            final HttpEntity entity = response.getEntity();
            if (entity == null) {
                return false;
            }

            return copyStreamToFile(entity.getContent(), file);
        }

        protected boolean copyStreamToFile(final InputStream source, final File target) throws IOException {
            final byte[] buffer = new byte[1024 * 4];
            final OutputStream out = new FileOutputStream(target);
            int read = 0;
            while ((read = source.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            try {
                out.close();
            } catch (final IOException ignore) {
            }
            return true;
        }

    }

    // Request builders

    /**
     * GET-request builder.
     *
     * @see {@link Http#get(String)}
     */
    private static class HttpGetRequestBuilder extends HttpRequestBuilder {

        protected HttpGetRequestBuilder(final String url) {
            super(url);
        }

        @Override
        protected HttpUriRequest createRequest() throws IOException {
            try {
                final URIBuilder builder = new URIBuilder(url);
                final List<NameValuePair> dataList = getData();
                for (final NameValuePair d : dataList) {
                    builder.addParameter(d.getName(), d.getValue());
                }
                return new HttpGet(builder.toString());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * POST-request builder. Supports data and entity modifications.
     *
     * @see {@link Http#post(String)}
     */
    private static class HttpPostRequestBuilder extends HttpRequestBuilder {

        protected HttpEntity entity;

        protected HttpPostRequestBuilder(final String url) {
            super(url);
        }

        @Override
        public HttpRequestBuilder entity(final HttpEntity entity) {
            ensureNoData();
            this.entity = entity;
            return this;
        }

        @Override
        public HttpRequestBuilder data(final String name, final String value) {
            ensureNoEntity();
            return super.data(name, value);
        }


        @Override
        public HttpRequestBuilder data(final NameValuePair... data) {
            ensureNoEntity();
            return super.data(data);
        }

        @Override
        public HttpRequestBuilder data(final Map<?, ?> data) {
            ensureNoEntity();
            return super.data(data);
        }

        @Override
        protected HttpUriRequest createRequest() throws IOException {
            final HttpPost request = new HttpPost(url);
            if (data != null) {
                entity = new UrlEncodedFormEntity(data, charset);
            }

            request.setEntity(entity);
            return request;
        }

        private void ensureNoEntity() {
            if (entity != null) {
                throw new IllegalStateException(
                        "You cannot set the data after specifying a custom entity.");
            }
        }

        private void ensureNoData() {
            if (data != null) {
                throw new IllegalStateException(
                        "You cannot specify the entity after setting POST data.");
            }
        }

    }
}
