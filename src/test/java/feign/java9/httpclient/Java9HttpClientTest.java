package feign.java9.httpclient;

import feign.Feign;
import feign.Feign.Builder;
import feign.client.AbstractClientTest;

/**
 * Tests client-specific behavior, such as ensuring Content-Length is sent when specified.
 */
public class Java9HttpClientTest extends AbstractClientTest
{

    @Override
    public Builder newBuilder()
    {
        return Feign.builder().client(new Java9HttpClient());
    }

}