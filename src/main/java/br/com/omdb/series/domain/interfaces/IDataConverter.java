package br.com.omdb.series.domain.interfaces;

public interface IDataConverter {

    <T> T getData(String json, Class<T> classType);
}
