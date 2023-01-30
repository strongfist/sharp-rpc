package com.ali.rpc;

@FunctionalInterface
public interface Rpc<I,O> {


     O send(I i);
}
