/*
 * Copyright 2018 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */

package com.example.springBootspringsession.config;


import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.MessageBytes;
import org.springframework.session.Session;
import org.springframework.session.web.http.*;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangzhenhui
 * @date 2018/1/19 001918:53
 */
public class CustomHttpSessionStrategy implements MultiHttpSessionStrategy, HttpSessionManager {


    /**
     * The default delimiter for both serialization and deserialization.
     */
    private static final String DEFAULT_DELIMITER = " ";

    private static final String SESSION_IDS_WRITTEN_ATTR = CookieHttpSessionStrategy.class
            .getName().concat(".SESSIONS_WRITTEN_ATTR");

    static final String DEFAULT_ALIAS = "0";

    static final String DEFAULT_SESSION_ALIAS_PARAM_NAME = "_s";

    private static final Pattern ALIAS_PATTERN = Pattern.compile("^[\\w-]{1,50}$");

    private String sessionParam = DEFAULT_SESSION_ALIAS_PARAM_NAME;

    /**
     * 通过tomcat的PathParameter的方式来获取,例如/index;jsessionid=ABCD*
     */
    private static String sessionPathParam = "jsessionid";

    private CookieSerializer cookieSerializer = new DefaultCookieSerializer();

    /**
     * The delimiter between a session alias and a session id when reading a cookie value.
     * The default value is " ".
     */
    private String deserializationDelimiter = DEFAULT_DELIMITER;

    /**
     * The delimiter between a session alias and a session id when writing a cookie value.
     * The default is " ".
     */
    private String serializationDelimiter = DEFAULT_DELIMITER;


    @Override
    public String getRequestedSessionId(HttpServletRequest request) {
        Map<String, String> sessionIds = getSessionIds(request);
        String sessionAlias = getCurrentSessionAlias(request);
        parsePathParameters(request);
        String jsessionId = getPathParameter(sessionPathParam);
        String cookieSessionId = sessionIds.get(sessionAlias);

        return StringUtils.isBlank(jsessionId)?cookieSessionId:jsessionId;
    }

    @Override
    public String getCurrentSessionAlias(HttpServletRequest request) {
        if (this.sessionParam == null) {
            return DEFAULT_ALIAS;
        }
        String u = request.getParameter(this.sessionParam);
        if (u == null) {
            return DEFAULT_ALIAS;
        }
        if (!ALIAS_PATTERN.matcher(u).matches()) {
            return DEFAULT_ALIAS;
        }
        return u;
    }

    @Override
    public String getNewSessionAlias(HttpServletRequest request) {
        Set<String> sessionAliases = getSessionIds(request).keySet();
        if (sessionAliases.isEmpty()) {
            return DEFAULT_ALIAS;
        }
        long lastAlias = Long.decode(DEFAULT_ALIAS);
        for (String alias : sessionAliases) {
            long selectedAlias = safeParse(alias);
            if (selectedAlias > lastAlias) {
                lastAlias = selectedAlias;
            }
        }
        return Long.toHexString(lastAlias + 1);
    }

    private long safeParse(String hex) {
        try {
            return Long.decode("0x" + hex);
        }
        catch (NumberFormatException notNumber) {
            return 0;
        }
    }

    @Override
    public void onNewSession(Session session, HttpServletRequest request,
                             HttpServletResponse response) {
        Set<String> sessionIdsWritten = getSessionIdsWritten(request);
        if (sessionIdsWritten.contains(session.getId())) {
            return;
        }
        sessionIdsWritten.add(session.getId());

        Map<String, String> sessionIds = getSessionIds(request);
        String sessionAlias = getCurrentSessionAlias(request);
        sessionIds.put(sessionAlias, session.getId());

        String cookieValue = createSessionCookieValue(sessionIds);
        this.cookieSerializer
                .writeCookieValue(new CookieSerializer.CookieValue(request, response, cookieValue));
    }

    @SuppressWarnings("unchecked")
    private Set<String> getSessionIdsWritten(HttpServletRequest request) {
        Set<String> sessionsWritten = (Set<String>) request
                .getAttribute(SESSION_IDS_WRITTEN_ATTR);
        if (sessionsWritten == null) {
            sessionsWritten = new HashSet<String>();
            request.setAttribute(SESSION_IDS_WRITTEN_ATTR, sessionsWritten);
        }
        return sessionsWritten;
    }

    private String createSessionCookieValue(Map<String, String> sessionIds) {
        if (sessionIds.isEmpty()) {
            return "";
        }
        if (sessionIds.size() == 1 && sessionIds.keySet().contains(DEFAULT_ALIAS)) {
            return sessionIds.values().iterator().next();
        }

        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, String> entry : sessionIds.entrySet()) {
            String alias = entry.getKey();
            String id = entry.getValue();

            buffer.append(alias);
            buffer.append(this.serializationDelimiter);
            buffer.append(id);
            buffer.append(this.serializationDelimiter);
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }

