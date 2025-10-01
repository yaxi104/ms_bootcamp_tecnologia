package com.reactive.ms_technology.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageInfoTest {

    @Test
    void constructorShouldStoreValuesCorrectly() {
        PageInfo pageInfo = new PageInfo(2, 5);
        assertEquals(2, pageInfo.getPage());
        assertEquals(5, pageInfo.getSize());
    }

    @Test
    void ofShouldReturnSameValuesIfValid() {
        PageInfo pageInfo = PageInfo.of(3, 15);
        assertEquals(3, pageInfo.getPage());
        assertEquals(15, pageInfo.getSize());
    }

    @Test
    void ofShouldDefaultPageToZeroIfNull() {
        PageInfo pageInfo = PageInfo.of(null, 10);
        assertEquals(0, pageInfo.getPage());
        assertEquals(10, pageInfo.getSize());
    }

    @Test
    void ofShouldDefaultPageToZeroIfNegative() {
        PageInfo pageInfo = PageInfo.of(-1, 10);
        assertEquals(0, pageInfo.getPage());
        assertEquals(10, pageInfo.getSize());
    }

    @Test
    void ofShouldDefaultSizeToTenIfNull() {
        PageInfo pageInfo = PageInfo.of(1, null);
        assertEquals(1, pageInfo.getPage());
        assertEquals(10, pageInfo.getSize());
    }

    @Test
    void ofShouldDefaultSizeToTenIfZero() {
        PageInfo pageInfo = PageInfo.of(1, 0);
        assertEquals(1, pageInfo.getPage());
        assertEquals(10, pageInfo.getSize());
    }

    @Test
    void ofShouldDefaultSizeToTenIfNegative() {
        PageInfo pageInfo = PageInfo.of(1, -5);
        assertEquals(1, pageInfo.getPage());
        assertEquals(10, pageInfo.getSize());
    }
}