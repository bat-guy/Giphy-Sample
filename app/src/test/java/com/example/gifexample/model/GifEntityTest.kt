package com.example.gifexample.model

import com.example.gifexample.MockData
import com.example.gifexample.model.GifEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class GifEntityTest {

    @Test
    fun test_favouriteEntityConvertList() {
        assertEquals(GifEntity.convertList(MockData.getSearchResponse(), hashSetOf("7Ae6u4cMbO33vKKn28")), listOf(MockData.getFavouriteEntity()))
    }

    @Test
    fun test_nonFavouriteEntityConvertList() {
        assertTrue(GifEntity.convertList(MockData.getSearchResponse(), hashSetOf()) != listOf(MockData.getFavouriteEntity()))
    }
}