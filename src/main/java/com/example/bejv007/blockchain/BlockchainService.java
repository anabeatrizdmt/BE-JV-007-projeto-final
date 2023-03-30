package com.example.bejv007.blockchain;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class BlockchainService {

    private final String API_URL = "https://blockchain.info/tobtc?currency=BRL";

    public BigDecimal getBtcQuote() {
        try {
            URL url = new URL(API_URL.concat("&value=1"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            if (status != 200) {
                throw new RuntimeException("Erro: " + status);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            return new BigDecimal(content.toString());

        } catch (IOException e) {
            throw new RuntimeException("Erro: ", e);
        }
    }
}
