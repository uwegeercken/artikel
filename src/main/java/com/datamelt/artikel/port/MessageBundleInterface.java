package com.datamelt.artikel.port;

public interface MessageBundleInterface
{
    String get(String message);
    String get(String key, Object... args);
}
