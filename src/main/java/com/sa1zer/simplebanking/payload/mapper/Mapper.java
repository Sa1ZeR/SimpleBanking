package com.sa1zer.simplebanking.payload.mapper;

public interface Mapper<F, T> {

    T map(F from);
}