    @Override
    public void onInvalidateSession(HttpServletRequest request,
                                    HttpServletResponse response) {
        Map<String, String> sessionIds = getSessionIds(request);
        String requestedAlias = getCurrentSessionAlias(request);
        sessionIds.remove(requestedAlias);

        String cookieValue = createSessionCookieValue(sessionIds);
        this.cookieSerializer
                .writeCookieValue(new CookieSerializer.CookieValue(request, response, cookieValue));
    }

    /**
     * Sets the name of the HTTP parameter that is used to specify the session alias. If
     * the value is null, then only a single session is supported per browser.
     *
     * @param sessionAliasParamName the name of the HTTP parameter used to specify the
     * session alias. If null, then ony a single session is supported per browser.
     */
    public void setSessionAliasParamName(String sessionAliasParamName) {
        this.sessionParam = sessionAliasParamName;
    }

    /**
     * Sets the {@link CookieSerializer} to be used.
     *
     * @param cookieSerializer the cookieSerializer to set. Cannot be null.
     */
    public void setCookieSerializer(CookieSerializer cookieSerializer) {
        Assert.notNull(cookieSerializer, "cookieSerializer cannot be null");
        this.cookieSerializer = cookieSerializer;
    }

    /**
     * Sets the name of the cookie to be used.
     * @param cookieName the name of the cookie to be used
     * @deprecated use {@link #setCookieSerializer(CookieSerializer)}
     */
    @Deprecated
    public void setCookieName(String cookieName) {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(cookieName);
        this.cookieSerializer = serializer;
    }

    /**
     * Sets the delimiter between a session alias and a session id when deserializing a
     * cookie. The default is " " This is useful when using
     * <a href="https://tools.ietf.org/html/rfc6265">RFC 6265</a> for writing the cookies
     * which doesn't allow for spaces in the cookie values.
     *
     * @param delimiter the delimiter to set (i.e. "_ " will try a delimeter of either "_"
     * or " ")
     */
    public void setDeserializationDelimiter(String delimiter) {
        this.deserializationDelimiter = delimiter;
    }

    /**
     * Sets the delimiter between a session alias and a session id when deserializing a
     * cookie. The default is " ". This is useful when using
     * <a href="https://tools.ietf.org/html/rfc6265">RFC 6265</a> for writing the cookies
     * which doesn't allow for spaces in the cookie values.
     *
     * @param delimiter the delimiter to set (i.e. "_")
     */
    public void setSerializationDelimiter(String delimiter) {
        this.serializationDelimiter = delimiter;
    }

    @Override
    public Map<String, String> getSessionIds(HttpServletRequest request) {
        List<String> cookieValues = this.cookieSerializer.readCookieValues(request);
        String sessionCookieValue = cookieValues.isEmpty() ? ""
                : cookieValues.iterator().next();
        Map<String, String> result = new LinkedHashMap<String, String>();
        StringTokenizer tokens = new StringTokenizer(sessionCookieValue,
                this.deserializationDelimiter);
        if (tokens.countTokens() == 1) {
            result.put(DEFAULT_ALIAS, tokens.nextToken());
            return result;
        }
        while (tokens.hasMoreTokens()) {
            String alias = tokens.nextToken();
            if (!tokens.hasMoreTokens()) {
                break;
            }
            String id = tokens.nextToken();
            result.put(alias, id);
        }
        return result;
    }


    private final Map<String,String> pathParameters = new HashMap<>();

    public void addPathParameter(String name, String value) {
        pathParameters.put(name, value);
    }

    public String getPathParameter(String name) {
        return pathParameters.get(name);
    }

