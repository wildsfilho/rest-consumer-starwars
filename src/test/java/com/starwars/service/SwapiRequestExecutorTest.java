package com.starwars.service;

import com.starwars.domain.external.ResultSwapi;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

class SwapiRequestExecutorTest {

    private static final String pathUrl = "http://teste.com.br";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    @Spy
    private SwapiRequestExecutor requestExecutor;

    @Mock
    HttpURLConnection connection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_get() throws IOException {

        InputStream is = this.getClass().getResourceAsStream("/teste.json");
        doReturn(connection).when(requestExecutor).create(any());
        when(connection.getResponseCode()).thenReturn(200);
        when(connection.getInputStream()).thenReturn(is);
        String url = "http://www.google.ats";
        ResultSwapi resultSwapi = requestExecutor.get(url, ResultSwapi.class);

        assertNotNull(resultSwapi.getNext());
        assertEquals(1, resultSwapi.getResults().size());
    }

    @Test
    public void should_get_exception() {
        requestExecutor.get(pathUrl, ResultSwapi.class);
        exception.expect(IOException.class);

    }
}