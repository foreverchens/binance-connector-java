package unit.spot.trade;

import com.binance.connector.client.enums.HttpMethod;
import com.binance.connector.client.impl.SpotClientImpl;
import java.util.LinkedHashMap;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockWebServer;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import unit.MockData;
import unit.MockWebServerDispatcher;

public class TestGetOpenOcoOrders {
    private MockWebServer mockWebServer;
    private String baseUrl;

    private final long startTime = System.currentTimeMillis();
    private final long endTime = startTime + 1000;
    
    private final int recvWindow = 1000;

    @Before
    public void init() {
        this.mockWebServer = new MockWebServer();
        this.baseUrl = mockWebServer.url(MockData.PREFIX).toString();
    }

    @Test
    public void testGetOpenOcoOrdersWithParams() {
        String path = String.format("/api/v3/openOrderList?recvWindow=1000", startTime, endTime);
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("recvWindow", recvWindow);

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(MockData.PREFIX, path, MockData.MOCK_RESPONSE, HttpMethod.GET, MockData.HTTP_STATUS_OK);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(MockData.API_KEY, MockData.SECRET_KEY, baseUrl);
        String result = client.createTrade().getOpenOCOOrders(parameters);
        assertEquals(MockData.MOCK_RESPONSE, result);
    }
}