    /**
     * 解析HttpServletRequest中pathParameter中
     * @param request
     */
    public void parsePathParameters(HttpServletRequest request){

        // Process in bytes (this is default format so this is normally a NO-OP

        //
        String uri = request.getRequestURI();

        MessageBytes mb = MessageBytes.newInstance();
        mb.setString(uri);
        mb.toBytes();
        ByteChunk uriBC = mb.getByteChunk();
        int semicolon = uriBC.indexOf(';', 0);
        // Performance optimisation. Return as soon as it is known there are no
        // path parameters;
        if (semicolon == -1) {
            return;
        }

        // What encoding to use? Some platforms, eg z/os, use a default
        // encoding that doesn't give the expected result so be explicit
        Charset charset = Charset.forName(request.getCharacterEncoding());


        while (semicolon > -1) {
            // Parse path param, and extract it from the decoded request URI
            int start = uriBC.getStart();
            int end = uriBC.getEnd();

            int pathParamStart = semicolon + 1;
            int pathParamEnd = ByteChunk.findBytes(uriBC.getBuffer(),
                    start + pathParamStart, end,
                    new byte[] {';', '/'});

            String pv = null;

            if (pathParamEnd >= 0) {
                if (charset != null) {
                    pv = new String(uriBC.getBuffer(), start + pathParamStart,
                            pathParamEnd - pathParamStart, charset);
                }
                // Extract path param from decoded request URI
                byte[] buf = uriBC.getBuffer();
                for (int i = 0; i < end - start - pathParamEnd; i++) {
                    buf[start + semicolon + i]
                            = buf[start + i + pathParamEnd];
                }
                uriBC.setBytes(buf, start,
                        end - start - pathParamEnd + semicolon);
            } else {
                if (charset != null) {
                    pv = new String(uriBC.getBuffer(), start + pathParamStart,
                            (end - start) - pathParamStart, charset);
                }
                uriBC.setEnd(start + semicolon);
            }


            if (pv != null) {
                int equals = pv.indexOf('=');
                if (equals > -1) {
                    String name = pv.substring(0, equals);
                    String value = pv.substring(equals + 1);
                    this.addPathParameter(name, value);
                }
            }

            semicolon = uriBC.indexOf(';', semicolon);
        }
    }


    @Override
    public HttpServletRequest wrapRequest(HttpServletRequest request,
                                          HttpServletResponse response) {
        request.setAttribute(HttpSessionManager.class.getName(), this);
        return request;
    }

    @Override
    public HttpServletResponse wrapResponse(HttpServletRequest request,
                                            HttpServletResponse response) {
        return new CustomHttpSessionStrategy.MultiSessionHttpServletResponse(response, request);
    }

    @Override
    public String encodeURL(String url, String sessionAlias) {
        String encodedSessionAlias = urlEncode(sessionAlias);
        int queryStart = url.indexOf("?");
        boolean isDefaultAlias = DEFAULT_ALIAS.equals(encodedSessionAlias);
        if (queryStart < 0) {
            return isDefaultAlias ? url
                    : url + "?" + this.sessionParam + "=" + encodedSessionAlias;
        }
        String path = url.substring(0, queryStart);
        String query = url.substring(queryStart + 1, url.length());
        String replacement = isDefaultAlias ? "" : "$1" + encodedSessionAlias;
        query = query.replaceFirst("((^|&)" + this.sessionParam + "=)([^&]+)?",
                replacement);
        String sessionParamReplacement = String.format("%s=%s", this.sessionParam,
                encodedSessionAlias);

        if (!isDefaultAlias && !query.contains(sessionParamReplacement)
                && url.endsWith(query)) {
            // no existing alias
            if (!(query.endsWith("&") || query.length() == 0)) {
                query += "&";
            }
            query += sessionParamReplacement;
        }

        return path + "?" + query;
    }

    private String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A {@link CookieHttpSessionStrategy} aware {@link HttpServletResponseWrapper}.
     */
    class MultiSessionHttpServletResponse extends HttpServletResponseWrapper {
        private final HttpServletRequest request;

        MultiSessionHttpServletResponse(HttpServletResponse response,
                                        HttpServletRequest request) {
            super(response);
            this.request = request;
        }

        private String getCurrentSessionAliasFromUrl(String url) {
            String currentSessionAliasFromUrl = null;
            int queryStart = url.indexOf("?");

            if (queryStart >= 0) {
                String query = url.substring(queryStart + 1);
                Matcher matcher = Pattern
                        .compile(String.format("%s=([^&]+)",
                                CustomHttpSessionStrategy.this.sessionParam))
                        .matcher(query);

                if (matcher.find()) {
                    currentSessionAliasFromUrl = matcher.group(1);
                }
            }

            return currentSessionAliasFromUrl;
        }

        @Override
        public String encodeRedirectURL(String url) {
            String encodedUrl = super.encodeRedirectURL(url);
            String currentSessionAliasFromUrl = getCurrentSessionAliasFromUrl(encodedUrl);
            String alias = (currentSessionAliasFromUrl != null)
                    ? currentSessionAliasFromUrl : getCurrentSessionAlias(this.request);

            return CustomHttpSessionStrategy.this.encodeURL(encodedUrl, alias);
        }

        @Override
        public String encodeURL(String url) {
            String encodedUrl = super.encodeURL(url);
            String currentSessionAliasFromUrl = getCurrentSessionAliasFromUrl(encodedUrl);
            String alias = (currentSessionAliasFromUrl != null)
                    ? currentSessionAliasFromUrl : getCurrentSessionAlias(this.request);

            return CustomHttpSessionStrategy.this.encodeURL(encodedUrl, alias);
        }
    }
}
