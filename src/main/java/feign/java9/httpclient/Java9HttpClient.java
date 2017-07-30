package feign.java9.httpclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import feign.Client;
import feign.Request;
import feign.Request.Options;
import feign.Response;

import jdk.incubator.http.*;
import jdk.incubator.http.HttpClient.Redirect;
import jdk.incubator.http.HttpRequest.*;
import jdk.incubator.http.HttpResponse.BodyHandler;

public class Java9HttpClient implements Client
{

    public Response execute(Request request, Options options) throws IOException
    {
        final HttpClient client = HttpClient.newBuilder()
            .followRedirects(Redirect.ALWAYS)
            .build();

        URI uri;
        try
        {
            uri = new URI(request.url());
        }
        catch (final URISyntaxException e)
        {
            throw new IOException("Invalid uri " + request.url(), e);
        }

        final BodyProcessor body;
        if(request.body() == null)
        {
            body = HttpRequest.noBody();
        }
        else
        {
            body = BodyProcessor.fromByteArray(request.body());
        }

        final HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(uri)
            .method(request.method(), body)
            .headers(asString(request.headers()))
            .build();

        HttpResponse<byte[]> httpResponse;
        try
        {
            httpResponse = client.send(httpRequest, BodyHandler.asByteArray());
        }
        catch (final InterruptedException e)
        {
            throw new IOException("Invalid uri " + request.url(), e);
        }

        System.out.println(httpResponse.headers().map());

        OptionalLong length = httpResponse.headers().firstValueAsLong("Content-Length");


        final Response response = Response.builder()
            .body(new ByteArrayInputStream(httpResponse.body()), length.isPresent()? (int)length.getAsLong() : null)
            .reason(httpResponse.headers().firstValue("Reason-Phrase").orElse(null))
            .request(request)
            .status(httpResponse.statusCode())
            .headers(castMapCollectType(httpResponse.headers().map()))
            .build();
        return response;
    }

    private Map<String, Collection<String>> castMapCollectType(Map<String, List<String>> map)
    {
        final Map<String, Collection<String>> result = new HashMap<>();
        map.forEach((key, value) -> result.put(key, new HashSet<>(value)));
        return result;
    }

    private String[] asString(Map<String, Collection<String>> headers)
    {
        return headers.entrySet().stream()
            .flatMap(entry -> entry.getValue()
                .stream()
                .map(value -> Arrays.asList(entry.getKey(), value))
                .flatMap(List::stream)
            )
            .collect(Collectors.toList())
            .toArray(new String[0]);
    }

}
