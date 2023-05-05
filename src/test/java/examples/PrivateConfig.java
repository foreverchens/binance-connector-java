package examples;

public final class PrivateConfig {
    private PrivateConfig() {
    }
    public static final String BASE_URL = "https://api.binance.com";

    public static final String API_KEY = "";
    public static final String SECRET_KEY = ""; // Unnecessary if PRIVATE_KEY_PATH is used
    public static final String PRIVATE_KEY_PATH = ""; // Key must be PKCS#8 standard

    public static final String TESTNET_API_KEY = API_KEY;
    public static final String TESTNET_SECRET_KEY = SECRET_KEY; // Unnecessary if TESTNET_PRIVATE_KEY_PATH is used
    public static final String TESTNET_PRIVATE_KEY_PATH = ""; //Key must be PKCS#8 standard
}
