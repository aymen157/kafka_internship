package com.example.saasanalyticsprovider.AnalyticsDataProcessors;

public interface IDataProcessor<T, TOut> {
    TOut Process(T data);
}
